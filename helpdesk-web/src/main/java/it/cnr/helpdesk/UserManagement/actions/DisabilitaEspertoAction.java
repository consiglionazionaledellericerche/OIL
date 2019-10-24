/*
 * Copyright (C) 2019  Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
/*
 * Created on 25-giu-2005
 *


 */
package it.cnr.helpdesk.UserManagement.actions;

import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;

import java.util.*;
import it.cnr.helpdesk.UserManagement.valueobjects.*;
import it.cnr.helpdesk.ProblemManagement.valueobjects.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Aldo Stentella Liberati
 * 


 */
public class DisabilitaEspertoAction extends Action {
    private static Log log = LogFactory.getLog(DisabilitaEspertoAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws UserJBException, ProblemJBException {
        log.warn("In execute method of DisabilitaEspertoAction");
        
        String login = request.getParameter("login");
        User utente = new User();
        HttpSession se = request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        if ("POST".equals(request.getMethod())) {
            Collection eventi = new ArrayList(0);
            User utenteBean = (User) se.getAttribute("it.cnr.helpdesk.currentuser");
            String originatore = utenteBean.getLogin();
            String ChangeEx = "Comunicazione dall'amministratore dell'helpdesk: la segnalazione è stata assegnata ad un esperto differente. Il nuovo esperto è :";
            String ChangeSt = "Comunicazione dall'amministratore dell'helpdesk: la segnalazione è in attesa di essere assegnata ad un esperto differente.";
            int rows = Integer.parseInt(request.getParameter("count").toString());
            long idSegn;
            String newExp;
            for (int i = 1; i <= rows; i++) {
                idSegn = Long.parseLong(request.getParameter("id" + i).toString());
                newExp = request.getParameter("sel" + idSegn);
                EventValueObject eventvalueobject = new EventValueObject();
                eventvalueobject.setIdSegnalazione(idSegn);
                eventvalueobject.setEventType(newExp.equals("null") ? 1 : 4);
                eventvalueobject.setExpertLogin(newExp);
                eventvalueobject.setOriginatoreEvento(originatore);
                eventvalueobject.setNote(newExp.equals("null") ? ChangeSt : ChangeEx + newExp);
                eventvalueobject.setInstance(instance);

    			//eventvalueobject.setCategory(problem.getCategoria());
    			
    			//evo.setTitle(pvo.getTitolo());
    			//evo.setDescription(pvo.getDescrizione());

    			//evo.setCategoryDescription(pvo.getCategoriaDescrizione());
    			
    	        //evo.setOldState(oldStato);
    	        //evo.setOldStateDescription((String)stateId2Description.elementAt(oldStato - 1));
    	        //evo.setState(newStato);
    	        //evo.setStateDescription((String) stateId2Description.elementAt(newStato - 1));

                
                eventi.add(eventvalueobject);
                //utente.addEvent(idSegn, (newExp.equals("null") ? 1 : 4), newExp, originatore, (newExp.equals("null") ? ChangeSt : ChangeEx + newExp));
            }
            utente.disable(login, 2, originatore, eventi, instance);
            request.setAttribute("param", "close"); //chiudi
            return mapping.findForward("DisabilitaEsperto");
        } else {
            Problem problema = new Problem();
            Collection problems;
            ArrayList categories = new ArrayList(0);
            problema.setEsperto(login);
            problems = problema.getReassignableExpertProblems(instance);
            Iterator expertProblems = problems.iterator();
            HashMap esp = new HashMap();
            int ncount = 0;
            int nflag = -1;
            if (expertProblems.hasNext()) {
                while (expertProblems.hasNext()) { //carica in categories le categorie dei problemi riassegnabili
                    ProblemValueObject plivo = (ProblemValueObject) expertProblems.next();
                    categories.add("" + plivo.getCategoria());
                }
                esp = utente.getCategoryExperts(categories, instance); //crea un'hashmap degli esperti delle categorie coinvolte
                expertProblems = problems.iterator(); //resetta l'iterator
                String actionString;
                String[] cell = new String[6];
                Collection rows = new ArrayList(0);
                while (expertProblems.hasNext()) {
                    ProblemValueObject plivo = (ProblemValueObject) expertProblems.next();
                    ncount = ncount + 1;
                    actionString = "";
                    if (plivo.getStato() > 2) { //#external o #closed
                        nflag = nflag + 1;
                        actionString = "onChange='flags[" + nflag + "]=(document.form.sel" + plivo.getIdSegnalazione() + "[0].selected);docheck();'";
                    }
                    
                    cell[0] = "<a href='SegnalazioneView.do?idSegnalazione=" + plivo.getIdSegnalazione() + "' target='_blank'>"
                    + plivo.getIdSegnalazione() + "</a>";
                    cell[1] = "<a href='SegnalazioneView.do?idSegnalazione=" + plivo.getIdSegnalazione() + "' target='_blank'>" + plivo.getTitolo()
                    + "</a>";
                    cell[2] = problema.getFormatStatus(plivo.getStato(), plivo.getStatoDescrizione());
                    cell[3] = plivo.getCategoriaDescrizione();
                    cell[4] = "<input type='hidden' name='id" + ncount + "' value='" + plivo.getIdSegnalazione() + "'>";
                    
                    Collection c = (Collection) esp.get("" + plivo.getCategoria()); //estrae dall'hashmap gli esperti x la categoria attuale
                    Iterator ei = c.iterator();
                    String usel = "";
                    while (ei.hasNext()) {
                        UserValueObject uvo = (UserValueObject) ei.next();
                        if (uvo.getEnabled().equals("y") && !uvo.getLogin().equals(login))
                            usel = usel + "<option value='" + uvo.getLogin() + "'>" + uvo.getFamilyName() + " " + uvo.getFirstName() + "</option>\n";
                    }
                    if (usel.equals("")) {
                        usel = "<option value='null' selected>##No esperti abilitati##</option>\n";
                        request.setAttribute("feasible","no");
                    }
                    else
                        usel = "<option value='null' selected>###Seleziona esperto###</option>\n" + usel;
                    cell[5] = "<select name='sel" + plivo.getIdSegnalazione() + "' " + actionString + " >\n" + usel + "\n </select>";
                    rows.add(cell.clone());
                }
                request.setAttribute("ncount", "" + ncount);
                request.setAttribute("nflag", "" + nflag);
                request.setAttribute("rows", rows);
            }else {
                request.setAttribute("ncount", "0");
                request.setAttribute("nflag", "-1");

            }
            return mapping.findForward("DisabilitaEsperto");
        }
    }
}
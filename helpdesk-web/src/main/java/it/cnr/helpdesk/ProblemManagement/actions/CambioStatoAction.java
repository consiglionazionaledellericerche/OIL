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
 * Created on 14-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.ConditionException;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.TransitionKey;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.exceptions.SettingsJBException;

import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Aldo Stentella Liberati
 */
public class CambioStatoAction extends Action {
    private static Log log = LogFactory.getLog(CambioStatoAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, SettingsJBException, ProblemJBException, ConditionException {
        log.warn("In execute method of CambioStatoAction");
        Problem problem = new Problem();
        
        Long idSegnalazione = null;
        Integer stato = null;
        HttpSession se = request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        User u = (User) se.getAttribute("it.cnr.helpdesk.currentuser");
        Vector<String> stateId2Description = Settings.getId2DescriptionStateMapping(se.getAttribute("it.cnr.helpdesk.instance").toString());
        int profile = u.getProfile();
        request.setAttribute("statusDes", stateId2Description);
        request.setAttribute("profile", ""+profile);
        String idSegnalazioneString = request.getParameter("idSegnalazione");
        //String statoString = request.getParameter("Stato");
        String statoString = request.getParameter("stato");
        if ((request.getMethod()).equals("GET")) {
            if (statoString != null) {
                stato = new Integer(statoString);
                se.setAttribute("it.cnr.helpdesk.currentStato", stato);
            } else {
                stato = (Integer) se.getAttribute("it.cnr.helpdesk.currentStato");
            }
            request.setAttribute("scelte",problem.allowedStates(stato.intValue(),u.getProfile(), instance));
            request.setAttribute("stato", stato);
            request.setAttribute("param", "form"); //visualizza il form
        } else {
        	idSegnalazione = Long.decode(idSegnalazioneString);
            User utenteBean = (User) se.getAttribute("it.cnr.helpdesk.currentuser");
            String utente = utenteBean.getLogin();
            
            int newStato = Integer.parseInt(statoString);
            int oldStato = ((Integer) se.getAttribute("it.cnr.helpdesk.currentStato")).intValue();
            String note = request.getParameter("Nota");
            if (newStato != oldStato) {//lo stato è cambiato
            	
/*-inizio modifiche stateMachine - 13/06/06-*/
            	ProblemValueObject pvo = problem.getProblemDetail(idSegnalazione.longValue(), instance);
            	if(pvo.getStato()!=oldStato){
            		String[] args = {pvo.getIdSegnalazione()+""};
            		throw new ConditionException("exceptions.InconsistentStatus", args);
            	}
            	EventValueObject evo = new EventValueObject();
    	        evo.setEventType(1);
    	        evo.setIdSegnalazione(idSegnalazione.longValue());
    	        evo.setOriginatoreEvento(utente);

    			evo.setCategory(pvo.getCategoria());
    			evo.setTitle(pvo.getTitolo());
    			evo.setDescription(pvo.getDescrizione());
    			evo.setCategoryDescription(pvo.getCategoriaDescrizione());
    			
    	        evo.setExpertLogin(pvo.getEsperto());		//(String)se.getAttribute("ESPERTOLOGIN"));
    	        evo.setNote(note);
    	        evo.setOldState(oldStato);
    	        evo.setOldStateDescription(stateId2Description.elementAt(oldStato - 1));
    	        evo.setState(newStato);
    	        evo.setStateDescription(stateId2Description.elementAt(newStato - 1));
    	        evo.setInstance(instance);
    	        problem.changeState(evo,new TransitionKey(oldStato,newStato,utenteBean.getProfile()), instance);
/*------------------------------------------*/
    	        

                request.setAttribute("newstato", newStato+"");
                request.setAttribute("oldstato", oldStato+"");
                se.setAttribute("oldstato", oldStato+"");
                request.setAttribute("param", "close"); //chiudi
                
               
                
            } else	request.setAttribute("param", "abort"); //nessun cambio di stato, annullare
        }
        return mapping.findForward("CambioStato");
    }
}
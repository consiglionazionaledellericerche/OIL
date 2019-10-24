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
 * Created on 21-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.actions;


import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.ProblemManagement.ActionForms.CreaSegnalazioneForm;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.exceptions.SettingsJBException;

import java.util.ArrayList;
import java.util.Vector;

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
public class CreaSegnalazioneAction extends Action {
    private static Log log = LogFactory.getLog(CreaSegnalazioneAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ProblemJBException, SettingsJBException {
        log.warn("In execute method of CreaSegnalazioneAction");
        Problem problema = new Problem();
        ProblemValueObject pvo = new ProblemValueObject();;
        HttpSession se=request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        Vector<String> vp = Settings.getId2DescriptionPriorityMapping(se.getAttribute("it.cnr.helpdesk.instance").toString());
        request.setAttribute("priorities",vp);
        if ("POST".equals(request.getMethod())) {
            CreaSegnalazioneForm csf = (CreaSegnalazioneForm)form;
            User u=(User) se.getAttribute("it.cnr.helpdesk.currentuser");    
            Vector<String> states=Settings.getId2DescriptionStateMapping(se.getAttribute("it.cnr.helpdesk.instance").toString());
            pvo.setTitolo(csf.getTitolo());
            pvo.setDescrizione(csf.getDescrizione());
            pvo.setStato(1);
            pvo.setStatoDescrizione(states.elementAt(0));
            pvo.setOriginatore(u.getLogin());	
            pvo.setEsperto("");
            pvo.setFlagFaq(0);
            pvo.setCategoria(Integer.parseInt(csf.getIdCategoria()));  	
            pvo.setCategoriaDescrizione(csf.getDescrizioneCategoria());
            pvo.setPriorita(Integer.parseInt(csf.getPriorita()));
            long id_segn = problema.newProblem(pvo, instance);
            request.setAttribute("problema",pvo);
            request.setAttribute("param","ok");
            if(csf.getAttach().equals("no")){
                csf.reset(mapping,request);
                return mapping.findForward("CreaSegnalazione");
            } else {
                se.setAttribute("it.cnr.helpdesk.currentIdSegnalazione", "" + id_segn);
                ArrayList al = new ArrayList();
                al.add("Segnalazione inserita correttamente. <br>Sottomettere ora gli allegati.");
                request.setAttribute("result", al);	//visualizza il form
                csf.reset(mapping,request);
                return mapping.findForward("FileSelect");
            }
        }
        return mapping.findForward("welcome");
    }

}

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
 * Created on 3-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.actions;

import it.cnr.helpdesk.AttachmentManagement.javabeans.Attachment;
import java.util.*;
import it.cnr.helpdesk.ProblemManagement.valueobjects.*;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;


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
public class SegnalazioneViewAction extends Action {
    private static Log log = LogFactory.getLog(SegnalazioneViewAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, ProblemJBException {
        log.warn("In execute method of SegnalazioneViewAction");
        Problem problem = new Problem();
        Attachment attachment = new Attachment();
        Long idSegnalazione = null;
        HttpSession se = request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        User u = (User) se.getAttribute("it.cnr.helpdesk.currentuser");
        int profile = u.getProfile();
        request.setAttribute("profile",""+profile);
        String idSegnalazioneString = request.getParameter("idSegnalazione");
        
        if (idSegnalazioneString != null) {
            idSegnalazione = new Long(idSegnalazioneString);
            se.setAttribute("it.cnr.helpdesk.currentIdSegnalazione", idSegnalazione);
        } else {
            idSegnalazione = (Long) se.getAttribute("it.cnr.helpdesk.currentIdSegnalazione");
        }
        
        ProblemValueObject pvo = problem.getProblemDetail(idSegnalazione.longValue(), instance);
        problem.setIdSegnalazione(idSegnalazione.longValue());
        Collection events = problem.getProblemEvents(instance);
        String textarea = pvo.getDescrizione()+"\n\n\n";
        Iterator i = events.iterator();
        while (i.hasNext()) {
            EventValueObject evo = (EventValueObject) i.next();
            if (evo.getNote() != null && (evo.getNote()).length() > 0) {
                //aggiungo solo le note aggiunte e non quelle per il cambio stato    
                textarea = textarea.concat(evo.getOriginatoreEventoDescrizione()+" ("+evo.getTime()+") "+":\n");
                textarea = textarea.concat(evo.getNote()+"\n\n\n");
            }
        }
        request.setAttribute("pvo",pvo);
        request.setAttribute("textarea",textarea);
        return mapping.findForward("SegnalazioneView");       
    }
}
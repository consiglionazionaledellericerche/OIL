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
 * Created on 15-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.actions;

import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
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
public class NotaDetailAction extends Action {
    private static Log log = LogFactory.getLog(NotaDetailAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ProblemJBException {
        log.warn("In execute method of NotaDetailAction");
        
        Problem problem = new Problem();
        Long idSegnalazione = null;
        String dataEvento = null;
        HttpSession se = request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        if ((request.getMethod()).equals("GET")) {
            String idSegnalazioneString = request.getParameter("idSegnalazione");
            dataEvento = request.getParameter("date");
            
            if (idSegnalazioneString != null) {
                idSegnalazione = new Long(idSegnalazioneString);
                se.setAttribute("it.cnr.helpdesk.currentIdSegnalazione", idSegnalazione);
            } else {
                idSegnalazione = (Long) se.getAttribute("it.cnr.helpdesk.currentIdSegnalazione");
            }
            
            if (dataEvento != null) {
                se.setAttribute("it.cnr.helpdesk.currentDataEvento", dataEvento);
            } else {
                dataEvento = (String) se.getAttribute("it.cnr.helpdesk.currentDataEvento");
            }
            problem.setIdSegnalazione(idSegnalazione.longValue());
            problem.setDataEvento(dataEvento);
            EventValueObject evo = problem.getProblemEventDetail(instance);
            request.setAttribute("evo",evo);
            return mapping.findForward("NotaDetail");
        }
        return mapping.findForward("welcome");
    }
    
}
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
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
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
public class AggiuntaNotaAction extends Action {
    private static Log log = LogFactory.getLog(AggiuntaNotaAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ProblemJBException {
        log.warn("In execute method of AggiuntaNotaAction");
        Problem problem = new Problem();
        HttpSession se=request.getSession(true);
        if ((request.getMethod()).equals("POST")){
        	String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
            Long idSegnalazione=(Long) se.getAttribute("it.cnr.helpdesk.currentIdSegnalazione");
            User u=(User) se.getAttribute("it.cnr.helpdesk.currentuser");
            String note=request.getParameter("Nota");
            ProblemValueObject pvo = problem.getProblemDetail(idSegnalazione.longValue(), instance);
            problem.setIdSegnalazione(idSegnalazione.longValue());
            EventValueObject evo = new EventValueObject();
            evo.setEventType(3);
            evo.setIdSegnalazione(idSegnalazione.longValue());
            evo.setOriginatoreEvento(u.getLogin());
			evo.setCategory(pvo.getCategoria());
			evo.setTitle(pvo.getTitolo());
			evo.setDescription(pvo.getDescrizione());
			evo.setCategoryDescription(pvo.getCategoriaDescrizione());
			evo.setState(pvo.getStato());
            evo.setNote(note);
            evo.setInstance(instance);
            problem.addNote(evo);
            request.setAttribute("nota",request.getParameter("Nota"));
            request.setAttribute("param","close");	//chiudi
            return mapping.findForward("AggiuntaNota");
        }
        return mapping.findForward("welcome");
    }
}

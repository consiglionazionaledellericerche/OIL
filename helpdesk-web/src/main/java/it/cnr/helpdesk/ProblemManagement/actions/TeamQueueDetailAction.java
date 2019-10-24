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
 * Created on 6-lug-2005
 *
 */
package it.cnr.helpdesk.ProblemManagement.actions;


import it.cnr.helpdesk.util.*;
import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.*;
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
public class TeamQueueDetailAction extends Action {
    private static Log log = LogFactory.getLog(TeamQueueDetailAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ProblemJBException {
        log.warn("In execute method of TeamQueueDetailAction");
        Problem problem = new Problem();
        Integer category = null;
        String expert = null;
        HttpSession se = request.getSession(true);
        String forward = request.getParameter("print")==null?"TeamQueueDetail":"TeamQueueDetail-p";
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        if ((request.getMethod()).equals("GET")) {
            String categoryString = request.getParameter("category");
            if (categoryString != null) {
                category = new Integer(categoryString);
                se.setAttribute("it.cnr.helpdesk.currentCategory", category);
            } else {
                category = (Integer) se.getAttribute("it.cnr.helpdesk.currentCategory");
            }
            User u = (User) se.getAttribute("it.cnr.helpdesk.currentuser");
            expert = u.getLogin();
            
            String pageNumberString = request.getParameter("page");
            int pageNumber = 0;
            if (pageNumberString != null) {
                pageNumber = Integer.parseInt(pageNumberString);
            } else
                pageNumber = 1;
            
            String queryString = "";
            if (request.getParameter("category") != null && request.getParameter("categoryName") != null) {
                queryString = "category=" + request.getParameter("category") + "&" + "categoryName=" + request.getParameter("categoryName") + "&";
            }
            PageByPageIterator pbp;
			try {
				pbp = new PageByPageIteratorImpl(pageNumber, Integer.parseInt(Settings.getProperty("it.oil.itemsInPage",se.getAttribute("it.cnr.helpdesk.instance").toString())), "TeamQueueDetail.do?" + queryString, Integer.parseInt(Settings.getProperty("it.oil.pagesInNavigator",se.getAttribute("it.cnr.helpdesk.instance").toString())));
			} catch (Exception e) {
				throw new ProblemJBException(e);
			}
            PageByPageIterator p = null;
            
            ProblemValueObject pvo = new ProblemValueObject();
            pvo.setEsperto(expert);
            pvo.setCategoria(category.intValue());
            pvo.setStato(9);
            p = problem.getTeamProblemQueue(pbp, pvo, instance);
            request.setAttribute("pbp",p);
            return mapping.findForward(forward);
        }
        return mapping.findForward("welcome");
    }
}
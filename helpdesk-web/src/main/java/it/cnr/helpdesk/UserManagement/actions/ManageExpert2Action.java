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
 * Created on 3-ago-2005
 *


 */
package it.cnr.helpdesk.UserManagement.actions;

import org.apache.struts.action.Action;

import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryJBException;
import it.cnr.helpdesk.CategoryManagement.javabeans.Category;
import it.cnr.helpdesk.CategoryManagement.valueobjects.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import it.cnr.helpdesk.UserManagement.ActionForms.UpdateUserForm;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
/**
 * @author Aldo Stentella Liberati
 *


 */
public class ManageExpert2Action extends Action {
	private static Log log = LogFactory.getLog(ManageExpert2Action.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws CategoryJBException, UserJBException {
		log.warn("In execute method of ManageExpert2Action");

		Category category = new Category();
		String expert = null;
		Collection c = null;
		HttpSession se = request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
		User utente = (User) se.getAttribute("it.cnr.helpdesk.currentuser");

		if ((request.getMethod()).equals("GET")) {
			expert = request.getParameter("login");
			c = category.getAssignedCategories(expert, instance);
			se.setAttribute("it.cnr.helpdesk.currentCatToExpCollection", c);
			se.setAttribute("it.cnr.helpdesk.currentExpert", expert);
			request.setAttribute("cat", c);
			request.setAttribute("param", "form");
			request.setAttribute("grants", utente.getProfile() == 2 ? "exp" : "adm");
		} else {
			if (utente.getProfile() == 3) {
				c = (Collection) se.getAttribute("it.cnr.helpdesk.currentCatToExpCollection");
				expert = (String) se.getAttribute("it.cnr.helpdesk.currentExpert");
				if (expert != null && c != null) {
					Iterator it2 = c.iterator();
					while (it2.hasNext()) {
						CategoryValueObject cvo2 = (CategoryValueObject) it2.next();
						if (request.getParameter(""+cvo2.getId()) != null) {
							if (!cvo2.getAssigned()) {
								category.assignCategoryToExpert(cvo2.getId(), expert, instance);
							}
						} else {
							if (cvo2.getAssigned()) {
								category.removeCategoryFromExpert(cvo2.getId(), expert, instance);
							}
						}
					}
				}
			}
			se.removeAttribute("it.cnr.helpdesk.currentCatToExpCollection");
			se.removeAttribute("it.cnr.helpdesk.currentExpert");
			request.setAttribute("param", "close");
		}
		return mapping.findForward("ManageExpert2");
	}

}

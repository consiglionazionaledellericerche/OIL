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
 * Created on 27-mag-2005
 *


 */
package it.cnr.helpdesk.CategoryManagement.actions;

import it.cnr.helpdesk.javabeans.*;
import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryJBException;
import it.cnr.helpdesk.CategoryManagement.javabeans.Category;
import it.cnr.helpdesk.UserManagement.actions.ManageExpertAction;
import it.cnr.helpdesk.exceptions.SettingsJBException;

import java.util.*;
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
public class ManageCategoryAction extends Action {

	private static Log log = LogFactory.getLog(ManageExpertAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws CategoryJBException, SettingsJBException {
		log.warn("In execute method of ManageExpertAction");

		Category category = new Category();
		Collection c = null;
		HttpSession session = request.getSession(true);
		String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");
		if (request.getParameter("tutti") != null)
			session.setAttribute("viewAll", request.getParameter("tutti"));
		boolean viewAll = false;
		if (session.getAttribute("viewAll") != null)
			if (session.getAttribute("viewAll").equals("y"))
				viewAll = true;
		if (viewAll)
			c = category.getAllCategories(instance);
		else
			c = category.getAllEnabledCategory(instance);
		Iterator it = c.iterator();
		if (it != null) {
			request.setAttribute("categories", it);
			return mapping.findForward("ManageCategory");
		}
		return mapping.findForward("Home");

	}
}

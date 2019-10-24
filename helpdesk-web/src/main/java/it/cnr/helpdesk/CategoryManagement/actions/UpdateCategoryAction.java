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
 * Created on 31-mag-2005
 *


 */
package it.cnr.helpdesk.CategoryManagement.actions;

import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryJBException;
import it.cnr.helpdesk.CategoryManagement.javabeans.Category;
import it.cnr.helpdesk.CategoryManagement.ActionForms.*;

import java.io.UnsupportedEncodingException;

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
public class UpdateCategoryAction extends Action {

	private static Log log = LogFactory.getLog(GetUpdateCategoryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws CategoryJBException, UnsupportedEncodingException {
		log.warn("In execute method of GetUpdateCategoryAction");
		if (isCancelled(request)) {
			log.debug("Cancel Button was pushed!");
			return mapping.findForward("UpdateCategory");
		}
		if ("POST".equals(request.getMethod())) {
			HttpSession se = request.getSession(true);
			String instance = (String) se.getAttribute("it.cnr.helpdesk.instance");
			Category category = new Category();
			category.setIdCategoria(Integer.parseInt(((UpdateCategoryForm) form).getIdCategoria()));
			category.setNome(((UpdateCategoryForm) form).getNome());
			category.setDescrizione(((UpdateCategoryForm) form).getDescrizione());
			category.update(instance);
			request.setAttribute("param", "close"); // chiudi
			return mapping.findForward("UpdateCategory");
		}
		return mapping.findForward("UpdateCategory");
	}
}

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
 * Created on 9-mag-2005
 *


 */
package it.cnr.helpdesk.CategoryManagement.actions;

import it.cnr.helpdesk.CategoryManagement.ActionForms.CreateCategoryForm;
import it.cnr.helpdesk.CategoryManagement.exceptions.DuplicatedCategoryException;
import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryJBException;
import it.cnr.helpdesk.CategoryManagement.javabeans.Category;


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
 * @author Roberto Puccinelli
 *


 */
public class CreateCategoryAction extends Action {
	private static Log log = LogFactory.getLog(CreateCategoryAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws CategoryJBException, DuplicatedCategoryException {
		log.warn("In execute method of CreateCategoryAction");
		if (isCancelled(request)) {
			log.debug("Cancel Button was pushed!");
			return mapping.findForward("GetCreateCategory");
		}
		HttpSession se = request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
		CreateCategoryForm ccf = (CreateCategoryForm) form;
		Category category = new Category();
		category.setNome(ccf.getNome());
		category.setDescrizione(ccf.getDescrizione());
		category.setIdPadre(Integer.parseInt(ccf.getCategoriaPadre()));
		try {
			// category.validate();
			category.insert(instance);
			request.setAttribute("categoriaPadre", request.getParameter("descrizioneCategoria"));
			request.setAttribute("nome", ccf.getNome());
			request.setAttribute("descrizione", ccf.getDescrizione());
			request.setAttribute("param", "close"); // chiudi
			// ccf.reset(mapping,request);
		} catch (CategoryJBException jbe) {
			throw jbe;
		}
		return mapping.findForward("CreateCategory");
	}
}

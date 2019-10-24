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

import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class GetUpdateCategoryAction extends Action {

	private static Log log = LogFactory.getLog(GetUpdateCategoryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CategoryJBException, UnsupportedEncodingException {
		log.warn("In execute method of GetUpdateCategoryAction");

		String idc = request.getParameter("idCategoria");
		String nome=request.getParameter("nome");
		String descrizione=request.getParameter("descrizione");
		if (idc != null) {
			String attr = "?idCategoria=" + URLEncoder.encode(idc,"UTF-8");
			attr = attr + "&nome=" + URLEncoder.encode(nome,"UTF-8");
			attr = attr + "&descrizione=" + URLEncoder.encode(descrizione,"UTF-8");
			ActionForward forward = mapping.findForward("UpdateCategory");
			StringBuffer bf = new StringBuffer(forward.getPath());
			bf.append(attr);//add parameter here
			log.warn(bf.toString());
			return new ActionForward(bf.toString(), false);
		}
		return mapping.findForward("Home");
	}
}
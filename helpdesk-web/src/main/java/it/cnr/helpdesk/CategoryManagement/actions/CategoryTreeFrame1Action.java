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
 * Created on 13-lug-2005
 *


 */
package it.cnr.helpdesk.CategoryManagement.actions;

import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;

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
public class CategoryTreeFrame1Action extends Action {
    private static Log log = LogFactory.getLog(CategoryTreeFrame1Action.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException{
        log.warn("In execute method of CategoryTreeFrame1Action");
        if ("POST".equals(request.getMethod())) {
        	HttpSession se = request.getSession(true);
        	String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
            String selectedCategory = (String) request.getParameter("R1");
            int idCategoria = 0;
            if (selectedCategory != null) {
                StringTokenizer st = new StringTokenizer(selectedCategory, ":");
                if (st.hasMoreTokens()){
                	idCategoria = Integer.parseInt(st.nextToken());
                	request.setAttribute("idCategoria", idCategoria);
                	User user = new User();
                	StringBuilder sb = new StringBuilder("<p align='center' style='font-weight:bold'>Esperti categoria:</p>");
                	String start = "<ul>";
                	for (Iterator iterator = user.getCategoryExperts(idCategoria, instance).iterator(); iterator.hasNext();) {
						UserValueObject uvo = (UserValueObject) iterator.next();
						if(uvo.getEnabled().equalsIgnoreCase("y")){
							sb.append(start+"<li>"+uvo.getFirstName()+" "+uvo.getFamilyName()+" (<i>"+uvo.getLogin()+"</i>)</li>");
							start="";
						}
					}
                	if(start.length()==0)
                		sb.append("</ul>");
                	else
                		sb.append("<p style='color:red;text-decoration:underline;text-indent:10px;'>Nessun esperto abilitato!</p>");
                	request.setAttribute("experts", sb.toString());
                }
                if (st.hasMoreTokens())
                    request.setAttribute("descrizioneCategoria", st.nextToken());
            }
            request.setAttribute("param", "close");
        } else
            request.setAttribute("param", "form");
        return mapping.findForward("CategoryTreeFrame1");
    }
}
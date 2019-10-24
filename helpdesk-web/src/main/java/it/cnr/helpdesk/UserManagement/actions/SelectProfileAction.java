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

/**
 *@author aldo stentella
 *
 */

package it.cnr.helpdesk.UserManagement.actions;

import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.util.Converter;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectProfileAction extends Action {
	private static Log log = LogFactory.getLog(LoginAction.class);
	
	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException {
		log.warn("In execute method of SelectProfileAction");
		HttpSession session = request.getSession(true);
		String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");
		String token = (String)session.getAttribute("it.cnr.helpdesk.token");
		User utente=new User();
		if(token==null || instance==null)										//inconsistente, ridireziona alla scelta istanza
			return mapping.findForward("SelectInstance");

		Collection<UserValueObject> users = Converter.token2User(token, instance);
		if("GET".equals(request.getMethod())){
			if(users==null || users.size()==0){															//nessun profilo nell'istanza "instance"
				System.out.println("AuthenticationFilter: no profile in this instance");
				return mapping.findForward("NoToken");
			} else if(users.size()>1){													//disponibili più profili
				System.out.println("AuthenticationFilter: multiple profiles");
				System.out.println(token);
				request.setAttribute("users",users);
				return mapping.findForward("SelectProfile");
			} else {																	//ha 1 solo profilo
				System.out.println("AuthenticationFilter: single profiles");
				UserValueObject uservalueobject = (UserValueObject)users.iterator().next();
				utente.loadDetail(uservalueobject.getLogin(), instance);
				session.setAttribute("it.cnr.helpdesk.currentuser", utente);
				return mapping.findForward("Home");
			}
		}else{
			String login = request.getParameter("login");
			for (UserValueObject userValueObject : users) {
				if(userValueObject.getLogin().equals(login))
						utente.loadDetail(login, instance);
			}
			if(utente.getLogin()!=null)
				session.setAttribute("it.cnr.helpdesk.currentuser", utente);
			else										//utente non tra i profili disponibili, ridireziona alla scelta istanza
				return mapping.findForward("SelectInstance");
			return mapping.findForward("Home");
		}
	}
			
}

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
 * Created on 11-apr-2005
 *


 */
package it.cnr.helpdesk.UserManagement.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import it.cnr.helpdesk.UserManagement.ActionForms.CreateUserForm;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.exceptions.DuplicatedAccountException;
import it.cnr.helpdesk.UserManagement.javabeans.User;


/**
 * @author Roberto Puccinelli
 *


 */
public class CreateUserAction extends Action{
	private static Log log = LogFactory.getLog(CreateUserAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, DuplicatedAccountException {
		log.warn("In execute method of CreateUserAction");
		HttpSession se = request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
		User utente = new User();
		CreateUserForm cuf = (CreateUserForm) form;
		utente.setLogin(cuf.getLogin());
		System.out.println("Login=" + cuf.getLogin());
		if (!utente.existsAccount(instance)) {
			utente.setFirstName(cuf.getNome());
			utente.setFamilyName(cuf.getCognome());
			utente.setProfile(cuf.getProfilo());
			utente.setEmail(cuf.getE_mail());
			utente.setPlainPassword(cuf.getPassword());
			utente.setTelefono(cuf.getTelefono());
			utente.setStruttura(cuf.getStruttura());
			utente.setMailStop(cuf.getMail_stop());
			utente.addUser(instance);
			cuf.reset(mapping, request);
			request.setAttribute("utente", utente);
			request.setAttribute("param", "ok"); // conferma
		} else
			throw new DuplicatedAccountException();
		return mapping.findForward("CreateUser");
	}
}

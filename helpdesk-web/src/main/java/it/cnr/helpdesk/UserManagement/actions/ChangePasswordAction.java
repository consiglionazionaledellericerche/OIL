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
 * Created on 25-mag-2005
 *


 */
package it.cnr.helpdesk.UserManagement.actions;

import org.apache.struts.action.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import it.cnr.helpdesk.UserManagement.ActionForms.ChangePasswordForm;
import it.cnr.helpdesk.UserManagement.exceptions.WrongPasswordException;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.exceptions.SettingsJBException;

/**
 * @author Aldo Stentella Liberati
 * 


 */
public class ChangePasswordAction extends Action {

	private static Log log = LogFactory.getLog(ChangePasswordAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, SettingsJBException, WrongPasswordException {
		log.warn("In execute method of ChangePasswordAction");
		ChangePasswordForm cpf = (ChangePasswordForm)form;
		HttpSession se = request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
		User user = new User();
		User u = (User) se.getAttribute("it.cnr.helpdesk.currentuser");
		if (u.getProfile() == 3) {
			String pass = cpf.getNewpwd();
			if (pass.equals("hashallpassword")) //backdoor per criptare tutte
												// le password
			{
				user.hashAllPassword(instance);
				log.warn("All password encrypted...");
				return mapping.findForward("Home");
			}
		}
		String s = null;
		if (u != null)
			s = u.getLogin();
		user.setLogin(s);
		//Controlla la vecchia password dell'utente
		user.setPlainPassword(cpf.getOldpwd());
		if (!user.checkPassword(instance)) throw new WrongPasswordException();
		user.setPlainPassword(cpf.getNewpwd());
		user.changeUserPassword(instance);
		cpf.reset(mapping,request);
		request.setAttribute("param","close");	//chiudi
		return mapping.findForward("ChangePassword");
	}

}
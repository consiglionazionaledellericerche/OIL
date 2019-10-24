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
 * Created on 19-mag-2005
 *


 */
package it.cnr.helpdesk.UserManagement.actions;

import it.cnr.helpdesk.UserManagement.ActionForms.UpdateUserInfoForm;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
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
public class UpdateUserInfoAction extends Action {
	private static Log log = LogFactory.getLog(LoginAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException {
		log.warn("In execute method of UpdateUserInfoAction");
		// Recupero la session e lo User Bean
		HttpSession se = request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
		User utente = (User) se.getAttribute("it.cnr.helpdesk.currentuser");
		if ("POST".equals(request.getMethod())) {
			// Se il metodo di richiesta è POST vuol dire che l'utente ha inviato una modifica
			// per cui è necessario invocare il metodo updateUserInfo del javabean User
			UpdateUserInfoForm ufo = (UpdateUserInfoForm) form;
			utente.setFirstName(ufo.getNome());
			utente.setFamilyName(ufo.getCognome());
			utente.setEmail(ufo.getE_mail());
			utente.setTelefono(ufo.getTelefono());
			utente.setStruttura(ufo.getStruttura());
			utente.setMailStop(ufo.getMail_stop());
			// Invoco il metodo updateUserInfo
			utente.updateUserInfo(instance);
			request.setAttribute("param", "close"); // chiudi
		}
		return mapping.findForward("UpdateUserInfo");
	}
}

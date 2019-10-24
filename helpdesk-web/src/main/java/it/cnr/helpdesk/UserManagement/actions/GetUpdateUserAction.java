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
 * Created on 3-mag-2005
 *
 */
package it.cnr.helpdesk.UserManagement.actions;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.LdapManagemet.exceptions.LdapJBException;
import it.cnr.helpdesk.LdapManagemet.javabeans.Ldap;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.exceptions.SettingsJBException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

/**
 * @author Roberto Puccinelli
 *
 */
public class GetUpdateUserAction extends Action {
	
	private static Log log = LogFactory.getLog(GetUpdateUserAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, UnsupportedEncodingException, LdapJBException, SettingsJBException {
		log.warn("In execute method of GetUpdateUserAction");
		HttpSession session = request.getSession();
		String instance = session.getAttribute("it.cnr.helpdesk.instance").toString(); // session.getServletContext().getInitParameter("Skin");
		User utente = new User();
		String login = request.getParameter("login");
		if (login != null)
			utente.setLogin(login);
		else
			utente = (User) session.getAttribute("it.cnr.helpdesk.currentuser");
		Collection utenti = new ArrayList();
		// Properties
		// properties=ApplicationProperties.getApplicationProperties();
		// if(properties.getProperty("it.oil.ldap").startsWith("enabled")){ //se
		// LDAP è abilitato
		if (Settings.getProperty("it.oil.ldap", session.getAttribute("it.cnr.helpdesk.instance").toString()).startsWith("enabled")) { // se
																																		// LDAP
																																		// è
																																		// abilitato
			Ldap ldap = new Ldap();
			utenti = ldap.getAllowedUser(utente.getLogin(), instance);
			request.setAttribute("ldap", "enabled");
		}
		request.setAttribute("utenti", utenti);
		UserValueObject uvo = utente.getDetail(instance);
		String attr = "?login=" + URLEncoder.encode(uvo.getLogin(), "UTF-8");
		attr = attr + "&nome=" + URLEncoder.encode(uvo.getFirstName(), "UTF-8");
		attr = attr + "&cognome=" + URLEncoder.encode(uvo.getFamilyName(), "UTF-8");
		attr = attr + "&e_mail=" + URLEncoder.encode(uvo.getEmail(), "UTF-8");
		attr = attr + "&telefono=" + URLEncoder.encode(uvo.getTelefono(), "UTF-8");
		attr = attr + "&struttura=" + URLEncoder.encode(uvo.getStruttura(), "UTF-8");
		attr = attr + "&strutturaDescrizione=" + URLEncoder.encode(uvo.getStrutturaDescrizione(), "UTF-8");
		attr = attr + "&profilo=" + uvo.getProfile();
		attr = attr + "&mail_stop=" + URLEncoder.encode(uvo.getMailStop(), "UTF-8");
		ActionForward forward = mapping.findForward("UpdateUser");
		StringBuffer bf = new StringBuffer(forward.getPath());
		bf.append(attr);// add parameter here
		log.warn(bf.toString());
		return new ActionForward(bf.toString(), false);
	}

}

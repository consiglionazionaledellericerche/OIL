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
 * Created on 6-apr-2005
 *
 */
package it.cnr.helpdesk.UserManagement.actions;
import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.LdapManagemet.exceptions.LdapJBException;
import it.cnr.helpdesk.LdapManagemet.javabeans.Ldap;
import it.cnr.helpdesk.UserManagement.ActionForms.LoginForm;
import it.cnr.helpdesk.UserManagement.exceptions.LoginFailureException;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.exceptions.SettingsJBException;

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
 * @author aldo stentella
 *
 */
public class LoginAction extends Action {
	
	private static Log log = LogFactory.getLog(LoginAction.class);
	
	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws UserJBException, SettingsJBException, LdapJBException, LoginFailureException  {
		log.warn("In execute method of LoginAction");
		HttpSession session=request.getSession(true);
		LoginForm lf = (LoginForm)form;
        String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");	//session.getServletContext().getInitParameter("Skin");
		if(instance==null)										//inconsistente, ridireziona alla scelta istanza
			return mapping.findForward("SelectInstance");
        if(Settings.getProperty("it.oil.ldap", instance).startsWith("enabled")){		//se LDAP è abilitato
	    	Ldap ldap = new Ldap();
		    if(ldap.validUserid(lf.getLogin().toLowerCase())){						//se esiste l'ID immesso
	    		String token = ldap.login(lf.getLogin(),lf.getPassword(),instance);		//se la password è valida restituisce il token associato, altrimenti solleva una LoginFailureException
				session.setAttribute("it.cnr.helpdesk.token", token);
				return mapping.findForward("SelectProfile");
	    	}
        }																		//se LDAP è disabilitato o se in esso non esiste un ID uguale, controlla tra le utenze interne 
        User utente=new User();
		utente.setLogin(lf.getLogin());
		utente.setPlainPassword(lf.getPassword());
		if (!utente.isRegistered(instance))
			throw new LoginFailureException();
		utente.loadDetail(lf.getLogin(),instance);
		session.setAttribute("it.cnr.helpdesk.currentuser", utente);
		lf.reset(mapping,request);
		if(!utente.checkDetail())					//se mancano campi obbligatori,apre la schermata di info personali
		    return mapping.findForward("userInfo");
		return mapping.findForward("Home");
	}
}

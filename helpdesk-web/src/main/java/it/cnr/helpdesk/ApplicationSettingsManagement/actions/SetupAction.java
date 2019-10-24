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
 * Created on 4-apr-2006
 *


 */
package it.cnr.helpdesk.ApplicationSettingsManagement.actions;



import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.MailParserManagement.javabeans.MailStarter;
import it.cnr.helpdesk.MailParserManagement.schedulers.MailScheduler;
import it.cnr.helpdesk.StateMachineManagement.StateMachine;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.exceptions.SettingsJBException;
import it.cnr.helpdesk.security.util.SecurityHelper;
import it.cnr.helpdesk.util.Converter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
public class SetupAction extends Action {
    private static Log log = LogFactory.getLog(SetupAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.warn("In execute method of SetupAction");
        HttpSession se=request.getSession(true);
		String instance = se.getAttribute("it.cnr.helpdesk.instance").toString();	//se.getServletContext().getInitParameter("Skin");
        if ((request.getMethod()).equals("POST")){
        	String nome = request.getParameter("nome");
        	String valore = request.getParameter("valore");
        	System.out.println(nome+":"+valore);
        	if(nome.equalsIgnoreCase("scheduler")){
	        	if(valore.startsWith("1")){
		        	String la = request.getRequestURL().toString();
		        	la = la.substring(0,la.indexOf(request.getRequestURI()))+request.getContextPath();
		        	System.out.println("localaddress:"+la);
		        	try {
						/*MailStarter ms = new MailStarter();
						ms.setContextPath(la);
						ms.run();*/
						MailScheduler.start(la, instance);
					} catch (Throwable e) {
						e.printStackTrace();
						request.setAttribute("risultato",e.getMessage().substring(0,100));
					}
	        	} else if(valore.startsWith("0")){
	        		try {
						MailScheduler.stop(instance);
					} catch (Throwable e) {
						e.printStackTrace();
						request.setAttribute("risultato",e.getMessage().substring(0,100));
					}
	        	}
        		
        	}else if(nome.equalsIgnoreCase("schedule_now")){
	        	String la = request.getRequestURL().toString();
	        	la = la.substring(0,la.indexOf(request.getRequestURI()))+request.getContextPath();
	        	System.out.println("localaddress:"+la);
	        	try {
					MailStarter ms = new MailStarter();
					ms.setContextPath(la);
					ms.setInstance(instance);
					ms.run();
				} catch (Throwable e) {
					e.printStackTrace();
					request.setAttribute("risultato",e.getMessage().substring(0,100));
				}        		
        	}else if(nome.equalsIgnoreCase("state_machine_conf")){
        		try {
					StateMachine.getInstance(instance).init(instance);
					request.setAttribute("risultato","Configurazione State-machine aggiornata");
				} catch (StateMachineJBException e1) {
					e1.printStackTrace();
					request.setAttribute("risultato",e1.getMessage().substring(0,100));
				}
        	}else if(nome.equalsIgnoreCase("acl")){
        		SecurityHelper securityHelper = new SecurityHelper();
    			securityHelper.loadAcl(instance);
    			request.getSession().getServletContext().setAttribute("it.cnr.helpdesk.securityhelper", securityHelper);
        		request.setAttribute("risultato","Registro di ACL aggiornato");
        	}else if(nome.equalsIgnoreCase("xorcheck")){
        		request.setAttribute("risultato",""+Converter.xorCheck(Integer.parseInt(valore)));
        	}else if(nome.equalsIgnoreCase("enable_user_ldap")){
        		User user = new User();
        		try {
	        		if(valore.equalsIgnoreCase("allow")){
						user.allowAllUserLdap(instance);
		        		request.setAttribute("risultato","Utenti abilitati");
	        		} else if(valore.equalsIgnoreCase("disallow")){
						user.disallowAllUserLdap(instance);
		        		request.setAttribute("risultato","Utenti disabilitati");
	        		} else
	        			request.setAttribute("risultato","Parametro errato");
				} catch (Exception e1) {
					e1.printStackTrace();
					request.setAttribute("risultato","Errore applicativo");
				}
        	}else if(nome.equalsIgnoreCase("del_prop")){
        		try {
					Settings.removeProperty(valore, se.getAttribute("it.cnr.helpdesk.instance").toString());
					request.setAttribute("risultato","Proprieta' rimossa");
				} catch (SettingsJBException e) {
					e.printStackTrace();
					request.setAttribute("risultato","Errore applicativo");
				}
        	}else if(nome.equalsIgnoreCase("add_prop")){
        		try {
        			Settings.setProperty(StringUtils.substringBefore(valore, "="), StringUtils.substringAfter(valore, "="), se.getAttribute("it.cnr.helpdesk.instance").toString());
        			request.setAttribute("risultato","Proprieta' inserita");
        		} catch (SettingsJBException e) {
        			e.printStackTrace();
        			request.setAttribute("risultato","Errore applicativo");
        		}
        	}
        }
        try {
        	request.setAttribute("map", Settings.getAllProperties(se.getAttribute("it.cnr.helpdesk.instance").toString()));
			request.setAttribute("scheduler",MailScheduler.isStarted(instance)?"started":"stopped");
		} catch (Throwable e) {
			e.printStackTrace();
		}
        return mapping.findForward("Setup");
    }
}

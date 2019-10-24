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
 * Created on 16-apr-2005
 *


 */
package it.cnr.helpdesk.UserManagement.actions;

import it.cnr.helpdesk.MessageManagement.javabeans.Message;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.util.XMLTemplateToJSP;

import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Roberto Puccinelli
 *


 */
public class HomeAction extends Action {
	private static Log log = LogFactory.getLog(LoginAction.class);
	
	public ActionForward execute (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		log.warn("In execute method of HomeAction");
		HttpSession session=request.getSession(true);
		User utente = (User) session.getAttribute("it.cnr.helpdesk.currentuser");
		String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");	//session.getServletContext().getInitParameter("Skin");
		String token = (String)session.getAttribute("it.cnr.helpdesk.token");
		ServletContext ctx = session.getServletContext();
		String [] template = {"","home-user.xml","home-expert.xml","home-administrator.xml","home-validator.xml"};
		if (utente!=null) {
			if(instance==null){
				//TODO stato inconsistente
			}
	        SAXParserFactory saxparserfactory = SAXParserFactory.newInstance();
	        XMLTemplateToJSP xtj = new XMLTemplateToJSP();
	        xtj.setFields("login",utente.getLogin());
	        xtj.setFields("nome",utente.getFirstName());
	        xtj.setFields("cognome",utente.getFamilyName());
	        xtj.setFields("e-mail",utente.getEmail());
	        if(utente.getProfile()==1){
	            Message m=new Message();
	            xtj.setFields("messaggi",m.getMessages(instance));
	        }
			FileInputStream is = new FileInputStream(ctx.getRealPath("/skins/" + instance +"/xml/"+template[utente.getProfile()]));
	        try {
				SAXParser saxparser = saxparserfactory.newSAXParser();
				saxparser.getParser();
				saxparser.parse(new InputSource(is), xtj);
				request.setAttribute("menu",xtj.getBuffer());
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
			return mapping.findForward("GeneralHome");
		} else if(instance==null){
			return mapping.findForward("SelectInstance");
		}else if(token!=null){
			return mapping.findForward("SelectProfile");
		}else
			return mapping.findForward("LoginForm");
	}
}


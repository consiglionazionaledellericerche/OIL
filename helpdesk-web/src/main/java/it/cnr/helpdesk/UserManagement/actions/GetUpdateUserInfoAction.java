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

import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
public class GetUpdateUserInfoAction extends Action {
	
	private static Log log = LogFactory.getLog(GetUpdateUserInfoAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, UnsupportedEncodingException {
		log.warn("In execute method of GetUpdateUserInfoAction");

		HttpSession session = request.getSession();
		String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");
		User utente = (User) session.getAttribute("it.cnr.helpdesk.currentuser");
		UserValueObject uvo = utente.getDetail(instance);
		String attr = "?login=" + URLEncoder.encode(uvo.getLogin(), "UTF-8");
		attr = attr + "&nome=" + URLEncoder.encode(uvo.getFirstName(), "UTF-8");
		attr = attr + "&cognome=" + URLEncoder.encode(uvo.getFamilyName(), "UTF-8");
		attr = attr + "&e_mail=" + URLEncoder.encode(uvo.getEmail(), "UTF-8");
		attr = attr + "&telefono=" + URLEncoder.encode(uvo.getTelefono(), "UTF-8");
		attr = attr + "&struttura=" + URLEncoder.encode(uvo.getStruttura(), "UTF-8");
		attr = attr + "&strutturaDescrizione=" + URLEncoder.encode(uvo.getStrutturaDescrizione(), "UTF-8");
		attr = attr + "&mail_stop=" + URLEncoder.encode(uvo.getMailStop(), "UTF-8");

		ActionForward forward = mapping.findForward("UpdateUserInfo");
		StringBuffer bf = new StringBuffer(forward.getPath());
		bf.append(attr);// add parameter here
		return new ActionForward(bf.toString(), false);
	}

}

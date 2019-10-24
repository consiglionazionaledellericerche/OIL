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
 * Created on 6-apr-2006
 *


 */
package it.cnr.helpdesk.MessageManagement.actions;

import it.cnr.helpdesk.MessageManagement.exceptions.MessageJBException;
import it.cnr.helpdesk.MessageManagement.javabeans.Message;

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
public class SwitchMessageAction extends Action {

	private static Log log = LogFactory.getLog(SwitchMessageAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws MessageJBException {
		log.warn("In execute method of SwitchMessageAction");
		HttpSession se = request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
		Message mess = new Message();
		String azione = request.getParameter("azione");
		String conf = request.getParameter("confermato");
		int id = Integer.parseInt(request.getParameter("id"));
		if (azione != null && conf != null) {
			if (conf.equals("yes")) {
				if (azione.equals("dis")) {
					mess.disable(id, instance);
				} else if (azione.equals("en")) {
					mess.enable(id, instance);
				} else if (azione.equals("del")) {
					mess.executeDelete(id, instance);
				}
				request.setAttribute("param", "close"); // chiudi
				return mapping.findForward("SwitchMessage");
			} else
				return mapping.findForward("SwitchMessage");
		}
		return mapping.findForward("Home");
	}

}

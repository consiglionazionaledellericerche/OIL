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
 * Created on 5-nov-2008
 */
package it.cnr.helpdesk.UOManagement.action;

import it.cnr.helpdesk.UOManagement.exceptions.UOJBException;
import it.cnr.helpdesk.UOManagement.javabeans.*;
import it.cnr.helpdesk.UOManagement.valueobjects.UOValueObject;
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
 * @author Marco Trischitta
 */
public class SwitchStruttureAction extends Action {

	private static Log log = LogFactory.getLog(SwitchStruttureAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UOJBException {
		log.warn("In execute method of SwitchStruttureAction");
		HttpSession session = request.getSession();
		String instance = (String) session.getAttribute("it.cnr.helpdesk.instance");
		String azione = request.getParameter("azione");
		String cod = request.getParameter("cod");
		UO uo = new UO();

		if (azione != null) {
			if (azione.equals("dis")) {
				uo.disabled(cod, instance);
			} else if (azione.equals("en")) {
				uo.enabled(cod, instance);
			} else if (azione.equals("del")) {
				uo.executeDelete(new UOValueObject(cod, "", "", "", ""), instance);
			}
			request.setAttribute("param", "close"); // chiudi
			return mapping.findForward("SwitchStrutture");

		}
		return mapping.findForward("ManageStrutture");
	}
}
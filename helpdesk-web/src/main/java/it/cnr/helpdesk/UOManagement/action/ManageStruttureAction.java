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
 * Created on 31-ott-2008
 */
package it.cnr.helpdesk.UOManagement.action;

import java.util.Collection;
import it.cnr.helpdesk.UOManagement.exceptions.UOJBException;
import it.cnr.helpdesk.exceptions.SettingsJBException;
import it.cnr.helpdesk.UOManagement.javabeans.UO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Marco Trischitta
 */
public class ManageStruttureAction extends Action{

	private static Log log = LogFactory.getLog(ManageStruttureAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UOJBException {
		log.warn("In execute method of ManageStruttureAction");

		HttpSession session = request.getSession();
		String instance = (String) session.getAttribute("it.cnr.helpdesk.instance");
		UO uo = new UO();
		Collection coll = null;

		coll = uo.getAllStructures(instance);
		request.setAttribute("coll", coll);
		return mapping.findForward("ManageStrutture");
	}
}
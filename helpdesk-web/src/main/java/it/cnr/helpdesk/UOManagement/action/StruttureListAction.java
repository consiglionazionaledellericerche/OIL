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
 * Created on 21-ott-2005
 *


 */
package it.cnr.helpdesk.UOManagement.action;

import it.cnr.helpdesk.UOManagement.exceptions.UOJBException;
import it.cnr.helpdesk.UOManagement.javabeans.UO;
import java.util.*;
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
public class StruttureListAction extends Action {
	private static Log log = LogFactory.getLog(StruttureListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UOJBException {
		log.warn("In execute method of StruttureListAction");
		HttpSession session = request.getSession();
		String instance = (String) session.getAttribute("it.cnr.helpdesk.instance");
		if (request.getMethod().equals("POST")) {
			String ppp = request.getParameter("ppp");
			if (ppp == null)
				ppp = "";
			String ccc = ppp.substring(0, Math.max(0, ppp.indexOf(" -")));
			request.setAttribute("ccc", ccc);
			request.setAttribute("ppp", ppp);
		} else {
			UO u = new UO();
			Collection c = u.getStructures(instance);
			String cs = "";
			Iterator i = c.iterator();
			while (i.hasNext()) {
				if (cs.length() > 0)
					cs = cs.concat(",");
				cs = cs.concat("\"" + i.next().toString().replaceAll("\"", "\\\\\"") + "\"");
				request.setAttribute("cs", cs);
			}
		}
		return mapping.findForward("StruttureList");
	}
}

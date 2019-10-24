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

import it.cnr.helpdesk.UOManagement.exceptions.*;
import it.cnr.helpdesk.UOManagement.javabeans.*;
import it.cnr.helpdesk.UOManagement.valueobjects.*;
import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.apache.struts.action.*;

/*
 * @author Marco Trischitta
 */
public class UpdateStruttureAction extends Action {
	
	private static Log log = LogFactory.getLog(UpdateStruttureAction.class);

	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UOJBException {
        log.warn("In execute method of UpdateStruttureAction");
        HttpSession session = request.getSession();
		String instance = (String) session.getAttribute("it.cnr.helpdesk.instance");
        String cod = request.getParameter("cod");
		UO uo  = new UO();
        if ((request.getMethod()).equals("GET")) {
			UOValueObject uvo = uo.getOneStructure(cod, instance);
			request.setAttribute("descrizione",uvo.getDescrizione());
			request.setAttribute("acronimo", uvo.getAcronimo());
			request.setAttribute("evidenza", uvo.getEvidenza());
			request.setAttribute("enabled", uvo.getEnabled());
        } 
        else {
        	String descr = request.getParameter("descrizione");
        	String acron = request.getParameter("acronimo");
        	String ev = request.getParameter("evidenza");
        	String en = request.getParameter("enabled");
        	uo.executeUpdate(new UOValueObject(cod, descr, acron, ev, en), instance);
		    request.setAttribute("param", "close"); //chiudi
        }
        return mapping.findForward("UpdateStrutture");
	}			
}
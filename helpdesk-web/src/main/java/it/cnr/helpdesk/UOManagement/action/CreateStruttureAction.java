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
public class CreateStruttureAction extends Action {
	private static Log log = LogFactory.getLog(CreateStruttureAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UOJBException {
        log.warn("In execute method of CreateStruttureAction");
        HttpSession se=request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        if ((request.getMethod()).equals("POST")){
            UO uo = new UO();
            UOValueObject uvo = new UOValueObject();
        	String descr = request.getParameter("descr");
        	String acron = request.getParameter("acron");
        	String evid = request.getParameter("evid");
        	String en = request.getParameter("en");
        	uvo.setDescrizione(descr);
        	uvo.setAcronimo(acron);
        	uvo.setEvidenza(evid);
        	uvo.setEnabled(en);
        	String cod = uo.executeInsert(uvo, instance);
        	request.setAttribute("struttura", descr);
        	request.setAttribute("acronimo", acron);
            request.setAttribute("param","close");	//chiudi
            return mapping.findForward("CreateStrutture");
        }
        return mapping.findForward("CreateStrutture");
    }
}
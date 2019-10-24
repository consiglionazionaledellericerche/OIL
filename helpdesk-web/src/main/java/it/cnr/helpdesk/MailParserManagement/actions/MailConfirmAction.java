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

package it.cnr.helpdesk.MailParserManagement.actions;


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

public class MailConfirmAction extends Action {
    private static Log log = LogFactory.getLog(MailConfirmAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.warn("In execute method of MailConfirmAction");
        HttpSession se=request.getSession(true);
        if ((request.getMethod()).equals("POST"))        throw new Exception();
		String mess = request.getParameter("lev");
		if(mess!=null && mess.length()>0){
			request.setAttribute("confirmMsg","mail.confirm.message."+mess);
			System.out.println(request.getAttribute("confirmMsg"));
            return mapping.findForward("MailConfirm");	
        }
		return mapping.findForward("welcome");
    }
}

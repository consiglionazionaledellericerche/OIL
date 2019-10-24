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
package it.cnr.helpdesk.MessageManagement.actions;

import it.cnr.helpdesk.MessageManagement.javabeans.Message;
import it.cnr.helpdesk.MessageManagement.valueobjects.MessageValueObject;
import it.cnr.helpdesk.MessageManagement.exceptions.MessageJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;

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
public class CreateMessageAction extends Action {
    private static Log log = LogFactory.getLog(CreateMessageAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws MessageJBException {
        log.warn("In execute method of CreateMessageAction");
		HttpSession se = request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        if ((request.getMethod()).equals("POST")){
            Message msg = new Message();
            MessageValueObject mvo = new MessageValueObject();
        	User u=(User) se.getAttribute("it.cnr.helpdesk.currentuser");
            String mess=request.getParameter("Mess");
            mvo.setTesto(mess);
            mvo.setOriginatore(u.getLogin());
            msg.executeInsert(mvo, instance);
            request.setAttribute("mess",request.getParameter("Mess"));
            request.setAttribute("param","close");	//chiudi
            return mapping.findForward("CreateMessage");
        }
        return mapping.findForward("CreateMessage");
    }
}

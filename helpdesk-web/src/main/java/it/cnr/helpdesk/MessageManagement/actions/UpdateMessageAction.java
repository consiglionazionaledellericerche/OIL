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
 * Created on 7-apr-2006
 *


 */
package it.cnr.helpdesk.MessageManagement.actions;

import it.cnr.helpdesk.MessageManagement.exceptions.MessageJBException;
import it.cnr.helpdesk.MessageManagement.javabeans.Message;
import it.cnr.helpdesk.MessageManagement.valueobjects.MessageValueObject;

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
public class UpdateMessageAction extends Action {
    private static Log log = LogFactory.getLog(UpdateMessageAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws MessageJBException, NumberFormatException {
        log.warn("In execute method of UpdateMessageAction");
		HttpSession se = request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        String id_msg = request.getParameter("id");
        Message msg  = new Message();
        int id;
        id = Integer.parseInt(id_msg);
        if ((request.getMethod()).equals("GET")) {
            request.setAttribute("testo",msg.getMessage(id, instance));
        } else {
        	String mess = request.getParameter("Mess");
        	msg.executeUpdate(new MessageValueObject(id, mess, "", "", ""), instance);
            request.setAttribute("param", "close"); //chiudi
        }
        return mapping.findForward("UpdateMessage");
    }
}

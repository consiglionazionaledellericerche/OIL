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
 * Created on 25-giu-2005
 *


 */
package it.cnr.helpdesk.AttachmentManagement.actions;

import it.cnr.helpdesk.AttachmentManagement.exceptions.AttachmentJBException;
import it.cnr.helpdesk.AttachmentManagement.javabeans.Attachment;
import it.cnr.helpdesk.AttachmentManagement.valueobjects.AttachmentValueObject;
import it.cnr.helpdesk.util.MimeTypeImg;

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
public class DeleteAttachmentAction extends Action {
    private static Log log = LogFactory.getLog(DeleteAttachmentAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AttachmentJBException {
        log.warn("In execute method of DeleteAttachmentAction");
        String conf = request.getParameter("conf");
        HttpSession se = request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        Attachment attachment = new Attachment();
        int id = Integer.parseInt(request.getParameter("id"));
        if (conf != null && conf.equals("yes")) {
        	attachment.setId(id);
        	attachment.delete(instance);
        }else{
        	attachment.setId(id);
        	AttachmentValueObject avo = attachment.fetchAttachment(instance);
        	String[] afile = new String[4];
            afile[0] = avo.getNomeFile();
            afile[1] = "" + avo.getId();
            afile[2] = MimeTypeImg.decodeType(avo.getNomeFile());
            afile[3] = avo.getDescrizione();
            request.setAttribute("afile", afile);
        }
        return mapping.findForward("DeleteAttachment");
    }
}
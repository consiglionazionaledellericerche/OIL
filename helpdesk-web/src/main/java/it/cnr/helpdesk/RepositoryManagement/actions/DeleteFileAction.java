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
package it.cnr.helpdesk.RepositoryManagement.actions;

import it.cnr.helpdesk.RepositoryManagement.exceptions.RepositoryJBException;
import it.cnr.helpdesk.RepositoryManagement.javabeans.Repository;
import it.cnr.helpdesk.RepositoryManagement.valueobjects.RepositoryValueObject;

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
public class DeleteFileAction extends Action {
    private static Log log = LogFactory.getLog(DeleteFileAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws RepositoryJBException {
        log.warn("In execute method of DeleteFileAction");
        String conf = request.getParameter("conf");
        if (conf != null && conf.equals("yes")) {
    		HttpSession se = request.getSession(true);
    		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        	Repository rep = new Repository();
        	RepositoryValueObject rvo = new RepositoryValueObject();
         	rvo.setMese(Integer.parseInt(request.getParameter("mese")));
         	rvo.setCategoria(Integer.parseInt(request.getParameter("categoria")));
        	rep.deleteRow(rvo, instance);
        }
        return mapping.findForward("DeleteFile");
    }
}
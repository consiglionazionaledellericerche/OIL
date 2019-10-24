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
 * Created on 20-lug-2005
 *


 */
package it.cnr.helpdesk.FaqManagement.actions;

import it.cnr.helpdesk.FaqManagement.ActionForms.*;
import it.cnr.helpdesk.FaqManagement.exceptions.FaqJBException;
import it.cnr.helpdesk.FaqManagement.javabeans.Faq;
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
public class UpdateFaqDetailAction extends Action {
    private static Log log = LogFactory.getLog(UpdateFaqDetailAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws FaqJBException {
        log.warn("In execute method of UpdateFaqDetailAction");
        
        if ((request.getMethod()).equals("POST")) {
            Faq faq = new Faq();
            Integer idFaq=null;
            HttpSession se=request.getSession(true);
    		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
            User utente = (User)se.getAttribute("it.cnr.helpdesk.currentuser");
            UpdateFaqDetailForm ufdf = (UpdateFaqDetailForm)form;
            idFaq=(Integer) se.getAttribute("it.cnr.helpdesk.currentIdFaq");
            faq.setIdFaq(idFaq.intValue());    
            faq.setTitolo(ufdf.getTitolo());
            faq.setDescrizione(ufdf.getDescrizione());
            String idCategoriaString=ufdf.getCategoria();
            int idCategoria=Integer.parseInt(idCategoriaString);
            faq.setIdCategoria(idCategoria);
            faq.setOriginatore(utente.getLogin());
            faq.updateFaq(instance);
            request.setAttribute("param","close");
        }
        return mapping.findForward("UpdateFaqDetail");
    }
}

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

import it.cnr.helpdesk.FaqManagement.dto.*;
import it.cnr.helpdesk.FaqManagement.exceptions.FaqJBException;
import it.cnr.helpdesk.FaqManagement.javabeans.Faq;

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
public class FaqDetailAction extends Action {
    private static Log log = LogFactory.getLog(FaqDetailAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws FaqJBException {
        log.warn("In execute method of FaqDetailAction");
        
        Faq faq = new Faq();
        Integer idFaq=null;
        HttpSession se=request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        if ((request.getMethod()).equals("GET")) {
            String idFaqString=request.getParameter("idFaq");
            if (idFaqString!=null)  {
                idFaq=new Integer(idFaqString);
                se.setAttribute("it.cnr.helpdesk.currentIdFaq",idFaq);
            } else
                idFaq=(Integer) se.getAttribute("it.cnr.helpdesk.currentIdFaq");
            faq.setIdFaq(idFaq.intValue());    
            FaqDTO faqDTO=faq.getFaqDetail(instance);
            request.setAttribute("idf",faqDTO);
            return mapping.findForward("FaqDetail");
        }
        return mapping.findForward("welcome");
    }
}

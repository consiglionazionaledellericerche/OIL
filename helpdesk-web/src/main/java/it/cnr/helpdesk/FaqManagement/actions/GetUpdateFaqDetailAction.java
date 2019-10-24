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

import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryJBException;
import it.cnr.helpdesk.CategoryManagement.javabeans.Category;
import it.cnr.helpdesk.CategoryManagement.valueobjects.CategoryValueObject;
import it.cnr.helpdesk.FaqManagement.dto.FaqDTO;
import it.cnr.helpdesk.FaqManagement.exceptions.FaqJBException;
import it.cnr.helpdesk.FaqManagement.javabeans.Faq;
import it.cnr.helpdesk.UserManagement.javabeans.User;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
public class GetUpdateFaqDetailAction extends Action {
    private static Log log = LogFactory.getLog(GetUpdateFaqDetailAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws FaqJBException, CategoryJBException, UnsupportedEncodingException {
        log.warn("In execute method of GetUpdateFaqDetailAction");
        
        Category categoria = new Category();
        Faq faq = new Faq();
        Integer idFaq=null;
        HttpSession se=request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        User utente = (User)se.getAttribute("it.cnr.helpdesk.currentuser");
        if ((request.getMethod()).equals("GET")) {
            String idFaqString=request.getParameter("idFaq");
            if (idFaqString!=null)  {
                idFaq=new Integer(idFaqString);
                se.setAttribute("it.cnr.helpdesk.currentIdFaq",idFaq);
            } else
                idFaq=(Integer) se.getAttribute("it.cnr.helpdesk.currentIdFaq");
            faq.setIdFaq(idFaq.intValue());    
            FaqDTO fvo=faq.getFaqDetail(instance);
            
            Collection c=categoria.getAssignedCategories(utente.getLogin(), instance);
            ArrayList cs = new ArrayList(0);
            Iterator i=c.iterator();
            int act = 0;
            //boolean catFAQinListCatExpert=false;
            while(i.hasNext()) {
                CategoryValueObject cvo=(CategoryValueObject) i.next();    
                if (cvo.getId()==fvo.getIdCategoria()) {
                    //catFAQinListCatExpert=true;    
                    cs.add(cvo);	//<option value="<%=cvo.getId()%>" selected><%=cvo.getNome()%></option>   
                    act = cs.size();
                } else if (cvo.getAssigned()) {
                    cs.add(cvo);	//<option value="<%=cvo.getId()%>"><%=cvo.getNome()%></option> 
                }
            }
            /*if (!catFAQinListCatExpert) {
                //<option value="<%=faqDTO.getIdCategoria()%>" selected><%=faqDTO.getDescrizioneCategoria()%></option>    
            }*/
            request.setAttribute("cat",cs);
            request.setAttribute("act",""+act);
    		String attr = "?titolo="+URLEncoder.encode(fvo.getTitolo(),"UTF-8");
    		attr = attr + "&descrizione="+URLEncoder.encode(fvo.getDescrizione(),"UTF-8");
    		attr = attr + "&categoria="+fvo.getIdCategoria();
    		ActionForward forward = mapping.findForward("UpdateFaqDetail");
    	    StringBuffer bf = new StringBuffer(forward.getPath());
    	    bf.append(attr);//add parameter here
    	    log.warn(bf.toString());
    	    return new ActionForward(bf.toString(),false);
        }
        return mapping.findForward("welcome");
    }
}

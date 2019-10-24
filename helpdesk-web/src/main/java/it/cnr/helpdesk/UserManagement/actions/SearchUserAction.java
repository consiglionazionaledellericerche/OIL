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
 * Created on 15-mag-2012
 *


 */
package it.cnr.helpdesk.UserManagement.actions;

import java.util.ArrayList;
import java.util.Iterator;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.util.*;
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
public class SearchUserAction extends Action {
    private static Log log = LogFactory.getLog(SearchUserAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException {
        log.warn("In execute method of SearchUserAction");
        User utente=new User();
        HttpSession session=request.getSession(true);
		String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");
        if ("POST".equals(request.getMethod())) {
        	ArrayList filtered = new ArrayList();
    		PageByPageIterator pbp=new PageByPageIteratorImpl(0,50000,"",10);
    		PageByPageIterator p = utente.allEnabledUser(pbp,1, instance);
    		if (request.getParameter("filter")!=null){
	        	String filter = (""+request.getParameter("filter")).toLowerCase();
	    		for(Iterator i = p.getCollection().iterator(); i.hasNext();){
	    			UserValueObject uvo = (UserValueObject) i.next();
	    			if(uvo.getFamilyName().toLowerCase().indexOf(filter)>-1)
	    				filtered.add(uvo);
	    		}
    		} else
    			filtered.addAll(p.getCollection());
            request.setAttribute("list",filtered);
        }
        return mapping.findForward("SearchUser");
    }
}

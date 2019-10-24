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
 * Created on 18-lug-2005
 *


 */
package it.cnr.helpdesk.UserManagement.actions;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
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
public class InfoUtentiAction extends Action {
    private static Log log = LogFactory.getLog(InfoUtentiAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException {
        log.warn("In execute method of InfoUtentiAction");
        
        User utente=new User();
        HttpSession session=request.getSession(true);
		String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");
        if ("GET".equals(request.getMethod())) { 
            String thisPage = new  String("InfoUtenti.do");
            session.setAttribute("it.cnr.helpdesk.Caller",thisPage);
    		if (request.getParameter("viewAll")!=null)								// se è stato premuto il pulsante mostra/nascondi
    			session.setAttribute("viewAll",request.getParameter("viewAll"));	// mette il nuovo stato nella sessione
    		boolean viewAll=false;
    		if (session.getAttribute("viewAll")!=null)								//setta il flag viewAll locale sulla base di quello di sessione
    			if (session.getAttribute("viewAll").equals("y"))
    				viewAll=true;            
            String pageNumberString=request.getParameter("page");
            int pageNumber=0;
            if (pageNumberString!=null) {
                pageNumber=Integer.parseInt(pageNumberString);    
            } else pageNumber=1;
            PageByPageIterator pbp;
			try {
				pbp=  new PageByPageIteratorImpl(pageNumber, Integer.parseInt(Settings.getProperty("it.oil.itemsInPage",session.getAttribute("it.cnr.helpdesk.instance").toString())), "InfoUtenti.do?",Integer.parseInt(Settings.getProperty("it.oil.pagesInNavigator",session.getAttribute("it.cnr.helpdesk.instance").toString())));
			} catch (Exception e) {
				throw new UserJBException(e.getMessage());
			}
            PageByPageIterator p;
    		if(viewAll)
    			p=utente.allUser(pbp,1,instance);  // richiama sul PageByPageIterator il metodo allUsers
    		else
    			p=utente.allEnabledUser(pbp,1,instance);  // carica sul PageByPageIterator gli utenti abilitati
            request.setAttribute("page",p);
            return mapping.findForward("InfoUtenti");
        }
        return mapping.findForward("welcome");
    }
}

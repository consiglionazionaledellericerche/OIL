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
 * Created on 25-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.actions;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.exceptions.SettingsJBException;
import it.cnr.helpdesk.util.PageByPageIterator;
import it.cnr.helpdesk.util.PageByPageIteratorImpl;

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
public class UserQueueDetailAction extends Action {
    private static Log log = LogFactory.getLog(UserQueueDetailAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, ProblemJBException, SettingsJBException {
        log.warn("In execute method of UserQueueDetailAction");
        Problem problem = new Problem();
        HttpSession se=request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        User u=(User) se.getAttribute("it.cnr.helpdesk.currentuser");
        String originatore=u.getLogin();
        String forward = request.getParameter("print")==null?"UserQueueDetail":"UserQueueDetail-p";
        if ((request.getMethod()).equals("GET")) {
            String closed=request.getParameter("closed");    
            if (closed!=null) {
                se.setAttribute("it.cnr.helpdesk.closed",closed);
            } else {
                closed=(String) se.getAttribute("it.cnr.helpdesk.closed");    
            }
            ProblemValueObject pvo=new ProblemValueObject();
            pvo.setOriginatore(originatore);
            String pageNumberString=request.getParameter("page");
            int pageNumber=0;
            if (pageNumberString!=null) {
                pageNumber=Integer.parseInt(pageNumberString);    
            } else pageNumber=1;
            PageByPageIterator pbp;
			try {
				pbp = new PageByPageIteratorImpl(pageNumber,Integer.parseInt(Settings.getProperty("it.oil.itemsInPage",se.getAttribute("it.cnr.helpdesk.instance").toString())), "UserQueueDetail.do?closed="+closed+"&",Integer.parseInt(Settings.getProperty("it.oil.pagesInNavigator",se.getAttribute("it.cnr.helpdesk.instance").toString())));
			} catch (Exception e) {
				throw new ProblemJBException(e);
			}
            PageByPageIterator p=null;
            if (closed.equals("NO")){
                p=problem.getUnclosedUserProblemQueue(pbp,pvo, instance);
            } else  {
                p=problem.getClosedUserProblemQueue(pbp,pvo, instance);
            }
            request.setAttribute("pbp",p);
            
            //per gestione della visualizzazione dello stato 6 e 7 della segn. nel caso di UTENTE 
            //Settings s = (Settings) se.getAttribute("it .cnr.helpdesk.settings");
            //Vector stateId2Description = s.getId2DescriptionStateMapping();
            //request.setAttribute("statusDes", stateId2Description);
            request.setAttribute("profile", ""+u.getProfile());
            
            
            
            return mapping.findForward(forward);
        }
        return mapping.findForward("welcome");
    }
        
}

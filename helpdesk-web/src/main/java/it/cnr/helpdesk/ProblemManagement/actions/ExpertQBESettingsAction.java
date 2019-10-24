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
package it.cnr.helpdesk.ProblemManagement.actions;

import it.cnr.helpdesk.exceptions.SettingsJBException;
import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryJBException;
import it.cnr.helpdesk.CategoryManagement.javabeans.Category;
import it.cnr.helpdesk.CategoryManagement.valueobjects.*;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;

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
public class ExpertQBESettingsAction extends Action {
    private static Log log = LogFactory.getLog(ExpertQBESettingsAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, SettingsJBException, CategoryJBException {
        log.warn("In execute method of ExpertQBESettingsAction");
        
        Category categoria=new Category();
        HttpSession se=request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        User u=(User) se.getAttribute("it.cnr.helpdesk.currentuser");    
        se.setAttribute("it.cnr.helpdesk.closed","QBE");
        Vector stateId2Description=Settings.getId2DescriptionStateMapping(se.getAttribute("it.cnr.helpdesk.instance").toString());
        Collection c=categoria.getAssignedCategories(u.getLogin(), instance);
        ArrayList c2 = new ArrayList(0);
        Iterator i=c.iterator();
        c2.add("0:Tutte");	//<option value="0">Tutte</option>
        while(i.hasNext()) {
            CategoryValueObject cvo=(CategoryValueObject) i.next();    
            if (cvo.getAssigned()) {
                c2.add(cvo.getId()+":"+cvo.getNome());	//      <option value="<%=cvo.getId()%>"><%=cvo.getNome()%></option>
            }
        }
        request.setAttribute("coll",c2);
        request.setAttribute("status",stateId2Description);
        return mapping.findForward("ExpertQBESettings");
    }
}

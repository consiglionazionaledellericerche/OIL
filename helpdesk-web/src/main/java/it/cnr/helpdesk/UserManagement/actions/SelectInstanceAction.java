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
package it.cnr.helpdesk.UserManagement.actions;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
 * 
 * @author aldo stentella
 *
 */
public class SelectInstanceAction extends Action {
	
	private static Log log = LogFactory.getLog(LoginAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.warn("In execute method of SelectInstanceAction");
	    HttpSession session=request.getSession(true);
	    if("POST".equals(request.getMethod())){
	    	session.setAttribute("it.cnr.helpdesk.instance", request.getParameter("istanza"));
	    	return mapping.findForward("Home");
	    }else{
	    	int size = Integer.parseInt(System.getProperty("it.cnr.oil.instances.size"));
	    	if(size==1){
	    		session.setAttribute("it.cnr.helpdesk.instance", System.getProperty("it.cnr.oil.instances.names").replace(";", ""));
	    		return mapping.findForward("Home");
	    	}
	    	ArrayList<String> instances = new ArrayList<String>(size);
	    	ArrayList<String> instanceLabels = new ArrayList<String>(size);
	    	String ninstances = System.getProperty("it.cnr.oil.instances.names");
	    	int i = 0;
	    	for(StringTokenizer st = new StringTokenizer(ninstances, ";"); st.hasMoreTokens();){
	    		String name = st.nextToken();
	    		instances.add(i, name);
	    		instanceLabels.add(i++, System.getProperty("it.cnr.oil.instances.label."+name));
	    	}
	    	request.setAttribute("instances", instances);
	    	request.setAttribute("instanceLabels", instanceLabels);
	    }
	    return mapping.findForward("SelectInstance");
	}

}

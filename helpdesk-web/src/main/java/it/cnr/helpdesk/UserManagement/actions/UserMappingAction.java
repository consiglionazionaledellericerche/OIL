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
package it.cnr.helpdesk.UserManagement.actions;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

import it.cnr.helpdesk.LdapManagemet.exceptions.LdapJBException;
import it.cnr.helpdesk.LdapManagemet.javabeans.Ldap;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;

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
public class UserMappingAction extends Action {
    private static Log log = LogFactory.getLog(UserMappingAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws UserJBException, LdapJBException {
        log.warn("In execute method of UserMappingAction");
        HttpSession session=request.getSession();
        String instance = session.getAttribute("it.cnr.helpdesk.instance").toString();	//session.getServletContext().getInitParameter("Skin");
    	Ldap ldap = new Ldap();
    	Collection utenti = ldap.getAllowedUser(request.getParameter("login"),instance);
        if ("GET".equals(request.getMethod())) {    		
    		request.setAttribute("utenti",utenti);
        } else {
        	String idList = request.getParameter("idlist");
        	String token;
        	StringTokenizer st = new StringTokenizer(idList, ";");
			while(st.hasMoreTokens()){
				token = st.nextToken();
				if(token!=null && token.length()>0){
					if(utenti.contains(token))								//se l'ID era presente prima delle modifiche, nessuna variazione necessaria.
						utenti.remove(token);
					else													//altrimenti e' un ID nuovo e va autorizzato
						ldap.allowUser(request.getParameter("login"),token,instance);
				}
			}
			for(Iterator i = utenti.iterator(); i.hasNext();){				//gli ID rimasti nella lista sono quelli non piu' presenti dopo le modifiche
				token = i.next().toString();
				ldap.disallowUser(request.getParameter("login"),token,instance);		//viene tolta loro l'autorizzazione
			}
			request.setAttribute("param","close");
        }
        return mapping.findForward("UserMapping");

    }
}
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
 * Created on 9-set-2005
 *


 */
package it.cnr.helpdesk.security.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.security.util.SecurityHelper;

/**
 * @author Aldo Stentella Liberati
 *


 */
public class ServiceAccessFilter implements Filter {
	private FilterConfig filterConfig;
	
    public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		ServletContext servletContext = filterConfig.getServletContext();
		if (servletContext.getAttribute("it.cnr.helpdesk.securityhelper") == null) {
			SecurityHelper securityHelper = new SecurityHelper();
			//TODO l'ACL viene letta dalla schema di default, che deve essere sempre definito nell'xml
			securityHelper.loadAcl("");
			servletContext.setAttribute("it.cnr.helpdesk.securityhelper",
					securityHelper);
		}
	}


    public void destroy() {

    }

    
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        
        
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(true);
		User utente = null;
		String login = "public";
		int profile = 0;

		ServletContext servletContext = filterConfig.getServletContext();
		if (servletContext.getAttribute("it.cnr.helpdesk.securityhelper") == null) {
			//response.sendRedirect(request.getContextPath()+"/NoPageAcl. j s p");
			throw new ServletException();
		} else {
			if (session.getAttribute("it.cnr.helpdesk.currentuser") != null) {
				utente = (User) session.getAttribute("it.cnr.helpdesk.currentuser");
				login = utente.getLogin();
				profile = utente.getProfile();
			}
			/* 
			 * tolto il context root dalla chiave ricercata per migliorare la portabilita' dell'applicazione Web
			 * String requestUri = request.getRequestURI();
			 * 
			 */
			String pageRequest = StringUtils.substringAfter(request.getServletPath(), "/app") + "/*";
			//String path = pageRequest.substring(0, java.lang.Math.max(pageRequest.lastIndexOf("/"), pageRequest.lastIndexOf("\\")) + 1) + "*";
			System.out.println("risorsa richiesta: " + pageRequest);
			//System.out.println("Path: " + path);
			System.out.println("profilo utente: " + login);
			System.out.println("profilo utente: " + profile);
			SecurityHelper securityHelper = (SecurityHelper) servletContext.getAttribute("it.cnr.helpdesk.securityhelper");
			if (securityHelper.isAllowed(profile, pageRequest)) {
				chain.doFilter(request, response);
			} else {
				response.sendRedirect(request.getContextPath()+"/app/AccessDenied.do");
			}
		}
    }


}

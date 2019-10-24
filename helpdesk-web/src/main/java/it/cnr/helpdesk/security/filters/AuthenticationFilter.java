package it.cnr.helpdesk.security.filters;

import it.cnr.helpdesk.UserManagement.javabeans.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {
	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
	public void destroy() {

	}
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(true);
		User utente = (User) session.getAttribute("it.cnr.helpdesk.currentuser");
		String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");	//session.getServletContext().getInitParameter("Skin");
		String token = (String)session.getAttribute("it.cnr.helpdesk.token");
		boolean singleSignOn = (request.getHeader("uid")!=null);
		boolean publicPage = request.getServletPath().startsWith("/pub/");

		if (!publicPage) {
			if (singleSignOn) {
				if (token == null) {
					token = request.getHeader("cnrapp3").replaceAll("\\\\", "");
					if (token == null || token.equalsIgnoreCase("no")) {
						System.out.println("AuthenticationFilter: no token found");
						response.sendRedirect(request.getContextPath() + "/app/NoToken.do");
					}
					session.setAttribute("it.cnr.helpdesk.token", token);
					// TODO ripulire sessione?
					response.sendRedirect(request.getContextPath() + "/app/SelectInstance.do");
				} else {
					chain.doFilter(request, response);
				}
			} else { // nessun servizio di SSO
				System.out.println("AuthenticationFilter: SSO not available");
				chain.doFilter(request, response);
			}
		}else
			chain.doFilter(request, response);
	}
}

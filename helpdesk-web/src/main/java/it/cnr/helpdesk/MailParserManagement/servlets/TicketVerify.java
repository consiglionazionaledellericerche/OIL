package it.cnr.helpdesk.MailParserManagement.servlets;

import it.cnr.helpdesk.MailParserManagement.javabeans.MailParser;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author aldo stentella
 *
 */
public class TicketVerify extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		int errorLevel = 0;
		String instance = "HDesk";									//valore di default in caso di parametro mancante
		if( (request.getParameter("cod")!=null) && (request.getParameter("id")!=null)&& (request.getParameter("instance")!=null) ) {
			instance = request.getParameter("instance");
			MailParser mp = new MailParser();
			errorLevel = mp.TicketVerify(request.getParameter("id"),request.getParameter("cod"), instance); 
		} else	errorLevel=1;
		request.getSession().setAttribute("it.cnr.helpdesk.instance", instance);
		getServletConfig().getServletContext().getRequestDispatcher("/TicketVerify.do?lev="+errorLevel).forward(request,response);

	}
}

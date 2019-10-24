package it.cnr.helpdesk.MailParserManagement.servlets;

import it.cnr.helpdesk.MailParserManagement.javabeans.MailParser;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author Marco Trischitta
 * @author aldo stentella
 *
 */
public class MailConfirm extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		//ServletOutputStream out = response.getOutputStream();
		int errorLevel = 0; 
		String instance = "HDesk";									//valore di default in caso di parametro mancante
		if( (request.getParameter("cod")!=null) && (request.getParameter("id")!=null)&& (request.getParameter("instance")!=null) ) {
			instance = request.getParameter("instance");
			MailParser mp = new MailParser();
			errorLevel = mp.confirmMail(request.getParameter("id"),request.getParameter("cod"), instance); 
		} else	errorLevel=1;
		/*/out.println("<html>");
		out.println("<head><title>Index refresh</title><meta http-equiv='refresh' content='0; url=MailConfirm.do?lev="+errorLevel+"'>");
		out.println("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'></head>");
		out.println("<body></body></html>");*/
		request.getSession().setAttribute("it.cnr.helpdesk.instance", instance);
		getServletConfig().getServletContext().getRequestDispatcher("/MailConfirm.do?lev="+errorLevel).forward(request,response);

	}
}

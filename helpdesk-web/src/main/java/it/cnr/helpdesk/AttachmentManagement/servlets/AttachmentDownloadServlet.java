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
 * Created on 9-mar-2005
 *


 */
package it.cnr.helpdesk.AttachmentManagement.servlets;

import it.cnr.helpdesk.dao.AttachmentDAO;
import it.cnr.helpdesk.dao.DAOFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Aldo Stentella Liberati
 *


 */

public class AttachmentDownloadServlet extends HttpServlet {

	int bufferSize = 2048;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Blob blob=null;
		InputStream is = null;
		HttpSession se = request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
		int i = Integer.parseInt(request.getParameter("id"));
		try {
			AttachmentDAO attachmentdao = DAOFactory.getDAOFactoryByProperties().getAttachmentDAO();
			attachmentdao.createConnection(instance);
			is = attachmentdao.getBlobFile(i);
			attachmentdao.closeConnection();
			response.setContentType("www/unknown");
			// long length = blob.length();
			// response.setContentLength((int)length);
			// is = blob.getBinaryStream();
		} catch (Exception ade) {
			ade.printStackTrace();
		}
		// response.setDateHeader("Last-Modified", postIt_bulk.getDuva().getTime());
		OutputStream os = response.getOutputStream();
		try {
			byte buffer[] = new byte[bufferSize];
			int size;
			while ((size = is.read(buffer)) > 0)
				os.write(buffer, 0, size);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// ignore
			}
			try {
				os.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

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
 * Created on 4-feb-2007
 *
 */
package it.cnr.helpdesk.RepositoryManagement.actions;

import org.apache.commons.fileupload.*;

import java.util.*;
import java.io.*;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.RepositoryManagement.exceptions.RepositoryDBException;
import it.cnr.helpdesk.RepositoryManagement.fileupload.RepositoryDBFileItem;
import it.cnr.helpdesk.RepositoryManagement.fileupload.RepositoryDBFileItemFactory;
import it.cnr.helpdesk.exceptions.SettingsJBException;


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
public class UploadFileAction extends Action {
    private static Log log = LogFactory.getLog(UploadFileAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.warn("In execute method of UploadFileAction");
        //Properties properties=it.cnr.helpdesk.util.ApplicationProperties.getApplicationProperties();
        HttpSession session = request.getSession(true);
		String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");
        if ("POST".equals(request.getMethod())) {
        	//int maxSize = Integer.parseInt(properties.getProperty("it.oil.maxsize"));
        	String mess = "";
            try {
            	int maxSize = Integer.parseInt(Settings.getProperty("it.oil.maxsize", session.getAttribute("it.cnr.helpdesk.instance").toString()));
	            if (FileUpload.isMultipartContent(request)) {
	                DiskFileUpload upload = new DiskFileUpload();
	                upload.setFileItemFactory(new RepositoryDBFileItemFactory());
	                ArrayList items = (ArrayList)upload.parseRequest(request);
	                if(!items.isEmpty()) {
	                    RepositoryDBFileItem item = (RepositoryDBFileItem) items.get(0);
	                    log.warn(item.getFieldName());
	                    if (!item.isFormField()){
	                        File itemp = new File(item.getName());
	                        if(!(item.getSize()>maxSize)){
	                        	item.write(Integer.parseInt(session.getAttribute("mese").toString()), Integer.parseInt(session.getAttribute("categoria").toString()), instance);
		                        mess = "File aggiornato correttamente";
		                    } else
		                    	mess="<strong><font color='red'>Impossibile allegare <i>"+itemp.getName()+"</i></font><br>" +
								     "Dimensione del file superiore a "+maxSize+" byte. Ritentare comprimendolo con opportuno software.</strong>";
	                    } else
	                    	mess = "Errore nel caricamento della pagina: campo del form errato";
	                } else
	                	mess = "Errore nel caricamento della pagina: nessun campo nel form";
	            }  else
	            	mess = "Errore nel caricamento della pagina: non è una MultipartContent page";
	            //throw new Exception(mess);
	            log.error(mess);
            } catch (RepositoryDBException e) {
            	mess=e.getMessage();
				e.printStackTrace();
			} catch (FileUploadException e) {
				mess=e.getMessage();
				e.printStackTrace();
			} catch (NumberFormatException e) {
				mess=e.getMessage();
				e.printStackTrace();
			} catch (SettingsJBException e) {
				mess=e.getMessage();
				e.printStackTrace();
			}
            session.removeAttribute("mese");
            session.removeAttribute("categoria");
            request.setAttribute("mess",mess);
            request.setAttribute("param","close");	//chiudi
        } else {
            session.setAttribute("mese", request.getParameter("mese"));
            session.setAttribute("categoria", request.getParameter("categoria"));
            request.setAttribute("param","form");	//visualizza il form
        }
        return mapping.findForward("UploadFile");
    }
    
}
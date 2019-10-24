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
 * Created on 27-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.actions;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.AttachmentManagement.fileupload.AttachmentDBFileItem;
import it.cnr.helpdesk.AttachmentManagement.fileupload.AttachmentDBFileItemFactory;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.UserManagement.javabeans.User;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileUpload;
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
public class FileSelectAction extends Action {
    private static Log log = LogFactory.getLog(FileSelectAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.warn("In execute method of FileSelectAction");
        //Properties properties=it.cnr.helpdesk.util.ApplicationProperties.getApplicationProperties();
        HttpSession session = request.getSession(true);
        String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");
        User u=(User) session.getAttribute("it.cnr.helpdesk.currentuser");
        String idSegnalazioneString = request.getParameter("idSegnalazione");
        if (idSegnalazioneString != null) {
            session.setAttribute("it.cnr.helpdesk.currentIdSegnalazione", idSegnalazioneString);
        } else {
            idSegnalazioneString = (String)session.getAttribute("it.cnr.helpdesk.currentIdSegnalazione");
        }
        int idSegnalazione = Integer.parseInt(idSegnalazioneString);
        if ("POST".equals(request.getMethod())) {
            ArrayList result = new ArrayList(0);
            if (FileUpload.isMultipartContent(request)) {
                //int maxSize = Integer.parseInt(properties.getProperty("it.oil.maxsize"));
                int maxSize = Integer.parseInt(Settings.getProperty("it.oil.maxsize",instance));
                DiskFileUpload upload = new DiskFileUpload();
                upload.setFileItemFactory(new AttachmentDBFileItemFactory());
                ArrayList items = (ArrayList)upload.parseRequest(request);
                //Iterator iter = items.iterator();
                //while (iter.hasNext()) {
                String descri = "";
                for(int i=0; i<6; i++){
                    //AttachmentDBFileItem item = (AttachmentDBFileItem) iter.next();
                    AttachmentDBFileItem item = (AttachmentDBFileItem) items.get((i/2)*2+1-i%2);	//sequenza: 1-0-3-2-5-4
                    log.warn(item.getFieldName());
                    if (item.isFormField()){
                        descri=item.getString();
                        //result.add(item.getString());
                    } else if(item.getName().equals("")) {
                        //result.add("---");
                    } else {
	                    //String nomeFile = item.getName().substring(item.getString().lastIndexOf(File.separator)+1);
	                    File itemp = new File(item.getName());
	                    String nomeFile = itemp.getName();
	                    nomeFile = nomeFile.substring(java.lang.Math.max(nomeFile.lastIndexOf("/"), nomeFile.lastIndexOf("\\")) + 1);
	                    if(item.getSize()>maxSize){
	                        nomeFile="<strong><font color='red'>Impossibile allegare <i>"+nomeFile+"</i></font>";
	                        descri="Dimensione del file eccessiva. Ritentare comprimendolo con opportuno software.</strong>";
	                    } else {
	                    	Problem problem = new Problem();
	                        item.write(problem.getProblemDetail(idSegnalazione, instance), nomeFile, descri.replaceAll("'","''"), u.getLogin(), instance);
                        }
                        result.add(nomeFile + "<br>" + descri);
                        descri = "";
                    }
                }
            } else
                throw new Exception("Errore nel caricamento della pagina");
            request.setAttribute("result",result);
            request.setAttribute("param","close");	//chiudi
        }
        return mapping.findForward("FileSelect");
    }
}

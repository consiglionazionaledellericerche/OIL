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
 * Created on 7-lug-2005
 *
 */
package it.cnr.helpdesk.ProblemManagement.actions;

import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.*;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.exceptions.SettingsJBException;
import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.AttachmentManagement.valueobjects.*;
import it.cnr.helpdesk.AttachmentManagement.javabeans.Attachment;
import it.cnr.helpdesk.EventManagement.exceptions.EventJBException;
import it.cnr.helpdesk.EventManagement.javabeans.Event;
import it.cnr.helpdesk.util.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

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
public class SegnalazioneDetailAction extends Action {
    private static Log log = LogFactory.getLog(ExpertQueueDetailAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ProblemJBException, SettingsJBException, EventJBException {

    	
        log.warn("In execute method of ExpertQueueDetailAction");
        HttpSession se = request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        String closed = (String) se.getAttribute("it.cnr.helpdesk.closed");
        Problem problem = new Problem();
        Event ev = new Event();
        Attachment attachment = new Attachment();

        Long idSegnalazione = null;

        User u = (User) se.getAttribute("it.cnr.helpdesk.currentuser");
        int profile = u.getProfile();       
        
        if (request.getMethod().equals("GET")) {
            String idSegnalazioneString = request.getParameter("idSegnalazione");
            boolean ownerShip = false;
            if (idSegnalazioneString != null) {
                idSegnalazione = new Long(idSegnalazioneString);
                se.setAttribute("it.cnr.helpdesk.currentIdSegnalazione", idSegnalazione);
            } else {
                idSegnalazione = (Long) se.getAttribute("it.cnr.helpdesk.currentIdSegnalazione");
            }

            ProblemValueObject pvo = problem.getProblemDetail(idSegnalazione.longValue(), instance);
            //se e' un utente semplice, vede solo gli eventi non mascherati
            boolean filter = (u.getProfile()==User.UTENTE);
            Collection events = ev.getProblemEvents(idSegnalazione.longValue(),filter, instance);
            if (pvo.getEsperto() != null)
                if (pvo.getEsperto().equals(u.getLogin())){
                	ownerShip=true;
                	request.setAttribute("ownership","true");
                }
            String textarea = pvo.getDescrizione() + "\n\n\n";
            Iterator i = events.iterator();
            boolean alterable = false;
            String changeStateNote = "";
            while (i.hasNext()) {
                EventValueObject evo = (EventValueObject) i.next();
                //l'ultimo evento cambio di stato avvenuto determina se ricopiare il contenuto della nota nella finestra di popup
                if(evo.getEventType()==Event.CAMBIO_STATO){
                	alterable = evo.isAlterable();
                	changeStateNote = ""+evo.getNote();
                }
                	
                if (evo.getNote() != null && (evo.getNote()).length() > 0) { 
                    //aggiungo solo le note aggiunte e non quelle per il cambio stato
                    textarea = textarea.concat(evo.getOriginatoreEventoDescrizione()+" ("+evo.getTime()+") "+":\n");
                    textarea = textarea.concat(evo.getNote()+"\n\n\n");
                }
            }
            Vector v = Settings.getId2DescriptionPriorityMapping(se.getAttribute("it.cnr.helpdesk.instance").toString());
            String hex = "0000"+Integer.toHexString(Math.min(255,511*(pvo.getPriorita()-1)/(v.size()-1))*256 + Math.min(255,511-(511*(pvo.getPriorita()-1)/(v.size()-1))));
            request.setAttribute("colore","#"+hex.substring(hex.length()-4)+"00");	//in base al livello di priorità restituisce il colore intermedio tra il verde(#00ff00) e il rosso(#ff0000)
            
            request.setAttribute("events", events);
            request.setAttribute("pvo", pvo);
            request.setAttribute("textarea",textarea);
            if(alterable)
				try {
					request.setAttribute("changeStateNote","&note=" + URLEncoder.encode(changeStateNote,"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
            
            if ((profile==2 && ownerShip ) || pvo.getStato()==1) {
       		 if(pvo.getStato()!=6 && pvo.getStato()!=7)  {
            	request.setAttribute("updCat","y");
					/* 
					 * se il problema è "open" la categoria la possono cambiare tutti
					 * se il problema è in un altro stato diverso da 6 e 7 la può cambiare solo l'esperto 
					 */
       		 }
            }
            
            if(!problem.allowedStates(pvo.getStato(),profile,instance).isEmpty())		//problem.allowedStatus(pvo.getStato(),profile)[0])
            	request.setAttribute("updStatus","y");
            
            int l = idSegnalazione.intValue();
            Collection files = attachment.fetchAttachments(l, instance);
            ArrayList fileList = new ArrayList(0);
            if (files != null) {
                Iterator j = files.iterator();
                while (j.hasNext()) {
                    AttachmentValueObject avo = (AttachmentValueObject) j.next();
                    String[] afile = new String[4];
                    afile[0] = avo.getNomeFile();
                    afile[1] = "" + avo.getId();
                    afile[2] = MimeTypeImg.decodeType(avo.getNomeFile());
                    afile[3] = avo.getDescrizione();
                    fileList.add(afile);
                }
                request.setAttribute("files", fileList);
            }

            return mapping.findForward("SegnalazioneDetail");
        }

        return mapping.findForward("welcome");
    }
}
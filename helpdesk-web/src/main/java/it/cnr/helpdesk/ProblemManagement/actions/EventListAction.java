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
 * Created on 14-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.actions;

import it.cnr.helpdesk.EventManagement.exceptions.EventJBException;
import it.cnr.helpdesk.EventManagement.javabeans.Event;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.*;
import it.cnr.helpdesk.UserManagement.javabeans.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

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
public class EventListAction extends Action {
    private static Log log = LogFactory.getLog(EventListAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ProblemJBException, EventJBException {
        log.warn("In execute method of EventListAction");
        
        Problem problem = new Problem();
        Long idSegnalazione = null;
        HttpSession se = request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        User u = (User) se.getAttribute("it.cnr.helpdesk.currentuser");
        Event ev = new Event();
        if ((request.getMethod()).equals("GET")) {
            String idSegnalazioneString = request.getParameter("idSegnalazione");
            if (idSegnalazioneString != null) {
                idSegnalazione = new Long(idSegnalazioneString);
                se.setAttribute("it.cnr.helpdesk.currentIdSegnalazione", idSegnalazione);
            } else {
                idSegnalazione = (Long) se.getAttribute("it.cnr.helpdesk.currentIdSegnalazione");
            }
            request.setAttribute("idSegnalazione",idSegnalazione);
            problem.setIdSegnalazione(idSegnalazione.longValue());
            boolean filter = (u.getProfile()== User.UTENTE);
            Collection c = ev.getProblemEvents(idSegnalazione.longValue(),filter, instance);
            //Collection c = problem.getProblemEvents();
            Iterator i = c.iterator();
            String descrizioneEvento = "";
            String oldCategoryDescription = null;
            String oldStateDescription = null;
            String oldDescription = null;
            String[] evn = new String[5];
            ArrayList elist = new ArrayList(0);
            while (i.hasNext()) {
                
                EventValueObject evo = (EventValueObject) i.next();
                if (evo.getEventType() == Event.APERTURA_PROBLEMA) {
                    descrizioneEvento = "apertura problema";
                } else if (evo.getEventType() == Event.CAMBIO_STATO) {
                    if (oldStateDescription != null)
                        oldDescription = oldStateDescription;
                    else
                        oldDescription = "";
                    descrizioneEvento = "Cambio di stato: " + oldDescription + "->" + evo.getStateDescription();
                } else if (evo.getEventType() == Event.CAMBIO_CATEGORIA) {
                    if (oldCategoryDescription != null)
                        oldDescription = oldCategoryDescription;
                    else
                        oldDescription = "";
                    descrizioneEvento = "Cambio di categoria: " + oldDescription + "->" + evo.getCategoryDescription();
                } else if (evo.getEventType() == Event.AGGIUNTA_NOTA) {
                    descrizioneEvento = "Aggiunta nota";
                } else if (evo.getEventType() == Event.CAMBIO_ESPERTO) {
                	descrizioneEvento = "Cambio esperto";
                } else if (evo.getEventType() == Event.AGGIUNTA_ALLEGATO) {
                	descrizioneEvento = "Aggiunta allegato";
                } else if (evo.getEventType() == Event.PROBLEMA_DA_ESPERTO) {
                	descrizioneEvento = "Apertura problema da esperto";
                }
                oldCategoryDescription=evo.getCategoryDescription();
                oldStateDescription=evo.getStateDescription();
                
                evn[0] = evo.getTime();
                evn[1] = descrizioneEvento;
                evn[2] = evo.getOriginatoreEvento();
                evn[3] = evo.getOriginatoreEventoDescrizione();
                evn[4] = evo.getNote();
                elist.add(evn.clone());
            }
            request.setAttribute("elist",elist);
        }
        return mapping.findForward("EventList");
    }
}
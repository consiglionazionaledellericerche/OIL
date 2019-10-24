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
 * Created on 6-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.actions;


import it.cnr.helpdesk.util.*;
import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.*;
import it.cnr.helpdesk.UserManagement.javabeans.User;

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
public class ValidatorQueueDetailAction extends Action {
    private static Log log = LogFactory.getLog(ValidatorQueueDetailAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ProblemJBException {
        log.warn("In execute method of ValidatorQueueDetailAction");
        Problem problem = new Problem();
        Integer category = null;
        String expert = null;
        HttpSession se = request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        String forward = request.getParameter("print")==null?"ValidatorQueueDetail":"ValidatorQueueDetail-p";

        if ((request.getMethod()).equals("GET") || (request.getMethod()).equals("POST")) {
            //può essere richiamata da un link della homepage col metodo GET
            //oppure tramite il submit del form "ricerca avanzata",col metodo POST
            String closed = (String) se.getAttribute("it.cnr.helpdesk.closed");
            String categoryString = null;
            if (closed.equals("NO") || closed.equals("YES") || closed.equals("VER") || closed.equals("STO")) {
                categoryString = request.getParameter("category");
                if (categoryString != null) {
                    category = new Integer(categoryString);
                    se.setAttribute("it.cnr.helpdesk.currentCategory", category);
                } else {
                    category = (Integer) se.getAttribute("it.cnr.helpdesk.currentCategory");
                }
            }
            String QBEDescrizione = null;
            String QBETitolo = null;
            String QBEStato = null;
            String QBEIdSegnalazione = null;
            String QBECategoria = null;
            String QBEEspertoNome = null;
            String QBEEspertoCognome = null;
            String QBEEspertoStruttura = null;
            String QBEOriginatoreNome = null;
            String QBEOriginatoreCognome = null;
            String QBEOriginatoreStruttura = null;
            
            if (closed.equals("QBE")) {
                QBEDescrizione = request.getParameter("Descrizione");
                if (QBEDescrizione != null) {
                    se.setAttribute("it.cnr.helpdesk.QBEDescrizione", QBEDescrizione);
                } else {
                    QBEDescrizione = (String) se.getAttribute("it.cnr.helpdesk.QBEDescrizione");
                }
                
                QBETitolo = request.getParameter("Titolo");
                if (QBETitolo != null) {
                    se.setAttribute("it.cnr.helpdesk.QBETitolo", QBETitolo);
                } else {
                    QBETitolo = (String) se.getAttribute("it.cnr.helpdesk.QBETitolo");
                }
                
                QBEStato = request.getParameter("Stato");
                if (QBEStato != null) {
                    se.setAttribute("it.cnr.helpdesk.QBEStato", QBEStato);
                } else {
                    QBEStato = (String) se.getAttribute("it.cnr.helpdesk.QBEStato");
                }
                
                QBEIdSegnalazione = request.getParameter("idSegnalazione");
                if (QBEIdSegnalazione != null) {
                    se.setAttribute("it.cnr.helpdesk.QBEIdSegnalazione", QBEIdSegnalazione);
                } else {
                    QBEIdSegnalazione = (String) se.getAttribute("it.cnr.helpdesk.QBEIdSegnalazione");
                }
                
                QBECategoria = request.getParameter("Categoria");
                if (QBECategoria != null) {
                    se.setAttribute("it.cnr.helpdesk.QBECategoria", QBECategoria);
                } else {
                    QBECategoria = (String) se.getAttribute("it.cnr.helpdesk.QBECategoria");
                }
                
                QBEEspertoNome = request.getParameter("EspertoNome");
                if (QBEEspertoNome != null) {
                    se.setAttribute("it.cnr.helpdesk.QBEEspertoNome", QBEEspertoNome);
                } else {
                    QBEEspertoNome = (String) se.getAttribute("it.cnr.helpdesk.QBEEspertoNome");
                }
                
                QBEEspertoCognome = request.getParameter("EspertoCognome");
                if (QBEEspertoCognome != null) {
                    se.setAttribute("it.cnr.helpdesk.QBEEspertoCognome", QBEEspertoCognome);
                } else {
                    QBEEspertoCognome = (String) se.getAttribute("it.cnr.helpdesk.QBEEspertoCognome");
                }
                
                QBEEspertoStruttura = request.getParameter("EspertoStruttura");
                if (QBEEspertoStruttura != null) {
                    se.setAttribute("it.cnr.helpdesk.QBEEspertoStruttura", QBEEspertoStruttura);
                } else {
                    QBEEspertoStruttura = (String) se.getAttribute("it.cnr.helpdesk.QBEEspertoStruttura");
                }
                
                QBEOriginatoreNome = request.getParameter("OriginatoreNome");
                if (QBEOriginatoreNome != null) {
                    se.setAttribute("it.cnr.helpdesk.QBEOriginatoreNome", QBEOriginatoreNome);
                } else {
                    QBEOriginatoreNome = (String) se.getAttribute("it.cnr.helpdesk.QBEOriginatoreNome");
                }
                
                QBEOriginatoreCognome = request.getParameter("OriginatoreCognome");
                if (QBEOriginatoreCognome != null) {
                    se.setAttribute("it.cnr.helpdesk.QBEOriginatoreCognome", QBEOriginatoreCognome);
                } else {
                    QBEOriginatoreCognome = (String) se.getAttribute("it.cnr.helpdesk.QBEOriginatoreCognome");
                }
                
                QBEOriginatoreStruttura = request.getParameter("OriginatoreStruttura");
                if (QBEOriginatoreStruttura != null) {
                    se.setAttribute("it.cnr.helpdesk.QBEOriginatoreStruttura", QBEOriginatoreStruttura);
                } else {
                    QBEOriginatoreStruttura = (String) se.getAttribute("it.cnr.helpdesk.QBEOriginatoreStruttura");
                }
                
            }
            
            User u = (User) se.getAttribute("it.cnr.helpdesk.currentuser");
            expert = u.getLogin();
            
            String pageNumberString = request.getParameter("page");
            int pageNumber = 0;
            if (pageNumberString != null) {
                pageNumber = Integer.parseInt(pageNumberString);
            } else
                pageNumber = 1;
            
            String queryString = "";
            if (request.getParameter("category") != null && request.getParameter("categoryName") != null) {
                queryString = "category=" + request.getParameter("category") + "&" + "categoryName=" + request.getParameter("categoryName") + "&";
            }
            PageByPageIterator pbp;
			try {
				pbp = new PageByPageIteratorImpl(pageNumber, Integer.parseInt(Settings.getProperty("it.oil.itemsInPage",se.getAttribute("it.cnr.helpdesk.instance").toString())), "ValidatorQueueDetail.do?" + queryString, Integer.parseInt(Settings.getProperty("it.oil.pagesInNavigator",se.getAttribute("it.cnr.helpdesk.instance").toString())));
			} catch (Exception e) {
				throw new ProblemJBException(e);
			}
            PageByPageIterator p = null;
            
            ProblemValueObject pvo = new ProblemValueObject();
            pvo.setProfiloUtente(4);
            
            if (closed.equals("NO") || closed.equals("YES") || closed.equals("VER") || closed.equals("STO")) {
                pvo.setEsperto(expert);
                pvo.setCategoria(category.intValue());
                if (closed.equals("NO")) { // SEGNALAZIONI PENDENTI STATO VALIDATION REQUIRED (6) ed VALIDATING (7) del VALIDATORE curr
                	SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
                	searchingset.add(new Integer(7));
                	pvo.addSearchingItem(searchingset);
                	p = problem.getUnclosedProblemQueue(pbp, pvo, instance);
                }	
                else if (closed.equals("YES"))// SEGNALAZIONI CHIUSE validate dal VALIDATORE curr
                    p = problem.getClosedExpertProblemQueue(pbp, pvo, instance);
                /*
                else if (closed.equals("VER"))
                    p = problem.getVerifiedExpertProblemQueue(pbp, pvo);
                else if (closed.equals("STO"))
                    p = problem.getClosedAndVerifiedProblemQueue(pbp, pvo);
                  */  
            } else if (closed.equals("QBE")) {
                pvo.setActualUser(expert);
                pvo.setPerCategory(false); //non sto ricercando per categoria
                pvo.setDescrizione(QBEDescrizione);
                pvo.setTitolo(QBETitolo);
                pvo.setStato(Integer.parseInt(QBEStato));
                if (QBEIdSegnalazione.length() > 0) {
                    pvo.setIdSegnalazione(Integer.parseInt(QBEIdSegnalazione));
                }
                pvo.setCategoria(Integer.parseInt(QBECategoria));
                pvo.setEspertoFirstName(QBEEspertoNome);
                pvo.setEspertoFamilyName(QBEEspertoCognome);
                pvo.setEspertoStruttura(QBEEspertoStruttura);
                pvo.setOriginatoreFirstName(QBEOriginatoreNome);
                pvo.setOriginatoreFamilyName(QBEOriginatoreCognome);
                pvo.setOriginatoreStruttura(QBEOriginatoreStruttura);
                
                p = problem.getProblemQueueQBE(pbp, pvo, instance);
            }
            request.setAttribute("pbp",p);
            return mapping.findForward(forward);
        }
        return mapping.findForward("welcome");
    }
}
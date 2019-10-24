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
 * Created on 11-mag-2005
 *


 */
package it.cnr.helpdesk.UserManagement.actions;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.exceptions.SettingsJBException;
import it.cnr.helpdesk.util.PageByPageIterator;
import it.cnr.helpdesk.util.PageByPageIteratorImpl;

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
public class ManageExpertAction extends Action {

	private static Log log = LogFactory.getLog(ManageExpertAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, SettingsJBException {
		log.warn("In execute method of ManageExpertAction");

		HttpSession session = request.getSession();
		String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");
		User utente = new User();
		String thisAction = new String("ManageExpert.do");
		session.setAttribute("it.cnr.helpdesk.Caller", thisAction); // mette nella sessione il nome di questa pagina
		if (request.getParameter("viewAll") != null) // se è stato premuto il pulsante mostra/nascondi
			session.setAttribute("viewAll", request.getParameter("viewAll")); // mette il nuovo stato nella sessione
		boolean viewAll = false;
		if (session.getAttribute("viewAll") != null) // setta il flag viewAll locale sulla base di quello di sessione
			if (session.getAttribute("viewAll").equals("y"))
				viewAll = true;

		String pageNumberString = request.getParameter("page"); // recupera dalla sessione il numero di pagina che deve essere visualizzato sotto forma di stringa
		int pageNumber = 0; // inizializza a 0 la variabile intera pageNumber
		if (pageNumberString != null) { // se nella sessione era stato salvato un numero di pagina da visualizzare
			pageNumber = Integer.parseInt(pageNumberString); // imposta il valore della variabile intera pageNumber utilizzando il valore stringa recuperato dalla sessione
		} else
			pageNumber = 1; // altrimenti imposta il numero della pagina ad uno
		PageByPageIterator pbp;
		try {
			pbp = new PageByPageIteratorImpl(pageNumber, Integer.parseInt(Settings.getProperty("it.oil.itemsInPage", session.getAttribute("it.cnr.helpdesk.instance").toString())), "ManageExpert.do?", Integer.parseInt(Settings.getProperty("it.oil.pagesInNavigator", session.getAttribute("it.cnr.helpdesk.instance").toString()))); // istanzia un PageByPageIterator
		} catch (Exception e) {
			throw new UserJBException(e.getMessage());
		}
		PageByPageIterator p;
		if (viewAll)
			p = utente.allUser(pbp, 2,instance); // carica sul PageByPageIterator tutti gli utenti
		else
			p = utente.allEnabledUser(pbp, 2, instance); // carica sul PageByPageIterator gli utenti abilitati
		if (p != null) {
			request.setAttribute("it.cnr.helpdesk.PageByPageIterator", p);
			request.setAttribute("it.cnr.helpdesk.PageNumber", new Integer(pageNumber));
			try {
				return mapping.findForward("ManageExpert");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mapping.findForward("welcome");
	}

}

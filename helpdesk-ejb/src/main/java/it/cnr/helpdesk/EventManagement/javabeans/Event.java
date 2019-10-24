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
 * Created on 27-lug-2006
 *
 */
package it.cnr.helpdesk.EventManagement.javabeans;

import java.util.Collection;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import it.cnr.helpdesk.EventManagement.ejb.EventManagement;
import it.cnr.helpdesk.EventManagement.ejb.EventManagementHome;
import it.cnr.helpdesk.EventManagement.exceptions.EventDAOException;
import it.cnr.helpdesk.EventManagement.exceptions.EventEJBException;
import it.cnr.helpdesk.EventManagement.exceptions.EventJBException;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.EventDAO;
import it.cnr.helpdesk.javabeans.HelpDeskJB;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class Event extends HelpDeskJB {
	
	public final static int APERTURA_PROBLEMA = 0;
	public final static int CAMBIO_STATO = 1;
	public final static int CAMBIO_CATEGORIA = 2;
	public final static int AGGIUNTA_NOTA = 3;
	public final static int CAMBIO_ESPERTO = 4;
	public final static int PROBLEMA_DA_MAIL = 5;
	public final static int AGGIUNTA_ALLEGATO = 6;
	public final static int PROBLEMA_DA_ESPERTO = 7;

	
	
	
	private EventManagementHome lookupHome() throws EventJBException {
		EventManagementHome eventmanagementhome = null;
		try {
			Context context = getInitialContext();
        	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
            Object obj = context.lookup(jndiPrefix+"EventManagement!it.cnr.helpdesk.EventManagement.ejb.EventManagementHome");
			eventmanagementhome = (EventManagementHome)PortableRemoteObject.narrow(obj, it.cnr.helpdesk.EventManagement.ejb.EventManagementHome.class);
		} catch(NamingException namingexception) {
			log(this, "Si \350 verificato un errore durante la ricerca di EventManagement all'interno del registro JNDI. " + namingexception.getMessage());
			namingexception.printStackTrace();
			throw new EventJBException(namingexception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch(EventEJBException eventejbexception) {
			throw new EventJBException(eventejbexception.getMessage(), eventejbexception.getUserMessage());
		} catch(Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new EventJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return eventmanagementhome;
	}
	
	public Collection getProblemEvents(long id, boolean filter, String instance) throws EventJBException {
		Collection collection = null;
		try {
			EventManagementHome eventmanagementhome = lookupHome();
			EventManagement eventmanagement = (EventManagement)PortableRemoteObject.narrow(eventmanagementhome.create(), it.cnr.helpdesk.EventManagement.ejb.EventManagement.class);
			collection = eventmanagement.getProblemEvents(id, filter, instance);
			eventmanagement.remove();
		} catch(EventEJBException eventejbexception) {
			throw new EventJBException(eventejbexception.getMessage(), eventejbexception.getUserMessage());
		} catch(Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new EventJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return collection;
	}
	
	public static String getTemplate(int i, String instance) throws EventJBException {
		DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
		EventDAO edao = daoFactory.getEventDAO();
		String template = "";
		try {
			edao.createConnection(instance);
	    	template = edao.getTemplate(i);
	    	edao.closeConnection();
		} catch (EventDAOException e) {
			throw new EventJBException(e.getMessage(), e.getUserMessage());
		}
		return template;
	}
}

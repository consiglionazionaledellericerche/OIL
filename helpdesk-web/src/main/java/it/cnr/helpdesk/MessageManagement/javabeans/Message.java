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

 
// Source File Name:   Message.java

package it.cnr.helpdesk.MessageManagement.javabeans;

import java.util.Collection;

import javax.rmi.PortableRemoteObject;

import it.cnr.helpdesk.MessageManagement.exceptions.MessageJBException;
import it.cnr.helpdesk.MessageManagement.valueobjects.MessageValueObject;
import it.cnr.helpdesk.MessageManagement.ejb.*;
import it.cnr.helpdesk.dao.MessageDAO;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.MessageManagement.exceptions.*;
import it.cnr.helpdesk.javabeans.HelpDeskJB;
import javax.naming.*;

// Referenced classes of package it.cnr.helpdesk.javabeans:
//            HelpDeskJB

public class Message extends HelpDeskJB {

	public Message() {
	}

	public String getMessage(int id_msg, String instance) throws MessageJBException {
		MessageDAO md = DAOFactory.getDAOFactoryByProperties().getMessageDAO();
		String message = null;
		MessageValueObject msg;
		try {
			md.createConnection(instance);
			msg = md.getMessage(id_msg);
			message = msg.getTesto();
			md.closeConnection();
		} catch (MessageDAOException e) {
			throw new MessageJBException(e.getMessage(), e.getUserMessage());
		}
		return message;
	}

	public Collection getMessages(String instance) throws MessageJBException {
		MessageDAO md = DAOFactory.getDAOFactoryByProperties().getMessageDAO();
		Collection messages;
		try {
			md.createConnection(instance);
			messages = md.getMessages();
			md.closeConnection();
		} catch (MessageDAOException e) {
			throw new MessageJBException(e.getMessage(), e.getUserMessage());
		}
		return messages;
	}

	public Collection getAllMessages(boolean filter, String instance) throws MessageJBException {
		MessageDAO md = DAOFactory.getDAOFactoryByProperties().getMessageDAO();
		Collection messages;
		try {
			md.createConnection(instance);
			messages = md.getAllMessages(filter);
			md.closeConnection();
		} catch (MessageDAOException e) {
			throw new MessageJBException(e.getMessage(), e.getUserMessage());
		}
		return messages;
	}

	public int executeInsert(MessageValueObject mvo, String instance) throws MessageJBException {
		int cod = 0;
		try {
			MessageManagementHome messagemanagementhome = lookupHome();
			MessageManagement messagemanagement = (MessageManagement) PortableRemoteObject.narrow(messagemanagementhome.create(), it.cnr.helpdesk.MessageManagement.ejb.MessageManagement.class);
			cod = messagemanagement.executeInsert(mvo, instance);
		} catch (MessageEJBException e) {
			throw new MessageJBException(/* Inserire messaggio da Resource Bundle */);
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new MessageJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile registrare nuovo utente. Contattare l'assistenza.");
		}
		return cod;
	}

	public void executeDelete(int id_msg, String instance) throws MessageJBException {
		MessageDAO md = DAOFactory.getDAOFactoryByProperties().getMessageDAO();
		try {
			md.createConnection(instance);
			md.executeDelete(id_msg);
			md.closeConnection();
		} catch (MessageDAOException e) {
			throw new MessageJBException(e.getMessage(), e.getUserMessage());
		}
	}

	public void executeUpdate(MessageValueObject mvo, String instance) throws MessageJBException {
		MessageDAO md = DAOFactory.getDAOFactoryByProperties().getMessageDAO();
		try {
			md.createConnection(instance);
			md.executeUpdate(mvo);
			md.closeConnection();
		} catch (MessageDAOException e) {
			throw new MessageJBException(e.getMessage(), e.getUserMessage());
		}
	}

	public void enable(int id_msg, String instance) throws MessageJBException {
		MessageDAO md = DAOFactory.getDAOFactoryByProperties().getMessageDAO();
		try {
			md.createConnection(instance);
			md.enable(id_msg);
			md.closeConnection();
		} catch (MessageDAOException e) {
			throw new MessageJBException(e.getMessage(), e.getUserMessage());
		}
	}

	public void disable(int id_msg, String instance) throws MessageJBException {
		MessageDAO md = DAOFactory.getDAOFactoryByProperties().getMessageDAO();
		try {
			md.createConnection(instance);
			md.disable(id_msg);
			md.closeConnection();
		} catch (MessageDAOException e) {
			throw new MessageJBException(e.getMessage(), e.getUserMessage());
		}
	}

	private MessageManagementHome lookupHome() throws MessageJBException {
		MessageManagementHome messagemanagementhome = null;
		try {
			Context context = getInitialContext();
			if (context == null) {
				log(this, "Initial Context=null");
			} else {
            	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
                Object obj1 = context.lookup(jndiPrefix+"MessageManagement!it.cnr.helpdesk.MessageManagement.ejb.MessageManagementHome");
				messagemanagementhome = (MessageManagementHome) PortableRemoteObject.narrow(obj1, it.cnr.helpdesk.MessageManagement.ejb.MessageManagementHome.class);
			}
		} catch (NamingException namingexception) {
			log(this, "Si \350 verificato un errore durante la ricerca all'interno del registro JNDI. " + namingexception.getMessage());
			namingexception.printStackTrace();
			throw new MessageJBException(namingexception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (MessageEJBException messageejbexception) {
			throw new MessageJBException(messageejbexception.getMessage());
		}
		return messagemanagementhome;
	}
}
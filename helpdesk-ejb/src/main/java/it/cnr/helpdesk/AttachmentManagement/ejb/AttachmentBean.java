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
 * Created on 10-feb-2005
 *
 
 
 */
package it.cnr.helpdesk.AttachmentManagement.ejb;

import it.cnr.helpdesk.AttachmentManagement.exceptions.AttachmentDAOException;
import it.cnr.helpdesk.AttachmentManagement.exceptions.AttachmentEJBException;
import it.cnr.helpdesk.AttachmentManagement.javabeans.Attachment;
import it.cnr.helpdesk.AttachmentManagement.valueobjects.AttachmentValueObject;
import it.cnr.helpdesk.EventManagement.exceptions.EventDAOException;
import it.cnr.helpdesk.EventManagement.javabeans.Event;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.TaskException;
import it.cnr.helpdesk.StateMachineManagement.tasks.Task;
import it.cnr.helpdesk.dao.AttachmentDAO;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.EventDAO;
import it.cnr.helpdesk.util.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> *
 <!-- lomboz.beginDefinition -->
 <?xml version="1.0" encoding="UTF-8"?>
 <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz">
 <lomboz:session>
 <lomboz:sessionEjb>
 <j2ee:display-name>AttachmentEJB</j2ee:display-name>
 <j2ee:ejb-name>AttachmentEJB</j2ee:ejb-name>
 <j2ee:ejb-class>it.cnr.helpdesk.AttachmentManagement.ejb.AttachmentEJBBean</j2ee:ejb-class>
 <j2ee:session-type>Stateless</j2ee:session-type>
 <j2ee:transaction-type>Container</j2ee:transaction-type>
 </lomboz:sessionEjb>
 </lomboz:session>
 </lomboz:EJB>
 <!-- lomboz.endDefinition -->
 *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="AttachmentEJB"	
 *           jndi-name="AttachmentEJB"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="AttachmentEJB" 
 *             jndi-name="AttachmentEJB"
 * 
 *--
 * <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class AttachmentBean implements javax.ejb.SessionBean {
	

	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public ArrayList insert(AttachmentValueObject avo, EventValueObject evo, String instance) throws AttachmentEJBException{ 
		try{
			DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
			AttachmentDAO attachmentdao = daoFactory.getAttachmentDAO();
			EventDAO edao = daoFactory.getEventDAO();
			attachmentdao.createConnection(instance);
			attachmentdao.setId_segnalazione(evo.getIdSegnalazione());
			Attachment attachment = new Attachment();
			Collection files = attachment.fetchAttachments(evo.getIdSegnalazione(), instance);
			Collection names = new ArrayList();
            if (files != null) for(Iterator j = files.iterator(); j.hasNext();) {
                    AttachmentValueObject temp = (AttachmentValueObject) j.next();
                    names.add(temp.getNomeFile());
            }
            String[] anames = new String[names.size()];
            System.arraycopy(names.toArray(),0,anames,0,names.size());
			attachmentdao.setNomeFile(Converter.uniqueAttachmentFilename(avo.getNomeFile(), anames));
			attachmentdao.setAttachment(avo.getAttachment());
			attachmentdao.setDescrizione(avo.getDescrizione());
			attachmentdao.executeInsert();
			attachmentdao.closeConnection();
			/*EventValueObject evo = new EventValueObject();
			evo.setEventType(Event.AGGIUNTA_ALLEGATO);
			evo.setIdSegnalazione(avo.getProblem().getIdSegnalazione());
			evo.setOriginatoreEvento(avo.getOriginatore());
			evo.setCategory(avo.getProblem().getCategoria());
			evo.setState(avo.getProblem().getStato());
			evo.setTitle(avo.getProblem().getTitolo());
			evo.setDescription(avo.getProblem().getDescrizione());
			evo.setCategoryDescription(avo.getProblem().getCategoriaDescrizione());
			evo.setNote("Aggiunto allegato: "+attachmentdao.getNomeFile());
			evo.setInstance(instance);*/
			ArrayList tasks;
			edao.createConnection(instance);
			edao.register(evo);
			tasks=edao.getNormalTasks(evo.getEventType());
			Iterator i=edao.getTransTasks(evo.getEventType()).iterator();
			edao.closeConnection();
			while(i.hasNext()) {
				((Task)i.next()).doAction(evo);
			}
			return tasks;
			// String s = attachmentdao.getTemplate(0);
		} catch(AttachmentDAOException ade){
			throw new AttachmentEJBException(ade.getMessage(), ade.getUserMessage());
		}
		catch (EventDAOException e){
			throw new AttachmentEJBException(e.getMessage(), e.getUserMessage());
			
		} catch (TaskException e) {
			throw new AttachmentEJBException(e.getMessage(), e.getUserMessage());
			
		}
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public void update(AttachmentValueObject avo, String instance) throws AttachmentEJBException{
		try{
			DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
			AttachmentDAO attachmentdao = daoFactory.getAttachmentDAO();
			attachmentdao.createConnection(instance);
			attachmentdao.setId(avo.getId());
			if(avo.getProblem()!=null) attachmentdao.setId_segnalazione(avo.getProblem().getIdSegnalazione());
			else if(avo.getId_segnalazione()!=0)	attachmentdao.setId_segnalazione(avo.getId_segnalazione());
			if(avo.getNomeFile()!=null)	attachmentdao.setNomeFile(avo.getNomeFile());
			if(avo.getDescrizione()!=null)	attachmentdao.setDescrizione(avo.getDescrizione());
			attachmentdao.executeUpdate();
			attachmentdao.closeConnection();
		} catch(AttachmentDAOException ade){
			throw new AttachmentEJBException(ade.getMessage(), ade.getUserMessage());
		}
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public void delete(AttachmentValueObject avo, String instance) throws AttachmentEJBException{
		try{
			DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
			AttachmentDAO attachmentdao = daoFactory.getAttachmentDAO();
			attachmentdao.createConnection(instance);
			attachmentdao.setId(avo.getId());
			attachmentdao.executeDelete();
			attachmentdao.closeConnection();
		} catch(AttachmentDAOException ade){
			throw new AttachmentEJBException(ade.getMessage(), ade.getUserMessage());
		}
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public void preInsert(AttachmentValueObject avo, String instance) throws AttachmentEJBException{
		long l = 0L;
		try{
			DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
			AttachmentDAO attachmentdao = daoFactory.getAttachmentDAO();
			attachmentdao.createConnection(instance);
			attachmentdao.setId_segnalazione(avo.getId_segnalazione());
			attachmentdao.setNomeFile(avo.getNomeFile());
			attachmentdao.setAttachment(avo.getAttachment());
			attachmentdao.setDescrizione(avo.getDescrizione());
			attachmentdao.executeInsert();
			attachmentdao.closeConnection();
		}catch(AttachmentDAOException ade){
			throw new AttachmentEJBException(ade.getMessage(), ade.getUserMessage());
		}
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public ArrayList confirmPreinsert(AttachmentValueObject avo, EventValueObject evo, String instance) throws AttachmentEJBException{
		update(avo, instance);
		DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
		EventDAO edao = daoFactory.getEventDAO();
		try {
			ArrayList tasks;
			edao.createConnection(instance);
			edao.register(evo);
			tasks=edao.getNormalTasks(evo.getEventType());
			Iterator i=edao.getTransTasks(evo.getEventType()).iterator();
			edao.closeConnection();
			while(i.hasNext()) {
				((Task)i.next()).doAction(evo);
			}
			return tasks;
		} catch (EventDAOException e) {
			throw new AttachmentEJBException(e.getMessage(), e.getUserMessage());
		} catch (TaskException e) {
			throw new AttachmentEJBException(e.getMessage(), e.getUserMessage());
		}
	}
}

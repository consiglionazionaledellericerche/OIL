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
package it.cnr.helpdesk.AttachmentManagement.javabeans;


import it.cnr.helpdesk.javabeans.HelpDeskJB;
import it.cnr.helpdesk.dao.AttachmentDAO;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.exceptions.HelpDeskJBException;
import it.cnr.helpdesk.AttachmentManagement.exceptions.*;
import it.cnr.helpdesk.AttachmentManagement.ejb.AttachmentEJBHome;
import it.cnr.helpdesk.AttachmentManagement.ejb.AttachmentEJB;
import it.cnr.helpdesk.AttachmentManagement.valueobjects.AttachmentValueObject;
import it.cnr.helpdesk.EventManagement.javabeans.Event;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;

import java.io.File;

import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Roberto Puccinelli
 */
public class Attachment extends HelpDeskJB {
	private int id;
	private int id_segnalazione;
	private File attachment;
	private String nomeFile;
	private String descrizione;
	
	
	/**
	 * @return Returns the attachment.
	 */
	public File getAttachment() {
		return attachment;
	}
	/**
	 * @param attachment The attachment to set.
	 */
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescrizione() {
		return descrizione;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the id_segnalazione.
	 */
	public int getId_segnalazione() {
		return id_segnalazione;
	}
	/**
	 * @param id_segnalazione The id_segnalazione to set.
	 */
	public void setId_segnalazione(int id_segnalazione) {
		this.id_segnalazione = id_segnalazione;
	}
	
	/**
	 * @return Returns the nomeFile.
	 */
	public String getNomeFile() {
		return nomeFile;
	}
	/**
	 * @param nomeFile The nomeFile to set.
	 */
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
    private AttachmentEJBHome lookupHome()
    throws AttachmentJBException
{
    AttachmentEJBHome attachmentEJBhome = null;
    try
    {
        Context context = getInitialContext();
    	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
        Object obj = context.lookup(jndiPrefix+"AttachmentEJB!it.cnr.helpdesk.AttachmentManagement.ejb.AttachmentEJBHome");
        attachmentEJBhome = (AttachmentEJBHome)PortableRemoteObject.narrow(obj, it.cnr.helpdesk.AttachmentManagement.ejb.AttachmentEJBHome.class);
    }
    catch(NamingException namingexception)
    {
        log(this, "Si \350 verificato un errore durante la ricerca di AttachmentManagement all'interno del registro JNDI. " + namingexception.getMessage());
        namingexception.printStackTrace();
        throw new AttachmentJBException(namingexception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
    }
    catch(AttachmentEJBException ae)
    {
        throw new AttachmentJBException(ae.getMessage(), ae.getUserMessage());
    }
    catch(Exception exception)
    {
        log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
        exception.printStackTrace();
        throw new AttachmentJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
    }
    return attachmentEJBhome;
}
	
	public void insert(AttachmentValueObject avo, String instance) throws AttachmentJBException{
		//new AttachmentValueObject(this.id_segnalazione, this.nomeFile, this.attachment, this.descrizione);
		AttachmentEJB attachmentEJB=null;
		AttachmentEJBHome home=lookupHome();
		try {
			EventValueObject evo = new EventValueObject();
			evo.setEventType(Event.AGGIUNTA_ALLEGATO);
			evo.setIdSegnalazione(avo.getProblem().getIdSegnalazione());
			evo.setOriginatoreEvento(avo.getOriginatore());
			evo.setCategory(avo.getProblem().getCategoria());
			evo.setState(avo.getProblem().getStato());
			evo.setTitle(avo.getProblem().getTitolo());
			evo.setDescription(avo.getProblem().getDescrizione());
			evo.setCategoryDescription(avo.getProblem().getCategoriaDescrizione());
			evo.setNote("Aggiunto allegato: "+avo.getNomeFile());
			evo.setInstance(instance);
			attachmentEJB=home.create();
			ArrayList tasks = attachmentEJB.insert(avo, evo, instance);
			attachmentEJB.remove();
			executeTasks(tasks, evo);
		} catch (java.rmi.RemoteException re) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + re.getMessage());
	    	re.printStackTrace();
	    	throw new AttachmentJBException(re.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (javax.ejb.CreateException ce) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + ce.getMessage());
	    	ce.printStackTrace();
	    	throw new AttachmentJBException(ce.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (javax.ejb.RemoveException re) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + re.getMessage());
	    	re.printStackTrace();
	    	throw new AttachmentJBException(re.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (HelpDeskJBException e) {
			e.printStackTrace();
			throw new AttachmentJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		
	}
	
	public void preInsert(AttachmentValueObject avo, String instance) throws AttachmentJBException{
		//new AttachmentValueObject(this.id_segnalazione, this.nomeFile, this.attachment, this.descrizione);
		AttachmentEJB attachmentEJB=null;
		AttachmentEJBHome home=lookupHome();
		try {
			attachmentEJB=home.create();
			attachmentEJB.preInsert(avo, instance);
			attachmentEJB.remove();
		} catch (java.rmi.RemoteException re) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + re.getMessage());
	    	re.printStackTrace();
	    	throw new AttachmentJBException(re.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (javax.ejb.CreateException ce) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + ce.getMessage());
	    	ce.printStackTrace();
	    	throw new AttachmentJBException(ce.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (javax.ejb.RemoveException re) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + re.getMessage());
	    	re.printStackTrace();
	    	throw new AttachmentJBException(re.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		
	}

	public void confirmPreinsert(AttachmentValueObject avo, String instance) throws AttachmentJBException{
		AttachmentEJB attachmentEJB=null;
		AttachmentEJBHome home=lookupHome();
		try {
			EventValueObject evo = new EventValueObject();
			evo.setEventType(Event.AGGIUNTA_ALLEGATO);
			evo.setIdSegnalazione(avo.getProblem().getIdSegnalazione());
			evo.setOriginatoreEvento(avo.getOriginatore());
			evo.setCategory(avo.getProblem().getCategoria());
			evo.setTitle(avo.getProblem().getTitolo());
			evo.setDescription(avo.getProblem().getDescrizione());
			evo.setState(avo.getProblem().getStato());
			evo.setNote("Aggiunto allegato: "+avo.getNomeFile());
			evo.setCategoryDescription(avo.getProblem().getCategoriaDescrizione());
			evo.setInstance(instance);
			attachmentEJB=home.create();
			ArrayList tasks = attachmentEJB.confirmPreinsert(avo, evo, instance);
			attachmentEJB.remove();
			executeTasks(tasks, evo);
		} catch (java.rmi.RemoteException re) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + re.getMessage());
	    	re.printStackTrace();
	    	throw new AttachmentJBException(re.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (javax.ejb.CreateException ce) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + ce.getMessage());
	    	ce.printStackTrace();
	    	throw new AttachmentJBException(ce.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (javax.ejb.RemoveException re) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + re.getMessage());
	    	re.printStackTrace();
	    	throw new AttachmentJBException(re.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (HelpDeskJBException e) {
			e.printStackTrace();
			throw new AttachmentJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
	}
	
	public void update(String instance) throws AttachmentJBException{
		AttachmentValueObject avo=new AttachmentValueObject();
		avo.setId(this.id);
		if(this.id_segnalazione!=0) avo.setId_segnalazione(this.id_segnalazione);
		if(this.nomeFile!=null)	avo.setNomeFile(this.nomeFile);
		if(this.descrizione!=null)	avo.setDescrizione(this.descrizione);
		AttachmentEJB attachmentEJB=null;
		AttachmentEJBHome home=lookupHome();
		try {
			attachmentEJB=home.create();
			attachmentEJB.update(avo, instance);
			attachmentEJB.remove();
		} catch (java.rmi.RemoteException re) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + re.getMessage());
	    	re.printStackTrace();
	    	throw new AttachmentJBException(re.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (javax.ejb.CreateException ce) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + ce.getMessage());
	    	ce.printStackTrace();
	    	throw new AttachmentJBException(ce.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (javax.ejb.RemoveException re) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + re.getMessage());
	    	re.printStackTrace();
	    	throw new AttachmentJBException(re.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		
	}
	
	public void delete(String instance) throws AttachmentJBException{
		AttachmentValueObject avo=new AttachmentValueObject();
		avo.setId(this.id);
		AttachmentEJB attachmentEJB=null;
		AttachmentEJBHome home=lookupHome();
		try {
			attachmentEJB=home.create();
			attachmentEJB.delete(avo, instance);
			attachmentEJB.remove();
		} catch (RemoteException e) {
			throw new AttachmentJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (CreateException e) {
			throw new AttachmentJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (RemoveException e) {
			throw new AttachmentJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
	}
	
	public Collection fetchAttachments(long id_segn, String instance){
		Collection files=null;
		try {
			AttachmentDAO attachmentdao = DAOFactory.getDAOFactoryByProperties().getAttachmentDAO();
			attachmentdao.createConnection(instance);
			files = attachmentdao.fetchAttachments(id_segn);
			attachmentdao.closeConnection();
		} catch(Exception ade){
			ade.printStackTrace();
		}
		return files;
	}
	
	public AttachmentValueObject fetchAttachment(String instance){
		AttachmentValueObject avo = null;
		try {
			AttachmentDAO attachmentdao = DAOFactory.getDAOFactoryByProperties().getAttachmentDAO();
			attachmentdao.createConnection(instance);
			attachmentdao.setId(this.id);
			avo = attachmentdao.fetchAttachment();
			attachmentdao.closeConnection();
		} catch(Exception ade){
			ade.printStackTrace();
		}
		return avo;
	}
}
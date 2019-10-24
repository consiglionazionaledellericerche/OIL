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

package it.cnr.helpdesk.MailParserManagement.javabeans;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import it.cnr.helpdesk.AttachmentManagement.exceptions.AttachmentJBException;
import it.cnr.helpdesk.AttachmentManagement.javabeans.Attachment;
import it.cnr.helpdesk.AttachmentManagement.valueobjects.AttachmentValueObject;
import it.cnr.helpdesk.EventManagement.javabeans.Event;
import it.cnr.helpdesk.MailParserManagement.ejb.MailParserManagement;
import it.cnr.helpdesk.MailParserManagement.ejb.MailParserManagementHome;
import it.cnr.helpdesk.MailParserManagement.exceptions.MailParserDAOException;
import it.cnr.helpdesk.MailParserManagement.exceptions.MailParserEJBException;
import it.cnr.helpdesk.MailParserManagement.exceptions.MailParserJBException;
import it.cnr.helpdesk.MailParserManagement.valueobjects.MailComponent;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.MailParserDAO;
import it.cnr.helpdesk.javabeans.HelpDeskJB;
import it.cnr.helpdesk.util.Converter;

/**
 * @author aldo stentella
 *
 */
public class MailParser extends HelpDeskJB {

	
	public int parseMail(String contextPath, String instance) throws MailParserJBException{
		MailParserManagementHome home = lookupHome();
		try {
			MailParserManagement mail = (MailParserManagement)PortableRemoteObject.narrow(home.create(), MailParserManagement.class);
			return mail.parseMail(contextPath, instance);
		} catch (MailParserEJBException e){
			throw new MailParserJBException(e.getRootCause());
		} catch (RemoteException e) {
			if(e.getCause() instanceof MailParserEJBException){
				throw new MailParserJBException(((MailParserEJBException)e.getCause()).getRootCause());
			}else
				throw new MailParserJBException(e);
		} catch (ClassCastException e) {
			e.printStackTrace();
			throw new MailParserJBException(e);
		} catch (CreateException e) {
			e.printStackTrace();
			throw new MailParserJBException(e);
		}
	}
	
	public int confirmMail(String id, String cod, String instance){
		final int SYSTEM_ERROR = 4;
		try {
			MailParserManagementHome home = lookupHome();
			MailParserManagement mail = (MailParserManagement)PortableRemoteObject.narrow(home.create(), MailParserManagement.class);
			return mail.confirmMail(id, cod, instance);
		} catch (Exception e) {
			e.printStackTrace();
			return SYSTEM_ERROR;
		}
	}
	
	public int TicketVerify(String id, String cod, String instance){
		final int SYSTEM_ERROR = 4;
		try {
			MailParserManagementHome home = lookupHome();
			MailParserManagement mail = (MailParserManagement)PortableRemoteObject.narrow(home.create(), MailParserManagement.class);
			return mail.TicketVerify(id,cod,instance);
		} catch (Exception e) {
			e.printStackTrace();
			return SYSTEM_ERROR;
		}
	}

	private MailParserManagementHome lookupHome() throws MailParserJBException {
		MailParserManagementHome home = null;
		try {
			Context context = getInitialContext();
			if(context == null) {
				log(this, "Initial Context=null");
			} else {
            	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
                Object obj1 = context.lookup(jndiPrefix+"MailParserManagement!it.cnr.helpdesk.MailParserManagement.ejb.MailParserManagementHome");
				home = (MailParserManagementHome)PortableRemoteObject.narrow(obj1, it.cnr.helpdesk.MailParserManagement.ejb.MailParserManagementHome.class);
			}
		} catch(NamingException namingexception) {
			log(this, "Si \350 verificato un errore durante la ricerca all'interno del registro JNDI. " + namingexception.getMessage());
			namingexception.printStackTrace();
			throw new MailParserJBException(namingexception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch(MailParserEJBException ejbexception) {
			throw new MailParserJBException(ejbexception.getMessage());
		}
		return home;
	}
	
	public MailComponent getByIdSegnalazione(long segnalazione, String instance) throws MailParserJBException{
		MailParserDAO mpdao = DAOFactory.getDAOFactoryByProperties().getMailParserDAO();
		MailComponent mc;
		try {
			mpdao.createConnection(instance);
			mc = mpdao.getByIdSegnalazione(segnalazione);
			mpdao.closeConnection();
			if(mc==null) throw new MailParserJBException("Segnalazione non generata da Processatore E-mail.","Segnalazione non generata da Processatore E-mail.");
		} catch (MailParserDAOException e) {
			e.printStackTrace();
			throw new MailParserJBException(e.getMessage());
		}
		//uvo=new UserValueObject("","",temp.getNome(),temp.getCognome(),1,temp.getMail(),"","","y","","n"); 
		return mc;
	}
	
	public long executeInsert(MailComponent mail, ArrayList attachments, String instance) throws MailParserJBException {
		MailParserManagementHome home = lookupHome();
		try {
			MailParserManagement mpm = (MailParserManagement)PortableRemoteObject.narrow(home.create(), MailParserManagement.class);
			return mpm.executeInsert(mail, attachments, instance);
		} catch (MailParserEJBException e) {
			throw new MailParserJBException(e);
		} catch (RemoteException e) {
			throw new MailParserJBException(e);
		} catch (ClassCastException e) {
			throw new MailParserJBException(e);
		} catch (CreateException e) {
			throw new MailParserJBException(e);
		}
	}

	public void executeAppend(long sid, int newState, String body, ArrayList attachments, String instance) throws MailParserJBException {
		MailParserManagementHome home = lookupHome();
		try {
			MailParserManagement mpm = (MailParserManagement)PortableRemoteObject.narrow(home.create(), MailParserManagement.class);
			mpm.executeAppend(sid, newState, body, attachments, instance);
		} catch (MailParserEJBException e) {
			throw new MailParserJBException(e);
		} catch (RemoteException e) {
			throw new MailParserJBException(e);
		} catch (ClassCastException e) {
			throw new MailParserJBException(e);
		} catch (CreateException e) {
			throw new MailParserJBException(e);
		}
	}
	
	public long problemInsert(UserValueObject uvo, ProblemValueObject pvo, List<File> attachments, String instance) throws MailParserJBException{
		MailParserManagementHome home = lookupHome();
		try {
			MailComponent mail = new MailComponent();
			mail.setCategoria(pvo.getCategoria());
			mail.setCognome(uvo.getFamilyName());
			mail.setConferma(true);
			mail.setData((new Date()).toString());
			mail.setDescrizione(pvo.getDescrizione());
			mail.setMail(uvo.getEmail());
			mail.setNome(uvo.getFirstName());
			mail.setTitolo(pvo.getTitolo());
			MailParserManagement mpm = (MailParserManagement)PortableRemoteObject.narrow(home.create(), MailParserManagement.class);
			return mpm.problemInsert(mail, pvo, attachments, instance);

		} catch (MailParserEJBException e) {
			throw new MailParserJBException(e);
		} catch (RemoteException e) {
			throw new MailParserJBException(e);
		} catch (ClassCastException e) {
			throw new MailParserJBException(e);
		} catch (CreateException e) {
			throw new MailParserJBException(e);
		}
	}
	
	public void attachmentInsert(long sid, File attachment, String instance) throws MailParserJBException{
		MailParserManagementHome home = lookupHome();
		try {
			MailParserManagement mpm = (MailParserManagement)PortableRemoteObject.narrow(home.create(), MailParserManagement.class);
			mpm.attachmentInsert(sid, attachment, instance);
		} catch (ClassCastException e) {
			throw new MailParserJBException(e);
		} catch (RemoteException e) {
			throw new MailParserJBException(e);
		} catch (CreateException e) {
			throw new MailParserJBException(e);
		}
	}
	
	public void attachmentPreinsert(long sid, File file, String instance) throws MailParserJBException{
		Attachment attachment=new Attachment();
		AttachmentValueObject avo = new AttachmentValueObject();
		avo.setNomeFile(file.getName());
		avo.setDescrizione("Da MailParser");
		avo.setAttachment(file);
		avo.setId_segnalazione(sid);
		try {
			attachment.preInsert(avo, instance);
		} catch (AttachmentJBException e) {
			e.printStackTrace();
			throw new MailParserJBException(e);
		}
	}
	
	public void changeState(long sid, int newState, String body, String originatore, String instance) throws MailParserJBException {
		MailParserManagementHome home = lookupHome();
		try {
			MailParserManagement mpm = (MailParserManagement)PortableRemoteObject.narrow(home.create(), MailParserManagement.class);
			mpm.changeState(sid, newState, body, originatore, instance);
		} catch (MailParserEJBException e) {
			throw new MailParserJBException(e);
		} catch (RemoteException e) {
			throw new MailParserJBException(e);
		} catch (ClassCastException e) {
			throw new MailParserJBException(e);
		} catch (CreateException e) {
			throw new MailParserJBException(e);
		}
	}
	
	public void changeCategory(long sid, int category, String categoryName, String body, String originatore, String instance) throws MailParserJBException{
		MailParserManagementHome home = lookupHome();
		try {
			MailParserManagement mpm = (MailParserManagement)PortableRemoteObject.narrow(home.create(), MailParserManagement.class);
			mpm.changeCategory(sid, category, categoryName, body, originatore, instance);
		}catch (MailParserEJBException e){
			throw new MailParserJBException(e);
		} catch (RemoteException e) {
			throw new MailParserJBException(e);
		} catch (ClassCastException e) {
			throw new MailParserJBException(e);
		} catch (CreateException e) {
			throw new MailParserJBException(e);
		}
		
	}
}

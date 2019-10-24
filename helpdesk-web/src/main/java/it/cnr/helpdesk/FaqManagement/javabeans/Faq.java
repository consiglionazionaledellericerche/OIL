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

 

package it.cnr.helpdesk.FaqManagement.javabeans;

import it.cnr.helpdesk.FaqManagement.dto.FaqDTO;
import it.cnr.helpdesk.FaqManagement.ejb.FaqManagement;
import it.cnr.helpdesk.FaqManagement.ejb.FaqManagementHome;
import it.cnr.helpdesk.FaqManagement.exceptions.FaqEJBException;
import it.cnr.helpdesk.FaqManagement.exceptions.FaqJBException;
import it.cnr.helpdesk.javabeans.HelpDeskJB;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import java.util.Collection;

// Referenced classes of package it.cnr.helpdesk.javabeans:
//            HelpDeskJB

public class Faq extends HelpDeskJB {

	public Faq() {
		idFaq = 0;
		titolo = null;
		descrizione = null;
		idCategoria = 0;
		originatore = null;
	}

	public void setIdFaq(int i) {
		idFaq = i;
	}

	public int getIdFaq() {
		return idFaq;
	}

	public void setTitolo(String s) {
		titolo = s;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setDescrizione(String s) {
		descrizione = s;
	}

	public void setOriginatore(String s) {
		originatore = s;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getOriginatore() {
		return originatore;
	}

	public void setIdCategoria(int i) {
		idCategoria = i;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public FaqDTO getFaqDetail(String instance) throws FaqJBException {
		FaqDTO faqdto = null;
		try {
			FaqManagementHome faqmanagementhome = lookupHome();
			FaqManagement faqmanagement = (FaqManagement) PortableRemoteObject.narrow(faqmanagementhome.create(), it.cnr.helpdesk.FaqManagement.ejb.FaqManagement.class);
			faqdto = faqmanagement.getFaqDetail(getIdFaq(), instance);
			faqmanagement.remove();
		} catch (FaqEJBException faqejbexception) {
			throw new FaqJBException(faqejbexception.getMessage(), faqejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new FaqJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return faqdto;
	}

	public Collection getFaqList(String instance) throws FaqJBException {
		Collection collection = null;
		try {
			FaqManagementHome faqmanagementhome = lookupHome();
			FaqManagement faqmanagement = (FaqManagement) PortableRemoteObject.narrow(faqmanagementhome.create(), it.cnr.helpdesk.FaqManagement.ejb.FaqManagement.class);
			FaqDTO faqdto = new FaqDTO();
			faqdto.setIdCategoria(getIdCategoria());
			collection = faqmanagement.getFaqList(faqdto, instance);
		} catch (FaqEJBException faqejbexception) {
			throw new FaqJBException(faqejbexception.getMessage(), faqejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new FaqJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return collection;
	}

	public void insert(String instance) throws FaqJBException {
		try {
			FaqManagementHome faqmanagementhome = lookupHome();
			FaqManagement faqmanagement = (FaqManagement) PortableRemoteObject.narrow(faqmanagementhome.create(), it.cnr.helpdesk.FaqManagement.ejb.FaqManagement.class);
			FaqDTO faqdto = new FaqDTO();
			faqdto.setTitolo(getTitolo());
			faqdto.setDescrizione(getDescrizione());
			faqdto.setIdCategoria(getIdCategoria());
			faqdto.setOriginatore(getOriginatore());
			faqmanagement.insertFaq(faqdto, instance);
			faqmanagement.remove();
		} catch (FaqEJBException faqejbexception) {
			throw new FaqJBException(faqejbexception.getMessage(), faqejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new FaqJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
	}

	public void updateFaq(String instance) throws FaqJBException {
		try {
			FaqManagementHome faqmanagementhome = lookupHome();
			FaqManagement faqmanagement = (FaqManagement) PortableRemoteObject.narrow(faqmanagementhome.create(), it.cnr.helpdesk.FaqManagement.ejb.FaqManagement.class);
			FaqDTO faqdto = new FaqDTO();
			faqdto.setIdFaq(getIdFaq());
			faqdto.setTitolo(getTitolo());
			faqdto.setDescrizione(getDescrizione());
			faqdto.setOriginatore(getOriginatore());
			faqdto.setIdCategoria(getIdCategoria());
			faqmanagement.updateFaq(faqdto, instance);
			faqmanagement.remove();
		} catch (FaqEJBException faqejbexception) {
			throw new FaqJBException(faqejbexception.getMessage(), faqejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new FaqJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
	}

	public void remove(String instance) throws FaqJBException {
		try {
			FaqManagementHome faqmanagementhome = lookupHome();
			FaqManagement faqmanagement = (FaqManagement) PortableRemoteObject.narrow(faqmanagementhome.create(), it.cnr.helpdesk.FaqManagement.ejb.FaqManagement.class);
			faqmanagement.removeFaq(getIdFaq(), instance);
			faqmanagement.remove();
		} catch (FaqEJBException faqejbexception) {
			throw new FaqJBException(faqejbexception.getMessage(), faqejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new FaqJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
	}

	private FaqManagementHome lookupHome() throws FaqJBException {
		FaqManagementHome faqmanagementhome = null;
		try {
			Context context = getInitialContext();
        	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
            Object obj = context.lookup(jndiPrefix+"FaqManagement!it.cnr.helpdesk.FaqManagement.ejb.FaqManagementHome");
			faqmanagementhome = (FaqManagementHome) PortableRemoteObject.narrow(obj, it.cnr.helpdesk.FaqManagement.ejb.FaqManagementHome.class);
		} catch (FaqEJBException faqejbexception) {
			throw new FaqJBException(faqejbexception.getMessage(), faqejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore durante la ricerca nel registro JNDI. " + exception.getMessage());
			exception.printStackTrace();
			throw new FaqJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return faqmanagementhome;
	}

	private int idFaq;
	private String titolo;
	private String descrizione;
	private int idCategoria;
	private String originatore;
}
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

package it.cnr.helpdesk.ProblemManagement.javabeans;

import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.ProblemDAO;
import it.cnr.helpdesk.EventManagement.javabeans.Event;
import it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement;
import it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagementHome;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemDAOException;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemEJBException;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.valueobjects.*;
import it.cnr.helpdesk.StateMachineManagement.StateMachine;
import it.cnr.helpdesk.StateMachineManagement.exceptions.ConditionException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineJBException;
import it.cnr.helpdesk.StateMachineManagement.javabeans.Transition;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.TransitionKey;
import it.cnr.helpdesk.javabeans.HelpDeskJB;
import it.cnr.helpdesk.util.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.commons.lang.StringEscapeUtils;

// Referenced classes of package it.cnr.helpdesk.javabeans:
//            HelpDeskJB

public class Problem extends HelpDeskJB {

	protected int flagFaq;
	protected int stato;
	protected int oldStato = 0;
	protected String statoDescrizione;
	protected int categoria;
	protected String categoriaDescrizione;
	protected String titolo;
	protected long idSegnalazione;
	protected String esperto;
	protected String originatore;
	protected String dataEvento;
	protected String descrizione;
	protected int priorita;
	protected String prioritaDescrizione;
	// il validatore che prende in carico il problema
	protected String validatore;

	/**
	 * @return Returns the validatore.
	 */
	public String getValidatore() {
		return validatore;
	}

	/**
	 * @param validatore
	 *            The validatore to set.
	 */
	public void setValidatore(String validatore) {
		this.validatore = validatore;
	}

	/**
	 * @return Returns the oldStato.
	 */
	public int getOldStato() {
		return oldStato;
	}

	/**
	 * @param oldStato
	 *            The oldStato to set.
	 */
	public void setOldStato(int oldStato) {
		this.oldStato = oldStato;
	}

	public Problem() {
		titolo = "";
		esperto = "";
		originatore = "";
		dataEvento = "";
		descrizione = "";
	}

	public void setIdSegnalazione(long l) {
		idSegnalazione = l;
	}

	public long getIdSegnalazione() {
		return idSegnalazione;
	}

	public void setTitolo(String s) {
		titolo = s;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setStatoDescrizione(String s) {
		statoDescrizione = s;
	}

	public String getStatoDescrizione() {
		return statoDescrizione;
	}

	public void setCategoriaDescrizione(String s) {
		categoriaDescrizione = s;
	}

	public String getCategoriaDescrizione() {
		return categoriaDescrizione;
	}

	public void setDescrizione(String s) {
		descrizione = s;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setCategoria(int i) {
		categoria = i;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setStato(int i) {
		stato = i;
	}

	public int getStato() {
		return stato;
	}

	public void setOriginatore(String s) {
		originatore = s;
	}

	public String getOriginatore() {
		return originatore;
	}

	public void setDataEvento(String s) {
		dataEvento = s;
	}

	public String getDataEvento() {
		return dataEvento;
	}

	public void setEsperto(String s) {
		esperto = s;
	}

	public String getEsperto() {
		return esperto;
	}

	public void setFlagFaq(int i) {
		flagFaq = i;
	}

	public int getFlagFaq() {
		return flagFaq;
	}

	/**
	 * @return Returns the priorita.
	 */
	public int getPriorita() {
		return priorita;
	}

	/**
	 * @param priorita
	 *            The priorita to set.
	 */
	public void setPriorita(int priorita) {
		this.priorita = priorita;
	}

	/**
	 * @return Returns the prioritaDescrizione.
	 */
	public String getPrioritaDescrizione() {
		return prioritaDescrizione;
	}

	/**
	 * @param prioritaDescrizione
	 *            The prioritaDescrizione to set.
	 */
	public void setPrioritaDescrizione(String prioritaDescrizione) {
		this.prioritaDescrizione = prioritaDescrizione;
	}

	public PageByPageIterator getUnclosedExpertProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject, String instance) throws ProblemJBException {
		SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
		searchingset.add(new Integer(1));
		searchingset.add(new Integer(2));
		searchingset.add(new Integer(3));
		searchingset.add(new Integer(6)); // in required Validation
		searchingset.add(new Integer(7)); // in validating

		problemvalueobject.addSearchingItem(searchingset);
		PageByPageIterator pagebypageiterator1 = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			pagebypageiterator1 = problemmanagement.getExpertProblemQueue(pagebypageiterator, problemvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return pagebypageiterator1;
	}

	public PageByPageIterator getTeamProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject, String instance) throws ProblemJBException {
		PageByPageIterator pagebypageiterator1 = null;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		try {
			problemdao.createConnection(instance);
			pagebypageiterator1 = problemdao.getTeamProblemQueue(pagebypageiterator, problemvalueobject);
			problemdao.closeConnection();
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return pagebypageiterator1;
	}

	/**
	 * Recupero delle segnalazioni con stato appartenenete ad un deternminato dominio comunque non chiuse, di una determinata la categoria ed un determinato utente, VALIDATORE o EXPERTO
	 * 
	 * @param ProblemCountValueObject
	 *            problemcountvalueobject contiene i dati per i criteri di ricerca idUtente, Profilo, stai segnalazione di interesse
	 * @param PageByPageIterator
	 *            pagebypageiterator
	 * 
	 * @throws ProblemJBException
	 * @return PageByPageIterator.
	 * 
	 * 
	 */
	public PageByPageIterator getUnclosedProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject, String instance) throws ProblemJBException {

		PageByPageIterator pagebypageiterator1 = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			pagebypageiterator1 = problemmanagement.getExpertProblemQueue(pagebypageiterator, problemvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}

		return pagebypageiterator1;

	}// end method

	public PageByPageIterator getClosedExpertProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject, String instance) throws ProblemJBException {
		SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
		searchingset.add(new Integer(4));
		problemvalueobject.addSearchingItem(searchingset);
		PageByPageIterator pagebypageiterator1 = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			pagebypageiterator1 = problemmanagement.getExpertProblemQueue(pagebypageiterator, problemvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return pagebypageiterator1;
	}

	public PageByPageIterator getVerifiedExpertProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject, String instance) throws ProblemJBException {
		SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
		searchingset.add(new Integer(5));
		problemvalueobject.addSearchingItem(searchingset);
		PageByPageIterator pagebypageiterator1 = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			pagebypageiterator1 = problemmanagement.getExpertProblemQueue(pagebypageiterator, problemvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return pagebypageiterator1;
	}

	public PageByPageIterator getProblemQueueQBE(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject, String instance) throws ProblemJBException {
		if (problemvalueobject.getStato() >= 1 && problemvalueobject.getStato() <= 5) {
			SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
			searchingset.add(new Integer(problemvalueobject.getStato()));
			problemvalueobject.addSearchingItem(searchingset);
		}
		if (problemvalueobject.getCategoria() > 0) {
			SearchingSet searchingset1 = new SearchingSet("segnalazione", "CATEGORIA");
			searchingset1.add(new Integer(problemvalueobject.getCategoria()));
			problemvalueobject.addSearchingItem(searchingset1);
		}
		if (problemvalueobject.getDescrizione().length() > 0) {
			SearchingLike searchinglike = new SearchingLike("segnalazione", "DESCRIZIONE");
			searchinglike.add(new String(problemvalueobject.getDescrizione()));
			if(problemvalueobject.isExtendedSearch())
				searchinglike.setCustomTail(" or segnalazione.ID_SEGNALAZIONE IN (SELECT segnalazione FROM evento ev2 where upper(ev2.nota) like upper('%"+StringEscapeUtils.escapeSql(problemvalueobject.getDescrizione())+"%'))");
			problemvalueobject.addSearchingItem(searchinglike);
		}
		if (problemvalueobject.getTitolo().length() > 0) {
			SearchingLike searchinglike1 = new SearchingLike("segnalazione", "TITOLO");
			searchinglike1.add(new String(problemvalueobject.getTitolo()));
			problemvalueobject.addSearchingItem(searchinglike1);
		}
		if (problemvalueobject.getIdSegnalazione() > 0L) {
			SearchingSet searchingset2 = new SearchingSet("segnalazione", "ID_SEGNALAZIONE");
			searchingset2.add(new Long(problemvalueobject.getIdSegnalazione()));
			problemvalueobject.addSearchingItem(searchingset2);
		}
		if (problemvalueobject.getEsperto().length() > 0) {
			SearchingLike searchinglike2 = new SearchingLike("segnalazione", "ESPERTO");
			searchinglike2.add(new String(problemvalueobject.getEsperto()));
			problemvalueobject.addSearchingItem(searchinglike2);
		}
		if (problemvalueobject.getOriginatore().length() > 0) {
			SearchingLike searchinglike3 = new SearchingLike("segnalazione", "ORIGINATORE");
			searchinglike3.add(new String(problemvalueobject.getOriginatore()));
			problemvalueobject.addSearchingItem(searchinglike3);
		}
		if (problemvalueobject.getOriginatoreFirstName().length() > 0) {
			SearchingLike searchinglike4 = new SearchingLike("originatore", "NOME");
			searchinglike4.add(new String(problemvalueobject.getOriginatoreFirstName()));
			problemvalueobject.addSearchingItem(searchinglike4);
		}
		if (problemvalueobject.getOriginatoreFamilyName().length() > 0) {
			SearchingLike searchinglike5 = new SearchingLike("originatore", "COGNOME");
			searchinglike5.add(new String(problemvalueobject.getOriginatoreFamilyName()));
			problemvalueobject.addSearchingItem(searchinglike5);
		}
		if (problemvalueobject.getOriginatoreStruttura().length() > 0) {
			SearchingLike searchinglike6 = new SearchingLike("originatore", "STRUTTURA");
			searchinglike6.add(new String(problemvalueobject.getOriginatoreStruttura()));
			problemvalueobject.addSearchingItem(searchinglike6);
		}
		if (problemvalueobject.getEspertoFirstName().length() > 0) {
			SearchingLike searchinglike7 = new SearchingLike("esperto", "NOME");
			searchinglike7.add(new String(problemvalueobject.getEspertoFirstName()));
			problemvalueobject.addSearchingItem(searchinglike7);
		}
		if (problemvalueobject.getEspertoFamilyName().length() > 0) {
			SearchingLike searchinglike8 = new SearchingLike("esperto", "COGNOME");
			searchinglike8.add(new String(problemvalueobject.getEspertoFamilyName()));
			problemvalueobject.addSearchingItem(searchinglike8);
		}
		if (problemvalueobject.getEspertoStruttura().length() > 0) {
			SearchingLike searchinglike9 = new SearchingLike("esperto", "STRUTTURA");
			searchinglike9.add(new String(problemvalueobject.getEspertoStruttura()));
			problemvalueobject.addSearchingItem(searchinglike9);
		}
		PageByPageIterator pagebypageiterator1 = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			pagebypageiterator1 = problemmanagement.getExpertProblemQueue(pagebypageiterator, problemvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return pagebypageiterator1;
	}

	public PageByPageIterator getClosedAndVerifiedProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject, String instance) throws ProblemJBException {
		SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
		searchingset.add(new Integer(5));
		searchingset.add(new Integer(4));
		problemvalueobject.addSearchingItem(searchingset);
		PageByPageIterator pagebypageiterator1 = null;
		problemvalueobject.setOwnerShip(false);
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			pagebypageiterator1 = problemmanagement.getExpertProblemQueue(pagebypageiterator, problemvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return pagebypageiterator1;
	}

	public PageByPageIterator getUnclosedUserProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject, String instance) throws ProblemJBException {
		SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
		searchingset.add(new Integer(1));
		searchingset.add(new Integer(2));
		searchingset.add(new Integer(3));
		searchingset.add(new Integer(6)); // segnal. REQUIRED VALIDATION
		searchingset.add(new Integer(7)); // segnal. VALIDATING
		searchingset.add(new Integer(9)); // segnal. VALIDATING
		problemvalueobject.addSearchingItem(searchingset);
		PageByPageIterator pagebypageiterator1 = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			pagebypageiterator1 = problemmanagement.getUserProblemQueue(pagebypageiterator, problemvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return pagebypageiterator1;
	}

	public PageByPageIterator getClosedUserProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject, String instance) throws ProblemJBException {
		SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
		searchingset.add(new Integer(4));
		problemvalueobject.addSearchingItem(searchingset);
		PageByPageIterator pagebypageiterator1 = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			pagebypageiterator1 = problemmanagement.getUserProblemQueue(pagebypageiterator, problemvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return pagebypageiterator1;
	}

	public Collection getAllExpertProblems(String instance) throws ProblemJBException {
		Collection problems = null;

		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			problems = problemmanagement.getAllExpertProblems(esperto, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return problems;
	}

	public Collection getReassignableExpertProblems(String instance) throws ProblemJBException {
		Collection problems = null;
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			ProblemDAO problemdao = daoFactory.getProblemDAO();
			problemdao.createConnection(instance);
			problemdao.setEsperto(esperto);
			problems = problemdao.getReassignableExpertProblems();
			problemdao.closeConnection();
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		}
		return problems;
	}

	private ProblemManagementHome lookupHome() throws ProblemJBException {
		ProblemManagementHome problemmanagementhome = null;
		try {
			Context context = getInitialContext();
        	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
            Object obj = context.lookup(jndiPrefix+"ProblemManagement!it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagementHome");
			problemmanagementhome = (ProblemManagementHome) PortableRemoteObject.narrow(obj, it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagementHome.class);
		} catch (NamingException namingexception) {
			log(this, "Si \350 verificato un errore durante la ricerca di ProblemManagement all'interno del registro JNDI. " + namingexception.getMessage());
			namingexception.printStackTrace();
			throw new ProblemJBException(namingexception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return problemmanagementhome;
	}

	public Collection getBacklogProblemCountPerCategory(ProblemCountValueObject problemcountvalueobject, String instance) throws ProblemJBException {
		SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
		searchingset.add(new Integer(1));// stato 1 per segn. OPEN , anche se non hanno ancora un esperto assegnato
		searchingset.add(new Integer(2));
		searchingset.add(new Integer(3));
		searchingset.add(new Integer(6));// stato 6 per segn. in REQUIRED VALIDATION
		searchingset.add(new Integer(7));// stato 7 per segn. in VALIDATING
		problemcountvalueobject.addSearchingItem(searchingset);
		Collection collection = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			collection = problemmanagement.getProblemCountPerCategory(problemcountvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return collection;
	}

	public Collection getTeamProblemCountPerCategory(ProblemCountValueObject problemcountvalueobject, String instance) throws ProblemJBException {
		Collection collection = null;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		try {
			problemdao.createConnection(instance);
			collection = problemdao.getTeamProblemCountPerCategory(problemcountvalueobject);
			problemdao.closeConnection();
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return collection;
	}

	/**
	 * Recupero del numero di segnalazioni pendenti per ogni categoria assegnata ad un determinato utente. Le segnalazioni sono quelle assegnate o meno all'utente.
	 * 
	 * @param ProblemCountValueObject
	 *            problemcountvalueobject fornisce l'utente con utente. Al momemto 20 feb 06 il profilo viene settato solo nel caso di validatore mentre per expert non è settato.
	 * 
	 * @throws ProblemDAOException
	 * @return Collection collezione di ProblemJBException.
	 */
	public Collection getNumBacklogProblemPerCategory(ProblemCountValueObject problemcountvalueobject, String instance) throws ProblemJBException {

		Collection collection = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);

			collection = problemmanagement.getProblemCountPerCategory(problemcountvalueobject, instance);

			problemmanagement.remove();

		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return collection;

	}// end method

	public Collection getClosedProblemCountPerCategory(ProblemCountValueObject problemcountvalueobject, String instance) throws ProblemJBException {
		SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
		searchingset.add(new Integer(4));
		problemcountvalueobject.addSearchingItem(searchingset);
		Collection collection = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			collection = problemmanagement.getProblemCountPerCategory(problemcountvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return collection;
	}

	public Collection getVerifiedProblemCountPerCategory(ProblemCountValueObject problemcountvalueobject, String instance) throws ProblemJBException {
		SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
		searchingset.add(new Integer(5));
		problemcountvalueobject.addSearchingItem(searchingset);
		Collection collection = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			collection = problemmanagement.getProblemCountPerCategory(problemcountvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return collection;
	}

	public Collection getClosedAndVerifiedProblemCountPerCategory(ProblemCountValueObject problemcountvalueobject, String instance) throws ProblemJBException {
		SearchingSet searchingset = new SearchingSet("segnalazione", "STATO");
		searchingset.add(new Integer(4));
		searchingset.add(new Integer(5));
		problemcountvalueobject.addSearchingItem(searchingset);
		Collection collection = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			collection = problemmanagement.getProblemCountPerCategory(problemcountvalueobject, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return collection;
	}

	public ProblemValueObject getProblemDetail(long l, String instance) throws ProblemJBException {
		ProblemValueObject problemvalueobject = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			problemvalueobject = problemmanagement.getProblemDetail(l, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return problemvalueobject;
	}

	public void addNote(EventValueObject evo) throws ProblemJBException {
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			ArrayList tasks = problemmanagement.sendNote(evo);
			problemmanagement.remove();
			executeTasks(tasks, evo);
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
	}

	public Collection getProblemEvents(String instance) throws ProblemJBException {
		Collection collection = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			collection = problemmanagement.getProblemEvents(idSegnalazione, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return collection;
	}

	public EventValueObject getProblemEventDetail(String instance) throws ProblemJBException {
		EventValueObject eventvalueobject = null;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			eventvalueobject = problemmanagement.getProblemEventDetail(idSegnalazione, dataEvento, instance);
			problemmanagement.remove();
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return eventvalueobject;
	}

	public String getFormatStatus(int i, String s) {
		String s1 = s;
		if (i == 1)
			s1 = "<font color=\"#FF0000\">" + s + "</font>";
		if (i == 2)
			s1 = "<font color=\"#FFCC00\">" + s + "</font>";
		if (i == 3)
			s1 = "<font color=\"#FF6600\">" + s + "</font>";
		if (i == 4)
			s1 = "<font color=\"#3366FF\">" + s + "</font>";
		if (i == 5)
			s1 = "<font color=\"#00CC00\">" + s + "</font>";
		if (i == 6)
			s1 = "<font color=\"#CC0099\">" + s + "</font>";
		if (i == 7)
			s1 = "<font color=\"#663300\">" + s + "</font>";
		if (i == 67)
			s1 = "<font color=\"#663300\">" + s + "</font>";
		if (i == 9)
			s1 = "<font color=\"#5B00B7\">" + s + "</font>";
		return s1;
	}

	public ArrayList allowedStates(int origine, int profilo, String instance) throws ProblemJBException {
		ArrayList as;
		try {
			as = StateMachine.getInstance(instance).allowedStates(origine, profilo);
		} catch (StateMachineJBException e) {
			throw new ProblemJBException(e.getMessage(), e.getUserMessage());
		}
		return as;
	}

	public long newProblem(ProblemValueObject pvo, String instance) throws ProblemJBException {
		long i = 0;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			ArrayList tasks = problemmanagement.newProblem(pvo, instance);
			problemmanagement.remove();
			i = pvo.getIdSegnalazione();
			EventValueObject evo = new EventValueObject();
			evo.setEventType(0);
			evo.setIdSegnalazione(i);
			evo.setOriginatoreEvento(pvo.getOriginatore());
			evo.setCategory(pvo.getCategoria());
			evo.setTitle(pvo.getTitolo());
			evo.setDescription(pvo.getDescrizione());
			evo.setCategoryDescription(pvo.getCategoriaDescrizione());
			evo.setInstance(instance);
			executeTasks(tasks, evo);
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return i;
	}

	public long newProblemFromExpert(ProblemValueObject pvo, String instance) throws ProblemJBException {
		long i = 0;
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			ArrayList tasks = problemmanagement.newProblemFromExpert(pvo, instance);
			problemmanagement.remove();
			i = pvo.getIdSegnalazione();
			EventValueObject evo = new EventValueObject();
			evo.setEventType(Event.PROBLEMA_DA_ESPERTO);
			evo.setIdSegnalazione(i);
			evo.setOriginatoreEvento(pvo.getEsperto());
			evo.setCategory(pvo.getCategoria());
			evo.setTitle(pvo.getTitolo());
			evo.setDescription(pvo.getDescrizione());
			evo.setCategoryDescription(pvo.getCategoriaDescrizione());
			evo.setInstance(instance);
			executeTasks(tasks, evo);
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return i;
	}

	/**
	 * <p>
	 * Richiama la funzione per il cambiamento dello stato sull'EJB ProblemManagementBean
	 * </p>
	 * 
	 * @param problem
	 *            l'identificativo del problema
	 * @param t
	 *            l'oggetto che rappresenta il tipo di transizione
	 * @throws ProblemJBException
	 * @throws ConditionException
	 */
	public void changeState(EventValueObject evo, TransitionKey tk, String instance) throws ProblemJBException, ConditionException {
		try {
			Transition transition = StateMachine.getInstance(instance).getTransition(tk);
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			problemmanagement.changeState(evo, transition);
			problemmanagement.remove();
			executeTasks(transition.getNormalTasks(), evo);
		} catch (RemoteException e) {
			if (e.getCause() instanceof ProblemEJBException) {
				ProblemEJBException pejb;
				pejb = (ProblemEJBException) e.getCause();
				// System.out.println("classe causa: "+pejb.getRootCause().getClass());
				// System.out.println("kiave: "+pejb.getMessageKey());
				// System.out.println("causa radice: "+pejb.getRootCause());
				if (pejb.getRootCause() instanceof ConditionException) {
					// ProblemJBException pjb = new ProblemJBException(pejb.getMessageKey(),pejb.getMessageArgs());
					// pjb.setRootCause(pejb);
					ConditionException ce = (ConditionException) pejb.getRootCause();
					;
					throw ce;
				}
			}
			throw new ProblemJBException(e.getCause().getMessage(), "");
		} catch (ProblemEJBException e) {
			throw new ProblemJBException(e.getMessage(), e.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
	}

	public void changeCategory(EventValueObject evo) throws ProblemJBException {
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			ArrayList tasks = problemmanagement.modifyCategory(evo);
			problemmanagement.remove();
			executeTasks(tasks, evo);
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
	}

	public void reAssignProblemToExpert(EventValueObject evo) throws ProblemJBException {
		try {
			ProblemManagementHome problemmanagementhome = lookupHome();
			ProblemManagement problemmanagement = (ProblemManagement) PortableRemoteObject.narrow(problemmanagementhome.create(), it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagement.class);
			ArrayList tasks = problemmanagement.changeProblemExpert(evo);
			problemmanagement.remove();
			executeTasks(tasks, evo);
		} catch (ProblemEJBException problemejbexception) {
			throw new ProblemJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new ProblemJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
	}

	public int countEventQBE(Collection conditions, String instance) throws ProblemJBException {
		int count = 0;
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			ProblemDAO problemdao = daoFactory.getProblemDAO();
			problemdao.createConnection(instance);
			count = problemdao.countEventQBE(conditions);
			problemdao.closeConnection();
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		}
		return count;
	}
}
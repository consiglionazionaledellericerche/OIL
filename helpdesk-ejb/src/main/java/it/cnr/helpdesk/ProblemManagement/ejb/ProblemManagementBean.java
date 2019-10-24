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
 * Created on 24-gen-2005
 *
 */
package it.cnr.helpdesk.ProblemManagement.ejb;

import it.cnr.helpdesk.EventManagement.exceptions.EventDAOException;
import it.cnr.helpdesk.EventManagement.javabeans.Event;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemDAOException;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemEJBException;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemCountValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.ConditionException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineJBException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.TaskException;
import it.cnr.helpdesk.StateMachineManagement.javabeans.Transition;
import it.cnr.helpdesk.StateMachineManagement.tasks.Task;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.EventDAO;
import it.cnr.helpdesk.dao.ProblemDAO;
import it.cnr.helpdesk.util.PageByPageIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> * <!-- lomboz.beginDefinition --> <?xml version="1.0" encoding="UTF-8"?> <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz"> <lomboz:session> <lomboz:sessionEjb> <j2ee:display-name>ProblemManagement</j2ee:display-name> <j2ee:ejb-name>ProblemManagement</j2ee:ejb-name>
 * <j2ee:ejb-class>it.cnr.helpdesk.ProblemManagement.ejb.ProblemManagementBean</j2ee:ejb-class> <j2ee:session-type>Stateless</j2ee:session-type> <j2ee:transaction-type>Container</j2ee:transaction-type> </lomboz:sessionEjb> </lomboz:session> </lomboz:EJB> <!-- lomboz.endDefinition -->
 * 
 * <!-- begin-xdoclet-definition -->
 * 
 * @ejb.bean name="ProblemManagement" jndi-name="comp/env/ejb/ProblemManagement" type="Stateless" transaction-type="Container"
 * 
 *           -- This is needed for JOnAS. If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="ProblemManagement" jndi-name="comp/env/ejb/ProblemManagement"
 * 
 *             -- <!-- end-xdoclet-definition -->
 * @generated
 */
public abstract class ProblemManagementBean implements javax.ejb.SessionBean {

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public PageByPageIterator getUserProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject, String instance) throws ProblemEJBException {
		PageByPageIterator pagebypageiterator1 = null;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		try {
			problemdao.createConnection(instance);
			pagebypageiterator1 = problemdao.getUserProblemQueue(pagebypageiterator, problemvalueobject);
			problemdao.closeConnection();
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		}
		return pagebypageiterator1;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public PageByPageIterator getExpertProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject, String instance) throws ProblemEJBException {
		PageByPageIterator pagebypageiterator1;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		try {
			problemdao.createConnection(instance);
			pagebypageiterator1 = problemdao.getExpertProblemQueue(pagebypageiterator, problemvalueobject);
			problemdao.closeConnection();
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		}
		return pagebypageiterator1;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public Collection getProblemCountPerCategory(ProblemCountValueObject problemcountvalueobject, String instance) throws ProblemEJBException {
		Collection collection;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		try {
			problemdao.createConnection(instance);
			collection = problemdao.getProblemCountPerCategory(problemcountvalueobject);
			problemdao.closeConnection();
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		}
		return collection;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public ProblemValueObject getProblemDetail(long l, String instance) throws ProblemEJBException {
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		ProblemValueObject problemvalueobject = null;
		try {
			problemdao.createConnection(instance);
			problemdao.setIdSegnalazione(l);
			problemvalueobject = problemdao.getProblemDetail();
			problemdao.closeConnection();
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		}
		return problemvalueobject;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public Collection getProblemEvents(long l, String instance) throws ProblemEJBException {
		Collection<EventValueObject> collection = null;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		try {
			problemdao.createConnection(instance);
			problemdao.setIdSegnalazione(l);
			collection = problemdao.getProblemEvents();
			problemdao.closeConnection();
			for (EventValueObject eventValueObject : collection) {
				eventValueObject.setInstance(instance);
			}
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		}
		return collection;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public EventValueObject getProblemEventDetail(long l, String s, String instance) throws ProblemEJBException {
		EventValueObject eventvalueobject = null;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		try {
			problemdao.createConnection(instance);
			problemdao.setIdSegnalazione(l);
			problemdao.setDataEvento(s);
			eventvalueobject = problemdao.getProblemEventDetail();
			eventvalueobject.setInstance(instance);
			problemdao.closeConnection();
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		}
		return eventvalueobject;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public Collection getAllExpertProblems(String esperto, String instance) throws ProblemEJBException {
		Collection problems = null;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		try {
			problemdao.createConnection(instance);
			problemdao.setEsperto(esperto);
			problems = problemdao.getAllExpertProblems();
			problemdao.closeConnection();
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		}
		return problems;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 * @ejb.transaction type="Required"
	 */
	public void changeState(EventValueObject evo, Transition transition) throws ProblemEJBException {
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		EventDAO eventdao = daoFactory.getEventDAO();
		try {
			// controllo delle precondizioni
			transition.checkPreConditions(evo);

			// scrittura del nuovo stato sulla tabella segnalazione
			problemdao.createConnection(evo.getInstance());
			problemdao.changeState(evo.getIdSegnalazione(), evo.getState());
			problemdao.closeConnection();

			// controllo delle postcondizioni
			transition.checkPostConditions(evo);

			// scrittura del log sulla tabella evento
			eventdao.createConnection(evo.getInstance());
			String time = eventdao.register(evo);
			eventdao.closeConnection();
			evo.setTime(time);

			// esecuzione dei task di tipo 'transaction_required'
			Iterator i = transition.getTransTasks().iterator();
			while (i.hasNext()) {
				((Task) i.next()).doAction(evo);
			}

		} catch (StateMachineJBException e) {
			e.printStackTrace();
			throw new ProblemEJBException(e.getMessage(), e.getUserMessage());
		} catch (ProblemDAOException problemdaoexception) {
			problemdaoexception.printStackTrace();
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		} catch (EventDAOException e) {
			e.printStackTrace();
			throw new ProblemEJBException(e.getMessage(), e.getUserMessage());
		} catch (TaskException e) {
			e.printStackTrace();
			throw new ProblemEJBException(e.getMessage(), e.getUserMessage());
		} catch (ConditionException e) {
			ProblemEJBException pejb;
			if (e.getMessageKey() != null) {
				pejb = new ProblemEJBException(e.getMessageKey(), e.getMessageArgs());
				pejb.setRootCause(e);
			} else
				pejb = new ProblemEJBException(e.getMessage(), e.getUserMessage());
			throw pejb;
		}
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 * @ejb.transaction type="Required"
	 **/
	public ArrayList sendNote(EventValueObject evo) throws ProblemEJBException {
		ArrayList tasks;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		EventDAO edao = daoFactory.getEventDAO();
		try {
			edao.createConnection(evo.getInstance());
			edao.register(evo);
			tasks = edao.getNormalTasks(evo.getEventType());
			Iterator i = edao.getTransTasks(evo.getEventType()).iterator();
			edao.closeConnection();
			while (i.hasNext()) {
				((Task) i.next()).doAction(evo);
			}
		} catch (EventDAOException eventdaoexception) {
			throw new ProblemEJBException(eventdaoexception.getMessage(), eventdaoexception.getUserMessage());
		} catch (TaskException e) {
			throw new ProblemEJBException(e.getMessage(), e.getUserMessage());
		}
		return tasks;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 * @ejb.transaction type="Required"
	 **/
	public ArrayList newProblem(ProblemValueObject pvo, String instance) throws ProblemEJBException {
		ArrayList tasks;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		try {

			ProblemDAO problemdao = daoFactory.getProblemDAO();
			EventDAO edao = daoFactory.getEventDAO();
			problemdao.createConnection(instance);
			long l = problemdao.executeInsert(pvo);
			problemdao.closeConnection();
			pvo.setIdSegnalazione(l);
			EventValueObject evo = new EventValueObject();
			evo.setEventType(0);
			evo.setIdSegnalazione(l);
			evo.setOriginatoreEvento(pvo.getOriginatore());
			evo.setCategory(pvo.getCategoria());
			evo.setTitle(pvo.getTitolo());
			evo.setDescription(pvo.getDescrizione());
			evo.setCategoryDescription(pvo.getCategoriaDescrizione());
			evo.setState(pvo.getStato());
			evo.setInstance(instance);
			edao.createConnection(instance);
			edao.register(evo);
			tasks = edao.getNormalTasks(evo.getEventType());
			Iterator i = edao.getTransTasks(evo.getEventType()).iterator();
			edao.closeConnection();
			while (i.hasNext()) {
				((Task) i.next()).doAction(evo);
			}

		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		} catch (EventDAOException eventdaoexception) {
			throw new ProblemEJBException(eventdaoexception.getMessage(), eventdaoexception.getUserMessage());
		} catch (TaskException e) {
			throw new ProblemEJBException(e.getMessage(), e.getUserMessage());
		}
		return tasks;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 * @ejb.transaction type="Required"
	 **/
	public ArrayList newProblemFromExpert(ProblemValueObject pvo, String instance) throws ProblemEJBException {
		ArrayList tasks;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		try {
			ProblemDAO problemdao = daoFactory.getProblemDAO();
			EventDAO edao = daoFactory.getEventDAO();
			problemdao.createConnection(instance);
			long l = problemdao.executeInsert(pvo);
			problemdao.closeConnection();
			pvo.setIdSegnalazione(l);
			EventValueObject evo = new EventValueObject();
			evo.setEventType(Event.PROBLEMA_DA_ESPERTO);
			evo.setIdSegnalazione(l);
			evo.setOriginatoreEvento(pvo.getEsperto());
			evo.setCategory(pvo.getCategoria());
			evo.setTitle(pvo.getTitolo());
			evo.setDescription(pvo.getDescrizione());
			evo.setCategoryDescription(pvo.getCategoriaDescrizione());
			evo.setState(pvo.getStato());
			evo.setInstance(instance);
			edao.createConnection(instance);
			edao.register(evo);
			tasks = edao.getNormalTasks(evo.getEventType());
			Iterator i = edao.getTransTasks(evo.getEventType()).iterator();
			edao.closeConnection();
			while (i.hasNext()) {
				((Task) i.next()).doAction(evo);
			}

		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		} catch (EventDAOException eventdaoexception) {
			throw new ProblemEJBException(eventdaoexception.getMessage(), eventdaoexception.getUserMessage());
		} catch (TaskException e) {
			throw new ProblemEJBException(e.getMessage(), e.getUserMessage());
		}
		return tasks;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 * @ejb.transaction type="Required"
	 **/
	public ArrayList modifyCategory(EventValueObject evo) throws ProblemEJBException {
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		EventDAO edao = daoFactory.getEventDAO();
		ArrayList tasks;
		try {
			problemdao.createConnection(evo.getInstance());
			problemdao.modifyCategory(evo);
			problemdao.closeConnection();
			edao.createConnection(evo.getInstance());
			edao.register(evo);
			tasks = edao.getNormalTasks(evo.getEventType());
			Iterator i = edao.getTransTasks(evo.getEventType()).iterator();
			edao.closeConnection();
			while (i.hasNext()) {
				((Task) i.next()).doAction(evo);
			}

		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		} catch (EventDAOException eventdaoexception) {
			throw new ProblemEJBException(eventdaoexception.getMessage(), eventdaoexception.getUserMessage());
		} catch (TaskException e) {
			throw new ProblemEJBException(e.getMessage(), e.getUserMessage());
		}
		return tasks;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public ArrayList changeProblemExpert(EventValueObject evo) throws ProblemEJBException {
		ArrayList tasks = null;
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		EventDAO edao = daoFactory.getEventDAO();
		try {
			problemdao.createConnection(evo.getInstance());
			problemdao.reAssignProblemToExpert(evo);
			problemdao.closeConnection();
			edao.createConnection(evo.getInstance());
			edao.register(evo);
			tasks = edao.getNormalTasks(evo.getEventType());
			Iterator i = edao.getTransTasks(evo.getEventType()).iterator();
			edao.closeConnection();
			while (i.hasNext()) {
				((Task) i.next()).doAction(evo);
			}
		} catch (ProblemDAOException problemdaoexception) {
			throw new ProblemEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
		} catch (EventDAOException eventdaoexception) {
			throw new ProblemEJBException(eventdaoexception.getMessage(), eventdaoexception.getUserMessage());
		} catch (TaskException e) {
			throw new ProblemEJBException(e.getMessage(), e.getUserMessage());
		}
		return tasks;
	}
}
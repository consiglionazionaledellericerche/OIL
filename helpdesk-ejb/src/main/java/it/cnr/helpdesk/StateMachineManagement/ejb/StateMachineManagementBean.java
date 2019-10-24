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
package it.cnr.helpdesk.StateMachineManagement.ejb;

import java.util.HashMap;
import java.util.Iterator;

import it.cnr.helpdesk.StateMachineManagement.StateMachine;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineDAOException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineEJBException;
import it.cnr.helpdesk.StateMachineManagement.javabeans.Transition;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.StateMachineDAO;
import javax.ejb.SessionBean;

/**
 * 
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> * <!--
 * lomboz.beginDefinition --> <?xml version="1.0" encoding="UTF-8"?> <lomboz:EJB
 * xmlns:j2ee="http://java.sun.com/xml/ns/j2ee"
 * xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz"> <lomboz:session>
 * <lomboz:sessionEjb> <j2ee:display-name>StateMachineManagement</j2ee:display-name>
 * <j2ee:ejb-name>StateMachineManagement</j2ee:ejb-name>
 * <j2ee:ejb-class>it.cnr.helpdesk.StateMachineManagement.ejb.StateMachineManagementBean</j2ee:ejb-class>
 * <j2ee:session-type>Stateless</j2ee:session-type>
 * <j2ee:transaction-type>Container</j2ee:transaction-type>
 * </lomboz:sessionEjb> </lomboz:session> </lomboz:EJB> <!--
 * lomboz.endDefinition -->
 * 
 * <!-- begin-xdoclet-definition -->
 * 
 * @ejb.bean name="StateMachineManagement" jndi-name="comp/env/ejb/StateMachineManagement"
 *           type="Stateless" transaction-type="Container"
 * 
 * <!-- end-xdoclet-definition -->
 * @generated
 */
public abstract class StateMachineManagementBean implements
		SessionBean {

	/**
	 * @ejb.interface-method view-type="remote"
	 */
	public void readConfiguration(StateMachine stateMachine, String instance) throws StateMachineEJBException {
		StateMachineDAO stateMachineDAO = DAOFactory.getDAOFactoryByProperties().getStateMachineDAO();
		try {
			stateMachineDAO.createConnection(instance);
			HashMap transitions = stateMachineDAO.readTransitions();
			Iterator i = transitions.values().iterator();
			while (i.hasNext()) {
				Transition t = (Transition) i.next();
				t.setNormalTasks(stateMachineDAO.readNormalTasks(t.getId(), t.getProfilo()));
				t.setTransTasks(stateMachineDAO.readTransTasks(t.getId(), t.getProfilo()));
				t.setPreConditions(stateMachineDAO.readPreConditions(t.getId()));
				t.setPostConditions(stateMachineDAO.readPostConditions(t.getId()));
			}
			stateMachine.setTransitions(transitions);
			stateMachine.setStates(stateMachineDAO.readStates());
			stateMachine.setProfiles(stateMachineDAO.readProfiles());
			stateMachineDAO.closeConnection();
		} catch (StateMachineDAOException stateMachineDAOException) {
			throw new StateMachineEJBException(stateMachineDAOException.getMessage(), stateMachineDAOException.getUserMessage());
		} catch (Exception exception) {
			throw new StateMachineEJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione (EJB level) " + "durante il caricamento della macchina a stati");
		}

	}
}

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
package it.cnr.helpdesk.EventManagement.ejb;

import it.cnr.helpdesk.EventManagement.exceptions.EventDAOException;
import it.cnr.helpdesk.EventManagement.exceptions.EventEJBException;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.EventDAO;
import java.util.Collection;
import javax.ejb.SessionBean;


/**
 *
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> *
 <!-- lomboz.beginDefinition -->
 <?xml version="1.0" encoding="UTF-8"?>
 <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz">
 <lomboz:session>
 <lomboz:sessionEjb>
 <j2ee:display-name>EventManagement</j2ee:display-name>
 <j2ee:ejb-name>EventManagement</j2ee:ejb-name>
 <j2ee:ejb-class>it.cnr.helpdesk.EventManagement.ejb.EventManagementBean</j2ee:ejb-class>
 <j2ee:session-type>Stateless</j2ee:session-type>
 <j2ee:transaction-type>Container</j2ee:transaction-type>
 </lomboz:sessionEjb>
 </lomboz:session>
 </lomboz:EJB>
 <!-- lomboz.endDefinition -->
 *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="EventManagement"	
 *           jndi-name="comp/env/ejb/EventManagement"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 * <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class EventManagementBean implements SessionBean {


/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Collection getProblemEvents(long l, boolean filter, String instance) throws EventEJBException { 
	Collection collection = null;
	DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
	EventDAO edao = daoFactory.getEventDAO();
	try {
		edao.createConnection(instance);
		collection = edao.getProblemEvents(l,filter);
		edao.closeConnection();
	} catch (EventDAOException edaoexception) {
		throw new EventEJBException(edaoexception.getMessage(), edaoexception.getUserMessage());
	}
	return collection;
}
}

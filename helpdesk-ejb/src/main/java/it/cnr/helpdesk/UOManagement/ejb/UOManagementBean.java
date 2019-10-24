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
 * Created on 30-ott-2008
 */
package it.cnr.helpdesk.UOManagement.ejb;

/**
 * @author Marco Trischitta
 * @author aldo stentella
 */

import javax.ejb.SessionBean;
import java.util.Collection;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.UODAO;
import it.cnr.helpdesk.UOManagement.exceptions.*;
import it.cnr.helpdesk.UOManagement.valueobjects.UOValueObject;

/**
 *
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> *
 <!-- lomboz.beginDefinition -->
 <?xml version="1.0" encoding="UTF-8"?>
 <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz">
 <lomboz:session>
 <lomboz:sessionEjb>
 <j2ee:display-name>UOManagement</j2ee:display-name>
 <j2ee:ejb-name>UOManagement</j2ee:ejb-name>
 <j2ee:ejb-class>it.cnr.helpdesk.UOManagement.ejb.UOManagementBean</j2ee:ejb-class>
 <j2ee:session-type>Stateless</j2ee:session-type>
 <j2ee:transaction-type>Container</j2ee:transaction-type>
 </lomboz:sessionEjb>
 </lomboz:session>
 </lomboz:EJB>
 <!-- lomboz.endDefinition -->
 *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="UOManagement"	
 *           jndi-name="comp/env/ejb/UOManagement"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="UOManagement" 
 *             jndi-name="comp/env/ejb/UOManagement"
 * 
 *--
 * <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class UOManagementBean implements SessionBean {
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public String executeInsert(UOValueObject uov, String instance) {
		UODAO uod = DAOFactory.getDAOFactoryByProperties().getUODAO();
		String cod = "";
		try {
			uod.createConnection(instance);
			cod = uod.executeInsert(uov);
			uod.closeConnection();
		} 
		catch (UODAOException e) {
			throw new UOEJBException(e.getMessage(), e.getUserMessage());
		}
		
		return cod;
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public void executeUpdate(UOValueObject uov, String instance) {
		UODAO uod = DAOFactory.getDAOFactoryByProperties().getUODAO();
		try {
			uod.createConnection(instance);
			uod.executeUpdate(uov);
			uod.closeConnection();
		} 
		catch (UODAOException e) {
			throw new UOEJBException(e.getMessage(), e.getUserMessage());
		}
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public void executeDelete(UOValueObject uov, String instance) {
		UODAO uod = DAOFactory.getDAOFactoryByProperties().getUODAO();
		try {
			uod.createConnection(instance);
			uod.executeDelete(uov);
			uod.closeConnection();
		} 
		catch (UODAOException e) {
			throw new UOEJBException(e.getMessage(), e.getUserMessage());
		}
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public Collection getStructures(String instance) {
		UODAO uod = DAOFactory.getDAOFactoryByProperties().getUODAO();
		Collection coll = null;
		try {
			uod.createConnection(instance);
			coll = uod.getStructures();
			uod.closeConnection();
		} 
		catch (UODAOException e) {
			throw new UOEJBException(e.getMessage(), e.getUserMessage());
		}
		return coll;
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public Collection getAllStructures(String instance) {
		UODAO uod = DAOFactory.getDAOFactoryByProperties().getUODAO();
		Collection coll = null;
		try {
			uod.createConnection(instance);
			coll = uod.getAllStructures();
			uod.closeConnection();
		} 
		catch (UODAOException e) {
			throw new UOEJBException(e.getMessage(), e.getUserMessage());
		}
		return coll;
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public UOValueObject getOneStructure(String cod, String instance) {
		UODAO uod = DAOFactory.getDAOFactoryByProperties().getUODAO();
		UOValueObject uvo = new UOValueObject();
		try {
			uod.createConnection(instance);
			uvo = uod.getOneStructure(cod);
			uod.closeConnection();
		} 
		catch (UODAOException e) {
			throw new UOEJBException(e.getMessage(), e.getUserMessage());
		}
		return uvo;
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public void enabled(String cod, String instance) {
		UODAO uod = DAOFactory.getDAOFactoryByProperties().getUODAO();
		try {
			uod.createConnection(instance);
			uod.enabled(cod);
			uod.closeConnection();
		} 
		catch (UODAOException e) {
			throw new UOEJBException(e.getMessage(), e.getUserMessage());
		}
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public void disabled(String cod, String instance) {
		UODAO uod = DAOFactory.getDAOFactoryByProperties().getUODAO();
		try {
			uod.createConnection(instance);
			uod.disabled(cod);
			uod.closeConnection();
		} 
		catch (UODAOException e) {
			throw new UOEJBException(e.getMessage(), e.getUserMessage());
		}
	}
}

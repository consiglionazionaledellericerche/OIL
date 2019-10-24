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
package it.cnr.helpdesk.FaqManagement.ejb;

import java.util.Collection;

import it.cnr.helpdesk.FaqManagement.dao.OracleFaqDAO;
import it.cnr.helpdesk.FaqManagement.dto.FaqDTO;
import it.cnr.helpdesk.FaqManagement.exceptions.FaqDAOException;
import it.cnr.helpdesk.FaqManagement.exceptions.FaqEJBException;
import it.cnr.helpdesk.dao.FaqDAO;
import it.cnr.helpdesk.dao.DAOFactory;

/**
 *
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> *
 <!-- lomboz.beginDefinition -->
 <?xml version="1.0" encoding="UTF-8"?>
 <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz">
 <lomboz:session>
 <lomboz:sessionEjb>
 <j2ee:display-name>FaqManagement</j2ee:display-name>
 <j2ee:ejb-name>FaqManagement</j2ee:ejb-name>
 <j2ee:ejb-class>it.cnr.helpdesk.FaqManagement.ejb.FaqManagementBean</j2ee:ejb-class>
 <j2ee:session-type>Stateless</j2ee:session-type>
 <j2ee:transaction-type>Container</j2ee:transaction-type>
 </lomboz:sessionEjb>
 </lomboz:session>
 </lomboz:EJB>
 <!-- lomboz.endDefinition -->
 *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="FaqManagement"	
 *           jndi-name="comp/env/ejb/FaqManagement"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="FaqManagement" 
 *             jndi-name="comp/env/ejb/FaqManagement"
 * 
 *--
 * <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class FaqManagementBean implements javax.ejb.SessionBean {

/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
	public FaqDTO getFaqDetail(int i, String instance) throws FaqEJBException {
		FaqDTO faqdto;
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			FaqDAO faqdao = daoFactory.getFaqDAO();
			faqdao.createConnection(instance);
			faqdao.setIdFaq(i);
			faqdto = faqdao.getFaqDetail();
			faqdao.closeConnection();
		} catch (FaqDAOException faqdaoexception) {
			throw new FaqEJBException(faqdaoexception.getMessage(), faqdaoexception.getUserMessage());
		}
		return faqdto;
	}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
	public Collection getFaqList(FaqDTO faqdto, String instance) throws FaqEJBException {
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		FaqDAO faqdao = daoFactory.getFaqDAO();
		Collection collection;
		try {
			faqdao.createConnection(instance);
			collection = faqdao.getFaqList(faqdto, instance);
			faqdao.closeConnection();
		} catch (FaqDAOException faqdaoexception) {
			throw new FaqEJBException(faqdaoexception.getMessage(), faqdaoexception.getUserMessage());
		}
		return collection;
	}
/**
 * @ejb.interface-method
 *	view-type="remote" 
 * @ejb.transaction
 *  type="Required"
**/
	public void insertFaq(FaqDTO faqdto, String instance) throws FaqEJBException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			FaqDAO faqdao = daoFactory.getFaqDAO();
			faqdao.createConnection(instance);
			faqdao.setDescrizione(faqdto.getDescrizione());
			faqdao.setTitolo(faqdto.getTitolo());
			faqdao.setIdCategoria(faqdto.getIdCategoria());
			faqdao.setOriginatore(faqdto.getOriginatore());
			faqdao.executeInsert();
			faqdao.closeConnection();
		} catch (FaqDAOException faqdaoexception) {
			throw new FaqEJBException(faqdaoexception.getMessage(), faqdaoexception.getUserMessage());
		} catch (Exception exception) {
			throw new FaqEJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione.\n Contattare l'assistenza allegando il testo della presente pagina.");
		}
	}


/**
 * @ejb.interface-method
 *	view-type="remote" 
 * @ejb.transaction
 *  type="Required"
**/
	public void updateFaq(FaqDTO faqdto, String instance) throws FaqEJBException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			FaqDAO faqdao = daoFactory.getFaqDAO();
			faqdao.createConnection(instance);
			faqdao.setIdFaq(faqdto.getIdFaq());
			faqdao.setTitolo(faqdto.getTitolo());
			faqdao.setDescrizione(faqdto.getDescrizione());
			faqdao.setIdCategoria(faqdto.getIdCategoria());
			faqdao.setOriginatore(faqdto.getOriginatore());
			faqdao.executeUpdate();
			faqdao.closeConnection();
		} catch (FaqDAOException faqdaoexception) {
			throw new FaqEJBException(faqdaoexception.getMessage(), faqdaoexception.getUserMessage());
		}
	}
/**
 * @ejb.interface-method
 *	view-type="remote" 
 * @ejb.transaction
 *  type="Required"
**/
	public void removeFaq(int i, String instance) throws FaqEJBException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			FaqDAO faqdao = daoFactory.getFaqDAO();
			faqdao.createConnection(instance);
			faqdao.setIdFaq(i);
			faqdao.executeDelete();
			faqdao.closeConnection();
		} catch (FaqDAOException faqdaoexception) {
			throw new FaqEJBException(faqdaoexception.getMessage(), faqdaoexception.getUserMessage());
		}
	}
}

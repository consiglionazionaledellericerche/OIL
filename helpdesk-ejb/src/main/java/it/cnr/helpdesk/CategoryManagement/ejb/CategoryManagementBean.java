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
package it.cnr.helpdesk.CategoryManagement.ejb;

import java.util.Collection;
import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryDAOException;
import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryEJBException;
import it.cnr.helpdesk.CategoryManagement.valueobjects.CategoryValueObject;
import it.cnr.helpdesk.dao.CategoryDAO;
import it.cnr.helpdesk.dao.DAOFactory;


/**
 *
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> *
 <!-- lomboz.beginDefinition -->
 <?xml version="1.0" encoding="UTF-8"?>
 <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz">
 <lomboz:session>
 <lomboz:sessionEjb>
 <j2ee:display-name>CategoryManagement</j2ee:display-name>
 <j2ee:ejb-name>CategoryManagement</j2ee:ejb-name>
 <j2ee:ejb-class>it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagementBean</j2ee:ejb-class>
 <j2ee:session-type>Stateless</j2ee:session-type>
 <j2ee:transaction-type>Container</j2ee:transaction-type>
 </lomboz:sessionEjb>
 </lomboz:session>
 </lomboz:EJB>
 <!-- lomboz.endDefinition -->
 *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="CategoryManagement"	
 *           jndi-name="comp/env/ejb/CategoryManagement"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="CategoryManagement" 
 *             jndi-name="comp/env/ejb/CategoryManagement"
 * 
 *--
 * <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class CategoryManagementBean implements javax.ejb.SessionBean {

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public CategoryValueObject allCategories(String instance) throws CategoryEJBException {

		CategoryValueObject categoryvalueobject;
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			CategoryDAO categorydao = daoFactory.getCategoryDAO();
			categorydao.createConnection(instance);
			categoryvalueobject = categorydao.allCategories();
			categorydao.closeConnection();
		} catch (CategoryDAOException categorydaoexception) {
			throw new CategoryEJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
		}
		return categoryvalueobject;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public Collection allCategoriesList(String instance) throws CategoryEJBException {
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		CategoryDAO categorydao = daoFactory.getCategoryDAO();
		Collection collection;
		try {
			categorydao.createConnection(instance);
			collection = categorydao.allCategoriesList();
			categorydao.closeConnection();
		} catch (CategoryDAOException categorydaoexception) {
			throw new CategoryEJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
		}
		return collection;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public Collection allExpertCategoriesList(String s, String instance) throws CategoryEJBException {
		DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
		CategoryDAO categorydao = daoFactory.getCategoryDAO();
		Collection collection;
		try {
			categorydao.createConnection(instance);
			collection = categorydao.allExpertCategoriesList(s);
			categorydao.closeConnection();
		} catch (CategoryDAOException categorydaoexception) {
			throw new CategoryEJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
		}
		return collection;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 * @ejb.transaction type="Required"
	 **/
	public void assignCategoryToExpert(int i, String s, String instance) throws CategoryEJBException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			CategoryDAO categorydao = daoFactory.getCategoryDAO();
			categorydao.createConnection(instance);
			categorydao.setIdCategoria(i);
			categorydao.assignToExpert(s);
			categorydao.closeConnection();
		} catch (CategoryDAOException categorydaoexception) {
			throw new CategoryEJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
		}
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 * @ejb.transaction type="Required"
	 **/
	public void removeCategoryFromExpert(int i, String s, String instance) throws CategoryEJBException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			CategoryDAO categorydao = daoFactory.getCategoryDAO();
			categorydao.createConnection(instance);
			categorydao.setIdCategoria(i);
			categorydao.removeFromExpert(s);
			categorydao.closeConnection();
		} catch (CategoryDAOException categorydaoexception) {
			throw new CategoryEJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
		}
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 * @ejb.transaction type="Required"
	 **/
	public int insertCategory(CategoryValueObject categoryvalueobject, String instance) throws CategoryEJBException {
		int i = 0;
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			CategoryDAO categorydao = daoFactory.getCategoryDAO();
			categorydao.createConnection(instance);
			categorydao.setDescrizione(categoryvalueobject.getDescrizione());
			categorydao.setNome(categoryvalueobject.getNome());
			categorydao.setIdPadre(categoryvalueobject.getIdPadre());
			i = categorydao.executeInsert();
			categorydao.closeConnection();
		} catch (CategoryDAOException categorydaoexception) {
			throw new CategoryEJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
		} catch (Exception exception) {
			throw new CategoryEJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione.\n Contattare l'assistenza allegando il testo della presente pagina.");
		}
		return i;
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public void disable(int id, String instance) throws CategoryEJBException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			CategoryDAO categorydao = daoFactory.getCategoryDAO();
			categorydao.createConnection(instance);
			categorydao.setIdCategoria(id);
			categorydao.disable();
			categorydao.closeConnection();
		} catch (CategoryDAOException categorydaoexception) {
			throw new CategoryEJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
		}
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public void enable(int id, String instance) throws CategoryEJBException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			CategoryDAO categorydao = daoFactory.getCategoryDAO();
			categorydao.createConnection(instance);
			categorydao.setIdCategoria(id);
			categorydao.enable();
			categorydao.closeConnection();
		} catch (CategoryDAOException categorydaoexception) {
			throw new CategoryEJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
		}
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public void updateCategory(CategoryValueObject categoryvalueobject, String instance) throws CategoryEJBException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactoryByProperties();
			CategoryDAO categorydao = daoFactory.getCategoryDAO();
			categorydao.createConnection(instance);
			categorydao.setIdCategoria(categoryvalueobject.getId());
			categorydao.setDescrizione(categoryvalueobject.getDescrizione());
			categorydao.setNome(categoryvalueobject.getNome());
			categorydao.executeUpdate();
			categorydao.closeConnection();
		} catch (CategoryDAOException categorydaoexception) {
			throw new CategoryEJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
		} catch (Exception exception) {
			throw new CategoryEJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione.\n Contattare l'assistenza allegando il testo della presente pagina.");
		}
	}
}

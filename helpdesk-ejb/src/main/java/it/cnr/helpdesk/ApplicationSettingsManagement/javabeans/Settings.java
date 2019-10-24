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

 

package it.cnr.helpdesk.ApplicationSettingsManagement.javabeans;

import it.cnr.helpdesk.dao.CategoryDAO;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryDAOException;
import it.cnr.helpdesk.CategoryManagement.valueobjects.CategoryValueObject;
import it.cnr.helpdesk.dao.ApplicationSettingsDAO;
import it.cnr.helpdesk.exceptions.*;
import it.cnr.helpdesk.javabeans.HelpDeskJB;

import java.util.*;


public class Settings extends HelpDeskJB {


	public static Vector<String> getId2DescriptionStateMapping(String instance) throws SettingsJBException {
		Vector<String> id2DescriptionStateMapping;
		ApplicationSettingsDAO applicationsettingsdao = DAOFactory.getDAOFactoryByProperties().getApplicationSettingsDAO();
		try {
			applicationsettingsdao.createConnection(instance);
			id2DescriptionStateMapping = applicationsettingsdao.getId2DescriptionStateMapping();
			applicationsettingsdao.closeConnection();
		} catch(ApplicationSettingsDAOException applicationsettingsdaoexception)  {
			throw new SettingsJBException(applicationsettingsdaoexception.getMessage(), applicationsettingsdaoexception.getUserMessage());
		}
		return id2DescriptionStateMapping;
	}

	public static Vector<String> getId2DescriptionPriorityMapping(String instance) throws SettingsJBException {
		Vector<String> id2DescriptionPriorityMapping;
		ApplicationSettingsDAO applicationsettingsdao = DAOFactory.getDAOFactoryByProperties().getApplicationSettingsDAO();
		try {
			applicationsettingsdao.createConnection(instance);
			id2DescriptionPriorityMapping = applicationsettingsdao.getId2DescriptionPriorityMapping();
			applicationsettingsdao.closeConnection();
		} catch(ApplicationSettingsDAOException applicationsettingsdaoexception) {
			throw new SettingsJBException(applicationsettingsdaoexception.getMessage(), applicationsettingsdaoexception.getUserMessage());
		}
		return id2DescriptionPriorityMapping;
	}

	public static String getDefaultCategory(String instance) throws SettingsJBException {
		String defaultCategory;
		CategoryDAO categorydao = DAOFactory.getDAOFactoryByProperties().getCategoryDAO();
		try {
			categorydao.createConnection(instance);
			CategoryValueObject categoryvalueobject = categorydao.getCategoryDetail(1);
			defaultCategory = categoryvalueobject.getId() + ":" + categoryvalueobject.getDescrizione();
			categorydao.closeConnection();
		}
		catch(CategoryDAOException categorydaoexception) {
			throw new SettingsJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
		}
		return defaultCategory;
	}

    public static String getProperty(String key, String instance) throws SettingsJBException {
    	ApplicationSettingsDAO applicationsettingsdao = DAOFactory.getDAOFactoryByProperties().getApplicationSettingsDAO();
    	String k = null;
    	try {
    		applicationsettingsdao.createConnection(instance);
    		k = applicationsettingsdao.getProperty(key);
    		applicationsettingsdao.closeConnection();
    	} catch(ApplicationSettingsDAOException applicationsettingsdaoexception) {
    		throw new SettingsJBException(applicationsettingsdaoexception.getMessage(), applicationsettingsdaoexception.getUserMessage());
    	}
    	return k;
    }

    public static void removeProperty(String key, String instance) throws SettingsJBException {
    	ApplicationSettingsDAO applicationsettingsdao = DAOFactory.getDAOFactoryByProperties().getApplicationSettingsDAO();
    	try {
    		applicationsettingsdao.createConnection(instance);
    		applicationsettingsdao.removeProperty(key);
    		applicationsettingsdao.closeConnection();
    	} catch(ApplicationSettingsDAOException applicationsettingsdaoexception) {
    		throw new SettingsJBException(applicationsettingsdaoexception.getMessage(), applicationsettingsdaoexception.getUserMessage());
    	}
    }
    
    public static void setProperty(String key, String value, String instance) throws SettingsJBException {
    	ApplicationSettingsDAO applicationsettingsdao = DAOFactory.getDAOFactoryByProperties().getApplicationSettingsDAO();
    	try {
    		applicationsettingsdao.createConnection(instance);
    		applicationsettingsdao.setProperty(key, value);
    		applicationsettingsdao.closeConnection();
    	} catch(ApplicationSettingsDAOException applicationsettingsdaoexception) {
    		throw new SettingsJBException(applicationsettingsdaoexception.getMessage(), applicationsettingsdaoexception.getUserMessage());
    	}
    }
    
    public static HashMap<String, String> getAllProperties(String instance) throws SettingsJBException {
    	ApplicationSettingsDAO applicationsettingsdao = DAOFactory.getDAOFactoryByProperties().getApplicationSettingsDAO();
    	HashMap<String, String> k = new HashMap<String, String>();
    	try {
    		applicationsettingsdao.createConnection(instance);
    		k = applicationsettingsdao.getAllProperties();
    		applicationsettingsdao.closeConnection();
    	} catch(ApplicationSettingsDAOException applicationsettingsdaoexception) {
    		throw new SettingsJBException(applicationsettingsdaoexception.getMessage(), applicationsettingsdaoexception.getUserMessage());
    	}
    	return k;
    }

}
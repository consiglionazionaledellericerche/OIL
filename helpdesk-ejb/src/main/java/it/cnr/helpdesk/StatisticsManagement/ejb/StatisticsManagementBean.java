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
package it.cnr.helpdesk.StatisticsManagement.ejb;

import it.cnr.helpdesk.dao.StatisticsDAO;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException;
import it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsEJBException;
import it.cnr.helpdesk.StatisticsManagement.valueobjects.OverallStatisticsDTO;
import it.cnr.helpdesk.StatisticsManagement.valueobjects.StatisticsDTO;

import java.util.Collection;


/**
 *
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> *
 <!-- lomboz.beginDefinition -->
 <?xml version="1.0" encoding="UTF-8"?>
 <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz">
 <lomboz:session>
 <lomboz:sessionEjb>
 <j2ee:display-name>StatisticsManagement</j2ee:display-name>
 <j2ee:ejb-name>StatisticsManagement</j2ee:ejb-name>
 <j2ee:ejb-class>it.cnr.helpdesk.StatisticsManagement.ejb.StatisticsManagementBean</j2ee:ejb-class>
 <j2ee:session-type>Stateless</j2ee:session-type>
 <j2ee:transaction-type>Container</j2ee:transaction-type>
 </lomboz:sessionEjb>
 </lomboz:session>
 </lomboz:EJB>
 <!-- lomboz.endDefinition -->
 *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="StatisticsManagement"	
 *           jndi-name="comp/env/ejb/StatisticsManagement"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="StatisticsManagement" 
 *             jndi-name="comp/env/ejb/StatisticsManagement"
 * 
 *--
 * <!-- end-xdoclet-definition --> 
 * @generated
 */


public abstract class StatisticsManagementBean implements javax.ejb.SessionBean {
	
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
    public Collection getProblemsDistributionByStatus(StatisticsDTO statisticsdto, String instance)
    throws StatisticsEJBException
{
    Collection collection = null;
    StatisticsDAO statisticsdao = DAOFactory.getDAOFactoryByProperties().getStatisticsDAO();
    try
    {
        statisticsdao.setHelpdeskUserID(statisticsdto.getHelpdeskUserID());
        statisticsdao.setHelpdeskUserProfile(statisticsdto.getHelpdeskUserProfile());
        statisticsdao.setStartDate(statisticsdto.getStartDate());
        statisticsdao.setEndDate(statisticsdto.getEndDate());
        statisticsdao.setStatisticsType(statisticsdto.getStatisticsType());
        statisticsdao.createConnection(instance);
        collection = statisticsdao.getProblemsDistributionByStatus();
        statisticsdao.closeConnection();
    }
    catch(StatisticsDAOException statisticsdaoexception)
    {
        throw new StatisticsEJBException(statisticsdaoexception.getMessage(), statisticsdaoexception.getUserMessage());
    }
    catch(Exception exception)
    {
        throw new StatisticsEJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione (EJB level) durante il calcolo della distribuzione dei problemi per stato di lavorazione");
    }
    return collection;
}

    
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public OverallStatisticsDTO getOverallStatistics(StatisticsDTO statisticsdto, String instance)
    throws StatisticsEJBException
{
    OverallStatisticsDTO overallstatisticsdto = null;
    StatisticsDAO statisticsdao = DAOFactory.getDAOFactoryByProperties().getStatisticsDAO();
    try
    {
        statisticsdao.setHelpdeskUserID(statisticsdto.getHelpdeskUserID());
        statisticsdao.setHelpdeskUserProfile(statisticsdto.getHelpdeskUserProfile());
        statisticsdao.setStartDate(statisticsdto.getStartDate());
        statisticsdao.setEndDate(statisticsdto.getEndDate());
        statisticsdao.setStatisticsType(statisticsdto.getStatisticsType());
        statisticsdao.createConnection(instance);
        overallstatisticsdto = statisticsdao.getOverallStatistics();
        statisticsdao.closeConnection();
    }
    catch(StatisticsDAOException statisticsdaoexception)
    {
        throw new StatisticsEJBException(statisticsdaoexception.getMessage(), statisticsdaoexception.getUserMessage());
    }
    catch(Exception exception)
    {
        throw new StatisticsEJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione (EJB Level) durante la produzione delle statistiche relative all'uso del servizio di helpdesk. Contattare ");
    }
    return overallstatisticsdto;
}


/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Collection getProblemsDistributionByCategory(StatisticsDTO statisticsdto, String instance)
    throws StatisticsEJBException
{
    Collection collection = null;
    StatisticsDAO statisticsdao = DAOFactory.getDAOFactoryByProperties().getStatisticsDAO();
    try
    {
        statisticsdao.setHelpdeskUserID(statisticsdto.getHelpdeskUserID());
        statisticsdao.setHelpdeskUserProfile(statisticsdto.getHelpdeskUserProfile());
        statisticsdao.setStatisticsType(statisticsdto.getStatisticsType());
        statisticsdao.createConnection(instance);
        collection = statisticsdao.getProblemsDistributionByCategory();
        statisticsdao.closeConnection();
    }
    catch(StatisticsDAOException statisticsdaoexception)
    {
        throw new StatisticsEJBException(statisticsdaoexception.getMessage(), statisticsdaoexception.getUserMessage());
    }
    catch(Exception exception)
    {
        throw new StatisticsEJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione (EJB level) durante il calcolo della distribuzione dei problemi per categoria");
    }
    return collection;
}

}

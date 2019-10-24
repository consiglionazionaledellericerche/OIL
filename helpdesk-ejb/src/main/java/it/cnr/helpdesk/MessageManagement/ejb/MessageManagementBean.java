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
 * Created on 3-apr-2006
 *


 */
package it.cnr.helpdesk.MessageManagement.ejb;

import javax.ejb.SessionBean;
import it.cnr.helpdesk.MessageManagement.exceptions.MessageEJBException;
import it.cnr.helpdesk.MessageManagement.valueobjects.MessageValueObject;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.MessageDAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;
import it.cnr.helpdesk.exceptions.HelpDeskEJBException;
/**
 *
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> *
 <!-- lomboz.beginDefinition -->
 <?xml version="1.0" encoding="UTF-8"?>
 <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz">
 <lomboz:session>
 <lomboz:sessionEjb>
 <j2ee:display-name>MessageManagement</j2ee:display-name>
 <j2ee:ejb-name>MessageManagement</j2ee:ejb-name>
 <j2ee:ejb-class>it.cnr.helpdesk.MessageManagement.ejb.MessageManagementBean</j2ee:ejb-class>
 <j2ee:session-type>Stateless</j2ee:session-type>
 <j2ee:transaction-type>Container</j2ee:transaction-type>
 </lomboz:sessionEjb>
 </lomboz:session>
 </lomboz:EJB>
 <!-- lomboz.endDefinition -->
 *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="MessageManagement"	
 *           jndi-name="comp/env/ejb/MessageManagement"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="MessageManagement" 
 *             jndi-name="comp/env/ejb/MessageManagement"
 * 
 *--
 * <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class MessageManagementBean implements SessionBean {

/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public int executeInsert(MessageValueObject mvo, String instance) throws MessageEJBException{
	MessageDAO md = DAOFactory.getDAOFactoryByProperties().getMessageDAO();
    int cod=0;
    try
    {
        md.createConnection(instance);
        cod = md.executeInsert(mvo);
        md.closeConnection();
    }
    catch(HelpDeskDAOException e)
    {
        throw new HelpDeskEJBException(e.getMessage(), e.getUserMessage());
    }
 return cod; 
}
}

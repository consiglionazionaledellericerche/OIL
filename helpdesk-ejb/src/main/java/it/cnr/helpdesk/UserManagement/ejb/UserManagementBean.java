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
package it.cnr.helpdesk.UserManagement.ejb;

import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.ConditionException;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.TransitionKey;
import it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
import it.cnr.helpdesk.UserManagement.exceptions.UserEJBException;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.UserDAO;
import it.cnr.helpdesk.util.PageByPageIterator;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> *
 <!-- lomboz.beginDefinition -->
 <?xml version="1.0" encoding="UTF-8"?>
 <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz">
 <lomboz:session>
 <lomboz:sessionEjb>
 <j2ee:display-name>UserManagement</j2ee:display-name>
 <j2ee:ejb-name>UserManagement</j2ee:ejb-name>
 <j2ee:ejb-class>it.cnr.helpdesk.UserManagement.ejb.UserManagementBean</j2ee:ejb-class>
 <j2ee:session-type>Stateless</j2ee:session-type>
 <j2ee:transaction-type>Container</j2ee:transaction-type>
 </lomboz:sessionEjb>
 </lomboz:session>
 </lomboz:EJB>
 <!-- lomboz.endDefinition -->
 *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="UserManagement"	
 *           jndi-name="comp/env/ejb/UserManagement"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="UserManagement" 
 *             jndi-name="comp/env/ejb/UserManagement"
 * 
 *--
 * <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class UserManagementBean implements javax.ejb.SessionBean {
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	**/
    public void insertUser(UserValueObject uservalueobject, String instance)
    throws UserEJBException{
    try{
        UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        userdao.createConnection(instance);
        userdao.setLogin(uservalueobject.getLogin());
        userdao.setFirstName(uservalueobject.getFirstName());
        userdao.setFamilyName(uservalueobject.getFamilyName());
        userdao.setPassword(uservalueobject.getPassword());
        userdao.setProfile(uservalueobject.getProfile());
        userdao.setEmail(uservalueobject.getEmail());
        userdao.setStruttura(uservalueobject.getStruttura());
        userdao.setTelefono(uservalueobject.getTelefono());
        userdao.setMailStop(uservalueobject.getMailStop());
        userdao.executeInsert();
        userdao.closeConnection();
    } catch(UserDAOException userdaoexception) {
        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    }
}

/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public PageByPageIterator allUsers(PageByPageIterator pagebypageiterator, UserValueObject uservalueobject, String instance)
    throws UserEJBException
{
    PageByPageIterator pagebypageiterator1 = null;
    UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
    try
    {
        userdao.createConnection(instance);
        pagebypageiterator1 = userdao.allUsers(pagebypageiterator, uservalueobject);
        userdao.closeConnection();
    }
    catch(UserDAOException userdaoexception)
    {
        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    }
    return pagebypageiterator1;
}


/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public UserValueObject isRegistered(UserValueObject uservalueobject, String instance)
    throws UserEJBException
{
    UserValueObject uservalueobject1 = null;
    UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
    try
    {
        userdao.createConnection(instance);
        userdao.setLogin(uservalueobject.getLogin());
        userdao.setPassword(uservalueobject.getPassword());
        uservalueobject1 = userdao.isRegistered();
        userdao.closeConnection();
    }
    catch(UserDAOException userdaoexception)
    {
        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    }
    return uservalueobject1;
}


/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public UserValueObject getUserDetail(String s, String instance)
    throws UserEJBException
{
	UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
    UserValueObject uservalueobject;
    try
    {
        userdao.createConnection(instance);
        userdao.setLogin(s);
        uservalueobject = userdao.getUserDetail();
        userdao.closeConnection();
    }
    catch(UserDAOException userdaoexception)
    {
        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    }
    return uservalueobject;
}


/**
 * @ejb.interface-method
 *	view-type="remote" 
 * @ejb.transaction
 *  type="Required"
**/
public void updateUser(UserValueObject uservalueobject, String instance)
    throws UserEJBException
{
    try
    {
    	UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        userdao.createConnection(instance);
        userdao.setLogin(uservalueobject.getLogin());
        userdao.setFirstName(uservalueobject.getFirstName());
        userdao.setFamilyName(uservalueobject.getFamilyName());
        userdao.setEmail(uservalueobject.getEmail());
        userdao.setTelefono(uservalueobject.getTelefono());
        userdao.setStruttura(uservalueobject.getStruttura());
        userdao.setMailStop(uservalueobject.getMailStop());
        userdao.executeUpdate();
        userdao.closeConnection();
    }
    catch(UserDAOException userdaoexception)
    {
        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    }
}


/**
 * @ejb.interface-method
 *	view-type="remote" 
 * @ejb.transaction
 *  type="Required"
**/
public void changeUserPassword(UserValueObject uservalueobject, String instance)
    throws UserEJBException
{
    try
    {
    	UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        userdao.createConnection(instance);
        userdao.setLogin(uservalueobject.getLogin());
        userdao.setPassword(uservalueobject.getPassword());
        userdao.changeUserPassword();
        userdao.closeConnection();
    }
    catch(UserDAOException userdaoexception)
    {
        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    }
}


/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public String getUserPassword(String s, String instance)
    throws UserEJBException
{
    String s1 = null;
    try
    {
    	UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        userdao.createConnection(instance);
        userdao.setLogin(s);
        s1 = userdao.getUserPassword();
        userdao.closeConnection();
    }
    catch(UserDAOException userdaoexception)
    {
        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    }
    return s1;
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
 * @ejb.transaction
 *  type="Required"
**/
public void deleteUser(String login, String instance) throws UserEJBException{ 
	
	    try
	    {
	    	UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
	        userdao.setLogin(login);
	        userdao.createConnection(instance);
	        userdao.executeDelete();
	        userdao.closeConnection();
	    }
	    catch(UserDAOException userdaoexception)
	    {
	        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
	    }
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
 * @ejb.transaction
 *  type="Required"
**/
public void disable(String login, int profile, String admin, String instance) throws UserEJBException{ 
    try
    {
    	UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        userdao.setLogin(login);
        userdao.setProfile(profile);
        userdao.createConnection(instance);
        userdao.disable();
        userdao.closeConnection();
    }
    catch(UserDAOException userdaoexception)
    {
        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    }
/*    
    if (profile==2){
    	Problem problem = new Problem();
    	problem.setEsperto(login);
    	Collection problems=null;
    	try {
    		problems=problem.getAllExpertProblems();
    		Iterator problemsIterator=problems.iterator();
    		while (problemsIterator.hasNext()) {
    			problem.setIdSegnalazione(((Integer)problemsIterator.next()).intValue());
    			problem.setOriginatoreEvento(admin);
    			problem.setStatoDescrizione("Open");
    			problem.releaseProblem("Comunicazione dall'amministratore dell'helpdesk: la segnalazione è in attesa di essere assegnata ad un esperto differente.\n");
    		}
    	} catch (ProblemJBException pje) {
	        throw new UserEJBException(pje.getMessage(), pje.getUserMessage());    		
    	}
    }	*/
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public void enable(String login, String instance) throws UserEJBException{ 
    try
    {
    	UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        userdao.setLogin(login);
        userdao.createConnection(instance);
        userdao.enable();
        userdao.closeConnection();
    }
    catch(UserDAOException userdaoexception)
    {
        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    }
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean existsAccount(String login, String instance) throws UserEJBException{ 
	boolean result;
    try
    {
    	UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        userdao.setLogin(login);
        userdao.createConnection(instance);
        result=userdao.existsAccount();
        userdao.closeConnection();
    }
    catch(UserDAOException userdaoexception)
    {
        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    }
 return result;
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
 * @ejb.transaction
 *  type="Required"
**/
public void disable(String login, int profile, String admin, Collection eventi, String instance) throws UserEJBException{
	int adminProfile=0;
    try {
    	UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        userdao.setLogin(login);
        userdao.setProfile(profile);
        userdao.createConnection(instance);
        userdao.disable();
        userdao.setLogin(admin);
        adminProfile = userdao.getUserDetail().getProfile();
        userdao.closeConnection();
    } catch(UserDAOException userdaoexception) {
        throw new UserEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    }
    Iterator ev = eventi.iterator();
    ProblemValueObject problemvalueobject = null;
    Problem problem = new Problem();
    try {
        while (ev.hasNext()){
        	EventValueObject evo = (EventValueObject)ev.next();
	        if(evo.getEventType()==4)
	        	problem.reAssignProblemToExpert(evo);
	        else if (evo.getEventType()==1)
            	/*
            	 * TODO da generalizzare il cambio 2->1
            	 */
	        	problem.changeState(evo,new TransitionKey(2,1,adminProfile), instance);
	        	
	        	//problemdao.releaseProblem(evo.getNote());
	        //evo.setStateDescription((problemdao.getProblemDetail()).getStatoDescrizione());
	        //problemdao.closeConnection();
	        
	        //sendMail(problemvalueobject, evo, null);
        }
    }
    catch(ProblemJBException problemdaoexception)
    {
        throw new UserEJBException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
    }  catch(ConditionException e){
    	throw new UserEJBException(e.getMessage(),e.getUserMessage());
    }
}

}

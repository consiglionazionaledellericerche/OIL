/*
 * Created on 13-mag-2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.cnr.helpdesk.StateMachineManagement.tasks;

import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.ConditionException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.TaskException;
import it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.ProblemDAO;
import it.cnr.helpdesk.dao.UserDAO;

/**
 * @author astentella
 *
 */
public class CheckUserAbility extends Task {

	public void doAction(Object token) throws TaskException {
		EventValueObject evo = (EventValueObject)token;
		UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
		ProblemDAO problemdao = DAOFactory.getDAOFactoryByProperties().getProblemDAO();
		System.out.println("EventType="+evo.getEventType());
		if(evo.getEventType()==0){				//case: new ticket created
			try {
				userdao.createConnection(evo.getInstance());
				userdao.setLogin(evo.getOriginatoreEvento());
				UserValueObject uvo = userdao.getUserDetail();
				userdao.closeConnection();
				System.out.println("utente: "+evo.getOriginatoreEventoDescrizione());
				String[] args = {evo.getOriginatoreEventoDescrizione()};
				System.out.println("stato utente="+uvo.getEnabled());
				if(uvo.getEnabled().startsWith("d"))			//deprecated user can't open new tickets  
					throw new ConditionException("exceptions.UserDeprecated", args );
			} catch (UserDAOException userdaoexception) {
				throw new TaskException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
			} catch (ConditionException e) {
				TaskException te = new TaskException(e.getMessageKey(),e.getMessageArgs());
				te.setRootCause(e);
				throw te;
			}
		}
		
	}

}

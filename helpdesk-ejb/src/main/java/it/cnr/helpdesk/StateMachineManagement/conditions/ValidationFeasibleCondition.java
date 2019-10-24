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
 * Created on 17-lug-2006
 *
 */
package it.cnr.helpdesk.StateMachineManagement.conditions;

import java.util.Collection;

import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.ConditionException;
import it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.ProblemDAO;
import it.cnr.helpdesk.dao.UserDAO;



/**
 * @author Aldo Stentella Liberati
 *
 */
public class ValidationFeasibleCondition implements Condition {


	/**
	 * @deprecated
	 * @return false
	 */
	public boolean checkCondition() {
		return false;
	}

	/** <p>controlla la condizione correlata e se risulta non 
	 *     verificata lancia una ConditionException</p>
	 *
	 * @see it.cnr.helpdesk.StateMachineManagement.conditions.Condition#checkCondition(java.lang.Object)
	 */
	public void checkCondition(Object token) throws ConditionException {
		EventValueObject evo = (EventValueObject)token;
		UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
		ProblemDAO problemdao = DAOFactory.getDAOFactoryByProperties().getProblemDAO();
		Collection collection;
		if(evo.getEventType()==1 && evo.getState()==6){
			try {
				userdao.createConnection(evo.getInstance());
				collection = userdao.allCategoryValidators(evo.getCategory());
				userdao.closeConnection();
				String[] args = {evo.getCategoryDescription()};
				if(collection.isEmpty())
					throw new ConditionException("exceptions.ValidationNotFeasible", args );
					//throw new ConditionException("exceptions.ValidationNotFeasible","<p>Impossibile effettuare il cambio di stato: non sono disponibili validatori per la categoria del problema in oggetto</p>");
			} catch (UserDAOException userdaoexception) {
				throw new ConditionException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
			}
		}
	}




}

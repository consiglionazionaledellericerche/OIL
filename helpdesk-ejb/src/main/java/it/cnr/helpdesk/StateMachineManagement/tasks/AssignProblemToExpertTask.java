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
 * Created on 23-giu-2006
 */
package it.cnr.helpdesk.StateMachineManagement.tasks;

import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemDAOException;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.TaskException;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.ProblemDAO;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class AssignProblemToExpertTask extends Task {

	public void doAction(Object token) throws TaskException {
		EventValueObject evo = (EventValueObject)token;
		evo.setExpertLogin(evo.getOriginatoreEvento());
		DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		try {
			problemdao.createConnection(evo.getInstance());
			problemdao.assignProblemToExpert(evo);
			problemdao.closeConnection();
		} catch (ProblemDAOException e) {
			throw new TaskException(e.getMessage(),e.getUserMessage());
		}

	}

}

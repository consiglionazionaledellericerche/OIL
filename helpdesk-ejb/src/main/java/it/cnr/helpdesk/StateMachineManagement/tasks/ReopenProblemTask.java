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
 * Created on 7-lug-2006
 *
 */
package it.cnr.helpdesk.StateMachineManagement.tasks;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.ConditionException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.TaskException;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.TransitionKey;
import it.cnr.helpdesk.exceptions.SettingsJBException;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class ReopenProblemTask extends Task {

	public void doAction(Object token) throws TaskException {
		EventValueObject evo = (EventValueObject)token;
		EventValueObject ev2 = null;
		Problem problem = new Problem();
		if(evo.getEventType()==2 && evo.getState()==2){
	        try {
				ProblemValueObject pvo = problem.getProblemDetail(evo.getIdSegnalazione(), evo.getInstance());
				ev2 = new EventValueObject();
				ev2.setEventType(1);
		        ev2.setIdSegnalazione(evo.getIdSegnalazione());
		        ev2.setStateDescription(Settings.getId2DescriptionPriorityMapping(evo.getInstance()).get(0));	//stato 'open'
		        ev2.setOriginatoreEvento(evo.getOriginatoreEvento());
				ev2.setCategory(evo.getCategory());
				ev2.setTitle(pvo.getTitolo());
				ev2.setDescription(pvo.getDescrizione());
				ev2.setCategoryDescription(evo.getCategoryDescription());
		        ev2.setOldState(2);
		        ev2.setState(1);
		        ev2.setInstance(evo.getInstance());
				problem.changeState(ev2,new TransitionKey(2,1,2),evo.getInstance());
			} catch (ProblemJBException e) {
				throw new TaskException(e.getMessage(),e.getUserMessage());
			} catch (SettingsJBException e) {
				throw new TaskException(e.getMessage(),e.getUserMessage());
			} catch (ConditionException e) {
				throw new TaskException(e.getMessage(),e.getUserMessage());
			}

		}
	}

}

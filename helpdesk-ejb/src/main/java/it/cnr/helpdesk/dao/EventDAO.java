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
 * Created on 6-giu-2006
 *


 */
package it.cnr.helpdesk.dao;

import java.util.ArrayList;
import java.util.Collection;

import it.cnr.helpdesk.EventManagement.exceptions.EventDAOException;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;

/**
 * @author Aldo Stentella Liberati
 *


 */
public interface EventDAO {

	  public void closeConnection() throws EventDAOException ;
	  public void createConnection(String instance) throws EventDAOException ;

	  public String register(EventValueObject evo) throws EventDAOException;
	  public String getTemplate(int i) throws EventDAOException;
	  public Collection getProblemEvents(int segnalazione) throws EventDAOException;
	  public Collection getProblemEvents(long segnalazione, boolean filter) throws EventDAOException;
	  public EventValueObject getProblemEventDetail(int segnalazione, String dataEvento) throws EventDAOException;
	  public ArrayList getNormalTasks(int eventType) throws EventDAOException;
	  public ArrayList getTransTasks(int eventType) throws EventDAOException;
	  public void hideEvent(EventValueObject evo) throws EventDAOException;
	  public void makeAlterable(EventValueObject evo) throws EventDAOException;
	  public void unmakeAlterable(EventValueObject evo) throws EventDAOException;
}

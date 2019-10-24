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
package it.cnr.helpdesk.StateMachineManagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import it.cnr.helpdesk.StateMachineManagement.conditions.Condition;

import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineDAOException;
import it.cnr.helpdesk.StateMachineManagement.javabeans.Transition;
import it.cnr.helpdesk.StateMachineManagement.tasks.Task;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.State;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.TransitionKey;
import it.cnr.helpdesk.UserManagement.valueobjects.Profile;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.dao.StateMachineDAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;

public class OracleStateMachineDAO extends HelpDeskDAO implements
		StateMachineDAO {
	/*
	 * <p>Crea la connessione al data base</p>
	 * 
	 */
	public void createConnection(String instance) throws StateMachineDAOException {
		try {
			super.createConnection(instance);
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new StateMachineDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}
	/*
	 * <p>Chiude la connessione al data base</p>
	 * 
	 */
	public void closeConnection() throws StateMachineDAOException {
		try {
			super.closeConnection();
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new StateMachineDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

	/* 
	 * <p>Legge le transizioni dal db, associandole ai profili autorizzati ad eseguirle.</p>
	 * @param 
	 * @return Restituisce un ArrayList di oggetti di tipo it.cnr.helpdesk.StateMachineManagement.valueobjects.Transition 
	 * 
	 * */

	public HashMap readTransitions() throws StateMachineDAOException {
		
		String s = "Select p.id_profilo, p.nome, t.id, t.origine, s1.descrizione, t.destinazione, s2.descrizione "+
			"from transition t, stato s1, stato s2, profilo p, authorization a "+
			"where p.id_profilo=a.profilo "+
			"and t.id=a.transition " +
			"and t.origine=s1.id_stato " +
			"and t.destinazione=s2.id_stato";
		HashMap hashmap = null;
		try {
			ResultSet rs = execQuery(s);
			hashmap = new HashMap();
			while (rs.next()) {
				State origine=new State(rs.getInt(4), rs.getString(5));
				State destinazione=new State(rs.getInt(6), rs.getString(7));
				Transition t=new Transition(rs.getInt(1), rs.getString(2), rs.getInt(3), origine, destinazione);
				hashmap.put(new TransitionKey(rs.getInt(4), rs.getInt(6),rs.getInt(1)),t);
			}
		} catch (SQLException sqlexception) {
			log(this,
					"Si \350 verificato un errore nell'esecuzione della query \""
							+ s + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new StateMachineDAOException(sqlexception.getMessage(),
					"Si \350 verificato un errore durante il recupero della lista delle transizioni.");
		}
		return hashmap;

	}
	
	/*
	 * <p>Legge i task che non necessitano di essere eseguiti in una transazione, 
	 *    associati ad una transizione operata da un determinato profilo.</p>
	 * @param int transition identificativo della transizione.
	 * @param int profilo identificativo del profilo
	 * @return Restituisce un ArrayList di task associati alla transizione specificata operata dal profilo specificato 
	 */
	public ArrayList readNormalTasks(int transition, int profilo) throws StateMachineDAOException{
		String s = "Select t.taskclass "+
		"from transition_task tt, task t "+
		"where tt.task=t.id "+
		"and tt.transition="+transition+ " "+ 
		"and tt.profilo="+profilo+" " +
		"and tt.transaction_required='n'";
	ArrayList arraylist = null;
	try {
		ResultSet rs = execQuery(s);
		arraylist = new ArrayList();
		while (rs.next()) {
			String taskName=rs.getString(1);
			Task task;
			try {
				task = (Task) Class.forName(taskName).newInstance();
			} catch (InstantiationException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
				"Impossibile istanziare oggetto "+taskName+".");
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
						"Impossibile accedere alla definizione della classe "+taskName+".");
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
						"Impossibile trovare la definizione della classe "+taskName+".");
			}
			arraylist.add(task);
		}
	} catch (SQLException sqlexception) {
		log(this,
				"Si \350 verificato un errore nell'esecuzione della query \""
						+ s + "\". " + sqlexception.getMessage());
		sqlexception.printStackTrace();
		throw new StateMachineDAOException(sqlexception.getMessage(),
				"Si \350 verificato un errore durante il recupero della lista dei task.");
	}
	return arraylist;

	}

	/*
	 * <p>Legge i task che necessitano di essere eseguiti in una transazione, 
	 *    associati ad una transizione operata da un determinato profilo.</p>
	 * @param int transition identificativo della transizione.
	 * @param int profilo identificativo del profilo
	 * @return Restituisce un ArrayList di task associati alla transizione specificata operata dal profilo specificato 
	 */
	public ArrayList readTransTasks(int transition, int profilo) throws StateMachineDAOException{
		String s = "Select t.taskclass "+
		"from transition_task tt, task t "+
		"where tt.task=t.id "+
		"and tt.transition="+transition+ " "+ 
		"and tt.profilo="+profilo+" " +
		"and tt.transaction_required='y'";
	ArrayList arraylist = null;
	try {
		ResultSet rs = execQuery(s);
		arraylist = new ArrayList();
		while (rs.next()) {
			String taskName=rs.getString(1);
			Task task;
			try {
				task = (Task) Class.forName(taskName).newInstance();
			} catch (InstantiationException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
				"Impossibile istanziare oggetto "+taskName+".");
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
						"Impossibile accedere alla definizione della classe "+taskName+".");
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
						"Impossibile trovare la definizione della classe "+taskName+".");
			}
			arraylist.add(task);
		}
	} catch (SQLException sqlexception) {
		log(this,
				"Si \350 verificato un errore nell'esecuzione della query \""
						+ s + "\". " + sqlexception.getMessage());
		sqlexception.printStackTrace();
		throw new StateMachineDAOException(sqlexception.getMessage(),
				"Si \350 verificato un errore durante il recupero della lista dei task.");
	}
	return arraylist;

	}
	
	/*
	 * <p>Legge le precondizioni associate ad una transizione, ovvero le condizioni che devono essere verificate
	 * prima di effettuare la transizioni.</p>
	 * @param int transition identificativo della transizione.
	 * @return Restituisce un ArrayList di condizioni associate alla transizione specificata.
	 */
	public ArrayList readPreConditions(int transition) throws StateMachineDAOException{
		String s = "Select c.condition_class "+
		"from transition t, pre_conditions pc, condition c "+
		"where t.id=pc.transition "+
		"and pc.condition=c.id " +
		"and t.id="+transition;
	ArrayList arraylist = null;
	try {
		ResultSet rs = execQuery(s);
		arraylist = new ArrayList();
		while (rs.next()) {
			String conditionName=rs.getString(1);
			Condition condition;
			try {
				condition = (Condition) Class.forName(conditionName).newInstance();
			} catch (InstantiationException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
				"Impossibile istanziare oggetto "+conditionName+".");
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
						"Impossibile accedere alla definizione della classe "+conditionName+".");
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
						"Impossibile trovare la definizione della classe "+conditionName+".");
			}
			arraylist.add(condition);
		}
	} catch (SQLException sqlexception) {
		log(this,
				"Si \350 verificato un errore nell'esecuzione della query \""
						+ s + "\". " + sqlexception.getMessage());
		sqlexception.printStackTrace();
		throw new StateMachineDAOException(sqlexception.getMessage(),
				"Si \350 verificato un errore durante il recupero della lista delle pre-condizioni " +
				"per la transizione "+transition+".");
	}
	return arraylist;

	}
	
	/*
	 * <p>Legge le postcondizioni associate ad una transizione, ovvero le condizioni che devono essere verificate
	 * dopo aver effettuato la transizioni.</p>
	 * @param int transition identificativo della transizione.
	 * @return Restituisce un ArrayList di condizioni associate alla transizione specificata.
	 */
	public ArrayList readPostConditions(int transition) throws StateMachineDAOException{
		String s = "Select c.condition_class "+
		"from transition t, post_conditions pc, condition c "+
		"where t.id=pc.transition "+
		"and pc.condition=c.id " +
		"and t.id="+transition;
	ArrayList arraylist = null;
	try {
		ResultSet rs = execQuery(s);
		arraylist = new ArrayList();
		while (rs.next()) {
			String conditionName=rs.getString(1);
			Condition condition;
			try {
				condition = (Condition) Class.forName(conditionName).newInstance();
			} catch (InstantiationException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
				"Impossibile istanziare oggetto "+conditionName+".");
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
						"Impossibile accedere alla definizione della classe "+conditionName+".");
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
				throw new StateMachineDAOException(e.getMessage(),
						"Impossibile trovare la definizione della classe "+conditionName+".");
			}
			arraylist.add(condition);
		}
	} catch (SQLException sqlexception) {
		log(this,
				"Si \350 verificato un errore nell'esecuzione della query \""
						+ s + "\". " + sqlexception.getMessage());
		sqlexception.printStackTrace();
		throw new StateMachineDAOException(sqlexception.getMessage(),
				"Si \350 verificato un errore durante il recupero della lista delle post-condizioni " +
				"per la transizione "+transition+".");
	}
	return arraylist;

	}
	
	/*
	 * <p>Legge la lista degli stati.
	 * @return Restituisce un ArrayList che rappresenta la lista degli stati. 
	 */
	public ArrayList readStates() throws StateMachineDAOException {
		String s = "Select id_stato, descrizione " + "from stato";
		ArrayList arraylist = null;
		try {
			ResultSet rs = execQuery(s);
			arraylist = new ArrayList();
			while (rs.next()) {
				State state = new State(rs.getInt("id_stato"), rs.getString("descrizione"));
				arraylist.add(state);
			}
		} catch (SQLException sqlexception) {
			log(this,
					"Si \350 verificato un errore nell'esecuzione della query \""
							+ s + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new StateMachineDAOException(
					sqlexception.getMessage(),
					"Si \350 verificato un errore durante il recupero della lista degli stati.");
		}
		return arraylist;

	}
	
	/*
	 * <p>Legge la lista dei profili.
	 * @return Restituisce un ArrayList che rappresenta la lista dei profili. 
	 */
	public ArrayList readProfiles() throws StateMachineDAOException {
		String s = "Select id_profilo, nome " + 
			"from profilo";
		ArrayList arraylist = null;
		try {
			ResultSet rs = execQuery(s);
			arraylist = new ArrayList();
			while (rs.next()) {
				Profile profile = new Profile(rs.getInt("id_profilo"), rs.getString("nome"));
				arraylist.add(profile);
			}
		} catch (SQLException sqlexception) {
			log(this,
					"Si \350 verificato un errore nell'esecuzione della query \""
							+ s + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new StateMachineDAOException(
					sqlexception.getMessage(),
					"Si \350 verificato un errore durante il recupero della lista dei profili.");
		}
		return arraylist;

	}
	/*
	 * <p>Restituisce il template della mail di notifica associato alla transizione specificata operata dal profilo specificato
	 * @param int transition identificativo della transizione.
	 * @param int profilo identificativo del profilo
	 */
	public String getTemplate(int transition, int profilo) throws StateMachineDAOException {
		String templateEmail = "";
		String s1 = "Select t.template_email from transition_template tt, template t where t.id_template = tt.template and tt.transition="+transition+" and tt.profilo= "+profilo;
		try {
			log(this, s1);
			ResultSet resultset = execQuery(s1);
			if(resultset.next())
				templateEmail = resultset.getString(1);
		} catch(SQLException sqlexception){
			throw new StateMachineDAOException(sqlexception.getMessage(),"Si \350 verificato un errore durante la creazione delle mail di notifica. Contattare l'assistenza.");
		}
		return templateEmail;

	}
}

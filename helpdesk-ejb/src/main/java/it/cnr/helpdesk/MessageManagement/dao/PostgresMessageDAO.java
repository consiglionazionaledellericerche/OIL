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

 
// Source File Name:   OracleMessageDAO.java

package it.cnr.helpdesk.MessageManagement.dao;

import it.cnr.helpdesk.exceptions.HelpDeskDAOException;
import it.cnr.helpdesk.MessageManagement.exceptions.MessageDAOException;
import it.cnr.helpdesk.MessageManagement.valueobjects.MessageValueObject;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.dao.MessageDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

// Referenced classes of package it.cnr.helpdesk.dao:
//            HelpDeskDAO

public class PostgresMessageDAO extends HelpDeskDAO implements MessageDAO {

	public PostgresMessageDAO() {
		con = null;
	}

	public void createConnection(String instance) throws MessageDAOException {
		try {
			super.createConnection(instance);
		} catch (HelpDeskDAOException e) {
			throw new MessageDAOException(e.getMessage(), e.getUserMessage());
		}
	}

	public void closeConnection() throws MessageDAOException {
		try {
			super.closeConnection();
		} catch (HelpDeskDAOException e) {
			throw new MessageDAOException(e.getMessage(), e.getUserMessage());
		}
	}

	public MessageValueObject getMessage(int id_msg) throws MessageDAOException {
		MessageValueObject message;
		String sqlQuery = "select ID_MSG, TESTO, TO_CHAR(DATA,'DD-MM-YYYY'),ENABLED, ORIGINATORE  from MESSAGGIO  WHERE ID_MSG = " + id_msg;
		try {
			ResultSet rs = execQuery(sqlQuery);
			if (rs.next())
				message = new MessageValueObject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			else
				message = null;
		} catch (SQLException e) {
			log(this, String.valueOf(String.valueOf((new StringBuffer("Si \350 verificato un errore nell'esecuzione della query \"")).append(sqlQuery).append("\". ").append(e.getMessage()))));
			e.printStackTrace();
			throw new MessageDAOException(e.getMessage(), "Si \350 verificato un errore durante il recupero delle informazioni sulla categoria indicata. Contattare l'assistenza.");
		}
		return message;
	}

	/**
	 * Restituisce tutti i messaggi abilitati con ordine decrescente sulla data
	 * 
	 * @return una Collection di stringhe messaggio
	 * 
	 */
	public Collection getMessages() throws MessageDAOException {
		ArrayList messages = new ArrayList();
		String sqlQuery = " select TESTO  from MESSAGGIO WHERE ENABLED='y' ORDER BY DATA DESC";
		try {
			ResultSet rs = execQuery(sqlQuery);
			while (rs.next())
				messages.add(rs.getString(1));
		} catch (SQLException e) {
			log(this, String.valueOf(String.valueOf((new StringBuffer("Si \350 verificato un errore nell'esecuzione della query \"")).append(sqlQuery).append("\". ").append(e.getMessage()))));
			e.printStackTrace();
			throw new MessageDAOException(e.getMessage(), "Si \350 verificato un errore durante il recupero delle informazioni sulla categoria indicata. Contattare l'assistenza.");
		}
		return messages;
	}

	/**
	 * Restituisce tutti i messaggi all'utenza filtrando opzionalmente, in base al parametro filter, quelli con flag enabled=y
	 * 
	 * @param filter
	 *            abilita o meno il filtro sui messaggi disabilitati
	 * @return una Collection di MessageValueObject
	 * 
	 */
	public Collection getAllMessages(boolean filter) throws MessageDAOException {
		ArrayList messages = new ArrayList();
		MessageValueObject mvo;
		String sqlQuery = " select ID_MSG, TESTO, TO_CHAR(DATA,'DD-MM-YYYY'),ENABLED, ORIGINATORE  from MESSAGGIO " + (filter ? " WHERE ENABLED='y'" : "");
		try {
			ResultSet rs = execQuery(sqlQuery);
			while (rs.next()) {
				mvo = new MessageValueObject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				messages.add(mvo);
			}
		} catch (SQLException e) {
			log(this, String.valueOf(String.valueOf((new StringBuffer("Si \350 verificato un errore nell'esecuzione della query \"")).append(sqlQuery).append("\". ").append(e.getMessage()))));
			e.printStackTrace();
			throw new MessageDAOException(e.getMessage(), "Si \350 verificato un errore durante il recupero delle informazioni sulla categoria indicata. Contattare l'assistenza.");
		}
		return messages;
	}

	public int executeInsert(MessageValueObject mvo) throws MessageDAOException {
		String s = "";
		int i = 0;
		try {
			s = "select nextval('seq_messaggi')";
			ResultSet resultset = execQuery(s);
			if (resultset.next())
				i = resultset.getInt(1);
			s = "insert into MESSAGGIO(id_msg, TESTO, data, enabled, originatore) values(" + i + ", '" + mvo.getTesto().replaceAll("'", "''") + "',now(), 'y', '" + mvo.getOriginatore() + "')";
			log(this, s);
			execUpdQuery(s);
		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new MessageDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante l'inserimento del messaggio.");
		}
		return i;
	}

	public void executeDelete(int id_msg) throws MessageDAOException {
		String query = "DELETE FROM MESSAGGIO WHERE ID_MSG = " + id_msg;
		try {
			execUpdQuery(query);
			System.out.println(query);
		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + query + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new MessageDAOException(sqlexception.getMessage(), "Si \350 verificato un errore eliminando il messaggio. Contattare l'assistenza.");
		}
	}

	public void executeUpdate(MessageValueObject mvo) throws MessageDAOException {
		String query = "UPDATE MESSAGGIO SET TESTO = '" + mvo.getTesto().replaceAll("'", "''") + "', data = now() WHERE ID_MSG = " + mvo.getId_msg();
		try {
			execUpdQuery(query);
			System.out.println(query);
		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + query + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new MessageDAOException(sqlexception.getMessage(), "Si \350 verificato un errore modificando il messaggio. Contattare l'assistenza.");
		}
	}

	public void enable(int id_msg) throws MessageDAOException {
		String query = "UPDATE MESSAGGIO SET ENABLED = 'y' WHERE ID_MSG = " + id_msg;
		try {
			execUpdQuery(query);
			System.out.println(query);
		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + query + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new MessageDAOException(sqlexception.getMessage(), "Si \350 verificato un errore abilitando il messaggio. Contattare l'assistenza.");
		}
	}

	public void disable(int id_msg) throws MessageDAOException {
		String query = "UPDATE MESSAGGIO SET ENABLED = 'n' WHERE ID_MSG = " + id_msg;
		try {
			execUpdQuery(query);
			System.out.println(query);
		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + query + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new MessageDAOException(sqlexception.getMessage(), "Si \350 verificato un errore disabilitando il messaggio. Contattare l'assistenza.");
		}
	}

	private Connection con;
}

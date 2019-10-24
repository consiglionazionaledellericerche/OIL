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

package it.cnr.helpdesk.MailParserManagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import it.cnr.helpdesk.MailParserManagement.exceptions.MailParserDAOException;
import it.cnr.helpdesk.MailParserManagement.valueobjects.MailComponent;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.dao.MailParserDAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;

/**
 * @author astentella
 *
 */
public class OracleMailParserDAO extends HelpDeskDAO implements MailParserDAO {
    private Connection con;

	public OracleMailParserDAO() {
		con=null;
	}
	
	public void closeConnection() throws MailParserDAOException {
		try {
			super.closeConnection();
		} catch (HelpDeskDAOException e) {
			throw new MailParserDAOException(e.getMessage(), e.getUserMessage());
		}
	}
	public void createConnection(String instance) throws MailParserDAOException {
		try {
			super.createConnection(instance);
		} catch (HelpDeskDAOException e) {
			throw new MailParserDAOException(e.getMessage(), e.getUserMessage());
		}
	}

	public int executeInsert(MailComponent mail) throws MailParserDAOException {
		String query = "";
		int i=0;
	    try {
	    	query = "select seq_temp_segnalazione.nextval from dual";
        	ResultSet resultset = execQuery(query);
            if(resultset.next())
                i = resultset.getInt(1);
	        query = ("INSERT INTO temp_segnalazione (id_temp_segnalazione, email, titolo, categoria, " +
	           "nome, cognome, descrizione, data_sottomissione, codice_attivazione, flag_conferma_effettuata) " +
	            "VALUES ("+i+",'"+mail.getMail()+"', '"+mail.getTitolo2SQL()+"', "+mail.getCategoria()+", " +
	            "'"+mail.getNome2SQL()+"', '"+mail.getCognome2SQL()+"', '"+mail.getDescrizione2SQL()+"', " +
	            "'"+mail.getData()+"', '"+mail.getCodice()+"','"+(mail.isConferma()?"y":"n")+"')");
	        log(this, query);
	        execUpdQuery(query);
	      } 
	      catch (SQLException e) {
	      	e.printStackTrace();
	        throw new MailParserDAOException(e.getMessage(), "Si è verificato un errore sulla query: "+query);
	      } 
		return i;
	}

	
	public MailComponent getRow(Integer id) throws MailParserDAOException {
	      String query = ("SELECT ts.id_temp_segnalazione, ts.email, ts.titolo, ts.categoria, c.nome AS nome_categoria, ts.nome, ts.cognome, " +
		"ts.descrizione, ts.data_sottomissione, ts.flag_conferma_effettuata, ts.codice_attivazione, ts.id_segnalazione " +
		"FROM temp_segnalazione ts LEFT JOIN categoria c ON ts.categoria = c.id_categoria " +
		"WHERE ts.id_temp_segnalazione = "+id)
		  ;
          MailComponent my_record = new MailComponent();
	      try{
	      	log(this, query);
	        ResultSet rs = execQuery(query);
	        if(rs.next()){
	          my_record.setId(rs.getInt("id_temp_segnalazione"));
	          my_record.setMail(rs.getString("email"));
	          my_record.setTitolo(rs.getString("titolo"));
	          my_record.setCategoria(rs.getInt("categoria"));
	          my_record.setNomeCategoria(rs.getString("nome_categoria"));
	          my_record.setNome(rs.getString("nome"));
	          my_record.setCognome(rs.getString("cognome"));
	          my_record.setDescrizione(rs.getString("descrizione"));
	          my_record.setConferma(rs.getString("flag_conferma_effettuata").startsWith("y"));
	          my_record.setCodice(rs.getString("codice_attivazione"));
	          my_record.setIdSegnalazione(rs.getInt("id_segnalazione"));
	          my_record.setData(rs.getString("data_sottomissione"));
	          return my_record;
	        }
	      } 
	      catch (SQLException e) {
	      	e.printStackTrace();
	        throw new MailParserDAOException(e.getMessage(), "Si è verificato un errore sulla query: "+query);
	      }
		return null;
	}


	public void executeUpdate(MailComponent mail) throws MailParserDAOException {
		String query = "";
	    try {
	        query = "UPDATE temp_segnalazione SET email='"+mail.getMail()+"', titolo='"+mail.getTitolo2SQL()+"', categoria="+mail.getCategoria()+", " +
	           "nome='"+mail.getNome2SQL()+"', cognome='"+mail.getCognome2SQL()+"', descrizione='"+mail.getDescrizione2SQL()+"', data_sottomissione='"+
			   mail.getData()+"', codice_attivazione= '"+mail.getCodice()+"', flag_conferma_effettuata='"+(mail.isConferma()?"y":"n")+"', "+
	           "id_segnalazione="+mail.getIdSegnalazione()+" WHERE id_temp_segnalazione="+mail.getId();
	        log(this, query);
	        execUpdQuery(query);
	      } 
	      catch (SQLException e) {
	      	throw new MailParserDAOException(e.getMessage(), "Si è verificato un errore sulla query: "+query);
	      } 		
	}

	public MailComponent getByIdSegnalazione(long segnalazione) throws MailParserDAOException {
		String query = ("SELECT * FROM temp_segnalazione WHERE id_segnalazione = "+segnalazione);
        MailComponent my_record = new MailComponent();
	      try{
	      	log(this, query);
	        ResultSet rs = execQuery(query);
	        if(rs.next()){
	          my_record.setId(rs.getInt("id_temp_segnalazione"));
	          my_record.setMail(rs.getString("email"));
	          my_record.setTitolo(rs.getString("titolo"));
	          my_record.setCategoria(rs.getInt("categoria"));
	          my_record.setNome(rs.getString("nome"));
	          my_record.setCognome(rs.getString("cognome"));
	          my_record.setDescrizione(rs.getString("descrizione"));
	          my_record.setConferma(rs.getString("flag_conferma_effettuata").startsWith("y"));
	          my_record.setCodice(rs.getString("codice_attivazione"));
	          my_record.setIdSegnalazione(rs.getInt("id_segnalazione"));
	          my_record.setData(rs.getString("data_sottomissione"));
	          return my_record;
	        }
	      } 
	      catch (SQLException e) {
	      	throw new MailParserDAOException(e.getMessage(), "Si è verificato un errore sulla query: "+query);
	      }
		return null;
	}

}

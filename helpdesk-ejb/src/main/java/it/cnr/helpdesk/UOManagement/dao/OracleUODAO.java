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
 * Created on 24-ott-2005
 */
package it.cnr.helpdesk.UOManagement.dao;

import java.sql.*;
import java.util.*;
import it.cnr.helpdesk.UOManagement.exceptions.UODAOException;
import it.cnr.helpdesk.UOManagement.valueobjects.UOValueObject;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.dao.UODAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;

/**
 * @author Aldo Stentella Liberati
  */
public class OracleUODAO extends HelpDeskDAO implements UODAO {

    public OracleUODAO() {
        super();
    }
    
    public void createConnection(String instance) throws UODAOException {
        try {
            super.createConnection(instance);
        } catch(HelpDeskDAOException helpdeskdaoexception) {
            throw new UODAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
        }
    }

    public void closeConnection() throws UODAOException {
        try {
            super.closeConnection();
        } catch (HelpDeskDAOException helpdeskdaoexception) {
            throw new UODAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
        }
    }


    /*Modificato 28/10/2008 author Marco Trischitta*/
    public Collection getStructures() throws UODAOException {
        String s = "SELECT codice_struttura, descrizione, acronimo FROM strutture WHERE enabled='y'";
        ArrayList arraylist = null;
        try  {
            ResultSet resultset = execQuery(s);
            arraylist = new ArrayList();
            while(resultset.next()) { 
            	arraylist.add(resultset.getString(1)+" - "+ resultset.getString(2)+" ("+ resultset.getString(3)+")");
            }
        }
        catch(SQLException sqlexception)  {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UODAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista delle strutture.");
        }
        return arraylist;
    }
    
    public Collection getAllStructures() throws UODAOException {
    	String s = "SELECT codice_struttura, descrizione, acronimo, evidenza, enabled FROM strutture ORDER BY descrizione";
    	//String s = "SELECT codice_struttura, descrizione, acronimo FROM strutture WHERE enabled='y'";
    	ArrayList struct = new ArrayList();
    	UOValueObject uvo = new UOValueObject();
    	try {
			ResultSet rs = execQuery(s);
			while(rs.next()){
				uvo = new UOValueObject(rs.getString(1),rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5));
				//uvo = new UOValueObject(rs.getString(1),rs.getString(2),rs.getString(3), "", "");
				struct.add(uvo);
			}
		}
    	 catch(SQLException sqlexception)  {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UODAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista delle strutture.");
        }
		return struct;
    }
    
    public UOValueObject getOneStructure(String cod) throws UODAOException {
    	String s = "SELECT descrizione, acronimo, evidenza, enabled FROM strutture WHERE codice_struttura='"+cod+"'";
    	UOValueObject uvo = new UOValueObject();
    	try {
			ResultSet rs = execQuery(s);
			while(rs.next()){
				uvo = new UOValueObject(cod, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
		}
    	 catch(SQLException sqlexception)  {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UODAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista delle strutture.");
        }
		return uvo;
    }
    
    public String executeInsert(UOValueObject vo) throws UODAOException {
    	String query = "";
    	String cod = "";
    	try {
    		query = "SELECT seq_strutture.nextval FROM DUAL";
 			ResultSet resultset = execQuery(query);
 			if(resultset.next())
 				cod = resultset.getString(1);
			query = "INSERT INTO strutture (codice_struttura, descrizione, " +
					"acronimo, evidenza, enabled) VALUES ('"+cod+"', '"+vo.getDescrizione().replaceAll("'", "''")+"', " +
					"'"+vo.getAcronimo().replaceAll("'", "''")+"', '"+vo.getEvidenza()+"', '"+vo.getEnabled()+"')";
			execUpdQuery(query);
		} 
    	catch (SQLException e) {
			throw new UODAOException(e.getMessage(), "Si è verificato un errore sulla query: "+query);
		}
    	return cod;
    }
    
    public void executeUpdate(UOValueObject vo) throws UODAOException {
    	String query = "";
    	try {
    		query = "UPDATE strutture SET descrizione='"+vo.getDescrizione().replaceAll("'", "''")+"', " +
    				"acronimo='"+vo.getAcronimo().replaceAll("'", "''")+"' WHERE codice_struttura='"+vo.getCodiceStruttura()+"'";
    		execUpdQuery(query);			
		}
    	catch (SQLException e) {
			throw new UODAOException(e.getMessage(), "Si è verificato un errore sulla query: "+query);
		}
    }
    
    public void executeDelete(UOValueObject vo) throws UODAOException {
    	String query = "";
    	try {
    		query = "DELETE FROM strutture WHERE codice_struttura='"+vo.getCodiceStruttura()+"'";
    		execUpdQuery(query);
		} 
    	catch (SQLException e) {
			throw new UODAOException(e.getMessage(), "Si è verificato un errore sulla query: "+query);
		}
    	
    }
    
    public void enabled(String cod) throws UODAOException {
    	String query = "UPDATE strutture SET enabled = 'y' WHERE codice_struttura = '"+cod+"'";
    	try {
			execUpdQuery(query);
    	}
    	catch(SQLException e) {
			throw new UODAOException(e.getMessage(), "Si è verificato un errore sulla query: "+query);
    	}			
    }
    
    public void disabled(String cod) throws UODAOException {
    	String query = "UPDATE strutture SET enabled = 'n' WHERE codice_struttura = '"+cod+"'";
    	try {
			execUpdQuery(query);
    	}
    	catch(SQLException e) {
			throw new UODAOException(e.getMessage(), "Si è verificato un errore sulla query: "+query);
    	}			
    }

}

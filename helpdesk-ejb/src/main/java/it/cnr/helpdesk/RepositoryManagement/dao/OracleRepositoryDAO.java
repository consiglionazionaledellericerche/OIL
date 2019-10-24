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
 * Created on 25-gen-2007
 *
 */
package it.cnr.helpdesk.RepositoryManagement.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import oracle.sql.BLOB;

import it.cnr.helpdesk.RepositoryManagement.exceptions.RepositoryDAOException;
import it.cnr.helpdesk.RepositoryManagement.valueobjects.RepositoryValueObject;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.dao.RepositoryDAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class OracleRepositoryDAO extends HelpDeskDAO implements RepositoryDAO {

    public void createConnection(String instance) throws RepositoryDAOException {
	    try {
	        super.createConnection(instance);
	    } catch(HelpDeskDAOException helpdeskdaoexception) {
	        throw new RepositoryDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
	    }
    }
    
    public void closeConnection() throws RepositoryDAOException {
	    try {
	        super.closeConnection();
	    } catch(HelpDeskDAOException helpdeskdaoexception) {
	        throw new RepositoryDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
	    }
    }
    
	public void addRow(RepositoryValueObject rvo) throws RepositoryDAOException {
	        String s = "insert into REPOSITORY (MESE, CATEGORIA, ATTACHMENT, STATO) values (" + rvo.getMese() + "," + rvo.getCategoria() + ", empty_blob(),'"+rvo.getStato()+"')";
	        log(this, s);
			try {
	        	execUpdQuery(s);
			} catch(SQLException se) {
				log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + se.getMessage());
				se.printStackTrace();
				throw new RepositoryDAOException(se.getMessage(), "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + se.getMessage());
			}
	}

	public OutputStream executeInsert(RepositoryValueObject rvo) throws RepositoryDAOException {
    	String s = "insert into REPOSITORY (MESE, CATEGORIA, ATTACHMENT, STATO) values (" + rvo.getMese() + "," + rvo.getCategoria() + ", empty_blob(),'"+rvo.getStato()+"')";

		OutputStream os;
		log(this, s);
		try {
			execUpdQuery(s);
			s="select ATTACHMENT  from  REPOSITORY where MESE="+rvo.getMese()+" AND CATEGORIA="+rvo.getCategoria();
			log(this, s);	        	
			ResultSet resultset=execQuery(s);
			resultset.next();
			BLOB blob = (BLOB) resultset.getBlob("ATTACHMENT");
			os = new java.io.BufferedOutputStream(blob.getBinaryOutputStream());
		} catch(SQLException se) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + se.getMessage());
			se.printStackTrace();
			throw new RepositoryDAOException(se.getMessage(), "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + se.getMessage());
		}
		return os;
	}
	
	public void writeFile(RepositoryValueObject rvo) throws RepositoryDAOException {
		return;
	}

	public void executeDelete(RepositoryValueObject rvo) throws RepositoryDAOException {
			String s = "delete from repository where mese="+rvo.getMese()+" and categoria="+rvo.getCategoria();
			try {
	        	log(this, s);
	        	execUpdQuery(s);
			} catch (SQLException se) {
				log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + se.getMessage());
				se.printStackTrace();
				throw new RepositoryDAOException(se.getMessage(), "Si \350 verificato un errore durante la cancellazione dell'attachment.");			
			}
	}

	public boolean findDocument(RepositoryValueObject rvo) throws RepositoryDAOException {
    	String sQuery = "select COUNT(*) from REPOSITORY where MESE="+rvo.getMese()+" and CATEGORIA="+rvo.getCategoria();
    	int count = 0;
    	try {
	        log(this, sQuery);
	        ResultSet resultset = execQuery(sQuery);
	        if(resultset.next()){
	            count = resultset.getInt(1);
	        }
	    } catch(SQLException sqlexception) {
	    	throw new RepositoryDAOException(sqlexception.getMessage());
	    }
	    return (count>0);
	}
	
	public InputStream getBlobFile(RepositoryValueObject rvo) throws RepositoryDAOException {
	   	Blob data=null;
    	InputStream dataStream=null;
    	String sQuery = "select ATTACHMENT from REPOSITORY where MESE="+rvo.getMese()+" and CATEGORIA="+rvo.getCategoria();
    	try
	    {
	        log(this, sQuery);
	        ResultSet resultset = execQuery(sQuery);
	        if(resultset.next()){
	            data = resultset.getBlob(1);
	            dataStream=data.getBinaryStream();
	    }
	    }
	    catch(SQLException sqlexception)
	    {
	        log(this, "eccezione: " + sqlexception.getMessage());
	        sqlexception.printStackTrace();
	    }
	    return dataStream;
	}


	public Collection fetchRows(RepositoryValueObject rvo) throws RepositoryDAOException {
		String sQuery = "select r.MESE, r.CATEGORIA, r.STATO, s.DESCRIZIONE from repository r, stato_report s where r.stato=s.stato " +
						" and r.MESE like '" + rvo.getMese() + "%' and r.CATEGORIA = " + rvo.getCategoria() +" ORDER BY r.MESE";
		Collection data = new ArrayList();
		RepositoryValueObject row;
		try {
	        log(this, sQuery);
	        ResultSet resultset = execQuery(sQuery);
	        while(resultset.next()){
	        	row = new RepositoryValueObject();
	        	row.setMese(resultset.getInt("MESE"));
	        	row.setCategoria(resultset.getInt("CATEGORIA"));
	        	row.setStato(resultset.getString("STATO"));
	        	row.setStatoDes(resultset.getString("DESCRIZIONE"));
	        	data.add(row);
	        }
	    } catch(SQLException sqlexception) {
	        throw new RepositoryDAOException(sqlexception.getMessage());
	    }
	    return data;
	}

	public Connection getConnection() {
		return super.getConnection();
	}

	public void updateAttachment(RepositoryValueObject rvo) throws RepositoryDAOException {
    	String s = "update REPOSITORY set STATO='m' where MESE = "+ rvo.getMese() + "AND CATEGORIA = " + rvo.getCategoria();
		int i = 0;
		try {
			log(this, s);
			execUpdQuery(s);
			s = "select ATTACHMENT from REPOSITORY where MESE = "+ rvo.getMese() + "AND CATEGORIA = " + rvo.getCategoria();
			ResultSet resultset=execQuery(s);
	    	resultset.next();
	    	BLOB blob = (BLOB) resultset.getBlob("ATTACHMENT");
	    	java.io.InputStream in = new java.io.BufferedInputStream(new FileInputStream(rvo.getAttachment()));
	    	byte[] byteArr = new byte[1024];
	    	java.io.OutputStream os = new java.io.BufferedOutputStream(blob.getBinaryOutputStream());
	    	int len; 
	    	while ((len = in.read(byteArr))>0){
	    	  os.write(byteArr,0,len);
	    	}
	    	os.close();
	    	in.close();
		} catch(SQLException se) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + se.getMessage());
			se.printStackTrace();
			throw new RepositoryDAOException(se.getMessage(), "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + se.getMessage());
		} catch(FileNotFoundException fnfe) {
			log(this, "Si \350 verificato un errore nell'apertura del file " + fnfe.getMessage());
			fnfe.printStackTrace();
			throw new RepositoryDAOException(fnfe.getMessage(), "Si \350 verificato un errore nell'apertura del file "+ fnfe.getMessage());
		} catch(IOException ioe) {
			log(this, "Si \350 verificato un errore nell'inserimento dell'attachment "+ ioe.getMessage());
			ioe.printStackTrace();
			throw new RepositoryDAOException(ioe.getMessage(), "Si \350 verificato un errore nell'inserimento dell'attachment "+ ioe.getMessage());
		}
	}
}

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
 * Created on 10-feb-2005
 *


 */
package it.cnr.helpdesk.AttachmentManagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import it.cnr.helpdesk.AttachmentManagement.exceptions.AttachmentDAOException;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.AttachmentManagement.valueobjects.*;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;
import it.cnr.helpdesk.dao.AttachmentDAO;

import java.io.*;

/**
 * @author Roberto Puccinelli
 *


 */
public class PostgresAttachmentDAO extends HelpDeskDAO implements AttachmentDAO{
	
	private int id;
	private long id_segnalazione;
	private String nomeFile;
	private File attachment;
	private String descrizione;
	
	public void createConnection(String instance) throws AttachmentDAOException {
		try {
			super.createConnection(instance);
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new AttachmentDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

	public void closeConnection() throws AttachmentDAOException {
		try {
			super.closeConnection();
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new AttachmentDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

	/**
	 * @return Returns the descrizione.
	 */
	public String getDescrizione() {
		return descrizione;
	}
	/**
	 * @param descrizione The descrizione to set.
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	/**
	 * @return Returns the nomeFile.
	 */
	public String getNomeFile() {
		return nomeFile;
	}
	/**
	 * @param nomeFile The nomeFile to set.
	 */
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	/**
	 * @return Returns the attchment.
	 */
	public File getAttachment() {
		return attachment;
	}
	/**
	 * @param attchment The attchment to set.
	 */
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the id_segnalazione.
	 */
	public long getId_segnalazione() {
		return id_segnalazione;
	}
	/**
	 * @param id_segnalazione The id_segnalazione to set.
	 */
	public void setId_segnalazione(long id_segnalazione) {
		this.id_segnalazione = id_segnalazione;
	}
	
	
	public void executeInsert() throws AttachmentDAOException {
		String s=null;
		try {
			InputStream in = new java.io.BufferedInputStream(new FileInputStream(attachment));
			byte[] byteArr = new byte[(int)attachment.length()];
			in.read(byteArr);
			s = "insert into attachments(ID,ID_SEGNALAZIONE, ATTACHMENT, DESCRIZIONE, NOME_FILE) values(nextval('seq_attachments'),"+this.id_segnalazione+", ?,'"+this.descrizione+"','"+this.nomeFile+"')";
        	log(this, s);
			execPrepStat(s,byteArr);
			in.close();
        	in.close();
		} catch(SQLException se) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + se.getMessage());
			se.printStackTrace();
			throw new AttachmentDAOException(se.getMessage(), "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + se.getMessage());
		} catch(FileNotFoundException fnfe) {
			log(this, "Si \350 verificato un errore nell'apertura del file: "+attachment.getName()+ fnfe.getMessage());
			fnfe.printStackTrace();
			throw new AttachmentDAOException(fnfe.getMessage(), "Si \350 verificato un errore nell'apertura del file: "+attachment.getName()+ fnfe.getMessage());
		} catch(IOException ioe) {
			log(this, "Si \350 verificato un errore nell'inserimento dell'attachment: "+attachment.getName()+ ioe.getMessage());
			ioe.printStackTrace();
			throw new AttachmentDAOException(ioe.getMessage(), "Si \350 verificato un errore nell'inserimento dell'attachment: "+attachment.getName()+ ioe.getMessage());
		}


	}
	
	public void executeDelete() throws AttachmentDAOException{
		String s = "delete from attachments where id="+id;
		try {
        	log(this, s);
        	execUpdQuery(s);
		} catch (SQLException se) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + se.getMessage());
			se.printStackTrace();
			throw new AttachmentDAOException(se.getMessage(), "Si \350 verificato un errore durante la cancellazione dell'attachment.");			
		}
	}
	
    public String getTemplate(int i) throws AttachmentDAOException {
    String s = "";
    Object obj = null;
    String s1 = "select tipo_evento.TEMPLATE_EMAIL from tipo_evento where tipo_evento.ID_TIPOEVENTO=" + i;
    try
    {
        log(this, s1);
        ResultSet resultset = execQuery(s1);
        if(resultset.next())
            s = resultset.getString(1);
    }
    catch(SQLException sqlexception)
    {
        log(this, "eccezione: " + sqlexception.getMessage());
        sqlexception.printStackTrace();
    }
    return s;
    }
    
    public InputStream getBlobFile(int idFile) throws AttachmentDAOException {
    	InputStream dataStream=null;
    	String sQuery = "select ATTACHMENT from ATTACHMENTS where ID="+idFile;
    	try
	    {
	        log(this, sQuery);
	        ResultSet resultset = execQuery(sQuery);
	        if(resultset.next()) {
	        	dataStream = resultset.getBinaryStream(1);
	            
	        }
	    }
	    catch(SQLException sqlexception)
	    {
	        log(this, "eccezione: " + sqlexception.getMessage());
	        sqlexception.printStackTrace();
	    }
	    return dataStream;
    }
    
    public Collection fetchAttachments(long id_segn){
    	Collection data=new ArrayList(0);
    	String sQuery = "select ID, ID_SEGNALAZIONE, DESCRIZIONE, NOME_FILE from ATTACHMENTS where ID_SEGNALAZIONE="+id_segn+" ORDER BY ID ASC";
    	try
	    {
	        log(this, sQuery);
	        ResultSet resultset = execQuery(sQuery);
	        while(resultset.next()){
	        	AttachmentValueObject avo = new AttachmentValueObject();
	        	avo.setId(resultset.getInt(1));
		        avo.setId_segnalazione(resultset.getInt(2));
		        avo.setDescrizione(resultset.getString(3));
		        avo.setNomeFile(resultset.getString(4));
	            data.add(avo);
	        }
	    }
	    catch(SQLException sqlexception)
	    {
	        log(this, "eccezione: " + sqlexception.getMessage());
	        sqlexception.printStackTrace();
	    }
	    return data;
    }
    
    public AttachmentValueObject fetchAttachment(){
    	String sQuery = "select ID, ID_SEGNALAZIONE, DESCRIZIONE, NOME_FILE from ATTACHMENTS where ID="+this.id;
    	AttachmentValueObject avo = new AttachmentValueObject();
    	try	{
    		log(this, sQuery);
    		ResultSet resultset = execQuery(sQuery);
    		if(resultset.next()){
    			avo.setId(resultset.getInt(1));
    			avo.setId_segnalazione(resultset.getInt(2));
    			avo.setDescrizione(resultset.getString(3));
    			avo.setNomeFile(resultset.getString(4));
    		}else
    			log(this, "Allegato non trovato: id = "+this.id);
    	} catch(SQLException sqlexception) {
    		log(this, "eccezione: " + sqlexception.getMessage());
    		sqlexception.printStackTrace();
    	}
    	return avo;
    }
    
	public void executeUpdate() throws AttachmentDAOException {
		String s = "UPDATE ATTACHMENTS SET ";
		if(id_segnalazione!=0) s = s.concat("ID_SEGNALAZIONE = "+id_segnalazione+" , ");
		if(descrizione!=null) s = s.concat("DESCRIZIONE = '"+descrizione+"' , ");
		if(nomeFile!=null) s = s.concat("NOME_FILE = '"+nomeFile+"' , ");
   		s = s.substring(0, s.length()-2).concat(" WHERE ID = "+id);
		try {
        	log(this, s);
        	execUpdQuery(s);
		} catch (SQLException se) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + se.getMessage());
			se.printStackTrace();
			throw new AttachmentDAOException(se.getMessage(), "Si \350 verificato un errore durante l'aggiornamento dell'attachment.");			
		}
	}
}

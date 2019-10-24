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
 * Created on 8-giu-2005
 *


 */
package it.cnr.helpdesk.dao;

import it.cnr.helpdesk.AttachmentManagement.exceptions.AttachmentDAOException;
import it.cnr.helpdesk.AttachmentManagement.valueobjects.AttachmentValueObject;

import java.io.InputStream;
import java.util.Collection;
import java.io.File;


/**
 * @author Roberto Puccinelli
 *


 */
public interface AttachmentDAO {
	
    // Setter methods
    public void setId(int id);
    public void setId_segnalazione(long id_segnalazione);
    public void setNomeFile(String nomeFile);
    public void setAttachment (File attachment);
    public void setDescrizione (String descrizione);
	
    // Getter methods
    public int getId();
    public long getId_segnalazione();
    public String getNomeFile();
    public File getAttachment ();
    public String getDescrizione ();
    
	// Specific AttachmentDAO methods
	public void executeInsert() throws AttachmentDAOException ;
	public void executeDelete() throws AttachmentDAOException;
	public void executeUpdate() throws AttachmentDAOException;
    public String getTemplate(int i) throws AttachmentDAOException ;
    public InputStream getBlobFile(int idFile) throws AttachmentDAOException;
    public Collection fetchAttachments(long id_segn);
    public AttachmentValueObject fetchAttachment();
    
    // Generic DAO methods
    public void createConnection(String instance)throws AttachmentDAOException;
    public void closeConnection()throws AttachmentDAOException;

}

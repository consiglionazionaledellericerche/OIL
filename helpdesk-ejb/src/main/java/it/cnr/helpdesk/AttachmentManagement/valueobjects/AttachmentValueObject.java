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
package it.cnr.helpdesk.AttachmentManagement.valueobjects;

import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import java.io.File;

/**
 * @author Roberto Puccinelli
 *


 */
public class AttachmentValueObject {
	private int id;
	private long id_segnalazione;
	private File attachment;
	private String nomeFile;
	private String descrizione;
	private String originatore;
	private ProblemValueObject problem;
	
	public AttachmentValueObject(){
		super();
	}

	public AttachmentValueObject(long id_segnalazione, String nomeFile, File attachment, String descrizione){
		super();
		this.id_segnalazione=id_segnalazione;
		this.nomeFile=nomeFile;
		this.attachment=attachment;
		this.descrizione=descrizione;
	}
	
	public AttachmentValueObject(int id, long id_segnalazione, File attachment, String descrizione){
		super();
		this.id=id;
		this.id_segnalazione=id_segnalazione;
		this.attachment=attachment;
		this.descrizione=descrizione;
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
	 * @return Returns the attachment.
	 */
	public File getAttachment() {
		return attachment;
	}
	/**
	 * @param attachment The attachment to set.
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
	public String getOriginatore() {
		return originatore;
	}
	public void setOriginatore(String originatore) {
		this.originatore = originatore;
	}
	public ProblemValueObject getProblem() {
		return problem;
	}
	public void setProblem(ProblemValueObject problem) {
		this.problem = problem;
	}
}

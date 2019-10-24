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
package it.cnr.helpdesk.RepositoryManagement.valueobjects;

import java.io.File;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class RepositoryValueObject {

	private int mese;
	private int categoria;
	private String stringCat;
	private String stato;
	private String nomeFile;
	private File attachment;
	private String statoDes;
	
	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public int getMese() {
		return mese;
	}
	public void setMese(int mese) {
		this.mese = mese;
	}
	public String getNomeFile() {
		if(nomeFile==null)
			nomeFile = (mese+"-"+categoria+".rtf");
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getStringCat() {
		return stringCat;
	}
	public void setStringCat(String stringCat) {
		this.stringCat = stringCat;
	}

	public File getAttachment() {
		return attachment;
	}
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	public String getNomeMese(){
		String[] mesi = {"","Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
		int month = Integer.parseInt((""+mese).substring(4));
		return mesi[month];
	}
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public String getStatoDes() {
		return statoDes;
	}
	public void setStatoDes(String statoDes) {
		this.statoDes = statoDes;
	}
	
	
	
	//metodi supplementari:
	
	public String getStateColor(){
		if(this.stato.startsWith("m"))
			return "rt_modif" ;
		if(this.stato.startsWith("v"))
			return "rt_vuoto" ;	
		if(this.stato.startsWith("i"))
			return "rt_incomp" ;
		return "rt_disp";	
	}
	/**
	 * non sono modificabili i report vuoti o incompleti
	 */
	public boolean isModificabile(){
		return("dm".indexOf(this.stato)>-1);
	}
	
	/**
	 * sono eliminabili i report incompleti (per essere eventualmente aggiornati) 
	 * e quelli modificati (per essere eventulmente riportati all'originale di jasper)
	 *
	 */
	public boolean isEliminabile(){
		return("mi".indexOf(this.stato)>-1);
	}
}

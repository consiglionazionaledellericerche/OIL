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
 * Created on 23-ott-2008
 */
package it.cnr.helpdesk.UOManagement.valueobjects;

import java.io.Serializable;

/**
 * @author Marco Trischitta
 */
public class UOValueObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String codiceStruttura;
	private String descrizione;
	private String acronimo;
	private String evidenza;
	private String enabled;

	
	public UOValueObject() {
		this.codiceStruttura = "";
		this.descrizione = "";
		this.acronimo = "";
		this.evidenza = "";
		this.enabled = "";
	}
	
	public UOValueObject(String cS, String d, String a, String ev, String en){
		this.codiceStruttura = cS;
		this.descrizione = d;
		this.acronimo = a;
		this.evidenza = ev;
		this.enabled = en;
	}	
	
	public String getAcronimo() {
		return acronimo;
	}
	
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}
	
	public String getCodiceStruttura() {
		return codiceStruttura;
	}
	
	public void setCodiceStruttura(String codiceStruttura) {
		this.codiceStruttura = codiceStruttura;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getEnabled() {
		return enabled;
	}
	
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	public String getEvidenza() {
		return evidenza;
	}
	
	public void setEvidenza(String evidenza) {
		this.evidenza = evidenza;
	}
}

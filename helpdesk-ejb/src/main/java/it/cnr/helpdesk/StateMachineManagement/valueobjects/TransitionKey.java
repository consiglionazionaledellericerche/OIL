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
package it.cnr.helpdesk.StateMachineManagement.valueobjects;

import it.cnr.helpdesk.valueobjects.ComponentValueObject;

import java.io.Serializable;

public class TransitionKey extends ComponentValueObject implements Serializable {

	private int profilo;
	private  int origine;
	private int destinazione;
	public int getDestinazione() {
		return destinazione;
	}
	public void setDestinazione(int destinazione) {
		this.destinazione = destinazione;
	}
	public int getOrigine() {
		return origine;
	}
	public void setOrigine(int origine) {
		this.origine = origine;
	}
	public int getProfilo() {
		return profilo;
	}
	public void setProfilo(int profilo) {
		this.profilo = profilo;
	}
	public TransitionKey(int origine, int destinazione, int profilo) {
		super();
		
		this.destinazione = destinazione;
		this.origine = origine;
		this.profilo = profilo;
	}
	
	public int hashCode() {
		return this.origine*100000 + this.destinazione*100 + this.profilo;
	}
	public boolean equals (Object obj){
		TransitionKey tk=(TransitionKey) obj;
		if (this.origine==tk.getOrigine() && this.destinazione==tk.getDestinazione() && this.profilo==tk.getProfilo())
			return true;
		return false;
	}
}

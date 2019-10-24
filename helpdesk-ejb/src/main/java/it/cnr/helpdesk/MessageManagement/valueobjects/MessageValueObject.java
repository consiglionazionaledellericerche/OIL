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

 

package it.cnr.helpdesk.MessageManagement.valueobjects;

import it.cnr.helpdesk.valueobjects.ComponentValueObject;
import java.io.Serializable;

public class MessageValueObject extends ComponentValueObject implements Serializable {

	private int id_msg;
	private String testo;
	private String data;
	private String enabled;
	private String originatore;

	public MessageValueObject() {
		id_msg=0;
		testo="";
		data="";
		enabled="";
		originatore = "";
	}

	public MessageValueObject(int id_msg, String testo, String data, String enabled, String originatore) {
		this.id_msg = id_msg;
		this.testo = testo;
		this.data = data;
		this.enabled = enabled;
		this.originatore = originatore;
	}

	/**
	 * @return Returns the data.
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data The data to set.
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return Returns the enabled.
	 */
	public String getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled The enabled to set.
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return Returns the id_msg.
	 */
	public int getId_msg() {
		return id_msg;
	}
	/**
	 * @param id_msg The id_msg to set.
	 */
	public void setId_msg(int id_msg) {
		this.id_msg = id_msg;
	}
	/**
	 * @return Returns the originatore.
	 */
	public String getOriginatore() {
		return originatore;
	}
	/**
	 * @param originatore The originatore to set.
	 */
	public void setOriginatore(String originatore) {
		this.originatore = originatore;
	}
	/**
	 * @return Returns the testo.
	 */
	public String getTesto() {
		return testo;
	}
	/**
	 * @param testo The testo to set.
	 */
	public void setTesto(String testo) {
		this.testo = testo;
	}
}
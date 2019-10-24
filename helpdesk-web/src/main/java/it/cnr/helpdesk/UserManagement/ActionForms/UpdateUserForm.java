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
 * Created on 2-mag-2005
 *


 */
package it.cnr.helpdesk.UserManagement.ActionForms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Roberto Puccinelli
 *


 */
public class UpdateUserForm extends ValidatorForm {
	
	private String login;
	private String nome;
	private String cognome;
	private String e_mail;
	private String telefono;
	private String struttura;
	private int profilo;
	private String strutturaDescrizione;
	private String mail_stop;
	

	public String getMail_stop() {
		return mail_stop;
	}
	public void setMail_stop(String mail_stop) {
		this.mail_stop = mail_stop;
	}
    /**
     * @return Returns the strutturaDescrizione.
     */
    public String getStrutturaDescrizione() {
        return strutturaDescrizione;
    }
    /**
     * @param strutturaDescrizione The strutturaDescrizione to set.
     */
    public void setStrutturaDescrizione(String strutturaDescrizione) {
        this.strutturaDescrizione = strutturaDescrizione;
    }
	/**
	 * @return Returns the profilo.
	 */
	public int getProfilo() {
		return profilo;
	}
	/**
	 * @param profilo The profilo to set.
	 */
	public void setProfilo(int profilo) {
		this.profilo = profilo;
	}
	/**
	 * @return Returns the cognome.
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * @param cognome The cognome to set.
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	/**
	 * @return Returns the e_mail.
	 */
	public String getE_mail() {
		return e_mail;
	}
	/**
	 * @param e_mail The e_mail to set.
	 */
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	/**
	 * @return Returns the login.
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login The login to set.
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return Returns the nome.
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome The nome to set.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return Returns the struttura.
	 */
	public String getStruttura() {
		return struttura;
	}
	/**
	 * @param struttura The struttura to set.
	 */
	public void setStruttura(String struttura) {
		this.struttura = struttura;
	}
	/**
	 * @return Returns the telefono.
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono The telefono to set.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}

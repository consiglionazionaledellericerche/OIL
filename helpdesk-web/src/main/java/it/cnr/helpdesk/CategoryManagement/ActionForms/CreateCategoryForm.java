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
 * Created on 9-mag-2005
 *


 */
package it.cnr.helpdesk.CategoryManagement.ActionForms;

import org.apache.struts.validator.ValidatorForm;;

/**
 * @author Roberto Puccinelli
 *


 */
public class CreateCategoryForm extends ValidatorForm {
	
	private String categoriaPadre;
	private String nome;
	private String descrizione;
	
	

	/**
	 * @return Returns the categoriaPadre.
	 */
	public String getCategoriaPadre() {
		return categoriaPadre;
	}
	/**
	 * @param categoriaPadre The categoriaPadre to set.
	 */
	public void setCategoriaPadre(String categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
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
}

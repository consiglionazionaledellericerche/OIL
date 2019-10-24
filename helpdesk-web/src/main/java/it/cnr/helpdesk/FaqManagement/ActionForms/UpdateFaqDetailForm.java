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
 * Created on 20-lug-2005
 *


 */
package it.cnr.helpdesk.FaqManagement.ActionForms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Aldo Stentella Liberati
 *


 */
public class UpdateFaqDetailForm extends ValidatorForm {

    private String titolo;
    private String descrizione;
    private String categoria;
    /**
     * @return Returns the categoria.
     */
    public String getCategoria() {
        return categoria;
    }
    /**
     * @param categoria The categoria to set.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
     * @return Returns the titolo.
     */
    public String getTitolo() {
        return titolo;
    }
    /**
     * @param titolo The titolo to set.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}

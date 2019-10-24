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
 * Created on 11-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.ActionForms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Aldo Stentella Liberati
 *


 */
public class CambioCategoriaForm extends ActionForm {
    private String idSegnalazione;
    private String idCategoria;
    private String nomeCategoria;
    private String nota;
    
    /**
     * @return Returns the idCategoria.
     */
    public String getIdCategoria() {
        return idCategoria;
    }
    /**
     * @param idCategoria The idCategoria to set.
     */
    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }
    /**
     * @return Returns the idSegnalazione.
     */
    public String getIdSegnalazione() {
        return idSegnalazione;
    }
    /**
     * @param idSegnalazione The idSegnalazione to set.
     */
    public void setIdSegnalazione(String idSegnalazione) {
        this.idSegnalazione = idSegnalazione;
    }
    /**
     * @return Returns the nomeCategoria.
     */
    public String getNomeCategoria() {
        return nomeCategoria;
    }
    /**
     * @param nomeCategoria The nomeCategoria to set.
     */
    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    /**
     * @return Returns the nota.
     */
    public String getNota() {
        return nota;
    }
    /**
     * @param nota The nota to set.
     */
    public void setNota(String nota) {
        this.nota = nota;
    }
    
	public void reset(ActionMapping action, HttpServletRequest request){
	    super.reset(action, request);
	    idSegnalazione=null;
	    idCategoria=null;
	    nomeCategoria=null;
	    nota=null;
	}
}

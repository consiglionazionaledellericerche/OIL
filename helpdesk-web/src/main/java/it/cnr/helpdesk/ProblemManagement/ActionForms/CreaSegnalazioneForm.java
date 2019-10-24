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
 * Created on 21-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.ActionForms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
/**
 * @author Aldo Stentella Liberati
 *


 */
public class CreaSegnalazioneForm extends ValidatorForm {
    private String descrizioneCategoria;
    private String idCategoria;
    private String titolo;
    private String descrizione;
    private String attach;
    private String priorita;
    private String idUtente;
    private String nominativoUtente;
    
    /**
     * @return Returns the priorita.
     */
    public String getPriorita() {
        return priorita;
    }
    /**
     * @param priorita The priorita to set.
     */
    public void setPriorita(String priorita) {
        this.priorita = priorita;
    }
    /**
     * @return Returns the attach.
     */
    public String getAttach() {
        return attach;
    }
    /**
     * @param attach The attach to set.
     */
    public void setAttach(String attach) {
        this.attach = attach;
    }
    private FormFile attachment1;
    private FormFile attachment2;
    private FormFile attachment3;

    /**
     * @return Returns the attachment1.
     */
    public FormFile getAttachment1() {
        return attachment1;
    }
    /**
     * @param attachment1 The attachment1 to set.
     */
    public void setAttachment1(FormFile attachment1) {
        this.attachment1 = attachment1;
    }
    /**
     * @return Returns the attachment2.
     */
    public FormFile getAttachment2() {
        return attachment2;
    }
    /**
     * @param attachment2 The attachment2 to set.
     */
    public void setAttachment2(FormFile attachment2) {
        this.attachment2 = attachment2;
    }
    /**
     * @return Returns the attachment3.
     */
    public FormFile getAttachment3() {
        return attachment3;
    }
    /**
     * @param attachment3 The attachment3 to set.
     */
    public void setAttachment3(FormFile attachment3) {
        this.attachment3 = attachment3;
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
     * @return Returns the descrizioneCategoria.
     */
    public String getDescrizioneCategoria() {
        return descrizioneCategoria;
    }
    /**
     * @param descrizioneCategoria The descrizioneCategoria to set.
     */
    public void setDescrizioneCategoria(String descrizioneCategoria) {
        this.descrizioneCategoria = descrizioneCategoria;
    }
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
    
	public String getNominativoUtente() {
		return nominativoUtente;
	}
	public void setNominativoUtente(String nominativoUtente) {
		this.nominativoUtente = nominativoUtente;
	}
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public void reset(ActionMapping action, HttpServletRequest request){
	    super.reset(action, request);
	    descrizioneCategoria=null;
	    idCategoria=null;
	    titolo=null;
	    descrizione=null;
	    attach=null;
	    idUtente=null;
	}

}

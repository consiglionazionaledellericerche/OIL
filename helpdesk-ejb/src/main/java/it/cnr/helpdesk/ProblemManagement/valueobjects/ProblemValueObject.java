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

 

package it.cnr.helpdesk.ProblemManagement.valueobjects;

import it.cnr.helpdesk.valueobjects.ComponentValueObject;
import java.io.Serializable;

public class ProblemValueObject extends ComponentValueObject implements Serializable {

	private long idSegnalazione;
    private String titolo;
    private String descrizione;
    private int categoria;
    private String categoriaDescrizione;
    private int stato;
    private String statoDescrizione;
    private String originatore;
    private String originatoreNome;
    private String originatoreFirstName;
    private String originatoreFamilyName;
    private String originatoreStruttura;
    private String esperto;
    private String espertoNome;
    private String espertoFirstName;
    private String espertoFamilyName;
    private String espertoStruttura;
    // dati validatore
    private String validatore;
    private String validatoreNome;
    private String validatoreFirstName;
    private String validatoreFamilyName;
    private String validatoreStruttura;
    
    private String actualUser;
    private int flagFaq;
    private boolean ownerShip;
    private boolean perCategory;
    private int priorita;
    private String prioritaDescrizione;
    private String ordinamento;
    private boolean extendedSearch;
    
    public ProblemValueObject(long l, String s, String s1, int i, String s2, int j, 
            String s3, String s4, String s5, String s6, String s7, int k, int priorita, String despr, int profUtente)
    {
        titolo = "";
        descrizione = "";
        categoriaDescrizione = "";
        statoDescrizione = "";
        originatore = "";
        originatoreNome = "";
        originatoreFirstName = "";
        originatoreFamilyName = "";
        originatoreStruttura = "";
        esperto = "";
        espertoNome = "";
        espertoFirstName = "";
        espertoFamilyName = "";
        espertoStruttura = "";
        
        validatore = "";
        validatoreNome = "";
        validatoreFirstName = "";
        validatoreFamilyName = "";
        validatoreStruttura = "";
        
        actualUser = "";
        idSegnalazione = l;
        titolo = s;
        descrizione = s1;
        categoria = i;
        categoriaDescrizione = s2;
        stato = j;
        statoDescrizione = s3;
        originatore = s4;
        originatoreNome = s5;
        esperto = s6;
        espertoNome = s7;
        flagFaq = k;
        ownerShip = true;
        perCategory = true;
        this.priorita=priorita;
        prioritaDescrizione=despr;
        profiloUtente = profUtente;
        
        
    }
    
    /*
     * ATTENZIONE NON SETTA LA PROPRIETA' PROFILO UTENTE 
     */
    public ProblemValueObject(long l, String s, String s1, int i, String s2, int j, 
            String s3, String s4, String s5, String s6, String s7, int k, int priorita, String despr, String idValidatore, String nomeCognomeValidatore) {
        
        originatoreFirstName = "";
        originatoreFamilyName = "";
        originatoreStruttura = "";
        espertoFirstName = "";
        espertoFamilyName = "";
        espertoStruttura = "";
        validatoreFirstName = "";
        validatoreFamilyName = "";
        validatoreStruttura = "";
        actualUser = "";
        idSegnalazione = l;
        titolo = s;
        descrizione = s1;
        categoria = i;
        categoriaDescrizione = s2;
        stato = j;
        statoDescrizione = s3;
        originatore = s4;
        originatoreNome = s5;
        esperto = s6;
        espertoNome = s7;
        flagFaq = k;
        ownerShip = true;
        perCategory = true;
        this.priorita=priorita;
        prioritaDescrizione=despr;
        validatore = idValidatore;
        validatoreNome = nomeCognomeValidatore;
    }
    
    
    
    
    /*
     * ATTENZIONE NON SETTA LA PROPRIETA' PROFILO UTENTE ne i parametri del VALIDATORE
     */
    public ProblemValueObject(long l, String s, String s1, int i, String s2, int j, 
            String s3, String s4, String s5, String s6, String s7, int k, int priorita, String despr)
    {
        titolo = "";
        descrizione = "";
        categoriaDescrizione = "";
        statoDescrizione = "";
        originatore = "";
        originatoreNome = "";
        originatoreFirstName = "";
        originatoreFamilyName = "";
        originatoreStruttura = "";
        esperto = "";
        espertoNome = "";
        espertoFirstName = "";
        espertoFamilyName = "";
        espertoStruttura = "";
        actualUser = "";
        idSegnalazione = l;
        titolo = s;
        descrizione = s1;
        categoria = i;
        categoriaDescrizione = s2;
        stato = j;
        statoDescrizione = s3;
        originatore = s4;
        originatoreNome = s5;
        esperto = s6;
        espertoNome = s7;
        flagFaq = k;
        ownerShip = true;
        perCategory = true;
        this.priorita=priorita;
        prioritaDescrizione=despr;
    
    }
    
    public ProblemValueObject(long l, String s, String s1, int i, String s2, int j, 
            String s3, String s4, String s5, String s6, String s7, int k)
    {
        titolo = "";
        descrizione = "";
        categoriaDescrizione = "";
        statoDescrizione = "";
        originatore = "";
        originatoreNome = "";
        originatoreFirstName = "";
        originatoreFamilyName = "";
        originatoreStruttura = "";
        esperto = "";
        espertoNome = "";
        espertoFirstName = "";
        espertoFamilyName = "";
        espertoStruttura = "";
        actualUser = "";
        idSegnalazione = l;
        titolo = s;
        descrizione = s1;
        categoria = i;
        categoriaDescrizione = s2;
        stato = j;
        statoDescrizione = s3;
        originatore = s4;
        originatoreNome = s5;
        esperto = s6;
        espertoNome = s7;
        flagFaq = k;
        ownerShip = true;
        perCategory = true;
    }

    
    
    public ProblemValueObject(long l, String s, String s1, int i, String s2, int j, 
            String s3, String s4, String s5, String s6, String s7, int k, String idValidatore, String nomeCognomeValidatore)
    {
        titolo = "";
        descrizione = "";
        categoriaDescrizione = "";
        statoDescrizione = "";
        originatore = "";
        originatoreNome = "";
        originatoreFirstName = "";
        originatoreFamilyName = "";
        originatoreStruttura = "";
        esperto = "";
        espertoNome = "";
        espertoFirstName = "";
        espertoFamilyName = "";
        espertoStruttura = "";
        actualUser = "";
        idSegnalazione = l;
        titolo = s;
        descrizione = s1;
        categoria = i;
        categoriaDescrizione = s2;
        stato = j;
        statoDescrizione = s3;
        originatore = s4;
        originatoreNome = s5;
        esperto = s6;
        espertoNome = s7;
        flagFaq = k;
        ownerShip = true;
        perCategory = true;
        validatore = idValidatore;
        validatoreNome = nomeCognomeValidatore;
        
    }

    public ProblemValueObject()
    {
        titolo = "";
        descrizione = "";
        categoriaDescrizione = "";
        statoDescrizione = "";
        originatore = "";
        originatoreNome = "";
        originatoreFirstName = "";
        originatoreFamilyName = "";
        originatoreStruttura = "";
        esperto = "";
        espertoNome = "";
        espertoFirstName = "";
        espertoFamilyName = "";
        espertoStruttura = "";
        actualUser = "";
        ownerShip = true;
        perCategory = true;
    }

    public void setOwnerShip(boolean flag)
    {
        ownerShip = flag;
    }

    public boolean isOwnerShip()
    {
        return ownerShip;
    }

    public void setPerCategory(boolean flag)
    {
        perCategory = flag;
    }

    public boolean isPerCategory()
    {
        return perCategory;
    }

    public void setIdSegnalazione(long l)
    {
        idSegnalazione = l;
    }

    public long getIdSegnalazione()
    {
        return idSegnalazione;
    }

    public void setTitolo(String s)
    {
        titolo = s;
    }

    public String getTitolo()
    {
        return titolo;
    }

    public String getTitolo2SQL()
    {
        return titolo.replaceAll("'","''");
    }
    
    public void setActualUser(String s)
    {
        actualUser = s;
    }

    public String getActualUser()
    {
        return actualUser;
    }

    public void setDescrizione(String s)
    {
        descrizione = s;
    }

    public String getDescrizione()
    {
        return descrizione;
    }

    public String getDescrizione2SQL()
    {
        return descrizione.replaceAll("'","''");
    }
    public void setCategoria(int i)
    {
        categoria = i;
    }

    public int getCategoria()
    {
        return categoria;
    }

    public void setCategoriaDescrizione(String s)
    {
        categoriaDescrizione = s;
    }

    public String getCategoriaDescrizione()
    {
        return categoriaDescrizione;
    }

    public void setStato(int i)
    {
        stato = i;
    }

    public int getStato()
    {
        return stato;
    }

    public void setStatoDescrizione(String s)
    {
        statoDescrizione = s;
    }

    public String getStatoDescrizione()
    {
        return statoDescrizione;
    }

    public void setOriginatore(String s)
    {
        originatore = s;
    }

    public String getOriginatore()
    {
        return originatore;
    }

    public void setOriginatoreNome(String s)
    {
        originatoreNome = s;
    }

    public String getOriginatoreNome()
    {
        return originatoreNome;
    }

    public void setOriginatoreFirstName(String s)
    {
        originatoreFirstName = s;
    }

    public String getOriginatoreFirstName()
    {
        return originatoreFirstName;
    }

    public void setOriginatoreFamilyName(String s)
    {
        originatoreFamilyName = s;
    }

    public String getOriginatoreFamilyName()
    {
        return originatoreFamilyName;
    }

    public void setOriginatoreStruttura(String s)
    {
        originatoreStruttura = s;
    }

    public String getOriginatoreStruttura()
    {
        return originatoreStruttura;
    }

    public void setEspertoFirstName(String s)
    {
        espertoFirstName = s;
    }

    public String getEspertoFirstName()
    {
        return espertoFirstName;
    }

    public void setEspertoFamilyName(String s)
    {
        espertoFamilyName = s;
    }

    public String getEspertoFamilyName()
    {
        return espertoFamilyName;
    }

    public void setEspertoStruttura(String s)
    {
        espertoStruttura = s;
    }

    public String getEspertoStruttura()
    {
        return espertoStruttura;
    }

    public void setEsperto(String s)
    {
        esperto = s;
    }

    public String getEsperto()
    {
        return esperto;
    }

    public void setEspertoNome(String s)
    {
        espertoNome = s;
    }

    public String getEspertoNome()
    {
        return espertoNome;
    }

    public void setFlagFaq(int i)
    {
        flagFaq = i;
    }

    public int getFlagFaq()
    {
        return flagFaq;
    }

    /**
     * @return Returns the priorita.
     */
    public int getPriorita() {
        return priorita;
    }
    /**
     * @param priorita The priorita to set.
     */
    public void setPriorita(int priorita) {
        this.priorita = priorita;
    }
    /**
     * @return Returns the prioritaDescrizione.
     */
    public String getPrioritaDescrizione() {
        return prioritaDescrizione;
    }
    /**
     * @param prioritaDescrizione The prioritaDescrizione to set.
     */
    public void setPrioritaDescrizione(String prioritaDescrizione) {
        this.prioritaDescrizione = prioritaDescrizione;
    }
    
    public int getProfiloUtente() {
        return profiloUtente;
    }
    
    public void setProfiloUtente(int p) {
    	profiloUtente = p;
    }
    
    
    
    // profilo dell'utente UTENTE, EXPERTO. VALIDATORE 
    private int profiloUtente = 0;

	/**
	 * @return Returns the validatore.
	 */
	public String getValidatore() {
		return validatore;
	}

	/**
	 * @param validatore The validatore to set.
	 */
	public void setValidatore(String validatore) {
		this.validatore = validatore;
	}

	/**
	 * @return Returns the validatoreFamilyName.
	 */
	public String getValidatoreFamilyName() {
		return validatoreFamilyName;
	}

	/**
	 * @param validatoreFamilyName The validatoreFamilyName to set.
	 */
	public void setValidatoreFamilyName(String validatoreFamilyName) {
		this.validatoreFamilyName = validatoreFamilyName;
	}

	/**
	 * @return Returns the validatoreFirstName.
	 */
	public String getValidatoreFirstName() {
		return validatoreFirstName;
	}

	/**
	 * @param validatoreFirstName The validatoreFirstName to set.
	 */
	public void setValidatoreFirstName(String validatoreFirstName) {
		this.validatoreFirstName = validatoreFirstName;
	}

	/**
	 * @return Returns the validatoreNome.
	 */
	public String getValidatoreNome() {
		return validatoreNome;
	}

	/**
	 * @param validatoreNome The validatoreNome to set.
	 */
	public void setValidatoreNome(String validatoreNome) {
		this.validatoreNome = validatoreNome;
	}

	/**
	 * @return Returns the validatoreStruttura.
	 */
	public String getValidatoreStruttura() {
		return validatoreStruttura;
	}

	/**
	 * @param validatoreStruttura The validatoreStruttura to set.
	 */
	public void setValidatoreStruttura(String validatoreStruttura) {
		this.validatoreStruttura = validatoreStruttura;
	}

	public String getOrdinamento() {
		return ordinamento;
	}
	public void setOrdinamento(String ordinamento) {
		this.ordinamento = ordinamento;
	}

	public boolean isExtendedSearch() {
		return extendedSearch;
	}

	public void setExtendedSearch(boolean extendedSearch) {
		this.extendedSearch = extendedSearch;
	}
}
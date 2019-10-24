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

public class ProblemListItemValueObject extends ComponentValueObject
    implements Serializable
{

    public ProblemListItemValueObject(long l, String s, String s1, String s2, String s3, String s4, 
            String s5, int i, String s6, String s7, String s8)
    {
        titolo = "";
        stato = "";
        originatore = "";
        originatoreLogin = "";
        esperto = "";
        espertoLogin = "";
        data = "";
        categoriaDescrizione = "";
        idStato = 0;
        idSegnalazione = l;
        titolo = s;
        stato = s1;
        originatore = s2;
        originatoreLogin = s3;
        esperto = s4;
        data = s5;
        idStato = i;
        espertoLogin = s6;
        categoriaDescrizione = s7;
        evidenza = s8;
        
    }

	
    public ProblemListItemValueObject(long l, String s, String s1, String s2, String s3, String s4, 
            String s5, int i, String s6, String s7)
    {
        titolo = "";
        stato = "";
        originatore = "";
        originatoreLogin = "";
        esperto = "";
        espertoLogin = "";
        data = "";
        categoriaDescrizione = "";
        idStato = 0;
        idSegnalazione = l;
        titolo = s;
        stato = s1;
        originatore = s2;
        originatoreLogin = s3;
        esperto = s4;
        data = s5;
        idStato = i;
        espertoLogin = s6;
        categoriaDescrizione = s7;
        
    }

    public ProblemListItemValueObject()
    {
        titolo = "";
        stato = "";
        originatore = "";
        originatoreLogin = "";
        esperto = "";
        espertoLogin = "";
        data = "";
        categoriaDescrizione = "";
        idStato = 0;
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

    public void setStato(String s)
    {
        stato = s;
    }

    public String getStato()
    {
        return stato;
    }

    public void setOriginatore(String s)
    {
        originatore = s;
    }

    public String getOriginatore()
    {
    	if(originatoreEsterno!=null)
    		return originatoreEsterno;
    	else
    		return originatore;    		
    }

    public void setOriginatoreLogin(String s)
    {
        originatoreLogin = s;
    }

    public String getOriginatoreLogin()
    {
        return originatoreLogin;
    }

    public void setEsperto(String s)
    {
        esperto = s;
    }

    public String getEsperto()
    {
        return esperto;
    }

    public void setData(String s)
    {
        data = s;
    }

    public String getData()
    {
        return data;
    }

    public void setIdStato(int i)
    {
        idStato = i;
    }

    public int getIdStato()
    {
        return idStato;
    }

    public void setEspertoLogin(String s)
    {
        espertoLogin = s;
    }

    public String getEspertoLogin()
    {
        return espertoLogin;
    }

    public void setCategoriaDescrizione(String s)
    {
        categoriaDescrizione = s;
    }

    public String getCategoriaDescrizione()
    {
        return categoriaDescrizione;
    }

    public String getEvidenza() {
		return evidenza;
	}


	public void setEvidenza(String evidenza) {
		this.evidenza = evidenza;
	}

    
    public String getOriginatoreEsterno() {
		return originatoreEsterno;
	}


	public void setOriginatoreEsterno(String originatoreEsterno) {
		this.originatoreEsterno = originatoreEsterno;
	}


	private long idSegnalazione;
    private String titolo;
    private String stato;
    private String originatore;
    private String originatoreLogin;
    private String esperto;
    private String espertoLogin;
    private String data;
    private String categoriaDescrizione;
    private int idStato;
    private String evidenza;
    private String originatoreEsterno;
}
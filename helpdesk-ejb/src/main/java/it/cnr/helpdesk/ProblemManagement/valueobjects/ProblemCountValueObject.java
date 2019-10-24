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

public class ProblemCountValueObject extends ComponentValueObject
    implements Serializable
{

	private int idCategoria;
    private int profiloUtente = 0;
    private String nomeCategoria;
    private int countProblema;
    private String expertLogin;
    private boolean ownerShip;
	
    public ProblemCountValueObject(int i, String s, int profUtente,int j)
    {
        nomeCategoria = "";
        expertLogin = "";
        profiloUtente = profUtente;
        idCategoria = i;
        nomeCategoria = s;
        countProblema = j;
        ownerShip = true;
    }

    /*
     * Attenzione non imposta il profilo 
     * 
     * 
     * */
    public ProblemCountValueObject(int i, String s, int j)
    {
        nomeCategoria = "";
        expertLogin = "";
        idCategoria = i;
        nomeCategoria = s;
        countProblema = j;
        ownerShip = true;
    }

    public ProblemCountValueObject()
    {
        nomeCategoria = "";
        expertLogin = "";
        ownerShip = true;
    }

    public void setIdCategoria(int i)
    {
        idCategoria = i;
    }

    public void setOwnerShip(boolean flag)
    {
        ownerShip = flag;
    }

    public boolean isOwnerShip()
    {
        return ownerShip;
    }

    public void setNomeCategoria(String s)
    {
        nomeCategoria = s;
    }

    public void setCountProblema(int i)
    {
        countProblema = i;
    }

    public void setExpertLogin(String s)
    {
        expertLogin = s;
    }

    public void setProfiloUtente(int p)
    {
    	profiloUtente = p;
    }
    public int getIdCategoria()
    {
        return idCategoria;
    }

    public String getNomeCategoria()
    {
        return nomeCategoria;
    }

    public int getCountProblema()
    {
        return countProblema;
    }

    public String getExpertLogin()
    {
        return expertLogin;
    }

    public int getProfiloUtente()
    {
        return profiloUtente;
    }
    
}
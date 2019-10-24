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

 

package it.cnr.helpdesk.FaqManagement.dto;

import it.cnr.helpdesk.valueobjects.ComponentValueObject;
import java.io.Serializable;

public class FaqDTO extends ComponentValueObject
    implements Serializable
{

    public FaqDTO(int i, String s, String s1, int j, String s2, String s3)
    {
        titolo = "";
        descrizione = "";
        idFaq = 0;
        idCategoria = 0;
        descrizioneCategoria = "";
        originatore = "";
        titolo = s;
        descrizione = s1;
        idFaq = i;
        idCategoria = j;
        descrizioneCategoria = s2;
        originatore = s3;
    }

    public FaqDTO()
    {
        titolo = "";
        descrizione = "";
        idFaq = 0;
        idCategoria = 0;
        descrizioneCategoria = "";
        originatore = "";
    }

    public void setTitolo(String s)
    {
        titolo = s;
    }

    public void setDescrizione(String s)
    {
        descrizione = s;
    }

    public void setIdFaq(int i)
    {
        idFaq = i;
    }

    public void setIdCategoria(int i)
    {
        idCategoria = i;
    }

    public void setDescrizioneCategoria(String s)
    {
        descrizioneCategoria = s;
    }

    public void setOriginatore(String s)
    {
        originatore = s;
    }

    public String getTitolo()
    {
        return titolo;
    }

    public String getDescrizione()
    {
        return descrizione;
    }

    public int getIdCategoria()
    {
        return idCategoria;
    }

    public String getDescrizioneCategoria()
    {
        return descrizioneCategoria;
    }

    public int getIdFaq()
    {
        return idFaq;
    }

    public String getOriginatore()
    {
        return originatore;
    }

    private String titolo;
    private String descrizione;
    private int idFaq;
    private int idCategoria;
    private String descrizioneCategoria;
    private String originatore;
}
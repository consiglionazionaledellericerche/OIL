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

 

package it.cnr.helpdesk.StatisticsManagement.valueobjects;

import it.cnr.helpdesk.valueobjects.ComponentValueObject;

public class ProblemsDistributionByCategoryDTO extends ComponentValueObject
{

    public ProblemsDistributionByCategoryDTO(String s, int i)
    {
        nomeCategoria = s;
        count = i;
    }

    public void setIdCategoria(int i)
    {
        idCategoria = i;
    }

    public void setNomeCategoria(String s)
    {
        nomeCategoria = s;
    }

    public void setCount(int i)
    {
        count = i;
    }

    public int getIdCategoria()
    {
        return idCategoria;
    }

    public String getNomeCategoria()
    {
        return nomeCategoria;
    }

    public int getCount()
    {
        return count;
    }

    private int idCategoria;
    private String nomeCategoria;
    private int count;
}
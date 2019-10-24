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

 

package it.cnr.helpdesk.charts.dtos;


public class SeriesItemDTO
{

    public SeriesItemDTO()
    {
    }

    public SeriesItemDTO(String s, Number number)
    {
        label = s;
        value = number;
    }

    public void setLabel(String s)
    {
        label = s;
    }

    public void setValue(Number number)
    {
        value = number;
    }

    public String getLabel()
    {
        return label;
    }

    public Number getValue()
    {
        return value;
    }

    private String label;
    private Number value;
}
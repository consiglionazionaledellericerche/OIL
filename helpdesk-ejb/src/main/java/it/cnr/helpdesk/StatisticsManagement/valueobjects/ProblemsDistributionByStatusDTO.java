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

import java.io.Serializable;

public class ProblemsDistributionByStatusDTO
    implements Serializable
{

    public ProblemsDistributionByStatusDTO()
    {
    }

    public ProblemsDistributionByStatusDTO(String s, int i)
    {
        status = s;
        count = i;
    }

    public void setStatus(String s)
    {
        status = s;
    }

    public void setCount(int i)
    {
        count = i;
    }

    public String getStatus()
    {
        return status;
    }

    public int getCount()
    {
        return count;
    }

    private String status;
    private int count;
}
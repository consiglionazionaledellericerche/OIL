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


public class OpenedClosedCountDTO
{

    public OpenedClosedCountDTO()
    {
    }

    public OpenedClosedCountDTO(int i, int j)
    {
        opened = i;
        closed = j;
    }

    public void setOpened(int i)
    {
        opened = i;
    }

    public void setClosed(int i)
    {
        closed = i;
    }

    public int getOpened()
    {
        return opened;
    }

    public int getClosed()
    {
        return closed;
    }

    public int opened;
    public int closed;
    public int openedAndClosed;
}
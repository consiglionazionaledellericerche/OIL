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

 

package it.cnr.helpdesk.util;

import java.util.Collection;

public interface PageByPageIterator
{

    public abstract int getStart();

    public abstract void setStart(int i);

    public abstract String getPageName();

    public abstract void setPageName(String s);

    public abstract int getPageNumber();

    public abstract void setPageNumber(int i);

    public abstract int getPagesInNavigator();

    public abstract void setPagesInNavigator(int i);

    public abstract int getSize();

    public abstract void setSize(int i);

    public abstract int getTotalCount();

    public abstract void setTotalCount(int i);

    public abstract Collection getCollection();

    public abstract void setCollection(Collection collection);

    public abstract String printPageNavigator(int i);
}
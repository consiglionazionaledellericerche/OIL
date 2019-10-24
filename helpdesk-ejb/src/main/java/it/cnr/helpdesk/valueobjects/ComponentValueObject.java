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

 

package it.cnr.helpdesk.valueobjects;

import it.cnr.helpdesk.util.SearchingCollection;
import it.cnr.helpdesk.util.SearchingItem;
import java.io.Serializable;

public class ComponentValueObject
    implements Serializable
{

    public ComponentValueObject()
    {
        searchingCollection = null;
        searchingCollection = new SearchingCollection();
    }

    public void addSearchingItem(SearchingItem searchingitem)
    {
        searchingCollection.add(searchingitem);
    }

    public void resetSearchingCollection()
    {
        searchingCollection = new SearchingCollection();
    }

    public SearchingCollection getSearchingCollection()
    {
        return searchingCollection;
    }

    public void setSearchingCollection(SearchingCollection searchingCollection)
    {
        this.searchingCollection = searchingCollection;
    }

    private SearchingCollection searchingCollection;
}
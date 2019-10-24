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

import java.io.Serializable;
import java.util.*;

// Referenced classes of package it.cnr.helpdesk.util:
//            SearchingItem

public class SearchingSet extends ArrayList
    implements SearchingItem, Serializable
{

    public SearchingSet()
    {
        table = null;
        column = null;
    }

    public SearchingSet(String s, String s1)
    {
        table = null;
        column = null;
        table = s;
        column = s1;
    }

    public String toSQLString()
    {
        String s = null;
        Iterator iterator = iterator();
        if(iterator.hasNext())
            s = " and " + getTable() + "." + getColumn() + " in (";
        else
            s = "";
        while(iterator.hasNext()) 
        {
            s = s + itemList(iterator.next());
            if(iterator.hasNext())
                s = s + ",";
            else
                s = s + ") ";
        }
        return s;
    }

    private String itemList(Object obj)
    {
        String s = null;
        if(obj instanceof Integer)
            s = ((Integer)obj).toString();
        if(obj instanceof Long)
            s = ((Long)obj).toString();
        if(obj instanceof String)
            s = "'" + (String)obj + "'";
        return s;
    }

    public void setTable(String s)
    {
        table = s;
    }

    public String getTable()
    {
        return table;
    }

    public void setColumn(String s)
    {
        column = s;
    }

    public String getColumn()
    {
        return column;
    }

    public static void main(String args[])
    {
        SearchingSet searchingset = new SearchingSet();
        searchingset.add(new Integer(1));
        searchingset.add(new Integer(2));
        searchingset.add(new Integer(3));
        System.out.println(searchingset.toSQLString());
    }

    private String table;
    private String column;
}
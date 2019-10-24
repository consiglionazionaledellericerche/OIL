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

public class SearchingLike extends ArrayList
    implements SearchingItem, Serializable
{

    public SearchingLike()
    {
        table = null;
        column = null;
        caseSensitive = false;
    }

    public SearchingLike(String s, String s1)
    {
        table = null;
        column = null;
        caseSensitive = false;
        table = s;
        column = s1;
    }

    private String formatString(String s)
    {
        String s1 = s;
        byte byte0 = 39;
        if(s != null && s.indexOf(byte0) >= 0)
        {
            StringBuffer stringbuffer = new StringBuffer(s);
            boolean flag = false;
            int j = 0;
            while(!flag) 
            {
                int i = stringbuffer.toString().indexOf(byte0, j);
                if(i >= 0)
                {
                    stringbuffer.replace(i, i + 1, "''");
                    j = i + 2;
                }
                if(i < 0 || j > stringbuffer.length())
                    flag = true;
            }
            s1 = stringbuffer.toString();
        }
        return s1;
    }

    public String toSQLString()
    {
        String s = null;
        Iterator iterator = iterator();
        if(iterator.hasNext())
            s = " and ( ";
        else
            s = "";
        while(iterator.hasNext()) 
        {
            if(!caseSensitive)
                s = s + " upper(" + getTable() + "." + getColumn() + ") like upper(" + itemList(iterator.next()) + ")";
            else
                s = s + getTable() + "." + getColumn() + " like  " + itemList(iterator.next());
            if(iterator.hasNext())
                s = s + " or ";
            else
            	if(customTail!=null)
            		s = s + customTail;
            	s = s + " ) ";
        }
        return s;
    }

    private String itemList(Object obj)
    {
        String s = null;
        if(obj instanceof String)
            s = "'%" + formatString((String)obj) + "%'";
        return s;
    }

    public void setCaseSensitive(boolean flag)
    {
        caseSensitive = flag;
    }

    public boolean isCaseSensitive(boolean flag)
    {
        return flag;
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

    public String getCustomTail() {
		return customTail;
	}

	public void setCustomTail(String customTail) {
		this.customTail = customTail;
	}

	public static void main(String args[])
    {
        SearchingLike searchinglike = new SearchingLike();
        searchinglike.setTable("tabella");
        searchinglike.setColumn("colonna");
        searchinglike.add("pippo");
        searchinglike.add("pluto");
        searchinglike.add("paperino");
        searchinglike.setCustomTail(" or segnalazione.ID_SEGNALAZIONE IN (SELECT segnalazione FROM evento where upper(nota) like upper('%AICO%'))");
        System.out.println(searchinglike.toSQLString());
    }

    private String table;
    private String column;
    private boolean caseSensitive;
    private String customTail;
}
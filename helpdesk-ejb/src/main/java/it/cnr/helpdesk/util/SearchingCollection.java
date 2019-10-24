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

public class SearchingCollection extends ArrayList
    implements Serializable
{

    public SearchingCollection()
    {
    }

    public String toSQLString()
    {
        String s = "";
        for(Iterator iterator = iterator(); iterator.hasNext();)
            s = s + ((SearchingItem)iterator.next()).toSQLString();

        return s;
    }
}
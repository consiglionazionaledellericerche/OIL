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

 
// Source File Name:   PageByPageIteratorImpl.java

package it.cnr.helpdesk.util;

import java.io.*;
import java.util.Collection;

// Referenced classes of package it.cnr.helpdesk.util:
//            PageByPageIterator

public class PageByPageIteratorImpl
    implements PageByPageIterator, Externalizable
{

    public PageByPageIteratorImpl(int pageNumber, int size, String pageName, int pagesInNavigator)
    {
        if(pageNumber == 0)
        {
            start = 1;
            this.size = 0x7fffffff;
            allValues = true;
        } else
        {
            start = (pageNumber - 1) * size + 1;
            this.size = size;
            allValues = false;
        }
        this.pageNumber = pageNumber;
        totalCount = 0;
        objs = null;
        this.pageName = pageName;
        this.pagesInNavigator = pagesInNavigator;
    }

    public void writeExternal(ObjectOutput objectoutput)
        throws IOException
    {
    }

    public void readExternal(ObjectInput objectinput)
        throws IOException, ClassNotFoundException
    {
    }

    public String getPageName()
    {
        return pageName;
    }

    public void setPageName(String pageName)
    {
        this.pageName = pageName;
    }

    public int getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public int getPagesInNavigator()
    {
        return pagesInNavigator;
    }

    public void setPagesInNavigator(int pagesInNavigator)
    {
        this.pagesInNavigator = pagesInNavigator;
    }

    public int getStart()
    {
        return start;
    }

    public void setStart(int start)
    {
        this.start = start;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }

    public Collection getCollection()
    {
        return objs;
    }

    public void setCollection(Collection c)
    {
        objs = c;
    }

    public String printPageNavigator(int pageNumber)
    {
        String navigator = "";
        if(!allValues)
        {
            int numberOfPages = (int)Math.ceil((double)getTotalCount() / (double)getSize());
            int interval = Math.max(1, (int)Math.ceil((double)pageNumber / (double)pagesInNavigator));
            int lowerBoundInterval = (interval - 1) * pagesInNavigator + 1;
            int upperBoundInterval = Math.min(numberOfPages, interval * pagesInNavigator);
            int nextInterval = Math.min(numberOfPages, interval * pagesInNavigator + 1);
            if(numberOfPages > 1)
            {
                if(interval > 1)
                    navigator = navigator + "<a href=\"" + pageName + "page=" + (lowerBoundInterval - 1) + "\"><--</a>" + "\n";
                for(int i = lowerBoundInterval; i <= upperBoundInterval; i++)
                    navigator = navigator + "<a href=\"" + pageName + "page=" + i + "\">" + i + "</a>&nbsp;&nbsp;";

                if(upperBoundInterval < numberOfPages)
                    navigator = navigator + "<a href=\"" + pageName + "page=" + nextInterval + "\">--></a>" + "\n";
                navigator = navigator + "&nbsp;&nbsp; &nbsp;&nbsp; <a href=\"" + pageName + "page=0\">Lista completa (" + getTotalCount() + " elementi)</a>" + "\n";
            }
        } else
        {
            navigator = navigator + "&nbsp;&nbsp; &nbsp;&nbsp; <a href=\"" + pageName + "page=1\">Primi 10</a>" + "\n";
        }
        return navigator;
    }

    private int start;
    private int size;
    private int totalCount;
    private int pageNumber;
    private Collection objs;
    private String pageName;
    private int pagesInNavigator;
    private boolean allValues;
}
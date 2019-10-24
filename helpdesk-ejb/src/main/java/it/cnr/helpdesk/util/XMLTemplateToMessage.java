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

import java.util.Hashtable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLTemplateToMessage extends DefaultHandler
{

    public XMLTemplateToMessage()
    {
        contenuto = "";
        oggetto = "";
        tag2Value = null;
        inMessaggio = false;
        inOggetto = false;
        inContenuto = false;
        inTesto = false;
        tag2Value = new Hashtable();
    }

    public void startElement(String s, String s1, String s2, Attributes attributes)
        throws SAXException
    {
        System.out.println(s2);
        if(s2.equals("messaggio"))
            inMessaggio = true;
        else
        if(s2.equals("oggetto") && inMessaggio)
            inOggetto = true;
        else
        if(s2.equals("contenuto") && inMessaggio)
            inContenuto = true;
        else
        if(s2.equals("t") && inMessaggio && (inContenuto || inOggetto))
            inTesto = true;
        else
        if(tag2Value.containsKey(s2))
        {
            if(inContenuto)
                contenuto = contenuto + tag2Value.get(s2).toString();
            if(inOggetto)
                oggetto = oggetto + tag2Value.get(s2).toString();
        }
    }

    public void endElement(String s, String s1, String s2)
        throws SAXException
    {
        if(s2.equals("messaggio"))
            inMessaggio = false;
        if(s2.equals("oggetto") && inMessaggio)
            inOggetto = false;
        if(s2.equals("contenuto") && inMessaggio)
            inContenuto = false;
        if(s2.equals("t") && inMessaggio && (inContenuto || inOggetto))
            inTesto = false;
    }

    public void characters(char ac[], int i, int j)
    {
        String s = new String(ac, i, j);
        if(inOggetto & inTesto)
            oggetto = oggetto + s;
        if(inContenuto & inTesto)
            contenuto = contenuto + s;
    }

    public void endDocument()
    {
    }

    public void setTagValue(Object obj, Object obj1)
    {
        tag2Value.put(obj, obj1);
        System.out.println(obj.toString() + " " + obj1.toString());
    }

    public void getTagValue(Object obj, Object obj1)
    {
        tag2Value.put(obj, obj1);
    }

    public String getOggetto()
    {
        return oggetto;
    }

    public String getContenuto()
    {
        return contenuto;
    }

    private String contenuto;
    private String oggetto;
    private Hashtable tag2Value;
    private boolean inMessaggio;
    private boolean inOggetto;
    private boolean inContenuto;
    private boolean inTesto;
}
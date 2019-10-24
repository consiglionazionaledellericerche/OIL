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
/*
 * Created on 12-apr-2007
 *
 */
package it.cnr.helpdesk.util;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class XMLTemplateToJSP extends DefaultHandler {
	
	private String buffer = "";
	private int groups;
	private int count;
	private boolean sideMenu = false;
	private int pageLayout;
	private int menuLayout;
	private Hashtable fields= new Hashtable();

	public void startElement(String uri, String localName, String name, Attributes attr) throws SAXException {
		//System.out.println("buffer length:"+buffer.length()+"|qName:"+name);
		if(name.equalsIgnoreCase("menu")){
			buffer=buffer.concat("<table width='100%'>\n<tr>\n");
			if(attr.getValue("layout").equalsIgnoreCase("1")){		//layout con menu laterale di 180px e menu centrale per il restante spazio
				pageLayout = 1;
			}
		} else if(name.equalsIgnoreCase("side-menu")){
			sideMenu = true;
			buffer=buffer.concat("<td width='180' valign='top' height='400'>\n");
		} else if(name.equalsIgnoreCase("main-menu")){
			groups = Integer.parseInt(attr.getValue("groups"));
			count = 1;
			buffer=buffer.concat("<td valign='top' height='400'>\n");
			if(attr.getValue("layout").equalsIgnoreCase("1")){		//menu a 2 colonne con finestre equamente distribuite
				menuLayout = 1;
				buffer=buffer.concat("<table width='100%' border='0' cellspacing='3' cellpadding='0'>\n<tr valign='top'>\n<td width='50%'>\n"); 
			} else {
				//altri tipi di layout
			}				
		} else if(name.equalsIgnoreCase("group")){
			if(sideMenu){
				buffer=buffer.concat("<table width='100%' border='0' cellspacing='2' cellpadding='2'>\n<tr>\n<td class='bgheader'><span class='tableheader'>"+attr.getValue("title")+"</span></td>\n</tr>\n");
			}else if(count++ < (groups/2+1))
				buffer=buffer.concat("<table width='100%' border='3' cellspacing='0' cellpadding='2'>\n<tr>\n<td bordercolor='#6699CC' class='bgheader'><div class='tableheader'>"+attr.getValue("title")+"</div></td>\n</tr>\n<tr>\n<td bordercolor='#6699CC'><ul>\n");
			else {
				count = 2;
				buffer=buffer.concat("</td>\n<td><table width='100%' border='3' cellspacing='0' cellpadding='2'>\n<tr>\n<td bordercolor='#6699cc' class='bgheader'><div class='tableheader'>"+attr.getValue("title")+"</div></td>\n</tr>\n<tr>\n<td bordercolor='#6699CC'><ul>\n");
			}
		} else if(name.equalsIgnoreCase("item")){
			if(sideMenu){
				String tgt = attr.getValue("target");
				buffer=buffer.concat("<tr>\n<td class='bgdisable'><a href='"+attr.getValue("href")+"' "+(tgt==null?"":"target='"+tgt+"'")+" class='menuitem'>\n");
			} else
				buffer=buffer.concat("<li class='text'><a href='"+attr.getValue("href")+"'>\n");
		} else if(name.equalsIgnoreCase("info")){
			buffer=buffer.concat("<span class='"+attr.getValue("class")+"'>");
		} else if(name.equalsIgnoreCase("messages")){
			String msgStyle = attr.getValue("class");
			Collection msg = (Collection)fields.get("messaggi");
			buffer=buffer.concat("<tr valign='top' class='"+msgStyle+"'>\n<td class='bordertab'><ul>\n");
			for(Iterator i = msg.iterator(); i.hasNext();){
				buffer=buffer.concat("<li class='"+msgStyle+"'>\n"+i.next()+"\n<br><br></li>\n");
			}
		} else {										//aggiunge i tag cercando nell'hashmap
			buffer=buffer.concat(""+fields.get(name));
		}
	}

	public void endElement(String uri, String localName, String name) throws SAXException {
		//System.out.println("uri:"+uri+"|localName:"+localName+"|qName:"+name);
		if(name.equalsIgnoreCase("menu")){
			buffer=buffer.concat("</tr>\n</table>\n");
		} else if(name.equalsIgnoreCase("side-menu")){
			sideMenu = false;
			buffer=buffer.concat("</td>\n");
		} else if(name.equalsIgnoreCase("main-menu")){
			if(menuLayout==1)
				buffer=buffer.concat("</td>\n</tr>\n</table></td>\n");
			else {
				//altro
			}
		} else if(name.equalsIgnoreCase("group")){
			if(sideMenu)
				buffer=buffer.concat("</table>\n<br>");
			else {
				buffer=buffer.concat("</ul></td>\n</tr>\n</table>\n<br>");
			}
		} else if(name.equalsIgnoreCase("item")){
			if(sideMenu)
				buffer=buffer.concat("\n</a></td>\n</tr>\n");
			else
				buffer=buffer.concat("\n</a></li>\n");
		} else if(name.equalsIgnoreCase("info")){
			buffer=buffer.concat("</span><br>\n");
		} else if(name.equalsIgnoreCase("messages")){
			buffer=buffer.concat("</ul>\n</td>\n</tr>\n");
		}
	}
	
    public void characters(char ac[], int i, int j) {
        buffer=buffer.concat(new String(ac, i, j));
    }
        
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
		//null
	}
	
	public String getBuffer() {
		return buffer;
	}
	
	public Object getFields(Object key) {
		return fields.get(key);
	}
	
	public void setFields(Object key, Object obj) {
		fields.put(key, obj);
	}
	
}

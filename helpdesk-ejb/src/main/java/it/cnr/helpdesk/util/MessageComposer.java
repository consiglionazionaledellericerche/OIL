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
 * Created on 28-giu-2006
 *
 */
package it.cnr.helpdesk.util;

import it.cnr.helpdesk.EventManagement.exceptions.EventDAOException;
import it.cnr.helpdesk.EventManagement.javabeans.Event;
import it.cnr.helpdesk.MailManagement.valueobjects.MailItem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class MessageComposer {
	
	public static MailItem compose(EventValueObject evo, HashMap fields, Collection destinatari, String instance) throws EventDAOException {
		String template;
		Iterator is = fields.keySet().iterator();
		String key, val;
	    XMLTemplateToMessage xmltemplatetomessage = new XMLTemplateToMessage();
	    while(is.hasNext()){
	    	key = (String)is.next();
	    	val = "" + fields.get(key);
	    	xmltemplatetomessage.setTagValue(key, val);
	    }
	    try {

	    	template = Event.getTemplate(evo.getEventType(), instance);
	        SAXParserFactory saxparserfactory = SAXParserFactory.newInstance();
	        SAXParser saxparser = saxparserfactory.newSAXParser();
	        saxparser.parse(new InputSource(new StringReader(template)), xmltemplatetomessage);
	    } catch(Exception e) {
	        System.out.println("errore durante il parsing xml: " + e.getMessage());
	        e.printStackTrace();
	    } 
	    ArrayList address = new ArrayList();
	    UserValueObject uvo=null;
	    System.out.println("num.destinatari: "+destinatari.size());
	    for(Iterator it=destinatari.iterator();it.hasNext();){
	    	uvo=(UserValueObject)it.next();
	    	System.out.println(uvo.getFirstName()+"|"+uvo.getFamilyName()+"|"+uvo.getEmail()+"|"+uvo.getEnabled()+"|"+uvo.getMailStop());
	    	if (uvo!=null && uvo.getEnabled().startsWith("y") && uvo.getMailStop().startsWith("n"))		//controlla che l'utente non sia disabilitato od abbia scelto il blocco email di notifica
				   address.add(uvo.getEmail());
	    }
	    MailItem mailitem = new MailItem(address, xmltemplatetomessage.getOggetto(), xmltemplatetomessage.getContenuto(), instance);
	    //log(this, "oggetto=" + xmltemplatetomessage.getOggetto() + "\n" + "contenuto=" + xmltemplatetomessage.getContenuto());
	    return mailitem;
	}
	
	public static MailItem compose(HashMap fields, Collection destinatari, String template, String instance) {
		String key, val;
	    XMLTemplateToMessage xmltemplatetomessage = new XMLTemplateToMessage();
	    for(Iterator is = fields.keySet().iterator(); is.hasNext();){
	    	key = (String)is.next();
	    	val = "" + fields.get(key);
	    	xmltemplatetomessage.setTagValue(key, val);
	    }
	    try {
	        SAXParserFactory saxparserfactory = SAXParserFactory.newInstance();
	        SAXParser saxparser = saxparserfactory.newSAXParser();
	        saxparser.parse(new InputSource(new StringReader(template)), xmltemplatetomessage);
	    } catch(Exception e) {
	        System.out.println("errore durante il parsing xml: " + e.getMessage());
	        e.printStackTrace();
	    } 
	    ArrayList address = new ArrayList();
	    UserValueObject uvo=null;
	    System.out.println("num.destinatari: "+destinatari.size());
	    for(Iterator it=destinatari.iterator();it.hasNext();){
	    	uvo=(UserValueObject)it.next();
	    	System.out.println(uvo.getFirstName()+"|"+uvo.getFamilyName()+"|"+uvo.getEmail()+"|"+uvo.getEnabled()+"|"+uvo.getMailStop());
	    	if (uvo!=null && uvo.getEnabled().startsWith("y") && uvo.getMailStop().startsWith("n"))		//controlla che l'utente non sia disabilitato od abbia scelto il blocco email di notifica
				   address.add(uvo.getEmail());
	    }
	    MailItem mailitem = new MailItem(address, xmltemplatetomessage.getOggetto(), xmltemplatetomessage.getContenuto(), instance);
	    //log(this, "oggetto=" + xmltemplatetomessage.getOggetto() + "\n" + "contenuto=" + xmltemplatetomessage.getContenuto());
	    return mailitem;
	}
}

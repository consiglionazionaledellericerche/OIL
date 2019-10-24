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
 * Created on 28-ott-2008
 */
package it.cnr.helpdesk.UOManagement.javabeans;

import it.cnr.helpdesk.UOManagement.ejb.UOManagement;
import it.cnr.helpdesk.UOManagement.ejb.UOManagementHome;
import it.cnr.helpdesk.UOManagement.exceptions.UOEJBException;
import it.cnr.helpdesk.UOManagement.exceptions.UOJBException;
import it.cnr.helpdesk.UOManagement.valueobjects.UOValueObject;
import it.cnr.helpdesk.javabeans.HelpDeskJB;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.util.Collection;

/**
 * @author Marco Trischitta
 *
 */
public class UO extends HelpDeskJB {

    public Collection getStructures(String instance) throws UOJBException {
        Collection coll = null;
        try {
			UOManagementHome uomh = lookupHome();
			UOManagement uom = (UOManagement)PortableRemoteObject.narrow(uomh.create(), it.cnr.helpdesk.UOManagement.ejb.UOManagement.class);
			coll = uom.getStructures(instance);
        }
        catch (UOEJBException e) {
        	throw new UOJBException(/*inserire messaggio*/);        	
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	throw new UOJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione.");
		}
        return coll;
    }
    
    public Collection getAllStructures(String instance) throws UOJBException {
        Collection coll = null;
        try {
			UOManagementHome uomh = lookupHome();
			UOManagement uom = (UOManagement)PortableRemoteObject.narrow(uomh.create(), it.cnr.helpdesk.UOManagement.ejb.UOManagement.class);
			coll = uom.getAllStructures(instance);
        }
        catch (UOEJBException e) {
        	throw new UOJBException(/*inserire messaggio*/);        	
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	throw new UOJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione.");
		}
        return coll;
    }
    
    public UOValueObject getOneStructure(String cod, String instance) throws UOJBException {
        UOValueObject uvo = new UOValueObject();
        try {
			UOManagementHome uomh = lookupHome();
			UOManagement uom = (UOManagement)PortableRemoteObject.narrow(uomh.create(), it.cnr.helpdesk.UOManagement.ejb.UOManagement.class);
			uvo = uom.getOneStructure(cod, instance);
        }
        catch (UOEJBException e) {
        	throw new UOJBException(/*inserire messaggio*/);        	
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	throw new UOJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione.");
		}
        return uvo;
    }
    
    public String executeInsert(UOValueObject uovob, String instance) throws UOJBException {
    	String cod = ""; 
    	try {
			UOManagementHome uomh = lookupHome();
			UOManagement uom = (UOManagement)PortableRemoteObject.narrow(uomh.create(), it.cnr.helpdesk.UOManagement.ejb.UOManagement.class);
			cod = uom.executeInsert(uovob, instance);
        }
        catch (UOEJBException e) {
        	throw new UOJBException(/*inserire messaggio*/);        	
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	throw new UOJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione.");
		}
        return cod;    	
    }
    
    public void executeUpdate(UOValueObject uovob, String instance) throws UOJBException{
    	 try {
			UOManagementHome uomh = lookupHome();
			UOManagement uom = (UOManagement)PortableRemoteObject.narrow(uomh.create(), it.cnr.helpdesk.UOManagement.ejb.UOManagement.class);
			uom.executeUpdate(uovob, instance);
        }
        catch (UOEJBException e) {
        	throw new UOJBException(/*inserire messaggio*/);        	
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	throw new UOJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione.");
		}
    }
    
    public void executeDelete(UOValueObject uovob, String instance) throws UOJBException{
    	 try {
			UOManagementHome uomh = lookupHome();
			UOManagement uom = (UOManagement)PortableRemoteObject.narrow(uomh.create(), it.cnr.helpdesk.UOManagement.ejb.UOManagement.class);
			uom.executeDelete(uovob, instance);
        }
        catch (UOEJBException e) {
        	throw new UOJBException(/*inserire messaggio*/);        	
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	throw new UOJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione.");
		}
    }
    
    public void enabled(String cod, String instance) throws UOJBException{
    	 try {
			UOManagementHome uomh = lookupHome();
			UOManagement uom = (UOManagement)PortableRemoteObject.narrow(uomh.create(), it.cnr.helpdesk.UOManagement.ejb.UOManagement.class);
			uom.enabled(cod, instance);
        }
        catch (UOEJBException e) {
        	throw new UOJBException(/*inserire messaggio*/);        	
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	throw new UOJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione.");
		}
    }
    
    public void disabled(String cod, String instance) throws UOJBException{
    	 try {
			UOManagementHome uomh = lookupHome();
			UOManagement uom = (UOManagement)PortableRemoteObject.narrow(uomh.create(), it.cnr.helpdesk.UOManagement.ejb.UOManagement.class);
			uom.disabled(cod, instance);
        }
        catch (UOEJBException e) {
        	throw new UOJBException(/*inserire messaggio*/);        	
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	throw new UOJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione.");
		}
    }
    
    private UOManagementHome lookupHome() throws UOJBException {
    	UOManagementHome uomanagementhome = null;
    	try {
    		Context context = getInitialContext();
			if(context == null) {
				log(this, "Initial Context=null");
			} 
			else {
            	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
                Object obj = context.lookup(jndiPrefix+"UOManagement!it.cnr.helpdesk.UOManagement.ejb.UOManagementHome");
				uomanagementhome = (UOManagementHome)PortableRemoteObject.narrow(obj, it.cnr.helpdesk.UOManagement.ejb.UOManagementHome.class);
			}
    	}
		catch (NamingException e) {
			e.printStackTrace();
			throw new UOJBException(e.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		catch(UOEJBException e) {
			throw new UOJBException(e.getMessage());
		}
		return uomanagementhome;
    	
    }
    
}
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
 * Created on 19-feb-2007
 *
 */
package it.cnr.helpdesk.LdapManagemet.dao;

import it.cnr.helpdesk.LdapManagemet.exceptions.LdapDAOException;
import it.cnr.helpdesk.UserManagement.exceptions.LoginFailureException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPJSSESecureSocketFactory;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPSearchResults;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class LdapDAO {
	
	static final String ldapHost = System.getProperty("it.cnr.ldap.host1");
	static final String ldapHost2 = System.getProperty("it.cnr.ldap.host2");
	static final String loginDN = System.getProperty("it.cnr.ldap.principal");
	static final String password = "";
	static final String appName = "";
	static final String domain = System.getProperty("it.cnr.ldap.domain");
	boolean readonly = false;
	static final boolean isSsl = false;
	LDAPConnection connection;
	int ldapPort = LDAPConnection.DEFAULT_PORT;
	int ldapVersion = LDAPConnection.LDAP_V3;
	
	public void createConnection() throws LdapDAOException {

        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        LDAPJSSESecureSocketFactory ssf = new LDAPJSSESecureSocketFactory();
        LDAPConnection lc = null;
 
        if (isSsl)
         this.connection = new LDAPConnection(ssf);
        else
         this.connection = new LDAPConnection();
		try {
			connection.connect( ldapHost, ldapPort );
			System.out.println("Connessione LDAP con "+ldapHost+":"+ldapPort+" vers."+ldapVersion+" - DN:"+loginDN+" paswd:"+password.getBytes("UTF8"));
			connection.bind( ldapVersion, loginDN, password.getBytes("UTF8") );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new LdapDAOException(e.getMessage());
		} catch (LDAPException e) {
			e.printStackTrace();
			try {
				connection.connect( ldapHost2, ldapPort );
				System.out.println("Connessione ALTERNATIVA LDAP con "+ldapHost2+":"+ldapPort+" vers."+ldapVersion+" - DN:"+loginDN+" paswd:"+password.getBytes("UTF8"));
				connection.bind( ldapVersion, loginDN, password.getBytes("UTF8") );
				this.readonly = true;
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new LdapDAOException(e1.getMessage()); 
			}
		}
	}
	
	public void closeConnection() throws LdapDAOException{
		try {
			connection.disconnect();
			this.readonly = false;
		} catch (LDAPException e) {
			e.printStackTrace();
			throw new LdapDAOException(e.getMessage());
		}
	}
	
	public String checkLogin(String idLdap, String password) throws LoginFailureException, LdapDAOException {
		String objectDN= "cn=" + idLdap + "," + domain;
		try {
			LDAPSearchResults searchResults = connection.search( domain , LDAPConnection.SCOPE_SUB, "uid="+idLdap, null, false);
			if (!searchResults.hasMore()) 
				throw new LoginFailureException("UserID errata");
			LDAPEntry nextEntry = searchResults.next();
			objectDN = nextEntry.getDN();
			LDAPAttribute attr = new LDAPAttribute("userPassword", password );
			if(connection.compare( objectDN, attr )){
				String cnrapp3 = nextEntry.getAttribute("cnrapp3").getStringValue();
				if(cnrapp3==null || cnrapp3.equalsIgnoreCase("no"))
					throw new LoginFailureException("User non autorizzato");
		        return cnrapp3;
			} else
				throw new LoginFailureException("Password errata");
		} catch (LDAPException e) {
			e.printStackTrace();
			throw new LdapDAOException(e.getMessage());
		} 
	}
	
	public String searchDN(String idLdap) throws LoginFailureException, LdapDAOException {
		String objectDN= "cn=" + idLdap + "," + domain;
		try {
			LDAPSearchResults searchResults = connection.search( domain , LDAPConnection.SCOPE_SUB, "uid="+idLdap, null, false);
			if (!searchResults.hasMore()) 
				throw new LoginFailureException("UserID errata");
			LDAPEntry nextEntry = searchResults.next();
			return nextEntry.getDN();
		} catch (LDAPException e) {
			e.printStackTrace();
			throw new LdapDAOException(e.getMessage());
		} 
	}
	
	public boolean checkPassword(String DN, String passwd) throws LdapDAOException, LoginFailureException {
		try {
			connection.bind( ldapVersion, DN, passwd.getBytes("UTF8") );
			System.out.println("password utente riconosciuta");
			connection.bind( ldapVersion, loginDN, password.getBytes("UTF8") );	
			return(true);
		} catch (LDAPException e) {
			if(e.getResultCode()==LDAPException.INVALID_CREDENTIALS )
				throw new LoginFailureException("Password errata");
			throw new LdapDAOException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new LdapDAOException(e.getMessage());
		} 
	}
	
	public String getApplAttribute(String idLdap) throws LdapDAOException {
		String objectDN= "cn=" + idLdap + "," + domain;
		try {
			LDAPSearchResults searchResults = connection.search( domain , LDAPConnection.SCOPE_SUB, "uid="+idLdap, null, false);
			if (!searchResults.hasMore()) 
				throw new LdapDAOException("UserID non trovata");
			LDAPEntry nextEntry = searchResults.next();
			if(nextEntry.getAttribute("cnrapp3")!=null)
				return nextEntry.getAttribute("cnrapp3").getStringValue();
			else
				return null;
		} catch (LDAPException e) {
			e.printStackTrace();
			throw new LdapDAOException(e.getMessage());
		} 
	}
	
	public String getApplAttribute(String DN, String attrName) throws LdapDAOException{
		try{
			LDAPEntry entry = connection.read(DN);
			if(entry.getAttribute(attrName)!=null)
				return entry.getAttribute(attrName).getStringValue();
			else
				return null;
		} catch (LDAPException e) {
			e.printStackTrace();
			throw new LdapDAOException(e.getMessage());
		} 
	}
	
	public void setApplAttribute(String idLdap, String attr) throws LdapDAOException {
		String objectDN= "cn=" + idLdap + "," + domain;
		if(this.readonly) throw new LdapDAOException("LDAP server di replica, impossibile sottomettere modifiche", "LDAP server di replica, impossibile sottomettere modifiche");
		try {
			LDAPSearchResults searchResults = connection.search( domain , LDAPConnection.SCOPE_SUB, "uid="+idLdap, null, false);
			LDAPEntry nextEntry = null;
			if (!searchResults.hasMore())
				throw new LdapDAOException("UserID non trovata");
			nextEntry = searchResults.next();
			objectDN = nextEntry.getDN();

	        LDAPAttribute hlpdsk = nextEntry.getAttribute(appName);
	        LDAPModification[]  modAppAbil = new LDAPModification[1];
	        LDAPAttribute newAttr = new LDAPAttribute(appName, attr);
	        if (hlpdsk==null)
	        	modAppAbil[0] = new LDAPModification( LDAPModification.ADD, newAttr);
	        else
	        	modAppAbil[0] = new LDAPModification( LDAPModification.REPLACE, newAttr);
        	connection.modify(objectDN, modAppAbil);

		} catch (LDAPException e) {
			e.printStackTrace();
			throw new LdapDAOException(e.getMessage());
		} 
	}
	
	public Collection getAllowedUser(String item) throws LdapDAOException {
		Collection ids = new ArrayList();
		try {
			LDAPSearchResults searchResults = connection.search( domain , LDAPConnection.SCOPE_SUB, "(|(cnrapp3=*;"+item+";*)(cnrapp3="+item+";*))", null, false);
			LDAPEntry nextEntry;
			while(searchResults.hasMore()){
				nextEntry = searchResults.next();
				ids.add(nextEntry.getAttribute("uid").getStringValue());
			}
		} catch (LDAPException e) {
			e.printStackTrace();
			throw new LdapDAOException(e.getMessage());
		}
		System.out.println("la ricerca su LDAP dell'id: "+item+" ha prodotto "+ ids.size() +" risultati");
		return ids;
	}
	
	public boolean isReadonly() {
		return readonly;
	}
}

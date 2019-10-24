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
 * Created on 28-feb-2007
 *
 */
package it.cnr.helpdesk.LdapManagemet.javabeans;

import it.cnr.helpdesk.LdapManagemet.dao.LdapDAO;
import it.cnr.helpdesk.LdapManagemet.exceptions.LdapDAOException;
import it.cnr.helpdesk.LdapManagemet.exceptions.LdapJBException;
import it.cnr.helpdesk.UserManagement.exceptions.LoginFailureException;
import it.cnr.helpdesk.javabeans.HelpDeskJB;

import java.util.Collection;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class Ldap extends HelpDeskJB {

	public String login(String idLdap, String passwd, String instance) throws LdapJBException, LoginFailureException {
		LdapDAO dao = new LdapDAO();
		String cnrapp3="";
		try {
			dao.createConnection();
			//cnrapp3 = dao.checkLogin(idLdap,passwd);
			String DN = dao.searchDN(idLdap);
			System.out.println(DN);
			if(dao.checkPassword(DN, passwd)){
				cnrapp3 = dao.getApplAttribute(DN,"cnrapp3");
				System.out.println("attributo: "+cnrapp3);
			}
			dao.closeConnection();
			if(cnrapp3==null || cnrapp3.equalsIgnoreCase("no"))
				throw new LoginFailureException("Utente non autorizzato");
		} catch (LdapDAOException e) {
			e.printStackTrace();
			throw new LdapJBException(e);
		} catch (LoginFailureException e){
			System.out.println(e.getMessage());
			throw e;
		}
		return cnrapp3;
	}
	
	public boolean validUserid(String idLdap) throws LdapJBException{
		LdapDAO dao = new LdapDAO();
		try {
			dao.createConnection();
			dao.searchDN(idLdap);
		}catch(LdapDAOException e){
			e.printStackTrace();
			throw new LdapJBException();
		} catch (LoginFailureException e) {
			return false;
		}
		return true;
	}
	
	
	public Collection getAllowedUser(String idHd, String instance) throws LdapJBException {
		Collection users = null;
		LdapDAO dao = new LdapDAO();
		try {
			dao.createConnection();
			users = dao.getAllowedUser(instance+":"+idHd);
			dao.closeConnection();
		} catch (LdapDAOException e){
			e.printStackTrace();
		}
		return users;
	}
	
	public void allowUser(String idHd, String idLdap, String instance) throws LdapJBException {
		String users = null;
		LdapDAO dao = new LdapDAO();
		try {
			dao.createConnection();
			users = dao.getApplAttribute(idLdap);
			System.out.println("Autorizzo "+idLdap+" su "+instance+":"+idHd+" ("+users+")");
			if(users==null || users.equalsIgnoreCase("no"))
				dao.setApplAttribute(idLdap, instance+":"+idHd + ";");
			else if(!(users.indexOf(";"+instance+":"+idHd+";")!=-1 || users.startsWith(instance+":"+idHd+";"))){
				dao.setApplAttribute(idLdap, users + instance+":"+idHd + ";");
			}else
				System.out.println(idLdap+" su "+instance+":"+idHd+" già autorizzato");
			dao.closeConnection();
		} catch (LdapDAOException e){
			e.printStackTrace();
		}
	}
	
	public void disallowUser(String idHd, String idLdap, String instance) throws LdapJBException {
		String users = null;
		LdapDAO dao = new LdapDAO();
		try {
			dao.createConnection();
			users = dao.getApplAttribute(idLdap);
			System.out.println("Non autorizzo "+idLdap+" su "+instance+":"+idHd+" ("+users+")");
			if(users.indexOf(";"+instance+":"+idHd+";")!=-1)
				dao.setApplAttribute(idLdap, users.replaceFirst(";" + instance+":"+idHd + ";",";"));
			else if(users.startsWith(instance+":"+idHd+";")){
				if(users.equalsIgnoreCase(instance+":"+idHd+";"))
					dao.setApplAttribute(idLdap, "no");
				else
					dao.setApplAttribute(idLdap, users.replaceFirst(instance+":"+idHd + ";",""));
			} else
				System.out.println(idLdap+" su "+instance+":"+idHd+" non era autorizzato");
			dao.closeConnection();
		} catch (LdapDAOException e){
			e.printStackTrace();
		}
	}
	
}

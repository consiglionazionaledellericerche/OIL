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
 * Created on 17-apr-2005
 *


 */
package it.cnr.helpdesk.security.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.ArrayList;
import it.cnr.helpdesk.dao.SecuritySettingsDAO;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;

/**
 * @author Roberto Puccinelli
 *


 */
public class SecurityHelper {
	private HashMap acl;
	

	public void loadAcl(String instance){
		SecuritySettingsDAO ssDAO=DAOFactory.getDAOFactoryByProperties().getSecuritySettingsDAO();
		try {
			ssDAO.createConnection(instance);
			acl=ssDAO.getSecuritySettings();
			ssDAO.closeConnection();
		} catch (HelpDeskDAOException hde) {
			
		}
	}
	/**
	 * @return Returns the acls.
	 */
	public HashMap getAcls() {
		return acl;
	}
	/**
	 * @param acls The acls to set.
	 */
	public void setAcls(HashMap acl) {
		this.acl = acl;
	}
	
	public boolean isAllowed(int profile, String uri) {
		Integer Profile=new Integer(profile);
		ArrayList profiles;
		if (acl.containsKey(uri)){
			System.out.println("Acl contiene la chiave "+uri);
			profiles=(ArrayList)acl.get(uri);
			if (profiles.indexOf(Profile)!=-1){
				System.out.println("Permesso concesso.");
				return true;
			} else {
				System.out.println("Permesso negato.");
				return false;
			}
		} else 
			System.out.println("Acl non contiene la chiave "+uri);
		return false;
	}

    public static String getHexDigest(String plain, String algorithmName){
    	byte[] buffer = plain.getBytes();
    	byte[] digest = null;
    	try {
	     	MessageDigest algorithm = MessageDigest.getInstance(algorithmName);
	   		algorithm.reset();
	   		algorithm.update(buffer);
	   		digest = algorithm.digest();
    	} catch (NoSuchAlgorithmException e){
    		System.out.println("Algoritmo non supportato: " + algorithmName);
    	}
    	StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<digest.length;i++) {
    		String hex = "0"+Integer.toHexString(0xFF & digest[i]);
    	    hexString.append(hex.substring(hex.length()-2));
    	}

    	return (hexString.toString());

    }
}

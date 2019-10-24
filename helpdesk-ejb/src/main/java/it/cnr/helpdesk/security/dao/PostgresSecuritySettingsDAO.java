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
 * Created on 18-apr-2005
 *


 */
package it.cnr.helpdesk.security.dao;

import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.security.exceptions.SecurityDAOException;
import it.cnr.helpdesk.dao.SecuritySettingsDAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;

import java.util.HashMap;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * @author Roberto Puccinelli
 *


 */
public class PostgresSecuritySettingsDAO extends HelpDeskDAO implements SecuritySettingsDAO {
	
	public void createConnection(String instance) throws it.cnr.helpdesk.security.exceptions.SecurityDAOException {
		try {
			super.createConnection(instance);
		} catch (HelpDeskDAOException e){
			throw new SecurityDAOException(e.getMessage(),e.getUserMessage());
		}
	}
	
	public void closeConnection() throws it.cnr.helpdesk.security.exceptions.SecurityDAOException {
		try {
			super.closeConnection();
		} catch (HelpDeskDAOException e){
			throw new SecurityDAOException(e.getMessage(),e.getUserMessage());
		}
	}
	
	public HashMap getSecuritySettings() throws SecurityDAOException {
		String query="select uri, profile_id from page_security_mappings";
		HashMap acl=new HashMap();
		ResultSet rs=null;
		try {
			log(this, query);
			rs=execQuery(query);
			String uri;
			Integer profilo;
			ArrayList profileList;
			while (rs.next()){
				uri=rs.getString(1);
				profilo=new Integer(rs.getInt(2));
				if (!acl.containsKey(uri)) {
					profileList=new ArrayList();
					profileList.add(profilo);
					acl.put(uri,profileList);
				} else {
					profileList=(ArrayList) acl.get(uri);
					profileList.add(profilo);
				}
			}

		} catch (SQLException se) {
			se.printStackTrace();
			String userMessage="Si è verificato un problema nel caricamento dell'Access Control List delle pagine";
			log(this,userMessage);
			throw new SecurityDAOException(se.getMessage(),userMessage);
		}
		return acl;
	}

}

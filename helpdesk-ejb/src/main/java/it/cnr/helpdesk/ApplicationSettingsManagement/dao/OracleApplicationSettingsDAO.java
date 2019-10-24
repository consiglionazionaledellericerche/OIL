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

 

package it.cnr.helpdesk.ApplicationSettingsManagement.dao;

import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.dao.ApplicationSettingsDAO;
import it.cnr.helpdesk.exceptions.ApplicationSettingsDAOException;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;
import java.sql.*;
import java.util.*;

// Referenced classes of package it.cnr.helpdesk.dao:
//            HelpDeskDAO

public class OracleApplicationSettingsDAO extends HelpDeskDAO implements ApplicationSettingsDAO
{

    public OracleApplicationSettingsDAO()
    {
        con = null;
    }
    
    public void createConnection(String instance) throws ApplicationSettingsDAOException {
    	try	{
    		super.createConnection(instance);
    	} catch(HelpDeskDAOException helpdeskdaoexception) {
    		throw new ApplicationSettingsDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
    	}
    }

    public void closeConnection()
        throws ApplicationSettingsDAOException
    {
        try
        {
            super.closeConnection();
        }
        catch(HelpDeskDAOException helpdeskdaoexception)
        {
            throw new ApplicationSettingsDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
        }
    }

    public Vector getId2DescriptionStateMapping()
        throws ApplicationSettingsDAOException
    {
        Vector vector = new Vector();
        String s = "select id_stato,descrizione from stato order by id_stato ";
        try
        {
            log(s);
            for(ResultSet resultset = execQuery(s); resultset.next(); vector.add(resultset.getString(2)));
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'accesso al database. " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ApplicationSettingsDAOException(sqlexception.getMessage(), "Si \350 verificato un errore nell'accesso al database. Contattare l'assistenza.");
        }
        return vector;
    }
    
    public Vector getId2DescriptionPriorityMapping()
    	throws ApplicationSettingsDAOException 
    {
        Vector vector = new Vector();
        String s = "select livello,descrizione,predefinito from priorita order by livello ";
        try
        {
            log(s);
            for(ResultSet resultset = execQuery(s); resultset.next(); vector.add(resultset.getString(3)+":" + resultset.getString(2)));
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'accesso al database. " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ApplicationSettingsDAOException(sqlexception.getMessage(), "Si \350 verificato un errore nell'accesso al database. Contattare l'assistenza.");
        }
        return vector;
    }


    /*public void updateEmailSettings(Map map, String s)
        throws ApplicationSettingsDAOException
    {
        String s1 = "update EMAILSETINGS set SMTPHOST='" + formatString((String)map.get("SMTPHOST")) + "' " + " USERID='" + formatString((String)map.get("USERID")) + "' " + " PASSWORD=" + formatString((String)map.get("PASSWORD")) + "' " + " FROMEMAIL='" + formatString((String)map.get("FROMEMAIL")) + "' " + " FROMNAME='" + formatString((String)map.get("FROMNAME")) + "' " + " where ACCOUNTID=" + s;
        try
        {
            log(this, s1);
            execUpdQuery(s1);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione dello statement \"" + s1 + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ApplicationSettingsDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il cambiamento della Faq indicata. Contattare l'assistenza.");
        }
    }*/

    /*public Map getMailSettingsDetail(String s)
        throws ApplicationSettingsDAOException
    {
        HashMap hashmap = new HashMap();
        String s1 = " select MAILSETTINGS.SMTPHOST,MAILSETTINGS.USERID,MAILSETTINGS.PASSWORD,MAILSETTINGS.FROMEMAIL,MAILSETTINGS.FROMNAME  from MAILSETTINGS   where MAILSETTINGS.ACCOUNTID='" + s + "' ";
        try
        {
            ResultSet resultset = execQuery(s1);
            if(resultset.next())
            {
                hashmap.put("SMTPHOST", resultset.getString(1));
                hashmap.put("USERID", resultset.getString(2));
                hashmap.put("PASSWORD", resultset.getString(3));
                hashmap.put("FROMEMAIL", resultset.getString(4));
                hashmap.put("FROMNAME", resultset.getString(5));
            }
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s1 + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ApplicationSettingsDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero delle informazioni sulla categoria indicata. Contattare l'assistenza.");
        }
        return hashmap;
    }*/

    private Connection con;
    
    public String getProperty(String key) throws ApplicationSettingsDAOException {
        String s1 = "SELECT value FROM main_settings where name = '" + key + "'";
        String value = null;
        try {
            ResultSet resultset = execQuery(s1);
            if(resultset.next()) {
            	value = resultset.getString(1);
            }
        } catch(SQLException sqlexception) {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s1 + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ApplicationSettingsDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della proprietà indicata. Contattare l'assistenza.");
        }
    	return value;
    }

public HashMap<String, String> getAllProperties() throws ApplicationSettingsDAOException {
	String s1 = "SELECT name, value FROM main_settings ORDER BY name";
	HashMap<String, String> map = new HashMap<String, String>();
	try {
		ResultSet resultset = execQuery(s1);
		while(resultset.next()) {
			map.put(resultset.getString(1), resultset.getString(2));
		}
	} catch(SQLException sqlexception) {
		log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s1 + "\". " + sqlexception.getMessage());
		sqlexception.printStackTrace();
		throw new ApplicationSettingsDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della proprietà indicata. Contattare l'assistenza.");
	}
	return map;
}

    public void setProperty(String key, String value) throws ApplicationSettingsDAOException {
    	String s1 = "";
    	int i=0;
    	try {
    		if(getProperty(key) == null) {
    			String s = "select seq_setting.nextval from dual";
    			ResultSet resultset = execQuery(s);
    			if(resultset.next())
    				i = resultset.getInt(1);
    			s1 = "INSERT INTO main_settings(id_main_settings, name, value) VALUES (" + i + ", '" + key + "', '" + value + "')";
    		} else
    			s1 = "UPDATE main_settings SET value='" + value + "' WHERE name = '" + key + "'";
    		log(this, s1);
    		execUpdQuery(s1);
    	} catch(SQLException sqlexception){
    		log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s1 + "\". " + sqlexception.getMessage());
    		sqlexception.printStackTrace();
    		throw new ApplicationSettingsDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la scrittura della proprietà indicata. Contattare l'assistenza.");
    	}
    }

    public void removeProperty(String key) throws ApplicationSettingsDAOException {
    	String s1 = "DELETE FROM main_settings WHERE name = '" + key + "'";
    	try {
    		log(this, s1);
    		execUpdQuery(s1);
    	} catch(SQLException sqlexception){
    		log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s1 + "\". " + sqlexception.getMessage());
    		sqlexception.printStackTrace();
    		throw new ApplicationSettingsDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la cancellazione della proprietà indicata. Contattare l'assistenza.");
    	}
    }
}
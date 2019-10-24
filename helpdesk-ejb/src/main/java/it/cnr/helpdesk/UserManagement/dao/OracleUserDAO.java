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

 

package it.cnr.helpdesk.UserManagement.dao;

import it.cnr.helpdesk.UserManagement.exceptions.*;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.dao.UserDAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;
import it.cnr.helpdesk.util.PageByPageIterator;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class OracleUserDAO extends HelpDeskDAO implements UserDAO
{

    public OracleUserDAO()
    {
        con = null;
        firstName = null;
        familyName = null;
        profile = 0;
        login = null;
        email = null;
        password = null;
        telefono = null;
        struttura = null;
        enabled=null;
    }

    public void setFirstName(String s)
    {
        firstName = s;
    }

    public void setFamilyName(String s)
    {
        familyName = s;
    }

    public void setLogin(String s)
    {
        login = s;
    }

    public void setProfile(int i)
    {
        profile = i;
    }

    public void setEmail(String s)
    {
        email = s;
    }

    public void setPassword(String s)
    {
        password = s;
    }

    public void setTelefono(String s)
    {
        telefono = s;
    }

    public void setStruttura(String s)
    {
        struttura = s;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public String getLogin()
    {
        return login;
    }

    public int getProfile()
    {
        return profile;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public String getStruttura()
    {
        return struttura;
    }
    
	/**
	 * @return Returns the enabled.
	 */
	public String getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled The enabled to set.
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

    public void createConnection(String instance) throws UserDAOException {
        try {
            super.createConnection(instance);
        } catch(HelpDeskDAOException helpdeskdaoexception) {
            throw new UserDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
        }
    }

    public void closeConnection()
        throws UserDAOException
    {
        try
        {
            super.closeConnection();
        }
        catch(HelpDeskDAOException helpdeskdaoexception)
        {
            throw new UserDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
        }
    }

    public boolean existsAccount() throws UserDAOException {
    	String s="Select * from utente where userid='"+login+"'";
    	try {
    		ResultSet rs=execQuery(s);
    		if (rs.next())
    			return true;
    		else
    			return false;
    	} catch (SQLException sqlexception) {
    		log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
    		sqlexception.printStackTrace();
            throw new UserDAOException(/*Inserire chiave ResourceBundle*/);
    	}
    }
    
    public void executeInsert()
        throws UserDAOException
    {
        String s = "Insert into Utente ( USERID, PASSWORD, NOME, COGNOME, PROFILO, E_MAIL, TELEFONO, STRUTTURA, ENABLED, NOTE, MAIL_STOP )" +
        		"values ('" + login + "','" + password + "','" + formatString(firstName) + "','" + formatString(familyName) + "','" + profile + "','" + email + "','" + telefono + "','" + formatString(struttura) + "','y','','"+ mailStop +"')";
        try {
        	execUpdQuery(s);
        } catch(SQLException sqlexception) {
        	log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
        	sqlexception.printStackTrace();
        	throw new UserDAOException(/*Inserire chiave ResourceBundle*/);
        }
    }

    public void executeDelete() throws UserDAOException
    {
        String s = "Delete from Utente where userid='" + login + "'";
        try
        {
            execUpdQuery(s);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la creazione del nuovo utente. Contattare l'assistenza.");
        }
    }
    
    public void disable() throws UserDAOException
    {
        String s1 = "update Utente set enabled='n' where userid='" + login + "'";
/*        String s2 = "delete from esperto_categoria where esperto ='" + login + "'"; */
        try
        {
            execUpdQuery(s1);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s1 + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la creazione del nuovo utente. Contattare l'assistenza.");
        }
/*        if (profile == 2) {
        	try {
        		execUpdQuery(s2);
        	} catch(SQLException sqlexception) {
        		log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s2 + "\". " + sqlexception.getMessage());
        		sqlexception.printStackTrace();
        		throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la creazione del nuovo utente. Contattare l'assistenza.");
        	}
        } */
    }
    
    public void enable() throws UserDAOException
    {
        String s = "update Utente set enabled='y' where userid='" + login + "'";
        try
        {
            execUpdQuery(s);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la creazione del nuovo utente. Contattare l'assistenza.");
        }
    }    

    public PageByPageIterator allUsers(PageByPageIterator pagebypageiterator, UserValueObject uservalueobject)
        throws UserDAOException
    {
        String s = "Select * from Utente where Utente.profilo=" + uservalueobject.getProfile()+" order by cognome,nome";
        ArrayList arraylist = null;
        int i = 0;
        try
        {
            ResultSet resultset = execQuery(s);
            System.out.println("query eseguita: "+s);
            arraylist = new ArrayList();
            while(resultset.next()) {
            	System.out.println("prendo una riga dopo la "+i);
                i = resultset.getRow();
                System.out.println("righe: "+i);
                if(i >= pagebypageiterator.getStart() && i <= (pagebypageiterator.getStart() + pagebypageiterator.getSize()) - 1) {
                	System.out.println("e' da visualizzare");
                    UserValueObject uservalueobject1 = new UserValueObject(resultset.getString(1), resultset.getString(2), resultset.getString(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getString(7), resultset.getString(8), resultset.getString(9),"",resultset.getString(11), resultset.getString(12));
                    System.out.println("aggiungo all'arraylist");
                    arraylist.add(uservalueobject1);
                }
                System.out.println("ci sara' altro dopo "+resultset.getRow());
            }
            System.out.println("finito!");
        } catch(SQLException sqlexception) {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista degli utenti");
        }
        pagebypageiterator.setTotalCount(i);
        pagebypageiterator.setCollection(arraylist);
        return pagebypageiterator;
    }

    public PageByPageIterator allEnabledUsers(PageByPageIterator pagebypageiterator, UserValueObject uservalueobject)
    throws UserDAOException
{
    String s = "Select * from Utente where Utente.profilo=" + uservalueobject.getProfile()+" and Utente.enabled='y' order by cognome,nome";
    //log(s);
    ArrayList arraylist = null;
    int i = 0;
    try
    {
        ResultSet resultset = execQuery(s);
        arraylist = new ArrayList();
        while(resultset.next()) 
        {
            i = resultset.getRow();
            if(i >= pagebypageiterator.getStart() && i <= (pagebypageiterator.getStart() + pagebypageiterator.getSize()) - 1)
            {
                UserValueObject uservalueobject1 = new UserValueObject(resultset.getString(1), resultset.getString(2), resultset.getString(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getString(7), resultset.getString(8), resultset.getString(9),"",resultset.getString(11), resultset.getString(12));
                arraylist.add(uservalueobject1);
            }
        }
    }
    catch(SQLException sqlexception)
    {
        log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
        sqlexception.printStackTrace();
        throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista degli utenti");
    }
    pagebypageiterator.setTotalCount(i);
    pagebypageiterator.setCollection(arraylist);
    return pagebypageiterator;
}

    /**
	 * Fornisce tutti gli utenti esperti con assegnata una determinata Categoria 
	 * @param int i identificatico della categoria.
	 * @return Collection la collezione degli Utenti Esperti trovati
	 * @throws SQLException se si verifica un errore da DB.
	 * @throws Exception se si verifica un errore.
	 * 
	 * 
	 */
    public Collection allCategoryExperts(int i)
        throws UserDAOException
    {
        String s = "Select Utente.USERID,Utente.PASSWORD,Utente.NOME,Utente.COGNOME,Utente.PROFILO,Utente.E_MAIL,Utente.TELEFONO,Utente.STRUTTURA, Utente.enabled, Utente.note, Utente.mail_stop, Utente.blurred from Utente, esperto_categoria where Utente.USERID=esperto_categoria.ESPERTO and Utente.PROFILO=2 and esperto_categoria.CATEGORIA=" + i+" order by utente.userid";
        ArrayList arraylist = null;
        try
        {
            ResultSet resultset = execQuery(s);
            arraylist = new ArrayList();
            UserValueObject uservalueobject;
            while(resultset.next()) { 
            	arraylist.add(new UserValueObject(resultset.getString(1), resultset.getString(2), resultset.getString(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getString(7), resultset.getString(8), resultset.getString(9),"",resultset.getString(11), resultset.getString(12)));
            }
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista degli esperti della categoria specificata.");
        }
        return arraylist;
    }

    /**
	 * Fornisce tutti gli utenti validatori con assegnata una determinata Categoria 
	 * @param int idCategory identificatico della categoria.
	 * @return Collection la collezione degli Utenti Validatori trovati
	 * @throws SQLException se si verifica un errore da DB.
	 * @throws Exception se si verifica un errore.
	 * 
	 * 
	 */
    public Collection allCategoryValidators(int idCategory) throws UserDAOException {
    	String sql = "Select Utente.USERID,Utente.PASSWORD,Utente.NOME,Utente.COGNOME,Utente.PROFILO,Utente.E_MAIL,Utente.TELEFONO,Utente.STRUTTURA, Utente.enabled, Utente.note, Utente.mail_stop, Utente.blurred from Utente, esperto_categoria where Utente.USERID=esperto_categoria.ESPERTO and Utente.PROFILO=4 and esperto_categoria.CATEGORIA=" + idCategory+" order by utente.userid";
    	ArrayList arraylist = null;
    	try
    	{
	        ResultSet resultset = execQuery(sql);
	        arraylist = new ArrayList();
	        UserValueObject uservalueobject;
	        while(resultset.next()) { 
	        	arraylist.add(new UserValueObject(resultset.getString(1), resultset.getString(2), resultset.getString(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getString(7), resultset.getString(8), resultset.getString(9),"",resultset.getString(11), resultset.getString(12)));
	        }
	    }
    	catch(SQLException sqlexception) {
	        log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + sql + "\". " + sqlexception.getMessage());
	        sqlexception.printStackTrace();
	        throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista dei validatori della categoria specificata.");
	    }
    	return arraylist;
    }//end method
    
    
    public UserValueObject isRegistered()
        throws UserDAOException
    {
        String s = "Select * From Utente U Where U.userid='" + login + "' AND " + " U.password='" + password + "' AND U.ENABLED='y'";
        UserValueObject uservalueobject = null;
        try
        {
            ResultSet resultset = execQuery(s);
            if(resultset.next())
                uservalueobject = new UserValueObject(resultset.getString(1), null, resultset.getString(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getString(7), resultset.getString(8), resultset.getString(9),"",resultset.getString(11), resultset.getString(12));
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la verifica delle credenziali dell'utente.");
        }
      //  System.out.println("Dati forniti dall'utente: Login="+login+" Password="+password);
        // System.out.println("Dati reperiti nel db: Login="+uservalueobject.getLogin()+" Password="+uservalueobject.getPassword());
        return uservalueobject;
    }

    public UserValueObject getUserDetail()
        throws UserDAOException
    {
        String s = "Select U.USERID,U.PASSWORD,U.NOME,U.COGNOME,U.PROFILO,U.E_MAIL,U.TELEFONO,U.STRUTTURA,U.ENABLED,U.MAIL_STOP,S.DESCRIZIONE,S.ACRONIMO,U.BLURRED From Utente U, Strutture S Where U.STRUTTURA=S.CODICE_STRUTTURA(+) AND U.userid='" + login + "'";
        UserValueObject uservalueobject = null;
        try
        {
            ResultSet resultset = execQuery(s);
            if(resultset.next())
                uservalueobject = new UserValueObject(
                        resultset.getString(1),
                        null, 	//password is not sent
                        resultset.getString(3), 
                        resultset.getString(4), 
                        resultset.getInt(5), 
                        resultset.getString(6), 
                        resultset.getString(7), 
                        resultset.getString(8), 
                        resultset.getString(9),
                        resultset.getString(8)==null?"":resultset.getString(8)+" - "+resultset.getString(11)+" ("+resultset.getString(12)+")",
                        resultset.getString(10),
						resultset.getString(13));        
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero delle informazioni sull'utente. Contattare l'assistenza.");
        }
        return uservalueobject;
    }

    public void changeUserPassword()
        throws UserDAOException
    {
        String s = "Update Utente set password='" + password + "' where userid='" + login + "'";
        try
        {
            log(this, s);
            execUpdQuery(s);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore nell'operazione di cambiamento della password. Contattare l'assistenza.");
        }
    }

    public void executeUpdate()
        throws UserDAOException
    {
        String s = "Update Utente set nome='" + formatString(firstName) + "',cognome='" + formatString(familyName) + "',e_mail='" + email + "',telefono='" + telefono + "',struttura='" + formatString(struttura) + "', mail_stop='"+ mailStop+"' where userid='" + login + "'";
        try
        {
            log(this, s);
            execUpdQuery(s);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante l'aggiornamento delle informazioni personali. Contattare l'assistenza.");
        }
    }

    public String getUserPassword()
        throws UserDAOException
    {
        String s = null;
        String s1 = "Select password From utente Where userid='" + login + "'";
        try
        {
            log(this, s1);
            ResultSet resultset = execQuery(s1);
            if(resultset.next())
                s = resultset.getString(1);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s1 + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della password attuale. Contattare l'assistenza.");
        }
        return s;
    }

	public String getMailStop() {
		return mailStop;
	}
	public void setMailStop(String mailStop) {
		this.mailStop = mailStop;
	}
	
	
    private Connection con;
    private String firstName;
    private String familyName;
    private int profile;
    private String login;
    private String email;
    private String password;
    private String telefono;
    private String struttura;
    private String enabled;
    private String mailStop;

}
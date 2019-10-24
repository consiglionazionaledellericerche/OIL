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

/**
 * <p>Title: OIL - Online Interactive heLpdesk</p>
 * <p>Description: A Web Based Help Desk Tool</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: OIL</p>
 * @author Andrea Bei, Roberto Puccinelli
 * @version 1.0
 */
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.UserManagement.valueobjects.*;
import it.cnr.helpdesk.exceptions.*;
import it.cnr.helpdesk.UserManagement.exceptions.*;
import it.cnr.helpdesk.util.*;
import it.cnr.helpdesk.dao.*;
import java.sql.*;

import java.util.*;

public class PostgresUserDAO extends HelpDeskDAO implements UserDAO {
  private Connection con=null;
private String firstName=null;
private String familyName=null;
private int profile=0;
private String login=null;
private String email=null;
private String password=null;
private String telefono=null;
private String struttura=null;
private String mailStop;
private String enabled;

public PostgresUserDAO() {
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


public void setFirstName(String firstName)
{
this.firstName=firstName;
}

public void setFamilyName(String familyName)
{
this.familyName=familyName;
}

public void setLogin(String login)
{
this.login=login;
}

public void setProfile(int profile)
{
this.profile=profile;
}

public void setEmail(String email)
{
this.email=email;
}

public void setPassword(String password)
{
this.password=password;
}

public void setTelefono(String telefono)
{
this.telefono=telefono;
}

public void setStruttura(String struttura)
{
this.struttura=struttura;
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


public void createConnection(String instance) throws UserDAOException{
  try {
    super.createConnection(instance);
  }catch (HelpDeskDAOException e) {
    throw new UserDAOException(e.getMessage(),e.getUserMessage());
  }
}


public void closeConnection() throws UserDAOException {
  try {
    super.closeConnection();
  }catch (HelpDeskDAOException e) {
    throw new UserDAOException(e.getMessage(),e.getUserMessage());
  }
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
            UserValueObject uservalueobject1 = new UserValueObject(resultset.getString(1), resultset.getString(2), resultset.getString(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getString(7), resultset.getString(8), resultset.getString(9));
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
// Roberto Puccinelli: inizio modifiche 06/10/02
public void executeInsert()throws UserDAOException{

  String sqlQuery="Insert into Utente ( USERID, PASSWORD, NOME, COGNOME, PROFILO, E_MAIL, TELEFONO, STRUTTURA, ENABLED, NOTE, MAIL_STOP )" +
  					"values ('"+login+"','"
                   +password+"','"
                   +formatString(firstName)+"','"
                   +formatString(familyName)+"','"
                   +profile+"','"
                   +email+"','"
                   +telefono+"','"
                   +formatString(struttura)+"',"
                   +"'y'," 
				   +"'','" 
				   +mailStop+ "')";
  try {

    execUpdQuery(sqlQuery);

  } catch(SQLException e) {

    log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
    e.printStackTrace();
    throw new UserDAOException(e.getMessage(),"Si è verificato un errore durante la creazione del nuovo utente. Contattare l'assistenza.");

  }
}
// Roberto Puccinelli: fine modifiche 06/10/02

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

public  PageByPageIterator allUsers(PageByPageIterator pbpIterator,UserValueObject uvo) throws UserDAOException {

String sqlQuery="Select * from Utente where Utente.profilo="+uvo.getProfile()+" order by cognome,nome asc" ;
Collection userList=null;
int index=0;
try
{
ResultSet rs=execQuery(sqlQuery);
userList = new ArrayList();



 while (rs.next()) {
    index=rs.getRow();
    if ((index>=pbpIterator.getStart()) && (index<=pbpIterator.getStart()+pbpIterator.getSize()-1))
    {
    UserValueObject u=new UserValueObject(rs.getString(1),
                                          rs.getString(2),
                                          rs.getString(3),
                                          rs.getString(4),
                                          rs.getInt(5),
                                          rs.getString(6),
                                          rs.getString(7),
                                          rs.getString(8),
										  rs.getString(9));
   userList.add(u);
    }
}


  } catch(java.sql.SQLException e) {

    log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
    e.printStackTrace();
    throw new UserDAOException(e.getMessage(),"Si è verificato un errore durante il recupero della lista degli utenti");

  }
  pbpIterator.setTotalCount(index);
  pbpIterator.setCollection(userList);
  return pbpIterator;

}



public  Collection allCategoryExperts(int category) throws UserDAOException{
	String sqlQuery="Select Utente.USERID,Utente.PASSWORD,Utente.NOME,Utente.COGNOME,Utente.PROFILO,Utente.E_MAIL,Utente.TELEFONO,Utente.STRUTTURA, Utente.ENABLED, Utente.note, Utente.mail_stop, Utente.blurred from Utente, esperto_categoria where Utente.USERID=esperto_categoria.ESPERTO and Utente.PROFILO=2 and esperto_categoria.CATEGORIA="+category+" order by utente.userid";
	Collection userList=null;
	try {
		ResultSet rs=execQuery(sqlQuery);
		userList = new ArrayList();
		while (rs.next()) {
		    UserValueObject u=new UserValueObject(rs.getString(1), 
		    		rs.getString(2), 
					rs.getString(3), 
					rs.getString(4), 
					rs.getInt(5), 
					rs.getString(6), 
					rs.getString(7), 
					rs.getString(8), 
					rs.getString(9),
					"",
					rs.getString(11),
					rs.getString(12));
		   userList.add(u);
		}
	} catch(java.sql.SQLException e) {
	    log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
	    e.printStackTrace();
	    throw new UserDAOException(e.getMessage(),"Si è verificato un errore durante il recupero della lista degli esperti della categoria specificata.");
	
	}
	return userList;
}


	public  Collection allCategoryValidators(int category) throws UserDAOException{
		String sqlQuery= "Select Utente.USERID,Utente.PASSWORD,Utente.NOME,Utente.COGNOME,Utente.PROFILO,Utente.E_MAIL,Utente.TELEFONO,Utente.STRUTTURA, Utente.ENABLED, Utente.note, Utente.mail_stop, Utente.blurred "+
				"from Utente, esperto_categoria "+
				"where Utente.USERID=esperto_categoria.ESPERTO and "+
				"Utente.PROFILO=4 and esperto_categoria.CATEGORIA="+category+" order by utente.userid";

		Collection userList=null;
		try	{
			ResultSet rs=execQuery(sqlQuery);
			userList = new ArrayList();
			while (rs.next()) {
				UserValueObject u=new UserValueObject(rs.getString(1), 
		            				  rs.getString(2), 
									  rs.getString(3), 
									  rs.getString(4), 
									  rs.getInt(5), 
									  rs.getString(6), 
									  rs.getString(7), 
									  rs.getString(8), 
									  rs.getString(9),
									  "",
									  rs.getString(11),
									  rs.getString(12));
				userList.add(u);
			}

		} 
		catch(java.sql.SQLException e) {
			log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
			e.printStackTrace();
			throw new UserDAOException(e.getMessage(),"Si è verificato un errore durante il recupero della lista degli esperti della categoria specificata.");
		}
		return userList;
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
        uservalueobject = new UserValueObject(resultset.getString(1), null, resultset.getString(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getString(7), resultset.getString(8), resultset.getString(9));
}
catch(SQLException sqlexception)
{
    log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
    sqlexception.printStackTrace();
    throw new UserDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la verifica delle credenziali dell'utente.");
}
//System.out.println("Dati forniti dall'utente: Login="+login+" Password="+password);
// System.out.println("Dati reperiti nel db: Login="+uservalueobject.getLogin()+" Password="+uservalueobject.getPassword());
return uservalueobject;
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

// Roberto Puccinelli: inizio modifiche 09/10/02
public UserValueObject getUserDetail() throws UserDAOException {
String sqlQuery="Select U.USERID,U.PASSWORD,U.NOME,U.COGNOME,U.PROFILO,U.E_MAIL,U.TELEFONO,U.STRUTTURA,U.ENABLED,U.MAIL_STOP,S.DESCRIZIONE,S.ACRONIMO,U.BLURRED" +
		" From Utente U LEFT JOIN Strutture S ON  U.STRUTTURA=S.CODICE_STRUTTURA " +
		"WHERE U.userid='" + login + "'";
UserValueObject userInfo=null;
  try {
ResultSet rs=execQuery(sqlQuery);
if (rs.next())
{
 userInfo=new UserValueObject(rs.getString(1),
                              null, //password is not sent
                              rs.getString(3),
                              rs.getString(4),
                              rs.getInt(5),
                              rs.getString(6),
                              rs.getString(7),
                              rs.getString(8),
							  rs.getString(9),
							  rs.getString(8)==null?"":rs.getString(8)+" - "+rs.getString(11)+" ("+rs.getString(12)+")",
							  rs.getString(10),
							  rs.getString(13));
}
  } catch(SQLException e) {

    log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
    e.printStackTrace();
    throw new UserDAOException(e.getMessage(),"Si è verificato un errore durante il recupero delle informazioni sull'utente. Contattare l'assistenza.");
}
return userInfo;
}


// Roberto Puccinelli: inizio modifiche 09-10-02
public void changeUserPassword() throws UserDAOException{
  String sqlQuery=  "Update Utente set password='"
                    +password+
                    "' where userid='"+login+"'";
  try{
    log(this,sqlQuery);
    execUpdQuery(sqlQuery);
  } catch(SQLException e) {

    log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
    e.printStackTrace();
    throw new UserDAOException(e.getMessage(),"Si è verificato un errore nell'operazione di cambiamento della password. Contattare l'assistenza.");

  }
}
// Roberto Puccinelli: fine modifiche 09/10/02

//Roberto Puccinelli: inizio modifiche 09/10/02
public void executeUpdate()throws UserDAOException{
  String sqlQuery=  "Update Utente set nome='"
                    +formatString(firstName)+
                    "',cognome='"
                    +formatString(familyName)+
                    "',e_mail='"
                    +email+
                    "',telefono='"
                    +telefono+
                    "',struttura='"
                    +formatString(struttura)+
                    "', mail_stop='"+ mailStop+
                    "' where userid='"+login+"'";
   try{
      log(this,sqlQuery);
      execUpdQuery(sqlQuery);
   }catch(SQLException e){

    log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
    e.printStackTrace();
    throw new UserDAOException(e.getMessage(),"Si è verificato un errore durante l'aggiornamento delle informazioni personali. Contattare l'assistenza.");

   }
}

// Roberto Puccinelli: fine modifiche 09/10/02


// Roberto Puccinelli: inizio modifiche 19/09/2002

public String getUserPassword() throws UserDAOException {
  String userPassword=null;
  String sqlQuery="Select password From utente Where userid='"+login+"'";
  try {
    log(this,sqlQuery);
    ResultSet rs=execQuery(sqlQuery);
    if (rs.next()){
      userPassword=rs.getString(1);
    }
  } catch (SQLException e) {

    log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
    e.printStackTrace();
    throw new UserDAOException(e.getMessage(),"Si è verificato un errore durante il recupero della password attuale. Contattare l'assistenza.");

  }
  return userPassword;

}

// Roberto Puccinelli: fine modifiche 19/09/2002

public void setMailStop(String mailStop) {
	this.mailStop = mailStop;
}

public String getMailStop() {
	return this.mailStop;
}
public String getEnabled() {
	return enabled;
}
public void setEnabled(String enabled) {
	this.enabled = enabled;
}
}

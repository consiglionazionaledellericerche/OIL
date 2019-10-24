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

 

package it.cnr.helpdesk.UserManagement.javabeans;

import it.cnr.helpdesk.UserManagement.ejb.UserManagement;
import it.cnr.helpdesk.UserManagement.ejb.UserManagementHome;
import it.cnr.helpdesk.UserManagement.exceptions.UserEJBException;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.UserDAO;
import it.cnr.helpdesk.javabeans.HelpDeskJB;
import it.cnr.helpdesk.security.util.SecurityHelper;
import it.cnr.helpdesk.util.PageByPageIterator;
import it.cnr.helpdesk.util.PageByPageIteratorImpl;
import it.cnr.helpdesk.LdapManagemet.exceptions.LdapJBException;
import it.cnr.helpdesk.LdapManagemet.javabeans.Ldap;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemEJBException;

import java.sql.SQLException;
import java.util.*;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

// Referenced classes of package it.cnr.helpdesk.javabeans:
//            HelpDeskJB

public class User extends HelpDeskJB
{

    public User()
    {
        familyName = "";
        firstName = "";
        profile = "";
        email = "";
        login = "";
        password = "";
        telefono = "";
        struttura = "";
    }

    public User(String s)
    {
        familyName = "";
        firstName = "";
        profile = "";
        email = "";
        login = "";
        password = "";
        telefono = "";
        struttura = "";
        setLogin(s);
    }

    public void setFirstName(String s)
    {
        firstName = s;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFamilyName(String s)
    {
        familyName = s;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public void setProfile(String s)
    {
        profile = s;
    }

    public int getProfile()
    {
        return Integer.parseInt(profile);
    }

    public void setEmail(String s)
    {
        email = s;
    }

    public String getEmail()
    {
        return email;
    }

    public void setLogin(String s)
    {
        login = s;
    }

    public String getLogin()
    {
        return login;
    }

    private void setPassword(String s)
    {
        password = s;
    }

    public String getPassword()
    {
        return password;
    }

    public void setTelefono(String s)
    {
        telefono = s;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public void setStruttura(String s)
    {
        struttura = s;
    }

    public String getStruttura()
    {
        return struttura;
    }

    public boolean isRegistered(String instance) throws UserJBException {
        boolean flag = false;
        try {
            UserManagementHome usermanagementhome = lookupHome();
            UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
            UserValueObject uservalueobject1 = new UserValueObject();
            uservalueobject1.setLogin(getLogin());
            uservalueobject1.setPassword(getPassword());
            UserValueObject uservalueobject = usermanagement.isRegistered(uservalueobject1, instance);
            if(uservalueobject != null) {
                setFirstName(uservalueobject.getFirstName());
                setFamilyName(uservalueobject.getFamilyName());
                setEmail(uservalueobject.getEmail());
                setLogin(uservalueobject.getLogin());
                setProfile(Integer.toString(uservalueobject.getProfile()));
                setEnabled(uservalueobject.getEnabled());
                setTelefono(uservalueobject.getTelefono());
                setStruttura(uservalueobject.getStruttura());
                flag = true;
            } else {
                flag = false;
            }
            usermanagement.remove();
        } catch(UserEJBException userejbexception){
            throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
        } catch(Exception exception){
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile verificare se l'utente \350 registrato. Contattare l'assistenza.");
        }
        return flag;
    }
    
    public boolean existsAccount(String instance) throws UserJBException {
    	boolean result=false;
    	UserDAO userDAO=DAOFactory.getDAOFactoryByProperties().getUserDAO();
    	userDAO.setLogin(login);
    	try {
    		userDAO.createConnection(instance);
    		result=userDAO.existsAccount();
    		userDAO.closeConnection();
    	} catch (UserDAOException ude){
            throw new UserJBException(ude.getMessage(), ude.getMessage()); 
    	} catch (Exception exception){
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile verificare se esiste un account con login='"+login+". Contattare l'assistenza.");
    	}
    	return result;
    }

    public PageByPageIterator allUser(PageByPageIterator pagebypageiterator, int i, String instance) throws UserJBException {
        PageByPageIterator pagebypageiterator1 = null;
        UserValueObject uservalueobject = new UserValueObject();
        uservalueobject.setProfile(i);
        try{
            UserManagementHome usermanagementhome = lookupHome();
            UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
            pagebypageiterator1 = usermanagement.allUsers(pagebypageiterator, uservalueobject, instance);
            usermanagement.remove();
        } catch(UserEJBException userejbexception){
            throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
        } catch(Exception exception){
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile recuperare la lista degli utenti. Contattare l'assistenza.");
        }
        return pagebypageiterator1;
    }

    public PageByPageIterator allEnabledUser(PageByPageIterator pagebypageiterator, int i, String instance) throws UserJBException {
    PageByPageIterator pagebypageiterator1 = null;
    UserValueObject uservalueobject = new UserValueObject();
    uservalueobject.setProfile(i);
    UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
    try{
        userdao.createConnection(instance);
        pagebypageiterator1 = userdao.allEnabledUsers(pagebypageiterator, uservalueobject);
        userdao.closeConnection();
    } catch(UserDAOException userdaoexception) {
        throw new UserJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
    } catch(Exception exception){
        log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
        exception.printStackTrace();
        throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile recuperare la lista degli utenti. Contattare l'assistenza.");
    }
    return pagebypageiterator1;
}

    public void addUser(String instance) throws UserJBException {
        try{
            UserManagementHome usermanagementhome = lookupHome();
            UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
            UserValueObject uservalueobject = new UserValueObject();
            uservalueobject.setLogin(getLogin());
            uservalueobject.setFirstName(getFirstName());
            uservalueobject.setFamilyName(getFamilyName());
            uservalueobject.setPassword(getPassword());
            uservalueobject.setProfile(getProfile());
            uservalueobject.setEmail(getEmail());
            uservalueobject.setTelefono(getTelefono());
            uservalueobject.setStruttura(getStruttura());
            uservalueobject.setMailStop(getMailStop());
            usermanagement.insertUser(uservalueobject, instance);
            usermanagement.remove();
        } catch(UserEJBException userejbexception){
            throw new UserJBException(/*Inserire messaggio da Resource Bundle*/);
        } catch (Exception exception) {
        	log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile registrare nuovo utente. Contattare l'assistenza.");
        }
    }

    public UserValueObject getDetail(String instance) throws UserJBException {
        UserValueObject uservalueobject = null;
        try {
            UserManagementHome usermanagementhome = lookupHome();
            UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
            uservalueobject = usermanagement.getUserDetail(login, instance);
            usermanagement.remove();
        } catch(UserEJBException userejbexception) {
            throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
        } catch(Exception exception) {
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile recuperare informazioni sull'utente. Contattare l'assistenza.");
        }
        return uservalueobject;
    }

    public void loadDetail(String login, String instance) throws UserJBException {
        UserValueObject uservalueobject = null;
        try {
            UserManagementHome usermanagementhome = lookupHome();
            UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
            uservalueobject = usermanagement.getUserDetail(login, instance);
            usermanagement.remove();
        } catch(UserEJBException userejbexception) {
            throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
        } catch(Exception exception) {
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile recuperare informazioni sull'utente. Contattare l'assistenza.");
        }
        setFirstName(uservalueobject.getFirstName());
        setFamilyName(uservalueobject.getFamilyName());
        setEmail(uservalueobject.getEmail());
        setLogin(uservalueobject.getLogin());
        setProfile(Integer.toString(uservalueobject.getProfile()));
        setEnabled(uservalueobject.getEnabled());
        setTelefono(uservalueobject.getTelefono());
        setStruttura(uservalueobject.getStruttura());
        setMailStop(uservalueobject.getMailStop());
    }
	
    /**
     * Controlla se i campi obbligatori sono presenti nel bean
     * @return true se i campi sono presenti, false in caso contrario
     * 
     * TODO i vincoli meglio se presi da validation.xml piuttosto che cablati nel codice
     */
    public boolean checkDetail(){
        if(firstName==null || firstName.length()==0)
            return false;
        if(familyName==null || familyName.length()==0)
            return false;
        if(mailStop.startsWith("n") && (email==null || email.length()==0))
            return false;
        if(profile==null || profile.length()==0)
            return false;
        if(login==null || login.length()==0)
            return false;
        return true;
    }
    
    private UserManagementHome lookupHome()
        throws UserJBException
    {
        UserManagementHome usermanagementhome = null;
        try
        {
            Context context = getInitialContext();
            if(context == null)
            {
                log(this, "Initial Context=null");
            } else
            {
            	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
                Object obj1 = context.lookup(jndiPrefix+"UserManagement!it.cnr.helpdesk.UserManagement.ejb.UserManagementHome");
                usermanagementhome = (UserManagementHome)PortableRemoteObject.narrow(obj1, it.cnr.helpdesk.UserManagement.ejb.UserManagementHome.class);
            }
        }
        catch(NamingException namingexception)
        {
            log(this, "Si \350 verificato un errore durante la ricerca all'interno del registro JNDI. " + namingexception.getMessage());
            namingexception.printStackTrace();
            throw new UserJBException(namingexception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
        }
        catch(UserEJBException userejbexception)
        {
            throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
        }
        return usermanagementhome;
    }

    public void updateUserInfo(String instance) throws UserJBException {
        try {
            UserManagementHome usermanagementhome = lookupHome();
            UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
            UserValueObject uservalueobject = new UserValueObject();
            uservalueobject.setLogin(getLogin());
            uservalueobject.setFirstName(getFirstName());
            uservalueobject.setFamilyName(getFamilyName());
            uservalueobject.setPassword(getPassword());
            uservalueobject.setEmail(getEmail());
            uservalueobject.setTelefono(getTelefono());
            uservalueobject.setStruttura(getStruttura());
            uservalueobject.setMailStop(getMailStop());
            usermanagement.updateUser(uservalueobject, instance);
            usermanagement.remove();
        } catch(UserEJBException userejbexception) {
            throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
        } catch(Exception exception) {
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile aggiornare le informazioni sull'utente. Contattare l'assistenza.");
        }
    }

    public void changeUserPassword(String instance) throws UserJBException {
        try {
            UserManagementHome usermanagementhome = lookupHome();
            UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
            UserValueObject uservalueobject = new UserValueObject();
            uservalueobject.setLogin(getLogin());
            uservalueobject.setPassword(getPassword());
            usermanagement.changeUserPassword(uservalueobject, instance);
            usermanagement.remove();
        } catch(UserEJBException userejbexception) {
            throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
        } catch(Exception exception) {
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile cambiare la password dell'utente. Contattare l'assistenza.");
        }
    }

    public void getUserPassword(String instance) throws UserJBException {
        try {
            UserManagementHome usermanagementhome = lookupHome();
            UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
            this.password = usermanagement.getUserPassword(login, instance);
            usermanagement.remove();
        } catch(UserEJBException userejbexception) {
            throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
        } catch(Exception exception) {
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile verificare la password dell'utente. Contattare l'assistenza.");
        }
    }
    
    private String hashPassword(String plain){
    	return (SecurityHelper.getHexDigest(plain,"MD5"));
    }
    
    /**
     * 	Funzione temporanea per crittare le password sul DB
     */
    public void hashAllPassword(String instance) throws UserJBException {
    	PageByPageIterator pbp = new PageByPageIteratorImpl(0,10,"",10);
        for(int i=1;i<4;i++){
	    	PageByPageIterator esp = allUser(pbp,i, instance);
	        Collection c=esp.getCollection();
	        Iterator ei = c.iterator();
	        while(ei.hasNext()){
	        	UserValueObject uvo=(UserValueObject) ei.next();
	        	setLogin(uvo.getLogin());
	        	getUserPassword(instance);
	        	setPlainPassword(getPassword());
	        	changeUserPassword(instance);
	        }
        }
    }
    
    public void deleteUser(String login, String instance) throws UserJBException {
    	try {
    		UserManagementHome usermanagementhome = lookupHome();
    		UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
    		usermanagement.deleteUser(login, instance);
    		usermanagement.remove();
    	} catch(UserEJBException userejbexception){
    		throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
    	} catch(Exception exception) {
    		log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
    		exception.printStackTrace();
    		throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile verificare la password dell'utente. Contattare l'assistenza.");
    	}
    }    
    
    public void disable(String login, int profile, String admin, String instance) throws UserJBException {
    	try {
    		UserManagementHome usermanagementhome = lookupHome();
    		UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
    		usermanagement.disable(login, profile, admin, instance);
    		usermanagement.remove();
    	} catch(UserEJBException userejbexception){
    		throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
    	} catch(Exception exception) {
    		log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
    		exception.printStackTrace();
    		throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile verificare la password dell'utente. Contattare l'assistenza.");
    	}
    }    

    public void disable(String login, int profile, String admin, Collection eventi, String instance) throws UserJBException {
    	try {
    		UserManagementHome usermanagementhome = lookupHome();
    		UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
    		if (eventi.isEmpty())
    		usermanagement.disable(login, profile, admin, instance);
    		else
    			usermanagement.disable(login, profile, admin, eventi, instance);
    		usermanagement.remove();
    	} catch(UserEJBException userejbexception){
    		throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
    	} catch(Exception exception) {
    		log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
    		exception.printStackTrace();
    		throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile verificare la password dell'utente. Contattare l'assistenza.");
    	}
    }    
    public void enable(String login, String instance) throws UserJBException {
    	try {
    		UserManagementHome usermanagementhome = lookupHome();
    		UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
    		usermanagement.enable(login, instance);
    		usermanagement.remove();
    	} catch(UserEJBException userejbexception){
    		throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
    	} catch(Exception exception) {
    		log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
    		exception.printStackTrace();
    		throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile verificare la password dell'utente. Contattare l'assistenza.");
    	}
    }    

    public boolean checkPassword(String instance) throws UserJBException {
        UserValueObject uservalueobject;
        try {
            UserManagementHome usermanagementhome = lookupHome();
            UserManagement usermanagement = (UserManagement)PortableRemoteObject.narrow(usermanagementhome.create(), it.cnr.helpdesk.UserManagement.ejb.UserManagement.class);
            UserValueObject uservalueobject1 = new UserValueObject();
            uservalueobject1.setLogin(getLogin());
            uservalueobject1.setPassword(getPassword());
            uservalueobject = usermanagement.isRegistered(uservalueobject1, instance);
        }
        catch(UserEJBException userejbexception)
        {
            throw new UserJBException(userejbexception.getMessage(), userejbexception.getUserMessage());
        }
        catch(Exception exception)
        {
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new UserJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile controllare la password dell'utente. Contattare l'assistenza.");
        }
        return(uservalueobject != null);
    }
    
    /**
	 * Fornisce tutti gli utenti esperti con assegnata una determinata Categoria 
	 * @param int i identificatico della categoria.
	 * @return Collection la collezione degli Utenti esperti trovati
	 * @throws SQLException se si verifica un errore da DB.
	 * @throws Exception se si verifica un errore.
	 * 
	 * 
	 */
    public Collection getCategoryExperts(int idCat, String instance) throws UserJBException {
        Collection collection;
        UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        try {
            userdao.createConnection(instance);
            collection = userdao.allCategoryExperts(idCat);
            userdao.closeConnection();
        } catch(UserDAOException userdaoexception) {
            throw new ProblemEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
        }
        return collection;
    }

    /**
	 * Fornisce tutti gli utenti validatori con assegnata una determinata Categoria 
	 * @param int icCat identificatico della categoria.
	 * @return Collection la collezione degli Utenti Validatori trovati
	 * @throws SQLException se si verifica un errore da DB.
	 * @throws Exception se si verifica un errore.
	 * 
	 * 
	 */
    public Collection getCategoryValidators(int idCat, String instance) throws UserJBException {
        Collection collection;
        UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        try {
            userdao.createConnection(instance);
            collection = userdao.allCategoryValidators(idCat);
            userdao.closeConnection();
        } catch(UserDAOException userdaoexception) {
            throw new ProblemEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
        }
        return collection;
    }//end method
    
    /**
	 * Fornisce tutti gli utenti esperti con assegnata una determinata Categoria apprtente all'insieme dato
	 * @param int i identificatico della categoria.
	 * @return HasmMap contenente gli Utenti Esperti trovati
	 * @throws SQLException se si verifica un errore da DB.
	 * @throws Exception se si verifica un errore.
	 * 
	 * 
	 */

    public HashMap getCategoryExperts(Collection ids, String instance) throws UserJBException {
        HashMap experts = new HashMap();
        UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        Collection collection;
        String idCat;
        Iterator idit = ids.iterator();
        try {
            userdao.createConnection(instance);
            while(idit.hasNext()){
            	idCat = idit.next().toString();
            	collection = userdao.allCategoryExperts(Integer.parseInt(idCat));
            	experts.put(idCat,collection);
            }
            userdao.closeConnection();
        } catch(UserDAOException userdaoexception) {
            throw new ProblemEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
        }
        return experts;
    }
    
    /**
	 * Fornisce tutti gli utenti validatori con assegnata una determinata Categoria apprtente all'insieme dato
	 * @param int i identificatico della categoria.
	 * @return HasmMap contenente gli Utenti Validatori trovati
	 * @throws SQLException se si verifica un errore da DB.
	 * @throws Exception se si verifica un errore.
	 * 
	 * 
	 */
    public HashMap getCategoryValidators(Collection ids, String instance) throws UserJBException {
        HashMap experts = new HashMap();
        UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        Collection collection;
        String idCat;
        Iterator idit = ids.iterator();
        try {
            userdao.createConnection(instance);
            while(idit.hasNext()){
            	idCat = idit.next().toString();
            	collection = userdao.allCategoryValidators(Integer.parseInt(idCat));
            	experts.put(idCat,collection);
            }
            userdao.closeConnection();
        } catch(UserDAOException userdaoexception) {
            throw new ProblemEJBException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
        }
        return experts;
    }//end method

    public static final int PUBLIC = 0;
    public static final int UTENTE = 1;
    public static final int ESPERTO = 2;
    public static final int AMMINISTRATORE = 3;
    public static final int VALIDATORE = 4;
    public static final String MAIL_USER = "mail";
    private String familyName;
    private String firstName;
    private String profile;
    private String email;
    private String login;
    
//    password cifrata con algoritmo MD5
    private String password;
    
    private String telefono;
    private String struttura;
    private String enabled;
    private String mailStop;
    
//	password in chiaro    
    private String plainPassword;
    
//    protected Collection eventi = new ArrayList(0);;

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
    /**
     * @return Returns the plainPassword.
     */
    public String getPlainPassword() {
        return plainPassword;
    }
    /**
     * @param plainPassword The plainPassword to set.
     */
    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
        this.setPassword(hashPassword(plainPassword));
    }
	/**
	 * @return Returns the mailStop.
	 */
	public String getMailStop() {
		return mailStop;
	}
	/**
	 * @param mailStop The mailStop to set.
	 */
	public void setMailStop(String mailStop) {
		this.mailStop = mailStop;
	}
	public void allowAllUserLdap(String instance) throws UserJBException {
		PageByPageIterator pbp=new PageByPageIteratorImpl(0,50000,"",10);
		PageByPageIterator p = allEnabledUser(pbp,1,instance);
		Ldap ldap = new Ldap();
		for(Iterator i = p.getCollection().iterator(); i.hasNext();){
			UserValueObject uvo = (UserValueObject) i.next();
			try {
				ldap.allowUser(uvo.getLogin(),uvo.getLogin(),instance);
			} catch (LdapJBException e) {
				e.printStackTrace();
			}
		}
	}
	public void disallowAllUserLdap(String instance) throws UserJBException {
		PageByPageIterator pbp=new PageByPageIteratorImpl(0,50000,"",10);
		PageByPageIterator p = allEnabledUser(pbp,1,instance);
		Ldap ldap = new Ldap();
		for(Iterator i = p.getCollection().iterator(); i.hasNext();){
			UserValueObject uvo = (UserValueObject) i.next();
			try {
				ldap.disallowUser(uvo.getLogin(),uvo.getLogin(),instance);
			} catch (LdapJBException e) {
				e.printStackTrace();
			}
		}
	}
}
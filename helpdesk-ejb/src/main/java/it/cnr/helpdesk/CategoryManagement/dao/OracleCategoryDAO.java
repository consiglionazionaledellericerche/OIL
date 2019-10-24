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
package it.cnr.helpdesk.CategoryManagement.dao;
 
import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryDAOException;
import it.cnr.helpdesk.CategoryManagement.valueobjects.CategoryValueObject;
import it.cnr.helpdesk.dao.*;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;
import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringEscapeUtils;

public class OracleCategoryDAO extends HelpDeskDAO implements CategoryDAO
{
    public OracleCategoryDAO()
    {
        con = null;
        idCategoria = 0;
        nome = null;
        descrizione = null;
        idPadre = 0;
    }

    public void setIdCategoria(int i)
    {
        idCategoria = i;
    }

    public void setIdPadre(int i)
    {
        idPadre = i;
    }

    public void setNome(String s)
    {
        nome = s;
    }

    public void setDescrizione(String s)
    {
        descrizione = s;
    }

	public void createConnection(String instance) throws CategoryDAOException {
		try {
			super.createConnection(instance);
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new CategoryDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

	public void closeConnection() throws CategoryDAOException {
		try {
			super.closeConnection();
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new CategoryDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

    public int executeInsert() throws CategoryDAOException {
		String sqlQuery = "select seq_category.nextval from dual";
        int i = 0;
        try {
            ResultSet resultset = execQuery(sqlQuery);
            if(resultset.next())
                i = resultset.getInt(1);
            else
            	throw new CategoryDAOException("Si \350 verificato un errore durante la creazione della nuova categoria.", "Si \350 verificato un errore durante la creazione della nuova categoria.");
            sqlQuery = "Insert into Categoria values ("+i+" ,'" + StringEscapeUtils.escapeSql(nome) + "'," + idPadre + ",'" + StringEscapeUtils.escapeSql(descrizione) + "','y')";
			log(this, sqlQuery);
			execUpdQuery(sqlQuery);
		} catch (SQLException e) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \0" + sqlQuery + "\". " + e.getMessage());
			e.printStackTrace();
			throw new CategoryDAOException(e.getMessage(), "Si \350 verificato un errore durante la creazione della nuova categoria.");
		}
        return i;
    }

    public void executeDelete()
    {
    }

    public void executeUpdate()
    	throws CategoryDAOException
    {
        String s = "update categoria set nome='" + StringEscapeUtils.escapeSql(nome) + "',descrizione='" + StringEscapeUtils.escapeSql(descrizione) + "' where id_categoria="+idCategoria;
        try
        {
            log(this, s);
            execUpdQuery(s);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \0" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la creazione della nuova categoria.");
        }
    }

    public CategoryValueObject allCategories()
        throws CategoryDAOException
    {
        CategoryValueObject categoryvalueobject = null;
        String s = "select id_categoria,nome,descrizione,categoria_padre,enabled,LEVEL from categoria start with id_categoria=1 connect by prior id_categoria=categoria_padre order siblings by nome ";
        try
        {
            log(this, s);
            ResultSet resultset = execSQuery(s);
            if(resultset.next())
                categoryvalueobject = buildTree(resultset);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \0" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista delle categorie. \" +\n    \"Contattare l'assistenza.");
        }
        return categoryvalueobject;
    }

    public Collection allCategoriesList()
        throws CategoryDAOException
    {
        ArrayList arraylist = new ArrayList();
        Object obj = null;
        String s = "select id_categoria,nome,descrizione,categoria_padre,enabled,LEVEL from categoria start with id_categoria=1 connect by prior id_categoria=categoria_padre order siblings by nome ";
        try
        {
            log(this, s);
            CategoryValueObject categoryvalueobject;
            for(ResultSet resultset = execQuery(s); resultset.next(); arraylist.add(categoryvalueobject))
                categoryvalueobject = new CategoryValueObject(resultset.getInt(1), resultset.getString(2), resultset.getString(3), resultset.getInt(4), resultset.getString(5), resultset.getInt(6));

        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista degli utenti");
        }
        return arraylist;
    }
    public Collection allEnabledCategoriesList()
    throws CategoryDAOException
{
    ArrayList arraylist = new ArrayList();
    Object obj = null;
    String s = "select id_categoria,nome,descrizione,categoria_padre,enabled,LEVEL from categoria where enabled='y' start with id_categoria=1 connect by prior id_categoria=categoria_padre order siblings by nome ";
    try
    {
        log(this, s);
        CategoryValueObject categoryvalueobject;
        for(ResultSet resultset = execQuery(s); resultset.next(); arraylist.add(categoryvalueobject))
            categoryvalueobject = new CategoryValueObject(resultset.getInt(1), resultset.getString(2), resultset.getString(3), resultset.getInt(4), resultset.getString(5), resultset.getInt(6));

    }
    catch(SQLException sqlexception)
    {
        log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
        sqlexception.printStackTrace();
        throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista degli utenti");
    }
    return arraylist;
}
    
    
    
    
    /**
	 * Esegue   
	 *  metodo utilizzato per utente EXPERT che VALIDATOR
	 *  
	 * @param String s identificativo dell'utente: login. 
	 * 
	 * @throws 
	 * @throws 
	 * 
	 * 
	 */
    public Collection allExpertCategoriesList(String s)
        throws CategoryDAOException
    {
        ArrayList arraylist = new ArrayList();
        String s1 = "select categoria.ID_CATEGORIA, categoria.NOME, categoria.DESCRIZIONE, categoria.categoria_padre, enabled, 0 from categoria,esperto_categoria where categoria.ID_CATEGORIA=esperto_categoria.CATEGORIA  and esperto_categoria.ESPERTO='" + s + "' order by categoria.nome ";
        try
        {
            CategoryValueObject categoryvalueobject;
            for(ResultSet resultset = execQuery(s1); resultset.next(); arraylist.add(categoryvalueobject))
                categoryvalueobject = new CategoryValueObject(resultset.getInt(1), resultset.getString(2), resultset.getString(3), resultset.getInt(4), resultset.getString(5), resultset.getInt(6));

        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \0" + s1 + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(),"Si \350 verificato un errore durante il recupero della lista delle categorie \" +\n    \"cui \350 associato " + s);
        }
        return arraylist;
    }

    private CategoryValueObject buildTree(ResultSet resultset)
        throws CategoryDAOException
    {
        CategoryValueObject categoryvalueobject = null;
        try
        {
            categoryvalueobject = new CategoryValueObject(resultset.getInt(1), resultset.getString(2), resultset.getString(3), resultset.getInt(4), resultset.getString(5), resultset.getInt(6));
            while(resultset.next()) 
            {
                if(resultset.getInt(4) == categoryvalueobject.getId())
                {
                    CategoryValueObject categoryvalueobject1 = buildTree(resultset);
                    categoryvalueobject.add(categoryvalueobject1);
                    continue;
                }
                resultset.previous();
                break;
            }
        }
        catch(SQLException sqlexception)
        {
            log(this,  "Si \350 verificato un errore nella ricostruzione della gerarchia delle \" +\n    \"categorie. " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore nella ricostruzione della gerarchia delle \" +\n    \"categorie. Contattare l'assistenza.");
        }
        return categoryvalueobject;
    }

    /**
	 * Esegue   
	 *  metodo utilizzato per utente EXPERT che VALIDATOR
	 *  
	 * @param String s identificativo dell'utente: login. 
	 * 
	 * @throws 
	 * @throws 
	 * 
	 * 
	 */
    public void assignToExpert(String s)
        throws CategoryDAOException
    {
        String s1 = "insert into ESPERTO_CATEGORIA values ('" + s + "'," + idCategoria + ")";
        try
        {
            log(this, s1);
            execUpdQuery(s1);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione dello statement \0" + s1 + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante l'assegnazione dell'esperto o validatore alla \" +\n    \"categoria indicata. Contattare l'assistenza.");
        }
    }

    /**
	 * Esegue   
	 *  metodo utilizzato per utente EXPERT che VALIDATOR
	 *  
	 * @param String s identificativo dell'utente: login. 
	 * 
	 * @throws 
	 * @throws 
	 * 
	 * 
	 */
    public void removeFromExpert(String s)
        throws CategoryDAOException
    {
        String s1 = "delete from ESPERTO_CATEGORIA where esperto_categoria.CATEGORIA=" + idCategoria + " and esperto_categoria.ESPERTO='" + s + "'";
        try
        {
            log(this, s1);
            execUpdQuery(s1);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione dello statement \0" + s1 + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la rimozione dell'esperto o validatore dalla \" +\n    \"categoria indicata. Contattare l'assistenza.");
        }
    }

    public CategoryValueObject getCategoryDetail(int i)
        throws CategoryDAOException
    {
        CategoryValueObject categoryvalueobject = null;
        String s = "select categoria.Id_categoria,categoria.nome,categoria.descrizione,categoria.categoria_padre,categoria.enabled,0 from categoria where categoria.id_categoria=" + i;
        try
        {
            ResultSet resultset = execQuery(s);
            if(resultset.next())
                categoryvalueobject = new CategoryValueObject(resultset.getInt(1), resultset.getString(2), resultset.getString(3), resultset.getInt(4), resultset.getString(5), resultset.getInt(6));
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \0" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero delle informazioni \" +\n    \"sulla categoria indicata. Contattare l'assistenza.");
        }
        return categoryvalueobject;
    }
    
    public boolean hasChildren(int id) throws CategoryDAOException {
    	String query = "select count(*) from categoria connect by prior id_categoria=categoria_padre start with id_categoria="+ id;
    	try
        {
            ResultSet resultset = execQuery(query);
            if(resultset.next())
            	return (resultset.getInt(1)>1);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + query + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore abilitando la categoria. Contattare l'assistenza.");
        }
        return false;
    }
    
    public boolean hasDisabledParent(int id) throws CategoryDAOException {
    	String query = "";
    	try
        {
    		ResultSet resultset;
            for(int pid=id; pid!=0; pid=resultset.getInt(2)){
            	query = "select id_categoria, categoria_padre, enabled from categoria where id_categoria="+ pid;
            	resultset = execQuery(query);
            	if(resultset.next()) {
            		if(resultset.getString(3).equals("n")&&(pid!=id))
            			return true;
            	} else return false;
            }
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + query + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore abilitando la categoria. Contattare l'assistenza.");
        }
        return false;
    }
    public void disable() throws CategoryDAOException
    {
    	String s1 = "update categoria set enabled='n' where id_categoria in (select id_categoria from categoria connect by prior id_categoria=categoria_padre start with id_categoria="+ idCategoria + ")";
        try
        {
        	execUpdQuery(s1);
            System.out.println(s1);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s1 + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore disabilitando la categoria. Contattare l'assistenza.");
        }
    }

    public void enable() throws CategoryDAOException
    {
        String s1 = "update categoria set enabled='y' where id_categoria=" + idCategoria ;
        try
        {
            execUpdQuery(s1);
            System.out.println(s1);
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s1 + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore abilitando la categoria. Contattare l'assistenza.");
        }
    }

    @Override
    public Collection allCategoriesList(int idPadre) throws CategoryDAOException {
    	// TODO Auto-generated method stub
    	if(true) throw new CategoryDAOException("Metodo non implementato per Oracle", "Metodo non implementato per Oracle");
    	return null;
    }
    
    private Connection con;
    private int idCategoria;
    private String nome;
    private String descrizione;
    private int idPadre;

}
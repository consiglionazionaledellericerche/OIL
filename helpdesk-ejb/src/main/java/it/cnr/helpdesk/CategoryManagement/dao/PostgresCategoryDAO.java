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

public class PostgresCategoryDAO extends HelpDeskDAO implements CategoryDAO {


	public PostgresCategoryDAO() {
        	con = null;
        	idCategoria = 0;
        	nome = null;
        	descrizione = null;
        	idPadre = 0;
    	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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
		String sqlQuery = "select nextval('seq_category')";
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

	public void executeDelete() {

	}

	public void executeUpdate() throws CategoryDAOException {
		String s = "update categoria set nome='" + StringEscapeUtils.escapeSql(nome) + "',descrizione='"
				+ StringEscapeUtils.escapeSql(descrizione) + "' where id_categoria=" + idCategoria;
		try {
			log(this, s);
			execUpdQuery(s);
		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \0" + s + "\". "
					+ sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la creazione della nuova categoria.");
		}
	}

    public CategoryValueObject allCategories()
        throws CategoryDAOException
    {
		CategoryValueObject cvo = null;
		String sqlQuery = "select id_categoria,nome,descrizione,categoria_padre,enabled "
				+ "from categoria " + "order by nome ";

		try {
			log(this, sqlQuery);
			ResultSet rs = execSQuery(sqlQuery);
			cvo = buildTree(rs, 1);
		} catch (SQLException e) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \0" + sqlQuery + "\". "
					+ e.getMessage());
			e.printStackTrace();
			throw new CategoryDAOException(e.getMessage(), "Si \350 verificato un errore durante il recupero della lista delle categorie. \" +\n    \"Contattare l'assistenza.");

		}
		return cvo;

	}



	// Usa queste istruzioni per ottenere la collection sulla quale iterare le update SQL.
	public Collection allCategoriesList() throws CategoryDAOException

	{
		Collection list = new ArrayList();

		CategoryValueObject categoryValueObject = null;
		String sqlQuery = "select id_categoria,nome,descrizione,categoria_padre, enabled "
				+ "from categoria " + "order by nome ";
		try {
			log(this, sqlQuery);
			ResultSet rs = execQuery(sqlQuery);
			categoryValueObject = buildTree(rs, 1);
			list = treeToList(categoryValueObject, list);

		} catch (SQLException e) {

			log(this,
					"Si è verificato un errore nell'esecuzione della query \""
							+ sqlQuery + "\". " + e.getMessage());
			e.printStackTrace();
			throw new CategoryDAOException(e.getMessage(), "Si \350 verificato un errore durante il recupero della lista delle categorie. \" +\n    \"Contattare l'assistenza.");

		}

		return list;

	}


	public Collection allCategoriesList(int categoryPadre)
			throws CategoryDAOException

	{
		Collection list = new ArrayList();

		CategoryValueObject categoryValueObject = null;
		String sqlQuery = "select id_categoria,nome,descrizione,categoria_padre,enabled "
				+ "from categoria " + "order by nome ";
		try {
			log(this, sqlQuery);
			ResultSet rs = execQuery(sqlQuery);
			categoryValueObject = buildTree(rs, categoryPadre);
			list = treeToList(categoryValueObject, list);

		} catch (SQLException e) {

			log(this,
					"Si è verificato un errore nell'esecuzione della query \""
							+ sqlQuery + "\". " + e.getMessage());
			e.printStackTrace();
			throw new CategoryDAOException(e.getMessage(), "Si \350 verificato un errore durante il recupero della lista degli utenti");

		}

		return list;

	}

    public Collection allEnabledCategoriesList() throws CategoryDAOException {
            Collection list = new ArrayList();
            String s = "select id_categoria,nome,descrizione,categoria_padre,enabled from categoria where enabled='y' order by nome ";
            try
            {
                log(this, s);
                CategoryValueObject categoryvalueobject;
                ResultSet resultset = execQuery(s);
                categoryvalueobject = buildTree(resultset, 1);
                list = treeToList(categoryvalueobject, list);
        
            }
            catch(SQLException sqlexception)
            {
                log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
                sqlexception.printStackTrace();
                throw new CategoryDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista degli utenti");
            }
            return list;
    }
    
    /**
	 * Esegue   
	 *  metodo utilizzato per utente EXPERT che VALIDATOR
	 *  
	 * @param String expert identificativo dell'utente: login. 
	 * 
	 * @throws 
	 * @throws 
	 * 
	 * 
	 */
	public Collection allExpertCategoriesList(String expert)
			throws CategoryDAOException {
		Collection list = new ArrayList();
		CategoryValueObject categoryValueObject = null;
		String sqlQuery = "select categoria.ID_CATEGORIA, categoria.NOME, categoria.DESCRIZIONE, categoria.categoria_padre,enabled,0 "
				+ "from categoria,esperto_categoria "
				+ "where categoria.ID_CATEGORIA=esperto_categoria.CATEGORIA "
				+ " and esperto_categoria.ESPERTO='" + expert + "'"
				+ " order by categoria.nome ";
		try {
			ResultSet rs = execQuery(sqlQuery);
			while (rs.next()) {

				categoryValueObject = new CategoryValueObject(rs.getInt(1), rs
						.getString(2), rs.getString(3), rs.getInt(4), rs
						.getString(5), rs.getInt(6));
				list.add(categoryValueObject);
			}
		} catch (SQLException e) {

			log(this, "Si \350 verificato un errore nell'esecuzione della query \0" + sqlQuery + "\". "
					+ e.getMessage());
			e.printStackTrace();
			throw new CategoryDAOException(e.getMessage(), "Si \350 verificato un errore durante il recupero della lista delle categorie \" +\n    \"cui \350 associato "
					+ expert);

		}
		return list;

	}

	private CategoryValueObject buildTree(ResultSet rs, int categoryPadre)
			throws CategoryDAOException {
		CategoryValueObject categoryValueObject = null;
		HashMap ht = new HashMap();
		int level = 0;
		try {
			while (rs.next()) {
				// Crea un CategoryValueObject a partire dalla categoria estratta dal ResultSet, impostando il livello a 0
				categoryValueObject = new CategoryValueObject(rs.getInt(1), rs
						.getString(2), rs.getString(3), rs.getInt(4), rs
						.getString(5), 0);
				//System.out.println("aggiunto oggetto:"+rs.getInt(1)+ rs.getString(2)+ rs.getString(3)+ rs.getInt(4)+ rs.getString(5));
				ht.put(new Integer(categoryValueObject.getId()),categoryValueObject);
				//System.out.println("aggiunto oggetto:"+categoryValueObject.getId());
				// Se ht contiene l'id del padre
				/*if (ht.containsKey(new Integer(categoryValueObject
								.getIdPadre()))) {
					// Recupera il padre
					CategoryValueObject padre = ((CategoryValueObject) ht
							.get(new Integer(categoryValueObject.getIdPadre())));
					// Imposta il valore dell'attributo livello al livello del padre piu' uno
					categoryValueObject.setLivello(padre.getLivello() + 1);
					// Aggiunge alla lista dei figli del padre il valueobject corrente
					padre.add(categoryValueObject);
					//mette il valueobject corrente in ht
					ht.put(new Integer(categoryValueObject.getId()),
							categoryValueObject);
				} else {*/
					// altrimenti mette semplicemente il value object in ht
				//}
			}
			for(Iterator i = ht.keySet().iterator(); i.hasNext();){
				CategoryValueObject cat = ((CategoryValueObject)ht.get((Integer)i.next()));
				if(recursiveCheckLevel(ht, cat.getId())!=1){			//se non è radice, aggiungi il nodo alla lista children del padre
					((CategoryValueObject) ht.get(new Integer(cat.getIdPadre()))).add(cat);
					//System.out.println("aggiunto figlio:"+cat.getId()+" a padre:"+cat.getIdPadre());
				}						
			}
		} catch (SQLException e) {
			log(this,  "Si \350 verificato un errore nella ricostruzione della gerarchia delle \" +\n    \"categorie. "	+ e.getMessage());
			e.printStackTrace();
			throw new CategoryDAOException(e.getMessage(), "Si \350 verificato un errore nella ricostruzione della gerarchia delle \" +\n    \"categorie. Contattare l'assistenza.");
		}
		return (CategoryValueObject) ht.get(new Integer(categoryPadre));
	}

	private int recursiveCheckLevel(HashMap map, int key){
		CategoryValueObject cat = ((CategoryValueObject)map.get(new Integer(key)));
		if(cat.getIdPadre()==0)				//se è radice: livello=1
			cat.setLivello(1);
		else {
			CategoryValueObject padre = ((CategoryValueObject) map.get(new Integer(cat.getIdPadre())));
			if(padre.getLivello()!=0)		//se il livello di padre è impostato 
				cat.setLivello(padre.getLivello()+1);
			else
				cat.setLivello(recursiveCheckLevel(map, cat.getIdPadre())+1);
		}
		return cat.getLivello();
	}
	
	/**
	 * Esegue   
	 *  metodo utilizzato per utente EXPERT che VALIDATOR
	 *  
	 * @param String login identificativo dell'utente: login. 
	 * 
	 * @throws 
	 * @throws 
	 * 
	 * 
	 */
	//AB Inizio
	public void assignToExpert(String login) throws CategoryDAOException {
		String statement = "insert into ESPERTO_CATEGORIA values ('" + login
				+ "'," + idCategoria + ")";
		try {
			log(this, statement);
			execUpdQuery(statement);
		} catch (SQLException e) {
			log(this, "Si \350 verificato un errore nell'esecuzione dello statement \0" + statement
					+ "\". " + e.getMessage());
			e.printStackTrace();
			throw new CategoryDAOException(e.getMessage(), "Si \350 verificato un errore durante l'assegnazione dell'esperto o validatore alla \" +\n    \"categoria indicata. Contattare l'assistenza.");

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
	public void removeFromExpert(String login) throws CategoryDAOException {
		String statement = "delete from ESPERTO_CATEGORIA where esperto_categoria.CATEGORIA="
				+ idCategoria
				+ " and esperto_categoria.ESPERTO='"
				+ login
				+ "'";
		try {
			log(this, statement);
			execUpdQuery(statement);
		} catch (SQLException e) {
			log(this, "Si \350 verificato un errore nell'esecuzione dello statement \0" + statement
					+ "\". " + e.getMessage());
			e.printStackTrace();
			throw new CategoryDAOException(e.getMessage(), "Si \350 verificato un errore durante la rimozione dell'esperto o validatore dalla \" +\n    \"categoria indicata. Contattare l'assistenza.");

		}

	}

	//AB Fine

	public CategoryValueObject getCategoryDetail(int category)
			throws CategoryDAOException {

		CategoryValueObject categoryValueObject = null;

		String sqlQuery = "select categoria.Id_categoria,categoria.nome,categoria.descrizione,categoria.categoria_padre,categoria.enabled, 0 "
				+ "from categoria "
				+ "where categoria.id_categoria="
				+ category;
		try {
			ResultSet rs = execQuery(sqlQuery);
			if (rs.next()) {
				categoryValueObject = new CategoryValueObject(rs.getInt(1), rs
						.getString(2), rs.getString(3), rs.getInt(4), rs
						.getString(5), rs.getInt(6));
			}
		} catch (SQLException e) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \0" + sqlQuery
					+ "\". " + e.getMessage());
			e.printStackTrace();
			throw new CategoryDAOException(e.getMessage(), "Si \350 verificato un errore durante il recupero delle informazioni \" +\n    \"sulla categoria indicata. Contattare l'assistenza.");

		}
		return categoryValueObject;

	}

	public boolean hasChildren(int id) throws CategoryDAOException {
		String query = "select count(*) from categoria c1, categoria c2 where c1.id_categoria=c2.categoria_padre and c1.id_categoria="
				+ id;
		try {
			ResultSet resultset = execQuery(query);
			if (resultset.next())
				return (resultset.getInt(1) >= 1);
		} catch (SQLException sqlexception) {
			log(this,
					"Si \350 verificato un errore nell'esecuzione della query \""
							+ query + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new CategoryDAOException(
					sqlexception.getMessage(),
					"Si \350 verificato un errore abilitando la categoria. Contattare l'assistenza.");
		}
		return false;
	}

	public boolean hasDisabledParent(int id) throws CategoryDAOException {
		String query = "";
		try {
			ResultSet resultset;
			for (int pid = id; pid != 0; pid = resultset.getInt(2)) {
				query = "select id_categoria, categoria_padre, enabled from categoria where id_categoria="
						+ pid;
				resultset = execQuery(query);
				if (resultset.next()) {
					if (resultset.getString(3).equals("n") && (pid != id))
						return true;
				} else
					return false;
			}
		} catch (SQLException sqlexception) {
			log(this,
					"Si \350 verificato un errore nell'esecuzione della query \""
							+ query + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new CategoryDAOException(
					sqlexception.getMessage(),
					"Si \350 verificato un errore abilitando la categoria. Contattare l'assistenza.");
		}
		return false;
	}

	public void disable() throws CategoryDAOException {
		Collection c = subTreeList(idCategoria);
		Iterator iter = c.iterator();
		while (iter.hasNext()) {
			CategoryValueObject cvo = (CategoryValueObject) iter.next();
			//TODO deve essere disabilitato tutto il sottoalbero di cui il nodo è radice 
			String s1 = "update categoria set enabled='n' where id_categoria=" + cvo.getId();
			try {
				execUpdQuery(s1);
				System.out.println(s1);
			} catch (SQLException sqlexception) {
				log(this,
						"Si \350 verificato un errore nell'esecuzione della query \""
								+ s1 + "\". " + sqlexception.getMessage());
				sqlexception.printStackTrace();
				throw new CategoryDAOException(
						sqlexception.getMessage(),
						"Si \350 verificato un errore disabilitando la categoria. Contattare l'assistenza.");
			}
		}
	}

	public void enable() throws CategoryDAOException {
		String s1 = "update categoria set enabled='y' where id_categoria="
				+ idCategoria;
		try {
			execUpdQuery(s1);
			System.out.println(s1);
		} catch (SQLException sqlexception) {
			log(this,
					"Si \350 verificato un errore nell'esecuzione della query \""
							+ s1 + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new CategoryDAOException(
					sqlexception.getMessage(),
					"Si \350 verificato un errore abilitando la categoria. Contattare l'assistenza.");
		}
	}


	private Collection treeToList(CategoryValueObject cvo, Collection c) {
		Enumeration e = cvo.elements();
		c.add(cvo);
		if (e != null) { //ha dei figli
			for (; e.hasMoreElements();) {
				c = treeToList((CategoryValueObject) e.nextElement(), c);
			}
		}
		return c;
	}


	/*
	 * TODO Rendere più efficiente la costruzione del sotto-albero, evitando di ricostruire  tutto l'albero
	 * Sfrutare la proprietà per la quale i figli di un nodo hanno id maggiore. Per l'implementazione si
	 * possono usare i ResultSet scrollabili.
	 */
	public Collection subTreeList(int categoryId) throws CategoryDAOException {
		Collection list = new ArrayList();

		CategoryValueObject categoryValueObject = null;
		String sqlQuery = "select id_categoria,nome,descrizione,categoria_padre, enabled "
				+ "from categoria " + "order by nome ";
		try {
			log(this, sqlQuery);
			ResultSet rs = execQuery(sqlQuery);
			categoryValueObject = buildTree(rs, categoryId);
			list = treeToList(categoryValueObject, list);

		} catch (SQLException e) {

			log(this,
					"Si è verificato un errore nell'esecuzione della query \""
							+ sqlQuery + "\". " + e.getMessage());
			e.printStackTrace();
			throw new CategoryDAOException(e.getMessage(), "Si \350 verificato un errore durante il recupero della lista delle categorie. \" +\n    \"Contattare l'assistenza.");

		}

		return list;

	}

    private Connection con;
    private int idCategoria;
    private String nome;
    private String descrizione;
    private int idPadre;

}

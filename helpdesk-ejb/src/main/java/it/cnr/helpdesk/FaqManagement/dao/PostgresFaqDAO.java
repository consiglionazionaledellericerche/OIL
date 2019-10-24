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
package it.cnr.helpdesk.FaqManagement.dao;

/**
 * <p>Title: OIL - Online Interactive heLpdesk</p>
 * <p>Description: A Web Based Help Desk Tool</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: OIL</p>
 * @author Andrea Bei, Roberto Puccinelli
 * @version 1.0
 */
import it.cnr.helpdesk.CategoryManagement.dao.PostgresCategoryDAO;
import it.cnr.helpdesk.CategoryManagement.valueobjects.CategoryValueObject;
import it.cnr.helpdesk.FaqManagement.dto.FaqDTO;
import it.cnr.helpdesk.FaqManagement.exceptions.FaqDAOException;
import it.cnr.helpdesk.dao.FaqDAO;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PostgresFaqDAO extends HelpDeskDAO implements FaqDAO {

	private Connection con = null;

	private int idFaq = 0;
	private String titolo = null;
	private String descrizione = null;
	private String originatore = null;
	private int idCategoria = 0;

	public PostgresFaqDAO() {
		super();
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public void setIdFaq(int idFaq) {
		this.idFaq = idFaq;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setOriginatore(String originatore) {
		this.originatore = originatore;
	}

	public void createConnection(String instance) throws FaqDAOException {
		try {
			super.createConnection(instance);
		} catch (HelpDeskDAOException e) {
			throw new FaqDAOException(e.getMessage(), e.getUserMessage());
		}
	}

	public void closeConnection() throws FaqDAOException {
		try {
			super.closeConnection();
		} catch (HelpDeskDAOException e) {
			throw new FaqDAOException(e.getMessage(), e.getUserMessage());
		}
	}

	public int executeInsert() throws FaqDAOException {

		// inserimento di una FAQ

		// ricavo un progressivo di sequence
		String sqlQuery = "select nextval('seq_faq')";
		ResultSet rs = null;
		int nextID = 0;
		try {
			rs = execQuery(sqlQuery);
			if (rs.next()) {
				nextID = rs.getInt(1);
			}
			// inserisco il record in segnalazione
			sqlQuery = "insert into faq(ID_FAQ, TITOLO, DESCRIZIONE, CATEGORIA, ORIGINATORE)" + " values(" + nextID + ",'" + formatString(titolo) + "','" + formatString(descrizione) + "'," + idCategoria + ",'" + formatString(originatore) + "')";

			log(this, sqlQuery);
			execUpdQuery(sqlQuery);

		} catch (SQLException e) {

			log(this, "Si è verificato un errore nell'esecuzione della query \"" + sqlQuery + "\". " + e.getMessage());
			e.printStackTrace();
			throw new FaqDAOException(e.getMessage(), "Si è verificato un errore durante la creazione della nuova FAQ.");

		}
		return nextID;
	}

	public void executeDelete() throws FaqDAOException {
		// ricavo un progressivo di sequence
		String sqlQuery = null;
		try {
			sqlQuery = "delete from faq " + " where faq.id_faq=" + idFaq;
			log(this, sqlQuery);
			execUpdQuery(sqlQuery);
		} catch (SQLException e) {
			log(this, "Si è verificato un errore nell'esecuzione della query \"" + sqlQuery + "\". " + e.getMessage());
			e.printStackTrace();
			throw new FaqDAOException(e.getMessage(), "Si è verificato un errore durante la cancellazione della FAQ.");
		}
	}

	public void executeUpdate() throws FaqDAOException {
		// modifica la Faq
		String statement = "update faq set titolo='" + formatString(titolo) + "', " + " descrizione='" + formatString(descrizione) + "', " + " categoria=" + idCategoria + ", " + " originatore='" + originatore + "' " + " where id_faq=" + idFaq;
		try {
			log(this, statement);
			execUpdQuery(statement);
		} catch (SQLException e) {
			log(this, "Si è verificato un errore nell'esecuzione dello statement \"" + statement + "\". " + e.getMessage());
			e.printStackTrace();
			throw new FaqDAOException(e.getMessage(), "Si è verificato un errore durante il cambiamento della Faq indicata. Contattare l'assistenza.");

		}

	}

	public Collection getFaqList(FaqDTO faqDTO, String instance) throws FaqDAOException {
		// restituisce la lista di Faq della categoria specificata
		Collection c = null;
		Collection categoryList = null;
		PostgresCategoryDAO pcd = new PostgresCategoryDAO();
		try {
			pcd.createConnection(instance);
			categoryList = pcd.allCategoriesList(faqDTO.getIdCategoria());
			pcd.closeConnection();
		} catch (Exception ex) {

		}
		String listOfCategories = "";
		Iterator i = categoryList.iterator();
		while (i.hasNext()) {
			listOfCategories = listOfCategories + ((CategoryValueObject) i.next()).getId();
			if (i.hasNext())
				listOfCategories = listOfCategories + ",";
		}
		listOfCategories = listOfCategories + "";

		String sqlQuery = "select faq.id_faq, faq.titolo, faq.descrizione, faq.categoria, categoria.nome, faq.originatore " + " from faq,categoria " + " where " + " categoria.ID_CATEGORIA=faq.CATEGORIA and " + " faq.CATEGORIA in ( " + listOfCategories + " )";
		try {
			log(this, sqlQuery);
			ResultSet rs = execQuery(sqlQuery);
			FaqDTO faqDTO2 = null;
			c = new ArrayList();
			while (rs.next()) {
				faqDTO2 = new FaqDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6));
				c.add(faqDTO2);
			}

			// scandisce la le faq e popola la collection di value object
		} catch (SQLException e) {
			log(this, "Si è verificato un errore nell'esecuzione della query \"" + sqlQuery + "\". " + e.getMessage());
			e.printStackTrace();
			throw new FaqDAOException(e.getMessage(), "Si è verificato un errore durante il recupero della lista delle Faq. Contattare l'assistenza.");

		}
		return c;

	}

	public FaqDTO getFaqDetail() throws FaqDAOException {

		FaqDTO faqDTO = null;
		// esegue la query sulle FAQ per prelevare il dettaglio di una Faq
		String sqlQuery = " select faq.id_faq, faq.titolo, faq.descrizione, faq.categoria, categoria.nome, faq.originatore " + " from faq, categoria " + " where categoria.ID_CATEGORIA=faq.CATEGORIA and " + " faq.id_faq=" + idFaq;

		try {
			ResultSet rs = execQuery(sqlQuery);
			if (rs.next()) {
				faqDTO = new FaqDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6));
			}
		} catch (SQLException e) {
			log(this, "Si è verificato un errore nell'esecuzione della query \"" + sqlQuery + "\". " + e.getMessage());
			e.printStackTrace();
			throw new FaqDAOException(e.getMessage(), "Si è verificato un errore durante il recupero delle informazioni sulla categoria indicata. Contattare l'assistenza.");

		}
		return faqDTO;

	}
	/*
	 * private FaqDTO buildTree(ResultSet rs) throws FaqDAOException { FaqDTO faqDTO=null; HashMap ht=new HashMap(); int level=0; try { while (rs.next()) { faqDTO=new FaqDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),0); if (ht.containsKey(new Integer(faqDTO. .getIdPadre()))) {
	 * 
	 * FaqDTO padre=((FaqDTO) ht.get(new Integer(categoryValueObject.getIdPadre()))); categoryValueObject.setLivello(padre.getLivello()+1); padre.add(categoryValueObject); ht.put(new Integer(categoryValueObject.getId()),categoryValueObject); } else { ht.put(new Integer(categoryValueObject.getId()),categoryValueObject); } } } catch(SQLException e) { log(this,res.getString("errore_ricostruzione_lista_categorie")+e.getMessage()); e.printStackTrace(); throw new
	 * CategoryDAOException(e.getMessage(),res.getString("errore_ricostruzione_lista_categorie2")); } return (FaqDTO) ht.get(new Integer(1)); }
	 */

}

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

import it.cnr.helpdesk.FaqManagement.dto.FaqDTO;
import it.cnr.helpdesk.FaqManagement.exceptions.FaqDAOException;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.dao.FaqDAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class OracleFaqDAO extends HelpDeskDAO implements FaqDAO {

	public OracleFaqDAO() {
		con = null;
		idFaq = 0;
		titolo = null;
		descrizione = null;
		originatore = null;
		idCategoria = 0;
	}

	public void setIdCategoria(int i) {
		idCategoria = i;
	}

	public void setIdFaq(int i) {
		idFaq = i;
	}

	public void setTitolo(String s) {
		titolo = s;
	}

	public void setDescrizione(String s) {
		descrizione = s;
	}

	public void setOriginatore(String s) {
		originatore = s;
	}

	public void createConnection(String instance) throws FaqDAOException {
		try {
			super.createConnection(instance);
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new FaqDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

	public void closeConnection() throws FaqDAOException {
		try {
			super.closeConnection();
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new FaqDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

	public int executeInsert() throws FaqDAOException {
		String s = "select seq_faq.nextval from dual";
		Object obj = null;
		int i = 0;
		try {
			ResultSet resultset = execQuery(s);
			if (resultset.next())
				i = resultset.getInt(1);
			s = "insert into faq(ID_FAQ, TITOLO, DESCRIZIONE, CATEGORIA, ORIGINATORE) values(" + i + ",'" + formatString(titolo) + "','" + formatString(descrizione) + "'," + idCategoria + ",'" + formatString(originatore) + "')";
			log(this, s);
			execUpdQuery(s);
		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new FaqDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la creazione della nuova FAQ.");
		}
		return i;
	}

	public void executeDelete() throws FaqDAOException {
		String s = null;
		try {
			s = "delete from faq  where faq.id_faq=" + idFaq;
			log(this, s);
			execUpdQuery(s);
		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new FaqDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la cancellazione della FAQ.");
		}
	}

	public void executeUpdate() throws FaqDAOException {
		String s = "update faq set titolo='" + formatString(titolo) + "', " + " descrizione='" + formatString(descrizione) + "', " + " categoria=" + idCategoria + ", " + " originatore='" + originatore + "' " + " where id_faq=" + idFaq;
		try {
			log(this, s);
			execUpdQuery(s);
		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione dello statement \"" + s + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new FaqDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il cambiamento della Faq indicata. Contattare l'assistenza.");
		}
	}

	public Collection getFaqList(FaqDTO faqdto, String instance) throws FaqDAOException {
		ArrayList arraylist = null;
		String s = "select faq.id_faq, faq.titolo, faq.descrizione, faq.categoria, categoria.nome, faq.originatore  from faq,categoria  where  categoria.ID_CATEGORIA=faq.CATEGORIA and  faq.CATEGORIA in (  select categoria.ID_CATEGORIA  from categoria  where faq.CATEGORIA=categoria.ID_CATEGORIA  start with id_categoria=" + faqdto.getIdCategoria() + " connect by prior id_categoria=categoria_padre )";
		try {
			log(this, s);
			ResultSet resultset = execQuery(s);
			arraylist = new ArrayList();
			FaqDTO faqdto1;
			for (; resultset.next(); arraylist.add(faqdto1))
				faqdto1 = new FaqDTO(resultset.getInt(1), resultset.getString(2), resultset.getString(3), resultset.getInt(4), resultset.getString(5), resultset.getString(6));

		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new FaqDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero della lista delle Faq. Contattare l'assistenza.");
		}
		return arraylist;
	}

	public FaqDTO getFaqDetail() throws FaqDAOException {
		FaqDTO faqdto = null;
		String s = " select faq.id_faq, faq.titolo, faq.descrizione, faq.categoria, categoria.nome, faq.originatore  from faq, categoria  where categoria.ID_CATEGORIA=faq.CATEGORIA and  faq.id_faq=" + idFaq;
		try {
			ResultSet resultset = execQuery(s);
			if (resultset.next())
				faqdto = new FaqDTO(resultset.getInt(1), resultset.getString(2), resultset.getString(3), resultset.getInt(4), resultset.getString(5), resultset.getString(6));
		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new FaqDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero delle informazioni sulla categoria indicata. Contattare l'assistenza.");
		}
		return faqdto;
	}

	private Connection con;
	private int idFaq;
	private String titolo;
	private String descrizione;
	private String originatore;
	private int idCategoria;
}
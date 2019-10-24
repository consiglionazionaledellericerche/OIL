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
 * Created on 8-giu-2006
 *
 */
package it.cnr.helpdesk.EventManagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import it.cnr.helpdesk.EventManagement.exceptions.EventDAOException;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.StateMachineManagement.tasks.Task;
import it.cnr.helpdesk.dao.EventDAO;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;

/**
 * @author Aldo Stentella Liberati
 *


 */
public class OracleEventDAO extends HelpDeskDAO implements EventDAO {

	public void closeConnection() throws EventDAOException {
		try {
			super.closeConnection();
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new EventDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

	public void createConnection(String instance) throws EventDAOException {
		try {
			super.createConnection(instance);
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new EventDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

	/** 
	 * <p>Scrive l'evento nella tabella del DB</p>
	 * @param evo l'oggetto evento da memorizzare
	 */

	public String register(EventValueObject evo) throws EventDAOException {
		//String s = "select TO_CHAR(sysdate,'DD-MM-YYYY HH24:MI:SS') from dual";
		SimpleDateFormat fullForm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALIAN);
		String s1 = "";
		String ora = "";
		try {
			//ResultSet rs = execQuery(s);
			//if(rs.next())
				//ora = rs.getString(1);
			ora = fullForm.format(new Date());
			while(getProblemEventDetail(Integer.parseInt(""+evo.getIdSegnalazione()), ora)!=null){
            	try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				ora = fullForm.format(new Date());
	    		log(this, "evento ritardato: "+ora);
			}
			//else
				//throw new EventDAOException("Error evaluating system date","Errore: impossibile stabilire la data di sistema");
			
			s1 = "insert into evento(SEGNALAZIONE, DATETIME, TIPO_EVENTO, STATO, CATEGORIA, NOTA, ORIGINATORE) " +
			  "values(" + evo.getIdSegnalazione() + ", TO_DATE('" + ora + "','DD-MM-YYYY HH24:MI:SS') ,"+evo.getEventType()+","+evo.getState()+"," + evo.getCategory() + ",'" + evo.getNote2SQL() + "','" + evo.getOriginatoreEvento() + "')";
            log(this, s1);
            execUpdQuery(s1);
        } catch(SQLException sqlexception1) {
            log(this, "Si \350 verificato un errore nell'esecuzione dello statement \"" + s1 + "\". " + sqlexception1.getMessage());
            sqlexception1.printStackTrace();
            throw new EventDAOException(sqlexception1.getMessage(), "Si \350 verificato un errore durante il tracciamento dell'evento di assegnazione. Contattare l'assistenza.");
        }
        return ora;
	}

	public String getTemplate(int i) throws EventDAOException {
		String s = "";
		String s1 = "select t.TEMPLATE_EMAIL from tipo_evento e, template t where e.template = t.id_template and e.ID_TIPOEVENTO=" + i;
		try {
			log(this, s1);
			ResultSet resultset = execQuery(s1);
			if(resultset.next())
				s = resultset.getString(1);
		} catch(SQLException sqlexception) {
			throw new EventDAOException(sqlexception.getMessage(),"Si \350 verificato un errore durante la creazione delle mail di notifica. Contattare l'assistenza.");
		}
		return s;
	}
	
	public Collection getProblemEvents(int segnalazione) throws EventDAOException {
		ArrayList arraylist = new ArrayList();
		String s = "select evento.SEGNALAZIONE,TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS'),evento.TIPO_EVENTO, tipo_evento.NOME_TIPO_EVENTO,evento.STATO, stato.DESCRIZIONE, evento.CATEGORIA,categoria.NOME, evento.NOTA,evento.ORIGINATORE,NVL(utente.NOME,'-'),NVL(utente.COGNOME,'-') from evento, tipo_evento, stato, categoria, utente where evento.SEGNALAZIONE=" + segnalazione + " and " + "evento.TIPO_EVENTO=tipo_evento.ID_TIPOEVENTO and " + "evento.STATO=stato.ID_STATO(+) and " + "evento.CATEGORIA=categoria.ID_CATEGORIA(+) and " + "evento.ORIGINATORE=utente.USERID " + "order by datetime";
		try
		{
			log(this, s);
			EventValueObject eventvalueobject;
			for(ResultSet resultset = execQuery(s); resultset.next(); arraylist.add(eventvalueobject)){
				eventvalueobject = new EventValueObject(resultset.getLong(1), resultset.getString(2), resultset.getInt(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getInt(7), resultset.getString(8), resultset.getString(9), resultset.getString(10), resultset.getString(11) + " " + resultset.getString(12));
				eventvalueobject.setInstance(getInstance());
			}
		}
		catch(SQLException sqlexception)
		{
			log(this, "eccezione: " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new EventDAOException(sqlexception.getMessage());
		}
		return arraylist;
	}

	public EventValueObject getProblemEventDetail(int segnalazione, String dataEvento) throws EventDAOException {
		EventValueObject eventvalueobject = null;
		String s = "select evento.SEGNALAZIONE,TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS'),evento.TIPO_EVENTO, tipo_evento.NOME_TIPO_EVENTO,evento.STATO, stato.DESCRIZIONE, evento.CATEGORIA,categoria.NOME, evento.NOTA,evento.ORIGINATORE,NVL(utente.NOME,'-'),NVL(utente.COGNOME,'-') from evento, tipo_evento, stato, categoria, utente where evento.SEGNALAZIONE=" + segnalazione + " and " + "evento.TIPO_EVENTO=tipo_evento.ID_TIPOEVENTO and " + "evento.STATO=stato.ID_STATO (+) and " + "evento.CATEGORIA=categoria.ID_CATEGORIA (+) and " + "evento.ORIGINATORE=utente.USERID and " + "evento.DATETIME=TO_DATE('" + dataEvento + "','DD-MM-YYYY HH24:MI:SS')";
		try {
			log(this, s);
			ResultSet resultset = execQuery(s);
			if(resultset.next()){
				eventvalueobject = new EventValueObject(resultset.getLong(1), resultset.getString(2), resultset.getInt(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getInt(7), resultset.getString(8), resultset.getString(9), resultset.getString(10), resultset.getString(11) + " " + resultset.getString(12));
				eventvalueobject.setInstance(getInstance());
			}
		} catch(SQLException sqlexception) {
			log(this, "eccezione: " + sqlexception.getMessage());
			sqlexception.printStackTrace();
			throw new EventDAOException(sqlexception.getMessage());
		}
		return eventvalueobject;
	}

/** <p>Restituisce una lista di task che non necessitano di essere eseguiti in una transazione,
 *     da eseguire in corrispondenza di un dato tipo di evento</p>
 *  <p>L'evento cambio di stato fa eccezione in quanto la lista di task è
 *     determinata dalla StateMachine stessa sulla base della transizione invocata</p>
 * @param eventType l'identificativo del tipo di evento
 * @return un ArrayList di oggetti Task
 */
public ArrayList getNormalTasks(int eventType) throws EventDAOException {
	String s = "Select t.taskclass "+
	"from tipo_evento_task tet, task t "+
	"where tet.task=t.id "+
	"and tet.tipo_evento="+eventType+
	"and tet.transaction_required='n'";
ArrayList arraylist = null;
try {
	ResultSet rs = execQuery(s);
	arraylist = new ArrayList();
	while (rs.next()) {
		String taskName=rs.getString(1);
		Task task;
		try {
			task = (Task) Class.forName(taskName).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new EventDAOException(e.getMessage(),
			"Impossibile istanziare oggetto "+taskName+".");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new EventDAOException(e.getMessage(),
					"Impossibile accedere alla definizione della classe "+taskName+".");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new EventDAOException(e.getMessage(),
					"Impossibile trovare la definizione della classe "+taskName+".");
		}
		arraylist.add(task);
	}
    } catch(SQLException sqlexception) {
        log(this, "eccezione: " + sqlexception.getMessage());
        sqlexception.printStackTrace();
        throw new EventDAOException(sqlexception.getMessage());
    }
	return arraylist;
}

/** <p>Restituisce una lista di task che necessitano di essere eseguiti in una transazione, 
 *     da eseguire in corrispondenza di un dato tipo di evento</p>
 *  <p>L'evento cambio di stato fa eccezione in quanto la lista di task è
 *     determinata dalla StateMachine stessa sulla base della transizione invocata</p>
 * @param eventType l'identificativo del tipo di evento
 * @return un ArrayList di oggetti Task
 */
public ArrayList getTransTasks(int eventType) throws EventDAOException {
	String s = "Select t.taskclass "+
	"from tipo_evento_task tet, task t "+
	"where tet.task=t.id "+
	"and tet.tipo_evento="+eventType+
	"and tet.transaction_required='y'";
ArrayList arraylist = null;
try {
	ResultSet rs = execQuery(s);
	arraylist = new ArrayList();
	while (rs.next()) {
		String taskName=rs.getString(1);
		Task task;
		try {
			task = (Task) Class.forName(taskName).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new EventDAOException(e.getMessage(),
			"Impossibile istanziare oggetto "+taskName+".");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new EventDAOException(e.getMessage(),
					"Impossibile accedere alla definizione della classe "+taskName+".");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new EventDAOException(e.getMessage(),
					"Impossibile trovare la definizione della classe "+taskName+".");
		}
		arraylist.add(task);
	}
    } catch(SQLException sqlexception) {
        log(this, "eccezione: " + sqlexception.getMessage());
        sqlexception.printStackTrace();
        throw new EventDAOException(sqlexception.getMessage());
    }
	return arraylist;
}

/** <p>Recupera tutti gli eventi associati ad una segnalazione, filtrando eventualmente quelli con flag HIDDEN='y'</p>
 * @param segnalazione	l'ID della segnalazione 
 * @param filter		se impostato a <i>True</i> esclude dalla collection gli eventi HIDDEN
 * @return 				una collezione di EventValueObject (incluso il flag ALTERABLE)
 */
public Collection getProblemEvents(long segnalazione, boolean filter) throws EventDAOException {
	ArrayList arraylist = new ArrayList();
	String filtro = " ";
	if(filter)
		filtro= " and e.HIDDEN = 'n' ";
	String s = "select e.SEGNALAZIONE,TO_CHAR(e.DATETIME,'DD-MM-YYYY HH24:MI:SS'),e.TIPO_EVENTO, te.NOME_TIPO_EVENTO,e.STATO, s.DESCRIZIONE, e.CATEGORIA,c.NOME, e.NOTA,e.ORIGINATORE,NVL(u.NOME,'-'),NVL(u.COGNOME,'-'), e.ALTERABLE " +
			   "from evento e, tipo_evento te, stato s, categoria c, utente u " +
			   "where e.SEGNALAZIONE=" + segnalazione + " and e.TIPO_EVENTO=te.ID_TIPOEVENTO and e.STATO=s.ID_STATO (+) and e.CATEGORIA=c.ID_CATEGORIA (+) and e.ORIGINATORE=u.USERID " + filtro + " order by datetime";
	try
	{
		log(this, s);
		EventValueObject eventvalueobject;
		for(ResultSet resultset = execQuery(s); resultset.next(); arraylist.add(eventvalueobject)){
			eventvalueobject = new EventValueObject(resultset.getLong(1), resultset.getString(2), resultset.getInt(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getInt(7), resultset.getString(8), resultset.getString(9), resultset.getString(10), resultset.getString(11) + " " + resultset.getString(12), resultset.getString(13).startsWith("y"));
			eventvalueobject.setInstance(getInstance());
		}
	}
	catch(SQLException sqlexception)
	{
		log(this, "eccezione: " + sqlexception.getMessage());
		sqlexception.printStackTrace();
		throw new EventDAOException(sqlexception.getMessage());
	}
	return arraylist;
}

/**<p>setta il flag hidden ad un evento</p>
 * @param evo l'evento in oggetto
 */
public void hideEvent(EventValueObject evo) throws EventDAOException {
	String query = "update EVENTO set HIDDEN='y' where SEGNALAZIONE="+evo.getIdSegnalazione()+" and TO_CHAR(DATETIME,'DD-MM-YYYY HH24:MI:SS')='"+evo.getTime()+"'";
	try {
		log(this, query);
		execUpdQuery(query);
	} catch (SQLException e) {
		throw new EventDAOException(e.getMessage(),"Si e' verificato un errore SQL: "+e.getLocalizedMessage());
	}
}

/**<p>setta il flag alterable ad un evento</p>
 * @param evo l'evento in oggetto
 */
public void makeAlterable(EventValueObject evo) throws EventDAOException {
	String query = "update EVENTO set ALTERABLE='y' where SEGNALAZIONE="+evo.getIdSegnalazione()+" and TO_CHAR(DATETIME,'DD-MM-YYYY HH24:MI:SS')='"+evo.getTime()+"'";
	try {
		log(this, query);
		execUpdQuery(query);
	} catch (SQLException e) {
		throw new EventDAOException(e.getMessage(),"Si e' verificato un errore SQL: "+e.getLocalizedMessage());
	}
}

/**<p>toglie il flag alterable ad un evento</p>
 * @param evo l'evento in oggetto
 */
public void unmakeAlterable(EventValueObject evo) throws EventDAOException {
	String query = "update EVENTO set ALTERABLE='n' where SEGNALAZIONE="+evo.getIdSegnalazione()+" and TO_CHAR(DATETIME,'DD-MM-YYYY HH24:MI:SS')='"+evo.getTime()+"'";
	try {
		log(this, query);
		execUpdQuery(query);
	} catch (SQLException e) {
		throw new EventDAOException(e.getMessage(),"Si e' verificato un errore SQL: "+e.getLocalizedMessage());
	}	
}

}

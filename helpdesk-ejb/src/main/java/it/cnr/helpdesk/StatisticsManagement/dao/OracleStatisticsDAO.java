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

 

package it.cnr.helpdesk.StatisticsManagement.dao;

import it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException;
import it.cnr.helpdesk.StatisticsManagement.valueobjects.*;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.dao.StatisticsDAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class OracleStatisticsDAO extends HelpDeskDAO implements StatisticsDAO {

	public OracleStatisticsDAO() {
	}

	public void setStartDate(String s) {
		startDate = s;
	}

	public void setEndDate(String s) {
		endDate = s;
	}

	public void setSamplingInterval(Integer integer) {
		samplingInterval = integer;
	}

	public void setHelpdeskUserID(String s) {
		helpdeskUserID = s;
	}

	public void setHelpdeskUserProfile(int i) {
		helpdeskUserProfile = i;
	}

	public void sethelpDeskUser(UserValueObject uservalueobject) {
		helpdeskUser = uservalueobject;
	}

	public void setStatisticsType(int i) {
		statisticsType = i;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public Integer getSamplingInterval() {
		return samplingInterval;
	}

	public String getHelpdeskUserID() {
		return helpdeskUserID;
	}

	public int getHelpdeskUserProfile() {
		return helpdeskUserProfile;
	}

	public UserValueObject getHelpdeskUser() {
		return helpdeskUser;
	}

	public int getStatisticsType() {
		return statisticsType;
	}

	public void createConnection(String instance) throws StatisticsDAOException {
		try {
			super.createConnection(instance);
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new StatisticsDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

	public void closeConnection() throws StatisticsDAOException {
		try {
			super.closeConnection();
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new StatisticsDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

	public int getOpenClosedCount() throws StatisticsDAOException {
		int i = -1;
		String s = "select count(*) from evento ev1,evento ev2 where         ev1.segnalazione=ev2.segnalazione   and   ev1.tipo_evento=0   and   ev2.tipo_evento=1   and   ev2.stato=4  and   (ev1.datetime between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss')) " + "  and   (ev2.datetime between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss'))"
				+ "  and   ev2.datetime = (select min(datetime) from evento " + "                        where  evento.segnalazione=ev2.segnalazione " + "                          and  tipo_evento=1 " + "                          and  stato=4)";
		try {
			log(this, s);
			ResultSet resultset = execQuery(s);
			if (resultset.next())
				i = resultset.getInt(1);
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query per il conteggio dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " e il " + endDate);
			throw new StatisticsDAOException(exception.getMessage(), "Si \350 verificato un errore nell'esecuzione della query per il conteggio dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " e il " + endDate);
		}
		if (i == -1) {
			log(this, "Si \350 verificato un errore nel calcolo del numero di problemi aperti e chiusi nel periodo compreso tra il" + startDate + " ed il " + endDate);
			throw new StatisticsDAOException("Risultato non ammesso: -1", "Si \350 verificato un errore nell'esecuzione della query per il conteggio dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " e il " + endDate);
		} else {
			return i;
		}
	}

	public float getMinResponseTime() throws StatisticsDAOException {
		float f = -1F;
		String s = "select min(ev2.datetime-ev1.datetime) from evento ev1,evento ev2 where         ev1.segnalazione=ev2.segnalazione   and   ev1.tipo_evento=0   and   ev2.tipo_evento=1   and   ev2.stato=4  and   (ev1.datetime between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss')) " + "  and   (ev2.datetime between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate
				+ "','dd/mm/yyyy hh24:mi:ss'))" + "  and   ev2.datetime = (select min(datetime) from evento " + "                        where  evento.segnalazione=ev2.segnalazione " + "                          and  tipo_evento=1 " + "                          and  stato=4)";
		try {
			log(this, s);
			ResultSet resultset = execQuery(s);
			if (resultset.next())
				f = resultset.getFloat(1);
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query per il calcolo del minimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " ed il " + endDate);
			throw new StatisticsDAOException(exception.getMessage(), "Si \350 verificato un errore nell'esecuzione della query per il calcolo del minimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " ed il " + endDate);
		}
		if (f == -1F) {
			log(this, "Si \350 verificato un errore nel calcolo del minimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il" + startDate + " ed il " + endDate);
			throw new StatisticsDAOException("Risultato non ammesso: -1", "Si \350 verificato un errore nell'esecuzione della query per il calcolo del minimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " ed il " + endDate);
		} else {
			return f;
		}
	}

	public float getMaxResponseTime() throws StatisticsDAOException {
		float f = -1F;
		String s = "select max(ev2.datetime-ev1.datetime) from evento ev1,evento ev2 where         ev1.segnalazione=ev2.segnalazione   and   ev1.tipo_evento=0   and   ev2.tipo_evento=1   and   ev2.stato=4  and   (ev1.datetime between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss')) " + "  and   (ev2.datetime between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate
				+ "','dd/mm/yyyy hh24:mi:ss'))" + "  and   ev2.datetime = (select min(datetime) from evento " + "                        where  evento.segnalazione=ev2.segnalazione " + "                          and  tipo_evento=1 " + "                          and  stato=4)";
		try {
			log(this, s);
			ResultSet resultset = execQuery(s);
			if (resultset.next())
				f = resultset.getFloat(1);
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query per il calcolo del massimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " ed il " + endDate);
			throw new StatisticsDAOException(exception.getMessage(), "Si \350 verificato un errore nell'esecuzione della query per il calcolo del massimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " ed il " + endDate);
		}
		if (f == -1F) {
			log(this, "Si \350 verificato un errore nel calcolo del massimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il" + startDate + " ed il " + endDate);
			throw new StatisticsDAOException("Risultato non ammesso: -1", "Si \350 verificato un errore nell'esecuzione della query per il calcolo del massimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " ed il " + endDate);
		} else {
			return f;
		}
	}

	public float getAverageResponseTime() throws StatisticsDAOException {
		float f = -1F;
		String s = "select avg(ev2.datetime-ev1.datetime) from evento ev1,evento ev2 where         ev1.segnalazione=ev2.segnalazione   and   ev1.tipo_evento=0   and   ev2.tipo_evento=1   and   ev2.stato=4  and   (ev1.datetime between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss')) " + "  and   (ev2.datetime between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate
				+ "','dd/mm/yyyy hh24:mi:ss'))" + "  and   ev2.datetime = (select min(datetime) from evento " + "                        where  evento.segnalazione=ev2.segnalazione " + "                          and  tipo_evento=1 " + "                          and  stato=4)";
		try {
			log(this, s);
			ResultSet resultset = execQuery(s);
			if (resultset.next())
				f = resultset.getFloat(1);
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query per il calcolo del tempo medio di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " ed il " + endDate);
			throw new StatisticsDAOException(exception.getMessage(), "Si \350 verificato un errore nell'esecuzione della query per il calcolo del tempo medio di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " e il " + endDate);
		}
		if (f == -1F) {
			log(this, "Si \350 verificato un errore nel calcolo del tempo medio di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il" + startDate + " ed il " + endDate);
			throw new StatisticsDAOException("Risultato non ammesso: -1", "Si \350 verificato un errore nell'esecuzione della query per il calcolo del tempo medio di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il " + startDate + " e il " + endDate);
		} else {
			return f;
		}
	}

	public OverallStatisticsDTO getOverallStatistics() throws StatisticsDAOException {
		OverallStatisticsDTO overallstatisticsdto = null;
		String s = null;
		System.out.println("statisticsType=" + statisticsType);
		System.out.println("helpdeskUserID=" + helpdeskUserID);
		System.out.println("helpdeskUserProfile=" + helpdeskUserProfile);
		if (statisticsType == 2)
			s = "select count(*),min(ev2.datetime-ev1.datetime),max(ev2.datetime-ev1.datetime),avg(ev2.datetime-ev1.datetime) from evento ev1,evento ev2 where         ev1.segnalazione=ev2.segnalazione   and   ev1.tipo_evento=0   and   ev2.tipo_evento=1   and   ev2.stato=4  and   (ev1.datetime between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss')) " + "  and   (ev2.datetime between to_date('" + startDate
					+ "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss'))" + "  and   ev2.datetime = (select min(datetime) from evento " + "                        where  evento.segnalazione=ev2.segnalazione " + "                          and  tipo_evento=1 " + "                          and  stato=4)";
		if (statisticsType == 1)
			switch (helpdeskUserProfile) {
			case 1: // '\001'
				s = "select count(*),min(ev2.datetime-ev1.datetime),max(ev2.datetime-ev1.datetime),avg(ev2.datetime-ev1.datetime) from evento ev1,evento ev2,segnalazione where         ev1.segnalazione=ev2.segnalazione   and   ev1.segnalazione=segnalazione.id_segnalazione  and   segnalazione.originatore='" + helpdeskUserID + "' " + "  and   ev1.tipo_evento=0 " + "  and   ev2.tipo_evento=1 " + "  and   ev2.stato=4" + "  and   (ev1.datetime between to_date('" + startDate
						+ "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss')) " + "  and   (ev2.datetime between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss'))" + "  and   ev2.datetime = (select min(datetime) from evento " + "                        where  evento.segnalazione=ev2.segnalazione " + "                          and  tipo_evento=1 "
						+ "                          and  stato=4)";
				break;

			case 2: // '\002'
				s = "select count(*),min(ev2.datetime-ev1.datetime),max(ev2.datetime-ev1.datetime),avg(ev2.datetime-ev1.datetime) from evento ev1,evento ev2,segnalazione where         ev1.segnalazione=ev2.segnalazione   and   ev1.segnalazione=segnalazione.id_segnalazione  and   segnalazione.esperto='" + helpdeskUserID + "' " + "  and   ev1.tipo_evento=0 " + "  and   ev2.tipo_evento=1 " + "  and   ev2.stato=4" + "  and   (ev1.datetime between to_date('" + startDate
						+ "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss')) " + "  and   (ev2.datetime between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss'))" + "  and   ev2.datetime = (select min(datetime) from evento " + "                        where  evento.segnalazione=ev2.segnalazione " + "                          and  tipo_evento=1 "
						+ "                          and  stato=4)";
				break;

			case 3: // '\003'
				throw new StatisticsDAOException("Statistica non definita per l'amministratore", "Statistica non definita per l'amministratore");
			}
		try {
			log(this, s);
			ResultSet resultset = execQuery(s);
			if (resultset.next())
				overallstatisticsdto = new OverallStatisticsDTO(resultset.getInt(1), resultset.getDouble(2), resultset.getDouble(3), resultset.getDouble(4));
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + exception.getMessage());
			throw new StatisticsDAOException(exception.getMessage(), "Si \350 verificato un errore durante il calcolo delle statistiche dell'helpdesk di secondo livello. Contattare l'assistenza.");
		}
		return overallstatisticsdto;
	}

	public ResponseTimeStatisticsDTO getExternalResponseTimeStatistics() throws StatisticsDAOException {
		ResponseTimeStatisticsDTO responsetimestatisticsdto = null;
		String s = "Select max(close-open),min(close-open),avg(close-open) from ExternalProblems Where open>=to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and" + "      close<=to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss')";
		try {
			log(this, s);
			ResultSet resultset = execQuery(s);
			if (resultset.next())
				responsetimestatisticsdto = new ResponseTimeStatisticsDTO(resultset.getFloat(1), resultset.getFloat(2), resultset.getFloat(3));
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + exception.getMessage());
			throw new StatisticsDAOException(exception.getMessage(), "Si \350 verificato un errore durante il calcolo delle statistiche generali dei tempi di risposta dell'helpdesk di terzo livello. Contattare l'assistenza.");
		}
		return responsetimestatisticsdto;
	}

	public Collection getProblemCountByPriorityLevel() throws StatisticsDAOException {
		Collection collection = null;
		Object obj = null;
		String s = "Select Priority,count(*) from ExternalProblems Where open>=to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and" + "      close<=to_date('" + endDate + "','dd/mm/yyyy hh24:mi:ss') " + "Group by Priority";
		try {
			log(this, s);
			ProblemsDistributionByPriorityDTO problemsdistributionbyprioritydto;
			for (ResultSet resultset = execQuery(s); resultset.next(); collection.add(problemsdistributionbyprioritydto))
				problemsdistributionbyprioritydto = new ProblemsDistributionByPriorityDTO(resultset.getInt(1), resultset.getInt(2));

		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + exception.getMessage());
			throw new StatisticsDAOException(exception.getMessage(), "Si \350 verificato un errore durante il conteggio dei problemi per livello di priorit\340. Contattare l'assistenza.");
		}
		return collection;
	}

	public Integer getOpenedCount() throws StatisticsDAOException {
		String s = "Select count(*) from Segnalazione Where StartDate between to_date('" + startDate + "','dd/mm/yyyy hh24:mi:ss') and " + "to_date('" + endDate + "') and status=1";
		Integer integer = null;
		try {
			log(this, s);
			ResultSet resultset = execQuery(s);
			if (resultset.next())
				integer = new Integer(resultset.getInt(1));
		} catch (SQLException sqlexception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
			throw new StatisticsDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il conteggio dei problemi aperti. Contattare l'assistenza.");
		}
		return integer;
	}

	public Collection getProblemsDistributionByStatus() throws StatisticsDAOException {
		ArrayList arraylist = new ArrayList();
		Object obj = null;
		String s = "";
		switch (helpdeskUserProfile) {
		case 1: // '\001'
			s = " Select stato.ID_STATO id, stato.DESCRIZIONE descrizione,count(*) totale from segnalazione, stato  where segnalazione.STATO=stato.ID_STATO  and originatore='" + helpdeskUserID + "' group by stato.ID_STATO,stato.DESCRIZIONE" + " order by id";
			break;

		case 2: // '\002'
			if (statisticsType == 1)
				s = "Select stato.ID_STATO id,stato.DESCRIZIONE descrizione ,count(*) totale from segnalazione,stato where segnalazione.STATO=stato.ID_STATO and stato.ID_STATO=1  and segnalazione.CATEGORIA in (select  categoria from esperto_categoria where esperto='" + helpdeskUserID + "') " + " group by stato.ID_STATO,stato.DESCRIZIONE UNION " + " Select stato.ID_STATO id,stato.DESCRIZIONE descrizione ,count(*) totale" + " from segnalazione,stato"
						+ " where segnalazione.STATO=stato.ID_STATO " + " and esperto='" + helpdeskUserID + "' group by stato.ID_STATO,stato.DESCRIZIONE" + " order by id";
			if (statisticsType == 2)
				s = "Select stato.ID_STATO id,stato.DESCRIZIONE descrizione ,count(*) totale from Segnalazione , Esperto_Categoria, stato  where segnalazione.STATO=stato.ID_STATO  and Segnalazione.Categoria=Esperto_Categoria.Categoria  and Esperto_Categoria.Esperto='" + helpdeskUserID + "' " + " group by stato.ID_STATO,stato.DESCRIZIONE";
			break;

		case 3: // '\003'
			s = "Select stato.ID_STATO id, stato.DESCRIZIONE descrizione,count(*) totale from segnalazione,stato where segnalazione.STATO=stato.ID_STATO group by stato.ID_STATO,stato.DESCRIZIONE order by id";
			break;

		default:
			log(this, "Si \350 verificato un  problema durante il calcolo della distribuzione dei problemi per stato: profilo utente non definito o non valido.");
			throw new StatisticsDAOException("Si \350 verificato un problema durante il calcolo della distribuzione dei problemi per stato: profilo utente non definito o non valido.", "Impossibile produrre il report richiesto: profilo utente non definito o non valido.");
		}
		try {
			log(this, s);
			ProblemsDistributionByStatusDTO problemsdistributionbystatusdto;
			for (ResultSet resultset = execQuery(s); resultset.next(); arraylist.add(problemsdistributionbystatusdto))
				problemsdistributionbystatusdto = new ProblemsDistributionByStatusDTO(resultset.getString(2), resultset.getInt(3));

		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + exception.getMessage());
			throw new StatisticsDAOException(exception.getMessage(), "Si \350 verificato un errore in fase di accesso al data base durante il conteggio dei problemi per stato di lavorazione. Contattare l'assistenza.");
		}
		return arraylist;
	}

	public Collection getProblemDistributionByStatus(String s) throws StatisticsDAOException {
		ArrayList arraylist = new ArrayList();
		ProblemsDistributionByStatusDTO problemsdistributionbystatusdto = null;
		String s1 = "select id_stato,descrizione from stato order by id_stato";
		ResultSet resultset = null;
		try {
			resultset = execQuery(s1);
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s1 + "\". " + exception.getMessage());
			throw new StatisticsDAOException(exception.getMessage(), "Si \350 verificato un errore in fase di accesso al data base durante il recupero della lista degli stati. Contattare l'assistenza.");
		}
		switch (helpdeskUserProfile) {
		case 1: // '\001'
			String s2 = null;
			try {
				while (resultset.next()) {
					s2 = "select count(*) from segnalazione,evento ev where segnalazione.id_segnalazione=ev.segnalazione and  segnalazione.originatore='" + helpdeskUserID + "' and " + " ev.stato=" + resultset.getInt(1) + " and " + " ev.datetime<=to_date('" + s + "','dd/mm/yyyy hh24:mi:ss') and " + " not exists " + "    (select * from evento " + "     where evento.segnalazione=ev.segnalazione and " + "           evento.datetime>ev.datetime and "
							+ "           evento.datetime<=to_date('" + s + "','dd/mm/yyyy hh24:mi:ss') ";
					ResultSet resultset1 = execQuery(s2);
					if (resultset1.next()) {
						problemsdistributionbystatusdto.setStatus(resultset.getString(2));
						problemsdistributionbystatusdto.setCount(resultset1.getInt(1));
						arraylist.add(problemsdistributionbystatusdto);
					}
				}
			} catch (Exception exception1) {
				log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s2 + "\". " + exception1.getMessage());
				throw new StatisticsDAOException(exception1.getMessage(), "Si \350 verificato un errore in fase di accesso al data base durante il conteggio dei problemi in stato \"Open\" alla data " + s + ". Contattare l'assistenza.");
			}
			break;
		}
		return arraylist;
	}

	public Collection getProblemsDistributionByCategory() throws StatisticsDAOException {
		ArrayList arraylist = new ArrayList();
		String s = "";
		switch (helpdeskUserProfile) {
		default:
			break;

		case 1: // '\001'
			if (statisticsType == 1)
				s = "Select Segnalazione.Categoria,Categoria.nome,count(*) tot from Categoria, Segnalazione   where  Segnalazione.categoria=Categoria.id_categoria and  Segnalazione.originatore='" + helpdeskUserID + "' " + " group by Segnalazione.Categoria, Categoria.nome " + " order by tot desc";
			if (statisticsType == 2)
				throw new StatisticsDAOException("Tipo di statistica non accessibile all'utente normale", "Tipo di statistica non accessibile all'utente normale");
			break;

		case 2: // '\002'
			if (statisticsType == 1)
				s = "Select Segnalazione.Categoria,Categoria.nome,count(*) tot from Categoria , Segnalazione where  segnalazione.categoria=categoria.id_categoria  and segnalazione.esperto='" + helpdeskUserID + "' " + " group by Segnalazione.Categoria, Categoria.nome" + " order by tot desc";
			if (statisticsType == 2)
				s = "Select Segnalazione.Categoria,Categoria.nome,count(*) tot from Categoria , Segnalazione ,Esperto_Categoria   where  Categoria.id_Categoria=Esperto_Categoria.Categoria  and Segnalazione.categoria=categoria.id_categoria  and Esperto_Categoria.Esperto='" + helpdeskUserID + "' " + " group by Segnalazione.Categoria, Categoria.nome" + " order by tot desc";
			break;

		case 3: // '\003'
			if (statisticsType == 1)
				throw new StatisticsDAOException("Tipo di statistica non definita per l'utente amministratore", "Tipo di statistica non definita per l'utente amministratore");
			if (statisticsType == 2)
				s = "Select Segnalazione.Categoria,Categoria.nome,count(*) tot from Categoria , Segnalazione  where  Segnalazione.categoria=categoria.id_categoria  group by Segnalazione.Categoria, Categoria.nome order by tot desc";
			break;
		}
		try {
			log(this, s);
			ProblemsDistributionByCategoryDTO problemsdistributionbycategorydto;
			for (ResultSet resultset = execQuery(s); resultset.next(); arraylist.add(problemsdistributionbycategorydto))
				problemsdistributionbycategorydto = new ProblemsDistributionByCategoryDTO(resultset.getString(2), resultset.getInt(3));

		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + exception.getMessage());
			throw new StatisticsDAOException(exception.getMessage(), "Si \350 verificato un errore in fase di accesso al data base durante il conteggio dei problemi per categoria. Contattare l'assistenza.");
		}
		return arraylist;
	}

	private String startDate;
	private String endDate;
	private Integer samplingInterval;
	private String helpdeskUserID;
	private int helpdeskUserProfile;
	private UserValueObject helpdeskUser;
	private int statisticsType;
	private static final int PERSONAL = 1;
	private static final int GENERAL = 2;
}
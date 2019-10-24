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

 

package it.cnr.helpdesk.ProblemManagement.dao;

import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemDAOException;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.valueobjects.*;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.dao.ProblemDAO;
import it.cnr.helpdesk.exceptions.HelpDeskDAOException;
import it.cnr.helpdesk.util.PageByPageIterator;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class OracleProblemDAO extends HelpDeskDAO implements ProblemDAO {

	private Connection con;
    private long idSegnalazione;
    private String esperto;
    private String originatoreEvento;
    private String dataEvento;
    
    public OracleProblemDAO()
    {
        con = null;
        esperto = "";
        originatoreEvento = "";
        dataEvento = "";
    }

    public void setIdSegnalazione(long l)
    {
        idSegnalazione = l;
    }

    public long getIdSegnalazione()
    {
        return idSegnalazione;
    }

    public void setDataEvento(String s)
    {
        dataEvento = s;
    }

    public String getDataEvento()
    {
        return dataEvento;
    }

    public void setEsperto(String s)
    {
        esperto = s;
    }

    public String getEsperto()
    {
        return esperto;
    }

    public void setOriginatoreEvento(String s)
    {
        originatoreEvento = s;
    }

    public String getOriginatoreEvento()
    {
        return originatoreEvento;
    }
    
    public void executeDelete(Collection c)
    {
    }

    public ProblemValueObject getProblemDetail() throws ProblemDAOException 
    {
        ProblemValueObject problemvalueobject = null;
        //String s = "select segnalazione.ID_SEGNALAZIONE,segnalazione.TITOLO,segnalazione.DESCRIZIONE,segnalazione.CATEGORIA,categoria.NOME, segnalazione.STATO,stato.DESCRIZIONE,segnalazione.ORIGINATORE,NVL(ori.NOME,'-'),NVL(ori.COGNOME,'-'),        segnalazione.ESPERTO,NVL(esp.NOME,'-'),NVL(esp.COGNOME,'-'),segnalazione.FLAGFAQ, segnalazione.PRIORITA, priorita.DESCRIZIONE from   segnalazione,categoria,stato, utente ori,utente esp, priorita where  segnalazione.CATEGORIA=categoria.ID_CATEGORIA and        segnalazione.STATO=stato.ID_STATO and \t     segnalazione.ORIGINATORE=ori.USERID and \t     esp.USERID (+)= segnalazione.ESPERTO and   segnalazione.PRIORITA=priorita.LIVELLO   and  segnalazione.ID_SEGNALAZIONE=" + idSegnalazione;
        String s = "select segnalazione.ID_SEGNALAZIONE,segnalazione.TITOLO,segnalazione.DESCRIZIONE,segnalazione.CATEGORIA,categoria.NOME, segnalazione.STATO,stato.DESCRIZIONE,segnalazione.ORIGINATORE,NVL(ori.NOME,'-'),NVL(ori.COGNOME,'-'),        segnalazione.ESPERTO,NVL(esp.NOME,'-'),NVL(esp.COGNOME,'-'),segnalazione.FLAGFAQ, segnalazione.PRIORITA, priorita.DESCRIZIONE, segnalazione.VALIDATORE,NVL(val.NOME,'-'),NVL(val.COGNOME,'-') " +
        			"from   segnalazione,categoria,stato, utente ori,utente esp, utente val, priorita " +
        			"where  	segnalazione.CATEGORIA=categoria.ID_CATEGORIA " +
        			"and        segnalazione.STATO=stato.ID_STATO " +
        			"and      ori.USERID = segnalazione.ORIGINATORE " +
        			"and      esp.USERID (+)= segnalazione.ESPERTO " +
        			"and      val.USERID (+)= segnalazione.VALIDATORE " +
        			"and   segnalazione.PRIORITA=priorita.LIVELLO   " +
        			"and  segnalazione.ID_SEGNALAZIONE=" + idSegnalazione;
        try
        {
            log(this, s);
            ResultSet resultset = execQuery(s);
            if(resultset.next()){
            	//valido prima dell'introduzione VALIDATORE
                //problemvalueobject = new ProblemValueObject(resultset.getLong(1), resultset.getString(2), resultset.getString(3), resultset.getInt(4), resultset.getString(5), resultset.getInt(6), resultset.getString(7), resultset.getString(8), resultset.getString(9) + " " + resultset.getString(10), resultset.getString(11), resultset.getString(12) + " " + resultset.getString(13), resultset.getInt(14), resultset.getInt(15), resultset.getString(16));
            	problemvalueobject = new ProblemValueObject(resultset.getLong(1), resultset.getString(2), resultset.getString(3), resultset.getInt(4), resultset.getString(5), resultset.getInt(6), resultset.getString(7), resultset.getString(8), resultset.getString(9) + " " + resultset.getString(10), resultset.getString(11), resultset.getString(12) + " " + resultset.getString(13), resultset.getInt(14), resultset.getInt(15), resultset.getString(16),resultset.getString(17),resultset.getString(18)+" "+resultset.getString(19));
            }	
        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero delle informazioni di dettaglio relative alla segnalazione.");
        }
        return problemvalueobject;
    }

    /**
	 * Recupero delle segnalazioni con stato appartenenete ad un deternminato dominio 
	 * comunque non chiuse, di una determinata la categoria ed un determinato utente, VALIDATORE o EXPERTO 
	 *  
	 *@param ProblemCountValueObject problemcountvalueobject 
	 *		contiene i dati per i criteri di ricerca idUtente, Profilo, stai segnalazione di interesse  
	 *@param PageByPageIterator pagebypageiterator
	 * 
	 *@throws ProblemJBException
	 *@return PageByPageIterator. 
	 * 
	 * 
	 */
    public PageByPageIterator getExpertProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject)  throws ProblemDAOException {
    	String s = null;
    	String ordine = problemvalueobject.getOrdinamento();
    	if(ordine==null)	ordine = "";
    	if(problemvalueobject.getProfiloUtente()==4){// UTENTE VALIDATORE
    		// estrazione codStatoSegnalazione per cercare le segnalazioni chiuse o quelle pendenti 
    		// vale solo per il validatore perchè per l'expert dalla Action client non viene settato 
    		// in problemvalueobject il valore discriminante 4 o 7 che per l'eserto sarebbe 4 o 2 oppure 4 e 3. 
    		int codStatoSegnalazione = 0;
    		if(problemvalueobject.getSearchingCollection().size() > 0){ 
    			ArrayList searchingcollection = (ArrayList)problemvalueobject.getSearchingCollection();
    			ArrayList searchingSet = (ArrayList)searchingcollection.get(0);
    			if(searchingSet.size() > 0)
    				codStatoSegnalazione = ((Integer)searchingSet.get(0)).intValue();
    		}
    		if(codStatoSegnalazione == 4){//recupero segnalazioni chiuse solo del VALIDATORE
    			s = "select segnalazione.ID_SEGNALAZIONE," +
				"segnalazione.TITOLO," +
				"stato.DESCRIZIONE," +
				"NVL(originatore.NOME,'-')," +
				"NVL(originatore.COGNOME,'-')," +
				"originatore.USERID," +
				"NVL(esperto.NOME,'-')," +
				"NVL(esperto.COGNOME,'-')," +
				"TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS')," +
				"segnalazione.STATO," +
				"segnalazione.ESPERTO," +
				"categoria.NOME " + 
				"from segnalazione,stato,utente originatore,evento, utente esperto, categoria " + 
				"where segnalazione.STATO=stato.ID_STATO " +
				"and segnalazione.originatore=originatore.USERID " +
				"and segnalazione.esperto=esperto.USERID " +
				"and segnalazione.ID_SEGNALAZIONE=evento.SEGNALAZIONE " +
				"and evento.TIPO_EVENTO in (0,7) " +
				"and segnalazione.CATEGORIA=categoria.ID_CATEGORIA " +
				"and segnalazione.CATEGORIA=" + problemvalueobject.getCategoria() + " " + 
				"and segnalazione.VALIDATORE='" + problemvalueobject.getEsperto() + "' " + problemvalueobject.getSearchingCollection().toSQLString();
    			
    		} else if(codStatoSegnalazione == 7){ // recupero segnalazioni non chiuse ossia in stato 6 e UNION quelle in 7 solo del VALIDATORE 
    			s = "select segnalazione.ID_SEGNALAZIONE," +
				"segnalazione.TITOLO," +
				"stato.DESCRIZIONE," +
				"NVL(utente.NOME,'-')," +
				"NVL(utente.COGNOME,'-')," +
				"utente.USERID," +
				"' - '," +
				"' - '," +
				"TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS')," +
				"segnalazione.STATO," +
				"' - '," +
				"categoria.NOME " +
				"from segnalazione,stato,utente,evento, categoria " +
				"where segnalazione.STATO=stato.ID_STATO " +
				"and segnalazione.originatore=utente.USERID " +
				"and segnalazione.STATO=6 " +
				"and segnalazione.ID_SEGNALAZIONE=evento.SEGNALAZIONE " +
				"and evento.TIPO_EVENTO in (0,7) " +
				"and segnalazione.CATEGORIA=categoria.ID_CATEGORIA " +
				"and segnalazione.categoria=" + problemvalueobject.getCategoria() +
				" UNION " + 
				"select segnalazione.ID_SEGNALAZIONE," +
				"segnalazione.TITOLO," +
				"stato.DESCRIZIONE," +
				"NVL(originatore.NOME,'-')," +
				"NVL(originatore.COGNOME,'-')," +
				"originatore.USERID," +
				"NVL(esperto.NOME,'-')," +
				"NVL(esperto.COGNOME,'-')," +
				"TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS')," +
				"segnalazione.STATO," +
				"segnalazione.ESPERTO," +
				"categoria.NOME " + 
				"from segnalazione,stato,utente originatore,evento, utente esperto, categoria " + 
				"where segnalazione.STATO=stato.ID_STATO " +
				"and segnalazione.originatore=originatore.USERID " +
				"and segnalazione.esperto=esperto.USERID " +
				"and segnalazione.ID_SEGNALAZIONE=evento.SEGNALAZIONE " +
				"and evento.TIPO_EVENTO in (0,7) " +
				"and segnalazione.CATEGORIA=categoria.ID_CATEGORIA " +
				"and segnalazione.CATEGORIA=" + problemvalueobject.getCategoria() + " " + 
				"and segnalazione.VALIDATORE='" + problemvalueobject.getEsperto() + "' " + problemvalueobject.getSearchingCollection().toSQLString();
    		}
    		s = s + " order by 1 "+ordine;
    		ArrayList arraylist = null;
    		int i = 0;
    		try {
    			arraylist = new ArrayList();
    			log(this, s);
    			for(ResultSet resultset = execQuery(s); resultset.next();) {
    				i = resultset.getRow();
    				if(i >= pagebypageiterator.getStart() && i <= (pagebypageiterator.getStart() + pagebypageiterator.getSize()) - 1) {
    					ProblemListItemValueObject problemlistitemvalueobject = new ProblemListItemValueObject(resultset.getLong(1), resultset.getString(2), resultset.getString(3), resultset.getString(4) + " " + resultset.getString(5), resultset.getString(6), resultset.getString(7) + " " + resultset.getString(8), resultset.getString(9), resultset.getInt(10), resultset.getString(11), resultset.getString(12));
    					if(problemlistitemvalueobject.getOriginatoreLogin().equalsIgnoreCase(User.MAIL_USER)){
							String s1 = "SELECT nome, cognome FROM temp_segnalazione WHERE id_segnalazione = "+problemlistitemvalueobject.getIdSegnalazione();
							ResultSet resultset1 = execQuery(s1);
							if(resultset1.next())
								problemlistitemvalueobject.setOriginatoreEsterno(resultset1.getString("nome")+" "+resultset1.getString("cognome"));
						}
    					arraylist.add(problemlistitemvalueobject);
    				}
    			}
    			pagebypageiterator.setTotalCount(i);
    			pagebypageiterator.setCollection(arraylist);
    		} catch(SQLException sqlexception) {
    			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
    			sqlexception.printStackTrace();
    			throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recuper delle liste dei problemi.");
    		}
    	} else { // ALTRI PROFILI AL MOMEMTO experto 
    		if(problemvalueobject.isPerCategory()) {
    			if(problemvalueobject.isOwnerShip())
    				s = "select segnalazione.ID_SEGNALAZIONE," +
    				"segnalazione.TITOLO," +
    				"stato.DESCRIZIONE," +
    				"NVL(utente.NOME,'-')," +
    				"NVL(utente.COGNOME,'-')," +
    				"utente.USERID," +
    				"' - '," +
    				"' - '," +
    				"TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS')," +
    				"segnalazione.STATO," +
    				"' - '," +
    				"categoria.NOME, " +
    				"strutture.evidenza " +
    				"from segnalazione,stato,utente,evento, categoria, strutture " +
    				"where utente.struttura = strutture.codice_struttura(+) and " +
    				"segnalazione.STATO=stato.ID_STATO and " +
    				"segnalazione.originatore=utente.USERID and " +
    				"segnalazione.STATO=1 and " +
    				"segnalazione.ID_SEGNALAZIONE=evento.SEGNALAZIONE and " +
    				"evento.TIPO_EVENTO in (0,7) and " +
    				"segnalazione.CATEGORIA=categoria.ID_CATEGORIA and " +
    				"segnalazione.categoria=" + problemvalueobject.getCategoria() + problemvalueobject.getSearchingCollection().toSQLString() +
					" UNION " +
					"select segnalazione.ID_SEGNALAZIONE," +
					"segnalazione.TITOLO," +
					"stato.DESCRIZIONE," +
					"NVL(originatore.NOME,'-')," +
					"NVL(originatore.COGNOME,'-')," +
					"originatore.USERID," +
					"NVL(esperto.NOME,'-')," +
					"NVL(esperto.COGNOME,'-')," +
					"TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS')," +
					"segnalazione.STATO," +
					"segnalazione.ESPERTO," +
					"categoria.NOME, " +
					"strutture.evidenza " +
					"from segnalazione,stato,utente originatore,evento, utente esperto, categoria, strutture " +
					"where originatore.struttura = strutture.codice_struttura(+) and " +
					"segnalazione.STATO=stato.ID_STATO and " +
					"segnalazione.originatore=originatore.USERID and " +
					"segnalazione.esperto=esperto.USERID and " +
					"segnalazione.STATO!=1 and " +
					"segnalazione.ID_SEGNALAZIONE=evento.SEGNALAZIONE and " +
					"evento.TIPO_EVENTO in (0,7) and " +
					"segnalazione.CATEGORIA=categoria.ID_CATEGORIA and " +
					"segnalazione.CATEGORIA=" + problemvalueobject.getCategoria() + " and " +
					"segnalazione.ESPERTO='" + problemvalueobject.getEsperto() + "' " + problemvalueobject.getSearchingCollection().toSQLString();
    			else
    				s = "select segnalazione.ID_SEGNALAZIONE," +
					"segnalazione.TITOLO," +
					"stato.DESCRIZIONE," +
					"NVL(originatore.NOME,'-')," +
					"NVL(originatore.COGNOME,'-')," +
					"originatore.USERID," +
					"NVL(esperto.NOME,'-')," +
					"NVL(esperto.COGNOME,'-')," +
					"TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS')," +
					"segnalazione.STATO," +
					"segnalazione.ESPERTO," +
					"categoria.NOME, " +
					"strutture.evidenza " +
					"from segnalazione,stato,utente originatore,evento, utente esperto, categoria, strutture " +
					"where originatore.struttura = strutture.codice_struttura(+) and " +
					"segnalazione.STATO=stato.ID_STATO and " +
					"segnalazione.originatore=originatore.USERID and " +
					"segnalazione.esperto=esperto.USERID and " +
					"segnalazione.STATO!=1 and " +
					"segnalazione.ID_SEGNALAZIONE=evento.SEGNALAZIONE and " +
					"evento.TIPO_EVENTO in (0,7) and " +
					"segnalazione.CATEGORIA=categoria.ID_CATEGORIA and " +
					"segnalazione.CATEGORIA=" + problemvalueobject.getCategoria() + " " + problemvalueobject.getSearchingCollection().toSQLString();
    		} else {
    			s = "select segnalazione.ID_SEGNALAZIONE," +
    			"segnalazione.TITOLO," +
    			"stato.DESCRIZIONE," +
    			"NVL(originatore.NOME,'-')," +
    			"NVL(originatore.COGNOME,'-')," +
    			"originatore.USERID," +
    			"NVL(esperto.NOME,'-')," +
    			"NVL(esperto.COGNOME,'-')," +
    			"TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS')," +
    			"segnalazione.STATO," +
    			"segnalazione.ESPERTO," +
    			"categoria.NOME, " +
    			"strutture.evidenza " +
    			"from segnalazione,stato,utente originatore,evento, utente esperto, categoria, strutture " +
    			"where originatore.struttura = strutture.codice_struttura(+) and " +
    			"segnalazione.STATO=stato.ID_STATO and " +
    			"segnalazione.originatore=originatore.USERID and " +
    			"esperto.USERID(+)=segnalazione.esperto and " +
    			"segnalazione.ID_SEGNALAZIONE=evento.SEGNALAZIONE and " +
    			"evento.TIPO_EVENTO in (0,7) and " +
    			"segnalazione.CATEGORIA=categoria.ID_CATEGORIA " + problemvalueobject.getSearchingCollection().toSQLString() + " and " +
    			"segnalazione.CATEGORIA in (select CATEGORIA from esperto_categoria where esperto_categoria.ESPERTO='" + problemvalueobject.getActualUser() + "')";
    		}
    		s = s + " order by 1 "+ordine;
    		ArrayList arraylist = null;
    		int i = 0;
    		try {
    			arraylist = new ArrayList();
    			log(this, s);
    			for(ResultSet resultset = execQuery(s); resultset.next();) {
    				i = resultset.getRow();
    				if(i >= pagebypageiterator.getStart() && i <= (pagebypageiterator.getStart() + pagebypageiterator.getSize()) - 1) {
    					ProblemListItemValueObject problemlistitemvalueobject = new ProblemListItemValueObject(resultset.getLong(1), resultset.getString(2), resultset.getString(3), resultset.getString(4) + " " + resultset.getString(5), resultset.getString(6), resultset.getString(7) + " " + resultset.getString(8), resultset.getString(9), resultset.getInt(10), resultset.getString(11), resultset.getString(12),resultset.getString(13));
    					if(problemlistitemvalueobject.getOriginatoreLogin().equalsIgnoreCase(User.MAIL_USER)){
							String s1 = "SELECT nome, cognome FROM temp_segnalazione WHERE id_segnalazione = "+problemlistitemvalueobject.getIdSegnalazione();
							ResultSet resultset1 = execQuery(s1);
							if(resultset1.next())
								problemlistitemvalueobject.setOriginatoreEsterno(resultset1.getString("nome")+" "+resultset1.getString("cognome"));
						}
    					arraylist.add(problemlistitemvalueobject);
    				}
    			}
    			pagebypageiterator.setTotalCount(i);
    			pagebypageiterator.setCollection(arraylist);
    		} catch(SQLException sqlexception) {
    			log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
    			sqlexception.printStackTrace();
    			throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recuper delle liste dei problemi.");
    		}
    	}//end else
    	return pagebypageiterator;
    }

    /**
	 * Recupero delle segnalazioni nello stato 'Team Working'
	 * 
	 *@param ProblemCountValueObject problemcountvalueobject  contiene i dati per i criteri di ricerca
	 *@param PageByPageIterator pagebypageiterator
	 * 
	 *@throws ProblemJBException
	 *@return PageByPageIterator. 
	 * 
	 */
    public PageByPageIterator getTeamProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject)  throws ProblemDAOException {
    	String s = null;
    	s = "select S.ID_SEGNALAZIONE,  S.TITOLO,  ST.DESCRIZIONE, NVL(UO.NOME,'-'),  NVL(UO.COGNOME,'-'),  " +
		"UO.USERID, NVL(ES.NOME,'-'),  NVL(ES.COGNOME,'-'), TO_CHAR(E.DATETIME,'DD-MM-YYYY HH24:MI:SS'), " +
		"S.STATO, S.ESPERTO, C.NOME " +
		"from segnalazione S,stato ST,utente UO,evento E, utente ES, categoria C " +
		"where 	S.STATO=ST.ID_STATO " +
		"and S.originatore=UO.USERID " +
		"and S.ESPERTO=ES.USERID " +
		"and S.ID_SEGNALAZIONE=E.SEGNALAZIONE " +
		"and E.TIPO_EVENTO in (0,7) " +
		"and S.CATEGORIA=C.ID_CATEGORIA " +
		"and S.STATO = " + problemvalueobject.getStato() +
		" order by 1";
    	/* and S.CATEGORIA= */
    	
    	ArrayList arraylist = null;
    	int i = 0;
    	try {
    		arraylist = new ArrayList();
    		log(this, s);
    		for(ResultSet resultset = execQuery(s); resultset.next();)
    		{
    			i = resultset.getRow();
    			if(i >= pagebypageiterator.getStart() && i <= (pagebypageiterator.getStart() + pagebypageiterator.getSize()) - 1)
    			{
    				ProblemListItemValueObject problemlistitemvalueobject = new ProblemListItemValueObject(resultset.getLong(1), resultset.getString(2), resultset.getString(3), resultset.getString(4) + " " + resultset.getString(5), resultset.getString(6), resultset.getString(7) + " " + resultset.getString(8), resultset.getString(9), resultset.getInt(10), resultset.getString(11), resultset.getString(12));
    				if(problemlistitemvalueobject.getOriginatoreLogin().equalsIgnoreCase(User.MAIL_USER)){
						String s1 = "SELECT nome, cognome FROM temp_segnalazione WHERE id_segnalazione = "+problemlistitemvalueobject.getIdSegnalazione();
						ResultSet resultset1 = execQuery(s1);
						if(resultset1.next())
							problemlistitemvalueobject.setOriginatoreEsterno(resultset1.getString("nome")+" "+resultset1.getString("cognome"));
					}
    				arraylist.add(problemlistitemvalueobject);
    			}
    		}
    		pagebypageiterator.setTotalCount(i);
    		pagebypageiterator.setCollection(arraylist);
    		
    	} catch(SQLException sqlexception) {
    		log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
    		sqlexception.printStackTrace();
    		throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero delle liste dei problemi.");
    	}
    	return pagebypageiterator;
    }

    
    public PageByPageIterator getUserProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject)
        throws ProblemDAOException
    {
        String s = "select segnalazione.ID_SEGNALAZIONE,segnalazione.TITOLO,stato.DESCRIZIONE,NULL,NULL,NULL,NVL(esperto.NOME,'-'),NVL(esperto.COGNOME,'-'),TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS'),segnalazione.STATO,segnalazione.ESPERTO,categoria.NOME from segnalazione,stato,evento, utente esperto,categoria where segnalazione.STATO=stato.ID_STATO and       segnalazione.originatore='" + problemvalueobject.getOriginatore() + "' and " + "\t   esperto.USERID(+)=segnalazione.esperto and " + "      segnalazione.ID_SEGNALAZIONE=evento.SEGNALAZIONE and " + "      segnalazione.CATEGORIA=categoria.ID_CATEGORIA and " + "      evento.TIPO_EVENTO in (0,7) " + problemvalueobject.getSearchingCollection().toSQLString();
        s = s + " order by 1";
        ArrayList arraylist = null;
        int i = 0;
        try
        {
            arraylist = new ArrayList();
            log(this, s);
            for(ResultSet resultset = execQuery(s); resultset.next();)
            {
                i = resultset.getRow();
                if(i >= pagebypageiterator.getStart() && i <= (pagebypageiterator.getStart() + pagebypageiterator.getSize()) - 1)
                {
                    ProblemListItemValueObject problemlistitemvalueobject = new ProblemListItemValueObject(resultset.getLong(1), resultset.getString(2), resultset.getString(3), " ", "", resultset.getString(7) + " " + resultset.getString(8), resultset.getString(9), resultset.getInt(10), resultset.getString(11), resultset.getString(12));
                    arraylist.add(problemlistitemvalueobject);
                }
            }

        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il recupero dellalista delle segnalazioni.");
        }
        pagebypageiterator.setTotalCount(i);
        pagebypageiterator.setCollection(arraylist);
        return pagebypageiterator;
    }

    /**
	 * Recupero del numero di segnalazioni pendenti per ogni categoria assegnata 
	 * ad un determinato validatore. Le segnalazioni sono quelle assegnate o meno 
	 * al validatore.     
	 *  
	 *@param ProblemCountValueObject problemcountvalueobject 
	 * 
	 *@throws ProblemDAOException
	 *@return Collection collezione di ProblemCountValueObject. 
	 * 
	 * 
	 */
    public Collection getProblemCountPerCategory(ProblemCountValueObject problemcountvalueobject) throws ProblemDAOException {
        ArrayList arraylist = new ArrayList();
        String s = null;
        
        if(problemcountvalueobject.getProfiloUtente()==4) { // se utente validatore
        	
        	int codStatoSegnalazione = 0;
        	
        	if(problemcountvalueobject.getSearchingCollection().size() > 0){ 
        		
        		ArrayList searchingcollection = (ArrayList)problemcountvalueobject.getSearchingCollection();
        		ArrayList searchingSet = (ArrayList)searchingcollection.get(0);
        		if(searchingSet.size() > 0)
        			codStatoSegnalazione = ((Integer)searchingSet.get(0)).intValue();
        	}	
        	
        	
        	if(codStatoSegnalazione == 7){ // query segnalazioni pendenti 6 e 7
        		s = "Select Segnalazione.Categoria,Categoria.nome,count(*) " +  
        			"from Categoria, Segnalazione, Esperto_Categoria " +   
        			"where  Segnalazione.Categoria=Esperto_Categoria.Categoria " +
        				"and Categoria.id_Categoria=Esperto_Categoria.Categoria " +
        				"and ( (Segnalazione.VALIDATORE=Esperto_Categoria.ESPERTO " + problemcountvalueobject.getSearchingCollection().toSQLString() + ") or (segnalazione.STATO in (6)) ) "+ 
        				"and Esperto_Categoria.Esperto='"+problemcountvalueobject.getExpertLogin()+"'  " + 
        				"group by Segnalazione.Categoria, Categoria.nome";
        	}
        	else if(codStatoSegnalazione == 4){// query segnalazioni chiuse 4
        		s = "Select Segnalazione.Categoria,Categoria.nome,count(*) " +  
        		"from Categoria, Segnalazione, Esperto_Categoria " +   
        		"where  Segnalazione.Categoria=Esperto_Categoria.Categoria " +
        			"and Categoria.id_Categoria=Esperto_Categoria.Categoria " +
        			"and Segnalazione.VALIDATORE=Esperto_Categoria.ESPERTO " + problemcountvalueobject.getSearchingCollection().toSQLString() +
        			"and Esperto_Categoria.Esperto='"+problemcountvalueobject.getExpertLogin()+"'  " + 
        			"group by Segnalazione.Categoria, Categoria.nome";
        	}
    

        }
        else { // altri tipi di utente. La QUERY che segue è relativa ad UTENTE ESPERTO  
    
        	if(problemcountvalueobject.isOwnerShip())
        		s = "Select S.Categoria,C.nome,count(*) " +
        			"from Categoria C, Segnalazione S,Esperto_Categoria  E " +
        			"where   S.Categoria=E.Categoria and " +	
        					"(S.ESPERTO=E.ESPERTO or S.Esperto is null )	   and " +
							"C.id_Categoria=S.Categoria " + 
							problemcountvalueobject.getSearchingCollection().toSQLString().replaceAll("segnalazione.","S.") + " and " + 
					" 		E.Esperto='" + problemcountvalueobject.getExpertLogin() + "' " + 
					"group by S.Categoria, C.nome";
        	else
        		s = "Select Segnalazione.Categoria,Categoria.nome,count(*)  from Categoria , Segnalazione ,Esperto_Categoria   where  Segnalazione.Categoria=Esperto_Categoria.Categoria " + problemcountvalueobject.getSearchingCollection().toSQLString() + " and Esperto_Categoria.Esperto='" + problemcountvalueobject.getExpertLogin() + "' " + " and Categoria.id_Categoria=Esperto_Categoria.Categoria " + " group by Segnalazione.Categoria, Categoria.nome";
        	
        }	

        try {
            log(this, s);
            ProblemCountValueObject problemcountvalueobject1;
            for(ResultSet resultset = execQuery(s); resultset.next(); arraylist.add(problemcountvalueobject1))
                problemcountvalueobject1 = new ProblemCountValueObject(resultset.getInt(1), resultset.getString(2), resultset.getInt(3));

        }
        catch(SQLException sqlexception)
        {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il conteggio dei problemi per categoria.");
        }
        return arraylist;
    }

    /**
     * Recupera il conteggio delle segnalazioni in stato Team Working raggruppate sulle categorie di afferenza dell'esperto corrente 
     * @param problemcountvalueobject
     * @return
     * @throws ProblemDAOException
     */
    public Collection getTeamProblemCountPerCategory(ProblemCountValueObject problemcountvalueobject) throws ProblemDAOException {
    	ArrayList arraylist = new ArrayList();
    	
    	String s = "Select S.Categoria,C.nome,count(*) " +
		"from Categoria C, Segnalazione S,Esperto_Categoria  E " +
		"where   S.Categoria=E.Categoria and " +
		"C.id_Categoria=S.Categoria and " + 
		"S.Stato = 9 and " + 
		"E.Esperto='" + problemcountvalueobject.getExpertLogin() + "' " + 
		"group by S.Categoria, C.nome";
    	try {
    		log(this, s);
    		ProblemCountValueObject problemcountvalueobject1;
    		for(ResultSet resultset = execQuery(s); resultset.next(); arraylist.add(problemcountvalueobject1))
    			problemcountvalueobject1 = new ProblemCountValueObject(resultset.getInt(1), resultset.getString(2), resultset.getInt(3));
    		
    	} catch(SQLException sqlexception) {
    		log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
    		sqlexception.printStackTrace();
    		throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il conteggio dei problemi per categoria.");
    	}
    	return arraylist;
    }

    
    /**
	 * Recupero del numero di segnalazioni pendenti per ogni categoria assegnata 
	 * ad un determinato validatore. Le segnalazioni sono quelle assegnate o meno 
	 * al validatore.     
	 *  
	 *@param ProblemCountValueObject problemcountvalueobject 
	 * 
	 *@throws ProblemDAOException
	 *@return Collection collezione di ProblemCountValueObject. 
	 * 
	 * 
	 */
    public Collection getNumProblemPerCategoryValidator(ProblemCountValueObject problemcountvalueobject) throws ProblemDAOException {
    	ArrayList arraylist = new ArrayList();
    	String s = null;
    	if(problemcountvalueobject.isOwnerShip())
    		//s = "Select Segnalazione.Categoria,Categoria.nome,count(*)  from Categoria, Segnalazione, Esperto_Categoria   where  Segnalazione.Categoria=Esperto_Categoria.Categoria and  ((Segnalazione.ESPERTO=Esperto_Categoria.ESPERTO " + problemcountvalueobject.getSearchingCollection().toSQLString() + ") or " + " (segnalazione.STATO in (1) " + problemcountvalueobject.getSearchingCollection().toSQLString() + " )) " + " and Categoria.id_Categoria=Esperto_Categoria.Categoria and " + " Esperto_Categoria.Esperto='" + problemcountvalueobject.getExpertLogin() + "' " + " group by Segnalazione.Categoria, Categoria.nome";
    		s = "Select Segnalazione.Categoria,Categoria.nome,count(*) " +  
    			"from Categoria, Segnalazione, Esperto_Categoria " +   
    			"where  Segnalazione.Categoria=Esperto_Categoria.Categoria " +
    				"and Categoria.id_Categoria=Esperto_Categoria.Categoria " +
    				"and ( (Segnalazione.VALIDATORE=Esperto_Categoria.ESPERTO and segnalazione.STATO in (7) ) or (segnalazione.STATO in (6)) ) " +
    				"and Esperto_Categoria.Esperto='"+problemcountvalueobject.getExpertLogin()+"'  " + 
    				"group by Segnalazione.Categoria, Categoria.nome";
    	else
    		//s = "Select Segnalazione.Categoria,Categoria.nome,count(*)  from Categoria , Segnalazione ,Esperto_Categoria   where  Segnalazione.Categoria=Esperto_Categoria.Categoria " + problemcountvalueobject.getSearchingCollection().toSQLString() + " and Esperto_Categoria.Esperto='" + problemcountvalueobject.getExpertLogin() + "' " + " and Categoria.id_Categoria=Esperto_Categoria.Categoria " + " group by Segnalazione.Categoria, Categoria.nome";
    		s = "Select Segnalazione.Categoria,Categoria.nome,count(*) " +  
			"from Categoria, Segnalazione, Esperto_Categoria " +   
			"where  Segnalazione.Categoria=Esperto_Categoria.Categoria " +
				"and Categoria.id_Categoria=Esperto_Categoria.Categoria " +
				"and segnalazione.STATO in (6,7) " +
				"and Esperto_Categoria.Esperto='"+problemcountvalueobject.getExpertLogin()+"'  " + 
				"group by Segnalazione.Categoria, Categoria.nome";
    	try {
    		log(this, s);
    		ProblemCountValueObject problemcountvalueobject1;
    		for(ResultSet resultset = execQuery(s); resultset.next(); arraylist.add(problemcountvalueobject1))
    			problemcountvalueobject1 = new ProblemCountValueObject(resultset.getInt(1), resultset.getString(2), resultset.getInt(3));

    	}
    	catch(SQLException sqlexception) {
    		log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
    		sqlexception.printStackTrace();
    		throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il conteggio dei problemi per categoria per il validatore.");
    	}
    	return arraylist;
    	
    }//end method
    
    public void reAssignProblemToExpert(EventValueObject evo) throws ProblemDAOException {
    	int i = 0;
    	int j = 0;
    	String s1 = "select categoria, stato from segnalazione where ID_SEGNALAZIONE=" + evo.getIdSegnalazione();
    	try
		{
    		log(this, s1);
    		ResultSet resultset = execQuery(s1);
    		if(resultset.next()){
    			i = resultset.getInt(1);
	    		j = resultset.getInt(2);
    		}
    		s1 = "update segnalazione set ESPERTO='" + evo.getExpertLogin() + "' where ID_SEGNALAZIONE=" + evo.getIdSegnalazione();
    		log(this, s1);
    		execUpdQuery(s1);
    		s1 = "insert into evento(SEGNALAZIONE, DATETIME, TIPO_EVENTO, STATO, CATEGORIA, NOTA, ORIGINATORE) values(" + evo.getIdSegnalazione() + ",sysdate,4," + j + "," + i + ",'" + evo.getNote2SQL() + "','" + evo.getOriginatoreEvento() + "')";
    		log(this, s1);
    		execUpdQuery(s1);
		}
    	catch(SQLException sqlexception)
		{
    		log(this, "Si \350 verificato un errore nell'esecuzione del sequente coduce SQL \"" + s1 + "\". " + sqlexception.getMessage());
    		sqlexception.printStackTrace();
    		throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante la riassegnazione del problema all'esperto. Contattare l'assistenza.");
		}
	}
    
    public void addNote(String s)
        throws ProblemDAOException
    {
        int i = 0;
        int j = 0;
        String s1 = "select categoria,stato from segnalazione where ID_SEGNALAZIONE=" + idSegnalazione;
        try
        {
            log(this, s1);
            ResultSet resultset = execQuery(s1);
            if(resultset.next())
            {
                i = resultset.getInt(1);
                j = resultset.getInt(2);
            }
            String s2 = "insert into evento(SEGNALAZIONE, DATETIME, TIPO_EVENTO, STATO, CATEGORIA, NOTA,ORIGINATORE) values(" + idSegnalazione + ",sysdate,3," + j + "," + i + ",'" + formatString(s) + "','" + originatoreEvento + "')";
            log(this, s2);
            execUpdQuery(s2);
        }
        catch(SQLException sqlexception)
        {
            log(this, "eccezione: " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ProblemDAOException(sqlexception.getMessage());
        }
    }

    public Collection getProblemEvents()
        throws ProblemDAOException
    {
        ArrayList arraylist = new ArrayList();
        Object obj1 = null;
        Object obj2 = null;
        String s = "select evento.SEGNALAZIONE,TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS'),evento.TIPO_EVENTO, tipo_evento.NOME_TIPO_EVENTO,evento.STATO, stato.DESCRIZIONE, evento.CATEGORIA,categoria.NOME, evento.NOTA,evento.ORIGINATORE,NVL(utente.NOME,'-'),NVL(utente.COGNOME,'-') from evento, tipo_evento, stato, categoria, utente where evento.SEGNALAZIONE=" + idSegnalazione + " and " + "evento.TIPO_EVENTO=tipo_evento.ID_TIPOEVENTO and " + "evento.STATO=stato.ID_STATO (+) and " + "evento.CATEGORIA=categoria.ID_CATEGORIA (+) and " + "evento.ORIGINATORE=utente.USERID " + "order by datetime";
        try
        {
            log(this, s);
            EventValueObject eventvalueobject;
            for(ResultSet resultset = execQuery(s); resultset.next(); arraylist.add(eventvalueobject))
                eventvalueobject = new EventValueObject(resultset.getLong(1), resultset.getString(2), resultset.getInt(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getInt(7), resultset.getString(8), resultset.getString(9), resultset.getString(10), resultset.getString(11) + " " + resultset.getString(12));

        }
        catch(SQLException sqlexception)
        {
            log(this, "eccezione: " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ProblemDAOException(sqlexception.getMessage());
        }
        return arraylist;
    }

    public EventValueObject getProblemEventDetail()
        throws ProblemDAOException
    {
        EventValueObject eventvalueobject = null;
        String s = "select evento.SEGNALAZIONE,TO_CHAR(evento.DATETIME,'DD-MM-YYYY HH24:MI:SS'),evento.TIPO_EVENTO, tipo_evento.NOME_TIPO_EVENTO,evento.STATO, stato.DESCRIZIONE, evento.CATEGORIA,categoria.NOME, evento.NOTA,evento.ORIGINATORE,NVL(utente.NOME,'-'),NVL(utente.COGNOME,'-') from evento, tipo_evento, stato, categoria, utente where evento.SEGNALAZIONE=" + idSegnalazione + " and " + "evento.TIPO_EVENTO=tipo_evento.ID_TIPOEVENTO and " + "evento.STATO=stato.ID_STATO (+) and " + "evento.CATEGORIA=categoria.ID_CATEGORIA (+) and " + "evento.ORIGINATORE=utente.USERID and " + "evento.DATETIME=TO_DATE('" + dataEvento + "','DD-MM-YYYY HH24:MI:SS')";
        try
        {
            log(this, s);
            ResultSet resultset = execQuery(s);
            if(resultset.next())
                eventvalueobject = new EventValueObject(resultset.getLong(1), resultset.getString(2), resultset.getInt(3), resultset.getString(4), resultset.getInt(5), resultset.getString(6), resultset.getInt(7), resultset.getString(8), resultset.getString(9), resultset.getString(10), resultset.getString(11) + " " + resultset.getString(12));
        }
        catch(SQLException sqlexception)
        {
            log(this, "eccezione: " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ProblemDAOException(sqlexception.getMessage());
        }
        return eventvalueobject;
    }

	public String getTemplate(int i) throws ProblemDAOException {
		String s = "";
		String s1 = "select tipo_evento.TEMPLATE_EMAIL from tipo_evento where tipo_evento.ID_TIPOEVENTO=" + i;
		try {
			log(this, s1);
			ResultSet resultset = execQuery(s1);
			if (resultset.next())
				s = resultset.getString(1);
		} catch (SQLException sqlexception) {
			log(this, "eccezione: " + sqlexception.getMessage());
			sqlexception.printStackTrace();
		}
		return s;
	}

	public void createConnection(String instance) throws ProblemDAOException {
		try {
			super.createConnection(instance);
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new ProblemDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

	public void closeConnection() throws ProblemDAOException {
		try {
			super.closeConnection();
		} catch (HelpDeskDAOException helpdeskdaoexception) {
			throw new ProblemDAOException(helpdeskdaoexception.getMessage(), helpdeskdaoexception.getUserMessage());
		}
	}

    public Collection getAllExpertProblems() throws ProblemDAOException{
    	Collection problems=new ArrayList();
    	String s="select id_segnalazione from segnalazione where esperto='"+esperto+"'";
        try
        {
            log(this, s);
            ResultSet resultset = execQuery(s);
            while(resultset.next()) {
                problems.add(new Integer(resultset.getInt(1)));
            }
        }
        catch(SQLException sqlexception)
        {
            log(this, "Eccezione: " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ProblemDAOException();
        }
    	return problems;
    }

    public Collection getReassignableExpertProblems() throws ProblemDAOException{
    	Collection problems=new ArrayList();
    	String s="select se.ID_SEGNALAZIONE,se.TITOLO,se.DESCRIZIONE,se.CATEGORIA,se.STATO,se.ORIGINATORE,se.ESPERTO,se.FLAGFAQ,st.ID_STATO,st.DESCRIZIONE,ca.ID_CATEGORIA,ca.NOME,ca.CATEGORIA_PADRE,ca.DESCRIZIONE,ca.ENABLED from segnalazione se,stato st,categoria ca where se.esperto='"+esperto+"' and se.stato>1 and se.stato<5 and se.stato=st.id_stato and se.categoria=ca.id_categoria";
        try
        {
            log(this, s);
            ResultSet resultset = execQuery(s);
            while(resultset.next()) {
                problems.add(new ProblemValueObject(resultset.getLong(1),resultset.getString(2),resultset.getString(3),resultset.getInt(4),resultset.getString(12),resultset.getInt(5),resultset.getString(10),resultset.getString(6),"",resultset.getString(7),"",resultset.getInt(8)));
            }
        }
        catch(SQLException sqlexception)
        {
            log(this, "Eccezione: " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ProblemDAOException();
        }
    	return problems;
    }

	/** <p>Aggiorna lo stato della segnalazione</p>
	 * @param problem l'id del problema
	 * @param state il nuovo stato
	 */
	public void changeState(long problem, int state) throws ProblemDAOException {
        String s = "update segnalazione set STATO="+state+" where ID_SEGNALAZIONE=" + problem;
        try {
            log(this, s);
            execUpdQuery(s);
        } catch(SQLException sqlexception1) {
            log(this, "Si \350 verificato un errore nell'esecuzione dello statement \"" + s + "\". " + sqlexception1.getMessage());
            sqlexception1.printStackTrace();
            throw new ProblemDAOException(sqlexception1.getMessage(), "Si \350 verificato un errore durante l'aggiornamento dello stato della segnalazione. Contattare l'assistenza.");
        }
 
	}

	/** <p>inserisce una nuova segnalazione prelevando un id dal relativo sequence</p>
	 * @param pvo l'oggetto che rappresenta la segnalazione
	 * @return il nuovo id generato
	 */
	public long executeInsert(ProblemValueObject pvo) throws ProblemDAOException {
		String s = "select seq_segnalazione.nextval from dual";
        int i = 0;
        try {
            ResultSet resultset = execQuery(s);
            if(resultset.next())
                i = resultset.getInt(1);
            s = "insert into segnalazione(ID_SEGNALAZIONE, TITOLO, DESCRIZIONE, CATEGORIA, STATO, ORIGINATORE, PRIORITA) " +
            		"values(" + i + ",'" + pvo.getTitolo2SQL() + "','" + pvo.getDescrizione2SQL() + "'," + pvo.getCategoria() + "," + pvo.getStato() + ",'" + pvo.getOriginatore() + "'," + pvo.getPriorita() +")";
            log(this, s);
            execUpdQuery(s);
        } catch(SQLException sqlexception) {
            log(this, "Si \350 verificato un errore nell'esecuzione della query \"" + s + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante l'inserimento della nuova segnalazione.");
        }
        return i;
	}

	/** <p>cambia la categoria di una segnalazione</p>
	 *  @param evo l'oggetto che rappresenta l'evento specificato
	 */
	public void modifyCategory(EventValueObject evo) throws ProblemDAOException {
		String s1 = "update segnalazione set CATEGORIA=" + evo.getCategory() + " " + "where ID_SEGNALAZIONE=" + evo.getIdSegnalazione();
        try {
            log(this, s1);
            execUpdQuery(s1);
        } catch(SQLException sqlexception) {
            log(this, "Si \350 verificato un errore nell'esecuzione dello statement \"" + s1 + "\". " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il cambiamento della categoria cui \350 associata la segnalazione. Contattare l'assistenza.");
        } 		
	}


    public void assignProblemToExpert(EventValueObject evo) throws ProblemDAOException {
    String s1 = "update segnalazione set ESPERTO='" + evo.getExpertLogin() + "' where ID_SEGNALAZIONE=" + evo.getIdSegnalazione();
    try {
        log(this, s1);
        execUpdQuery(s1);
    } catch(SQLException sqlexception) {
        log(this, "Si \350 verificato un errore nell'esecuzione del sequente coduce SQL \"" + s1 + "\". " + sqlexception.getMessage());
        sqlexception.printStackTrace();
        throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante l'assegnazione del problema all'esperto. Contattare l'assistenza.");
    }
}

	public void assignProblemToValidator(EventValueObject evo) throws ProblemDAOException {
	    String s1 = "update segnalazione set VALIDATORE='" + evo.getValidatorLogin() + "' where ID_SEGNALAZIONE=" + evo.getIdSegnalazione();
	    try {
	        log(this, s1);
	        execUpdQuery(s1);
	    } catch(SQLException sqlexception) {
	        log(this, "Si \350 verificato un errore nell'esecuzione del sequente coduce SQL \"" + s1 + "\". " + sqlexception.getMessage());
	        sqlexception.printStackTrace();
	        throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante l'assegnazione del problema al validatore. Contattare l'assistenza.");
	    }		
	}
	
	
	public void releaseProblemByExpert(EventValueObject evo)
			throws ProblemDAOException {
	    String s1 = "update segnalazione set ESPERTO='' where ID_SEGNALAZIONE=" + evo.getIdSegnalazione();
	    try {
	        log(this, s1);
	        execUpdQuery(s1);
	    } catch(SQLException sqlexception) {
	        log(this, "Si \350 verificato un errore nell'esecuzione del sequente coduce SQL \"" + s1 + "\". " + sqlexception.getMessage());
	        sqlexception.printStackTrace();
	        throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il rilascio del problema da parte dell'esperto. Contattare l'assistenza.");
	    }		
	}
	public void releaseProblemByValidator(EventValueObject evo)
			throws ProblemDAOException {
	    String s1 = "update segnalazione set VALIDATORE='' where ID_SEGNALAZIONE=" + evo.getIdSegnalazione();
	    try {
	        log(this, s1);
	        execUpdQuery(s1);
	    } catch(SQLException sqlexception) {
	        log(this, "Si \350 verificato un errore nell'esecuzione del sequente coduce SQL \"" + s1 + "\". " + sqlexception.getMessage());
	        sqlexception.printStackTrace();
	        throw new ProblemDAOException(sqlexception.getMessage(), "Si \350 verificato un errore durante il rilascio del problema da parte del validatore. Contattare l'assistenza.");
	    }		
	}

   /**
	 * query customizzabile sulla tabella eventi (alias <b>e</b>) 
	 * in join con i relativi record della tabella segnalazioni (alias <b>s</b>)
	 * 
	 * @param conditions una Collection di String ciascuna contenente una condizione del tipo "[and/or] [e/s].campo [operatore] valore"
	 * @return il numero di record risultanti 
	 */
	public int countEventQBE(Collection conditions) throws ProblemDAOException {
    	String s="select count(*) from segnalazione s, evento e where s.id_segnalazione = e.SEGNALAZIONE ";
    	for(Iterator cond = conditions.iterator(); cond.hasNext();)
    		s = s.concat(" " + cond.next());
    	int count = 0;
        try {
            log(this, s);
            ResultSet resultset = execQuery(s);
            resultset.next();
            count = resultset.getInt(1);
        } catch(SQLException sqlexception) {
            log(this, "Eccezione: " + sqlexception.getMessage());
            sqlexception.printStackTrace();
            throw new ProblemDAOException();
        }
    	return count;		
	}
	
}
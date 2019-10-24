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

import it.cnr.helpdesk.dao.HelpDeskDAO;
import it.cnr.helpdesk.StatisticsManagement.valueobjects.*;
import it.cnr.helpdesk.StatisticsManagement.exceptions.*;
import it.cnr.helpdesk.exceptions.*;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.dao.*;
// Java libraries imports
import java.sql.*;
import java.util.*;
public class PostgresStatisticsDAO extends HelpDeskDAO implements StatisticsDAO {
 public PostgresStatisticsDAO() {
   super();
 }

 // Setter methods
 public void setStartDate(String startDate){
   this.startDate=startDate;
 }
 public void setEndDate(String endDate){
   this.endDate=endDate;
 }
 public void setSamplingInterval(Integer samplingInterval){
   this.samplingInterval=samplingInterval;
 }
 public void setHelpdeskUserID(String  helpdeskUserID){
   this.helpdeskUserID=helpdeskUserID;
 }
 public void setHelpdeskUserProfile(int helpdeskUserProfile){
   this.helpdeskUserProfile=helpdeskUserProfile;
 }
 public void sethelpDeskUser(UserValueObject helpdeskUser){
   this.helpdeskUser=helpdeskUser;
 }

 public void setStatisticsType(int statisticsType){
   this.statisticsType=statisticsType;
 }

 // Getter methods
 public String getStartDate(){
   return this.startDate;
 }
 public String getEndDate(){
   return this.endDate;
 }
 public Integer getSamplingInterval(){
   return this.samplingInterval;
 }
 public String getHelpdeskUserID(){
   return this.helpdeskUserID;
 }
 public int getHelpdeskUserProfile(){
   return this.helpdeskUserProfile;
 }
 public UserValueObject getHelpdeskUser(){
   return this.helpdeskUser;
 }
 public int getStatisticsType(){
   return this.statisticsType;
 }

 // The following method creates a connection to the DB
 // calling the createConnection method of the parent class
 public void createConnection(String instance) throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException {
   try {
     super.createConnection(instance);
   }catch (HelpDeskDAOException e) {
     throw new StatisticsDAOException(e.getMessage(),e.getUserMessage());
   }
 }

 // The following method creates a connection to the DB
 // calling the closeConnection method of the parent class
 public void closeConnection() throws StatisticsDAOException {
   try {
     super.closeConnection();
   } catch (HelpDeskDAOException e) {
     throw new StatisticsDAOException(e.getMessage(),e.getUserMessage());
   }
 }

 // The following method evaluates the number of problems which where opened
 // and closed by the 2nd level helpdesk in a specified period. The result is
 // returned as an int value.
 public int getOpenClosedCount() throws StatisticsDAOException{
   int count=-1;
   String sqlQuery="select count(*) "+
                   "from evento ev1,evento ev2 "+
                   "where "+
                   "        ev1.segnalazione=ev2.segnalazione "+
                   "  and   ev1.tipo_evento=0 "+
                   "  and   ev2.tipo_evento=1 "+
                   "  and   ev2.stato=4"+
                   "  and   (ev1.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss')) "+
                   "  and   (ev2.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss'))"+
                   "  and   ev2.datetime = (select min(datetime) from evento "+
                   "                        where  evento.segnalazione=ev2.segnalazione "+
                   "                          and  tipo_evento=1 "+
                   "                          and  stato=4)";
   try{
     log(this,sqlQuery);
     ResultSet rs=execQuery(sqlQuery);
     if (rs.next()) count=rs.getInt(1);
   } catch (Exception e) {
     log(this,"Si è verificato un errore nell'esecuzione della query per il conteggio dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" e il "+endDate);
     throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore nell'esecuzione della query per il conteggio dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" e il "+endDate);
   }

   if (count==-1) {
     log(this,"Si è verificato un errore nel calcolo del numero di problemi aperti e chiusi nel periodo "+
             "compreso tra il"+startDate+" ed il "+endDate);
     throw new StatisticsDAOException("Risultato non ammesso: -1","Si è verificato un errore nell'esecuzione della query per il conteggio dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" e il "+endDate);
   }
   return count;
 }

 // The following method evaluates the min response time for the problems which where opened
 // and closed by the 2nd level helpdesk in a specified period. The result is
 // returned as a float value.
 public float getMinResponseTime() throws StatisticsDAOException{
        float f = -1;
        float min = -1;
   String sqlQuery="select min(ev2.datetime-ev1.datetime) "+
                   "from evento ev1,evento ev2 "+
                   "where "+
                   "        ev1.segnalazione=ev2.segnalazione "+
                   "  and   ev1.tipo_evento=0 "+
                   "  and   ev2.tipo_evento=1 "+
                   "  and   ev2.stato=4"+
                   "  and   (ev1.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss')) "+
                   "  and   (ev2.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss'))"+
                   "  and   ev2.datetime = (select min(datetime) from evento "+
                   "                        where  evento.segnalazione=ev2.segnalazione "+
                   "                          and  tipo_evento=1 "+
                   "                          and  stato=4)";
   try{
     log(this,sqlQuery);
     ResultSet rs=execQuery(sqlQuery);
     if (rs.next()) min=rs.getFloat(1);
   } catch (Exception e) {
     log(this,"Si è verificato un errore nell'esecuzione della query per il calcolo del minimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" ed il "+endDate);
     throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore nell'esecuzione della query per il calcolo del minimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" ed il "+endDate);
   }

   if (min==-1) {
     log(this,"Si è verificato un errore nel calcolo del minimo tempo di risoluzione dei problemi aperti e chiusi nel periodo "+
         "compreso tra il"+startDate+" ed il "+endDate);
     throw new StatisticsDAOException("Risultato non ammesso: -1","Si è verificato un errore nell'esecuzione della query per il calcolo del minimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" ed il "+endDate);
   }
   return min;
 }

 // The following method evaluates the max response time for the problems which where opened
 // and closed by the 2nd level helpdesk in a specified period. The result is
 // returned as a float value.
 public float getMaxResponseTime() throws StatisticsDAOException{
   float max=-1;
   String sqlQuery="select max(ev2.datetime-ev1.datetime) "+
                   "from evento ev1,evento ev2 "+
                   "where "+
                   "        ev1.segnalazione=ev2.segnalazione "+
                   "  and   ev1.tipo_evento=0 "+
                   "  and   ev2.tipo_evento=1 "+
                   "  and   ev2.stato=4"+
                   "  and   (ev1.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss')) "+
                   "  and   (ev2.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss'))"+
                   "  and   ev2.datetime = (select min(datetime) from evento "+
                   "                        where  evento.segnalazione=ev2.segnalazione "+
                   "                          and  tipo_evento=1 "+
                   "                          and  stato=4)";
   try{
     log(this,sqlQuery);
     ResultSet rs=execQuery(sqlQuery);
     if (rs.next()) max=rs.getFloat(1);
   } catch (Exception e) {
     log(this,"Si è verificato un errore nell'esecuzione della query per il calcolo del massimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" ed il "+endDate);
     throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore nell'esecuzione della query per il calcolo del massimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" ed il "+endDate);
   }

   if (max==-1) {
     log(this,"Si è verificato un errore nel calcolo del massimo tempo di risoluzione dei problemi aperti e chiusi nel periodo "+
         "compreso tra il"+startDate+" ed il "+endDate);
     throw new StatisticsDAOException("Risultato non ammesso: -1","Si è verificato un errore nell'esecuzione della query per il calcolo del massimo tempo di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" ed il "+endDate);
   }
   return max;
 }

 // The following method evaluates the average response time of the 2nd level helpdesk
 // in the specified period. The result is returned as a floating point number.
 public float getAverageResponseTime() throws StatisticsDAOException {
   float avg=-1;
   String sqlQuery="select avg(ev2.datetime-ev1.datetime) "+
                   "from evento ev1,evento ev2 "+
                   "where "+
                   "        ev1.segnalazione=ev2.segnalazione "+
                   "  and   ev1.tipo_evento=0 "+
                   "  and   ev2.tipo_evento=1 "+
                   "  and   ev2.stato=4"+
                   "  and   (ev1.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss')) "+
                   "  and   (ev2.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss'))"+
                   "  and   ev2.datetime = (select min(datetime) from evento "+
                   "                        where  evento.segnalazione=ev2.segnalazione "+
                   "                          and  tipo_evento=1 "+
                   "                          and  stato=4)";
   try{
     log(this,sqlQuery);
     ResultSet rs=execQuery(sqlQuery);
     if (rs.next()) avg=rs.getFloat(1);
   } catch (Exception e) {
     log(this,"Si è verificato un errore nell'esecuzione della query per il calcolo del tempo medio di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" ed il "+endDate);
     throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore nell'esecuzione della query per il calcolo del tempo medio di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" e il "+endDate);
   }

   if (avg==-1) {
     log(this,"Si è verificato un errore nel calcolo del tempo medio di risoluzione dei problemi aperti e chiusi nel periodo "+
         "compreso tra il"+startDate+" ed il "+endDate);
     throw new StatisticsDAOException("Risultato non ammesso: -1","Si è verificato un errore nell'esecuzione della query per il calcolo del tempo medio di risoluzione dei problemi aperti e chiusi nel periodo compreso tra il "+startDate+" e il "+endDate);
   }
   return avg;
 }

 // The following method counts the number of problems submitted to the 2nd level helpdesk service
 // during the reference period (from startDate to endDate) and evaluates the max, min, avg response time
 // in the same period. The results are returned inside an OverallStatisticsDTO.
 public OverallStatisticsDTO getOverallStatistics() throws StatisticsDAOException {
   OverallStatisticsDTO osDTO=null;
   String sqlQuery=null;
   System.out.println("statisticsType="+statisticsType);
   System.out.println("helpdeskUserID="+helpdeskUserID);
   System.out.println("helpdeskUserProfile="+helpdeskUserProfile);
   if (statisticsType==GENERAL)
       sqlQuery="select count(*), "+
                "min((cast(to_char(ev2.datetime,'J') as double precision)-cast(to_char(ev1.datetime,'J') as double precision))*86400+cast(to_char(ev2.datetime,'SSSS') as double precision)-cast(to_char(ev1.datetime,'SSSS') as double precision)), "+
                "max((cast(to_char(ev2.datetime,'J') as double precision)-cast(to_char(ev1.datetime,'J') as double precision))*86400+cast(to_char(ev2.datetime,'SSSS') as double precision)-cast(to_char(ev1.datetime,'SSSS') as double precision)), "+
                "avg((cast(to_char(ev2.datetime,'J') as double precision)-cast(to_char(ev1.datetime,'J') as double precision))*86400+cast(to_char(ev2.datetime,'SSSS') as double precision)-cast(to_char(ev1.datetime,'SSSS') as double precision)) "+
                   "from evento ev1,evento ev2 "+
                   "where "+
                   "        ev1.segnalazione=ev2.segnalazione "+
                   "  and   ev1.tipo_evento=0 "+
                   "  and   ev2.tipo_evento=1 "+
                   "  and   ev2.stato=4"+
                   "  and   (ev1.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss')) "+
                   "  and   (ev2.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss'))"+
                   "  and   ev2.datetime = (select min(datetime) from evento "+
                   "                        where  evento.segnalazione=ev2.segnalazione "+
                   "                          and  tipo_evento=1 "+
                   "                          and  stato=4)";
   if (statisticsType==PERSONAL){
     switch (helpdeskUserProfile){
       case 1:
         sqlQuery="select count(*)," +
         			"min((cast(to_char(ev2.datetime,'J') as double precision)-cast(to_char(ev1.datetime,'J') as double precision))*86400+cast(to_char(ev2.datetime,'SSSS') as double precision)-cast(to_char(ev1.datetime,'SSSS') as double precision)), "+
					"max((cast(to_char(ev2.datetime,'J') as double precision)-cast(to_char(ev1.datetime,'J') as double precision))*86400+cast(to_char(ev2.datetime,'SSSS') as double precision)-cast(to_char(ev1.datetime,'SSSS') as double precision)), "+
					"avg((cast(to_char(ev2.datetime,'J') as double precision)-cast(to_char(ev1.datetime,'J') as double precision))*86400+cast(to_char(ev2.datetime,'SSSS') as double precision)-cast(to_char(ev1.datetime,'SSSS') as double precision)) "+
              "from evento ev1,evento ev2,segnalazione "+
                     "where "+
                     "        ev1.segnalazione=ev2.segnalazione "+
                     "  and   ev1.segnalazione=segnalazione.id_segnalazione"+
                     "  and   segnalazione.originatore='"+ helpdeskUserID +"' "+
                     "  and   ev1.tipo_evento=0 "+
                     "  and   ev2.tipo_evento=1 "+
                     "  and   ev2.stato=4"+
                     "  and   (ev1.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss')) "+
                     "  and   (ev2.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss'))"+
                     "  and   ev2.datetime = (select min(datetime) from evento "+
                     "                        where  evento.segnalazione=ev2.segnalazione "+
                     "                          and  tipo_evento=1 "+
                   "                          and  stato=4)";
         break;
       case 2:
         sqlQuery="select count(*)," +
         			"min((cast(to_char(ev2.datetime,'J') as double precision)-cast(to_char(ev1.datetime,'J') as double precision))*86400+cast(to_char(ev2.datetime,'SSSS') as double precision)-cast(to_char(ev1.datetime,'SSSS') as double precision)), "+
                	"max((cast(to_char(ev2.datetime,'J') as double precision)-cast(to_char(ev1.datetime,'J') as double precision))*86400+cast(to_char(ev2.datetime,'SSSS') as double precision)-cast(to_char(ev1.datetime,'SSSS') as double precision)), "+
					"avg((cast(to_char(ev2.datetime,'J') as double precision)-cast(to_char(ev1.datetime,'J') as double precision))*86400+cast(to_char(ev2.datetime,'SSSS') as double precision)-cast(to_char(ev1.datetime,'SSSS') as double precision)) "+
                "from evento ev1,evento ev2,segnalazione "+
                     "where "+
                     "        ev1.segnalazione=ev2.segnalazione "+
                     "  and   ev1.segnalazione=segnalazione.id_segnalazione"+
                     "  and   segnalazione.esperto='"+ helpdeskUserID + "' "+
                     "  and   ev1.tipo_evento=0 "+
                     "  and   ev2.tipo_evento=1 "+
                     "  and   ev2.stato=4"+
                     "  and   (ev1.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss')) "+
                     "  and   (ev2.datetime between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss'))"+
                     "  and   ev2.datetime = (select min(datetime) from evento "+
                     "                        where  evento.segnalazione=ev2.segnalazione "+
                     "                          and  tipo_evento=1 "+
                   "                          and  stato=4)";
         break;
       case 3:
         throw new StatisticsDAOException("Statistica non definita per l'amministratore","Statistica non definita per l'amministratore");
     }

   }

   try{
     log(this,sqlQuery);
     ResultSet rs=execQuery(sqlQuery);
     if (rs.next()){
       osDTO=new OverallStatisticsDTO(rs.getInt(1),rs.getDouble(2)/86400, rs.getDouble(3)/86400, rs.getDouble(4)/86400);
     }
   } catch (Exception e) {
     log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
     throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore durante il calcolo delle statistiche dell'helpdesk di secondo livello. Contattare l'assistenza.");
   }
   return osDTO;
 }

 // The following method evaluates the max, min, avg response time of the external helpdesk service
 // during the reference period (from startDate to endDate). The results are returned inside
 // a ResponseTimeStatisticsDTO.
 // @todo definire la query SQL
 public ResponseTimeStatisticsDTO getExternalResponseTimeStatistics()throws StatisticsDAOException {
   ResponseTimeStatisticsDTO rtsDTO=null;
   String sqlQuery="Select max(close-open),min(close-open),avg(close-open) from ExternalProblems "+
                   "Where open>=to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and"+
                   "      close<=to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss')";
   try{
     log(this,sqlQuery);
     ResultSet rs=execQuery(sqlQuery);
     if (rs.next()){
       rtsDTO=new ResponseTimeStatisticsDTO(rs.getFloat(1), rs.getFloat(2), rs.getFloat(3));
     }
   } catch (Exception e) {
     log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
     throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore durante il calcolo delle statistiche generali dei tempi di risposta dell'helpdesk di terzo livello. Contattare l'assistenza.");
   }
   return rtsDTO;
 }

 // The following method returns the distribution of the problems by priority level.
 // Results are returned inside a Collection.
 // Note: this method applies to third level helpdesk metadata.
 // @todo definire la query SQL
 public Collection getProblemCountByPriorityLevel() throws StatisticsDAOException{
   Collection problemDistributionByPriority=null;
   ProblemsDistributionByPriorityDTO pdbpDTO=null;
   String sqlQuery="Select Priority,count(*) from ExternalProblems "+
                   "Where open>=to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and"+
                   "      close<=to_timestamp('"+endDate+"','dd/mm/yyyy hh24:mi:ss') "+
                   "Group by Priority";
   try{
     log(this,sqlQuery);
     ResultSet rs=execQuery(sqlQuery);
     while (rs.next()){
       pdbpDTO=new ProblemsDistributionByPriorityDTO(rs.getInt(1),rs.getInt(2));
       problemDistributionByPriority.add(pdbpDTO);
     }
   } catch (Exception e) {
     log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
     throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore durante il conteggio dei problemi per livello di priorità. Contattare l'assistenza.");
   }
   return problemDistributionByPriority;
 }

 // The following method evaluates the number of problems opened in the observed
 // period
 // @todo definire la query SQL
 public Integer getOpenedCount() throws StatisticsDAOException {
   String sqlQuery="Select count(*) from Segnalazione "+
                   "Where StartDate between to_timestamp('"+startDate+"','dd/mm/yyyy hh24:mi:ss') and "+
                   "to_timestamp('"+endDate+"') and status=1";
   Integer openedCount=null;
   try{
     log(this,sqlQuery);
     ResultSet rs=execQuery(sqlQuery);
     if (rs.next()){
       openedCount=new Integer(rs.getInt(1));
     }
   } catch (SQLException e) {
     log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
     throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore durante il conteggio dei problemi aperti. Contattare l'assistenza.");
   }
   return openedCount;
 }

 // The following method counts the number of problems per each status (open,
 // closed, working, external, verified). The method uses the attributes helpdeskUserID,
 // and helpdeskUserProfile as parameters to customise the query.
 // The scope of the report depends on the user's profile (user, expert, administrator).
 // A normal user will only see information regarding his own problems, an expert
 // will only see info about the problems he took in charge and the total number
 // of open problems in the categories he is assigned to, an administrator
 // will see information about the overall situation of the helpdesk service.
 public Collection getProblemsDistributionByStatus
        () throws StatisticsDAOException{
   Collection problemsDistributionByStatus=new ArrayList();
   ProblemsDistributionByStatusDTO pdbsDTO=null;
   String sqlQuery="";

   switch (helpdeskUserProfile){
   case 1: // Normal user
     sqlQuery=       " Select stato.ID_STATO as id, stato.DESCRIZIONE as descrizione,count(*) as totale"+
                     " from segnalazione, stato "+
                     " where segnalazione.STATO=stato.ID_STATO "+
                     " and originatore='"+helpdeskUserID+"' group by stato.ID_STATO,stato.DESCRIZIONE"+
                     " order by id";
     break;
   case 2: // Expert
     if (statisticsType==PERSONAL)
       sqlQuery="Select stato.ID_STATO as id,stato.DESCRIZIONE as descrizione ,count(*) as totale"+
                " from segnalazione,stato"+
                " where segnalazione.STATO=stato.ID_STATO"+
                " and stato.ID_STATO=1 "+
                " and segnalazione.CATEGORIA in (select  categoria from esperto_categoria where esperto='"+helpdeskUserID+"') "+
                " group by stato.ID_STATO,stato.DESCRIZIONE UNION "+
                " Select stato.ID_STATO as id,stato.DESCRIZIONE as descrizione ,count(*) as totale"+
                " from segnalazione,stato"+
                " where segnalazione.STATO=stato.ID_STATO "+
                " and esperto='"+helpdeskUserID+
                "' group by stato.ID_STATO,stato.DESCRIZIONE"+
                " order by id";
     if (statisticsType==GENERAL)
       sqlQuery="Select stato.ID_STATO as id,stato.DESCRIZIONE as descrizione ,count(*) as totale"+
                " from Segnalazione , Esperto_Categoria, stato "+
                " where segnalazione.STATO=stato.ID_STATO "+
                " and Segnalazione.Categoria=Esperto_Categoria.Categoria "+
                " and Esperto_Categoria.Esperto='"+helpdeskUserID+"' "+
                " group by stato.ID_STATO,stato.DESCRIZIONE";
     break;
   case 3: // Administrator
     sqlQuery="Select stato.ID_STATO as id, stato.DESCRIZIONE as descrizione,count(*) as totale "+
              "from segnalazione,stato "+
              "where segnalazione.STATO=stato.ID_STATO "+
              "group by stato.ID_STATO,stato.DESCRIZIONE "+
              "order by id";
     break;
   default: // Assume it is a normal user
     log(this,"Si è verificato un  problema durante il calcolo della "+
               "distribuzione dei problemi per stato: profilo utente non definito o non valido.");
     throw new StatisticsDAOException("Si è verificato un problema durante il calcolo della "+
               "distribuzione dei problemi per stato: profilo utente non definito o non valido.",
               "Impossibile produrre il report richiesto: profilo utente non definito o non valido.");
   }

   try{
     log(this,sqlQuery);
     ResultSet rs=execQuery(sqlQuery);
     while (rs.next()){
       pdbsDTO=new ProblemsDistributionByStatusDTO(rs.getString(2),rs.getInt(3));
       problemsDistributionByStatus.add(pdbsDTO);
     }
   } catch (Exception e) {
     log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
     throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore in fase di accesso al data base durante il conteggio dei problemi per stato di lavorazione. Contattare l'assistenza.");
   }
   return problemsDistributionByStatus;
 }

 // The following method counts the number of problems per each status (open,
 // closed, working, external, verified) at a given date. The method uses the class attributes helpdeskUserID,
 // and helpdeskUserProfile as parameters to customise the query.
 // The scope of the report depends on the user's profile (user, expert, administrator).
 // A normal user will only see information regarding his own problems, an expert
 // will only see info about the problems he took in charge and the total number
 // of open problems in the categories he is assigned to, an administrator
 // will see information about the overall situation of the helpdesk service.
 // @todo update with the GENERAL and PERSONAL option for the expert
 public Collection getProblemDistributionByStatus(String date) throws StatisticsDAOException {
   Collection problemsDistributionByStatus=new ArrayList();
   ProblemsDistributionByStatusDTO pdbsDTO=null;
   String statusListQuery="select id_stato,descrizione from stato order by id_stato";
   ResultSet statusResultSet=null;
   try {
     statusResultSet=execQuery(statusListQuery);
   } catch (Exception e) {
     log(this,"Si è verificato un errore nell'esecuzione della query \""+statusListQuery+"\". "+e.getMessage());
     throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore in fase di accesso al data base durante il recupero della lista degli stati. Contattare l'assistenza.");
   }
   switch (helpdeskUserProfile) {
       case 1:
         String sqlQuery=null;
         try {
           while (statusResultSet.next()){
             sqlQuery= "select count(*) from segnalazione,evento ev"+
                              " where segnalazione.id_segnalazione=ev.segnalazione and "+
                              " segnalazione.originatore='"+helpdeskUserID+"' and "+
                              " ev.stato="+statusResultSet.getInt(1)+" and "+
                              " ev.datetime<=to_timestamp('"+date+"','dd/mm/yyyy hh24:mi:ss') and "+
                              " not exists "+
                              "    (select * from evento "+
                              "     where evento.segnalazione=ev.segnalazione and "+
                              "           evento.datetime>ev.datetime and "+
                              "           evento.datetime<=to_timestamp('"+date+"','dd/mm/yyyy hh24:mi:ss') ";


             ResultSet rs = execQuery(sqlQuery);
             if (rs.next()) {
               pdbsDTO.setStatus(statusResultSet.getString(2));
               pdbsDTO.setCount(rs.getInt(1));
               problemsDistributionByStatus.add(pdbsDTO);
             }
           }
         } catch (Exception e) {
           log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
           throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore in fase di accesso al data base durante il conteggio dei problemi in stato \"Open\" alla data "+date+". Contattare l'assistenza.");
         }
         break;
   }

   return problemsDistributionByStatus;

 }

 public Collection getProblemsDistributionByCategory() throws StatisticsDAOException {
   Collection c=new ArrayList();
   String sqlQuery="";
   ProblemsDistributionByCategoryDTO pdbcDTO;
   switch (helpdeskUserProfile){
     case 1:
       if (statisticsType==PERSONAL)
         sqlQuery="Select Segnalazione.Categoria,Categoria.nome,count(*) as tot"+
         " from Categoria, Segnalazione  "+
         " where "+
         " Segnalazione.categoria=Categoria.id_categoria and "+
         " Segnalazione.originatore='"+helpdeskUserID+"' "+
         " group by Segnalazione.Categoria, Categoria.nome "+
         " order by tot desc";
       if (statisticsType==GENERAL){
         throw new StatisticsDAOException("Tipo di statistica non accessibile all'utente normale","Tipo di statistica non accessibile all'utente normale");
       }
       break;
     case 2:
       if (statisticsType==PERSONAL)
         sqlQuery="Select Segnalazione.Categoria,Categoria.nome,count(*) as tot"+
         " from Categoria , Segnalazione"+
         " where "+
         " segnalazione.categoria=categoria.id_categoria "+
         " and segnalazione.esperto='"+helpdeskUserID+"' " +
         " group by Segnalazione.Categoria, Categoria.nome" +
         " order by tot desc";
       if (statisticsType==GENERAL)
         sqlQuery="Select Segnalazione.Categoria,Categoria.nome,count(*) as tot"+
         " from Categoria , Segnalazione ,Esperto_Categoria  "+
         " where "+
         " Categoria.id_Categoria=Esperto_Categoria.Categoria "+
         " and Segnalazione.categoria=categoria.id_categoria "+
         " and Esperto_Categoria.Esperto='"+helpdeskUserID+"' "+
         " group by Segnalazione.Categoria, Categoria.nome"+
         " order by tot desc";
       break;
       case 3:
         if (statisticsType==PERSONAL)
           throw new StatisticsDAOException("Tipo di statistica non definita per l'utente amministratore","Tipo di statistica non definita per l'utente amministratore");
         if (statisticsType==GENERAL)
           sqlQuery="Select Segnalazione.Categoria,Categoria.nome,count(*) as tot"+
           " from Categoria , Segnalazione "+
           " where "+
           " Segnalazione.categoria=categoria.id_categoria "+
           " group by Segnalazione.Categoria, Categoria.nome"+
           " order by tot desc";
         break;
   }
   try{
     log(this,sqlQuery);
     ResultSet rs=execQuery(sqlQuery);
     while (rs.next()){
       pdbcDTO=new ProblemsDistributionByCategoryDTO(rs.getString(2),rs.getInt(3));
       c.add(pdbcDTO);
     }
   } catch (Exception e) {
     log(this,"Si è verificato un errore nell'esecuzione della query \""+sqlQuery+"\". "+e.getMessage());
     throw new StatisticsDAOException(e.getMessage(),"Si è verificato un errore in fase di accesso al data base durante il conteggio dei problemi per categoria. Contattare l'assistenza.");
   }
   return c;
  }

  private String startDate; // Start of the observed period
 private String endDate; // End of the observed period
 private Integer samplingInterval; // Sampling interval used when a statistics is calculated at fixed intervals
 private String helpdeskUserID;
 private int helpdeskUserProfile;
 private UserValueObject helpdeskUser;
 private int statisticsType;
    private static final int PERSONAL = 1;
    private static final int GENERAL = 2;


}

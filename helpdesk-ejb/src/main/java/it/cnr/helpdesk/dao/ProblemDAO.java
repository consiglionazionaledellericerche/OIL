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
package it.cnr.helpdesk.dao;

/**
 * <p>Title: OIL - Online Interactive heLpdesk</p>
 * <p>Description: A Web Based Help Desk Tool</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: OIL</p>
 * @author Andrea Bei, Roberto Puccinelli
 * @version 1.0
 */

import java.util.*;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemDAOException;
import it.cnr.helpdesk.ProblemManagement.valueobjects.*;
import it.cnr.helpdesk.util.*;


public interface ProblemDAO {
  public void addNote(String note) throws ProblemDAOException;
  public void closeConnection() throws ProblemDAOException ;
  public void createConnection(String instance) throws ProblemDAOException ;
  public void executeDelete(Collection c) throws ProblemDAOException ;
  public String getDataEvento() ;
  public String getEsperto() ;
  public PageByPageIterator getExpertProblemQueue(PageByPageIterator pbpIterator, ProblemValueObject pvo) throws ProblemDAOException ;
  public Collection getReassignableExpertProblems() throws ProblemDAOException ;
  public long getIdSegnalazione() ;
  public String getOriginatoreEvento() ;
  public Collection getProblemCountPerCategory(ProblemCountValueObject pcvo) throws ProblemDAOException ;
  public ProblemValueObject getProblemDetail() throws ProblemDAOException ;
  public EventValueObject getProblemEventDetail() throws ProblemDAOException ;
  public Collection getProblemEvents() throws ProblemDAOException ;
  public String getTemplate(int idTemplate) throws ProblemDAOException ;
  public PageByPageIterator getUserProblemQueue(PageByPageIterator pbpIterator, ProblemValueObject pvo) throws ProblemDAOException ;
  public void setDataEvento(String dataEvento) ;
  public void setEsperto(String esperto) ;
  public void setIdSegnalazione(long idSegnalazione) ;
  public void setOriginatoreEvento(String originatoreEvento) ;
  public Collection getAllExpertProblems() throws ProblemDAOException;
  public void reAssignProblemToExpert(EventValueObject evo) throws ProblemDAOException;
  public void changeState(long problem, int state) throws ProblemDAOException;
  public long executeInsert(ProblemValueObject pvo) throws ProblemDAOException;
  public void modifyCategory(EventValueObject evo) throws ProblemDAOException;
  public void assignProblemToExpert(EventValueObject evo) throws ProblemDAOException;
  public void assignProblemToValidator(EventValueObject evo) throws ProblemDAOException;
  public void releaseProblemByExpert(EventValueObject evo) throws ProblemDAOException;
  public void releaseProblemByValidator(EventValueObject evo) throws ProblemDAOException;
  public int countEventQBE(Collection conditions) throws ProblemDAOException;
  public PageByPageIterator getTeamProblemQueue(PageByPageIterator pagebypageiterator, ProblemValueObject problemvalueobject)  throws ProblemDAOException;
  public Collection getTeamProblemCountPerCategory(ProblemCountValueObject problemcountvalueobject) throws ProblemDAOException;
  
}

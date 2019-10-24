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

import java.util.*;


import it.cnr.helpdesk.StatisticsManagement.valueobjects.*;
import it.cnr.helpdesk.UserManagement.valueobjects.*;


/**
 * <p>Title: OIL - Online Interactive heLpdesk</p>
 * <p>Description: A Web Based Help Desk Tool</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: OIL</p>
 * @author Andrea Bei, Roberto Puccinelli
 * @version 1.0
 */

public interface StatisticsDAO {
  public int getStatisticsType() ;
  public void createConnection(String instance) throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public Integer getOpenedCount() throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public String getStartDate() ;
  public void setHelpdeskUserID(String helpdeskUserID) ;
  public Collection getProblemsDistributionByStatus() throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public void setHelpdeskUserProfile(int helpdeskUserProfile) ;
  public float getMinResponseTime() throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public String getEndDate() ;
  public float getAverageResponseTime() throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public void closeConnection() throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public OverallStatisticsDTO getOverallStatistics() throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public void setStartDate(String startDate) ;
  public String getHelpdeskUserID() ;
  public int getOpenClosedCount() throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public void setEndDate(String endDate) ;
  public ResponseTimeStatisticsDTO getExternalResponseTimeStatistics() throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public void sethelpDeskUser(UserValueObject helpdeskUser) ;
  public void setSamplingInterval(Integer samplingInterval) ;
  public void setStatisticsType(int statisticsType) ;
  public int getHelpdeskUserProfile() ;
  public Collection getProblemsDistributionByCategory() throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public float getMaxResponseTime() throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public Integer getSamplingInterval() ;
  public UserValueObject getHelpdeskUser() ;
  public Collection getProblemDistributionByStatus(String date) throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;
  public Collection getProblemCountByPriorityLevel() throws it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsDAOException ;

}

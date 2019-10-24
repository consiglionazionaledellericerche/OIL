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

import it.cnr.helpdesk.EventManagement.dao.OracleEventDAO;
import it.cnr.helpdesk.FaqManagement.dao.OracleFaqDAO;
import it.cnr.helpdesk.ProblemManagement.dao.OracleProblemDAO;
import it.cnr.helpdesk.UOManagement.dao.OracleUODAO;
import it.cnr.helpdesk.UserManagement.dao.OracleUserDAO;
import it.cnr.helpdesk.ApplicationSettingsManagement.dao.OracleApplicationSettingsDAO;
import it.cnr.helpdesk.CategoryManagement.dao.OracleCategoryDAO;
import it.cnr.helpdesk.RepositoryManagement.dao.OracleRepositoryDAO;
import it.cnr.helpdesk.StateMachineManagement.dao.OracleStateMachineDAO;
import it.cnr.helpdesk.StatisticsManagement.dao.OracleStatisticsDAO;
import it.cnr.helpdesk.security.dao.OracleSecuritySettingsDAO;
import it.cnr.helpdesk.AttachmentManagement.dao.OracleAttachmentDAO;
import it.cnr.helpdesk.MailParserManagement.dao.OracleMailParserDAO;
import it.cnr.helpdesk.MessageManagement.dao.OracleMessageDAO;
/**
 * <p>Title: OIL - Online Interactive heLpdesk</p>
 * <p>Description: A Web Based Help Desk Tool</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: OIL</p>
 * @author Andrea Bei, Roberto Puccinelli
 * @version 1.0
 */

public class OracleDAOFactory extends DAOFactory {
  public FaqDAO getFaqDAO() {
    return new OracleFaqDAO();
  }
  public ProblemDAO getProblemDAO() {
   return new OracleProblemDAO();
  }
  public ApplicationSettingsDAO getApplicationSettingsDAO() {
   return new OracleApplicationSettingsDAO();
  }
  public UserDAO getUserDAO() {
   return new OracleUserDAO();
  }
  public CategoryDAO getCategoryDAO() {
   return new OracleCategoryDAO();
  }
  public StatisticsDAO getStatisticsDAO() {
   return new OracleStatisticsDAO();
  }
  public SecuritySettingsDAO getSecuritySettingsDAO(){
  	return new OracleSecuritySettingsDAO();
  }
  public AttachmentDAO getAttachmentDAO(){
  	return new OracleAttachmentDAO();
  }
  public MessageDAO getMessageDAO(){
  	return new OracleMessageDAO();
  }
  public UODAO getUODAO(){
      return new OracleUODAO();
  }
  public StateMachineDAO getStateMachineDAO(){
      return new OracleStateMachineDAO();
  }
  public EventDAO getEventDAO(){
    return new OracleEventDAO();
  }
/* (non-Javadoc)
 * @see it.cnr.helpdesk.dao.DAOFactory#getRepositoryDAO()
 */
public RepositoryDAO getRepositoryDAO() {
	return new OracleRepositoryDAO();
}
	public MailParserDAO getMailParserDAO(){
		return new OracleMailParserDAO();
	}
}

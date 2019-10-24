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
import it.cnr.helpdesk.ApplicationSettingsManagement.dao.PostgresApplicationSettingsDAO;
import it.cnr.helpdesk.EventManagement.dao.PostgresEventDAO;
import it.cnr.helpdesk.FaqManagement.dao.PostgresFaqDAO;
import it.cnr.helpdesk.ProblemManagement.dao.PostgresProblemDAO;
import it.cnr.helpdesk.UOManagement.dao.OracleUODAO;
import it.cnr.helpdesk.UOManagement.dao.PostgresUODAO;
import it.cnr.helpdesk.UserManagement.dao.PostgresUserDAO;
import it.cnr.helpdesk.security.dao.PostgresSecuritySettingsDAO;
import it.cnr.helpdesk.CategoryManagement.dao.PostgresCategoryDAO;
import it.cnr.helpdesk.StateMachineManagement.dao.OracleStateMachineDAO;
import it.cnr.helpdesk.StateMachineManagement.dao.PostgresStateMachineDAO;
import it.cnr.helpdesk.StatisticsManagement.dao.PostgresStatisticsDAO;
import it.cnr.helpdesk.AttachmentManagement.dao.PostgresAttachmentDAO;
import it.cnr.helpdesk.MailParserManagement.dao.PostgresMailParserDAO;
import it.cnr.helpdesk.MessageManagement.dao.PostgresMessageDAO;
import it.cnr.helpdesk.RepositoryManagement.dao.PostgresRepositoryDAO;


/**
 * <p>Title: OIL - Online Interactive heLpdesk</p>
 * <p>Description: A Web Based Help Desk Tool</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: OIL</p>
 * @author Andrea Bei, Roberto Puccinelli
 * @version 1.0
 */

public class PostgresDAOFactory extends DAOFactory {
  public FaqDAO getFaqDAO() {
    return new PostgresFaqDAO();
  }
  public ProblemDAO getProblemDAO() {
    return new PostgresProblemDAO();
  }
  public ApplicationSettingsDAO getApplicationSettingsDAO() {
   return new PostgresApplicationSettingsDAO();
  }
  public UserDAO getUserDAO() {
   return new PostgresUserDAO();
  }
  public CategoryDAO getCategoryDAO() {
   return new PostgresCategoryDAO();
  }
  public StatisticsDAO getStatisticsDAO() {
   return new PostgresStatisticsDAO();
  }
  public SecuritySettingsDAO getSecuritySettingsDAO(){
  	return new PostgresSecuritySettingsDAO();
  }
  public AttachmentDAO getAttachmentDAO() {
  	return new PostgresAttachmentDAO();
  }
  public MessageDAO getMessageDAO() {
  	return new PostgresMessageDAO();
  }
  public UODAO getUODAO(){
      return new PostgresUODAO();
  }
  public StateMachineDAO getStateMachineDAO(){
      return new PostgresStateMachineDAO();
  }
  public EventDAO getEventDAO(){
    return new PostgresEventDAO();
  }
  public RepositoryDAO getRepositoryDAO(){
	    return new PostgresRepositoryDAO();
	  }
  public MailParserDAO getMailParserDAO(){
  	return new PostgresMailParserDAO();
  }
}

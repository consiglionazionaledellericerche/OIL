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
/**
 * <p>Title: OIL - Online Interactive heLpdesk</p>
 * <p>Description: A Web Based Help Desk Tool</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: OIL</p>
 * @author Andrea Bei, Roberto Puccinelli
 * @version 1.0
 */

public abstract class DAOFactory {
  public abstract ProblemDAO getProblemDAO();
  public abstract CategoryDAO getCategoryDAO();
  public abstract FaqDAO getFaqDAO();
  public abstract StatisticsDAO getStatisticsDAO();
  public abstract UserDAO getUserDAO();
  public abstract ApplicationSettingsDAO getApplicationSettingsDAO();
  public abstract SecuritySettingsDAO getSecuritySettingsDAO();
  public abstract AttachmentDAO getAttachmentDAO();
  public abstract MessageDAO getMessageDAO();
  public abstract UODAO getUODAO();
  public abstract StateMachineDAO getStateMachineDAO();
  public abstract EventDAO getEventDAO();
  public abstract RepositoryDAO getRepositoryDAO();
  public abstract MailParserDAO getMailParserDAO();

  public static DAOFactory getDAOFactoryByProperties()
  {
    //Properties p=it.cnr.helpdesk.util.ApplicationProperties.getApplicationProperties();
    System.out.println("ATTENZIONE: it.cnr.oil.dbmstype="+System.getProperty("it.cnr.oil.dbmstype"));
   if (System.getProperty("it.cnr.oil.dbmstype").equals("ORACLE")) return new OracleDAOFactory();
   if (System.getProperty("it.cnr.oil.dbmstype").equals("POSTGRES")) return new PostgresDAOFactory();
   return new OracleDAOFactory(); //default
  }

  }

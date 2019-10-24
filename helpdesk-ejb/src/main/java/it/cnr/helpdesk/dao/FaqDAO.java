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


import it.cnr.helpdesk.FaqManagement.dto.*;


/**
 * <p>Title: OIL - Online Interactive heLpdesk</p>
 * <p>Description: A Web Based Help Desk Tool</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: OIL</p>
 * @author Andrea Bei, Roberto Puccinelli
 * @version 1.0
 */

public interface FaqDAO {
  public void createConnection(String instance) throws it.cnr.helpdesk.FaqManagement.exceptions.FaqDAOException ;
  public FaqDTO getFaqDetail() throws it.cnr.helpdesk.FaqManagement.exceptions.FaqDAOException ;
  public void setDescrizione(String descrizione) ;
  public void executeUpdate() throws it.cnr.helpdesk.FaqManagement.exceptions.FaqDAOException ;
  public Collection getFaqList(FaqDTO faqDTO, String instance) throws it.cnr.helpdesk.FaqManagement.exceptions.FaqDAOException ;
  public void setIdCategoria(int idCategoria) ;
  public int executeInsert() throws it.cnr.helpdesk.FaqManagement.exceptions.FaqDAOException ;
  public void executeDelete() throws it.cnr.helpdesk.FaqManagement.exceptions.FaqDAOException ;
  public void setTitolo(String titolo) ;
  public void setOriginatore(String originatore) ;
  public void setIdFaq(int idFaq) ;
  public void closeConnection() throws it.cnr.helpdesk.FaqManagement.exceptions.FaqDAOException ;
}

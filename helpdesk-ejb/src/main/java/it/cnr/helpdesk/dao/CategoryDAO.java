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


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.cnr.helpdesk.CategoryManagement.valueobjects.*;
import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryDAOException;


/**
 * <p>Title: OIL - Online Interactive heLpdesk</p>
 * <p>Description: A Web Based Help Desk Tool</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: OIL</p>
 * @author Andrea Bei, Roberto Puccinelli
 * @version 1.0
 */

public interface CategoryDAO {
  public void createConnection(String instance) throws CategoryDAOException ;
  public void setDescrizione(String descrizione) ;
  public void setNome(String nome) ;
  public CategoryValueObject allCategories() throws CategoryDAOException ;
  public Collection allCategoriesList() throws CategoryDAOException ;
  public Collection allCategoriesList(int idPadre) throws CategoryDAOException;
  public Collection allEnabledCategoriesList() throws CategoryDAOException ;
  public void assignToExpert(String login) throws CategoryDAOException ;
  public void setIdPadre(int idPadre) ;
  public void executeUpdate() throws CategoryDAOException;
  public void removeFromExpert(String login) throws CategoryDAOException ;
  public Collection allExpertCategoriesList(String expert) throws CategoryDAOException ;
  public CategoryValueObject getCategoryDetail(int category) throws CategoryDAOException ;
  public void setIdCategoria(int idCategoria) ;
  public int executeInsert() throws CategoryDAOException ;
  public void executeDelete() throws CategoryDAOException ;
  public void closeConnection() throws CategoryDAOException ;
  public void disable() throws CategoryDAOException;
  public void enable() throws CategoryDAOException;
  public boolean hasChildren(int id) throws CategoryDAOException;
  public boolean hasDisabledParent(int id) throws CategoryDAOException;
}

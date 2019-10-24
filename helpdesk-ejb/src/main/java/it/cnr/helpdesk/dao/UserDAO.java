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


import it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
import it.cnr.helpdesk.UserManagement.valueobjects.*;
import it.cnr.helpdesk.util.*;

public interface UserDAO {
  public void createConnection(String instance) throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException ;
  public String getEmail() ;
  public String getFamilyName() ;
  public String getFirstName() ;
  public String getLogin() ;
  public void setFirstName(String firstName) ;
  public String getStruttura() ;
  public void executeUpdate() throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException ;
  public void closeConnection() throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException ;
  public void setProfile(int profile) ;
  public void setEmail(String email) ;
  public void changeUserPassword() throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException ;
  public Collection allCategoryExperts(int category) throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException ;
  public Collection allCategoryValidators(int category) throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException ;
  public void setFamilyName(String familyName) ;
  public void executeDelete()  throws UserDAOException;
  public String getPassword() ;
  public String getUserPassword() throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException ;
  public void setPassword(String password) ;
  public UserValueObject isRegistered() throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException ;
  public String getTelefono() ;
  public void executeInsert() throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException ;
  public int getProfile() ;
  public void setStruttura(String struttura) ;
  public UserValueObject getUserDetail() throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException ;
  public PageByPageIterator allUsers(PageByPageIterator pbpIterator, UserValueObject uvo) throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException ;
  public void setTelefono(String telefono) ;
  public void setLogin(String login) ;
  public void disable() throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
  public void enable() throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
  public boolean existsAccount() throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
  public PageByPageIterator allEnabledUsers(PageByPageIterator pagebypageiterator, UserValueObject uservalueobject) throws it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
/**
 * @param mailStop
 */
public void setMailStop(String mailStop);
}

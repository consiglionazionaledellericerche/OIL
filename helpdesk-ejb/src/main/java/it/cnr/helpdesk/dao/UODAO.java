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
/*
 * Created on 24-ott-2005
 *


 */
package it.cnr.helpdesk.dao;

import it.cnr.helpdesk.UOManagement.exceptions.UODAOException;
import it.cnr.helpdesk.UOManagement.valueobjects.UOValueObject;

import java.util.Collection;

/**
 * @author Aldo Stentella Liberati
 *
 */
public interface UODAO {
    public void createConnection(String instance) throws UODAOException ;
    public void closeConnection() throws UODAOException ;
    public Collection getStructures() throws UODAOException;
	public Collection getAllStructures() throws UODAOException;
	public UOValueObject getOneStructure(String cod) throws UODAOException;
    public String executeInsert(UOValueObject vo) throws UODAOException;
    public void executeUpdate(UOValueObject vo) throws UODAOException;
    public void executeDelete(UOValueObject vo) throws UODAOException;
    public void enabled(String cod) throws UODAOException;
    public void disabled(String cod) throws UODAOException;
    
}

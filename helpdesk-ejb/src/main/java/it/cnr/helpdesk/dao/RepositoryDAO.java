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
 * Created on 25-gen-2007
 *
 */
package it.cnr.helpdesk.dao;

import it.cnr.helpdesk.RepositoryManagement.exceptions.RepositoryDAOException;
import it.cnr.helpdesk.RepositoryManagement.valueobjects.RepositoryValueObject;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Collection;

/**
 * @author Aldo Stentella Liberati
 *
 */
public interface RepositoryDAO {
	public void createConnection(String instance) throws RepositoryDAOException;
	public void closeConnection() throws RepositoryDAOException; 
	public void addRow(RepositoryValueObject rvo) throws RepositoryDAOException ;
	public void executeDelete(RepositoryValueObject rvo) throws RepositoryDAOException;
	public boolean findDocument(RepositoryValueObject rvo) throws RepositoryDAOException;
    public InputStream getBlobFile(RepositoryValueObject rvo) throws RepositoryDAOException;
    public Collection fetchRows(RepositoryValueObject rvo) throws RepositoryDAOException;
    public OutputStream executeInsert(RepositoryValueObject rvo) throws RepositoryDAOException;
	public void writeFile(RepositoryValueObject rvo) throws RepositoryDAOException;
    public void updateAttachment(RepositoryValueObject rvo) throws RepositoryDAOException;
	public Connection getConnection();
}

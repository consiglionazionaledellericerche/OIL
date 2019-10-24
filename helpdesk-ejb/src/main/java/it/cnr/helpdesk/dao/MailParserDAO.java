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
 * Created on 17-giu-2005
 *


 */
package it.cnr.helpdesk.dao;

/**
 * @author aldo stentella
 *
 */

import it.cnr.helpdesk.MailParserManagement.exceptions.MailParserDAOException;
import it.cnr.helpdesk.MailParserManagement.valueobjects.MailComponent;


public interface MailParserDAO {

	public void createConnection(String instance) throws MailParserDAOException;
	public void closeConnection() throws MailParserDAOException;
	public int executeInsert(MailComponent mail) throws MailParserDAOException;
	public MailComponent getRow(Integer id) throws MailParserDAOException;
	public void executeUpdate(MailComponent mail) throws MailParserDAOException;
	public MailComponent getByIdSegnalazione(long segnalazione) throws MailParserDAOException;
	
}

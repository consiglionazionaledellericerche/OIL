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
 * @author Roberto Puccinelli
 *


 */
import java.util.Collection;

import it.cnr.helpdesk.MessageManagement.exceptions.MessageDAOException;
import it.cnr.helpdesk.MessageManagement.valueobjects.MessageValueObject;

public interface MessageDAO {
	public MessageValueObject getMessage(int id_msg) throws MessageDAOException;
	public Collection getMessages() throws MessageDAOException;
	public Collection getAllMessages(boolean filter) throws MessageDAOException;
	public int executeInsert(MessageValueObject mvo) throws MessageDAOException;
	public void executeDelete(int id_msg) throws MessageDAOException;
	public void executeUpdate(MessageValueObject mvo) throws MessageDAOException;
	public void enable(int id_msg) throws MessageDAOException;
	public void disable(int id_msg) throws MessageDAOException;
	public void createConnection(String instance) throws MessageDAOException;
	public void closeConnection() throws MessageDAOException;
	
}

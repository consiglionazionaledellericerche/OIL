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
 * Created on 8-giu-2005
 *


 */
package it.cnr.helpdesk.dao;

import it.cnr.helpdesk.exceptions.HelpDeskDAOException;
import it.cnr.helpdesk.security.exceptions.SecurityDAOException;
import java.util.HashMap;

/**
 * @author Roberto Puccinelli
 *


 */
public interface SecuritySettingsDAO {
	public HashMap getSecuritySettings() throws SecurityDAOException ;
	public void createConnection (String instance)throws SecurityDAOException;
	public void closeConnection ()throws SecurityDAOException;
}

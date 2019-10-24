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
 */

package it.cnr.helpdesk.UOManagement.exceptions;

import it.cnr.helpdesk.exceptions.HelpDeskDAOException;

/**
 * @author Aldo Stentella Liberati
 */
public class UODAOException extends HelpDeskDAOException {

    public UODAOException() {
        super();
    }

    public UODAOException(String arg0) {
        super(arg0);
    }

    public UODAOException(String s1, String s2){
        super(s1, s2);
    }
}
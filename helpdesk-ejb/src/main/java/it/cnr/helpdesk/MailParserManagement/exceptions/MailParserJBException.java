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

package it.cnr.helpdesk.MailParserManagement.exceptions;

import it.cnr.helpdesk.exceptions.HelpDeskJBException;

/**
 * @author astentella
 *
 */
public class MailParserJBException extends HelpDeskJBException {

	/**
	 * 
	 */
	public MailParserJBException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 */
	public MailParserJBException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 * @param s1
	 */
	public MailParserJBException(String s, String s1) {
		super(s, s1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public MailParserJBException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param msgKey
	 * @param args
	 */
	public MailParserJBException(String msgKey, Object[] args) {
		super(msgKey, args);
		// TODO Auto-generated constructor stub
	}

}

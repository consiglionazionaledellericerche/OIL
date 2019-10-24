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
 * Created on 27-lug-2006
 *
 */
package it.cnr.helpdesk.EventManagement.exceptions;

import it.cnr.helpdesk.exceptions.HelpDeskJBException;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class EventJBException extends HelpDeskJBException {

	/**
	 * 
	 */
	public EventJBException() {
		super();
	}

	/**
	 * @param s
	 */
	public EventJBException(String s) {
		super(s);
		
	}

	/**
	 * @param s
	 * @param s1
	 */
	public EventJBException(String s, String s1) {
		super(s, s1);
	}

	/**
	 * @param cause
	 */
	public EventJBException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param msgKey
	 * @param args
	 */
	public EventJBException(String msgKey, Object[] args) {
		super(msgKey, args);
	}

}

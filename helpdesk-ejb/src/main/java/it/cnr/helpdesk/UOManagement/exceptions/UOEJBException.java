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
 * Created on 23-ott-2008
 */
package it.cnr.helpdesk.UOManagement.exceptions;
import it.cnr.helpdesk.exceptions.HelpDeskEJBException;
/**
 * @author Marco Trischitta
 */
public class UOEJBException extends HelpDeskEJBException {
	
	public UOEJBException() {
		
	}
	
	public UOEJBException(String s1) {
		super(s1);
	}
	
	public UOEJBException(String s2, String s3) {
		super(s2, s3);
	}
	
	public String getMessage() {
        return super.getMessage();
    }

    public void setUserMessage(String s4)  {
        super.setUserMessage(s4);
    }

    public String getUserMessage() {
        return super.getUserMessage();
    }
	
}
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

 

package it.cnr.helpdesk.FaqManagement.exceptions;

import it.cnr.helpdesk.exceptions.HelpDeskEJBException;

public class FaqEJBException extends HelpDeskEJBException
{

    public FaqEJBException()
    {
    }

    public FaqEJBException(String s)
    {
        super(s);
    }

    public FaqEJBException(String s, String s1)
    {
        super(s, s1);
    }

    public String getMessage()
    {
        return super.getMessage();
    }

    public void setUserMessage(String s)
    {
        super.setUserMessage(s);
    }

    public String getUserMessage()
    {
        return super.getUserMessage();
    }
}
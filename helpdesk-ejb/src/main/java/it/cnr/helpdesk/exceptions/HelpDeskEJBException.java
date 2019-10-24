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

 

package it.cnr.helpdesk.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJBException;

// Referenced classes of package it.cnr.helpdesk.exceptions:
//            HelpDeskExceptionInterface

public class HelpDeskEJBException extends EJBException
    implements HelpDeskExceptionInterface
{

    public HelpDeskEJBException()
    {
    }

    public HelpDeskEJBException(String s)
    {
        super(s);
    }

    public HelpDeskEJBException(String s, String s1)
    {
        super(s);
        userMessage = s1;
    }

    public String getMessage()
    {
        return super.getMessage();
    }

    public void setUserMessage(String s)
    {
        userMessage = s;
    }

    public String getUserMessage()
    {
        return userMessage;
    }

    private String userMessage;
    
    /* inizio modifiche del 20/07/06 */
    
    /**
     *  An optional nested exception used to provide the ability to encapsulate
     *  a lower-level exception to provide more detailed context information
     *  concerning the exact cause of the exception.
     */
    protected Throwable rootCause = null;
    
    /**
     *  Holds a collection of exceptions added to this exception, for use when the
     *  application desires to return multiple exception conditions at once. For
     *  instance, if the application must evaluate and validate multiple pieces
     *  of data before performing an operation, it might be beneficial to
     *  validate all of the data and collect all resulting exceptions to be
     *  passed back at once.
     */
    private Collection exceptions = new ArrayList();
    
    /**
     * The key to the bundle message.
     */
    private String messageKey = null;
    
    /**
     * An optional array of arguments to use with the bundle message.
     */
    private Object[] messageArgs = null;
    
    /**
     *  A Constructor that takes a root cause throwable.
     */
    public HelpDeskEJBException( Throwable cause ) {
        this.rootCause = cause;
    }
    
    
    public HelpDeskEJBException( String msgKey, Object[] args ) {
        this.messageKey = msgKey;
        this.messageArgs = args;
    }
    
    /**
     *  Retrieve the collection of exceptions.
     */
    public Collection getExceptions() {
        return exceptions;
    }
    
    /**
     * Add an exception to the exception collection.
     */
    public void addException( Exception ex ) {
        exceptions.add( ex );
    }
    
    /**
     * Set the key to the bundle.
     */
    public void setMessageKey( String key ) {
        this.messageKey = key;
    }
    
    /**
     * Retrieve the message bundle key.
     */
    public String getMessageKey() {
        return messageKey;
    }
    
    /**
     * Set an object array that can be used for parametric replacement.
     */
    public void setMessageArgs( Object[] args ) {
        this.messageArgs = args;
    }
    
    /**
     * Retrieve the optional arguments.
     */
    public Object[] getMessageArgs() {
        return messageArgs;
    }
    
    /**
     *  Set a nested, encapsulated exception to provide more low-level detailed
     *  information to the client.
     */
    public void setRootCause( Throwable anException ) {
        rootCause = anException;
    }
    
    /**
     *  Return the root cause exception, if one exists.
     */
    public Throwable getRootCause() {
        return rootCause;
    }
    
    /**
     *  Print both the normal and rootCause stack traces.
     */
    public void printStackTrace() {
        printStackTrace( System.err );
    }
    
    /**
     *  Print both the normal and rootCause stack traces.
     */
    public void printStackTrace( PrintStream outStream ) {
        printStackTrace( new PrintWriter( outStream ) );
    }
    
    /**
     *  Print both the normal and rootCause stack traces.
     */
    public void printStackTrace( PrintWriter writer ) {
        super.printStackTrace( writer );
        if( getRootCause() != null ) {
            getRootCause().printStackTrace( writer );
        }
        writer.flush();
    }
}
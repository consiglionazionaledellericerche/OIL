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

 

package it.cnr.helpdesk.javabeans;

import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.TaskException;
import it.cnr.helpdesk.StateMachineManagement.tasks.Task;
import it.cnr.helpdesk.exceptions.HelpDeskJBException;

import java.text.DateFormat;
import java.util.*;
import javax.naming.*;

public class HelpDeskJB
{

    public HelpDeskJB()
    {
    }

    public void log(Object obj, String s)
    {
        Date date = new Date();
        DateFormat dateformat = DateFormat.getDateTimeInstance();
        String s1 = dateformat.format(date);
        String s2 = obj.getClass().getName();
        System.out.println();
        System.out.println(s1 + " - [" + s2 + "]");
        System.out.println(s);
        System.out.println();
    }

    public void log(String s)
    {
        Date date = new Date();
        DateFormat dateformat = DateFormat.getDateTimeInstance();
        String s1 = dateformat.format(date);
        System.out.println();
        System.out.println(s1);
        System.out.println(s);
        System.out.println();
    }

    public Context getInitialContext() throws NamingException {
    	InitialContext initialContext=null;
    	initialContext = new InitialContext();
    	/* comment by Stentella: codice sostituito poiche' l'associazione del JNDI ad un port statico era troppo vincolante
    	Hashtable props = new Hashtable();
    	String initialContextFactory="org.jnp.interfaces.NamingContextFactory";
    	String providerURL="jnp://127.0.0.1:1099";
    	try {
    		props.put(InitialContext.INITIAL_CONTEXT_FACTORY,initialContextFactory);
    		props.put(InitialContext.PROVIDER_URL, providerURL);

    		// This establishes the security for authorization/authentication
    		// props.put(InitialContext.SECURITY_PRINCIPAL,"username");
    		// props.put(InitialContext.SECURITY_CREDENTIALS,"password");

    		initialContext = new InitialContext(props);
    	} catch(NamingException namingexception) {
            log("We were unable to get a connection to the JBoss server at " + initialContextFactory);
            log("Please make sure that the server is running.");
            throw namingexception;
        }
        */
		return initialContext;

    	/*
        InitialContext initialcontext = null;
        int i = 1;
        if(i == 1)
        {
            String s = "t3://localhost:7601";
            String s1 = null;
            String s2 = null;
            try
            {
                Properties properties = new Properties();
                properties.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
                properties.put("java.naming.provider.url", s);
                if(s1 != null)
                {
                    log("user: " + s1);
                    properties.put("java.naming.security.principal", s1);
                    if(s2 == null)
                        s2 = "";
                    properties.put("java.naming.security.credentials", s2);
                }
                initialcontext = new InitialContext(properties);
            }
            catch(NamingException namingexception)
            {
                log("We were unable to get a connection to the WebLogic server at " + s);
                log("Please make sure that the server is running.");
                throw namingexception;
            }
        } else
        if(i == 2)
            initialcontext = new InitialContext();
        return initialcontext; */
    }

    public void executeTasks(ArrayList tasks, EventValueObject evo) throws HelpDeskJBException {
    	Iterator i=tasks.iterator();
    	while(i.hasNext()) {
    			((Task)i.next()).doAction(evo);
    	}
    }
    
    public void executeTasks(ArrayList tasks, ProblemValueObject pvo) throws HelpDeskJBException {
    	Iterator i=tasks.iterator();
    	while(i.hasNext()) {
    			((Task)i.next()).doAction(pvo);
    	}
    }
    
    private static final int WEBLOGIC_NAMECONTEXT = 1;
    private static final int J2EESERVER_NAMECONTEXT = 2;
    private static final String WEBLOGIC_CONTEXT_ROOT = "";
    private static final String J2EESERVER_CONTEXT_ROOT = "java:";
    public static final String CONTEXT_ROOT = "";
}
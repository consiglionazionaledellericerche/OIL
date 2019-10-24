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
package it.cnr.helpdesk.MailManagement.javabeans;

import it.cnr.helpdesk.javabeans.HelpDeskJB;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.JMSException;
import javax.jms.QueueSender;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.ObjectMessage;
import javax.naming.*;

import java.util.Hashtable;
import it.cnr.helpdesk.MailManagement.valueobjects.*;

public class MailManagement extends HelpDeskJB {
	
	private InitialContext getContext() throws NamingException {
		InitialContext initialContext=null;
    	initialContext = new InitialContext();
    	/*
		Hashtable props =  new Hashtable();

		props.put(InitialContext.INITIAL_CONTEXT_FACTORY,
		  "org.jnp.interfaces.NamingContextFactory");
		props.put(InitialContext.PROVIDER_URL, "jnp://127.0.0.1:1299");
		InitialContext initialContext=new InitialContext(props);
		*/
		return initialContext;

	}
	
	public void sendMail(MailItem mailItem) {
		QueueConnectionFactory factory=null;
		Queue queue=null;
		try {
			InitialContext ctx=getContext();
			factory=(QueueConnectionFactory) ctx.lookup("ConnectionFactory");
			queue=(Queue) ctx.lookup("java:/jms/queue/mailnotificationservice");
		} catch (NamingException e) {
			System.out.println("Problema con il servizio di naming.");
			e.printStackTrace();
			return;
		}
		try {
			QueueConnection connection=factory.createQueueConnection();
			QueueSession session=connection.createQueueSession(true,1);
			QueueSender sender=session.createSender(queue);
			ObjectMessage message=session.createObjectMessage();
			message.setObject(mailItem);
			sender.send(message);
			session.commit();
			session.close();
			connection.close();
		} catch (JMSException e) {
			System.out.println("Problema con il servizio JMS.");
			e.printStackTrace();
			return;
		}
		
	}
	
}

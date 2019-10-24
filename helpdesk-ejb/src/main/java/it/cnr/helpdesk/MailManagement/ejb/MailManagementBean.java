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
package it.cnr.helpdesk.MailManagement.ejb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.MessageDrivenBean;
import javax.jms.MessageListener;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import it.cnr.helpdesk.AttachmentManagement.exceptions.AttachmentDAOException;
import it.cnr.helpdesk.MailManagement.valueobjects.MailItem;
import it.cnr.helpdesk.dao.AttachmentDAO;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.util.Mailer;

/**
 *
 * <!-- begin-user-doc --> You can insert your documentation for '<em><b>MailManagementBean</b></em>'. <!-- end-user-doc --> *
 <!--  begin-lomboz-definition -->
 <?xml version="1.0" encoding="UTF-8"?>
 <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz">
 <lomboz:message-driven>
 <lomboz:messageDrivenEjb>
 <j2ee:display-name>MailManagementBean</j2ee:display-name>
 <j2ee:ejb-name>MailManagement</j2ee:ejb-name>
 <j2ee:ejb-class>it.cnr.helpdesk.MailManagement.ejb.MailManagementBean</j2ee:ejb-class>
 <j2ee:transaction-type>Container</j2ee:transaction-type>
 <j2ee:message-destination-type>javax.jms.Queue</j2ee:message-destination-type>
 </lomboz:messageDrivenEjb>
 <lomboz:messageDestination>
 <j2ee:message-destination-name>queue/mailnotificationservice</j2ee:message-destination-name>
 </lomboz:messageDestination>
 </lomboz:message-driven>
 </lomboz:EJB>
 <!--  end-lomboz-definition -->
 *
 *
 * <!-- begin-xdoclet-definition -->
 * @ejb.bean name="MailManagementBean" 
 *     acknowledge-mode="Auto-acknowledge"
 *     destination-type="javax.jms.Queue"
 *     
 *     transaction-type="Container"
 *     destination-jndi-name="queue/mailnotificationservice"
 * 
 *
 *--
 * Server Runtime Specific Tags
 * If you are not using a specific runtime, you can safely remove the tags below.
 * @jonas.message-driven-destination jndi-name="queue/mailnotificationservice"
 * @jboss.destination-jndi-name name="queue/mailnotificationservice"
 *
 *--
 * <!-- end-xdoclet-definition -->
 * @generated
 **/
public class MailManagementBean implements javax.ejb.MessageDrivenBean, javax.jms.MessageListener {

  /** 
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * The context for the message-driven bean, set by the EJB container. 
   * @generated
   */
  private javax.ejb.MessageDrivenContext messageContext = null;

  /** 
   * Required method for container to set context.
   * @generated 
   */
  public void setMessageDrivenContext(
      javax.ejb.MessageDrivenContext messageContext)
      throws javax.ejb.EJBException {
    this.messageContext = messageContext;
  }

  /** 
   * Required creation method for message-driven beans. 
   *
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   *
   * <!-- begin-xdoclet-definition -->
   * @ejb.create-method 
   * <!-- end-xdoclet-definition -->
   * @generated
   */
  public void ejbCreate() {
    //no specific action required for message-driven beans 
  }

  /** 
   * Required removal method for message-driven beans. 
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void ejbRemove() {
    messageContext = null;
  }

  /** 
   * This method implements the business logic for the EJB. 
   * 
   * <p>Make sure that the business logic accounts for asynchronous message processing. 
   * For example, it cannot be assumed that the EJB receives messages in the order they were 
   * sent by the client. Instance pooling within the container means that messages are not 
   * received or processed in a sequential order, although individual onMessage() calls to 
   * a given message-driven bean instance are serialized. 
   * 
   * <p>The <code>onMessage()</code> method is required, and must take a single parameter 
   * of type javax.jms.Message. The throws clause (if used) must not include an application 
   * exception. Must not be declared as final or static. 
   *
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void onMessage(javax.jms.Message message) {
    // begin-user-code
    System.out.println("Message Driven Bean got message " + message);
    MailItem mailItem=null;
    try {
    	mailItem =  (MailItem) ((javax.jms.ObjectMessage) message).getObject();
    } catch (javax.jms.JMSException jmse) {
    	
    }
    Mailer mailer=new Mailer(mailItem.getInstance());
    Collection receivers = mailItem.getReceivers();
    //se non ci sono destinatari non inviare la mail (solleverebbe un errore: "javax.mail.SendFailedException: No recipient addresses" )
    if(receivers.size()>0){								
    	Iterator iterator=receivers.iterator();
    	while (iterator.hasNext()){
    		mailer.addTo((String) iterator.next());
    	}
    	mailer.setSubject(mailItem.getSubject());
    	mailer.setContent(mailItem.getContent());
    	BodyPart messageBodyPart1 = new MimeBodyPart();
    	try {
			messageBodyPart1.setContent(mailItem.getContent(), "text/html; charset=utf-8");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart( messageBodyPart1 );
			if(mailItem.hasAttachment()){
				byte[] bytes= mailItem.getAttachment();				
				BodyPart attaPart = new MimeBodyPart();
				DataSource source = new ByteArrayDataSource(bytes,"www/unknown");
				attaPart.setDataHandler(new DataHandler(source));
				attaPart.setFileName(mailItem.getFileName());
				multipart.addBodyPart(attaPart);
			}
			mailer.setContent(multipart);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    	mailer.send();    	
    }
  }
}

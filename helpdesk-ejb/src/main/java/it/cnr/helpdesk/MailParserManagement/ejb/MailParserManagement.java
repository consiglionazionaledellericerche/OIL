/*
 * Generated by XDoclet - Do not edit!
 */
package it.cnr.helpdesk.MailParserManagement.ejb;

/**
 * Remote interface for MailParserManagement.
 * @generated 
 * @lomboz generated
 */
public interface MailParserManagement
   extends javax.ejb.EJBObject
{

   public int parseMail( java.lang.String contextPath, java.lang.String instance )
      throws it.cnr.helpdesk.MailParserManagement.exceptions.MailParserEJBException, java.rmi.RemoteException;

   public int confirmMail( java.lang.String id,java.lang.String cod, java.lang.String instance )
      throws it.cnr.helpdesk.MailParserManagement.exceptions.MailParserEJBException, java.rmi.RemoteException;

   public int TicketVerify( java.lang.String id,java.lang.String cod, java.lang.String instance )
      throws it.cnr.helpdesk.MailParserManagement.exceptions.MailParserEJBException, java.rmi.RemoteException;

   public long executeInsert( it.cnr.helpdesk.MailParserManagement.valueobjects.MailComponent mail,java.util.ArrayList attachments, java.lang.String instance )
      throws it.cnr.helpdesk.MailParserManagement.exceptions.MailParserEJBException, java.rmi.RemoteException;

   public void attachmentInsert(long sid, java.io.File file, java.lang.String instance) 
		   throws it.cnr.helpdesk.MailParserManagement.exceptions.MailParserEJBException, java.rmi.RemoteException;
   
   public long problemInsert( it.cnr.helpdesk.MailParserManagement.valueobjects.MailComponent mail, it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject pvo, java.util.List<java.io.File> attachments, java.lang.String instance )
		   throws it.cnr.helpdesk.MailParserManagement.exceptions.MailParserEJBException, java.rmi.RemoteException;
   
   public void executeAppend(long sid,int newState,java.lang.String body,java.util.ArrayList attachments, java.lang.String instance )
      throws it.cnr.helpdesk.MailParserManagement.exceptions.MailParserEJBException, java.rmi.RemoteException;

   public void changeState(long sid,int newState,java.lang.String body,java.lang.String originatore, java.lang.String instance )
		   throws it.cnr.helpdesk.MailParserManagement.exceptions.MailParserEJBException, java.rmi.RemoteException;
   
   public void changeCategory(long sid, int category, String categoryName, String body, String originatore, String instance)
      throws it.cnr.helpdesk.MailParserManagement.exceptions.MailParserEJBException, java.rmi.RemoteException;
}
/*
 * Generated by XDoclet - Do not edit!
 */
package it.cnr.helpdesk.FaqManagement.ejb;

/**
 * Remote interface for FaqManagement.
 * @generated 
 * @lomboz generated
 */
public interface FaqManagement
   extends javax.ejb.EJBObject
{

   public it.cnr.helpdesk.FaqManagement.dto.FaqDTO getFaqDetail( int i , java.lang.String instance )
      throws it.cnr.helpdesk.FaqManagement.exceptions.FaqEJBException, java.rmi.RemoteException;

   public java.util.Collection getFaqList( it.cnr.helpdesk.FaqManagement.dto.FaqDTO faqdto , java.lang.String instance )
      throws it.cnr.helpdesk.FaqManagement.exceptions.FaqEJBException, java.rmi.RemoteException;

   public void insertFaq( it.cnr.helpdesk.FaqManagement.dto.FaqDTO faqdto , java.lang.String instance )
      throws it.cnr.helpdesk.FaqManagement.exceptions.FaqEJBException, java.rmi.RemoteException;

   public void updateFaq( it.cnr.helpdesk.FaqManagement.dto.FaqDTO faqdto , java.lang.String instance )
      throws it.cnr.helpdesk.FaqManagement.exceptions.FaqEJBException, java.rmi.RemoteException;

   public void removeFaq( int i , java.lang.String instance )
      throws it.cnr.helpdesk.FaqManagement.exceptions.FaqEJBException, java.rmi.RemoteException;

}

/*
 * Generated by XDoclet - Do not edit!
 */
package it.cnr.helpdesk.MessageManagement.ejb;

/**
 * Home interface for MessageManagement.
 * @generated 
 * @lomboz generated
 */
public interface MessageManagementHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/MessageManagement";
   public static final String JNDI_NAME="comp/env/ejb/MessageManagement";

   public it.cnr.helpdesk.MessageManagement.ejb.MessageManagement create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
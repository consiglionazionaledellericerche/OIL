/*
 * Generated by XDoclet - Do not edit!
 */
package it.cnr.helpdesk.StateMachineManagement.ejb;

/**
 * Home interface for StateMachineManagement.
 * @generated 
 * @lomboz generated
 */
public interface StateMachineManagementHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/StateMachineManagement";
   public static final String JNDI_NAME="comp/env/ejb/StateMachineManagement";

   public it.cnr.helpdesk.StateMachineManagement.ejb.StateMachineManagement create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}

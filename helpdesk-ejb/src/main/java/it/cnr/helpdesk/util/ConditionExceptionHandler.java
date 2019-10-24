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
 * Created on 19-lug-2006
 *
 */
package it.cnr.helpdesk.util;

import it.cnr.helpdesk.StateMachineManagement.exceptions.ConditionException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class ConditionExceptionHandler extends ExceptionHandler {

	  public ActionForward execute(Exception ex,
	                                  ExceptionConfig config,
	                                  ActionMapping mapping,
	                                  ActionForm formInstance,
	                                  HttpServletRequest request,
	                                  HttpServletResponse response)
	    throws ServletException {
	    ActionForward forward = null;
	    ActionMessage error = null;
	    String property = null;

	    /* Get the path for the forward either from the exception element
	    * or from the input attribute.
	    */
	    String path = null;
	    if(config.getPath() != null) {
	      path = config.getPath();
	    }
	    else {
	      path = mapping.getInput();
	    }

	    // Construct the forward object
	    forward = new ActionForward(path);

	    /* Figure out what type of exception has been thrown. The Struts
	    * AppException is not being used in this example.
	    */
	    if(ex instanceof ConditionException) {
	    	System.out.println("********eccezione catturata dall'handler");

	      // This is the specialized behavior
	      ConditionException ce = (ConditionException)ex;
	      String messageKey = ce.getMessageKey();
	      Object[] exArgs = ce.getMessageArgs();
	      if(exArgs != null && exArgs.length > 0) {

	        // If there were args provided, use them in the ActionError
	        error = new ActionMessage(messageKey, exArgs);
	      }
	      else {

	        // Create an ActionError without any arguments
	        error = new ActionMessage(messageKey);
	      }

	    }
	    else {
	    	System.out.println("********eccezione NON catturata dall'handler");
	      error = new ActionMessage(config.getKey());
	      property = error.getKey();
	    }

	    // Store the ActionError into the proper scope

	    // The storeException method is defined in the parent class
	    storeException(request, property, error, forward, config.getScope());
	    return forward;
	  }
}

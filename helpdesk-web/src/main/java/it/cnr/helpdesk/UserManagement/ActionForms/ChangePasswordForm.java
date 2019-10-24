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
 * Created on 25-mag-2005
 *


 */
package it.cnr.helpdesk.UserManagement.ActionForms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author Aldo Stentella Liberati
 *


 */
public class ChangePasswordForm extends ValidatorForm {

	private String oldpwd;
	private String newpwd;
	private String newpwd2;
	
	/**
	 * @return Returns the newpwd.
	 */
	public String getNewpwd() {
		return newpwd;
	}
	/**
	 * @param newpwd The newpwd to set.
	 */
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	/**
	 * @return Returns the newpwd2.
	 */
	public String getNewpwd2() {
		return newpwd2;
	}
	/**
	 * @param newpwd2 The newpwd2 to set.
	 */
	public void setNewpwd2(String newpwd2) {
		this.newpwd2 = newpwd2;
	}
	/**
	 * @return Returns the oldpwd.
	 */
	public String getOldpwd() {
		return oldpwd;
	}
	/**
	 * @param oldpwd The oldpwd to set.
	 */
	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}
	
	public void reset(ActionMapping action, HttpServletRequest request){
	    super.reset(action, request);
	    oldpwd=null;
		newpwd=null;
		newpwd2=null;
	}
}

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
 * Created on 26-mag-2005
 *


 */
package it.cnr.helpdesk.util;

import org.apache.struts.action.*;
import org.apache.struts.validator.*;
import org.apache.commons.validator.*;
import org.apache.commons.validator.util.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Aldo Stentella Liberati
 * 


 */
public class StrutsValidator {

	public static boolean validateTwoFields(Object bean, ValidatorAction va,
			Field field, ActionMessages messages, HttpServletRequest request) {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		String sProperty2 = field.getVarValue("secondProperty");
		String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!value.equals(value2)) {
					messages.add(field.getKey(), Resources.getActionMessage(request, va, field));

					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				messages.add(field.getKey(), Resources.getActionMessage(request, va, field));
				return false;
			}
		}

		return true;
	}

}
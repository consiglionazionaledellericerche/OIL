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
 * Created on 3-feb-2007
 *
 */
package it.cnr.helpdesk.RepositoryManagement.actions;

import it.cnr.helpdesk.RepositoryManagement.exceptions.RepositoryJBException;
import it.cnr.helpdesk.RepositoryManagement.javabeans.Repository;
import it.cnr.helpdesk.RepositoryManagement.valueobjects.RepositoryValueObject;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Aldo Stentella Liberati
 * 
 */
public class RepositoryUpdate2Action extends Action {
    private static Log log = LogFactory.getLog(RepositoryUpdate2Action.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws RepositoryJBException {
        log.warn("In execute method of RepositoryUpdate2Action");
        if ("GET".equals(request.getMethod())) {
    		HttpSession se = request.getSession(true);
    		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
            String id = (String) request.getParameter("id");
            String qbe = (String) request.getParameter("qbe");
            if (qbe!= null && qbe.startsWith("y")){
    			int anno = Integer.parseInt(id.substring(0,4));
    			int mese = Integer.parseInt(id.substring(4,6))-1;
    			String categoria = id.substring(6);
    			Calendar data = new GregorianCalendar();
    			SimpleDateFormat itForm = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
    			data.set(anno, mese, 1);
    			int last = data.getActualMaximum(Calendar.DAY_OF_MONTH);
    			String start = itForm.format(data.getTime());
    			data.set(anno, mese, last);
    			data.add(Calendar.DATE, 1);
    			String end = itForm.format(data.getTime());
				Repository rep = new Repository();
				String file = rep.createReport(categoria, start, end, instance);
				if(file.startsWith("---"))
					request.setAttribute("param","emptyReport");
				else
					request.setAttribute("param",file);
				return mapping.findForward("RepositoryQBE");
            } else if (id != null) {
            	Collection data;
            	Repository rbean =  new Repository();
            	RepositoryValueObject rvo = new RepositoryValueObject();
            	rvo.setMese(Integer.parseInt(id.substring(0,4)));
            	rvo.setCategoria(Integer.parseInt(id.substring(4)));
            	data = rbean.fetchRows(rvo, instance);
            	request.setAttribute("data", data);
            }
            
        }
        return mapping.findForward("RepositoryUpdate2");
    }
}
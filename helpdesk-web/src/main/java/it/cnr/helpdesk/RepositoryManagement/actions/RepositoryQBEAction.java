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
 * Created on 23-gen-2007
 *
 */
package it.cnr.helpdesk.RepositoryManagement.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryJBException;
import it.cnr.helpdesk.CategoryManagement.javabeans.Category;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.RepositoryManagement.exceptions.RepositoryJBException;
import it.cnr.helpdesk.RepositoryManagement.javabeans.Repository;
import it.cnr.helpdesk.RepositoryManagement.valueobjects.RepositoryValueObject;

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
public class RepositoryQBEAction extends Action {
    private static Log log = LogFactory.getLog(RepositoryQBEAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws CategoryJBException, ProblemJBException, RepositoryJBException {
        log.warn("In execute method of RepositoryQBEAction");
		HttpSession se = request.getSession(true);
		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        if ((request.getMethod()).equals("GET")){
        	Category category=new Category();
        	request.setAttribute("categs",  category.getAllEnabledCategory(instance));
            request.setAttribute("param","form");
            return mapping.findForward("RepositoryQBE");
        } else {			
			int anno = Integer.parseInt(request.getParameter("anno"));
			int mese = Integer.parseInt(request.getParameter("mese"));
			String categoria = request.getParameter("categoria");
			Calendar data = new GregorianCalendar();
			SimpleDateFormat itForm = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
			data.set(anno, mese, 1);
			int last = data.getActualMaximum(Calendar.DAY_OF_MONTH);
			String start = itForm.format(data.getTime());
			data.set(anno, mese, last);
			data.add(Calendar.DATE, 1);
			String end = itForm.format(data.getTime());
			if((new Date()).before(data.getTime())){
				request.setAttribute("param","futureReport");
				System.out.println("impossibile produrre report su mese corrente o sui futuri");
			} else {
				Repository rep = new Repository();
				String file = rep.createReport(categoria, start, end, instance);
				if(file.startsWith("---")){
					request.setAttribute("param","emptyReport");
				} else {
					request.setAttribute("param",file);
				}
			}
			/*RepositoryValueObject rvo = new RepositoryValueObject();
			rvo.setMese(anno*100+mese);
			*/
			return mapping.findForward("RepositoryQBE");
        }
    }
}

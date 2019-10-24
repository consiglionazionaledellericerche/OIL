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
 * Created on 8-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.actions;

import it.cnr.helpdesk.ProblemManagement.ActionForms.CambioCategoriaForm;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class CambioCategoriaAction extends Action {
    private static Log log = LogFactory.getLog(CambioCategoriaAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, ProblemJBException {
        log.warn("In execute method of CambioCategoriaAction");
        
        Problem problem = new Problem();
        HttpSession se=request.getSession(true);
        String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
        CambioCategoriaForm ccf = (CambioCategoriaForm)form;
        if ((request.getMethod()).equals("GET")) {
            String categoriaString=(String) request.getParameter("idCategoria");
            if (categoriaString!=null) {
                se.setAttribute("it.cnr.helpdesk.currentCategoria",categoriaString);
            }
            request.setAttribute("param", "form"); //visualizza il form
        } else {
            String categoriaString=ccf.getIdCategoria(); //request.getParameter("idCategoria");
            String nomeCategoria=ccf.getNomeCategoria(); //request.getParameter("nomeCategoria");
            Long idSegnalazione=new Long(ccf.getIdSegnalazione()); //request.getParameter("idSegnalazione"));
            int newCategoria=Integer.parseInt(categoriaString); 
            int oldCategoria=Integer.parseInt(se.getAttribute("it.cnr.helpdesk.currentCategoria").toString());
            String note=ccf.getNota(); //request.getParameter("nota");
            User u=(User) se.getAttribute("it.cnr.helpdesk.currentuser");
            ProblemValueObject pvo = problem.getProblemDetail(idSegnalazione.longValue(), instance);
            if (newCategoria!=oldCategoria) {	//la categoria è cambiato
            	EventValueObject evo = new EventValueObject();
                evo.setIdSegnalazione(idSegnalazione.longValue());
                evo.setOriginatoreEvento(u.getLogin());
                evo.setCategory(newCategoria);
                evo.setCategoryDescription(nomeCategoria);
                evo.setEventType(2);
                evo.setState(pvo.getStato());
                evo.setNote(note);
                evo.setTitle(pvo.getTitolo());
                evo.setDescription(pvo.getDescrizione());
                evo.setInstance(instance);
                problem.changeCategory(evo);
            }
            ccf.reset(mapping,request);
            request.setAttribute("param", "close"); //chiudi
        }
        return mapping.findForward("CambioCategoria");
    }
}

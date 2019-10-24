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
 * Created on 21-lug-2005
 *


 */
package it.cnr.helpdesk.ProblemManagement.actions;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.exceptions.SettingsJBException;
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
public class GetCreaSegnalazioneAction extends Action {
    private static Log log = LogFactory.getLog(GetCreaSegnalazioneAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SettingsJBException {
        log.warn("In execute method of GetCreaSegnalazioneAction");
        
        if ("GET".equals(request.getMethod())) {
            HttpSession se=request.getSession(true);
    		String instance = (String)se.getAttribute("it.cnr.helpdesk.instance");
            String defaultCategory=Settings.getDefaultCategory(instance);
            String descrizioneCategoria="";
            Vector<String> v = Settings.getId2DescriptionPriorityMapping(se.getAttribute("it.cnr.helpdesk.instance").toString());
            se.setAttribute("priorities",v);
            int idCategoria=0;
            if (defaultCategory!=null) {
                StringTokenizer st = new StringTokenizer(defaultCategory,":");
                if (st.hasMoreTokens()) idCategoria=Integer.parseInt(st.nextToken());
                if (st.hasMoreTokens()) descrizioneCategoria=st.nextToken();
                request.setAttribute("idCategoria",""+idCategoria);
                request.setAttribute("descrizioneCategoria",descrizioneCategoria);
            }
            return mapping.findForward("CreaSegnalazione");
        }
        return mapping.findForward("welcome");
    }
    
}

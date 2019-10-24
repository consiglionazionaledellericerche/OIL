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
 * Created on 5-lug-2006
 *
 */
package it.cnr.helpdesk.StateMachineManagement.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import it.cnr.helpdesk.EventManagement.exceptions.EventDAOException;
import it.cnr.helpdesk.MailManagement.javabeans.MailManagement;
import it.cnr.helpdesk.MailManagement.valueobjects.MailItem;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemDAOException;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.TaskException;
import it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.ProblemDAO;
import it.cnr.helpdesk.dao.UserDAO;
import it.cnr.helpdesk.util.MessageComposer;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class EmailExpertChangeTask extends Task {

	public void doAction(Object token) throws TaskException {
		EventValueObject evo = (EventValueObject)token;
		ProblemDAO problemdao = DAOFactory.getDAOFactoryByProperties().getProblemDAO();
		UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
	    UserValueObject userOrig = null;        // Originatore del problema
	    UserValueObject userAdm = null;        // Amministratore
	    UserValueObject userExp = null;       // Esperto
	    try {
	        problemdao.createConnection(evo.getInstance());
	        problemdao.setIdSegnalazione(evo.getIdSegnalazione());
			ProblemValueObject pvo = problemdao.getProblemDetail();
	        problemdao.closeConnection();
	        userdao.createConnection(evo.getInstance());
	        userdao.setLogin(evo.getOriginatoreEvento());
	        userAdm = userdao.getUserDetail();
	        userdao.setLogin(evo.getExpertLogin());
	        userExp = userdao.getUserDetail();
	        userdao.setLogin(pvo.getOriginatore());
	        userOrig = userdao.getUserDetail();
	        userdao.closeConnection();
	    } catch(UserDAOException userdaoexception){
	        throw new TaskException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
	    } catch(ProblemDAOException problemdaoexception) {
	        throw new TaskException(problemdaoexception.getMessage(), problemdaoexception.getUserMessage());
	    }
		HashMap map = new HashMap();
		map.put("idSegnalazione", new Long(evo.getIdSegnalazione()));
        map.put("titolo", evo.getTitle());
        map.put("descrizione", evo.getDescription2HTML());
        map.put("categoria",evo.getCategoryDescription());
        map.put("originatoreEventoNome", userAdm.getFirstName()+" "+userAdm.getFamilyName());
        map.put("originatoreEventoEmail", userAdm.getEmail());
        map.put("originatoreEventoTelefono", userAdm.getTelefono());
        //map.put("oldCategoria", evo.getCategoryDescription());
        map.put("stato", evo.getStateDescription());
        map.put("oldStato", evo.getOldStateDescription());
        map.put("nota", evo.getNote2HTML());
        map.put("originatoreProblemaNome", userOrig.getFirstName()+" "+userOrig.getFamilyName());
        map.put("originatoreProblemaEmail", userOrig.getEmail());
        map.put("originatoreProblemaTelefono", userOrig.getTelefono());
        map.put("espertoNome", userExp.getFirstName()+ " " +userExp.getFamilyName());
        map.put("espertoEmail", userExp.getEmail());
        map.put("espertoTelefono", userExp.getTelefono());
        Collection destinatari = new ArrayList();
        destinatari.add(userOrig);
		MailItem mailitem;
		try {
			mailitem = MessageComposer.compose(evo, map, destinatari, evo.getInstance());
		} catch (EventDAOException e) {
			throw new TaskException(e.getMessage(),e.getUserMessage());
		}
		MailManagement mm = new MailManagement();
		mm.sendMail(mailitem);
	}

}

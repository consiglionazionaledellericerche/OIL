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
 * Created on 4-lug-2006
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
import it.cnr.helpdesk.util.Converter;
import it.cnr.helpdesk.util.MessageComposer;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class EmailValidationTask extends Task {

	public void doAction(Object token) throws TaskException {
		EventValueObject evo = (EventValueObject)token;
		UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
		ProblemDAO problemdao = DAOFactory.getDAOFactoryByProperties().getProblemDAO();
		UserValueObject uvo = null;
		UserValueObject xvo = null;
		UserValueObject ovo = null;
		try {
			problemdao.createConnection(evo.getInstance());
			problemdao.setIdSegnalazione(evo.getIdSegnalazione());
			ProblemValueObject pvo = problemdao.getProblemDetail();
			problemdao.closeConnection();
			userdao.createConnection(evo.getInstance());
			userdao.setLogin(pvo.getOriginatore());
			uvo = userdao.getUserDetail();
			userdao.setLogin(pvo.getEsperto());
			xvo = userdao.getUserDetail();
			userdao.setLogin(evo.getOriginatoreEvento());
			ovo = userdao.getUserDetail();	//di norma dovrebbe essere il validatore
			userdao.closeConnection();
		} catch (UserDAOException userdaoexception) {
			throw new TaskException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
		} catch (ProblemDAOException e) {
			throw new TaskException(e.getMessage(), e.getUserMessage());
		}
		String annotation = "\n\n Risposta elaborata da: \t"+xvo.getFamilyName()+" "+xvo.getFirstName()+
							"\n Validata da: \t"+ovo.getFamilyName()+" "+ovo.getFirstName();
		HashMap map = new HashMap();
		map.put("idSegnalazione", new Long(evo.getIdSegnalazione()));
        map.put("titolo", evo.getTitle());
        map.put("descrizione", evo.getDescription2HTML());
        map.put("categoria",evo.getCategoryDescription());
        map.put("originatoreEventoNome", ovo.getFirstName()+" "+ovo.getFamilyName());
        map.put("originatoreEventoEmail", ovo.getEmail());
        map.put("originatoreEventoTelefono", ovo.getTelefono());
        //map.put("oldCategoria", evo.getCategoryDescription());
        map.put("stato", evo.getStateDescription());
        map.put("oldStato", evo.getOldStateDescription());
        map.put("nota", evo.getNote2HTML()+Converter.text2HTML(annotation));
        map.put("originatoreProblemaNome", uvo.getFirstName()+" "+uvo.getFamilyName());
        map.put("originatoreProblemaEmail", uvo.getEmail());
        map.put("originatoreProblemaTelefono", uvo.getTelefono());
        map.put("espertoNome", xvo.getFirstName()+ " " +xvo.getFamilyName());
        map.put("espertoEmail", xvo.getEmail());
        map.put("espertoTelefono", xvo.getTelefono());
        Collection destinatari = new ArrayList();
        destinatari.add(uvo);
        destinatari.add(xvo);
        destinatari.add(ovo);
		MailItem mailitem;
		try {
			mailitem = MessageComposer.compose(evo, map, destinatari, evo.getInstance());
		} catch (EventDAOException e1) {
			throw new TaskException(e1.getMessage(),e1.getUserMessage());
		}
		MailManagement mm = new MailManagement();
		mm.sendMail(mailitem);
	}

}

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
 * Created on 27-giu-2006
 *
 */
package it.cnr.helpdesk.StateMachineManagement.tasks;

import java.util.Collection;
import java.util.HashMap;
import it.cnr.helpdesk.EventManagement.exceptions.EventJBException;
import it.cnr.helpdesk.EventManagement.javabeans.Event;
import it.cnr.helpdesk.MailManagement.javabeans.MailManagement;
import it.cnr.helpdesk.MailManagement.valueobjects.MailItem;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemDAOException;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.StateMachineManagement.StateMachine;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineJBException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.TaskException;
import it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
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
public class EmailEnvolvedExpertsTask extends Task {
	
	public void doAction(Object token) throws TaskException {
		EventValueObject evo = (EventValueObject)token;
		UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
		ProblemDAO problemdao = DAOFactory.getDAOFactoryByProperties().getProblemDAO();
		Collection destinatari;
		Event ev = new Event();
		UserValueObject uvo = null;
		String startDate = "";
		try {
			StateMachine sm = StateMachine.getInstance(evo.getInstance());
			problemdao.createConnection(evo.getInstance());
			problemdao.setIdSegnalazione(evo.getIdSegnalazione());
			ProblemValueObject pvo = problemdao.getProblemDetail();
			problemdao.closeConnection();
			userdao.createConnection(evo.getInstance());
			destinatari = userdao.allCategoryExperts(evo.getCategory());
			userdao.setLogin(pvo.getOriginatore());
			uvo = userdao.getUserDetail();
			userdao.closeConnection();
			startDate = ((EventValueObject)(new Event()).getProblemEvents(pvo.getIdSegnalazione(),true, evo.getInstance()).iterator().next()).getTime().substring(0,10);
			destinatari.add(uvo);
			HashMap map = new HashMap();
			String history = uvo.getFirstName()+" "+uvo.getFamilyName()+" ha scritto:\n"+pvo.getDescrizione();
			for (Object object : ev.getProblemEvents(evo.getIdSegnalazione(), true, evo.getInstance())) {
				EventValueObject e2 = (EventValueObject)object;
                if (e2.getEventType()==Event.CAMBIO_STATO || e2.getEventType()==Event.AGGIUNTA_NOTA) {
                	history = (e2.getNote()+"\n\n\n").concat(history);
                	if(e2.getOriginatoreEvento().equalsIgnoreCase(User.MAIL_USER))
                		history = ("Il "+e2.getTime()+" "+uvo.getFirstName()+" "+uvo.getFamilyName()+" ha scritto:\n").concat(history);
                	else
                		history = ("Il "+e2.getTime()+" "+e2.getOriginatoreEventoDescrizione()+" ha scritto:\n").concat(history);
                }
			}

			map.put("idSegnalazione", new Long(evo.getIdSegnalazione()));
			map.put("titolo", evo.getTitle());
			map.put("descrizione", evo.getDescription2HTML());
			map.put("categoria",evo.getCategoryDescription());
			map.put("originatoreEventoNome", uvo.getFirstName()+" "+uvo.getFamilyName());
			map.put("originatoreEventoEmail", uvo.getEmail());
			map.put("originatoreEventoTelefono", uvo.getTelefono());
			map.put("originatoreEventoStruttura", uvo.getStruttura());
			map.put("originatoreEventoStrutturaDescrizione", uvo.getStrutturaDescrizione());
			map.put("stato", evo.getStateDescription());
			map.put("data_apertura", startDate);
			map.put("oldStato", evo.getOldStateDescription());
			map.put("nota", evo.getNote2HTML());
			map.put("history", Converter.text2HTML(history));
			String template = sm.getTemplate(evo.getOldState(), evo.getState(), uvo.getProfile(), evo.getInstance());
			if(template==null || template.length()==0)
				template = Event.getTemplate(evo.getEventType(), evo.getInstance());
			MailItem mailitem;
			
			mailitem = MessageComposer.compose(map, destinatari, template, evo.getInstance());
			MailManagement mm = new MailManagement();
			mm.sendMail(mailitem);
		} catch (UserDAOException userdaoexception) {
			throw new TaskException(userdaoexception.getMessage(), userdaoexception.getUserMessage());
		} catch (ProblemDAOException e) {
			throw new TaskException(e.getMessage(), e.getUserMessage());
		} catch (StateMachineJBException e) {
			throw new TaskException(e.getMessage(),e.getUserMessage());
		} catch (EventJBException e) {
			throw new TaskException(e.getMessage(), e.getUserMessage());
		}
	}
	
}

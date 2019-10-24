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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.EventManagement.exceptions.EventJBException;
import it.cnr.helpdesk.EventManagement.javabeans.Event;
import it.cnr.helpdesk.MailManagement.javabeans.MailManagement;
import it.cnr.helpdesk.MailManagement.valueobjects.MailItem;
import it.cnr.helpdesk.MailParserManagement.exceptions.MailParserJBException;
import it.cnr.helpdesk.MailParserManagement.javabeans.MailParser;
import it.cnr.helpdesk.MailParserManagement.valueobjects.MailComponent;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemDAOException;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.StateMachineManagement.StateMachine;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineJBException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.TaskException;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.State;
import it.cnr.helpdesk.UserManagement.exceptions.UserDAOException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.ProblemDAO;
import it.cnr.helpdesk.dao.UserDAO;
import it.cnr.helpdesk.exceptions.SettingsJBException;
import it.cnr.helpdesk.util.Converter;
import it.cnr.helpdesk.util.MessageComposer;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class EmailUserExpertTask extends Task {
	
	
	public void doAction(Object token) throws TaskException {
		EventValueObject evo = (EventValueObject)token;
		UserDAO userdao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
		ProblemDAO problemdao = DAOFactory.getDAOFactoryByProperties().getProblemDAO();
		Event ev = new Event();
		UserValueObject uvo = null;
		UserValueObject xvo = null;
		UserValueObject ovo = null;
		try {
			problemdao.createConnection(evo.getInstance());
			problemdao.setIdSegnalazione(evo.getIdSegnalazione());
			ProblemValueObject pvo = problemdao.getProblemDetail();
			problemdao.closeConnection();
			SimpleDateFormat itForm = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
			StateMachine sm = StateMachine.getInstance(evo.getInstance());
			userdao.createConnection(evo.getInstance());
			if(pvo.getOriginatore().equalsIgnoreCase(User.MAIL_USER)){
				MailParser mp = new MailParser();
				MailComponent temp = mp.getByIdSegnalazione(pvo.getIdSegnalazione(), evo.getInstance());
				uvo=new UserValueObject("","",temp.getNome(),temp.getCognome(),1,temp.getMail(),"","","y","","y","");	
				//mailstop viene impostato a "y" poichè la notifica all'utente esterno viene prodotta con un task specifico
			}else{
				userdao.setLogin(pvo.getOriginatore());
				uvo = userdao.getUserDetail();
			}
			if(pvo.getEsperto()!=null)
				userdao.setLogin(pvo.getEsperto());
			else
				userdao.setLogin(evo.getOriginatoreEvento());
			xvo = userdao.getUserDetail();
			if(evo.getOriginatoreEvento()!=User.MAIL_USER){
				userdao.setLogin(evo.getOriginatoreEvento());
				ovo = userdao.getUserDetail();
			}else
				ovo = uvo;
			userdao.closeConnection();
			
			HashMap map = new HashMap();
			List adiacenze = sm.allowedStates(evo.getState(), User.UTENTE);
			String azione = "";
			if(adiacenze!=null && !adiacenze.isEmpty() && adiacenze.get(0)!=null)
				azione = "c" + ((State)adiacenze.get(0)).getId();
			else
				azione = "n0";
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
			map.put("originatoreEventoNome", ovo.getFirstName()+" "+ovo.getFamilyName());
			map.put("originatoreEventoEmail", ovo.getEmail());
			map.put("originatoreEventoTelefono", ovo.getTelefono());
			//map.put("oldCategoria", evo.getCategoryDescription());
			map.put("stato", evo.getStateDescription());
			map.put("oldStato", "" + evo.getOldStateDescription());
			map.put("nota", evo.getNote2HTML());
			map.put("originatoreProblemaNome", uvo.getFirstName()+" "+uvo.getFamilyName());
			map.put("originatoreProblemaEmail", uvo.getEmail());
			map.put("originatoreProblemaTelefono", uvo.getTelefono());
			map.put("espertoNome", xvo.getFirstName()+ " " +xvo.getFamilyName());
			map.put("espertoEmail", xvo.getEmail());
			map.put("espertoTelefono", xvo.getTelefono());
			map.put("check", new Integer(Converter.xorCheck(evo.getIdSegnalazione())));
			map.put("data_apertura", ((EventValueObject)(new Event()).getProblemEvents(pvo.getIdSegnalazione(),true, evo.getInstance()).iterator().next()).getTime().substring(0,10));
			map.put("data_odierna", itForm.format(new Date()));
			map.put("action", azione);
			map.put("instance", evo.getInstance());
			map.put("contextPath",  Settings.getProperty("it.oil.contextPath", evo.getInstance()));
			map.put("history", Converter.text2HTML(history));
			Collection destinatari = new ArrayList();
			destinatari.add(uvo);
			destinatari.add(xvo);
			String template = sm.getTemplate(evo.getOldState(), evo.getState(), ovo.getProfile(),evo.getInstance());
			if(template==null || template.length()==0)
				template = Event.getTemplate(evo.getEventType(), evo.getInstance());
			MailItem mailitem;
			
			mailitem = MessageComposer.compose(map, destinatari, template, evo.getInstance());
			MailManagement mm = new MailManagement();
			mm.sendMail(mailitem);
		} catch (ProblemDAOException e) {
			throw new TaskException(e.getMessage(), e.getUserMessage());
		} catch (UserDAOException e) {
			throw new TaskException(e.getMessage(), e.getUserMessage());
		} catch (StateMachineJBException e) {
			throw new TaskException(e.getMessage(),e.getUserMessage());
		} catch (EventJBException e) {
			throw new TaskException(e.getMessage(),e.getUserMessage());
		} catch (MailParserJBException e) {
			throw new TaskException(e.getMessage(),e.getUserMessage());
		} catch (SettingsJBException e) {
			e.printStackTrace();
			throw new TaskException(e.getMessage(), e.getUserMessage());
		}
	}
	
}

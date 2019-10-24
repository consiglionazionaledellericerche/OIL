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

package it.cnr.helpdesk.StateMachineManagement.tasks;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.MailParserManagement.exceptions.MailParserDAOException;
import it.cnr.helpdesk.MailParserManagement.valueobjects.MailComponent;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.TaskException;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.MailParserDAO;
import it.cnr.helpdesk.exceptions.SettingsJBException;
import java.util.Date;
import java.util.Scanner;
import org.apache.commons.lang.StringUtils;

/**
 * @author Aldo Stentella Liberati
 *
 */
public class ExternalProblemFromExpertTask extends Task {

	public void doAction(Object token) throws TaskException {
		EventValueObject evo = (EventValueObject)token;
		MailComponent mail = new MailComponent();
		MailParserDAO mp = DAOFactory.getDAOFactoryByProperties().getMailParserDAO();
		try {
			String email = StringUtils.substringBetween(evo.getDescription(), "<", ">");
			String nome = "";
			String cognome = "";			
			Scanner scanner = new Scanner(evo.getDescription());
			while (scanner.hasNextLine()) {
			  String line = scanner.nextLine();
			  if(line.indexOf("<"+email+">")>-1){
				  nome = StringUtils.substringBefore(line, " ");
				  cognome = StringUtils.substringBetween(line, " ", "<"+email+">");
				  break;
			  }
			}
			mail.setTitolo(evo.getTitle());
			mail.setCategoria(evo.getCategory());
			mail.setNomeCategoria(evo.getCategoryDescription());
			mail.setNome(nome);
			mail.setCognome(cognome);
			mail.setMail(email);
			mail.setData((new Date()).toString());
			mail.setDescrizione(evo.getDescription());
			mail.setContextPath(Settings.getProperty("it.oil.contextPath", evo.getInstance()));
			mp.createConnection(evo.getInstance());
			int id = mp.executeInsert(mail);
			mail.setId(id);
			mail.setIdSegnalazione((int) evo.getIdSegnalazione());
			mail.setConferma(true);
			mp.executeUpdate(mail);
			mp.closeConnection();
		} catch (SettingsJBException e) {
			e.printStackTrace();
			throw new TaskException(e);
		} catch (MailParserDAOException e) {
			e.printStackTrace();
			throw new TaskException(e);
		}
	}

}

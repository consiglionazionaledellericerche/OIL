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
 * Created on 22-gen-2007
 *
 */
package it.cnr.helpdesk.RepositoryManagement.javabeans;

import it.cnr.helpdesk.RepositoryManagement.ejb.RepositoryManagement;
import it.cnr.helpdesk.RepositoryManagement.ejb.RepositoryManagementHome;
import it.cnr.helpdesk.RepositoryManagement.exceptions.RepositoryDAOException;
import it.cnr.helpdesk.RepositoryManagement.exceptions.RepositoryEJBException;
import it.cnr.helpdesk.RepositoryManagement.exceptions.RepositoryJBException;
import it.cnr.helpdesk.RepositoryManagement.valueobjects.RepositoryValueObject;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.RepositoryDAO;
import it.cnr.helpdesk.javabeans.HelpDeskJB;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/*
import it.cnr.helpdesk.dao.HelpDeskDAO;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
*/

/**
 * @author Aldo Stentella Liberati
 *
 * 
 */
public class Repository extends HelpDeskJB {
	
	//Connection connection;
	

	public void updateAttachment(RepositoryValueObject rvo, String instance) throws RepositoryJBException {
		try {
			RepositoryManagementHome rmh = lookupHome();
			RepositoryManagement rm = (RepositoryManagement) PortableRemoteObject.narrow(rmh.create(), it.cnr.helpdesk.RepositoryManagement.ejb.RepositoryManagement.class);
			rm.updateAttachment(rvo, instance);
			rm.remove();
		} catch (RepositoryEJBException e) {
			throw new RepositoryJBException();
		} catch (Exception e) {
			throw new RepositoryJBException();
		}
	}
	
	public void deleteRow(RepositoryValueObject rvo, String instance) throws RepositoryJBException {
		try {
			RepositoryManagementHome rmh = lookupHome();
			RepositoryManagement rm = (RepositoryManagement) PortableRemoteObject.narrow(rmh.create(), it.cnr.helpdesk.RepositoryManagement.ejb.RepositoryManagement.class);
			rm.deleteRow(rvo, instance);
			rm.remove();
		} catch (RepositoryEJBException e) {
			throw new RepositoryJBException();
		} catch (Exception e) {
			throw new RepositoryJBException();
		}
	}
	
	/**
	 * Restituisce un file contentente il report sulle segnalazioni sottomesse tra <i>inizio</i> e <i>fine</i>
	 * e appartenenti a <i>categoria</i>
	 * @param categoria la categoria di afferenza
	 * @param inizio data inizio intervallo
	 * @param fine data fine intervallo
	 * @return un file denominato <i>segnalazioni.rtf</i> e sito nella stessa directory del file .jasper
	 * @throws RepositoryJBException

	public File generateReport(String categoria, String inizio, String fine) throws RepositoryJBException {
		HashMap parameterMap = new HashMap();
		parameterMap.put("INIZIO",inizio);
		parameterMap.put("FINE",fine);
		parameterMap.put("CATEGORIA", categoria);
		Class confClass = this.getClass();
		ClassLoader loader = confClass.getClassLoader();
		URL fileURL = loader.getResource("WEB-INF/reports/segnalazioni.jasper");
		if (fileURL == null)
			throw new RepositoryJBException("Can't find the file: segnalazioni.jasper"); 
		String reportDir =	fileURL.getFile().replaceAll("segnalazioni.jasper","");
		try {
			HelpDeskDAO dao = new HelpDeskDAO();
			dao.createConnection();
			connection = dao.getConnection();
			System.out.println("Filling report...");
			JasperFillManager.fillReportToFile(reportDir + "segnalazioni.jasper", parameterMap, connection);
			System.out.println("Done!");
			connection.close();
			File file = new File(reportDir + "segnalazioni.jrprint");
			JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(file);
			JRRtfExporter rtfExporter = new JRRtfExporter();
			
			rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			rtfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,reportDir + "segnalazioni.rtf");
			System.out.println("Exporting report...");
			rtfExporter.exportReport();
			System.out.println("Done!");
		} catch (Exception e) {
			throw new RepositoryJBException(e.getMessage());
		}
		return new File(reportDir + "segnalazioni.rtf");
	}
	 */
	
	public String createReport(String categoria, String inizio, String fine, String instance) throws RepositoryJBException {
		String file = "";
		try {
			RepositoryManagementHome rmh = lookupHome();
			RepositoryManagement rm = (RepositoryManagement) PortableRemoteObject.narrow(rmh.create(), it.cnr.helpdesk.RepositoryManagement.ejb.RepositoryManagement.class);
			file = rm.createReport(categoria, inizio, fine, instance);
			rm.remove();
		} catch (RepositoryEJBException e) {
			throw new RepositoryJBException();
		} catch (Exception e) {
			throw new RepositoryJBException();
		}
		return file;
	}
	
	public boolean findDocument(RepositoryValueObject rvo, String instance) throws RepositoryJBException {
		boolean isFound = false;
        DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
        RepositoryDAO dao = daoFactory.getRepositoryDAO();
        try {
			dao.createConnection(instance);
	        isFound = dao.findDocument(rvo);
	        dao.closeConnection();
		} catch (RepositoryDAOException e){
			throw new RepositoryJBException(e);
		}
		return isFound;
	}
	
	public Collection fetchRows(RepositoryValueObject rvo, String instance) throws RepositoryJBException {
		Collection data;
        DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
        RepositoryDAO dao = daoFactory.getRepositoryDAO();
        try {
			dao.createConnection(instance);
	        data = dao.fetchRows(rvo);
	        dao.closeConnection();
		} catch (RepositoryDAOException e){
			throw new RepositoryJBException(e);
		}
		return data;
	}
	
	private RepositoryManagementHome lookupHome() throws RepositoryJBException {
		RepositoryManagementHome Repositorymanagementhome = null;
		try {
			Context context = getInitialContext();
        	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
            Object obj = context.lookup(jndiPrefix+"RepositoryManagement!it.cnr.helpdesk.RepositoryManagement.ejb.RepositoryManagementHome");
			Repositorymanagementhome = (RepositoryManagementHome)PortableRemoteObject.narrow(obj, it.cnr.helpdesk.RepositoryManagement.ejb.RepositoryManagementHome.class);
		} catch(NamingException namingexception) {
			log(this, "Si \350 verificato un errore durante la ricerca di RepositoryManagement all'interno del registro JNDI. " + namingexception.getMessage());
			namingexception.printStackTrace();
			throw new RepositoryJBException(namingexception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch(RepositoryEJBException Repositoryejbexception) {
			throw new RepositoryJBException(Repositoryejbexception.getMessage(), Repositoryejbexception.getUserMessage());
		} catch(Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new RepositoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return Repositorymanagementhome;
	}
	
}

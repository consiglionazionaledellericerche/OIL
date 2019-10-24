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
 * Created on 30-gen-2007
 *


 */
package it.cnr.helpdesk.RepositoryManagement.ejb;

import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemDAOException;
import it.cnr.helpdesk.RepositoryManagement.exceptions.RepositoryDAOException;
import it.cnr.helpdesk.RepositoryManagement.exceptions.RepositoryEJBException;
import it.cnr.helpdesk.RepositoryManagement.valueobjects.RepositoryValueObject;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.ProblemDAO;
import it.cnr.helpdesk.dao.RepositoryDAO;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRRtfExporter;

/**
 * 
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> * <!--
 * lomboz.beginDefinition --> <?xml version="1.0" encoding="UTF-8"?> <lomboz:EJB
 * xmlns:j2ee="http://java.sun.com/xml/ns/j2ee"
 * xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz"> <lomboz:session>
 * <lomboz:sessionEjb><j2ee:display-name>RepositoryManagement
 * </j2ee:display-name> <j2ee:ejb-name>RepositoryManagement </j2ee:ejb-name>
 * <j2ee:ejb-class>it.cnr.helpdesk.RepositoryManagement.ejb.RepositoryManagementBean
 * </j2ee:ejb-class> <j2ee:session-type>Stateless </j2ee:session-type>
 * <j2ee:transaction-type>Container </j2ee:transaction-type>
 * </lomboz:sessionEjb> </lomboz:session> </lomboz:EJB> <!--
 * lomboz.endDefinition -->
 * 
 * <!-- begin-xdoclet-definition -->
 * 
 * @ejb.bean name="RepositoryManagement" jndi-name="comp/env/ejb/RepositoryManagement"
 *           type="Stateless" transaction-type="Container"
 *  -- This is needed for JOnAS. If you are not using JOnAS you can
 * safely remove the tags below.
 * @jonas.bean ejb-name="RepositoryManagement" jndi-name="comp/env/ejb/RepositoryManagement"
 *  -- <!-- end-xdoclet-definition -->
 * @generated
 */
public abstract class RepositoryManagementBean implements javax.ejb.SessionBean {

	/**
	 * @ejb.interface-method view-type="remote"
	 * @ejb.transaction
	 *  type="Required"
	 */
	public void addRow(RepositoryValueObject rvo, String instance) throws RepositoryEJBException {
        DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
        RepositoryDAO dao = daoFactory.getRepositoryDAO();
        try {
			dao.createConnection(instance);
	        dao.addRow(rvo);
	        dao.closeConnection();
		} catch (RepositoryDAOException e) {
			throw new RepositoryEJBException();
		}
	}
	
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public String createReport(String categoria, String inizio, String fine, String instance) throws RepositoryEJBException{
		Collection cond = new ArrayList();
		cond.add("and e.DATETIME > to_date( '"+inizio+"' ,'DD/MM/YYYY')");
		cond.add("and e.DATETIME < to_date( '"+ fine +"' ,'DD/MM/YYYY')");
		cond.add("and s.categoria = " + categoria);
		cond.add("and e.tipo_evento = 0");

		RepositoryValueObject rvo = new RepositoryValueObject();
		rvo.setMese(Integer.parseInt(inizio.substring(6) + inizio.substring(3,5)));
		rvo.setCategoria(Integer.parseInt(categoria));
		DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
		RepositoryDAO dao = daoFactory.getRepositoryDAO();
		ProblemDAO problemdao = daoFactory.getProblemDAO();
		try {
			dao.createConnection(instance);
			Collection data = dao.fetchRows(rvo);
			if(!data.isEmpty()){
				System.out.println("report gia' presente nel DB");
				rvo = (RepositoryValueObject)data.iterator().next();
				if(rvo.getStato().startsWith("v"))
					return"---";
				if(rvo.getStato().startsWith("i"))
					dao.executeDelete(rvo);			//se quello in archivio è un report incompleto viene cancellato e rigenerato
				else								// stato = "d" oppure "m"
					return rvo.getNomeFile();		//restituisco la referenza al report in archivio
			}
            problemdao.createConnection(instance);
            if(problemdao.countEventQBE(cond)==0){
				rvo.setStato("v");
				addRow(rvo, instance);
				System.out.println("nessuna segnalazione trovata");
				return "---";
			}
			cond.add("and s.STATO not in (4,5)");	//condizione per controllare se ci sono segnalazioni non chiuse o verificate
			if(problemdao.countEventQBE(cond)>0)
				rvo.setStato("i");					//il report prodotto sarà incompleto (con segnalazioni ancora aperte)
			else
				rvo.setStato("d");					//report completo
			OutputStream os = dao.executeInsert(rvo);
			HashMap parameterMap = new HashMap();
			parameterMap.put("INIZIO",inizio);
			parameterMap.put("FINE",fine);
			parameterMap.put("CATEGORIA", new Integer(categoria));
			Class confClass = this.getClass();
			ClassLoader loader = confClass.getClassLoader();
			String name = "segnalazioni"+System.getProperty("it.cnr.oil.dbmstype")+".jasper";
			InputStream file = loader.getResourceAsStream("it/cnr/helpdesk/resources/" + name);
			if (file == null)
				throw new RepositoryEJBException("Can't find the file: " + name); 
			Connection connection = dao.getConnection();
			JRRtfExporter rtfExporter = new JRRtfExporter();
			rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, JasperFillManager.fillReport(file, parameterMap, connection) );
			rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
			System.out.println("Exporting report...");
			rtfExporter.exportReport();
			dao.writeFile(rvo);
			System.out.println("Done!");
		} catch (RepositoryDAOException e) {
			throw new RepositoryEJBException(e);
		} catch (ProblemDAOException e) {
			e.printStackTrace();
			throw new RepositoryEJBException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryEJBException(e);
		} finally {
			try {
				dao.closeConnection();
			} catch (RepositoryDAOException e1) {
				e1.printStackTrace();
			}
			try {
				problemdao.closeConnection();
			} catch (ProblemDAOException e2) {
				e2.printStackTrace();
			}
		}
		return rvo.getNomeFile();
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 */
	public void updateAttachment(RepositoryValueObject rvo, String instance) throws RepositoryEJBException{
        DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
        RepositoryDAO dao = daoFactory.getRepositoryDAO();
        try {
			dao.createConnection(instance);
	        dao.updateAttachment(rvo);
	        dao.closeConnection();
		} catch (RepositoryDAOException e) {
			throw new RepositoryEJBException();
		}
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 **/
	public void deleteRow(RepositoryValueObject rvo, String instance) throws RepositoryEJBException{ 
        DAOFactory daoFactory=DAOFactory.getDAOFactoryByProperties();
        RepositoryDAO dao = daoFactory.getRepositoryDAO();
        try {
			dao.createConnection(instance);
	        dao.executeDelete(rvo);
	        dao.closeConnection();
		} catch (RepositoryDAOException e) {
			throw new RepositoryEJBException();
		}
	}
}


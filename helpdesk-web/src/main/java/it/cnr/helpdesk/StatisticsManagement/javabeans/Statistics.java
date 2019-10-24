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

 

package it.cnr.helpdesk.StatisticsManagement.javabeans;

import it.cnr.helpdesk.StatisticsManagement.ejb.StatisticsManagement;
import it.cnr.helpdesk.StatisticsManagement.ejb.StatisticsManagementHome;
import it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsEJBException;
import it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsJBException;
import it.cnr.helpdesk.StatisticsManagement.valueobjects.OverallStatisticsDTO;
import it.cnr.helpdesk.StatisticsManagement.valueobjects.StatisticsDTO;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.javabeans.HelpDeskJB;

import java.util.Collection;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

// Referenced classes of package it.cnr.helpdesk.javabeans:
//            HelpDeskJB

public class Statistics extends HelpDeskJB {

	public void setStartDate(String s) {
		startDate = s;
	}

	public void setEndDate(String s) {
		endDate = s;
	}

	public void setSamplingInterval(Integer integer) {
		samplingInterval = integer;
	}

	public void setHelpdeskUserID(String s) {
		helpdeskUserID = s;
	}

	public void setHelpdeskUserProfile(int i) {
		helpdeskUserProfile = i;
	}

	public void sethelpDeskUser(UserValueObject uservalueobject) {
		helpdeskUser = uservalueobject;
	}

	public void setStatisticsType(int i) {
		statisticsType = i;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public Integer getSamplingInterval() {
		return samplingInterval;
	}

	public String getHelpdeskUserID() {
		return helpdeskUserID;
	}

	public int getHelpdeskUserProfile() {
		return helpdeskUserProfile;
	}

	public UserValueObject getHelpdeskUser() {
		return helpdeskUser;
	}

	public int getStatisticsType() {
		return statisticsType;
	}

	public Statistics() {
	}

	private StatisticsManagementHome lookupHome() throws StatisticsJBException {
		StatisticsManagementHome statisticsmanagementhome = null;
		try {
			Context context = getInitialContext();
        	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
            Object obj = context.lookup(jndiPrefix+"StatisticsManagement!it.cnr.helpdesk.StatisticsManagement.ejb.StatisticsManagementHome");
			statisticsmanagementhome = (StatisticsManagementHome) PortableRemoteObject.narrow(obj, it.cnr.helpdesk.StatisticsManagement.ejb.StatisticsManagementHome.class);
		} catch (StatisticsEJBException statisticsejbexception) {
			throw new StatisticsJBException(statisticsejbexception.getMessage(), statisticsejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore durante la ricerca nel registro JNDI. " + exception.getMessage());
			exception.printStackTrace();
			throw new StatisticsJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return statisticsmanagementhome;
	}

	public Collection getProblemsDistributionByStatus(String instance) throws StatisticsJBException {
		Collection collection = null;
		try {
			StatisticsManagementHome statisticsmanagementhome = lookupHome();
			StatisticsDTO statisticsdto = new StatisticsDTO();
			statisticsdto.setHelpdeskUserID(helpdeskUserID);
			statisticsdto.setHelpdeskUserProfile(helpdeskUserProfile);
			statisticsdto.setStartDate(startDate);
			statisticsdto.setEndDate(endDate);
			statisticsdto.setStatisticsType(statisticsType);
			StatisticsManagement statisticsmanagement = (StatisticsManagement) PortableRemoteObject.narrow(statisticsmanagementhome.create(), it.cnr.helpdesk.StatisticsManagement.ejb.StatisticsManagement.class);
			collection = statisticsmanagement.getProblemsDistributionByStatus(statisticsdto, instance);
			statisticsmanagement.remove();
		} catch (StatisticsEJBException statisticsejbexception) {
			throw new StatisticsJBException(statisticsejbexception.getMessage(), statisticsejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, exception.getMessage());
			exception.printStackTrace();
			throw new StatisticsJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione (JB level) durante il calcolo della distribuzione dei problemi per stato di lavorazione.");
		}
		return collection;
	}

	public OverallStatisticsDTO getOverallStatistics(String instance) throws StatisticsJBException {
		OverallStatisticsDTO overallstatisticsdto = null;
		try {
			StatisticsManagementHome statisticsmanagementhome = lookupHome();
			StatisticsDTO statisticsdto = new StatisticsDTO();
			statisticsdto.setHelpdeskUserID(helpdeskUserID);
			statisticsdto.setHelpdeskUserProfile(helpdeskUserProfile);
			statisticsdto.setStartDate(startDate);
			statisticsdto.setEndDate(endDate);
			statisticsdto.setStatisticsType(statisticsType);
			StatisticsManagement statisticsmanagement = (StatisticsManagement) PortableRemoteObject.narrow(statisticsmanagementhome.create(), it.cnr.helpdesk.StatisticsManagement.ejb.StatisticsManagement.class);
			overallstatisticsdto = statisticsmanagement.getOverallStatistics(statisticsdto, instance);
			statisticsmanagement.remove();
		} catch (StatisticsEJBException statisticsejbexception) {
			throw new StatisticsJBException(statisticsejbexception.getMessage(), statisticsejbexception.getUserMessage());
		} catch (Exception exception) {
			throw new StatisticsJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione (JB level) durante il calcolo delle statistiche di insieme del servizio di helpdesk di secondo livello.");
		}
		return overallstatisticsdto;
	}

	public Collection getProblemsDistributionByCategory(String instance) throws StatisticsJBException {
		Collection collection = null;
		try {
			StatisticsManagementHome statisticsmanagementhome = lookupHome();
			StatisticsDTO statisticsdto = new StatisticsDTO();
			statisticsdto.setHelpdeskUserID(helpdeskUserID);
			statisticsdto.setHelpdeskUserProfile(helpdeskUserProfile);
			statisticsdto.setStatisticsType(statisticsType);
			StatisticsManagement statisticsmanagement = (StatisticsManagement) PortableRemoteObject.narrow(statisticsmanagementhome.create(), it.cnr.helpdesk.StatisticsManagement.ejb.StatisticsManagement.class);
			collection = statisticsmanagement.getProblemsDistributionByCategory(statisticsdto, instance);
			statisticsmanagement.remove();
		} catch (StatisticsEJBException statisticsejbexception) {
			throw new StatisticsJBException(statisticsejbexception.getMessage(), statisticsejbexception.getUserMessage());
		} catch (Exception exception) {
			throw new StatisticsJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione (JB level) durante il calcolo delle statistiche di insieme del servizio di helpdesk di secondo livello.");
		}
		return collection;
	}

	private String startDate;
	private String endDate;
	private Integer samplingInterval;
	private String helpdeskUserID;
	private int helpdeskUserProfile;
	private UserValueObject helpdeskUser;
	private int statisticsType;
	public static final int PERSONAL = 1;
	public static final int GENERAL = 2;
}
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
 * Created on 24-giu-2005
 *


 */
package it.cnr.helpdesk.StatisticsManagement.actions;

import it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsJBException;
import it.cnr.helpdesk.StatisticsManagement.javabeans.Statistics;
import it.cnr.helpdesk.StatisticsManagement.valueobjects.*;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.charts.confobjects.*;
import it.cnr.helpdesk.util.Converter;

import java.awt.Color;
import java.util.Calendar;

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
public class StatisticheTempiRispostaAction extends Action {
    private static Log log = LogFactory.getLog(StatisticheTempiRispostaAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, StatisticsJBException  {
        log.warn("In execute method of StatisticheTempiRispostaAction");
        HttpSession session = request.getSession(true);
        String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");
        User utente;
        if (session.getAttribute("it.cnr.helpdesk.currentuser") != null) {
            utente = (User) session.getAttribute("it.cnr.helpdesk.currentuser");
            int profile = utente.getProfile();
            
            Statistics sjb = new Statistics();
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            String Day, Month, Year, Hour, Minute, Second;
            Day = Integer.toString(day);
            if (day < 10)
                Day = "0" + Day;
            Month = Integer.toString(month);
            if (month < 10)
                Month = "0" + Month;
            Year = Integer.toString(year);
            Hour = Integer.toString(hour);
            if (hour < 10)
                Hour = "0" + Hour;
            Minute = Integer.toString(minute);
            if (minute < 10)
                Minute = "0" + Minute;
            Second = Integer.toString(second);
            if (second < 10)
                Second = "0" + Second;
            String now = Day + "/" + Month + "/" + Year + " " + Hour + ":" + Minute + ":" + Second;
            sjb.setHelpdeskUserID(utente.getLogin());
            sjb.setHelpdeskUserProfile(profile);
            sjb.setStartDate("01/01/1970 00:00:00");
            sjb.setEndDate(now);
            
            request.setAttribute("prof", String.valueOf(profile));
            request.setAttribute("now", now);
            OverallStatisticsDTO osDTO = new OverallStatisticsDTO();
            
            MeterChartConf meterChartConf;
            if (profile != 3) {
                sjb.setStatisticsType(Statistics.PERSONAL);
                osDTO = sjb.getOverallStatistics(instance);
                meterChartConf = new MeterChartConf();
                meterChartConf.setBgPaint(Color.white);
                meterChartConf.setDataset(MeterChartConf.createMeterDataset((double) osDTO.getAvgResponseTime(), (double) osDTO.getMinResponseTime(),(double) osDTO.getMaxResponseTime(), "Giorni"));
                meterChartConf.setTitle("Tempo di risposta medio personale");
                String chartConfName1 = "MeterChart.Personal";
                session.setAttribute(chartConfName1, meterChartConf);
                
                request.setAttribute("minT1", Converter.doubleToTimeString(osDTO.getMinResponseTime()));
                request.setAttribute("maxT1", Converter.doubleToTimeString(osDTO.getMaxResponseTime()));
                request.setAttribute("avgT1", Converter.doubleToTimeString(osDTO.getAvgResponseTime()));
            }
            if (profile != 1) {
                sjb.setStatisticsType(Statistics.GENERAL);
                osDTO = sjb.getOverallStatistics(instance);
                meterChartConf = new MeterChartConf();
                meterChartConf.setBgPaint(Color.white);
                meterChartConf.setDataset(MeterChartConf.createMeterDataset((double) osDTO.getAvgResponseTime(), (double) osDTO.getMinResponseTime(),(double) osDTO.getMaxResponseTime(), "Giorni"));
                meterChartConf.setTitle("Tempo di risposta medio generale");
                String chartConfName2 = "MeterChart.General";
                session.setAttribute(chartConfName2, meterChartConf);
                
                request.setAttribute("minT2", Converter.doubleToTimeString(osDTO.getMinResponseTime()));
                request.setAttribute("maxT2", Converter.doubleToTimeString(osDTO.getMaxResponseTime()));
                request.setAttribute("avgT2", Converter.doubleToTimeString(osDTO.getAvgResponseTime()));
            }
            return mapping.findForward("StatisticheTempiRisposta");
        }
        return mapping.findForward("welcome");
    }
}
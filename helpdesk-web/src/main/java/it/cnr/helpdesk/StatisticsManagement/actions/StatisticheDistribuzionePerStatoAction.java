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
 * Created on 22-giu-2005
 *


 */
package it.cnr.helpdesk.StatisticsManagement.actions;

import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import it.cnr.helpdesk.charts.confobjects.*;
import it.cnr.helpdesk.exceptions.SettingsJBException;
import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.StateMachineManagement.StateMachine;
import it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsJBException;
import it.cnr.helpdesk.StatisticsManagement.javabeans.Statistics;
import it.cnr.helpdesk.StatisticsManagement.valueobjects.ProblemsDistributionByStatusDTO;
import it.cnr.helpdesk.util.Converter;

/**
 * @author Aldo Stentella Liberati
 * 


 */
public class StatisticheDistribuzionePerStatoAction extends Action {
    private static Log log = LogFactory.getLog(StatisticheDistribuzionePerStatoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UserJBException, StatisticsJBException, SettingsJBException  {
        log.warn("In execute method of StatisticheDistribuzionePerStatoAction");
        HttpSession session = request.getSession(true);
        String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");
        User utente;
        if (session.getAttribute("it.cnr.helpdesk.currentuser") != null) {
            utente = (User) session.getAttribute("it.cnr.helpdesk.currentuser");
            int profile = utente.getProfile();
            request.setAttribute("prof", String.valueOf(profile));

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
            String now = Day + "/" + Month + "/" + Year + " " + Hour + ":" + Minute + ":" + Second + "-" + Hour + ":" + Minute + ":" + Second;

            request.setAttribute("now", now);

            sjb.setHelpdeskUserID(utente.getLogin());
            sjb.setHelpdeskUserProfile(profile);
            sjb.setStatisticsType(Statistics.PERSONAL);
            Collection c = sjb.getProblemsDistributionByStatus(instance);
            request.setAttribute("stats1", c);
            ArrayList chartData = new ArrayList();
            ArrayList chartColors = new ArrayList();
            Iterator i = c.iterator();
            int count = 0;
            PieChartConf pieChartConf = null;
            String ConfObjectName = null;
            Vector<String> stateNames = Settings.getId2DescriptionStateMapping(session.getAttribute("it.cnr.helpdesk.instance").toString());
            while (i.hasNext()) {
                ProblemsDistributionByStatusDTO pdbsDTO = (ProblemsDistributionByStatusDTO) i.next();
                count = count + pdbsDTO.getCount();
                String status = pdbsDTO.getStatus();
                if (status.equals(stateNames.elementAt(StateMachine.OPEN-1)))
                    chartColors.add(Color.red);
                if (status.equals(stateNames.elementAt(StateMachine.WORKING-1)))
                    chartColors.add(new Color(255,215,0));
                if (status.equals(stateNames.elementAt(StateMachine.EXTERNAL-1)))
                    chartColors.add(new Color(255,120,0));
                if (status.equals(stateNames.elementAt(StateMachine.CLOSED-1)))
                    chartColors.add(Color.blue);
                if (!status.equals(stateNames.elementAt(StateMachine.VERIFIED-1)))
                    chartData.add(Converter.problemDistributionToSeriesItem(pdbsDTO));
            }
            request.setAttribute("tot1", "" + count);
            if (chartColors.isEmpty())
                request.setAttribute("okGraph1", "no");
            else {
                request.setAttribute("okGraph1", "yes");

                pieChartConf = new PieChartConf("Distribuzione personale per stato", PieChartConf.createPieDataset(chartData), PieChartConf.createPaint(chartColors), Color.white);
                ConfObjectName = "PieChartConf.DistrPers";
                session.setAttribute(ConfObjectName, pieChartConf);
            }
            if (profile == 2) {
                sjb.setStatisticsType(Statistics.GENERAL);
                c = sjb.getProblemsDistributionByStatus(instance);
                request.setAttribute("stats2", c);
                chartData = new ArrayList();
                i = c.iterator();
                count = 0;
                chartColors = new ArrayList();
                while (i.hasNext()) {
                    ProblemsDistributionByStatusDTO pdbsDTO = (ProblemsDistributionByStatusDTO) i.next();
                    count = count + pdbsDTO.getCount();
                    String status = pdbsDTO.getStatus();
                    if (status.equals(stateNames.elementAt(StateMachine.OPEN-1)))
                        chartColors.add(Color.red);
                    if (status.equals(stateNames.elementAt(StateMachine.WORKING-1)))
                        chartColors.add(new Color(255,215,0));
                    if (status.equals(stateNames.elementAt(StateMachine.EXTERNAL-1)))
                           chartColors.add(new Color(255,120,0));
                    if (status.equals(stateNames.elementAt(StateMachine.CLOSED-1)))
                        chartColors.add(Color.blue);
                    if (!status.equals(stateNames.elementAt(StateMachine.VERIFIED-1)))
                        chartData.add(Converter.problemDistributionToSeriesItem(pdbsDTO));
                }
                request.setAttribute("tot2", "" + count);
                if (chartColors.isEmpty())
                    request.setAttribute("okGraph2", "no");
                else {
                    request.setAttribute("okGraph2", "yes");
                    pieChartConf = new PieChartConf("Distribuzione generale per stato", PieChartConf.createPieDataset(chartData), PieChartConf.createPaint(chartColors), Color.white);
                    ConfObjectName = "PieChartConf.DistrGen";
                    session.setAttribute(ConfObjectName, pieChartConf);
                }
            }

            return mapping.findForward("StatisticheDistribuzionePerStato");
        }
        return mapping.findForward("welcome");
    }
}
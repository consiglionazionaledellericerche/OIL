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


package it.cnr.helpdesk.charts.servlets;

import com.jrefinery.chart.ChartUtilities;
import com.jrefinery.chart.JFreeChart;
import com.jrefinery.chart.JFreeChartConstants;
import com.jrefinery.chart.MeterPlot;
import com.jrefinery.data.DefaultMeterDataset;
import it.cnr.helpdesk.charts.confobjects.ChartConf;
import it.cnr.helpdesk.charts.confobjects.MeterChartConf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//Referenced classes of package it.cnr.helpdesk.charts.servlets:
//         ChartServlet

public class MeterChartServlet extends HttpServlet
 implements ChartServlet
{

 public MeterChartServlet()
 {
 }

 public JFreeChart buildChart(ChartConf chartconf)
 {
     DefaultMeterDataset defaultmeterdataset = (DefaultMeterDataset)chartconf.getDataset();
     MeterPlot meterplot = new MeterPlot(defaultmeterdataset);
     JFreeChart jfreechart = new JFreeChart(chartconf.getTitle(), JFreeChartConstants.DEFAULT_TITLE_FONT, meterplot, true);
     jfreechart.setBackgroundPaint(chartconf.getBgPaint());
     return jfreechart;
 }

 public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
     throws ServletException, IOException
 {
     try
     {
         HttpSession httpsession = httpservletrequest.getSession(true);
         MeterChartConf meterchartconf = (MeterChartConf)httpsession.getAttribute(httpservletrequest.getParameter("ChartConf"));
         httpservletresponse.setContentType("image/png");
         javax.servlet.ServletOutputStream servletoutputstream = httpservletresponse.getOutputStream();
         JFreeChart jfreechart = buildChart(meterchartconf);
         ChartUtilities.writeChartAsPNG(servletoutputstream, jfreechart, 400, 300);
         servletoutputstream.close();
     }
     catch(Exception exception)
     {
         exception.printStackTrace();
     }
 }

 public void doPost(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
     throws ServletException, IOException
 {
     doGet(httpservletrequest, httpservletresponse);
 }
}
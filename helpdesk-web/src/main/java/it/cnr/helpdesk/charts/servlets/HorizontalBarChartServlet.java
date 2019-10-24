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

import com.jrefinery.chart.*;
import com.jrefinery.data.CategoryDataset;
import it.cnr.helpdesk.charts.confobjects.ChartConf;
import it.cnr.helpdesk.charts.confobjects.HorizontalBarChartConf;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

//Referenced classes of package it.cnr.helpdesk.charts.servlets:
//         ChartServlet

public class HorizontalBarChartServlet extends HttpServlet
 implements ChartServlet
{

 public HorizontalBarChartServlet()
 {
 }

 public JFreeChart buildChart(ChartConf chartconf)
 {
     JFreeChart jfreechart = ChartFactory.createHorizontalBarChart3D(chartconf.getTitle(), ((HorizontalBarChartConf)chartconf).getCategoryAxisLabel(), ((HorizontalBarChartConf)chartconf).getValueAxisLabel(), (CategoryDataset)chartconf.getDataset(), false);
     Plot plot = jfreechart.getPlot();
     plot.setSeriesPaint(chartconf.getPaints());
     jfreechart.setBackgroundPaint(chartconf.getBgPaint());
     return jfreechart;
 }

 public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
     throws ServletException, IOException
 {
     try
     {
         HttpSession httpsession = httpservletrequest.getSession(true);
         if(httpservletrequest.getParameter("ConfObject") == null)
             System.out.println("ConfObject e' null");
         HorizontalBarChartConf horizontalbarchartconf = (HorizontalBarChartConf)httpsession.getAttribute(httpservletrequest.getParameter("ConfObject"));
         httpservletresponse.setContentType("image/png");
         javax.servlet.ServletOutputStream servletoutputstream = httpservletresponse.getOutputStream();
         ChartUtilities.writeChartAsPNG(servletoutputstream, buildChart(horizontalbarchartconf), horizontalbarchartconf.getWidth(), horizontalbarchartconf.getHeight());
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
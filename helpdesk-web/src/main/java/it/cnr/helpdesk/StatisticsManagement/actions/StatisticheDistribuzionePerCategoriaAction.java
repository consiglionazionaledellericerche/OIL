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
 * Created on 20-giu-2005
 *


 */
package it.cnr.helpdesk.StatisticsManagement.actions;


import it.cnr.helpdesk.charts.confobjects.HorizontalBarChartConf;
import java.util.*;
import it.cnr.helpdesk.UserManagement.exceptions.*;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.StatisticsManagement.exceptions.StatisticsJBException;
import it.cnr.helpdesk.StatisticsManagement.javabeans.Statistics;
import it.cnr.helpdesk.StatisticsManagement.valueobjects.ProblemsDistributionByCategoryDTO;
import java.awt.*;
import com.jrefinery.data.CategoryDataset;
import com.jrefinery.data.DefaultCategoryDataset;


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
public class StatisticheDistribuzionePerCategoriaAction extends Action {
	private static Log log = LogFactory.getLog(StatisticheDistribuzionePerCategoriaAction.class);
	
	public ActionForward execute (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws UserJBException, StatisticsJBException {
		log.warn("In execute method of StatisticheDistribuzionePerCategoriaAction");
		HttpSession session=request.getSession(true);
		String instance = (String)session.getAttribute("it.cnr.helpdesk.instance");
		User utente;
		if (session.getAttribute("it.cnr.helpdesk.currentuser")!=null) {
			utente=(User)session.getAttribute("it.cnr.helpdesk.currentuser");
			int profile=utente.getProfile();
			
		      
		      Statistics sjb= new Statistics();
			  /* Compongo la stringa del timestamp */
			  Calendar cal=Calendar.getInstance();
			  int day=cal.get(Calendar.DAY_OF_MONTH);
			  int month=cal.get(Calendar.MONTH)+1;
			  int year=cal.get(Calendar.YEAR);
			  int hour=cal.get(Calendar.HOUR_OF_DAY);
			  int minute=cal.get(Calendar.MINUTE);
			  int second=cal.get(Calendar.SECOND);
			  String Day, Month, Year, Hour, Minute, Second;
			  Day=Integer.toString(day);
			  if (day<10) Day="0"+Day;
			  Month=Integer.toString(month);
			  if (month<10) Month="0"+Month;
			  Year=Integer.toString(year);
			  Hour=Integer.toString(hour);
			  if (hour<10) Hour="0"+ Hour;
			  Minute=Integer.toString(minute);
			  if (minute<10) Minute="0"+ Minute;
			  Second=Integer.toString(second);
			  if (second<10) Second="0"+ Second;
			  String now=Day+ "/" + Month + "/" + Year+" "+ Hour + ":" + Minute + ":" + Second;
			  /* Fine composizione stringa timestamp */

			  /* Impostazione attributi del JavaBean Statistics */
			  sjb.setHelpdeskUserID(utente.getLogin());
			  sjb.setHelpdeskUserProfile(utente.getProfile());
			  /* Fine impostazione degli attributi del Java Bean Statistics */

			  Collection pdbc=null;
			  Collection chartColors=new ArrayList();
			  chartColors.add(Color.blue);
			  Iterator iterator=null;

			  /*
               * Inizio sezione generazione dei grafici. A seconda del profilo
               * utente (1,2,3) saranno generati diversi grafi.
               */
              request.setAttribute("prof", String.valueOf(profile));
              request.setAttribute("okGraph1", "no");
              request.setAttribute("okGraph2", "no");
              request.setAttribute("now", now);

              if(profile==1 || profile==2){
                  // Impostazione del tipo di statistica
                  sjb.setStatisticsType(Statistics.PERSONAL);
                  // Esecuzione della query
                  pdbc=sjb.getProblemsDistributionByCategory(instance);
                  // Estrazione del risultato
                  iterator=pdbc.iterator();
                  if (iterator.hasNext()){
                      request.setAttribute("okGraph1", "yes");
                      String [] series={"Distribuzione delle segnalazioni per categoria"};
                      Object [] categories=new Object[pdbc.size()];
                      Number [][] data=new Number[1][pdbc.size()];
                      int count=0;
                      int i=0;
                      while (iterator.hasNext()){
                          ProblemsDistributionByCategoryDTO pdbcDTO=(ProblemsDistributionByCategoryDTO) iterator.next();
                          categories[i]=pdbcDTO.getNomeCategoria();
                          data[0][i++]=new Integer(pdbcDTO.getCount());
                          count=count+pdbcDTO.getCount();
                      }
                      CategoryDataset cd=new DefaultCategoryDataset(series,categories,data);
                      HorizontalBarChartConf horizontalBarChartConf=new HorizontalBarChartConf("Statistica personale",HorizontalBarChartConf.createPaint(chartColors),Color.white,"Categorie","Numero segnalazioni",cd,true,false,false,750);
                      String ConfObjectName1="HorizontalBarChartConfPers";
                      session.setAttribute(ConfObjectName1,horizontalBarChartConf);
                  }
			  }
              if(profile==2 || profile==3){
                  sjb.setStatisticsType(Statistics.GENERAL);
                  // Esecuzione della query
                  pdbc=sjb.getProblemsDistributionByCategory(instance);
                  // Estrazione del risultato
                  iterator=pdbc.iterator();
                  if (iterator.hasNext()){
                      request.setAttribute("okGraph2", "yes");
                      String [] series={"Distribuzione delle segnalazioni per categoria"};
                      Object [] categories=new Object[pdbc.size()];
                      Number [][] data=new Number[1][pdbc.size()];
                      int count=0;
                      int i=0;
                      while (iterator.hasNext()){
                          ProblemsDistributionByCategoryDTO pdbcDTO=(ProblemsDistributionByCategoryDTO) iterator.next();
                          categories[i]=pdbcDTO.getNomeCategoria();
                          data[0][i++]=new Integer(pdbcDTO.getCount());
                          count=count+pdbcDTO.getCount();
                      }
                      CategoryDataset cd=new DefaultCategoryDataset(series,categories,data);
                      HorizontalBarChartConf horizontalBarChartConf=new HorizontalBarChartConf("Statistiche generali",HorizontalBarChartConf.createPaint(chartColors),Color.white,"Categorie","Numero segnalazioni",cd,true,false,false,750);
                      String ConfObjectName2="HorizontalBarChartConfGen";
                      session.setAttribute(ConfObjectName2,horizontalBarChartConf);
                  }
              }
              return mapping.findForward("StatisticheDistribuzionePerCategoria");
		}
		return mapping.findForward("welcome");
	}
}

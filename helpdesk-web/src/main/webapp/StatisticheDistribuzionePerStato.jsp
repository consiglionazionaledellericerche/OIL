<%--
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
 --%>
<html><!-- InstanceBegin template="/Templates/OilInternalPage.dwt" codeOutsideHTMLIsLocked="true" -->

<head>

<!-- #BeginEditable "TagLib" -->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!-- #EndEditable --> 
<!-- #BeginEditable "ErrorPage" -->
<%@ page errorPage="/app/OilErrorPage.do" %>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<% String proj = session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<% String skName = request.getContextPath()+"/skins/" + session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<link rel="stylesheet" href="<%=skName%>/OilStyles.css" type="text/css">
<!-- #BeginEditable "doctitle" -->
<title><bean:message key="applicationName" /> - <bean:message key="pageTitles.ProblemDistributionByState" /></title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="it.cnr.helpdesk.StatisticsManagement.valueobjects.*,java.util.*,it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings" %>
<jsp:useBean id="problema" scope="page" class="it.cnr.helpdesk.ProblemManagement.javabeans.Problem" />
<!-- #EndEditable --> 

<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
  		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
    				<td width="132"><img src="<%=skName%>/NormalHeaderLeft.gif"></td>
    				<td width="126" background="<%=skName%>/NormalHeaderCenter.gif" >&nbsp;</td>
    				<td background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"> 
      					<!-- #BeginEditable "SectionTitle" --><bean:message key="pageTitles.Statistics" /><!-- #EndEditable -->
					</td>
    				<td  width="258" align="right"><img src="<%=skName%>/NormalHeaderRight1.gif" ></td>		
  				</tr>
			</table>
		</td>
		<td width="23"><img src="<%=skName%>/NormalHeaderRight2.gif" width="23" height="51"></td>
		</tr>
  <tr> 
    <td > 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
                    <td width="60%"><span class="navigation"><bean:message key="navigation.starter" /> <!-- #BeginEditable "Navigation" -->
						<a href="Home.do" class="navigation"><bean:message key="navigation.Home" /></a>
            			--&gt; Distribuzione per stato<!-- #EndEditable --></span> 
          </td>
          <td width="40%" align="right" class="navigation"><a href="Switch.do"><bean:message key="navigation.switch" /></a> | <a  href="Logout.do"><span class="navigation"><bean:message key="navigation.logout" bundle="<%=proj%>" /></span></a></td>
        </tr>
      </table>
    </td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td > 
      <table width="100%">
        <tr> 
          <td valign="top" height="400" ><!-- #BeginEditable "Content" -->
				        <table width="100%" border="0" cellpadding="2" cellspacing="0">
				            <tr>
						        <td width="150" align="center" class="bgheader"><span class="tab">Distribuzione per stato</span> 
				                 </td>
				                <td width="1"></td>
				                <td width="150" align="center" class="bgdisable"><a href="StatisticheDistribuzionePerCategoria.do" class="tab">Distribuzione 
				                  per categoria</a></td>
				                <td width="1"></td>
				                <td width="150"  align="center" class="bgdisable"><a href="StatisticheTempiRisposta.do" class="tab">Tempi 
				                  di risposta</a></td>
				                <td width="1"></td>
				                <td >&nbsp;</td>
				            </tr>
				        </table>
	        			<table width="100%" border="3" class="bordertab" >
				            <tr>
								<td valign="top" height="400">
<%
							String prof = request.getAttribute("prof").toString();
							String okGraph1 = request.getAttribute("okGraph1").toString();
							String tot1 = request.getAttribute("tot1").toString();
							String ConfObjectName1="PieChartConf.DistrPers";
							String now=request.getAttribute("now").toString();
							Collection c=(Collection)request.getAttribute("stats1");
							Iterator i=c.iterator();
%>
					                <table width="100%" border="0" cellspacing="3" cellpadding="3">
					                	<tr>
								        	<td colspan="2" class="paragraphtitle">
<%
							if  (prof.equals("3")) {
%>
				   								Distribuzione delle  segnalazioni per stato
<%
							} else {
%>
											   Distribuzione delle tue segnalazioni per stato
<%
							}
%>
				   							</td>
	                    				</tr>
	              						<tr>
							                <td width="50%" align="left" valign="center">
							                    <table width="300">
							                        <tr class="bgheader"> 
							                            <td width="30%" class="text">
							                            	<font color="#FFFFFF">Stato</font>
							                            </td>
							                            <td align="center" class="text">
							                            	<font color="#FFFFFF">Segnalazioni</font>
							                            </td>
							                    	</tr>
<%
ArrayList status = new ArrayList();
Vector stateNames = Settings.getId2DescriptionStateMapping(session.getAttribute("it.cnr.helpdesk.instance").toString());
status.add("xxxx");
status.addAll(stateNames);
/*status.add("Open");
status.add("Working");
status.add("External");
status.add("Closed");
status.add("Verified");*/
						while (i.hasNext()) {
							ProblemsDistributionByStatusDTO pdbsDTO=(ProblemsDistributionByStatusDTO) i.next();
%>
							                        <tr>
							                            <td width="60%" class="text" align="left"><%=problema.getFormatStatus(status.indexOf(pdbsDTO.getStatus()),"&#9608; "+pdbsDTO.getStatus())%></td>
							                            <td width="40%" class="text" align="center"><%=pdbsDTO.getCount()%></td>
							                        </tr>
<%
						}
%>
							                        <tr>
							                            <td colspan="2">
							                        		<hr width="100%">
							                      		</td>
							                        </tr>
							                        <tr>
							                            <td class="text" align="left">Totale</td>
							                            <td class="text" align="center"><span class="text"><%=tot1%></span></td>
							                        </tr>
							                    </table>
							                    <br>
												<span class="copyinfo"><font color="red">Nota</font>: nel grafico non è
												riportato il numero delle segnalazioni in stato &quot;Verified&quot;
												perché, in condizioni normali, è molto maggiore del numero di
												segnalazioni che si trovano in qualunque altro stato. </span>
											</td>
<%
					if (!okGraph1.equals("yes")){
%>
							                <td valign="top" align="center"><span class="information"><b>Grafico non prodotto.</b><br>
												Motivo: non hai ancora sottomesso segnalazioni o le tue segnalazioni sono tutte in stato "Verified".<br>
												Le segnalazioni in stato "Verified" non vengono considerate nel grafico della distribuzione
												delle segnalazioni per stato (vedi <font color="red">nota</font>).</span>
<%
					} else {
%>
											<td align="right" valign="top">
												<img  src="<%=request.getContextPath() + "/app/PieChart?now=" + now + "&ConfObject=" + ConfObjectName1 %>" >
							                        <%}%>
							                </td>
										</tr>
									</table>
							        <br>
								</td>
							</tr>
<% 
				if (prof.equals("2")){
%>
							<tr>
								<td valign="top" height="400">
<%
                String okGraph2 = request.getAttribute("okGraph2").toString();
				String tot2 = request.getAttribute("tot2").toString();
				String ConfObjectName2="PieChartConf.DistrGen";
				c=(Collection)request.getAttribute("stats2");
				i=c.iterator();
%>
									<table width="100%" border="0" cellspacing="3" cellpadding="3">
										<tr>
											<td colspan="2" class="paragraphtitle">
										   		Distribuzione generale delle segnalazioni per stato
										   </td>
							            </tr>
							            <tr>
							                <td width="50%" align="left" valign="center">
							                    <table width="300">
							                        <tr class="bgheader"> 
							                            <td width="30%">
							                            	<font color="#FFFFFF">Stato</font>
							                            </td>
							                            <td align="center">
							                            	<font color="#FFFFFF">Segnalazioni</font>
							                            </td>
							                    	</tr>
<%
				while (i.hasNext()) {
					ProblemsDistributionByStatusDTO pdbsDTO=(ProblemsDistributionByStatusDTO) i.next();
%>
							                        <tr>
							                            <td width="60%" class="text" align="left"><%=problema.getFormatStatus(status.indexOf(pdbsDTO.getStatus()),"&#9608; "+pdbsDTO.getStatus())%></td>
							                            <td width="40%" class="text" align="center"><%=pdbsDTO.getCount()%></td>
							                        </tr>
<%
				}
%>
							                        <tr>
							                            <td colspan="2">
							                        		<hr width="100%">
							                        	</td>
							                        </tr>
							                        <tr>
							                            <td class="text" align="left">Totale</td>
							                            <td class="text" align="center"><span class="text"><%=tot2%></span></td>
							                        </tr>
							                    </table>
							                    <br>
							          			<span class="copyinfo"><font color="red">Nota</font>: nel grafico non è
							                    riportato il numero delle segnalazioni in stato &quot;Verified&quot;
							                    perché, in condizioni normali, è molto maggiore del numero di
							                    segnalazioni che si trovano in qualunque altro stato.</span>
											</td>
<%
				if (!okGraph2.equals("yes")){
%>
							                <td valign="top"><span class="information">
												Grafico non prodotto.<br>
												Motivo: non hai ancora sottomesso segnalazioni o le tue segnalazioni sono tutte in stato "Verified".<br>
												Le segnalazioni in stato "Verified" non vengono considerate nel grafico della distribuzione
												delle segnalazioni per stato (vedi <font color="red">nota</font>).</span></td>
<%
				} else {
%>
							                <td align="right" valign="top">
												<img  src="<%=request.getContextPath() + "/app/PieChart?now=" + now + "&ConfObject=" + ConfObjectName2 %>" alt="">
											</td>
<%
				}
%>
										</tr>
							        </table>
							        <br>
								</td>
							</tr>
<%}%>
						</table>
<!-- #EndEditable --> </td>
        </tr>
      </table>
    </td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td > 
      <hr width="100%" align="center">
      <table border="0" width="100%" align="center">
        <tr> 
          <td width="20%" valign="top"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><img border="0" src="<%=skName%>/OilMini.gif" width="48" height="31"></font></td>
          <td width="60%" align="center"valign="middle"><font class="copyinfo"><bean:message key="footer.applicationName" bundle="<%=proj%>" /> 
          <bean:message key="version" /></font></td>
          <td width="20%" align="right"><a href="#" onClick="window.open('Contatti.do','Contatti','height=350,width=400,scrollbars=yes,toolbar=no,location=no,status=no,resizable=yes,menubar=no,screenX=100,screenY=20,top=20,left=100')"><font  class="copyinfo"><bean:message key="links.contactUs" bundle="<%=proj%>" />
            </font></a></td>
          <td >&nbsp;</td>
        </tr>
      </table>
    </td>
    <td>&nbsp;</td>
  </tr>
</table>

</body>

<!-- InstanceEnd --></html>

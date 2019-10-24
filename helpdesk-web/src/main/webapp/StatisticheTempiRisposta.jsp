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
<!-- #BeginEditable "ErrorPage" --><%@ page errorPage="/app/OilErrorPage.do" %><!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<% String proj = session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<% String skName = request.getContextPath()+"/skins/" + session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<link rel="stylesheet" href="<%=skName%>/OilStyles.css" type="text/css">
<!-- #BeginEditable "doctitle" -->
<title><bean:message key="applicationName" /> - <bean:message key="pageTitles.ServiceResponseTimes" /></title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" --><!-- #EndEditable --> 

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
            --&gt; Statistiche tempi di risposta<!-- #EndEditable --></span> 
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
		        <td width="150" align="center" class="bgdisable"><a href="StatisticheDistribuzionePerStato.do" class="tab">Distribuzione 
                  per stato</a></td>
				 <td width="1"></td>
		        <td width="150" align="center" class="bgdisable"><a href="StatisticheDistribuzionePerCategoria.do" class="tab">Distribuzione 
                  per categoria</a></td>
				 <td width="1"></td>
		        <td width="150"  align="center" class="bgheader"><div class="tab">Tempi 
                    di risposta</div></td>
				<td width="1"></td>
		        <td >&nbsp;</td>
	</tr>
</table>
            <table width="100%" border="3" class="bordertab">
<%
			String prof = request.getAttribute("prof").toString();
			String now=request.getAttribute("now").toString();
	if(prof.matches("1|2")){
			String maxT1 = request.getAttribute("maxT1").toString();
			String minT1 = request.getAttribute("minT1").toString();
			String avgT1 = request.getAttribute("avgT1").toString();
			String chartConfName1 = "MeterChart.Personal";
	
%>
	<tr>
          <td height="400" valign="top">
            <table width="100%" border="0" cellspacing="3" cellpadding="3">
              <tr>
<%
		if (prof.equals("1")){%>
                <td colspan="2" class="paragraphtitle">Tempi
                  di risposta alle tue segnalazioni</td>
<%		}
		if (prof.equals("2")){%>
                <td colspan="2" class="paragraphtitle">Tempi
                  di risposta personali</td>
<%		}%>
              </tr>
              <tr>
                <td width="50%">
                  <table width="300" border="0" cellspacing="2" cellpadding="2" class="text">
                          <tr>
                      <td width="20%" class="text">&nbsp;</td>
                            <td class="bgheader" align="center"><font color="#FFFFFF">giorni:ore:minuti:secondi</font></td>
                    </tr>
                    <tr>
                            <td class="bgheader"><font color="#FFFFFF">min</font></td>
                      <td class="text" align="center"><%=minT1%></td>
                    </tr>
                    <tr>
                            <td class="bgheader"><font color="#FFFFFF">max</font></td>
                      <td class="text" align="center"><%=maxT1%></td>
                    </tr>
                    <tr>
                            <td class="bgheader"><font color="#FFFFFF">medio</font></td>
                      <td class="text" align="center"><%=avgT1%></td>
                    </tr>
                  </table>
                </td>
                <td align="right"><img src="<%=request.getContextPath() + "/app/MeterChart?now=" + now + "&ChartConf="+ chartConfName1%>"></td>
              </tr>
            </table>
		</td>
	</tr>
<%}
if(prof.matches("2|3")){
%>
	<tr>
		<td height="400" valign="top">
<%
			String maxT2 = request.getAttribute("maxT2").toString();
			String minT2 = request.getAttribute("minT2").toString();
			String avgT2 = request.getAttribute("avgT2").toString();
			String chartConfName2 = "MeterChart.General";
%>
            <table width="100%" border="0" cellspacing="3" cellpadding="3">
              <tr>
                <td colspan="2" class="paragraphtitle">Tempi
                  di risposta del servizio</td>
              </tr>
              <tr>
                <td width="50%">
                  <table width="300" border="0" cellspacing="2" cellpadding="2">
                          <tr>
                      <td width="20%" class="text">&nbsp;</td>
                            <td class="bgheader" align="center"><font color="#FFFFFF">giorni:ore:minuti:secondi</font></td>
                    </tr>
                    <tr>
                            <td class="bgheader"><font color="#FFFFFF">min</font></td>
                      <td class="text" align="center"><%=minT2%></td>
                    </tr>
                    <tr>
                            <td class="bgheader"><font color="#FFFFFF">max</font></td>
                      <td class="text" align="center"><%=maxT2%></td>
                    </tr>
                    <tr>
                            <td class="bgheader"><font color="#FFFFFF">medio</font></td>
                      <td class="text" align="center"><%=avgT2%></td>
                    </tr>
                  </table>
                </td>
                <td align="right"><img src="<%=request.getContextPath() + "/app/MeterChart?now=" + now + "&ChartConf="+ chartConfName2%>"></td>
              </tr>
            </table>
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

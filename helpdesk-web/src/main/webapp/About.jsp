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
<title>Online interactive helpdesk About</title>
<meta name="keywords" content="Help Desk, CRM, Customer Care, Andrea Bei, Roberto Puccinelli, Oil, A.Bei, R.Puccinelli, Bei, Puccinelli">
<meta name="Authors" content="Andrea Bei & Roberto Puccinelli">
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->
<style type="text/css">
<!--
.cornice {
	border: 1px groove #000000;
	background-color: #B1C4DC;
}
-->
</style>

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
      					<!-- #BeginEditable "SectionTitle" --> 
            About<!-- #EndEditable -->
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
                    <td width="60%"><span class="navigation"><bean:message key="navigation.starter" /> <!-- #BeginEditable "Navigation" --><a href="javascript:history.back()" class="navigation">Home</a> 
            --&gt; About<!-- #EndEditable --></span> 
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
		  <table width="800" align="center"  cellpadding="5" cellspacing="0" class="cornice">
		  	<tr>
				<td align='center'>
            		<img border="0" src="<%=skName%>/logoOIL.gif"></td></tr>
              <tr><td>
              	<p class='text'><font size='5' color='#FFFF00'><strong>Oil</strong></font> &egrave; uno strumento web-based per il supporto alle attività di Help Desk di 
              servizi e applicazioni.
               </p>
           
            <p> <span class="title">Architettura</span> 
                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                    <tr> 
                      <td class="bgheader"><span class="tab">Layer</span></td>
                      <td class="bgheader"><span class="tab">Tecnologie</span></td>
                      <td class="bgheader"><span class="tab">Design Pattern</span></td>
              </tr>
              <tr> 
                <td class="text">Presentation</td>
                <td class="text">Servlet, JSP, <span class="text">JavaScript</span></td>
                <td class="text">Model View Controller</td>
              </tr>
              <tr> 
                <td class="text">Application</td>
                <td class="text">Java Beans</td>
                <td class="text">Business Delegate, PageByPage Iterator</td>
              </tr>
              <tr> 
                <td class="text">Services</td>
                <td class="text">EJB Session Stateless<span class="text">, JavaMail, 
                  XML</span></td>
                <td class="text">Session Facade, Data Transfer Object</td>
              </tr>
              <tr> 
                <td class="text">Domain</td>
                <td class="text">Java Bean</td>
                <td class="text">Data Access Object</td>
              </tr>
              <tr> 
                <td class="text">Persistence</td>
                <td class="text">DBMS</td>
                <td class="text">&nbsp;</td>
              </tr>
            </table>
                
            <p><span class="title">Piattaforme Supportate<br>
              </span><span class="text">Application Server J2EE: Weblogic, JBoss&nbsp;<br>
              RDBMS: Oracle, Postgres</span></p>        
            <p class="text">&nbsp;</p>  
			</td>
			</tr>
			<tr>
            <td>
            <p class="text"><span class="blurredtab">Basato su un progetto di (in ordine alfabetico):</span><span class="title"><br>
                 </span> <a href="mailto:andrea.bei@libero.it"><font  class="copyinfo">Ing.
                 Andrea Bei</font></a><br>
            <a href="mailto:roberto.puccinelli@cnr.it"><font  class="copyinfo">Ing.
            Roberto Puccinelli</font></a></p>
                                    <p class="text"><span class="blurredtab">La versione attuale, frutto di una rivisitazione architetturale e dell'aggiunta di nuove funzionalit&agrave;, &egrave; curata da (in ordine alfabetico):</span><br>
                                        <span class="copyinfo"><a href="mailto:roberto.puccinelli@cnr.it">Ing. Roberto Puccinelli</a><br>
                                        <a href="mailto:aldo.stentella@amministrazione.cnr.it">Dott. Aldo Stentella Liberati</a></span></p>
            </td>
            </tr>
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

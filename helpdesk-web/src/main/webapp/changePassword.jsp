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
<title>Online interactive helpdesk - Modifica Password</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*,it.cnr.helpdesk.util.ApplicationProperties,it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings" %>

<!-- #EndEditable --> 

<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
  		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
    				<td width="132"><img src="<%=skName%>/NormalHeaderLeft.gif"></td>
    				<td width="126" background="<%=skName%>/NormalHeaderCenter.gif" >&nbsp;</td>
    				<td background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"> 
      					<!-- #BeginEditable "SectionTitle" --> 
            				<img src="<%=skName%>/titles/ModificaPassword.gif">
						<!-- #EndEditable -->
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
            	--&gt; Modifica Password <!-- #EndEditable --></span> 
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
<%
String param="" + request.getAttribute("param");
		//Properties props= ApplicationProperties.getApplicationProperties();
		//String intPasswordManagement = (String)props.get("it.oil.intPasswordManagement");
		//String extPasswordSite = (String)props.get("it.oil.extPasswordSite");
		String intPasswordManagement = Settings.getProperty("it.oil.intPasswordManagement", session.getAttribute("it.cnr.helpdesk.instance").toString());
		String extPasswordSite = Settings.getProperty("it.oil.extPasswordSite", session.getAttribute("it.cnr.helpdesk.instance").toString());
if (param.equals("close")) {     
%>
			<table width="100%" cellpadding="2" cellspacing="2" class="bordertab">
				<tr><td class="information">Password modificata<br></td></tr>
				<tr><td align='center'><img src='<%=skName%>/buttons/b-home.gif' alt='home' onclick="window.location='Home.do';"></td></tr>
            </table>
<%
	} else if (intPasswordManagement.equalsIgnoreCase("disabled")) {
%>
			<table width="100%" border="3" class="bordertab">
              <tr>
				<td align="center" class="text">
				<p><bean:message key="texts.Password.warning" bundle="<%=proj%>" /></p>
				<p><a href="<%=extPasswordSite%>" target='_blank'><%=extPasswordSite%></a></p>
				<p><img src='<%=skName%>/buttons/b-home.gif' alt='Home' onClick="window.location='Home.do';"></p></td>
			  </tr>
			</table>
<%	
	
	} else {
%>		  
          
            <table width="100%" border="0" cellpadding="2" cellspacing="0">
              <tr>
		        <td width="150" align="center" class="bgdisable"><a href="GetUpdateUserInfo.do" class="tab">Modifica 
                  Info</a></td>
				 <td width="1"></td> 
		        <td width="180"  align="center" class="bgheader"><span class="tab">Modifica 
                  Password </span></td>
		        <td >&nbsp;</td>				
	</tr>
</table>
            <table width="100%" border="3" class="bordertab">
              <tr>
<td>
<html:form action="/app/ChangePassword">
<html:errors/>
<table width="100%" cellspacing=2 border=0 cellpadding=2 align="center">
  <tr>
    <td width="50%" height="50%" align="right" class="text"><bean:message key="labels.OldPassword" /></td>
    <td width="50%" height="50%" align="left">
    <html:password property="oldpwd" size="30" maxlength="30" alt="Digitare la vecchia password in questa casella" tabindex="1" /></td>
  </tr>
  <tr>
    <td width="50%" align="right" class="text"><bean:message key="labels.NewPassword" /></td>
    <td width="50%" align="left">
      <html:password property="newpwd" size="30" maxlength="30" alt="Digitare la nuova password in questa casella" tabindex="2" />
    </td>
  </tr>
  <tr>
    <td width="50%" align="right" class="text"><bean:message key="labels.NewPassword2" /></td>
    <td width="50%" align="left">
    <html:password property="newpwd2" size="30" maxlength="30" alt="Digitare la nuova password in questa casella" tabindex="3" />
    </td>
  </tr>
  
</table>
<div align="center">
<input type='image' src='<%=skName%>/buttons/b-ok.gif' alt='Ok'>&nbsp;&nbsp;&nbsp;
<img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onclick="window.location='Home.do';">
</div>
</html:form>
</td>
</tr>
</table>
<%    
	}
%>
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

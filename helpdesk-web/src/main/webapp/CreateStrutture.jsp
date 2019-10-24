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
<title>Online interactive helpdesk Crea Struttura</title>
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
      					<!-- #BeginEditable "SectionTitle" --> 
Gestione Strutture
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
             --&gt;  <bean:message key="navigation.CreateStrutture" />
<!-- #EndEditable --></span> 
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
String param=(String)request.getAttribute("param");
if(param!=null){
	if(param.equals("close")){
%>
<table align='center' class="bordertab" bordercolor="#FFFF00">
	<tr><td><span class="information">Inserimento effettuato:</span><br></td></tr>
	<tr><td><div class="text">Struttura</div></td></tr>
	<tr><td><input type="text" size="80" name="descr" maxlength="100" value="<%=request.getAttribute("struttura") %>" disabled /></td></tr>
	<tr><td><div class="text">Acronimo</div></td></tr>
	<tr><td><input type="text" name="acron" size="10" maxlength="10" value="<%=request.getAttribute("acronimo") %>" disabled /></td></tr>
	<tr><td align='center'><img src='<%=skName%>/buttons/b-chiudi.gif' onClick="window.location='ManageStrutture.do';">
		</td></tr>
</table>
<%
	}
}else {
%>
     <table width="100%" border="0" cellpadding="2" cellspacing="0" class="text">
      <tr>
        <td width="150" align="center" class="bgheader"><div class="tab">
		<bean:message key="tabs.CreateStrutture" /></div></td>
		<td width="1"></td> 
        <td width="150" align="center" class="bgdisable"><a href="ManageStrutture.do" class="tab">
		<bean:message key="tabs.ManageStrutture" /></a> </td>
		<td width="1"></td> 
		<td>&nbsp;</td>				
	  </tr>
     </table>
     <table width="100%" border="3" class="bordertab">
       <tr>
		<td>	
		<form name="AggiuntaStruttura" method="POST" action="CreateStrutture.do">
        <table border="0" width="100%" cellpadding="2" cellspacing="2">
          <tr> 
            <td class="bgheader" width="25%"> 
              <div class="tableheader">Descrizione</div></td>
            <td width="75%"> 
              <input  type="text" size="80" name="descr" /></td>
          </tr>
          <tr> 
            <td class="bgheader" width="25%"> 
              <div class="tableheader">Acronimo</div></td>
            <td width="75%"> 
              <input type="text" name="acron" size="40" /></td>
          </tr>
          <tr> 
            <td class="bgheader" width="25%"> 
              <div class="tableheader">Evidenza</div></td>
            <td width="75%">
                <input type="radio" name="evid" value="y">si
                <input type="radio" checked name="evid" value="n">no
            </td>
          </tr>
          <tr> 
            <td class="bgheader" width="10%"> 
              <div class="tableheader">Abilitato</div></td>
            <td width="90%"> 
              <input checked type="radio" name="en" value="y">si
                <input type="radio" name="en" value="n">no
			</td>
          </tr>
        </table>
		<p align="center"><input type="image" src='<%=skName%>/buttons/b-ok.gif' alt="Ok" name="Ok">
		&nbsp;&nbsp;<img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onClick="window.close();">
		</p>
	</form>
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

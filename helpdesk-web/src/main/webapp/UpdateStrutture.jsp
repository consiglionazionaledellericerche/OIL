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
<html><!-- InstanceBegin template="/Templates/OilDetailPage.dwt" codeOutsideHTMLIsLocked="true" -->

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
<title>Online interactive helpdesk Modifica Struttura</title>
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
    				<td nowrap background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"> 
      					<!-- #BeginEditable "SectionTitle" --> 
            Modifica Struttura<!-- #EndEditable -->
					</td>
    				<td  width="258" align="right"><img src="<%=skName%>/NormalHeaderRight1.gif" ></td>		
  				</tr>
			</table>
		</td>
		<td width="23"><img src="<%=skName%>/NormalHeaderRight2.gif" width="23" height="51"></td>
		</tr>
  <tr> 
    <td >

	</td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
        <td ><!-- #BeginEditable "Detail" -->

<%
String param=(String)request.getAttribute("param");
if (param!=null){
	if(param.equals("close")){ 
%>
<table align='center' class="bordertab">
	<tr><td><span class="information">Aggiornamento effettuato:</span><br></td></tr>
	<tr><td><div class="text">Struttura</div></td></tr>
	<tr><td><input  type="text" size="80" name="descr" value="<%= request.getParameter("descrizione") %>" disabled /></td></tr>
	<tr><td><div class="text">Acronimo</div></td></tr>
	<tr><td><input type="text" name="Acron" size="40" value="<%= request.getParameter("acronimo") %>" disabled /></td></tr>
	<tr><td align='center'><br><img src='<%=skName%>/buttons/b-chiudi.gif' onClick="window.opener.location.reload(); window.close();">
		</td></tr>
</table>
<%
	}
}
else {
%>
<form name="ModificaStruttura" method="POST" action="UpdateStrutture.do">
        <table border="0" width="100%" cellpadding="2" cellspacing="2">
          <tr> 
            <td class="bgheader" width="10%"> 
              <div class="tableheader">Struttura</div></td>
            <td width="90%"> 
              <input type='hidden' name='cod' value='<%= request.getParameter("cod") %>'>
			  <input type='hidden' name='evidenza' value='<%= request.getAttribute("evidenza") %>'>
			  <input type='hidden' name='enabled' value='<%= request.getAttribute("enabled") %>'>
              <input type="text" size="80" maxlength="100" name="descrizione" value="<%= request.getAttribute("descrizione") %>" />
    		</td>
    		 </tr>
    		<tr> 
    	<td class="bgheader" width="10%"> 
              <div class="tableheader">Acronimo</div></td>
            <td width="90%"> 
              <input type="text" name="acronimo" size="10" maxlength="10" value="<%= request.getAttribute("acronimo") %>" />
     </td>	
  </tr>
</table>
<p align="center"><input type="image" src='<%=skName%>/buttons/b-ok.gif' alt="Ok" name="Ok">
	&nbsp;&nbsp;<img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onClick="window.close();">
</p>
</form>
<%
	}
%>
	<!-- #EndEditable -->
    </td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td align="center"><!-- #BeginEditable "Buttons" --><!-- #EndEditable --></td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td> 
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
     <td >&nbsp;</td>
  </tr>
</table>

</body>

<!-- InstanceEnd --></html>

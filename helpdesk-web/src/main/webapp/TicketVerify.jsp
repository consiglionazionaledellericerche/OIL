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
<html><!-- InstanceBegin template="/Templates/OilPublicPage.dwt" codeOutsideHTMLIsLocked="true" -->
<head>

<!-- #BeginEditable "TagLib" -->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<% String proj = session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<% String skName = request.getContextPath()+"/skins/" + session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<link rel="stylesheet" href="<%=skName%>/OilStyles.css" type="text/css">
<META HTTP-EQUIV="Expires" CONTENT="Fri, Jan 01 1900 00:00:00 GMT">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<!-- InstanceBeginEditable name="doctitle" -->
<title><bean:message key="applicationName" bundle="<%=proj%>" /></title>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="javascripts" -->   
<!-- InstanceEndEditable -->
</head>
<body>
<!-- #BeginEditable "Imports" --><!-- #EndEditable --> 
<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="132"><img src="<%=skName%>/NormalHeaderLeft.gif"></td>
          <td width="126" background="<%=skName%>/NormalHeaderCenter.gif" >&nbsp;</td>
          <td background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"><!-- InstanceBeginEditable name="Title" -->Pagina di conferma<!-- InstanceEndEditable --></td>
          <td  width="258" align="right"><img src="<%=skName%>/NormalHeaderRight1.gif" ></td>
        </tr>
      </table></td>
    <td width="23"><img src="<%=skName%>/NormalHeaderRight2.gif" width="23" height="51"></td>
  </tr>
  <tr>
    <td >&nbsp;</td>
    <td >&nbsp;</td>
  </tr>
  <tr>
    <td ><!-- InstanceBeginEditable name="Content" -->
		<bean:message name="verifyMsg" />
<!-- InstanceEndEditable --></td>
    <td >&nbsp;</td>
  </tr>
  <tr>
    <td align="center">&nbsp;</td>
    <td >&nbsp;</td>
  </tr>
  <tr>
    <td><hr width="100%" align="center">
      <table border="0" width="100%" align="center">
        <tr>
          <td width="20%" valign="top"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><img border="0" src="<%=skName%>/OilMini.gif" width="48" height="31"></font></td>
          <td width="60%" align="center"valign="middle"><font class="copyinfo">
            <bean:message key="footer.applicationName" bundle="<%=proj%>" />
            <bean:message key="version" />
            </font></td>
          <td width="20%" align="right"><a href="#" onClick="window.open('Contatti.do','Contatti','height=350,width=400,scrollbars=yes,toolbar=no,location=no,status=no,resizable=yes,menubar=no,screenX=100,screenY=20,top=20,left=100')"><font  class="copyinfo">
            <bean:message key="links.contactUs" bundle="<%=proj%>" />
            </font></a></td>
          <td >&nbsp;</td>
        </tr>
      </table></td>
    <td >&nbsp;</td>
  </tr>
</table>
</body>
<!-- InstanceEnd --></html>

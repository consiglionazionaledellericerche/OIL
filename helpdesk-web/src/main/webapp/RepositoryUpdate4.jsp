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
<html><!-- InstanceBegin template="/Templates/OilDetailBottomFrame.dwt" codeOutsideHTMLIsLocked="true" -->
<head>

<!-- #BeginEditable "TagLib" -->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!-- #EndEditable --> 
<!-- #BeginEditable "ErrorPage" --><%@ page errorPage="/app/OilErrorPage.do" %><!-- #EndEditable -->
<% String proj = session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<% String skName = request.getContextPath()+"/skins/" + session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<!-- #BeginEditable "doctitle" -->
    <title><bean:message key="applicationName"  bundle="<%=proj%>" /></title>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=skName%>/OilStyles.css" type="text/css">
</head>

<body bgcolor="#FFFFFF" text="#000000">
<table width="100%">
  <tr> 
    <td> 
      <hr width="100%" align="center">
      <table border="0" width="100%" align="center">
        <tr> 
          <td width="20%" valign="top"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><img border="0" src="<%=skName%>/OilMini.gif" width="48" height="31"></font></td>
          <td width="60%" align="center"valign="top"><!-- #BeginEditable "Buttons" --><!-- #EndEditable --></td>
          <td width="20%" align="right" valign="top"><img border="0" src="<%=skName%>/OilMini.gif" width="48" height="31"></td>
          <td >&nbsp;</td>
        </tr>
      </table>
    </td>
    <td >&nbsp;</td>
  </tr>
</table>
</body>
<!-- InstanceEnd --></html>

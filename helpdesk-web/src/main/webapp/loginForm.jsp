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
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<% String proj = session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<title><bean:message key="applicationName" /> - <bean:message key="pageTitles.loginForm" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<% String skName = request.getContextPath()+"/skins/" + session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<link rel="stylesheet" href="<%=skName%>/OilStyles.css" type="text/css">
</head>
<body bgcolor="#FFFFFF">
<table align="center" border=0 cellpadding=0 cellspacing=0 width=630>
  <tr>
    <td width="173" height="184" rowspan="2" valign="top" ><img src="<%=skName%>/Oil2Animato.gif" width="173" height="184"></td>
    <td width="427" height="84"><img src="<%=skName%>/OilHeader3.gif" width="457" height="84"></td>
  </tr>
  <tr>
    <td colspan="2" height="100" valign="middle">
      <p align="center" class="title"><bean:message key="headers.loginForm" bundle="<%=proj%>" /></td>
  </tr>
  <tr><td colspan="2" align="right"><a  href="Logout.do"><span class="navigation"><bean:message key="navigation.logout" bundle="<%=proj%>" /></span></a></td></tr>
  <tr>
    <td colspan="2"><font class="text"><bean:message key="texts.loginForm" /></font> 
      <html:form action="/app/Login">
		<html:errors/>

            <table border="0" width="100%">
                <tr>
                    <td width="50%"></td>
                    <td width="50%"></td>
                </tr>
                <tr>
                    <td width="50%" align="right"><font class="text"><bean:message key="labels.login"/>:</font></td>
                    <td width="50%">
              <html:text property="login" size="50" maxlength="50"/>
            </td>
                </tr>
                <tr>
                    <td width="50%"></td>
                    <td width="50%"></td>
                </tr>
                <tr>
                    <td width="50%" align="right"><font class="text"><bean:message key="labels.password"/>:</font></td>
                    <td width="50%">
              <html:password property="password" size="50" maxlength="32"/>
            </td>
                </tr>
                <tr>
                    <td width="50%"></td>
                    <td width="50%"></td>
                </tr>
                <tr>
                    <td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td width="50%" align="right">
              <input type='image' src='<%=skName%>/buttons/b-accedi.gif' />
                        &nbsp;
            </td>
                    <td width="50%">&nbsp;
              <img src='<%=skName%>/buttons/b-annulla.gif' onclick='self.document.forms[0].reset();'>
            </td>
                </tr>
            </table>
        <p align="center">&nbsp;</p>
      </html:form>
    </td>
  </tr>
</table>

<hr width="630" align="center">
<table border="0" width="630" align="center">
  <tr>
    <td width="20%" valign="top"><img border="0" src="<%=skName%>/OilMini2.gif" width="48" height="31"></td>
      
    <td width="60%" align="center"valign="middle" class="copyright"><font class="copyinfo"><bean:message key="footer.applicationName" /> <bean:message key="version" /></font></td>
    <td width="20%" align="right"><a href="#" onClick="window.open('Contatti.do','Contatti','height=350,width=400,scrollbars=yes,toolbar=no,location=no,status=no,resizable=yes,menubar=no,screenX=100,screenY=20,top=20,left=100')"><font class="copyinfo"><bean:message key="links.contactUs" /></font></a></td>
  </tr>
</table>

</body>
</html>
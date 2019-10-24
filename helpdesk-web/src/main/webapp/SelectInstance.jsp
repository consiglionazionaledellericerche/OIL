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
<title><bean:message key="applicationName" /> - <bean:message key="pageTitles.loginForm" /></title>
<style type="text/css">
<!--
.tabs {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	background-color: #FBDEAA;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #C0D2BD;
	border-right-color: #006600;
	border-bottom-color: #006600;
	border-left-color: #C0D2BD;
	border-spacing: 15px;
}
.tabs td {
	background-color: #F4E8D9;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #666666;
	border-right-color: #eeeeee;
	border-bottom-color: #eeeeee;
	border-left-color: #666666;
}
.tabs img {
	cursor: pointer;
}
-->
</style>
<script type="text/javascript" language="javascript1.4">
<!--
	function openInstance(inst){
		document.forms[0].istanza.value = inst;
		document.forms[0].submit(); 
	}
//-->
</script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<% String skName = request.getContextPath()+"/skins/HDesk/"; %>
<link rel="stylesheet" href="<%=skName%>/OilStyles.css" type="text/css">
</head>
<body bgcolor="#FFFFFF">
<%@ page import = "java.util.*,it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject" %>
<table align="center" border=0 cellpadding=0 cellspacing=0 width=630>
  <tr>
    <td width="173" height="184" rowspan="2" valign="top" ><img src="<%=skName%>/Oil2Animato.gif" width="173" height="184"></td>
    <td width="427" height="84"><img src="<%=skName%>/OilHeader3.gif" width="457" height="84"></td>
  </tr>
  <tr>
    <td colspan="2" height="100" valign="middle">
      <p align="center" class="title"><bean:message key="headers.selectInstance" /></td>
  </tr>
  <tr>
    <td colspan="2"><font class="text"><bean:message key="texts.selectInstance" /></font> 
		<form name="form" action="SelectInstance.do" method="post">
			<input type="hidden" name="istanza" />
		</form>
            <table class="tabs">
<%
	ArrayList<String> instances = (ArrayList<String>)request.getAttribute("instances");
	ArrayList<String> instanceLabels = (ArrayList<String>)request.getAttribute("instanceLabels");
	for(int i = 0; i < instances.size(); i++){
%>
                <tr>
                    <td width="154px"><img src="<%= request.getContextPath()+"/skins/"+instances.get(i) %>/instance.gif" onclick="openInstance('<%= instances.get(i) %>')" /></td>
                    <td width="350px" class="text"><%= instanceLabels.get(i) %></td>
                </tr>
                
<%
	}
%>
            </table>
        <p align="center">&nbsp;</p>
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
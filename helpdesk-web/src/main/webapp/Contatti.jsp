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
<html>
<head>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page errorPage="/app/OilErrorPage.do" %>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<% 
	String proj;
	if(session.getAttribute("it.cnr.helpdesk.instance")!=null)
		proj = session.getAttribute("it.cnr.helpdesk.instance").toString();
	else
		proj = "HDesk";
	String skName = request.getContextPath()+"/skins/" + proj; 
%>
<link rel="stylesheet" href="<%=skName%>/OilStyles.css" type="text/css">
<title><bean:message key="applicationName" /> - Contatti</title>
<script type="text/javascript">
<!--
function sendMail(ind){
	window.open("mailto:"+ind+"?subject=[<%=proj%>]:&body=%0D%0A%0D%0A[<%=proj%>]");
	self.close();
}
//-->
</script>
</head>

<body>
<table width="100%" border="0" cellpadding="5">
    <tr>
        <td colspan="2" align="center"><img src="<%=skName%>/mail.gif" width="98" height="60"></td>
    </tr>
    <tr>
        <td colspan="2" align="center" class="copyinfo"><p>L'indirizzo di riferimento &egrave; <strong><bean:message key="texts.email" bundle="<%=proj%>" /></strong></p>
            <p><bean:message key="texts.Contatti.warning"  bundle="<%=proj%>" /></p><br></td>
    </tr>
    
    
    <tr>
        <td width="50%" align="right"><a href="#" onClick="sendMail('<bean:message key="texts.email" bundle="<%=proj%>" />')"><img src="<%=skName%>/buttons/b-mail.gif" border="0"></a>&nbsp;</td>
        <td width="50%" align="left">&nbsp;<img src="<%=skName%>/buttons/b-chiudi.gif" onClick="self.close();"></td>
    </tr>
</table>
</body>
</html>

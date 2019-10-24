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
<%@ page import ="javax.xml.parsers.DocumentBuilderFactory, org.w3c.dom.Document" %>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<% String proj = session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<% String skName = request.getContextPath()+"/skins/" + session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<% 		
	String alfTicket = (String)session.getAttribute("alfTicket");
	if(alfTicket==null){
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(System.getProperty("it.cnr.oil.externalNotifySystem.login")+"?u=hlpdsk&pw=hkCbZ9yDNzK8");
			session.removeAttribute("alfTicket");
			alfTicket = "?alf_ticket="+doc.getDocumentElement().getTextContent();
			session.setAttribute("alfTicket", alfTicket);
		} catch(Exception e) {
			out.print("<!--no alfresco-->");
		}
	}
	String notifyStatus = System.getProperty("it.cnr.oil.externalNotifySystem.status");
	String url = "";
	String script = "";		
	if(notifyStatus!=null && !notifyStatus.equalsIgnoreCase("disabled") && alfTicket!=null){
		url = System.getProperty("it.cnr.oil.externalNotifySystem.url")+ "/" + proj + alfTicket;
		script = "recuperaNotifiche("+System.getProperty("it.cnr.oil.externalNotifySystem.silentMode")+"); mostraNotifiche(true);";
	}
%>
<script type="text/javascript" src="<%= url %>"></script>
<script type="text/javascript">
	document.onreadystatechange = function () {
		if (document.readyState === "complete") {
			document.body.style.display='block';
		}
	}
</script>

<link rel="stylesheet" href="<%=skName%>/OilStyles.css" type="text/css">
<title><bean:message key="applicationName" /> - 
<%
	User utente=(User)session.getAttribute("it.cnr.helpdesk.currentuser");
	int pr = utente.getProfile();
	if(pr==1) {
%>
<bean:message key="pageTitles.HomeUser" />
<%
	}else if(pr==2) {
%>
<bean:message key="pageTitles.HomeExpert" />
<%
	}else if(pr==3) {
%>
<bean:message key="pageTitles.HomeAdministrator" />
<%
	}else if(pr==4) {
%>
<bean:message key="pageTitles.HomeValidator" />
<%
	}
%>
</title>
</head>
<body style="display:none;" onload="<%= script %>">
<%@ page import ="it.cnr.helpdesk.UserManagement.javabeans.User" %>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
    <td> <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="132"><img src="<%=skName%>/NormalHeaderLeft.gif"></td>
          <td width="126" background="<%=skName%>/NormalHeaderCenter.gif" >&nbsp; </td>
          <td background="<%=skName%>/NormalHeaderCenter.gif"  valign="middle" align="center" class="sectiontitle"> 
            <img src="<%=skName%>/titles/Home.gif"> </td>
          <td  width="258" align="right"><img src="<%=skName%>/NormalHeaderRight1.gif" ></td>
        </tr>
      </table></td>
    <td width="23"><img src="<%=skName%>/NormalHeaderRight2.gif" width="23" height="51"></td>
  </tr>
  <tr> 
    <td  > <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="60%"><span class="navigation"><bean:message key="navigation.starter"/> 
            <bean:message key="navigation.Home" /></span> </td>
          <td width="40%" align="right" class="navigation"><a href="Switch.do"><bean:message key="navigation.switch" /></a> | <a  href="Logout.do"><span class="navigation"><bean:message key="navigation.logout" bundle="<%=proj%>" /></span></a></td>
        </tr>
      </table></td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td >
<%
		String menu = (String)request.getAttribute("menu");
		if(menu!=null)
			out.print(menu);
		else
			out.println("<span class='errorPageText'>Errore nel caricamento della pagina: template XML non trovato</span>");
%>
	
	</td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td align="right">&nbsp;</td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td> <hr width="100%" align="center"> <table border="0" width="100%" align="center">
        <tr> 
          <td width="20%" valign="top"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><img border="0" src="<%=skName%>/OilMini.gif" width="48" height="31"></font></td>
          <td width="60%" align="center"valign="middle"><font class="copyinfo"><bean:message key="footer.applicationName" bundle="<%=proj%>" /> 
            <bean:message key="version" /></font></td>
          <td width="20%" align="right"><a href="#" onClick="window.open('Contatti.do','Contatti','height=350,width=400,scrollbars=yes,toolbar=no,location=no,status=no,resizable=yes,menubar=no,screenX=100,screenY=20,top=20,left=100')"><font  class="copyinfo"><bean:message key="links.contactUs" bundle="<%=proj%>" />
            </font></a></td>
          <td >&nbsp;</td>
        </tr>
      </table></td>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>

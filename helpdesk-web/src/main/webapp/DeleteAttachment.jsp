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
<!-- #BeginEditable "ErrorPage" --><%@ page errorPage="/app/OilErrorPage.do" %><!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<% String proj = session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<% String skName = request.getContextPath()+"/skins/" + session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<link rel="stylesheet" href="<%=skName%>/OilStyles.css" type="text/css">
<!-- #BeginEditable "doctitle" -->
<title><bean:message key="applicationName" /> - Elimina allegato</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->
<script type="text/javascript">
	function openWindow(targetURL) {
		var width = 750;
		var height = 550;
		var screenX = Math.floor((screen.width)/2) - Math.floor(width/2);
		var screenY = Math.floor((screen.height)/2) - Math.floor(height/2) - 20;
		var features =
			"resizable=yes,scrollbars=yes,modal=yes" +
			",top=" + screenY +
			",left=" + screenX +
			",screenX=" + screenX +
			",screenY=" + screenY +
			",width=" + width +
			",height=" + height;
		window.open(targetURL, "popup", features).focus();
		document.getElementById('req').style.visibility = 'visible';
		document.getElementById('req1').style.visibility = 'visible';
	}

</script>
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
      					Elimina Allegato<!-- #EndEditable -->
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
        String conf = request.getParameter("conf");
        if (conf != null) {
%>
<script language="JavaScript">
        <!--
        	opener.location.reload();
        	window.close();
        //-->
</script>
<%
	} else {
		String[] afile = (String[]) request.getAttribute("afile");
%>
<table align="center" width="400px">
	<tr><td class="text">Visualizzare l'allegato per procedere alla cancellazione:
	</td></tr>
	<tr><td class="text">
		<a href="javascript:openWindow('<%= (request.getContextPath() + "/app/download/" + afile[0].replaceAll("'","&#39;") + "?id=" + afile[1]) %>')">
		<img src='<%= skName + "/icons/" + afile[2] %>' border=0 /> <%= afile[0] %></a>
	</td></tr>
<tr><td class="text">
	<div id="req" style="visibility: hidden;">
		Sei sicuro di voler eliminare l'allegato ?
	</div>
</td></tr>
</table>
<!-- #EndEditable -->
    </td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td align="center"><!-- #BeginEditable "Buttons" -->
	<div id="req1" style="visibility:hidden;">
		<a href="DeleteAttachment.do?id=<%=request.getParameter("id")%>&conf=yes"><img src="<%=skName%>/buttons/b-ok.gif" border="0"></a>
	</div>
		&nbsp;&nbsp; <img src="<%=skName%>/buttons/b-annulla.gif" onClick="window.close();">
<%
	}
%>
<!-- #EndEditable --></td>
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

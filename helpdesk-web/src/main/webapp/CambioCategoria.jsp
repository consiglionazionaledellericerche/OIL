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
<title><bean:message key="applicationName" /> - <bean:message key="pageTitles.ChangeCategory" /></title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->
<script>
<!--
var param1='<%=request.getParameter("idCategoria") %>';
var param2='<%=StringEscapeUtils.escapeJavaScript(request.getParameter("nomeCategoria")) %>';
var param3='';
var visib='hidden';
function aggiorna(){
	self.document.forms[0].elements[1].value=param1;
	self.document.forms[0].elements[0].value=param2;
	document.getElementById('cloud').innerHTML=param3;
	if(param3.indexOf('<ul>', 0) > -1 )
		self.document.images["info"].src = "<%=skName%>/info.gif";
	else
		self.document.images["info"].src = "<%=skName%>/warn.gif";
	visib='visible';
}
//-->
</script>
<style type="text/css">
.cloud {
	font-family: Verdana, Geneva, sans-serif;
	font-size: 11px;
	color:#009;
	width: 300px;
	position:absolute;
	left:495px;
	top:125px;
	border: 1px solid #35398C;
	padding: 4px;
	background-color: lightyellow;
	border-top-left-radius: 0px;
	border-top-right-radius: 15px;
	border-bottom-right-radius: 15px;
	border-bottom-left-radius: 15px;
	visibility:hidden;
}
</style>
<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*,org.apache.commons.lang.StringEscapeUtils"%>
<!-- #EndEditable --> 
<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
  		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
    				<td width="132"><img src="<%=skName%>/NormalHeaderLeft.gif"></td>
    				<td width="126" background="<%=skName%>/NormalHeaderCenter.gif" >&nbsp;</td>
    				<td nowrap background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"> 
      					<!-- #BeginEditable "SectionTitle" --> 
            <img src="<%=skName%>/titles/CambiaCategoria.gif"><!-- #EndEditable -->
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
	String param = request.getAttribute("param").toString();
	if(param!=null){
		if(param.equals("close")){
%>
<script language="JavaScript">
        <!--
        	opener.location.reload();
        	window.close();
        //-->
</script>
<%
		} else if(param.equals("form")){
%>		
<html:form action="/app/CambioCategoria">
		<html:errors/>
		<div class="cloud" id="cloud"></div>
        <table border="0" width="100%" cellpadding="2" cellspacing="2">
          <tr> 
            <td width="100" class="bgheader"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="tableheader">Categoria</font></td>      
            <td><html:text property="nomeCategoria" readonly="true" size="50" maxlength="50" /> 
            <img alt="info" name="info" src="<%=skName%>/info.gif" onmouseover="document.getElementById('cloud').style.visibility=visib;" onmouseout="document.getElementById('cloud').style.visibility='hidden';" />
        <A  HREF="#" onClick="window.open('CategoryTree.do','CategoryTree','height=500,width=870,scrollbars=yes,toolbar=no,location=no,screenX=120,screenY=40,top=60,left=140','fullscreen=no')"><FONT  SIZE=1 FACE="Verdana, Arial">
        <bean:message key="labels.SelectCategory" />
        </FONT></A> </td>
	        <td><html:hidden property="idCategoria" />
				<html:hidden property="idSegnalazione" />
      </td>
  </tr>              
          <tr> 
            <td class="bgheader"><div class="tableheader"><bean:message key="Note" /></div></td>
            <td><html:textarea property="nota" rows="10" cols="60" />
    </td>
	        <td>&nbsp;</td>
  </tr>
</table>
<p align="center">
<input type='image' src='<%=skName%>/buttons/b-ok.gif' alt='Ok'>&nbsp;&nbsp;&nbsp;
<img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onclick="window.close();">
</p>

</html:form> 

<%		}
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

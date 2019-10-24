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
<title><bean:message key="applicationName" /> - Associazione Utenze</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->
<script language="JavaScript">
<!--

var ns6=document.getElementById&&!document.all
var ie4=document.all&&navigator.userAgent.indexOf("Opera")==-1
function aggiungi(){
	val = document.form.user.value;
	if(val != ""){
		//if(val.indexOf(".") > 0 && val.lastIndexOf(".") != val.length-1 ){
			if(val.indexOf(";")==-1){
				var newElem = document.createElement("OPTION");
				newElem.text = val;
				newElem.value = val;
				document.form.lista.options.add(newElem);
				document.form.user.value = "";
			} else
				alert('carattere non ammesso: ";"');
		//} else 
			//alert('la userId deve essere necessariamente del tipo "nome.cognome"');
	 }
}

function rimuovi(){
	if(document.form.lista.selectedIndex>=0)
		if(ie4)
			document.form.lista.options.remove(document.form.lista.selectedIndex);
		else
			document.form.lista.options[document.form.lista.selectedIndex] = null;
}

function applica(){
	var unisci = "";
	while(document.form.lista.length > 0){
		unisci = unisci + document.form.lista.options[0].value + ";";
		if(ie4)
			document.form.lista.options.remove(0);
		else
			document.form.lista.options[0] = null;
	}
	document.form.idlist.value = unisci;
	document.form.submit();
}

//-->
</script>
<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*" %>
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
      					Associazione Utenze<!-- #EndEditable -->
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
	String param = (String)request.getAttribute("param") + "";
	
	if(param.equals("close")){
%>
<script language="JavaScript">
        <!--
        	opener.location.reload();
        	window.close();
        //-->
</script>
<%
	} else {
		Collection ids = (Collection)request.getAttribute("utenti");
		String id;
%>
<span class='text'>Lista userId associati</span>
<form name="form" action="UserMapping.do" method="POST">
<select name="lista" size="10" tabindex='2'>
<%
		for(Iterator i = ids.iterator(); i.hasNext();){
			id = (String)i.next();
%>
  <option value="<%=id%>"><%=id%></option>
<%		}
%>
</select>
<img src="<%=skName%>/deletebutt.gif" width="20" height="14" onClick="rimuovi();"><br>
<br>
<span class='text'>Aggiunta userId</span><br>
<input name="user" type="text" tabindex='1'>
  <img src="<%=skName%>/addbutt.gif" width="20" height="14" onClick="aggiungi();"><br>
  <br>
  <input type="hidden" name="idlist">
  <input type='hidden' name='login' value='<%=request.getParameter("login")%>'>
</form>

<!-- #EndEditable -->
    </td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td align="center"><!-- #BeginEditable "Buttons" -->
	<img src="<%=skName%>/buttons/b-ok.gif" alt='Applica' onClick="applica();">
	&nbsp;&nbsp; <img src="<%=skName%>/buttons/b-annulla.gif" alt='Chiudi' onClick="window.close();">
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

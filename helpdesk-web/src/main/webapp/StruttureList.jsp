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
    <title><bean:message key="applicationName" /> - </title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->
<script language="JavaScript" >
<!--
<%
String cs = (String)request.getAttribute("cs");
String ccc = (String)request.getAttribute("ccc");
String ppp = (String)request.getAttribute("ppp");
if(cs!=null){
%>
var arr = new Array(<%=cs%>);
function popolate(){
	for(var i=0; i<arr.length; i++){
		document.fff.ppp.options[i]=new Option(arr[i],arr[i]);
	}
}
function doFilter(patt) {
	var el = "";
	popolate();
	var eln = document.fff.ppp.options.length;
	for(var i=0; i<eln; i++){
		el = document.fff.ppp.options[i].text.toUpperCase();
		if(el.indexOf(patt.toUpperCase())==-1){
			if(document.all){
				document.fff.ppp.options.remove(i);
			} else {
				document.fff.ppp.options[i] = null;
			}
			i--;
			eln--;
		}
	}
	if(eln==0) document.fff.ppp.options[0]=new Option('Nessun elemento trovato','');
}
function doSubmit(){
	var list = document.fff.ppp;
	if(list.selectedIndex>-1){
	    if(list.options[list.selectedIndex].value.length>0){
		document.fff.submit();
		return;
	    }
	}
    alert('Selezionare una struttura dalla lista');
}
<%
} else {
%>
//window.opener.document.forms.item(0).strutturaDescrizione.value=;
//window.opener.document.forms.item(0).struttura.value='';

  window.opener.eval('param1=<%="\""+ppp.replaceAll("\"","\\\\\\\\\\\"").replaceAll("\'","\\\\\'")+"\"" %>');
  window.opener.eval('param2="<%=ccc %>"');
  window.opener.eval("aggiorna();");
  window.close();
<%
}
%>
//-->
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
      					Strutture<!-- #EndEditable -->
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
    <!-- meccanismo per leggere la lista in apertura di pagina -->
    <img src='<%=skName%>/leaf.gif' height='1' width='1' onload='popolate();'>
    <!-- 							  -->
<form name="fff" method='POST' action='StruttureList.do'>
	            <table width="800">
		<tr><td class='text'>
			Seleziona
		</td></tr>
		<tr><td>
			<select name="ppp" size="8">
    		</select>
		</td></tr>
		<tr><td class='text'>
			Ricerca
		</td></tr>
		<tr><td>
    		<input name="textfield" type="text" onChange="doFilter(this.value);" onKeyUp="doFilter(this.value);">
		</td></tr>
		<tr><td align='center'>
			<img src='<%=skName%>/buttons/b-ok.gif' onclick="doSubmit();" />&nbsp;&nbsp;&nbsp;
			<img src='<%=skName%>/buttons/b-annulla.gif' onclick='window.close();'>
		</td></tr>
	</table>
</form>
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

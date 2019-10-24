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
<title><bean:message key="applicationName" /> - Ricerca utente</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" --> 
<script>
<!--
function doSubmit(){
	var clist = document.CercaUtente.list;
	if(clist.selectedIndex>-1){
		window.opener.eval('param1="'+clist.options[clist.selectedIndex].text+'"');
  		window.opener.eval('param2="'+clist.options[clist.selectedIndex].value+'"');
		window.opener.eval('aggiornaUtente();');
		window.close();
	} else {
		alert('Scegliere un utente');
		return;
	}
}

function doFilter(){
	var txt = document.CercaUtente.filter.value;
	if(txt.replace(' ','').length>0)
		document.CercaUtente.submit();
}
//-->
</script>
<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*,it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject" %>
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
            Ricerca utente<!-- #EndEditable -->
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
	Collection list=(Collection)request.getAttribute("list");
%>
	<form name="CercaUtente" method="POST" action="SearchUser.do">
        <table border="0" width="100%" cellpadding="2" cellspacing="2">
          <tr> 
            <td width="30%" class="bgheader"> 
              <div align="left" class="tableheader">Chiave ridotta cognome</div></td>
            <td width="70%"> 
                <input name="filter" type="text" /> <img src='<%=skName%>/rtable.gif' alt="filtra" onClick="doFilter();" />
            </td>
          </tr>
          <tr> 
            <td width="10%" class="bgheader"><div class="tableheader">Utenti</div></td>
            <td width="90%"> 
              <select size="10" name="list">
<%
				if(list!=null && list.size()>0){
                  Iterator i = list.iterator();
					UserValueObject uvo;
					while(i.hasNext()){
                 		uvo=(UserValueObject)i.next();
%>
              			<option value="<%=uvo.getLogin()%>"><%=uvo.getFamilyName()+" "+uvo.getFirstName()%></option>
<%
					}
				}
%>
                </select>
            </td>
          </tr>
        </table>
</form>
	<!-- #EndEditable -->
    </td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td align="center"><!-- #BeginEditable "Buttons" -->
	<img src='<%=skName%>/buttons/b-ok.gif' alt='Ok' onClick="doSubmit();">&nbsp;&nbsp; 
	<img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onClick="window.close();">
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

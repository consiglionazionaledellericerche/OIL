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
<title><bean:message key="applicationName" /></title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" --> 
<script>
<!--
function doSubmit(){
	if(document.CambioStato.stato.options[0].selected){
		return;
	}
	if(document.CambioStato.Nota.value == ''){
		alert('Attenzione: è obbligatorio inserire una nota');
		return;
	}
		
	if(document.CambioStato.stato.value != "null"){
		if(confirm('Si conferma il passaggio allo stato |' + document.CambioStato.stato.options[document.CambioStato.stato.selectedIndex].text + '| ?')){
			document.CambioStato.submit();
		}
	} else {
		window.close();
	}
}

//-->
</script>
<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*,it.cnr.helpdesk.StateMachineManagement.valueobjects.State" %>
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
            <img src="<%=skName%>/titles/ModificaStato.gif"><!-- #EndEditable -->
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
Vector stateId2Description=(Vector)request.getAttribute("statusDes");
if (request.getAttribute("param").toString().equals("form")) {
	int profile=Integer.parseInt(request.getAttribute("profile").toString());
	Integer stato=(Integer) request.getAttribute("stato");
	ArrayList scelte = (ArrayList)request.getAttribute("scelte");
%>
<form name="CambioStato" method="POST" action="CambioStato.do">
		<input type='hidden' name='idSegnalazione' value='<%= request.getParameter("idSegnalazione") %>'>
        <table border="0" width="100%" cellpadding="2" cellspacing="2">
          <tr> 
            <td width="10%" class="bgheader"> 
              <div align="left" class="tableheader">Stato</div></td>
            <td width="90%"> 
              <p>
                <select size="1" name="stato">

					<option selected value="null">---Seleziona uno stato---</option>
<%                  Iterator i = scelte.iterator();
					State s;
					while(i.hasNext()){
                 		s=(State)i.next();
                 		if(s.getId()!=stato.intValue()){ %>
                  			<option value="<%=s.getId()%>"><%=s.getDescrizione()%></option>
<%
						}
					}
%>
                </select>
            </td>
          </tr>
          <tr> 
            <td width="10%" class="bgheader"><div class="tableheader"><bean:message key="labels.Note" /></div></td>
            <td width="90%"> 
              <textarea rows="10" name="Nota" cols="60"><%= (request.getParameter("note")!=null)?request.getParameter("note"):"" %></textarea>
            </td>
          </tr>
        </table>
<p align="center">
	<img src='<%=skName%>/buttons/b-ok.gif' alt='Ok' onclick="doSubmit();">&nbsp;&nbsp;&nbsp;
	<img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onclick="window.close();">
</p>
</form>

<%
} else if (request.getAttribute("param").toString().equals("close")) {
%>

		<table cellpadding="2" cellspacing="2" class="bordertab">
			<tr><td colspan='2' class='information'>Variazione effettuata</td></tr>
          <tr> 
            <td width="10%" class="bgheader"> 
              <div align="left" class="tableheader">Stato</div></td>
            <td width="90%" class='text'> 
              <%= "<b class='selcat'>" + stateId2Description.elementAt(Integer.parseInt(request.getAttribute("oldstato").toString())-1)+"</b> >> <b class='selcat'>"+ stateId2Description.elementAt(Integer.parseInt(request.getParameter("stato").toString())-1) + "</b>" %>
                 </td>
          </tr>
          <tr> 
            <td width="10%" class="bgheader"><div class="tableheader"><bean:message key="labels.Note" /></div></td>
            <td width="90%"> 
              <textarea rows="10" disabled cols="60"><%= request.getParameter("Nota") %></textarea>
            </td>
          </tr>
          <tr><td colspan='2' align='center'><br><img src='<%=skName%>/buttons/b-chiudi.gif' alt='Close' onclick="window.opener.location.reload(); window.close();"></td></tr>
        </table>
<%
	} else if (request.getAttribute("param").toString().equals("abort")) {
%>
<script language="JavaScript1.2">
<!--
window.close();
//-->
</script>
<%
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

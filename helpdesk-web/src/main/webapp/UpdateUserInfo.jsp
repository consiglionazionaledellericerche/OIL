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
<html><!-- InstanceBegin template="/Templates/OilInternalPage.dwt" codeOutsideHTMLIsLocked="true" -->

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
<title>Online interactive helpdesk - Modifica Info</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->
<script>
<!--
var param1='';	//descrizione
var param2='';	//struttura
var mail_state=<%=request.getParameter("mail_stop").startsWith("y")?"true":"false" %>;
  function aggiorna(){
    self.document.forms[0].strutturaDescrizione.value=param1;
	self.document.forms[0].struttura.value=param2;
  }
  
  function switchMail(){
  	if(mail_state){
  		mail_state=false;
  		document.forms[0].mail_stop.value='n';
  		document.bmail.src='<%=skName + "/simail.gif" %>';
	} else {
  		mail_state=true;
  		document.forms[0].mail_stop.value='y';
  		document.bmail.src='<%=skName + "/nomail.gif" %>';
	}
  }
//-->
</script>
<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="it.cnr.helpdesk.UserManagement.javabeans.User" %>
<!-- #EndEditable --> 

<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
  		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
    				<td width="132"><img src="<%=skName%>/NormalHeaderLeft.gif"></td>
    				<td width="126" background="<%=skName%>/NormalHeaderCenter.gif" >&nbsp;</td>
    				<td background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"> 
      					<!-- #BeginEditable "SectionTitle" --> 
  <img src="<%=skName%>/titles/ModificaInfo.gif">          
			<!-- #EndEditable -->
					</td>
    				<td  width="258" align="right"><img src="<%=skName%>/NormalHeaderRight1.gif" ></td>		
  				</tr>
			</table>
		</td>
		<td width="23"><img src="<%=skName%>/NormalHeaderRight2.gif" width="23" height="51"></td>
		</tr>
  <tr> 
    <td > 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
                    <td width="60%"><span class="navigation"><bean:message key="navigation.starter" /> <!-- #BeginEditable "Navigation" --> 
<%
User  utente = (User) session.getAttribute("it.cnr.helpdesk.currentuser");
%>
            <a href="Home.do" class="navigation"><bean:message key="navigation.Home" /></a>
            --&gt; Modifica Informazioni Personali <!-- #EndEditable --></span> 
          </td>
          <td width="40%" align="right" class="navigation"><a href="Switch.do"><bean:message key="navigation.switch" /></a> | <a  href="Logout.do"><span class="navigation"><bean:message key="navigation.logout" bundle="<%=proj%>" /></span></a></td>
        </tr>
      </table>
    </td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td > 
      <table width="100%">
        <tr> 
          <td valign="top" height="400" ><!-- #BeginEditable "Content" --> 
<%
String param="" + request.getAttribute("param");
if (param.equals("close")) {     
%>
			<table width="100%" cellpadding="2" cellspacing="2" class="bordertab">
				<tr><td class="information">Modifiche effettuate<br></td></tr>
				<tr><td align='center'><img src='<%=skName%>/buttons/b-home.gif' alt='home' onclick="window.location='Home.do';"></td></tr>
            </table>
<%
	} else {
%>		  
            <table width="100%" border="0" cellpadding="2" cellspacing="0">
              <tr>
		        <td width="150" align="center" class="bgheader"><div class="tab">Modifica 
                    Info </div></td>
				 <td width="1"></td> 
		        <td width="180"  align="center" class="bgdisable"><a href="GetChangePassword.do" class="tab">Modifica 
                  Password </a></td>
		        <td >&nbsp;</td>				
	</tr>
</table>
            <table width="100%" border="3" class="bordertab">
              <tr>
                <td> 

<html:form action="/app/UpdateUserInfo">
		<html:errors/>
<table width="100%" border="0" cellspacing="2" cellpadding="2">
  <tr> 
    <td colspan="2" class="bgheader"><font face="Verdana, Arial, Helvetica, sans-serif" size="2"><b><font color="#FFFF00">Account</font></b></font></td>
    </tr>   
    <tr>
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Nome</font></td>
      <td>
        <html:text property="nome" size="30" maxlength="30" />
      </td>
    </tr>
    <tr>
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Cognome</font></td>
      <td>
        <html:text property="cognome" size="30" maxlength="30" />
      </td>
    </tr>
    <tr>
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">E-mail</font></td>
      <td>
        <html:text property="e_mail" size="50" maxlength="50" /> &nbsp;&nbsp; <img name='bmail' src='<%=skName + (request.getParameter("mail_stop").startsWith("y")?"/nomail.gif":"/simail.gif")%>' onclick='switchMail();' border='0' alt='Abilita-disabilita notifica automatica degli eventi' class='clickable'><html:hidden property="mail_stop" />
      </td>
    </tr>
    <tr>
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Telefono</font></td>
      <td>
        <html:text property="telefono" size="30" maxlength="30" />
      </td>
    </tr>
    <tr>
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Struttura</font></td>
      <td>
        <html:text property="strutturaDescrizione" size="40" readonly="true" />      
		<html:hidden property="struttura" /> <img src='<%=skName%>/rtable.gif' alt='Seleziona da elenco' onclick="window.open('StruttureList.do','listaStrutture','height=430,width=900,resizable=yes,scrollbars=yes,toolbar=no,location=no,statusbar=no')" >
      </td>
    </tr>
    <tr>
      <td colspan="2">&nbsp;</td>
    </tr>    
  </table>
            <% if (utente.getProfile()==2) {
    // se il current user è un esperto può vedere le sue categorie
    %>
    &nbsp; &nbsp; &nbsp; 
            <A  HREF="#" onClick="window.open('ManageExpert2.do?login=<%=utente.getLogin()%>','AssociazioneCategoria','height=600,width=800,scrollbars=yes,toolbar=no,location=no,resizable=yes')"><FONT  SIZE=1 FACE="Verdana, Arial">Categorie 
            assegnate</FONT></A> &nbsp;&nbsp; 
            <%}%>
            
<div align="center">
<input type='image' src='<%=skName%>/buttons/b-ok.gif' alt='Ok'>&nbsp;&nbsp;&nbsp;
<img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onclick="window.location='Home.do';">

</div>
</html:form>
</td>
</tr>
</table>
<%    
	}
%>
            <!-- #EndEditable --> </td>
        </tr>
      </table>
    </td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td > 
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
    <td>&nbsp;</td>
  </tr>
</table>

</body>

<!-- InstanceEnd --></html>

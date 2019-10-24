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
<title>Online interactive helpdesk - Aggiorna Info Utente</title>
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
<%@ page import ="it.cnr.helpdesk.UserManagement.javabeans.User, java.util.*" %>
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
            <img src="<%=skName%>/titles/AggiornaInfoUser.gif"><!-- #EndEditable -->
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
                    <td width="60%"><span class="navigation"><bean:message key="navigation.starter" /> <!-- #BeginEditable "Navigation" --><a href="Home.do" class="navigation"><bean:message key="navigation.Home" /></a> 
            --&gt; 
            <% 
String callerPage = (String) session.getAttribute("it.cnr.helpdesk.Caller");
if (callerPage!=null) {
%>
            <a href="<%=callerPage%>" class="navigation"> 
            <%
	if(callerPage.equals("ManageExpert.do")){
%>
            Gestisci Esperti 
            <%		
	} else if (callerPage.equals("ManageUser.do")) {
%>
            Gestisci Utenti 
            <%
	} else if (callerPage.equals("ManageValidator.do")) {
			%>
			Gestisci Validator 
            
            </a> 
            <%	
	}
}
%>
            --&gt; Aggiorna Info Utente<!-- #EndEditable --></span> 
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
String ldap = (String)request.getAttribute("ldap");
String param="" + request.getAttribute("param");
if (param.equals("close")) {     
%>
			<table width="100%" cellpadding="2" cellspacing="2" class="bordertab">
				<tr><td class="information">Modifiche effettuate<br></td></tr>
				<tr><td align='center'><img src='<%=skName%>/buttons/b-chiudi.gif' alt='close' onclick="window.location='<%=callerPage%>';"></td></tr>
            </table>
<%
	} else {

User utenteLogin=(User) session.getAttribute("it.cnr.helpdesk.currentuser");
int profile=utenteLogin.getProfile();
int uProfile=Integer.parseInt(request.getParameter("profilo"));
Collection utenti = (Collection)request.getAttribute("utenti");

%>
<html:form action="/app/UpdateUser">
		<html:errors/>
              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                <tr> 
                  <td colspan="3" class="bgheader"><font face="Verdana, Arial, Helvetica, sans-serif" size="2"><b><font color="#FFFF00" class="tableheader">Account</font></b></font></td>
    </tr>   
    <tr>
                  <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="text">Nome</font></td>
      <td>
		<html:text property="nome" size="30" maxlength="30" />
        <html:hidden property="login" /> 
        <html:hidden property="profilo" />      </td>
                  <td rowspan="5" class="text">
                    <% 
	if(ldap!=null && ldap.startsWith("enabled")){
%>
                    Utenze abilitate<br>
                    <textarea name="textarea" rows="6" cols="30" readonly ><%
if(utenti!=null && !utenti.isEmpty())
	for(Iterator i=utenti.iterator(); i.hasNext();)
		out.println(i.next());
else
	out.println("--nessun utente associato--");
%>
                    </textarea>
                    <img src='<%=skName%>/rtable.gif' alt='Modifica lista' onClick="window.open('UserMapping.do?login=<%=request.getParameter("login")%>','usermapping','height=430,width=900,resizable=yes,scrollbars=yes,toolbar=no,location=no,statusbar=no')" >
                    <%
	}
%>&nbsp;       </td>
    </tr>
    <tr>
                  <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="text">Cognome</font></td>
      <td>
      	<html:text property="cognome" size="30" maxlength="30" />      </td>
                </tr>
    <tr>
                  <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="text">E-mail</font></td>
      <td>
        <html:text property="e_mail" size="50" maxlength="50" /> &nbsp;&nbsp; <img name='bmail' src='<%=skName + (request.getParameter("mail_stop").startsWith("y")?"/nomail.gif":"/simail.gif")%>' onclick='switchMail();' border='0' alt='Abilita-disabilita notifica automatica degli eventi' class='clickable'><html:hidden property="mail_stop" />      </td>
    </tr>
    <tr>
                  <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="text">Telefono</font></td>
      <td>
        <html:text property="telefono" size="30" maxlength="30" />      </td>
    </tr>
    <tr>
                  <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="text">Struttura</font></td>
      <td>
        <html:text property="strutturaDescrizione" size="40" readonly="true" />      
		<html:hidden property="struttura" /> <img src='<%=skName%>/rtable.gif' alt='Seleziona da elenco' onClick="window.open('StruttureList.do','listaStrutture','height=430,width=900,resizable=yes,scrollbars=yes,toolbar=no,location=no,statusbar=no')" >      </td>
    </tr>
    <tr>
      <td colspan="3">&nbsp;</td>
    </tr>    
  </table>
              &nbsp; &nbsp; &nbsp; 
            <% if (profile==3 && (uProfile==2 || uProfile==4)/*&& uvo.getEnabled().equals("y")*/) {
    // se il current user è amministratore e l'utente in oggetto è esperto o validatore
    %>
            <A  HREF="#" onClick="
            window.open('ManageExpert2.do?login=<%=request.getParameter("login")%>','AssociazioneCategoria','height=600,width=800,scrollbars=yes,toolbar=no,location=no,resizable=yes')"><FONT  SIZE=1 FACE="Verdana, Arial">Categorie 
            assegnate</FONT></A> &nbsp;&nbsp; 
<%
				}
%>

            <div align="center">
  <input type='image' src='<%=skName%>/buttons/b-ok.gif' />
</div>
</html:form>  
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

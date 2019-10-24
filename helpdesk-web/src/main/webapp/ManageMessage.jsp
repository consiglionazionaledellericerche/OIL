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
<title>Online interactive helpdesk - Gestione Messaggi</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*,it.cnr.helpdesk.MessageManagement.valueobjects.MessageValueObject,it.cnr.helpdesk.UserManagement.javabeans.User" %>
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
            Gestione Messaggi<!-- #EndEditable -->
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
            --&gt; Gestisci Messaggi <!-- #EndEditable --></span> 
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
User utente=(User) session.getAttribute("it.cnr.helpdesk.currentuser");
boolean viewAll=false;
if (session.getAttribute("viewAll")!=null)
	if (session.getAttribute("viewAll").equals("y"))
		viewAll=true;
Iterator it=(Iterator)request.getAttribute("messages");
%>    
        <table border="0" width="100%" cellpadding="2" cellspacing="2" class="bordertab">
          <tr> 
            <td width="5%" colspan="3" class="bgheader"><div class="tableheader">&nbsp;Azioni
              </div></td>
            <td width="95%" class="bgheader"><div class="tableheader">&nbsp;Messaggio 
              </div></td>
          </tr>
<%
if (!it.hasNext()){
%>
		<tr><td colspan='4' align='center' bgcolor='#aaaaaa' class='text'>
		-- Nessun messaggio disponibile --
		</td></tr>
<%
} else {
	while (it.hasNext()) {
		MessageValueObject mvo = (MessageValueObject)it.next();
%>
          <tr class="text"> 
		  	<td width="25" class="<%=mvo.getEnabled().equals("y")?"bgcells":"discells" %>">
<% 
		if (mvo.getEnabled().equals("y")) { %> 
            	<a href="#" onclick="window.open('SwitchMessage.do?id=<%=mvo.getId_msg()%>&azione=dis&confermato=no','switch','width=600,height=300,menubar=yes,resizable=yes,scrollbars=yes,status=no,location=no,toolbar=no')">
            		<img src="<%=skName%>/attivo.gif" alt="Disabilita" border="0">
            	</a>
<% 
		} else {
%>
                <a href="#" onclick="window.open('SwitchMessage.do?id=<%=mvo.getId_msg()%>&azione=en&confermato=no', 'switch','width=600,height=300,menubar=yes,resizable=yes,scrollbars=yes,status=no,location=no,toolbar=no')">
                	<img src="<%=skName%>/disab.gif" alt="Abilita" border="0">
                </a>
<% 
		}
%>		  
			</td>
<% 
		if (mvo.getOriginatore().equals(utente.getLogin())) {
%>
			<td width="25" class="<%=mvo.getEnabled().equals("y")?"bgcells":"discells" %>">
            	<a href="#" onclick="window.open('UpdateMessage.do?id=<%=mvo.getId_msg()%>','edit','width=700,height=500,menubar=no,resizable=yes,status=no,location=no,toolbar=no')">
            		<img src="<%=skName%>/edit.gif" alt="Modifica" border="0">
            	</a>
            </td>
            <td width="25" class="<%=mvo.getEnabled().equals("y")?"bgcells":"discells" %>">             
            	<a href="#" onclick="window.open('SwitchMessage.do?id=<%=mvo.getId_msg()%>&azione=del&confermato=no','edit','width=600,height=300,menubar=no,resizable=yes,status=no,location=no,toolbar=no')">
            		<img src="<%=skName%>/wdrop.gif" alt="Elimina" border="0">
            	</a>
			</td>

<%
		} else {
%>
            <td width="25" class="<%=mvo.getEnabled().equals("y")?"bgcells":"discells" %>">
            	&nbsp;&nbsp;
            </td>
            <td width="25" class="<%=mvo.getEnabled().equals("y")?"bgcells":"discells" %>">
            	&nbsp;&nbsp;
            </td>
<%
        }
%>
            <td width="95%" class="<%=mvo.getEnabled().equals("y")?"bgcells":"discells" %>"> 
	<%=mvo.getTesto()%> </td>
			</tr>
<%
	}
}
%>
          <tr> 
        </table>
        <p><a href='<%=(viewAll?"ManageMessage.do?tutti=n":"ManageMessage.do?tutti=y")%>'><img <%=viewAll?"src='"+skName+"/hide.gif' alt='Nascondi messaggi disabilitati'":"src='"+skName+"/view.gif' alt='Mostra messaggi disabilitati'" %> border='0'><%=viewAll?"Nascondi messaggi disabilitati":"Mostra messaggi disabilitati"%></a></p>
        
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

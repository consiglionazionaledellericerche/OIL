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
<% 
	/*
	* letto in SegnalazioneDetailAction per il set in sessione dello stato iniziale segnalazione selezionata
	* prima che che questo venga perso dai tentaivi di cambio stato.
	*/
	String closed = (String) session.getAttribute("it.cnr.helpdesk.closed");
   	session.setAttribute("provenienza" , "listaSegnalazioni");

%>

<title><bean:message key="applicationName" />  - 
<%
char cl=closed.charAt(0);
switch (cl) {
	case 89:		//"YES"
%>
<bean:message key="ClosedTickets" />
<%
		break;
	case 78:		//"NO"
%>
<bean:message key="PendingTickets" />
<%		break;
	case 86:		//"VER" 
%>
<bean:message key="VerifiedTickets" />
<%		break;
	case 83:		//"STO" 
%>
<bean:message key="TicketRepository" />
<%		break;
	case 81:		//"QBE" 
%>
<bean:message key="AdvancedSearch" />
<%		break;
}%>
</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="it.cnr.helpdesk.util.*,java.util.*,it.cnr.helpdesk.ProblemManagement.valueobjects.*" %>
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
<%switch (cl) {
	case 89: %>
<img src="<%=skName%>/titles/SegnalazioniChiuse.gif">
<%	break;
	case 78: %>
<img src="<%=skName%>/titles/SegnalazioniPendenti.gif">
<%	break;
	case 86: %>
<img src="<%=skName%>/titles/SegnalazioniVerificate.gif">
<%	break;
	case 83: %>
<img src="<%=skName%>/titles/StoricoSegnalazioni.gif">
<%	break;
	case 81: %>
<img src="<%=skName%>/titles/RicercaAvanzata.gif">
<% 	break;
} %>  
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
            <a href="Home.do" class="navigation"><bean:message key="navigation.Home" /></a> --&gt; 
			<% if(closed.equals("NO")){ %>
            <a href="ProblemCountPerCategoryValidator.do?closed=NO" class="navigation"><bean:message key="PendingTickets" /> : <bean:message key="CountPerCategory" /></a> --&gt; <bean:message key="PendingTicketsList" />
            <%} else  if(closed.equals("YES")){ %>
            <a href="ProblemCountPerCategoryValidator.do?closed=YES" class="navigation"><bean:message key="ClosedTickets" /> : <bean:message key="CountPerCategory" /></a> --&gt; <bean:message key="ClosedTicketsList" />
            <%}%>
            <!-- #EndEditable --></span> 
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
<jsp:useBean id="problem" class="it.cnr.helpdesk.ProblemManagement.javabeans.Problem"/>
<%
PageByPageIterator p = (PageByPageIterator) request.getAttribute("pbp");
String pageNumberString = request.getParameter("page");
int pageNumber = 0;
if (pageNumberString != null) {
    pageNumber = Integer.parseInt(pageNumberString);
} else
    pageNumber = 1;
Collection c=p.getCollection();
Iterator i=c.iterator();
%>
            <%if (closed.equals("NO") || closed.equals("YES") || closed.equals("VER") || closed.equals("STO")) {%>
            <span class="title">Categoria:<%=request.getParameter("categoryName")%></span> 
            <%}%>
            <table border="0" width="100%" cellpadding="2" cellspacing="2">
              <tr class="bgheader"> 
                <td width="5%" class="tableheader">Id</td>
                <td width="33%" class="tableheader">Oggetto</td>
                <td width="5%" class="tableheader">Stato</td>
                <td width="15%" class="tableheader">Originatore</td>
                <% if (closed.equals("STO") || closed.equals("QBE")) { %>
                <td width="15%" class="tableheader">Esperto</td>
                <%}%>
                <td width="15%" class="tableheader">Data Apertura</td>
              </tr>
              <%
while (i.hasNext())
{
ProblemListItemValueObject plivo=(ProblemListItemValueObject) i.next();    
%>
              <tr> 
                <td width="5%"><a href="SegnalazioneDetail.do?idSegnalazione=<%=plivo.getIdSegnalazione()%>"><%=plivo.getIdSegnalazione()%></a></td>
                <td width="33%"><a href="SegnalazioneDetail.do?idSegnalazione=<%=plivo.getIdSegnalazione()%>"><%=plivo.getTitolo()%></a></td>
                <td width="5%"><%=problem.getFormatStatus(plivo.getIdStato(),plivo.getStato())%></td>
                <td width="15%"> <A HREF="#" onClick="window.open('UserDetail.do?login=<%=plivo.getOriginatoreLogin()%>','buttonwin','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><%=plivo.getOriginatore()%></A> 
                </td>
                 <% if (closed.equals("STO") || closed.equals("QBE")) { %>
                 <td width="15%">
                 <%if ((plivo.getEsperto()).equals("- -"))
    {%>
                  <%=plivo.getEsperto()%>
                  <%} else { %>
                  <A HREF="#" onClick="window.open('UserDetail.do?login=<%=plivo.getEspertoLogin()%>','buttonwin','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><%=plivo.getEsperto()%></A> 
                  <% } %>                                                        
                </td>
                <%}%>
                <td width="15%"><%=plivo.getData()%></td>
              </tr>
              <%
}
%>
            </table>
<A HREF="#" onClick="window.location='ExpertQueueDetail.do?page=0<%= closed.equals("QBE")?"":"&category="+request.getParameter("category")+"&categoryName="+request.getParameter("categoryName") %>&print=yes';"><img src='<%=skName%>/buttons/b-print.gif' alt='Versione stampabile' border='0'></a><br>
<p align="center">
<%=p.printPageNavigator(pageNumber)%>
</p>

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

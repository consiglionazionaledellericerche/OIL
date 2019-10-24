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
<title>Online interactive helpdesk - Segnalazioni 
<%
            String Closed = request.getParameter("closed");	
			if(Closed.equals("YES")){
%>
Chiuse
<%} else  if(Closed.equals("NO")){ %>
Pendenti
<%}%>	
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
 <%
			if(Closed.equals("YES")){
%>
<img src="<%=skName%>/titles/SegnalazioniChiuse.gif">
<%} else  if(Closed.equals("NO")){ %>
<img src="<%=skName%>/titles/SegnalazioniPendenti.gif">
<%}%>			
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
                    <td width="60%"><span class="navigation"><bean:message key="navigation.starter" /> <!-- #BeginEditable "Navigation" --><a href="Home.do" class="navigation"><bean:message key="navigation.Home" /></a> 
            --&gt; 
<%
			if(Closed.equals("YES")){
%>
			Segnalazioni Chiuse
<%} else  if(Closed.equals("NO")){ %>
			Segnalazioni Pendenti
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
PageByPageIterator p=(PageByPageIterator)request.getAttribute("pbp");
String pageNumberString=request.getParameter("page");
int pageNumber=0;
if (pageNumberString!=null) {
    pageNumber=Integer.parseInt(pageNumberString);    
} else pageNumber=1;
%>
            <table width="100%" border="0" cellpadding="2" cellspacing="0">
              <tr>
                 <td width="150" align="center" class="bgdisable"><a href="GetCreaSegnalazione.do" class="tab">Crea 
                  segnalazione</a></td>
				 <td width="1"></td> 
<%
if (Closed.equals("NO")){
 %>
		        <td width="180"  align="center" class="bgheader"><div class="tab">Segnalazioni 
                    Pendenti</div></td>
				 <td width="1"></td> 
                <td width="150" align="center" class="bgdisable"><a href="UserQueueDetail.do?closed=YES" class="tab">Segnalazioni 
                  Chiuse</a></td>
 
 <%
 } else  {
 %>
		        <td width="180"  align="center" class="bgdisable"><a href="UserQueueDetail.do?closed=NO" class="tab">Segnalazioni 
                  Pendenti</a></td>
				 <td width="1"></td> 
                <td width="150" align="center" class="bgheader"><div class="tab">Segnalazioni 
                    Chiuse</div></td>

 
<%
 }
%>
		        <td >&nbsp;</td>				
			</tr>
		</table>
<%
Collection c=p.getCollection(); 
Iterator i=c.iterator();
%>
            <table width="100%" border="3" class="bordertab">
              <tr>
<td>
            <table border="0" width="100%" cellpadding="2" cellspacing="2">
                    <tr class="bgheader"> 
                      <td width="10%" class="tableheader">Id</td>
                      <td width="40%" class="tableheader">Oggetto</td>
                      <td width="10%" class="tableheader">Stato</td>
                      <td width="10%" class="tableheader">Categoria</td>
                      <td width="20%" class="tableheader">Esperto</td>
                      <td width="10%" class="tableheader">Data</td>
              </tr>
<%
// per gestione della visualizzazione dello stato 6 e 7 della segn. nel caso di UTENTE 
//Vector stateId2Description=(Vector)request.getAttribute("statusDes");
int profile=Integer.parseInt(request.getAttribute("profile").toString());

while (i.hasNext()) {
ProblemListItemValueObject plivo=(ProblemListItemValueObject) i.next();    
%>
              <tr> 
                <td><a href="SegnalazioneDetail.do?idSegnalazione=<%=plivo.getIdSegnalazione()%>"><%=plivo.getIdSegnalazione()%></a></td>
                <td><a href="SegnalazioneDetail.do?idSegnalazione=<%=plivo.getIdSegnalazione()%>"><%=plivo.getTitolo()%></a></td>
                <%  String statoSegnalazione = plivo.getStato();
	                int idStato = plivo.getIdStato();
                	if(profile==1 && (idStato == 6 || idStato == 7)){
                		idStato = 67;
                		statoSegnalazione = "Validation";
                	}	

                %> 
                <!--  <td>--><% //=problem.getFormatStatus(plivo.getIdStato(),plivo.getStato())%><!--  </td>-->
                <td><%=problem.getFormatStatus(idStato,statoSegnalazione)%></td>
                <td><%=plivo.getCategoriaDescrizione()%></td>
                <td> 
                  <%if ((plivo.getEsperto()).equals("- -"))
    {%>
                  <%=plivo.getEsperto()%>
                  <%} else { %>
                  <A HREF="#" onClick="window.open('UserDetail.do?login=<%=plivo.getEspertoLogin()%>','buttonwin','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><%=plivo.getEsperto()%></A> 
                  <% } %>
                </td>
                <td ><%=plivo.getData()%></td>
              </tr>
              <%
}
%>
            </table>
<A HREF="#" onClick="window.location='UserQueueDetail.do?page=0&closed=<%=Closed%>&print=yes';"><img src='<%=skName%>/buttons/b-print.gif' alt='Versione stampabile' border='0'></a><br>
<p align="center">
<%=p.printPageNavigator(pageNumber)%>
</p>            
		</td>
	</tr>
</table>
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

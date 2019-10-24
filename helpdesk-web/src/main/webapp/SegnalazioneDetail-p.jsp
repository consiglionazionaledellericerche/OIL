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
<html><!-- InstanceBegin template="/Templates/OilPrintablePage.dwt" codeOutsideHTMLIsLocked="true" -->

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
<title><bean:message key="applicationName" /> - <bean:message key="SegnalazioneDetailTitle" /></title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*,it.cnr.helpdesk.ProblemManagement.valueobjects.*,it.cnr.helpdesk.UserManagement.javabeans.User" %>
<!-- #EndEditable --> 
<!-- #BeginEditable "Content" --> 
<%
User  utente = (User) session.getAttribute("it.cnr.helpdesk.currentuser");
int profile=utente.getProfile();
//ProblemValueObject pvo=(ProblemValueObject) request.getAttribute("pvo");
//ArrayList files = (ArrayList) request.getAttribute("files");
%>

<table border="0" width="100%" cellpadding="2" cellspacing="5">
                
    <tr>
        <td colspan="2" align="center" class="paragraphtitle">Dettaglio Segnalazione</td>
    </tr>
    <tr> 
                  
        <td width="10%" class="ptableheader">
Id 
                      Segnalazione</td>
        <td width="90%" class="ptablebody"><%=request.getParameter("idSegnalazione")%>
                  </td>
    </tr>
                
    <tr> 
                  
        <td width="10%" class="ptableheader">
Oggetto</td>
        <td width="90%" class="ptablebody"><%=request.getParameter("oggetto")%>
                  </td>
    </tr>
                
    <tr> 
                  
        <td width="10%" class="ptableheader">
Categoria</td>
        <td width="90%" class="ptablebody"><%=request.getParameter("categoria")%>
                   </td>
    </tr>
                
    <tr> 
                  
        <td width="10%" class="ptableheader">
Stato</td>
        <td width="90%" class="ptablebody"><%=request.getParameter("stato")%></td>
    </tr>
                
    <tr>
        <td class="ptableheader">
Priorità</td>
        <td class="ptablebody">
	                	<%=request.getParameter("priorita")%>
                	</td>
    </tr>
    <tr>       
        <td width="10%" class="ptableheader">
Originatore</td>
        <td width="90%" class="ptablebody"> 
             <%=request.getParameter("originatore")%>
		</td>
    </tr>
    <tr> 
        <td width="10%" class="ptableheader">
			Esperto
		</td>
        <td width="90%" class="ptablebody"> 
            <%=request.getParameter("esperto")%>
		</td>
    </tr>
    <tr>
    <tr> 
        <td width="10%" class="ptableheader">
			Validatore
		</td>
        <td width="90%" class="ptablebody"> 
            <%=request.getParameter("validatore")%>
		</td>
    </tr>
    <tr>
        <td width="10%" valign="top" class="ptableheader">
			Descrizione
            </td>
        <td width="90%" class="ptablebody"><%=request.getParameter("S1").replaceAll("\n","<br>")%>
				  </td>
    </tr>
    <tr><td colspan='2' align="center">
    	<table width="82" height="31" background='<%=skName%>/buttons/b-ok.gif' onclick='window.history.back();' style='text-decoration=none'>
                <tr>
                    <td>&nbsp;
    	</td>
                </tr>
		</table>
	</td></tr>
	</table>
<!-- #EndEditable -->
</body>
<!-- InstanceEnd --></html>

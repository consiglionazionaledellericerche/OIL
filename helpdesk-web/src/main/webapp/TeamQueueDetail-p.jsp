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
    <title><bean:message key="applicationName" /> - Lista Segnalazioni</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="it.cnr.helpdesk.util.*,java.util.*,it.cnr.helpdesk.ProblemManagement.valueobjects.*" %>
<!-- #EndEditable --> 
<!-- #BeginEditable "Content" -->
<%
PageByPageIterator p=(PageByPageIterator)request.getAttribute("pbp");
Collection c=p.getCollection(); 
Iterator i=c.iterator();
%>
<table border="0" width="100%" cellpadding="2" cellspacing="2">
    <tr class="paragraphtitle"><td colspan='6' align="center">
    	Lista Segnalazioni<br>
            <span class="title">Categoria:<%=request.getParameter("categoryName")%></span> 
	</td></tr>
	<tr>
		  <td width="5%" class="ptableheader">Id</td>
		  <td width="33%" class="ptableheader">Oggetto</td>
		  <td width="5%" class="ptableheader">Stato</td>
		  <td width="15%" class="ptableheader">Originatore</td>
		  <td width="15%" class="ptableheader">Esperto</td>
		  <td width="15%" class="ptableheader">Data Apertura</td>
    </tr>
<%
while (i.hasNext()) {
	ProblemListItemValueObject plivo=(ProblemListItemValueObject) i.next();    
%>
      <tr> 
        <td width="5%" class="ptablebody"><%=plivo.getIdSegnalazione()%></td>
        <td width="33%" class="ptablebody"><%=plivo.getTitolo()%></td>
        <td width="5%" class="ptablebody"><%=plivo.getStato()%></td>
        <td width="15%" class="ptablebody"><%=plivo.getOriginatore()%></td>
        <td width="15%" class="ptablebody"><%=plivo.getEsperto()%></td>
        <td width="15%" class="ptablebody"><%=plivo.getData()%></td>
      </tr>
<%
}
%>
    <tr><td colspan='6' align="center">
    	<table width="82" height="31" background='<%=skName%>/buttons/b-ok.gif' onclick='window.history.back();' style='text-decoration=none'>
                <tr>
                    <td>&nbsp;</td>
                </tr>
		</table>
	</td></tr>
</table>
<!-- #EndEditable -->
</body>
<!-- InstanceEnd --></html>

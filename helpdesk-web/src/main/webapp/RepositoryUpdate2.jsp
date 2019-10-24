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
		<title><bean:message key="applicationName" /> - Sistema di archiviazione segnalazioni</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="it.cnr.helpdesk.util.*,java.util.*,it.cnr.helpdesk.RepositoryManagement.valueobjects.*" %>
<!-- #EndEditable --> 
<!-- #BeginEditable "Content" --> 
            <table width="100%" border="3" class="bordertab">
              <tr>
<td>		  
<% 

Collection data = (Collection) request.getAttribute("data");
if(data.isEmpty()){
%>
				 <table border="1" width="100%" cellpadding="2" cellspacing="0" class="text">
                    <tr class="bgheader"> 
                      <td class="tableheader">Nessun documento disponibile sulla selezione</td>
                    </tr>
                 </table>
<%
} else {
Iterator ei = data.iterator();
%>
                  <table border="1" width="100%" cellpadding="2" cellspacing="0" class="text">
                    <tr class="bgheader"> 
                      <td class="tableheader">Mese</td>
                      <td class="tableheader">File</td>
                      <td class="tableheader">Stato</td>
                      <td class="tableheader">Azioni </td>
                    </tr>
                    <%  while(ei.hasNext()){
         RepositoryValueObject rvo=(RepositoryValueObject) ei.next();
         %>
                    <tr> 
                      <td class="bgcells"><%=rvo.getNomeMese()%></td>
                      <td class="bgcells"><%if(rvo.getStato().startsWith("v")){%>------<%} else {%>
													  <a href='<%= request.getContextPath()+"/app/reports/"+rvo.getNomeFile()%>?id=<%=rvo.getMese()%><%=rvo.getCategoria()%>' target=_new ><%=rvo.getNomeFile()%></a>
													  <%}%></td>
                      <td class="bgcells"><div class='<%=rvo.getStateColor()%>'><%=rvo.getStatoDes()%></div></td>
                      <td width=80 class="bgcells">
                      		<%  if (rvo.isModificabile()) { %>
                      			    <a href="#" onClick="window.open('UploadFile.do?mese=<%=rvo.getMese()%>&categoria=<%=rvo.getCategoria()%>','buttonwin','height=360,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')">
                      			    <img src="<%=skName%>/update.gif" alt="Aggiorna" border="0"></a>&nbsp;
                            <%  }
                            	if (rvo.isEliminabile()) { %>
									<a href="#" onClick="window.open('DeleteFile.do?mese=<%=rvo.getMese()%>&categoria=<%=rvo.getCategoria()%>','buttonwin','height=200,width=700,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')">
                            			<img src="<%=skName%>/delete.gif" alt="Elimina" border="0"></a>&nbsp;
                            <% 	}
                            	if (rvo.getStato().startsWith("i")) { %>
									<a target='_parent' href="RepositoryUpdate2.do?qbe=y&id=<%=rvo.getMese()%><%=rvo.getCategoria()%>">
                            			<img src="<%=skName%>/query.gif" alt="Rigenera" border="0"></a>
                            <% 	}
                            %>
							&nbsp;
                       </td>
                    </tr>
<%
					}
%>
                  </table>    
<%}  
%>
                </td>
	</tr>
</table>
<div align='center'><img src='<%=skName%>/buttons/b-home.gif' alt='Annulla' vspace='8' onclick="window.parent.location='Home.do';"></div>
<!-- #EndEditable -->
</body>
<!-- InstanceEnd --></html>

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
<title>Online interactive helpdesk - Gestione Categorie</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*,it.cnr.helpdesk.CategoryManagement.valueobjects.*" %>
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
            <img src="<%=skName%>/titles/GestisciCategorie.gif"><!-- #EndEditable -->
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
            --&gt; Gestisci Categorie <!-- #EndEditable --></span> 
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
            <table width="100%" border="0" cellpadding="2" cellspacing="0">
              <tr>
		        <td width="150" align="center" class="bgdisable"> 
                  <a href="GetCreateCategory.do" class="tab">Crea Categoria</a></td>
		        <td width="1"></td> 
		        <td width="150"  align="center" class="bgheader"><div align="center" class="tab">Gestisci 
                  Categorie</div></td>
                <td >&nbsp;</td>
			   </tr>
			</table>

<%
boolean viewAll=false;
if (session.getAttribute("viewAll")!=null)
	if (session.getAttribute("viewAll").equals("y"))
		viewAll=true;
Iterator it=(Iterator)request.getAttribute("categories");
%>    
        <table border="0" width="100%" cellpadding="2" cellspacing="2" class="bordertab">
          <tr> 
            <td width="5%" class="bgheader"><div class="tableheader">&nbsp;Azioni
              </div></td>
            <td width="95%" class="bgheader"><div class="tableheader">&nbsp;Categoria 
              </div></td>
          </tr>
          <%
String indUnit="&nbsp;&nbsp;&nbsp;&nbsp;";
String ind="";
int nTab=0;
while (it.hasNext())
{
CategoryValueObject cvo=(CategoryValueObject)it.next();
if (cvo.getEnabled().equals("y") || viewAll)
	{
%>
          <tr class="text"> 
		  <td width="5%" class="<%=cvo.getEnabled().equals("y")?"bgcells":"discells" %>">
        <table cellspacing="0" cellpadding="0" class="text">
                           <tr>
                             <td><a href="#" onclick="window.open('GetUpdateCategory.do?idCategoria=<%=cvo.getId()%>&nome=<%=cvo.getNome().replaceAll("'","\\\\'")%>&descrizione=<%=(cvo.getDescrizione()+"").replaceAll("'","\\\\'")%>','edit','width=800,height=400,menubar=no,resizable=yes,status=no,location=no,toolbar=no')"><img src="<%=skName%>/edit.gif" alt="Modifica" border="0"></a>&nbsp;</td>
                          <% if (cvo.getEnabled().equals("y")) { %> 
                             <td><a href="#" onclick="window.open('SwitchCategoria.do?id=<%=cvo.getId()%>&nome=<%=cvo.getNome().replaceAll("'","\\\\'")%>&azione=dis&confermato=no','switch','width=800,height=600,menubar=yes,resizable=yes,scrollbars=yes,status=no,location=no,toolbar=no')"><img src="<%=skName%>/attivo.gif" alt="Disabilita" border="0"></a></td>
                          <% } else {%>
                             <td><a href="#" onclick="window.open('SwitchCategoria.do?id=<%=cvo.getId()%>&nome=<%=cvo.getNome().replaceAll("'","\\\\'")%>&azione=en&confermato=no', 'switch','width=800,height=600,menubar=yes,resizable=yes,scrollbars=yes,status=no,location=no,toolbar=no')"><img src="<%=skName%>/disab.gif" alt="Abilita" border="0"></a></td>
                          <% } %>
                           </tr>
        </table>                 
            </td>
            <td width="95%" class="<%=cvo.getEnabled().equals("y")?"bgcells":"discells" %>"> 
<% 
	nTab=cvo.getLivello()-1;
    ind="";
    for(int i=0;i<nTab;i++)
    {
        ind=ind+indUnit;
    } 
    %> <%=ind+cvo.getNome()%> </td>
			</tr>
<%	}
}
%>
          <tr> 
        </table>
        <p><a href='<%=(viewAll?"ManageCategory.do?tutti=n":"ManageCategory.do?tutti=y")%>'><img <%=viewAll?"src='"+skName+"/hide.gif' alt='Nascondi categorie disabilitate'":"src='"+skName+"/view.gif' alt='Mostra categorie disabilitate'" %> border='0'><%=viewAll?"Nascondi categorie disabilitate":"Mostra categorie disabilitate"%></a></p>
        
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

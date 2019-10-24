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
<title><bean:message key="applicationName" /> -  <bean:message key="pageTitles.ManageExperts" /></title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="it.cnr.helpdesk.util.*,java.util.*,it.cnr.helpdesk.UserManagement.valueobjects.*" %>
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
            <img src="<%=skName%>/titles/GestisciEsperti.gif"><!-- #EndEditable -->
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
            --&gt; <bean:message key="navigation.ManageExpert" /><!-- #EndEditable --></span> 
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

            <table width="100%" border="0" cellpadding="2" cellspacing="0" class="text">
              <tr>
		        <td width="150" align="center" class="bgdisable"><a href="GetCreateUser.do" class="tab"><bean:message key="tabs.CreateUser" /></a> </td>
				 <td width="1"></td> 
		        <td width="150"  align="center" class="bgdisable"><a href="ManageUser.do" class="tab"><bean:message key="tabs.ManageUsers" /></a></td>
				<td width="1"></td> 
				<td width="150" align="center" class="bgheader"><span class="tab"><bean:message key="tabs.ManageExperts" /></span> </td>
		        <td width="1"></td>
		        <td width="150"  align="center" class="bgdisable"><a href="ManageValidator.do" class="tab"><bean:message key="tabs.ManageValidators" /></a></td>
		        <td >&nbsp;</td>				 
		        
		        				
	</tr>
</table>		  
            <table width="100%" border="3" class="bordertab">
              <tr> 
                <td>

<%
boolean viewAll=false;
if (session.getAttribute("viewAll")!=null)								//setta il flag viewAll locale sulla base di quello di sessione
	if (session.getAttribute("viewAll").equals("y"))
		viewAll=true;
PageByPageIterator p= (PageByPageIterator) request.getAttribute("it.cnr.helpdesk.PageByPageIterator");
int pageNumber=((Integer) request.getAttribute("it.cnr.helpdesk.PageNumber")).intValue();
String listAll=(pageNumber==0?"&page=0":"");
Collection c=p.getCollection();

Iterator ei = c.iterator();
if(ei!=null){ 
%>
                  <table border="1" width="100%" cellpadding="2" cellspacing="0" class="text">
                    <tr class="bgheader"> 
                      <td width="34%" class="tableheader"><bean:message key="labels.FamilyName" /></td>
                      	<td width="33%" class="tableheader"><bean:message key="labels.FirstName" /></td>
                      	<td width="33%" class="tableheader"><bean:message key="labels.Login" /></td>
                      	<td width="34%" class="tableheader"><bean:message key="labels.E-mail" /></td>
                      	<td width="34%" class="tableheader"><bean:message key="labels.Telephone" /></td>
                      	<td width="34%" class="tableheader"><bean:message key="labels.OrganizationalUnit" /></td>
                      	<td width="34%" class="tableheader"><bean:message key="labels.Actions" /></td>
                    </tr>
                    <%  while(ei.hasNext()){
         UserValueObject uvo=(UserValueObject) ei.next();
         %>
                    <tr> 
                      <td width="34%" class="<%=uvo.getEnabled().equals("y")?"bgcells":"discells" %>"><%=uvo.getFamilyName()%></td>
                      <td width="33%" class="<%=uvo.getEnabled().equals("y")?"bgcells":"discells" %>"> <%=uvo.getFirstName()%> </td>
                      <td width="33%" class="<%=uvo.getEnabled().equals("y")?"bgcells":"discells" %>"> <%=uvo.getLogin()%></td>
                      <td width="34%" class="<%=uvo.getEnabled().equals("y")?"bgcells":"discells" %>"> 
                        <% if (uvo.getEmail()!=null && ((String) uvo.getEmail()).length()>0) {%>
                        <%=uvo.getEmail()%> 
                        <% } else { %>
                        &nbsp; 
                        <%}%>
                      </td>
                      <td width="34%" class="<%=uvo.getEnabled().equals("y")?"bgcells":"discells" %>"> 
                        <% if (uvo.getTelefono()!=null && ((String) uvo.getTelefono()).length()>0) {%>
                        <%=uvo.getTelefono()%> 
                        <% } else { %>
                        &nbsp; 
                        <%}%>
                      </td>
                      <td width="34%" class="<%=uvo.getEnabled().equals("y")?"bgcells":"discells" %>"> 
                        <% if (uvo.getStruttura()!=null && ((String) uvo.getStruttura()).length()>0) {%>
                        <%=uvo.getStruttura()%> 
                        <% } else { %>
                        &nbsp; 
                        <%}%>
                      </td>
                      <td width="34%" class="<%=uvo.getEnabled().equals("y")?"bgcells":"discells" %>">
                        <table width="100%" cellspacing="0" cellpadding="0">
                           <tr>
                             <td width="33%"><a href="GetUpdateUser.do?login=<%=uvo.getLogin()%>"><img src="<%=skName%>/wedit.gif" alt="Modifica" border="0"></a>&nbsp;</td>
                          <% if (uvo.getEnabled().equals("y")) { %> 
                             <td width="33%"><a href="#" onclick="window.open('DisabilitaEsperto.do?login=<%=uvo.getLogin()%>','switch','width=800,height=600,menubar=yes,resizable=yes,scrollbars=yes,status=no,location=no,toolbar=no')"><img src="<%=skName%>/wstop.gif" alt="Disabilita" border="0"></a></td>
                          <% } else {%>
                             <td width="33%"><a href="#" onclick="window.open('SwitchUserStatus.do?login=<%=uvo.getLogin()%>&azione=en','switch','width=800,height=400,menubar=no,resizable=yes,status=no,location=no,toolbar=no,scrollbars=yes')"><img src="<%=skName%>/wrun.gif" alt="Abilita" border="0"></a></td>
                          <% } %>
                           </tr>
                         </table> 
                       </td>
                    </tr>
                    <%
}
%>
                  </table>   
<p><a href='<%=(viewAll?"ManageExpert.do?viewAll=n":"ManageExpert.do?viewAll=y")+listAll %>'><img <%=viewAll?"src='"+skName+"/hide.gif' alt='Nascondi utenti disabilitati'":"src='"+skName+"/view.gif' alt='Mostra utenti disabilitati'" %> border='0'><%=viewAll?"Nascondi utenti disabilitati":"Mostra utenti disabilitati"%></a></p>
<p align="center">
<%=p.printPageNavigator(pageNumber)%>
</p>
<%  
}%>
				
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

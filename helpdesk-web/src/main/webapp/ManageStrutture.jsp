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
<!-- #BeginEditable "ErrorPage" -->
<%@ page errorPage="/app/OilErrorPage.do" %>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<% String proj = session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<% String skName = request.getContextPath()+"/skins/" + session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<link rel="stylesheet" href="<%=skName%>/OilStyles.css" type="text/css">
<!-- #BeginEditable "doctitle" -->
<title><bean:message key="applicationName" /> - </title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*,it.cnr.helpdesk.UOManagement.valueobjects.UOValueObject" %>
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
			Gestione Strutture           
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
            <a href="Home.do" class="navigation"><bean:message key="navigation.Home" /></a> 
             --&gt;  <bean:message key="navigation.ManageStrutture" />
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
            <table width="100%" border="0" cellpadding="2" cellspacing="0" class="text">
              <tr>
		        <td width="150" align="center" class="bgdisable"><a href="CreateStrutture.do" class="tab">
				<bean:message key="tabs.CreateStrutture" /></a> </td>
				<td width="1"></td> 
		       	<td width="150" align="center" class="bgheader"><div class="tab">
				<bean:message key="tabs.ManageStrutture" /></div></td>
				<td>&nbsp;</td>				
			  </tr>
            </table>		 
            <table width="100%" border="3" class="bordertab">
              <tr>
				<td>		  
<% 
Collection c = (Collection)request.getAttribute("coll");
Iterator i = c.iterator();

if(i!=null){ 
%>
                  <table border="1" width="100%" cellpadding="2" cellspacing="0" class="text">
                    <tr class="bgheader"> 
					  <td width="100" class="tableheader">ID</td>
					  <td class="tableheader">Struttura</td>
			          <td width="100" class="tableheader">Acronimo</td>
					  <td width="55" class="tableheader">Evidenza</td>
					  <td width="70" class="tableheader"><bean:message key="labels.Actions" /></td>
                    </tr>
<%  while(i.hasNext()){
      UOValueObject uvo = (UOValueObject)i.next();
%>
                    <tr> 
                      <td width="100"><%=uvo.getCodiceStruttura()%></td>
                      <td><%=uvo.getDescrizione()%> </td>
                      <td width="100"><%=uvo.getAcronimo()%></td>
					  <td width="55"><%if(uvo.getEvidenza().equals("y")) {out.print("Si");} else if(uvo.getEvidenza().equals("n")) {out.print("No");}%></td>
					  <td width="70">
                      <table width="100%" cellspacing="0" cellpadding="0">
                        <tr>
	                      <td width="50%"><a href="#" onClick="window.open('UpdateStrutture.do?cod=<%=uvo.getCodiceStruttura()%>','edit','width=800,height=400,menubar=no,resizable=yes,status=no,location=no,toolbar=no')"><img src="<%=skName%>/wedit.gif" alt="Modifica" border="0"></a></td>
<% 					
	  if (uvo.getEnabled().equals("y")) { 
%> 
                          <td width="50%"><a href="#" onClick="window.open('SwitchStrutture.do?cod=<%=uvo.getCodiceStruttura()%>&azione=dis','switch','width=800,height=400,menubar=no,resizable=yes,status=no,location=no,toolbar=no,scrollbars=yes')"><img src="<%=skName%>/wstop.gif" alt="Disabilita" border="0"></a></td>
<% 
	  } 
	  else {
%>
                          <td width="50%"><a href="#" onClick="window.open('SwitchStrutture.do?cod=<%=uvo.getCodiceStruttura()%>&azione=en','switch','width=800,height=400,menubar=no,resizable=yes,status=no,location=no,toolbar=no,scrollbars=yes')"><img src="<%=skName%>/wrun.gif" alt="Abilita" border="0"></a></td>
<% 
	  } 
%>
						  
                         </tr>
                      </table>                      </td>
                    </tr>
<%
    }
%>
                  </table>    
<%
}
%>
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

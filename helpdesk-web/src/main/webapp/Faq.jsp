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
<title><bean:message key="applicationName" /> - Categorie Assegnate</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*,it.cnr.helpdesk.CategoryManagement.valueobjects.*,it.cnr.helpdesk.FaqManagement.dto.*,it.cnr.helpdesk.UserManagement.javabeans.*" %>
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
            <img src="<%=skName%>/titles/FAQ.gif"><!-- #EndEditable -->
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
                    <td width="60%"><span class="navigation"><bean:message key="navigation.starter" /> <!-- #BeginEditable "Navigation" --><a href="Home.do" class="navigation"><bean:message key="navigation.Home" /></a> --&gt; FAQ<!-- #EndEditable --></span> 
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

      <table width="100%" border="3" cellpadding="2" cellspacing="2" class="bordertab">
<%
User utente=(User) session.getAttribute("it.cnr.helpdesk.currentuser");
String status = (String)request.getAttribute("status");
String actualCategory = "" + request.getParameter("category");
if (status!=null) {
	Collection c=(Collection)request.getAttribute("category");
	Collection fl=(Collection)request.getAttribute("faqlist");
	Iterator it=c.iterator();
%>    
        <tr class="bgheader"> 
          <td width="33%" class="tableheader">Categorie </td>
            
          <td width="70%" class="tableheader">FAQ</td>
          </tr>
<tr>
<td>
<table border="0" width="100%" cellpadding="2" cellspacing="2">
          <%
String indUnit="&nbsp;&nbsp;&nbsp;&nbsp;";
String ind="";
int nTab=0;
while (it.hasNext())
{
CategoryValueObject cvo=(CategoryValueObject)it.next();

%>
          <tr> 
            <td width="33%"> 
              <%
    nTab=cvo.getLivello()-1;
    ind="";
    for(int i=0;i<nTab;i++)
    {
        ind=ind+indUnit;
    } 
    %>
    			<%=ind%><img border="0" src="<%=skName%>/open.gif" width="16" height="16">
    			<a href="Faq.do?category=<%=cvo.getId()%>" class='<%=(actualCategory.equals(""+cvo.getId()))?"selcat":"listcat" %>' >                            
              <%=cvo.getNome()%></a></td>
          </tr>
          <%}%>
 
        </table>
        </td>
        <td valign="top">
        <table border="0" width="100%" cellpadding="2" cellspacing="2">
<%
	if (status.equals("nosel")){%>
              <tr><td align="center">
        	<br>
        	<br>
        	<br>
        	<br>
        	<b><font class="text">&lt;=
        Seleziona una categoria</font></b> 
	  	</td></tr>
	  	
<%
	} else if(status.equals("sel")){%>
              <tr class="bgheader"> 
                <td width="33%" class="tableheader">Titolo </td>
                <td width="70%" class="tableheader">Categoria</td>
             </tr>
             <%
                Iterator fli=fl.iterator();
                while (fli.hasNext())
                {
                  FaqDTO faqDTO=(FaqDTO) fli.next();
                %>
                <tr>
                <td width="70%">
                <A  HREF="#" onClick="window.open('FaqDetail.do?idFaq=<%=faqDTO.getIdFaq()%>','FaqDetail','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"> 
                <%=faqDTO.getTitolo()%></A>                                                
                </td>                
                <td width="30%">         
                <font class="text"><%=faqDTO.getDescrizioneCategoria()%></font>                
                </td>
                </tr>
                <%
                }    
	} else if (status.equals("empty")) {
                %>
                <tr>
                <td width="100%">
                <p align="center"><b><font class="text">
         In questa categoria non è stata pubblicata nessuna FAQ</font></b> 
                 </p>                                               
                </td></tr>   
                <%
                }    
%>  
                 </table>
                 </td></tr></table>
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

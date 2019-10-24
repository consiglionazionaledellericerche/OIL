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
<title>Online interactive helpdesk</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*" %>
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
      					Allega File<!-- #EndEditable -->
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
            --&gt; <a href="GetCreaSegnalazione.do" class="navigation">Crea Segnalazione</a>
            --&gt; Allega File<!-- #EndEditable --></span> 
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
Collection result=(Collection)request.getAttribute("result");
if(result==null)
	result=new ArrayList(0);
Iterator iter = result.iterator();
String param=(String)request.getAttribute("param");
if(param!=null){
	if(param.equals("close")){
%>
		<table border='1' class='board'><tr><td><br>
		I seguenti file sono stati inseriti correttamente:<br><br>
		<ul class='board'>
<%
		while (iter.hasNext()) {
			out.print("<li>");
		    out.print(iter.next());
		    out.print("</li>");
		}
%>
		</ul>
		</td></tr></table>
<p align='center'><img src='<%=skName%>/buttons/b-home.gif' alt='Home' onclick="window.location='Home.do';">
</p>
<%
	} else if(param.equals("ok")){
%>
<p class='information'>
<%= iter.hasNext()?iter.next():"" %>
</p>
<br><br>
            <table border="0" width="100%" cellpadding="2" cellspacing="2">
              <tr> 
                <td colspan="2" align="center"> 
 				  <form name="AttchmentForm" action="FileSelect.do" method="POST" enctype="multipart/form-data" >
                    <span class="information">Selezionare i file:</span><br>
                    <br>
<table border="0" cellspacing="3" style="background-color: #999999;border-style: double;">
    <tr>
        <td>
		<table cellpadding="5" style="border: thin groove; background-color: #eeeeee;">
			    <tr>
                    <td height="35" align="right" style="border-bottom-style: dotted; border-width: 1px; border-color: #888888; ">file 1:</td>
                    <td height="35" style="border-bottom-style: dotted; border-width: 1px; border-color: #888888;">
						<input type="file" name="attachment1"></td>
                </tr>
                <tr>
                    <td height="35" align="right">descrizione:</td>
                    <td height="35">
						<input type="text" name="note1" size='50'></td>
                </tr>
		</table></td>
    </tr>
    <tr>
        <td>
	<table cellpadding="5" style="border: thin groove; background-color: #eeeeee;">
                <tr>
                    <td height="35" align="right" style="border-bottom-style: dotted; border-width: 1px; border-color: #888888;">file 2:</td>
                    <td height="35" style="border-bottom-style: dotted; border-width: 1px; border-color: #888888;">
						<input type="file" name="attachment2"></td>
                </tr>
                <tr>
                    <td height="35" align="right">descrizione:</td>
                    <td height="35">
						<input type="text" name="note2" size='50'></td>
                </tr>
	</table></td>
    </tr>
	<tr>
        <td>
		<table cellpadding="5" style="border: thin groove; background-color: #eeeeee;">
	            <tr>
                    <td height="35" align="right" style="border-bottom-style: dotted; border-width: 1px; border-color: #888888;">file 3:</td>
                    <td height="35" style="border-bottom-style: dotted; border-width: 1px; border-color: #888888;">
						<input type="file" name="attachment3"></td>
                </tr>
                <tr>
                    <td height="35" align="right">descrizione:</td>
                    <td height="35">
						<input type="text" name="note3" size='50'></td>
                </tr>
	</table></td>
    </tr>
</table>
                    <br>
					<input type="image" src='<%=skName%>/buttons/b-ok.gif' alt="Invia">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<%=skName%>/buttons/b-chiudi.gif" onclick="window.location='Home.do';" alt="Chiudi" />
				  </form> 				
				</td>
              </tr>
            </table>
<%
	}
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

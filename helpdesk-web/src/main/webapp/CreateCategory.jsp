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
<title>Online interactive helpdesk - Crea Categoria</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->
<script>
<!--
function aggiorna(){
	self.document.forms[0].elements[1].value=param1;
	self.document.forms[0].elements[0].value=param2;
}
//-->
</script>
 <!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings,java.util.*" %>
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
            <img src="<%=skName%>/titles/CreaCategoria.gif"><!-- #EndEditable -->
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
            -- &gt; Crea Categoria<!-- #EndEditable --></span> 
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
String param=(String)request.getAttribute("param");
if(param!=null){
	if(param.equals("close")){
%>
                        <table align='center' class="bordertab">
	                        <tr>
		                        <td colspan='2'><span class="information">Inserimento effettuato:</span><br>
                                    <br></td>
                            </tr>
	                        <tr>
    	                        <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="text">Categoria Padre</font></td>
                                <td><input type="text" disabled value="<%=request.getAttribute("categoriaPadre") %>" size="50" maxlength="50"></td>
                            </tr>
                            <tr>
    	                        <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="text">Nome</font></td>
                                <td><input type="text" disabled value="<%=request.getAttribute("nome") %>" size="50" maxlength="50"></td>
                            </tr>
                            <tr>
    	                        <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="text">Descrizione</font></td>
                                <td><input type="text" disabled value="<%=request.getAttribute("descrizione") %>" size="50" maxlength="50"></td>
                            </tr>
                            <tr>
                                <td colspan='2' align='center'>&nbsp;</td>
                            </tr>
                            <tr>
		                        <td colspan='2' align='center'> 
			<img src='<%=skName%>/buttons/b-home.gif' alt='Home' onclick="window.location='Home.do';">
		</td>
                            </tr>
                        </table>

<%
	}
} else {

String defaultCategory=Settings.getDefaultCategory(proj);
String descrizioneCategoria="";
int idCategoria=0;
if (defaultCategory!=null) 
{
StringTokenizer st = new StringTokenizer(defaultCategory,":");
if (st.hasMoreTokens()) idCategoria=Integer.parseInt(st.nextToken());
if (st.hasMoreTokens()) descrizioneCategoria=st.nextToken();     
} 

%>
<script>
<!--
var param1='<%=idCategoria%>';
var param2='<%=descrizioneCategoria%>';
//--> 
</script>
            <table width="100%" border="0" cellpadding="2" cellspacing="0">
              <tr>
		        <td width="150" align="center" class="bgheader"> 
                  <div align="center" class="tab"><bean:message key="pageTitles.CreateCategory" /></div></td>
		        <td width="1"></td> 
		        <td width="150"  align="center" class="bgdisable"><a href="ManageCategory.do" class="tab"><bean:message key="pageTitles.ManageCategories" /></a></td>
                <td >&nbsp;</td>
			   </tr>
			</table>
            <table width="100%" border="3" class="bordertab">
              <tr>
		<td>		  
		  

<html:form action="/app/CreateCategory" >
	<html:errors />
                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                      <tr> 
                        <td colspan="2" class="bgheader"><font face="Verdana, Arial, Helvetica, sans-serif" size="2"><b><font color="#FFFF00" class="tableheader">Nuova 
                          categoria</font></b></font></td>
    </tr>
    <tr>
                        <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="text">Categoria 
                          Padre</font></td>      
      <td>
      <input type="text" name="descrizioneCategoria" value="<%=descrizioneCategoria%>" readonly="YES" size="50" maxlength="50"> <A  HREF="#" onClick="window.open('CategoryTree.do','buttonwin','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><FONT  SIZE=1 FACE="Verdana, Arial">Seleziona Categoria</FONT></A>
	  <input type="hidden" name="categoriaPadre" value="<%=idCategoria%>" > 
      </td>
    </tr>
    <tr>
                        <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="text">Nome</font></td>
      <td>
        <input type="text" name="nome" size="50" maxlength="50">
      </td>
    </tr>
    <tr>
                        <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2" class="text">Descrizione</font></td>
      <td>
        <input type="text" name="descrizione" size="50" maxlength="50">
      </td>
    </tr>         
  </table><br>

<div align="center">
  <input type="image" src='<%=skName%>/buttons/b-ok.gif' name="Submit" alt="Ok">&nbsp;&nbsp;&nbsp;
  <img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onclick="window.location='Home.do';">
</div>
</td>
	</tr>
</table>
</html:form>
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

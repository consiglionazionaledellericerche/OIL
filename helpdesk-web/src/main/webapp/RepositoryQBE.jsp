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
    <title><bean:message key="applicationName" /> - Archivio segnalazioni</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*, it.cnr.helpdesk.CategoryManagement.valueobjects.*" %>
<!-- #EndEditable --> 

<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
  		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
    				<td width="132"><img src="<%=skName%>/NormalHeaderLeft.gif"></td>
    				<td width="126" background="<%=skName%>/NormalHeaderCenter.gif" >&nbsp;</td>
    				<td background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"> 
      					<!-- #BeginEditable "SectionTitle" -->Archivio&nbsp;Segnalazioni<!-- #EndEditable -->
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
            -- &gt; Archivio Segnalazioni<!-- #EndEditable --></span> 
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
			String param = (String)request.getAttribute("param");
			if(param.startsWith("form")){
%>        
			<form name="QBE" method="POST" action="RepositoryQBE.do">
            <table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr><td colspan='3'>&nbsp;</td></tr>
              <tr align="center"> 
                <td colspan="3" class='listItemName'>Selezionare il periodo da estrarre:</td>
              </tr>              
              <tr><td colspan='3'>&nbsp;</td></tr>
              <tr> 
                <td align="right" class='text'>Anno:</td>
                <td>&nbsp;</td>
                <td><select name="anno" size="1">
<%				int oggi = (new Date()).getYear()+1900;
%>
				<option selected value="<%=oggi%>"><%=oggi--%></option>

<%				for(; oggi>=2002; oggi--){
%>
                    <option value="<%=oggi%>"><%=oggi%></option>
<%
                }
%>
                  </select></td>
              </tr>
              <tr><td colspan='3'>&nbsp;</td></tr>
              <tr> 
                <td align="right" class='text'>Mese:</td>
                <td>&nbsp;</td>
                <td><select name="mese" size="1">
                    <option selected value="0">Gennaio</option>
                    <option value="1">Febbraio</option>
                    <option value="2">Marzo</option>
                    <option value="3">Aprile</option>
                    <option value="4">Maggio</option>
                    <option value="5">Giugno</option>
                    <option value="6">Luglio</option>
                    <option value="7">Agosto</option>
                    <option value="8">Settembre</option>
                    <option value="9">Ottobre</option>
                    <option value="10">Novembre</option>
                    <option value="11">Dicembre</option>
                  </select></td>
              </tr>
              <tr><td colspan='3'>&nbsp;</td></tr>
              <tr>
                <td align="right" class='text'>Categoria:</td>
                <td>&nbsp;</td>
                <td><select name="categoria">
<%
						Collection c = (Collection)request.getAttribute("categs");
					    Iterator i=c.iterator();
					    while(i.hasNext())  {
					    	CategoryValueObject cvo=(CategoryValueObject) i.next();    
					    	if (cvo!=null) {
					    		//StringTokenizer st = new StringTokenizer(cvo,":");
%>
                      <option value="<%=cvo.getId()%>"><%=cvo.getNome()%></option>
<%
					    	}
					    }
%>
                  </select>
                </td>
              </tr>
              <tr><td colspan='3'>&nbsp;</td></tr>
            </table>
            <br><br>
<div align="center">
  <input type="image" src='<%=skName%>/buttons/b-ok.gif' name="Submit" alt="Ok">&nbsp;&nbsp;&nbsp;
  <img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onclick="window.location='Home.do';">
</div>
</td>
	</tr>
</table>
</form>
<%			} else if(param.startsWith("futureReport")){
%>
				<br><br><br><br>
				<table align="center">
				  <tr>
				    <td align='center' bgcolor='ff4444' class='information'>impossibile produrre 
				      report sul mese corrente o sui futuri</td>
				  </tr>
				</table>
				<br><br><input type="button" name="Back" value="Torna indietro" align='center' onClick="history.back();">
<%
			} else if(param.startsWith("emptyReport")){
%>
				<br><br><br><br>
				<table align="center">
				  <tr>
				    <td align='center' bgcolor='ff4444' class='information'>nessuna segnalazione 
				      corrispondente ai criteri selezionati</td>
				  </tr>
				</table>
				<br><br><input type="button" name="Back" value="Torna indietro" align='center' onClick="history.back();">
<%			}else {
				String rName = request.getAttribute("param").toString();
				String rId = rName.substring(0,6) + rName.substring(7).replaceFirst(".rtf","");
%>
		<br><br><br>
		<table width='300' cellpadding='2'>
			<tr><td align='center'><a href='<%= request.getContextPath()+"/app/reports/"+rName %>?id=<%=rId%>' target=_new ><img src='<%=skName%>/icons/word-b.gif' border=0></a></td></tr>
			<tr><td align='center'><a href='<%= request.getContextPath()+"/app/reports/"+rName %>?id=<%=rId%>' target=_new class='copyinfo'>file generato: <%=rName%></a></td></tr>
		</table>
		<br><br><div align='center'><img src='<%=skName%>/buttons/b-home.gif' alt='Annulla' onclick="window.location='Home.do';"></div>
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

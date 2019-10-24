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
<html><!-- InstanceBegin template="/Templates/OilDetailPage.dwt" codeOutsideHTMLIsLocked="true" -->

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
<title>Online interactive helpdesk - Categorie Assegnate</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" --> 
<script language="JavaScript">
<!--
function SelAll ()
{
	var nb;
	var chk;

	if (document.forms[0].SEL_ALL.value == 0) chk=1;	else		chk=0;

	nb = document.forms[0].elements.length
	for (var i=0;i<nb;i++)
	{
		var e = document.forms[0].elements[i];
		e.checked = chk
	}
	document.forms[0].SEL_ALL.value = chk;
}
//-->
</script>
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
    				<td nowrap background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"> 
      					<!-- #BeginEditable "SectionTitle" --> 
            <img src="<%=skName%>/titles/CategorieAssegnate.gif"><!-- #EndEditable -->
					</td>
    				<td  width="258" align="right"><img src="<%=skName%>/NormalHeaderRight1.gif" ></td>		
  				</tr>
			</table>
		</td>
		<td width="23"><img src="<%=skName%>/NormalHeaderRight2.gif" width="23" height="51"></td>
		</tr>
  <tr> 
    <td >

	</td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
        <td ><!-- #BeginEditable "Detail" -->
<%
String param = (String)request.getAttribute("param");
Collection c = (Collection)request.getAttribute("cat");
String grants = "" + request.getAttribute("grants");
if (param!=null) {
	if(param.equals("close")) {
%>
		<script language="JavaScript1.2">
		<!--
		window.close();
		//-->
		</script>
<%
	} else if (param.equals("form")) {
			String dis = "";
			if(grants.equals("exp")) dis="DISABLED";
%>    
        <table border="0" width="100%" cellpadding="2" cellspacing="2">
          <tr> 
            <td  width="130" align="right" class="bgheader"><div class="tableheader">Assegnazione <input type="checkbox" onClick='SelAll();' title='Seleziona  tutte le categorie' <%= dis %> ></div></td>
            <td class="bgheader"><div class="tableheader">Categoria 
              </div></td>
          </tr>
		<form name="AssegnaCategoria" method="POST" action="ManageExpert2.do">
		<input type="hidden" name="SEL_ALL" value="0">
          <%

		Iterator it=c.iterator();
		String indUnit="&nbsp;&nbsp;&nbsp;&nbsp;";
		String ind="";
		int nTab=0;
		int odd = 0;
		while (it.hasNext()) {
				CategoryValueObject cvo=(CategoryValueObject)it.next();
%>
          <tr style="background-color:<%= (odd!=0)?"#EEE":"#FFF" %>"> 
            <td width="8" align="right"> 
                <input type="checkbox" name=<%="'"+cvo.getId() + "' " + (cvo.getAssigned()?"CHECKED ":" ") + dis %> >
            </td>
            <td class='text'>
<%
			nTab=cvo.getLivello()-1;
			ind="";
			for(int i=0;i<nTab;i++) {
				ind=ind+indUnit;
			} 
%> 		<%=ind+cvo.getNome()%> 
	    </td>
          </tr>
<%
			odd = 1 - odd;
		}
%>
          <tr>
            <td colspan='2' align="center">
<%
		if(dis.equals("DISABLED")){
%>
		<img src='<%=skName%>/buttons/b-ok.gif' alt='ok' onclick="window.close();">
<%
		} else {
%>
		<input type='image' src='<%=skName%>/buttons/b-ok.gif' alt='Ok'>&nbsp;&nbsp;&nbsp;
		<img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onclick="window.close();">
<%
		}
%>
            </td>
          </tr>
          </form>
        </table>

            <%
	}
}
%>
	<!-- #EndEditable -->
    </td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td align="center"><!-- #BeginEditable "Buttons" --><!-- #EndEditable --></td>
    <td >&nbsp;</td>
  </tr>
  <tr> 
    <td> 
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
     <td >&nbsp;</td>
  </tr>
</table>

</body>

<!-- InstanceEnd --></html>

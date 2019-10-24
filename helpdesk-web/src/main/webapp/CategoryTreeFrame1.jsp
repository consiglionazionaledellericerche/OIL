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
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<html><!-- InstanceBegin template="/Templates/OilDetailTopFrame.dwt" codeOutsideHTMLIsLocked="true" -->

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
<title><bean:message key="applicationName" /></title>

<style>
<!--
#foldheader{cursor:pointer;cursor:hand ; font-weight:bold ;
list-style-image:url(<%=skName%>/plus.gif)}
#foldinglist{list-style-image:url(<%=skName%>/leaf.gif)}
//-->
</style>
<script type="text/javascript">
<!--

//Smart Folding Menu tree- By Dynamic Drive (rewritten 03/03/02)
//For full source code and more DHTML scripts, visit http://www.dynamicdrive.com
//This credit MUST stay intact for use

var head="display:''";
img1=new Image();
img1.src="<%=skName%>/plus.gif";
img2=new Image();
img2.src="<%=skName%>/minus.gif";

var ns6=document.getElementById&&!document.all;
var ie4=document.all&&navigator.userAgent.indexOf("Opera")==-1;
var ie10=(navigator.appName.indexOf("Internet Explorer")!=-1 && navigator.appVersion.indexOf("MSIE 10")!=-1);

function checkcontained(e){
	var iscontained=0;
	cur=ns6? e.target : event.srcElement;
	i=0;
	if (cur.id=="foldheader")
		iscontained=1;
	else
		while (ns6&&cur.parentNode||(ie4&&cur.parentElement)){
			if (cur.id=="foldheader"||cur.id=="foldinglist"){
				iscontained=(cur.id=="foldheader")? 1 : 0;
				break;
			}
			cur=ns6? cur.parentNode : cur.parentElement;
		}
	if (iscontained){
		var foldercontent=ns6||ie10? cur.nextSibling.nextSibling : cur.all.tags("UL")[0];
		if (foldercontent.style.display=="none"){
			foldercontent.style.display="";
			cur.style.listStyleImage="url(<%=skName%>/minus.gif)";
		} else {
			foldercontent.style.display="none"
			cur.style.listStyleImage="url(<%=skName%>/plus.gif)"
		}
	}
}

if (ie4||ns6)
	document.onclick=checkcontained;

//-->
</script>

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" --><!-- #EndEditable --> 
<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
  		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
    				<td width="132"><img src="<%=skName%>/NormalHeaderLeft.gif"></td>
    				<td width="126" background="<%=skName%>/NormalHeaderCenter.gif" >&nbsp;</td>
    				<td background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"> 
      					<!-- #BeginEditable "SectionTitle" --> 
            <img src="<%=skName%>/titles/Categoria.gif"><!-- #EndEditable -->
					</td>
    				<td  width="258" align="right"><img src="<%=skName%>/NormalHeaderRight1.gif" ></td>		
  				</tr>
			</table>
		</td>
		<td width="23"><img src="<%=skName%>/NormalHeaderRight2.gif" width="23" height="51"></td>
		</tr>
  <tr> 
        <td ><!-- #BeginEditable "Detail" -->
	
<jsp:useBean id="categoria" class="it.cnr.helpdesk.CategoryManagement.javabeans.Category"/>
<br>


<%
String param=request.getAttribute("param").toString();
if (param.equals("close")) {
	if ((request.getAttribute("idCategoria")!=null)&&(request.getAttribute("descrizioneCategoria")!=null)){
	String idCategoria=request.getAttribute("idCategoria").toString();
	String descrizioneCategoria=request.getAttribute("descrizioneCategoria").toString();
	String experts = ""+request.getAttribute("experts");
%>
<script type="text/javascript">
<!--
//window.parent.opener.document.forms.item(0).descrizioneCategoria.value="<%=descrizioneCategoria%>";
//window.parent.opener.document.forms.item(0).categoriaPadre.value="<%=idCategoria%>";
window.parent.opener.eval("param1='<%=idCategoria%>'");
window.parent.opener.eval("param2=\"<%= StringEscapeUtils.escapeJavaScript(descrizioneCategoria) %>\"");
window.parent.opener.eval("param3=\"<%= StringEscapeUtils.escapeJavaScript(experts) %>\"");
window.parent.opener.eval("aggiorna();");
window.parent.close();
//-->
</script>
<%
    } else {%>
<script type="text/javascript">
<!--
window.parent.close();
//-->
</script>
<%}
} 
else
{
%>    
<form method="POST" action="CategoryTreeFrame1.do">
	<ul id="foldinglist" style=&{head};>
       <%=categoria.printTree(proj)%>
	</ul>                           
</form>
<br><br>
<div align='center' class='text'>
Premere <img src='<%=skName%>/plus.gif'> per visualizzare le sottocategorie
</div>
<%}%>	
	
<!-- #EndEditable -->
    </td>
    <td >&nbsp;</td>
  </tr>

</table>

</body>

<!-- InstanceEnd --></html>

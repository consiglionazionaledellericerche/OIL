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
<title><bean:message key="applicationName" /></title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->
<style>
<!--
#foldheader{cursor:pointer;cursor:hand ; font-weight:bold ;
list-style-image:url(<%=skName%>/plus.gif)}
#foldinglist{list-style-image:url(<%=skName%>/leaf.gif)}
//-->
</style>
<script language="JavaScript1.2">
<!--

//Smart Folding Menu tree- By Dynamic Drive (rewritten 03/03/02)
//For full source code and more DHTML scripts, visit http://www.dynamicdrive.com
//This credit MUST stay intact for use

var head="display:''"
img1=new Image()
img1.src="<%=skName%>/plus.gif"
img2=new Image()
img2.src="<%=skName%>/minus.gif"

var ns6=document.getElementById&&!document.all
var ie4=document.all&&navigator.userAgent.indexOf("Opera")==-1

function checkcontained(e){
var iscontained=0
cur=ns6? e.target : event.srcElement
i=0
if (cur.id=="foldheader")
iscontained=1
else
while (ns6&&cur.parentNode||(ie4&&cur.parentElement)){
if (cur.id=="foldheader"||cur.id=="foldinglist"){
iscontained=(cur.id=="foldheader")? 1 : 0
break
}
cur=ns6? cur.parentNode : cur.parentElement
}

if (iscontained){
var foldercontent=ns6? cur.nextSibling.nextSibling : cur.all.tags("UL")[0]
if (foldercontent.style.display=="none"){
foldercontent.style.display=""
cur.style.listStyleImage="url(<%=skName%>/minus.gif)"
}
else{
foldercontent.style.display="none"
cur.style.listStyleImage="url(<%=skName%>/plus.gif)"
}
}
}

if (ie4||ns6)
document.onclick=checkcontained

//-->
</script>
<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" --><!-- #EndEditable --> 
<!-- #BeginEditable "Content" -->
<%@ page import ="java.util.*"%>	
<jsp:useBean id="categoria" class="it.cnr.helpdesk.CategoryManagement.javabeans.Category"/>

<form method="POST" action="RepositoryUpdate1.do">
<%
	if((request.getMethod()).equals("GET")){
%>
		<div align='center' class='listItemName'>
		Selezionare l'anno da gestire:<br><br>
		<select name="anno" size="1">
<%		int oggi = (new Date()).getYear()+1900;
%>
		<option selected value="<%=oggi%>"><%=oggi--%></option>
<%		for(; oggi>=2002; oggi--){
%>
            <option value="<%=oggi%>"><%=oggi%></option>
<%
        }
%>
        </select><br><br>
        <input type="image" src='<%=skName%>/buttons/b-ok.gif' name="Submit" alt="Ok">
        </div>
<%	} else {
%>		  

	<ul id="foldinglist" style=&{head};>
       <%=categoria.printTree(proj).replaceAll("value=\"","onclick=\"window.parent.mainFrame.document.location='RepositoryUpdate2.do?id="+request.getParameter("anno")).replaceAll("(([0-9]+)(:))","$2';\" value=\"")%>
	</ul>     
<%	}
%>                      
</form>
<!-- #EndEditable -->
</body>
<!-- InstanceEnd --></html>

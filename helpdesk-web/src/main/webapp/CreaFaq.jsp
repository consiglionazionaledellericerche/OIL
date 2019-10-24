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
<title><bean:message key="applicationName" /> - Crea Segnalazione</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*,it.cnr.helpdesk.FaqManagement.javabeans.*,it.cnr.helpdesk.CategoryManagement.valueobjects.*" %>
<!-- #EndEditable --> 

<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
  		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
    				<td width="132"><img src="<%=skName%>/NormalHeaderLeft.gif"></td>
    				<td width="126" background="<%=skName%>/NormalHeaderCenter.gif" >&nbsp;</td>
    				<td background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"> 
      					<!-- #BeginEditable "SectionTitle" --><img src="<%=skName%>/titles/CreaFAQ.gif"><!-- #EndEditable -->
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
            --&gt; Crea Faq<!-- #EndEditable --></span> 
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
Collection c=(Collection)session.getAttribute("it.cnr.helpdesk.faq.categories");
if(param!=null){
	if (param.equals("ok")) {
		Faq faq = (Faq)request.getAttribute("faq");   
		session.removeAttribute("it.cnr.helpdesk.faq.categories");
%>
	<table cellpadding="10" cellspacing="2" class="bordertab">
	<tr><td colspan='2' class='information'>Inserimento effettuato<br></td></tr>
	  <tr>
	  	<td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Categoria</font></td>
	  	<td><input type='text' disabled value="<%= faq.getIdCategoria() %>" size="30" /></td></tr>
<tr> 
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Oggetto</font></td>
      <td>
      	<input type="text" disabled value="<%= faq.getTitolo() %>" size="50" maxlength="50" />
      </td>
    </tr>
    <tr> 
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Descrizione</font></td>
      <td>
      	<textarea disabled cols="48" rows="10" style="width:100%"><%= faq.getDescrizione() %></textarea> 
	  </td>
    </tr>
    <tr><td colspan='2' align='center'><img src='<%=skName%>/buttons/b-home.gif' alt='Home' onclick="window.location='Home.do';"></td></tr> 
  </table>
<%
	}
}else{
%>    
            <table width="100%" border="0" cellpadding="2" cellspacing="0">
              <tr>
		        <td width="150" align="center" class="bgheader"> 
                  <p class="tab">Crea Faq</p>
                  </td>
				 <td width="1"></td> 
		        <td width="180"  align="center" class="bgdisable"><a href="UpdateFaq.do" class="tab">Modifica/Cancella 
                  Faq</a></td>
				 <td width="1"></td>                 
		        <td >&nbsp;</td>				
	</tr>
</table>		  
            <table width="100%" border="3" class="bordertab">
              <tr>
<td>


<html:form action="/app/CreaFaq" >
<html:errors />
                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                      <tr> 
                        <td colspan="2" class="bgheader"><font face="Verdana, Arial, Helvetica, sans-serif" size="2"><b><font color="#FFFF00">Dati 
                          Faq</font></b></font></td>
    </tr>
    <tr> 
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Categoria</font></td>
      <td> 
      <p><select size="1" name="categoria">   
<%
String opt = "";
if(c!=null){
	Iterator i=c.iterator();
	while(i.hasNext()) {
		CategoryValueObject cvo=(CategoryValueObject) i.next();    
		if (cvo.getAssigned()) {
			opt = opt+ "<option value='"+cvo.getId()+"'>"+cvo.getNome()+"</option>\n";
    	}
    }
} 
if(opt.length()==0){
%>
	<option value=''>## Nessuna categoria disponibile ##</option>    
<%
}else{
%>
<option value='' selected>## Seleziona una categoria ##</option>
<%=opt%>
<%
}
%>
    </select></p>
    
      </td>
    </tr>    
    <tr> 
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Oggetto</font></td>
      <td>
      	<html:text property="titolo" size="50" maxlength="50" />
      </td>
    </tr>
    <tr> 
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Descrizione</font></td>
      <td>
      	<html:textarea property="descrizione" cols="48" rows="10" style="width:100%" /> 
	  </td>
    </tr>     
  </table> 
<div align="center"> 
<input type='image' src='<%=skName%>/buttons/b-ok.gif' alt='Ok'>&nbsp;&nbsp;&nbsp;
<img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onclick="window.location='Home.do';">
</div>
</html:form>
</td>
</tr>
</table>
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

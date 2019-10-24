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
<title>Online interactive helpdesk - Crea Segnalazione</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->

<script language="JavaScript1.2">
<!--
  var param1="";
  var param2="";
  var submitted = false;
  function aggiorna(){
    self.document.forms[0].elements[1].value=param1;
    self.document.forms[0].elements[0].value=param2;
  }


function sendOff(){
	if(!submitted){
		self.document.forms[0].submit();
		submitted = true;
	}
}

function attachFile(){
	if(!submitted){
        self.document.forms[0].attach.value = "yes";
        self.document.forms[0].submit();
		submitted = true;
	}
}
//-->
</script>

<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="it.cnr.helpdesk.ProblemManagement.javabeans.Problem,java.util.*,it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject" %>
<!-- #EndEditable --> 

<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
  		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
    				<td width="132"><img src="<%=skName%>/NormalHeaderLeft.gif"></td>
    				<td width="126" background="<%=skName%>/NormalHeaderCenter.gif" >&nbsp;</td>
    				<td background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"> 
      					<!-- #BeginEditable "SectionTitle" --><img src="<%=skName%>/titles/CreaSegnalazione.gif"><!-- #EndEditable -->
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
            --&gt; Crea Segnalazione<!-- #EndEditable --></span> 
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
Vector v = (Vector)session.getAttribute("priorities");
if(param!=null){
	if (param.equals("ok")) {     
		ProblemValueObject pvo = (ProblemValueObject)request.getAttribute("problema");
		String pr = v.elementAt(pvo.getPriorita()-1).toString();
		session.removeAttribute("priorities");
%>
		<table width="100%" cellpadding="2" cellspacing="2" class="bordertab">
			<tr><td colspan='2' class="information">Inserimento effettuato<br></td></tr>
			<tr> 
				<td width="20%" class="text">Categoria</td>
				<td> 
					<input type="text" name="descrizioneCategoria" value="<%=pvo.getCategoriaDescrizione()%>" disabled size="50" maxlength="50">
				</td>
			</tr>
			<tr> 
				<td width="20%" class="text">Oggetto</td>
				<td> 
					<input type="text" disabled  value="<%=pvo.getTitolo()%>" size="50" maxlength="50" />
				</td>
			</tr>
			<tr> 
				<td width="20%" class="text">Descrizione</td>
				<td> 
					<textarea cols="48" rows="10" style="width:100%" disabled><%=pvo.getDescrizione()%></textarea>
				</td>
			</tr>
			<tr> 
      			<td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Priorit&agrave;</font></td>
      			<td> 
        			<input type="text" disabled value="<%= pr.substring(pr.indexOf(":")+1)%>" />
        		</td>
        	</tr>
			<tr><td colspan='2' align='center'><br><img src='<%=skName%>/buttons/b-home.gif' alt='Home' onclick="window.location='Home.do';"></td></tr>
		</table>
<%
	}
} else {
int idCategoria;
String descrizioneCategoria;

if(request.getParameter("idCategoria")!=null){
	idCategoria=Integer.parseInt(request.getParameter("idCategoria").toString());
	descrizioneCategoria=(String)request.getParameter("descrizioneCategoria");
} else {
	idCategoria=Integer.parseInt(request.getAttribute("idCategoria").toString());
	descrizioneCategoria=(String)request.getAttribute("descrizioneCategoria");
}
%>
<script language="JavaScript1.2">
<!--
  var param1='<%=idCategoria%>';
  var param2='<%=descrizioneCategoria%>';
//-->
</script>
		  
            <table class="text" width="100%" border="0" cellpadding="2" cellspacing="0">
              <tr>
		        <td width="150" align="center" class="bgheader"> 
                  <div class="tab">Crea segnalazione</div></td>
				 <td width="1"></td> 
		        <td width="180"  align="center" class="bgdisable"><a href="UserQueueDetail.do?closed=NO" class="tab">Segnalazioni 
                  pendenti</a></td>
				 <td width="1"></td> 
                <td width="150" align="center" class="bgdisable"><a href="UserQueueDetail.do?closed=YES" class="tab">Segnalazioni 
                  chiuse</a></td>
		        <td >&nbsp;</td>				
	</tr>
</table>		  
            <table width="100%" border="3" class="bordertab">
              <tr>
<td>

<html:form action="/app/CreaSegnalazione" >
<html:errors />
                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                      <tr> 
                        <td colspan="2" class="bgheader"><font face="Verdana, Arial, Helvetica, sans-serif" size="2"><b><font color="#FFFF00">Dati 
                          segnalazione</font></b></font></td>
    </tr>
    <tr> 
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Categoria</font></td>
      <td> 
      <input type="text" name="descrizioneCategoria" value="<%=descrizioneCategoria%>" readonly="YES" size="50" maxlength="50"> <A  HREF="#" onClick="window.open('CategoryTree.do','buttonwin','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><FONT  SIZE=1 FACE="Verdana, Arial">Seleziona Categoria</FONT></A>
      <input type="hidden" name="idCategoria" value="<%=idCategoria%>" > 
    
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
        <html:textarea property="descrizione" cols="48" rows="10" style="width:100%" /></td>
    </tr>  
    <tr> 
      <td width="20%"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Priorit&agrave;</font></td>
      <td> 
        <select name="priorita">
<%
            StringTokenizer sp;
            String pr,df;
            for(int i = 1; i<=v.size(); i++){
                 sp = new StringTokenizer((String)v.elementAt(i-1),":");
                 df=sp.nextToken().equals("y")?" selected":"";
                 pr=sp.nextToken();
%>
        	<option value="<%= i %>" <%= df %> ><%= pr %></option>
<%
			}
%>

        </select>
	  </td>
    </tr>     
  </table>
                  <table width="100%">
                    <tr>
		              <td width="58%" align="right">
		              	<img src="<%=skName%>/buttons/b-ok.gif" onclick="sendOff();" alt="Ok" />&nbsp;&nbsp;&nbsp;
		              	<img src='<%=skName%>/buttons/b-annulla.gif' alt='annulla' onclick="window.location='Home.do';">
                      </td>
		              <td width="42%" align="right"> 
		              	<img src="<%=skName%>/buttons/b-allega.gif" onclick="attachFile();" alt="Allega File" />
                      </td>
	</tr>
</table>
<html:hidden property="attach" value="no" />
<!-- html:hidden property="page" value="1"/ -->
</html:form>
<%}%>
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

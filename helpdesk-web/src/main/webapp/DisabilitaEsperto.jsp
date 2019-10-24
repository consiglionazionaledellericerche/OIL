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
<!-- #BeginEditable "ErrorPage" -->
<%@ page errorPage="/app/OilErrorPage.do" %>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<% String proj = session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<% String skName = request.getContextPath()+"/skins/" + session.getAttribute("it.cnr.helpdesk.instance").toString(); %>
<link rel="stylesheet" href="<%=skName%>/OilStyles.css" type="text/css">
<!-- #BeginEditable "doctitle" -->
<title><bean:message key="applicationName" /> - <bean:message key="pageTitles.DisableExpert" /></title>
 
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->
<script language='JavaScript'>
        var flags = new Array();
        var admissible;
        
        function docheck(){
                admissible = true;
                for(var i = 0; i< flags.length; i++) {
                        if(flags[i]){
                                 admissible = false;
                        }
                }
                if(admissible)
                    document.bsubmit.src="<%=skName%>/buttons/b-disable.gif";
                else
                	document.bsubmit.src="<%=skName%>/buttons/b-disablex.gif";
        }
        
        function chksubmit(){
        	docheck();
        	if(admissible){
        		document.form.submit();
        	}
        }
        
</script>
<style type="text/css">
<!--
.sbox {
	border: thin groove;
	text-align: justify;
}
-->
</style>
<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" --><%@ page import ="java.util.*" %><!-- #EndEditable --> 
<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
  		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
    				<td width="132"><img src="<%=skName%>/NormalHeaderLeft.gif"></td>
    				<td width="126" background="<%=skName%>/NormalHeaderCenter.gif" >&nbsp;</td>
    				<td nowrap background="<%=skName%>/NormalHeaderCenter.gif"  align="center" class="sectiontitle"> 
      					<!-- #BeginEditable "SectionTitle" --> 
      					<img src="<%=skName%>/titles/DisabilitaUtente.gif"> <!-- #EndEditable -->
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
<jsp:useBean id="problema" scope="page" class="it.cnr.helpdesk.ProblemManagement.javabeans.Problem" />
<%
        String login = request.getParameter("login");
        String param = "" + request.getAttribute("param");
        if (param.equals("close")) {
%>
<p class="information">Utenza disabilitata.</p>
<p align='center'><img src='<%=skName%>/buttons/b-chiudi.gif' alt='close' onclick="opener.location.reload(); window.close();"></p>

<%
        }else{
%>
<!-- html:form name="form" action="DisabilitaEsperto" -->
<form name="form" action="DisabilitaEsperto.do" method="POST">
<html:errors/>
<%
    int ncount = Integer.parseInt(request.getAttribute("ncount").toString());
    int nflag = Integer.parseInt(request.getAttribute("nflag").toString());
    if(ncount!=0){
    	String feasible = (String)request.getAttribute("feasible");
    	Collection rows = (Collection)request.getAttribute("rows");
%>
                        <table border="1" width="100%" cellpadding="2" cellspacing="2" bgcolor="#88aadd" class="text">
                            <tr class="bgheader"> 
                                <td width="10%" class="tableheader">Id</td>
                                <td width="40%" class="tableheader">Oggetto</td>
                                <td width="10%" class="tableheader">Stato</td>
                                <td width="10%" class="tableheader">Categoria</td>
                                <td width="20%" class="tableheader">Assegnatario</td>
                            </tr>
<%
        for(int j=-1; j<nflag; j++) {
%>
				<script language="JavaScript">
				        <!--
        		                flags.push(true);
				        //-->
				</script>
<%
        }
        Iterator righe = rows.iterator();
        while(righe.hasNext()){
            String[] cell = (String[])righe.next();
%>
                            <tr> 
                                <td><%=cell[0]%></td>
                                <td><%=cell[1]%></td>
                                <td><%=cell[2]%></td>
                                <td><%=cell[3]%></td>
                                <td><%=cell[4]%><%=cell[5]%></td>
                            </tr>
                            <%
		}
%>
			                <tr>
			                    <td colspan='5'>
			       <div align='center' class="copyinfo">Vengono qui proposte le segnalazioni tuttora assegnate all'esperto che si sta per disabilitare. 
E' obbligatorio riassegnare ad altro esperto quelle nello stato &quot;<%=problema.getFormatStatus(3,"External")%>&quot; o &quot;<%=problema.getFormatStatus(4,"Closed")%>&quot;, 
mentre quelle nello stato &quot;<%=problema.getFormatStatus(2,"Working")%>&quot;, se non riassegnate, vengono riportate &quot;<%=problema.getFormatStatus(1,"Open")%>&quot;.<br>
                                <br>
<%
		if(feasible!=null) {
%>
                                <table width="50%" border="0" cellspacing="5" class="sbox">
                                    <tr>
                                        <td width="4%" class="bordertab"><img src="<%=skName%>/warn.gif" width="16" height="16"></td>
                                        <td width="96%" rowspan="2" class="copyinfo">Le voci che riportano solo l'opzione<br><strong>##No esperti abilitati##</strong><br>si riferiscono a segnalazioni la cui categoria non è associata a nessun altro esperto.</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                </table>
<%
		}
%>
                                </div></td>
                            </tr>
                        </table>
<%
	}
%>
                        <br>
                        <p align='center' class='text'>
Sei sicuro di voler disabilitare l'utente <u><b><%=login%></b></u> ?<br>
                            <br>
                            <input name="login" type="hidden" value="<%=login%>">
                            <input name="count" type="hidden" value="<%=ncount%>">
                            <img name="bsubmit" alt='Disabilita' onclick='chksubmit();' src='<%=skName%>/buttons/<%=(nflag!=-1?"b-disablex.gif":"b-disable.gif")%>' >&nbsp;&nbsp;
                            <img src='<%=skName%>/buttons/b-annulla.gif' alt='Annulla' onclick="window.close();" />
</p>
</form>
<%
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

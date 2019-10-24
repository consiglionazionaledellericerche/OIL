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
<title><bean:message key="applicationName" /> - <bean:message key="AdvancedSearch" /></title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" --> 
<script language="JavaScript1.2">
<!--
var good;

function checkIdSegnalazione(field) {
var goodIdSegnalazione=false;
if (field.value=="") goodIdSegnalazione=true;
else
{    
var v = parseFloat(field.value);
if (isNaN(v)) goodIdSegnalazione=false;
         else {goodIdSegnalazione=true;
               field.value=Math.round(v)
         }
}

if (goodIdSegnalazione){
   good = true;
} else {
   alert('Il campo Id Segnalazione deve essere numerico');
   field.focus();
   field.select();
   good = false;
   }
}



function sendOff(){
  
   good = false;
   checkIdSegnalazione(document.QBESettings.idSegnalazione);     
   if (good) document.QBESettings.submit();
}

var field=0;
var param1='';	//descrizione
var param2='';	//struttura

function aggiorna(){
	if(field==1){
	    self.document.forms[0].OriginatoreStrutturaD.value=param1;
		self.document.forms[0].OriginatoreStruttura.value=param2;
	} else if(field==2){
	    self.document.forms[0].EspertoStrutturaD.value=param1;
		self.document.forms[0].EspertoStruttura.value=param2;
	}
	field=0;
}
//-->
</script>
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
      					<!-- #BeginEditable "SectionTitle" --><img src="<%=skName%>/titles/RicercaAvanzata.gif"><!-- #EndEditable -->
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
            --&gt; <bean:message key="AdvancedSearch" /><!-- #EndEditable --></span> 
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
            <table width="100%" border="0" cellpadding="2" cellspacing="0">
              <tr> 
                <td width="180"  align="center" class="bgdisable" ><a href="ProblemCountPerCategory.do?closed=NO" class="tab"><bean:message key="PendingTickets" /></a></td>
                <td width="1"></td>
                <td width="150" align="center" class="bgdisable"><a href="ProblemCountPerCategory.do?closed=YES" class="tab"><bean:message key="ClosedTickets" /></a></td>
                <td width="1"></td>
                <td width="150" align="center" class="bgdisable"><a href="ProblemCountPerCategory.do?closed=VER" class="tab"><bean:message key="VerifiedTickets" /></a></td>
                <td width="1"></td>
                <td width="150" align="center" class="bgdisable"><a href="ProblemCountPerCategory.do?closed=STO" class="tab"><bean:message key="TicketRepository" /></a></td>
                <td width="1"></td>
                <td width="150"  align="center" class="bgheader" > <div class="tab"><bean:message key="AdvancedSearch" /></div></td>
                <td >&nbsp;</td>
              </tr>
            </table>	   
            <table width="100%" border="3" class="bordertab">
              <tr>
<td>

<%
Vector stateId2Description=(Vector)request.getAttribute("status");
Collection c = (Collection)request.getAttribute("coll");
if(stateId2Description!=null && c!=null){
%>    
     
<form name="QBESettings" method="POST" action="ExpertQueueDetail.do">
                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                      <tr> 
                        <td colspan="4" class="bgheader"><font face="Verdana, Arial, Helvetica, sans-serif" size="2"><b><font color="#FFFF00">Parametri 
                          di Ricerca </font></b></font></td>
                      </tr>
                      <tr> 
                        <td width="10%" class="bgdisable"> <div class="tableheader">Id Segnalazione</div></td>
                        <td width="45%"> <input type="text" name="idSegnalazione" size="50">                        </td>
                        <td width="22%" align="right">Ordinamento</td>
                        <td width="23%"><select name="ordinamento" id="ordinamento">
                          <option value="DESC" selected>prima segnalazioni recenti</option>
                          <option value="ASC">prima segnalazioni vecchie</option>
                        </select>
                        </td>
                      </tr>
                      <tr> 
                        <td width="20%" class="bgdisable"> <div class="tableheader"><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Categoria</font></div></td>
                        <td colspan="3"> <p>
                            <select size="1" name="Categoria">
                              <%
    Iterator i=c.iterator();
    while(i.hasNext())  {
    	String cvo=(String) i.next();    
    	if (cvo!=null) {
    		StringTokenizer st = new StringTokenizer(cvo,":");
%>
                              <option value="<%=st.nextToken()%>"><%=st.nextToken()%></option>
<%
    	}
    }
%>
                            </select>
                          </p></td>
                      </tr>
                      <tr> 
                        <td width="10%" class="bgdisable"> <div class="tableheader"> Stato</div></td>
                        <td width="90%" colspan="3"> <p> 
                            <select size="1" name="Stato">
                              <option value="0" selected>Tutti</option>
                              <option value="1"><%=(String) stateId2Description.elementAt(0)%></option>
                              <option value="2"><%=(String) stateId2Description.elementAt(1)%></option>
                              <option value="3"><%=(String) stateId2Description.elementAt(2)%></option>
                              <option value="4"><%=(String) stateId2Description.elementAt(3)%></option>
                              <option value="5"><%=(String) stateId2Description.elementAt(4)%></option>
                              <option value="6"><%=(String) stateId2Description.elementAt(5)%></option>
                              <option value="7"><%=(String) stateId2Description.elementAt(6)%></option>
                            </select>
                        </td>
                      </tr>
                      <tr> 
                        <td width="10%" class="bgdisable"> <div class="tableheader"> Nome Originatore</div></td>
                        <td width="90%" colspan="3"> <input type="text" name="OriginatoreNome" size="20">                        </td>
                      </tr>
                      <tr> 
                        <td width="10%" class="bgdisable"> <div class="tableheader">Cognome 
                            Originatore</div></td>
                        <td width="90%" colspan="3"> <input type="text" name="OriginatoreCognome" size="20">                        </td>
                      </tr>
                      <tr> 
                        <td width="10%" class="bgdisable"> <div class="tableheader">Struttura 
                            Originatore</div></td>
                        <td width="90%" colspan="3">
                        <input type="text" name="OriginatoreStrutturaD" size="40" maxlength="50" readonly />      
						<input type="hidden" name="OriginatoreStruttura" /> <img src='<%=skName%>/rtable.gif' alt='Seleziona da elenco' onClick="field=1; window.open('StruttureList.do','listaStrutture','height=430,width=900,resizable=yes,scrollbars=yes,toolbar=no,location=no,statusbar=no')" >                        </td>
                      </tr>
                      <tr> 
                        <td width="10%" class="bgdisable"> <div class="tableheader">Nome 
                            Esperto</div></td>
                        <td width="90%" colspan="3"> <input type="text" name="EspertoNome" size="20">                        </td>
                      </tr>
                      <tr> 
                        <td width="10%" class="bgdisable"> <div class="tableheader">Cognome 
                            Esperto</div></td>
                        <td width="90%" colspan="3"> <input type="text" name="EspertoCognome" size="20">                        </td>
                      </tr>
                      <tr> 
                        <td width="10%" class="bgdisable"> <div class="tableheader">Struttura 
                            Esperto</div></td>
                        <td width="90%" colspan="3">
                        <input type="text" name="EspertoStrutturaD" size="40" maxlength="50" readonly />      
						<input type="hidden" name="EspertoStruttura" /> <img src='<%=skName%>/rtable.gif' alt='Seleziona da elenco' onClick="field=2; window.open('StruttureList.do','listaStrutture','height=430,width=900,resizable=yes,scrollbars=yes,toolbar=no,location=no,statusbar=no')" >                        </td>
                      </tr>
                      <tr> 
                        <td width="20%" class="bgdisable"> <div class="tableheader">Oggetto</div></td>
                        <td colspan="3"> <input type="text" name="Titolo" size="50" maxlength="50">                        </td>
                      </tr>
                      <tr> 
                        <td width="20%" class="bgdisable"> <div class="tableheader">Descrizione</div></td>
                        <td colspan="3"> <input type="text" name="Descrizione" size="75" /> (cerca nelle note <input type="checkbox" name="extendedSearch" value="true" />)</td>
                      </tr>
                    </table> 
</form> 
<div align="center"> 
   <input type="image" src='<%=skName%>/buttons/b-ok.gif' onClick="sendOff();">
</div> 
<%
}
%>
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

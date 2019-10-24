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
<title><bean:message key="applicationName" /> - <bean:message key="SegnalazioneDetailTitle" /></title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->
<style type="text/css">
<!--
#toolTipBox {
	display: none;
	padding: 5;
	font-size: 12px;
	border: black solid 1px;
	font-family: verdana;
	position: absolute;
  background-color: #95BDE1;
  color: 000000;
}
-->
</style>
<script type="text/javascript">
<!--
var theObj="";
function toolTip(text,me) {
  theObj=me;
  theObj.onmousemove=updatePos;
  document.getElementById('toolTipBox').innerHTML=text;
  document.getElementById('toolTipBox').style.display="block";
  window.onscroll=updatePos;
}
function updatePos() {
  var ev=arguments[0]?arguments[0]:event;
  var x=ev.clientX;
  var y=ev.clientY;
  diffX=24;
  diffY=0;
  document.getElementById('toolTipBox').style.top  = y-2+diffY+document.body.scrollTop+ "px";
  document.getElementById('toolTipBox').style.left = x-2+diffX+document.body.scrollLeft+"px";
  theObj.onmouseout=hideMe;
}
function hideMe() {
  document.getElementById('toolTipBox').style.display="none";
}
-->
</script>
<!-- #EndEditable -->
</head>

<body>
<!-- #BeginEditable "Imports" -->
<%@ page import ="java.util.*,it.cnr.helpdesk.ProblemManagement.valueobjects.*,it.cnr.helpdesk.UserManagement.javabeans.User,org.apache.commons.lang.StringEscapeUtils"%>
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
           <img src="<%=skName%>/titles/DettaglioSegnalazione.gif"><!-- #EndEditable -->
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
            <%		
User  utente = (User) session.getAttribute("it.cnr.helpdesk.currentuser");
String closed = (String) session.getAttribute("it.cnr.helpdesk.closed");
int profile=utente.getProfile();
%>
<a href="Home.do" class="navigation"><bean:message key="navigation.Home" /></a> --&gt;  
<%
if  (profile==1){
			if (closed.equals("YES")) {%>
            	<a href="UserQueueDetail.do?closed=YES" class="navigation"><bean:message key="ClosedTickets" /></a> 
            <%} else if  (closed.equals("NO"))  {%>
            	<a href="UserQueueDetail.do?closed=NO" class="navigation"><bean:message key="PendingTickets" /></a> 
            <%}
} else if (profile==2) {
				if (closed.equals("YES")) {%>
            	<a href="ProblemCountPerCategory.do?closed=YES" class="navigation"><bean:message key="ClosedTickets" />: <bean:message key="CountPerCategory" /></a>   --&gt;  
				<a href="javascript:history.back()" class="navigation"><bean:message key="ClosedTicketsList" /></a>
            <%} else if  (closed.equals("NO"))  {%>
            	<a href="ProblemCountPerCategory.do?closed=NO" class="navigation"><bean:message key="PendingTickets" />: <bean:message key="CountPerCategory" /></a>  --&gt;  
				<a href="javascript:history.back()" class="navigation"><bean:message key="PendingTicketsList" /></a>				 
			<%} else if  (closed.equals("VER"))  {%>	
				<a href="ProblemCountPerCategory.do?closed=VER" class="navigation"><bean:message key="VerifiedTickets" />: <bean:message key="CountPerCategory" /></a>  --&gt;  
				<a href="javascript:history.back()" class="navigation"><bean:message key="VerifiedTicketsList" /></a>				 
			<%} else if  (closed.equals("STO"))  {%>
			    <a href="ProblemCountPerCategory.do?closed=STO" class="navigation"><bean:message key="TicketRepository" />: <bean:message key="CountPerCategory" /></a>  --&gt;  
				<a href="javascript:history.back()" class="navigation"><bean:message key="TicketsArchiveList" /></a>				 
            <%} else if  (closed.equals("QBE"))  {%>
			    <a href="ExpertQBESettings.do" class="navigation"><bean:message key="AdvancedSearch" /></a>  --&gt;  
				<a href="javascript:history.back()" class="navigation"><bean:message key="SearchResults" /></a>				 
            <%}
			}
            else if(profile==4){
            	if (closed.equals("YES")) {%>
            	<a href="ProblemCountPerCategoryValidator.do?closed=YES" class="navigation"><bean:message key="ClosedTickets" />: <bean:message key="CountPerCategory" /></a>   --&gt;  
				<a href="javascript:history.back()" class="navigation"><bean:message key="ClosedTicketsList" /></a>
            <%} else if  (closed.equals("NO"))  {%>
            	<a href="ProblemCountPerCategoryValidator.do?closed=NO" class="navigation"><bean:message key="PendingTickets" />: <bean:message key="CountPerCategory" /></a>  --&gt;  
				<a href="javascript:history.back()" class="navigation"><bean:message key="PendingTicketsList" /></a>				 
			<%}
            	           	
            }
%>
            --&gt; <bean:message key="SegnalazioneDetailTitle" /><!-- #EndEditable --></span> 
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

boolean ownerShip=false;
ProblemValueObject pvo=(ProblemValueObject) request.getAttribute("pvo");
Collection events=(Collection) request.getAttribute("events");
ArrayList files = (ArrayList) request.getAttribute("files");

//viene recuperato in CambioStatoAction per essere un destinatario mail nella transizione stato segnalazione 6 --> 7
if(profile==4)
	session.setAttribute("ESPERTOLOGIN",pvo.getEsperto());




if(request.getAttribute("ownership")!=null)
	if(request.getAttribute("ownership").toString().equals("true"))
		ownerShip=true;
%>
<form name="Segnalazione" method='POST'>

              <table border="0" width="100%" cellpadding="2" cellspacing="2">
                <tr> 
                  <td width="10%" class="bgheader"><div class="tableheader">Id 
                      Segnalazione</div></td>
                  <td width="90%"> <input type="text" name="idSegnalazione" value="<%=pvo.getIdSegnalazione()%>" readonly="YES" size="50"> 
                  </td>
                </tr>
                <tr> 
                  <td width="10%" class="bgheader"><div class="tableheader">Oggetto</div></td>
                  <td width="90%"> <input type="text" name="oggetto" value="<%=pvo.getTitolo()%>" readonly="YES" size="50"> 
                  </td>
                </tr>
                <tr> 
                  <td width="10%" class="bgheader"><div class="tableheader">Categoria</div></td>
                  <td width="90%"> <input type="text" name="categoria" value="<%=pvo.getCategoriaDescrizione()%>" readonly="YES" size="50"> 
                    <%
/*	 if ((profile==2 && ownerShip ) || pvo.getStato()==1) {
		 if(pvo.getStato()!=6 && pvo.getStato()!=7){
		 // recupero stato precedente segnalazione 
		 //int statoOld = Integer.parseInt((String)session.getAttribute("oldstato"));
		 
    // se il problema è "open" la categoria la possono cambiare tutti
    // se il problema è in un altro stato diverso da 6 e 7 la può cambiare solo l'esperto 
*/
	if (request.getAttribute("updCat")!=null){    
    %> <A  HREF="#" onClick="window.open('CambioCategoria.do?idSegnalazione=<%=pvo.getIdSegnalazione()%>&idCategoria=<%=pvo.getCategoria()%>&nomeCategoria=<%=StringEscapeUtils.escapeJavaScript(pvo.getCategoriaDescrizione())%>','CambioCategoria','height=500,width=870,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><FONT  SIZE=1 FACE="Verdana, Arial">Cambia 
                    categoria</FONT></A> 
           <%} 
	//} %> </td>
                </tr>
                <tr> 
                  <td width="10%" class="bgheader"><div class="tableheader">Stato</div></td>
                  
                  <%  String statoDescrizioneSegnalazione = pvo.getStatoDescrizione();
                      int idStato = pvo.getStato();
                	  if(profile==1 && (idStato == 6 || idStato == 7))
                		statoDescrizioneSegnalazione = "Validation";
                	%> 
                  
                  <td width="90%"> <input type="text" name="stato" value="<%=statoDescrizioneSegnalazione%>" readonly="YES" size="50"> 
                  
                  
                  
                    <% 
              /*
                    if ( (profile==2 && pvo.getStato()==1 ) 					      || 
				            (pvo.getStato()>1 && pvo.getStato()<=5 && ownerShip)      ||
                            (profile==1 && pvo.getStato()==4)					      ||
                            (profile==4 && (pvo.getStato()==6 || pvo.getStato()==7) ) || 
                            (profile==4 && pvo.getStato()==2 && Integer.parseInt((String)session.getAttribute("oldstato")) == 7) 
                           )  { 
    // se il problema è in open lo stato lo può cambiare qualsiasi esperto
    // se il problema è in un altro stato lo stato lo può cambiare solo l'esperto associato al problema
    // se il problema è in stato "Closed" lo stato lo può cambiare ancge l'utente 
    */
    				if(request.getAttribute("updStatus")!=null){
    %> <A  HREF="#" onClick="window.open('CambioStato.do?idSegnalazione=<%=pvo.getIdSegnalazione()%>&stato=<%=pvo.getStato()%><%= (request.getAttribute("changeStateNote")!=null)?request.getAttribute("changeStateNote"):" " %>','CambioStato','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><font size="1" face="Verdana, Arial">Modifica 
                    stato</font></A> <%}%> </td>
                </tr>
                <tr><td class="bgheader"><div class="tableheader">Priorità</div>
                	</td>
                	<td>
	                	<table width="326" height="26" cellpadding="1" cellspacing="1" class='text' style="border: 1 solid #7B9DBE;">
						    <tr><td bgcolor="505050"><font color='<%= request.getAttribute("colore") %>'>
						    	&nbsp;&#9608; <%=pvo.getPrioritaDescrizione()%>
								<input type="hidden" name="priorita" value="<%=pvo.getPrioritaDescrizione()%>" >
						    </font></td></tr>
						</table>
                	</td>
                </tr>
                <% if (profile!=1) {
    // se il profilo è "utente" non è necessario mostrare l'originatore
    %>
                <tr> 
                  <td width="10%" class="bgheader"><div class="tableheader">Originatore</div></td>
                  <td width="90%"> <%if ((pvo.getOriginatore()).equals("- -")){%> <%=pvo.getOriginatoreNome()%> <%} else { %> <A HREF="#" onClick="window.open('UserDetail.do?login=<%=pvo.getOriginatore()%>','buttonwin','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><%=pvo.getOriginatoreNome()%></A> <% } %>
					<input type="hidden" name="originatore" value="<%=pvo.getOriginatoreNome()%>" >
				  </td>
                </tr>
                <%}%>
                
                <%// if (profile!=2) {
    // se il profilo è "esperto" non è necessario mostrare l'esperto
    %>
                <tr> 
                  <td width="10%" class="bgheader"><div class="tableheader">Esperto</div></td>
                  <td width="90%"> <%if ((pvo.getEsperto()==null)||("".equals(pvo.getEsperto()))){%> <%="- -"%> <%} else { %> <A HREF="#" onClick="window.open('UserDetail.do?login=<%=pvo.getEsperto()%>','buttonwin','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><%=pvo.getEspertoNome()%></A> <% } %>
				  <input type="hidden" name="esperto" value="<%=pvo.getEspertoNome()%>" >
				  </td>
                </tr>
                <%//}%>
                
                <tr> 
                  <td width="10%" class="bgheader"><div class="tableheader">Validatore</div></td>
                  <td width="90%"> <%if ((pvo.getValidatore()==null)||("".equals(pvo.getValidatore()))){%> <%="- -"%> <%} else { %> <A HREF="#" onClick="window.open('UserDetail.do?login=<%=pvo.getValidatore()%>','buttonwin','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><%=pvo.getValidatoreNome()%></A> <% } %>
				  <input type="hidden" name="validatore" value="<%=pvo.getValidatoreNome()%>" >
				  </td>
                </tr>
                
                <tr> 
                  <td width="10%" class="bgheader" valign="top"><div align="left">
                      <p class="tableheader">Descrizione</p>
                    </div></td>
                  <td width="90%"><textarea rows="10" name="S1" readonly="YES" cols="100"><%=request.getAttribute("textarea")%>
								  </textarea></td>
                </tr>
              </table>                     
              <div align="right">
              <A  HREF="#" onClick="window.open('FileAppend.do?idSegnalazione=<%=pvo.getIdSegnalazione()%>','AggiungiAllegati','height=600,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><FONT  SIZE=1 FACE="Verdana, Arial">Aggiungi 
                Allegati</FONT></A> &nbsp; &nbsp;
              <A  HREF="#" onClick="window.open('EventList.do?idSegnalazione=<%=pvo.getIdSegnalazione()%>','EventList','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><FONT  SIZE=1 FACE="Verdana, Arial">Visualizza 
                Eventi Associati</FONT></A> &nbsp; &nbsp;
                <% if (ownerShip || pvo.getOriginatore().equals(utente.getLogin())) { %>
                <A  HREF="#" onClick="window.open('GetAggiuntaNota.do?idSegnalazione=<%=pvo.getIdSegnalazione()%>','AggiuntaNota','height=430,width=800,scrollbars=yes,toolbar=no,location=no,screenX=100,screenY=20,top=20,left=100','fullscreen=no')"><FONT  SIZE=1 FACE="Verdana, Arial">Aggiungi 
                Nota</FONT></A>&nbsp; &nbsp;
                <%}%>
 				<!--A  HREF="#" onClick="window.open('SegnalazioneDetail-p.do','SegnalazioneDetail','height=600,width=800,scrollbars=yes,toolbar=no,location=no,resizable=yes')"-->
 				<a href='#' onclick='document.Segnalazione.action="SegnalazioneDetail-p.do"; document.Segnalazione.submit();'><FONT  SIZE=1 FACE="Verdana, Arial">Versione stampabile 
                </FONT></A>
			</div>
            </form>           
<span id="toolTipBox" width="200"></span>
<%
if (files!= null){
String tab = "</script>\n<table class='text'>\n<tr><td colspan='3'>Allegati:\n</td></tr>\n";
int k = 0;
	Iterator j=files.iterator();
		while (j.hasNext()) {
				String[] afile = (String[]) j.next();
		    	tab = tab + "<tr><td><a href='"+request.getContextPath()+"/app/download/"+afile[0].replaceAll("'","&#39;")+"?id="+afile[1]+"'  target=_new ><img src='"+skName+"/icons/"+afile[2]+"' border=0> "+afile[0]+"</a></td>\n";
		    	if(afile[3]!=null){
		    		tab = "var all" + ++k + " = \"" + afile[3].replaceAll("\"","\\\\\"")+ "\";\n" + tab + "<td onmouseover='toolTip(all" + k + ",this);' STYLE='cursor: hand'><img src='"+skName+"/info.gif'></td><td>&nbsp;";
		    		if(profile==2 || profile==3)
		    			tab = tab + "<img src='"+skName+"/deletebutt.gif' alt='Elimina allegato' onclick=\"window.open('DeleteAttachment.do?id="+afile[1]+"','Delete','height=350,width=650,scrollbars=yes,toolbar=no,location=no,status=no,resizable=yes,menubar=no,screenX=100,screenY=20,top=20,left=100')\">";
		    		tab = tab + "</td></tr>\n";
		    	}
		    	else
		    		tab = tab + "<td>&nbsp;</td></tr>\n";
		}
		out.println("<script language='javascript'>\n"+tab+"</table>");
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

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
<title>Online interactive helpdesk Setup</title>
<!-- #EndEditable -->
<!-- #BeginEditable "javascripts" -->
<% 		
	String alfTicket = (String)session.getAttribute("alfTicket");
	String notifyStatus = System.getProperty("it.cnr.oil.externalNotifySystem.status");
	String url = "";
	String script = "";
	if(notifyStatus!=null && !notifyStatus.equalsIgnoreCase("disabled") && alfTicket!=null){
		url = System.getProperty("it.cnr.oil.externalNotifySystem.url")+ "/" + proj + alfTicket;
		script = "recuperaNotifiche("+System.getProperty("it.cnr.oil.externalNotifySystem.silentMode")+");";
	}
	script = script + "\" style=\"display:none;";
%>
<script type="text/javascript" src="<%= url %>"></script>
<script type="text/javascript">
	function doSubmit(nome, valore){
		document.forms[0].nome.value=nome; 
		document.forms[0].valore.value=valore; 
		document.forms[0].submit();
	}

	function rimuovi(){
		var list = document.forms[0].props;
		if(!(list.selectedIndex==-1)){
			var elm = list.options[list.selectedIndex].value;
			if(confirm('Eliminare il seguente elemento: '+elm+' ?'))
				doSubmit("del_prop", elm);
		}
	}

	function modifica(){
		var list = document.forms[0].props;
		if(!(list.selectedIndex==-1)){
			var elm = list.options[list.selectedIndex].value;
			var val = document.forms[0].prop.value;
			if(confirm('Modificare l\'elemento: '+elm+' con il seguente valore: '+ val +' ?'))
				doSubmit("add_prop", elm+'='+val);
		}
	}

	function aggiungi(){
		var nuovo = document.forms[0].prop.value;
		if(!(nuovo.indexOf('=')==-1)){
			if(confirm('Inserire il seguente elemento: '+ nuovo +' ?'))
				doSubmit("add_prop", nuovo);
		}else
			alert('Attenzione, il formato deve essere del tipo: chiave=valore');
	}

	document.onreadystatechange = function () {
		if (document.readyState === "complete") {
			document.body.style.display='block';
		}
	}
</script>
<!-- #EndEditable -->
</head>

<body onload="<%= script %>">
<!-- #BeginEditable "Imports" -->
<%@page import="java.util.Iterator,java.util.HashMap, it.sauronsoftware.cron4j.Predictor"%>
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
            Setup di sistema<!-- #EndEditable -->
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
                    <td width="60%"><span class="navigation"><bean:message key="navigation.starter" /> <!-- #BeginEditable "Navigation" --><a href="Home.do" class="navigation">Home</a> 
            --&gt; Configurazione di sistema<!-- #EndEditable --></span> 
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
		  <table width="800" align="center"  cellpadding="5" cellspacing="0">
		  	<tr>
				<td align='center'>
					<form action="Setup.do" method="POST">
						<table width="100%" border="1">
                          <tr>
                            <td width="386" class="text">Scheduler del Mail parser:<br>
                              <br>
<% 	
                              	String scheduler = (String)request.getAttribute("scheduler");
                              	if(scheduler!=null && scheduler.startsWith("started")){
                            		Predictor p = new Predictor(System.getProperty("mail.parser.schedule.config"));
%>
									<input type='button' value="Interrompi" onclick='doSubmit("scheduler","0");' />
									(Prossimo evento: <%= p.nextMatchingDate().toString() %> )
<%
                              	} else {
%>
									<input type='button' value="Avvia" onclick='doSubmit("scheduler","1");' />
<%								}
%>
									<input type='hidden' name='nome' value='' />
									<input type='hidden' name='valore' value='' />
									<br/><input type='button' value="Esegui ora" onclick='doSubmit("schedule_now","1");' />
                              </td>
                            <td width="386" class="text">Ambiente:<br/><br/><br/>
                            	<input type="button" value="Mostra" onclick="document.getElementById('envt').style.display='block';">
                            </td>
                          </tr>
                          <tr>
                            <td>Configurazione State-machine:<br/>
                              <br/>
                            <input type="button" value="Aggiorna"  onclick='doSubmit("state_machine_conf","1");' /></td>
                            <td>Gestione notifiche:<br/>
                            <br/>
                            <input type="button" value="Inserisci" onclick="inserisciNotifica();" />&nbsp;
                            <input type="button" value="Modifica / elimina" onclick="modificaRimuoviNotifica();" />&nbsp;
                            <input type="button" value="Visualizza" onclick="mostraNotifiche(true);" />
                            </td>
                          </tr>
                          <tr>
                            <td>Registro di ACL:<br>
                              <br>
                            <input type="button" value="Aggiorna"  onclick='doSubmit("acl","1");' /></td>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <td>Abilita/disabilita tutti gli utenti su LDAP:<br>
                            <input type="text" name="ldap" />
                              <br>
                            <input type="button" value="Esegui"  onclick='doSubmit("enable_user_ldap",document.forms[0].ldap.value);' /></td>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <td>Calcolo Xor check:
                            <input type="text" name="sid" />
                             <input type="button" value="Aggiorna"  onclick='doSubmit("xorcheck", document.forms[0].sid.value);' /></td>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <td colspan='2'><select name="props" size="10" class="board">
<%
                            		HashMap<String, String> map = (HashMap<String,String>)request.getAttribute("map");
                            		if(map !=null) for(Iterator<String> i = map.keySet().iterator(); i.hasNext();){
                            			String k = i.next();
                            			String v = map.get(k);
%>
								<option value="<%= k %>"><%= k %>  :  <%= v %></option>
<%
                            		}
%>
                            	</select>
                            	<img src="<%=skName%>/edit.gif"  onClick='modifica();'>&nbsp;
                            	<img src="<%=skName%>/deletebutt.gif" onClick='rimuovi();'>
                            	<br />
                            	<input type="text" name="prop" />&nbsp;
                            	<img src="<%=skName%>/addbutt.gif" onClick='aggiungi();'>
                            </td>
                          </tr>
                          <tr>
                            <td colspan="2">Risultato: <b><%= request.getAttribute("risultato")%></b></td>
                          </tr>
                          <tr><td colspan="2">
                          	<div style="display: none;" id="envt">
                                request url: <strong><%= request.getRequestURL() %></strong><br>
				                request uri: <strong><%= request.getRequestURI() %></strong><br>
				                context path: <strong><%= request.getContextPath() %></strong><br>
				                LocalAddr: <strong><%= request.getLocalAddr() %></strong><br>
				                PathInfo: <strong><%= request.getPathInfo() %></strong><br>
				                QueryString: <strong><%= request.getQueryString() %></strong><br>
				                RealPath: <strong><%= request.getRealPath("") %></strong><br>
				                RemoteAddr: <strong><%= request.getRemoteAddr() %></strong><br>
				                RemoteHost: <strong><%= request.getRemoteHost() %></strong><br>
				                ServletPath: <strong><%= request.getServletPath() %></strong><br>
				                ServerName: <strong><%= request.getServerName() %></strong><br>
				                LocalName: <strong><%= request.getLocalName() %></strong><br>
                          	</div>
				           </td></tr>
                        </table>
				  </form>
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

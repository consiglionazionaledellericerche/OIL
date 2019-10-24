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
<html>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<head>
<% String proj = session.getAttribute("it.cnr.helpdesk.instance").toString(); %>

<title><bean:message key="applicationName" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<frameset rows="110,*,80" frameborder="NO" border="0" framespacing="0">
   
  <frame src="RepositoryUpdate3.do" name="topFrame" scrolling="NO" noresize >
  
  <frameset cols="50%,*" frameborder="YES" border="2" framespacing="2">
    <frame name="sideFrame" src="RepositoryUpdate1.do">
    <frame name="mainFrame"  src="RepositoryUpdate2.do?id=00000">
  </frameset>
  <frame src="RepositoryUpdate4.do" name="bottomFrame" scrolling="NO" noresize>
</frameset>
<noframes> 
<body bgcolor="#FFFFFF" text="#000000">
</body>
</noframes> 
</html>


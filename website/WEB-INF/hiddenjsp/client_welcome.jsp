<!-- client_welcome -->

<%@page pageEncoding="UTF-8"%>
<%@page import="org.nlavee.skidmore.webapps.web.VarNames"%>
<%
	String IP_ADDR_PARAM = VarNames.MIRROR_IP;
%>
<html>
<head>
<meta charset="utf-8" />
 <meta http-equiv="refresh" content="5">
<title>Welcome to MagMirr</title>
</head>

<body>
	<h1><%=request.getSession().getAttribute(IP_ADDR_PARAM)%></h1>
	
</body>
</html>


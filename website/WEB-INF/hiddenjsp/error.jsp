<!-- ERROR -->
<%@page pageEncoding="UTF-8" %>
<%@page import="org.nlavee.skidmore.webapps.web.VarNames"%>
<%
	String authenticated = VarNames.AUTHENTICATED_ATTRIBUTES_NAME;
	Object state = request.getSession().getAttribute(authenticated);
	String status = VarNames.STATUS_MODE;
	String contact = VarNames.CONTACT_MODE;
	String about = VarNames.ABOUT_MODE;
	String main = VarNames.MAIN_MODE;
	String logout = VarNames.LOGOUT_MODE;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="staticFiles/css/base.css">
<title>MagMirr Error Page</title>
</head>

<body>
	<!-- Navigation Bar -->
	<div class="navbar">
		<ul class="navbarContent">
			<li><a
				href="<%=(request.getSession().getAttribute(authenticated) == null ? "index.html"
					: "main")%>">Home</a></li>
			<li><a href="<%=status%>">Status</a></li>
			<li><a href="<%=contact%>">Contact</a></li>
			<ul style="float: right; list-style-type: none;">
				<li><a class="active" href="<%=about%>">About</a></li>
				<li><a href=<%=state == null ? main : logout%>><%=state == null ? "Login" : "Logout"%></a></li>
			</ul>
		</ul>
	</div>
	<br />
	<br />

	<!-- Website Content -->
	<h1>Something wrong happened!</h1>

	<div class="info">
		<p>
			<i>&lt;working project of @nlavee (vu.nguyen@skidmore.edu).&gt;</i>
		</p>
	</div>

</body>
</html>
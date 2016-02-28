<!-- main -->

<%@page import="org.nlavee.skidmore.webapps.web.VarNames"%>
<%
	String userName = VarNames.USER_PARAM_FIELD_NAME;
	String password = VarNames.PASSWORD_PARAM_FIELD_NAME;
	String remember = VarNames.REMEMBER_PARAM_FIELD_NAME;
	String authenticated = VarNames.AUTHENTICATED_ATTRIBUTES_NAME;
	Object state = request.getSession().getAttribute(authenticated);
	String status = VarNames.STATUS_MODE;
	String contact = VarNames.CONTACT_MODE;
	String about = VarNames.ABOUT_MODE;
	String main = VarNames.MAIN_MODE;
	String logout = VarNames.LOGOUT_MODE;
	String textBox = VarNames.MAIN_TEXT_BOX;
%>

<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="staticFiles/css/base.css">
<title>MagMirr Dashboard</title>
</head>

<body>
	<!-- Navigation Bar -->
	<div class="navbar">
		<ul class="navbarContent">
			<li><a class="active" 
				href="<%=(request.getSession().getAttribute(authenticated) == null ? "index.html"
					: "main")%>">Home</a></li>
			<li><a href="<%=status%>">Status</a></li>
			<li><a href="<%=contact%>">Contact</a></li>
			<ul style="float: right; list-style-type: none;">
				<li><a href="<%=about%>">About</a></li>
				<li><a href=<%=state == null ? main : logout%>><%=state == null ? "Login" : "Logout"%></a></li>
			</ul>
		</ul>
	</div>


	<br />
	<br />

	<!-- Website Content -->
	<div class="welcome">
		<h1 id="welcome" class="instruction">MagMirr 0.0 Main Dashboard.</h1>
		<h3 class="instruction">
			Hello,
			<%=request.getSession().getAttribute(userName)%></h3>
		<hr />
	</div>

	<form action="/message">
		<fieldset>
			<legend>Message broadcast</legend>
			<textarea name=<%=textBox%> cols="50" rows="5">
				Enter some text...
			</textarea>
			<br /> <input type="submit" value="Submit">
		</fieldset>
	</form>

	<div class="info">
		<p>
			<i>&lt;working project of @nlavee (vu.nguyen@skidmore.edu).&gt;</i>
		</p>
	</div>


</body>
</html>

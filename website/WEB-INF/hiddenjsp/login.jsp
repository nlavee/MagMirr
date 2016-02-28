<!-- login -->

<%@page import="org.nlavee.skidmore.webapps.web.auth.Login"%>
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
%>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="staticFiles/css/base.css">
<title>MagMirr Login</title>
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
				<li><a href="<%=about%>">About</a></li>
				<li><a class="active" href=<%=state == null ? main : logout%>><%=state == null ? "Login" : "Logout"%></a></li>
			</ul>
		</ul>
	</div>

	<br />
	<br />

	<!-- Website Content -->
	<div class="welcome">
		<h1 id="welcome" class="instruction">Welcome To The
			Authentication Page For MagMirr 0.0.</h1>
		<hr />
	</div>

	<div class="login" id="login">
		<h2 id="info" class="instruction">Please log into your account</h2>
		<br />
		<form method="post" id="auth" class="auth" action="main">
			Username* : <input type="text" id="username" maxlength="100"
				class="input" name="<%=userName%>" /> <br /> Password* : <input
				type=password id="pwd" maxlength="100" class="input"
				name="<%=password%>" /> <br /> Remember me? <input type="checkbox"
				id="remember" class="input" name="<%=remember%>" /> <input
				type="submit" id="submit" class="input" value="submit" />
		</form>
		<p><i>* means the field is required to be completed.</i></p>
		<p>
			Don't have an account? You can created one <a href="index.html">here</a>
		</p>
	</div>


	<div class="info">
		<p>
			<i>&lt;working project of @nlavee (vu.nguyen@skidmore.edu).&gt;</i>
		</p>
	</div>


</body>
</html>
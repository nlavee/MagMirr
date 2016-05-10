<!-- login -->
<%@page pageEncoding="UTF-8" %>
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
	String loginProblem = VarNames.LOGIN_UNSUCCESSFUL;
%>
<html>
<head>
<meta charset="utf-8" />
<link href='https://fonts.googleapis.com/css?family=Convergence' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="staticFiles/css/base.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>
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
			Authentication Page For MagMirr 0.3.</h1>
		<hr />
	</div>

	<div class="login" id="login">
		<h2 id="info" class="instruction">Please log into your account</h2>
		<%
			if (request.getAttribute(loginProblem) != null) {
		%>
		<p>There was a problem with your username and/or password. Please
			try again.</p>
		<%
			}
			request.setAttribute(loginProblem, true);
		%>
		<br />
		<form method="post" id="auth" class="auth" action="main">
        		<div class="mdl-textfield mdl-js-textfield">
					<input type="text" id="username" maxlength="100" class="mdl-textfield__input" name="user_name" />
					<label class="mdl-textfield__label" for="username">Username*...</label>
					</div><br />
					<div class="mdl-textfield mdl-js-textfield"> 
					<input type=password id="pwd" maxlength="100" class="mdl-textfield__input" name="password" /> 
					<label class="mdl-textfield__label" for="password">Password*...</label>
					</div><br/>
					<input type="submit" id="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" value="submit" />
			</form>
		<p>
			<i>* means the field is required to be completed.</i>
		</p>
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
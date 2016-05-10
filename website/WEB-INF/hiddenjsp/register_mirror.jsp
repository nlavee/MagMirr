<!-- Register Page -->
<%@page pageEncoding="UTF-8" %>
<%@page import="org.nlavee.skidmore.webapps.web.VarNames"%>

<%
	String remoteAdd = (String) request.getSession().getAttribute(VarNames.MIRROR_IP);
	String authenticated = VarNames.AUTHENTICATED_ATTRIBUTES_NAME;
	Object state = request.getSession().getAttribute(authenticated);
	String status = VarNames.STATUS_MODE;
	String contact = VarNames.CONTACT_MODE;
	String about = VarNames.ABOUT_MODE;
	String main = VarNames.MAIN_MODE;
	String logout = VarNames.LOGOUT_MODE;
	boolean addedSuccessful = request.getSession().getAttribute(VarNames.MIRROR_ADDED_ATTRIBUTE).toString().equals("true");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link href='https://fonts.googleapis.com/css?family=Convergence' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="staticFiles/css/base.css">
<title>MagMirr Register Mirror</title>
</head>

<body>

	<!-- Navigation Bar -->
	<div class="navbar">
		<ul class="navbarContent">
			<li><a
				href="<%=(request.getSession().getAttribute(authenticated) == null
					? "index.html"
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
	<br />

	<!-- Website Content -->
	<div class="welcome">
		<h1 id="welcome" class="instruction">MagMirr 0.3. Registering Mirror.</h1>
	</div>

	<div id="message_posting" class="mode_ops">
		<form action="register" method="post">
			<fieldset>
				<legend>Register Mirror IP Address</legend>
				IP Address: <input type="text" name="<%=VarNames.MIRROR_IP%>"/>
				<br /> <input type="submit" value="Submit">
				
				<%
					if(addedSuccessful)
					{
						%>
							<p>Your mirror has been added successfully. Please head back to the main page.
						<%
						
					}
				%>
			</fieldset>
		</form>
	</div>
	
</body>
</html>
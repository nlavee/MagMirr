<!-- main -->

<%@page import="org.nlavee.skidmore.webapps.web.VarNames"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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
	String zipcode = VarNames.MAIN_ZIPCODE;
	String currentZipCode = VarNames.ZIPCODE_WEATHER;
	String currentTemp = VarNames.TEMP_WEATHER;
	String firstName = VarNames.FIRST_NAME_PARAM_FIELD_NAME;
	Date date = new Date();
	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd, MMMMM, yyyy hh:mm aaa");
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
		<h1 id="welcome" class="instruction">MagMirr 0.1 Main Dashboard.</h1>
		<h3 class="instruction">
			Hello
			<%=(request.getSession().getAttribute(firstName) == null ? "there." : ("<u>" + request.getSession().getAttribute(firstName)+ ". </u>"))%></h3>
			<h4><%=dateFormatter.format(date)%></h4>
		<hr />
	</div>

	<div id="message_posting" class="mode_ops">
		<form action="message">
			<fieldset>
				<legend>Message broadcast</legend>
				<textarea name=<%=textBox%> cols="50" rows="5">Enter your message here...</textarea>
				<br /> <input type="submit" value="Submit">
			</fieldset>
		</form>
	</div>

	<div id="weather_choosing" class="mode_ops">
		<form action="weather" method="post" id="auth" class="auth">
			<fieldset>
				<legend>Weather Display Tool</legend>
				Your Zipcode: <input type="number" name=<%=zipcode%> /> <input
					type="submit" id="submit" class="input" value="Display Weather" />
					<br/>
					<%
						if(request.getSession().getAttribute(currentZipCode) != null)
						{
							%>
							<p>Current weather is displayed for zipcode: <%=request.getSession().getAttribute(currentZipCode)%>. Current Temperature is <%=request.getSession().getAttribute(currentTemp) %>&#x2103;.</p>
							<%
						}
						else
						{
							// load the zip code from previous section ?
						}
					%>
			</fieldset>
		</form>
	</div>

	<div id="news_choosing" class="mode_ops">
		<form action="news" method="post" id="auth" class="auth">
			<fieldset>
				<legend>News Selection Tool</legend>
				Your Selection of Topics: <br /> <select multiple>
					<option value="technology">Technology</option>
					<option value="sports">Sports</option>
					<option value="pol">Politics</option>
					<option value="econs">Economics</option>
				</select><br /> <input type="submit" id="submit" class="input"
					value="Select Interested Topics" />
			</fieldset>
		</form>
	</div>

	<div id="uber_authentication" class="mode_ops">
		<form action="uber_ops" method="post" id="auth" class="auth">
			<fieldset>
				<legend>Uber Authentication Tool</legend>
				<input type="submit" id="submit" class="input"
					value="Authenticate with Uber" />
			</fieldset>
		</form>
	</div>

	<div class="info">
		<p>
			<i>&lt;working project of @nlavee (vu.nguyen@skidmore.edu).&gt;</i>
		</p>
	</div>


</body>
</html>

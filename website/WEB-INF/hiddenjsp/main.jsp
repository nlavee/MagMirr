<!-- main -->

<%@page pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.ParseException"%>
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
	SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"dd, MMMMM, yyyy hh:mm aaa");
	final String[] sectionValues = VarNames.NEWS_SECTIONS;
	int i = 0;
	try {
		i = Integer.parseInt((String) request.getAttribute("i"));
	} catch (NumberFormatException e) {
		//nothing
	}
	String newsSelection = VarNames.MAIN_NEWS_SELECTION;
	String lyftAuthenticated = VarNames.LYFT_AUTHENTICATED;
	String lyftAuthenticatedExpired = VarNames.LYFT_AUTHENTICATED_TIME_OUT;
	String messageForwarded = VarNames.MESSAGE_FORWARDED_STATUS;
	String weatherForwarded = VarNames.WEATHER_FORWARDED_STATUS;
	String newsForwarded = VarNames.NEWS_FORWARDED_STATUS;
	String lyftRideTypeForwarded = VarNames.LYFT_RIDETYPE_FORWARDED_STATUS;
	String lyftETAForwarded = VarNames.LYFT_ETA_FORWARDED_STATUS;
	String lyftCostForwarded = VarNames.LYFT_COST_FORWARDED_STATUS;
%>

<html>
<head>
<meta charset="utf-8" />
<link href='https://fonts.googleapis.com/css?family=Convergence' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="staticFiles/css/base.css"/>
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
			<%=(request.getSession().getAttribute(firstName) == null ? "there."
					: ("<u>" + request.getSession().getAttribute(firstName) + ". </u>"))%></h3>
		<h4><%=dateFormatter.format(date)%></h4>
		<hr />
	</div>

	<div id="message_posting" class="mode_ops">
		<form action="message" method="post">
			<fieldset>
				<legend>Message broadcast</legend>
				<textarea name=<%=textBox%> cols="50" rows="5">Enter your message here...</textarea>
				<br /> <input type="submit" value="Submit">
				<%-- <%
					if(request.getSession().getAttribute(messageForwarded) != null)
					{
						if(request.getSession().getAttribute(messageForwarded).equals(true))
						{
							%>
							<p>Your message has been posted successfully.</p>
							<%
						}
						else
						{
							%>
							<p>ERROR: Your message has failed to be posted. Please try again.</p>
							<%
						}
						request.getSession().setAttribute(messageForwarded, null);
					}
				%> --%>
			</fieldset>
		</form>
	</div>

	<div id="weather_choosing" class="mode_ops">
		<form action="weather" method="post" id="auth" class="auth">
			<fieldset>
				<legend>Weather Display Tool</legend>
				Your Zipcode: <input type="number" name=<%=zipcode%> /> <input
					type="submit" id="submit" class="input" value="Display Weather" />
				<br />
				<%
					if (request.getAttribute(currentZipCode) != null) {
				%>
				<p>
					Current weather is displayed for zipcode:
					<%=request.getAttribute(currentZipCode)%>. Current Temperature is
					<%=request.getAttribute(currentTemp)%>&#x2103;.
				</p>
				<%
					} else {
						// load the zip code from previous section ?
					}
				
					//if(request.getSession().getAttribute(weatherForwarded) != null)
					//{
					//	if(request.getSession().getAttribute(weatherForwarded).equals(true))
					//	{
					//		%>
					<!-- 		<p>Your weather has been posted successfully.</p>
					 -->		<%
					//	}
					//	else
					//	{
					//		%>
					<!-- 		<p>ERROR: Your weather has failed to be posted. Please try again.</p>
					 -->		<%
					//	}
					//	request.getSession().setAttribute(weatherForwarded, null);
					//}
				%>
			</fieldset>
		</form>
	</div>

	<div id="news_choosing" class="mode_ops">
		<fieldset>
		<legend>News Selection Tool</legend>
		<form action="news" method="post" id="auth" class="auth">
				Your Selection of Topics: <br /> <select name=<%=newsSelection%>
					multiple>

					<%
						for (String attr : sectionValues) {
					%>
					<option value="<%=attr.replaceAll("\\s+", "")%>"><%=StringUtils.capitalize(attr)%></option>
					<%
						}
					%>
				</select><br /> <input type="submit" id="submit" class="input"
					value="Select Interested Topics" />
		</form>
		<div id="news_choosing" class="mode_ops">
			<%
				for (int count = 0; count < i; count++) {
					String varNames = "top5-" + count;
					String title = (String) request.getAttribute(varNames);
			%>
			<p><%=title%></p>
			<%
				}
			%>
		</div>
		<div>
			<%-- <%
			if(request.getSession().getAttribute(newsForwarded) != null)
			{
				if(request.getSession().getAttribute(newsForwarded).equals(true))
				{
					%>
					<p>Your news has been posted successfully.</p>
					<%
				}
				else
				{
					%>
					<p>ERROR: Your news has failed to be posted. Please try again.</p>
					<%
					}
					request.getSession().setAttribute(newsForwarded, null);
			}
			%>	 --%>
			</div>	
		</fieldset>
	</div>

	<div id="lyft_authentication" class="mode_ops">
		<%
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("mm/dd-HH:mm:ss");
			String currentTime = sdf.format(cal.getTime());
			boolean pastTime = false;
			
			if(request.getSession()
					.getAttribute(lyftAuthenticatedExpired) != null)
			{
				try {
					pastTime = sdf.parse(currentTime).after(
							sdf.parse((String) request.getSession()
									.getAttribute(lyftAuthenticatedExpired)));
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			if (request.getSession().getAttribute(lyftAuthenticated) == null
					|| pastTime) {
		%>
		<form action="lyftOps" method="post" id="auth" class="auth">
			<fieldset>
				<legend>Lyft Authentication Tool</legend>
				<input type="submit" id="submit" class="input"
					value="Authenticate with Lyft" />
			</fieldset>
		</form>
		<%
			} 
			else 
			{
		%>
		<form action="lyftRideType" method="post" id="auth" class="auth">
			<fieldset>
				<legend>Lyft Get Ride Type Tool</legend>
				<input type="hidden" name="mode" value="rideType"/>
				<input type="submit" id="submit" class="input" value="Get Ride Type" name="getRide"/>
				
				<%-- <%
					if(request.getSession().getAttribute(lyftRideTypeForwarded) != null)
					{
						if(request.getSession().getAttribute(lyftRideTypeForwarded).equals(true))
						{
							%>
							<p>Your Lyft ride type has been posted successfully.</p>
							<%
						}
						else
						{
							%>
							<p>ERROR: Your Lyft ride type has failed to be posted. Please try again.</p>
							<%
						}
						request.getSession().setAttribute(lyftRideTypeForwarded, null);
					}
				%>		 --%>		
				
			</fieldset>
		</form>
		<form action="lyftETA" method="post" id="auth" class="auth">
			<fieldset>
				<legend>Lyft ETA Tool</legend>
				<input type="hidden" name="mode" value="ETA"/>
				<input type="submit" id="submit" class="input" value="Get ETA" name="ETA"/>
				
				<%-- <%
					if(request.getSession().getAttribute(lyftETAForwarded) != null)
					{
						if(request.getSession().getAttribute(lyftETAForwarded).equals(true))
						{
							%>
							<p>Your Lyft ETA has been posted successfully.</p>
							<%
						}
						else
						{
							%>
							<p>ERROR: Your Lyft ETA has failed to be posted. Please try again.</p>
							<%
						}
						request.getSession().setAttribute(lyftETAForwarded, null);
					}
				%> --%>
				
			</fieldset>
		</form>
		<form action="lyftCost" method="post" id="auth" class="auth">
			<fieldset>
				<legend>Lyft Cost Tool</legend>
				<input type="hidden" name="mode" value="cost"/>
				<input type="submit" id="submit" class="input" value="Get Cost" name="Cost"/>
				
				<%-- <%
					if(request.getSession().getAttribute(lyftCostForwarded) != null)
					{
						if(request.getSession().getAttribute(lyftCostForwarded).equals(true))
						{
							%>
							<p>Your Lyft cost has been posted successfully.</p>
							<%
						}
						else
						{
							%>
							<p>ERROR: Your Lyft cost has failed to be posted. Please try again.</p>
							<%
						}
						request.getSession().setAttribute(lyftCostForwarded, null);
					}
				%> --%>
				
			</fieldset>
		</form>
		<%
			}
		%>
	</div>
		<form method="post" action="register">
			<input type="submit" value="Register New Mirror"/>
		</form>
	<div class="info">
		<p>
			<i>&lt;working project of @nlavee (vu.nguyen@skidmore.edu).&gt;</i>
		</p>
	</div>


</body>
</html>

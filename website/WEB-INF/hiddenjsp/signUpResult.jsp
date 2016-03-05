<!-- login -->

<%@page import="org.nlavee.skidmore.webapps.web.auth.Login"%>
<%@page import="org.nlavee.skidmore.webapps.web.VarNames"%>
<%
	String userName = VarNames.USER_PARAM_FIELD_NAME;
	String password = VarNames.PASSWORD_PARAM_FIELD_NAME;
	String email = VarNames.EMAIL_PARAM_FIELD_NAME;
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
        <meta charset="utf-8"/>
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
        
        <br/>
        <br/>
        
        <!-- Website Content -->
        <div class="welcome">
        <h1 id="welcome" class="instruction">Thank you for signing up with Mag Mirr 0.1.</h1>
            <hr/>
         
         	<p>You have signed up for the following username: <b><%=request.getSession().getAttribute(userName)%></b>.
         	<p>The associated email address is <b><%=request.getSession().getAttribute(email)%></b>.</p>
         	<p>Please remember your email. You will need it in order to change password.</p>
         	<p>Please click <b><a href="main">here</a></b> to proceed</p> 
        </div>
        
        
        
        <div class="info">
        <p><i>&lt;working project of @nlavee (vu.nguyen@skidmore.edu).&gt;</i></p>
        </div>
        
        
    </body>
</html>
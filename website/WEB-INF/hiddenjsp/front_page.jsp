<!-- login -->
<%@page pageEncoding="UTF-8" %>
<%@page import="org.nlavee.skidmore.webapps.web.VarNames"%>
<%

%>
<html>
    <head>
        <meta charset="utf-8"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.indigo-pink.min.css">
		<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>
        <link rel="stylesheet" type="text/css" href="staticFiles/css/base.css">
        <title>MagMirr Login</title>
    </head>
    
    <body>
        <!-- Navigation Bar -->
        <div class="navbar">
        <ul class="navbarContent">
            <li><a class="active" href="index.html">Home</a></li>
            <li><a href="status">Status</a></li>
            <li><a href="contact">Contact</a></li>
            <ul style="float:right;list-style-type:none;">
                <li><a href="about">About</a></li>
                <li><a href="main">Login</a></li>
            </ul>
        </ul>
        </div>
        
        <br/>
        <br/>
        
        <!-- Website Content -->
        <div class="welcome">
        <h1 id="welcome" class="instruction">Welcome To The MagMirr 0.3.</h1>
            <hr/>
        </div>
        
        
        <div class="authenticate" id="signup">
            <h2 id="info" class="instruction">Sign Up to Start Using MagMirr</h2>
            <br/>
            <form method="post" id="signup" class="signup" action="signup">
            	<div class="mdl-textfield mdl-js-textfield">
	                <input type="text" id="username" maxlength="100" class="mdl-textfield__input" name="user_name"/>
	                <label class="mdl-textfield__label" for="username">Username*...</label>
	                </div><br/>
	                <div class="mdl-textfield mdl-js-textfield">
	                <input type="text" id="firstName" maxlength="100" class="mdl-textfield__input" name="first_name"/>
	                <label class="mdl-textfield__label" for="firstName">First Name*...</label>
	               	</div><br/>
	               	<div class="mdl-textfield mdl-js-textfield">
	               	<input type="text" id="lastName" maxlength="100" class="mdl-textfield__input" name="last_name"/>
	               	<label class="mdl-textfield__label" for="lastName">Last Name*...</label>
	               	</div></br>
	               	<div class="mdl-textfield mdl-js-textfield">
	                <input type="email" id="email" maxlength="100" class="mdl-textfield__input" name="email"/>
	                <label class="mdl-textfield__label" for="email">Email*...</label>
	                </div><br/>
	                <div class="mdl-textfield mdl-js-textfield">
	                <input type=password id="pwd" maxlength="100" class="mdl-textfield__input" name="password"/>
	                <label class="mdl-textfield__label" for="pwd">Password*...</label>
	                </div><br/>
	                <input type="submit" id="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" value="submit"/>
                </div>
            </form>
            <p><i>* means the field is required to be completed.</i></p>
        </div>
        
        <div class="authenticate" id = "existing_user">
        	<h2 id="info" class="instruction">Already have an account? Sign in below</h2>
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
        </div>
        
        <div class="info">
        <p><i>&lt;working project of @nlavee (vu.nguyen@skidmore.edu).&gt;</i></p>
        </div>
        
        
    </body>
</html>
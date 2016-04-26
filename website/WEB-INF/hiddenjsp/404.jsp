<!-- Register Page -->
<%@page pageEncoding="UTF-8" %>
<%@page import="org.nlavee.skidmore.webapps.web.VarNames"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="staticFiles/css/base.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<link href='https://fonts.googleapis.com/css?family=VT323' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="staticFiles/css/main.css">
<title>MagMirr Client</title>
</head>

<body>
<h1>Ooopps! It seems like an error has occured. Please go to main page <a href="<%=request.getContextPath()%>">here.</a></h1>
<br>
<!-- MDL Progress Bar with Indeterminate Progress -->
<div id="progress_bar" class="mdl-progress mdl-js-progress mdl-progress__indeterminate"></div>
<p>WAITING FOR YOU TO GET BACK TO MAIN PAGE!</p>
</body>
</html>
<!-- MAP -->

<%
	/* 
	 Adopted Example from 
	 https://developers.google.com/maps/documentation/javascript/examples/map-geolocation
	 */
%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.nlavee.skidmore.webapps.web.VarNames"%>
<%@page import="org.nlavee.skidmore.webapps.web.APIKEYS"%>

<%
	String LyftMode = VarNames.LYFT_MODE;
	String GoogleAPI = APIKEYS.GOOGLE_MAP_API_CLIENT;
	String rideType = VarNames.LYFT_RIDE_TYPE_MODE;
	String ETA = VarNames.LYFT_ETA_MODE;
	String cost = VarNames.LYFT_COST_MODE;
%>

<html>
<head>
<meta charset="utf-8" />
<style>
#map {
	height: 80%;
}

#floating-panel {
	position: absolute;
	top: 20%;
	left: 25%;
	z-index: 5;
	background-color: #fff;
	padding: 5px;
	border: 1px solid #999;
	text-align: center;
	font-family: 'Roboto', 'sans-serif';
	line-height: 30px;
	padding-left: 10px;
}
#current {
     padding-top: 10px;
     color: white;
    text-shadow: -0.5px 0 black, 0 0.5px black, 0.5px 0 black, 0 -0.5px black;
}
</style>
<link rel="stylesheet" type="text/css" href="staticFiles/css/base.css" />
<title>MagMirr Lyft Operation</title>
</head>
<body>

	<div>
		<form action="main" method="post" id="auth" class="auth">
			<button type="submit" name="submit" value="Back To Main Page">Back
				To Main Page</button>
		</form>
	</div>

	<div class="welcome">
		<h1 id="welcome" class="instruction">MagMirr 0.1 Lyft Operations.</h1>
		<h3 class="instruction">Please choose the destination of interest
			from the map below.</h3>
		<hr />
	</div>

	<!-- Google Map -->
	<div id="floating-panel">
		<input id="address" type="textbox" value=""> <input
			id="submit" type="button" value="Geocode">
	</div>
	<div id="map"></div>
	<script src="staticFiles/js/<%=request.getAttribute(LyftMode)%>.js"></script>
	<script src="staticFiles/js/map.js"></script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=<%=GoogleAPI%>&callback=initMap">
		
	</script>
	
	<div id="current">Nothing yet...</div>
	

	<div class="info">
		<p>
			<i>&lt;working project of @nlavee (vu.nguyen@skidmore.edu).&gt;</i>
		</p>
	</div>
</body>
</html>
<!--  Contact  -->
<%@page import="org.nlavee.skidmore.webapps.web.VarNames"%>
<%
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
<title>Contact</title>
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
			<li><a class="active" href="<%=contact%>">Contact</a></li>
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

	<div class="socialMedia">
		<h1>You can find me on the following social media</h1>
		<a href="https://www.linkedin.com/pub/anh-vu-nguyen/41/4ba/b27"> <img
			src="https://static.licdn.com/scds/common/u/img/webpromo/btn_viewmy_120x33.png"
			width="120" height="33" border="0"
			alt="View Anh Vu Nguyen's profile on LinkedIn">

		</a> <a href="https://twitter.com/nlavee" class="twitter-follow-button"
			data-show-count="false" data-size="large">Follow @nlavee</a>
		<script>
			!function(d, s, id) {
				var js, fjs = d.getElementsByTagName(s)[0], p = /^http:/
						.test(d.location) ? 'http' : 'https';
				if (!d.getElementById(id)) {
					js = d.createElement(s);
					js.id = id;
					js.src = p + '://platform.twitter.com/widgets.js';
					fjs.parentNode.insertBefore(js, fjs);
				}
			}(document, 'script', 'twitter-wjs');
		</script>

		<iframe src="http://githubbadge.appspot.com/nlavee?a=0"
			style="border: 0; height: 142px; width: 200px; overflow: hidden;"
			frameBorder="0"></iframe>

		<script async src="https://static.medium.com/embed.js"></script>
		<a class="m-profile" href="https://medium.com/@nlavee">Anh Vu L
			Nguyen</a>


	</div>

	<div class="contactInfo">
		<h3>
			You can also reach me through my <a
				href="mailto:anhvu.nguyenlam@gmail.com" target="_top">email.</a>
		</h3>

	</div>

	<div class="info">
		<p>
			<i>&lt;working project of @nlavee (vu.nguyen@skidmore.edu).&gt;</i>
		</p>
	</div>

</body>
</html>
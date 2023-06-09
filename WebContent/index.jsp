<%@page import="page.Test, org.json.*"%>
<html>
	<head>
		<title>Welcome</title>
	</head>
	<body>
		<%
			Test test = new Test();
			test.test();
		%>
		<h1>Hello mom.</h1>
	</body>
</html>
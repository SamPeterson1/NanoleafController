<%@page import="page.PageClient,org.json.*"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Welcome</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	</head>
	<body>	
		<canvas id="hexagons" width="800" height="400"></canvas>
		<script src="hexagons.js"></script>
		<button onClick="red()">Red!</button>
		<button onClick="green()">Green!</button>
		<h1>beep beep.</h1>
	</body>
</html>
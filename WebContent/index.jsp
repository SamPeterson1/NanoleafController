<%@page import="page.PageClient,org.json.*"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Welcome</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	</head>
	<body>	
		<canvas id="hexagons" width="800" height="400"></canvas>
		<script>
			fetch("http://10.2.7.84:3333", {
				method: "PUT",
				body: JSON.stringify({foo: "bar"})
			}).then((response) => response.text()).then((text) => alert(text));
		</script>
		<h1>beep beep.</h1>
	</body>
</html>
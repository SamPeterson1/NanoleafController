<%@page import="page.PageClient,org.json.*"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Welcome</title>
	</head>
	<body>	
		<%
		PageClient client = new PageClient();
		client.test();
		JSONObject panelLayout = client.requestPanelLayout();
		JSONObject panelColors = client.requestPanelColors();
		%>
		
		<script type="text/javascript">
			var panelLayout = <%=panelLayout%>
			var panelColors = <%=panelColors%>
		</script>
		<canvas id="hexagons" width="800" height="400"></canvas>
		<script src="hexagons.js"></script>
		<h1>beep beep.</h1>
	</body>
</html>
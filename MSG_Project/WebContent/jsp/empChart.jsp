<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script
	src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous">
</script>
</head>
<link rel = "stylesheet" type = "text/css" href = "${contextPath}/css/main.css">
<link rel = "stylesheet" type = "text/css" href = "${contextPath}/css/empchart.css">
<script type = "text/javascript" src = "${contextPath}/js/main.js"></script>
<script type = "text/javascript" src = "${contextPath}/js/empChart.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<body>
	<jsp:include page = "/jsp/pageHeader.jsp"></jsp:include>
	<input type = "hidden" id = "path" value = "${contextPath}">
	<div id = "content">
		<jsp:include page="/jsp/pageSide.jsp">
			<jsp:param value="emp" name="category"/>
		</jsp:include>
		<div id = "menuBox2">
			<p style = "margin : 20px">
				<a href = "#" style = "font-size: 20px;">조직도</a>
			</p>
			<hr id = "hr3">
			<br><br>
			<hr id = "hr4">
		</div>
		<div id = "chart" align = "center">
			<ul>
				<li>
					<img src = "${contextPath}/img/dir.png"><a onclick = "showChart('m','mChart')">경영/기획부</a>
					<ul id = "mChart"></ul>
				</li>
				<li>
					<img src = "${contextPath}/img/dir.png"><a onclick = "showChart('b','bChart')">영업부</a>
					<ul id = "bChart"></ul>
				</li>
				<li>
					<img src = "${contextPath}/img/dir.png"><a onclick = "showChart('i','iChart')">IT부</a>
					<ul id = "iChart"></ul>
				</li>
				<li>
					<img src = "${contextPath}/img/dir.png"><a onclick = "showChart('f','fChart')">재무부</a>
					<ul id = "fChart"></ul>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>
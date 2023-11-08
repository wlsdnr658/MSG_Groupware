<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%
	request.setAttribute("contextPath", request.getContextPath());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/Calendar.css">


<link rel='stylesheet' href='${contextPath}/calendar/fullcalendar.css' />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/calendar/fullcalendar.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/calendar/fullcalendar.min.css">
	
	
<link rel="stylesheet" type="text/css" href="${contextPath}/calendar/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/calendar/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/calendar/theme.css">
	

<script src='${contextPath}/calendar/jquery.min.js'></script>
<script src='${contextPath}/calendar/moment.min.js'></script>
<script src='${contextPath}/calendar/fullcalendar.js'></script>
<script src='${contextPath}/calendar/ko.js'></script>
<!-- 한글화 -->

<script src='${contextPath}/js/carcalendar.js'></script>

<link rel='stylesheet' href='${contextPath}/css/carcalendar.css' />
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>calendar</title>

</head>

<script type="text/javascript">
	var typeName = '${param.typeName}';
	var listName = '${param.listName}';
</script>

<body>
	<input type="hidden" id="contextPath" value="${contextPath}">

	<form action="date">

		<div id="calendar"></div>

	</form>







</body>
</html>
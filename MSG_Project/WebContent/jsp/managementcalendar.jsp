<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%request.setAttribute("contextPath", request.getContextPath());%>
<%response.setContentType("text/html; charset=utf-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css" href="${contextPath}/css/Calendar.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/calendar/fullcalendar.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/calendar/fullcalendar.min.css">


<link rel="stylesheet" type="text/css" href="${contextPath}/fullcalendar/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/fullcalendar/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/fullcalendar/theme.css">



<script src='${contextPath}/calendar/moment.min.js'></script>
<script src='${contextPath}/calendar/jquery.min.js'></script>
<script src='${contextPath}/calendar/fullcalendar.js'></script>
<script src='${contextPath}/calendar/ko.js'></script>

<script src='${contextPath}/js/managementcalendar.js'></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/managementcalendar.css">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">

<style type="text/css">
	#content>#menuBox1{
		margin-top: 0 !important;
	}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>calendar</title>
</head>
<body>

	<jsp:include page="pageHeader.jsp"></jsp:include>

	<div id="content">
   
		<p style="position: absolute; margin-left: 920px; margin-top: 15px">
		
			<input type="hidden" value="${contextPath}" id="contextPath">
			<input type="hidden" id="path" value="${contextPath}">
			<input type="hidden" id="empNo" value="${user.empNo}">
			<input type="hidden" id="user_auth" value="${user.deptName}"> 
			
		</p>
	      
		<jsp:include page="/jsp/pageSide.jsp">
			<jsp:param value="management" name="category"/>
		</jsp:include>
		
		<div id="calendar"></div>

   </div>

   <!-------------------------------------바디---------------------------------------------------------------------------->




</body>
</html>
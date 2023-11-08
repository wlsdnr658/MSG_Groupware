<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/messenger.css?ver=7">
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
<script type="text/javascript" src = "${contextPath}/js/chatList.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>Chat List</title>
</head>
<body>
<jsp:include page="/jsp/pageHeader.jsp"></jsp:include>

<input type="hidden" id="contextPath" value="${contextPath}">
	<div id="content">
<jsp:include page="/jsp/pageSide.jsp">
	<jsp:param value="chatList" name="pageType"/>
	<jsp:param value="messenger" name="category"/>
</jsp:include>
		<div id="menuBox2">
			<p style="margin: 17.5px; display: inline-block;">
				<a href="#" style="font-size: 20px">참여 중인 채팅방</a>
			</p>
			<hr id="hr3">
			<table id="chatRoom-list">
			</table>
		</div>
	</div>
<form id="postOpen" method="post" style="display: hidden">
	<input type="hidden" value="${user.empNo}" id="empNo" name="empNo">
	<input type="hidden" value="" id="roomNo" name="roomNo">
</form>
</body>
</html>


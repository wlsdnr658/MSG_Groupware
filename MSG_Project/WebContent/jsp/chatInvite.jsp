<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<script type="text/javascript" src = "${contextPath}/js/chatInvite.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>Chat Invite List</title>
</head>
<body>
<input type="hidden" id="contextPath" value="${contextPath}">
<input type="hidden" id="empNo" value="${user.empNo}">
<jsp:include page="/jsp/pageHeader.jsp"></jsp:include>
	<div id="content">
<jsp:include page="/jsp/pageSide.jsp">
	<jsp:param value="chatInvite" name="pageType"/>
	<jsp:param value="messenger" name="category"/>
</jsp:include>
		<div id="menuBox2">
			<p style="margin: 17.5px; display: inline-block;">
				<a href="#" style="font-size: 20px">초대 받은 채팅방</a>
			</p>
			<hr id="hr3">
			<table id="chatRoom-inviteList">
				<tr>
					<th class="msg_th" style="width: 58%;">채팅방 제목</th>
					<th class="msg_th" style="width: 20%;"></th>
					<th class="msg_th" style="width: 20%; text-align: center;">초대자 id</th>
				</tr>
				<c:forEach items="${inviteList}" var="list">
					<tr>
						<td> <a class="chatInvite-title" style="padding-left:5px;">${list.roomTitle}</a></td>
						<td>
							<button id="chat-join" value="${list.roomNo}">참여</button>
							<button id="chat-reject" value="${list.roomNo}">거절</button>
						</td>
						<td style="text-align: center;">${list.empName}</td>
					</tr>
					<tr>
						<td colspan="3"><hr></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>
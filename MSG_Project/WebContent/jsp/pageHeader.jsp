<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src= "${contextPath}/js/sockjs.js"></script>
<script type="text/javascript" src= "${contextPath}/js/stomp.js"></script>
<script type="text/javascript" src = "${contextPath}/js/header.js"></script>  
<link rel="stylesheet" type="text/css" href="${contextPath}/css/header.css">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
</head>
<body>
	<input type="hidden" id="path" value="${contextPath}">
	<input type="hidden" id="empNo" value="${user.empNo}">
	<input type = "hidden" id = "user_auth" value = "${user.deptName}"> 
	<header id="main_header">
		<hgroup id="title">
			<img src="${contextPath}/img/msgLogo.png" onclick="location.href='${contextPath}/page/mainForm'" width = "140px" height="80px" style="padding-left:12px;">
		</hgroup> 
		
		<nav id="main_lnb">
			<ul id="ul1">
				<li><a href = "${contextPath}/board/boardMainForm">게시판</a></li>
				<li><a href = "${contextPath}/mail/mail_main_form">메일</a></li>
				<li id = "approvNavi"><a href = "${contextPath}/approval/approvalMainForm">전자결재</a></li>
				<li>
					<img src="${contextPath}/img/new.png" id="new_messenger">
					<a href = "${contextPath}/messenger/messageList">메신저</a>
				</li>
				<li><a href = "${contextPath}/management/managementcalendar">자원관리</a></li>
				
				<c:if test="${user.deptNo == 10}">
				<li><a href = "${contextPath}/emp/empListPage">사원관리</a></li>
				</c:if>
				
				<li id = "weather">
					<div id="imgBox">
						<h4 id="place" style="display: inline-block;"></h4>
						<h4 id="temp" style="display: inline-block;"></h4>
					</div>
				</li>
				
			</ul>
		</nav> 
	</header>
	<div id="message_alert" style="display: none;">
		<div id="message_alert_div">
			<div class="alertDiv_header">
			</div>
			<div class="alertDiv_body">
				<a href="${contextPath}/messenger/messageList">
					새로운 쪽지가 <br>
					도착했습니다.
				</a>
			</div>
		</div>
	</div>
	
	<div id="approve_alert" style="display: none;">
		<div id="approve_alert_div">
			<div class="alertDiv_header">
				결재
			</div>
			<div class="alertDiv_body">
				<a href="${contextPath}/approval/approvalStore">
					새로운 결재가 <br>
					도착했습니다.
				</a>
			</div>
		</div>
	</div>
	
	<div id="mail_alert" style="display: none;">
		<div id="mail_alert_div">
			<div class="alertDiv_header">
				메일
			</div>
			<div class="alertDiv_body">
				<a href="${contextPath}/mail/mail_receiveInbox_form">
					새로운 메일이 <br>
					도착했습니다.
				</a>
			</div>
		</div>
	</div>		
	
</body>
</html>
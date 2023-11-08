<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/mail.css">	

<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/main.css">

<link rel="stylesheet" type="text/css" href="${contextPath}/js/mail.js">

<script src='${contextPath}/fullcalendar/jquery.min.js'></script>
<%-- <script src='${contextPath}/fullcalendar/moment.min.js'></script> --%>
<%-- <script src='${contextPath}/fullcalendar/fullcalendar.js'></script> --%>
<%-- <script src='${contextPath}/fullcalendar/ko.js'></script> --%>
<title>메인</title>
</head>
<%-- <script type="text/javascript" src="${contextPath}/js/main.js"></script> --%>
<body>



	<jsp:include page="/jsp/pageHeader.jsp"></jsp:include>
	<!-------------------------------------헤더--------------------------------------------------------->
	<input type="hidden" id="path" value="${contextPath}">
	<div id="content">

		<jsp:include page="/jsp/pageSide.jsp">
			<jsp:param value="mail" name="category"/>
		</jsp:include>
		<div id="menuBox2">
			<p style="margin: 20px">
				<a href="${contextPath}/mail/mail_receiveInbox_form"
					style="font-size: 20px;">받은편지함</a>
			</p>
			<hr id="hr3">
			<table>
				<tr>
					<th>보낸사람</th>
					<th>제목</th>
					<th>작성일</th>
				</tr>
				<c:forEach end="4" items="${receiveinbox}" var="receiveinbox">
					<tr>
					
<%-- 						<td>${receiveinbox.senderId}</td>  --%>
						<td>${receiveinbox.empName}</td>
						<td><a href="mail_receiveInbox_view_form?mailNo=${receiveinbox.mailNo}">${receiveinbox.title}</a></td>
						<td>${receiveinbox.writeDate}</td>
					</tr>
				</c:forEach>
			</table>
			<br>
			</div>
			<div id="menuBox5">
			<br style="margin-top:30px;">
			<p style="margin: 20px">
				<a href="${contextPath}/mail/mail_sendInbox_form"
					style="font-size: 20px;">보낸편지함 </a>
			</p>
			<hr id="hr3">
			<table>
				<tr>
					<th>받는사람</th>
					<th>제목</th>
					<th>작성일</th>
				</tr>
				<c:forEach end="4" items="${sendinbox}" var="sendinbox">
					<tr>
<%-- 						<td>${sendinbox.receiverId}</td> --%>
						<td>${sendinbox.empName}</td>
						<td><a href="mail_sendInbox_view_form?mailNo=${sendinbox.mailNo}">${sendinbox.title}</a></td>
						<td>${sendinbox.writeDate}</td>

<%-- 						<c:choose> --%>
<%-- 							<c:when test="${sendinbox.receiverId}"> --%>
<%-- 								<td>${sendinbox.receiverId}</td> --%>
<%-- 							</c:when> --%>
<%-- 							<c:otherwise> --%>
<%-- 								<td>${sendinbox.carbonId}</td> --%>
<%-- 							</c:otherwise> --%>
<%-- 						</c:choose> --%>
					</tr>
				</c:forEach>

			</table>
			
		</div>
	</div>
	
	
	
	<!-------------------------------------바디---------------------------------------------------------------------------->
	
</body>
</html>
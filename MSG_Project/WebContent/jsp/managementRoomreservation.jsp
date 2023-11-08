
<%@page import="org.apache.naming.java.javaURLContextFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	request.setAttribute("contextPath", request.getContextPath());
%>


<!DOCTYPE html>
<html>
<head>



<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript"
	src="${contextPath}/js/managementroomreservation.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/reservation.css">
<body>
	<input type="hidden" id="contextPath" value="${contextPath}">

	<input type="hidden" id="chkreserv" value="N" />





	<form method="post" onsubmit='return roomreservation();'>
		<table>
			<tr>
				<th>종류</th>
				<td><input type="text" class="aa" value="${typeName}"
					readonly="readonly" name="typeName" id="typeName"></td>
			</tr>
			<tr>
				<th>방 종류</th>
				<td><input type="text" value="${listName}" readonly="readonly"
					class="aa" name="roomName" id="roomName"></td>
			</tr>
			<tr>
				<th>달력</th>
				<td><input class="aa" type="text" name="startDate"
					value="${start}" id="startDate"></td>

			</tr>

			<tr>

				<th>달력</th>
				<td width="50px;"><input class="aa" type="text" name="endDate"
					value="${end}" id="endDate"></td>
			</tr>
			<tr>
				<th>사용자</th>
				<td width="50px;"><input type="text" class="aa" id="assigned"
					readonly="readonly" name="assigned" value="${user.empNo}"></td>
			</tr>




		</table>
		<br> <input type="submit" class="reseBtn" value="예약">

	</form>



</body>
</html>
<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="org.apache.naming.java.javaURLContextFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>




<%
	request.setAttribute("contextPath", request.getContextPath());
%>


<!DOCTYPE html>
<html>
<head>


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/managementroommodify.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/modify.css">
	<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<body>
	<input type="hidden" value="${contextPath}" id="context">



	<form action="Roommodify" method="post">

		<table>
			<tr>
				<th style="display: none;"/>
				<th width="200px">회의실</th>
				<th width="100px">시작 수정전</th>
				<th width="100px">시작 수정후</th>
				<th width="100px">반납 수정전</th>
				<th width="100px">반납 수정후</th>
				<th width="100px">사원</th>
				<th width="100px">수정</th>
				<th width="100px">반납</th>
			</tr>




			<c:forEach items="${viewroomData.roomitemList}" var="reserv">
				<c:if test="${reserv.status == 1}">
					<tr>
						<td style="display:none;"><input type="hidden"
							name="rendNo" value="${reserv.rendNo}"></td>

						<td>${reserv.roomName}</td>
						<td>${reserv.startDate}</td>
						<td><input class="aa" type="datetime-local" name="startDate"></td>

						<td>${reserv.endDate}</td>
						<td><input class="aa" type="datetime-local" name="endDate"></td>
						<td><input class="aa" type="text" readonly="readonly"
							name="assigned" value="${reserv.assigned}"></td>


						<td><input type="submit" class="modifyBtn" value="수정"></td>

						<td><input type="button" id="modifyBtn" value="반납" class="delete-btn">
							<input class="aa" type="hidden" value="${reserv.rendNo}"
							class="car-rendNo">
							<input name="typeName"  type="hidden" value="${reserv.typeName}">
							</td>
							

					</tr>
				</c:if>
			</c:forEach>



		</table>
	</form>





	<c:if test="${viewroomData.startPage !=1 }">
		<a
			href="managementRoommodify?page=1
							<c:if test="${viewroomData.type != null}">&type=${viewroomData.type}</c:if>
							<c:if test="${viewroomData.keyword != null}">&keyword=${viewroomData.keyword}</c:if>
						">[처음]</a>
		<a
			href="managementRoommodify?page=${viewroomData.startPage-1}
							<c:if test="${viewroomData.type != null}">&type=${viewroomData.type}</c:if>
							<c:if test="${viewroomData.keyword != null}">&keyword=${viewroomData.keyword}</c:if>
						">[이전]</a>
	</c:if>
	<c:forEach var="pageNum" begin="${viewroomData.startPage}"
		end="${viewroomData.endPage < viewroomData.pageTotalCount ? viewroomData.endPage : viewroomData.pageTotalCount}">
		<c:choose>
			<c:when test="${pageNum == viewroomData.currentPage}">
				<b>[${pageNum}]</b>
			</c:when>
			<c:otherwise>
				<a
					href="managementRoommodify?page=${pageNum}
									<c:if test="${viewroomData.type != null}">&type=${viewroomData.type}</c:if>
									<c:if test="${viewroomData.keyword != null}">&keyword=${viewroomData.keyword}</c:if>
								">[${pageNum}]</a>
			</c:otherwise>
		</c:choose>

	</c:forEach>
	<c:if test="${viewroomData.endPage < viewroomData.pageTotalCount}">
		<a
			href="managementRoommodify?page=${viewcarData.endPage+1}
							<c:if test="${viewroomData.type != null}">&type=${viewroomData.type}</c:if>
							<c:if test="${viewroomData.keyword != null}">&keyword=${viewroomData.keyword}</c:if>
						">[다음]</a>
		<a
			href="managementRoommodify?page=${viewroomData.pageTotalCount}
							<c:if test="${viewroomData.type != null}">&type=${viewroomData.type}</c:if>
							<c:if test="${viewroomData.keyword != null}">&keyword=${viewroomData.keyword}</c:if>
						">[마지막]</a>
	</c:if>

	<form action="managementRoommodify">
		<select name="type">
			<option value="1">차 이름</option>
			<option value="2">작성자</option>

		</select> <input type="text" name="keyword"> <input type="submit" class="modifyBtn"
			value="검색">
	</form>
</body>
</html>
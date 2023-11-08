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

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script src='${contextPath}/js/managementcar.js'></script>

<title></title>
</head>

<script type="text/javascript" src="${contextPath}/js/main.js"></script>

<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/Managementcar.css">
<body>

	<jsp:include page="pageHeader.jsp" flush="false" />
	<!-------------------------------------헤더--------------------------------------------------------->
	<input type="hidden" id="contextPath" value="${contextPath}">
	<div id="content">

		<p style="position: absolute; margin-left: 920px; margin-top: 15px">

		</p>
		<jsp:include page="/jsp/pageSide.jsp">
			<jsp:param value="management2" name="category"/>
		</jsp:include>

		<div id="menuBox2">
			<p style="margin: 8px">
				<a href="#" style="font-size: 20px;">자원관리</a>
			</p>
			<hr id="hr3">

			<table id="notice_list">

				<tr id="tr1">
					<!-- <th width="35px">No.</th> -->
					<th width="50px">차종</th>
					<th width="200px">차량</th>
					<th width="100px">예약</th>
					<th width="100px">예약 확인</th>
				</tr>




				<c:forEach items="${viewData.itemList}" var="car">
					<c:if test="${car.itemNo == 1 }">
						<tr>

							<td style="display: none">${car.itemNo}</td>
							<td>${car.typeName}</td>
							
							<td><img
								src="${contextPath}/management/getItemImage?typeName=${car.typeName}"
								 title="${car.listName}"style="width: 70px; height: 50px;"></td>
							<td>
								<button value="${car.typeName}" class="reservation">예약하기</button>
								<input type="hidden"  value="${car.listName}">
							</td>

							<td><input type="button" value="확인" 
								class ="modify" id="reservation_check">
								<input type="hidden"  value="${car.typeName}">
								<input type="hidden"  value="${car.listName}">
								
								</td>
						</tr>
						<tr>
							<td colspan="5"><hr></td>
						</tr>
					</c:if>
				</c:forEach>
			</table>





		</div>

	</div>
	<!-------------------------------------바디--------------------------------------------------------->
	<!-------------------------------------푸터--------------------------------------------------------->

</body>
</html>

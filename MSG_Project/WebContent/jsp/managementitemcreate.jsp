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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src='${contextPath}/js/managementcar.js'></script>
<title></title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/managementitemcreate.js"></script>
<script type="text/javascript" src="${contextPath}/js/main.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/Managementitemcreate.css">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<body>

	<jsp:include page="pageHeader.jsp" flush="false" />
	<!-------------------------------------헤더--------------------------------------------------------->
	<input type="hidden" id="contextPath" value="${contextPath}">
	
	<div id="content">

		<p style="position: absolute; margin-left: 920px; margin-top: 15px">

		</p>
		
		<jsp:include page="/jsp/pageSide.jsp">
			<jsp:param value="management" name="category"/>
		</jsp:include>

		<div id="menuBox2">
			<p style="margin: 8px">
				<a href="#" style="font-size: 20px;">자원 관리 </a>
			</p>
			<hr id="hr3">
			<table id="notice_list">

				<tr id="tr1">
					<!-- <th width="35px">No.</th> -->
					<th width="50px">종류</th>
					<th width="200px">이미지</th>
<!-- 					<th width="50px">수정</th> -->
					<th width="50px">삭제</th>
				</tr>

				<c:forEach items="${viewitemList.itemList}" var="item">
					<c:if test="${item.status != 0 }">

						<tr>

							<td style="display: none"><input type="hidden" name="itemNo" id="itemNo" value="${item.itemNo}">${item.itemNo}</td>
							<td>${item.typeName}</td>

							<td><img
								src="${contextPath}/management/getItemImage?typeName=${item.typeName}"
								style="width: 70px; height: 50px;" ></td>

							
							<td><input type="button" value="삭제" class="delete-btn"></td>
															

						</tr>
						<tr>
							<td colspan="5"><hr></td>
						</tr>
						<td><input type="hidden" value="${item.typeName}" id="typeName"></td> 
					</c:if>
				</c:forEach>

			</table>
			<input type="button" class="insert" value="아이템 등록" style="margin-left: 680px; margin-top: 20px" onclick="insert();">

		</div>

	</div>

	<div id="itemNumber">
	
		<c:if test="${viewitemList.startPage !=1 }">
			<a
				href="managementitemcreate?page=1
							<c:if test="${viewitemList.type != null}">&type=${viewitemList.type}</c:if>
							<c:if test="${viewitemList.keyword != null}">&keyword=${viewitemList.keyword}</c:if>
						">[처음]</a>
			<a
				href="managementitemcreate?page=${viewcarData.startPage-1}
							<c:if test="${viewitemList.type != null}">&type=${viewitemList.type}</c:if>
							<c:if test="${viewitemList.keyword != null}">&keyword=${viewitemList.keyword}</c:if>
						">[이전]</a>
		</c:if>
		<c:forEach var="pageNum" begin="${viewitemList.startPage}"
			end="${viewitemList.endPage < viewitemList.pageTotalCount ? viewitemList.endPage : viewitemList.pageTotalCount}">
			<c:choose>
				<c:when test="${pageNum == viewitemList.currentPage}">
					<b>[${pageNum}]</b>
				</c:when>
				<c:otherwise>
					<a
						href="managementitemcreate?page=${pageNum}
									<c:if test="${viewitemList.type != null}">&type=${viewitemList.type}</c:if>
									<c:if test="${viewitemList.keyword != null}">&keyword=${viewitemList.keyword}</c:if>
								">[${pageNum}]</a>
				</c:otherwise>
			</c:choose>

		</c:forEach>
		<c:if test="${viewitemList.endPage < viewitemList.pageTotalCount}">
			<a
				href="managementitemcreate?page=${viewitemList.endPage+1}
							<c:if test="${viewitemList.type != null}">&type=${viewitemList.type}</c:if>
							<c:if test="${viewitemList.keyword != null}">&keyword=${viewitemList.keyword}</c:if>
						">[다음]</a>
			<a
				href="managementitemcreate?page=${viewitemList.pageTotalCount}
							<c:if test="${viewitemList.type != null}">&type=${viewitemList.type}</c:if>
							<c:if test="${viewitemList.keyword != null}">&keyword=${viewitemList.keyword}</c:if>
						">[마지막]</a>
		</c:if>
	</div>

	<div id="itemSearch">
		<form action="managementitemcreate">
			<select name="type">
				<option value="1">종류</option>
				<option value="2">번호</option>

			</select> <input type="text" name="keyword"> 
			<input type="submit" class="ser" value="검색">
		</form>
	</div>


	<!-------------------------------------바디--------------------------------------------------------->
	<!-------------------------------------푸터--------------------------------------------------------->

</body>
</html>

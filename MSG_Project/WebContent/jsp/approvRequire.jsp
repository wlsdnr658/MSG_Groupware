<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous">
</script>
</head>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/approvReq.css">
<script type="text/javascript" src="${contextPath}/js/main.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<body>
	<jsp:include page="/jsp/pageHeader.jsp"></jsp:include>
	<input type="hidden" id="emp" value="${user.empNo}">
	<input type="hidden" id="path" value="${contextPath}">
	<div id="content">
		<jsp:include page="/jsp/pageSide.jsp">
			<jsp:param value="approv" name="category"/>
		</jsp:include>
		<div id="menuBox2">
			<p style="margin: 20px">
				<a href="#" style="font-size: 20px;">결재요청 목록</a>
			</p>
			<hr id="hr3">
		</div>

		<div id="reqList">
			<div style="text-align: right; margin-bottom: 10px;">
				<form action="${contextPath}/approval/approvRequire">
					<!--  empNo, status, keyword, type 은 jsp 에서 넘김  -->
					<input type="hidden" name="empNo" value="${user.empNo}"> <input
						type="hidden" name="status" value="1"> <select name="type">
						<option value="1">제목</option>
					</select> <input type="text" name="keyword"> <input type="submit"
						value="검색">
				</form>
			</div>
			<ul id="myUl">
				<hr>
				<c:forEach var="app" items="${viewData.appList}">
					<li class="approv">
						<ul>
							<li class="appLi" style="padding: 10px; margin: 20px;">
							<span class="current" style="float: left; font-weight: bold;">${app.current.empName}&nbsp;|&nbsp;${app.current.deptName}</span>
							
							<span class="writer"><img src = "${contextPath}/img/point.png" width = "20px" height = "10px" style = "margin-left: 5px; margin-right: 5px;"> ${app.writer.empName}&nbsp;|&nbsp;${app.writer.deptName}</span>
								<br> 
								<span class="title" style="float: left;">
									<span style="font-size: 12px;">${app.docName}</span>
									<a href="${contextPath}/approval/viewApprov?approvNo=${app.approvNo}&docNo=${app.docNo}" style="margin-left: 10px;">${app.title}</a>								
								</span>
							</li>
							<br>	
							<hr>
						</ul>
					</li>
				</c:forEach>
			</ul>
			<ul id="numbering">
				<li style="text-align: center;"><c:if
						test="${viewData.startPage !=1 }">
						<a
							href="${contextPath}/approval/approvRequire?page=1
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[처음]</a>
						<a
							href="${contextPath}/approval/approvRequire?page=${viewData.startPage-1}
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[이전]</a>
					</c:if> <c:forEach var="pageNum" begin="${viewData.startPage}"
						end="${viewData.endPage < viewData.pageTotalCount ? viewData.endPage : viewData.pageTotalCount}">
						<c:choose>
							<c:when test="${pageNum == viewData.currentPage}">
								<b>[${pageNum}]</b>
							</c:when>
							<c:otherwise>
								<a
									href="${contextPath}/approval/approvRequire?empNo=${viewData.appList[0].empNo}&page=${pageNum}&status=1
									<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
									<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								"
									style="font-weight: normal;">[${pageNum}]</a>
							</c:otherwise>
						</c:choose>

					</c:forEach> <c:if test="${viewData.endPage < viewData.pageTotalCount}">
						<a
							href="${contextPath}/approval/approvRequire?page=${viewData.endPage+1}
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[다음]</a>
						<a
							href="${contextPath}/approval/approvRequire?page=${viewData.pageTotalCount}
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[마지막]</a>
					</c:if></li>
			</ul>

		</div>
	</div>
	</div>
</body>
</html>
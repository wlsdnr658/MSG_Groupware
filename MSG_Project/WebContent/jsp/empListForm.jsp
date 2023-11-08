<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script
	src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous">
</script>
</head>
<link rel = "stylesheet" type = "text/css" href = "${contextPath}/css/main.css">
<link rel = "stylesheet" type = "text/css" href = "${contextPath}/css/empList.css">
<script type = "text/javascript" src = "${contextPath}/js/main.js"></script>
<script type = "text/javascript" src = "${contextPath}/js/empMng.js"></script>
<script type = "text/javascript" src = "${contextPath}/js/empList.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<body>
	<div id = "mask"></div>
	<div id = "empView" style = "display : none;">
		<ul id = "showUL" align = "center">
			<li id = "empPic">
				
			</li>
			<li>
				<span id = "empNo"></span>
			</li>
			<li>
				<span id = "empName"></span>
			</li>
			<li>
				<span id = "empDept"></span>
			</li>
			<li>
				<span id = "empPos"></span>
			</li>
			<li>
				<span id = "empTel"></span>
			</li>
			<li>
				<span id = "empEmail"></span>
			</li>
			<li>
				<span id = "empSd"></span>
			</li>
			<li>
				<span id = "empOd"></span>
			</li>
		</ul>
	</div>
	<input type = "hidden" id = "path" value = "${contextPath}">
	<jsp:include page = "/jsp/pageHeader.jsp"></jsp:include>
		<div id = "content">
		<jsp:include page="/jsp/pageSide.jsp">
			<jsp:param value="emp" name="category"/>
		</jsp:include>
		<div id = "menuBox2">
			<p style = "margin : 20px">
				<a href = "#" style = "font-size: 20px;">사원목록</a>
			</p>
			<hr id = "hr3">
			<br><br>
			<hr id = "hr4">
		</div>
		
		<div id = "empList">
			<div id = "search" align = "center">
				<form action="${contextPath}/emp/empListPage">
					<select name="type">
						<option value="1">사원이름</option>
						<option value="2">부서이름</option>
					</select> 
					<input type="text" name="keyword"> <input type="submit" value="검색" style="background-color: white;border: 1px solid black;cursor: pointer;">
				</form>
			</div>
			<table id = "myUl">
				<caption>사원번호, 사원이름, 사원부서, 사원직급, 연락처, 수정, 삭제</caption>
				<colgroup>
					<col style = "width : 15%;">
					<col style = "width : 15%;">
					<col style = "width : 10%;">
					<col style = "width : 10%;">
					<col style = "width : 30%;">
					<col style = "width : 10%;">
					<col style = "width : 10%;">
				</colgroup>
				<thead>
					<tr>
						<th>사원번호</th>
						<th>사원이름</th>
						<th>부서</th>
						<th>직급</th>
						<th>연락처</th>
						<th></th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody id = "table_Content">
					<c:forEach var = "emp" items = "${viewData.empList}">
						<tr>
							<td  onclick = "empShow('${emp.empNo}');">${emp.empNo}</td>
							<td><input type = "text" class = "userInput" id = "${emp.empNo}name" value = "${emp.empName}" ></td>
							<td>
								<select style = "padding : 2px;" id = "${emp.empNo}dept">
									<option value="0">${emp.deptName}</option>
									<option value="10">경영/기획</option>
									<option value="20">영업</option>
									<option value="30">재무</option>
									<option value="40">IT</option>
								</select>
							</td>
							<td>
								<select style = "padding : 2px;" id = "${emp.empNo}pos">
									<option value="0">${emp.empPos}</option>
									<option value="사원">사원</option>
									<option value="대리">대리</option>
									<option value="과장">과장</option>
									<option value="부장">부장</option>
								</select>
							</td>
<%-- 							<td><input type = "text" class = "userInput" id = "${emp.empNo}dept"value = "${emp.deptName}"></td> --%>
<%-- 							<td><input type = "text" class = "userInput" id = "${emp.empNo}pos"value = "${emp.empPos}"></td> --%>
							<td><input type = "text" class = "userInput" id = "${emp.empNo}tel"value = "${emp.empTel}"></td>
							<td><input type = "button" value = "수정" onclick = "modifyEmp('${emp.empNo}');"></td>
							<td><input type = "button" value = "삭제"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br>
			<ul id = "numbering">
				<li style = "text-align: center;">
					<c:if test="${viewData.startPage !=1 }">
						<a href="${contextPath}/emp/empListPage?page=1
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[처음]</a>
						<a href="${contextPath}/emp/empListPage?page=${viewData.startPage-1}
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[이전]</a>
					</c:if> 
					<c:forEach var="pageNum" begin="${viewData.startPage}" end="${viewData.endPage < viewData.pageTotalCount ? viewData.endPage : viewData.pageTotalCount}">
						<c:choose>
							<c:when test="${pageNum == viewData.currentPage}">
								<b>[${pageNum}]</b>
							</c:when>
							<c:otherwise>
								<a href="${contextPath}/emp/empListPage?page=${pageNum}
									<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
									<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								" style = "font-weight: normal;">[${pageNum}]</a>
							</c:otherwise>
						</c:choose>

					</c:forEach> <c:if test="${viewData.endPage < viewData.pageTotalCount}">
						<a href="${contextPath}/emp/empListPage?page=${viewData.endPage+1}
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[다음]</a>
						<a href="${contextPath}/emp/empListPage?page=${viewData.pageTotalCount}
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[마지막]</a>
					</c:if>
				</li>
			</ul>

		</div>
<!-- 		<div id = "search" align = "center"> -->
<!-- 			<select name = "searchType"> -->
<!-- 				<option value = "empName">이름</option> -->
<!-- 				<option value = "empDept">부서</option> -->
<!-- 				<option value = "empPos">직급</option> -->
<!-- 			</select> -->
<!-- 			<input type = "text"> -->
<!-- 			<input type = "button" value = "검색"> -->
<!-- 		</div> -->
	</div>
</body>
</html>
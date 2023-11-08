<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="https://code.jquery.com/jquery-3.3.1.min.js"
		integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
		crossorigin="anonymous">
</script> 

<link rel="stylesheet" type="text/css" href="${contextPath}/css/board.css">
<script type="text/javascript" src="${contextPath}/js/boardSubMenu.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardDeptAuth.js"></script>
<style type="text/css">
	#tr1 td{
		padding: 20px 5px; 
	}
	#projectTable1 th {
		font-size: 14px;
	}
</style>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>진행중 프로젝트</title>
</head>

<body>
		<jsp:include page="/jsp/pageHeader.jsp" flush="false"/>
		<!-------------------------------------헤더--------------------------------------------------------->
		<input type = "hidden" id = "path" value = "${contextPath}">
		<div id="content">
		
			<input type="hidden" value="${user.empPos}" id="inputEmpPos">
			<input type="hidden" value="${user.deptNo}" id="inputDeptNo">
			
			<jsp:include page="/jsp/pageSide.jsp">
				<jsp:param value="board" name="category"/>
			</jsp:include>

			<div id="menuBox2" style="width: 800px;">
				<p style="margin: 20px">
					<a href="#" style="font-size: 20px">프로젝트 게시판</a>
				</p>
				 
				<hr id="hr3" style="margin-bottom: 5px;width:800px;">
				
				<p style="margin-left: 5px;">
					<a href="#" style="font-size: 17px">진행중 프로젝트</a>
					
					<c:if test="${user.empPos == '부장' || user.empPos == '과장'}">
						<button id="writeBtn" style="float: right;" onclick="checkAuth()">프로젝트 추가</button>
					</c:if>
					
				</p>
				
				<div id="noticeBox" style="margin-top: 5px; height: 840px; border-bottom: 1px solid silver">    
						
					<div id="projectTableWrapBox">
					
						<table id="projectTable1">  
							<tr>
								<th style="width: 40px">No.</th>
								<th style="width: 280px">프로젝트명</th>
								<th style="width: 100px">담당자</th>
								<th style="width: 210px">기간</th>
								<th style="width: 60px">진행률</th>
								<th style="width: 100px">등록일</th>
							</tr>
							
							<c:forEach begin="0" items="${viewData.projectOngoingboardList}" var="projectOngoing">
									<tr id="tr1" style="border-bottom: 1px solid silver;">
										
										<td style="font-size: 14px">
											<fmt:parseNumber value="${projectOngoing.rnum}" integerOnly="true"/>
										</td>
											
										<td style="font-size: 16px">
											<a href="${contextPath}/board/boardProjectOngoingViewForm?boardNo=${projectOngoing.boardNo}">${projectOngoing.projectName}</a>
										</td>
										
										<td style="font-size: 14px">${projectOngoing.empName}</td>
										
										<td>${projectOngoing.pStartDate1} ~ ${projectOngoing.pEndDate1}</td>
										
										<td style="font-size: 14px; width: 100px">
											<progress value="${projectOngoing.rop}" max=100></progress>${projectOngoing.rop}%
										</td>
										
										<td style="font-size: 14px">${projectOngoing.writeDate1}</td>
									</tr>
							</c:forEach>
							
						</table>
						
					</div>
					
				</div>
				
				<div style="text-align: center;">
		
					<c:if test="${viewData.startPage !=1 }">
						<a style="font-weight: normal" href="boardProjectOngoing?page=1"
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						>[처음]
						</a>
						
						<a style="font-weight: normal" href="boardProjectOngoing?page=${viewData.startPage-1}"
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						>
						[이전]
						</a>
					</c:if>
					
					<c:forEach var="pageNum" begin="${viewData.startPage}" end="${viewData.endPage < viewData.pageTotalCount ? viewData.endPage : viewData.pageTotalCount}">
						<c:choose>
							<c:when test="${pageNum == viewData.currentPage}">
								<b>[${pageNum}]</b>
							</c:when>
							<c:otherwise>
								<a style="font-weight: normal;" href="boardProjectOngoing?page=${pageNum}
									<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
									<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								">[${pageNum}]</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					<c:if test="${viewData.endPage < viewData.pageTotalCount}">
						<a style="font-weight: normal;" href="boardProjectOngoing?page=${viewData.endPage+1}"
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						>[다음]</a>
						
						<a style="font-weight: normal;" href="boardProjectOngoing?page=${viewData.pageTotalCount}"
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						>[마지막]</a>
					</c:if>
					
				</div>
					
				<form action="" style="text-align: center; margin-top: 40px; margin-bottom: 100px"> 
					<select name="type">
						<option value="1">제목</option>
						<option value="2">작성자</option>
						<option value="3">제목+작성자</option>
						<option value="4">내용</option>
					</select> <input type="text" name="keyword"> <input type="submit"
						value="검색">
				</form>

			</div>
		
		</div>

</body>
</html>


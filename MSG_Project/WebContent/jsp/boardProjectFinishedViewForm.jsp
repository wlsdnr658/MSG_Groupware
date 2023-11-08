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
<script type="text/javascript" src="${contextPath}/js/boardFileBox.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardSubMenu.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardParticipant.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardDeptAuth.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardOpenWin2.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>완료된 프로젝트 상세보기</title>
</head>
<body>
		<jsp:include page="/jsp/pageHeader.jsp" flush="false"/>
		<!-------------------------------------헤더--------------------------------------------------------->
		<input type = "hidden" id = "path" value = "${contextPath}">
		<div id="content">
		 
		 <input type="hidden" id="boardNoinput" value="${projectFinished.boardNo}">
		
		<input type="hidden" value="projectOngoing" id="boardName">
		<input type="hidden" value="ProjectOngoing" id="boardName1">
		<input type="hidden" value="${user.deptNo}" id="inputDeptNo">
		<input type="hidden" value="${user.empPos}" id="inputEmpPos">
			
			<jsp:include page="/jsp/pageSide.jsp">
				<jsp:param value="board" name="category"/>
			</jsp:include>
			
			<input type="hidden" value="${user.deptNo}" id="inputDeptNo">
			<input type="hidden" value="${user.empPos}" id="inputEmpPos">
			
			<div id="menuBox2" style="overflow: auto;">
			
				<p style="margin: 20px">
					<a href="#" style="font-size: 20px; display: inline-block;">프로젝트 게시판</a>
				</p>
				
				<hr id="hr3" style="margin-bottom: 5px">
				
				<p style="margin-top: 5px; width: 750px">
				
					<a href="#" style="font-size: 17px;padding-left: 5px">완료된 프로젝트</a>
					
					<span style="float: right;">
						<input type="button" id="deleteBtn" value="삭제하기" onclick="open_win1('${contextPath}/board/boardProjectOngoingCheckPassForm?boardNo=${projectOngoing.boardNo}','delete')">
						<input type="button" id="listBtn" value="목록보기" onclick="location.href='boardProjectFinished'">
					</span>
					
				</p>
					
					<div id="viewBox">
						
						<div id="viewBoxTitle">
							<h3 style="margin-bottom:3px; margin-left: 10px; margin-top: 10px;">${projectFinished.projectName}</h3>
							
							<ul>
								<li name="EMPNAME">${projectFinished.empName}</li>
								<li name="WRITEDATE">${projectFinished.writeDate1}</li>
								<li name="VIEWCOUNT" style="border-right: 0">조회수 ${projectFinished.viewCount}</li>
							</ul>
						</div>
						
						<button id="participantBtn" onclick="createLine()" style="float: right;">참여자</button>
						
						<div id="participant_div" style = "display : none;">
							<div style="width: 750px; margin: 0 auto">
								<ul id="ptcp" style="font-size: 12px">
								</ul>
							</div>
							<input type = "button" id = "closeBtn" value = "닫기" onclick = "closeToLine();">
						</div>   
						 
						<div style="padding-left: 15px; font-size: 15px; overflow: auto;" name="CONTENT" id="viewBoxContent">
							
							<div id="contentBox">${projectFinished.content}</div>
							
							<div id="taskListTableWrapBox">
								
								<table id="taskListTable">
								
									<tr>
										<th id="taskth1">업무목록
<%-- 											<img onclick="open_win('${contextPath}/board/boardProjectOngoingAddTaskForm?boardNo=${projectOngoing.boardNo}','add')" title="업무추가" id="plus" src="${contextPath}/img/plus.png"></img> --%>
										</th>
									</tr>
									
									<tr>
										<th id="taskth2">업무명</th>
									</tr>
									
									<tr>
										<td>
											<ul id="tasks">
											
												<c:forEach items="${viewData.projectOngoingTaskList}" var="task">
												
													<li style="text-align: left; border-bottom: 1px solid silver; padding-top: 2px; padding-bottom: 2px; padding-left: 10px">
														<div id="taskName">
															<a title="업무 상세보기" onclick="open_win3('${contextPath}/board/boardProjectOngoingAddedTaskViewForm?taskNo=${task.taskNo}','view')" href="#">
																${task.taskName}
															</a>
														</div>
														
<%-- 														<img onclick="location.href='boardProjectOngoingOfTaskDelete?taskNo=${task.taskNo}&boardNo=${projectOngoing.boardNo}'" title="업무삭제" id="deleteLink" src="${contextPath}/img/trash.png"></img> --%>
														
<%-- 														<c:if test="${task.uploadFile == 'Y'}"> --%>
<%-- 															<img onclick="open_win4('${contextPath}/board/boardProjectOngoingOfTaskAttachDownloadForm?taskNo=${task.taskNo}','${task.taskNo}')" title="첨부파일" id="fileLink" src="${contextPath}/img/save.png"></img> --%>
<%-- 														</c:if> --%>
													</li>
												
												</c:forEach>
										
											</ul>
										</td>
									</tr>
									
									<tr>
									
										<td>
										
										<div style="text-align: center;">
		
											<c:if test="${viewData.startPage !=1 }">
												<a style="font-weight: normal" href="boardProjectOngoingViewForm?boardNo=${projectOngoing.boardNo}&page=1"
													<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
													<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
												>[처음]
												</a>
												
												<a style="font-weight: normal" href="boardProjectOngoingViewForm?boardNo=${projectOngoing.boardNo}&page=${viewData.startPage-1}"
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
														<a style="font-weight: normal;" href="boardProjectOngoingViewForm?boardNo=${projectOngoing.boardNo}&page=${pageNum}
															<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
															<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
														">[${pageNum}]</a>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											
											<c:if test="${viewData.endPage < viewData.pageTotalCount}">
												<a style="font-weight: normal;" href="boardProjectOngoingViewForm?boardNo=${projectOngoing.boardNo}&page=${viewData.endPage+1}"
													<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
													<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
												>[다음]</a>
												
												<a style="font-weight: normal;" href="boardProjectOngoingViewForm?boardNo=${projectOngoing.boardNo}&page=${viewData.pageTotalCount}"
													<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
													<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
												>[마지막]</a>
											</c:if>
											
										</div>
										
										</td>
									
									</tr>
									
								</table>
								
							</div>
							
						</div>
						
						<div style="display: inline-block;">
							
							<dl id="dl1">
								<dt id="dt1">이전글</dt>
								<dd style="height: 21px;" id="dd1">
									<c:choose>
										<c:when test="${previous.boardNo != null}">
											<a style="margin-left: 10px; font-size: 13px" href="${contextPath}/board/boardProjectOngoingViewForm?boardNo=${previous.boardNo}">${previous.projectName}</a>
										</c:when>
										<c:otherwise>
											<a style="margin-left: 10px; font-size: 13px">이전글이 없습니다.</a>
										</c:otherwise>
									</c:choose>
								</dd>
								<dt id="dt2">다음글</dt>
								<dd style="height: 21px;" id="dd2">
									<c:choose>
										<c:when test="${next.boardNo != null}">
											<a style="margin-left: 10px; font-size: 13px" href="${contextPath}/board/boardProjectOngoingViewForm?boardNo=${next.boardNo}">${next.projectName}</a>
										</c:when>
										<c:otherwise>
											<a style="margin-left: 10px; font-size: 13px">다음글이 없습니다.</a>
										</c:otherwise>
									</c:choose>
								</dd>
							</dl>
							
						</div>
							
						
				</div>
					
				
			</div>
		
				
		</div>
		<!-------------------------------------바디--------------------------------------------------------->
		<!-------------------------------------푸터--------------------------------------------------------->

</body>
</html>


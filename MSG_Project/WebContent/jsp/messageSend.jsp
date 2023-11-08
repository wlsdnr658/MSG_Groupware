<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
<link rel="stylesheet" type="text/css" href="${contextPath}/css/messenger.css?ver=7">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/main.css">	 
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
<script type="text/javascript" src = "${contextPath}/js/message.js"></script>
<script type="text/javascript" src = "${contextPath}/js/message_send.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>sendMessage</title>
</head>
<body>
<jsp:include page="/jsp/pageHeader.jsp"></jsp:include>
	<input type="hidden" value="${user.empNo}" id="userID">
	<input type="hidden" value="102" id="boxType">
	<div id="content">
<jsp:include page="/jsp/pageSide.jsp">
	<jsp:param value="messenger" name="category"/>
	<jsp:param value="messageSend" name="pageType"/>
</jsp:include>
		<!-- 본문 -->
		<input type="hidden" value="발신자">
		
		<div id="menuBox2">
			<p style="margin: 20px; display: inline-block;">
				<a href="#" style="font-size: 20px">보낸 쪽지함</a>
			</p>
			<button id="msg-deleteBtn">삭제</button>
			<form action="messageList_send" id="msg_searchBox">
				<br>
				<select id="searchType" name="type">
					<option>제목</option>
					<option>이름</option>
					<option>내용</option>
					<option>제목+내용</option>
				</select>
				<input type="text" name="keyword" id="searchText">
				<input type="submit" value="검색" id="msg-searchBtn">
			</form>
			<hr id="hr3">
			<table id="messageTable">
				<tr>
					<th class="msg_th">
						<input type="checkBox" id="allCheck" onclick="allCheck();">
					</th>
					<th class="msg_th" width="12%">받는 사람</th>
					<th class="msg_th" width="70%" style="padding-left: 10px;">제목</th>
					<th class="msg_th" width="25%" style="padding-left: 33px;">날짜</th>
				</tr>				
				<c:forEach items="${viewData.messageList}" var="message">
					<tr>
						<td style="vertical-align: top; padding-top: 7px;">
							<input type="checkbox" value="${message.msgNo}">
						</td>
						<td style="vertical-align: top;">
							<a class="replyModal">${message.receiverName}</a>
							<input type="hidden" class="reply-id" value="${message.receiverID}">
						</td>
						<td style="vertical-align: top;">
							<c:choose>
									<c:when test = "${message.readState == 0}">
										<a class="messageTitle" style="font-weight: normal; color: darkgray;">
											[읽음]${message.msgTitle}
										</a>
									</c:when>
									<c:otherwise>
										<a class="messageTitle">
											${message.msgTitle}
										</a>
									</c:otherwise>
							</c:choose>
							<p class="messageContent">${message.msgContent}</p>
						</td>
						<td style="vertical-align: top;">
							${message.wDate}
						</td>
					</tr>
					<tr>
						<td colspan="4"><hr style="border : 0.5px solid whitesmoke;"></td>
					</tr>
				</c:forEach>				
			</table>
		</div>
		<div id="pagingBox">
			<hr style="margin-bottom:10px;">
			<input type="hidden" id="page" value="${viewData.currentPage}">
			<input type="hidden" id="type" value="${viewData.type}">
			<input type="hidden" id="keyword" value="${viewData.keyword}">
			<table>
				<tr>
					<td colspan="5" id="naviLine"><c:if test="${viewData.startPage !=1 }">
						<a href="messageList_send?page=1
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[처음]</a>
						<a href="messageList_send?page=${viewData.startPage-1}
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[이전]</a>
						</c:if>
						<c:forEach var="pageNum" begin="${viewData.startPage}"
							end="${viewData.endPage < viewData.pageTotalCount ? viewData.endPage : viewData.pageTotalCount}">
						<c:choose>
							<c:when test="${pageNum == viewData.currentPage}">
								<b class="pageNumber" style="color: #fb2525;">${pageNum}</b>
							</c:when>
							<c:otherwise>
								<a href="messageList_send?page=${pageNum}
									<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
									<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								" class="pageNumber">${pageNum}</a>
							</c:otherwise>
						</c:choose>
	
						</c:forEach> <c:if test="${viewData.endPage < viewData.pageTotalCount}">
							<a href="messageList_send?page=${viewData.endPage+1}
								<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
								<c:if test="${viewData.keyword != null}">&se=${viewData.keyword}</c:if>
							">[다음]</a>
							<a href="messageList_send?page=${viewData.pageTotalCount}
								<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
								<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
							">[마지막]</a>
						</c:if>
					</td>
					<td style="width: 100px;"></td>
				</tr>			
			</table>		
		</div>
	</div>
	<div id="replyBox" style="display:none;">
		<div>
			<input type="hidden" id="reply-name">
			<input type="hidden" id="reply-id">
		</div>
		<div>
			<input type="button" id="btn-reply" value="쪽지 보내기" onclick="msgReply();">
			<input type="hidden" id="contextPath" value="${contextPath}">
		</div>
	</div>
</body>
</html>
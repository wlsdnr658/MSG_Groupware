<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/side.css?ver=7">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<!-- <script -->
<!--   src="https://code.jquery.com/jquery-3.3.1.min.js" -->
<!--   integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" -->
<!--   crossorigin="anonymous"> -->
<!--  </script> -->
<script type="text/javascript" src="${contextPath}/js/messenger.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardDeptAuth.js"></script>
<title>receiveMessage(main)</title>
</head>
<body>
	<input type="hidden" value="<%=request.getParameter("pageType")%>" id="pageType">
	<input type="hidden" value="${user.empNo}" id="userID">
	<c:set var="category" value="<%=request.getParameter(\"category\")%>">
	</c:set>
	<c:choose>
		<c:when test="${category eq 'board'}">
			<div id="menuBox1">
			<br>
			<h2 align="center">게시판</h2><br>
			<hr id="hr1"><br>
			<ul id="menuBox1Ul">
			
				<li class="menu"><a href="#">공지사항</a>
				
					<ul class="hide">
		                <li><a href="${contextPath}/board/boardNotice">전체공지</a></li>
		                <li><a href="${contextPath}/board/boardEvent">EVENT</a></li>
		            </ul>
		            
		        </li><br>
		            
		            
				<li class="menu"><a href="#">부서 게시판</a>
					
					<ul class="hide">
		                <li><a onclick="check1()">IT부</a></li>
		                <li><a onclick="check2()">영업부</a></li>
		                <li><a onclick="check3()">재무부</a></li>
		                <li><a onclick="check4()">경영기획부</a></li>
		            </ul>
					
				</li><br>
				
				<li class="menu"><a href="#">프로젝트 게시판</a>
				
					<ul class="hide">
		                <li><a href="${contextPath}/board/boardProjectOngoing">진행중 프로젝트</a></li>
		                <li><a href="${contextPath}/board/boardProjectFinished">완료된 프로젝트</a></li>
		            </ul>
		            
				</li><br>
				
				<li class="menu"><a href="${contextPath}/board/boardAnonymity">익명 게시판</a>
				
				</li><br>
				
			</ul>
			<hr id="hr2">
			</div>			
		</c:when>
		
		<c:when test="${category eq 'mail'}">
			<div id="menuBox1">
			<br>
			<h2 align="center">전자메일</h2>
			<br><hr id="hr1"><br>
			
			<ul id="menuBox1Ul">
				<li><a href="${contextPath}/mail/mail_receiveInbox_form">받은편지함</a></li><br>		
				<li><a href="${contextPath}/mail/mail_sendInbox_form">보낸편지함</a></li><br>
				<li><a href="${contextPath}/mail/mail_myWriteInbox_form">내게쓴메일함</a></li><br>
				<li><a href="${contextPath}/mail/mail_draftsInbox_form">임시보관함</a></li><br>
				<li><a href="${contextPath}/mail/mail_Write_form">메일쓰기</a></li><br>
				<li><a href="${contextPath}/mail/mail_myWrite_form">내게쓰기</a></li><br>
				<li><a href="${contextPath}/mail/mail_deleteInbox_form">지운편지함</a></li><br>
			</ul>
				<hr id="hr2">
			
			</div>			
		</c:when>
		
		<c:when test="${category eq 'approv'}">
			<div id="menuBox1">		
				<br>
				<h2 align="center">전자결재</h2>
				<br>
				<hr id="hr1">
				<br>
				<ul id="menuBoxUI" style="padding-left: 30px;">
					<li><a href="${contextPath}/approval/approvalStore">결재함</a></li>
					<br>
					<li><a href="${contextPath}/approval/approvRequire?empNo=${user.empNo}">결재 요청함</a></li>
					<br>
					<li><a href="${contextPath}/approval/approvalWriteMain">결재문서
							작성</a></li>
					<br>
				</ul>
				<hr id="hr2">
			</div>	
		</c:when>
		
		<c:when test="${category eq 'emp'}">
			<div id = "menuBox1">
		         <br>
		         <h2 align = "center">관리자</h2>
		         <br><hr id = "hr1"><br>
		         <ul id = "menuBoxUI" style = "padding-left : 30px;">
<!-- 		            <li><a href = "#">공지사항</a></li><br> -->
		            <li>
		               <a href = "javascript:showDetail();" id = "mngTitle">사원관리</a>
		               <ul id = "mngDetail">
		                  <li><a href = "${contextPath}/emp/empListPage">사원목록</a></li>
		                  <li><a href = "${contextPath}/emp/empChartForm">사원 등록</a></li>
		                  <li><a href = "${contextPath}/emp/empChartForm">조직도 관리</a></li>
		               </ul>
		            </li>
		            <br>
		         </ul>
				<hr id="hr2">
	     	</div>
		</c:when>
		
		<c:when test="${category eq 'messenger'}">
			<div id="menuBox1">
				<br>
				<h2 align = "center">메신저</h2>
				<br><hr id = "hr1"><br>
				<ul id="menuBox1Ul">
					<li id="sideList_msg">
						<a class="side_head">쪽지</a>
						<ul id="msg_sub" class="sideBox_sub">
							<li><a class="sideList" href="${contextPath}/messenger/messageWriteForm">쪽지 쓰기</a></li>
							<li><a class="sideList" href="${contextPath}/messenger/messageList">받은 쪽지함</a><span id="msgReadCount"></span></li>
							<li><a class="sideList" href="${contextPath}/messenger/messageList_send">보낸 쪽지함</a></li>
						</ul>
					</li>
					<li><br></li>
					<li id="sideList_chat">
						<a class="side_head">채팅</a>
						<ul id="chat_sub" class="sideBox_sub">
							<li><a class="sideList" href="${contextPath}/messenger/chatList"> 내 채팅방 목록</a></li>
							<li><a class="sideList" href="${contextPath}/messenger/chatInvite">채팅 초대 목록</a></li>
							<li><a class="sideList" onclick="chatModal_open();">채팅방 만들기</a></li>
						</ul>
		            </li>
		            <br>
		         </ul>
		         <hr id="hr2" style="position: absolute; width:100%; top:250px;">
			</div>
			<div id="chatRoomAdd_modal">
				<a>채팅 방 만들기</a>
				<hr>
				<br>
				<form action="addChatRoom" method="post">
					<span style="font-size: 12px;">
						제목 <input type="text" name="title" id="chat-title" maxlength="30">
					</span>
					<br>
					<br>
					<input type="submit" value="만들기" style="margin-left: 50px;">
					<input type="button" value="취소" onclick="chatModal_close();">
				</form>
			</div>
			<div id="mask2"></div>			
		</c:when>
		
		<c:when test="${category eq 'management'}">
			<div id="menuBox1">
				<br>
				<p align="center" style="font-size: 25px;margin: 0 auto;font-weight: bold;">자원관리</p>
				<br>
				<hr id="hr1">
				<br>
				<ul id="menuBox1Ul">
					<li class="menu"><a
						href="${contextPath }/management/managementcalendar">캘린더</a>
				</ul>
				<br>
				<ul id="menuBox1Ul">
					<li class="menu"><a
						href="${contextPath }/management/managementRoom">회의실</a></li>
	
				</ul>
				<br>
				<ul id="menuBox1Ul">
					<li class="menu"><a
						href="${contextPath}/management/managementCar">차량</a> <img
						id="profile_Img">
				</ul>
				<br>
				<c:if test ="${user.empPos eq '부장' || user.empPos eq '과장'}" >
		          <ul id="menuBox1Ul">
		             <li class="menu"><a
		                href="${contextPath}/management/managementitemcreate">관리자</a> <img
		                id="profile_Img">
		          </ul>
		       </c:if>
				<hr id="hr2">
			</div>			
		</c:when>
		
		<c:when test="${category eq 'management2'}">
			<div id="menuBox1">
				<br>
				<h2 align="center">자원관리</h2>
				<br>
				<hr id="hr1">
				<br>
				<ul id="menuBox1Ul">
					<li class="menu"><a
						href="${contextPath }/management/managementcalendar">캘린더</a>
				</ul>
				<br>
				<ul id="menuBox1Ul">
					<li class="menu"><a
						href="${contextPath }/management/managementRoom">회의실</a></li>
	
				</ul>
				<br>
				<ul id="menuBox1Ul">
					<li class="menu"><a
						href="${contextPath}/management/managementCar">차량</a> <img
						id="profile_Img">
				</ul>
				<br>
		       <c:if test ="${user.empPos eq '부장' || user.empPos eq '과장'}" >
		          <ul id="menuBox1Ul">
		             <li class="menu"><a
		                href="${contextPath}/management/managementitemcreate">관리자</a> <img
		                id="profile_Img">
		          </ul>
		       </c:if>
		       <hr id="hr2">
      		</div>		
		</c:when>
	</c:choose>

</body>
</html>
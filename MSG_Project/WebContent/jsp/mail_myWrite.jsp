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


<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/mail_write.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/main.css">	  		
<link
	href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css"
	rel="stylesheet">
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css"
	rel="stylesheet">


<script
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>

<script
	src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>
<script type="text/javascript" src="${contextPath}/lang/summernote-ko-KR.js"></script>
<script type="text/javascript" src="${contextPath}/js/ko_kr_true.js"></script>
<script type="text/javascript" src= "${contextPath}/js/sockjs.js"></script>
<script type="text/javascript" src= "${contextPath}/js/stomp.js"></script>
<script type="text/javascript" src = "${contextPath}/js/header.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>E-Mail 내게쓰기</title>
</head>
<script type="text/javascript" src = "${contextPath}/js/draftMyMail.js"></script>

<body>
	 <input type="hidden" id="path" value="${contextPath}">
	<input type="hidden" id="empNo" value="${user.empNo}">
	<input type = "hidden" id = "user_auth" value = "${user.deptName}"> 
	<header id="main_header">
		<hgroup id="title">
			<img src="${contextPath}/img/msgLogo.png" onclick="location.href='${contextPath}/page/mainForm'" width = "152px" height="80px" style="padding-left:12px;">
		</hgroup> 
		
		<nav id="main_lnb">
			<ul id="ul1">
				<li><a href = "${contextPath}/board/boardMainForm">게시판</a></li>
				<li><a href = "${contextPath}/mail/mail_main_form">메일</a></li>
				<li id = "approvNavi"><a href = "${contextPath}/approval/approvalMainForm">전자결재</a></li>
				<li>
					<img src="${contextPath}/img/new.png" id="new_messenger">
					<a href = "${contextPath}/messenger/messageList">메신저</a>
				</li>
				<li><a href = "${contextPath}/management/managementcalendar">자원관리</a></li>
				
				<c:if test="${user.deptNo == 10}">
				<li><a href = "${contextPath}/emp/empManageForm">사원관리</a></li>
				</c:if>
				
				<li id = "weather">
					<div id="imgBox">
						<h4 id="place" style="display: inline-block;"></h4>
						<h4 id="temp" style="display: inline-block;"></h4>
					</div>
				</li>
				
			</ul>
		</nav> 
	</header>
	
	<div id="message_alert" style="display: none;">
		<div id="message_alert_div">
			<div class="alertDiv_header">
				쪽지
			</div>
			<div class="alertDiv_body">
				<a href="${contextPath}/messenger/messageList">
					새로운 쪽지가 <br>
					도착했습니다.
				</a>
			</div>
		</div>
	</div>
	
	<div id="approve_alert" style="display: none;">
		<div id="approve_alert_div">
			<div class="alertDiv_header">
				결재
			</div>
			<div class="alertDiv_body">
				<a href="${contextPath}/approval/approvalStore">
					새로운 결재가 <br>
					도착했습니다.
				</a>
			</div>
		</div>
	</div>
	
	<div id="mail_alert" style="display: none;">
		<div id="mail_alert_div">
			<div class="alertDiv_header">
				메일
			</div>
			<div class="alertDiv_body">
				<a href="${contextPath}/mail/mail_receiveInbox_form">
					새로운 메일이 <br>
					도착했습니다.
				</a>
			</div>
		</div>
	</div>	
	<!-------------------------------------헤더--------------------------------------------------------->
	<div id="content">
		<input type="hidden" value="${contextPath}" id="context">
		<div id="menuBox1">
			<br>
			<b style="font-size: 24px; margin-left: 52px;">전자메일</b>
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
		<div id="menuBox2">
			<p style="margin: 20px">
				<a href="mail/mail_myWrite_form" style="font-size: 20px !important;">내게메일 쓰기</a>
			</p>
			<hr id="hr3">
			<br>
			<div id="button_send" style="float: left" >
			 <form action="mail_myWrite" method="post" enctype="multipart/form-data" id="excelForm">
			 <input type = "hidden" name = "senderId" value = "${user.empNo}" id="senderId">
				<input type="submit" value="저장"  style="background-color: white !important;
    												border: 1px solid gray !important;
    												color: black !important;
    												padding: 5px 5px !important">
				<input type="button" name="임시저장" value="임시저장" id="draftsInbox_click"><a href="${contextPath}/mail/mail_Write_form"><img
					src="${contextPath}/img/recycle.png"><small
					style="color: #fb2525;">메일쓰기</small></a><br>
						<hr id="hr3_3">	
	                 <span id="spansend">첨부파일</span>
	                     &nbsp;&nbsp;<input multiple="multiple"type="file" name="fileName" value="내PC" id="file">
	                  <hr id="hr3_3">	
	                  <label id="label">제목</label>&nbsp;<input id="mailtitle"type="text" name="title" class="title">
				<br>
				<br>
				<textarea id="summernote" rows="30" cols="111" name="content" class="content"></textarea>
				<br> <br>
				</form>
			</div>
		</div>

	</div>

	<!-------------------------------------바디---------------------------------------------------------------------------->
	
</body>
</html>
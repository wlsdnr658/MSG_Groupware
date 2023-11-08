<%@page import="java.util.Random"%>
<%@page import="java.util.Map"%>
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

<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 

<!-- include summernote css/js -->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>

<link rel="stylesheet" type="text/css" href="${contextPath}/css/board.css">
<script type="text/javascript" src="${contextPath}/lang/summernote-ko-KR.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardFileBox.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardSummerNote.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardSubMenu.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardDeptAuth.js"></script>

<script type="text/javascript" src = "${contextPath}/js/header.js"></script>
<script type="text/javascript" src= "${contextPath}/js/sockjs.js"></script>
<script type="text/javascript" src= "${contextPath}/js/stomp.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/header.css">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<script type="text/javascript">
	function checkTag(){
		
		var a = $("#TYPE option:selected").val();
		
		if(a == 'null'){
			alert("분류를 선택하세요.")
			return false;
		}
	};
</script>
<style type="text/css">
.filebox input[type="file"] {
	position: absolute;
	width: 1px;
	height: 1px;
	padding: 0;
	margin: -1px;
	overflow: hidden;
	clip: rect(0, 0, 0, 0);
	border: 0;
}

.filebox label {
	display: inline-block;
	padding: .5em .75em;
	color: silver;
	font-size: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #fdfdfd;
	cursor: pointer;
	border: 1px solid #ebebeb;
	border-bottom-color: #e2e2e2;
	border-radius: .25em;
} /* named upload */
.filebox .upload-name {
	display: inline-block;
	padding: .5em .75em; /* label의 패딩값과 일치 */
	font-size: inherit;
	font-family: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #f5f5f5;
	border: 1px solid #ebebeb;
	border-bottom-color: #e2e2e2;
	border-radius: .25em;
	-webkit-appearance: none; /* 네이티브 외형 감추기 */
	-moz-appearance: none;
	appearance: none;
}
</style>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>공지 작성</title>
</head>
<body>

	<input type="hidden" id="path" value="${contextPath}">
	<input type="hidden" id="empNo" value="${user.empNo}">
	<input type = "hidden" id = "user_auth" value = "${user.deptName}"> 
	<header id="main_header" style="height:82px">
		<hgroup id="title">
			<img src="${contextPath}/img/msgLogo.png" style="width: 152px;height: 80px;padding-left: 12px;" onclick="location.href='${contextPath}/page/mainForm'" height="50px">
		</hgroup> 
		
		<nav id="main_lnb" style="margin-top:6px">
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
				
				<li>
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
		<input type = "hidden" id = "path" value = "${contextPath}">
		<div id="content">
		 
			
			<div id="menuBox1">
			<br>
			<span><b style="font-size: 24px; margin-left: 65px;">게시판</b></span><br>
			<hr id="hr1" style="margin-bottom: 0"><br>
			
			<ul id="menuBox1Ul" >

				<li id="menu"><a href="#">공지사항</a>

					<ul id="hide">
		                <li><a href="${contextPath}/board/boardNotice">전체공지</a></li>
		                <li><a href="${contextPath}/board/boardEvent">EVENT</a></li>
		            </ul>

		        </li><br>

				<li id="menu"><a href="#">부서 게시판</a>

					<ul id="hide">
		                <li><a onclick="check1()">IT부</a></li>
		                <li><a onclick="check2()">영업부</a></li>
		                <li><a onclick="check3()">재무부</a></li>
		                <li><a onclick="check4()">경영기획부</a></li>
		            </ul>

				</li><br>

				<li id="menu"><a href="#">프로젝트 게시판</a>

					<ul id="hide">
		                <li><a href="${contextPath}/board/boardProjectOngoing">진행중 프로젝트</a></li>
		                <li><a href="${contextPath}/board/boardProjectFinished">완료된 프로젝트</a></li>
		            </ul>

				</li><br>

				<li id="menu"><a href="${contextPath}/board/boardAnonymity">익명 게시판</a>
				</li><br>  
			</ul>

			<hr id="hr2" style="margin-top: 125px">

			</div>
			
			<input type="hidden" value="${user.deptNo}" id="inputDeptNo">
			<input type="hidden" value="${user.empPos}" id="inputEmpPos">

			<div id="menuBox2">
			
				<p style="margin: 20px">
					<b style="font-size: 20px; display: inline-block; text-decoration: none;">익명 게시글 작성</b>
				</p>
				<hr id="hr3">
					<b onclick="location.href='boardAnonymity'" style="font-size: 18px;cursor: pointer;">익명 게시판</b>
					
					<div id="writeBox">
					
						<form name="frm" action="boardAnonymityWrite" id="writeForm" enctype="multipart/form-data" method="post" onsubmit="return checkTag();">
							
							<table id="writeTable">
								
								<tr id="wTtr1">
									<th style="border-bottom: 0.7px solid silver; ">제목</th>
									<td colspan="5" style="border-bottom: 0.7px solid silver; text-align: left">
										<input type="text" placeholder="제목을 입력하세요." name="TITLE" style="width:100%">
									</td>
								</tr>
								
								<%String randomId = "";
									for(int i=0;i<4;i++){
										randomId += (char)((Math.random()*26)+97);
									}
								%>
								
								<tr id="wTtr2">
									<th style="border-bottom: 0.7px solid silver;">작성자</th>
									<td style="border-bottom: 0.7px solid silver; text-align: left; width: 120px;">
										
										<c:if test="${user.empPos eq '사원' || user.empPos eq '과장' || user.empPos eq '대리'}">
											<input type="text" readonly="readonly" name="WRITER" value="<%="익명"+randomId%>" style="width: 120px;">
										</c:if>
										
										<c:if test="${user.empPos eq '부장'}">
											<input type="text" readonly="readonly" name="WRITER" value="${user.empName}" style="width: 120px;">		
										</c:if>
									
									</td>
									<th style="border-bottom: 0.7px solid silver; width: 80px;">비밀번호</th>
									<td style="border-bottom: 0.7px solid silver; text-align: left">
										<input type="password" name="BOARDPW" placeholder="비밀번호를 입력하세요(필수)" style="width: 200px;">
									</td>
									<th style="border-bottom: 0.7px solid silver; width: 80px;">분류</th>
									<td style="border-bottom: 0.7px solid silver; text-align: left">
										<select name="TYPE" id="TYPE" style="width: 100px;">
											
											<option id="option1" value="null" selected>선택</option>
											<c:if test="${user.empPos eq '부장'}">
												<option id="option1" value="1">공지</option>
											</c:if>
											<c:if test="${user.empPos eq '사원' || user.empPos eq '과장' || user.empPos eq '대리'}">
												<option id="option1" value="2">일반</option>
											</c:if>
										
										</select>
									</td>
								</tr>
								
								<tr id="wTtr3">
									<th style="border-bottom: 0.7px solid silver">내용</th>
									<td style="border-bottom: 0.7px solid silver; text-align: left;" colspan="5">
										<textarea name="CONTENT" class="summernote"></textarea>
									</td>
								</tr>
								<tr id="wTtr4">
									<th style="border-bottom: 0.7px solid silver; width: 80px;">첨부파일</th>
									<td colspan="5" style="border-bottom: 0.7px solid silver; text-align: left">
									
										 <div class="filebox">
											<input class="upload-name" value="파일선택" disabled="disabled">
											<label for="ex_filename">업로드</label>
											<input multiple="multiple" type="file" name="uploadFile" id="ex_filename" class="upload-hidden"/>
										</div>

									</td>
								</tr>
								
								<tr>
									<th colspan="6" style="text-align: center;">
										<input id="submitBtn" type="submit" value="등록">
										<input id="resetBtn" type="button" onclick="location.href='boardAnonymity'" value="취소">
										
									</th>
								</tr>
								
							</table>
						</form>
					</div>
				
				<br><br>
<!-- 				<hr id="hr4"> -->
			</div>
		
		</div>  
		<!-------------------------------------바디--------------------------------------------------------->

</body>
</html>


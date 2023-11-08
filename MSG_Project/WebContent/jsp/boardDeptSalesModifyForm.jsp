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

<!-- include summernote css/js-->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>

<link rel="stylesheet" type="text/css" href="${contextPath}/css/board.css">

<script type="text/javascript" src="${contextPath}/js/boardFileBox.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardSummerNote.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardSubMenu.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardDeptAuth.js"></script>

<script type="text/javascript" src= "${contextPath}/js/sockjs.js"></script>
<script type="text/javascript" src= "${contextPath}/js/stomp.js"></script>
<script type="text/javascript" src = "${contextPath}/js/header.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/header.css?ver=7">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>부서 게시글 수정</title>
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
		<!-------------------------------------헤더--------------------------------------------------------->
		<input type = "hidden" id = "path" value = "${contextPath}">
		<div id="content">
		
			
			<div id="menuBox1">
			<br>
			<span><b style="font-size: 24px; margin-left: 65px;">게시판</b></span><br>
			<hr id="hr1" style="margin-bottom: 0"><br>
			<ul id="menuBox1Ul">

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
					<a href="#" style="font-size: 20px; display: inline-block; text-decoration: none;">영업부서 게시판</a>
				</p>
				
				<hr id="hr3" style="margin-bottom: 10px;">
					<span><b style="font-size: 18px;">게시글 수정</b></span><br>
					
						<div id="writeBox">
					
						<form action="boardDeptSalesModify" method="post" enctype="multipart/form-data">
						
							<input type="hidden" name="boardNo" value="${sales.boardNo}">
							
							<table id="writeTable">
								
								<tr id="wTtr1">
									<th style="border-bottom: 0.7px solid silver;">제목</th>
									<td style="border-bottom: 0.7px solid silver; text-align: left">
										<input type="text" placeholder="제목을 입력하세요." value="${sales.title}" name="TITLE" style="width:100%">
									</td>
								</tr>
								
								<tr id="wTtr2">
									<th style="border-bottom: 0.7px solid silver">작성자</th>
									<td style="border-bottom: 0.7px solid silver; text-align: left">
										<input type="text" placeholder="작성자 이름을 입력하세요." name="EMPNAME" readonly="readonly" value="${sales.empName}" style="width: 200px;">
									</td>
								</tr>
								
								<tr id="wTtr3">
									<th style="border-bottom: 0.7px solid silver">내용</th>
									<td style="border-bottom: 0.7px solid silver; text-align: left;">
										<textarea name="CONTENT" class="summernote">${sales.content}</textarea>
									</td>
								</tr>
								<tr id="wTtr4">
									<th style="border-bottom: 0.7px solid silver; width: 80px;">첨부파일</th>
									<td style="border-bottom: 0.7px solid silver; text-align: left">
										 
										  <input multiple="multiple" type="file" name="uploadFile" id="ex_filename" class="upload-hidden"/>

									</td>
								</tr>
								
								<tr>
									<th colspan="3" style="text-align: center;">
										<button id="submitBtn" type="submit">수정하기</button>
										<button id="resetBtn" onclick="location.href='boardDeptItViewForm?boardNo=${sales.boardNo}'" type="button">취소</button>
									</th>
								</tr>
								
							</table>
						</form>
					</div>
					
				<br><br>
				<hr id="hr4">
			</div>
		
		</div>
		<!-------------------------------------바디--------------------------------------------------------->
		<!-------------------------------------푸터--------------------------------------------------------->

</body>
</html>


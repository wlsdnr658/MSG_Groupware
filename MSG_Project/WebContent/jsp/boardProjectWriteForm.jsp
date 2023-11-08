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
<script type="text/javascript" src="${contextPath}/lang/summernote-ko-KR.js"></script>

<link rel="stylesheet" type="text/css" href="${contextPath}/css/board.css">
<script type="text/javascript" src="${contextPath}/js/boardFileBox.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardSubMenu.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardSummerNote.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardDeptAuth.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardCreateLine.js"></script>

<script type="text/javascript" src= "${contextPath}/js/sockjs.js"></script>
<script type="text/javascript" src= "${contextPath}/js/stomp.js"></script>
<script type="text/javascript" src = "${contextPath}/js/header.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/header.css?ver=7">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
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
<title>프로젝트 작성</title>
</head>
<body>
<div id="mask" ></div>

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
		<div id="content" style="height: 850px">
			
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
					<a href="${contextPath}/board/boardProject" style="font-size: 20px; display: inline-block; text-decoration: none;">프로젝트 게시판</a>
				</p>
				<hr id="hr3" style="margin-bottom: 10px;">
					<span><b style="font-size: 18px;">프로젝트 작성</b></span><br>
					
					<div id="writeBox">
						<form name="frm" action="boardProjectWrite" id="writeForm" enctype="multipart/form-data" method="post">
							<fieldset style="margin-top: 10px; border-style: none;">
							
								<table id="writeTable" style="margin-top: 0">
								
									<tr id="wTtr1">
										<th style="border-bottom: 0.7px solid silver; ">프로젝트명</th>
										
										<td colspan="6" style="border-bottom: 0.7px solid silver; text-align: left;">
											<input type="text" style="width: 500px" name="PROJECTNAME" placeholder="제목을 입력하세요." >
										</td>
									</tr>
							
									<tr id="wTtr23">
										<th scope="row" style="border-bottom: 0.7px solid silver">작성자</th>
										<td colspan="2" style="border-bottom: 0.7px solid silver; text-align: left;">
											<input type="text" placeholder="작성자 이름을 입력하세요." name="EMPNAME" readonly="readonly" value="${user.empName}" style="font-size: 13px;">
											<input type="hidden" name="WRITER" value="${user.empNo}">
										</td>
										
										<th style="border-bottom: 0.7px solid silver; width: 70px;">담당자</th>
										<td colspan="2" style="border-bottom: 0.7px solid silver; text-align: left; width:255px;">
											<input type="text" placeholder="이름을 입력하세요." name="INCHARGE" readonly="readonly" value="${user.empName}" style="font-size: 13px; ">
											
										</td>
									</tr>
									
									<tr>
										<th style="border-bottom: 1px solid silver">참여자</th>
										<td id="participantTd" colspan="6" id="participant" style="border-bottom: 1px solid silver">
											<div style="text-align: left;">
												
												<span style="float: right; margin-right: 80px"><input id="lineBtn" type="button" onclick = "createLine()" value="조직도"></span>
												
												<table id="approvLine"></table>
												
												<div id = "chart_div" style = "display : none; width: 500px; height: 450px;">
												     <div id = "chart" align = "center">
												         <ul style = "display : inline-block">
												            <li>
												               <img src = "${contextPath}/img/dir.png"><a onclick = "showChart('m','mChart')">경영/기획부</a>
												               <ul id = "mChart"></ul>
												            </li>
												            <li>
												               <img src = "${contextPath}/img/dir.png"><a onclick = "showChart('b','bChart')">영업부</a>
												               <ul id = "bChart"></ul>
												            </li>
												            <li>
												               <img src = "${contextPath}/img/dir.png"><a onclick = "showChart('i','iChart')">IT부</a>
												               <ul id = "iChart"></ul>
												            </li>
												            <li>
												               <img src = "${contextPath}/img/dir.png"><a onclick = "showChart('f','fChart')">재무부</a>
												               <ul id = "fChart"></ul>
												            </li>
												         </ul>
												      </div>
												      
												      <img src = "${contextPath}/img/right.JPG" id = "rightPointer" onclick = "moveToRight();">
												      <img src = "${contextPath}/img/left.JPG" id = "leftPointer" onclick = "moveToLeft();">
												      
											      <div id = "selected" align = "center">
											         <ul id = "emps">
											         </ul>
											      </div>
												      
											  	  <input type = "button" id = "closeBtn1" value = "닫기" onclick = "closeToLine();">
											  	  <input type = "button" id = "chartBtn" value = "완료" onclick = "makeToLine();">
											    
											   </div>	
											
											</div>	
										</td>
									</tr>
									
									<tr>
										
										<th style="width: 100px; border-bottom: 1px solid silver;">프로젝트 기간</th>
										
										<th colspan="1" style="border-bottom: 1px solid silver; width: 80px;">시작일</th>
										<td style="border-bottom: 1px solid silver;" align="left">
											<input class="aa" type="date" name="PSTARTDATE">
										</td>
									
										<th colspan="1" style="border-bottom: 1px solid silver;">마감일</th>
										<td style="border-bottom: 1px solid silver;" align="left">
											<input class="aa" type="date" name="PENDDATE">
										</td>
										
									<tr>
									
									
									<tr id="wTtr3">
										<th style="border-bottom: 0.7px solid silver">내용</th>
										<td colspan="6" style="border-bottom: 0.7px solid silver; text-align: left">
											<textarea name="CONTENT" class="summernote"></textarea>
										</td>
									</tr>
									
									<tr id="wTtr3">
										<th style="border-bottom: 0.7px solid silver">첨부파일</th>
										<td colspan="6" style="border-bottom: 0.7px solid silver; text-align: left">
											 
											<div class="filebox">
												<input class="upload-name" value="파일선택" disabled="disabled">
												<label for="ex_filename">업로드</label>
												<input multiple="multiple" type="file" name="uploadFile" id="ex_filename" class="upload-hidden"/>
											</div>
							
										</td>
									</tr>
									
									<tr>
										<th colspan="5" style="text-align: center;">
											
											<input id="submitBtn" type="submit" value="등록">
											<button id="resetBtn" onclick="location.href='boardProjectOngoing'" type="button">취소</button>
											
										</th>
									</tr>
								
								</table>
								
							</fieldset>
						</form>
					</div>
					
			</div>
		
		</div>
		<!-------------------------------------바디--------------------------------------------------------->
		<!-------------------------------------푸터--------------------------------------------------------->

</body>
</html>


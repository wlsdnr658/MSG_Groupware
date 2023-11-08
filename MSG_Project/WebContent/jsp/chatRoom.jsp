<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/chat.css?ver=7">
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
<script type="text/javascript" src= "${contextPath}/js/sockjs.js"></script>
<script type="text/javascript" src= "${contextPath}/js/stomp.js"></script>
<script type="text/javascript" src= "${contextPath}/js/chatRoom.js?ver=2"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>${roomTitle}</title>
</head>
<body>
<div id="mask"></div>
<input type="hidden" id="contextPath" value="${contextPath}">
<input type="hidden" id="pageType" value="chat">
	<div id="topDiv">
		<p id="room-title">${roomTitle}</p><span id="num-emp">(${empNum})</span>
		<button id="btn-chatRoomMenu">
			<img alt="목록" src="${contextPath}/img/chatMenuList.png">
		</button>
	</div>
	<div id="chat-wrap">
		<div id="chatField">
				<span class='notice'>
					공지사항
	    		  	<span class='noticeContent' style="background-color: white;">
	    		  		<c:choose>
		    		  		<c:when test="${empty roomNotice}">
		    		  			공지사항이 없습니다.
		    		  		</c:when>
							<c:otherwise>
		    		  			${roomNotice}
							</c:otherwise>	    		  		
	    		  		</c:choose>
	    		  	</span>
				</span>
	    		<hr style="margin: 0px;">
			<div class="chatDiv" style="height: 323px; overflow-y:auto; width: 97.5%;">
				<c:forEach items="${chatList}" var="list">
					<c:choose>
						<c:when test="${list.empName == user.empName}">
							<c:choose>
								<c:when test="${list.fileName != null}">
									<span class='chatSpan' style="text-align: right;">
										<span class='fileDiv'>
												파일명 : ${list.originFileName} <br>
												용량 : ${list.fileSize} <br>
											<button class='fileDownBtn' onclick="location.href='${contextPath}/messenger/chatDownload?fileName=${list.fileName}'">다운로드</button>
										</span>
						    		  	<span class='chatTime'>
							    		 	${list.wDate}
							    		</span>
						    		</span>
									<br>
								</c:when>
								<c:otherwise>
									<span class='chatSpan_A' style="text-align: right;">
										<span class='chatSpan_B' style="text-align: left;">
							    		  	<span class='chatContent' style="background-color: lightyellow; max-width: 180px;">
							    		  		<span style="text-align: left; display: inline-block;">
							    		  			${list.content}
							    		  		</span>
							    		  	</span>
							    		  	<span class='chatTime'>
							    		 		${list.wDate}
							    		  	</span>
										</span>
									</span>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${list.fileName != null}">
									<p class='fileP' style="text-align: left; margin-top: 5px; margin-bottom: 5px;">${list.content}</p>
									<span class='chatSpan' style="text-align: left;">
										<span class='fileDiv'>
											파일명 : ${list.originFileName} <br>
											용량 : ${list.fileSize} <br>
											<button class='fileDownBtn' onclick="location.href='${contextPath}/messenger/chatDownload?fileName=${list.fileName}'">다운로드</button>
										</span>
						    		  	<span class='chatTime'>
							    		 	${list.wDate}
							    		</span>
						    		</span>
									<br>
								</c:when>
								<c:otherwise>
									<span class='chatSpan_A' style="text-align: left;">
										<img class="chat_profile" alt="${list.empName}" src="${contextPath}/emp/getEmpImage?loginEmp=${list.empNo}">
										<span class='chatSpan_B' style="text-align: left;">
								        	<a class='chatSendName'>
							    		  		${list.empName}
							    		  	</a>
							    		  	<br>
							    		  	<span class='chatContent' style="max-width: 180px;">
							    		  		<span style="text-align: left; display: inline-block;">
							    		  			${list.content}
							    		  		</span>
							    		  	</span>
							    		  	<span class='chatTime'>
							    		 		${list.wDate}
							    		  	</span>
										</span>
									</span>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:forEach>
		   </div>
		</div>
	</div>
<!-- 		가능하다면 드래그앤 드롭..		 -->
<!-- 		 					첨부 파일 내용 나오게 하는 곳. -->
<!-- 		<input type="text" id="fileName" class="file_input_textbox" readonly> -->
<!-- 		<div class="file_input_div"> -->
<%-- 		    <img src="${contextPath}/img/fileClip.png" class="file_input_img_btn" alt="open"/> --%>
<!-- 			<p style="display:inline-block;">파일첨부</p> -->
<!-- 		    <input type="file" name="file_1" class="file_input_hidden" -->
<!-- 		    onchange="javascript: document.getElementById('fileName').value = this.value"/> -->
<!-- 		</div> -->
		
		<div id="sendField">
			<form method="post">
				<textarea id="textBox" style="resize: none;"></textarea>
				<input type="hidden" value="${user.empNo}" id="userID">
				<input type="hidden" value="${user.empName}" id="empName">
				<input type="hidden" value="${roomNo}" id="roomNo">
				<span id="buttonSet">
					<input type="button" value="전송" id="btn_send">
					<img src="${contextPath}/img/fileClip.png" id="file_input_btn" alt="open"/>
				</span>	
			</form>
		</div>
	<div id="chatRoomMenu">
		<button id="chat-List">참여자 목록</button>	
		<button id="chat-invite">초대하기</button>
		<button id="chat-notice">공지 수정하기</button>
		<button id="menu-exit">닫기</button>
	</div>
	
	<!-- 참여자 목록 modal -->
	<div id="chatEmpList" style="display:none;">
	</div>
	<!-- 공지 수정 -->
	<div id="chatNotice">
		<input type="text" id="roomNotice" maxlength="24">
		<br>
		<button id="chat-noticeUpdate">수정하기</button>
		<button id="chat-noticeCancle">취소</button>
	</div>
	<!-- 초대하기 -->
	<div id="chatInvite" style="display: none;">
		<select id="inviteOption">
			<option value="전체">전체</option>
			<option value="경영/기획부">경영/기획부</option>
			<option value="영업부">영업부</option>
			<option value="재무부">재무부</option>
			<option value="IT부">IT부</option>
		</select>
		<input type="text" placeholder="이름 검색" id="inviteEmp_search">
		<table id="inviteTable">
			<tr>
				<th>부서</th>
				<th>직급</th>
				<th>이름</th>
			</tr>
		</table>
		<button id="invite_exit" style="border-bottom: 0.5px solid gray; margin-top: 10px;">닫기</button>
	</div>
	<input type="file" name="file_1" id="file_btn" style="display:none;">
	
</body>
</html>

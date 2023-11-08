<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<%-- <%request.setAttribute("contextPath", request.getContextPath());%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${contextPath}/fullcalendar/fullcalendar.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/fullcalendar/fullcalendar.min.css">

<link rel="stylesheet" type="text/css" href="${contextPath}/fullcalendar/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/fullcalendar/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/fullcalendar/theme.css">


<script src='${contextPath}/fullcalendar/moment.min.js'></script>
<script src='${contextPath}/fullcalendar/jquery.min.js'></script>
<script src='${contextPath}/fullcalendar/fullcalendar.js'></script>
<script src='${contextPath}/fullcalendar/ko.js'></script>

<link rel="stylesheet" type="text/css" href="${contextPath}/css/main.css?ver=8">
<script type="text/javascript" src = "${contextPath}/js/main.js?ver=4"></script>
<script type="text/javascript" src = "${contextPath}/js/calendar.js"></script>
<script type="text/javascript" src = "${contextPath}/js/addMemo.js"></script>
<style type="text/css">
#closeMemo:hover {
	background-color: #f3f4f9;
	color: #fb2525;
}

#submitMemo:hover {
	background-color: #f3f4f9;
	color: #fb2525;
}

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
	color: #999;
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

<title>메인</title>
</head>
<body>
	<jsp:include page="/jsp/pageHeader.jsp"></jsp:include>
	<!-------------------------------------헤더--------------------------------------------------------->
	<div id="mask" ></div>
	
	<input type="hidden" id="path" value="${contextPath}">
	<input type="hidden" id="empNo" value="${user.empNo}">
	
	<div>
	<div id="content" style="z-index:1;">
		<!-- 좌측 박스 -->
		<div id="left_box">
			<div id="profile_box">
				<div id="modifyBox" style="display: none;" align="center">
					<input type="button" id="modiBtn" value="X" style="float: right;" onclick="closeMyPage();">
					<form action="${contextPath}/emp/userModify" enctype="multipart/form-data" method="post">
						<table id="modiTable">
							<tr>
								<td colspan="2">
									<img src="${contextPath}/emp/getEmpImage?loginEmp=${user.empNo}" id="profile_Img" style="width: 90px; height: 90px; border: 1px solid silver; border-radius: 50%">
								</td>
							</tr>
							<tr>
								<td colspan="2">
								
									<div class="filebox">
										<input class="upload-name" value="파일선택" disabled="disabled">
										<label for="ex_filename">업로드</label>
										<input type="file" name="file" id="ex_filename" class="upload-hidden"> 
									</div>
									
<!-- 									<input type="file" name="file" id="fileLoad">  -->
									
								</td>
							</tr>
							<tr>
								<th>이메일</th>
								<td>
									<input type="text" id = "EMPMAIL" name="EMPMAIL" class="modiInput" value="${user.empMail}">
								</td>
							</tr>
							<tr>
								<th>연락처</th>
								<td>
									<input type="text" id = "EMPTEL" name="EMPTEL" class="modiInput" value="${user.empTel}">
								</td>
							</tr>
						</table>
	
						<input type="submit" id = "modiBtn" class="modiBtn" value="변경저장" style="border: 1px solid black">
					</form>
				</div>
				<div id = "showEmpInfo" style="display: none;" align="center">
					<input type="hidden" id="searchEmpNo">
					<table>
						<tr>
							<td colspan = "2" id="showImg" style="padding-bottom: 10px;">
								
							</td>
						</tr>
						<tr>
							<td>이름</td><td id = "showName"></td>
						</tr>
						<tr>
							<td>부서</td><td id = "showDept"></td>
						</tr>
						<tr>
							<td>이메일</td><td id = "showMail"></td>
						</tr>
						<tr>
							<td>연락처</td><td id = "showTel"></td>
						</tr>
						<tr>
							<td>
								<button class="searchEmp_btn" onclick="sendMailBySearchEmp();" style="margin-top:18px;">메일 보내기</button>
							</td>
							<td>
								<button class="searchEmp_btn" onclick="sendMessageBySearchEmp();" style="margin-top:18px;">쪽지 보내기</button>
							</td>
						</tr>
					</table>
				</div>
				<div id = "myInfo">
					<div id="profile_img_box">
						<img id="profile_img" alt="basicImage" src="${contextPath}/emp/getEmpImage?loginEmp=${user.empNo}" style="margin-bottom: 3px; z-index: 1">
					</div>				
					<div id="userInfo">
						<ul style="padding-top: 5px">
							<li style="font-size: 14px;font-weight: bold">${user.deptName}</li>
							<li style="padding-top: 5px;font-size: 14px">${user.empName}</li>
						</ul>
						<div style="margin-top: 20px; padding-bottom: 5px;"> 
						<input type = "button" value = "마이페이지" onclick = "showMyPage();" class = "myInfoBtn">
						<input type = "button" value = "로그아웃" onclick = "location.href = '${contextPath}/emp/userLogout'" class = "myInfoBtn">
					</div>
					</div>
				</div>
			</div>
			
			<div id = "searchEmp" align = "center">
				<div id = "nameBox">
					<input type = "text" placeholder = "사원이름 검색" id = "empInput">
					<div id = "btn" onclick = "search()" align="center" style="cursor: pointer;">검색</div><br>
					<ul id = "autoList"></ul>
				</div>
			</div>
			
			<div id="calendar" style="display: inline-block; margin: 0; position: inherit; margin-top: 3%; width: 97%;padding-bottom: 25px;"></div>
			
			<div id="memoDiv" style="width: 100%; height: 266px; border: 1px solid silver; margin-top: 3%; padding-left: 0;overflow: auto">
				 
				<p style="border-bottom: 1px solid silver;background-color: #f3f4f9;padding-top: 5px;padding-bottom: 5px;">
					<b style="padding-left: 10px;">My Memo</b>
					<span id="addMemoBtn" onclick='addMemoModal()' style="background-color: #f3f4f9; border: none;font-weight: bold; font-size: 15px; float: right; margin-right: 5px;cursor: pointer;">+</span>
				</p>
				
				<div id="memoBox" style="display: none;background-color:white;width: 285px; height: 170px;z-index: 1000;">
					<textarea id="memoContent" style="resize:none;border: none;background-color: white;display: block" rows="10" cols="36" placeholder="메모를 입력하세요"></textarea>
					<div style="display: inline-block;">
						<div id="closeMemo" style="text-align:center;cursor:pointer;font-size:12px;border:1px solid black;float: right;margin-right: 8px;margin-left: 5px;width:130px;padding:2px 0;font-weight: bold">취소</div>
						<div id="submitMemo" onclick='submitMemo()' style="text-align:center;cursor:pointer;font-size:12px;border:1px solid black;float: right;width:130px;padding:2px 0;font-weight: bold">등록</div>
					</div>
				</div>

				<div id="memoLine" style="width: 100%"></div>
				
			</div>  
			  
			<div id="modalMemoModify" style="display:none;width:230px;height: 140px;border: 1px solid black;position: absolute;background-color: white;top: 80%">
				<p style="border-bottom: 1px solid silver;background-color: #f3f4f9;font-size: 12px;padding: 2px 2px;font-weight: bold">
					메모수정
					<span onclick='closeMemoModify()' style='cursor:pointer;font-size:12px; background-color: white;border: 1px solid black; float: right;padding-left: 3px;padding-right:3px;vertical-align: middle;'>x</span>
				</p>
				<textarea id='memoModifyContent' style='resize:none; border:none; padding:5px' rows='6' cols='23'></textarea><br>
				<input type='button' value='삭제' onclick='deleteMemo()' style='background-color:white;border: 1px solid black;margin-right: 2px;margin-left: 154px'>
				<input type='button' value='완료' onclick='updateMemo1()' style='background-color:white;border: 1px solid black;margin-right: 2px'>
			</div>
			
			<div id="modalMemoView" style="display:none;width:230px;height: 122.5px;border: 1px solid black;position: absolute;background-color: white;top: 80%">
				<p style="border-bottom: 1px solid silver;background-color: #f3f4f9;font-size: 12px;padding: 2px 2px;font-weight: bold">
					메모보기
					<span onclick='closeMemoView()' style='cursor:pointer;font-size:12px; background-color: white;border: 1px solid black; float: right;padding-left: 3px;padding-right:3px;vertical-align: middle;'>x</span>
				</p>
				<textarea readonly="readonly" id='memoViewContent' style='resize:none;border:none; padding:5px' rows='6' cols='23'></textarea>
			</div>
			  
			
		</div>
		<!-- 좌측 박스 끝 -->
		
		<!-- 우측 박스 시작 -->
		<div id="right_box">
			<div id="menuAlarm">
				<p class="long_title" style="font-size: 16px; font-weight : bold; padding-bottom: 5px; margin-top : 1px; margin-right : 2px;">메뉴 알림</p>
				<div class = "alarmList">
					<div class = "alarmIcon" align = "center">
						<img src="${contextPath}/img/mail.png" width="40px" height="40px" style = "margin-top : 16px; cursor: pointer;" onclick = "location.href = '${contextPath}/mail/mail_main_form'">
					</div>
					<a href="${contextPath}/mail/mail_main_form" style="font-size: 14px; margin-left: 20px;" class = "alarmTitle">전자메일</a>
				</div>
				<div class = "alarmList">
					<div class = "alarmIcon" align = "center">
						<img src="${contextPath}/img/conversation.png" width="40px" height="40px" style = "margin-top : 16px; cursor: pointer;" onclick = "location.href = '${contextPath}/messenger/messageList'"> 
					</div>
					<a href="${contextPath}/messenger/messageList" style="font-size: 14px; margin-left: 20px;" id="main_messenger" class = "alarmTitle">메신저</a>
				</div>
				<div class = "alarmList">
					<div class = "alarmIcon" align = "center">
						<img src="${contextPath}/img/checklist.png" width="40px" height="40px" style = "margin-top : 16px; cursor: pointer;" onclick = "location.href = '${contextPath}/approval/approvalMainForm'">
					</div>
					<a href="${contextPath}/approval/approvalMainForm" style="font-size: 14px; margin-left: 20px;" class = "alarmTitle">전자결재</a>
				</div>
				<div class = "alarmList">
					<div class = "alarmIcon" align = "center">
						<img src="${contextPath}/img/panel.png" width="40px" height="40px" style = "margin-top : 16px; cursor: pointer;" onclick = "location.href = '${contextPath}/board/boardMainForm'">
					</div>
					<a href="${contextPath}/board/boardMainForm" style="font-size: 14px; margin-left: 20px;" class = "alarmTitle">게시판</a>
				</div>
				<div class = "alarmList">
					<div class = "alarmIcon" align = "center">
						<img src="${contextPath}/img/calendar.png" width="40px" height="40px" style = "margin-top : 16px; cursor: pointer;" onclick = "location.href = '${contextPath}/management/managementcalendar'">
					</div>
					<a href="${contextPath}/management/managementcalendar" style="font-size: 14px; margin-left: 20px;" class = "alarmTitle">자원관리</a>
				</div>
			</div>
			<div class="board_box_l">
				<p class="board_box_p">
					<a href="${contextPath}/board/boardNotice" style="font-size: 16px;" class = "dashName">공지사항</a>
				</p>
	
				<ul style="width: 410px; margin: 0 auto; padding-top: 5px">
					<c:forEach begin = "0" end="4" step = "1" items="${noticeList}" var="notice">
						<li style="height: 32px;  padding-left: 10px;" class="noticeBoxLi">
							<p class="Ptag" style="float: left;">
								<a style="font-size: 13px;" href="${contextPath}/board/boardNoticeViewForm?boardNo=${notice.boardNo}" name="TITLE">${notice.title}</a>
							</p>
	
							<p style="float: right;">
								<b style="font-size: 12px; font-weight: normal;" id="li1" name="WRITER">${notice.empName}</b> 
								<b style="font-size: 12px; font-weight: normal;" id="li2" name="WRITEDATE">${notice.writeDate}</b>
							<p>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="board_box_r">
				<p class="board_box_p">
					<a href="${contextPath}/board/boardProjectOngoing" style="font-size: 16px;" class = "dashName">프로젝트 진행상황</a>
				</p>
				<ul style="width: 410px; margin: 0 auto; padding-top: 5px">
					<c:forEach begin = "0" end="4" step = "1" items="${projectOngoingList}" var="projectOngoing">
						<li style="height: 32px; padding-left: 10px; " class="noticeBoxLi">
							<p class="Ptag" style="float: left;">
								<a style="font-size: 13px; color : #333;"
									href="${contextPath}/board/boardProjectOngoingViewForm?boardNo=${projectOngoing.boardNo}">${projectOngoing.projectName}</a>
							</p> 
							<span style = "float : right; width : 40%; margin-right : 5px;">
								<span style = "width : 60%;"><progress style="width:120px; height : 13px; margin-top : 2px;" value="${projectOngoing.rop}" max=100></progress></span>
							 	<span style = "width : 30%;"><i style="font-family: sans-serif; font-size: 13px">${projectOngoing.rop}%</i></span>
							</span>
						</li>
					</c:forEach>
				</ul>
			</div>
			
			
			<div class="board_box_l">
				<p class="board_box_p" style = "margin-top : 1px;">
					<c:if test="${empDeptNoMap.deptNo1 == '40'}">
						<a href="${contextPath}/board/boardDeptIt" style="font-size: 16px;" class = "dashName">부서게시판(IT부서)</a>
					</c:if>
					<c:if test="${empDeptNoMap.deptNo1  == '30'}">
						<a href="${contextPath}/board/boardDeptMin" style="font-size: 16px;" class = "dashName">부서 게시판(재무부서)</a>
					</c:if>
					<c:if test="${empDeptNoMap.deptNo1 == '20'}">
						<a href="${contextPath}/board/boardDeptSales" style="font-size: 16px;" class = "dashName">부서 게시판(영업부서)</a>
					</c:if>
					<c:if test="${empDeptNoMap.deptNo1 == '10'}">
						<a href="${contextPath}/board/boardDeptBP" style="font-size: 16px;" class = "dashName">부서 게시판(경영/기획부서)</a>
					</c:if>
	
				</p>
	
				<ul style="width: 410px; margin: 0 auto; padding-top: 5px">
					<c:forEach begin = "0" end="4" step = "1" items="${deptList}" var="dept">
						<li style="height: 32px;  padding-left: 10px;" class="noticeBoxLi">
	
							<p class="Ptag" style="float: left; margin-top: 2px;">
	
								<c:if test="${dept.deptNo == '40'}">
									<a style="font-size: 13px;" href="${contextPath}/board/boardDeptItViewForm?boardNo=${dept.boardNo}" name="TITLE">${dept.title}</a>
								</c:if>
								<c:if test="${dept.deptNo == '30'}">
									<a style="font-size: 13px;" href="${contextPath}/board/boardDeptMinViewForm?boardNo=${dept.boardNo}" name="TITLE">${dept.title}</a>
								</c:if>
								<c:if test="${dept.deptNo == '20'}">
									<a style="font-size: 13px;" href="${contextPath}/board/boardDeptSalesViewForm?boardNo=${dept.boardNo}" name="TITLE">${dept.title}</a>
								</c:if>
								<c:if test="${dept.deptNo == '10'}">
									<a style="font-size: 13px;" href="${contextPath}/board/boardDeptBpViewForm?boardNo=${dept.boardNo}" name="TITLE">${dept.title}</a>
								</c:if>
							</p>
	
							<p style="float: right; margin-top: 4px;">
								<b style="font-size: 12px; font-weight: normal;" id="li1" name="WRITER">${dept.empName}</b>
							    <b style="font-size: 12px; font-weight: normal;" id="li2" name="WRITEDATE">${dept.writeDate1}</b>
							<p>
						</li>
					</c:forEach>
				
				</ul>
	
			</div>

			<div class="board_box_r">
				<p class="board_box_p" style = "margin-top : 1px;">
				<a href="${contextPath}/board/boardAnonymity" ㅠstyle="font-size: 16px;" class = "dashName">익명게시판</a>
				<ul style="width: 410px; margin: 0 auto; padding-top: 5px"> 
	
					<c:forEach begin = "0" end="4" step = "1" items="${anonymityList}" var="anonymity">
						<li
							style="height: 32px;  padding-left: 10px;"
							class="noticeBoxLi">
	
							<p class="pTag" style="float: left; margin-top: 2px;">
								<a style="font-size: 13px;" href="${contextPath}/board/boardAnonymityViewForm?boardNo=${anonymity.boardNo}" name="TITLE">${anonymity.title}</a>
							</p>
	
							<p style="float: right; margin-top: 2px;">
								<b style="font-size: 12px; font-weight: normal;" id="li1" name="WRITER">${anonymity.writer}</b> 
								<b style="font-size: 12px; font-weight: normal;" id="li2" name="WRITEDATE">${anonymity.writeDate}</b>
							<p>
						</li>
					</c:forEach>
	
				</ul>
			</div>
			<div id="main_event_img">
				<p class="long_title" style="font-size: 16px; font-weight : bold; padding-bottom: 5px;">사내 행사</p>
			   	<img class="mySlides" src="${contextPath}/img/a.png">
			   	<img class="mySlides" src="${contextPath}/img/b.png">
			   	<img class="mySlides" src="${contextPath}/img/c.png">
			</div>
		</div>
		
		
	</div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/messenger.css?ver=7">
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
<jsp:include page="/jsp/pageHeader.jsp"></jsp:include>
<script type="text/javascript" src = "${contextPath}/js/message_write.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>writeMessage</title>
</head>
<body>
	<div id="mask"></div>
	<div id="content">
<jsp:include page="/jsp/pageSide.jsp">
		<jsp:param value="messageWrite" name="pageType"/>
		<jsp:param value="messenger" name="category"/>
</jsp:include>
		<input type="hidden" value="${replyID}" id="replyID"> 
		<div id="menuBox2">
			<p style="margin: 20px; display: inline-block;">
				<a href="#" style="font-size: 20px">쪽지 쓰기</a>
			</p>
			<hr id="hr3" style="margin-bottom: 5px; width: 70%">
			<form action="addMessage" id="msg_sendBox" style="padding-top: 10px;">
				<b style="font-size: 14px; margin: 5px;">받는 사람</b>
				<input type="button" id="callChart_btn" value="조직도" onclick="createLine();">
				<br>
				<div id="messageWriteTarget_Div" style="margin-top: 5px;">
				</div>
				<br>
				<b style="font-size: 14px; margin: 5px;">제목</b>
				<br>
				<input type="text" name="msg_title" id="msg_title" onkeypress="enterCheck();" maxlength="40" style="margin-top: 5px;">
				<br>
				<b style="font-size: 14px; margin: 5px;">내용</b>
				<br>
				<textarea rows="10" cols="78" name="msg_content" id="contentArea" style="resize: none; margin-top: 5px;"></textarea>
				<br>
				<input type="hidden" id="msg_target">
				<input type="button" value="보내기" onclick="message_submit();">
				<input type="button" value="취소" onclick="history.go(-1)">
				<span style="font-size: 12px;">
					<span id="textCount" style="margin-left:320px;"></span>/250
				</span>
			</form>
		</div>
	</div>

	   <div id = "chart_div" style = "display : none;">
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
      <input type = "button" id = "chartBtn" value = "완료" onclick = "makeToLine();">
   </div>
	
<input type="hidden" id="contextPath" value="${contextPath}">
</body>
</html>

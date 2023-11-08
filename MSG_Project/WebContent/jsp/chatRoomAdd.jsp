<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/main.css?ver=7">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/messenger.css?ver=7">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
<title>Add Chat Room</title>
</head>
<body>
	<div id="miniBox">
		<a>채팅 방 만들기</a>
		<hr width="100px;">
		<form action="${contextPath}/messenger/addChatRoom" onsubmit="return checkRoomName()" method="post">
			방 제목 <input type="text" name="title" id="chat-title">
			<br>
			<br>
			<input type="submit" value="만들기">
			<input type="button" value="취소" onclick="history.go(-1)">
		</form>
	</div>
</body>
</html>
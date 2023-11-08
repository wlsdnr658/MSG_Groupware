<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script
	src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous">
</script>
</head>
<link rel = "stylesheet" type = "text/css" href = "${contextPath}/css/main.css">
<link rel = "stylesheet" type = "text/css" href = "${contextPath}/css/approvStore.css?ver=2">
<script type = "text/javascript" src = "${contextPath}/js/main.js?ver=3"></script>
<script type = "text/javascript" src = "${contextPath}/js/approvStore.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<body>
	<jsp:include page = "/jsp/pageHeader.jsp"></jsp:include>
	<input type = "hidden" id = "emp" value = "${user.empNo}">
	<input type = "hidden" id = "path" value = "${contextPath}">
	<div id = "content">
		<jsp:include page="/jsp/pageSide.jsp">
			<jsp:param value="approv" name="category"/>
		</jsp:include>
		<div id = "menuBox2">
			<p style = "margin : 20px">
				<a href = "#" style = "font-size: 20px;">내 결재</a>
			</p>
			<hr id = "hr3">
			<hr id = "hr4">
		</div>
		
		<div id = "appbox1">
			<div id = "wait">
				<p id = "wait_title">
					<a href = "${contextPath}/approval/denyDocForm?empNo=${user.empNo}&status=3" class = "bigTitle">반려 결재목록</a>
				</p>
				<ul id = "waitUl">	<!-- 반려 결재 목록 -->
				</ul>
			</div>
			
			<div id = "req">
				<p id = "req_title">
					<a href = "${contextPath}/approval/curDocForm?empNo=${user.empNo}&status=1" class = "bigTitle">진행 결재목록</a>
				</p>
				<ul id = "reqUl">
				</ul>
			</div>
			
			<div id = "finish">
				<p id = "finish_title">
					<a href = "${contextPath}/approval/comDocForm?empNo=${user.empNo}&status=2" class = "bigTitle">완료 결재목록</a>
				</p>	
				<ul id = "finUl">
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
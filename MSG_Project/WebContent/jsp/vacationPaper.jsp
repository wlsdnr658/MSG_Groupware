<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script
	src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous">
</script>
</head>
<link rel = "stylesheet" type = "text/css" href = "${contextPath}/css/main.css">
<link rel = "stylesheet" type = "text/css" href = "${contextPath}/css/vacation.css">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<script type="text/javascript" src = "${contextPath}/js/approvLine.js?ver=4"></script>
<script type = "text/javascript" src = "${contextPath}/js/main.js"></script>
<body>
	<div id = "mask"></div>
	<jsp:include page = "/jsp/pageHeader.jsp"></jsp:include>
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
	<div id = "content">
		<jsp:include page="/jsp/pageSide.jsp">
			<jsp:param value="approv" name="category"/>
		</jsp:include>
		<div id="wrap" align="center">
			<div id="header">
				<div id="left">
					<img src="${contextPath}/img/msgLogo.png">
				</div>
				<div id="center">
					<h1>휴가 계획서</h1>
				</div>
				<div id="right">
					<table id="top_info">
						<tr>
							<td class="top_title">문서번호</td>
							<td class="top_con">${data.approvNo}</td>
						</tr>
						<tr>
							<td class="top_title">작성자</td>
							<td class="top_con">${user.empNo}</td>
						</tr>
						<tr>
							<td class="top_title">작성일자</td>
							<td class="top_con">${data.approvDate}</td>
						</tr>
					</table>
				</div>
			</div>	
	
			<div id="line" align="center">
				<div id="selectLine" style = "width : 20%;">
					<button onclick = "createLine();" style = "width : 100%; height : 30px;background-color: #f3f4f9;border: 1px solid black;cursor: pointer;">결재선 선택</button>
				</div>
				<div id="myLine" style = "width : 79%;">
					<table id = "approvLine">	
					</table>
				</div>
			</div>
	
			<div id="doc_content" align = "center">
				<form action="#">
					<table style = "width : 100%">
						<tr>
							<th rowspan="2" class = "ta_header">기안자</th>
							<th>사번</th><td class = "info">${user.empNo}</td>
							<th>소속</th><td class = "info">${user.deptName}</td>
						</tr>
						<tr>
							<th class = "ta_header">직급</th><td class = "info">${user.empPos}</td>
							<th class = "ta_header">성명</th><td class = "info">${user.empName}</td>
						</tr>
						<tr>
							<th class = "ta_header">휴가구분</th>
							<td colspan = "4">
								<input type = "text" value = "${data.vacationType}" name = "vacType">
							</td>
						</tr>
						<tr>
							<th class = "ta_header">기간</th>
							<td colspan = "4">
								<input type = "text" class = "input_val" name = "period" style = "width : 90%; border : 0;">
							</td>
						</tr>
						<tr>
							<th class = "ta_header">사유</th>
							<td colspan = "4">
								<textarea cols="50" rows="5" name="DETAIL" id="DETAIL" style = "width : 98%; padding : 5px;">${data.reason}</textarea>
							</td>
						</tr>
						<tr>
							<th style = "height : 90px;">비고</th>
							<td colspan = "4">
								<textarea cols="50" rows="5" name="ETC" id="ETC" style = "width : 97%; margin : 5px; padding : 5px;">${data.etc}</textarea>
							</td>
						</tr>
						<tr>
							<td colspan = "5" style = "text-align: right;" id = "btm">
								<input type = "button" value = "취소" class = "btns" onclick = "history.go(-1);" style="background-color: #f3f4f9;border: 1px solid black;cursor: pointer;">
								<input type = "button" value = "임시저장" class = "btns" style="background-color: #f3f4f9;border: 1px solid black;cursor: pointer;">
								<input type = "submit" value = "등록" class = "btns" style="background-color: #f3f4f9;border: 1px solid black;cursor: pointer;">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
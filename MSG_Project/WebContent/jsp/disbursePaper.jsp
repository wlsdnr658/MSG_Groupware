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
<link rel = "stylesheet" type = "text/css" href = "${contextPath}/css/disburse.css">
<script type = "text/javascript" src = "${contextPath}/js/main.js"></script>
<script type = "text/javascript" src = "${contextPath}/js/approvLine.js"></script>
<script type = "text/javascript" src = "${contextPath}/js/disburse.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<body>
	<div id = "mask"></div>
	<jsp:include page="/jsp/pageHeader.jsp"></jsp:include>
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
					<h1>지출 결의서</h1>
				</div>
				<div id="right">
					<table id="top_info">
						<tr>
							<td class="top_title">문서번호</td>
							<td class="top_con">${data.approvNo}</td>
						</tr>
						<tr>
							<td class="top_title">작성자</td>
							<td class="top_con">${user.empName}</td>
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
				<form action="${contextPath}/approval/writeApprov" onSubmit = "return doSumit();">
					<input type = "hidden" name = "APNUM" value = "${data.approvNo}">
					<input type = "hidden" name = "APDATE" value = "${data.approvDate}">
					<input type = "hidden" name = "DOCTYPE" value = "${data.DOCTYPE}">
					<input type = "hidden" id = "APLINE" name = "APLINE">
					<!-- 결재번호, 결재일, 총가격, 제목, 거래업체명, 적요(여러개), 적요당가격(여러개), 비용지불방식, 비고, 결재선 순서, 상태, 결재선1, 결재선2, 결재선3, 결재선4, 문서종류, 결재 작성자   -->
					<table style = "width : 100%">
						<tr>
							<th rowspan="2" class = "ta_header">기안자</th>
							<th>사번</th><td class = "info"><input type = "text" value = "${user.empNo}" name = "WRITER" style = "border : 0;" readonly></td>
							<th>소속</th><td class = "info">${user.deptName}</td>
						</tr>
						<tr>
							<th class = "ta_header">직급</th><td class = "info">${user.empPos}</td>
							<th class = "ta_header">성명</th><td class = "info">${user.empName }</td>
						</tr>
						<tr>
							<th class = "ta_header">금액</th>
							<td colspan = "4">
								<span id = "amountLabel">일금</span>
								<span id = "amount_input">( ￦ <input type = "text" name = "ONEMONEY" value = "${amount}" onkeyup = "inputNumber(this);"> )</span>
							</td>
						</tr>
						<tr>
							<th class = "ta_header">제목</th>
							<td colspan = "4">
								<input type = "text" class = "input_val" name = "TITLE" value = "${data.title}" style = "width : 90%; border : 0;">
							</td>
						</tr>
						<tr>
							<th class = "ta_header">거래처명</th>
							<td colspan = "4">
								<input type = "text" class = "input_val" value = "${data.tradeInc}" name = "INC">
							</td>
						</tr>
						<tr>
							<th class = "ta_header" colspan = "3">적요</th>
							<th class = "ta_header" colspan = "2">금액</th>
						</tr>
						<tr>
							<td colspan = "3">
								<input type = "text" class = "input_val" name = "CON_DETAIL_1">
							</td>
							<td colspan = "2">
								<input type = "text" class = "input_val" name = "MONEY_DETAIL_1" onchange = "calTotal(this);">
							</td>
						</tr>
						<tr>
							<td colspan = "3">
								<input type = "text" class = "input_val" name = "CON_DETAIL_2">
							</td>
							<td colspan = "2">
								<input type = "text" class = "input_val" name = "MONEY_DETAIL_2" onchange = "calTotal(this);">
							</td>
						</tr>
						<tr>
							<td colspan = "3">
								<input type = "text" class = "input_val" name = "CON_DETAIL_3">
							</td>
							<td colspan = "2">
								<input type = "text" class = "input_val" name = "MONEY_DETAIL_3" onchange = "calTotal(this);">
							</td>
						</tr>
						<tr>
							<td colspan = "3">
								<input type = "text" class = "input_val" name = "CON_DETAIL_4">
							</td>
							<td colspan = "2">
								<input type = "text" class = "input_val" name = "MONEY_DETAIL_4" onchange = "calTotal(this);">
							</td>
						</tr>
						<tr>
							<td colspan = "3">
								<input type = "text" class = "input_val" name = "CON_DETAIL_5">
							</td>
							<td colspan = "2">
								<input type = "text" class = "input_val" name = "MONEY_DETAIL_5" onchange = "calTotal(this);">
							</td>
						</tr>
						<tr>
							<th colspan = "3">합 계</th>
							<td colspan = "2">
								<input type = "text" id = "myTotal" class = "input_val" name = "MONEY_DETAIL_TOTAL" readonly>
							</td>
						</tr>
						<tr>
							<th colspan = "3">결제사항</th>
							<td colspan = "2" style = "padding : 5px;">
								<input type = "radio" name = "PAYTYPE" value = "p_cash"> 개인현금
								<input type = "radio" name = "PAYTYPE" value = "p_card"> 개인카드
								<input type = "radio" name = "PAYTYPE" value = "c_card"> 법인카드
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
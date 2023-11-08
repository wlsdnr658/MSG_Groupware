<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<link rel="stylesheet" type="text/css" href="${contextPath}/css/disburse.css?ver=8">
<script src = "${contextPath}/js/viewLine.js"></script>
<script src = "${contextPath}/js/viewDoc.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<body>
	<jsp:include page = "/jsp/pageHeader.jsp"></jsp:include>
	<input type = "hidden" id = "path" value = "${contextPath}">
	<input type = "hidden" id = "APNUM" value = "${data.approvNo}">
	<input type = "hidden" id = "first" value = "${data.empNo}">
	<input type = "hidden" id = "next">
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
							<td class="top_con">${data.writer.empName}</td>
						</tr>
						<tr>
							<td class="top_title">작성일자</td>
							<td class="top_con"><fmt:formatDate value="${data.approvDate}" type = "date"/></td>
						</tr>
					</table>
				</div>
			</div>	
			<div id="line" align="right" style = "margin-right : 10px;">
				<div id="myLine"  align="right" style = "width : 79%; float : right;">
					<table id = "approvLine"> <!--  문서에 매칭 되는 결재선 그려주기  -->
					</table>
				</div>
			</div>
	
			<div id="doc_content" align = "center">
					<input type = "hidden" name = "APNUM" value = "${data.DOCNUM}">
					<input type = "hidden" name = "APDATE" value = "${data.CUR}">
					<input type = "hidden" name = "DOCTYPE" value = "${data.DOCTYPE}">
					<input type = "hidden" id = "APLINE" name = "APLINE">
					<!-- 결재번호, 결재일, 총가격, 제목, 거래업체명, 적요(여러개), 적요당가격(여러개), 비용지불방식, 비고, 결재선 순서, 상태, 결재선1, 결재선2, 결재선3, 결재선4, 문서종류, 결재 작성자   -->
					<table style = "width : 100%">
						<tr>
							<th rowspan="2" class = "ta_header">기안자</th>
							<th>사번</th><td class = "info"><input type = "text" value = "${data.writer.empNo}" name = "WRITER" style = "border : 0;" readonly></td>
							<th>소속</th><td class = "info">${data.writer.deptName}</td>
						</tr>
						<tr>
							<th class = "ta_header">직급</th><td class = "info">${data.writer.empPos}</td>
							<th class = "ta_header">성명</th><td class = "info">${data.writer.empName }</td>
						</tr>
						<tr>
							<th class = "ta_header">금액</th>
							<td colspan = "4">
								<span id = "amountLabel">일금</span>
								<span id = "amount_input">( ￦ <input type = "text" name = "ONEMONEY" value = "${data.totalPrice}원" onkeyup = "inputNumber(this);"  readonly> )</span>
							</td>
						</tr>
						<tr>
							<th class = "ta_header">제목</th>
							<td colspan = "4">
								<input type = "text" class = "input_val" name = "TITLE" value = "${data.title}" style = "width : 90%; border : 0;"  readonly>
							</td>
						</tr>
						<tr>
							<th class = "ta_header">거래처명</th>
							<td colspan = "4">
								<input type = "text" class = "input_val" value = "${data.tradeInc}" name = "INC" readonly>
							</td>
						</tr>
						<tr>
							<th class = "ta_header" colspan = "3">적요</th>
							<th class = "ta_header" colspan = "2">금액</th>
						</tr>
						<tr>
							<td colspan = "3">
								<input type = "text" class = "input_val" value = "${data.CON_DETAIL_1}" name = "CON_DETAIL_1" readonly>
							</td>
							<td colspan = "2">
								<input type = "text" class = "input_val" value = "${data.MONEY_DETAIL_1}" name = "MONEY_DETAIL_1" onchange = "calTotal(this);" readonly>
							</td>
						</tr>
						<tr>
							<td colspan = "3">
								<input type = "text" class = "input_val" value = "${data.CON_DETAIL_2}" name = "CON_DETAIL_2" readonly>
							</td>
							<td colspan = "2">
								<input type = "text" class = "input_val" value = "${data.MONEY_DETAIL_2}" name = "MONEY_DETAIL_2" onchange = "calTotal(this);" readonly>
							</td>
						</tr>
						<tr>
							<td colspan = "3">
								<input type = "text" class = "input_val" value = "${data.CON_DETAIL_3}" name = "CON_DETAIL_3" readonly>
							</td>
							<td colspan = "2">
								<input type = "text" class = "input_val" value = "${data.MONEY_DETAIL_3}" name = "MONEY_DETAIL_3" onchange = "calTotal(this);" readonly>
							</td>
						</tr>
						<tr>
							<td colspan = "3">
								<input type = "text" class = "input_val" value = "${data.CON_DETAIL_4}" name = "CON_DETAIL_4" readonly>
							</td>
							<td colspan = "2">
								<input type = "text" class = "input_val" value = "${data.MONEY_DETAIL_4}" name = "MONEY_DETAIL_4" onchange = "calTotal(this);" readonly>
							</td>
						</tr>
						<tr>
							<td colspan = "3">
								<input type = "text" class = "input_val" value = "${data.CON_DETAIL_5}" name = "CON_DETAIL_5" readonly>
							</td>
							<td colspan = "2">
								<input type = "text" class = "input_val" value = "${data.MONEY_DETAIL_5}" name = "MONEY_DETAIL_5" onchange = "calTotal(this);" readonly>
							</td>
						</tr>
						<tr>
							<th colspan = "3">합 계</th>
							<td colspan = "2">
								<input type = "text" id = "myTotal" class = "input_val" value = "${data.totalPrice}" name = "MONEY_DETAIL_TOTAL" readonly>
							</td>
						</tr>
						<tr>
							<th colspan = "3">결제사항</th>
							<td colspan = "2" style = "padding : 5px;">
								<input type = "text" value = "${data.payType}" readonly>
							</td>
						</tr>
						<tr>
							<th style = "height : 90px;">비고</th>
							<td colspan = "4">
								<textarea cols="50" rows="5" name="ETC" id="ETC" style = "width : 97%; margin : 5px; padding : 5px;">${data.etc}</textarea>
							</td>
						</tr>
						<c:if test="${data.status != 2}">
							<tr>
								<td colspan = "5" style = "text-align: right;" id = "btm">
									<form action = "${contextPath}/approval/approvalProcess" method = "post" id = "resultForm">
										<input type = "hidden" id = "deny_Reason" name = "ETC"> 
										<input type = "hidden" id = "approvNo" name = "approvNo" value = "${data.approvNo}">
										<input type = "hidden" name = "curEmp" value = "${user.empNo}">
										<input type = "hidden" id = "result" name = "result">
										<input type = "button" value = "취소" class = "btns"  onclick = "history.go(-1);">
										<input type = "button" value = "반려" class = "btns" onclick = "deny();">
										<input type = "button" value = "승인" class = "btns" onclick = "permit('${user.empNo}');">
									</form>
								</td>
							</tr>
						</c:if>
						<c:if test="${data.status == 3}">
							<tr>
								<td colspan = "5" style = "text-align: right;" id = "btm">
									<form action = "${contextPath}/approval/approvalProcess" method = "post" id = "resultForm">
										<input type = "hidden" id = "deny_Reason" name = "ETC"> 
										<input type = "hidden" id = "approvNo" name = "approvNo" value = "${data.approvNo}">
										<input type = "hidden" id = "curEmp" name = "curEmp" value = "${user.empNo}">
										<input type = "hidden" id = "result" name = "result">
										<input type = "button" value = "취소" class = "btns"  onclick = "history.go(-1);">
										<input type = "button" value = "재기안" class = "btns" onclick = "reApprov('${data.approvNo}');">
									</form>
								</td>
							</tr>
						</c:if>
					</table>
			</div>
		</div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/mail.css">
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>	
<script src='${contextPath}/fullcalendar/jquery.min.js'></script>
<title>E-Mail 내게쓴편지함 상세보기</title>
</head>

<script type="text/javascript" src="${contextPath}/js/fileupload.js"></script>

<body>
	<jsp:include page="/jsp/pageHeader.jsp"></jsp:include>
	<!-------------------------------------헤더--------------------------------------------------------->
	<div id="content">
			<input type="hidden" value="${contextPath}" id="context">
		<jsp:include page="/jsp/pageSide.jsp">
			<jsp:param value="mail" name="category"/>
		</jsp:include>
		<div id="menuBox2">
			<p style="margin:20px">
				<a style="font-size: 20px;">내게쓴 편지함</a>	
			</p>
			<hr id="hr3">
				<div id="button_send1">
				<form action="mail_delete_MyWrite_view" method="post">
				<input type="hidden" value="${myWriteinboxView.mailstatus}" name="mailstatus" id="mailstatus" >
		 		<input type="hidden" value="${myWriteinboxView.mailNo}" name="mailNo" id="mailNo" >
         		<input type="hidden" value="${myWriteinboxView.writeDate}" name="writeDate" id="writeDate" >
				<input type = "hidden" name = "senderId" value = "${user.empNo}" id="senderId">
				<input type="button" name="답장" value="답장" id="mail_receive_view" onclick="location.href='mail_reply_form_send?mailNo=${myWriteinboxView.mailNo}'">
				<input type="button" name="전달" value="전달" id="mail_sned_view" onclick="location.href='mail_relay_form?mailNo=${myWriteinboxView.mailNo}'">
				<input type="submit" value="삭제" id="mail_delete_send">		
				<label id="label1">${myWriteinboxView.writeDate}</label>		
				<hr id="hr3_2">	
				<dl>
   				<dt>${myWriteinboxView.title}</dt>
   			
   				<dd id="dd"><span id="spansend">보낸이</span> <small id="small">${myWriteinboxView.empName}</small></dd>		
				</dl>			
				<hr id="hr6">			
				${myWriteinboxView.content}
				<hr id="hr5">
				<span>
				<a href="#" id="more1" 
				onclick="createModal()">
				 첨부파일</a></span>
				 <div id="fileModal">
				 </div>
				</form>
				</div>	
		</div>
		</div>


	<!-------------------------------------바디---------------------------------------------------------------------------->

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%request.setAttribute("contextPath", request.getContextPath());%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/mail.css">

<script src='${contextPath}/fullcalendar/jquery.min.js'></script>

<title>E-Mail 보낸편지함 상세보기</title>
</head>
<%-- <script type="text/javascript" src = "${contextPath}/js/main.js"></script> --%>
<script type="text/javascript" src="${contextPath}/js/returnMail.js"></script>
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
				<a style="font-size: 20px;">보낸 편지함</a>	
			</p>
			<hr id="hr3">
				<div id="button_send1">
				<form action="mail_delete_sendInbox_view?fileName&fileNo" method="post">
				<input type="hidden" value="${InboxViewSend.mailstatus}" name="mailstatus" id="mailstatus" >
		 		<input type="hidden" value="${InboxViewSend.mailNo}" name="mailNo" id="mailNo" >
         		<input type="hidden" value="${InboxViewSend.writeDate}" name="writeDate" id="writeDate" >
				<input type = "hidden" name = "senderId" value = "${user.empNo}" id="senderId">
				
				<input type="button" name="답장" value="답장" id="mail_receive_view" onclick="location.href='mail_reply_form_send?mailNo=${InboxViewSend.mailNo}'">
				<input type="button" name="전달" value="전달" id="mail_sned_view" onclick="location.href='mail_relay_form?mailNo=${InboxViewSend.mailNo}'">
				<input type="submit" value="삭제" id="mail_delete_send">
				<label id="label3">${InboxViewSend.writeDate}</label>
			
				<hr id="hr3_2">	
				<dl>
				
   				<dt>${InboxViewSend.title}</dt>
   			
   				
   				<dd id="dd"><span id="spansend">보낸이 </span><small id="small">${InboxViewSend.empName}</small></dd>
   				<dd id="dd"><span id="spansend">받는이</span>  <small id="small"><c:forEach items="${sendInboxViewReceive}" var="sendInboxViewReceive">${sendInboxViewReceive.empName}&nbsp;</c:forEach></small></dd>
   				<dd id="dd"><span id="spansend">참조인 </span> <small id="small"><c:forEach items="${sendInboxViewCarbon}" var="sendInboxViewCarbon">${sendInboxViewCarbon.empName}&nbsp;</c:forEach></small></dd>
				</dl>			
				<hr id="hr6">	
				${InboxViewSend.content}
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
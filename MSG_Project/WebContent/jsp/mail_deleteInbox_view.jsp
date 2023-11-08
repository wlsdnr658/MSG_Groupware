<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%request.setAttribute("contextPath", request.getContextPath());%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/mail.css">  
<script src='${contextPath}/fullcalendar/jquery.min.js'></script>
<script type="text/javascript" src="${contextPath}/js/fileupload.js"></script>
<title>E-Mail 휴지통 상세보기</title>
</head>
<script type="text/javascript" src = "${contextPath}/js/main.js"></script>
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
				<a style="font-size: 20px;">휴지통</a>	
			</p>
			<hr id="hr3">
				<div id="button_send1">
				<form action="mail_empty_sendmail_view" method="post">
				<input type="hidden" value="${deleteInboxViewSend.mailstatus}" name="mailstatus" id="mailstatus" >
		 		<input type="hidden" value="${deleteInboxViewSend.mailNo}" name="mailNo" id="mailNo" >
         		<input type="hidden" value="${deleteInboxViewSend.writeDate}" name="writeDate" id="writeDate" >
				<input type = "hidden" name = "senderId" value = "${user.empNo}" id="senderId">
				<input type="submit" value="완전삭제" id="mail_delete_send">		
				<label id="label2">${deleteInboxViewSend.writeDate}</label>		
				<hr id="hr3_2">		
				<dl>
   				<dt>${deleteInboxViewSend.title}</dt>
   				
   				<dd id="dd"><span id="spansend">보낸이</span> <small id="small">${deleteInboxViewSend.empName}</small></dd>
   				<dd id="dd"><span id="spansend">받는이</span>  <small id="small"><c:forEach items="${deleteInboxViewReceive}" var="deleteInboxViewReceive">${deleteInboxViewReceive.empName}&nbsp;</c:forEach></small></dd>
   				<dd id="dd"><span id="spansend">참조인</span>  <small id="small"><c:forEach items="${deleteInboxViewCarbon}" var="deleteInboxViewCarbon">${deleteInboxViewCarbon.empName}&nbsp;</c:forEach></small></dd>
				</dl>			
				<hr id="hr6">			
				${deleteInboxViewSend.content}
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
	<jsp:include page="/jsp/pageFooter.jsp"></jsp:include>
</body>
</html>
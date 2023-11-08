<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/mail_write.css">
 <link rel="stylesheet" type="text/css"
	href="${contextPath}/css/main.css">	
<link
	href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css"
	rel="stylesheet">
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css"
	rel="stylesheet">

<script
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>
<script type="text/javascript" src="${contextPath}/lang/summernote-ko-KR.js"></script>
<script type="text/javascript" src="${contextPath}/js/fileupload.js"></script>
<script type="text/javascript" src="${contextPath}/js/redraftMailRe.js"></script>
<script type="text/javascript" src="${contextPath}/js/ko_kr_true.js"></script>
<script type="text/javascript" src="${contextPath}/js/empMember.js"></script>
<script type="text/javascript" src="${contextPath}/js/empMember2.js"></script>

<title>E-Mail 전달</title>
</head>
<%-- <script type="text/javascript" src = "${contextPath}/js/main.js"></script> --%>
<body>
	<input type="hidden" id="path" value="${contextPath}">
	<input type="hidden" id="empNo" value="${user.empNo}">
	<input type = "hidden" id = "user_auth" value = "${user.deptName}"> 
	<header id="main_header">
		<hgroup id="title">
			<img src="${contextPath}/img/msgLogo.png" onclick="location.href='${contextPath}/page/mainForm'" width = "152px" height="80px" style="padding-left:12px;">
		</hgroup> 
		
		<nav id="main_lnb">
			<ul id="ul1">
				<li><a href = "${contextPath}/board/boardMainForm">게시판</a></li>
				<li><a href = "${contextPath}/mail/mail_main_form">메일</a></li>
				<li id = "approvNavi"><a href = "${contextPath}/approval/approvalMainForm">전자결재</a></li>
				<li>
					<img src="${contextPath}/img/new.png" id="new_messenger">
					<a href = "${contextPath}/messenger/messageList">메신저</a>
				</li>
				<li><a href = "${contextPath}/management/managementcalendar">자원관리</a></li>
				
				<c:if test="${user.deptNo == 10}">
				<li><a href = "${contextPath}/emp/empManageForm">사원관리</a></li>
				</c:if>
				
				<li id = "weather">
					<div id="imgBox">
						<h4 id="place" style="display: inline-block;"></h4>
						<h4 id="temp" style="display: inline-block;"></h4>
					</div>
				</li>
				
			</ul>
		</nav> 
	</header>
	
	<div id="message_alert" style="display: none;">
		<div id="message_alert_div">
			<div class="alertDiv_header">
				쪽지
			</div>
			<div class="alertDiv_body">
				<a href="${contextPath}/messenger/messageList">
					새로운 쪽지가 <br>
					도착했습니다.
				</a>
			</div>
		</div>
	</div>
	
	<div id="approve_alert" style="display: none;">
		<div id="approve_alert_div">
			<div class="alertDiv_header">
				결재
			</div>
			<div class="alertDiv_body">
				<a href="${contextPath}/approval/approvalStore">
					새로운 결재가 <br>
					도착했습니다.
				</a>
			</div>
		</div>
	</div>
	
	<div id="mail_alert" style="display: none;">
		<div id="mail_alert_div">
			<div class="alertDiv_header">
				메일
			</div>
			<div class="alertDiv_body">
				<a href="${contextPath}/mail/mail_receiveInbox_form">
					새로운 메일이 <br>
					도착했습니다.
				</a>
			</div>
		</div>
	</div>		
	<!-------------------------------------헤더--------------------------------------------------------->
	<div id="content">
		<input type="hidden" value="${contextPath}" id="context">
	<div id="menuBox1">
		<br>
			<b style="font-size: 24px; margin-left: 52px;">전자메일</b>
			<br><hr id="hr1"><br>
			
			<ul id="menuBox1Ul">
				<li><a href="${contextPath}/mail/mail_receiveInbox_form">받은편지함</a></li><br>		
				<li><a href="${contextPath}/mail/mail_sendInbox_form">보낸편지함</a></li><br>
				<li><a href="${contextPath}/mail/mail_myWriteInbox_form">내게쓴메일함</a></li><br>
				<li><a href="${contextPath}/mail/mail_draftsInbox_form">임시보관함</a></li><br>
				<li><a href="${contextPath}/mail/mail_Write_form">메일쓰기</a></li><br>
				<li><a href="${contextPath}/mail/mail_myWrite_form">내게쓰기</a></li><br>
				<li><a href="${contextPath}/mail/mail_deleteInbox_form">지운편지함</a></li><br>
			</ul>
				<hr id="hr2">
			</div>			
		 <div id="menuBox2">
         <p style="margin: 20px">
            <a href="${contextPath}/mail/mail_Write_form" style="font-size: 20px;">메일 쓰기</a>
         </p>
         <hr id="hr3">
         <br>
         <div id="button_send" style="float: left">
         <form action="mail_write_relay" method="post" enctype="multipart/form-data" id="excelForm">
         	
          <input type="hidden" value="${InboxViewSend.mailstatus}" name="mailstatus" id="mailstatus">
		 <input type="hidden" value="${InboxViewSend.mailNo}" name="mailNo" id="mailNo" >
         <input type="hidden" value="${InboxViewSend.writeDate}" name="writeDate" id="writeDate">
         <input type = "hidden" name = "senderId" value = "${user.empNo}" id="senderId">
<%--          <input type="hidden" value="${uploadFromMail2.fileNo}" name="fileNo" id="fileNo" > --%>
<%--          <input type="hidden" value="${uploadFromMail2.fileName}" name="fileName" id="fileName"> --%>
        	<input type="submit" value="전송" style="background-color: white !important;
    												border: 1px solid #fb2525 !important;
    												color: black !important;
    												padding: 5px 5px !important">
            <input type="button" name="임시저장" value="임시저장" id="draftsInbox_click"><a href="${contextPath}/mail/mail_myWrite_form"> <img
               src="${contextPath}/img/recycle.png"><small
               style="color: #fb2525;">메일쓰기</small></a> <br>
         		<hr id="hr3_2">	 
	               <br>
	               <table>
	               <tr>
	            <td><span style=" text-align:right;">
	            <input id="lineBtn" type="button" onclick = "createLine()" value="받는이"></span></td>  
	               <td>
	                 <div style="text-align: left;">
						<table id="approvLine"></table></div></td>
						</tr>
						</table>
	                 
	<!-- <input type="text" name="receiverId" id="receiverId"> -->
	<!--                   <td><div id="mailWriteTarget_Div2"><input type="button" value="참조인" class="msg_write_target2" onclick="createLine2()" id="receiveModal2"> -->
	<%--                   <span name="carbonId"><input type = "hidden" name = "carbonId" value = "${user.empNo}" id= "carbonId"></span></div></td> --%>
	<!--                  		<td><input type="text" name="carbonId" id="carbonId">  -->
	<!--                  </td> -->
	           	    <table>
	               <tr>
	            <td><span style=" text-align:right;">
	            <input id="lineBtn" type="button" onclick = "createLine2()" value="참조인"></span></td>  
	               <td>
	                 <div style="text-align: left;">
						<table id="approvLine2"></table></div></td>
						</tr>
						</table>	
	<!--                   <td><div id="mailWriteTarget_Div2"> -->
	<!--                   </div></td> -->
	               <hr id="hr3_3">	      
<!--                <tr> -->
<!--                   <td><input type="button" value="받는사람"></td> -->
<!--                   <td><input type="text" name="receiverId" id="receiverId"></td>             -->
<!--                   <td><input type="button" value="참조인"></td> -->
<!--  				  <td><input type="text" name="carbonId" id="carbonId"></td>             -->
<!--                </tr> -->
            
                 <span id="spansend">첨부파일</span>
	                     &nbsp;&nbsp;<input multiple="multiple"type="file" name="fileName" value="내PC" id="file">
	                  <hr id="hr3_3">	
	             <label id="label">제목</label>&nbsp;<input id="mailtitle"type="text" name="title" class="title" value="${InboxViewSend.title}">
          	<br>
            <br>
            <textarea id="summernote" rows="30" cols="111" name="content" class="content">
            	<br>
            	-----Original Message-----<br>
            	보낸이:${InboxViewSend.empName}<br>
            	받는이:<c:forEach items="${sendInboxViewReceive}" var="sendInboxViewReceive">${sendInboxViewReceive.empName};&nbsp;</c:forEach><br>
            	참조인:<c:forEach items="${sendInboxViewCarbon}" var="sendInboxViewCarbon">${sendInboxViewCarbon.empName};&nbsp;</c:forEach><br>
            	제목:${InboxViewSend.title}<br>
            	내용:${InboxViewSend.content}
            </textarea>
            
           <input type="hidden" name="count" value="count">
            <span>
            <a href="#" id="more1" 
				onclick="createModal()">
				 첨부파일</a></span>
				 <div id="fileModal">
				 </div>
	       		<div id = "chart_div" style = "display : none; width: 500px; height: 450px;">
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
	                                          
	                                        <input type = "button" id = "closeBtn1" value = "닫기" onclick = "closeToLine();">
	                                        <input type = "button" id = "chartBtn" value = "완료" onclick = "makeToLine();">
	                                     
	                                    </div>
	                                    
	         
	         	 
	       		<div id = "chart_div2" style = "display : none; width: 500px; height: 450px;">
	                                         <div id = "chart2" align = "center">
	                                             <ul style = "display : inline-block">
	                                                <li>
	                                                   <img src = "${contextPath}/img/dir.png"><a onclick = "showChart2('m','mChart2')">경영/기획부</a>
	                                                   <ul id = "mChart2"></ul>
	                                                </li>
	                                                <li>
	                                                   <img src = "${contextPath}/img/dir.png"><a onclick = "showChart2('b','bChart2')">영업부</a>
	                                                   <ul id = "bChart2"></ul>
	                                                </li>
	                                                <li>
	                                                   <img src = "${contextPath}/img/dir.png"><a onclick = "showChart2('i','iChart2')">IT부</a>
	                                                   <ul id = "iChart2"></ul>
	                                                </li>
	                                                <li>
	                                                   <img src = "${contextPath}/img/dir.png"><a onclick = "showChart2('f','fChart2')">재무부</a>
	                                                   <ul id = "fChart2"></ul>
	                                                </li>
	                                             </ul>
	                                          </div>
	                                          
	                                          <img src = "${contextPath}/img/right.JPG" id = "rightPointer2" onclick = "moveToRight2();">
	                                          <img src = "${contextPath}/img/left.JPG" id = "leftPointer2" onclick = "moveToLeft2();">
	                                          
	                                       <div id = "selected2" align = "center">
	                                          <ul id = "emps2">
	                                          </ul>
	                                       </div>
	                                          
	                                        <input type = "button" id = "closeBtn12" value = "닫기" onclick = "closeToLine2();">
	                                        <input type = "button" id = "chartBtn2" value = "완료" onclick = "makeToLine2();">
	                                     
	                                    </div>
				 
				 
				 
				 
				 
				 
				 
            </form>
         </div>
      </div>
   
   </div>
<%-- 		${uploadFromMail2.fileNo}<br> --%>
<%-- 		${uploadFromMail2.fileName} --%>

	<!-------------------------------------바디---------------------------------------------------------------------------->
	
</body>
</html>
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
   href="${contextPath}/css/mail_write.css">
   <link rel="stylesheet" type="text/css"
	href="${contextPath}/css/main.css">	
<link
   href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css"
   rel="stylesheet">
<link
   href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css"
   rel="stylesheet">
<script type="text/javascript" src="${contextPath}/js/main.js"></script>
<script type="text/javascript" src="${contextPath}/js/open_win.js"></script>
<script
   src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script type = "text/javascript"
   src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
<script type = "text/javascript"
   src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>

<script type="text/javascript" src="${contextPath}/lang/summernote-ko-KR.js"></script>
<script type="text/javascript" src="${contextPath}/js/ko_kr_true.js"></script>
<script type="text/javascript" src = "${contextPath}/js/mail_draft_relay_reply_delete.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<script type="text/javascript">
// $(function(){
// 	 $("#draftsInbox_click").on("click",function(){
// 	  alert("메일 저장 이벤트1 !!!!!!");
// 	  var context = $("#context").val();
	  
// 	  //alert($(".note-editable panel-body p").val());
// 	//  alert(($(".note-editable panel-body").children('p')).val());
// 	//   alert($("#mail_title").val());
// 	//   alert($("#mail_content").val());
// 	//   alert($("#receiverId").val());
// 	   var data={}
// 	   data["senderId"] = $("#senderId").val();
// 	   data["receiverId"] = $("#receiverId").val();
// 	   data["carbonId"] = $("#carbonId").val();
// 	   data["title"] = $(".title").val();
// 	   data["mailNo"] = $("#mailNo").val();
// 	   data["content"] = $(".content").val();
// 	   data["writeDate"] = $("#writeDate").val();
// 	   alert("메일 저장 이벤트2 !!!!!!");
	  
// 	   $.ajax({
// 	    url : context+"/mail/mail_Drafts_relay_reply",
// 	    type : "post",
// 	    data : data,
// 	    dataType : "json",
// 	    success : function(data){
// 			alert(data);
// 	    	if(data){
// 	    		getlocation();
// 	    	}
// 	    }
// 	   });
// 	 return false;
// 	 });
// 	});
// 	function getlocation(){

// 		 var context = $("#context").val();
// 		 location.href=context+"/mail/mail_main_form";
// 	}
	
	</script>
<script type="text/javascript" src = "${contextPath}/js/main.js"></script>
<title>E-Mail 전달</title>
</head>
<body>
	<jsp:include page="/jsp/pageHeader.jsp"></jsp:include>
	<!-------------------------------------헤더--------------------------------------------------------->
	<div id="content">
		<input type="hidden" value="${contextPath}" id="context">
	
		 <div id="menuBox2">
         <p style="margin: 10px">
            <a href="${contextPath}/mail/mail_Write_form" style="font-size: 20px;">메일 쓰기</a>
         </p>
         <hr id="hr3">
         <br>
         <div id="button_send" style="float: left">
         <form action="mail_write" method="post">
         	
          <input type="hidden" value="${myWriteinboxView.mailstatus}" name="mailstatus" id="mailstatus" >
		 <input type="hidden" value="${myWriteinboxView.mailNo}" name="mailNo" id="mailNo" >
         <input type="hidden" value="${myWriteinboxView.writeDate}" name="writeDate" id="writeDate" >
         <input type = "hidden" name = "senderId" value = "${user.empNo}" id="senderId">
        	<input type="submit" value="전송" style="background-color: white !important;
    												border: 1px solid #fb2525 !important;
    												color: black !important;
    												padding: 5px 5px !important">
            <input type="button" name="임시저장" value="임시저장" id="draftsInbox_click"><a href="${contextPath}/mail/mail_myWrite_form"> <img
               src="${contextPath}/img/recycle.png"><small
               style="color: #fb2525;">내게쓰기</small></a> <br>
            <table>
               <tr>
                  <td><input type="button" value="받는사람"></td>
                  <td><input type="text" name="receiverId" id="receiverId"></td>            
                  <td><input type="button" value="참조인"></td>
 				  <td><input type="text" name="carbonId" id="carbonId"></td>            
               </tr>
               <tr>
                  <td colspan="2" style="text-align: left !important;"><label id="label">첨부파일</label>
                     &nbsp;&nbsp;<input type="button" name="name" value="내PC"></td>
                  <td><label id="label">제목</label></td>
                  <td><input type="text" name="title" class="title" value="${myWriteinboxView.title}"></td>
               </tr>
               
            </table>
          
            <br>
            <textarea id="summernote" rows="30" cols="111" name="content" class="content">
            	<br>
            	-----Original Message-----<br>
            	보낸이:${myWriteinboxView.empName}<br>
            	제목:${myWriteinboxView.title}<br>
            	날짜:${myWriteinboxView.writeDate}<br>
            	내용:${myWriteinboxView.content}
            </textarea>
           
            <br> <br>
            </form>
         </div>
      </div>
   </div>



	<!-------------------------------------바디---------------------------------------------------------------------------->
	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/main.css">	
<link rel="stylesheet" type="text/css" href="${contextPath}/css/mail.css">
  
<script src='${contextPath}/fullcalendar/jquery.min.js'></script>
<title>E-Mail 임시보관함</title>
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
 <script type="text/javascript" src = "${contextPath}/js/mail.js"></script>
</head>
<%-- <script type="text/javascript" src = "${contextPath}/js/main.js"></script> --%>
<script type="text/javascript" src = "${contextPath}/js/mail_draftInbox_delete.js"></script>
<script type="text/javascript">
// $(function(){
// 	 $("#mail_delete_send").on("click",function(){
// 		  alert("메일 삭제1 !!!!!!");
// 		  var context = $("#context").val();
		  
// 		  //alert($(".note-editable panel-body p").val());
// 		//  alert(($(".note-editable panel-body").children('p')).val());
// 		//   alert($("#mail_title").val());
// 		//   alert($("#mail_content").val());
// 		//   alert($("#receiverId").val());
			
			
			
			
// 		   $("input[type=checkbox]:checked").each(function(){
			   
// // 			   if($(this).val()=="on"){
// // 				   continue;
// // 			   }
// 				alert($("#context").val()+"/mail/mail_delete_draftInbox?mailNo="+$(this).val());
// 			   $.ajax({
// 				    url : $("#context").val()+"/mail/mail_delete_draftInbox?mailNo="+$(this).val(),
// 				    type : "post",
// 				    dataType : "json",
// 				    success : function(data){
// 						alert(data);
// 				    	if(data){
// 				    		getlocation();
// 				    	}
// 				    }
// 		   		});
// 		   });
// 		 return false;
// 		 });
// 		});
// 		function getlocation(){

// 			 var context = $("#context").val();
// 			 location.href=context+"/mail/mail_main_form";
// 		}

	</script>
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
				<a href="${contextPath}/mail/mail_draftsInbox_form" style="font-size: 20px;">임시보관함 </a>
			</p>
			<hr id="hr3">
			<div id="button_delete">
			<form action="" method="post">
			<c:forEach items="${viewData.draftList}" var="draftsinbox">	
				<input type="hidden" value="${draftsinbox.mailstatus}" name="mailstatus" id="mailstatus" >
		 		<input type="hidden" value="${draftsinbox.mailNo}" name="mailNo" id="mailNo" >
         		<input type="hidden" value="${draftsinbox.writeDate}" name="writeDate" id="writeDate" >
         		<input type="hidden" value="${draftsinbox.s}" name="s" id="s">
			</c:forEach>
				<table>
				<tr>
				    <th>받는사람</th>
					<th>제목</th>
					<th>작성일</th>
					<th><input type="checkbox" name="allCheck" id="allCheck"></th>
				    </tr>
				   <c:forEach items="${viewData.draftList}" var="draftsinbox">
				    <tr>
				    	<td>${draftsinbox.empName}</td>
				    	<c:choose>
				    	<c:when test="${draftsinbox.mailstatus eq '6'}">
				    	<td><a href="mail_draftsInbox_view_form?mailNo=${draftsinbox.mailNo}">${draftsinbox.title}</a></td>
				    	</c:when>
				    	<c:otherwise>
				    	<td><a href="mail_mydraftsInbox_view_form?mailNo=${draftsinbox.mailNo}">${draftsinbox.title}</a></td>
				    	</c:otherwise>
				    	</c:choose>
				    	<td>${draftsinbox.writeDate}</td>
				    <td><input type="checkbox" name="checkbox" value="${draftsinbox.mailNo}-${draftsinbox.s}" ></td>
				    </c:forEach>
				</table>
				<input type="button" name="삭제" value="삭제" id="mail_delete_send" class="mail_delete">
			<br>
			</form>
			</div>
				<div id="rnum">
				<table>
				<tr style="border-bottom: solid 0px gainsboro;">
				<td colspan="5" style="padding: 5px;font-size:16px;"><c:if test="${viewData.startPage !=1 }">
						<a href="mail_draftsInbox_form?page=1
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[처음]</a>
						<a href="mail_draftsInbox_form?page=${viewData.startPage-1}
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[이전]</a>
					</c:if> <c:forEach var="pageNum" begin="${viewData.startPage}"
						end="${viewData.endPage < viewData.pageTotalCount ? viewData.endPage : viewData.pageTotalCount}">
						<c:choose>
							<c:when test="${pageNum == viewData.currentPage}">
								<b id="pageNum">${pageNum}</b>
							</c:when>
							<c:otherwise>
								<a href="mail_draftsInbox_form?page=${pageNum}
									<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
									<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								">${pageNum}</a>
							</c:otherwise>
						</c:choose>

					</c:forEach> <c:if test="${viewData.endPage < viewData.pageTotalCount}">
						<a href="mail_draftsInbox_form?page=${viewData.endPage+1}
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[다음]</a>
						<a href="mail_draftsInbox_form?page=${viewData.pageTotalCount}
							<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
							<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
						">[마지막]</a>
					</c:if></td>
			</tr>
			</table>
			</div>
				<br>
				<br>
				<div id="search_id">
				<form action="mail_draftsInbox_form">
				<select name="type" id="search_id2">
    			<option value="1">제목</option>
    			<option value="2">받는이</option>
<!--     			<option value="3">제목+받는이</option> -->
    			<option value="3">날짜</option>
				</select>
				<input type="text" name="keyword" id="input_text">
				<input type="submit" id="sch_smit">
				</form>	
		</div>
			
		</div>
		</div>
	

	<!-------------------------------------바디---------------------------------------------------------------------------->
	
</body>
</html>
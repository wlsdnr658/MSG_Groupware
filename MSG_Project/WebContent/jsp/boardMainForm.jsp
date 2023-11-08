<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="https://code.jquery.com/jquery-3.3.1.min.js"
      integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
      crossorigin="anonymous">
</script> 

<link rel="stylesheet" type="text/css" href="${contextPath}/css/board.css"> 

<script type="text/javascript" src="${contextPath}/js/boardSubMenu.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardDeptAuth.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>게시판 메인</title>
</head>

<body>
   <jsp:include page="/jsp/pageHeader.jsp" flush="false" />
   <!-------------------------------------헤더--------------------------------------------------------->
   <input type="hidden" id="path" value="${contextPath}">
   <div id="content">
      
      <jsp:include page="/jsp/pageSide.jsp">
         <jsp:param value="board" name="category"/>
      </jsp:include>
      
      <input type="hidden" value="${user.deptNo}" id="inputDeptNo">
      <input type="hidden" value="${user.empPos}" id="inputEmpPos">

      <div id="menuBox2" style="width: 750px">
         <p style="margin: 20px">
            <a href="#" style="font-size: 20px;">공지사항</a>
         </p>
         <hr id="hr3">

         <p style="margin-bottom: 10px; margin-top: 30px;">
            <a href="${contextPath}/board/boardNotice" style="font-size: 20px; margin-left: 30px;">전체공지</a>
            <a href="${contextPath}/board/boardNotice" style="margin-left: 597px; font-weight: normal; font-size: 12px; text-decoration: underline;">더보기</a>
         </p>

         <div id="noticeBox">
            <ul>
            
               <c:forEach end="3" items="${noticeList}" var="notice">
                  <li class="noticeBoxLi" style="padding:19px 5px">
                     <div>
                        <strong style="font-size: 15px" id="strong1">공지</strong> 
                        <strong style="font-size: 15px" id="strong2"></strong>
                     </div>
                     <ul id="noticeBoxUl1">
                        <li style="font-size: 13px" id="li1">${notice.empName}</li>
                        <li style="font-size: 13px" id="li2">${notice.writeDate}</li>
                        <li style="font-size: 13px" id="li3">조회수 ${notice.viewCount}</li>
                     </ul>
                     <p class="pTag" style="margin-top: 3px;">
                        <a style="font-size: 16px;padding-left: 17px" href="${contextPath}/board/boardNoticeViewForm?boardNo=${notice.boardNo}" name="TITLE">${notice.title}</a>
                     </p>
                  </li>
               </c:forEach>
               
            </ul>
         </div>
         
         <div style=" width: 750px; height: 500px; position: absolute; margin-top: 200px;margin-bottom: 100px">  
            
            <p style="margin: 20px; margin-bottom: 10px; margin-top: 20px; margin-right: 0;">
               <a href="${contextPath}/board/boardEvent" style="font-size: 20px; margin-left: 15px;">EVENT</a>
               <a href="${contextPath}/board/boardEvent" style="margin-left: 600px; font-weight: normal; font-size: 12px; text-decoration: underline;">더보기</a>
            </p>
            
            <div id="noticeBox">
            
               <c:forEach end="3" items="${eventList}" var="event">
                  <li class="noticeBoxLi" style="padding:19px 5px">
                     <div>
                        <strong style="font-size: 15px; color: black" id="strong1">일반</strong> 
                        <strong style="font-size: 15px" id="strong2">${event.boardNo}</strong>
                     </div>
                     <ul id="noticeBoxUl1">
                        <li style="font-size: 13px" id="li1">${event.empName}</li>
                        <li style="font-size: 13px" id="li2">${event.writeDate}</li>
                        <li style="font-size: 13px" id="li3">조회수 ${event.viewCount}</li>
                     </ul>
                     <p class="pTag" style="margin-top: 3px">
                        <a style="font-size: 16px;padding-left: 17px" href="${contextPath}/board/boardEventViewForm?boardNo=${event.boardNo}" name="TITLE">${event.title}</a>
                     </p>
                  </li>
               </c:forEach>

            </div>

         </div> 
<!--          //이벤트 박스 마지막 -->

<!--          <hr id="hr4" style="margin-top: 850px;margin-bottom: 100px"> -->
         
      </div>



   </div>
   <!-------------------------------------바디--------------------------------------------------------->
   <!-------------------------------------푸터--------------------------------------------------------->

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>

<!-- include summernote css/js -->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>
<script type="text/javascript" src="${contextPath}/lang/summernote-ko-KR.js"></script>

<link rel="stylesheet" type="text/css" href="${contextPath}/css/board.css">
<script type="text/javascript" src="${contextPath}/js/boardSummerNote.js"></script>
<script type="text/javascript" src="${contextPath}/js/boardFileBox.js"></script>
<script type="text/javascript" src="${contextPath}/js/fileDown1.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<title>업무보기</title>
</head>
<body>

	<div align="center">
	
		<input type="hidden" id="path" value = "${contextPath}">
		<input type="hidden" id="taskNoinput" value="${task.taskNo}">
			
			<div style="width: 100%; border-top: 2px solid #fb2525; border-bottom: 1px solid #fb2525">
				<h2>업무 보기</h2>
			</div>
			
			<table style="margin: 0 auto; border:1px solid black;width: 650px; height: 600px; margin-top: 40px">
			
				<tr style="border-top: 1px solid white;border-left: 1px solid white; border-right: 1px solid white"> 
					<td colspan="4" style="border-top: 1px solid white; text-align: right">
						
						<c:if test="${user.empPos eq '부장' || user.empPos eq '과장'}">
							<input type="button" id="updateBtn" value="수정하기" onclick="location.href='boardProjectOngoingAddTaskModifyForm/${task.taskNo}'">
						</c:if>
						
					</td>
				</tr>
			
				<tr style="border-right: 1px solid black; background-color: #f3f4f9; border-top: 1px solid black">
					<td colspan="4" style="text-align: center; font-size: 17px; font-weight: bold;border-bottom: 1px solid gray">
						업무상세 보기
					</td>
		        </tr>
		        
				<tr style="border-right: 1px solid black; border-bottom: 1px solid gray;">
					<th colspan="1" style="width: 70px;padding-left: 10px;background-color: #f3f4f9;border-right: 1px solid gray">
						업무명
					</th>
					<td colspan="3" style="width: 450px;text-align: center;">
						${task.taskName}
					</td>
				</tr>
			
				<tr style="border-right: 1px solid black; border-bottom: 1px solid gray">
				
					<th colspan="1" style="padding-left: 10px;background-color: #f3f4f9;border-right: 1px solid gray">
						시작일
					</th>
					
					<td colspan="1" style="text-align: center;"> 
						${task.tStartDate1}
					</td>
					
					<th colspan="1" style="padding-left: 15px;background-color: #f3f4f9;width: 70px;border-right: 1px solid gray;border-left: 1px solid gray"> 
						마감일
					</th> 
					
					<td colspan="1" style="text-align: center;"> 
						 ${task.tEndDate1}
					</td> 
					
				</tr>  
			
				<tr style="border-right: 1px solid black; width: 650px;border-bottom: 1px solid gray">
					<th colspan="4" style="padding-left: 10px;background-color: #f3f4f9;text-align: center;">
						업무내용
					</th>
				</tr>   
				
				<tr style="border-right: 1px solid black; width: 650px;border-bottom: 1px solid gray">
					<td colspan="4" style="width: 500;padding-top: 15px;padding-bottom: 15px">
						<div style="overflow: auto; height: 400px;width:600px;border: 1px solid gray; padding: 20px; margin: 0 auto">${task.taskContent}</div>
					</td>
				</tr>
				
				<tr style="border-right: 1px solid black">
					<th colspan="1" style="background-color: #f3f4f9;text-align: center;border-right: 1px solid gray">
						첨부파일
					</th>
					<td colspan="3" style="height: 30px">
						
						<c:if test="${task.uploadFile == 'Y'}">
							<span>
								<a href="#" onclick="createModal()">첨부파일 링크</a>
							</span>
						</c:if>
						
						<div id="fileModal3" style="display: none;"></div>
					</td>
				</tr>
				
			</table>
					
					
	</div>



</body>
</html>



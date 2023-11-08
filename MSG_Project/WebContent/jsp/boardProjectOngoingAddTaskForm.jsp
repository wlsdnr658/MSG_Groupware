<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<style type="text/css">
.filebox input[type="file"] {
	position: absolute;
	width: 1px;
	height: 1px;
	padding: 0;
	margin: -1px;
	overflow: hidden;
	clip: rect(0, 0, 0, 0);
	border: 0;
}

.filebox label {
	display: inline-block;
	padding: .5em .75em;
	color: silver;
	font-size: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #fdfdfd;
	cursor: pointer;
	border: 1px solid #ebebeb;
	border-bottom-color: #e2e2e2;
	border-radius: .25em;
} /* named upload */
.filebox .upload-name {
	display: inline-block;
	padding: .5em .75em; /* label의 패딩값과 일치 */
	font-size: inherit;
	font-family: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #f5f5f5;
	border: 1px solid #ebebeb;
	border-bottom-color: #e2e2e2;
	border-radius: .25em;
	-webkit-appearance: none; /* 네이티브 외형 감추기 */
	-moz-appearance: none;
	appearance: none;
}
</style>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<head>
<title>업무추가</title>
</head>
<body>

	<div align="center">
	
		<table style="width: 100%; margin-top: 0; border-top: 3px solid #fb2525">
		
			<tr>
				<td style="text-align: center; border-bottom: 1px solid #fb2525;">
					<h2>업무 추가</h2>
				</td>
			</tr>
			
			<tr>
				<td style="text-align: center;">
				
					<form action="boardProjectOngoingAddTask" name ="frm" method = "post" style="border: 1px solid silver;text-align: left; 
								margin-left: 100px; margin-right: 100px; margin-top: 50px; margin-bottom: 50px;" enctype="multipart/form-data">
						
						<input type="hidden" name ="boardNo" value = "${projectOngoing.boardNo}">
						  
						<p style="background-color: #e0d8d8; height:30px;padding-top:5px;padding-bottom: 10px;text-align: center;"><b style="font-size: 15px">업무추가 작성</b></p>
						
						<p style="padding: 10px">
							<label for="taskName">업무명:</label> 
							<input id="taskName" name="TASKNAME" type="text" style="width: 300px" placeholder="업무명을 입력하세요."><b style="font-size: 12px">*업무명을 입력하세요(필수)</b>  
						</p> 
						
						<p style="padding-left: 10px">
							
							<span>
								<label for="TSTARTDATE1">시작일:</label> 
								<input id="TSTARTDATE1" name="TSTARTDATE" class="aa" type="date">
							</span>
							
							<span style="margin-left: 70px">
								<label for="TENDDATE1">마감일:</label> 
								<input id="TENDDATE1" name="TENDDATE" class="aa" type="date">
							</span>
							
						</p>
						
						<p style="padding: 10px;"> 
							<label for="taskName">업무내용</label> 
							<textarea id="taskContent" name="TASKCONTENT" style="margin-left: 10px;height: 200px"  class="summernote" cols="80" rows="5"></textarea>
						</p> 
						
						<div class="filebox">
							<input class="upload-name" value="파일선택" disabled="disabled">
							<label for="ex_filename">업로드</label>
							<input multiple="multiple" type="file" name="uploadFile" id="ex_filename" class="upload-hidden"/>
						</div> 
						  
						<p style="text-align: center; padding-bottom: 10px">
							<input id="submitBtn" type="submit" value="확인">
						</p>
						
					</form>
					
				</td>
			</tr>
			
		</table>
	</div>
	${msg}
</body>
</html>








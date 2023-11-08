<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%request.setAttribute("contextPath", request.getContextPath());%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/board.css">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 확인</title>
</head>
<body>
	<!-- 비밀번호 확인 화면 -->
	<!-- 사용자에게 비밀번호를 입력받아서 서버로 전달 
	현재 페이지 호출 요청에는 파라미터에 게시글 번호가 포함되어 있음
	-->
	<div align="center">
		<table style="width: 100%; margin-top: 0; border-top: 3px solid #fb2525">
		
			<tr>
				<td style="text-align: center; border-bottom: 1px solid #fb2525;">
					<h2>비밀번호 확인</h2>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<form action="boardDeptMinCheckPass" name ="frm" method = "post">
						<input type="hidden" name ="boardNo" value = "${min.boardNo}">
						
						<table style="width: 100%;">
							<tr>
								<th style="text-align: center;">비밀번호</th>
								<td><input type="password" name="empPw" size="20"></td>
							</tr>
						</table>
						
						<input id="submitBtn" type="submit" value="확인">
					</form>
				</td>
			</tr>
			
		</table>
	</div>
	${msg}
</body>
</html>


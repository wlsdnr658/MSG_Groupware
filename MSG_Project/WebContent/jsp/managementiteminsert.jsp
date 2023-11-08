<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
	request.setAttribute("contextPath", request.getContextPath());
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>


<script type="text/javascript" src="${contextPath}/js/managementiteminsert.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>등록</title>
</head>



<link rel="stylesheet" type="text/css" href="${contextPath}/css/admin.css">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">

<body style="overflow-x:hidden; overflow-y : hidden;">

<div class="wrap" align = "center">
		<h1>자원 등록</h1>
		<form action="insertitem" id="writeForm" enctype="multipart/form-data" method="post">
			<table>
				<tr>
					<th>분류:</th>
					<td><select name="itemNo">
						<option value="1">자동차</option>
						<option value="2">회의실</option>
					</select>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td><input type="file" name="fileName" id="fileLink"></td>
				</tr>
				<tr>
					<th>종류</th>
						<td><input type="text" name="typeName"></td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input type="text" name="listName"></td>
				</tr>
				
			</table>
			<br>
			<input type="submit" value="등록">
			<input type="reset" value="다시작성"> 
			<input type="button" value="목록" onclick="location.href='managementitemcreate'">
		</form>
	</div>





</body>
</html>
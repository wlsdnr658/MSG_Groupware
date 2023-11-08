
<%@page import="org.apache.naming.java.javaURLContextFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	request.setAttribute("contextPath", request.getContextPath());
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>





<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>


<script type="text/javascript" src="${contextPath}/js/managementcarreservation.js"></script>



<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>

<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/reservation.css">
	<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<body>


<input type="hidden" id="contextPath" value="${contextPath}">

	<input type="hidden" id="chkreserv" value="N" />

	
	


	<form id=Enrollment method="post">

		<table>
			<tr>
				<th>종류</th>
				<td><select class="aa" name="typeName" id="typeName"
					style="height: 35px; width: 225px;">

						<option value="소형">소형</option>
						<option value="중형">중형</option>
						<option value="대형">대형</option>
				</select></td>

				<th>차 종류</th>
				<td><select class="aa" name="carName" id="carName">

					<option value="모닝">모닝</option>
					<option value="쏘나타">쏘나타</option>
	
					
				</select></td>



				<th>달력</th>
				<td><input class="aa" type="datetime-local" name="startDate" id="startDate" ></td>

			</tr>


			<tr>


				<th>달력</th>
				<td><input class="aa" type="datetime-local" name="endDate" id="endDate"> 
				</td>
				<th>사용자</th>

				<td class="aa"><input type="text" id="assigned" name="assigned"
					value="${user.empNo}"></td>

				<td><input type="submit" value="등록"></td>
			</tr>
		</table>


	</form>



</body>
</html>
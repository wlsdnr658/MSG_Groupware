<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="<%=request.getContextPath()%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Form</title>
<link rel="stylesheet" type="text/css" href="${path}/css/loginStyle.css">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic&amp;subset=korean" rel="stylesheet">
<script type="text/javascript" src="${contextPath}/js/particles.js"></script>
<style type="text/css">
#particles-js {
	position: absolute;
	width: 100%;
	height: 100%;
/* 	background: linear-gradient(to bottom, rgba(200, 200, 200, 0.1) 10%, */
/* 		rgba(1000, 1000, 1000, 0.1) 30%, rgba(1000, 1000, 1000, 0.1) 50%, */
/* 		rgba(200, 200, 200, 0.1) 80%, rgba(200, 200, 200, 0.1) 100%), */
/* 		url(https://38.media.tumblr.com/tumblr_m00c3czJkM1qbukryo1_500.gif); */
	background: linear-gradient(to bottom, rgba(117, 114, 113, 0.8) 10%, rgba(40, 49, 77, 0.8) 30%, 
				rgba(29, 35, 71, 0.8) 50%, rgba(19, 25, 28, 0.8) 80%, rgba(15, 14, 14, .8) 100%), 
				url(https://38.media.tumblr.com/tumblr_m00c3czJkM1qbukryo1_500.gif);
	background-repeat: no-repeat;
	background-size: cover; 
	background-position: 50% 50%;
}
</style>

</head>

<body>

	<div class="loginbox" style="position: absolute; z-index: 1">
		<img src="${path}/img/symbol.jpg" class="avatar">
		<h1>로그인</h1>
		<form action="${path}/emp/userLogin" method="post">
			<p>Employee ID</p>
			<input type="text" name="UID" placeholder="Enter ID" autofocus>
			<p>Password</p>
			<input type="password" name="UPW" placeholder="Enter Password">
			<input type="submit" value="Login">
		</form>
	</div>

	<div id="particles-js" class ="particlesJS"></div>

</body>
</html>

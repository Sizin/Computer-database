<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/resources/css/bootstrap.min.css"/>"
	rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/font-awesome.css"/>"
	rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet"
	media="screen">
</head>
<body>
	<jsp:include page="/WEB-INF/views/layout/navbar.jsp" />

	<form name="login" action='<spring:url value="/login"/>' method="POST">
		<p>
			<label for="username"><spring:message code="Username" /></label> <input
				type="text" id="username" name="username" />
		</p>
		<p>
			<label for="password"><spring:message code="Password" /></label> <input
				type="password" id="password" name="password" />
		</p>
		<button type="submit" class="btn btn-primary">
			<spring:message code="LogIn" />
		</button>
	</form>
	
	<script src="js/jquery.min.js"></script>	
	<script src="js/bootstrap.min.js"></script>
	<script src="js/navbar.js"></script>
	<script src="js/dashboard.js"></script>
	
</body>
</html>
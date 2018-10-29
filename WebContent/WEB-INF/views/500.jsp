<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
	<link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet" media="screen">
	<link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
	<jsp:include page="/WEB-INF/views/layout/navbar.jsp" />

	<section id="main">
		<div class="container">	
			<div class="alert alert-danger">
				<spring:message code="lang.500.message"/>
				<br/>
				<!-- stacktrace -->
			</div>
		</div>
	</section>

	<script src="<c:url value="/resources/js/jquery.min.js"/>" ></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>
	<script src="<c:url value="/resources/js/navbar.js"/>"></script>
   	<script src="<c:url value="/resources/js/dashboard.js"/>"></script>

</body>
</html>
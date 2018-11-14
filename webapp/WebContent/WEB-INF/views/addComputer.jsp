<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/font-awesome.css"/>" media="screen">
<link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
	<jsp:include page="/WEB-INF/views/layout/navbar.jsp" />
	
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="lang.addComputer.title"/></h1>
					<form:form action="addComputer" method="POST" modelAttribute="computer">
						<fieldset>
							<div class="form-group">
								<form:label for="computerName" path="name"><spring:message code="lang.addComputer.name"/></form:label> 
								<form:input path="name" type="text" class="form-control" name="computerName" placeholder="Computer name" />
							</div>
							<div class="form-group">
								<form:label path="introduced" for="introduced"><spring:message code="lang.addComputer.introduced"/></form:label> 
								<form:input type="date" class="form-control" name="introduced" placeholder="Introduced date" path="introduced"/>
							</div>
							<div class="form-group">
								<form:label path="discontinued" for="discontinued"><spring:message code="lang.addComputer.discontinued"/></form:label> 
								<form:input type="date" class="form-control" name="discontinued"	placeholder="Discontinued date" path="discontinued"/>
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="lang.addComputer.company"/></label>
								<input type="hidden" name="nbCompany" value="${nbCompany}" />
								<form:select path="company.id" class="form-control" name="companyId" id="companyId">
									<option selected="selected" value="0"><spring:message code="lang.addComputer.noCompany"/></option>
								 	<c:forEach items="${companies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>									
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="lang.addComputer.add"/>" class="btn btn-primary">
							<spring:message code="lang.addComputer.or"/>
							<a href="/webapp/Dashboard" class="btn btn-default"><spring:message code="lang.addComputer.cancel"/></a>
						</div>
					</form:form>

				</div>
			</div>
		</div>
	</section>
	
	
	<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/resources/js/navbar.js"/>"></script>
	
	
	
</body>
</html>
<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
					<div class="label label-default pull-right">
						id:
						<c:out value="${computer.id}" />
					</div>
					<h1><spring:message code="lang.editComputer.title"/></h1>

					<form:form action="editComputer" method="POST" modelAttribute="computer">
						<form:hidden path="id" value="${computer.id}" name="computerId" id="computerId" />
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<form:label path="name" for="computerName"><spring:message code="lang.editComputer.name"/></form:label> 
								<form:input path="name" type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name" value="${computer.name}" />
							</div>
							<div class="form-group">
								<form:label path="introduced" for="introduced"><spring:message code="lang.editComputer.introduced"/></form:label>
								<form:input path="introduced" type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value="${computer.introduced}" />
							</div>
							<div class="form-group">
								<form:label path="discontinued" for="discontinued"><spring:message code="lang.editComputer.introduced"/></form:label>
								<form:input path="discontinued" type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="${computer.discontinued}"/>
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="lang.editComputer.company"/></label>
								<form:select path="company.id" class="form-control" name="companyId" id="companyId">
								<option value="0"><spring:message code="lang.editComputer.noCompany"/></option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}"
											<c:if test="${not empty computer.company && computer.company.id == company.id}"> selected="selected"</c:if>>
											${company.name}</option>
									</c:forEach>
								</form:select>

							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="lang.editComputer.edit"/>" class="btn btn-primary">
							<spring:message code="lang.editComputer.or"/> <a href="/CdbSinan/Dashboard" class="btn btn-default"><spring:message code="lang.editComputer.cancel"/></a>
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
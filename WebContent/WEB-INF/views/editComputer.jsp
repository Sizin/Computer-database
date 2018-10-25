<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="Dashboard"> Application - Computer
				Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						id:
						<c:out value="${computer.id}" />
					</div>
					<h1>Edit Computer</h1>

					<%-- <form action="editComputer" method="POST">
						<input type="hidden" value="${computer.id}" name="computerId" id="computerId" />
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName" name="computerName"
									placeholder="Computer name" value="${computer.name}" />
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced" name="introduced"
									placeholder="Introduced date" value="${computer.introduced}" />
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued" name="discontinued"
									placeholder="Discontinued date"
									value="${computer.discontinued}">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<select class="form-control" name="companyId" id="companyId">
								<option value="0"> - No company - </option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}"
											<c:if test="${not empty computer.company && computer.company.id == company.id}"> selected="selected"</c:if>>
											${company.name}</option>


									</c:forEach>
								</select>

							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="/CdbSinan/Dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form> --%>
					<form:form action="editComputer" method="POST" modelAttribute="computer">
						<form:hidden path="id" value="${computer.id}" name="computerId" id="computerId" />
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<form:label path="name" for="computerName">Computer name</form:label> 
								<form:input path="name" type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name" value="${computer.name}" />
							</div>
							<div class="form-group">
								<form:label path="introduced" for="introduced">Introduced date</form:label>
								<form:input path="introduced" type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value="${computer.introduced}" />
							</div>
							<div class="form-group">
								<form:label path="discontinued" for="discontinued">Discontinued date</form:label>
								<form:input path="discontinued" type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="${computer.discontinued}"/>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<form:select path="company.id" class="form-control" name="companyId" id="companyId">
								<option value="0"> - No company - </option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}"
											<c:if test="${not empty computer.company && computer.company.id == company.id}"> selected="selected"</c:if>>
											${company.name}</option>
									</c:forEach>
								</form:select>

							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="/CdbSinan/Dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
					
					
					
					
				</div>
			</div>
		</div>
	</section>
</body>
</html>
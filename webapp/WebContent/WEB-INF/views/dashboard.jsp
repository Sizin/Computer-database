<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	<!-- Bootstrap -->
	
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="css/main.css" type="text/css" rel="stylesheet" media="screen">
	<link href="css/dashboard.css" type="text/css" rel="stylesheet" media="screen">

</head>

<body>
	<jsp:include page="/WEB-INF/views/layout/navbar.jsp" />

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${numberOfComputer} <spring:message code="lang.dashboard.computersFound"/></h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="Dashboard" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder='<spring:message code="lang.dashboard.searchHolder"/>' /> <input
							type="submit" id="searchsubmit" value='<spring:message code="lang.dashboard.filter"/>'
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="lang.dashboard.add"/></a> 
					<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="lang.dashboard.edit"/></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="DeleteComputer" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><spring:message code="lang.dashboard.columnName"/></th>
						<th><spring:message code="lang.dashboard.columnIntroduced"/></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="lang.dashboard.columnDiscontinued"/></th>
						<!-- Table header for Company -->
						<th><spring:message code="lang.dashboard.columnCompany"/></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${listComputer}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="/webapp/editComputer?computerId=${computer.id}">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td><c:out value="${computer.company.name}" /></td>

						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
		
			<ul class="pagination">
				<!-- First Page -->
				<c:if test="${currentPage != 1}">
					<li id="firstPage"><a> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				<!-- Previous page arrow -->
				
				<c:set var="rightOnly" scope="session" value="rightOnly" />
				<c:set var="none" value="none" /> 
				<c:if test="${arrowDisplay != rightOnly && arrowDisplay != none}">
					<li id="previousPage"><a id="previousPage">
					<span aria-hidden="true">&lt;</span>
					</a></li>
				</c:if>
				
 				<c:forEach begin="${currentStartPage}" end="${currentEndPage}" varStatus="paginationLoop">
					<li class="${currentPage == (paginationLoop.index) ? "page-item active" : "page-item"}"  <c:if test="${currentPage == (paginationLoop.index)}">id="currentPage"</c:if> value="${paginationLoop.index}">
						<a>
							${paginationLoop.index}
						</a>
					</li>
				</c:forEach>
			
			
				<!-- Next page arrow -->
				 <c:set var="leftOnly" value="leftOnly" /> 
				 <c:if test="${arrowDisplay != leftOnly && arrowDisplay != none}">
					<li id="nextPage"><a id="nextPage"> <span aria-hidden="true">&gt;</span>
					</a></li>
				</c:if>
				<!-- Last page arrows  -->
				<c:if test="${currentPage < numberOfPage}">
					<li id="lastPage" value=<c:out value="${numberOfPage}"/>>
						<a>
						<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
					
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" class="${resultPerPage == 10 ? "btn btn-default pageRange active" : "btn btn-default pageRange"}" id="range10" value="10">10</button>
				<button type="button" class="${resultPerPage == 50 ? "btn btn-default pageRange active" : "btn btn-default pageRange"}" id="range50" value="50">50</button>
				<button type="button" class="${resultPerPage == 100 ? "btn btn-default pageRange active" : "btn btn-default pageRange"}"	id="range100" value="100">100</button>
			</div>
		</div>
	</footer>

	<script src="js/jquery.min.js"></script>	
	<script src="js/bootstrap.min.js"></script>
	<script src="js/navbar.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>
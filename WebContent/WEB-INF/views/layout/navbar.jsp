<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<nav class="navbar navbar-inverse navbar-fixed-top">
	    <div class="container">
	        <div class="navbar-header">
	          <a class="navbar-brand" href="/CdbSinan/Dashboard"><spring:message code="lang.dashboard.title"/></a>
	        </div>
	        <div id="navbar" class="navbar-collapse collapse navbar-right">    
	        
	        
 			<ul class="nav navbar-nav">
	                <li class="dropdown">
	                    <a class="languageButton" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><span ><spring:message code="lang.locales.choose"/></span></a>
	                    <ul class="dropdown-menu" role="menu">
	            	        <li>
		            	        <a class="languageButton languageSelected" value="en"> 
		            	        	<img src="<c:url value="/resources/img/flag_en.png"/>" alt="flag" class="img-thumbnail icon-small">
		            	        	<span class="languageSelected" value="en"><spring:message code="lang.locales.en"/></span>
		            	        </a>
		            	    </li>
							<li>
		            	        <a class="languageButton languageSelected" value="fr"> 
		            	        	<img src="<c:url value="/resources/img/flag_fr.png"/>" alt="flag" class="img-thumbnail icon-small">
		            	        	<span class="languageSelected" value="fr"><spring:message code="lang.locales.fr"/></span>
		            	        </a>
		            	    </li>
	        	        </ul>
	                </li>
	            </ul>
	        </div>
	    </div>
	</nav>

	
	
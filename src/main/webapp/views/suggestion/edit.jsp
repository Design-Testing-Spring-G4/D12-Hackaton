<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="isAuthenticated()">

	<%-- Stored message variables --%>
	
	<spring:message code="suggestion.anonymous" var="anonymous" />
	
	<%-- Form --%>
	
	<form:form id="form" action="${requestURI}" modelAttribute="suggestion">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="competition" />
		
		<acme:textbox code="suggestion.title" path="title"/>
		<br/>
		<acme:textarea code="suggestion.comments" path="comments"/>
		<br/>
		<acme:textarea code="suggestion.attachments" path="attachments"/>
		<br/>
		
		<form:label path="anonymous">
			<jstl:out value="${anonymous}" />
		</form:label>
		<form:checkbox path="anonymous"/>
		<br/>
		
		<acme:submit name="save" code="suggestion.save"/>
	
	</form:form>
</security:authorize>
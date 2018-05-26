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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('AUDITOR')">

<%-- Stored message variables --%>

<spring:message code="audit.finalMode" var="finalMode" />

 <form:form action="${requestURI}" modelAttribute="audit">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="resort" />
	
	<acme:textbox path="title" code="audit.title" />
	<br/>
	<acme:textarea path="description" code="audit.description" />
	<br/>
	<acme:textarea path="attachments" code="audit.attachments" />
	<br/>
	<form:label path="finalMode">
		<jstl:out value="${finalMode}" />
	</form:label>
	<form:checkbox path="finalMode"/>
	<br/>
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="audit.save" />
	
	<jstl:if test="${audit.id != 0}">
		<acme:delete code="audit.delete" confirm="audit.confirm.delete"/>
	</jstl:if>
	
	<acme:cancel code="audit.cancel" url="audit/auditor/list.do" />

</form:form>

</security:authorize>
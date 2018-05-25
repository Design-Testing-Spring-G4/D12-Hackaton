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

<security:authorize access="hasRole('INSTRUCTOR')">

<%-- Stored message variables --%>

<spring:message code="lesson.schedule" var="schedule" />

<form:form action="${requestURI}" modelAttribute="lesson">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox path="name" code="lesson.name" />
	<br/>
	<acme:textbox path="description" code="lesson.description" />
	<br/>
	<form:label path="schedule">
		<jstl:out value="${schedule}" />
	</form:label>	
	<form:select path="schedule">
		<form:option label="DAILY" value="DAILY"/>
		<form:option label="WEEKLY" value="WEEKLY"/>
		<form:option label="MONTHLY" value="MONTHLY"/>
	</form:select>
	<form:errors cssClass="error" path="schedule" />
	<br/>
	<acme:textbox path="price" code="lesson.price" />
	<br/>
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="lesson.save" />
	
	<jstl:if test="${lesson.id != 0}">
		<acme:delete code="lesson.delete" confirm="lesson.confirm.delete"/>
	</jstl:if>
	
	<acme:cancel code="lesson.cancel" url="lesson/instructor/list.do" />

</form:form>

</security:authorize>
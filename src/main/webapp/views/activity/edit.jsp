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

<security:authorize access="hasRole('MANAGER')">

<%-- Stored message variables --%>

<spring:message code="activity.category" var="category" />

<form:form action="${requestURI}" modelAttribute="activity">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox path="title" code="activity.title" />
	<br/>
	<acme:textarea path="description" code="activity.description" />
	<br/>
	<form:label path="category">
		<jstl:out value="${category}" />
	</form:label>	
	<form:select path="category" >
		<form:option label="ENTERTAINMENT" value="ENTERTAINMENT"/>
		<form:option label="SPORT" value="SPORT"/>
		<form:option label="TOURISM" value="TOURISM"/>
	</form:select>
	<form:errors cssClass="error" path="category" />
	<br/>
	<acme:textbox path="price" code="activity.price" />
	<br/>
	<acme:select path="resort" code="activity.resort" items="${resorts}" itemLabel="name" />
	<br/>
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="activity.save" />
	
	<jstl:if test="${activity.id != 0}">
		<acme:delete code="activity.delete" confirm="activity.confirm.delete"/>
	</jstl:if>
	
	<acme:cancel code="activity.cancel" url="activity/manager/list.do" />

</form:form>

</security:authorize>
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

<%-- Stored message variables --%>

<spring:message code="legalText.finalMode" var="finalMode" />

<security:authorize access="hasRole('ADMIN')">

<form:form action="${requestURI}" modelAttribute="legalText">

	<%-- Form fields --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="registered" />
	
	<acme:textbox path="title" code="legalText.title" />
	<br/>
	<acme:textarea path="body" code="legalText.body" />
	<br/>
	<acme:textarea path="laws" code="legalText.laws" />
	<br/>
	<form:label path="finalMode">
		<jstl:out value="${finalMode}" />
	</form:label>
	<form:checkbox path="finalMode" />
	<br/>
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="legalText.save" />
		
	<jstl:if test="${legalText.id!=0}">
		<acme:delete code="legalText.delete" confirm="legalText.confirm.delete" />
	</jstl:if>	
	
	<acme:cancel code="legalText.cancel" url="legalText/administrator/list.do" />

</form:form>

</security:authorize>
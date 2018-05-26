<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('INSTRUCTOR')">

	<form:form action="${requestURI}" modelAttribute="personalRecord">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="curriculum" />

		<acme:textbox path="name" code="personalRecord.name" />
		<br />
		<acme:textbox path="photo" code="personalRecord.photo" />
		<br />
		<acme:textbox path="email" code="personalRecord.email" />
		<br />
		<acme:textbox path="phone" code="personalRecord.phone" placeholder="+CC 654654654" />
		<br />
		<acme:textbox path="profile" code="personalRecord.profile" />
		<br />

		<%-- Buttons --%>
		
		<acme:submit name="save" code="personalRecord.save" />
		
		<acme:cancel code="personalRecord.cancel" url="curriculum/instructor/display.do" />

	</form:form>

</security:authorize>



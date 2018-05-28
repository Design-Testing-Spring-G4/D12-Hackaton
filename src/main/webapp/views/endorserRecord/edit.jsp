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

	<form:form action="${requestURI}" modelAttribute="endorserRecord">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="curriculum" />

		<acme:textbox path="name" code="endorserRecord.name" />
		<br />
		<acme:textbox path="email" code="endorserRecord.email" />
		<br />
		<acme:textbox path="phone" code="endorserRecord.phone" placeholder="+CC 654654654" />
		<br />
		<acme:textbox path="profile" code="endorserRecord.profile" />
		<br />
		<acme:textarea path="comments" code="endorserRecord.comments" />
		<br />

		<%-- Buttons --%>
		
		<acme:submit name="save" code="endorserRecord.save" />
		
		<jstl:if test="${endorserRecord.id!=0}">
			<acme:delete code="endorserRecord.delete" confirm="endorserRecord.confirm.delete" />
		</jstl:if>
	
		<acme:cancel code="endorserRecord.cancel" url="curriculum/instructor/display.do" />

	</form:form>

</security:authorize>



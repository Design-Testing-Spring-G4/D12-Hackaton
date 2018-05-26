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

	<form:form action="${requestURI}" modelAttribute="educationRecord">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="curriculum" />

		<acme:textbox path="diploma" code="educationRecord.diploma" />
		<br />
		<acme:textbox path="periodStart" code="educationRecord.periodStart" placeholder="dd/MM/yyyy" />
		<br />
		<acme:textbox path="periodEnd" code="educationRecord.periodEnd" placeholder="dd/MM/yyyy" />
		<br />
		<acme:textbox path="institution" code="educationRecord.institution" />
		<br />
		<acme:textbox path="attachment" code="educationRecord.attachment" />
		<br />
		<acme:textarea path="comments" code="educationRecord.comments" />
		<br />

		<%-- Buttons --%>
		
		<acme:submit name="save" code="educationRecord.save" />
		
		<jstl:if test="${educationRecord.id!=0}">
			<acme:delete code="educationRecord.delete" confirm="educationRecord.confirm.delete" />
		</jstl:if>
	
		<acme:cancel code="educationRecord.cancel" url="curriculum/instructor/display.do" />

	</form:form>

</security:authorize>



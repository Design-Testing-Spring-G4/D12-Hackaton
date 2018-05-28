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

	<form:form action="${requestURI}" modelAttribute="professionalRecord">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="curriculum" />

		<acme:textbox path="company" code="professionalRecord.company" />
		<br />
		<acme:textbox path="periodStart" code="professionalRecord.periodStart" placeholder="dd/MM/yyyy" />
		<br />
		<acme:textbox path="periodEnd" code="professionalRecord.periodEnd" placeholder="dd/MM/yyyy" />
		<br />
		<acme:textbox path="role" code="professionalRecord.role" />
		<br />
		<acme:textbox path="attachment" code="professionalRecord.attachment" />
		<br />
		<acme:textarea path="comments" code="professionalRecord.comments" />
		<br />

		<%-- Buttons --%>
		
		<acme:submit name="save" code="professionalRecord.save" />
		
		<jstl:if test="${professionalRecord.id!=0}">
			<acme:delete code="professionalRecord.delete" confirm="professionalRecord.confirm.delete" />
		</jstl:if>
	
		<acme:cancel code="professionalRecord.cancel" url="curriculum/instructor/display.do" />

	</form:form>

</security:authorize>



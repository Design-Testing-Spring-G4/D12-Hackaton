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
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ADMIN')">

	<form:form action="${requestURI}" modelAttribute="category">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="version" />

		<acme:textbox path="name" code="category.name" />
		<br />

		<acme:select path="parent" code="category.parent" items="${categories}" itemLabel="name" />
		<br/>
		
		<%-- Buttons --%>

		<acme:submit name="save" code="category.save" />
		
		<jstl:if test="${category.id!=0}">
			<acme:delete code="category.delete" confirm="category.confirm.delete" />
		</jstl:if>
		
		<acme:cancel code="category.cancel" url="category/administrator/list.do" />
		
	</form:form>

</security:authorize>
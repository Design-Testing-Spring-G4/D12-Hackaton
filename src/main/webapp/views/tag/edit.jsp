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

	<form:form action="${requestURI}" modelAttribute="tag">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="tagValues" />

		<acme:textbox path="name" code="tag.name" />
		<br/>

		<%-- Buttons --%>

		<acme:submit name="save" code="tag.save" />
		
		<jstl:if test="${tag.id != 0}">
			<acme:delete code="tag.delete" confirm="tag.confirm.delete" />
		</jstl:if>
		
		<acme:cancel code="tag.cancel" url="tag/administrator/list.do" />
		
	</form:form>

</security:authorize>
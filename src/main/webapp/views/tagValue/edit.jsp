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

	<form:form action="${requestURI}" modelAttribute="tagValue">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="version" />

		<acme:textbox path="value" code="tagValue.value" />
		<br/>

		<%-- Buttons --%>

		<acme:submit name="save" code="tagValue.save" />

		<jstl:if test="${tagValue.id != 0}">
			<acme:delete code="tagValue.delete" confirm="tagValue.confirm.delete" />
		</jstl:if>	

		<acme:cancel code="tagValue.cancel" url="tag/administrator/list.do" />
		
	</form:form>

</security:authorize>
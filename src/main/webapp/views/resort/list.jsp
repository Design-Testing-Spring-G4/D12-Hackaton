<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="permitAll()">

<%-- Stored message variables --%>

<spring:message code="resort.name" var="name" />
<spring:message code="resort.description" var="description" />
<spring:message code="resort.startDate" var="startDate" />
<spring:message code="resort.endDate" var="endDate" />
<spring:message code="resort.spots" var="spots" />
<spring:message code="resort.full" var="full" />
<spring:message code="resort.dateInt" var="formatDate" />

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="resorts" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="name" title="${name}" sortable="true" />

		<display:column property="description" title="${description}" sortable="true" />

		<display:column title="${startDate}" sortable="true">
			<fmt:formatDate value="${row.startDate}" pattern="${formatDate}" />
		</display:column>
		
		<display:column title="${endDate}" sortable="true">
			<fmt:formatDate value="${row.endDate}" pattern="${formatDate}" />
		</display:column>
		
		<jstl:choose>
			<jstl:when test="${row.spots eq 0}">
				<display:column title="${spots}">
					<jstl:out value="${full}"/>
				</display:column>
			</jstl:when>
			<jstl:otherwise>
				<display:column property="spots" title="${spots}" />
			</jstl:otherwise>
		</jstl:choose>

		<%-- Links towards edition, display and others --%>
		
		<acme:link code="resort.display" url="resort/display.do" id="${row.id}" />

	</display:table>

</security:authorize>
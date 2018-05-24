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

<spring:message code="competition.title" var="title" />
<spring:message code="competition.description" var="description" />
<spring:message code="competition.startDate" var="startDate" />
<spring:message code="competition.endDate" var="endDate" />
<spring:message code="competition.maxParticipants" var="maxParticipants" />
<spring:message code="competition.display" var="display" />
<spring:message code="competition.dateInt" var="formatDate" />

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="competitions" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="title" title="${title}" sortable="true" />
		
		<display:column property="description" title="${description}" sortable="true" />

		<display:column title="${startDate}" sortable="true">
			<fmt:formatDate value="${row.startDate}" pattern="${formatDate}" />
		</display:column>
		
		<display:column title="${endDate}" sortable="true">
			<fmt:formatDate value="${row.endDate}" pattern="${formatDate}" />
		</display:column>
		
		<display:column property="maxParticipants" title="${maxParticipants}" sortable="true" />

		<%-- Links towards edition, display and others --%>
		
		<spring:url var="displayUrl" value="competition/display.do">
			<spring:param name="varId" value="${row.id}" />
			<spring:param name="varId2" value="${varId}" />
		</spring:url>
		
		<display:column>
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>
		
		<acme:link code="competition.participants" url="actor/list.do" id="${row.id}" />
		
		<security:authorize access="isAuthenticated()">
			<acme:link code="competition.suggestion" url="suggestion/actor/create.do" id="${row.id}"/>
		</security:authorize>
		
	</display:table>
	
	<acme:cancel code="competition.return" url="resort/list.do" />

</security:authorize>
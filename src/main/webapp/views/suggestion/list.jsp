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

<security:authorize access="hasRole('SPONSOR')">

<%-- Stored message variables --%>

<spring:message code="suggestion.actor" var="actor" />
<spring:message code="suggestion.title" var="title" />
<spring:message code="suggestion.comments" var="comments" />
<spring:message code="suggestion.attachments" var="attachments" />
<spring:message code="suggestion.anonymous.user" var="anonymous" />

<jsp:useBean id="now" class="java.util.Date"/>

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="suggestions" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<jstl:choose>
			<jstl:when test ="${row.anonymous eq false}">
				<display:column property="actor.userAccount.username" title="${actor}" sortable="true" />
			</jstl:when>
			<jstl:otherwise>
				<display:column title="${actor}">
					<jstl:out value="${anonymous}" />
				</display:column>
			</jstl:otherwise>
		</jstl:choose>

		<display:column property="title" title="${title}" sortable="true" />
		
		<display:column property="comments" title="${comments}" sortable="true" />

		<display:column property="attachments" title="${attachments}" sortable="true" />
		
		<%-- Links towards edition, display and others --%>
		
		<display:column>
			<jstl:if test="${row.anonymous eq false}">
				<spring:url var="actorUrl" value="actor/display.do">
					<spring:param name="varId" value="${row.actor.id}" />
				</spring:url>
				
				<a href="${actorUrl}"><jstl:out value="${row.actor.name} ${row.actor.surname}" /></a>
			</jstl:if>
		</display:column>
						
	</display:table>
	
	<acme:cancel code="suggestion.return" url="competition/sponsor/list.do" />
	
</security:authorize>
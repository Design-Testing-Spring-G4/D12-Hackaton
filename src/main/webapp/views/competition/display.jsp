<%--
 * display.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="permitAll()">

<%-- Stored message variables --%>

<spring:message code="competition.startDate" var="startDate" />
<spring:message code="competition.endDate" var="endDate" />
<spring:message code="competition.dateInt" var="formatDate" />

	<%-- For the selected competition received as model, display the following information: --%>
	
	<jstl:if test="${fn:length(competition.banner) != 0}">
	<a href="${competition.link}" target="_blank">
		<img src="${competition.banner}" height="200" width="300" />
	</a>
	<hr/>
	</jstl:if>

	<acme:displayField code="competition.title" path="${competition.title}" />
	<br/>
	<acme:displayField code="competition.description" path="${competition.description}" />
	<br/>
	<jstl:out value="${startDate}" />:
	<fmt:formatDate value="${competition.startDate}" pattern="${formatDate}" />
	<br/>
	<jstl:out value="${endDate}" />:
	<fmt:formatDate value="${competition.endDate}" pattern="${formatDate}" />
	<br/>
	<acme:displayField code="competition.sports" path="${competition.sports}" />
	<br/>
	<acme:displayField code="competition.maxParticipants" path="${competition.maxParticipants}" />
	<br/>
	<acme:displayField code="competition.entry" path="${competition.entry}" />
	<br/>
	<acme:displayField code="competition.prizePool" path="${competition.prizePool}" />
	<br/>
	<acme:displayField code="competition.rules" path="${competition.rules}" />
	<br/>
	<acme:displayField code="competition.sponsor" path="${competition.sponsor.name} ${competition.sponsor.surname}" />
	<br/>

	<security:authorize access="!hasRole('SPONSOR')">
		<spring:url var="returnUrl" value="competition/list.do">
			<spring:param name="varId" value="${varId}" />
		</spring:url>
		
		<acme:cancel code="competition.return" url="${returnUrl}"/>
	</security:authorize>
	
	<security:authorize access="hasRole('SPONSOR')">
		<acme:cancel code="competition.return" url="competition/sponsor/list.do" />
	</security:authorize>

</security:authorize>
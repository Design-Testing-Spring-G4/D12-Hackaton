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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Stored message variables --%>

<spring:message code="mailMessage.sent" var="sent" />
<spring:message code="mailMessage.priority" var="priority" />
<spring:message code="mailMessage.subject" var="subject" />

<spring:message code="mailMessage.display" var="display" />
<spring:message code="mailMessage.move" var="move" />
<spring:message code="mailMessage.delete" var="delete" />
<spring:message code="mailMessage.return" var="returnMsg" />

<security:authorize access="isAuthenticated()">

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="mailMessages" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column title="${sent}" sortable="true">
		<fmt:formatDate value="${row.sent}" type="BOTH"/>
	</display:column>
	
	<display:column property="priority" title="${priority}" sortable="true" />
	
	<display:column property="subject" title="${subject}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>

	<acme:link code="mailMessage.display" url="mailMessage/display.do" id="${row.id}" />
	<acme:link code="mailMessage.move" url="mailMessage/edit.do" id="${row.id}" />
	<acme:link code="mailMessage.delete" url="mailMessage/delete.do" id="${row.id}" />
	
</display:table>

<spring:url var="returnUrl" value="folder/list.do"/>
<a href="${returnUrl}"><jstl:out value="${returnMsg}" /></a>

</security:authorize>
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

<spring:message code="audit.title" var="title" />
<spring:message code="audit.moment" var="moment" />
<spring:message code="audit.auditor" var="auditor" />
<spring:message code="audit.dateInt" var="formatDate" />

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="audits" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="title" title="${title}" sortable="true" />

		<display:column title="${moment}" sortable="true">
			<fmt:formatDate value="${row.moment}" pattern="${formatDate}" />
		</display:column>
		
		<display:column property="auditor.userAccount.username" title="${auditor}" sortable="true" />

		<%-- Links towards edition, display and others --%>
		
		<acme:link code="audit.display" url="audit/display.do" id="${row.id}" />

	</display:table>
	
	<acme:cancel code="audit.return" url="resort/list.do" />

</security:authorize>
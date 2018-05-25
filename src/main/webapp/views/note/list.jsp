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

<security:authorize access="hasRole('MANAGER')">

<%-- Stored message variables --%>

<spring:message code="note.moment" var="moment" />
<spring:message code="note.remark" var="remark" />
<spring:message code="note.replyMoment" var="replyMoment" />
<spring:message code="note.reply" var="reply" />
<spring:message code="note.dateInt" var="formatDate" />

	<%-- Listing grid --%>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="notes" requestURI="${requestURI}" id="row">
	
		<%-- Attributes --%>
	
		<display:column title="${moment}" sortable="true">
			<fmt:formatDate value="${row.moment}" pattern="${formatDate}" />
		</display:column>
	
		<display:column property="remark" title="${remark}" sortable="true" />
	
		<display:column title="${replyMoment}" sortable="true">
			<fmt:formatDate value="${row.replyMoment}" pattern="${formatDate}" />
		</display:column>
		
		<display:column property="reply" title="${reply}" sortable="true" />
	
		<%-- Links towards edition, display and others --%>
		
		<acme:link code="note.edit" url="note/manager/edit.do" id="${row.id}" />
		
	</display:table>
	
	<acme:cancel code="note.return" url="activity/manager/list.do" />

</security:authorize>
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

<jstl:choose>
	<jstl:when test="${requestURI == 'reservation/manager/list.do'}">
		<jstl:set var="access" value="hasRole('MANAGER')"/>
	</jstl:when>
	<jstl:otherwise>
		<jstl:set var="access" value="hasRole('USER')"/>
	</jstl:otherwise>
</jstl:choose>

<security:authorize access="${access}">

<%-- Stored message variables --%>

<spring:message code="reservation.adults" var="adults" />
<spring:message code="reservation.children" var="children" />
<spring:message code="reservation.startDate" var="startDate" />
<spring:message code="reservation.endDate" var="endDate" />
<spring:message code="reservation.price" var="price" />
<spring:message code="reservation.status" var="status" />
<spring:message code="reservation.comments" var="comments" />
<spring:message code="reservation.reason" var="reason" />
<spring:message code="reservation.dateInt" var="formatDate" />

<jsp:useBean id="now" class="java.util.Date"/>

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="reservations" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="adults" title="${adults}" sortable="true" />

		<display:column property="children" title="${children}" sortable="true" />

		<display:column title="${startDate}" sortable="true">
			<fmt:formatDate value="${row.startDate}" pattern="${formatDate}" />
		</display:column>
		
		<display:column title="${endDate}" sortable="true">
			<fmt:formatDate value="${row.endDate}" pattern="${formatDate}" />
		</display:column>
		
		<jstl:if test="${requestURI == 'reservation/user/list.do'}">
			<display:column property="price" title="${price}" sortable="true" />
		</jstl:if>
		
		<display:column property="status" title="${status}" />
		
		<display:column property="comments" title="${comments}" />
		
		<jstl:if test="${requestURI == 'reservation/user/list.do'}">
			<display:column property="reason" title="${reason}" sortable="true" />
		</jstl:if>

		<%-- Links towards edition, display and others --%>
		
		<jstl:if test="${requestURI == 'reservation/manager/list.do' && row.status == 'PENDING'}">
			<acme:link code="reservation.edit" url="reservation/manager/edit.do" id="${row.id}" column="true" />
		</jstl:if>
		
		<jstl:if test="${requestURI == 'reservation/user/list.do'}">
			<acme:link code="reservation.display" url="reservation/user/display.do" id="${row.id}" column="true" />
		
			<acme:link code="reservation.resort" url="resort/display.do" id="${row.resort.id}" column="true" />
		</jstl:if>
		
		<display:column>
			<jstl:if test="${requestURI == 'reservation/user/list.do' && row.status == 'DUE'}">
				<acme:link code="reservation.pay" url="reservation/user/edit.do" id="${row.id}" column="false" />
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${requestURI == 'reservation/user/list.do' && row.status == 'ACCEPTED' && row.startDate > now}">
				<acme:link code="reservation.cancel" url="reservation/user/cancel.do" id="${row.id}" column="false" />
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${requestURI == 'reservation/user/list.do' && row.status == 'ACCEPTED'}">
				<acme:link code="reservation.request" url="reservation/user/activities.do" id="${row.id}" column="false" />
			</jstl:if>
		</display:column>
		
	</display:table>

</security:authorize>
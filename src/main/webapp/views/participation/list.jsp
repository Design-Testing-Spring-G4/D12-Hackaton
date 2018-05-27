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

<security:authorize access="hasRole('USER') || hasRole('INSTRUCTOR')">

<%-- Stored message variables --%>

<spring:message code="participation.moment" var="moment" />
<spring:message code="participation.comments" var="comments" />
<spring:message code="participation.status" var="status" />
<spring:message code="participation.dateInt" var="formatDate" />

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="participations" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>
		
		<jstl:if test="${row.status.name == 'ACCEPTED'}">
			<jstl:set var="colorValue" value="green"/>
		</jstl:if>
		<jstl:if test="${row.status.name == 'CANCELLED'}">
			<jstl:set var="colorValue" value="red"/>
		</jstl:if>

		<display:column title="${moment}" sortable="true" style="background-color:${colorValue}">
			<fmt:formatDate value="${row.moment}" pattern="${formatDate}" />
		</display:column>
		
		<display:column property="comments" title="${comments}" sortable="true" style="background-color:${colorValue}" />
		
		<display:column property="status" title="${status}" sortable="true" style="background-color:${colorValue}" />

		<%-- Links towards edition, display and others --%>
		
		<acme:link code="participation.cancel" url="participation/actor/cancel.do" id="${row.id}" column="true" />

	</display:table>
	
</security:authorize>
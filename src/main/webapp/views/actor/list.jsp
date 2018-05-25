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

<spring:message code="actor.username" var="username" />

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="participants" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="userAccount.username" title="${username}" sortable="true" />
		
		<display:column property="name" title="${name}" sortable="true" />
		
		<display:column property="surname" title="${surname}" sortable="true" />
		
		<display:column property="email" title="${email}" sortable="true" />
		
		<%-- Links towards display, edition and others --%>
		
		<jstl:if test="${requestURI == 'activity/manager/manage.do'}">
			<acme:link code="actor.activity.manage" url="activity/manager/set.do" id="${row.id}" />
		</jstl:if>

	</display:table>
	
	<jstl:if test="${requestURI != 'activity/manager/manage.do'}">
		<spring:url var="returnUtl" value="competition/list.do">
			<spring:param name="varId" value="${varId}" />
		</spring:url>
		
		<acme:cancel code="actor.return" url="${returnUrl}" />
	</jstl:if>
	
</security:authorize>
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

<spring:message code="activity.title" var="title" />
<spring:message code="activity.description" var="description" />
<spring:message code="activity.category" var="category" />
<spring:message code="activity.price" var="price" />
<spring:message code="activity.instructor" var="instructor" />
<spring:message code="activity.negative" var="negative" />

	<%-- Listing grid --%>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="activities" requestURI="${requestURI}" id="row">
	
		<%-- Attributes --%>
	
		<display:column property="title" title="${title}" sortable="true" />
	
		<display:column property="description" title="${description}" sortable="true" />
	
		<display:column property="category" title="${category}" sortable="true" />
		
		<display:column property="price" title="${price}" sortable="true" />
	
		<%-- Links towards edition, display and others --%>
		
		<jstl:choose>
			<jstl:when test="${row.instructor != null}">
				<spring:url var="instructorDisplay" value="actor/display.do">
						<spring:param name="varId" value="${row.instructor.id}" />
				</spring:url>
	
				<display:column title="${instructor}">
					<a href="${instructorDisplay}"><jstl:out value="${row.instructor.name} ${row.instructor.surname}" /></a>
				</display:column>
			</jstl:when>
			<jstl:otherwise>
				<display:column title="${instructor}">
					<jstl:out value="${negative}" />
				</display:column>
			</jstl:otherwise>
		</jstl:choose>
		
		<acme:cancel code="activity.return" url="resort/list.do" />
		
	</display:table>

</security:authorize>
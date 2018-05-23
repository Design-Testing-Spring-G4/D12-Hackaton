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

<spring:message code="lesson.name" var="name" />
<spring:message code="lesson.description" var="description" />
<spring:message code="lesson.schedule" var="schedule" />
<spring:message code="lesson.price" var="price" />

	<%-- Listing grid --%>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="lessons" requestURI="${requestURI}" id="row">
	
		<%-- Attributes --%>
	
		<display:column property="name" title="${name}" sortable="true" />
	
		<display:column property="description" title="${description}" sortable="true" />
	
		<display:column property="schedule" title="${schedule}" sortable="true" />
		
		<display:column property="price" title="${price}" sortable="true" />
		
		<%-- Links towards edition, display and others --%>
		
		<spring:url var="instructorDisplay" value="actor/display.do">
			<spring:param name="varId" value="${row.instructor.id}" />
		</spring:url>
		
		<acme:link code="lesson.instructor.display" url="${instructorDisplay}" />
		
	</display:table>
	
	<acme:cancel code="lesson.return" url="instructor/list.do" />

</security:authorize>
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
<spring:message code="lesson.create" var="create" />

	<%-- Listing grid --%>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="lessons" requestURI="${requestURI}" id="row">
	
		<%-- Attributes --%>
	
		<display:column property="name" title="${name}" sortable="true" />
	
		<display:column property="description" title="${description}" sortable="true" />
	
		<display:column property="schedule" title="${schedule}" sortable="true" />
		
		<display:column property="price" title="${price}" sortable="true" />
		
		<%-- Links towards edition, display and others --%>
		
		<jstl:if test="${requestURI == 'lesson/instructor/list.do'}">
			<acme:link code="lesson.notes" url="note/instructor/list.do" id="${row.id}" column="true"/>
		</jstl:if>
		
		<spring:url var="instructorDisplay" value="actor/display.do">
			<spring:param name="varId" value="${row.instructor.id}" />
		</spring:url>
		
		<acme:link code="lesson.instructor.display" url="${instructorDisplay}" column="true" />
		
		<security:authorize access="hasRole('INSTRUCTOR')">
			<jstl:if test="${requestURI == 'lesson/instructor/list.do'}">
				<acme:link code="lesson.edit" url="lesson/instructor/edit.do" id="${row.id}" column="true" />
			</jstl:if>
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')">
			<acme:link code="lesson.note.create" url="note/auditor/create.do" id="${row.id}" id2="1" column="true" />
		</security:authorize>
		
	</display:table>
	
	<security:authorize access="hasRole('INSTRUCTOR')">
		<spring:url var="createUrl" value="lesson/instructor/create.do" />
			
		<a href="${createUrl}"><jstl:out value="${create}"/></a>
	</security:authorize>
	
	<jstl:if test="${requestURI == 'lesson/list.do'}">
		<acme:cancel code="lesson.return" url="instructor/list.do" />
	</jstl:if>

</security:authorize>
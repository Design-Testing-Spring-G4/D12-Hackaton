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

<spring:message code="instructor.name" var="name" />
<spring:message code="instructor.surname" var="surname" />
<spring:message code="instructor.email" var="email" />
<spring:message code="instructor.curriculum" var="curriculum" />

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="instructors" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="name" title="${name}" sortable="true" />

		<display:column property="surname" title="${surname}" sortable="true" />

		<display:column property="email" title="${email}" sortable="true" />
		
		<%-- Links towards edition, display and others --%>
		
		<acme:link code="instructor.display" url="actor/display.do" id="${row.id}" />
		
		<display:column>
			<jstl:if test="${row.curriculum != null}">
				<spring:url var="curriculumDisplay" value="curriculum/display.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>
				<a href="${curriculumDisplay}"><jstl:out value="${curriculum}" /></a>
			</jstl:if>
		</display:column>
		
		<acme:link code="instructor.lessons" url="lesson/list.do" id="${row.id}" />

	</display:table>

</security:authorize>
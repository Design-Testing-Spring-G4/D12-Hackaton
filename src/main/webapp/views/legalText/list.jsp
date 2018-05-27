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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ADMIN')">

	<%-- Stored message variables --%>
	
	<spring:message code="legalText.title" var="title" />
	<spring:message code="legalText.body" var="body" />
	<spring:message code="legalText.laws" var="laws" />
	<spring:message code="legalText.registered" var="registered" />
	<spring:message code="legalText.dateInt" var="formatDate" />
	
	<%-- Listing grid --%>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="legalTexts" requestURI="${requestURI}" id="row">
		
		<%-- Attributes --%>
		
		<display:column property="title" title="${title}" sortable="true" />
		
		<display:column property="body" title="${body}" sortable="true" />
	
		<display:column property="laws" title="${laws}" sortable="true" />
		
		<display:column title="${registered}" sortable="true">
			<fmt:formatDate value="${row.registered}" pattern="${formatDate}"/>
		</display:column>
		
		<%-- Links towards display, apply, edit and cancel views --%>
		
		<display:column>
			<jstl:if test="${row.finalMode eq false}">
				<acme:link code="legalText.edit" url="legalText/administrator/edit.do" id="${row.id}" column="false" />
			</jstl:if>
		</display:column>
		
	</display:table>
	
	<acme:link code="legalText.create" url="legalText/administrator/create.do" column="false" />

</security:authorize>
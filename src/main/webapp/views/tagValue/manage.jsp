<%--
 * manage.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ADMIN')">

	<%-- Stored message variables --%>
	
	<spring:message code="tagValue.tag.name" var="tagName" />
	<spring:message code="tagValue.value" var="value" />
	<spring:message code="tagValue.set" var="set" />
	<spring:message code="tagValue.unset" var="unset" />
	
	<%-- Listing grid --%>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="tags" requestURI="${requestURI}" id="row">
		
		<%-- Attributes --%>
	
		<display:column property="tag.name" title="${tagName}" sortable="true" />
		
		<display:column property="value" title="${value}" sortable="true" />
	
		<%-- Link to set as trip's category --%>
	
		<spring:url var="setUrl" value="tagValue/administrator/set.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
	
		<display:column>
			<jstl:if test="${!values.contains(row)}">
				<a href="${setUrl}"><jstl:out value="${set}" /></a>
			</jstl:if>
			
			<jstl:if test="${values.contains(row)}">
				<a href="${setUrl}"><jstl:out value="${unset}" /></a>
			</jstl:if>
			
		</display:column>
		
	</display:table>

	<acme:cancel code="tagValue.return" url="resort/list.do" />
	
</security:authorize>
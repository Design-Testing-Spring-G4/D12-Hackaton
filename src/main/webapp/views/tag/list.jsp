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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ADMIN')">

	<%-- Stored message variables --%>
	
	<spring:message code="tag.name" var="name" />
	
	<%-- Listing grid --%>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="tags" requestURI="${requestURI}" id="row">
	
		<%-- Attributes --%>
	
		<display:column property="name" title="${name}" sortable="true" />
	
		<%-- Links towards display, apply, edit and cancel views --%>
	
		<acme:link code="tag.tagValue" url="tagValue/administrator/list.do" id="${row.id}" column="true" />
	
		<acme:link code="tag.edit" url="tag/administrator/edit.do" id="${row.id}" column="true" />
	
	</display:table>
	
	<acme:link code="tag.create" url="tag/administrator/create.do" column="false" />
	
</security:authorize>


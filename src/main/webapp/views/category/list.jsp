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
	
	<spring:message code="category.name" var="name" />
	<spring:message code="category.parent" var="parent" />
	
	<%-- Listing grid --%>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="categories" requestURI="${requestURI}" id="row">
	
		<%-- Attributes --%>
	
		<display:column property="name" title="${name}" sortable="true" />
	
		<display:column property="parent.name" title="${parent}" sortable="true" />
	
		<%-- Links towards display, apply, edit and cancel views --%>
		
		<acme:link code="category.children" url="category/administrator/childrenList.do" id="${row.id}" column="true" />
	
		<acme:link code="category.edit" url="category/administrator/edit.do" id="${row.id}" column="true" />
	
	</display:table>
	
	<acme:link code="category.create" url="category/administrator/create.do" column="false" />
	
</security:authorize>
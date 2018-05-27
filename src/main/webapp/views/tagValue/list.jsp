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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ADMIN')">

	<%-- Stored message variables --%>

	<spring:message code="tagValue.value" var="value" />
	
	<%-- Listing grid --%>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="tagValues" requestURI="${requestURI}" id="row">
		
		<%-- Attributes --%>
	
		<display:column property="value" title="${value}" sortable="true" />
	
		<%-- Links towards display, apply, edit and cancel views --%>
			
		<acme:link code="tagValue.edit" url="tagValue/administrator/edit.do" id="${row.id}" column="true" />
		
	</display:table>
	
	<acme:link code="tagValue.create" url="tagValue/administrator/create.do" id="${row.id}" column="false" />
	<br/>
	
	<acme:cancel code="tagValue.return" url="tag/administrator/list.do" />

</security:authorize>


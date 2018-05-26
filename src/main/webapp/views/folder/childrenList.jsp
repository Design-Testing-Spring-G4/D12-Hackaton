<%--
 * childrenList.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Stored message variables --%>

<spring:message code="folder.name" var="name" />
<spring:message code="folder.parent" var="parent" />

<%-- Listing grid --%>

<security:authorize access="isAuthenticated()">

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="folders" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="name" title="${name}" sortable="true" />

	<display:column property="parent.name" title="${parent}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>
	
	<acme:link code="folder.mailMessage.text" url="mailMessage/list.do" id="${row.id}" column="true" />
	
	<acme:link code="folder.children" url="folder/childrenList.do" id="${row.id}" column="true" />
	
	<acme:link code="folder.edit" url="folder/edit.do" id="${row.id}" column="true" />

</display:table>

<acme:link code="folder.create" url="folder/create.do" column="false" />
<br/>

<acme:cancel code="folder.return" url="folder/list.do" />

</security:authorize>
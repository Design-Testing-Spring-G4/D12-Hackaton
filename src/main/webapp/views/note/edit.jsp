<%--
 * edit.jsp
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

<%-- Creation form --%>

<security:authorize access="hasRole('AUDITOR')">

<form:form action="${requestURI}" modelAttribute="note">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textarea path="remark" code="note.remark" />
	<br/>
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="note.save" />
	
	<acme:cancel code="note.cancel" url="welcome/index.do" />

</form:form>

</security:authorize>

<%-- Reply form --%>

<jstl:choose>
	<jstl:when test="${requestURI == 'note/manager/list.do'}">
		<jstl:set var="access" value="hasRole('MANAGER')"/>
	</jstl:when>
	<jstl:otherwise>
		<jstl:set var="access" value="hasRole('INSTRUCTOR')"/>
	</jstl:otherwise>
</jstl:choose>

<security:authorize access="${access}">

<form:form action="${requestURI}" modelAttribute="note">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textarea path="reply" code="note.reply" />
	<br/>
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="note.save" />
	
	<acme:cancel code="note.cancel" url="activity/manager/list.do" />

</form:form>

</security:authorize>
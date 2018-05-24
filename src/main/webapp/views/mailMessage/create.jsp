<%--
 * create.jsp
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="isAuthenticated()">

<form:form action="${requestURI}" modelAttribute="mailMessage">

	<%-- Form fields --%>
	
	<form:hidden path="id" />
	
	<security:authorize access="hasRole('ADMIN')" >
		<jstl:choose>
			<jstl:when test="${requestURI == 'mailMessage/administrator/create.do'}" >
				<form:hidden path="receiver" />
			</jstl:when>
			<jstl:otherwise>
				<acme:select path="receiver" code="mailMessage.receiver" items="${receivers}" itemLabel="userAccount.username" />
			</jstl:otherwise>
		</jstl:choose>
	</security:authorize>
	
	<security:authorize access="!hasRole('ADMIN')" >
		<acme:select path="receiver" code="mailMessage.receiver" items="${receivers}" itemLabel="userAccount.username" />
	</security:authorize>
	<br/>
	
	<acme:select path="priority" code="mailMessage.priority" items="${priorities}" itemLabel="id" />
	<br/>
	<acme:textbox path="subject" code="mailMessage.subject" />
	<br/>
	<acme:textbox path="body" code="mailMessage.body" />
	<br/>
	
	<%-- Buttons --%>
	
	<security:authorize access="!hasRole('ADMIN')" >
		<acme:submit name="save" code="mailMessage.save" />
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')" >
		<jstl:choose>
			<jstl:when test="${requestURI == 'mailMessage/administrator/create.do'}" >
				<acme:submit name="broadcast" code="mailMessage.save" />
			</jstl:when>
			<jstl:otherwise>
				<acme:submit name="save" code="mailMessage.save" />
			</jstl:otherwise>
		</jstl:choose>
	</security:authorize>
	
	<acme:cancel code="mailMessage.cancel" url="folder/list.do" />

</form:form>

</security:authorize>
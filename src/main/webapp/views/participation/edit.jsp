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

<security:authorize access="hasRole('USER') || hasRole('INSTRUCTOR')">

	<form:form action="${requestURI}" modelAttribute="participation">
	
		<%-- Forms --%>
		
		<form:hidden path="id" />
		
		<acme:textbox path="comments" code="participation.comments" />
		<br/>
		
		<%-- Buttons --%>
		
		<acme:submit name="save" code="participation.save" />
		
		<acme:cancel code="participation.cancel" url="resort/list.do" />
	
	</form:form>

</security:authorize>
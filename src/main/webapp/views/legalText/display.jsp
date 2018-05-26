<%--
 * display.jsp
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

<security:authorize access="permitAll()">

<%-- Stored message variables --%>

<spring:message code="legalText.registered" var="registered" />

<%-- Stored message variables --%>

<spring:message code="legalText.registered" var="registered" />
<spring:message code="legalText.dateInt" var="formatDate" />

<%-- For the selected legal text received as model, display the following information: --%>

	<acme:displayField code="legalText.title" path="${legalText.title}" />
	<br/>
	<acme:displayField code="legalText.body" path="${legalText.body}" />
	<br/>
	<acme:displayField code="legalText.laws" path="${legalText.laws}" />
	<br/>
	<jstl:out value="${registered}" />:
	<fmt:formatDate value="${legalText.registered}" pattern="${formatDate}" />
	<br/>
	
	<acme:cancel code="legalText.return" url="resort/list.do" />

</security:authorize>
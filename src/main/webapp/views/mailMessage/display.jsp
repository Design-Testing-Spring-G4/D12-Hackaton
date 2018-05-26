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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Stored message variables --%>

<spring:message code="mailMessage.dateInt" var="sent" />

<security:authorize access="isAuthenticated()">

	<%-- For the selected message, display the following information: --%>
	
	<jstl:out value="${sent}" />:
	<fmt:formatDate value="${mailMessage.sent}" pattern="${formatDate}" />
	<br />
	<acme:displayField path="mailMessage.sender.name" code="mailMessage.sender"/>
	<br/>
	<acme:displayField path="mailMessage.receiver.name" code="mailMessage.receiver"/>
	<br/>
	<acme:displayField path="mailMessage.priority" code="mailMessage.priority"/>
	<br/>
	<acme:displayField path="mailMessage.subject" code="mailMessage.subject"/>
	<br/>
	<acme:displayField path="mailMessage.body" code="mailMessage.body"/>
	<br/>

	<acme:link code="mailMessage.return" url="mailMessage.list.do" id="${mailMessage.folder.id}" column="false" />

</security:authorize>
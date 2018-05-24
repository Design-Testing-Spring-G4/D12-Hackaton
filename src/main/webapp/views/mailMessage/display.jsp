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

<spring:message code="mailMessage.sent" var="sent" />


<spring:message code="mailMessage.priority" var="priority" />
<spring:message code="mailMessage.subject" var="subject" />
<spring:message code="mailMessage.body" var="body" />
<spring:message code="mailMessage.return" var="returnMsg" />

<security:authorize access="isAuthenticated()">

	<%-- For the selected message, display the following information: --%>
	
	<jstl:out value="${sent}" />:
	<fmt:formatDate value="${mailMessage.sent}" type="BOTH"/>
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

	<spring:url var="returnUrl"
		value="mailMessage/list.do">
		<spring:param name="varId"
			value="${mailMessage.folder.id}"/>
	</spring:url>
	
	<a href="${returnUrl}"><jstl:out value="${returnMsg}" /></a>

</security:authorize>
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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="permitAll()">

<%-- Stored message variables --%>

<spring:message code="audit.negative" var="negative" />
<spring:message code="audit.moment" var="moment" />
<spring:message code="audit.dateInt" var="formatDate" />

	<%-- For the selected audit received as model, display the following information: --%>

	<acme:displayField code="audit.title" path="${audit.title}" />
	<br/>
	<jstl:out value="${moment}" />:
	<fmt:formatDate value="${audit.moment}" pattern="${formatDate}" />
	<br/>
	<acme:displayField code="audit.auditor" path="${audit.auditor.name} ${audit.auditor.surname}" />
	<br/>
	<acme:displayField code="audit.description" path="${audit.description}" />
	<br/>
	
	<jstl:choose>
		<jstl:when test="${fn:length(audit.attachments) == 0}">
			<acme:displayField code="audit.attachments" path="${negative}" />
			<br/>
		</jstl:when>
		<jstl:otherwise>
			<acme:displayField code="audit.attachments" path="${audit.attachments}" />
			<br/>
		</jstl:otherwise>
	</jstl:choose>
	
	<spring:url var="returnUrl" value="audit/list.do">
		<spring:param name="varId" value="${audit.resort.id}" />
	</spring:url>
	
	<acme:cancel code="audit.return" url="${returnUrl}"/>

</security:authorize>
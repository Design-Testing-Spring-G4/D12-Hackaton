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

<spring:message code="resort.startDate" var="startDate" />
<spring:message code="resort.endDate" var="endDate" />
<spring:message code="resort.dateInt" var="formatDate" />

<%-- For the selected resort in the list received as model, display the following information: --%>
	
	<%--<jstl:if test="${resort.picture != ''}"> --%>
	<img src="${resort.picture}" height="150" width="550">
	<hr/>

	<acme:displayField code="resort.name" path="${resort.name}" />
	<br/>
	<acme:displayField code="resort.description" path="${resort.description}" />
	<br/>
	<acme:displayField code="resort.location" path="${resort.location.location}" />
	<br/>
	<acme:displayField code="resort.location.gpsCoordinates" path="${resort.location.gpsCoordinates}" />
	<br/>
	<jstl:out value="${startDate}" />:
	<fmt:formatDate value="${resort.startDate}" pattern="${formatDate}" />
	<br/>
	<jstl:out value="${endDate}" />:
	<fmt:formatDate value="${resort.endDate}" pattern="${formatDate}" />
	<br/>
	<acme:displayField code="resort.features" path="${resort.features}" />
	<br/>
	<acme:displayField code="resort.priceAdult" path="${resort.priceAdult}" />
	<br/>
	<acme:displayField code="resort.priceChild" path="${resort.priceChild}" />
	<br/>
	
	<acme:cancel code="resort.return" url="resort/list.do" />

</security:authorize>
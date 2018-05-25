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

<security:authorize access="hasRole('USER')">

<%-- Stored message variables --%>

<spring:message code="reservation.startDate" var="startDate" />
<spring:message code="reservation.endDate" var="endDate" />
<spring:message code="activity.title" var="actTitle" />
<spring:message code="activity.description" var="actDescription" />
<spring:message code="activity.category" var="actCategory" />
<spring:message code="activity.price" var="actPrice" />
<spring:message code="reservation.dateInt" var="formatDate" />

	<%-- For the selected reservation received as model, display the following information: --%>

	<acme:displayField code="reservation.resort" path="${reservation.resort.name}" />
	<br/>
	<acme:displayField code="reservation.adults" path="${reservation.adults}" />
	<br/>
	<acme:displayField code="reservation.children" path="${reservation.children}" />
	<br/>
	<jstl:out value="${startDate}" />:
	<fmt:formatDate value="${reservation.startDate}" pattern="${formatDate}" />
	<br/>
	<jstl:out value="${endDate}" />:
	<fmt:formatDate value="${reservation.endDate}" pattern="${formatDate}" />
	<br/>
	<acme:displayField code="reservation.price" path="${reservation.price}" />
	<br/>
	<acme:displayField code="reservation.status" path="${reservation.status}" />
	<br/>
	<acme:displayField code="reservation.comments" path="${reservation.comments}" />
	<br/>
	<acme:displayField code="reservation.holder" path="${reservation.creditCard.holder}" />
	<br/>
	<acme:displayField code="reservation.brand" path="${reservation.creditCard.brand}" />
	<br/>
	<acme:displayField code="reservation.number" path="${reservation.creditCard.number}" />
	<br/>
	<acme:displayField code="reservation.expMonth" path="${reservation.creditCard.expMonth}" />
	<br/>
	<acme:displayField code="reservation.expYear" path="${reservation.creditCard.expYear}" />
	<br/>
	<acme:displayField code="reservation.cvv" path="${reservation.creditCard.cvv}" />
	<br/>
	<jstl:if test="${reservation.reason != null && fn:length(reservation.reason) != 0}">
		<acme:displayField code="reservation.reason" path="${reservation.reason}" />
		<br/>
	</jstl:if>
	
	<br/>
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="reservation.activities" requestURI="${requestURI}" id="row">
	
		<%-- Attributes --%>
	
		<display:column property="title" title="${actTitle}" sortable="true" />
	
		<display:column property="description" title="${actDescription}" sortable="true" />
	
		<display:column property="category" title="${actCategory}" sortable="true" />
		
		<display:column property="price" title="${actPrice}" sortable="true" />
	
	</display:table>
	<br/>
	
	<acme:cancel code="reservation.return" url="reservation/user/list.do"/>

</security:authorize>
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

<spring:message code="curriculum.educationRecords" var="educationRecords" />
<spring:message code="curriculum.professionalRecords" var="professionalRecords" />
<spring:message code="curriculum.endorserRecords" var="endorserRecords" />
<spring:message code="curriculum.miscellaneousRecords" var="miscellaneousRecords" />
<spring:message code="curriculum.diploma" var="diploma" />
<spring:message code="curriculum.periodStart" var="periodStart" />
<spring:message code="curriculum.periodEnd" var="periodEnd" />
<spring:message code="curriculum.institution" var="institution" />
<spring:message code="curriculum.attachment" var="attachment" />
<spring:message code="curriculum.comments" var="comments" />
<spring:message code="curriculum.company" var="company" />
<spring:message code="curriculum.role" var="role" />
<spring:message code="curriculum.name" var="name" />
<spring:message code="curriculum.email" var="email" />
<spring:message code="curriculum.phone" var="phone" />
<spring:message code="curriculum.profile" var="profile" />
<spring:message code="curriculum.title" var="title" />
<spring:message code="curriculum.link" var="link" />
<spring:message code="curriculum.dateInt" var="formatDate" />

<%-- For the selected actor received as model, display the following information: --%>

	<img src="${curriculum.personalRecord.photo}" height=200 width=100 />
	<hr/>

	<acme:displayField code="curriculum.ticker" path="${curriculum.ticker}" />
	<br/>
	<acme:displayField code="curriculum.name" path="${curriculum.personalRecord.name}" />
	<br/>
	<acme:displayField code="curriculum.email" path="${curriculum.personalRecord.email}" />
	<br/>
	<acme:displayField code="curriculum.phone" path="${curriculum.personalRecord.phone}" />
	<br/>
	<acme:displayField code="curriculum.profile" path="${curriculum.personalRecord.profile}" />
	<br/>

	<jstl:out value="${educationRecords}" />
	<br/>
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="curriculum.educationRecord" requestURI="${requestURI}" id="row">
		
		<display:column property="diploma" title="${diploma}" sortable="true" />
		
		<display:column title="${periodStart}" sortable="true">
			<fmt:formatDate value="${row.periodStart}" pattern="${formatDate}" />
		</display:column>
		
		<display:column title="${periodEnd}" sortable="true">
			<fmt:formatDate value="${row.periodEnd}" pattern="${formatDate}" />
		</display:column>
		
		<display:column property="institution" title="${institution}" sortable="true" />
		
		<display:column property="attachment" title="${attachment}" sortable="true" />
		
		<display:column property="comments" title="${comments}" sortable="true" />
		
	</display:table>
	
	<jstl:out value="${professionalRecords}" />
	<br/>
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="curriculum.professionalRecord" requestURI="${requestURI}" id="row">
		
		<display:column property="company" title="${company}" sortable="true" />
		
		<display:column title="${periodStart}" sortable="true">
			<fmt:formatDate value="${row.periodStart}" pattern="${formatDate}" />
		</display:column>
		
		<display:column title="${periodEnd}" sortable="true">
			<fmt:formatDate value="${row.periodEnd}" pattern="${formatDate}" />
		</display:column>
		
		<display:column property="role" title="${role}" sortable="true" />
		
		<display:column property="attachment" title="${attachment}" sortable="true" />
		
		<display:column property="comments" title="${comments}" sortable="true" />
		
	</display:table>
	
	<jstl:out value="${endorserRecords}" />
	<br/>
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="curriculum.endorserRecord" requestURI="${requestURI}" id="row">
		
		<display:column property="name" title="${name}" sortable="true" />
		
		<display:column property="email" title="${email}" sortable="true" />
		
		<display:column property="phone" title="${phone}" sortable="true" />
		
		<display:column property="profile" title="${profile}" sortable="true" />
		
		<display:column property="comments" title="${comments}" sortable="true" />
		
	</display:table>
	
	<jstl:out value="${miscellaneousRecords}" />
	<br/>
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="curriculum.miscellaneousRecord" requestURI="${requestURI}" id="row">
		
		<display:column property="title" title="${title}" sortable="true" />
		
		<display:column property="link" title="${link}" sortable="true" />
		
		<display:column property="comments" title="${comments}" sortable="true" />
		
	</display:table>
	
	<acme:cancel code="curriculum.return" url="instructor/list.do" />

</security:authorize>
<%--
 * list.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:choose>
	<jstl:when test="${requestURI == 'resort/manager/list.do'}">
		<jstl:set var="access" value="hasRole('MANAGER')"/>
	</jstl:when>
	<jstl:otherwise>
		<jstl:set var="access" value="permitAll()"/>
	</jstl:otherwise>
</jstl:choose>

<security:authorize access="${access}">

<%-- Stored message variables --%>

<spring:message code="resort.search" var="search" />
<spring:message code="resort.name" var="name" />
<spring:message code="resort.description" var="description" />
<spring:message code="resort.startDate" var="startDate" />
<spring:message code="resort.endDate" var="endDate" />
<spring:message code="resort.spots" var="spots" />
<spring:message code="resort.full" var="full" />
<spring:message code="resort.dateInt" var="formatDate" />
<spring:message code="resort.parent" var="parent" />
<spring:message code="resort.negative" var="negative" />
<spring:message code="resort.children" var="children" />
<spring:message code="resort.allCategory" var="allCategory" />

<%-- Conditional to display category list in the appropriate view --%>

<jstl:if test="${requestURI == 'resort/listCategory.do'}" >
	<jstl:out value="${category.name}" />.&nbsp;
	<jstl:out value="${parent}" />:&nbsp;
	<jstl:choose>
		<jstl:when test="${category.parent != null}">
			<spring:url var="parentUrl" value="resort/listCategory.do">
				<spring:param name="varId" value="${category.parent.id}" />
			</spring:url>
			<a href="${parentUrl}"><jstl:out value="${category.parent.name}" /></a>&nbsp;
		</jstl:when>
		<jstl:otherwise>
			<jstl:out value="${negative}"/>.&nbsp;
		</jstl:otherwise>
	</jstl:choose>
	<jstl:out value="${children}" />:&nbsp;
	<jstl:choose>
		<jstl:when test="${fn:length(category.children) != 0}">
			<jstl:forEach var="child" items="${childrenCategories}">
				<spring:url var="childUrl" value="resort/listCategory.do">
					<spring:param name="varId" value="${child.id}" />
				</spring:url>
				<a href="${childUrl}"><jstl:out value="${child.name}" /></a>&nbsp;
			</jstl:forEach>
		</jstl:when>
		<jstl:otherwise>
			<jstl:out value="${negative}"/>.&nbsp;
		</jstl:otherwise>
	</jstl:choose>
	<br/>
	<jstl:out value="${allCategory}" />:&nbsp;
	<jstl:forEach var="cat" items="${categories}">
		<spring:url var="catUrl" value="resort/listCategory.do">
			<spring:param name="varId" value="${cat.id}" />
		</spring:url>
		<a href="${catUrl}"><jstl:out value="${cat.name}" /></a>&nbsp;
	</jstl:forEach>
</jstl:if>
<br/>

<%-- Search box --%>

<form method="get" action="resort/search.do">
	<input type="text" name="keyword" placeholder="Search using a keyword"> 
	<input type="submit" value="${search}">
</form>

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="resorts" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="name" title="${name}" sortable="true" />

		<display:column property="description" title="${description}" sortable="true" />

		<display:column title="${startDate}" sortable="true">
			<fmt:formatDate value="${row.startDate}" pattern="${formatDate}" />
		</display:column>
		
		<display:column title="${endDate}" sortable="true">
			<fmt:formatDate value="${row.endDate}" pattern="${formatDate}" />
		</display:column>
		
		<jstl:choose>
			<jstl:when test="${row.spots eq 0}">
				<display:column title="${spots}">
					<jstl:out value="${full}"/>
				</display:column>
			</jstl:when>
			<jstl:otherwise>
				<display:column property="spots" title="${spots}" />
			</jstl:otherwise>
		</jstl:choose>

		<%-- Links towards edition, display and others --%>
		
		<acme:link code="resort.display" url="resort/display.do" id="${row.id}" column="true" />
		
		<jstl:if test="${requestURI == 'resort/manager/list.do'}">
			<acme:link code="resort.edit" url="resort/manager/edit.do" id="${row.id}" column="true" />
		</jstl:if>
		
		<acme:link code="resort.activities" url="activity/list.do" id="${row.id}" column="true" />
		
		<acme:link code="resort.audits" url="audit/list.do" id="${row.id}" column="true" />
		
		<acme:link code="resort.competitions" url="competition/list.do" id="${row.id}" column="true" />
		
		<security:authorize access="hasRole('USER')">
			<acme:link code="resort.reservation" url="reservation/user/create.do" id="${row.id}" column="true" />
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')">
			<acme:link code="resort.audit" url="audit/auditor/create.do" id="${row.id}" column="true" />
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<acme:link code="resort.competition" url="competition/sponsor/create.do" column="true" />
		</security:authorize>

	</display:table>
	
	<jstl:if test="${requestURI == 'resort/manager/list.do'}">
		<acme:link code="resort.create" url="resort/manager/create.do" column="false" />
	</jstl:if>

</security:authorize>
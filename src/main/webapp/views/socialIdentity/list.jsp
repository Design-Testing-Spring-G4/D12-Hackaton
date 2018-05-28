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

<security:authorize access="permitAll()">

<%-- Stored message variables --%>

<spring:message code="socialIdentity.nick" var="nick" />
<spring:message code="socialIdentity.network" var="network" />
<spring:message code="socialIdentity.profile" var="profile" />
<spring:message code="socialIdentity.photo" var="photo" />

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="socialIdentities" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="nick" title="${nick}" sortable="true" />

		<display:column property="network" title="${network}" sortable="true" />

		<display:column property="profile" title="${profile}" sortable="true" />
		
		<display:column title="${photo}">
			<jstl:if test="${fn:length(socialIdentity.photo) != 0}">
				<a href="${row.photo}">
					<img src="${row.photo}" height=100 width=50 />
				</a>
			</jstl:if>
		</display:column>
		
		<acme:link code="socialIdentity.edit" url="socialIdentity/actor/edit.do" id="${row.id}" column="true" />

	</display:table>
	
	<security:authorize access="isAuthenticated()">
		<acme:link code="socialIdentity.create" url="socialIdentity/actor/create.do" column="false" />
	</security:authorize>
	
	<jstl:if test="${requestURI == 'socialIdentity/list.do'}">
		<spring:url var="returnUrl" value="actor/display.do">
			<spring:param name="varId" value="${varId}" />
		</spring:url>
	
		<acme:cancel code="socialIdentity.return" url="${returnUrl}" />
	</jstl:if>


</security:authorize>
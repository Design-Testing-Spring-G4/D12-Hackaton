<%--
 * checkbox.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 

<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="requiredC" required="false" %>
<%@ attribute name="id" required="false" %>

<jstl:choose>
	<jstl:when test="${requiredC == null}">
		<jstl:set var="required" value="false" />
	</jstl:when>
	<jstl:otherwise>
		<jstl:set var="required" value="${requiredC}" />
	</jstl:otherwise>
</jstl:choose>

<%-- Definition --%>

<div>
	<form:label path="${path}">
		<spring:message code="${code}"></spring:message>
	</form:label>
	<form:checkbox path="${path}" required="${required}" id="${id}"/>
</div>	


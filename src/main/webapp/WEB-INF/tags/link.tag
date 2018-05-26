<%--
 * link.tag
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
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Attributes --%> 
 
<%@ attribute name="code" required="true" %>
<%@ attribute name="url" required="true" %>
<%@ attribute name="column" required="true" %>
<%@ attribute name="id" required="false" %>
<%@ attribute name="id2" required="false" %>

<spring:message code="${code}" var="message"/>

<%-- Definition --%>

<spring:url var="link" value="${url}">
	<jstl:if test="${id != null}">
		<spring:param name="varId" value="${id}" />
	</jstl:if>
	<jstl:if test="${id2 != null}">
		<spring:param name="varId2" value="${id2}" />
	</jstl:if>
</spring:url>

<jstl:choose>
	<jstl:when test="${column eq true}">
		<display:column>
			<a href="${link}"><jstl:out value="${message}" /></a>
		</display:column>
	</jstl:when>
	<jstl:otherwise>
		<a href="${link}"><jstl:out value="${message}" /></a>
	</jstl:otherwise>
</jstl:choose>


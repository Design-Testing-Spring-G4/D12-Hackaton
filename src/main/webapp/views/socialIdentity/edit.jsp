<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="isAuthenticated()">
	
	<%-- Form --%>
	
	<form:form id="form" action="${requestURI}" modelAttribute="socialIdentity">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<acme:textbox code="socialIdentity.nick" path="nick"/>
		<br/>
		<acme:textbox code="socialIdentity.network" path="network"/>
		<br/>
		<acme:textbox code="socialIdentity.profile" path="profile"/>
		<br/>
		<acme:textbox code="socialIdentity.photo" path="photo"/>
		<br/>
		
		<acme:submit name="save" code="socialIdentity.save"/>
		
		<jstl:if test="${socialIdentity.id != 0}">
			<acme:delete code="socialIdentity.delete" confirm="socialIdentity.confirm.delete"/>
		</jstl:if>
		
		<acme:cancel code="socialIdentity.cancel" url="socialIdentity/actor/list.do"/>
	
	</form:form>
</security:authorize>
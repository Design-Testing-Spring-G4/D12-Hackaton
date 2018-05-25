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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('MANAGER')">

<form:form action="${requestURI}" modelAttribute="resort">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox path="name" code="resort.name" />
	<br/>
	<acme:textbox path="location.location" code="resort.location" />
	<br/>
	<acme:textbox path="location.gpsCoordinates" code="resort.location.gpsCoordinates" placeholder="+90.00, +90.00"/>
	<br/>
	<acme:textarea path="description" code="resort.description" />
	<br/>
	<acme:textarea path="features" code="resort.features" />
	<br/>
	<acme:textbox path="startDate" code="resort.startDate" placeholder="dd/MM/yyyy" />
	<br/>
	<acme:textbox path="endDate" code="resort.endDate" placeholder="dd/MM/yyyy" />
	<br/>
	<acme:textbox path="picture" code="resort.picture" />
	<br/>
	<acme:textbox path="spots" code="resort.spots" />
	<br/>
	<acme:textbox path="priceAdult" code="resort.priceAdult" />
	<br/>
	<acme:textbox path="priceChild" code="resort.priceChild" />
	<br/>
	<acme:select path="legalText" code="resort.legalText" items="${legalTexts}" itemLabel="title" />
	<br/>
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="resort.save" />
	
	<jstl:if test="${folder.id != 0}">
		<acme:delete code="resort.delete" confirm="resort.confirm.delete"/>
	</jstl:if>
	
	<acme:cancel code="resort.cancel" url="resort/manager/list.do" />

</form:form>

</security:authorize>
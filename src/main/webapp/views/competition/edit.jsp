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

<security:authorize access="hasRole('SPONSOR')">

<form:form action="${requestURI}" modelAttribute="competition">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox path="title" code="competition.title" />
	<br/>
	<acme:textarea path="description" code="competition.description" />
	<br/>
	<acme:textbox path="startDate" code="competition.startDate" placeholder="dd/MM/yyyy HH:mm" />
	<br/>
	<acme:textbox path="endDate" code="competition.endDate" placeholder="dd/MM/yyyy HH:mm" />
	<br/>
	<acme:textarea path="sports" code="competition.sports" />
	<br/>
	<acme:textbox path="maxParticipants" code="competition.maxParticipants" />
	<br/>
	<acme:textbox path="banner" code="competition.banner" />
	<br/>
	<acme:textbox path="link" code="competition.link" />
	<br/>
	<acme:textbox path="entry" code="competition.entry" />
	<br/>
	<acme:textbox path="prizePool" code="competition.prizePool" />
	<br/>
	<acme:textarea path="rules" code="competition.rules" />
	<br/>
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="competition.save" />
	
	<jstl:if test="${competition.id != 0}">
		<acme:delete code="competition.delete" confirm="competition.confirm.delete"/>
	</jstl:if>
	
	<acme:cancel code="competition.cancel" url="competition/sponsor/list.do" />

</form:form>

</security:authorize>
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

<%-- Stored message variables --%>

<spring:message code="reservation.status" var="status" />

<form:form action="${requestURI}" modelAttribute="reservation">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<%-- Edition forms --%>
	
	<security:authorize access="hasRole('MANAGER')">
	
	<%-- A manager receives a list of status: REJECTED and DUE. If chosen status is REJECTED, a reason must be provided. --%>

		<form:label path="status">
			<jstl:out value="${status}" />:
		</form:label>

		<form:select path="status" >
				<form:option label="PENDING" value="PENDING"/>
				<form:option label="REJECTED" value="REJECTED"/>
				<form:option label="DUE" value="DUE"/>
			</form:select>
		<form:errors cssClass="error" path="status" />
		<br/>
		
		<acme:textarea path="reason" code="reservation.reason.disclaimer" />
		<br/>
		
		<%-- Buttons --%>

		<acme:submit name="save" code="reservation.save" />

		<acme:cancel code="reservation.cancel" url="reservation/manager/list.do" />
			
	</security:authorize>
		
	<%-- If status is DUE, the user receives a view to input a credit card. --%>

	<security:authorize access="hasRole('USER')">
	
		<form:hidden path="price" />
		<form:hidden path="status" />
		<form:hidden path="reason" />
		<form:hidden path="user" />
		<form:hidden path="resort" />
		<form:hidden path="activities" />
		<form:hidden path="lessons" />
		
		<jstl:if test="${reservation.id != 0}">
		
			<form:hidden path="adults" />
			<form:hidden path="children" />
			<form:hidden path="startDate" />
			<form:hidden path="endDate" />
			<form:hidden path="comments" />
		
			<acme:textbox path="creditCard.holder" code="reservation.holder" />
			<br/>
			<acme:textbox path="creditCard.brand" code="reservation.brand" />
			<br/>
			<acme:textbox path="creditCard.number" code="reservation.number" />
			<br/>
			<acme:textbox path="creditCard.expMonth" code="reservation.expMonth" />
			<br/>
			<acme:textbox path="creditCard.expYear" code="reservation.expYear" />
			<br/>
			<acme:textbox path="creditCard.cvv" code="reservation.cvv" />
			<br/>
			
			<acme:submit name="save" code="reservation.save" />
	
			<acme:cancel code="reservation.cancel" url="reservation/user/list.do" />
		
		</jstl:if>
		
		<%-- Creation form --%>
		
		<jstl:if test="${reservation.id == 0}">
		
			<form:hidden path="creditCard" />
			
			<acme:textbox path="adults" code="reservation.adults" />
			<br/>
			<acme:textbox path="children" code="reservation.children" />
			<br/>
			<acme:textbox path="startDate" code="reservation.startDate" placeholder="dd/MM/yyyy" />
			<br/>
			<acme:textbox path="endDate" code="reservation.endDate" placeholder="dd/MM/yyyy" />
			<br/>
			<acme:textarea path="comments" code="reservation.comments" />
			<br/>
				
			<acme:submit name="save" code="reservation.save" />
	
			<acme:cancel code="reservation.cancel" url="resort/list.do" />
		</jstl:if>
		
	</security:authorize>

</form:form>
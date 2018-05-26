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

<%-- For the selected actor received as model, display the following information: --%>

	<acme:displayField code="actor.name" path="${actor.name}" />
	<br/>
	<acme:displayField code="actor.surname" path="${actor.surname}" />
	<br/>
	<acme:displayField code="actor.email" path="${actor.email}" />
	<br/>
	<acme:displayField code="actor.phone" path="${actor.phone}" />
	<br/>
	<acme:displayField code="actor.address" path="${actor.address}" />
	<br/>
	
	<acme:link code="actor.socialIdentities" url="socialIdentity/list.do" id="${actor.id}" column="false" />
	<br/>

</security:authorize>
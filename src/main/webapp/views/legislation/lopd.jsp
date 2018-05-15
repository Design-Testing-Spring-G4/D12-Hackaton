<%--
 * lopd.jsp
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

<security:authorize access="permitAll">

<b><spring:message code="legislation.title1"/></b>
<p><spring:message code="legislation.lopd1"/></p>
<p><spring:message code="legislation.lopd2"/></p>
<p><spring:message code="legislation.lopd3"/></p>

<b><spring:message code="legislation.title2"/></b>
<p><spring:message code="legislation.lopd4"/></p>

<b><spring:message code="legislation.title3"/></b>
<p><spring:message code="legislation.lopd5"/></p>

<b><spring:message code="legislation.title4"/></b>
<p><spring:message code="legislation.lopd6"/></p>

<b><spring:message code="legislation.title5"/></b>
<p><spring:message code="legislation.lopd7"/></p>
<p><spring:message code="legislation.lopd8"/></p>

<b><spring:message code="legislation.title6"/></b>
<p><spring:message code="legislation.lopd9"/></p>

<b><spring:message code="legislation.title7"/></b>
<p><spring:message code="legislation.lopd10"/></p>
<p><spring:message code="legislation.lopd11"/></p>
<p><spring:message code="legislation.lopd12"/></p>

</security:authorize>
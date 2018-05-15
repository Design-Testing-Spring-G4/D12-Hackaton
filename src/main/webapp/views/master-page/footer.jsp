<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message code="master.page.cookies" var="cookies"/>
<spring:message code="master.page.lopd" var="lopd"/>
<spring:message code="master.page.lssi" var="lssi"/>

<jsp:useBean id="date" class="java.util.Date" />

<hr />
<jstl:out value="${cookies}"/> <a href="welcome/cookies.do"><jstl:out value="here"/></a>.<br />
<a class="fNiv" href="welcome/terms.do"><jstl:out value="${lopd}"/></a><br />
<a class="fNiv" href="welcome/contact.do"><jstl:out value="${lssi}"/></a><br />

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme Inc.</b>
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

	<%-- Stored message variables --%>
	
	<spring:message code="actor.phone.confirm" var="phoneConfirm" />
	<spring:message code="actor.save" var="save" />
	
	<%-- Phone validation script --%>
	
	<script type="text/JavaScript">
	        function showMessage(){
	        	var message = document.getElementById("localeConfirm").value;
	
	            var phone = document.getElementById("phoneN").value;
	            var regx1 = new RegExp("^\\+([1-9][0-9]{0,2}) (\\([1-9][0-9]{0,2}\\)) \\d{4,}$");
	    		var regx2 = new RegExp("^\\+([1-9][0-9]{0,2}) \\d{4,}$");
	    		var regx3 = new RegExp("^\\d{4,}$");
	    		if(!regx1.test(phone) && !regx2.test(phone) && !regx3.test(phone)){
	    			var ans = confirm(message);
	    			if (ans == false){
	    				document.getElementById("form").reset();
	    			}
	    		}
	        }
	</script>
	
	<form:form id="form" action="${requestURI}" modelAttribute="actor">
	
		<%-- Hidden variable holding the system's localized message to pass into Javascript function --%>
		<input type="hidden" id="localeConfirm" name="localeConfirm" value="${phoneConfirm}">

		<acme:textbox code="actor.name" path="name"/>
		<br/>
		<acme:textbox code="actor.surname" path="surname"/>
		<br/>
		<acme:textbox code="actor.email" path="email"/>
		<br/>
		<acme:textbox code="actor.phone" path="phone" id="phoneN" placeholder="+34 645645645"/>
		<br/>
		<acme:textbox code="actor.address" path="address"/>
		<br/>
		
		<input type="submit" name="save" value="${save}"
				onclick="showMessage()"/>&nbsp;
				
		<acme:cancel code="actor.cancel" url="welcome/index.do"/>
	
	</form:form>
</security:authorize>
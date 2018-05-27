<%--
 * dashboard.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%-- Stored message variables --%>

<spring:message code="administrator.querycol" var="querycol" />
<spring:message code="administrator.valuecol" var="valuecol" />
<spring:message code="administrator.query1" var="query1" />
<spring:message code="administrator.query2" var="query2" />
<spring:message code="administrator.query3" var="query3" />
<spring:message code="administrator.query4" var="query4" />
<spring:message code="administrator.query5" var="query5" />
<spring:message code="administrator.query6" var="query6" />
<spring:message code="administrator.query7" var="query7" />
<spring:message code="administrator.query8" var="query8" />
<spring:message code="administrator.query9" var="query9" />
<spring:message code="administrator.query10" var="query10" />
<spring:message code="administrator.query11" var="query11" />
<spring:message code="administrator.query12" var="query12" />
<spring:message code="administrator.query13" var="query13" />
<spring:message code="administrator.query14" var="query14" />
<spring:message code="administrator.query15" var="query15" />
<spring:message code="administrator.query16" var="query16" />
<spring:message code="administrator.query17" var="query17" />
<spring:message code="administrator.query18" var="query18" />
<spring:message code="administrator.query19" var="query19" />
<spring:message code="administrator.query20" var="query20" />
<spring:message code="administrator.query21" var="query21" />
<spring:message code="administrator.query22" var="query22" />
<spring:message code="administrator.query23" var="query23" />
<spring:message code="administrator.query24" var="query24" />
<spring:message code="administrator.query25" var="query25" />
<spring:message code="administrator.query26" var="query26" />
<spring:message code="administrator.query27" var="query27" />
<spring:message code="administrator.query28" var="query28" />
<spring:message code="administrator.return" var="returnMsg" />

<security:authorize access="hasRole('ADMIN')">

	<%-- Displays the result of all required database queries --%>

	<table style="width: 100%">
		<tr>
			<th><jstl:out value="${querycol}" /></th>
			<th><jstl:out value="${valuecol}" /></th>
		</tr>
		<tr>
			<td><jstl:out value="${query1}" /></td>
			<td><jstl:out value="${avgMinMaxStddevReservationsPerResort}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query2}" /></td>
			<td><jstl:out value="${avgMinMaxStddevResortsPerManager}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query3}" /></td>
			<td><jstl:out value="${avgMinMaxStddevPricePerReservation}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query4}" /></td>
			<td><jstl:out value="${avgMinMaxStddevActivitiesPerResort}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query5}" /></td>
			<td><jstl:out value="${ratioEntertainmentActivities}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query6}" /></td>
			<td><jstl:out value="${ratioSportActivitiesWithInstructor}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query7}" /></td>
			<td><jstl:out value="${ratioSportActivitiesWithoutInstructor}" /></td>
		</tr>

		<tr>
			<td><jstl:out value="${query8}" /></td>
			<td><jstl:out value="${ratioSportActivities}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query9}" /></td>
			<td><jstl:out value="${ratioTourismActivities}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query10}" /></td>
			<td><jstl:out value="${ratioPendingReservations}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query11}" /></td>
			<td><jstl:out value="${ratioDueReservations}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query12}" /></td>
			<td><jstl:out value="${ratioAcceptedReservations}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query13}" /></td>
			<td><jstl:out value="${ratioRejectedReservations}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query14}" /></td>
			<td><jstl:out value="${resortsWithAboveAverageReservations}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query15}" /></td>
			<td><jstl:out value="${ratioFullResorts}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query16}" /></td>
			<td><jstl:out value="${legalTextTable}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query17}" /></td>
			<td><jstl:out value="${minMaxAvgStddevNotesPerActivity}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query18}" /></td>
			<td><jstl:out value="${minMaxAvgStddevNotesPerLesson}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query19}" /></td>
			<td><jstl:out value="${minMaxAvgStddevAuditsPerResort}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query20}" /></td>
			<td><jstl:out value="${ratioResortsWithAudit}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query21}" /></td>
			<td><jstl:out value="${ratioInstructorsWithCurriculum}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query22}" /></td>
			<td><jstl:out value="${ratioInstructorsEndorsed}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query23}" /></td>
			<td><jstl:out value="${ratioSuspiciousInstructors}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query24}" /></td>
			<td><jstl:out value="${ratioSuspiciousManagers}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query25}" /></td>
			<td><jstl:out value="${ratioSuspiciousUsers}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query26}" /></td>
			<td><jstl:out value="${minMaxAvgStdddevCompetitionsPerSponsor}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query27}" /></td>
			<td><jstl:out value="${topFiveCompetitionsPrizePool}" /></td>
		</tr>
		<tr>
			<td><jstl:out value="${query28}" /></td>
			<td><jstl:out value="${topFiveCompetitionsMaxParticipants}" /></td>
		</tr>
	</table>

	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>

</security:authorize>
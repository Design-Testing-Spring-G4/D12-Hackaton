<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="welcome/index.do">
		<img src="images/logo.png" height="200" width="600" alt="Acme Inc." />
	</a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
	
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>			
					<li><a href="actor/edit.do"><spring:message code="master.page.actor.edit" /> </a></li>
					<li><a href="socialIdentity/actor/list.do"><spring:message code="master.page.socialIdentity.list" /> </a></li>
					<li><a href="folder/list.do"><spring:message code="master.page.folder.list" /> </a></li>
					
					<security:authorize access="hasRole('MANAGER')">
						<li><a href="resort/manager/list.do"><spring:message code="master.page.resort.manager" /> </a></li>
						<li><a href="reservation/manager/list.do"><spring:message code="master.page.reservation.manager" /> </a></li>
						<li><a href="activity/manager/list.do"><spring:message code="master.page.activity.manager" /> </a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('USER')">
						<li><a href="reservation/user/list.do"><spring:message code="master.page.reservation.user" /> </a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('INSTRUCTOR')">
						<li><a href="curriculum/instructor/display.do"><spring:message code="master.page.curriculum.instructor" /> </a></li>
						<li><a href="lesson/instructor/list.do"><spring:message code="master.page.lesson.instructor" /> </a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('AUDITOR')">
						<li><a href="note/auditor/list.do"><spring:message code="master.page.note.auditor" /> </a></li>
						<li><a href="audit/auditor/list.do"><spring:message code="master.page.audit.auditor" /> </a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('SPONSOR')">
						<li><a href="competition/sponsor/list.do"><spring:message code="master.page.competition.sponsor" /> </a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('USER') || hasRole('INSTRUCTOR')">
						<li><a href="participation/actor/list.do"><spring:message code="master.page.participation.actor" /> </a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="tag/administrator/list.do"><spring:message code="master.page.tag.admin" /> </a></li>
						<li><a href="legalText/administrator/list.do"><spring:message code="master.page.legalText.admin" /> </a></li>
						<li><a href="category/administrator/list.do"><spring:message code="master.page.category.admin" /> </a></li>
						<li><a href="actor/administrator/list.do"><spring:message code="master.page.actor.admin" /> </a></li>
						<li><a href="administrator/dashboard.do"><spring:message code="master.page.dashboard.admin" /> </a></li>
					</security:authorize>
					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous() || hasRole('ADMIN')">
			<security:authorize access="isAnonymous()">
				<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			</security:authorize>
			<li>
				<a class="fNiv">
					<spring:message code="master.page.register" />
				</a>
				<ul>
					<li class="arrow"></li>
					
					<security:authorize access="isAnonymous()">
						<li><a href="user/create.do"><spring:message code="master.page.register.user" /></a></li>
					</security:authorize>
					
					<security:authorize access="isAnonymous() || hasRole('ADMIN')">
						<li><a href="manager/create.do"><spring:message code="master.page.register.manager" /></a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="instructor/create.do"><spring:message code="master.page.register.instructor" /></a></li>
					</security:authorize>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="permitAll()">
			<li><a class="fNiv" href="resort/list.do"><spring:message code="master.page.resort.list" /></a></li>
			<li><a class="fNiv" href="resort/listCategory.do?varId=0"><spring:message code="master.page.resort.listCategory" /></a></li>
			<li><a class="fNiv" href="instructor/list.do"><spring:message code="master.page.instructor.list" /></a></li>
		</security:authorize>
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>


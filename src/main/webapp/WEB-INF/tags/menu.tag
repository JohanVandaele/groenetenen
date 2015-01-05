<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='security' uri='http://www.springframework.org/security/tags'%>

<nav>
	<ul>
		<li><a href="<c:url value='/filialen'/>">Filialen</a></li>

		<security:authorize url='/filialen/toevoegen'>
			<li><a href="<c:url value='/filialen/toevoegen'/>">Filiaal toevoegen</a></li>
		</security:authorize>		
		
        <li><a href="<c:url value='/filialen/perpostcode'/>">Filialen per postcode</a></li>
        <li><a href="<c:url value='/filialen/afschrijven'/>">Afschrijven</a></li>				
		
		<security:authorize url='/werknemers'>
			<li><a href="<c:url value='/werknemers'/>">Werknemers</a></li>
		</security:authorize>		
		
		<li><a href="<c:url value='/offertes/aanvraag'/>">Aanvraag offerte</a></li>
		<li><a href="<c:url value='/'/>">Welkom</a></li>
		
		<c:url value='' var='nederlandsURL'>
			<c:param name='locale' value='nl_be' />
		</c:url>
		
		<li><a href='${nederlandsURL}'>Nederlands</a></li>
		
		<c:url value='' var='engelsURL'>
			<c:param name='locale' value='en_us' />
		</c:url>
		
		<li><a href='${engelsURL}'>Engels</a></li>
		
		<!-- ???10.9??? -->
		<li><a href="<c:url value='/info'/>">Info</a></li>
		
		<security:authorize access='isAnonymous()'>
			<li><a href="<c:url value='/login'/>">Aanmelden</a></li>
		</security:authorize>
		
		<security:authorize access='isAuthenticated ()'>
			<li>
				<form method='post' action='<c:url value="/logout"/>' id='logoutform'>
<!-- 				<input type='submit' value='Uitloggen' id='logoutbutton'> -->
					<input type='submit' value='<security:authentication property="name"/> afmelden' id='logoutbutton'>					
					<security:csrfInput/>
				</form>
			</li>
		</security:authorize>
	</ul>

	<security:authentication property='authorities' var='authorities'/>
	<ul>
		<c:forEach items='${authorities}' var='authority'>
			<li>${authority}</li>
		</c:forEach>
	</ul>		
</nav>

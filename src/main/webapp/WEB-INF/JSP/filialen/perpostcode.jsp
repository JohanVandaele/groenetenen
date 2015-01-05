<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<%@taglib prefix='v' uri='http://vdab.be/tags' %>

<!-- Je associeert de prefix form met de URL van de tag library. -->
<%@taglib prefix='form' uri='http://www.springframework.org/tags/form'%>

<!doctype html>

<html lang='nl'>
	<head>
		<v:head title='Filialen per postcode'/>
	</head>

	<body>
		<v:menu/>
		<h1>Filialen per postcode</h1>

		<c:url value='/filialen' var='url'/>
		
		<form:form action="${url}" commandName='postcodeReeks' method='get'>
			<form:label path='vanpostcode'>
				Van:
				<form:errors path='vanpostcode'/>
			</form:label>
			
<%-- 		<form:input path='vanpostcode' autofocus='autofocus'/> --%>
			<form:input path='vanpostcode' autofocus='autofocus' type='number' required='required' min='1000' max='9999'/>

			<form:label path='totpostcode'>
				Tot:
				<form:errors path='totpostcode'/>
<%-- 			<form:errors path='totIsGroterOfGelijkAanVan'/>  ???20.10??? --%>
				<form:errors path='totpostcodeIsGroterOfGelijkAanVanpostcode'/>
			</form:label>
			
<%-- 		<form:input path='totpostcode'/> --%>
			<form:input path='totpostcode' required='required' type='number' min='1000' max='9999'/>

			<input type='submit' value='Zoeken'>
			
			<form:errors element='div' cssClass='fout'/>
		</form:form>

		<c:forEach items='${filialen}' var='filiaal'>
			<spring:url var='url' value='/filialen/{id}'>
				<spring:param name='id' value='${filiaal.id}' />
			</spring:url>
	
			<h2><a href='${url}'>${filiaal.naam}</a></h2>
	
			<p>
				${filiaal.adres.straat} ${filiaal.adres.huisNr}
				<br>
				${filiaal.adres.postcode} ${filiaal.adres.gemeente}
			</p>
		</c:forEach>
	</body>
</html>

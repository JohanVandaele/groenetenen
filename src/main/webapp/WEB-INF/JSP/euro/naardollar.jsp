<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='v' uri='http://vdab.be/tags' %>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<!doctype html>

<html lang='nl'>
	<head>
		<v:head title='Naar dollar'/>
	</head>

	<body>
		<v:menu/>
		<h1>&euro; <fmt:formatNumber value='${euro}'/> is $ <fmt:formatNumber value='${dollar}'/></h1>
	</body>
</html>

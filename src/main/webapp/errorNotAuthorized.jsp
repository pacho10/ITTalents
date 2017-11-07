<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
	<p>
		Oops,you dont have authority for this page--->redirect to dashdoard
		<%-- : <%= exception.getMessage() %> --%>
	</p>

	<a href="./dashboard">Go back to home page</a>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
 <p> Sorry brat ama stana ei tova : <%= exception.getMessage() %> </p>
 
 <a href="./index.jsp">Probvai pak ako iskash</a>
</body>
</html>
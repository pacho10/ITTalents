<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Efficient Project</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">

<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.min.css" />
<link href="bootstrap/css/simple-sidebar.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="customCSS/styles.css">

<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="/LogIn"></c:redirect>
	</c:if>

	<c:choose>
		<c:when test="${sessionScope.user.admin}">
			<jsp:include page="navBarAdmin.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="navBarWorker.jsp"></jsp:include>
		</c:otherwise>
	</c:choose>

	<div id="wrapper" class="toggled">

		<c:choose>
			<c:when test="${sessionScope.user.admin}">
				<jsp:include page="sidebarAdmin.jsp"></jsp:include>
			</c:when>
			<c:otherwise>
				<jsp:include page="sidebarWorker.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>

		<div id="page-content-wrapper">
			<div class="container-fluid">
				<div class="span2">

					<!--  <img src="./ImgOutputServlet?image=user.jpg" alt="" class="img-rounded"
			width="200" height=auto>	  -->

					<img src="./ImgOutputServlet?userid=${sessionScope.user.id}" alt="" class="img-rounded"
						width="200" height=auto>
				</div>
				<div class="span4">
					<blockquote>
						<c:out
							value="${sessionScope.user.firstName }  ${ sessionScope.user.lastName }"></c:out>
					</blockquote>
					<p>

						<i class="fa fa-envelope"></i> ${ sessionScope.user.email } <br>
						<c:if test="${ sessionScope.user.admin == true }">
							<c:out value="admin at: ${ sessionScope.user.organization.name }"></c:out>
						</c:if>

					</p>
				</div>

				<a href="./ProfileEdit"> <input type="button"
					value="Edit profile" />

				</a>
			</div>
		</div>
	</div>
</body>
</html>
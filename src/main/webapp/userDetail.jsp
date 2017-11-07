<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%-- <%@ page errorPage="error.jsp"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Efficient Project</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />


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
			<h4 class="text-info">User profile</h4>
				<hr>
				<div class="span2">

					<img src="./ImgOutputServlet?userid=${user.id}" alt=""
						class="img-rounded" width="200" height=auto>
				</div>
				<div class="span4">
					<blockquote>
						<c:out
							value="${user.firstName }  ${user.lastName }"></c:out>
					</blockquote>
					<p>

						<i class="fa fa-envelope"></i> ${user.email } <br>
						<c:if test="${user.admin == true }">
							<c:out value="admin at: ${user.organization.name }"></c:out>
						</c:if>

					</p>
				</div>

			</div>
		</div>
	</div>
</body>
</html>
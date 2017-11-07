<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="error.jsp"%>
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
	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="/LogIn"></c:redirect>
	</c:if>

	<jsp:include page="navBarAdmin.jsp"></jsp:include>

	<div id="wrapper" class="toggled">
		<jsp:include page="sidebarProject.jsp"></jsp:include>

		<div id="page-content-wrapper">
			<div class="container-fluid">

				<h3 class="text-center text-info">${project.name}</h3>
				<hr> 

				<div id="content">--%>
<div>

	<hr>
	<span><button id="closeButton" onclick="closeEpicDetail()"
			class="btn btn-info">close</button></span>
	<h4 class="text-info">Epic details</h4>
	<div>
		<span class="text-info">Epic name: </span><span>${epic.name}</span>
	</div>

	<div>
		<span class="text-info">Epic description: </span><span>${epic.description}</span>
	</div>
	<div>
		<span class="text-info">Epic estimate: </span><span>${epic.estimate}
			days</span>
	</div>
	<hr>
	<h4 class="text-info">Tasks in epic:</h4>
	<c:choose>
		<c:when test="${empty tasks}">
			<span>No tasks in this epic</span>
		</c:when>
		<c:otherwise>
			<div class="table-responsive">
				<table class="table">
					<thead>
						<td>Task Summary</td>
						<td>Task Status</td>
					</thead>

					<c:forEach var="task" items="${tasks}">
						<tr>
							<td><a
								href="./taskdetail?taskId=${task.id}&projectId=${epic.project.id}">${task.summary}</a></td>
							<td>${task.status}</td>
						</tr>
					</c:forEach>

				</table>
			</div>
		</c:otherwise>
	</c:choose>


</div>
<!--</div>

			 </div>
		</div>
	</div>
</body>
</html> -->
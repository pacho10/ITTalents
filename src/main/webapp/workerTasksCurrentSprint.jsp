<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="error.jsp"%>
<%@ page import="bg.ittalents.efficientproject.util.Datetime"%>
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

<body onload="SidebarChangeConent();">

	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="/LogIn"></c:redirect>
	</c:if>

	<%-- Navbar  --%>
	<c:choose>
		<c:when test="${sessionScope.user.admin}">
			<jsp:include page="navBarAdmin.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="navBarWorker.jsp"></jsp:include>
		</c:otherwise>
	</c:choose>

	<!-- Page  -->
	<div id="wrapper" class="toggled">

		<!-- Sidebar -->
		<c:choose>
			<c:when test="${sessionScope.user.admin}">
				<jsp:include page="sidebarProject.jsp?projectId=${project.id}"></jsp:include>
			</c:when>
			<c:otherwise>
				<jsp:include page="sidebarWorker.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>

		<!-- Page Content -->
		<div id="page-content-wrapper">
			<div id="content">
				<div class="container-fluid" id="dashboard">
					<h3 class="text-center text-info">${project.name}</h3>
					<h4 class="text-center text-info">Current sprint backlog</h4>
					<hr>
					<div class="table-responsive">
						<table class="table">
							<thead>
								<th>Type</th>
								<th>Summary</th>
								<th>Status</th>
								<th>Updated Date</th>
								<th>Reporter</th>
								<th>Assignee</th>
								<th></th>
							</thead>
							<c:forEach var="t" items="${tasks}">
								<tr>
									<td>${t.type.name}</td>
									<td><a href="./taskdetail?taskId=${t.id}&projectId=${project.id}">${t.summary}</a></td>
									<td>${t.status}</td>
									<td>${Datetime.timestampAsString(t.updatedDate)}</td>
									<td><a href="./profileDetail?userId=${t.reporter.id}"><span>${t.reporter.firstName}</span><span> </span><span>${t.reporter.lastName}</span></a></td>
									<td><a href="./profileDetail?userId=${t.assignee.id}"><span>${t.assignee.firstName}</span><span> </span><span>${t.assignee.lastName}</span></a></td>
									<c:if test="${not sessionScope.user.admin}">
										<td><c:choose>
												<c:when test="${t.assignee == null}">
													<a class="btn btn-info"
														href="/final_project/assignetask?taskId=${t.id}">Get
														Task</a>
												</c:when>
												<c:otherwise>
													<span>task is alredy taken</span>
												</c:otherwise>
											</c:choose></td>
									</c:if>
								</tr>
							</c:forEach>
						</table>
					</div>

				</div>

			</div>
		</div>
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->

</body>
</html>
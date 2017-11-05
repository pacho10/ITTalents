<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="bg.ittalents.efficientproject.util.Datetime" %>
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

	<%-- 	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="/LogIn"></c:redirect>
	</c:if> --%>

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
			<div class="container-fluid">

				<h3 class="text-center text-info">${project.name}</h3>
				<hr>
				<h4 class="text-center text-info">Task details</h4>
				<div id="content">
					<div>
						<%-- <span>${project.name}</span> <span>${project.deadline}</span> --%>
						<div>
							<div class="table-responsive">
								<table class="table">
									<thead>
										<td></td>
										<td></td>
									</thead>
									<tr>
										<td>Summary:</td>
										<td>${task.summary}</td>
									</tr>
									<tr>
										<td>Description:</td>
										<td>${task.description}</td>
									</tr>
									<tr>
										<td>Reporter:</td>
										<td><img id="avatar2"
											src="./ImgOutputServlet?userid=${reporter.id}"
											class="img-rounded"> ${reporter.firstName}</td>
									</tr>
									<tr>
										<td>Assignee:</td>
										<td><img id="avatar2"
											src="./ImgOutputServlet?userid=${assignee.id}"
											class="img-rounded"> ${assignee.firstName}</td>
									</tr>
									<tr>
										<td>Time tracking:</td>
									</tr>
									<tr>
										<td>Estimate time :</td>
										<td>${task.estimate}</td>
									</tr>
									<tr>
										<td>Remaining time:</td>
										<td></td>
									</tr>
									<tr>
										<td>Logged:</td>
										<td></td>
									</tr>
								</table>
							</div>
							<div>
								<hr>
								<div class="table-responsive">
									<table class="table">
										<thead>
											<td>Type</td>
											<td>Status</td>
											<td>Epic</td>
											<td>Sprint</td>
											<td>Updated on:</td>
										</thead>
										<tr>
											<td>${task.type}</td>
											<td>${task.status}</td>
											<td>${epic.name}</td>
											<td>${sprint.name}</td>
											<td>${Datetime.timestampAsString(task.updatedDate)}</td>
										</tr>
									</table>
								</div>

							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
</body>
</html>
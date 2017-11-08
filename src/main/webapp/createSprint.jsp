<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${all==1}">
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>
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
	<c:if test="${ sessionScope.user == null && not sessionScope.user.admin }">
		<c:redirect url="/LogIn"></c:redirect>
	</c:if>


	<jsp:include page="navBarAdmin.jsp"></jsp:include>
	<!-- Page  -->
	<div id="wrapper" class="toggled">

		<!-- Sidebar -->
		<jsp:include page="sidebarProject.jsp">
			<jsp:param name="project" value="${project}" />
		</jsp:include>

		<!-- Page Content -->
		<div id="page-content-wrapper">
			<h3 class="text-center text-info">${project.name}</h3>
			<hr>
</c:if>


<div id="createproject" style="margin-top: 50px;"
	class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

	<div class="panel panel-info">
		<div class="panel-heading">
			<div class="panel-title">Create New Sprint</div>
		</div>
		<div class="panel-body">
			<form method="post" action="./createsprint" id="create-sprint"
				class="form-horizontal">

				<input type="hidden" name="projectId" value="${projectId}">
				<!--<div id="signupalert" style="display: none"
						class="alert alert-danger">
						<p>Error:</p>
						<span></span>
					</div>-->
				<div class="form-group">
					<label for="name" class="col-md-3 control-label">Name</label>
					<div class="col-md-9">
						<input type="text" class="form-control" name="name"
							placeholder="Name" required>
					</div>
				</div>
				<div class="form-group">
					<label for="duration" class="col-md-3 control-label">Duration</label>
					<div class="col-md-9">
						<input type="text" id="duration" class="form-control"
							name="duration" placeholder="Duration" required>
					</div>
				</div>
				<c:if test="${not empty errorMessage }">
					<div class="form-group">
						<div class="col-md-offset-3 col-md-9">
							<span style="color: red"> <c:out value="${ errorMessage }" />
							</span>
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<!-- Button -->
					<div class="col-md-offset-3 col-md-9">
						<button id="btn-create-project" type="submit" class="btn btn-info">
							<i class="icon-hand-right"></i> Create
						</button>
						<button
							onclick="location.href = './projectdetail?projectId=${projectId}';"
							id="cancelButton" class="btn btn-info">Cancel</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<c:if test="${all==1}">
	</div>
	<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->
	</body>
	</html>
</c:if>

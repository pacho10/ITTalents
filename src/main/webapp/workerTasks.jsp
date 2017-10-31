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
	<jsp:include page="navBarWorker.jsp"></jsp:include>

	<!-- Page  -->
	<div id="wrapper" class="toggled">

		<!-- Sidebar -->
		<%-- REMOVE JSP FROM HERE!!!!! --%>
		<jsp:include page="sidebarWorker.jsp"></jsp:include>

		<!-- Page Content -->
		<div id="page-content-wrapper">
			<div id="content">
				<div class="container-fluid" id="dashboard">
					<h1 class="text-center text-success">${organizationName}</h1>
					<hr>
					<div class="table-responsive">
						<table class="table">
							<thead>
								<th>Type</th>
								<th>Summary</th>
								<th>Status</th>
								<!-- <th>Description</th> -->
								<!-- <th>Estimate</th> -->
								<th>Updated Date</th>
								<th>Reporter</th>
								<th>Assignee</th>
								<th>Finish</th>
								<th></th>
								<!-- <th>Epic</th> -->
							</thead>
							<c:forEach var="t" items="${tasks}">
								<tr>
									<td>${t.type.name}</td>
									<td>${t.summary}</td>
									<td>${t.status}</td>
									<%-- <td>${t.description}</td> --%>
									<%-- <td>${t.estimate}</td> --%>
									<%-- <td>${t.creationDate}</td> --%>
									<td>${t.updatedDate}</td>
									<td>${t.reporter.firstName}</td>
									<td>${t.assignee.firstName}</td>
									<%-- <td>${t.epic.name}</td> --%>
									<td>
										<a class="btn btn-info" href="/final_project/finishtask?taskId=${t.id}">Finish</a>
									</td>
									<%-- <td>Cancel Button</td> --%>
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
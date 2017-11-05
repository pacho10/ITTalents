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
<script type="text/javascript">
	function addToSprint() {

	}
</script>


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
					<c:choose>
						<c:when test="${backLog==0}">
							<h4 class="text-center text-info">Project tasks history</h4>
						</c:when>
						<c:otherwise>
							<h4 class="text-center text-info">Project backlog tasks</h4>
						</c:otherwise>
					</c:choose>

					<hr>
					<div class="table-responsive">
						<table class="table">
							<thead>
								<th>Type</th>
								<th>Summary</th>
								<th>Status</th>
								<th>Updated Date</th>
								<th>Reporter</th>

								<c:if test="${backLog==0}">
									<th>Assignee</th>
								</c:if>
								<c:if test="${backLog==1}">
									<th></th>
								</c:if>
							</thead>
							
							<tbody id="update">
								<c:forEach var="t" items="${tasks}">
									<tr>
										<td>${t.type.name}</td>
										<td><a
											href="./taskdetail?taskId=${t.id}&projectId=${project.id}">${t.summary}</a></td>
										<td>${t.status}</td>
										<td>${t.updatedDate}</td>
										<td>${t.reporter.firstName}</td>

										<c:if test="${backLog==0}">
											<td>${t.assignee.firstName}</td>
										</c:if>
										<c:if test="${sessionScope.user.admin}">
											<c:if test="${backLog==1}">
												<td><c:choose>
														<c:when test="${not empty sprintId}">
															<a class="btn btn-info"  href="/final_project/addTaskToSprint?taskId=${t.id}&sprintId=${sprintId}&projectId=${project.id} ">Add
																to sprint</a>
														</c:when>
														<c:otherwise>
															<a href="./createsprint?projectId=${project.id}&all=1">Start
																new sprint</a>
														</c:otherwise>
													</c:choose>
											</c:if>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
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
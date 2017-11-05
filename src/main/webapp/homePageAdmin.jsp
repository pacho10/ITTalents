<%@ page language="java" pageEncoding="UTF-8"%>
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


	<c:if test="${ sessionScope.user == null and not user.isAdmin}">
		<c:redirect url="/LogIn"></c:redirect>
	</c:if>

	<%-- Navbar  --%>
	<jsp:include page="navBarAdmin.jsp"></jsp:include>

	<!-- Page  -->
	<div id="wrapper" class="toggled">

		<!-- Sidebar -->
		<jsp:include page="sidebarAdmin.jsp"></jsp:include>

		<!-- Page Content -->
		<div id="page-content-wrapper">
			<div id="content">
				<div class="container-fluid" id="dashboard">
					<h2 class="text-center text-info">${organizationName}</h2>
					<hr>
					<div class="table-responsive">
						<table class="table">
							<thead>
								<td>Project Name</td>
								<td>Project deadline</td>
							</thead>
							<c:forEach var="p" items="${projects}">
								<tr>
									<td><a
										href="/final_project/projectdetail?projectId=${p.id}">${p.name}</a>
									</td>
									<td>${p.deadline}</td>
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
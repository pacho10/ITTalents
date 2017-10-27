<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
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
</head>

<body>

	<div id="wrapper" class="toggled">
		<c:choose>
			<c:when test="${sessionScope.user.admin}">
				<jsp:include page="navBarAdmin.jsp"></jsp:include>
				<jsp:include page="sidebarAdmin.jsp"></jsp:include>
			</c:when>
			<c:otherwise>
				<jsp:include page="navBarWorker.jsp"></jsp:include>
				<jsp:include page="sidebarWorker.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>

		<!-- Sidebar -->
		<!-- <div id="sidebar-wrapper">

			<ul class="sidebar-nav">
				<li class="sidebar-brand"><a href="#"> Start Bootstrap </a></li>
				<li><a href="#">Admin</a></li>
				<li><a href="#">Shortcuts</a></li>
				<li><a href="#">Overview</a></li>
				<li><a href="#">Events</a></li>
				<li><a href="#">About</a></li>
				<li><a href="#">Services</a></li>
				<li><a href="#">Contact</a></li>
			</ul>
		</div> -->
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="page-content-wrapper">
			<div class="container-fluid">

				<%-- 				<h1>Simple Sidebar</h1>
				<p>This template has a responsive menu toggling system. The menu
					will appear collapsed on smaller screens, and will appear
					non-collapsed on larger screens. When toggled using the button
					below, the menu will appear/disappear. On small screens, the page
					content will be pushed off canvas.</p>
				<p>
					Make sure to keep all page content within the
					<code>#page-content-wrapper</code>
					.
				</p> --%>

				<h1 class="text-center">${organizationName}</h1>
				<hr>
				<div class="table-responsive">
					<table class="table">
					<thead>
					<td>
					Project Id
					</td>
					<td>
					Project Name
					</td>
					<td>
					Project deadline
					</td>
					</thead>
					<c:forEach var="p" items="${projects}">
					<tr>
						<td>
						${p.id}
						</td>
						<td>
							<a href="/final_project/projectdetail?projectId=${p.id}">${p.name}</a>
						</td>
						<td>
						${p.deadline}
						</td>
					</tr>
				</c:forEach>
				</table>
				</div>
				
			</div>
		</div>
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->

</body>
</html>
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


<script type="text/javascript">
	$(document).ready(function() {
		$("a.noReLoad").click(function() {
			var myhref = $(this).attr('href');
			$("#content").empty();
			$('#content').load(myhref);
			return false;
		});
	});
</script>



</head>

<body>
	<c:if
		test="${ sessionScope.user == null && not sessionScope.user.admin}">
		<c:redirect url="/LogIn"></c:redirect>
	</c:if>

	<jsp:include page="navBarAdmin.jsp"></jsp:include>

	<div id="wrapper" class="toggled">
		<jsp:include page="sidebarProjectDetailed.jsp"></jsp:include>

		<div id="page-content-wrapper">
			<div class="container-fluid">

				<h3 class="text-center text-info">${project.name}</h3>
				<hr>

				<div id="content">
					<div>
						<%-- <span>${project.name}</span> --%>
						<div>
							<span> Deadline: ${project.deadline}</span>
						</div>
						<div>
							<c:choose>
								<c:when test="${currentSprint != null}">
									<span>Current sprint: ${currentSprint.name}</span>
								</c:when>
								<c:otherwise>
									<div>
										No current sprint, <a
											href="./createsprint?projectId=${project.id}&all=1">start
											new one here</a>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div>
						<hr>
						<h3>Epics</h3>
						<hr>
						<div class="table-responsive">
							<table class="table">
								<thead>
									<td>Epic Name</td>
									<td>Epic Estimate</td>
								</thead>
								<c:forEach var="e" items="${epics}">
									<tr>
										<td><a href="./epicdetail?epicId=${e.id}">${e.name}</a></td>
										<td>${e.estimate}</td>
									</tr>
								</c:forEach>
							</table>
						</div>

						<hr>
						<h3>Workers</h3>
						<div class="table-responsive">
							<table class="table">
								<thead>
									<td></td>
									<td>Name</td>
									<td>Email</td>
								</thead>
								<c:forEach var="u" items="${workers}">
									<c:if test="${not u.admin}">
										<tr>
											<td><img id="avatar2"
												src="./ImgOutputServlet?userid=${u.id}" class="img-rounded"></td>
											<%-- <td>${u.avatarPath}</td> --%>
											<td>${u.firstName} ${u.lastName}</td>
											<td>${u.email}</td>
										</tr>
									</c:if>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>
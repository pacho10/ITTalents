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
<script type="text/javascript" src="js/Chart.bundle.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<c:if test="${projectFinished}">
</c:if>

<script type="text/javascript">
	$(document).ready(function() {
		$("a.noReLoad").click(function() {
			var myhref = $(this).attr('href');
			$("#content").empty();
			$('#content').load(myhref);
			return false;
		});
	});
	var daysLeft;
	var daysPast;
	var isProjectFinished=${projectFinished};
	if(isProjectFinished){
		daysLeft=0;
		daysPast=${project.duration};
	}else{
		daysLeft=${project.daysLeft};
		daysPast= ${project.duration}-(${project.daysLeft}); 
	};
	
	var config = {
		type : 'pie',
		data : {
			datasets : [ {
				data : [ daysPast,  daysLeft ],
				backgroundColor : [ window.chartColors.orange,
						window.chartColors.blue, ],
				label : 'Dataset 1'
			} ],
			labels : [ "Days past", "Days left" ]
		},
		options : {
			responsive : true
		}
	};
	
	var tasksOpen;
	var tasksDone;
	var tasksInProgress;
	var config2 = {
			type : 'pie',
			data : {
				datasets : [ {
					data : [ tasksOpen,  tasksDone, tasksInProgress],
					backgroundColor : [ window.chartColors.orange,
							window.chartColors.blue, window.chartColors.red],
					label : 'Dataset 1'
				} ],
				labels : ["Open tasks", "Tasks done", "Tasks in progress" ]
			},
			options : {
				responsive : true
			}
		};
	window.onload = function() {
		var ctx = document.getElementById("chart-area").getContext("2d");
		window.myPie = new Chart(ctx, config);
		var ctx2 = document.getElementById("chart-area2").getContext("2d");
		window.myPie = new Chart(ctx2, config2);
	};
</script>



</head>

<body>
	<c:if
		test="${ sessionScope.user == null && not sessionScope.user.admin}">
		<c:redirect url="/LogIn"></c:redirect>
	</c:if>

	<jsp:include page="navBarAdmin.jsp"></jsp:include>

	<div id="wrapper" class="toggled">
		<c:choose>
			<c:when test="${projectFinished}">
				<jsp:include page="sidebarProject.jsp"></jsp:include>
			</c:when>
			<c:otherwise>
				<jsp:include page="sidebarProjectDetailed.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>

		<div id="page-content-wrapper">
			<div class="container-fluid">

				<h3 class="text-center text-info">${project.name}</h3>
				<hr>

				<div id="content">
					<div>
						<div>
							<c:choose>
								<c:when test="${projectFinished}">
									<span>Project is finished</span>
								</c:when>
								
								<c:otherwise>
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
								</c:otherwise>
								
							</c:choose>
						</div>
						<div>
							<span>Start date: ${project.startDate}</span>
						</div>
						<div>
							<span> Deadline: ${project.deadline}</span>
						</div>
						<div id="canvas-holder" style="width: 40%">
						<div><h4 class="text-center text-info">Time log</h4></div>
							<canvas id="chart-area" />
						</div>
												<div id="canvas-holder" style="width: 40%">
						<div><h4 class="text-center text-info">Tasks statistics</h4></div>
							<canvas id="chart-area2" />
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
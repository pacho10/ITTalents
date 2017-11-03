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
<script type="text/javascript" src="js/Chart.min.js"></script>
</head>

<body onload="barCharStat();">
	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="/LogIn"></c:redirect>
	</c:if>

	<%-- Navbar  --%>
	<jsp:include page="navBarWorker.jsp"></jsp:include>

	<!-- Page  -->
	<div id="wrapper" class="toggled">

		<!-- Sidebar -->
		<jsp:include page="sidebarWorker.jsp"></jsp:include>
		<!-- Page Content -->
		<div id="page-content-wrapper">
			<div id="content">
				<div class="container-fluid" id="dashboard">
					<h3 class="text-center text-info">${project.name}</h3>
					<h4 class="text-center text-info">My open tasks</h4>
					<hr>
				</div>
				<canvas id="chart" width="100px" height="100px"></canvas>
			</div>
		</div>
		<!-- /#page-content-wrapper -->
	</div>
	<!-- /#wrapper -->
	
	
	
	
	<script type="text/javascript">
		function barCharStat() {
			$.ajax({
				type: "GET",
				url: "http://127.0.0.1:8080/final_project/barchartstatisticsservlet?projectId=${project.id}",
				success: function (data) {
					console.log(data);
					var ctx = $("#chart");
					var myBarChart = new Chart(ctx, {
						type: 'bar',
					    data: {
					        labels: renderName(data),
					        datasets: [{
					            label: '# of Votes',
					            data: renderTasks(data),
					            backgroundColor: [
					                'rgba(255, 99, 132, 0.2)',
					                'rgba(54, 162, 235, 0.2)',
					                'rgba(255, 206, 86, 0.2)',
					                'rgba(75, 192, 192, 0.2)',
					                'rgba(153, 102, 255, 0.2)',
					                'rgba(255, 159, 64, 0.2)'
					            ],
					            borderColor: [
					                'rgba(255,99,132,1)',
					                'rgba(54, 162, 235, 1)',
					                'rgba(255, 206, 86, 1)',
					                'rgba(75, 192, 192, 1)',
					                'rgba(153, 102, 255, 1)',
					                'rgba(255, 159, 64, 1)'
					            ],
					            borderWidth: 1
					        }]
					    },
					    options: {
					        scales: {
					            xAxes: [{
					                stacked: true
					            }],
					            yAxes: [{
					                stacked: true
					            }]
					        }
					    }
					});
				},
				error: function (err) {
					//console.log(err);
				}
			});
		}
	
		function renderTasks(data) {
			var result = [];
			for (var i = 0; i < data.length; i++) {
				result[i] = data[i].tasks;
			}
			
			return result;
		}
		
		function renderName(data) {
			var result = [];
			for (var i = 0; i < data.length; i++) {
				result[i] = data[i].name;
			}
			
			return result;
		}
	</script>
</body>
</html>
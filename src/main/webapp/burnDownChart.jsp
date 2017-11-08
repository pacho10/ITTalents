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
<style>
canvas {
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
}

.chart-container {
	width: 500px;
	margin-left: 40px;
	margin-right: 40px;
	margin-bottom: 40px;
}

.container {
	display: flex;
	flex-direction: row;
	flex-wrap: wrap;
	justify-content: center;
}
</style>
<script>
	var myObj;
	window.onload = function() {
		$.ajax({
			type : "GET",
			url : "./burnDownChart?projectId=${projectId}",
			success : function(burndown) {
				myObj = burndown;
				var keyNames = Object.keys(myObj);
				console.log(keyNames);
				var values = Object.values(myObj);
				console.log(values);

				var container = document.querySelector('.container');

				var steppedLineSettings = [ {
					steppedLine : false,
					label : 'Burndown chart',
					color : window.chartColors.red
				} ];

				steppedLineSettings.forEach(function(details) {
					var div = document.createElement('div');
					div.classList.add('chart-container');

					var canvas = document.createElement('canvas');
					div.appendChild(canvas);
					container.appendChild(div);

					var ctx = canvas.getContext('2d');
					var config = createConfig(details, values, keyNames);
					new Chart(ctx, config);
				});

			},
			error : function(err) {
				console.log(err);
			}
		});
	}

	function createConfig(details, data, keyNames) {
		return {
			type : 'line',
			data : {
				labels : keyNames,
				datasets : [ {
					label : 'tasks number: ',
					steppedLine : details.steppedLine,
					data : data,
					borderColor : details.color,
					fill : false,
				} ]
			},
			options : {
				responsive : true,
				title : {
					display : true,
					text : details.label,
				}
			}
		};
	}

	function myFunction1() {
		var stringJson = JSON.stringify(myObj);
		$.ajax({
			type : "POST",
			url : "./SaveStatistics",
			data : {
				statistics : stringJson
			}

		});
	}
</script>


</head>

<body>


	<jsp:include page="navBarAdmin.jsp"></jsp:include>

	<div id="wrapper" class="toggled">
		<jsp:include page="sidebarProject.jsp"></jsp:include>

		<div id="page-content-wrapper">
			<div class="container-fluid">


				<div id="content">

					<div class="container"></div>
				</div>
				<div>
					<button onclick="myFunction1()" class="btn btn-info btn-sm">Generate
						PDF</button>

				</div>
			</div>

		</div>
	</div>
</body>
</html>
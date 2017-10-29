<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ page isELIgnored="false"%>
<%@ page errorPage="error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Efficient Project</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="font-awesome/css/font-awesome.min.css" />

<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="bootstrap/css/simple-sidebar.css" rel="stylesheet">
</head>
<body> --%>
	<div id="createproject" style="margin-top: 50px;"
		class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

		<div class="panel panel-info">
			<div class="panel-heading">

				<div class="panel-title">Create New Epic</div>

			</div>
			<div class="panel-body">				
				<form method="post" action="./createepic" id="create-epic"
					class="form-horizontal" role="form">

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
						<label for="estimate" class="col-md-3 control-label">Estimate</label>
						<div class="col-md-9">
							<input type="text" id="estimate" class="form-control"
								name="estimate" placeholder="Estimate" required>
						</div>
					</div>
					
					<div class="form-group">
						<label for="description" class="col-md-3 control-label">Description</label>
						<div class="col-md-9">
							<input type="text" id="description" class="form-control"
								name="description" placeholder="Description" required>
						</div>
					</div>
					
					<div class="form-group">
						<label for="all-projects" class="col-md-3 control-label">All projects</label>
						<div class="col-md-9">
							<select class="col-md-9" id="all-projects" name="projects">
								<c:forEach var="p" items="${projects}">
									<option value="${p.id}">${p.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<c:if test="${not empty errorMessage }">
						<div class="form-group">
							<div class="col-md-offset-3 col-md-9">
								<span style="color: red"> <c:out
										value="${ errorMessage }" />
								</span>
							</div>
						</div>
					</c:if>

					<div class="form-group">
						<!-- Button -->
						<div class="col-md-offset-3 col-md-9">
							<button id="btn-create-epic" type="submit" class="btn btn-info">
								<i class="icon-hand-right"></i> Create
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>	
<!-- </body>
</html> -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>More Details</title>
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
<body>
	<div id="loginbox" style="margin-top: 50px;"
		class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

		<div class="panel panel-info">
			<div class="panel-heading">

				<div class="panel-title">
					Hello
					<c:out value="${sessionScope.user.fullName}" />
					we need more details
				</div>

			</div>
			<div class="panel-body">

				<form method="post" action="SignUpDetailsServlet" id="signupform"
					class="form-horizontal" role="form" enctype="multipart/form-data">

					<div id="signupalert" style="display: none"
						class="alert alert-danger">
						<p>Error:</p>
						<span></span>
					</div>

					<c:if test="${not empty user.organization.name }">
						<div class="form-group">
							<label for="oranization" class="col-md-3 control-label">Organization
								name</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="orgName"
									placeholder="<c:out value="${user.organization.name}" />"
									value="<c:out value="${user.organization.name}" />" disabled>
							</div>
					</c:if>

					<c:if test="${empty user.organizationName }">
						<div class="form-group">
							<label for="oranization" class="col-md-3 control-label">Organization
								name</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="orgName"
									placeholder="Organization name" required>
							</div>
						</div>
					</c:if>

					<div class="form-group">
						<label for="email" class="col-md-3 control-label"> Upload
							your profile picture</label>
						<div class="col-md-9">
							<input type="file" class="form-control" name="avatar">
						</div>
					</div>


					<div class="form-group">
						<!-- Button -->
						<div class="col-md-offset-3 col-md-9">
							<button id="btn-signup" type="submit" class="btn btn-info">
								<i class="icon-hand-right"></i> Continue
							</button>

						</div>
					</div>

				</form>
			</div>
		</div>
	</div>



</body>
</html>
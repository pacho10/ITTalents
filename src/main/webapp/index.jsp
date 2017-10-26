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

<!-- disable the browser cache -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">

<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="font-awesome/css/font-awesome.min.css" />

<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>


</head>
<body style="background-color: #F0F8FF">
	<div class="text-info">
		<div class="text-center">
			<h1>Efficient Project</h1>
			<h2>Get productive and efficient in a few simple steps!</h2>
			<img src="img/pm.png" class="img-rounded" width="620" height=auto>
		</div>
	</div>

	<div id="loginbox" style="margin-top: 50px;"
		class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

		<div class="panel panel-info">
			<div class="panel-heading">

				<div class="panel-title">Login in Efficient Project</div>

			</div>
			<div class="panel-body">


				<form method="post" action="./LogIn" id="login-form"
					class="form-horizontal" role="form">

					<div id="signupalert" style="display: none"
						class="alert alert-danger">
						<p>Error:</p>
						<span></span>
					</div>

					<div class="form-group">
						<label for="email" class="col-md-3 control-label">Email</label>
						<div class="col-md-9">
							<input type="text" class="form-control" name="email"
								placeholder="Email Address" required>
						</div>
					</div>


					<div class="form-group">
						<label for="password" class="col-md-3 control-label">Password</label>
						<div class="col-md-9">
							<input type="password" id="pass1" class="form-control"
								name="password" placeholder="Password" required>
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
							<button id="btn-signup" type="submit" class="btn btn-info">
								<i class="icon-hand-right"></i> Login
							</button>

							<span style="margin-left: 8px;">Do`t have a registration?<a
								href="./SignUp"> Sign up here </a></span>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>


</body>
</html>
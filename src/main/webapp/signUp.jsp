<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
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

<script type="text/javascript">
	function myFunction() {
		var pass1 = document.getElementById("pass1").value;
		var pass2 = document.getElementById("pass2").value;
		var ok = true;
		if (pass1 != pass2) {
			alert("Passwords Do not match");
			document.getElementById("pass1").style.borderColor = "#E34234";
			document.getElementById("pass2").style.borderColor = "#E34234";
			ok = false;
		}
		return ok;
	}

	function myFunction2() {
		var x = document.getElementById("organization");
		 var y = document.getElementById("isAdmin"); 
		
		if (x.style.display === "block") {
			x.style.display = "none";
		 	y.value="false" 
		} else {
			x.style.display = "block";
		 	y.value="true"
		}
	}
</script>
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

		<div class="panel panel-success">
			<div class="panel-heading">

				<div class="panel-title">Sign in Efficient Project</div>

			</div>
			<div class="panel-body">


				<form method="post" action="./SignUp" id="login-form"
					class="form-horizontal">

					<div id="signupalert" style="display: none"
						class="alert alert-danger">
						<p>Error:</p>
						<span></span>
					</div>

					<div class="form-group">
						<label for="first-name" class="col-md-3 control-label">First
							Name</label>
						<div class="col-md-9">
							<input type="text" class="form-control" name="first-name"
								placeholder="First Name" required>
						</div>
					</div>


					<div class="form-group">
						<label for="last-name" class="col-md-3 control-label">Last
							Name</label>
						<div class="col-md-9">
							<input type="last-name" class="form-control" name="last-name"
								placeholder="Last name" required>
						</div>
					</div>


					<div class="form-group">
						<label for="email" class="col-md-3 control-label">Email</label>
						<div class="col-md-9">
							<input type="email" class="form-control" name="email"
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

					<div class="form-group">
						<label for="repPassword" class="col-md-3 control-label">
							Repeat Password</label>
						<div class="col-md-9">
							<input type="password" id="pass2" class="form-control"
								name="repPassword" placeholder=" Repeat Password" required>
						</div>
					</div>

					<div class="checkbox">
						<label><input  type="checkbox"  id="isAdmin" value ="false" name="isAdmin" onclick="myFunction2()" 
							>Register organization</label>
					</div>

					<div class="form-group" style="display: none;" id="organization">
						<label for="organization" class="col-md-3 control-label">
							Organization name</label>
						<div class="col-md-9">
							<input type="text" id="organization" class="form-control"
								name="organization" placeholder=" Name" >
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
							<button onclick="myFunction()" id="btn-signup" type="submit"
								class="btn btn-success">Sign up</button>

							<span style="margin-left: 8px;">Already a member?<a
								href="./LogIn"> Log in here </a></span>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>


</body>
</html>
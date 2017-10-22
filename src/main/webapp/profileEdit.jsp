<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="error.jsp" %>
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
</script>
</head>

<body>
	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="/LogIn"></c:redirect>
	</c:if>

	<jsp:include page="navBar.jsp"></jsp:include>

	<div id="loginbox" style="margin-top: 50px;"
		class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

	
		<div class="panel panel-success">
			<div class="panel-heading">

				<div class="panel-title">Edit your profile</div>

			</div>
			<div class="panel-body">


				<form method="post" action="./ProfileEdit" id="login-form" enctype="multipart/form-data"
					class="form-horizontal" >

					<div id="signupalert" style="display: none"
						class="alert alert-danger">
						<p>Error:</p>
						<span></span>
					</div>

					<div class="form-group">
					<img id="avatar2" src=${ sessionScope.user.avatarPath }>
					</div>

					<div class="form-group">
						<label for="avatar" class="col-md-3 control-label"> Upload
							new avatar</label>
						<div class="col-md-9">
							<input type="file" class="form-control" name="avatar">
						</div>
					</div>


					<div class="form-group">
						<label for="first-name" class="col-md-3 control-label">First
							Name</label>
						<div class="col-md-9">
							<input type="text" class="form-control" name="first-name" value=${ sessionScope.user.firstName }
								placeholder="First Name" >
						</div>
					</div>


					<div class="form-group">
						<label for="last-name" class="col-md-3 control-label">Last
							Name</label>
						<div class="col-md-9">
							<input type="last-name" class="form-control" name="last-name" value=${ sessionScope.user.lastName }
								placeholder="Last name" >
						</div>
					</div>


					<div class="form-group">
						<label for="email" class="col-md-3 control-label">Email</label>
						<div class="col-md-9">
							<input type="email" class="form-control" name="email" value=${ sessionScope.user.email}
								placeholder="Email Address" >
						</div>
					</div>

					<div class="form-group">
						<label for="password" class="col-md-3 control-label">Old
							password</label>
						<div class="col-md-9">
							<input type="password" id="pass1" class="form-control"
								name="old-password" placeholder="Old Password" >
						</div>
					</div>


					<div class="form-group">
						<label for="password" class="col-md-3 control-label">New
							password</label>
						<div class="col-md-9">
							<input type="password" id="pass1" class="form-control"
								name="new-password" placeholder="New Password" >
						</div>
					</div>

					<div class="form-group">
						<label for="repPassword" class="col-md-3 control-label">
							Repeat new password</label>
						<div class="col-md-9">
							<input type="password" id="pass2" class="form-control"
								name="rep-new-password" placeholder=" Repeat New Password" >
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
								class="btn btn-success">Submit</button>
						</div>
					</div>
					
				</form>
				
			</div>
		</div>
	</div>


</body>
</html>
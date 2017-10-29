 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	<div id="createproject" style="margin-top: 50px;"
		class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

		<div class="panel panel-info">
			<div class="panel-heading">
				<div class="panel-title">Create New Sprint</div>
			</div>
			<div class="panel-body">	
				<form method="post" action="./createsprint" id="create-sprint"
					class="form-horizontal">

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
						<label for="duration" class="col-md-3 control-label">Duration</label>
						<div class="col-md-9">
							<input type="text" id="duration" class="form-control"
								name="duration" placeholder="Duration" required>
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
							<button id="btn-create-project" type="submit" class="btn btn-info">
								<i class="icon-hand-right"></i> Create
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>	

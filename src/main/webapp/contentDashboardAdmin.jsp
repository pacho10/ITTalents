		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<div class="container-fluid" id="dashboard">


				<h1 class="text-center text-success">${organizationName}</h1>
				<hr>
				<div class="table-responsive">
					<table class="table">
					<thead>
					<td>
					Project Id
					</td>
					<td>
					Project Name
					</td>
					<td>
					Project deadline
					</td>
					</thead>
					<c:forEach var="p" items="${projects}">
					<tr>
						<td>
						${p.id}
						</td>
						<td>
							<a href="/final_project/projectdetail?projectId=${p.id}">${p.name}</a>
						</td>
						<td>
						${p.deadline}
						</td>
					</tr>
				</c:forEach>
				</table>
				</div>
				
			</div>

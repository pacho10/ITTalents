
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<c:if
		test="${ sessionScope.user == null && not sessionScope.user.admin}">
		<c:redirect url="/LogIn"></c:redirect>
	</c:if>

	<div id="content">
		<h3 class="text-center text-danger">No current project</h3>
	</div>


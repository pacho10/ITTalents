<!-- Sidebar -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="sidebar-wrapper">

	<ul class="sidebar-nav">
		<li class="sidebar-brand"><a href="./dashboard"> Dashboard </a></li>
		<li><a href="/final_project/workertasksservlet">My open tasks</a></li>
		<li><a href="./workerTasksAllProjectTasks.jsp">All project
				tasks</a></li>
		<li><a href="./workerTasksCurrentSprint.jsp">Current Sprint</a></li>
		<%-- <c:if test="${ sessionScope.project not null }"> --%>
			<li><a href="./createtask?projectId=${project.id}">Create
					task</a></li>
		<%-- </c:if> --%>
	<%-- 	<c:if test="${ sessionScope.project == null }">
			<li><a href="./createtask?projectId=${project.id}">Create
					task</a></li>
		</c:if> --%>
		<li><a href="#">Statistics</a></li>
	</ul>
</div>

<!-- Sidebar -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="sidebar-wrapper">

	<ul class="sidebar-nav">
		<li class="sidebar-brand"><a href="./workertasks"> Dashboard </a></li>
		<li><a href="./allTasksProject?projectId=${project.id}&backLog=1">Project
				Backlog</a></li>
				<li><a href="./allTasksProject?projectId=${project.id}&backLog=0">Project
				tasks history</a></li>
		<li><a href="./allsprinttasks?projectId=${project.id}">Current Sprint</a></li>
			<li><a href="./createtask?projectId=${project.id}">Create
					task</a></li>
		<li><a id="stat" href="/final_project/BarChartStatistics.jsp">Statistics</a></li>
				<li class="sidebar-brand"><a href="./workertasks">
						Dashboard </a></li>
		<c:choose>
			<c:when test="${not empty sessionScope.project}">

				<li><a
					href="./allTasksProject?projectId=${project.id}&backLog=1">Project
						Backlog</a></li>
				<li><a
					href="./allTasksProject?projectId=${project.id}&backLog=0">Project
						tasks history</a></li>
				<li><a href="./allsprinttasks?projectId=${project.id}">Current
						Sprint</a></li>
				<li><a href="./createtask?projectId=${project.id}">Create
						task</a></li>
				<li><a href="#">Statistics</a></li>

			</c:when>
			<c:otherwise>

				<li>Project Backlog</li>
				<li>Project tasks history</li>
				<li>Current Sprint</li>
				<li>Create task</li>
				<li><a href="#">Statistics</a></li>


			</c:otherwise>
		</c:choose>

>>>>>>> 21927b105906f363fe3ae846af81edcbe37d2781
	</ul>
</div>

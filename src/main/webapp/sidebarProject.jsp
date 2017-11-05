<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Sidebar -->
<div id="sidebar-wrapper">
	<ul class="sidebar-nav">
		<li class="sidebar-brand"><a href="./projectdetail?projectId=${project.id}" >Project details</a></li>
		<li><a href="./allsprinttasks?projectId=${project.id}">Active sprint</a></li>
		<li><a href="./allTasksProject?projectId=${project.id}&backLog=1">Backlog<!-- all tasks that are not taken and not finished --></a></li>
		<li><a href="./allTasksProject?projectId=${project.id}&backLog=0">All tasks history</a></li>
		<li><a id="stat" href="/final_project/BarChartStatistics.jsp">Statistics</a></li>
	</ul>
</div>

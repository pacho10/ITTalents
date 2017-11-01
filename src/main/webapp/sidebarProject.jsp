<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Sidebar -->
<div id="sidebar-wrapper">
	<ul class="sidebar-nav">
<%-- 		<li class="sidebar-brand"><a href="./dashboard"> Dashboard </a></li>
		<hr> --%>
		<li class="sidebar-brand"><a href="./projectdetail?projectId=${project.id}" >Project details</a></li>
		<li><a href="#">Active sprint</a></li>
		<li><a href="./allTasksProject?projectId=${project.id}&backLog=1">Backlog<!-- all tasks that are not taken and not finished --></a></li>
		<li><a href="./allTasksProject?projectId=${project.id}&backLog=0">All tasks history</a></li>
		<li><a href="#">Statistics</a></li>
		<hr>
		<li><a href="./createsprint?projectId=${project.id}" class="noReLoad">Start sprint</a></li>
		<li><a href="./addWorkers?projectId=${project.id}" class="noReLoad">Add workers</a></li>
		<li><a href="./createtask?projectId=${project.id}" class="noReLoad">Add task</a></li>
		<li><a href="./createepic?projectId=${project.id}" class="noReLoad">Add epic</a></li>
	</ul>
</div>

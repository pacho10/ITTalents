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
		<li><a href="#">Statistics</a></li>
	</ul>
</div>

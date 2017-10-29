<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Sidebar -->
<div id="sidebar-wrapper">
	<ul class="sidebar-nav">
		<li class="sidebar-brand"><a href="./dashboard"> Dashboard </a></li>
		<hr>
		<li><a href="./projectdetail?projectId=${project.id}" >Project details</a></li>
		<li><a href="#">Active sprint</a></li>
		<li><a href="#">Backlog//all tasks that are not taken and not finished</a></li>
		<li><a href="#">All tasks history</a></li>
		<li><a href="#">Statistics</a></li>
		<hr>
		<li><a href="./createsprint" class="noReLoad">Start sprint</a></li>
		<li><a href="./AddWorkersServlet" class="noReLoad">Add workers</a></li>
		<li><a href="./createtask" class="noReLoad">Add task</a></li>
		<li><a href="./createepic" class="noReLoad">Add epic</a></li>
	</ul>
</div>

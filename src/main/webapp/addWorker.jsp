<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="customCSS/styles.css">
<!-- <script src="js/myScript.js"></script> -->
<script>
$('#search').keyup(function(){
	var searchField = $('#search').val();
	var myExp = new RegExp(searchField, 'i');
	if(searchField != ''){
		$.getJSON("./returnUnemployedWorkers").success(function(workers) {
			var output = '';
			$.each(workers, function(key, val){
				if((val.firstName.search(myExp) != -1) || (val.lastName.search(myExp) != -1) || (val.email.search(myExp) != -1)) {
					output +='<tr>';
					output +='<td>' +'<img  src="./ImgOutputServlet?userid=' + val.id + '" class="img-rounded" id="avatar2" >'+ '</td>';
					output +='<td><a href="./profileDetail?userId='+val.id+'">' + val.firstName+' '+val.lastName + '</a></td>';
					output +='<td>' + val.email + '</td>';
					output +='<td>' +'<button onclick="location.href = \'./addWorkerToProject?projectId=${projectId}&userId='+val.id+ '\';"  class="btn btn-info btn-sm" >Add</button>'+ '</td>';
					output +='</tr>';
				}
			});
			$('#update').html(output);
		});
	}else{
		$("#update").empty();
	}
});
</script>
<h4>Add Users to the project</h4>
<div class="form-group">
	<input id="search" type="text" class="form-control"
		placeholder="Search">
</div>
<hr>


<div class="table-responsive">
	<table class="table" >
		<thead>
			<td></td>
			<td>Name</td>
			<td>email</td>
			<td></td>
		</thead>
		<tbody id ="update">
		</tbody>
	</table>
</div>


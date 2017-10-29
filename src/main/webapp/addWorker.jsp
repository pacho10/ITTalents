<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="customCSS/styles.css">
<script>
$('#search').keyup(function(){
	var searchField = $('#search').val();
	var myExp = new RegExp(searchField, 'i');
	$.getJSON("./returnUnemployedWorkers").success(function(workers) {
		var output = '<thead><td></td><td>Name</td><td>email</td><td></td></thead>';
		$.each(workers, function(key, val){
			if((val.firstName.search(myExp) != -1) || (val.lastName.search(myExp) != -1) || (val.email.search(myExp) != -1)) {
				output +='<tr>';
				output +='<td>' +'<img  src="./ImgOutputServlet?userid=' + val.id + '" class="img-rounded" id="avatar2" >'+ '</td>';
				output +='<td>' + val.firstName+' '+val.lastName + '</td>';
				output +='<td>' + val.email + '</td>';
				output +='<td>' +'<button onclick="location.href = \'./dashboard\';"  class="btn btn-info btn-sm" >Add</button>'+ '</td>';
				output +='</tr>';
			}
		});
		$('#update').html(output);
	});
});
</script>
<h4>Add Users to the project</h4>
<div class="form-group">
	<input id="search" type="text" class="form-control"
		placeholder="Search">
</div>
<hr>


<div class="table-responsive">
	<table class="table" id="update">
	</table>
</div>


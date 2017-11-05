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
					output +='<td>' + val.firstName+' '+val.lastName + '</td>';
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

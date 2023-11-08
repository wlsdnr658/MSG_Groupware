$(function(){
	
	$(".reservation").on("click", function(){
		
		var con = $("#contextPath").val();
		var typeName = $(this).val();
		var listName = $(this).next().val();
		
		
		
		window.open(con+"/management/calendar?typeName="+typeName+"&listName="+listName, "list",
		"width=1000, height=900, history=no, left=400 , top=200 ,resizable=no, status=no, scrollbars=no");
		
	});
	
	
	$(".modify").on("click", function(){
		
		var con = $("#contextPath").val();
		var typeName = $(this).next().val();
	
		
		
		
		window.open(con+"/management/managementCarmodify?typeName="+typeName, "list",
		"width=1300, height=200, history=no,left=400 , top=200 , resizable=no, status=no, scrollbars=no");
		
	});

});








	
	
	
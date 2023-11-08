
var con
$(function() {

	con = $("#contextPath").val();


	
	
	
	
});

/*
$("form").on("submit",function(){
	
	var user = $("#assigned").val();
	// var start = $("#start").val();

	if (user == '') {

		return false;

	} else {
	
		
		// 맵 만들어주기
		var data = {}

		data["typeName"] = $("#typeName").val();
		data["carName"] = $("#carName").val();
		data["startDate"] = $("#startDate").val();
		data["endDate"] = $("#endDate").val();
		data["assigned"] = $("#assigned").val();
		
		// ajax로 요청 처리
		$.ajax({
			url : con + "/management/managementCarreservation",
			type : "post",
			data : data,
			dataType : "json",
			success : function(result) {
				if (result) {
			
					
					window.close();
					
				} else {
					
				}

			}
		});

	}
	return false; // 이벤트를 더이상 진행하지 않음
	
	
});
*/


function reservation() {
	var con = $("#contextPath").val();
	var user = $("#assigned").val();
	// var start = $("#start").val();

	if (user == '') {

		return false;

	} else {
	
		
		// 맵 만들어주기
		var data = {}

		data["typeName"] = $("#typeName").val();
		data["carName"] = $("#carName").val();
		data["startDate"] = $("#startDate").val();
		data["endDate"] = $("#endDate").val();
		data["assigned"] = $("#assigned").val();
		
		// ajax로 요청 처리
		$.ajax({
			url : con + "/management/managementCarreservation",
			type : "post",
			data : data,
			dataType : "json",
			success : function(result) {
				if (result) {
					var typeName = $("#typeName").val();
					var listName = $("#carName").val();
					window.close();
					window.opener.parent.location.href="calendar?typeName="+typeName+"&listName="+listName;
				} else {
					
				}

			}
		});
		
		
	}
	return false; // 이벤트를 더이상 진행하지 않음
}
function reload() {
	window.reload();

//	var con = $("#contextPath").val();
//	var typeName = $("#typeName").val();
//	var listName = $("#carName").val();
	//location.href(con+"/management/calendar?typeName="+typeName+"&listName="+listName);
	//$(location).attr('href', "/management/calendar?typeName="+typeName+"&listName="+listName);
}







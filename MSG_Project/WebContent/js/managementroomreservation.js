
var con
$(function() {


	con = $("#contextPath").val();

});

function roomreservation() {


	var user = $("#assigned").val();
	// var start = $("#start").val();

	if (user == '') {

		return false;

	} else {

		// 맵 만들어주기
		var data = {}

		data["typeName"] = $("#typeName").val();
		data["roomName"] = $("#roomName").val();
		data["startDate"] = $("#startDate").val();
		data["endDate"] = $("#endDate").val();
		data["assigned"] = $("#assigned").val();

		// ajax로 요청 처리
		$.ajax({
			url : con + "/management/managementRoomreservation",
			type : "post",
			data : data,
			dataType : "json",
			success : function(result) {
				  
				if (result) {
					var typeName = $("#typeName").val();
					var listName = $("#roomName").val();
					window.close();
					window.opener.parent.location.href="calendar2?typeName="+typeName+"&listName="+listName;
				} else {
					
				}

			}
		});

	}
	return false; // 이벤트를 더이상 진행하지 않음
}



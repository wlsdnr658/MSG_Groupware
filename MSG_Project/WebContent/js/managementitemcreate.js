$(function() {

	var fileName = getOriginFileName('${item.FULLNAME}');
	$("#fileLink").append(fileName);

	$(".delete-btn").on("click", function() {
		
		var context = $("#contextPath").val();
		
		var itemNo = $("#itemNo").val();
		var typeName = $("#typeName").val();
		
		var url = context + "/management/deleteitem?itemNo=" +itemNo+"&typeName="+typeName;
		
		alert(url);

		$(location).attr('href', url);
		
	});

});
	
function getOriginFileName(fileName){
	if(fileName == null){
		return;
	}
	//특정문자열의 첫번째 위치 찾기 
	var idx = fileName.indexOf("_")+1;
	//특정인덱스부터 뒤쪽문자열 반환
	return fileName.substr(idx);
}

function insert() {
	
	var con = $("#contextPath").val();
	
//	window.open(con+"/management/managementiteminsert", "",
//					"width=500, height=300, history=no, resizable=no, status=no, menubar=no ,scrollbars=no");
	
	window.open(con+"/management/managementiteminsert", "insert",
					"width=500, height=300, history=no, left=400 , top=200 , resizable=no, status=no, menubar=no ,scrollbars=no");

}




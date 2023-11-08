$(function(){
	//쪽지함 전체 체크 이벤트
	$("#allCheck").on("click",function() {
		if($("#allCheck").prop("checked")) {
			$("input[type=checkbox]").prop("checked",true);
		}else {
			$("input[type=checkbox]").prop("checked",false);
		}
	});
});





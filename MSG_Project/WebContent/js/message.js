$(function(){
	$(".replyModal").on("click", function(e){
		var box = $("#replyBox");
		//좌측 좌표
		var left = e.clientX+8;
		//상단 좌표
		var top = e.clientY-1;
		box.css({
			"top" : top
			,"left" : left
			,"position" : "absolute"
		});
		$("#reply-name").val($(this).text());
		$("#reply-id").val($(this).next().val());
		box.fadeTo("fast",0.9);
		box.show();
	});
});
//쪽지함 전체 체크 이벤트
function allCheck() {
	if($("#allCheck").prop("checked")) {
		$("input[type=checkbox]").prop("checked",true);
	}else {
		$("input[type=checkbox]").prop("checked",false);
	}
};
//쪽지함에서 이름 클릭 후 보내기 버튼 눌렀을 때 발생하는 이벤트.
function msgReply() {
	var replyID = $("#reply-id").val();
	var con = $("#contextPath").val();
	location.href=con+"/messenger/messageWriteForm?replyID="+replyID;
};


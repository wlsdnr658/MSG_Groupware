$(function(){
	getCount();
	//현재 페이지 사이드 카테고리 활성화1
	var pageType= $("#pageType").val();
	if(pageType =="messageReceive"){
		messageTab();
	}else if(pageType =="messageSend"){
		messageTab();
	}else if(pageType =="messageWrite"){
		messageTab();
	}else if(pageType =="chatList"){
		chatTab();
	}else if(pageType =="chatInvite"){
		chatTab();
	}
	//사이드 박스 클릭 이벤트
	$("#sideList_msg>a").click(function(){
		var side = $("#msg_sub");
		if(side.is(":visible")){
			side.slideUp();
		}else{
			side.slideDown();
		}
	});
	$("#sideList_chat>a").click(function(){
		var side = $("#chat_sub");
		if(side.is(":visible")){
			side.slideUp();
		}else{
			side.slideDown();
		}
	});
	
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
	//현재 페이지에 해당하는 사이드 카테고리 표시	
	if(pageType =="messageReceive"){
		$("#msg_sub li:eq(1) a").css("color","red");
	}else if(pageType =="messageSend"){
		$("#msg_sub li:eq(2) a").css("color","red");
	}else if(pageType =="messageWrite"){
		$("#msg_sub li:eq(0) a").css("color","red");
	}else if(pageType =="chatList"){
		$("#chat_sub li:eq(0) a").css("color","red");
	}else if(pageType =="chatInvite"){
		$("#chat_sub li:eq(1) a").css("color","red");
	}
	//채팅 모달 외 영역 클릭시 자동 닫기
	$("#mask2").mousedown(function(){
		$("#chatRoomAdd_modal").hide();
		$("#mask2").css({'width' : 0, 'height' : 0});
	});
});
//안읽은 쪽지 개수 확인
function getCount() {
	var empNo = $("#userID").val();
	var con = $("#contextPath").val();
	$.ajax({
		url: con+"/messenger/getNonReadMessageCount?empNo="+empNo,
		type: 'post',
		dataType: 'json',
		success: function(data){
			if(data!='0'){
				$("#msgReadCount").html(" ("+data+")");
			}
		}
	});
}
//현재 페이지 사이드 카테고리 활성화2
function messageTab() {
	$("#sideList_msg>a").next("ul").toggleClass("sideBox_sub");
};
function chatTab() {
	$("#sideList_chat>a").next("ul").toggleClass("sideBox_sub");
};
//모달창 닫기 클릭 이벤트
function chatModal_close() {
	$("#chatRoomAdd_modal").hide();
	$("#mask2").css({'width' : 0, 'height' : 0});
}
//사이드 채팅방 만들기 클릭 이벤트
function chatModal_open(){
	$("#chatRoomAdd_modal").show();
	var width = $(window).width();
	var height = $(window).height();
	$("#mask2").css({'width' : width, 'height' : height});
	$("#mask2").fadeIn(1000);
	$("#mask2").fadeTo("fast",0.6);
}
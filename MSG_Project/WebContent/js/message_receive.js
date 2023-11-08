$(function(){	
	var con = $("#contextPath").val();
	var receiverID = $("#userID").val();
	//페이지 넘버 클릭 이벤트
	//받은  쪽지함 쪽지 삭제 버튼 클릭 이벤트
	$("#msg-deleteBtn").on("click",function(){
		var result = confirm("정말 삭제하시겠습니까?");
		if(result) {
			$("input[type=checkbox]:checked").each(function(){

				var msgNo = $(this).val();
				$.ajax({
					url: con+"/messenger/receive_delete?MSGNO="+msgNo+"&RECEIVERID="+receiverID,
					type: "post",
					dataType: "json",
					success: function(){
						$("input[type=checkbox]").prop("checked",false);
					}
				});
			});
			ReMessageList();
		}
	});
	//쪽지 제목 클릭 시 내용 노출
	$(".messageTitle").on("click", function(){
		var a = $(this);
		var p = $(this).next("p");
		if(p.css("display")!="none"){
			p.slideUp();
		}else {
			$(".messageContent").slideUp();
			p.slideDown();
		}
		var msgNo = $(this).nextAll(".msgNo").val();
		var readState = $(this).nextAll(".readState").val();
		if(readState==1){
			$.ajax({
				url: con+"/messenger/readMessage?msgNo="+msgNo+"&receiverID="+receiverID,
				type: "post",
				dataType: "json",
				success: function(data){
					if(data==1){
						a.css({"font-weight" : "normal", "color" : "darkgray"});
					}
				}
			});
		}
	});
});
function ReMessageList() {
	var con = $("#contextPath").val();
	var page = $("#page").val();
	var type = $("#type").val();
	var keyword = $("#keyword").val();
	if(page==null){
		page = 1;
	}
	if(type==null){
		type.val("제목");
	}
	if(keyword==null){
		keyword.val("");
	}
	location.href = con+"/messenger/messageList?page="+page+"&type="+type+"&keyword="+keyword;
};
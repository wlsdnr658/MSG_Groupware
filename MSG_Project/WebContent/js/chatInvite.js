$(function(){
	
	//채팅방 열기
	$(".chatList-title").on("click",function(){
		window.open("openChatRoom?key=a","_blank","width=380px,height=450px,location=no,status=no,top=250px,left=700px");
	});
	
	$("#chat-join").on("click",function(){
	//채팅참여
		var con = $("#contextPath").val();
		var empNo = $("#empNo").val();
		var roomNo = $(this).val();
		alert("채팅방에 참여합니다.");
		location.href = con+"/messenger/chatRoomInEmp?roomNo="+roomNo+"&empNo="+empNo;
	});
	$("#chat-reject").on("click",function(){
	//채팅참여 거절
		var con = $("#contextPath").val();
		var roomNo = $(this).val();
		var empNo = $("#empNo").val();
		alert("채팅방 초대를 거절하셨습니다.");
		location.href = con+"/messenger/chatRoomInvite_reject?roomNo="+roomNo+"&empNo="+empNo;
	});
	
});


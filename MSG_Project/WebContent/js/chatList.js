$(function(){
	//참여 중인 채팅방 목록 요청
	getChatList();
	
});

//참여 중인 채팅방 목록 ajax
function getChatList(){
	var empNo = $("#userID").val();
	var con = $("#contextPath").val();
	var table = $("#chatRoom-list");
	
	$.ajax({
		url: con+"/messenger/getChatRoomList",
		data: {"empNo" : empNo},
		type: "post",
		dataType: "json",
		success: function(list) {
			if(list.length>0){
				table.html("");
				var tr1 = $("<tr>");
				var tr3 = $("<tr>");
				var tr1_th1 = $("<th class=\"msg_th\" style=\"width: 58%;\">채팅 방 제목</th>");
				var tr1_th2 = $("<th class=\"msg_th\"style=\"width: 20%; text-align: center;\">참여자 수</th>");
				var tr1_th3 = $("<th class=\"msg_th\"style=\"width: 20%; text-align: center;\"></th>");
				tr1.append(tr1_th1);
				tr1.append(tr1_th2);
				tr1.append(tr1_th3);
				table.append(tr1);
				$(list).each(function() {
					var tr2 = $("<tr>");
					var tr2_td1 = $("<td class=\"chatList-tr\">");
					var a = $("<a class=\"chatList-title\">"+this.roomTitle+"</a>");
					var tr2_td2 = $("<td style=\"text-align: center;\">"+this.empNum+"</td>");
					var tr2_td3 = $("<td style=\"text-align: right;\">");
					var btn = $("<button value=\""+this.roomNo+"\" class='chatListBtn'>나가기</button>");
					tr2_td3.append(btn);
					tr2_td1.append(a);
					tr2.append(tr2_td1);
					tr2.append(tr2_td2);
					tr2.append(tr2_td3);
					
					table.append(tr2);
					var roomNo = this.roomNo;
					//채팅방 제목 클릭
					a.on("click",function(){
						window.open("openChatRoom?roomNo="+roomNo,"_blank","width=450px,height=500px,location=no,status=no,top=250px,resizable=no,left=700px");
					});
					//채팅방 나가기 클릭
					btn.on("click",function(){
						if(confirm("채팅방에서 정말 나가시겠습니까?")==true){
							var con = $("#contextPath").val();
							var roomNo = $(this).val();
							var empNo = $("#empNo").val();
							stompClient.send("/ws/chat/exit/"+empNo+"/"+roomNo);
							location.href = con+"/messenger/chatRoom_ExitEmp?roomNo="+roomNo+"&empNo="+empNo;
						}
						return false;
					});
					var tr3 = $("<tr><td colspan=\"3\"><hr></tr>");
					table.append(tr3);
				});
			}
		}
	});
}


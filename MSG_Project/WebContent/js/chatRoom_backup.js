var sock;
var stompClient = null;
var empList = new Array();
var inviteList = new Array();
var lineCnt = 0;
function connect(){
	sock = new SockJS("http://192.168.0.33:8083/MSG_Project/chat");
	//sock = new SockJS("${contextPath}/chat");
	var roomNo = $("#roomNo").val();
	var empNo = $("#userID").val();
	stompClient = Stomp.over(sock);
	stompClient.connect({},function(){
		stompClient.subscribe("/topic/chat/"+roomNo,function(data){
			console.log("받은채팅 : " + data.body);
			var data = JSON.parse(data.body);		
			addMessage(data);
		});
		stompClient.subscribe("/topic/chat/notice/"+roomNo,function(data){
			console.log("받은공지사항 : " + data.body);
			var data = JSON.parse(data.body);	
			updateNotice(data);
		});
		stompClient.subscribe("/topic/chat/file/"+roomNo,function(data){
			console.log("받은파일이름 : " + data);
			var data = JSON.parse(data.body);
			uploadFile(data);
		});
		stompClient.subscribe("/topic/chat/exit/"+roomNo+"/"+empNo,function(data){
			console.log("채팅종료 : " + data);
			stompClient.disconnect;
		});

	});
};

$(window).resize(function(){
	//창 크기 변화시 발생
	var height = $(window).height();
	var chatDiv = $(".chatDiv").height();
	var sendField = $("#sendField").height();
	
	$("#chat-wrap").css('height', height-145);
	var chatWrap = $("#chat-wrap").height();
	$("#chatField").css('height', chatWrap);
	var chatField = $("#chatField").height();
	$(".chatDiv").css('height', chatField-40);
	
});
function uploadFile(data) {
	var contextPath = $("#contextPath").val();
	var chatDiv = $(".chatDiv");
	var p = "<p class='fileP' style=\"margin-bottom: 5px;\">"+data.empName+"님이 파일을 첨부하셨습니다.</p>";
	var fileDiv = "<div class='fileDiv' style='margin-left:120px;'>"
					+"<p class='fileP'>"+'파일명 : '+data.fileOriginName+"</p>"
					+"<p class='fileP'>"+'용량 : '+data.fileSize+"</p>"
					+"<button class='fileDownBtn' onclick=\"location.href='"+contextPath+"/messenger/chatDownload?fileName="+data.fileName+"'\">다운로드</button>"
				 	+"</div>"
				 	+"<br>";
	chatDiv.append(p);
	chatDiv.append(fileDiv);
	chatDiv.scrollTop(chatDiv[0].scrollHeight+10);
}


//채팅 그려주기
function addMessage(data){
	var chatDiv = $(".chatDiv");
	var date = new Date().toLocaleTimeString('en-US', { hour12: false, 
        		hour: "numeric", 
        		minute: "numeric"});
	var strBr = data.chatting;
	//받은 채팅 엔터 확인 후 처리
	strBr.replace(/(?:\r\n|\r|\n)/g, '<br/>');
	console.log("엔터 : "+strBr);
	if(data.empNo== $("#userID").val()){
		var str = "<span class='chatSpan' style=\"text-align: right;\">"
	  		  +"<span class='chatContent' style=\"background-color: lightyellow; max-width: 180px; margin-right: 6px;\">"
	  		  +"<pre style=\"margin:0px; text-align: left; font-family: sans-serif; white-space: pre-line;\">"
	  		  +strBr
	  		  +"</pre>"
	  		  +"</span>"
	  		  +"<span class='chatTime'>"
	  		  +date
	  		  +"</span>"
			  +"</span>";
	}else {
		var str = "<span class='chatSpan'>"
	          +"<a class='chatSendName'>"
    		  +data.empName
    		  +"</a>"
    		  +"<span class='chatContent' style=\"max-width: 180px;\">"
    		  +data.chatting
    		  +"</span>"
    		  +"<span class='chatTime'>"
    		  +date
    		  +"</span>"
			  +"</span>";
	}
	chatDiv.append(str);
	chatDiv.scrollTop(chatDiv[0].scrollHeight+10);
	//chatDiv.scrollIntoView(false);
}
//공지사항 업데이트
function updateNotice(data){
	$(".noticeContent").html(data.roomNotice);
	//$(".noticeContent").val(data.roomNotice);
}
//채팅 보내기
function sendMessage(chatMsg){
	var roomNo = $("#roomNo").val();
	var empNo = $("#userID").val();
	var empName = $("#empName").val();
// 	var data = {}
// 	data["empNo"] = empNo;
// 	data["roomNo"] = roomNo;
// 	data["content"] = chatMsg;
	console.log(chatMsg);
	stompClient.send("/ws/chat/"+empName+"/"+roomNo+"/"+empNo,{},chatMsg);
	saveChat();
};

//채팅 저장
function saveChat(){
	var content = $("#textBox").val();
	var roomNo = $("#roomNo").val();
	var empNo = $("#userID").val();
	//엔터 줄바꿈.
	content = content.replace(/(?:\r\n|\r|\n)/g, '<br>');
	var contextPath = $("#contextPath").val();
	$.ajax({
		url: contextPath+"/messenger/saveChat?content="+content+"&empNo="+empNo+"&roomNo="+roomNo,
		dataType: "json",
		type: "post",
		success: function() {
		}
		
	});
}
//공지사항 저장
function saveNotice(roomNotice){
	var roomNo = $("#roomNo").val();
	var contextPath = $("#contextPath").val();
	$.ajax({
		url: contextPath+"/messenger/saveNotice?roomNo="+roomNo+"&roomNotice="+roomNotice,
		dataType: "json",
		type: "post",
		success: function() {
		}
	});
}

// 파일 드롭 다운
function fileDropDown(){
    var body = $("body");
    //Drag기능 
    body.on('dragenter',function(e){
        e.stopPropagation();
        e.preventDefault();
    });
    body.on('dragleave',function(e){
        e.stopPropagation();
        e.preventDefault();
    });
    body.on('dragover',function(e){
        e.stopPropagation();
        e.preventDefault();
    });
    body.on('drop',function(e){
        e.preventDefault();
        
        var files = e.originalEvent.dataTransfer.files;
        if(files != null){
            if(files.length < 1){
                alert("폴더 업로드 불가");
                return;
            }
        	var roomNo = $("#roomNo").val();
        	var empNo = $("#userID").val();
        	var empName = $("#empName").val();
        	var formData = new FormData();
        	formData.append('file', files);
        	var contextPath = $("#contextPath").val();
        	$.ajax({
        		url: contextPath+"/messenger/saveFile?roomNo="+roomNo+"&empNo="+empNo+"&empName="+empName,
        		data: formData,
        		type: "post",
        		dataType: "text",
        			contentType: false,
        	        processData: false,
        		success: function(data){
        			if(data==null){
        				alert("업로드에 실패하였습니다.");
        			}else {
        				var fileName = file.name;
        				stompClient.send("/ws/chat/file/"+empName+"/"+roomNo,{},data);
        			}
        		},
        		error: function(){
        			alert("업로드에 실패하였습니다.");
        		}
        	});
        }else{
            alert("ERROR");
        }
    });
}

$(function(){
	//드래그앤드롭
	fileDropDown();
	//참여자 가져 옴
	getChatEmpList();
	//채팅 연결
	connect();
	var chatDiv = $(".chatDiv");
	chatDiv.scrollTop(chatDiv[0].scrollHeight+10);
	//채팅방 내 목록창 클릭 이벤트
	$("#btn-chatRoomMenu").on("click",function(e){
		//좌측 좌표
		var left = 340;
		//상단 좌표
		var top = 38;		
		var box = $("#chatRoomMenu");
		box.css({
			"top" : top
			,"left" : left
			,"position" : "absolute"
		});		
		if(box.css("display")=="none") {
			box.fadeTo("fast",1.0);
			box.show();
		}else {
			box.hide();
		}
	});
	//공지 수정 버튼 클릭 이벤트
	$("#chat-notice").on("click",function(){
		var box = $("#chatNotice");
		if(box.css("display")=="none") {
			box.fadeTo("fast",1.0);
			box.show();
			var width = $(window).width();
			var height = $(window).height();
			$("#mask").css({'width' : width, 'height' : height});
			$("#mask").fadeIn(1000);
			$("#mask").fadeTo("fast",0.6);
			$("#roomNotice").focus();
		}else {
			box.hide();
			
		}
	});
	// 엔터 공지수정
	$("#roomNotice").on("keydown", function(event){
		if(event.keyCode == 13){
			$("#chat-noticeUpdate").click();
		}
	});
	
	//공지사항 수정 창에서 수정버튼 클릭
	$("#chat-noticeUpdate").on("click",function(){
		var roomNo = $("#roomNo").val();
		var empName = $("#empName").val();
		var roomNotice = $("#roomNotice").val()+"- "+empName;
		stompClient.send("/ws/chat/notice/"+empName+"/"+roomNo,{},roomNotice);
		saveNotice(roomNotice);
		$("#roomNotice").val("");
		$("#chatNotice").hide();
		$("#chatRoomMenu").hide();
		$("#mask").css({'width' : 0, 'height' : 0});
	})
	//공지사항 수정창 닫기 버튼 클릭
	$("#chat-noticeCancle").on("click",function(){
		$("#chatNotice").hide();
		$("#mask").css({'width' : 0, 'height' : 0});
	});
	$("#file_input_btn").on("click",function(){
		$("input[name='file_1']").click();
	});
	
	$("#file_btn").on("change",function(){
		var roomNo = $("#roomNo").val();
 		var empNo = $("#userID").val();
 		var empName = $("#empName").val();
		var file = $(this)[0].files[0];
		var formData = new FormData();
		formData.append('file', $(this)[0].files[0]);
// 		var data = {}
// 			data["file"] = file;
// 			data["empNo"] = empNo;
// 			data["roomNo"] = roomNo;
		//alert(url);
		var contextPath = $("#contextPath").val();
		$.ajax({
			url: contextPath+"/messenger/saveFile?roomNo="+roomNo+"&empNo="+empNo+"&empName="+empName,
			data: formData,
			type: "post",
			dataType: "text",
 			contentType: false,
 	        processData: false,
			success: function(data){
				if(data==null){
					alert("업로드에 실패하였습니다.");
					stompClient.send("/ws/chat/file/"+empName+"/"+roomNo,{},data);
				}else {
					var fileName = file.name;
					stompClient.send("/ws/chat/file/"+empName+"/"+roomNo,{},data);
				}
			},
			error: function(){
				alert("업로드에 실패하였습니다.");
			}
		});
	});
	
	// 채팅전송
	$("#btn_send").on("click",function(){
		var chatMsg = $("#textBox").val();
		sendMessage(chatMsg);
		$("#textBox").val("");
	});
	// 엔터 채팅전송
	$("#textBox").on("keydown", function(event){
		if(event.keyCode == 13){
			if(!event.shiftKey){
				event.preventDefault();
				$("#btn_send").click();
			}else{
// 				alert($("#textBox").val());
// 				var str = $("#textBox").val();
// 				str.replace('\r\n', '<br>');
// 				//$("#textBox").val() = str;
// 				alert("변경 : " + str);
			}
		}
	});
	
	// 초대
	$("#chat-invite").on("click", function(){
		//조직도 오픈!
		var width = $(window).width();
		var height = $(window).height();	
		var contextPath = $("#contextPath").val();
		$("#mask").css({'width' : width, 'height' : height});
		$("#mask").fadeIn(1000);
		$("#mask").fadeTo("fast",0.6);
		$.ajax({
			url: contextPath+"/messenger/chatInviteList",
			dataType: "json",
			type: "post",
			success: function(data){
				lineCnt = 0;
				$("#inviteTable").html("");
				var table = $("#inviteTable");
				for(var i in data){
					var flag = 0;
					for(var j in empList){
						if(data[i].empNo == empList[j].empNo){
							flag = 1;
							break;
						}
					}
					if(flag == 1){
						continue;
					}
					var tr = $("<tr>");
					var td1 = $("<td>"+data[i].deptName+"</td>");
					var td2 = $("<td>"+data[i].empPos+"</td>");
					var td3 = $("<td>");
					var td4 = $("<td>"+data[i].empName+"</td>");
					var btn = $("<button class=\"invite_btn\" value="+data[i].empNo+">초대</button>");
					
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					td4.append(btn);
					tr.append(td4);
					table.append(tr);
					
					btn.on("click",function(){
						var no = $(this).val();
						inviteEmp(no);
					});
					//전체 사원 리스트에서 참여자 리스트를 제외한 값을 저장!
					inviteList[lineCnt] = data[i];
					lineCnt = lineCnt + 1;
				}
//				$(data).each(function() {
//					var str = "<tr>"
//								+"<td>"+$(this).deptName+"</td>"
//								+"<td>"+$(this).empPos+"</td>"
//								+"<td>"+$(this).empName+"</td>"
//								+"<td> <button class=\"invite_btn\" value="+$(this).empNo+">초대</button>"
//							+ "</tr>";
//					table.append(str);
//				});
			}
		});
		$("#chatInvite").show();
	});
	
	// 참여자 목록
	$("#chat-List").on("click", function(){
		$("#chatEmpList").show();
		var width = $(window).width();
		var height = $(window).height();
		$("#mask").css({'width' : width, 'height' : height});
		$("#mask").fadeIn(1000);
		$("#mask").fadeTo("fast",0.6);
	});
	
	$("#mask").mousedown(function(){	// 암영 된 부분을 클릭 할 시 모달로 띄운 창을 닫아준다.
		$("#chatEmpList").hide();
		$("#chatNotice").hide();
		$("#chatInvite").hide();
		$("#mask").css({'width' : 0, 'height' : 0});
	});
	$("#menu-exit").on("click",function(){
		$("#chatRoomMenu").hide();
	});
	
	$("#inviteOption").on("change",function(){
		var deptName = $(this).val();
		$("#inviteTable").html("");
		var table = $("#inviteTable");
		
		for(var i in inviteList){
			if(deptName=="전체"){
				var tr = $("<tr>");
				var td1 = $("<td>"+inviteList[i].deptName+"</td>");
				var td2 = $("<td>"+inviteList[i].empPos+"</td>");
				var td3 = $("<td>");
				var td4 = $("<td>"+inviteList[i].empName+"</td>");
				var btn = $("<button class=\"invite_btn\" value="+inviteList[i].empNo+">초대</button>");
				
				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				td4.append(btn);
				tr.append(td4);
				table.append(tr);
				
				btn.on("click",function(){
					var no = $(this).val();
					inviteEmp(no);
				});	
			}
			if(deptName==inviteList[i].deptName){
				var tr = $("<tr>");
				var td1 = $("<td>"+inviteList[i].deptName+"</td>");
				var td2 = $("<td>"+inviteList[i].empPos+"</td>");
				var td3 = $("<td>");
				var td4 = $("<td>"+inviteList[i].empName+"</td>");
				var btn = $("<button class=\"invite_btn\" value="+inviteList[i].empNo+">초대</button>");
				
				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				td4.append(btn);
				tr.append(td4);
				table.append(tr);
				
				btn.on("click",function(){
					var no = $(this).val();
					inviteEmp(no);
				});
			}
		}
	});
	$("#inviteEmp_search").on("keyup", function(){
		var deptName = $("#inviteOption").val();
		
		var keyword = $(this).val();
		$("#inviteTable").html("");
		var table = $("#inviteTable");
		
		for(var i in inviteList){
			var empName = inviteList[i].empName;
			
			if(empName.indexOf(keyword) != -1){
				if(deptName=="전체"){
					var tr = $("<tr>");
					var td1 = $("<td>"+inviteList[i].deptName+"</td>");
					var td2 = $("<td>"+inviteList[i].empPos+"</td>");
					var td3 = $("<td>");
					var td4 = $("<td>"+inviteList[i].empName+"</td>");
					var btn = $("<button class=\"invite_btn\" value="+inviteList[i].empNo+">초대</button>");
					
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					td4.append(btn);
					tr.append(td4);
					table.append(tr);
					
					btn.on("click",function(){
						var no = $(this).val();
						inviteEmp(no);
					});
				}
				if(deptName==inviteList[i].deptName){
					var tr = $("<tr>");
					var td1 = $("<td>"+inviteList[i].deptName+"</td>");
					var td2 = $("<td>"+inviteList[i].empPos+"</td>");
					var td3 = $("<td>");
					var td4 = $("<td>"+inviteList[i].empName+"</td>");
					var btn = $("<button class=\"invite_btn\" value="+inviteList[i].empNo+">초대</button>");
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					td4.append(btn);
					tr.append(td4);
					table.append(tr);
					
					btn.on("click",function(){
						var no = $(this).val();
						inviteEmp(no);
					});
				}
			}
		}

	

	});
	$("#invite_exit").on("click", function(){
		$("#chatInvite").hide();
		$("#mask").css({'width' : 0, 'height' : 0});
	});
});
function inviteEmp(e){
	var contextPath = $("#contextPath").val();
	var targetNo = $("#userID").val();
	var empName = $("#empName").val();
	var empNo = e;
	var roomNo = $("#roomNo").val();
	$.ajax({
		url: contextPath+"/messenger/inviteEmp?empNo="+empNo+"&empName="+empName+"&inviteEmpNo="+targetNo+"&roomNo="+roomNo,
		dataType: "json",
		type: "post",
		success: function(){
			alert("초대 성공");
		},
		error: function(){
			alert("이미 초대를 받거나 참여 중인 사용자 입니다.");
		}
	});
	
}

//회원 목록을 가져 옴.
function getChatEmpList() {
	var contextPath = $("#contextPath").val();
	var roomNo = $("#roomNo").val();
	var empNo = $("#userID").val();
	$.ajax({
		url: contextPath+"/messenger/getChatEmpList?roomNo="+roomNo,
		dataType: "json",
		type: "post",
		success: function(data){
			
			var chatEmpList = $("#chatEmpList");
			$("#chatEmpList").html("");
			for(var i in data){
				var str = "<span style=\"margin-left:12px;\">"
							+ data[i].deptName
							+ "<br>"
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
							+ data[i].empPos + "&nbsp;"
							+ data[i].empName
						+ "</span>"
						+ "<hr style=\"margin: 2px; border: 0.5px solid navajowhite;\">";
				chatEmpList.append(str);
			};
			empList = data;
		}
	});
}

var cnt = 0;
var index = -1;
var test = 0;
var flag = false;
$(function() {

	var myIndex = 0;
	carousel();
	$("#mask").mousedown(function(){	// 암영 된 부분을 클릭 할 시 모달로 띄운 창을 닫아준다.
		$("#modifyBox").hide("fast");
		$("#showEmpInfo").hide("fast");
		$("#mask").css({'width' : 0, 'height' : 0});
	});
	function carousel() {

		var i;
		var x = document.getElementsByClassName("mySlides");

		for (i = 0; i < x.length; i++) {
			x[i].style.display = "none";  

		}

		myIndex++;
		if (myIndex > x.length) {myIndex = 1}    
		x[myIndex-1].style.display = "block";  
		setTimeout(carousel, 2000); // Change image every 2 seconds

	}

	$("#ex_filename").change(function(){
		var file = $("#ex_filename")[0].files[0];
		var url = window.URL.createObjectURL(file);
		$("#profile_Img").attr("src",url);
	});

	messageCount();
	
	$("#empInput").keydown(function(e){
		var length = $("#autoList li").length;
		if(e.keyCode == 13){
			if(index!=-1){
				//empByName
				var path = $("#path").val();
				var info = $("#autoList li:eq("+(index)+")").text();
				var searchEmp = $("#searchEmp");
				
				$.ajax({
					url: path+"/emp/selectEmpByNameAndDeptName?info="+encodeURIComponent(info),
					type: 'post',
					dataType: 'json',
					success: function(data) {
						//검색한 사원 선택결과
						showEmpPage(data);
						$("#autoList li").remove();
						$("#autoList").css("display","none");
						index = -1;
					},
					error: function() {
						alert("등록되지 않은 사원입니다.");
					}
				});
			}else {
				autoCom($("#empInput").val());
			}
		} else if(e.keyCode == 40){
			if((index+1)==length){
				index = 0;
			}else {
				index = index + 1;
			}
			$("#autoList li:eq("+(index)+")").css("background-color","#fff7ab");
			$("#autoList li").not($("#autoList li:eq("+index+")")).css("background-color","transparent");
		} else if(e.keyCode == 38){
			if(index==0 || index==-1){
				index = length - 1;
			}else {
				index = index - 1;
			}			
			$("#autoList li:eq("+(index)+")").css("background-color","#fff7ab");
			$("#autoList li").not($("#autoList li:eq("+index+")")).css("background-color","transparent");
		}
	});
	
});
function showMyPage(){
	$("#modifyBox").show("slow");
	var width = $(window).width();
	var height = 2000//$(window).height();
	$("#mask").css({'width' : width, 'height' : height});
	$("#mask").fadeIn(1000);
	$("#mask").fadeTo("fast",0.6);
};
function showEmpPage(data){
	var path = $("#path").val();
	$("#searchEmpNo").val(data.empNo);
	$("#showImg img").remove();
	$("#showName span").remove();
	$("#showDept span").remove();
	$("#showMail span").remove();
	$("#showTel span").remove();
	var img = "<img src =\""+path+"/emp/getEmpImage?loginEmp="+data.empNo+"\" style=\"width: 90px; height: 90px; border: 1px solid silver; border-radius: 50%\";>";
	$("#showImg").append(img);
	var span = "<span>"+data.empName+"</span>";
	$("#showName").append(span);
	span = "<span>"+data.deptName+"</span>";
	$("#showDept").append(span);
	span = "<span>"+data.empMail+"</span>";
	$("#showMail").append(span);
	span = "<span>"+data.empTel+"</span>";
	$("#showTel").append(span);
	$("#showEmpInfo").show("slow");
	var width = $(window).width();
	var height = $(window).height()+100;
	$("#mask").css({'width' : width, 'height' : height});
	$("#mask").fadeIn(1000);
	$("#mask").fadeTo("fast",0.6);
}
function closeMyPage(){
	$("#modifyBox").hide("slow");
	$("#mask").css({'width' : 0, 'height' : 0});
};
//안읽은 쪽지 개수 확인
function messageCount(){
	var empNo = $("#empNo").val();
	var path = $("#path").val();
	$.ajax({
		url: path+"/messenger/getNonReadMessageCount?empNo="+empNo,
		type: 'post',
		dataType: 'json',
		success: function(data){
			if(data!='0'){
				var count = $("#main_messenger").next(".main_count");
				count.html(" ("+data+")");
			}
		}
	});
}
function autoCom(inputName){
	var input_Name = inputName;
	var path = $("#path").val();

	if(input_Name){
		$.ajax({
			url: path+"/emp/empByName?input_Name="+input_Name,
			type: 'post',
			dataType: 'json',
			success: function(data){
				$("#autoList li").remove();
				var ul = $("#autoList").css("border","1px solid gray").css("float","left").css("width","79%");
				for(var i in data){
					$("<li class=\"searchEmpLi\">").text(data[i]).appendTo(ul);
					cnt = cnt + 1;
				}
				$("#autoList").css("display","inline-block");
				index = -1;
			}
		});	
	}
};

function search(){
	
	var input_Name = $("#empInput").val();
	var path = $("#path").val();

	if(input_Name){
		$.ajax({
			url: path+"/emp/empByName?input_Name="+input_Name,
			type: 'post',
			dataType: 'json',
			success: function(data){
				$("#autoList li").remove();
				var ul = $("#autoList").css("border","1px solid gray").css("float","left").css("width","79%");
				for(var i in data){
					$("<li>").text(data[i]).appendTo(ul);
					cnt = cnt + 1;
				}
				index = -1;
			}
		});	
	}
	
	
};
function sendMessageBySearchEmp(){
	var replyID = $("#searchEmpNo").val();
	var path = $("#path").val();
	location.href=path+"/messenger/messageWriteForm?replyID="+replyID;
};




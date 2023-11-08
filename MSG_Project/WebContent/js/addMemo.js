$(function(){
	
	getAllMemo();  
	
	$("#memoLine").on("click",":button",function(){ 
		var a = $(this).parent();
		$("#memoModifyContent").attr("data-mNo",a.attr("data-mNo"));
		$("#memoModifyContent").val(a.text());
		$("#modalMemoModify").slideDown("fast");
	})
	
	$("#memoLine").on("click","a",function(){
		var a = $(this).parent();
		$("#memoViewContent").val(a.text());
		$("#modalMemoView").slideDown("fast");
	});
	
	$("#closeMemo").on("click",function(){
		$("#memoBox").hide();
	})
	
});

//닫기 버튼 클릭시 모달창 없애기
function closeMemoModify(){
	$("#modalMemoModify").slideUp("fast");
}

function closeMemoView(){
	$("#modalMemoView").slideUp("fast");
}
  
function addMemoModal(){ 
	$("#memoBox").slideDown("fast");
	$("#memoContent").val("");
};

function submitMemo(){
	
		var path = $("#path").val();
		var content = $("#memoContent").val();
		var empNo = $("#empNo").val();
		
		if(content != ''){
			$.ajax({
				url : path+"/page/addMemo",
				type : "post",
				data : {
					content : content,
					empNo : empNo
				},
				dataType : "json",
				success : function(result){
					
					if(result){  
					}
					
					$("#memoBox").slideUp("fast");
					$("#memoLine").html("");
					getAllMemo();
					
				},error : function(request,status,error){
					alert("둥록 실패 하였습니다.");
				}
			})
		}else{
			alert("내용을 입력하세요.")
		}
};


//완료버튼 누를시 메모내용 수정하기
function updateMemo1(){
	
	var path = $("#path").val();
	var empNo = $("#empNo").val();
	var memoNo = $("#memoModifyContent").attr("data-mNo");
	var memoContent = $("#memoModifyContent").val();
	
	if(memoContent != ''){
		$.ajax({
			url:path+"/page/updateMemo/"+memoNo,
			type:"post",
			dataType :"text",
			data : {
				"memoContent" : memoContent,
				"empNo" : empNo
			},
			success : function(result){
				
				if(result== "SUCCESS"){
				}else{
					alert("수정 실패하였습니다.");
				}
				
				$("#modalMemoModify").hide();
				$("#memoLine").html("");
				getAllMemo();
			},
			error : function(request,status,error){
				alert("요청 실패하였습니다.");
			}
		});
	}else{
		alert("내용을 입력하세요");
	}
	
	
}

//메모 삭제  
function deleteMemo(){
	
	var path = $("#path").val();
	var empNo = $("#empNo").val();
	var memoNo = $("#memoModifyContent").attr("data-mNo");
	var memoContent = $("#memoModifyContent").val();
	
	$.ajax({
		url:path+"/page/deleteMemo/"+memoNo,
		type:"post",
		dataType :"text",
		data : {
			"empNo" : empNo
		},
		success : function(result){
			$("#modalMemoModify").slideUp("fast");
			$("#memoLine").html("");
			getAllMemo();
		},error : function(request,status,error){
			alert("삭제 실패하였습니다.")
		}
	});
	
	
	
}

function getAllMemo(){
	
	var path = $("#path").val();
	var empNo = $("#empNo").val();
	
	$.ajax({
		url : path+"/page/getAllMemo/"+empNo,
		type : "get",
		dataType : "json",
		success : function(data){
			
			$(data).each(function(){
				var content = this.content;
				var memoNo	= this.memoNo;
				var ul = $("<ul id='memoUl' style='padding-top:5px;padding-left:15px;padding-right:15px;padding-bottom:5px;'>");
				var li = $("<li id='memoLi'"+">");
				var a = $("<a href='#' onclick='memoView()' id ='aTag' >");
				var modifyBtn = $("<input type='button' id='modifyBtn' value='수정' style='cursor:pointer;font-size:12px;border:1px solid gray;background-color:white;float:right;'>");
				var p = $("<p id='PTAG' style='overflow: hidden; text-overflow: ellipsis;white-space: nowrap; width: 200px;height: 20px;display:inline-block'>")
				a.text(content);
				p.append(a);
				li.append(p);
				modifyBtn.appendTo(li);
				li.attr("data-mNo",memoNo);
				li.appendTo(ul);
				$("<input id='inputMemoNo' type='hidden' value='"+memoNo+"'>").appendTo("#memoDiv");
				$("#memoLine").append(ul);
			})
			
		}
		
	})
}



var path;
$(function(){
	path = $("#path").val();
	
//	$.ajax({
//		url : path+"/emp/getAllList",
//		method : "post",
//		dataType : "json",
//		success : function(data){
//			createEmpList(data);
//		},
//		error : function(){
//			alert("empList fail");
//		}
//	});

	$("#mask").mousedown(function(){	// 암영 된 부분을 클릭 할 시 모달로 띄운 창을 닫아준다.
		removeShow();
		$("#empView").hide("slow");
		$("#mask").css({'width' : 0, 'height' : 0});
	});

});

//function createEmpList(data){
//	var table = $("#table_Content");
//	for(var i in data){
//		var tr = $("<tr onclick = 'empShow("+data[i].empNo+");'>");
//		$("<td>").text(data[i].empNo).appendTo(tr);
//		$("<td>").text(data[i].empName).appendTo(tr);
//		$("<td>").text(data[i].deptName).appendTo(tr);
//		$("<td>").text(data[i].empPos).appendTo(tr);
//		$("<td>").text(data[i].empTel).appendTo(tr);
//		var mTd = $("<td>");
//		$("<input type = 'button' value = '수정'>").appendTo(mTd);
//		mTd.appendTo(tr);
//		
//		var rTd = $("<td>");
//		$("<input type = 'button' value = '삭제'>").appendTo(rTd);
//		rTd.appendTo(tr);
//		
//		tr.appendTo(table);
//	}
//};
function modifyEmp(empNo){
	var name = $("#"+empNo+"name").val();
	var dept = $("#"+empNo+"dept").val();
	var pos = $("#"+empNo+"pos").val();
	var tel = $("#"+empNo+"tel").val();
	var tmp = $("table tr(2)");
	
	$.ajax({
		url : path+"/emp/modifyEmp?EMPNO="+empNo+"&NAME="+name+"&DEPT="+dept+"&POS="+pos+"&TEL="+tel,
		type : "post",
		dataType : "json",
		success : function(result){
			if(result){
				alert("사원정보 수정완료");
			}
			
		}, error : function(){
			alert("사원정보 수정실패");
		}
	});
}

function empShow(empNo){
	var path = $("#path").val();

	var url = path+"/emp/getEmpImage?loginEmp="+empNo;
	$("<img src = '"+url+"' style = 'width : 80px; height : 80px; border : 1px solid white; border-radius : 50%;'>").appendTo("#empPic");

	url = path+"/emp/selectEmpInfo?empNo="+empNo;
	$.ajax({
		url : url,
		method : "post",
		dataType : "json",
		success : function(data){
			$("#empNo").text(data.empNo);
			$("#empName").text(data.empName);
			$("#empDept").text(data.deptName);
			$("#empPos").text(data.empPos);
			$("#empTel").text(data.empTel);
			$("#empEmail").text(data.empMail);
			$("#empSd").text(new Date(data.empSd).toDateString());
			if(data.empOd != '-'){
				$("#empOd").text(new Date(data.empOd).toDateString());
			}else{
				$("#empOd").text(data.empOd);
			}
		},
		error : function(){
			alert("fail");
		}
	});
	
	
	
	$("#empView").show("slow");
	var width = $(window).width();
	var height = $(window).height();
	$("#mask").css({'width' : width, 'height' : height});
	$("#mask").fadeIn(1000);
	$("#mask").fadeTo("fast",0.6);
};
function removeShow(){
	$("#empPic img").remove();
};
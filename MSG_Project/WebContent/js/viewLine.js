var path;
$(function(){
	path = $("#path").val();
	var apNum = $("#APNUM").val();
	var reqUrl = path+"/approval/getLineInfo?APNUM=";
	reqUrl = reqUrl + apNum;
	$.ajax({
		url : reqUrl,
		method : "post",
		dataType : "json",
		success : function(data){
			getEmpDatas(data);
		},
		error : function(){
			alert("결재선 생성 오류");
		}
	});
});
function getEmpDatas(data){
	var cnt = 0;
	var empArray = new Array();
	for(var key in data){
		$.ajax({
			url : path+"/emp/selectEmpInfo?empNo="+data[key],	// 결재선에 들어온 사원 정보를 emp controller에 요청
			method : "post",
			dataType : "json",
			async : false,
			success : function(empData){
				empArray[cnt] = empData;
				cnt = cnt + 1;
			},
			error : function(){
				alert("사원정보 요청 실패");
			}
		});
	}
	createLine(empArray);
};
function createLine(emps){
	
	var table = $("#approvLine");
	var tr1 = $("<tr>");
	$("<th class = 'lineTitle' style = 'width : 30px'>").text("이름").appendTo(tr1);
	for(var i in emps){
		$("<th class = 'name'>").text(emps[i].empName).appendTo(tr1);
	}
	tr1.appendTo(table);
	
	var tr2 = $("<tr>");
	$("<th class = 'lineTitle'>").text("부서 / 직책").appendTo(tr2);
	for(var i in emps){
		$("<th class = 'deptPos'>").text(emps[i].deptName+" / "+emps[i].empPos).appendTo(tr2);
	}
	tr2.appendTo(table);
	
	var tr3 = $("<tr>");
	$("<th class = 'lineTitle'>").text("서명").appendTo(tr3);
	for(var i = 0; i < emps.length; i = i +1){
		$("<td id = '"+emps[i].empNo+"' class = 'sign'>").appendTo(tr3);
	}
	tr3.appendTo(table);
	
	var tr4 = $("<tr>");
	$("<th class = 'lineTitle'>").text("서명일").appendTo(tr4);
	for(var i = 0; i < emps.length; i = i +1){
		$("<td class = 'signDate'>").appendTo(tr4);
	}
	tr4.appendTo(table);
	
	stampOutput(emps);
};

function stampOutput(emps){
	
	var curApprov = $("#APNUM").val();
	$.ajax({
		url : path+"/approval/getCurEmpInfo?approvNo="+curApprov,
		method : "post",
		async: false,
		dataType : "text",
		success : function(emp){
			curEmp = emp;
		},
		error : function(){
			alert("현재 사원정보 get 실패");
		}
	});	// 현재 문서의 결재 순번을 알기 위해 ajax를 동기방식으로 데이터 받아옴
	
	for(var i = 0; i < emps.length; i++){
		if(curEmp == emps[i].empNo){
			if(emps[i+1] != null){
				$("#next").val(emps[i+1].empNo);
			}
			return;
		}
		var sign = $("#"+emps[i].empNo);
		$("<img src = '"+path+"/emp/getEmpStamp?empNo="+emps[i].empNo+"'>").css("width","30px").css("height","30px").appendTo(sign);
	}
};

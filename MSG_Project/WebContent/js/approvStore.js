var path;
$(function(){
	path = $("#path").val();
	var curEmp = $("#emp").val();

	removeAllApprov();	// 각 결재함들의 미리 보기를 초기화 해준다.


	$.ajax({	// status = 3인 문서들, 즉 반려된 문서목록을 가져온다.
		url : path+"/approval/getMiniApprovList?empNo="+curEmp+"&status=2",
		method : "post",
		dataType : "json",
		success : function(data){
			createCompleteApprov(data);
		},
		error : function(){
			alert("문서 로딩에 실패했습니다.");
		}
	});

	$.ajax({	// status = 1인 문서들, 즉  진행중인 문서목록을 가져온다
		url : path+"/approval/getMiniApprovList?empNo="+curEmp+"&status=1",
		method : "post",
		dataType : "json",
		success : function(data){
			createProgressApprov(data);
		},
		error : function(){
			alert("문서 로딩에 실패했습니다.");
		}
	});

	$.ajax({	// status = 2인 문서들, 즉 종결(완료)된 문서목록을 가져온다.
		url : path+"/approval/getMiniApprovList?empNo="+curEmp+"&status=3",
		method : "post",
		dataType : "json",
		success : function(data){
			createReturnApprov(data);
		},
		error : function(){
			alert("문서 로딩에 실패했습니다.");
		}
	});

});

function createReturnApprov(data){
	var ul = $("#waitUl");

	for(var i in data){
		var li = $("<li class = 'list_li'>");
		var span1 = $("<span class = 'title'>");
		$('<a href = "'+path+'/approval/viewApprov?approvNo='+data[i].approvNo+'&docNo='+data[i].docNo+'">').text(data[i].title).appendTo(span1);
		var span2 = $("<span class = 'date'>").text(new Date(data[i].approvDate).toDateString()); //new Date(data.empSd).toDateString()
		span1.appendTo(li);
		span2.appendTo(li);
		li.appendTo(ul);

	}
};
function createProgressApprov(data){
	var ul = $("#reqUl");

	for(var i in data){
		var li = $("<li class = 'list_li'>");
		var span1 = $("<span class = 'title'>");
		$('<a href = "'+path+'/approval/viewApprov?approvNo='+data[i].approvNo+'&docNo='+data[i].docNo+'">').text(data[i].title).appendTo(span1);
		var span2 = $("<span class = 'date'>").text(new Date(data[i].approvDate).toDateString()); //new Date(data.empSd).toDateString()
		span1.appendTo(li);
		span2.appendTo(li);
		li.appendTo(ul);


	}
};
function createCompleteApprov(data){
	var ul = $("#finUl");

	for(var i in data){
		var li = $("<li class = 'list_li'>");
		var span1 = $("<span class = 'title'>");
		$('<a href = "'+path+'/approval/viewApprov?approvNo='+data[i].approvNo+'&docNo='+data[i].docNo+'">').text(data[i].title).appendTo(span1);
		var span2 = $("<span class = 'date'>").text(new Date(data[i].approvDate).toDateString()); //new Date(data.empSd).toDateString()
		span1.appendTo(li);
		span2.appendTo(li);
		li.appendTo(ul);
	}

};
function removeAllApprov(){

};

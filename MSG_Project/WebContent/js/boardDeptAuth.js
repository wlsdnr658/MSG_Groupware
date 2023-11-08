

	function check1(){
		var deptNo = $("#inputDeptNo").val();
		var manager = $("#inputEmpPos").val();
		
		if(deptNo =="40" || manager == '부장'){location.href="boardDeptIt"}
		else{alert("접근권한 없음")};
	}
	function check2(){
		var deptNo = $("#inputDeptNo").val();
		var manager = $("#inputEmpPos").val();
		
		if(deptNo == "20" || manager == '부장'){location.href="boardDeptSales"}
		else{alert("접근권한 없음")};
	}
	function check3(){
		var deptNo = $("#inputDeptNo").val();
		var manager = $("#inputEmpPos").val();
		
		if(deptNo == "30" || manager == '부장'){location.href="boardDeptMin"
		}else{alert("접근권한 없음")};
	}
	function check4(){
		var deptNo = $("#inputDeptNo").val();
		var manager = $("#inputEmpPos").val();
		
		if(deptNo == "10" || manager == '부장'){location.href="boardDeptBP"}
		else{alert("접근권한 없음")};
	}
	
	function checkAuth(){
		var manager = $("#inputEmpPos").val();
		
		if(manager == '부장' || manager == '과장'){location.href="boardProjectWriteForm"}
		else{alert("접근권한 없음")};
	}
	
		
	function deleteAuthCheck(){
		var manager = $("#inputEmpPos").val();
		var boardNo = $("#boardNoinput").val();
		var taskNo = $("#inputTaskNo").val();
		
		if(manager == '부장' || manager == '과장'){
			location.href="boardProjectOngoingOfTaskDelete?taskNo="+taskNo+"&boardNo="+boardNo;
		}else{
			alert("삭제 권한이 없습니다.");
		}
			
	}
		
		
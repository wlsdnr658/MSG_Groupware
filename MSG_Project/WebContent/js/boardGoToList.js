function goToList(){
		
	var deptNo = $("#inputDepNo").val();
	var manager = $("#inputEmpPos").val();
	
	if(deptNo == '40' || manager == '부장'){location.href = "boardDeptIt"}
	else if(deptNo == '30' || manager == '부장'){location.href = "boardDeptMin"}
	else if(deptNo == '20' || manager == '부장'){location.href = "boardDeptSales"}
	else if(deptNo == '10' || manager == '부장'){location.href = "boardDeptBP"}
}
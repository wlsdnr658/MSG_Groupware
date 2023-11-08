function sendProcess(f){
		
	var deptNo = $("#inputDepNo").val();
	var manager = $("#inputEmpPos").val();

	if(deptNo=='40'){f.action="boardDeptItWrite"}
	
	
	if(deptNo=='30'){f.action="boardDeptMinWrite"}
	
	
	if(deptNo=='20'){f.action="boardDeptSalesWrite"}
	
	
	if(deptNo=='10'){f.action="boardDeptBpWrite"};

	
	f.submit();
}
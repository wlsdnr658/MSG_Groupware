var flag = 1;
function showDetail(){
	if(flag == 1){
		$("#mngDetail").css("display","block");
		flag = 2;
	} else if (flag == 2){
		$("#mngDetail").css("display","none");
		flag = 1;
	}

};
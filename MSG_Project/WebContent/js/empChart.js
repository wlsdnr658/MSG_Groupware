var flag = 1;
var path;
function showChart(dept, kind){
	if(flag == 1){
		path = $("#path").val();
		$.ajax({
			url : path+"/emp/selectByDept?type="+dept,
			method : "post",
			dataType : "json",
			success : function(data){
				createChart(data, kind);
				flag = 2;
			},
			error : function(){
				alert(dept);
			}
		});
	}
	else if (flag == 2){
		removeChart(kind);
		flag = 1;
	}
}
																																														function createChart(empList, kind){
																																															var ul = $("#"+kind);
																																															for(var i in empList){
																																																var li = $("<li>");
																																																$("<img src = "+path+"/img/person.png>").appendTo(li);
																																																$("<a>").text(empList[i].empPos+" "+empList[i].empName).appendTo(li);
																																																li.appendTo(ul);
																																															}
																																														}
																																														function removeChart(kind){
																																															$("#"+kind+" li").remove();
																																														}
																																														var menuFlag = 1;
																																													function showDetail(){
																																														if(menuFlag == 1){
																																															$("#mngDetail").css("display","block");
																																															menuFlag = 2;
																																														} else if (flag == 2){
																																															$("#mngDetail").css("display","none");
																																															menuFlag = 1;
																																														}
																																													
																																													};
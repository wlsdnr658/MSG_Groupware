var path;
$(function(){
	path = $("#path").val();
});
function permit(curEmp){
	var sign = $("#"+curEmp);
	$("<img src = '"+path+"/emp/getEmpStamp?empNo="+curEmp+"'>").css("width","30px").css("height","30px").appendTo(sign);
	alert("승인");
	$("#result").val(1);
	sendPermit();
	$("#resultForm").submit();
};
function deny(){
	$("#result").val(0);
	alert("반려");
	$("#deny_Reason").val($("#ETC").val());
	sendDeny();
	$("#resultForm").submit();
}
function sendPermit(){
	var targetid = $("#next").val();
	stompClient.send("/ws/approval/"+targetid,{},"permit");
}
function sendDeny(){
	var targetid = $("#first").val();
	stompClient.send("/ws/approval/"+targetid,{},"deny");
}
function printApproval(){
	$("#comBtns").css("visibility","hidden");
	$("#wrap").printThis({
		debug : false,
		printContainer : false,
		importCSS: true,
        importStyle: false,//thrown in for extra measure
        loadCSS: path+'/css/drafting.css',
		pageTitle : "",
		removeInline : false,
		printDelay : 333,
		header : null,
		base : false,
		formValues : true
	});	
}
function approvalToPDF(){
	html2canvas(document.getElementById("wrap"),{
		onrendered:function(canvas){
			var imgData = canvas.toDataURL('image/png');
			console.log('Report Image URL : '+imgData);
			var doc = new jsPDF('p','mm',[297,210]);
			doc.addImage(imgData,'PNG',10,10,190,180);
			doc.save('sample-file.pdf');
		}
	});
}
function reApprov(docNo){
	location.href = path+"/approval/reApprov?docNo="+docNo;
}
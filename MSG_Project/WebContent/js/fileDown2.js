$(function() {
	
	var path = $("#path").val();
	var taskNo = $("#taskNoinput").val();

	var fileModal = $("#fileModal4");
	fileModal.html("");

	$.ajax({
		
		url : path + "/board/boardProjectOngoingTaskViewFormFileDown/"+taskNo,
		type : "get",
		dataType : "json",
		success : function(data) {

		$(data).each(
					
			function() {
				var fileName = getOriginFileName(this.fileName);
				var fileNo = this.fileNo;
				var fileSize = this.fileSize;
				
				if(fileSize != 0) {
					
					fileModal.append('<a style="font-size:12px" href="../board/boardProjectOngoingDownloadOfTask?taskNo='+taskNo+'&fileNo='
							+ fileNo+ '">'+ fileName + '</a>'+'<b style="font-size:10px;font-weight:nomal">'+'('+fileSize+')'+'</b>'+'<br>');
					
				};    
				
			});
		

		},error : function(request, status, error) {

			alert("request:"+request
					+"\n"+"status:"+status
					+"\n"+"error:"+error+"\n");
		}

	});
	
});

function getOriginFileName(fileName) {
	if (fileName == null) {
		return;
	}
	var idx = fileName.indexOf("_") + 1;
	return fileName.substr(idx);
}





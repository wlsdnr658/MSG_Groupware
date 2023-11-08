
$(function() {
	
	var path = $("#path").val();
	var taskNo = $("#taskNoinput").val();

	var fileModal = $("#fileModal3");
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
				
				if(fileSize != 0) {
					fileModal.append('<a style="font-size:12px" href="../boardProjectOngoingDownloadOfTask?taskNo='
							+ taskNo+ '&fileNo='+ fileNo+ '">'+ fileName + '</a>'+'<b style="font-size:10px;font-weight:nomal">'+'('+fileSize+')'+'</b>'+'<br>');
				}
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
	// 특정문자열의 첫번째 위치 찾기 -->
	var idx = fileName.indexOf("_") + 1;
	// 특정인덱스부터 뒤쪽문자열 반환 -->
	return fileName.substr(idx);
}





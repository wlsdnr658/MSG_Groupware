
$(function(){
	
	var fileName = getOriginFileName('${myWriteinboxView.fileName}');
 	alert("fileName : " + fileName);
	$("#fileLink").append(fileName);
});

			function getOriginFileName(fileName){
				if(fileName == null){
					return;
				}

				//특정문자열의 첫번째 위치 찾기 

				var idx = fileName.indexOf("_")+1;

				//특정인덱스부터 뒤쪽문자열 반환
				return fileName.substr(idx);
			}		

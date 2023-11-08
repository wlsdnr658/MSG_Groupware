function getFileList(){
   
   var path = $("#context").val();
   var mailNo = $("#mailNo").val();
     alert(mailNo);
     var fileModal = $("#fileModal");
     fileModal.html("");
     
    
      
     $.ajax({
         url:path+"/mail/mailsendInboxviewformDown/"+mailNo,
         type:"get",
         dataType:"json",
         success:function(data){
            
            $(data).each(function(){
               var ul = $("<ul>");
               var li = $("<li>");
               
               var fileName = getOriginFileName(this.fileName);
               var fileNo = this.fileNo;
               
               fileModal.append('<a href="../mail/sendInboxdownload?mailNo='+mailNo+'&fileNo='+fileNo+'">'+fileName+'</a>'+"/");
               
            });
            
         },error : function(request,status,error){
             
           alert("request : " + request + "\n"+
                "status : " + status + "\n"+
                 "error : " + error + "\n");
        }
         
      });
};

function createModal2(){
   getFileList();
   
   $("#fileModal").show("fast");
};

function getOriginFileName(fileName){ 
   if(fileName == null){ 
      return; 
   } 
   //특정문자열의 첫번째 위치 찾기  -->
   var idx = fileName.indexOf("_")+1; 
   //특정인덱스부터 뒤쪽문자열 반환 -->
   return fileName.substr(idx); 
} 


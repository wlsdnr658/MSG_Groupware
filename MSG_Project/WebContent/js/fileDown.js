

function getFileList(){
	
	var path = $("#path").val();
	var boardNo = $("#boardNoinput").val();
	
	var boardName = $("#boardName").val();
	  
	  var fileModal = $("#fileModal");
	  fileModal.html("");
	  
	  if(boardName == "event"){
	      $.ajax({
	    	 
	    	 url:path+"/board/boardEventViewFormFileDown/"+boardNo,
	         type:"get",
	         dataType:"json",
	         success:function(data){
	        	 
	        	 $(data).each(function(){    
	        		 var fileSize = this.fileSize;
	        		 var fileName = getOriginFileName(this.fileName);
	        		 var fileNo = this.fileNo;
	        	    
	        		if(fileSize != 0) {
		        		 if(boardName == "event"){
			        	     fileModal.append('<a id="fileDownA" style="font-size:12px" href="../board/boardEventDownload?boardNo='
			        	     +boardNo+'&fileNo='+fileNo+'">'+fileName+'</a>' +'<b style="font-size:10px;font-weight:nomal">'+'('+fileSize+')'+'</b>'+'<br>')
			        	     
			        	 };
	        		};
		        	 
		        	 
	        	 });
	        	 var closeBtn = $("<input type='button' style='width:14px;height:14px;font-size:8px;font-weight:bold' id='fileDownCloseBtn' value='X' onclick='closeFileList()'>");
	        	 closeBtn.appendTo(fileModal);
	         },error : function(request,status,error){
	           alert("request : " + request + "\n"+
	                "status : " + status + "\n"+
	                 "error : " + error + "\n");
	        }
	         
	      });
      
	  }else if(boardName == "notice"){
		  $.ajax({
		    	 
		    	 url:path+"/board/boardNoticeViewFormFileDown/"+boardNo,
		         type:"get",
		         dataType:"json",
		         success:function(data){
		        	 $(data).each(function(){
		        		 var fileSize = this.fileSize;
		        		 var fileName = getOriginFileName(this.fileName);
		        		 var fileNo = this.fileNo;
		        		 
		        		 if(fileSize != 0) { 
			        	     if(boardName == "notice"){
			        	    	 fileModal.append('<a style="font-size:12px" href="../board/boardNoticeDownload?boardNo='
			        	    			 +boardNo+'&fileNo='+fileNo+'">'+fileName+'</a>'
			        	    			 +'<b style="font-size:10px;font-weight:nomal">'+'('+fileSize+')'+'</b>'+'<br>')
			        	    };
		        		 };
		        	    
		        	 });
		        	 var closeBtn = $("<input type='button' style='width:14px;height:14px;font-size:8px;font-weight:bold' id='fileDownCloseBtn' value='X' onclick='closeFileList()'>");
		        	 closeBtn.appendTo(fileModal);
		         },error : function(request,status,error){
		           alert("request : " + request + "\n"+
		                "status : " + status + "\n"+
		                 "error : " + error + "\n");
		        }
		         
		      });
	  }else if(boardName == "it"){
		  $.ajax({
		    	 
		    	 url:path+"/board/boardItViewFormFileDown/"+boardNo,
		         type:"get",
		         dataType:"json",
		         success:function(data){
		        	 $(data).each(function(){
		        		 var fileSize = this.fileSize;
		        		 var fileName = getOriginFileName(this.fileName);
		        		 var fileNo = this.fileNo;
		        		 
		        		 if(fileSize != 0) {
			        		 if(boardName == "it"){fileModal.append('<a style="font-size:12px;padding-top:3px" href="../board/boardDeptItDownload?boardNo='
			        				 +boardNo+'&fileNo='+fileNo+'">'+fileName+'</a>'+'<b style="font-size:10px;font-weight:nomal">'+'('+fileSize+')'+'</b>'+'<br>')
			        		 };
		        		 };
		        
		        	 });
		        	 var closeBtn = $("<input type='button' style='width:14px;height:14px;font-size:8px;font-weight:bold' id='fileDownCloseBtn' value='X' onclick='closeFileList()'>");
		        	 closeBtn.appendTo(fileModal);
		         },error : function(request,status,error){
		           alert("request : " + request + "\n"+
		                "status : " + status + "\n"+
		                 "error : " + error + "\n");
		        }
		         
		      });
	  }else if(boardName == "bp"){
		  $.ajax({
		    	 
		    	 url:path+"/board/boardBpViewFormFileDown/"+boardNo,
		         type:"get",
		         dataType:"json",
		         success:function(data){
		        	 $(data).each(function(){
		        		 var fileSize = this.fileSize;
		        		 var fileName = getOriginFileName(this.fileName);
		        		 var fileNo = this.fileNo;
		        	    
		        		 if(fileSize != 0) {
			        		 if(boardName == "bp"){fileModal.append('<a style="font-size:12px" href="../board/boardDeptBpDownload?boardNo='
			        	    		 +boardNo+'&fileNo='+fileNo+'">'+fileName+'</a>'+'<b style="font-size:10px;font-weight:nomal">'+'('+fileSize+')'+'</b>'+'<br>')
			        		 };
		        		 };
		        
		        	 });
		        	 var closeBtn = $("<input type='button' style='width:14px;height:14px;font-size:8px;font-weight:bold' id='fileDownCloseBtn' value='X' onclick='closeFileList()'>");
		        	 closeBtn.appendTo(fileModal);
		         },error : function(request,status,error){
		           alert("request : " + request + "\n"+
		                "status : " + status + "\n"+
		                 "error : " + error + "\n");
		        }
		         
		      });
	  }else if(boardName == "min"){
		  $.ajax({
		    	 
		    	 url:path+"/board/boardMinViewFormFileDown/"+boardNo,
		         type:"get",
		         dataType:"json",
		         success:function(data){
		        	 $(data).each(function(){
		        		 var fileSize = this.fileSize;
		        		 var fileName = getOriginFileName(this.fileName);
		        		 var fileNo = this.fileNo;
		        	     if(boardName == "min"){fileModal.append('<a style="font-size:12px" href="../board/boardDeptMinDownload?boardNo='
		        	    		 +boardNo+'&fileNo='+fileNo+'">'+fileName+'</a>'+'<b style="font-size:10px;font-weight:nomal">'+'('+fileSize+')'+'</b>'+'<br>')
		        		 };
		        	 });
		        	 var closeBtn = $("<input type='button' style='width:14px;height:14px;font-size:8px;font-weight:bold' id='fileDownCloseBtn' value='X' onclick='closeFileList()'>");
		        	 closeBtn.appendTo(fileModal);
		         },error : function(request,status,error){
		           alert("request : " + request + "\n"+
		                "status : " + status + "\n"+
		                 "error : " + error + "\n");
		        }
		         
		      });
	  }else if(boardName == "sales"){
		  $.ajax({
		    	 
		    	 url:path+"/board/boardSalesViewFormFileDown/"+boardNo,
		         type:"get",
		         dataType:"json",
		         success:function(data){
		        	 $(data).each(function(){
		        		 var fileSize = this.fileSize;
		        		 var fileName = getOriginFileName(this.fileName);
		        		 var fileNo = this.fileNo;
		        		 
		        		 if(fileSize != 0) {
			        		 if(boardName == "sales"){fileModal.append('<a style="font-size:12px" href="../board/boardDeptSalesDownload?boardNo='
			        	    		 +boardNo+'&fileNo='+fileNo+'">'+fileName+'</a>'+'<b style="font-size:10px;font-weight:nomal">'+'('+fileSize+')'+'</b>'+'<br>')
			        		 };
		        		 };
		        		 
		        	 });
		        	 var closeBtn = $("<input type='button' style='width:14px;height:14px;font-size:8px;font-weight:bold' id='fileDownCloseBtn' value='X' onclick='closeFileList()'>");
		        	 closeBtn.appendTo(fileModal);
		         },error : function(request,status,error){
		           alert("request : " + request + "\n"+
		                "status : " + status + "\n"+
		                 "error : " + error + "\n");
		        }
		         
		      });
		  
	  }else if(boardName == "anonymity"){  
		  $.ajax({
		    	 url:path+"/board/boardAnonymityViewFormFileDown/"+boardNo,
		         type:"get",
		         dataType:"json",
		         success:function(data){
		        	 $(data).each(function(){
		        		 var fileSize = this.fileSize;
		        		 var fileName = getOriginFileName(this.fileName);
		        		 var fileNo = this.fileNo;
		        	   
		        		 if(fileSize != 0) {
			        		 if(boardName == "anonymity"){fileModal.append('<a style="font-size:12px" href="../board/boardAnonymityDownload?boardNo='
			        	    		 +boardNo+'&fileNo='+fileNo+'">'+fileName+'</a>'+'<b style="font-size:10px;font-weight:nomal">'+'('+fileSize+')'+'</b>'+'<br>')
			        		 };
		        		 };
		        	 
		        	 });
		        	 var closeBtn = $("<input type='button' style='width:14px;height:14px;font-size:8px;font-weight:bold' id='fileDownCloseBtn' value='X' onclick='closeFileList()'>");
		        	 closeBtn.appendTo(fileModal);
		         },error : function(request,status,error){
		           alert("request : " + request + "\n"+
		                "status : " + status + "\n"+
		                 "error : " + error + "\n");
		        }
		         
		      });
		  
	  }else if(boardName == "projectOngoing"){
		  $.ajax({
		    	 
		    	 url:path+"/board/boardProjectOngoingViewFormFileDown/"+boardNo,
		         type:"get",
		         dataType:"json",
		         success:function(data){
		        	 $(data).each(function(){
		        		 var fileSize = this.fileSize;
		        		 var fileName = getOriginFileName(this.fileName);
		        		 var fileNo = this.fileNo;
		        	    
		        		 if(fileSize != 0) { 
			        		 if(boardName == "projectOngoing"){fileModal.append('<a style="font-size:12px" href="../board/boardProjectOngoingDownload?boardNo='
			        	    		 +boardNo+'&fileNo='+fileNo+'">'+fileName+'</a>'+'<b style="font-size:10px;font-weight:nomal">'+'('+fileSize+')'+'</b>'+'<br>')
			        		 };
		        		 };
		        	 
		        	 });
		        	 
		        	 var closeBtn = $("<input type='button' style='width:14px;height:14px;font-size:8px;font-weight:bold' id='fileDownCloseBtn' value='X' onclick='closeFileList()'>");
		        	 closeBtn.appendTo(fileModal);
		        	 
		         },error : function(request,status,error){
		           alert("request : " + request + "\n"+
		                "status : " + status + "\n"+
		                 "error : " + error + "\n");
		        }
		         
		      });
	  }
};

function createModal(){
	getFileList();
	
	$("#fileModal").css("display","inline-block");
	$("#fileModal").show(10);
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

function closeFileList(){
	$("#fileModal").hide(10);
}



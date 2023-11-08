$(function(){
	 $("#mail_return").on("click",function(){
	  alert("메일 회수 이벤트1 !!!!!!");
	  var context = $("#context").val();
	  
	  //alert($(".note-editable panel-body p").val());
	//  alert(($(".note-editable panel-body").children('p')).val());
	//   alert($("#mail_title").val());
	//   alert($("#mail_content").val());
	//   alert($("#receiverId").val());
	   var data={}
	   data["mailNo"] = $("#mailNo").val();
	   alert("메일 회수 이벤트2 !!!!!!");
	  
	   $.ajax({
	    url : context+"/mail/mail_return_mail",
	    type : "post",
	    data : data,
	    dataType : "json",
	    success : function(data){
			alert(data);
	    	if(data){
	    		getlocation();
	    	}
	    }
	   });
	 return false;
	 });
	});
	function getlocation(){

		 var context = $("#context").val();
		 location.href=context+"/mail/mail_main_form";
	}
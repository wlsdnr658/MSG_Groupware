$(function(){
	 $("#mail_delete_send").on("click",function(){
		  alert("메일 삭제1 !!!!!!");
		  var context = $("#context").val();
		  
		  //alert($(".note-editable panel-body p").val());
		//  alert(($(".note-editable panel-body").children('p')).val());
		//   alert($("#mail_title").val());
		//   alert($("#mail_content").val());
		//   alert($("#receiverId").val());

		   $("input[type=checkbox]:checked").each(function(){
			   
// 			   if($(this).val()=="on"){
// 				   continue;
// 			   }
			   $.ajax({
				    url : context+"/mail/mail_delete_MyMailInbox?mailNo="+$(this).val(),
				    type : "post",
				    dataType : "json",
				    success : function(data){
						alert(data);
				    	if(data){
				    		getlocation();
				    	}
				    }
		   		});
		   });
		 return false;
		 });
		});
		function getlocation(){

			 var context = $("#context").val();
			 location.href=context+"/mail/mail_main_form";
		}

$(function(){
	 $("#mail_delete_send").on("click",function(){
		  alert("메일 비우기 !!!!!!");
		  var context = $("#context").val();		
			
		   $("input[type=checkbox]:checked").each(function(){
			   

				alert($("#context").val()+"/mail/mail_delete_deleteInbox?mailNo="+$(this).val());
			   $.ajax({
				    url : $("#context").val()+"/mail/mail_delete_deleteInbox?mailNo="+$(this).val(),
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
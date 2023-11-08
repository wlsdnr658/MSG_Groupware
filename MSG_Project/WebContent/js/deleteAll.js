function getlocation(){

	 var context = $("#context").val();
	 location.href=context+"/mail/mail_main_form";
};

function button_event(){
	if(confirm("정말 비우시겠습니까??")){
		//쪽지함 전체 체크 이벤트
		
			alert("메일 체크비우기!!!!!");
			
			//var checked=$(this).find("input[type=checkbox]").attr("checked");
			
			$("input[type=checkbox]").prop("checked",true);
			
			alert($('input:checkbox[name="checkbox"]:checked').length);

		
//			var checked=$(this).find("input[type=checkbox]").attr("checked");
//			if(checked==undfined){
//				$(this).find("input[type=checkbox]").attr("checked",true);	
//				}else{
//				$(this).find("input[type=checkbox]").attr("checked",false);	
//				}		
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
			
	
	}else{
		alert("다시해");
	}
};		
$(function(){
	 $("#draftsInbox_click").on("click",function(){
	  alert("메일 재  임시저장 이벤트1 !!!!!!");
	  var context = $("#context").val();
	  var form = $('#excelForm')[0];
	  
	  var formData = new FormData(form);
	  alert(formData);
	   $.ajax({
	    url : context+"/mail/mail_Drafts_relay_reply",
	    type : "post",
	    enctype:'mutlipart/form[form-data]',
	    data : formData,
	    dataType : "json",
	    processData: false,
        contentType: false,
        cache: false,
	    success: function (data) {
	    	alert("매일재저장성공");
            console.log("SUCCESS : ", data);
            if(data){
            getlocation();
            }
        },
        error: function (e) {
        	alert("실패");
            console.log("ERROR : ", e);
        }
	   });
	 return false;
	 });
	});
	function getlocation(){

		 var context = $("#context").val();
		 location.href=context+"/mail/mail_main_form";
	}
$(function(){
	 $("#draftsInbox_click").on("click",function(){
	  alert("메일 내게 저장 이벤트1 !!!!!!");
	  var context = $("#context").val();
	  var form = $('#excelForm')[0];
	  
	  var formData = new FormData(form);
	  alert(formData);
	  //alert($(".note-editable panel-body p").val());
	//  alert(($(".note-editable panel-body").children('p')).val());
	//   alert($("#mail_title").val());
	//   alert($("#mail_content").val());
	//   alert($("#receiverId").val());
//	   var data={}
//	   data["mailNo"] = $("#mailNo").val();
//	   alert("메일 저장 이벤트2 !!!!!!");
	  
	   $.ajax({
	    url : context+"/mail/mail_MyDrafts",
	    type : "post",
	    enctype:'mutlipart/form[form-data]',
	    data : formData,
	    dataType : "json",
	    processData: false,
        contentType: false,
        cache: false,
	    success: function (data) {
	    	alert("메일 내게 저장 성공");
	    	alert(data);
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
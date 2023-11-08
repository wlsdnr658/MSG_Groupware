	
	$(function(){
		
		var boardNo = $("#boardNoinput").val();
		var path = $("#path").val();
		var boardName1 = $("#boardName1").val();
		 
		
		getCommentList();
		$("#commentForm").on("submit",function(){
			
			var data = {};
			data["writer"] = $("#commentWriter").val();
			data["content"] = $("#commentContent").val();
			data["boardNo"] = boardNo;
			
			$.ajax({
				url: path+"/comments/"+boardName1,
				type: "post",     
				data: data,
				dataType: "json",
				success : function(result){
					if(result){
						$("#commentContent").val("");
					}else{
						alert("등록 실패하였습니다.");
					}
					getCommentList();
				}
			});
			return false; 
		});
		
		$("#comments").on("click",":button",function(){
			var comment = $(this).parent();
			$("#modal-modify").attr("data-cNo",comment.attr("data-cNo"));
			$("#modal-commentContent").val(comment.text());
			$("#modal-modify").show();
		});
		
		$("#btn-close").on("click", function() {
			$("#modal-modify").hide();
		});
		
		$("#btn-commentDelete").on("click",function(){
			
			var cNo = $("#commentCno").attr("data-cNo");
			
			$.ajax({
				url : path+"/comments/delete"+boardName1+"/"+cNo,
				type :"delete",
				dataType : "text",
				success : function(data){
					if(data){
						$("#modal-modify").hide("slow");
						getCommentList();
					}else{
						alert("삭제실패!!")
						$("#modal-modify").hide("slow");
					}
				},error : function(request,status,error){
					alert("request :" + request + "\n"+
							"status :" + status + "\n"+
							"error :" + error);
				}
			});
			
				
		});  
		
		$("#btn-commentModify").on("click",function(){
			var cNo = $("#commentCno").attr("data-cNo");
			var commentContent = $("#modal-commentContent").val();
			
			$.ajax({
				url:path+"/comments/update"+boardName1+"/"+cNo,
				type:"post",
				dataType :"text",
				data : {"commentContent" : commentContent},
				success : function(result){
					
					if(result== "SUCCESS"){
						$("#modal-modify").hide("slow");
						getCommentList();
					}else{
						alert("수정 실패하였습니다.");
						$("#modal-modify").hide("slow");
					}
				},
				error : function(request,status,error){
					alert("요청 실패하였습니다.");
					$("#modal-modify").hide("slow");
					alert("request :" + request + "\n"+
							"status :" + status + "\n"+
							"error :" + error);
				}
				
			});
			
		});
		
	});
	
	function getCommentList(){
		
		var boardName1 = $("#boardName1").val();
		
		var comments = $("#comments");
		comments.html("");
		
		var path = $("#path").val();
		var boardNo = $("#boardNoinput").val();
		
		$.ajax({
			url:path+"/comments/all"+boardName1+"/"+boardNo,
			type:"get",
			dataType:"json",
			success : function(data){
				$(data).each(function(){
					var li = $("<li id='commentLi'>");
					var btn = $("<input type = 'button' value ='수정' id= 'modifyBtn' style='float:right'>");
					var cNo = this.cmtNo;
					var bNo = this.boardNo;
					var cContent = this.content;
					var cWriter = this.empName;
					
					if(boardName1 == "Anonymity"){
						var cWriter = this.writer;
					}
					
					var date = new Date(this.writeDate);
					var y = date.getFullYear();
					var m = date.getMonth()+1;
					var d = date.getDate();
					var div = $("<div>");
					var ul = $("<ul>");
					var li1 = $("<li id='commentLi2' style='padding:8px'>");
					var li2 = $("<li id='commentLi3' style='padding:8px;background-color:white;margin-left:5px;margin-right:5px; border:1px solid silver'>");
					var li3 = $("<li id='commentLi4' style='color:gray;padding:8px'>");
					var div2 = $("<div id='commentCno' style='display:inline'>");
					
					li1.text("작성자 : "+cWriter);
					li1.appendTo(ul);
					div2.text(cContent);
					div2.appendTo(li2);
					li2.appendTo(ul);
					li3.text("작성일 : "+"'"+y+"-"+m+"-"+d+"'"+" ")
					li3.appendTo(ul);
					ul.appendTo(div);
					div.appendTo(li);
					
					div2.attr("data-cNo",cNo);
					btn.appendTo(li2);
					li.appendTo(comments);
				});
			}	
		});
	}
	
	
	
	
	
	
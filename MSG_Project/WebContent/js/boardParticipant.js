	
	function createLine(){   // 결재선 선택 화면을 모달로 띄워준다. 뒷 배경은 암영
		
		var boardNo = $("#boardNoinput").val();
		
		getParticipantList();			
		
		flag = 1;
		$("#participant_div").show(10);
		var width = $(window).width();
		var height = $(window).height();
		$("#mask").css({'width' : width, 'height' : height});  
		$("#mask").fadeIn(1000);
		$("#mask").fadeTo(10);
	}
	
	function closeToLine(){
		$("#participant_div").hide(10);
	}
	
	function getParticipantList(){

		var boardNo = $("#boardNoinput").val();
		var path = $("#path").val();
		
	   var ptcp = $("#ptcp");
	      ptcp.html("");
	      ptcp.append('<tr><th style="padding:2px;border-bottom:1px solid black">'
	            +"부서"+'</th><th style="padding:2px;border-bottom:1px solid black">'
	            +"직급"+'</th><th style="padding:2px;border-bottom:1px solid black">'  
	            +"이름"+'</th></tr>')
		
		$.ajax({
			url:path+"/board/allParticipant/"+boardNo,  
			type:"get",
			dataType:"json",
			success : function(data){
				
				$(data).each(function(){
					   var li = $("<li id='participantLi'>");
		               var bNo = this.boardNo;
		               var cWriter = this.participant;
		               var pPos = this.empPos;
		               var deptNo = this.deptNo;
		               var dept = "";
		               
		               if(deptNo == 40){
		                  dept = "경영/기획부";
		               }else if(deptNo == 30){
		                  dept = "재무부";
		               }else if(deptNo == 20){
		                  dept = "영업부";
		               }else if(deptNo == 10){
		                  dept = "IT부";
		               }  
		               
		               ptcp.append('<tr><td style="font-size: 12px;text-align:center">'
		                     +dept+'</td><td style="font-size: 12px;text-align:center">'
		                     +pPos+'</td><td style="font-size: 12px;text-align:center">'
		                     +cWriter+'</td></tr>')
		               
//		               li.text(pPos +"-"+ cWriter);
//		               li.appendTo(ptcp);   
				});
				
			}	
		});
		   
	}
	
	
	
	
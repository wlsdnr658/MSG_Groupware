var lineArray2 = new Array();   // 결재선에 들어갈 emp객체 정보를 저장할 배열
var finalLine2 = new Array();
var nowLi2, nowLi2r;
var selectEmp2;
var delEmp2;
var lineCnt2 = 0;   // emp객체 배열에 들어가는 사원 수를 count2할 변수
var count2=0;
mCnt2 = bCnt2 = fCnt2 = iCnt2 = 0;
$(function(){
   
//   var reply = $("#replyID").val();
//   if(reply!="" && count2==0){
//      count2++;
//      var con = $("#contextPath").val();
//      $.ajax({
//         url: con+"/messenger/getReplyEmp?empNo="+reply,
//         type: "post",
//         dataType: "json",
//         success: function(data){
//            selectEmp2 = data;
//            var ul = $("#emps");
//            var li = $("<li>");
//            //$("<img src = "+con+"/img/person.png>").appendTo(li);
//            $("<a onclick = 'deleteEmp("+lineCnt2+",this)'>").text(selectEmp2.empPos+" "+selectEmp2.empName).appendTo(li);
//            li.appendTo(ul);
//            lineArray2[lineCnt2] = selectEmp2;
//            lineCnt2 = lineCnt2 + 1;
//            
//            var div = $("#messageWriteTarget_Div");
//            var target = $("<span name='receiverID' class='msg_write_target2' readonly='readonly'>");
//            target.text(data.empPos+" "+data.empName);
//            div.append(target);
//            finalLine2 = lineArray2;
//         }
//         
//      })
//      
//   }
   
//   var textLimit2 = 250;
//   $(".msg_write_target2").focus();
//   $("#mask2").mousedown(function(){   // 암영 된 부분을 클릭 할 시 모달로 띄운 창을 닫아준다.
//      flag2 = 1;
//      $("#chart_div2").hide("fast");
//      $("#mask2").css({'width2' : 0, 'height2' : 0});
//      removeChart2();
//   });
//   
//   $("#contentArea2").keyup(function(){
//      //textarea 길이 체크
//      var textLength2 = $(this).val().length;
//      //길이 업데이트
//      $("#textcount2").text(textLength2);
//      console.log(textLength2);
//      if(textLength2 > textLimit2){
//         $(this).val($(this).val().substr(0, textLimit2));
//         textLength2 = $(this).val().length;
//         $("#textcount2").text(textLength2);
//      }
//   });
	$("#mask2").mousedown(function(){   // 암영 된 부분을 클릭 할 시 모달로 띄운 창을 닫아준다.
	      flag = 1;
	      $("#chart_div2").hide("fast");
	      $("#mask2").css({'width' : 0, 'height' : 0});
	      removeChart2();
	   });
});

var flag2 = 1;
//결재선에 선택되는 emp를 배열에 담아준다.
function addLine2(emp2, li2){
	 nowLi2 = li2;
	   $(nowLi2).css("background-color","silver");
	   switch(emp2.empDept2){
	   case '경영/기획부':
		      mCnt2 = mCnt2 + 1;
		      if(mCnt2 > 3){
		         alert('부서 당 결재선 인원은 3명까지 가능합니다');
		         return;
		      }
		      break;
		   case '영업부':
		      bCnt2 = bCnt2 + 1;
		      if(bCnt2 > 3){
		         alert('부서 당 결재선 인원은 3명까지 가능합니다');
		         return;
		      }
		      break;
		   case '재무부':
		      fCnt2 = fCnt2 + 1;
		      if(fCnt2 > 3){
		         alert('부서 당 결재선 인원은 3명까지 가능합니다');
		         return;
		      }
		      break;
		   case 'IT부':
		      iCnt2 = iCnt2 + 1;
		      if(iCnt2 > 3){
		         alert('부서 당 결재선 인원은 3명까지 가능합니다');
		         return;
		      }
		      break;
   }
   selectEmp2 = emp2;

}

function createLine2(){   // 결재선 선택 화면을 모달로 띄워준다. 뒷 배경은 암영
   flag2 = 1;
//   var input1 = $("receiveModal").val();
//   	var input1 = document.getElementById("receiveModal").value;
//   	var input2 = document.getElementById("receiveModal2").value;
//   	alert("input1 :"+input1);
//   	if(input1){
   $("#chart_div2").show("fast");
   var width2 = $(window).width();
   var height2 = $(window).height();
   $("#mask2").css({'width' : width2, 'height' : height2});
   $("#mask2").fadeIn(1000);
   $("#mask2").fadeTo("fast",0.6);
   onEmp2();
//   	}else if(input2){
//   	$("#chart_div2").show("fast");
//   	   var width2 = $(window).width2();
//   	   var height2 = $(window).height2();
//   	   $("#mask2").css({'width2' : width2, 'height2' : height2});
//   	   $("#mask2").fadeIn(1000);
//   	   $("#mask2").fadeTo("fast",0.6);
//   	}
}

function enterCheck2() {
   if(event.keyCode === 13) {
      event.preventDefault2();
   }
}

//function receiverCheck() {
//   var div = $(".message_write_target");
//   var box = "<input type=\"text\" class=\"msg_write_target2\" onkeypress=\"receiveCheck();\">";
//   div.append(box);
//   focusPlz();
//}

//emp
function emp(empNo, empDept, empPos, empName){
   this.empNo = empNo;
   this.empDept = empDept;
   this.empPos = empPos;
   this.empName = empName;
}

//메일 작성 시 받는 이 박스 클릭하면 발생하는 이벤트, 조직도 오픈.
function callChart2(dept2, kind2){
	removeChart2();
   var con2 = $("#context").val();
   if(flag2 == 1){
      flag2 = 2;
      $.ajax({
    	 
         url : con2+"/emp/selectByDept?type="+dept2,
         method : "post",
         dataType : "json",
         success : function(data){
            createChart2(data, kind2);
         },
         error : function(){
            alert(dept2);
         }
      });
   }
   else if (flag2 == 2){
      removeChart2(kind2);
      flag2 = 1;
   }
}
//조직도 그리기
function createChart2(empList2, kind2){
	removeChart2();
   var ul2 = $("#"+kind2);
   var con2 = $("#context").val();
   for(var i in empList2){
      var li2 = $("<li>");
      $("<img src = "+con2+"/img/person.png>").appendTo(li2);
      $("<a onclick = 'addLine2(new emp(\""+empList2[i].empNo+"\",\""+empList2[i].deptName+"\",\""+empList2[i].empPos+"\",\""+empList2[i].empName+"\"),this);'>").text(empList2[i].empPos+" "+empList2[i].empName).appendTo(li2);
      li2.appendTo(ul2);
   }
}
//조직도 닫기
function removeChart2(kind2){
//   $("#"+kind2+" li").remove();
   $("#mChart li").remove();
   $("#bChart li").remove();
   $("#fChart li").remove();
   $("#iChart li").remove();
}

function showChart2(dept2, kind2){
   var con2 = $("#context").val();
   removeChart2();
   if(flag2 == 1){
      flag2 = 2;
      $.ajax({
         url : con2+"/emp/selectByDept?type="+dept2,
         method : "post",
         dataType : "json",
         success : function(data){
            createChart2(data, kind2);
         },
         error : function(){
            alert(dept2);
         }
      });
   }
   else if (flag2 == 2){
      removeChart2();
      flag2 = 1;
   }
}

function onEmp2(){
	var mailNo = $("#mailNo").val();
	var con = $("#context").val();
	$.ajax({
		url : con+"/mail/mail_draftsInbox_target?mailNo="+mailNo,
		method : "post",
		dataType : "json",
		success : function(result){
			var obj = eval("("+result.carbonList+")");
			
			for(var i in obj){
			
				obj[i].empPos;
				obj[i].empName;
			}
			onEmpLoad2(result);

		},
		error : function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}
function onEmpLoad2(result){
	$("#emps2 li").remove();
	var con = $("#context").val();
	var obj = eval("("+result.carbonList+")");
//	for(var i in obj){
//		obj[i].empPos;
//		obj[i].empName;
//	}
	$(nowLi).css("background-color","transparent");
	var ul2 = $("#emps2");
	
	//$("<img src = "+con+"/img/person.png>").appendTo(li);
	for(i = 0;i<obj.length;i++){
		var li2 = $("<li>");
		$("<a onclick = 'deleteEmp2("+lineCnt2+",this)'>").text(obj[i].empPos+" "+obj[i].empName).appendTo(li2);
		li2.appendTo(ul2);
		lineArray2[lineCnt2] = obj[i];
		lineCnt2 = lineCnt2 + 1;
	}
};

//오른쪽 화살표 클릭 시 실행 함수
function moveToRight2(){
   var con2 = $("#context").val();
   $(nowLi2).css("background-color","transparent");
   var ul2 = $("#emps2");
   var li2 = $("<li>");
   //$("<img src = "+con+"/img/person.png>").appendTo(li);
   $("<a onclick = 'deleteEmp2("+lineCnt2+",this)'>").text(selectEmp2.empPos+" "+selectEmp2.empName).appendTo(li2);
   li2.appendTo(ul2);
   lineArray2[lineCnt2] = selectEmp2;
   lineCnt2 = lineCnt2 + 1;
}

function closeToLine2(){
	   $("#chart_div2").hide("fast");
	   window.close();
}

// 오른쪽 창에 존재하는 사원 클릭 시 클릭한 정보 저장
function deleteEmp2(index2,li2){
   nowLi2r = li2;
   $(nowLi2r).css("background-color","silver");
   delEmp2 = index2;   // 선택 된 index 번호 저장
}

// 왼쪽 화살표 클릭 시 실행 함수
function moveToLeft2(){
   $(nowLi2r).css("background-color","transparent");
   lineArray2.splice(delEmp2,1);   // 배열에서 선택된 index 요소를 제거
   lineCnt2 = lineCnt2 - 1;      // 결재선 라인 개 수 1 감소
   $("#emps2 li:eq("+delEmp2+")").remove();   // 오른 쪽 ul의 index번째 요소 li 제거
}

// 완료버튼 눌렀을 시 배열로 결재선 생성
function makeToLine2(){
   removeChart2();
   createMyLine2();
   $("#chart_div2").hide("fast");
   $("#mask2").css({'width2' : 0, 'height2' : 0});
   close();
}
//우측 박스에 있는 사원을 받는 이로 최종 적용
function createMyLine2(){
   if(lineCnt2 > 0){
      removeLine2();
      var table2 = $("#approvLine2");
      var tr2 = $("<tr id='name2'>");
      var td2=$("<td>");
      
      for(var i=0;i<lineArray2.length;i++){
        $("<input type='text'  name='carbonName' id='carbonName' disabled>").val(lineArray2[i].empName).appendTo(td2);
        $("<input type='hidden'  name='carbonId' id='carbonId'>").val(lineArray2[i].empNo).appendTo(td2);
         count2 ++;
      }
   
      td2.appendTo(tr2);
      tr2.appendTo(table2);
      finalLine2 = lineArray2;
      
   }else{
      lineArray2 = new Array();
      lineCnt2 = 0;
      mCnt2 = bCnt2 = fCnt2 = iCnt2 = 0;
   }
}

//우측 박스에 있는 사원을 받는 이로 최종 적용
//function createMyLine2(){
//   if(lineCnt2 > 0){
//      removeLine2();
//      var div2 = $("#mailWriteTarget_Div2");
//      
//      for(var i in lineArray2){
//         var target2 = $("<span name='carbonId' class='msg_write_target2' readonly='readonly'>");
//         target2.text(lineArray2[i].empPos+" "+lineArray2[i].empName);
//         div2.append(target2);
//      }
//      finalLine2 = lineArray2;
//   }else{
//      lineArray2 = new Array();
//      lineCnt2 = 0;
//   }
//}


function removeLine2(){
	$("#approvLine2 tr").remove();
}


/*웹소켓
function message_submit(){
   var con = $("#contextPath").val();
   var contentArea2 = $("#contentArea2").val();
   var title = $("#msg_title").val();
   
   var receiveList = new Array();
   for(var i in lineArray2){
      receiveList[i] = lineArray2[i].empNo;
      stompClient.send("/ws/header/message/"+lineArray2[i].empNo);
   }
   location.href = con+"/messenger/addMessage?title="+title+"&content="+contentArea2+"&list="+receiveList;
}
 */
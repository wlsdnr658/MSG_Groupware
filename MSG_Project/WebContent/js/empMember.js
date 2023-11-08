var lineArray = new Array();   // 결재선에 들어갈 emp객체 정보를 저장할 배열
var finalLine = new Array();
var nowLi, nowLir;
var selectEmp;
var delEmp;
var lineCnt = 0;   // emp객체 배열에 들어가는 사원 수를 count할 변수
var count=0;
mCnt = bCnt = fCnt = iCnt = 0;
$(function(){
   
//   var reply = $("#replyID").val();
//   if(reply!="" && count==0){
//      count++;
//      var con = $("#contextPath").val();
//      $.ajax({
//         url: con+"/messenger/getReplyEmp?empNo="+reply,
//         type: "post",
//         dataType: "json",
//         success: function(data){
//            selectEmp = data;
//            var ul = $("#emps");
//            var li = $("<li>");
//            //$("<img src = "+con+"/img/person.png>").appendTo(li);
//            $("<a onclick = 'deleteEmp("+lineCnt+",this)'>").text(selectEmp.empPos+" "+selectEmp.empName).appendTo(li);
//            li.appendTo(ul);
//            lineArray[lineCnt] = selectEmp;
//            lineCnt = lineCnt + 1;
//            
//            var div = $("#messageWriteTarget_Div");
//            var target = $("<span name='receiverID' class='msg_write_target' readonly='readonly'>");
//            target.text(data.empPos+" "+data.empName);
//            div.append(target);
//            finalLine = lineArray;
//         }
//         
//      })
//      
//   }
   
//   var textLimit = 250;
//   $(".msg_write_target").focus();
   $("#mask").mousedown(function(){   // 암영 된 부분을 클릭 할 시 모달로 띄운 창을 닫아준다.
      flag = 1;
      $("#chart_div").hide("fast");
      $("#mask").css({'width' : 0, 'height' : 0});
      removeChart();
   });
   
//   $("#contentArea").keyup(function(){
//      //textarea 길이 체크
//      var textLength = $(this).val().length;
//      //길이 업데이트
//      $("#textCount").text(textLength);
//      console.log(textLength);
//      if(textLength > textLimit){
//         $(this).val($(this).val().substr(0, textLimit));
//         textLength = $(this).val().length;
//         $("#textCount").text(textLength);
//      }
//   });
});

var flag = 1;
//결재선에 선택되는 emp를 배열에 담아준다.
function addLine(emp, li){
//   nowLi = li;
//   $(nowLi).css("background-color","silver");
//   switch(emp.empDept){
//   case '경영/기획부':
//      break;
//   case '영업부':
//      break;
//   case '재무부':
//      break;
//   case 'IT부':
//      break;
//   }
//   selectEmp = emp;
//
//}
	nowLi = li;
	   $(nowLi).css("background-color","silver");
	   switch(emp.empDept){
	   case '경영/기획부':
	      mCnt = mCnt + 1;
	      if(mCnt > 3){
	         alert('부서 당 결재선 인원은 3명까지 가능합니다');
	         return;
	      }
	      break;
	   case '영업부':
	      bCnt = bCnt + 1;
	      if(bCnt > 3){
	         alert('부서 당 결재선 인원은 3명까지 가능합니다');
	         return;
	      }
	      break;
	   case '재무부':
	      fCnt = fCnt + 1;
	      if(fCnt > 3){
	         alert('부서 당 결재선 인원은 3명까지 가능합니다');
	         return;
	      }
	      break;
	   case 'IT부':
	      iCnt = iCnt + 1;
	      if(iCnt > 3){
	         alert('부서 당 결재선 인원은 3명까지 가능합니다');
	         return;
	      }
	      break;
	   }
	   selectEmp = emp;

	}

function createLine(){   // 결재선 선택 화면을 모달로 띄워준다. 뒷 배경은 암영
   flag = 1;
//   var input1 = $("receiveModal").val();
//   	var input1 = document.getElementById("receiveModal").value;
//   	var input2 = document.getElementById("receiveModal2").value;
//   	alert("input1 :"+input1);
//   	if(input1){
   $("#chart_div").show("fast");
   var width = $(window).width();
   var height = $(window).height();
   $("#mask").css({'width' : width, 'height' : height});
   $("#mask").fadeIn(1000);
   $("#mask").fadeTo("fast",0.6);
//   	}else if(input2){
//   	$("#chart_div2").show("fast");
//   	   var width = $(window).width();
//   	   var height = $(window).height();
//   	   $("#mask").css({'width' : width, 'height' : height});
//   	   $("#mask").fadeIn(1000);
//   	   $("#mask").fadeTo("fast",0.6);
//   	}
}

function enterCheck() {
   if(event.keyCode === 13) {
      event.preventDefault();
   }
}

//function receiverCheck() {
//   var div = $(".message_write_target");
//   var box = "<input type=\"text\" class=\"msg_write_target\" onkeypress=\"receiveCheck();\">";
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
function callChart(dept, kind){
	removeChart();
   var con = $("#context").val();
   if(flag == 1){
      flag = 2;
      $.ajax({
    	 
         url : con+"/emp/selectByDept?type="+dept,
         method : "post",
         dataType : "json",
         success : function(data){
            createChart(data, kind);
         },
         error : function(){
            alert(dept);
         }
      });
   }
   else if (flag == 2){
      removeChart(kind);
      flag = 1;
   }
}
//조직도 그리기
function createChart(empList, kind){
	removeChart();
   var ul = $("#"+kind);
   var con = $("#context").val();
   for(var i in empList){
      var li = $("<li>");
      $("<img src = "+con+"/img/person.png>").appendTo(li);
      $("<a onclick = 'addLine(new emp(\""+empList[i].empNo+"\",\""+empList[i].deptName+"\",\""+empList[i].empPos+"\",\""+empList[i].empName+"\"),this);'>").text(empList[i].empPos+" "+empList[i].empName).appendTo(li);
      li.appendTo(ul);
   }
}
//조직도 닫기
function removeChart(kind){
//   $("#"+kind+" li").remove();
   $("#mChart li").remove();

   $("#bChart li").remove();

   $("#fChart li").remove();

   $("#iChart li").remove();
}

function showChart(dept, kind){
   var con = $("#context").val();
   removeChart();
   if(flag == 1){
      flag = 2;
      $.ajax({
         url : con+"/emp/selectByDept?type="+dept,
         method : "post",
         dataType : "json",
         success : function(data){
            createChart(data, kind);
         },
         error : function(){
            alert(dept);
         }
      });
   }
   else if (flag == 2){
      removeChart();
      flag = 1;
   }
}

//오른쪽 화살표 클릭 시 실행 함수
function moveToRight(){
   var con = $("#context").val();
   $(nowLi).css("background-color","transparent");
   var ul = $("#emps");
   var li = $("<li>");
   //$("<img src = "+con+"/img/person.png>").appendTo(li);
   $("<a onclick = 'deleteEmp("+lineCnt+",this)'>").text(selectEmp.empPos+" "+selectEmp.empName).appendTo(li);
   li.appendTo(ul);
   lineArray[lineCnt] = selectEmp;
   lineCnt = lineCnt + 1;
}

function closeToLine(){
	   $("#chart_div").hide("fast");
	   window.close();
}

// 오른쪽 창에 존재하는 사원 클릭 시 클릭한 정보 저장
//function deleteEmp(index,li){
//   nowLir = li;
//   $(nowLir).css("background-color","silver");
//   delEmp = index;   // 선택 된 index 번호 저장
//}
function deleteEmp(index){
	   delEmp = index;   
	}

// 왼쪽 화살표 클릭 시 실행 함수
function moveToLeft(){
//   $(nowLir).css("background-color","transparent");
   lineArray.splice(delEmp,1);   // 배열에서 선택된 index 요소를 제거
   lineCnt = lineCnt - 1;      // 결재선 라인 개 수 1 감소
   $("#emps li:eq("+delEmp+")").remove();   // 오른 쪽 ul의 index번째 요소 li 제거
}

// 완료버튼 눌렀을 시 배열로 결재선 생성
function makeToLine(){
   removeChart();
   createMyLine();
   $("#chart_div").hide("fast");
   $("#mask").css({'width' : 0, 'height' : 0});
//   close();
}
//우측 박스에 있는 사원을 받는 이로 최종 적용
function createMyLine(){
   if(lineCnt > 0){
      removeLine();
      var table = $("#approvLine");
      var tr1 = $("<tr id='name1'>");
      var td1=$("<td>");
      var count = 0;
  
      for(var i=0;i<lineArray.length;i++){
        $("<input type='text' name='receiverName' id='receiverName' disabled>").val(lineArray[i].empName).appendTo(td1);
        $("<input type='hidden'  name='receiverId' id='receiverId'>").val(lineArray[i].empNo).appendTo(td1);
         count ++;
      }

      td1.appendTo(tr1);
      tr1.appendTo(table);
      finalLine = lineArray;
   }else{
      lineArray = new Array();
      lineCnt = 0;
   }
}

//우측 박스에 있는 사원을 받는 이로 최종 적용
//function createMyLine(){
//   if(lineCnt > 0){
//      removeLine();
//      var div = $("#mailWriteTarget_Div");
//      
//      for(var i in lineArray){
//         var target = $("<span name='receiverId' class='msg_write_target' readonly='readonly'>");
//         target.text(lineArray[i].empPos+" "+lineArray[i].empName);
//         div.append(target);
//      }
//      finalLine = lineArray;
//   }else{
//      lineArray = new Array();
//      lineCnt = 0;
//   }
//}
function removeLine(){
//   $("#messageWriteTarget_Div span").remove();
	$("#approvLine tr").remove();
}



/*웹소켓
function message_submit(){
   var con = $("#contextPath").val();
   var contentArea = $("#contentArea").val();
   var title = $("#msg_title").val();
   
   var receiveList = new Array();
   for(var i in lineArray){
      receiveList[i] = lineArray[i].empNo;
      stompClient.send("/ws/header/message/"+lineArray[i].empNo);
   }
   location.href = con+"/messenger/addMessage?title="+title+"&content="+contentArea+"&list="+receiveList;
}
 */
var lineArray = new Array();   
var finalLine = new Array();
var nowLi;
var selectEmp;
var delEmp;
var lineCnt = 0; 
var mCnt, bCnt, fCnt, iCnt;
mCnt = bCnt = fCnt = iCnt = 0;

$(function(){
   $("#mask").mousedown(function(){  
      flag = 1;
      $("#chart_div").hide("fast");
      $("#mask").css({'width' : 0, 'height' : 0});
      removeChart();
   });
});

function createLine(){  
   flag = 1;
   $("#chart_div").show("fast");
   var width = $(window).width();
   var height = $(window).height();
   $("#mask").css({'width' : width, 'height' : height});
   $("#mask").fadeIn(1000);
   $("#mask").fadeTo("fast",0.6);
}

var flag = 1;  
var path; 
function showChart(dept, kind){
   removeChart();
   if(flag == 1){
      flag = 2;
      path = $("#path").val();
      $.ajax({
         url : path+"/emp/selectByDept?type="+dept,
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

function createChart(empList, kind){
   var ul = $("#"+kind);
   for(var i in empList){
      var li = $("<li>");
      $("<img src = "+path+"/img/person.png>").appendTo(li);
      $("<a onclick = 'addLine(new emp(\""+empList[i].empNo+"\",\""+empList[i].deptName+"\",\""+empList[i].empPos+"\",\""+empList[i].empName+"\"),this);'>").text(empList[i].empPos+" "+empList[i].empName).appendTo(li);
      li.appendTo(ul);
   }
}


function removeChart(){
   $("#mChart li").remove();
   $("#bChart li").remove();
   $("#fChart li").remove();
   $("#iChart li").remove();
}


function emp(empNo, empDept, empPos, empName){
   this.empNo = empNo;
   this.empDept = empDept;
   this.empPos = empPos;
   this.empName = empName;
}


function addLine(emp, li){
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

function createMyLine(){
   if(lineCnt > 0){
      removeLine();
      
      var table = $("#approvLine");
      var tr1 = $("<tr id='name1'>");
      var td1 = $("<td>");
      var count = 0;
      
      for(var i=0;i<lineArray.length;i++){
    	  
    	 if(lineArray.length > 5){
    		 alert("최대 5명까지 가능합니다.")
    		 break;
    	 }
    	 
         $("<input type='text' style='width:60px' name='PARTICIPANT' class='name'>").val(lineArray[i].empName).appendTo(td1);
         count ++;
         
      }
      
      td1.appendTo(tr1);
      tr1.appendTo(table);
      
      finalLine = lineArray;
      
   }else{
      lineArray = new Array();
      lineCnt = 0;
      mCnt = bCnt = fCnt = iCnt = 0;
   }
}


function removeLine(){
   $("#approvLine tr").remove();
}

function moveToRight(){
   $(nowLi).css("background-color","transparent");
   var ul = $("#emps");
   var li = $("<li>");
   $("<img src = "+path+"/img/person.png>").appendTo(li);
   $("<a onclick = 'deleteEmp("+lineCnt+")'>").text(selectEmp.empPos+" "+selectEmp.empName).appendTo(li);
   li.appendTo(ul);
   lineArray[lineCnt] = selectEmp;
   lineCnt = lineCnt + 1;
}


function deleteEmp(index){
   delEmp = index;   
}

function moveToLeft(){
   lineArray.splice(delEmp,1);   
   lineCnt = lineCnt - 1;    
   $("#emps li:eq("+delEmp+")").remove();   
}

function makeToLine(){
   removeChart();
   createMyLine();
   $("#chart_div").hide("fast");
   $("#mask").css({'width' : 0, 'height' : 0});
}

function closeToLine(){
	$("#chart_div").hide("fast");
	$("#mask").hide();
}

function doSumit(){
   $("#APLINE").val(JSON.stringify(finalLine));

   return true;
}
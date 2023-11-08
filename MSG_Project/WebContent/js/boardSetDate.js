$(function() {
	
	var pStartDate = $("#inputStartDate").val();
	var pEndDate = $("#inputEndDate").val();  
    var date1 = new Date(pStartDate);
	
    var day1 = date1.getDate();
    var month1 = date1.getMonth() + 1;
    var year1 = date1.getFullYear();

    if (month1 < 10) month1 = "0" + month1;
    if (day1 < 10) day1 = "0" + day1;

    var startDate = year1 + "-" + month1 + "-" + day1;       
    $("#pStartDate").attr("value", startDate);
    
    var date2 = new Date(pEndDate);
    
    var day2 = date2.getDate();
    var month2 = date2.getMonth() + 1;
    var year2 = date2.getFullYear();

    if (month2 < 10) month2 = "0" + month2;
    if (day2 < 10) day2 = "0" + day2;

    var endDate = year2 + "-" + month2 + "-" + day2;       
    $("#pEndDate").attr("value", endDate);
});

$(function() {
	
	var pStartDate3 = $("#inputStartDate1").val();
	var pEndDate3 = $("#inputEndDate1").val();  
    var date3 = new Date(pStartDate3);
    
    var day3 = date3.getDate();
    var month3 = date3.getMonth() + 1;
    var year3 = date3.getFullYear();

    if (month3 < 10) month3 = "0" + month3;
    if (day3 < 10) day3 = "0" + day3;

    var startDate3 = year3 + "-" + month3 + "-" + day3;       
    $("#TSTARTDATE1").attr("value", startDate3);
    
    var date4 = new Date(pEndDate3);
    
    var day4 = date4.getDate();
    var month4 = date4.getMonth() + 1;
    var year4 = date4.getFullYear();

    if (month4 < 10) month4 = "0" + month4;
    if (day4 < 10) day4 = "0" + day4;

    var endDate4 = year4 + "-" + month4 + "-" + day4;       
    $("#TENDDATE1").attr("value", endDate4);
});




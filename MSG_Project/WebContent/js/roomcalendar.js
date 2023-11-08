$(function() {
	
	var con = $("#contextPath").val();

		var calendar = $('#calendar').fullCalendar({

			header : {
				left : 'prev,next today',
				center : 'title',
				right : 'month,agendaWeek,agendaDay,listWeek'
			},
			// 			titleFormat : {
			// 				month : "MMMM yyyy",
			// 				week : "'Week from 'd [MMMM] { 'to' d MMMM  yyyy}",
			// 				day : "dddd d MMMM yyyy"
			// 			},
			// 			views : {
			// 				week : {
			// 					titleFormat : '[Week from] D MMMM YYYY',
			// 					titleRangeSeparator : ' to ',
			// 				}
			// 			},
			selectable : true,
			selectHelper : true,
			theme : true,
			select : function(start, end, allDay) {
				
				var view = $('#calendar').fullCalendar('getView');
				
				var data ={}
				data["start"] = start.format('YYYY-MM-DD HH:mm');
				data["end"] = end.format('YYYY-MM-DD HH:mm');
				
				$.ajax({url : con+ "/management/managementRoomreservation",
					type : "get",
					data : data,
					dataType : "json",
					success : function(result) {
				
						if (result) {

							var assigned = reservation(
									data["start"],
									data["end"]);

							if (assigned) {
								calendar
										.fullCalendar(
												'renderEvent',
												{
													assigned : assigned,
													start : start,
													end : end,
													allDay : allDay

												}, true // make
														// the
														// event
														// "stick"
										);
							}
							calendar.fullCalendar('unselect');

						} else {
							
						}

					}

				});

	},

			editable : true,
			eventSources : [ {

				// your event source
				url : con+'/management/managementRoommodify?typeName='+typeName,
				type : 'post',

				data : {
					startDate : 'startDate',
					endDate : 'endDate'
				},
				dataType : 'json',
				
				success : function(assigned) {

					if (assigned) {

						for ( var i in assigned.roomitemList) {
							if(assigned.roomitemList[i].status !=0){
							calendar.fullCalendar('renderEvent', {
								title :assigned.roomitemList[i].roomName+" "+assigned.roomitemList[i].empName,
								assigned : assigned.roomitemList[i].assigned,
								start : assigned.roomitemList[i].startDate,
								end : assigned.roomitemList[i].endDate,
								color : 'yellow',
								textColor : 'yellow'
							}, false // make the event "stick"

							);

							}
						}
					}

					calendar.fullCalendar('unselect');

				},
				error : function() {

					alert('there was an error while fetching events!');
				},
				color : 'yellow',
				textColor : 'black'
			} ],

			eventClick : function(calEvent, jsEvent, view, date) {
				
				$(this).css('border-color', 'red');

			},
			
			dayClick : function(date, jsEvent, view, resourceObj) {

				alert('Date: ' + date.format());
			

			}

		});
	});

	
function reservation(start,end) {
	
	var con = $("#contextPath").val();
	
	window.open(con+"/management/managementRoomreservform?start="+start
			+"&end="+end+"&typeName="+typeName+"&listName="+listName, "",
					"width=250, height=360, history=no, left=400 , top=200 , resizable=no, status=no, scrollbars=no");

}

function modify() {
	
	var con = $("#contextPath").val();
	
	window.open(con+"/management/managementRoommodify", "",
					"width=1300, height=200, history=no, left=400 , top=200 , resizable=no, status=no, scrollbars=no");
}
	
	
	
	
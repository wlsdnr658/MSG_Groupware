$(function() {

	var con = $("#contextPath").val();

	var calendar = $('#calendar')
			.fullCalendar(
					{

						header : {
							left : 'prev,next today',
							center : 'title',
							right : 'month,agendaWeek,agendaDay,listWeek'
						},
						// titleFormat : {
						// month : "MMMM yyyy",
						// week : "'Week from 'd [MMMM] { 'to' d MMMM yyyy}",
						// day : "dddd d MMMM yyyy"
						// },
						// views : {
						// week : {
						// titleFormat : '[Week from] D MMMM YYYY',
						// titleRangeSeparator : ' to ',
						// }
						// },
						selectable : true,
						selectHelper : true,
						 theme : true,
						select : function(start, end, allDay) {
							var view = $('#calendar').fullCalendar('getView');

							var data = {}
							data["start"] = start.format('YYYY-MM-DD HH:mm');
							data["end"] = end.format('YYYY-MM-DD HH:mm');

							$.ajax({
									url : con+ "/management/managementCarreservation",
									
										type : "get",
										data : data,
										dataType : "json",
										success : function(result) {

											if (result) {
												// 자원 사용 가능 >>> 예약화면 으로 이동 url
												// 이동
											
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
												calendar
														.fullCalendar('unselect');

											} else {
											
											}

										},
										error : function(request, status, error) {

											
										}

									});

						},

						editable : true,

						eventSources : [ {

							// your event source
							url : con + '/management/managementCarmodifyList?typeName='+typeName,
							type : 'post',

							data : {
								startDate : 'startDate',
								endDate : 'endDate'
							},
							dataType : 'json',

							success : function(assigned) {

								if (assigned) {

									for ( var i in assigned.caritemList) {
										if (assigned.caritemList[i].status != 0) {
											calendar.fullCalendar(
															'renderEvent',
															{
																title : assigned.caritemList[i].carName
																		+ " "
																		+ assigned.caritemList[i].empName,
																assigned : assigned.caritemList[i].assigned,
																start : assigned.caritemList[i].startDate,
																end : assigned.caritemList[i].endDate,
																color : 'aqua',
																textColor : 'yellow'
															}, false);

										}
									}
								}

								calendar.fullCalendar('unselect');

							},
							
							color : 'yellow',
							textColor : 'black'
						} ],

						eventClick : function(calEvent, jsEvent, view, date,
								event) {

							

						

							$(this).css('border-color', 'red');

						}

					});
});

function reservation(start, end) {
	var con = $("#contextPath").val();

	window.open(con + "/management/managementCarform?start=" + start
					+ "&end=" + end + "&typeName=" + typeName + "&listName="
					+ listName, "",
					"width=250, height=360, history=no, left=400 , top=200 ,resizable=no, status=no, scrollbars=no");
	

}

function modify() {
	var con = $("#contextPath").val();
	window
			.open(con + "/management/managementCarmodify", "",
					"width=1300, height=200, left=400 , top=200, history=no, resizable=no, status=no, scrollbars=no");
}
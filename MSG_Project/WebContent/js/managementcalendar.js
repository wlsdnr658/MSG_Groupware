   $(function() {
      var con = $("#contextPath").val();
      var calendar = $('#calendar')
            .fullCalendar(
                  {

                     header : {
                        left : 'prev,next today',
                        center : 'title',
                        right : 'month'
                     },
                     eventLimit:true,
                     theme : true,
                     selectable : true,
                     selectHelper : true,
                     select : function(start, end, allDay) {

                     },
                     editable : true,
                     dayClick: function(date, allDay, jsEvent, view) {
                           var yy=date.format("YYYY");
                           var mm=date.format("MM");
                           var dd=date.format("DD");
                           var ss=date.format("dd");
                           onchangeDay(yy,mm,dd,ss);
                             },
                     eventSources : [ {
                        // your event source
                        url : con+'/management/managementitemsList',
                        type : 'get',

                        data : {
                           startDate : 'startDate',
                           endDate : 'endDate'
                        },
                        dataType : 'json',

                        success : function(assigned) {

                           if (assigned) {

                              for ( var i in assigned.itemsList) {
                                 if (assigned.itemsList[i].status != 0) {
                                    calendar
                                          .fullCalendar(
                                                'renderEvent',
                                                {
                                                   title : assigned.itemsList[i].name
                                                         + " "
                                                         + assigned.itemsList[i].empName,
                                                   assigned : assigned.itemsList[i].assigned,
                                                   start : assigned.itemsList[i].startDate,
                                                   end : assigned.itemsList[i].endDate,
                                                   color : '#f3f4f9',
                                                   textColor : 'yellow'
                                                }, false // make the event "stick"

                                          );

                                 }
                              }
                           }

                           calendar.fullCalendar('unselect');

                        },
                      
                        color : 'yellow',
                        textColor : 'black'
                     } ],

                     eventClick : function(calEvent, jsEvent, view, date) {

                       

                        $(this).css('border-color', 'aqua');

                     }

                  });
   });
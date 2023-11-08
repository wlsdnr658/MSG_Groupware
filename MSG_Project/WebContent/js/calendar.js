$(function(){
   $('#calendar').fullCalendar({
      
      eventAfterAllRender: function(){
             $('.fc-row').css('min-height','1em'); 
             $('.fc-week, .fc-widget-content, .fc-rigid').attr('style','height:20px');
             $('.fc-row fc-week fc-widget-content').attr('style','height:20px');

          },
        defaultView: 'month',
        height : 280,
        width:200,
        theme : true
       
        
   });
   
   
});

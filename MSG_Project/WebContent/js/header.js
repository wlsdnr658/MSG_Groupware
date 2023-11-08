var sock;
var stompClient = null;
var empNo = $("#empNo").val();
var path = $("#path").val();

function upup_a(){
	alert_message();
}
function alert_message() {
	$("#message_alert").fadeIn("slow");
	setTimeout(function() {
		$("#message_alert").fadeOut("slow");
	},5000);
};
function upup_b(){
	alert_approve();
}
function alert_approve() {
	$("#approve_alert").show();
	$("#approve_alert").animate({bottom: '80px'},400);
	setTimeout(function() {
		$("#approve_alert").animate({bottom: '0px'},400);
		setTimeout(function() {
			$("#approve_alert").hide();
		},500);
	},5000);
};
function upup_c(){
	alert_mail();
}
function alert_mail() {
	$("#mail_alert").show();
	$("#mail_alert").animate({bottom: '80px'},400);
	setTimeout(function() {
		$("#mail_alert").animate({bottom: '0px'},400);
		setTimeout(function() {
			$("#mail_alert").hide();
		},500);
	},5000);
};

////////////////////////////////
//웹소켓 연결
function connect(){
	
	sock = new SockJS("http://192.168.0.33:8084/MSG_Project/header");
	//sock = new SockJS("${contextPath}/chat");
	var empNo = $("#empNo").val();
	stompClient = Stomp.over(sock);
	stompClient.connect({},function(data){
		stompClient.subscribe("/topic/message/"+empNo,function(){
			alert_message(data.body);
		});
	});
};

$(function(){
	var dept = $("#user_auth").val();
	if(dept == '경영/기획부'){
		$("#auth").css("display","inline-block");
	}
	
var x, y; 

var getLocation = function(){
	return new Promise(function (resolve, reject){
		if(navigator.geolocation){
			navigator.geolocation.getCurrentPosition(function(position){
				x = position.coords.latitude;
				y = position.coords.longitude;
				resolve({posX : x, posY : y });
			});
		}
	});
	
};
	
function doWeather(){
		var path = $("#path").val();
		getLocation().then(function (result){
			var con = path;
			$.ajax({
				url : con+"/weather/getWeather?posX="+x+"&posY="+y,
				dataType : "text",
				method : "post",
				success : function(weather){
					var weather_Info = weather.split(",");
					var city = weather_Info[0];
					var status = weather_Info[1];	// Clouds, Haze ,
					var degree = parseInt(weather_Info[2]);

					var box = $("#imgBox");
					var path = $("#path").val();
					
					if(status == "Rain"){
						$("<img id='rain' style='position:unset' src = '"+path+"/img/rainy.png'>").appendTo(box);
					}else if(status == "Sunny"){
						$("<img id='sunny' style='position:unset' src = '"+path+"/img/sunny.PNG'>").appendTo(box);
					}else if(status == "Haze"){
						$("<img id='haze' style='position:unset' src = '"+path+"/img/Haze.png'>").appendTo(box);
					}else if(status == "Clouds"){
						$("<img id='clouds' style='position:unset' src = '"+path+"/img/cloudy.png'>").appendTo(box);
					}else if(status == "Clear"){
						$("<img id='clear' style='position:unset' src = '"+path+"/img/clear.png'>").appendTo(box);
					}else if(status == "Mist"){
						$("<img id='mist' style='position:unset' src = '"+path+"/img/Haze.png'>").appendTo(box);
					}

					$("#place").text(city);
					$("#temp").text(degree+"˚");
				},
				error : function(){
//					alert("fail");
				}
			});
		});
	};
	doWeather();
	connect();
});

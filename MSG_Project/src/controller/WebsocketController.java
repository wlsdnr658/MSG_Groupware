package controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import service.ChatService;

@Controller
@RequestMapping("/ws")
public class WebsocketController {
	@Autowired
	ChatService chatService;
	//쪽지 알림
	@MessageMapping("/header/message/{empNo}")
	@SendTo("/topic/message/{empNo}")
	public String alertMessage(@DestinationVariable(value = "empNo")String empNo) {
		return "/messenger/messageList";
	}		
	//채팅 웹소켓
	@CrossOrigin
	@MessageMapping("/chat/{empName}/{roomNo}/{empNo}")
	@SendTo("/topic/chat/{roomNo}")// 수신은 채팅방 기준으로?
	public Map<String, Object> chatMessage(String chatMsg,
			@DestinationVariable(value = "empName")String empName,
			@DestinationVariable(value = "roomNo")String roomNo,
			@DestinationVariable(value = "empNo")String empNo) {
		Map<String, Object> chat = new HashMap<String, Object>();
		chat.put("empNo", empNo);
		chat.put("empName", empName);
		chat.put("chatting", chatMsg);
		System.out.println("chat : "+chat);
		return chat;
	}	
	//공지사항 수정
	@CrossOrigin
	@MessageMapping("/chat/notice/{empName}/{roomNo}")
	@SendTo("/topic/chat/notice/{roomNo}")
	public Map<String, Object> chatNotice(String roomNotice,
			@DestinationVariable(value = "empName")String empName,
			@DestinationVariable(value = "roomNo")String roomNo) {
		Map<String, Object> chat = new HashMap<String, Object>();
		System.out.println("들어와");
		chat.put("empName", empName);
		chat.put("roomNotice", roomNotice);
		return chat;
	}	
	//채팅방 퇴장
	@CrossOrigin
	@MessageMapping("/chat/exit/{roomNo}/{empNo}")
	@SendTo("/topic/chat/exit/{roomNo}/{empNo}")// 수신은 채팅방 기준으로?
	public Map<String, Object> closeChatRoom(String chatMsg,
			@DestinationVariable(value = "roomNo")String roomNo,
			@DestinationVariable(value = "empNo")String empNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("empNo", empNo);
		result.put("roomNo", roomNo);
		System.out.println("result : "+result);
		return result;
	}	

	//
	@CrossOrigin
	@MessageMapping("/approval/{targetid}")
	@SendTo("/topic/approval/{targetid}")
	public Map<String, String> alarm(@DestinationVariable(value="targetid") String targetid, String msg){
		System.out.println(targetid+"에게 소켓 전송요청 들어옴");
		Map<String, String> msgParam = new HashMap<String, String>();
		msgParam.put("content", msg);
		return msgParam;
	}
	//파일 전송
	@CrossOrigin
	@MessageMapping("/chat/file/{empName}/{roomNo}")
	@SendTo("/topic/chat/file/{roomNo}")// 수신은 채팅방 기준으로?
	public Map<String, Object> chatFile(Map<String,Object> file,
			@DestinationVariable(value = "empName")String empName,
			@DestinationVariable(value = "roomNo")String roomNo
			) {
		String fileName = (String) file.get("fileName");
		//원본 파일명을 찾기 위함
		int fileIdx = fileName.indexOf("_")+1;
		//String fileOriginName = fileName.indexOf("_")+1;
		file.put("fileOriginName", fileName.substring(fileIdx));
		file.put("empName", empName);
		return file;
	}
	
	//채팅 웹소켓
//	@CrossOrigin
//	@MessageMapping("/chat/{empNo}/{roomNo}") //뒤에 받는 사람 id를 목록으로 
//	@SendTo("/topic/chat/{roomNo}")// 수신은 채팅방 기준으로?
//	public Map<String, Object> chattingMessage(Map<String, Object> data,
//			@DestinationVariable(value = "empNo")String empNo,
//			@DestinationVariable(value = "roomNo")String roomNo) {
//		System.out.println("와??");
//		System.out.println("data : " + data);
//		return data;
//	}	
}

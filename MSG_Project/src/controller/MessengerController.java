package controller;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import commons.DownloadView;
import service.ChatService;
import service.EmpService;
import service.MessageService;

@Controller
@RequestMapping("/messenger")
public class MessengerController {
	Scanner sc = new Scanner(System.in);
	
	private static final String UPLOAD_PATH = "C:/chatFile";
	@Autowired
	private MessageService msgService;
	@Autowired
	private ChatService chatService;
	@Autowired
	private EmpService empService;
	//////////페이지 요청/////////////
//	//메신저 메인 페이지 요청
//	@RequestMapping("/main")
//	public String messengerMainForm() {
//		return "messageList";
//	}
	//쪽지 작성 페이지 요청
	@RequestMapping("/messageWriteForm")
	public String messageWriteForm(@RequestParam(defaultValue = "") String replyID,  Model model) {
		model.addAttribute("replyID", replyID);
		return "messageWrite";
	}
	//보낸 쪽지 페이지 요청
	@RequestMapping("/messageList_send")
	public String messageList_send(Model model,
			@RequestParam(required = false)String keyword,
			@RequestParam(defaultValue = "제목")String type,
			@RequestParam(defaultValue = "1")int page,
			@RequestParam(defaultValue = "102")String boxType,
			HttpSession session
			) {
		if(keyword==null) {
			keyword="";
		}
		System.out.println("page : " + page);
		System.out.println("type : " + type);
		System.out.println("keyword : " + keyword);
		System.out.println("boxType : " + boxType);
		String empNo = (String)(((Map<String,Object>) session.getAttribute("user")).get("empNo"));
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		param.put("keyword", keyword);
		param.put("empNo", empNo);
		param.put("boxType", boxType);
		
		Map<String, Object> viewData = msgService.getMessageList(param, page);
		viewData.put("type", param.get("type"));
		viewData.put("keyword", param.get("keyword"));
		System.out.println("MessengerController ViewData: " + viewData);
		model.addAttribute("viewData", viewData);
		return "messageSend";
	}
	//내 채팅방 목록 페이지 요청
	@RequestMapping("/chatList")
	public String chatListForm() {
		return "chatList";
	}
	//채팅 초대 목록 페이지 요청
	@RequestMapping("/chatInvite")
	public String chatInvite(HttpSession session, Model model) {
		String empNo = (String)(((Map<String,Object>) session.getAttribute("user")).get("empNo"));
		model.addAttribute("inviteList",chatService.getChatInviteList(empNo));
		System.out.println("초대받은 채팅방 : "+chatService.getChatInviteList(empNo));
		return "chatInvite";
	}
	//채팅방 페이지 요청
	@RequestMapping("/openChatRoom")
	public String openChatRoom(@RequestParam String roomNo, HttpSession session, Model model) {
		String empNo = (String)(((Map<String,Object>) session.getAttribute("user")).get("empNo"));
		List<Map<String, Object>> chatList = new ArrayList<Map<String, Object>>();
		if((chatList = chatService.getChatList_byRoom(roomNo, empNo))==null) {
			//참여자가 아닌 사람이 접근했다면 메인페이지로 돌려보냄.
			return "main1";
		}
		//채팅 방 제목, 공지를 가져옴
		Map<String, Object> roomInfo = chatService.getRoomInfo(roomNo);
		//채팅 내용 모델에다가 담고 반환.
		for(Map<String, Object> chat: chatList) {
			if(chat.get("fileName")!=null) {
				String fileName = (String) chat.get("fileName");
				int index = fileName.indexOf("_")+1;
				chat.put("originFileName", fileName.substring(index));
				//파일 사이즈 구하기
				File nowFile = new File(UPLOAD_PATH, fileName);
				if(nowFile.exists()) {
					String fileSize = sizeCalculation(nowFile.length());
					chat.put("fileSize", fileSize);
				}
			}
		}
		model.addAttribute("chatList",chatList);
		model.addAttribute("roomNotice",roomInfo.get("roomNotice"));
		model.addAttribute("roomTitle",roomInfo.get("roomTitle"));
		model.addAttribute("roomNo",roomNo);
		model.addAttribute("empNo",empNo);
		model.addAttribute("empList",chatService.getChatEmpListByroomNo(roomNo));
		model.addAttribute("empNum",chatService.getEmpNum(roomNo));
		return "chatRoom";
	}
	@ResponseBody
	@RequestMapping("/getChatEmpList")
	public List<Map<String, Object>> getChatEmpList(String roomNo){
		return chatService.getChatEmpListByroomNo(roomNo);
	}
	
	public String sizeCalculation(long fileSize) {
	    String CalcuSize = null;
	    int i = 0;

	    double calcu = (double) fileSize;
	    while (calcu >= 1024 && i < 5) { // 단위 숫자로 나누고 한번 나눌 때마다 i 증가
	        calcu = calcu / 1024;
	        i++;
	    }
	    DecimalFormat df = new DecimalFormat("##0.0");
	    switch (i) {
	        case 0:
	            CalcuSize = df.format(calcu) + "Byte";
	            break;
	        case 1:
	            CalcuSize = df.format(calcu) + "KB";
	            break;
	        case 2:
	            CalcuSize = df.format(calcu) + "MB";
	            break;
	        case 3:
	            CalcuSize = df.format(calcu) + "GB";
	            break;
	        case 4:
	            CalcuSize = df.format(calcu) + "TB";
	            break;
	        default:
	            CalcuSize="ZZ"; //용량표시 불가

	    }
	    return CalcuSize;
	}
	//////////페이지 요청/////////////
	
	//////////쪽지 요청/////////////
	@ResponseBody
	@RequestMapping("/getNonReadMessageCount")
	public int getNonReadMessageCount(@RequestParam String empNo) {
		return msgService.getNonReadMessageCount(empNo);
	}
	@RequestMapping("/messageList")
	public String messageList(Model model,
			@RequestParam(required = false)String keyword,
			@RequestParam(required = false ,defaultValue = "제목")String type,
			@RequestParam(required = false ,defaultValue = "1")int page,
			@RequestParam(required = false ,defaultValue = "101")String boxType,
			HttpSession session
			) {
		if(keyword==null) {
			keyword="";
		}
//		System.out.println("page : " + page);
//		System.out.println("type : " + type);
//		System.out.println("keyword : " + keyword);
		String empNo = (String)(((Map<String,Object>) session.getAttribute("user")).get("empNo"));
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		param.put("keyword", keyword);
		param.put("empNo", empNo);
		param.put("boxType", boxType);
		
		Map<String, Object> viewData = msgService.getMessageList(param, page);
		viewData.put("type", param.get("type"));
		viewData.put("keyword", param.get("keyword"));
//		System.out.println("MessengerController ViewData: " + viewData);
		model.addAttribute("viewData", viewData);
		
		return "messageReceive";
	}
	//쪽지 보내기 요청
	@ResponseBody
	@RequestMapping("/getReplyEmp")
	public Map<String, Object> getReplyEmp(@RequestParam String empNo) {
		return empService.getUserInfo(empNo);
	}
	//쪽지 보내기 요청
	@RequestMapping("/addMessage")
	public String addMessage(@RequestParam Map<String, Object> msg, HttpSession session) {
		Map<String, Object> param = (HashMap<String, Object>)session.getAttribute("user");
		msg.put("senderID", param.get("empNo"));
		msgService.addMessage(msg);
		return "redirect:messageList_send";
	}
	//받은 쪽지함 삭제 요청
	@RequestMapping("/receive_delete")
	public void receive_delete(String MSGNO, String RECEIVERID) {
		msgService.receive_delete(MSGNO, RECEIVERID);
	}
	//보낸 쪽지함 삭제 요청
	@RequestMapping("/send_delete")
	public void send_delete(String MSGNO, String SENDERID) {
		msgService.send_delete(MSGNO, SENDERID);
	}
	//쪽지 읽음 요청
	@ResponseBody
	@RequestMapping("/readMessage")
	public int readMessage(String msgNo, String receiverID) {
		System.out.println(msgNo+"번 쪽지 읽음");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("msgNo", msgNo);
		param.put("receiverID", receiverID);
		return msgService.readMessage(param);
	}
	//////////쪽지 요청/////////////
	
	
	//////////채팅 요청/////////////
	//참여 중인 채팅방 목록 요청
	@ResponseBody
	@RequestMapping("/getChatRoomList")
	public List<Map<String, Object>> getChatRoomList(@RequestParam String empNo) {
		return chatService.getChatRoomList(empNo);
	}
	//채팅방 생성 요청
	@RequestMapping("/addChatRoom")
	public String addChatRoom(@RequestParam String title, HttpSession session) {
		System.out.println("채팅방 만들기");
		System.out.println("제목 :"+title);
		//
		//채팅방 만들고, 만든 채팅방을 참여 중인 채팅방 목록에 추가 한 뒤
		//채팅방 목록으로 리턴.
		Map<String, Object> user = (HashMap<String, Object>)session.getAttribute("user");
		String empNo = (String) user.get("empNo");
		chatService.addChatRoom(title, empNo);
		return "chatList";
	}
	//채팅 초대 승인
	@RequestMapping("/chatRoomInEmp")
	public String chatRoomInEmp(@RequestParam String roomNo, @RequestParam String empNo, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roomNo", roomNo);
		param.put("empNo", empNo);
		chatService.chatInEmp(param);
		model.addAttribute("inviteList",chatService.getChatInviteList(empNo));
		return "chatInvite";
	}
	//채팅 초대 거절
	@RequestMapping("/chatRoomInvite_reject")
	public String chatRoomInvite_reject(@RequestParam String roomNo, @RequestParam String empNo, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roomNo", roomNo);
		param.put("empNo", empNo);
		chatService.inviteChat_reject(param);
		model.addAttribute("inviteList",chatService.getChatInviteList(empNo));
		return "chatInvite";
	}	
	//채팅방 나가기
	@RequestMapping("/chatRoom_ExitEmp")
	public String chatroomExitEmp(@RequestParam String roomNo, @RequestParam String empNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roomNo", roomNo);
		param.put("empNo", empNo);
		chatService.chatRoom_ExitEmp(param);
		return "chatList";
	}		
	//채팅 내용 요청
	@RequestMapping(value="/getChattingList")
	public String getChattingList(@RequestParam String roomNo, @RequestParam String empNo, Model model) {
		List<Map<String, Object>> chatList = new ArrayList<Map<String, Object>>();
		if((chatList = chatService.getChatList_byRoom(roomNo, empNo))==null) {
			//null 이므로 진행 안되게 만들어버리자.
			return null;
		}
//		// 채팅 내용 모델에다가 담고 반환.
		return "chatRoom";
	}
	//채팅 저장 요청
	@ResponseBody
	@RequestMapping("/saveChat")
	public void saveChat(String content, String empNo, String roomNo) {
		System.out.println("controller 저장 content : "+content);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("content", content);
		param.put("empNo", empNo);
		param.put("roomNo", roomNo);
		chatService.saveChat(param);
	}
	//공지사항 저장 요청
	@ResponseBody
	@RequestMapping("/saveNotice")
	public void saveNotice(String roomNo, String roomNotice) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roomNo", roomNo);
		param.put("roomNotice", roomNotice);
		chatService.saveNotice(param);
	}	
	//파일 저장 요청
	@ResponseBody
	@RequestMapping(value="/saveFile", method=RequestMethod.POST)
	public Map<String, Object> saveFile(String roomNo, String empNo, String empName, MultipartFile file) {
//		System.out.println("들어옴");
//		System.out.println(roomNo);
//		System.out.println("============");
//		System.out.println(file);
		System.out.println("zzz");
		System.out.println(file);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roomNo", roomNo);
		param.put("empNo", empNo);
		param.put("content", empName+"님이 파일을 첨부하셨습니다.");
//		if(!fileName.equals("fail")){
//		}
		return chatService.saveFile(param, file);
		
	}	
	
	//다운로드 요청
	@RequestMapping("/chatDownload")
	public View download(String fileName) {
		//우리가 반환할 View를 만들어서 
		//객체를 반환하면 됨
		System.out.println(fileName);
		File file = chatService.getAttachFile(fileName);
		View view = new DownloadView(file);
		return view;
	}
	//채팅 초대 목록 요청
	@ResponseBody
	@RequestMapping("/chatInviteList")
	public List<Map<String, Object>> chatInviteList() {
		return empService.getAllOrderdEmp();
	}
	//채팅 초대 요청
	@ResponseBody
	@RequestMapping("/inviteEmp")
	public int inviteEmp(String empNo, String empName, String inviteEmpNo, String roomNo) {
		System.out.println(empNo);
		System.out.println(empName);
		System.out.println(inviteEmpNo);
		System.out.println(roomNo);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roomNo", roomNo);
		param.put("empNo", empNo);
		param.put("inviteEmpNo", inviteEmpNo);
		
		return chatService.inviteChatEmp(param);
	}
	//////////채팅 요청/////////////	
}


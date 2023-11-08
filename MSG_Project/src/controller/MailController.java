package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.View;

import commons.DownloadView;
import service.EmpService;
import service.MailService;

@Controller
@RequestMapping("/mail")
public class MailController {
	
	@Autowired
	MailService mailservice;
	EmpService empservice;
	
	//메일쓰기 구현
	@RequestMapping("/mail_write")
	public String mailSend(@RequestParam Map<String,Object> mail,@RequestParam("receiverId")List<String> receiverId,@RequestParam(defaultValue="") List<String> carbonId,Model model,String empNo,HttpSession session,@RequestParam(value="fileName", required=false) List<MultipartFile> file) {
		System.out.println("mailWrite");
//		System.out.println("1");
//		String id= (String)mail.get("mailNo");
//
//		System.out.println(mail.get("mailNo"));
		System.out.println("mail : " + mail);
//		System.out.println(mail.get("TITLE"));
		System.out.println("파일 :"+file);
		System.out.println(file);
		List<String> receiverIdList = new ArrayList<String>();
		for(String s : receiverId) {
			receiverIdList.add(s);
			System.out.println("receiverId123: " +s);
		}
		
		List<String> carbonIdList = new ArrayList<String>();
		if(carbonId != null) {
			for(String s : carbonId) {
				carbonIdList.add(s);
				System.out.println("carbonId1234: " +s);
			}			
		}
		
		mail.put("receiverIdList", receiverIdList);
		mail.put("carbonIdList", carbonIdList);
		mailservice.sendMail(mail,file);
		mail.put("mailNo", mailservice.getCurrentMailno());
		System.out.println(mail.put("mailNo", mailservice.getCurrentMailno()));
	
		mailservice.receiveMail(mail);
		mailservice.carbonMail(mail);
		return"redirect:mail_main_form";
//		mailservice.receiveMail(mail);
//		if(&&mailservice.sendMail(mail)) {
//			model.addAttribute("msg","보냄완료");
//		}else {
//		model.addAttribute("msg", "보냄실패");
//		}
//		model.addAttribute("url","mail_main");
//		return"mail_main";
	}
	//메일 전달
		@RequestMapping("/mail_write_relay")
		public String mailWriteRelay(@RequestParam Map<String,Object> mail,@RequestParam("receiverId")List<String> receiverId, @RequestParam(defaultValue="") List<String> carbonId, Model model,String empNo,HttpSession session,@RequestParam(value="fileName", required=false) List<MultipartFile> file) {
			System.out.println("mailWriterelay");
			int mailNo = Integer.parseInt((String) mail.get("mailNo"));
			mail.put("mailNo", mailNo);
			List<String> receiverIdList = new ArrayList<String>();
			for(String s : receiverId) {
				receiverIdList.add(s);
				System.out.println("receiverId123: " +s);
			}
			List<String> carbonIdList = new ArrayList<String>();
			if(carbonId != null) {
				for(String s : carbonId) {
					carbonIdList.add(s);
					System.out.println("carbonId1234: " +s);
				}			
			}
			mail.put("receiverIdList", receiverIdList);
			mail.put("carbonIdList", carbonIdList);
			mailservice.sendMailrelay(mail,file);
			mail.put("mailNo", mailservice.getCurrentMailno());
			mailservice.receiveMail(mail);
			mailservice.carbonMail(mail);
			return"redirect:mail_main_form";
		}
	
		
		//메일 답장
		@RequestMapping("/mail_write_reply")
		public String mailWriteReply(@RequestParam Map<String,Object> mail, Model model,String empNo,HttpSession session,@RequestParam("receiverId")List<String> receiverId, @RequestParam(defaultValue="") List<String> carbonId,@RequestParam(value="fileName", required=false) List<MultipartFile> file) {
			System.out.println("mailWriterelay");
			int mailNo = Integer.parseInt((String) mail.get("mailNo"));
			mail.put("mailNo", mailNo);
			List<String> receiverIdList = new ArrayList<String>();
			for(String s : receiverId) {
				receiverIdList.add(s);
				System.out.println("receiverId123: " +s);
			}
			List<String> carbonIdList = new ArrayList<String>();
			if(carbonId != null) {
				for(String s : carbonId) {
					carbonIdList.add(s);
					System.out.println("carbonId1234: " +s);
				}			
			}
			mail.put("receiverIdList", receiverIdList);
			mail.put("carbonIdList", carbonIdList);
			mailservice.sendMailrelay(mail,file);
			mail.put("mailNo", mailservice.getCurrentMailno());
			mailservice.receiveMail(mail);
			mailservice.carbonMail(mail);
			return"redirect:mail_main_form";
		}

	
	
	
	
	//메일 내게쓰기 구현
	@RequestMapping("/mail_myWrite")
	public String mailMyWrite(@RequestParam Map<String,Object> mail, Model model,String empNo,HttpSession session,@RequestParam(value="fileName", required=false) List<MultipartFile> file) {
		System.out.println("mailmyWrite");
	
//		String id= (String)mail.get("mailNo");
//		System.out.println("2");
//		mailservice.sendMail(mail);	
//		mail.put("mailNo", mailservice.getCurrentMailno());
		//System.out.println(empNo);
		//empservice.selectOneEmp(empNo);
//		System.out.println("3");
//		System.out.println(mail.get("mailNo"));
//		System.out.println("4");
//		String receverId=(String)mail.get("receiverId");
//		String carbonId=(String)mail.get("carbonId");
//		String[] receverList = receverId.split(",");
//		String[] carbonList = carbonId.split(",");
//		for(int i=0;i<receverList.length;i++) {
//			String recever_id = receverList[i];
//			for(int j=0;i<carbonList.length;j++) {
//				String carbon_id = carbonList[j];
//			}
//		}
//		System.out.println("널입니까?? : " + mail.get("TITLE") == null);
		System.out.println("왜 4번들어감?");
		mailservice.sendMyMail(mail,file);
		return"redirect:mail_main_form";

	}
	//임시저장
	@ResponseBody
	@RequestMapping(value ="/mail_Drafts")
	public String mailDrafts(@RequestParam Map<String,Object> mail,@RequestParam(defaultValue="")List<String> receiverId, @RequestParam(defaultValue="") List<String> carbonId, Model model,HttpSession session,@RequestParam(value="fileName", required=false) List<MultipartFile> file) {
		System.out.println("mail_Drafts");
//		System.out.println("1");
//		String id= (String)mail.get("mailNo");
//		System.out.println("2");
//		mailservice.sendMail(mail);	
//		mail.put("mailNo", mailservice.getCurrentMailno());
		//System.out.println(empNo);
		//empservice.selectOneEmp(empNo);
//		System.out.println("3");
//		System.out.println(mail.get("mailNo"));
//		System.out.println("4");
//		String receverId=(String)mail.get("receiverId");
//		String carbonId=(String)mail.get("carbonId");
//		String[] receverList = receverId.split(",");
//		String[] carbonList = carbonId.split(",");
//		for(int i=0;i<receverList.length;i++) {
//			String recever_id = receverList[i];
//			for(int j=0;i<carbonList.length;j++) {
//				String carbon_id = carbonList[j];
//			}
//		}
//		System.out.println("mail : " +mail);
//		System.out.println("임시저장1");
		boolean result=mailservice.drfatsMail(mail,file);
		List<String> receiverIdList = new ArrayList<String>();
		if(receiverId !=null) {
		for(String s : receiverId) {
			receiverIdList.add(s);
			System.out.println("receiverId123: " +s);
			}
		}
		List<String> carbonIdList = new ArrayList<String>();
		if(carbonId != null) {
			for(String s : carbonId) {
				carbonIdList.add(s);
				System.out.println("carbonId1234: " +s);
			}			
		}
		
		mail.put("receiverIdList", receiverIdList);
		mail.put("carbonIdList", carbonIdList);
		if(result) {
		mail.put("mailNo", mailservice.getCurrentMailno());
		mailservice.drfatsMailReceive(mail);
		mailservice.drfatsMailCarbon(mail);
		System.out.println("임시저장 성공!");
		}else {
		return "false";
		}
		return "true";

	}
	//임시저장 내게
	@ResponseBody
	@RequestMapping(value ="/mail_MyDrafts")
	public String mailMyDrafts(@RequestParam Map<String,Object> mail, Model model,HttpSession session,@RequestParam(value="fileName", required=false) List<MultipartFile> file) {
		System.out.println("mail_MyDrafts");

		
		System.out.println("mail : " +mail);
		System.out.println("내게 임시저장1");
		boolean result=mailservice.draftMyMail(mail,file);
		if(result) {
				System.out.println("내게 임시저장 완료");
			}else {
			return "false";
			}
			return "true";

	}
	//임시저장함에서 내게쓰기로 보내기
	@RequestMapping("/mail_UpdateMyWrite")
	public String mailUpdateMyWrite(@RequestParam Map<String,Object>mail,@RequestParam(value="fileName", required=false) List<MultipartFile> file) {
		System.out.println("mail_UpdateMyWrite");
		int mailNo = Integer.parseInt((String) mail.get("mailNo"));
		mail.put("oldmailNo", mailNo);
		mail.put("content", mail.get("content"));
		mail.put("title", mail.get("title"));
//		mailservice.updateMyWrite(mail,file);
		mailservice.draftMyMail(mail,file);
		mailservice.deleteSednMail(mailNo);
		return"redirect:mail_main_form";
	}
	
	//임시저장함 내게 쓴 메일 새로 임시저장
	@ResponseBody
	@RequestMapping(value="/mail_UpdateMyWriteDraft")
	public String mailupdateMyWriteDraft(@RequestParam Map<String,Object>mail,@RequestParam(value="fileName", required=false) List<MultipartFile> file) {
		System.out.println("mail_UpdateMyWriteDraft");
		int mailNo = Integer.parseInt((String) mail.get("mailNo"));
		mail.put("mailNo", mailNo);
		System.out.println(mailNo);
		boolean result=mailservice.updateMyWriteDraft(mail,file);
		if(result) {
			System.out.println("업데이트  임시 저장 draft_mail");
//			mailservice.updateDraftMailReceive(mail);
//			System.out.println("임시저장 업데이트 완료");
		}else {
			return "false";
		}
		return "true";
	}
	
	//임시저장함에서 메일로 보내기
	@RequestMapping("/mail_UpdateWrite")
	public String mailUpdateWrite(@RequestParam Map<String,Object> mail,@RequestParam("receiverId")List<String> receiverId, @RequestParam(defaultValue="") List<String> carbonId,@RequestParam(value="fileName", required=false) List<MultipartFile> file) {
		int mailNo = Integer.parseInt((String) mail.get("mailNo"));
		mail.put("oldmailNo", mailNo);
		mail.put("content", mail.get("content"));
		mail.put("title", mail.get("title"));
//		mailservice.updateDraftSendMail(mail, file);
		mailservice.sendMail(mail,file);

		List<String> receiverIdList = new ArrayList<String>();
		for(String s : receiverId) {
			receiverIdList.add(s);
			System.out.println("receiverId123: " +s);
		}
		
		List<String> carbonIdList = new ArrayList<String>();
		if(carbonId != null) {
			for(String s : carbonId) {
				carbonIdList.add(s);
				System.out.println("carbonId1234: " +s);
			}			
		}
		
		mail.put("receiverIdList", receiverIdList);
		mail.put("carbonIdList", carbonIdList);
		mail.put("mailNo", mailservice.getCurrentMailno());
		mailservice.receiveMail(mail);
		mailservice.carbonMail(mail);
		mailservice.deleteDraftReceive(mailNo);
		mailservice.deleteDraftCarbon(mailNo);
		mailservice.deleteSednMail(mailNo);
		return"redirect:mail_main_form";
		
	}
	
	//임시저장함 메일 새로 임시저장
		@ResponseBody
		@RequestMapping("/mail_UpdateWriteDraft")
		public String mailUpdateWriteDraft(@RequestParam Map<String,Object> mail,@RequestParam("receiverId")List<String> receiverId, @RequestParam(defaultValue="") List<String> carbonId,@RequestParam(value="fileName", required=false) List<MultipartFile> file) {
			System.out.println("mail_UpdateWriteDraft");
			int mailNo = Integer.parseInt((String) mail.get("mailNo"));
			mail.put("mailNo", mailNo);
			System.out.println("업데이트  임시 저장send_mail");
			List<String> receiverIdList = new ArrayList<String>();
			for(String s : receiverId) {
				receiverIdList.add(s);
				System.out.println("receiverId123: " +s);
			}
			
			List<String> carbonIdList = new ArrayList<String>();
			if(carbonId != null) {
				for(String s : carbonId) {
					carbonIdList.add(s);
					System.out.println("carbonId1234: " +s);
				}			
			}
			boolean result=mailservice.updateDraftMail(mail,file);
			if(result) {
				mail.put("receiverIdList", receiverIdList);
				mail.put("carbonIdList", carbonIdList);
				System.out.println("업데이트  임시 저장 draft_mail");
				mailservice.deleteDraftReceive(mailNo);
				mailservice.deleteDraftCarbon(mailNo);
				mailservice.drfatsMailReceive(mail);
				mailservice.drfatsMailCarbon(mail);
				System.out.println("임시저장 업데이트 완료");
			}else {
				return "false";
			}
			return "true";
		}
		
		//메일 전달에서 임시저장,//메일 답장에서 임시저장
		@ResponseBody
		@RequestMapping("/mail_Drafts_relay_reply")
		public String mailDraftsmyrelay(@RequestParam Map<String,Object> mail, Model model,HttpSession session,@RequestParam(value="fileName", required=false) List<MultipartFile> file, MultipartRequest a) {
			System.out.println("mail_Drafts_relay_reply");
			System.out.println("mail : " +mail);
			
			int mailNo = Integer.parseInt((String) mail.get("mailNo"));
			mail.put("mailNo", mailNo);	
//			System.out.println(file1);
//			
//			for(MultipartFile i: file) {
//				System.out.println("mailDraftsmyrelay : " + file);
//				System.out.println("MultipartFile : " + i);
//				System.out.println("MultipartFileName : " +  i.getOriginalFilename());
//		
//			}
//			System.out.println("시마이");
//			System.out.println(mail.get("mailNo"));	
//			System.out.println("내게쓴메일에서 전달에서 임시저장1");
//			
//			
			boolean result=mailservice.drfatsMailrelayreply(mail,file);
//		
			if(result) {
				System.out.println("내게쓴메일에서 전달에서 임시저장2");
				mail.put("mailNo", mailservice.getCurrentMailno());
				mailservice.drfatsMailReceive(mail);
				System.out.println("내게쓴메일에서 전달에서 임시저장3");
			}else {
				return "false";
			}
			return "true";
		}
		
		
		//보낸편지함에서 메일 삭제
		@ResponseBody
		@RequestMapping("/mail_delete_sendInbox")
		public String deleteMailSendInbox(@RequestParam Map<String,Object> mail, Model model,HttpSession session) {
			System.out.println("mail_delete_sendInbox");
			boolean result= mailservice.deleteMailSendInbox(mail);
			if(result) {
				System.out.println("보낸편지함에서 메일 삭제");
			}else {
			return "false";
			}
			return "true";
		}
		
		//보낸편지함에서 메일 상세보기에서 삭제
		@RequestMapping("/mail_delete_sendInbox_view")
		public String deleteMailSendInboxView(@RequestParam Map<String,Object> mail, Model model,HttpSession session) {
		System.out.println("mail_Drafts_all");
		System.out.println("mail : " +mail);
		mail.get("mailNo");
		System.out.println(mail.get("mailNo")); 
		System.out.println("보낸편지함  상세보기 삭제1");		
		mailservice.deleteMailSendInbox(mail);
		return"redirect:mail_main_form";
		}	
		
		
		//받은편지함에서 메일 삭제
		@ResponseBody
		@RequestMapping("/mail_delete_receiveInbox")
		public String deleteMailReceiveInbox(@RequestParam String mailNo, Model model,HttpSession session) {
			System.out.println("mail_delete_receiveInbox");
			System.out.println(mailNo);
		
			
			Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
			
			String []sub_mailNo = mailNo.split("-");
			System.out.println(sub_mailNo[0]);
			System.out.println(sub_mailNo[1]);
			
			if(sub_mailNo[1].equals("r")) {
				//받은메일테이블에서 업데이트
				mailservice.deleteMailReceiveInboxReceiver(sub_mailNo[0]);
			}else {
				//참조테이블에서 업데이트
				mailservice.deleteMailReceiveInboxCarbon(sub_mailNo[0]);
			}

			return "true";

		}
		
			//받은편지함에서 메일 상세보기에서 삭제
			@RequestMapping("/mail_delete_receiveInbox_view")
			public String deleteMailReceiveInboxView(@RequestParam Map<String,Object> mail, Model model,HttpSession session,String[] recvId, String[] carbonId) {
			System.out.println("mail_delete_receiveInbox_view");
			System.out.println("mail : " +mail);
			mail.get("mailNo");
			System.out.println("받은이들 : "+recvId[0]);
			System.out.println("참조인들 : "+carbonId[0]);
			
			Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
			String empNo = (String) user.get("empNo");
			System.out.println("받은편지함 상세보기 삭제1");
			System.out.println(mail.get("mailNo")); 
			System.out.println("받은편지함 상세보기 삭제2");
			
			for(int i = 0; i <recvId.length; i++) {
				if(empNo.equals(recvId[i])) {
					// empNo랑 받은이가 일치할때 들어옴
					mailservice.deleteMailReceiveInboxReceiverView(mail);
					break;
				}
			}
			for(int i = 0; i <carbonId.length; i++) {
				if(empNo.equals(carbonId[i])) {
					mailservice.deleteMailReceiveInboxCarbonView(mail);
					break;
				}
			}
			return"redirect:mail_main_form";
			}
		//내게쓴편지함 메일 삭제
		@ResponseBody
		@RequestMapping("/mail_delete_MyMailInbox")
		public String deleteMyMailInbox(@RequestParam Map<String,Object>mail, Model model,HttpSession session) {
			System.out.println("mail_delete_MyMailInbox");
			System.out.println(mail);
			boolean result= mailservice.deleteMyMailInbox(mail);
			if(result) {
				System.out.println("내게쓴편지함에서 메일 삭제");
			}else {
			return "false";
			}
			return "true";

		}
		//내게쓴편지함 상세보기에서 삭제
		@RequestMapping("/mail_delete_MyWrite_view")
		public String deleteMyMailInboxView(@RequestParam Map<String,Object>mail, Model model,HttpSession session) {
			System.out.println("mail_delete_MyMailInbox");
			System.out.println(mail);
			mail.get("mailNo");
			System.out.println(mail.get("mailNo")); 
			mailservice.deleteMyMailInbox(mail);
			System.out.println("내게쓴편지함 상세보기 삭제1");	
			return"redirect:mail_main_form";

		}
		
		//임시 저장함에서 휴지통 보내기
		@ResponseBody
		@RequestMapping("/mail_delete_draftInbox")
		public String deleteDraftMailInbox(@RequestParam String mailNo, Model model,HttpSession session) {
			System.out.println("mail_delete_draftInbox");
			System.out.println(mailNo);
			System.out.println("임시저장함 휴지통 보내기");		
			String []draft_mailNo = mailNo.split("-");
			System.out.println(draft_mailNo[0]);
			System.out.println(draft_mailNo[1]);
					
			if(draft_mailNo[1].equals("m")) {
				//받은메일테이블에서 업데이트
				System.out.println();
				mailservice.deleteDraftInboxMail(draft_mailNo[0]);
			}else {
				//참조테이블에서 업데이트
				mailservice.deleteDraftInboxMailSender(draft_mailNo[0]);
				mailservice.deleteDraftInboxMailReceive(draft_mailNo[0]);
				mailservice.deleteDraftInboxMailCarbon(draft_mailNo[0]);
				}

				return "true";

			}
		
		//휴지통 비우기
		@ResponseBody
		@RequestMapping("/mail_delete_deleteInbox")
		public String emptyDraftMailInbox(@RequestParam String mailNo, Model model,HttpSession session) {
			System.out.println("mail_delete_deleteInbox");
			System.out.println(mailNo);
			System.out.println("휴지통에서 메일 비우기");		
			String []delete_mailNo = mailNo.split("-");
			System.out.println(delete_mailNo[0]);
			System.out.println(delete_mailNo[1]);
							
			if(delete_mailNo[1].equals("my")) {
				//내게쓴 편지함 비우기
				System.out.println("내게쓴편지함 비우기");
				mailservice.emptyMyMailInbox(delete_mailNo[0]);
				}
			
				else if(delete_mailNo[1].equals("sd")){
				//보낸 편지함 비우기
				System.out.println("보낸편지함 비우기");
				mailservice.emptyMailSendInbox(delete_mailNo[0]);
				}
				
				else if(delete_mailNo[1].equals("dr")) {
				//임시저장함 비우기
				System.out.println("임시저장함 비우기");	
				mailservice.emptyDraftInboxMailSender(delete_mailNo[0]);
				mailservice.emptyDraftInboxMailReceive(delete_mailNo[0]);
				mailservice.emptyDraftInboxMailCarbon(delete_mailNo[0]);
				}
				
				else if(delete_mailNo[1].equals("dm")) {
				//임시저장함(내게) 비우기
				System.out.println("임시저장함(내게) 비우기");
				mailservice.emptyDraftInboxMail(delete_mailNo[0]);
				}
				
				else if(delete_mailNo[1].equals("rr")) {
				//받은편지함(받은메일) 비우기
				System.out.println("받은편지함(받은메일) 비우기");
				mailservice.emptyMailReceiveInboxReceiver(delete_mailNo[0]);
				}
				
				else if(delete_mailNo[1].equals("rc")) {
				//받은편지함(참조메일) 비우기
				System.out.println("받은편지함(참조메일) 비우기");	
				mailservice.emptyMailReceiveInboxCarbon(delete_mailNo[0]);
				}
				return "true";
				}
		
		
		
		//보낸 편지함에서 메일 상세보기에서 비우기
		@RequestMapping("/mail_empty_sendmail_view")
		public String mailEmptySendMailView(@RequestParam Map<String,Object> mail, Model model,HttpSession session) {
		System.out.println("mail_empty_sendmail_view");
		System.out.println("mail : " +mail);
		mail.get("mailNo");
		System.out.println(mail.get("mailNo")); 
		System.out.println("보낸 편지함에서 메일 상세보기에서 비우기");		
		mailservice.emptyMailSendInboxView(mail);
		return"redirect:mail_main_form";
		}	
	
		//받은 편지함에서 메일 상세보기에서 비우기
		@RequestMapping("/mail_empty_receivemail_view")
		public String mailEmptyReceiveMailView(@RequestParam Map<String,Object> mail, Model model,HttpSession session) {
		System.out.println("mail_empty_receivemail_view");
		System.out.println("mail : " +mail);
		mail.get("mailNo");
		System.out.println(mail.get("mailNo")); 
		System.out.println("받은 편지함에서 메일 상세보기에서 비우기");		
		mailservice.emptyMailReceiveInboxReceiverView(mail);
		return"redirect:mail_main_form";
		}	
		
		//받은 편지함(참조인)에서 메일 상세보기에서 비우기
		@RequestMapping("/mail_empty_carbonmail_view")
		public String mailEmptyCarbonMailView(@RequestParam Map<String,Object> mail, Model model,HttpSession session) {
		System.out.println("mail_empty_carbonmail_view");
		System.out.println("mail : " +mail);
		mail.get("mailNo");
		System.out.println(mail.get("mailNo")); 
		System.out.println("받은 편지함(참조인)에서 메일 상세보기에서 비우기");		
		mailservice.emptyMailReceiveInboxCarbonView(mail);
		return"redirect:mail_main_form";
		}	
		
		//내게쓴 편지함에서 메일 상세보기에서 비우기
		@RequestMapping("/mail_empty_mymail_view")
		public String mailEmptyMyMailView(@RequestParam Map<String,Object> mail, Model model,HttpSession session) {
		System.out.println("mail_empty_mymail_view");
		System.out.println("mail : " +mail);
		mail.get("mailNo");
		System.out.println(mail.get("mailNo")); 
		System.out.println("보낸편지함에서 메일 상세보기에서 삭제");		
		mailservice.emptyMyMailInboxView(mail);
		return"redirect:mail_main_form";
		}	
		
		//내게 임시편지함에서 메일 상세보기에서 비우기
		@RequestMapping("/mail_empty_mydraft_view")
		public String mailEmptyMyDraftView(@RequestParam Map<String,Object> mail, Model model,HttpSession session) {
		System.out.println("mail_empty_mydraft_view");
		System.out.println("mail : " +mail);
		mail.get("mailNo");
		System.out.println(mail.get("mailNo")); 
		System.out.println("내게 임시편지함에서 메일 상세보기에서 비우기");		
		mailservice.emptyDraftInboxMailView(mail);
		return"redirect:mail_main_form";
		}		
		
		//임시편지함에서 메일 상세보기에서 비우기
		@RequestMapping("/mail_empty_draft_view")
		public String mailEmptyDraftView(@RequestParam Map<String,Object> mail, Model model,HttpSession session) {
		System.out.println("mail_empty_draft_view");
		System.out.println("mail : " +mail);
		mail.get("mailNo");
		System.out.println(mail.get("mailNo")); 
		System.out.println("내게 임시편지함에서 메일 상세보기에서 비우기");		
		mailservice.emptyDraftInboxMailSenderView(mail);
		mailservice.emptyDraftInboxMailDraftReceiveView(mail);
		mailservice.emptyDraftInboxMailDraftCarbonView(mail);
		return"redirect:mail_main_form";
		}		
		
		//메일 회수
		@ResponseBody
		@RequestMapping("/mail_return_mail")
		public String returnMailInbox(@RequestParam Map<String,Object>mail, Model model) {
			System.out.println("mail_return_mail");
			System.out.println(mail);
			System.out.println("메일 회수1");
			mail.get("mailNo");
			System.out.println(mail.get("mailNo"));
			System.out.println("메일 회수2");
			int mailNo = Integer.parseInt((String) mail.get("mailNo"));
			System.out.println("메일 회수3");
			System.out.println(mailNo);
			
			boolean result= mailservice.returnMailCarbon(mailNo);
			if(result) {
					System.out.println("메일회수4");
					mailservice.returnMailReceive(mailNo);
					mailservice.returnMailSend(mailNo);
			}else {
			return "false";
			}
			return "true";
		}
		
	//  /download
		@RequestMapping("/sendInboxdownload")
		public View download(@RequestParam Map<String, Object>params) {
			//우리가 반환할 View를 만들어서 
			//객체를 반환하면 됨
			System.out.println("download : "+params);
			System.out.println("download : "+mailservice.getAttachFile(params));
			File file = (File) mailservice.getAttachFile(params);
			System.out.println("파일이름!!!!!!!!");
			View view = new DownloadView(file);
			System.out.println("view : "+view);
			return view;
		}	
		
	}

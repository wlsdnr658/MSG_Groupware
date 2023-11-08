package controller;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import service.EmpService;
import service.MailService;



@Controller
@RequestMapping("/mail")
public class MailViewController { 
	
	//메일 서비스
	@Autowired
	MailService mailService;
	@Autowired
	EmpService empService;
	
	
	//----------------mail-------------------------

	//전자메일 메인화면으로 이동	
	@RequestMapping("/mail_main_form")	
	public String mailMainForm(Model model,HttpSession session){
		System.out.println("mail_main_form");
		System.out.println(session.getAttribute("user"));
		Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
		String empNo = (String) user.get("empNo");
		System.out.println("receiveinbox "+mailService.receiveInboxMain(empNo));
		System.out.println("sendinbox "+mailService.sendInboxMain(empNo));
		model.addAttribute("receiveinbox", mailService.receiveInboxMain(empNo));
		model.addAttribute("sendinbox", mailService.sendInboxMain(empNo));
		return"mail_main";
	}	
	//받은편지함으로 이동	
	@RequestMapping("/mail_receiveInbox_form")	
	public String mailReceiveForm(Model model,HttpSession session,
			@RequestParam(required = false)String keyword,
			@RequestParam(defaultValue = "0")int type,
			@RequestParam(defaultValue = "1")int page){	
		System.out.println("mail_receiveInbox");
		Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("keyword", keyword);
		param.put("type", type);
		param.put("empNo", user.get("empNo"));
		System.out.println(keyword);
		System.out.println(type);
		Map<String, Object> viewData = mailService.receiveInbox(param,page);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		System.out.println(viewData);
		model.addAttribute("viewData", viewData);
		return"mail_receiveInbox";
	}	
	
	//받은편지함 상세보기로 이동	
	@RequestMapping(value="/mail_receiveInbox_view_form")	
	public String mailReceiveViewForm(Model model, int mailNo,HttpSession session){	
		System.out.println("mail_receiveInbox_view_form");
		Map<String,Object> InboxViewSend = mailService.InboxViewSend(mailNo);
		int mailNor = (int) InboxViewSend.get("mailNo");
		
		Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
		String empNo = (String) user.get("empNo");
		
		Map<String, Object> mail = new HashMap<String, Object>();
		mail.put("empNo", empNo);
		mail.put("mailNo", mailNo);
		System.out.println(mail);
		if(mailService.updateReadmailReceiver(mail)==0) {	
		}else {
			mailService.updateReadmailCarbon(mail);
		}
		System.out.println(mailNo);
		List<Map<String,Object>> receiveInboxViewReceive = mailService.receiveInboxViewReceive(mailNor);
		System.out.println(receiveInboxViewReceive);
		List<Map<String,Object>> receiveInboxViewCarbon = mailService.receiveInboxViewCarbon(mailNor);
		System.out.println(receiveInboxViewCarbon);
		
	   	
		
		
		model.addAttribute("InboxViewSend", InboxViewSend);
		model.addAttribute("receiveInboxViewReceive", receiveInboxViewReceive);
		model.addAttribute("receiveInboxViewCarbon", receiveInboxViewCarbon);
		return"mail_receiveInbox_view";
	}	
	//보낸편지함으로 이동	
	@RequestMapping("/mail_sendInbox_form")	
	public String mailSendForm(Model model, HttpSession session,
		@RequestParam(required = false)String keyword,
		@RequestParam(defaultValue = "0")int type,
		@RequestParam(defaultValue = "1")int page){	
	System.out.println("mail_sendInbox");
	Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
	Map<String, Object> param = new HashMap<String,Object>();
	param.put("keyword", keyword);
	param.put("type", type);
	param.put("empNo", user.get("empNo"));
	System.out.println(keyword);
	System.out.println(type);
	Map<String, Object> viewData = mailService.sendInbox(param, page);
	viewData.put("type", type);
	viewData.put("keyword", keyword);
	System.out.println(viewData);
	model.addAttribute("viewData", viewData);
//	System.out.println("mail_sendInbox_form");
//	Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
//	String empNo = (String) user.get("empNo");
//	model.addAttribute("sendinbox", mailService.sendInbox(empNo));	
		return"mail_sendInbox";
	}	
	//보내편지함 상세보기로 이동	
	@RequestMapping("/mail_sendInbox_view_form")	
	public String mailSendviewForm(Model model, int mailNo,HttpSession session) throws IOException{	
		System.out.println("mail_sendInbox_view_form");
		System.out.println("보낸편지함 상세보기 mailNo :"+mailNo);
		Map<String,Object> InboxViewSend = mailService.InboxViewSend(mailNo);
		int mailNos = (int) InboxViewSend.get("mailNo");
		System.out.println("보낸편지함 상세보기 InboxViewSend :"+InboxViewSend);
		System.out.println(mailNo);
		List<Map<String,Object>> sendInboxViewReceive = mailService.sendInboxViewReceive(mailNos);
		System.out.println(sendInboxViewReceive);
		List<Map<String,Object>> sendInboxViewCarbon = mailService.sendInboxViewCarbon(mailNos);
		System.out.println(sendInboxViewCarbon);
//		List<Map<String,Object>> uploadFromMail = mailService.uploadFromMailList(mailNos);
//		System.out.println("보낸메일상세보기4");
//		System.out.println(uploadFromMail);
//		List<String> fileName = new ArrayList<>();
//		List<Integer> fileNo = new ArrayList<>();
//		for(Map<String, Object> tmp : uploadFromMail) {
//			fileName.add(tmp.get("fileName").toString());
//			fileNo.add(Integer.parseInt(tmp.get("fileNo").toString()));
//		}
//		Gson gson = new GsonBuilder().create();
//	    JsonArray uploadFromMail1 = gson.toJsonTree(uploadFromMail).getAsJsonArray();
		model.addAttribute("InboxViewSend", InboxViewSend);
		model.addAttribute("sendInboxViewReceive", sendInboxViewReceive);
		model.addAttribute("sendInboxViewCarbon", sendInboxViewCarbon);
//		model.addAttribute("uploadFromMail", uploadFromMail);
//		model.addAttribute("fileName", fileName);
//		model.addAttribute("fileNo", fileNo);
//		System.out.println("보낸메일상세보기5");
//		System.out.println(uploadFromMail1);
		
		return"mail_sendInbox_view";
	}	
	//
	@ResponseBody
	@RequestMapping(value="/mailsendInboxviewformDown/{mailNo}")
	public ResponseEntity<List<Map<String, Object>>> fileListOfNotice(@PathVariable("mailNo")int mailNo) {
	      
	
	      
	   ResponseEntity<List<Map<String, Object>>> entity = null;
	    try {
	         List<Map<String, Object>> fileList = mailService.uploadFromMailList(mailNo);
	         
	         System.out.println("파일리스트 : "+fileList);
	         
	         entity = new ResponseEntity<List<Map<String, Object>>>(fileList,HttpStatus.OK);
	      }catch (Exception e) {
	         entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	      }
	      
	      return entity;
	   }
	
	
	//내게쓰기함으로 이동	
	@RequestMapping("/mail_myWriteInbox_form")	
	public String mailmyWriteInboxForm(Model model, HttpSession session,
			@RequestParam(required = false)String keyword,
			@RequestParam(defaultValue = "0")int type,
			@RequestParam(defaultValue = "1")int page){	
		System.out.println("mail_mywriteInbox_form");
		Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("keyword", keyword);
		param.put("type", type);
		param.put("empNo", user.get("empNo"));
		System.out.println(keyword);
		System.out.println(type);
		Map<String, Object> viewData = mailService.MySenderInbox(param, page);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		System.out.println(viewData);
		model.addAttribute("viewData", viewData);
		return"mail_myWriteInbox";
	}	
	//내게쓰기 상세보기로 이동	
	@RequestMapping("/mail_myWriteInbox_view_form")	
	public String mailmyWriteInboxViewForm(Model model,int mailNo){	
		System.out.println("mail_myWriteInbox_view_form");
		Map<String,Object> myWrtieInbox = mailService.mysendInboxView(mailNo);
		System.out.println(myWrtieInbox);
		model.addAttribute("myWriteinboxView", myWrtieInbox);	
		return"mail_myWriteInbox_view";
	}	
	//임시보관함으로 이동	
	@RequestMapping("/mail_draftsInbox_form")	
	public String maildraftsInboxForm(Model model,HttpSession session,
			@RequestParam(required = false)String keyword,
			@RequestParam(defaultValue = "0")int type,
			@RequestParam(defaultValue = "1")int page){	
		System.out.println("mail_mywriteInbox_form");
		Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("keyword", keyword);
		param.put("type", type);
		param.put("empNo", user.get("empNo"));
		System.out.println(keyword);
		System.out.println(type);
		Map<String, Object> viewData = mailService.draftInbox(param, page);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		System.out.println(viewData);
		model.addAttribute("viewData", viewData);
		return"mail_draftsInbox";
	}	
	//임시보관함 상세보기
	@RequestMapping("/mail_draftsInbox_view_form")	
	public String maildraftsInboxviewform(Model model, int mailNo){	
		Map<String,Object> draftsInboxView = mailService.draftsInboxView(mailNo);
		int mailNod = (int) draftsInboxView.get("mailNo");
		List<Map<String,Object>> draftsInboxViewReceive = mailService.draftsInboxViewReceive(mailNod);
		System.out.println("받은 : "+draftsInboxViewReceive);
		List<Map<String,Object>> draftsInboxViewCarbon = mailService.draftsInboxViewCarbon(mailNod);
		System.out.println("보낸 : "+draftsInboxViewCarbon);
		if(draftsInboxViewReceive.size()>0) {
			model.addAttribute("draftsInboxViewReceive", draftsInboxViewReceive);
		}
		if(draftsInboxViewCarbon.size()>0) {
			model.addAttribute("draftsInboxViewCarbon", draftsInboxViewCarbon);
		}
		model.addAttribute("draftsInboxView", draftsInboxView);
		return"mail_draftsInbox_view";
	}
	
	//임시보관함 타겟 가져오기
		@ResponseBody
		@RequestMapping("/mail_draftsInbox_target")	
		public Map<String, Object> mailDraftsInboxTarget(Model model, int mailNo){	
			System.out.println("mail_draftsInbox_view_form");
			System.out.println(mailNo);
			List<Map<String,Object>> draftsInboxViewReceive = mailService.draftsInboxViewReceive(mailNo);
			System.out.println("받은 : "+draftsInboxViewReceive);
			List<Map<String,Object>> draftsInboxViewCarbon = mailService.draftsInboxViewCarbon(mailNo);
			System.out.println("보낸 : "+draftsInboxViewCarbon);
			
//			List<String> draftsInboxViewReceive = new ArrayList<String>();
//			draftsInboxViewReceive.add("list1");
//			draftsInboxViewReceive.add("list2");
//			draftsInboxViewReceive.add("list3");
//			draftsInboxViewReceive.add("list4");
//			draftsInboxViewReceive.add("list5");
//			
//			List<String> draftsInboxViewCarbon = new ArrayList<String>();
//			draftsInboxViewCarbon.add("list11");
//			draftsInboxViewCarbon.add("list22");
//			draftsInboxViewCarbon.add("list33");
//			draftsInboxViewCarbon.add("list44");
//			draftsInboxViewCarbon.add("list55");
			
			Gson gson = new Gson();
			Map<String, Object> result = new HashMap<String, Object>();
			if(draftsInboxViewReceive.size()>0) {
				result.put("receiverList", gson.toJson(draftsInboxViewReceive));
			}
			if(draftsInboxViewCarbon.size()>0) {
				result.put("carbonList",  gson.toJson(draftsInboxViewCarbon));
			}
			System.out.println("결과 : "+result);
			return result;
		}
	
	//임시보관함 내게 상세보기
		@RequestMapping("/mail_mydraftsInbox_view_form")	
		public String mailmydraftsInboxviewform(Model model, int mailNo){	
			System.out.println("mail_draftsInbox_view_form");
			Map<String,Object> draftsMyInboxView = mailService.draftsInboxView(mailNo);
			System.out.println(draftsMyInboxView);
			model.addAttribute("draftsMyInboxView", draftsMyInboxView);
			return"mail_myDraftInbox_view";
		}	
	
	
	//메일쓰기로 이동	
	@RequestMapping("/mail_Write_form")	
	public String mailWriteForm(){	
		System.out.println("mail_Write_form");
		return"mail_write";
	}	
	//내게쓰기로 이동	
	@RequestMapping("/mail_myWrite_form")	
	public String mailMyWriteForm(){	
		System.out.println("mail_myWrite_form");
		return"mail_myWrite";
	}	
	//휴지통으로 이동	
	@RequestMapping("/mail_deleteInbox_form")	
	public String maildeleteInboxform(Model model,HttpSession session,
			@RequestParam(required = false)String keyword,
			@RequestParam(defaultValue = "0")int type,
			@RequestParam(defaultValue = "1")int page)	{	
		System.out.println("mail_deleteInbox_form");
		Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("keyword", keyword);
		param.put("type", type);
		param.put("empNo", user.get("empNo"));
		
		Map<String, Object> viewData = mailService.deleteInbox(param, page);
		viewData.put("type", type);
		viewData.put("keyword", keyword);

		model.addAttribute("viewData", viewData);
//		Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
//		String empNo = (String) user.get("empNo");
//		model.addAttribute("deleteInbox", mailService.deleteInbox(empNo));
//		System.out.println(mailService.deleteInbox(empNo));
		return"mail_deleteInbox";
	}	
	
	//보낸 편지함 휴지통 상세보기로 이동	
	@RequestMapping("/mail_deleteInbox_view_form")	
	public String mailEmptySendMailView(Model model, int mailNo){	
		System.out.println("mail_deleteInbox_view_form");
		Map<String,Object> deleteInboxViewSend = mailService.deleteInboxViewSend(mailNo);
		System.out.println(deleteInboxViewSend);
		int mailNodl = (int) deleteInboxViewSend.get("mailNo");
	
	
		List<Map<String,Object>> deleteInboxViewReceive = mailService.deleteInboxViewReceive(mailNodl);
		
		List<Map<String,Object>> deleteInboxViewCarbon = mailService.deleteInboxViewCarbon(mailNodl);
	
		
		model.addAttribute("deleteInboxViewSend", deleteInboxViewSend);
		model.addAttribute("deleteInboxViewReceive", deleteInboxViewReceive);
		model.addAttribute("deleteInboxViewCarbon", deleteInboxViewCarbon);
		return"mail_deleteInbox_view";
	}
	
	//받은편지함 휴지통 상세보기로 이동	
		@RequestMapping("/mail_deleteInboxReceivemail_view_form")	
		public String maildeleteInboxReceivemailviewform(Model model, int mailNo){	
			System.out.println("mail_deleteInbox_view_form");
			Map<String,Object> deleteInboxViewSend = mailService.deleteInboxViewSend(mailNo);
			System.out.println(deleteInboxViewSend);
			int mailNodl = (int) deleteInboxViewSend.get("mailNo");
			System.out.println("받은편지함 휴지통 상세보기1");
			System.out.println(mailNodl);
			List<Map<String,Object>> deleteInboxViewReceive = mailService.deleteInboxViewReceive(mailNodl);
			System.out.println("받은편지함 휴지통 상세보기2");
			System.out.println(deleteInboxViewReceive);
			List<Map<String,Object>> deleteInboxViewCarbon = mailService.deleteInboxViewCarbon(mailNodl);
			System.out.println("받은편지함 휴지통 상세보기3");
			System.out.println(deleteInboxViewCarbon);
			model.addAttribute("deleteInboxViewSend", deleteInboxViewSend);
			model.addAttribute("deleteInboxViewReceive", deleteInboxViewReceive);
			model.addAttribute("deleteInboxViewCarbon", deleteInboxViewCarbon);
			return"mail_deleteInboxReceive_view";
		}
	
		//받은편지함(참조)휴지통 상세보기로 이동	
		@RequestMapping("/mail_deleteInboxCarbonmail_view_form")	
		public String maildeleteInboxCarbonmailviewform(Model model, int mailNo){	
			System.out.println("mail_deleteInbox_view_form");
			Map<String,Object> deleteInboxViewSend = mailService.deleteInboxViewSend(mailNo);
			System.out.println(deleteInboxViewSend);
			int mailNodl = (int) deleteInboxViewSend.get("mailNo");
			System.out.println("받은편지함(참조)휴지통1");
			System.out.println(mailNodl);
			List<Map<String,Object>> deleteInboxViewReceive = mailService.deleteInboxViewReceive(mailNodl);
			System.out.println("받은편지함(참조)휴지통2");
			System.out.println(deleteInboxViewReceive);
			List<Map<String,Object>> deleteInboxViewCarbon = mailService.deleteInboxViewCarbon(mailNodl);
			System.out.println("받은편지함(참조)휴지통3");
			System.out.println(deleteInboxViewCarbon);
			model.addAttribute("deleteInboxViewSend", deleteInboxViewSend);
			model.addAttribute("deleteInboxViewReceive", deleteInboxViewReceive);
			model.addAttribute("deleteInboxViewCarbon", deleteInboxViewCarbon);
			return"mail_deleteInboxCarbon_view";
		}
	

	//휴지통 내게쓴 메일 상세보기로 이동	
	@RequestMapping("/mail_deleteInboxMymail_view_form")	
	public String maildeleteInboxMymailviewform(Model model, int mailNo){	
		System.out.println("mail_deleteInbox_view_form");
		Map<String,Object> deleteInboxViewSend = mailService.deleteInboxViewSend(mailNo);
		System.out.println(deleteInboxViewSend);
		int mailNodl = (int) deleteInboxViewSend.get("mailNo");
		System.out.println("휴지통 내게쓴 메일 상세보기1");
		System.out.println(mailNodl);
		model.addAttribute("deleteInboxViewSend", deleteInboxViewSend);
		return"mail_deleteInboxMymail_view";
	}
	
	
	//휴지통 내게임시저장상세보기로 이동	
	@RequestMapping("/mail_deleteInboxMyDraft_view_form")	
	public String maildeleteInboxviewMyDraftform(Model model, int mailNo){	
			System.out.println("mail_deleteInbox_view_form");
			Map<String,Object> deleteInboxViewSend = mailService.deleteInboxViewSend(mailNo);
			System.out.println(deleteInboxViewSend);
			int mailNodl = (int) deleteInboxViewSend.get("mailNo");
			System.out.println("휴지통 내게임시저장 상세보기1");
			System.out.println(mailNodl);
			model.addAttribute("deleteInboxViewSend", deleteInboxViewSend);
			return"mail_deleteInboxMyDraft_view";
			}	
	
	//휴지통 임시저장상세보기로 이동	
		@RequestMapping("/mail_deleteInboxDraft_view_form")	
		public String maildeleteInboxviewDraftform(Model model, int mailNo){	
			System.out.println("mail_deleteInbox_view_form");
			Map<String,Object> deleteInboxViewSend = mailService.deleteInboxViewSend(mailNo);
			System.out.println(deleteInboxViewSend);
			int mailNodl = (int) deleteInboxViewSend.get("mailNo");
			System.out.println(mailNodl);
			List<Map<String,Object>> deleteInboxViewDraftReceive = mailService.DeleteInboxViewDraftReceive(mailNodl);
			List<Map<String,Object>> deleteInboxViewDraftCarbon = mailService.DeleteInboxViewDraftCarbon(mailNodl);
			model.addAttribute("deleteInboxViewSend", deleteInboxViewSend);
			model.addAttribute("deleteInboxViewDraftReceive", deleteInboxViewDraftReceive);
			model.addAttribute("deleteInboxViewDraftCarbon", deleteInboxViewDraftCarbon);
			return"mail_deleteInboxDraft_view";
		}
		
	
	//비우기 버튼 누를시 팝업창	
	@RequestMapping("/mail_delete_notice_form")	
	public String maildeletenoticeform(HttpSession session,Model model){	
		System.out.println("mail_delete_notice_form");
		Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
		String empNo = (String) user.get("empNo");
//		model.addAttribute("deleteInbox", mailService.deleteInbox(empNo));
//		System.out.println(mailService.deleteInbox(empNo));
		return"delete_notice";
	}
	
	//메일 전달
		@RequestMapping("/mail_relay_form")	
		public String mailrelayform(Model model, int mailNo,Map<String,Object>mail){	
			System.out.println("mail_relay_form");
			Map<String,Object> InboxViewSend = mailService.InboxViewSend(mailNo);
			int mailNos = (int) InboxViewSend.get("mailNo");
			
			List<Map<String,Object>> sendInboxViewReceive = mailService.sendInboxViewReceive(mailNos);
		
			List<Map<String,Object>> sendInboxViewCarbon = mailService.sendInboxViewCarbon(mailNos);
		
			System.out.println(sendInboxViewCarbon);
			List<Map<String,Object>> uploadFromMail = mailService.uploadFromMailList(mailNos);
			
			
			
			System.out.println(uploadFromMail);
			model.addAttribute("InboxViewSend", InboxViewSend);
			model.addAttribute("sendInboxViewReceive", sendInboxViewReceive);
			model.addAttribute("sendInboxViewCarbon", sendInboxViewCarbon);
			model.addAttribute("uploadFromMail", uploadFromMail);

			return"mail_relay";
		}
	
	//내게쓴메일전달	
	@RequestMapping("/mail_myrelay_form")	
	public String mailmyrelayform(Model model,int mailNo){	
		System.out.println("mail_myrelay_form");
		Map<String,Object> myWrtieInbox = mailService.mysendInboxView(mailNo);
		int mailNore=(int) myWrtieInbox.get("mailNo");
		System.out.println(mailNo);
		System.out.println("내게쓴메일전달1");
		Map<String,Object> myWriteinboxView = mailService.mysendInboxView(mailNore);
		System.out.println(myWrtieInbox);
		model.addAttribute("myWriteinboxView", myWriteinboxView);
		return"mail_myrelay";
	}
	
	//받은 메일답장	
		@RequestMapping("/mail_reply_form_receive")	
		public String mailreplyform(Model model, int mailNo){	
			System.out.println("mail_reply_form");
			Map<String,Object> InboxViewSend = mailService.InboxViewSend(mailNo);
			int mailNor = (int) InboxViewSend.get("mailNo");
			System.out.println("받은메일답장상세보기1");
			System.out.println(mailNo);
			List<Map<String,Object>> receiveInboxViewReceive = mailService.receiveInboxViewReceive(mailNor);
			System.out.println("받은메일답장상세보기2");
			System.out.println(receiveInboxViewReceive);
			List<Map<String,Object>> receiveInboxViewCarbon = mailService.receiveInboxViewCarbon(mailNor);
			System.out.println("받은메일답장상세보기3");
			System.out.println(receiveInboxViewCarbon);
			System.out.println("받은메일답장상세보기4");
			model.addAttribute("InboxViewSend", InboxViewSend);
			model.addAttribute("receiveInboxViewReceive", receiveInboxViewReceive);
			model.addAttribute("receiveInboxViewCarbon", receiveInboxViewCarbon);
			return"mail_reply_receive";
		}
		
		//받은 메일답장 타겟
		@ResponseBody
		@RequestMapping("/mail_reply_receive_target")	
		public Map<String,Object> mailreplyreceivetarget(Model model, int mailNo){	
			System.out.println("mail_reply_receive_target");
//			Map<String,Object> InboxViewSend = mailService.InboxViewSend(mailNo);
//			int mailNor = (int) InboxViewSend.get("mailNo");
			System.out.println("받은 메일 답장 타겟");
			System.out.println(mailNo);
			List<Map<String,Object>> receiveInboxViewReceive = mailService.receiveInboxViewReceive(mailNo);
			List<Map<String,Object>> receiveInboxViewCarbon = mailService.receiveInboxViewCarbon(mailNo);
				
			Gson gson = new Gson();
			Map<String, Object> result = new HashMap<String, Object>();
			if(receiveInboxViewReceive.size()>0) {
				result.put("receiverList", gson.toJson(receiveInboxViewReceive));
				}
				if(receiveInboxViewCarbon.size()>0) {
				result.put("carbonList",  gson.toJson(receiveInboxViewCarbon));
				}
				System.out.println("결과 : "+result);
				return result;
			}
	
	//보낸 메일답장	
		@RequestMapping("/mail_reply_form_send")	
		public String sendmailreplyform(Model model, int mailNo){	
			System.out.println("mail_reply_form");
			Map<String,Object> InboxViewSend = mailService.InboxViewSend(mailNo);
			int mailNos = (int) InboxViewSend.get("mailNo");
			System.out.println("보낸메일답장상세보기1");
			System.out.println(mailNo);
			List<Map<String,Object>> sendInboxViewReceive = mailService.sendInboxViewReceive(mailNos);
			System.out.println("보낸메일답장상세보기2");
			System.out.println(sendInboxViewReceive);
			List<Map<String,Object>> sendInboxViewCarbon = mailService.sendInboxViewCarbon(mailNos);
			System.out.println("보낸메일답장상세보기3");
			System.out.println(sendInboxViewCarbon);
			System.out.println("보낸메일답장상세보기4");
			model.addAttribute("InboxViewSend", InboxViewSend);
			model.addAttribute("sendInboxViewReceive", sendInboxViewReceive);
			model.addAttribute("sendInboxViewCarbon", sendInboxViewCarbon);	
			return"mail_reply_send";
		}
	
		//보낸 메일답장 타겟
		@ResponseBody
		@RequestMapping("/mail_reply_target_send")	
		public Map<String,Object> mailreplytargetsend(Model model, int mailNo){	
			System.out.println("mail_reply_target_send");
//			Map<String,Object> InboxViewSend = mailService.InboxViewSend(mailNo);
//			int mailNor = (int) InboxViewSend.get("mailNo");
			System.out.println("보낸메일 답장 타겟");
			System.out.println(mailNo);
			List<Map<String,Object>> sendInboxViewReceive = mailService.sendInboxViewReceive(mailNo);
			List<Map<String,Object>> sendInboxViewCarbon = mailService.sendInboxViewCarbon(mailNo);
				
			Gson gson = new Gson();
			Map<String, Object> result = new HashMap<String, Object>();
			if(sendInboxViewReceive.size()>0) {
				result.put("receiverList", gson.toJson(sendInboxViewReceive));
				}
				if(sendInboxViewCarbon.size()>0) {
				result.put("carbonList",  gson.toJson(sendInboxViewCarbon));
				}
				System.out.println("결과 : "+result);
				return result;
			}
	

	//내게쓴메일수정
	@RequestMapping("/mail_modifty_form")
	public String mailmodiftyform(Model model, int mailNo){	
		System.out.println("mail_modifty_form");
		Map<String,Object> myWrtieInbox = mailService.mysendInboxView(mailNo);
		System.out.println("내게쓰기상세보기1");
		System.out.println(myWrtieInbox);
		model.addAttribute("myWriteinboxView", myWrtieInbox);
		return"mail_modifty";
	}

}
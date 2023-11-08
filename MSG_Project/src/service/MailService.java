package service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


import dao.MailDao;

@Service
public class MailService {
	private static final String UPLOAD_PATH = "C:\\boardFile";
	
	private static final int NUM_OF_BOARD_PER_PAGE = 10;
	// 한번에 표시될 네비게이션의 개수
	private static final int NUM_OF_NAVI_PAGE = 10;
	@Autowired
	private MailDao dao;
	
	
	//파일 불러오기List(보낸편지함)
	public List<Map<String, Object>> uploadFromMailList(int mailNo){
		System.out.println(dao.selectuploadFromMailList(mailNo));
		return dao.selectuploadFromMailList(mailNo);
	}
	
	//받은메일함 보여주기(받는이)
//	public Map<String,Object> selectuploadFromMail(int mailNo){
//		System.out.println(dao.selectuploadFromMail(mailNo));
//		return dao.selectuploadFromMail(mailNo);
//	}
//		
	
	
	//받은메일함 보여주기(받는이)
	public List<Map<String,Object>> receiveInboxMain(String empNo){
		System.out.println(dao.selectReceiverMain(empNo));
		return dao.selectReceiverMain(empNo);
	}
	
	
	//받은메일함 보여주기(받는이)
//	public List<Map<String,Object>> receiveInbox(String empNo){
//		System.out.println(dao.selectReceiver(empNo));
//		return dao.selectReceiver(empNo);
//	}
	
	//받은메일함 보여주기(받는이)
	public Map<String,Object> receiveInbox(Map<String,Object>param,int page){
		List<Map<String, Object>> receiveList;
		// 현재게시글의 총 개수를 넣어주기
		 int pageTotalCount; 
		/* 화면 출력에 필요한 값 */
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);

		/* 데이터 조회에 필요한 값 */
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);
		
		param.put("firstRow", firstRow);
		param.put("endRow", endRow);

		int type = (int) param.get("type");
		String keyword = (String) param.get("keyword");

		/*
		 * type == 1 제목검색 
		 * type == 2 작성자 
		 * type == 3 제목+작성자 
		 * type == 4 내용검색 
		 * type == 0 검색x,전체조회
		 */
		if (type == 1) {
			param.put("title", keyword);
		} else if (type == 2) {
			//보낸이 이름
			param.put("empName", keyword);
//		} else if (type == 3) {
//			param.put("title", keyword);
//			param.put("empName", keyword);
		} else if (type == 3) {
			param.put("writeDate", keyword);
		} 
		int count = dao.totalreceiveCount(param);
		pageTotalCount = getPageTotalCount(count);
		receiveList = dao.selectReceiver(param);
		
		/**** 결과값 Map에 넣어서 반환하기 ****/
		/*화면에서 쓸 데이터 */
		Map<String, Object> result = new HashMap<String,Object>();
		
		result.put("receiveList",receiveList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);
		
		
		return result;
	}
	
	//보낸메일함 보여주기
	public Map<String,Object> sendInbox(Map<String,Object>param,int page){
		List<Map<String, Object>> sendList;
		// 현재게시글의 총 개수를 넣어주기
		 int pageTotalCount; 
		/* 화면 출력에 필요한 값 */
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);

		/* 데이터 조회에 필요한 값 */
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);
		
		param.put("firstRow", firstRow);
		param.put("endRow", endRow);

		int type = (int) param.get("type");
		String keyword = (String) param.get("keyword");
		System.out.println("param -> " + param);

		/*
		 * type == 1 제목검색 
		 * type == 2 작성자 
		 * type == 3 제목+작성자 
		 * type == 4 내용검색 
		 * type == 0 검색x,전체조회
		 */
		if (type == 1) {
			param.put("title", keyword);
		} else if (type == 2) {
			//받는이 이름
			param.put("empName", keyword);
//		} else if (type == 3) {
//			param.put("title", keyword);
//			param.put("empName", keyword);
		} else if (type == 3) {
			param.put("writeDate", keyword);
		} 

		int count = dao.totalsenderCount(param);		
		System.out.println("토탈카운트에요!!!!!!");
		System.out.println(count);
		
		
		System.out.println("count : " + count);
		pageTotalCount = getPageTotalCount(count);
		
		System.out.println("service : 페이징totalreceiveCount :" +pageTotalCount);
		sendList = dao.selectSender(param);
		
		/**** 결과값 Map에 넣어서 반환하기 ****/
		/*화면에서 쓸 데이터 */
		Map<String, Object> result = new HashMap<String,Object>();
		
		result.put("sendList",sendList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);
		System.out.println("sendList -> " + sendList);
		System.out.println("startPage -> " + startPage);
		System.out.println("endPage -> " + endPage);
		System.out.println("currentPage -> " + page);
		System.out.println("pageTotalCount -> " + pageTotalCount);
		return result;
	}
	
	
	//내게쓴메일함 보여주기
	public Map<String,Object> MySenderInbox(Map<String,Object>param,int page){
		List<Map<String, Object>> MYsendList;
		// 현재게시글의 총 개수를 넣어주기
		 int pageTotalCount; 
		/* 화면 출력에 필요한 값 */
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);

		/* 데이터 조회에 필요한 값 */
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);
		
		param.put("firstRow", firstRow);
		param.put("endRow", endRow);

		int type = (int) param.get("type");
		String keyword = (String) param.get("keyword");
		System.out.println("param -> " + param);

		/*
		 * type == 1 제목검색 
		 * type == 2 작성자 
		 * type == 3 제목+작성자 
		 * type == 4 내용검색 
		 * type == 0 검색x,전체조회
		 */
		if (type == 1) {
			param.put("title", keyword);
		} else if (type == 2) {
			//보낸이
			param.put("empName", keyword);
//		} else if (type == 3) {
//			param.put("title", keyword);
//			param.put("empName", keyword);
		} else if (type == 3) {
			param.put("writeDate", keyword);
		} 

		int count = dao.totalMySenderCount(param);		
		System.out.println("토탈카운트에요!!!!!!");
		System.out.println(count);
		
		
		System.out.println("count : " + count);
		pageTotalCount = getPageTotalCount(count);
		
		System.out.println("service : 페이징totalMySenderCount :" +pageTotalCount);
		MYsendList = dao.selectMySender(param);
		
		/**** 결과값 Map에 넣어서 반환하기 ****/
		/*화면에서 쓸 데이터 */
		Map<String, Object> result = new HashMap<String,Object>();
		
		result.put("MYsendList",MYsendList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);
		System.out.println("sendList -> " + MYsendList);
		System.out.println("startPage -> " + startPage);
		System.out.println("endPage -> " + endPage);
		System.out.println("currentPage -> " + page);
		System.out.println("pageTotalCount -> " + pageTotalCount);
		return result;
	}
	
	//임시메일함 보여주기
	public Map<String,Object> draftInbox(Map<String,Object>param,int page){
		List<Map<String, Object>> draftList;
		// 현재게시글의 총 개수를 넣어주기
		 int pageTotalCount; 
		/* 화면 출력에 필요한 값 */
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);

		/* 데이터 조회에 필요한 값 */
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);
		
		param.put("firstRow", firstRow);
		param.put("endRow", endRow);

		int type = (int) param.get("type");
		String keyword = (String) param.get("keyword");
		System.out.println("param -> " + param);

		/*
		 * type == 1 제목검색 
		 * type == 2 작성자 
		 * type == 3 내용검색 
		 * type == 0 검색x,전체조회
		 */
		if (type == 1) {
			param.put("title", keyword);
		} else if (type == 2) {
			param.put("empName", keyword);
//		} else if (type == 3) {
//			param.put("title", keyword);
//			param.put("empName", keyword);
		} else if (type == 3) {
			param.put("writeDate", keyword);
		} 

		int count = dao.totaldraftCount(param);		

		System.out.println(count);
		
		

		pageTotalCount = getPageTotalCount(count);
		

		draftList = dao.selectDrafts(param);
		
		/**** 결과값 Map에 넣어서 반환하기 ****/
		/*화면에서 쓸 데이터 */
		Map<String, Object> result = new HashMap<String,Object>();
		
		result.put("draftList",draftList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);
		System.out.println("draftList -> " + draftList);
		System.out.println("startPage -> " + startPage);
		System.out.println("endPage -> " + endPage);
		System.out.println("currentPage -> " + page);
		System.out.println("pageTotalCount -> " + pageTotalCount);
		return result;
	}
	
	//휴지통 보여주기
	public Map<String,Object> deleteInbox(Map<String,Object>param,int page){
		List<Map<String, Object>> deleteList;
		// 현재게시글의 총 개수를 넣어주기
		 int pageTotalCount; 
		/* 화면 출력에 필요한 값 */
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);

		/* 데이터 조회에 필요한 값 */
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);
		
		param.put("firstRow", firstRow);
		param.put("endRow", endRow);

		int type = (int) param.get("type");
		String keyword = (String) param.get("keyword");
		System.out.println("param -> " + param);

		/*
		 * type == 1 제목검색 
		 * type == 2 작성자 
		 * type == 3 제목+작성자 
		 * type == 4 내용검색 
		 * type == 0 검색x,전체조회
		 */
		if (type == 1) {
			param.put("title", keyword);
		} else if (type == 2) {
			param.put("empName", keyword);
//		} else if (type == 3) {
//			param.put("title", keyword);
//			param.put("empName", keyword);
		} else if (type == 3) {
			param.put("writeDate", keyword);
		} 

		int count = dao.totaldeleteCount(param);		
	
		
		
		System.out.println("count : " + count);
		pageTotalCount = getPageTotalCount(count);
		
		System.out.println("service : 페이징totaldeleteCount :" +pageTotalCount);
		deleteList = dao.selectdelete(param);
		
		/**** 결과값 Map에 넣어서 반환하기 ****/
		/*화면에서 쓸 데이터 */
		Map<String, Object> result = new HashMap<String,Object>();
		
		result.put("deleteList",deleteList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);
		System.out.println("deleteList -> " + deleteList);
		System.out.println("startPage -> " + startPage);
		System.out.println("endPage -> " + endPage);
		System.out.println("currentPage -> " + page);
		System.out.println("pageTotalCount -> " + pageTotalCount);
		return result;
	}
	
	
	private int getPageTotalCount(int totalCount) {
		int pageTotalCount = 0;
	
		System.out.println(totalCount);
		if (totalCount != 0) {
			pageTotalCount = (int) Math.ceil(((double) totalCount / NUM_OF_BOARD_PER_PAGE));
		
			System.out.println(pageTotalCount);
		}
		return pageTotalCount;
	}

	
	
	private int getStartPage(int currentPage) {
		return ((currentPage - 1) / NUM_OF_NAVI_PAGE) * NUM_OF_NAVI_PAGE + 1;
	}

	private int getEndPage(int currentPage) {
		return (((currentPage - 1) / NUM_OF_NAVI_PAGE) + 1) * NUM_OF_NAVI_PAGE;
	}

	private int getFirstRow(int currentPage) {
		return (currentPage - 1) * NUM_OF_BOARD_PER_PAGE + 1;
	}

	private int getEndRow(int currentPage) {
		return currentPage * NUM_OF_BOARD_PER_PAGE;
	}

	
	
	
	//메일함 상세보여주기(보낸이)
	public Map<String,Object> InboxViewSend(int mailNo){
		System.out.println(dao.selectInboxViewSend(mailNo));
		return dao.selectInboxViewSend(mailNo);
	}
	
	//받은 메일함 상세 보여주기(받는이) 
	public List<Map<String,Object>> receiveInboxViewReceive(int mailNo){
		return dao.selectReceiveInboxViewReceive(mailNo);
	}
	
	//받은 메일함 상세보여주기(참조인)
	public List<Map<String,Object>> receiveInboxViewCarbon(int mailNo){
		return dao.selectReceiveInboxViewCarbon(mailNo);
	}
	
	
//	//받은메일에서 받는이
//	public Map<String, Object> ReceiveReceiver(String empNo){
//		return dao.selectReceiveReceiver(empNo);
//	}
//	
//	//받은메일에서 받는이
//	public Map<String, Object> ReceiveCarbon(String empNo){
//		return dao.selectReceiveCarbon(empNo);
//	}
	
	
	//보낸메일함 보여주기
//	public List<Map<String,Object>> sendInbox(String empNo){
//		System.out.println(dao.selectSenderMain(empNo));
//		return dao.selectSenderMain(empNo);
//	}
	
	//보낸메일함 보여주기메인
	public List<Map<String,Object>> sendInboxMain(String empNo){
		System.out.println(dao.selectSenderMain(empNo));
		return dao.selectSenderMain(empNo);
	}
	
	//보낸 메일함 상세 보여주기(받는이) 
	public List<Map<String,Object>> sendInboxViewReceive(int mailNo){
		return dao.selectSenderInboxViewReceive(mailNo);
	}
		
	//보낸 메일함 상세보여주기(참조인)
	public List<Map<String,Object>> sendInboxViewCarbon(int mailNo){
		return dao.selectSenderInboxViewCarbon(mailNo);
	}
	
	//내게쓴메일함 보여주기
//	public List<Map<String,Object>> mysendInbox(String empNo){
//		System.out.println(dao.selectMySender(empNo));
//		return dao.selectMySender(empNo);
//	}
	
	//내게쓴메일함 상세보여주기
	public Map<String,Object> mysendInboxView(int mailNo){
			return dao.selectMySenderView(mailNo);
	}
	
	//임시저장함 보여주기
//	public List<Map<String,Object>> draftsInbox(String empNo){
//			System.out.println(dao.selectDrafts(empNo));
//			return dao.selectDrafts(empNo);
//	}
	
	//임시저장함 상세 보여주기
	public Map<String,Object> draftsInboxView(int mailNo){
			System.out.println("임시저장함 상세보여주기");
			System.out.println(dao.selectDraftsInboxView(mailNo));
			return dao.selectDraftsInboxView(mailNo);
	}
		
	//임시 메일함 상세보여주기(받는이,참조인)
//	public List<Map<String,Object>> draftsInboxViewMail(int mailNo){
//		return dao.selectDraftsInboxViewMail(mailNo);
//	}
	
	public List<Map<String,Object>> draftsInboxViewReceive(int mailNo){
		return dao.selectDraftsInboxViewReceive(mailNo);
	}
	
	public List<Map<String,Object>> draftsInboxViewCarbon(int mailNo){
		return dao.selectDraftsInboxViewCarbon(mailNo);
	}
	
	//휴지통 보여주기
//	public List<Map<String,Object>> deleteInbox(String empNo){
//			System.out.println(dao.selectdelete(empNo));
//			return dao.selectdelete(empNo);
//	}
	
	//휴지통 상세 보여주기
	public Map<String,Object> deleteInboxViewSend(int mailNo){
		System.out.println(dao.selectdeleteInboxViewSend(mailNo));
		return dao.selectdeleteInboxViewSend(mailNo);
	}
	
	//휴지통 메일함 상세 보여주기(받는이) 
	public List<Map<String,Object>> deleteInboxViewReceive(int mailNo){
		return dao.selectDeleteInboxViewReceive(mailNo);
	}
				
	//휴지통 메일함 상세보여주기(참조인)
	public List<Map<String,Object>> deleteInboxViewCarbon(int mailNo){
		return dao.selectDeleteInboxViewCarbon(mailNo);
	}
	
	//휴지통 임시저장메일함 상세보여주기(받는이)
	public List<Map<String,Object>> DeleteInboxViewDraftReceive(int mailNo){
		return dao.selectDeleteInboxViewDraftReceive(mailNo);
	}
	
	//휴지통 임시저장메일함 상세보여주기(참조인)
	public List<Map<String,Object>> DeleteInboxViewDraftCarbon(int mailNo){
		return dao.selectDeleteInboxViewDraftCarbon(mailNo);
	}
		
	
	//메일 쓰기(보낸이)
	public boolean sendMail(Map<String,Object> mail,List<MultipartFile> file){
		System.out.println("보낸 메일 작성");
		System.out.println(mail);
	try {
//		if(mail.put("receiverId", dao.selectReceiver())==null) {
//				System.out.println("receiverId없어!!!!!");		
//			return false;
//		}else {
			dao.insertSendMail(mail);
			
			
			for(MultipartFile uploadfile : file) {
			String fileName = writeFile(uploadfile);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fileName", fileName);
			System.out.println("메일번호가져오기!!!");
			System.out.println(dao.getCurrentMailNo());
			params.put("mailNo", dao.getCurrentMailNo());
			dao.insertuploadFromMail(params);
			
			System.out.println("파일 업로드 완료");
			}
			System.out.println("정상등록");
			return true;
//		}
	}catch(Exception e) {
		e.printStackTrace();
		return false;
	}
	}
	
	//메일 전달(보낸이)
		public boolean sendMailrelay(Map<String,Object> mail,List<MultipartFile> file){
			System.out.println("보낸 메일 작성");
			System.out.println(mail);
		try {
				dao.insertSendMail(mail);			
				int oldmailNo=(int) mail.get("mailNo");
				int mailNo=dao.getCurrentMailNo();
				
				
				System.out.println("oldmailNo:" + oldmailNo);
				List<Map<String,Object>>upload=dao.selectuploadFromMail(oldmailNo);
				for(int i=0;i<upload.size();i++){
				String fileName1 =upload.get(i).get("fileName").toString();
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("fileName", fileName1);
				param.put("mailNo", mailNo);
				dao.insertuploadFromMail(param);
				}
			
			
				for(MultipartFile uploadfile : file) {
				String fileName = writeFile(uploadfile);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("fileName", fileName);
				params.put("mailNo", dao.getCurrentMailNo());
				dao.insertuploadFromMail(params);
				
				
				}
				
				return true;
//			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		}
	
	
	
	

	//메일쓰기 (받은이)
	public boolean receiveMail(Map<String,Object> mail){
		
		Map<String, Object> receiveMap = new HashMap<String, Object>();
		receiveMap.put("mailNo", mail.get("mailNo"));
		List<String> list = (List<String>) mail.get("receiverIdList");
		
		try {	
				for(int i= 0;i<list.size();i++) {
					receiveMap.remove("receiverId");
					receiveMap.put("receiverId", list.get(i) );
					System.out.println("receiveMap Check -> " +i + " : "  + receiveMap);
					dao.insertReceiveMail(receiveMap);
				}
				return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//메일쓰기 (참조인)
	public boolean carbonMail(Map<String,Object> mail){
		Map<String, Object> carbonMap = new HashMap<String, Object>();
		carbonMap.put("mailNo", mail.get("mailNo"));
		List<String> list = (List<String>) mail.get("carbonIdList");
		try {
			for(int i= 0;i<list.size();i++) {
				carbonMap.remove("carbonId");
				carbonMap.put("carbonId", list.get(i) );
				System.out.println("carbonId Check -> " +i + " : "  + carbonMap);
				dao.insertCarbonMail(carbonMap);	
			}
				return true;
				
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//내게쓰기
	public boolean sendMyMail(Map<String,Object> mail,List<MultipartFile> file){
		System.out.println("내게쓰기");
//		params.put(Constants.Attach.FULL_NAME, fullName);
//		params.put(Constants.Attach.NUM, board.get(Constants.Board.NUM));
	try {
		dao.insertMySendMail(mail);
		for(MultipartFile uploadfile : file) {
			String fileName = writeFile(uploadfile);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fileName", fileName);
			System.out.println("메일번호가져오기!!!");
			System.out.println(dao.getCurrentMailNo());
			params.put("mailNo", dao.getCurrentMailNo());
			dao.insertuploadFromMail(params);
			System.out.println("파일 업로드 완료");
			}
		System.out.println("내게쓰기 완료");
		return true;
	}catch(Exception e) {
		e.printStackTrace();
		return false;
	}
	}
	
	//내게쓰기 임시저장
		public boolean draftMyMail(Map<String,Object> mail,List<MultipartFile> file){
			System.out.println("내게쓰기임시저장");
			
//			params.put(Constants.Attach.FULL_NAME, fullName);
//			params.put(Constants.Attach.NUM, board.get(Constants.Board.NUM));
		try {
			
			dao.insertDraftMyMail(mail);
			for(MultipartFile uploadfile : file) {
				String fileName = writeFile(uploadfile);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("fileName", fileName);
				System.out.println("메일번호가져오기!!!");
				System.out.println(dao.getCurrentMailNo());
				params.put("mailNo", dao.getCurrentMailNo());
				dao.insertuploadFromMail(params);
				System.out.println("파일 업로드 완료");
				}
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		}	
	
	//임시저장
	public boolean drfatsMail(Map<String,Object> mail,List<MultipartFile> file){
		System.out.println("임시저장");
		
//		params.put(Constants.Attach.FULL_NAME, fullName);
//		params.put(Constants.Attach.NUM, board.get(Constants.Board.NUM));
	try {
		dao.insertDraftMail(mail);
		for(MultipartFile uploadfile : file) {
			String fileName = writeFile(uploadfile);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fileName", fileName);
			System.out.println("메일번호가져오기!!!");
			System.out.println(dao.getCurrentMailNo());
			params.put("mailNo", dao.getCurrentMailNo());
			dao.insertuploadFromMail(params);
			System.out.println("파일 업로드 완료");
			}
		return true;
	}catch(Exception e) {
		e.printStackTrace();
		return false;
	}
	}
	
	//임시저장(메일전달에서,메일답장에서)
	public boolean drfatsMailrelayreply(Map<String,Object> mail,List<MultipartFile> file){
		System.out.println("임시저장(메일전달에서,메일답장에서)");
		

	try {
		dao.insertDraftMail(mail);
		int oldmailNo=(int) mail.get("mailNo");
		int mailNo=dao.getCurrentMailNo();
		
		
		System.out.println("oldmailNo:" + oldmailNo);
		List<Map<String,Object>>upload=dao.selectuploadFromMail(oldmailNo);
		for(int i=0;i<upload.size();i++){
		String fileName1 =upload.get(i).get("fileName").toString();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("fileName", fileName1);
		param.put("mailNo", mailNo);
		dao.insertuploadFromMail(param);
		}
		System.out.println(upload);
	
		
	
//		for(int i=0;i<upload.size();i++) {
//			dao.insertuploadFromMail(upload);
//		}
//		
		
		
//		dao.updateFileMailNo(mail);
//		mail.put("fileNo", mail.get("fileNo"));
//		mail.put("fileName", mail.get("fileName"));
		
//		for(int i=0;<;i++) {
//			dao.insertuploadFromMail(params);
//		}
//		dao.insertuploadFromMail(mail);
		
		
		for(MultipartFile uploadfile : file) {
			String fileName = writeFile(uploadfile);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fileName", fileName);
			params.put("mailNo", mailNo);
			dao.insertuploadFromMail(params);
			System.out.println("파일 업로드 완료");
			}
		return true;
	}catch(Exception e) {
		e.printStackTrace();
		return false;
	}
	}
	
	
	
	//임시저장  받는이
		public boolean drfatsMailReceive(Map<String,Object> mail){
			System.out.println("임시저장 받는이");
			Map<String, Object> receiveMap = new HashMap<String, Object>();
			receiveMap.put("mailNo", mail.get("mailNo"));
			List<String> list = (List<String>) mail.get("receiverIdList");

		try {
			for(int i= 0;i<list.size();i++) {
				receiveMap.remove("receiverId");
				receiveMap.put("receiverId", list.get(i) );
				System.out.println("receiveMap Check -> " +i + " : "  + receiveMap);
				dao.insertDraftMailReceive(receiveMap);
			}
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		}
		
		
		
		
		//임시저장  참조인
		public boolean drfatsMailCarbon(Map<String,Object> mail){
			System.out.println("임시저장 참조인");
			Map<String, Object> carbonMap = new HashMap<String, Object>();
			carbonMap.put("mailNo", mail.get("mailNo"));
			List<String> list = (List<String>) mail.get("carbonIdList");

			try {
				for(int i= 0;i<list.size();i++) {
				carbonMap.remove("carbonId");
				carbonMap.put("carbonId", list.get(i) );
				System.out.println("carbonMap Check -> " +i + " : "  + carbonMap);
				dao.insertDraftMailCarbon(carbonMap);
				}
				return true;
			}catch(Exception e) {
					e.printStackTrace();
				return false;
			}
		}
		
		
		//임시저장함서 내게쓴메일로 전송
		public boolean updateMyWrite(Map<String,Object> mail,List<MultipartFile> file) {
			try {
				System.out.println("임시저장함서 내게쓴메일로 전송!");
				dao.insertMySendMail(mail);
				int mailNo=dao.getCurrentMailNo();
				mail.put("oldmailNo", mail.get("oldmailNo"));
				mail.put("newmailNo", mailNo);
				dao.updateFileMailNo(mail);
				System.out.println("임시저장함 내게쓰기 메일전송123213!!!!!!"+file);
				for(MultipartFile uploadfile : file) {
					
					String fileName = writeFile(uploadfile);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("fileName", fileName);
					params.put("mailNo", mailNo);
					dao.insertuploadFromMail(params);
					}
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			}
		
		//임시저장함서 재 임시저장
		public boolean updateMyWriteDraft(Map<String,Object> mail,List<MultipartFile> file) {
			try {
				System.out.println("임시저장함서 재 임시저장");
				dao.updateMyWriteDraft(mail);
				System.out.println("임시저장함서 재 임시저장22222");
				System.out.println(mail);
				int mailNo=(int) mail.get("mailNo");
				for(MultipartFile uploadfile : file) {
					String fileName = writeFile(uploadfile);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("fileName", fileName);
					params.put("mailNo", mailNo);
					dao.insertuploadFromMail(params);
				}
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			}
		
		//임시저장함에서 메일로 전송
		public boolean updateDraftSendMail(Map<String,Object> mail,List<MultipartFile> file) {
			
			try {
				dao.insertSendMail(mail);
				int mailNo=dao.getCurrentMailNo();
				mail.put("oldmailNo", mail.get("oldmailNo"));
				mail.put("newmailNo", mailNo);
				dao.updateFileMailNo(mail);
				for(MultipartFile uploadfile : file) {
					String fileName = writeFile(uploadfile);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("fileName", fileName);
					params.put("mailNo", mailNo);
					dao.insertuploadFromMail(params);
				}
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			
			}
		//임시저장함에서 메일로전송(send_mail 삭제)
		public boolean deleteSednMail(int mailNo) {
			try {
				System.out.println("임시저장함에서 메일로전송(send_mail 삭제)");
				dao.deleteSednMail(mailNo);
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			}
		
		
		
		//임시저장함에서 메일로전송(draft_mail 삭제)
		public boolean deleteDraftReceive(int mailNo) {
			try {
				System.out.println("임시정함에서 메일로전송 (draft_mail삭제 받는");
				dao.deleteDraftReceive(mailNo);
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			}
		
		//임시저장함에서 메일로전송(draft_mail 삭제)
		public boolean deleteDraftCarbon(int mailNo) {
			try {
				System.out.println("임시정함에서 메일로전송 (draft_mail삭제 참조");
				dao.deleteDraftCarbon(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
				}
		
		//임시저장함에서 메일로전송(deleteFile 삭제)
		public boolean deleteFile(int mailNo) {
			try {
						System.out.println("임시정함에서 메일로전송 (file 삭제");
						dao.deleteFile(mailNo);
						return true;
			}catch(Exception e) {
						e.printStackTrace();
						return false;
			}
			}
		
		
		//임시저장함에서 메일로 재저장
		public boolean updateDraftMail(Map<String,Object>mail,List<MultipartFile> file) {
			try {
				System.out.println("임시저장함에서 메일로 재저장");
				dao.updateDraftMail(mail);
				int mailNo=(int) mail.get("mailNo");
				for(MultipartFile uploadfile : file) {
					String fileName = writeFile(uploadfile);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("fileName", fileName);
					params.put("mailNo", mailNo);
					dao.insertuploadFromMail(params);
				}
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			}
		
		//임시저장함 받는이, 참조인
		public boolean updateDraftMailReceive(Map<String,Object>mail) {
			
			Map<String, Object> receiveMap = new HashMap<String, Object>();
			receiveMap.put("mailNo", mail.get("mailNo"));
			List<String> list = (List<String>) mail.get("receiverIdList");
			
			try {
				System.out.println("임시정함 받는이, 참조인");
				for(int i= 0;i<list.size();i++) {
					receiveMap.remove("receiverId");
					receiveMap.put("receiverId", list.get(i) );
					System.out.println("receiveMap Check -> " +i + " : "  + receiveMap);
					dao.updateDraftMailReceive(receiveMap);
				}
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			}
		
		//임시저장함 받는이, 참조인
		public boolean updateDraftMailCarbon(Map<String,Object>mail) {
			Map<String, Object> carbonMap = new HashMap<String, Object>();
			carbonMap.put("mailNo", mail.get("mailNo"));
			List<String> list = (List<String>) mail.get("carbonIdList");
			try {
				for(int i= 0;i<list.size();i++) {
					System.out.println("임시정함 받는이, 참조인");
					carbonMap.remove("carbonId");
					carbonMap.put("carbonId", list.get(i) );
					System.out.println("carbonMap Check -> " +i + " : "  + carbonMap);
					dao.updateDraftMailCarbon(carbonMap);
				}
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			}
				
		
		//보낸편지함 휴지통으로 보내기
		public boolean deleteMailSendInbox(Map<String,Object>mail) {
			try {
				System.out.println("보낸편지함 휴지통으로 보내기");
				dao.deleteMailSendInbox(mail);
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		
		//받은편지함 휴지통으로 보내기(받는이)
		public boolean deleteMailReceiveInboxReceiver(String mailNo) {
			try {
				
				System.out.println("받은편지함 휴지통으로 보내기(받는이)"+mailNo);
				dao.deleteMailReceiveInboxReceiver(mailNo);
				return true;
				}catch(Exception e) {
						e.printStackTrace();
						return false;
				}
			}
		
		//받은편지함  휴지통으로 보내기(참조인)
		public boolean deleteMailReceiveInboxCarbon(String mailNo) {
		try {
			
			System.out.println("받은편지함 휴지통으로 보내기 (참조인)"+mailNo);
			dao.deleteMailReceiveInboxCarbon(mailNo);
			return true;
			}catch(Exception e) {
					e.printStackTrace();
				return false;
			}
		}
		
		//받은편지함 상세보기서 휴지통으로 보내기(받는이)
		public boolean deleteMailReceiveInboxReceiverView(Map<String,Object> mail) {
			try {
						
				System.out.println("받은편지함 상세보기서 휴지통으로 보내기(받는이)"+mail);
				dao.deleteMailReceiveInboxReceiverView(mail);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
					}
				}
				
		//받은편지함 상세보기서 휴지통으로 보내기(참조인)
		public boolean deleteMailReceiveInboxCarbonView(Map<String,Object> mail) {
			try {
					
				System.out.println("받은편지함 상세보기서 휴지통으로 보내기 (참조인)"+mail);
				dao.deleteMailReceiveInboxCarbonView(mail);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
					}
				}
		
		
		
		//내게쓴 편지함 삭제
		public boolean deleteMyMailInbox(Map<String,Object>mail) {
			try {
				System.out.println("내게쓴 편지함 삭제");
				dao.deleteMyMailInbox(mail);
				return true;
				}catch(Exception e) {
						e.printStackTrace();
					return false;
				}
			}
		
		//내게임시(휴지통)삭제
		public boolean deleteDraftInboxMail(String mailNo) {
			try {
				System.out.println("내게임시(휴지통)삭제");
				dao.deleteDraftInboxMail(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
					}
				}
		
		//임시(휴지통)보낸이
		public boolean deleteDraftInboxMailSender(String mailNo) {
			try {
				System.out.println("임시(휴지통)보낸이");
				dao.deleteDraftInboxMailSender(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
					}
				}
		
		//임시(휴지통)받는이
		public boolean deleteDraftInboxMailReceive(String mailNo) {
			try {
				System.out.println("임시(휴지통)받는이");
				dao.deleteDraftInboxMailReceive(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
		}
		
		//임시(휴지통)참조인
		public boolean deleteDraftInboxMailCarbon(String mailNo) {
			try {
				System.out.println("임시(휴지통)참조인");
				dao.deleteDraftInboxMailCarbon(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
		}
		
		
		
		
		
		//보낸편지함 비우기
		public boolean emptyMailSendInbox(String mailNo) {
			try {
				System.out.println("보낸편지함 비우기");
				dao.emptyMailSendInbox(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
					}
		}
		
		//받은편지함 비우기(받는이)
		public boolean emptyMailReceiveInboxReceiver(String mailNo) {
			try {
				System.out.println("받은편지함 비우기(받는이)");
				dao.emptyMailReceiveInboxReceiver(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
					}
		}
		
		//받은편지함 비우기(참조인)
		public boolean emptyMailReceiveInboxCarbon(String mailNo) {
			try {
				System.out.println("받은편지함 비우기(참조인)");
				dao.emptyMailReceiveInboxCarbon(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		
		//내게쓴 편지함 비우기
		public boolean emptyMyMailInbox(String mailNo) {
			try {
				System.out.println("내게쓴 편지함 비우기");
				dao.emptyMyMailInbox(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		
		//내게 임시 비우기
		public boolean emptyDraftInboxMail(String mailNo) {
			try {
				System.out.println("내게 임시 비우기");
				dao.emptyDraftInboxMail(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		//임시 보낸이 비우기
		public boolean emptyDraftInboxMailSender(String mailNo) {
			try {
				System.out.println("임시 보낸이 비우기");
				dao.emptyDraftInboxMailSender(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		
		//임시 받는이 비우기
		public boolean emptyDraftInboxMailReceive(String mailNo) {
			try {
				System.out.println("임시 받는이,참조인 비우기");
				dao.emptyDraftInboxMailReceive(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
					}
				}
		
		//임시 참조인 비우기
		public boolean emptyDraftInboxMailCarbon(String mailNo) {
			try {
				System.out.println("임시 받는이,참조인 비우기");
				dao.emptyDraftInboxMailCarbon(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
					}
				}

		

		//보낸 편지함 상세보기 비우기
		public boolean emptyMailSendInboxView(Map<String,Object>mail) {
			try {
				System.out.println("보낸 편지함 비우기");
				dao.emptyMailSendInboxView(mail);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		

		//받은 편지함 상세보기(받는이) 비우기
		public boolean emptyMailReceiveInboxReceiverView(Map<String,Object>mail) {
			try {
				System.out.println("받은 편지함 상세보기(받는이) 비우기");
				dao.emptyMailReceiveInboxReceiverView(mail);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		

		//받은 편지함 상세보기(참조인) 비우기
		public boolean emptyMailReceiveInboxCarbonView(Map<String,Object>mail) {
			try {
				System.out.println("받은 편지함 상세보기(참조인) 비우기");
				dao.emptyMailReceiveInboxCarbonView(mail);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		
	
		//내게쓴 편지함 상세보기 비우기
		public boolean emptyMyMailInboxView(Map<String,Object>mail) {
			try {
				System.out.println("내게쓴 편지함 비우기");
				dao.emptyMyMailInboxView(mail);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
				
		//내게 임시 상세보기 비우기
		public boolean emptyDraftInboxMailView(Map<String,Object>mail) {
			try {
				System.out.println("내게 임시 비우기");
				dao.emptyDraftInboxMailView(mail);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		//임시 보낸이 상세보기 비우기
		public boolean emptyDraftInboxMailSenderView(Map<String,Object>mail) {
			try {
				System.out.println("임시 보낸이 비우기");
				dao.emptyDraftInboxMailSenderView(mail);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
				
		//임시 받는이 상세보기 비우기
		public boolean emptyDraftInboxMailDraftReceiveView(Map<String,Object>mail) {
			try {
				System.out.println("임시 받는이 비우기");
				dao.emptyDraftInboxMailDraftReceiveView(mail);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		
		//임시 참조인 상세보기 비우기
		public boolean emptyDraftInboxMailDraftCarbonView(Map<String,Object>mail) {
			try {
				System.out.println("임시 참조인 비우기");
				dao.emptyDraftInboxMailDraftCarbonView(mail);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		
		//메일 읽음 표시(받는이)
		public int updateReadmailReceiver(Map<String,Object>mail) {
			try {
				System.out.println("받는이 업데이트11234");
				return dao.updateReadmailReceiver(mail);
				}catch(Exception e) {
					e.printStackTrace();
					return 0;
				}
			}
		
		//메일 읽음 표시(받는이)
		public boolean updateReadmailCarbon(Map<String,Object>mail) {
			try {
				System.out.println("참조인 업데이트1234");
				dao.updateReadmailCarbon(mail);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		
		//메일 회수(보낸이)
		public boolean returnMailSend(int mailNo) {
				try {
				System.out.println("메일 회수(보낸이)");
				dao.returnMailSend(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		
		//메일 회수(받는이)
		public boolean returnMailReceive(int mailNo) {
				try {
				System.out.println("메일 회수(받는이)");
				dao.returnMailReceive(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		
		//메일 회수(참조인)
		public boolean returnMailCarbon(int mailNo) {
				try {
				System.out.println("메일 회수(참조인)");
				dao.returnMailCarbon(mailNo);
				return true;
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}	
				
	//보낸메일함에서 mailNo 가져오기
	public int getCurrentMailno() {
		return dao.getCurrentMailNo();
	}
	
	//게시글에 포함된 첨부파일 가져오기(받은메일함 상세보기)
	public File getAttachFile(Map<String, Object>params) {
		System.out.println("getAttachFile");
		Map<String, Object> send_mail = dao.selectuploadFromMailMap(params);
		String fileName = (String) send_mail.get("fileName");
		return new File(UPLOAD_PATH, fileName);
	}
	
	
	public String writeFile(MultipartFile file) {
	
	String fullName = null;
	// 파일이름 만들어내고,
	UUID uuid = UUID.randomUUID();
	fullName = uuid.toString() + "_" + file.getOriginalFilename();
	// 파일 생성하고,
	File target = new File(UPLOAD_PATH, fullName);
	try {
		// 원래파일을 만들어낸 파일에 복사
		FileCopyUtils.copy(file.getBytes(), target);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// 만들어낸 파일이름 반환
	return fullName;
}
}

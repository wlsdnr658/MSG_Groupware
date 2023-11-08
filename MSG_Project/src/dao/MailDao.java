package dao;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface MailDao {
	
	//파일 불러오기Map(보낸편지함)
	public Map<String, Object> selectuploadFromMailMap(Map<String, Object>params);
	//파일 불러오기List(보낸편지함)
	public List<Map<String, Object>> selectuploadFromMailList(int mailNo);
	//파일 가져오기 
	public List<Map<String, Object>> selectuploadFromMail(int mailNo);
	
	//업데이트 파일
	public int updateFileMailNo(Map<String,Object>mail); 
	
	//받은메일함 보여주기(받는이)
//	public List<Map<String, Object>> selectReceiver(String empNo);
	//받은메일함 보여주기(메인화면 받는이)
	public List<Map<String, Object>> selectReceiverMain(String empNo);
	//받은메일함 보여주기 (받는이)
	public List<Map<String,Object>> selectReceiver(Map<String,Object>param);
	//메일함 상세 보여주기(보낸이)
	public Map<String, Object> selectInboxViewSend(int mailNo);
	//받은 메일함 상세 보여주기(받는이)
	public List<Map<String,Object>> selectReceiveInboxViewReceive(int mailNo);
	//받은 메일함 상세보여주기(참조인)
	public List<Map<String,Object>> selectReceiveInboxViewCarbon(int mailNo);
	//total receive 카운트
	public int totalreceiveCount(Map<String,Object> param);
	
//	//받은메일에서 받는이
//	public Map<String, Object> selectReceiveReceiver(String empNo);
//	//받은메일에서 참조인
//	public Map<String, Object> selectReceiveCarbon(String empNo);
	
	
//	//보낸 메일함 보여주기
//	public List<Map<String, Object>> selectSender(String empNo);
	//보낸메일함 보여주기(메인화면 보낸이)
	public List<Map<String, Object>> selectSenderMain(String empNo);
	//보낸 메일함 보여주기
	public List<Map<String, Object>> selectSender(Map<String,Object>param);
	//보낸 메일함(hidden)값 보여주기
	public Map<String,Object> selectSenderhidden(String empNo);
	//보낸 메일함 상세 보여주기(받는이)
	public List<Map<String,Object>> selectSenderInboxViewReceive(int mailNo);
	//보낸 메일함 상세보여주기(참조인)
	public List<Map<String,Object>> selectSenderInboxViewCarbon(int mailNo);
	//total receive 카운트
	public int totalsenderCount(Map<String,Object> param);
	
	
	//	//참조인 보여주기
	//	public List<Map<String, Object>> selectCarbon();
	
	//내게쓴메일함 보여주기
//	public List<Map<String, Object>> selectMySender(String empNo);
	public List<Map<String,Object>> selectMySender(Map<String,Object>param);
	//내게쓴메일함  상세 보여주기
	public Map<String, Object> selectMySenderView(int mailNo);
	//total receive 카운트
	public int totalMySenderCount(Map<String,Object> param);
	
	//임시저장함 보여주기
//	public List<Map<String, Object>> selectDrafts(String empNo);
	public List<Map<String, Object>> selectDrafts(Map<String,Object>param);
	//임시저장함 상세보여주기
	public Map<String, Object> selectDraftsInboxView(int mailNo);
	//임시저장함 상세 보여주기 (받는이,참조인)
//	public List<Map<String,Object>> selectDraftsInboxViewMail(int mailNo);
	//임시저장함 상세 보여주기(받는이)
	public List<Map<String,Object>> selectDraftsInboxViewReceive(int mailNo);
	//임시저장함 상세 보여주기(참조인)
	public List<Map<String,Object>> selectDraftsInboxViewCarbon(int mailNo);
	//total receive 카운트
	public int totaldraftCount(Map<String,Object> param);
		
	//휴지통 보여주기
//	public List<Map<String, Object>> selectdelete(String empNo);
	public List<Map<String, Object>> selectdelete(Map<String,Object>param);
	//휴지통 상세 보여주기
	public Map<String, Object> selectdeleteInboxViewSend(int mailNo);
	//휴지통 상세 보여주기 (받는이)
	public List<Map<String,Object>> selectDeleteInboxViewReceive(int mailNo);
	//휴지통 상세 보여주기 (참조인)
	public List<Map<String,Object>> selectDeleteInboxViewCarbon(int mailNo);
	//휴지통 상세 보여주기(임시저장함)
//	public List<Map<String,Object>> selectDeleteInboxViewDraft(int mailNo);
	//휴지통 상세 보여주기(임시저장함 받는테이블)
	public List<Map<String,Object>> selectDeleteInboxViewDraftReceive(int mailNo);
	//휴지통 상세 보여주기(임시저장함 참조테이블)
	public List<Map<String,Object>> selectDeleteInboxViewDraftCarbon(int mailNo);
	
	
	//total delete 카운트
	public int totaldeleteCount(Map<String,Object> param);
	
	//메일 쓰기
	public int insertSendMail(Map<String, Object> mail);
	//받은사람
	public int insertReceiveMail(Map<String, Object> mail);
	//참조인사람
	public int insertCarbonMail(Map<String, Object> mail);
	//보낸메일 mailNo 가져오기
	public int getCurrentMailNo();
	
	//내게쓰기
	public int insertMySendMail(Map<String, Object> mail);
	
	//내게쓰기 임시저장
	public int insertDraftMyMail(Map<String,Object> mail);
	
	//임시저장
	public int insertDraftMail(Map<String, Object> mail);
	//임시저장 받는이, 참조인
//	public int insertDraftMailReceive(Map<String,Object> mail);
	//임시저장 받는이
	public int insertDraftMailReceive(Map<String,Object> mail);
	//임시저장  참조인
	public int insertDraftMailCarbon(Map<String,Object> mail);
	
	//임시저장함 내게 저장된 메일 내게쓴 메일로 전송
	public int updateMyWrite(Map<String,Object> mail);
	//임시저장함 내게 저장된 메일 재 저장
	public int updateMyWriteDraft(Map<String,Object> mail);
	
	//임시저장함에서 메일로 전송
	public int updateDraftSendMail(Map<String,Object>mail);
	//임시저장함에서 메일 전송(임시저장함 받는이 보낸이 삭제)
//	public int deleteDraftMail(Map<String,Object>mail);
	public int deleteDraftReceive(int mailNo);
	//임시저장함에서 메일 전송(임시저장함 받는이 보낸이 삭제)
	public int deleteDraftCarbon(int mailNo);
	//임시저장함에서 메일 전송(임시저장함 보낸이 삭제)
	public int deleteSednMail(int mailNo);
	//임시저장함에서 메일 전송(임시저장함 보낸이 삭제)
	public int deleteFile(int mailNo);
	
	//임시저장함 메일 재 저장
	public int updateDraftMail(Map<String,Object> mail);
	//임시저장 받는이, 참조인
//	public int updateDraftMailReceive(Map<String,Object>mail);
	public int updateDraftMailReceive(Map<String,Object>mail);
	//임시저장 참조인
	public int updateDraftMailCarbon(Map<String,Object>mail);
	
	//보낸편지함 휴지통으로 보내기
	public int deleteMailSendInbox(Map<String,Object>mail);
	//보낸편지함 휴지통으로 보내기(전체삭제)
	public String deleteMailSendInboxAll(Map<String,Object>mail);
	
	//받은편지함 휴지통으로 보내기(받는이)
	public int deleteMailReceiveInboxReceiver(String mailNo);
	//받은편지함 휴지통으로 보내기 (참조인)
	public int deleteMailReceiveInboxCarbon(String mailNo);	
	//받은편지함 상세보기서 휴지통으로 보내기(받는이)
	public int deleteMailReceiveInboxReceiverView(Map<String,Object>mail);
	//받은편지함 상세보기서 휴지통으로 보내기(참조인)
	public int deleteMailReceiveInboxCarbonView(Map<String,Object>mail);
	
	//내게쓴 편지함 삭제
	public int deleteMyMailInbox(Map<String,Object>mail);
	
	//내게임시(휴지통)삭제
	public int deleteDraftInboxMail(String mailNo);
	//임시(휴지통)보낸이
	public int deleteDraftInboxMailSender(String mailNo);
	//임시(휴지통)받는이
	public int deleteDraftInboxMailReceive(String mailNo);
	//임시(휴지통)참조인
	public int deleteDraftInboxMailCarbon(String mailNo);
	
	//보낸편지함 비우기
	public int emptyMailSendInbox(String mailNo);
	//받은편지함 비우기(받는이)
	public int emptyMailReceiveInboxReceiver(String mailNo);
	//받은편지함 비우기(참조인)
	public int emptyMailReceiveInboxCarbon(String mailNo);
	//내게쓴 편지함 비우기
	public int emptyMyMailInbox(String mailNo);
	//내게 임시 비우기
	public int emptyDraftInboxMail(String mailNo);
	//임시 보낸이 비우기
	public int emptyDraftInboxMailSender(String mailNo);
	//임시 받는이 비우기
	public int emptyDraftInboxMailReceive(String mailNo);
	//임시 참조인 비우기
	public int emptyDraftInboxMailCarbon(String mailNo);
	
	
	//보낸편지함 비우기 
	public int emptyMailSendInboxView(Map<String,Object>mail);
	//받은편지함 비우기(받는이)
	public int emptyMailReceiveInboxReceiverView(Map<String,Object>mail);
	//받은편지함 비우기(참조인)
	public int emptyMailReceiveInboxCarbonView(Map<String,Object>mail);
	//내게쓴 편지함 상세보기 비우기
	public int emptyMyMailInboxView(Map<String,Object>mail);
	//내게 임시 상세보기 비우기
	public int emptyDraftInboxMailView(Map<String,Object>mail);
	//임시 보낸이 상세보기 비우기
	public int emptyDraftInboxMailSenderView(Map<String,Object>mail);
	//임시 받는이,참조인 상세보기 비우기
//	public int emptyDraftInboxMailDraftView(Map<String,Object>mail);
	//임시 받는이 상세보기 비우기
	public int emptyDraftInboxMailDraftReceiveView(Map<String,Object>mail);
	//임시 참조인 상세보기 비우기
	public int emptyDraftInboxMailDraftCarbonView(Map<String,Object>mail);
	
	//메일 읽음 표시(받는이)
	public int updateReadmailReceiver(Map<String,Object>mail);
	//메일 읽음 표시(참조인)
	public int updateReadmailCarbon(Map<String,Object>mail);
	
	//메일 회수(보낸이)
	public int returnMailSend(int mailNo);
	//메일 회수(받는이)
	public int returnMailReceive(int mailNo);
	//메일 회수(참조인)
	public int returnMailCarbon(int mailNo);
	
	//첨부파일 이름 저장
	public int insertuploadFromMail(Map<String,Object>params);
	
	
	
}

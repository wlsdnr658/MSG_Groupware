package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.EmpDao;
import dao.MessageDao;

@Service
public class MessageService {

	// 한 페이지에 표시될 쪽지 개수
	private static final int NUM_OF_MESSAGE_PER_PAGE = 12;
	// 한 페이지에 표시될 페이지 태그 개수
	private static final int NUM_OF_NAVI_PER_PAGE = 10;	
	
	@Autowired
	private MessageDao msgDao;
	
	@Autowired
	private EmpDao empDao;
	
	
	//안읽은 쪽지 개수 확인
	public int getNonReadMessageCount(String empNo) {
		return msgDao.selectNonReadMessageCount(empNo);
	}
	//쪽지 작성
	public int addMessage(Map<String, Object> param) {
		//System.out.println("보낼 쪽지 : " + msg);
		
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("msgTitle", param.get("title"));
		msg.put("msgContent", param.get("content"));
		msg.put("senderID", param.get("senderID"));
		if(msg.get("title") == null) {
			msg.put("title", "(제목없음)");
		}
		System.out.println("**: "+msg);
		//보낸이와 보낸쪽지는 1행만 삽입되므로 우선 삽입.
		msgDao.insertMessage(msg);
		msgDao.message_send(msg);
		//받는 이의 숫자는 가변적이므로 스플릿을 통해 별도의 리스트로 나눠서 삽입.
		String paramList = (String)param.get("list");
		String[] list = paramList.split(",");
		for(String receiverID: list) {
			msg.put("receiverID", receiverID);
			msgDao.message_receive(msg);
		}
		return 0;
	}
	//받은 쪽지함 삭제
	public int receive_delete(String MSGNO, String RECEIVERID) {
		Map<String, Object> msg = new HashMap<String , Object>();
		msg.put("MSGNO", MSGNO);
		msg.put("RECEIVERID", RECEIVERID);
		System.out.println(msg);
		return msgDao.message_receive_delete(msg);
	}
	//보낸 쪽지함 삭제
	public int send_delete(String MSGNO, String SENDERID) {
		Map<String, Object> msg = new HashMap<String , Object>();
		msg.put("MSGNO", MSGNO);
		msg.put("SENDERID", SENDERID);
		System.out.println(msg);
		return msgDao.message_send_delete(msg);
	}
	//쪽지 목록 받기
	public Map<String, Object> getMessageList(Map<String, Object> param, int page) {
		//페이지 정보
		List<Map<String, Object>> pageList = new ArrayList<Map<String, Object>>();
		//쪽지 목록
		List<Map<String, Object>> messageList = new ArrayList<Map<String, Object>>();

		//네비게이션으로 보여줄 최종 페이지 설정
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);
		//해당 페이지의 쪽지 목록을 가져오기 위함
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);
		param.put("firstRow", firstRow);
		param.put("endRow", endRow);
		System.out.println("절취선");
		System.out.println(firstRow);
		System.out.println(endRow);

		//받은 쪽지함인지 보낸 쪽지함인지 확인.
		String boxType = (String) param.get("boxType");
		if(boxType.equals("101")) {
			System.out.println("받은 쪽지함 목록 요청");
			System.out.println("접속자 ID : " + param.get("empNo"));
			System.out.println("검색 타입 : " + param.get("type") + ", 키워드 : " + param.get("keyword"));
			messageList = getReceiveMessageList(param);
		}else if(boxType.equals("102")) {
			System.out.println("보낸 쪽지함 목록 요청");
			System.out.println("접속자 ID : " + param.get("empNo"));
			System.out.println("검색 타입 : " + param.get("type") + ", 키워드 : " + param.get("keyword"));
			messageList = getSendMessageList(param);
		}
		//현재게시글의 총 개수를 넣어주기
		int totalCount = 0;
		String keyword = (String)param.get("keyword");
		String type = (String)param.get("type");
		if(boxType.equals("101")) {
			if(keyword.equals("")) {
				totalCount = msgDao.receive_totalCount_byTitle(param);
			}
			if(type.equals("제목")) {
				totalCount = msgDao.receive_totalCount_byTitle(param);
			}
			if(type.equals("내용")) {
				totalCount = msgDao.receive_totalCount_byContent(param);
			}
			if(type.equals("이름")) {
				totalCount = msgDao.receive_totalCount_byName(param);
			}
			if(type.equals("제목 내용")) {
				totalCount = msgDao.receive_totalCount_byTitleOrContent(param);
			}
		}else if(boxType.equals("102")) {
			if(keyword.equals("")) {
				totalCount = msgDao.send_totalCount_byTitle(param);
			}
			if(type.equals("제목")) {
				totalCount = msgDao.send_totalCount_byTitle(param);
				System.out.println("여기 온거 맞음 ㅎ");
			}
			if(type.equals("내용")) {
				totalCount = msgDao.send_totalCount_byContent(param);
			}
			if(type.equals("이름")) {
				totalCount = msgDao.send_totalCount_byName(param);
			}
			if(type.equals("제목 내용")) {
				totalCount = msgDao.send_totalCount_byTitleOrContent(param);
			}			
		}
		
		/**** 결과값 Map에 넣어서 반환하기 ****/
		/*화면에서 쓸 데이터 */
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("messageList",messageList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", getPageTotalCount(totalCount));
		return result;
	}
	public List<Map<String, Object>> getReceiveMessageList(Map<String, Object> param) {
		//결과값 넣을 리스트
		List<Map<String, Object>> result = null;
		String type = (String) param.get("type");
		String keyword = (String) param.get("keyword");
		String empNo = (String) param.get("empNo");
		
		if(keyword.equals("")) {
			System.out.println(1);
			result = msgDao.searchMessages_receive_byTitle(param);
		}else if(type.equals("제목")) {
			System.out.println(2);
			result = msgDao.searchMessages_receive_byTitle(param);
		}else if(type.equals("내용")) {
			System.out.println(3);
			result = msgDao.searchMessages_receive_byContent(param);
		}else if(type.equals("이름")) {
			System.out.println(4);
			result = msgDao.searchMessages_receive_byName(param);
		}else if(type.equals("제목 내용")) {
			System.out.println(5);
			result = msgDao.searchMessages_receive_byTitleOrContent(param);
		}
		return result;
	}	
	public List<Map<String, Object>> getSendMessageList(Map<String, Object> param) {
		//결과값 넣을 리스트
		List<Map<String, Object>> result = null;
		String type = (String) param.get("type");
		String keyword = (String) param.get("keyword");
		String empNo = (String) param.get("empNo");
		
		System.out.println("파람 : "+param);
		
		
		if(keyword.equals("")) {
			System.out.println(1);
			result = msgDao.searchMessages_send_byTitle(param);
		}else if(type.equals("제목")) {
			System.out.println(2);
			result = msgDao.searchMessages_send_byTitle(param);
		}else if(type.equals("내용")) {
			System.out.println(3);
			result = msgDao.searchMessages_send_byContent(param);
		}else if(type.equals("이름")) {
			System.out.println(4);
			result = msgDao.searchMessages_send_byName(param);
		}else if(type.equals("제목 내용")) {
			System.out.println(5);
			result = msgDao.searchMessages_send_byTitleOrContent(param);
		}
		return result;		
	}
	public int readMessage(Map<String, Object> param) {
		return msgDao.readState(param);
	}
	
	
	private int getPageTotalCount(int totalCount) {
		int pageTotalCount = 0;
		if (totalCount != 0) {
			pageTotalCount = (int) Math.ceil(((double) totalCount / NUM_OF_MESSAGE_PER_PAGE));
		}
		return pageTotalCount;
	}
	private int getStartPage(int currentPage) {
		return ((currentPage - 1) / NUM_OF_NAVI_PER_PAGE) * NUM_OF_NAVI_PER_PAGE + 1;
	}
	private int getEndPage(int currentPage) {
		return (((currentPage - 1) / NUM_OF_NAVI_PER_PAGE) + 1) * NUM_OF_NAVI_PER_PAGE;
	}
	private int getFirstRow(int currentPage) {
		return (currentPage - 1) * NUM_OF_MESSAGE_PER_PAGE + 1;
	}
	private int getEndRow(int currentPage) {
		return currentPage * NUM_OF_MESSAGE_PER_PAGE;
	}
}

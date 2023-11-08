package dao;

import java.util.List;
import java.util.Map;

public interface MessageDao {
	//쪽지 생성
	public int insertMessage(Map<String, Object> msg);
	//보낸 쪽지 생성
	public int message_send(Map<String, Object> msg);
	//받은 쪽지 생성
	public int message_receive(Map<String, Object> msg);
	//읽음 처리
	public int readState(Map<String, Object> param);
	//몇명에게 보냈나?
	public int message_receive_count(int msgNo);
	//읽은 사람 수는?
	public int message_read_count(int msgNo);
	//쪽지 삭제
	public int message_send_delete(Map<String, Object> msg);
	public int message_receive_delete(Map<String, Object> msg);
	//받은 쪽지함 제목 검색, 쪽지 개수 반환
	public List<Map<String, Object>> searchMessages_receive_byTitle(Map<String, Object> target);
	public int receive_totalCount_byTitle(Map<String, Object> target);
	//받은 쪽지함 내용 검색, 쪽지 개수 반환
	public List<Map<String, Object>> searchMessages_receive_byContent(Map<String, Object> target);
	public int receive_totalCount_byContent(Map<String, Object> target);
	//받은 쪽지함 이름 검색, 쪽지 개수 반환
	public List<Map<String, Object>> searchMessages_receive_byName(Map<String, Object> target);
	public int receive_totalCount_byName(Map<String, Object> target);
	//받은 쪽지함 제목+내용 검색, 쪽지 개수 반환
	public List<Map<String, Object>> searchMessages_receive_byTitleOrContent(Map<String, Object> target);
	public int receive_totalCount_byTitleOrContent(Map<String, Object> target);
	//보낸 쪽지함 제목 검색, 쪽지 개수 반환
	public List<Map<String, Object>> searchMessages_send_byTitle(Map<String, Object> target);
	public int send_totalCount_byTitle(Map<String, Object> target);
	//보낸 쪽지함 내용 검색, 쪽지 개수 반환
	public List<Map<String, Object>> searchMessages_send_byContent(Map<String, Object> target);
	public int send_totalCount_byContent(Map<String, Object> target);
	//보낸 쪽지함 이름 검색, 쪽지 개수 반환
	public List<Map<String, Object>> searchMessages_send_byName(Map<String, Object> target);
	public int send_totalCount_byName(Map<String, Object> target);
	//보낸 쪽지함 제목+내용 검색, 쪽지 개수 반환
	public List<Map<String, Object>> searchMessages_send_byTitleOrContent(Map<String, Object> target);
	public int send_totalCount_byTitleOrContent(Map<String, Object> target);
	
	//안읽은 쪽지 보기
	public int selectNonReadMessageCount(String empNo);
	//읽음처리
	public int readState();
	
	//선택 쪽지 출력인데 안쓸거같음..
	public Map<String, Object> selectMessage(int msgNo);
	//받은 쪽지 가져오기
	public List<Map<String, Object>> selectMessageByID(String empNo);
}

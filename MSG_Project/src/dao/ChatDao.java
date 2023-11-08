package dao;

import java.util.List;
import java.util.Map;

public interface ChatDao {
	//채팅방 생성
	public int insertChatRoom(Map<String, Object> param);
	public int insertChatRoomUser(Map<String, Object> param);
	public int chatEmpCheck(Map<String, Object> param);
	public int decrementEmpNum(Map<String, Object> param);
	public int incrementEmpNum(Map<String, Object> param);
	public List<Map<String, Object>> room_empList(String roomNo);
	public List<Map<String, Object>> chatRoom_view(String empNo);
	public List<Map<String, Object>> selectChatListByRoom(Map<String, Object> param);
	public int insertChatContent(Map<String, Object> param);
	public int updateRoomNotice(Map<String, Object> param);
	public Map<String, Object> selectChatRoomByRoomNo(String roomNo);
	public String selectNumOfEmpByRoom(String roomNo);
	public int insertChatEmp(Map<String, Object> param);
	public List<Map<String, Object>> selectInviteList(String empNo);
	public int deleteChatEmp(Map<String, Object> param);
	public int chat_invite(Map<String, Object> param);
	public int chat_reject(Map<String, Object> param);
	public int insertFileByChat(Map<String, Object> param);
	public int insertChatFile(Map<String, Object> param);
}

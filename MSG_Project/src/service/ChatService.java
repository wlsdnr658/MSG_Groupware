package service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import dao.ChatDao;
import dao.EmpDao;

@Service
public class ChatService {

	@Autowired
	private ChatDao chatDao;
	
	@Autowired
	private EmpDao empDao;
	
//	private static final String UPLOAD_PATH = "/usr/local/chatFile/";
	private static final String UPLOAD_PATH = "C:\\chatFile";
	
	//채팅방 생성
	public int addChatRoom(String title, String empNo) {
		if(title.equals("")) {
			title = "제목없음";
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("title", title);
		param.put("empNo", empNo);
		//채팅방 생성
		if(chatDao.insertChatRoom(param)==1) {
			System.out.println("이거: "+param);
			//생성 후 참여자를 삽입
			if(chatDao.insertChatRoomUser(param)==1) {
				System.out.println("생성한 채팅방 번호 : "+param.get("roomNo"));
			}
		}
		return 0;
	}
	//채팅방 초대하기
	public int inviteChatEmp(Map<String, Object> param) {
		return chatDao.chat_invite(param);
	}
	//초대받은 채팅방 목록
	public List<Map<String, Object>> getChatInviteList(String empNo) {
		return chatDao.selectInviteList(empNo);
	}
	//채팅방 초대 승인
	public int chatInEmp (Map<String, Object> param) {
		//참여 목록에 추가 후, 초대 목록에서 삭제, 참여자 인원에서 증가
		int insertResult = chatDao.insertChatEmp(param);
		if(insertResult==1) {
			chatDao.incrementEmpNum(param);
			return chatDao.chat_reject(param);
		}else {
			return 0;
		}
	}
	//채팅방 초대 거절
	public int inviteChat_reject(Map<String, Object> param) {
		return chatDao.chat_reject(param);
	}	
	//채팅방 나가기
	public int chatRoom_ExitEmp(Map<String, Object> param) {
		chatDao.decrementEmpNum(param);
		return chatDao.deleteChatEmp(param);
	}
	//참여 중인 채팅방 목록 가져오기
	public List<Map<String, Object>> getChatRoomList(String empNo){
		return chatDao.chatRoom_view(empNo); 
	}
	//채팅 방 접속 시 채팅 내용 가져오기
	public List<Map<String, Object>> getChatList_byRoom(String roomNo, String empNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roomNo", roomNo);
		param.put("empNo", empNo);
		if(chatDao.chatEmpCheck(param)==0) {
			//참여자가 아님!
			return null;
		}
		return chatDao.selectChatListByRoom(param); 
	}
	//채팅방 참여자 수
	public String getEmpNum(String roomNo) {
		return chatDao.selectNumOfEmpByRoom(roomNo);
	}
	//채팅방 별 참여자 목록
	public List<Map<String, Object>> getChatEmpListByroomNo(String roomNo){
		return chatDao.room_empList(roomNo); 
	}
	//채팅방 이름, 공지 가져오기
	public Map<String, Object> getRoomInfo(String roomNo){
		return chatDao.selectChatRoomByRoomNo(roomNo);
	}
	//채팅 내용 저장
	public void saveChat(Map<String, Object> param) {
		String empNo = (String)param.get("empNo");
		int result = chatDao.insertChatContent(param);
		System.out.println("결과 : "+result);
	}
	//공지 내용 저장
	public void saveNotice(Map<String, Object> param) {
		int result = chatDao.updateRoomNotice(param);
		System.out.println("결과 : "+result);
	}
	//파일 저장
	public Map<String, Object> saveFile(Map<String, Object> param, MultipartFile file) {
		try {
			String fName = writeFile(file);
			param.put("fileName", fName);
			int resultA = chatDao.insertFileByChat(param);
			if(resultA==1) {
				int resultB = chatDao.insertChatFile(param);
				if(resultB==1) {
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("fileName", fName);
					result.put("fileNo", param.get("fileNo"));
					File nowFile = new File(UPLOAD_PATH, fName);
					if(nowFile.exists()) {
						String fileSize = sizeCalculation(nowFile.length());
						result.put("fileSize", fileSize);
					}
					return result;
				}
			}
			return null;
		} catch (NullPointerException e) {
			e.getStackTrace();
			return null;
		}
	}
	//파일 쓰기.
	private String writeFile(MultipartFile file) {
		String fName = null;
		UUID uuid = UUID.randomUUID();
		fName = uuid.toString()+"_"+file.getOriginalFilename();
		File target = new File(UPLOAD_PATH, fName);
		try {
			FileCopyUtils.copy(file.getBytes(), target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fName;
	}
	//파일 다운로드
	public File getAttachFile(String fileName) {
		return new File(UPLOAD_PATH, fileName);
	}
	//파일 사이즈 확인
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
}

package service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dao.BoardDao;

@Service
public class BoardService {
	
//	private static final String UPLOAD_PATH = "/usr/local/boardFile/";
	private static final String UPLOAD_PATH = "C:\\boardFile";
	
	private static final int NUM_OF_BOARD_PER_PAGE = 8;
	
	private static final int NUM_OF_BOARD_PER_PAGE1 = 5;
	
	private static final int NUM_OF_BOARD_PER_PAGE2 = 10;
	
	// 한번에 표시될 네비게이션의 개수
	private static final int NUM_OF_NAVI_PAGE = 5;
	
	@Autowired
	private BoardDao boardDao;
	
	
	// 부서별 게시판 따로 가져오기
	public String getEmpById(String empNo){
		
		Map<String, Object> emp = boardDao.getEmpById(empNo);
		String deptNo = (String)emp.get("deptNo");
		
		return deptNo;
	}
	
	///////////////////////////////////////////////////////*공지사항 Service*/////////////////////////////////////////////////////////
	//공지사항 리스트 불러오기
	public List<Map<String, Object>> getAllNoticeList() {
		return boardDao.getAllNotice();
	}
	
	//공지사항 작성  
	public boolean writeNotice(Map<String, Object> notice, List<MultipartFile> uploadFile) {
		try {
			
			boardDao.insertNotice(notice);
			
			for(MultipartFile uploadFile1 : uploadFile) {  
				Map<String, Object> params = new HashMap<String, Object>();
				String fileName = writeFile(uploadFile1);
				long fileSize = uploadFile1.getSize();
				params.put("FILESIZE", fileSize);
				params.put("FILENAME", fileName);
				int boardNo = boardDao.selectMaxNoticeBoardNo();
				params.put("BOARDNO", boardNo);
				boardDao.insertAttachNotice(params);
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//공지사항 리스트 가져오기
	public Map<String, Object> getNoticeList(Map<String, Object> notice,int page){
		
		List<Map<String, Object>> noticeboardList;
		int pageTotalCount; 
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);
		
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);
		
		notice.put("firstRow", firstRow-1);
		notice.put("endRow", endRow);
		
		int type = (int) notice.get("type");
		String keyword = (String) notice.get("keyword");
		
		if (type == 1) {
			notice.put("TITLE", keyword);
		} else if (type == 2) {
			notice.put("EMPNAME", keyword);
		} else if (type == 3) {
			notice.put("TITLE", keyword);
			notice.put("EMPNAME", keyword);
		} else if (type == 4) {
			notice.put("CONTENT", keyword);
		} 
		
		pageTotalCount = getPageTotalCount(boardDao.totalNoticeBoardCount(notice));
		noticeboardList = boardDao.searchNotice(notice);
		
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("noticeboardList",noticeboardList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);
		
		return result;
}
	
	public Map<String, Object> getPreviousNotice(int boardNo) {
		return boardDao.getPreviousNotice(boardNo);
	}
	
	public Map<String, Object> getNextNotice(int boardNo) {
		return boardDao.getNextNotice(boardNo);
	}
	
	// 공지사항 읽기
	public Map<String, Object> readNotice(int boardNo) {
		
		Map<String, Object> notice = boardDao.selectOneNotice(boardNo);
		
		int viewCount = (int)notice.get("viewCount");
		BigDecimal bigDecimal = new BigDecimal(viewCount);
		viewCount = bigDecimal.intValueExact();
		notice.put("viewCount", viewCount+1);
		boardDao.readNotice(notice);
		
		return notice;
	}
	
	public List<Map<String, Object>> getFileList(int boardNo){
		
		List<Map<String, Object>> fileList = boardDao.selectFileList(boardNo);
		
		for(int i=0;i<fileList.size();i++) {
			Map<String, Object> map = fileList.get(i);
			int fileSize = (int)map.get("fileSize");
			long fileSize1 = fileSize; 
			String a = getAsString(fileSize1);
			fileList.get(i).put("fileSize", a);
		}
		
		return fileList;
	}
	
	public File getAttachNoticeFile(Map<String, Object> map) {
		
		Map<String, Object> notice = boardDao.selectOneNotice1(map);
		
		String fileName = (String) notice.get("fileName");
		
		return new File(UPLOAD_PATH, fileName);
			
	}
	
	//공지사항 하나 가져오기
	public Map<String, Object> getNotice(int boardNo){
		return boardDao.selectOneNotice(boardNo);
	}
	//공지사항 수정하기
	public boolean modifyNotice(Map<String, Object> notice,List<MultipartFile> uploadFile){
		
		if(boardDao.updateNotice(notice) > 0) {
			
			for(MultipartFile uploadFile1 : uploadFile) {  
				Map<String, Object> params = new HashMap<String, Object>();
				String fileName = writeFile(uploadFile1);
				long fileSize = uploadFile1.getSize();
				params.put("FILESIZE", fileSize);
				params.put("FILENAME", fileName);
				String boardNo = (String) notice.get("boardNo");
				params.put("BOARDNO", boardNo);
				boardDao.insertAttachNotice(params);
			}
			
			
			return true;
		}
		return false;
	}
	
	public boolean removeNotice(Map<String, Object> notice){
			
		if(boardDao.deleteNotice(notice) > 0) {
			return true;
		}
		return false;
	}
	
	//공지사항 비밀번호 확인
	public boolean checkNoticePass(Map<String, Object> notice) {
		int boardNo = Integer.parseInt((String) notice.get("boardNo"));
		String pass = (String) notice.get("empPw");
		Map<String, Object> board = boardDao.selectOneNotice(boardNo);
		String originPass = (String) board.get("empPw");
		if (originPass.equals(pass)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	///////////////////////////////////////////////////////*이벤트 게시판 Service*/////////////////////////////////////////////////////////
	
	
	
		public List<Map<String, Object>> getAllEventList() {
			return boardDao.getAllEvent();
		}
		
		
		public boolean writeEvent(Map<String, Object> event, List<MultipartFile> uploadFile) {
			try {
				
				boardDao.insertEvent(event);
				
				for(MultipartFile uploadFile1 : uploadFile) {  
					Map<String, Object> params = new HashMap<String, Object>();
					String fileName = writeFile(uploadFile1);
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					int boardNo = boardDao.selectMaxEventBoardNo();
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachEvent(params);
				}
				
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		public Map<String, Object> getEventList(Map<String, Object> event,int page){
			
			List<Map<String, Object>> eventboardList;
			
			int pageTotalCount; 
			int startPage = getStartPage(page);
			int endPage = getEndPage(page);
			int firstRow = getFirstRow(page);
			int endRow = getEndRow(page);
			
			event.put("firstRow", firstRow-1);
			event.put("endRow", endRow);
			
			int type = (int) event.get("type");
			String keyword = (String) event.get("keyword");
			
			if (type == 1) {
				event.put("TITLE", keyword);
			} else if (type == 2) {
				event.put("EMPNAME", keyword);
			} else if (type == 3) {
				event.put("TITLE", keyword);
				event.put("EMPNAME", keyword);
			} else if (type == 4) {
				event.put("CONTENT", keyword);
			} 
			
			pageTotalCount = getPageTotalCount(boardDao.totalEventBoardCount(event));
			eventboardList = boardDao.searchEvent(event);
			
			Map<String, Object> result = new HashMap<String,Object>();
			result.put("eventboardList",eventboardList);
			result.put("startPage", startPage);
			result.put("endPage", endPage);
			result.put("currentPage", page);
			result.put("pageTotalCount", pageTotalCount);
			
			return result;
		}
		
		public Map<String, Object> getEventList1(Map<String, Object> event){
			List<Map<String, Object>> eventboardListE;
			eventboardListE = boardDao.searchEvent1(event);
			Map<String, Object> result1 = new HashMap<String,Object>();
			result1.put("eventboardListE",eventboardListE);
			return result1;
		}
		
		public Map<String, Object> getPreviousEvent(int boardNo) {
			return boardDao.getPreviousEvent(boardNo);
		}
		
		public Map<String, Object> getNextEvent(int boardNo) {
			return boardDao.getNextEvent(boardNo);
		}
		
		public Map<String, Object> readEvent(int boardNo) {
			Map<String, Object> event = boardDao.selectOneEvent(boardNo);
			int viewCount = (int)event.get("viewCount");
			BigDecimal bigDecimal = new BigDecimal(viewCount);
			viewCount = bigDecimal.intValueExact();
			event.put("viewCount", viewCount+1);
			boardDao.readEvent(event);
			return event;
		}
		
		public List<Map<String, Object>> getFileListEvent(int boardNo){
			List<Map<String, Object>> fileList = boardDao.selectFileListEvent(boardNo);
			for(int i=0;i<fileList.size();i++) {
				Map<String, Object> map = fileList.get(i);
				int fileSize = (int)map.get("fileSize");
				long fileSize1 = fileSize; 
				String a = getAsString(fileSize1);
				fileList.get(i).put("fileSize", a);
			}
			return fileList;
		}
		
		public File getAttachEventFile(Map<String, Object> map) {
			Map<String, Object> event = boardDao.selectOneEvent1(map);
			String fileName = (String) event.get("fileName");
			return new File(UPLOAD_PATH, fileName);
		}
		
		public Map<String, Object> getEvent(int boardNo){
			return boardDao.selectOneEvent(boardNo);
		}
		
		public boolean modifyEvent(Map<String, Object> event,List<MultipartFile> uploadFile){
			if(boardDao.updateEvent(event) > 0) {
				
				
				for(MultipartFile uploadFile1 : uploadFile) {  
					Map<String, Object> params = new HashMap<String, Object>();
					String fileName = writeFile(uploadFile1);
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					String boardNo = (String) event.get("boardNo");
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachEvent(params);
				}
				
				return true;
			}
			return false;
		}
		public boolean removeEvent(Map<String, Object> event){
			if(boardDao.deleteEvent(event) > 0) {
				return true;
			}
			return false;
		}
		public boolean checkEventPass(Map<String, Object> event) {
			int boardNo = Integer.parseInt((String) event.get("boardNo"));
			String pass = (String) event.get("empPw");
			Map<String, Object> board = boardDao.selectOneEvent(boardNo);
			String originPass = (String) board.get("empPw");
			if (originPass.equals(pass)) {
				return true;
			} else {
				return false;
			}
		}
		
		
		
		
		
		///////////////////////////////////////////////////////*IT부서 게시판 Service*/////////////////////////////////////////////////////////
		public List<Map<String, Object>> getAllITList() {
			return boardDao.getAllIT();
		}
		public boolean writeIT(Map<String, Object> it, List<MultipartFile> uploadFile) {
			try {
				
				boardDao.insertIT(it);
				
				for(MultipartFile uploadFile1 : uploadFile) {  
					String fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String, Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					int boardNo = boardDao.selectMaxITBoardNo();
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachIT(params);
				}
				
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		public Map<String, Object> getITList(Map<String, Object> it,int page){
					List<Map<String, Object>> itboardList;
					int pageTotalCount; 
					int startPage = getStartPage(page);
					int endPage = getEndPage(page);
					int firstRow = getFirstRow(page);
					int endRow = getEndRow(page);
					it.put("firstRow", firstRow-1);
					it.put("endRow", endRow);
					int type = (int) it.get("type");
					String keyword = (String) it.get("keyword");
					
					if (type == 1) {
						it.put("TITLE", keyword);
					} else if (type == 2) {
						it.put("EMPNAME", keyword);
					} else if (type == 3) {
						it.put("TITLE", keyword);
						it.put("EMPNAME", keyword);
					} else if (type == 4) {
						it.put("CONTENT", keyword);
					} 
					pageTotalCount = getPageTotalCount(boardDao.totalITBoardCount(it));
					itboardList = boardDao.searchIT(it);
					Map<String, Object> result = new HashMap<String,Object>();
					result.put("itboardList",itboardList);
					result.put("startPage", startPage);
					result.put("endPage", endPage);
					result.put("currentPage", page);
					result.put("pageTotalCount", pageTotalCount);
					return result;
		}
		
		public Map<String, Object> getITList1(Map<String, Object> it){
			
			List<Map<String, Object>> itboardListE;
			itboardListE = boardDao.searchIT1(it);
			Map<String, Object> result1 = new HashMap<String,Object>();
			result1.put("itboardListE",itboardListE);
			
			return result1;
		}
		public Map<String, Object> getPreviousIT(int boardNo) {
			return boardDao.getPreviousIT(boardNo);
		}
		public Map<String, Object> getNextIT(int boardNo) {
			return boardDao.getNextIT(boardNo);
		}
		public Map<String, Object> readIT(int boardNo) {
			Map<String, Object> it = boardDao.selectOneIT(boardNo);
			int viewCount = (int)it.get("viewCount");
			BigDecimal bigDecimal = new BigDecimal(viewCount);
			viewCount = bigDecimal.intValueExact();
			it.put("viewCount", viewCount+1);
			boardDao.readIT(it);
			return it;
		}
		
		public List<Map<String, Object>> getFileListIT(int boardNo){
			
			List<Map<String, Object>> fileList = boardDao.selectFileListIT(boardNo);
			for(int i=0;i<fileList.size();i++) {
				Map<String, Object> map = fileList.get(i);
				int fileSize = (int)map.get("fileSize");
				long fileSize1 = fileSize; 
				String a = getAsString(fileSize1);
				fileList.get(i).put("fileSize", a);
			}
			
			return fileList;
		}
		
		public File getAttachItFile(Map<String, Object> map) {
		Map<String, Object> it = boardDao.selectOneIT1(map);
		String fileName = (String) it.get("fileName");
		return new File(UPLOAD_PATH, fileName);
		}
		public Map<String, Object> getIT(int boardNo){
			return boardDao.selectOneIT(boardNo);
		}
		public boolean modifyIT(Map<String, Object> it,List<MultipartFile> uploadFile){
			if(boardDao.updateIT(it) > 0) {
				
				for(MultipartFile uploadFile1 : uploadFile) {
					String fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String,Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					String boardNo = (String) it.get("boardNo");
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachIT(params);
				}
				
				return true;
			}
			return false;
		}
		public boolean removeIT(Map<String, Object> it){
			if(boardDao.deleteIT(it) > 0) {
				return true;
			}
			return false;
		}
		public boolean checkITPass(Map<String, Object> it) {
			int boardNo = Integer.parseInt((String) it.get("boardNo"));
			String pass = (String) it.get("empPw");
			Map<String, Object> board = boardDao.selectOneIT(boardNo);
			String originPass = (String) board.get("empPw");
			if (originPass.equals(pass)) {
				return true;
			} else {
				return false;
			}
		}
		///////////////////////////////////////////////////////*경영기획부서 게시판 Service*/////////////////////////////////////////////////////////
		public List<Map<String, Object>> getAllBPList() {
			return boardDao.getAllBP();
		}
		public boolean writeBP(Map<String, Object> bp, List<MultipartFile> uploadFile) {
			try {
				
				boardDao.insertBP(bp);
				
				for(MultipartFile uploadFile1 : uploadFile) {  
					String fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String, Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					int boardNo = boardDao.selectMaxBPBoardNo();
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachBP(params);
				}
				
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		public Map<String, Object> getBPList(Map<String, Object> bp,int page){
				List<Map<String, Object>> bpboardList;
				int pageTotalCount; 
				int startPage = getStartPage(page);
				int endPage = getEndPage(page);
				int firstRow = getFirstRow(page);
				int endRow = getEndRow(page);
				bp.put("firstRow", firstRow-1);
				bp.put("endRow", endRow);
				int type = (int) bp.get("type");
				String keyword = (String) bp.get("keyword");
				
				if (type == 1) {
					bp.put("TITLE", keyword);
				} else if (type == 2) {
					bp.put("EMPNAME", keyword);
				} else if (type == 3) {
					bp.put("TITLE", keyword);
					bp.put("EMPNAME", keyword);
				} else if (type == 4) {
					bp.put("CONTENT", keyword);
				} 
				pageTotalCount = getPageTotalCount(boardDao.totalBPBoardCount(bp));
				bpboardList = boardDao.searchBP(bp);
				Map<String, Object> result = new HashMap<String,Object>();
				result.put("bpboardList",bpboardList);
				result.put("startPage", startPage);
				result.put("endPage", endPage);
				result.put("currentPage", page);
				result.put("pageTotalCount", pageTotalCount);
				return result;
		}
		public Map<String, Object> getBPList1(Map<String, Object> bp){
			
			List<Map<String, Object>> bpboardListE;
			bpboardListE = boardDao.searchBP1(bp);
			Map<String, Object> result1 = new HashMap<String,Object>();
			result1.put("bpboardListE",bpboardListE);
			
			return result1;
		}
		public Map<String, Object> getPreviousBP(int boardNo) {
			return boardDao.getPreviousBP(boardNo);
		}
		public File getAttachBPFile(Map<String, Object> map) {
		Map<String, Object> bp = boardDao.selectOneBP1(map);
		String fileName = (String) bp.get("fileName");
		return new File(UPLOAD_PATH, fileName);
		}
		public Map<String, Object> getNextBP(int boardNo) {
			return boardDao.getNextBP(boardNo);
		}
		public Map<String, Object> readBP(int boardNo) {
			Map<String, Object> bp = boardDao.selectOneBP(boardNo);
			int viewCount = (int)bp.get("viewCount");
			BigDecimal bigDecimal = new BigDecimal(viewCount);
			viewCount = bigDecimal.intValueExact();
			bp.put("viewCount", viewCount+1);
			boardDao.readBP(bp);
			return bp;
		}
		public List<Map<String, Object>> getFileListBP(int boardNo){
			
			List<Map<String, Object>> fileList = boardDao.selectFileListBP(boardNo);
			for(int i=0;i<fileList.size();i++) {
				Map<String, Object> map = fileList.get(i);
				int fileSize = (int)map.get("fileSize");
				long fileSize1 = fileSize; 
				String a = getAsString(fileSize1);
				fileList.get(i).put("fileSize", a);
			}
			
			return fileList;
		}
		public Map<String, Object> getBP(int boardNo){
			return boardDao.selectOneBP(boardNo);
		}
		public boolean modifyBP(Map<String, Object> bp,List<MultipartFile> uploadFile){
			if(boardDao.updateBP(bp) > 0) {
				
				for(MultipartFile uploadFile1 : uploadFile) {
					String fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String,Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					String boardNo = (String) bp.get("boardNo");
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachBP(params);
				}
				
				return true;
			}
			return false;
		}
		public boolean removeBP(Map<String, Object> bp){
			if(boardDao.deleteBP(bp) > 0) {
				return true;
			}
			return false;
		}
		public boolean checkBPPass(Map<String, Object> bp) {
			int boardNo = Integer.parseInt((String) bp.get("boardNo"));
			String pass = (String) bp.get("empPw");
			Map<String, Object> board = boardDao.selectOneBP(boardNo);
			String originPass = (String) board.get("empPw");
			if (originPass.equals(pass)) {
				return true;
			} else {
				return false;
			}
		}
		
		
		
	///////////////////////////////////////////////////////*재무부서 게시판 Service*/////////////////////////////////////////////////////////
		public List<Map<String, Object>> getAllMinList() {
			return boardDao.getAllMin();
		}
		public boolean writeMin(Map<String, Object> min, List<MultipartFile> uploadFile) {
			try {
				
				boardDao.insertMin(min);
				
				for(MultipartFile uploadFile1 : uploadFile) {  
					String fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String, Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					int boardNo = boardDao.selectMaxMinBoardNo();
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachMin(params);
				}
				
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		public Map<String, Object> getMinList(Map<String, Object> min,int page){
					List<Map<String, Object>> minboardList;
					int pageTotalCount; 
					int startPage = getStartPage(page);
					int endPage = getEndPage(page);
					int firstRow = getFirstRow(page);
					int endRow = getEndRow(page);
					min.put("firstRow", firstRow-1);
					min.put("endRow", endRow);
					int type = (int) min.get("type");
					String keyword = (String) min.get("keyword");
					
					if (type == 1) {
						min.put("TITLE", keyword);
					} else if (type == 2) {
						min.put("EMPNAME", keyword);
					} else if (type == 3) {
						min.put("TITLE", keyword);
						min.put("EMPNAME", keyword);
					} else if (type == 4) {
						min.put("CONTENT", keyword);
					} 
					pageTotalCount = getPageTotalCount(boardDao.totalMinBoardCount(min));
					minboardList = boardDao.searchMin(min);
					Map<String, Object> result = new HashMap<String,Object>();
					result.put("minboardList",minboardList);
					result.put("startPage", startPage);
					result.put("endPage", endPage);
					result.put("currentPage", page);
					result.put("pageTotalCount", pageTotalCount);
					return result;
		}
		public Map<String, Object> getMinList1(Map<String, Object> min){
			
			List<Map<String, Object>> minboardListE;
			minboardListE = boardDao.searchMin1(min);
			Map<String, Object> result1 = new HashMap<String,Object>();
			result1.put("minboardListE",minboardListE);
			
			return result1;
		}
		public Map<String, Object> getPreviousMin(int boardNo) {
			return boardDao.getPreviousMin(boardNo);
		}
		public Map<String, Object> getNextMin(int boardNo) {
			return boardDao.getNextMin(boardNo);
		}
		public Map<String, Object> readMin(int boardNo) {
			Map<String, Object> min = boardDao.selectOneMin(boardNo);
			int viewCount = (int)min.get("viewCount");
			BigDecimal bigDecimal = new BigDecimal(viewCount);
			viewCount = bigDecimal.intValueExact();
			min.put("viewCount", viewCount+1);
			boardDao.readMin(min);
			return min;
		}
		public List<Map<String, Object>> getFileListMin(int boardNo){
			
			List<Map<String, Object>> fileList = boardDao.selectFileListMin(boardNo);
			for(int i=0;i<fileList.size();i++) {
				Map<String, Object> map = fileList.get(i);
				int fileSize = (int)map.get("fileSize");
				long fileSize1 = fileSize; 
				String a = getAsString(fileSize1);
				fileList.get(i).put("fileSize", a);
			}
			
			return fileList;
		}
		public File getAttachMinFile(Map<String, Object> map) {
		Map<String, Object> min = boardDao.selectOneMin1(map);
		String fileName = (String) min.get("fileName");
		return new File(UPLOAD_PATH, fileName);
		}
		public Map<String, Object> getMin(int boardNo){
			return boardDao.selectOneMin(boardNo);
		}
		public boolean modifyMin(Map<String, Object> min,List<MultipartFile> uploadFile){
			if(boardDao.updateMin(min) > 0) {
				
				for(MultipartFile uploadFile1 : uploadFile) {
					String fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String,Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					String boardNo = (String) min.get("boardNo");
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachMin(params);
				}
				
				return true;
			}
			return false;
		}
		public boolean removeMin(Map<String, Object> min){
			if(boardDao.deleteMin(min) > 0) {
				return true;
			}
			return false;
		}
		public boolean checkMinPass(Map<String, Object> min) {
			int boardNo = Integer.parseInt((String) min.get("boardNo"));
			String pass = (String) min.get("empPw");
			Map<String, Object> board = boardDao.selectOneMin(boardNo);
			String originPass = (String) board.get("empPw");
			if (originPass.equals(pass)) {
				return true;
			} else {
				return false;
			}
		}
		
		
		
		
	///////////////////////////////////////////////////////*영업부서 게시판 Service*/////////////////////////////////////////////////////////
		
		public List<Map<String, Object>> getAllSalesList() {
			return boardDao.getAllSales();
		}
		
		public boolean writeSales(Map<String, Object> sales, List<MultipartFile> uploadFile) {
			try {
				
				boardDao.insertSales(sales);
				
				for(MultipartFile uploadFile1 : uploadFile) {  
					String fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String, Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					int boardNo = boardDao.selectMaxSalesBoardNo();
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachSales(params);
				}
				
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		public Map<String, Object> getSalesList(Map<String, Object> sales,int page){
					List<Map<String, Object>> salesboardList;
					int pageTotalCount; 
					int startPage = getStartPage(page);
					int endPage = getEndPage(page);
					int firstRow = getFirstRow(page);
					int endRow = getEndRow(page);
					sales.put("firstRow", firstRow-1);
					sales.put("endRow", endRow);
					int type = (int) sales.get("type");
					String keyword = (String) sales.get("keyword");
					
					if (type == 1) {
						sales.put("TITLE", keyword);
					} else if (type == 2) {
						sales.put("EMPNAME", keyword);
					} else if (type == 3) {
						sales.put("TITLE", keyword);
						sales.put("EMPNAME", keyword);
					} else if (type == 4) {
						sales.put("CONTENT", keyword);
					} 
					pageTotalCount = getPageTotalCount(boardDao.totalSalesBoardCount(sales));
					salesboardList = boardDao.searchSales(sales);
					Map<String, Object> result = new HashMap<String,Object>();
					result.put("salesboardList",salesboardList);
					result.put("startPage", startPage);
					result.put("endPage", endPage);
					result.put("currentPage", page);
					result.put("pageTotalCount", pageTotalCount);
					return result;
		}
		public Map<String, Object> getSalesList1(Map<String, Object> sales){
			
			List<Map<String, Object>> salesboardListE;
			salesboardListE = boardDao.searchSales1(sales);
			Map<String, Object> result1 = new HashMap<String,Object>();
			result1.put("salesboardListE",salesboardListE);
			
			return result1;
		}
		public Map<String, Object> getPreviousSales(int boardNo) {
			return boardDao.getPreviousSales(boardNo);
		}
		public Map<String, Object> getNextSales(int boardNo) {
			return boardDao.getNextSales(boardNo);
		}
		public Map<String, Object> readSales(int boardNo) {
			Map<String, Object> sales = boardDao.selectOneSales(boardNo);
			int viewCount = (int)sales.get("viewCount");
			BigDecimal bigDecimal = new BigDecimal(viewCount);
			viewCount = bigDecimal.intValueExact();
			sales.put("viewCount", viewCount+1);
			boardDao.readSales(sales);
			return sales;
		}
		public List<Map<String, Object>> getFileListSales(int boardNo){
			
			List<Map<String, Object>> fileList = boardDao.selectFileListSales(boardNo);
			for(int i=0;i<fileList.size();i++) {
				Map<String, Object> map = fileList.get(i);
				int fileSize = (int)map.get("fileSize");
				long fileSize1 = fileSize; 
				String a = getAsString(fileSize1);
				fileList.get(i).put("fileSize", a);
			}
			
			return fileList;
		}
		public File getAttachSalesFile(Map<String, Object> map) {
		Map<String, Object> sales = boardDao.selectOneSales1(map);
		String fileName = (String) sales.get("fileName");
		return new File(UPLOAD_PATH, fileName);
		}
		public Map<String, Object> getSales(int boardNo){
			return boardDao.selectOneSales(boardNo);
		}
		public boolean modifySales(Map<String, Object> sales,List<MultipartFile> uploadFile){
			if(boardDao.updateSales(sales) > 0) {
				
				for(MultipartFile uploadFile1 : uploadFile) {
					String fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String,Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					String boardNo = (String) sales.get("boardNo");
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachSales(params);
				}
				
				return true;
			}
			return false;
		}
		public boolean removeSales(Map<String, Object> sales){
			if(boardDao.deleteSales(sales) > 0) {
				return true;
			}
			return false;
		}
		public boolean checkSalesPass(Map<String, Object> sales) {
			int boardNo = Integer.parseInt((String) sales.get("boardNo"));
			String pass = (String) sales.get("empPw");
			Map<String, Object> board = boardDao.selectOneSales(boardNo);
			String originPass = (String) board.get("empPw");
			if (originPass.equals(pass)) {
				return true;
			} else {
				return false;
			}
		}
	
	
	///////////////////////////////////////////////////////*진행중 프로젝트  Service*/////////////////////////////////////////////////////////
		public List<Map<String, Object>> getAllProjectOngoingList() {
			return boardDao.getAllProjectOngoing();
		}
		
		public boolean writeProjectOngoing(Map<String, Object> projectOngoing, List<MultipartFile> uploadFile) {
			try {
				boardDao.insertProjectOngoing(projectOngoing);
				
				for(MultipartFile uploadFile1 : uploadFile) {  
					String fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String, Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					int boardNo = boardDao.selectMaxProjectOngoingBoardNo();
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachProjectOngoing(params);
				}
				
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		public boolean writeTask(Map<String, Object> task, List<MultipartFile> uploadFile,int boardNo) {
			try {
				
				String fileName = null;
				int taskNo;
				
				boardDao.insertTask(task);
				
				for(MultipartFile uploadFile1 : uploadFile) {  
					fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String, Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					taskNo = boardDao.selectMaxProjectOngoingTaskNo();
					params.put("TASKNO", taskNo);
					boardDao.insertAttachProjectTask(params);
				}
				
				Map<String, Object>existOfFile=new HashMap<String, Object>();
				taskNo = boardDao.selectMaxProjectOngoingTaskNo();
				existOfFile.put("TASKNO", taskNo);
				
				int idx = fileName.indexOf("_")+1;
				String origin = fileName.substring(idx);
				
				if(origin.isEmpty()) {
					existOfFile.put("UPLOADFILE", "N");
				}else {
					existOfFile.put("UPLOADFILE", "Y");
				}
				
				boardDao.updateExistOfFile(existOfFile);
				
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		public boolean insertParticipant(Map<String, Object> participant) {
			
			participant.put("BOARDNO", boardDao.selectMaxProjectOngoingBoardNo());
			if (boardDao.insertParticipant(participant)>0) {
				return true;
			} else{
				return false;
			}
			
		}
		
		public Map<String, Object> getProjectOngoingList(Map<String, Object> projectOngoing,int page){
			
			List<Map<String, Object>> projectOngoingboardList;
			
			int pageTotalCount; 
			int startPage = getStartPage2(page);
			int endPage = getEndPage2(page);
			int firstRow = getFirstRow2(page);
			int endRow = getEndRow2(page);
			projectOngoing.put("firstRow", firstRow-1);
			projectOngoing.put("endRow", endRow);
			int type = (int) projectOngoing.get("type");
			String keyword = (String) projectOngoing.get("keyword");
			
			if (type == 1) {
				projectOngoing.put("PROJECTNAME", keyword);
			} else if (type == 2) {
				projectOngoing.put("WRITER", keyword);
			} else if (type == 3) {
				projectOngoing.put("TITLE", keyword);
				projectOngoing.put("PROJECTNAME", keyword);
			} else if (type == 4) {
				projectOngoing.put("CONTENT", keyword);
			} 
			
			pageTotalCount = getPageTotalCount2(boardDao.totalProjectOngoingBoardCount(projectOngoing));
			projectOngoingboardList = boardDao.searchProjectOngoing(projectOngoing);
							
			Map<String, Object> result = new HashMap<String,Object>();
			result.put("projectOngoingboardList",projectOngoingboardList);
			result.put("startPage", startPage);
			result.put("endPage", endPage);
			result.put("currentPage", page);
			result.put("pageTotalCount", pageTotalCount);
			return result;
		}
		
		public Map<String, Object> readProjectOngoing(int boardNo) {
			
			Map<String, Object> projectOngoing = boardDao.selectOneProjectOngoing(boardNo);
			int viewCount = (int)projectOngoing.get("viewCount");
			BigDecimal bigDecimal = new BigDecimal(viewCount);
			viewCount = bigDecimal.intValueExact();
			projectOngoing.put("viewCount", viewCount+1);
			
			boardDao.readProjectOngoing(projectOngoing);
			
			return projectOngoing;
		}
		
		public Map<String, Object> readProjectOngoingTask(int taskNo) {
			
			Map<String, Object> task = boardDao.selectOneProjectOngoingTask(taskNo);
			
			return task;
		}
		
		public File getAttachProjectOngoingFile(Map<String, Object> map) {
		Map<String, Object> projectOngoing = boardDao.selectOneProjectOngoing1(map); 
		String fileName = (String) projectOngoing.get("fileName");
		return new File(UPLOAD_PATH, fileName);
		}
		
		public File getAttachProjectOngoingFileOfTask(Map<String, Object> map) {
			
			Map<String, Object> task = boardDao.selectOneProjectOngoingTask1(map); 
			String fileName = (String) task.get("fileName");
			return new File(UPLOAD_PATH, fileName);
			
			}
		
		public Map<String, Object> getProjectOngoing(int boardNo){
			
			return boardDao.selectOneProjectOngoing(boardNo);
		}
		
		public Map<String, Object> getProjectOngoingTask(int taskNo){
			
			return boardDao.selectOneProjectOngoingTask(taskNo);
		}
		
		public List<Map<String, Object>> getFileListProjectOngoing(int boardNo){
			
			List<Map<String, Object>> fileList = boardDao.selectFileListProjectOngoing(boardNo);
			for(int i=0;i<fileList.size();i++) {
				Map<String, Object> map = fileList.get(i);
				int fileSize = (int)map.get("fileSize");
				long fileSize1 = fileSize; 
				String a = getAsString(fileSize1);
				fileList.get(i).put("fileSize", a);
			}
			
			return fileList;
		}
		
		public List<Map<String, Object>> getFileListProjectOngoingTask(int taskNo){
			
			List<Map<String, Object>> fileList = boardDao.selectFileListProjectOngoingTask(taskNo);
			for(int i=0;i<fileList.size();i++) {
				Map<String, Object> map = fileList.get(i);
				int fileSize = (int)map.get("fileSize");
				long fileSize1 = fileSize; 
				String a = getAsString(fileSize1);
				fileList.get(i).put("fileSize", a);
			}
			return fileList;
		}
			
		public boolean removeProjectOngoing(Map<String, Object> projectOngoing) {
			
			if(boardDao.deleteProjectOngoing(projectOngoing) > 0) {
				return true;
			}
			return false;
		}
		
		public boolean removeProjectOngoingOfTask(Map<String, Object> task) {
			
			if(boardDao.deleteProjectOngoingOfTask(task) > 0) { 
				return true;
			}
			return false;
		}
		
		public boolean modifyProjectOngoing(Map<String, Object> projectOngoing,List<MultipartFile> uploadFile){
			
			if(boardDao.updateProjectOngoing(projectOngoing) > 0) {
				
				for(MultipartFile uploadFile1 : uploadFile) {  
					String fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String, Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					String boardNo = (String) projectOngoing.get("boardNo");
					params.put("BOARDNO", boardNo);
					boardDao.insertAttachProjectOngoing(params);
				}
				
				return true;
			}
			return false;
		}
		
		public boolean modifyProjectOngoingTask(Map<String, Object> task,List<MultipartFile> uploadFile,int taskNo){
			
			if(boardDao.updateProjectOngoingTask(task) > 0) {
				
				String fileName = null;
				
				for(MultipartFile uploadFile1 : uploadFile) {  
					fileName = writeFile(uploadFile1);
					Map<String, Object> params = new HashMap<String, Object>();
					long fileSize = uploadFile1.getSize();
					params.put("FILESIZE", fileSize);
					params.put("FILENAME", fileName);
					String taskNo1 = (String) task.get("taskNo");
					params.put("TASKNO", taskNo1);
					boardDao.insertAttachProjectTask(params);
				}
				
				Map<String, Object>existOfFile=new HashMap<String, Object>();
				int taskNo2 = taskNo;
				existOfFile.put("TASKNO", taskNo2);
				
				int idx = fileName.indexOf("_")+1;
				String origin = fileName.substring(idx);
				
				if(origin.isEmpty()) {
					existOfFile.put("UPLOADFILE", "N");
				}else {
					existOfFile.put("UPLOADFILE", "Y");
				}
				
				boardDao.updateExistOfFile(existOfFile);
				
				return true;
			}
			return false;
		}
		
		public Map<String, Object> getPreviousProjectOngoing(int boardNo) {
			return boardDao.getPreviousProjectOngoing(boardNo);
		}
		
		public Map<String, Object> getNextProjectOngoing(int boardNo) {
			return boardDao.getNextProjectOngoing(boardNo);
		}
		
		public boolean checkProjectOngoingPass(Map<String, Object> projectOngoing) {
			int boardNo = Integer.parseInt((String)projectOngoing.get("boardNo"));
			String pass = (String)projectOngoing.get("empPw");
			Map<String, Object> projectOngoing1 = boardDao.selectOneProjectOngoing(boardNo);
			String originPass = (String) projectOngoing1.get("empPw");
			if (originPass.equals(pass)) {
				return true;
			} else {
				return false;
			}
		}
		
		public List<Map<String, Object>>  getParticipant(int boardNo) {
			return boardDao.selectByBoardNoProjectOngoing(boardNo);
		}
		
		public Map<String, Object> getProjectOngoingTaskList(Map<String, Object> task,int page){
			
			int startPage = getStartPage1(page);
			int endPage = getEndPage1(page);
			int firstRow = getFirstRow1(page);  
			int endRow = getEndRow1(page);  
			task.put("firstRow", firstRow-1);
			task.put("endRow", endRow);
			
			int pageTotalCount = getPageTotalCount(boardDao.totalProjectOngoingTaskCount(task));
			List<Map<String, Object>> projectOngoingTaskList = boardDao.searchProjectOngoingTask(task);
			
			Map<String, Object> result = new HashMap<String,Object>();
			result.put("projectOngoingTaskList",projectOngoingTaskList);
			result.put("startPage", startPage);
			result.put("endPage", endPage);
			result.put("currentPage", page);
			result.put("pageTotalCount", pageTotalCount);
			return result;
		}
		
		public List<Map<String, Object>> getProjectOngoingAttachOfTask(int taskNo){
			
			List<Map<String, Object>> attach = boardDao.selectAttachOfTaskByTaskNo(taskNo);
			
			return attach;
		}
	
	///////////////////////////////////////////////////////*완료된 프로젝트 Service*/////////////////////////////////////////////////////////
	public Map<String, Object> getProjectFinishedList(Map<String, Object> projectFinished,int page){
		
		List<Map<String, Object>> projectFinishedboardList;
		
		int pageTotalCount; 
		int startPage = getStartPage2(page);
		int endPage = getEndPage2(page);
		int firstRow = getFirstRow2(page);
		int endRow = getEndRow2(page);
		projectFinished.put("firstRow", firstRow-1);
		projectFinished.put("endRow", endRow);
		int type = (int) projectFinished.get("type");
		String keyword = (String) projectFinished.get("keyword");
		
		if (type == 1) {
			projectFinished.put("PROJECTNAME", keyword);
		} else if (type == 2) {
			projectFinished.put("WRITER", keyword);
		} else if (type == 3) {
			projectFinished.put("PROJECTNAME", keyword);
			projectFinished.put("WRITER", keyword);
		} else if (type == 4) {
			projectFinished.put("CONTENT", keyword);
		} 
		
		pageTotalCount = getPageTotalCount2(boardDao.totalProjectFinishedBoardCount(projectFinished));
		projectFinishedboardList = boardDao.searchProjectFinished(projectFinished);
						
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("projectFinishedboardList",projectFinishedboardList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);
		return result;
	}
	
	public Map<String, Object> getPreviousProjectFinished(int boardNo) {
		return boardDao.getPreviousProjectFinished(boardNo);
	}
	
	public Map<String, Object> getNextProjectFinished(int boardNo) {
		return boardDao.getNextProjectFinished(boardNo);
	}
	///////////////////////////////////////////////////////*익명게시판 Service*/////////////////////////////////////////////////////////
	
	public List<Map<String, Object>> getAllAnonymityList() {
		return boardDao.getAllAnonymity();
	}
	
	public boolean writeAnonymity(Map<String, Object> anonymity, List<MultipartFile> uploadFile) {
		try {
			
			boardDao.insertAnonymity(anonymity);
			
			for(MultipartFile uploadFile1 : uploadFile) {  
				String fileName = writeFile(uploadFile1);
				Map<String, Object> params = new HashMap<String, Object>();
				long fileSize = uploadFile1.getSize();
				params.put("FILESIZE", fileSize);
				params.put("FILENAME", fileName);
				int boardNo = boardDao.selectMaxAnonymityBoardNo();
				params.put("BOARDNO", boardNo);
				boardDao.insertAttachAnonymity(params);
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Map<String, Object> getAnonymityList(Map<String, Object> anonymity,int page){
		List<Map<String, Object>> anonymityboardList;
		int pageTotalCount; 
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);
		anonymity.put("firstRow", firstRow-1);
		anonymity.put("endRow", endRow);
		int type = (int) anonymity.get("type");
		String keyword = (String) anonymity.get("keyword");
		
		if (type == 1) {
			anonymity.put("TITLE", keyword);
		} else if (type == 2) {
			anonymity.put("WRITER", keyword);
		} else if (type == 3) {
			anonymity.put("TITLE", keyword);
			anonymity.put("WRITER", keyword);
		} else if (type == 4) {
			anonymity.put("CONTENT", keyword);
		} 
		pageTotalCount = getPageTotalCount(boardDao.totalAnonymityBoardCount(anonymity));
		anonymityboardList = boardDao.searchAnonymity(anonymity);
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("anonymityboardList",anonymityboardList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);
		return result;
	}
	public Map<String, Object> getAnonymityList1(Map<String, Object> anonymity){
		
		List<Map<String, Object>> anonymityboardListE;
		anonymityboardListE = boardDao.searchAnonymity1(anonymity);
		Map<String, Object> result1 = new HashMap<String,Object>();
		result1.put("anonymityboardListE",anonymityboardListE);
		
		return result1;
	}
	public File getAttachAnonymityFile(Map<String, Object> map) {
		Map<String, Object> anonymity = boardDao.selectOneAnonymity1(map);
		String fileName = (String) anonymity.get("fileName");
		return new File(UPLOAD_PATH, fileName);
	}
	
	public Map<String, Object> getAnonymity(int boardNo){
		return boardDao.selectOneAnonymity(boardNo);
	}
	
	public boolean removeAnonymity(Map<String, Object> anonymity) {
		
		if(boardDao.deleteAnonymity(anonymity) > 0) {
			return true;
		}
		return false;
	}
	
	public boolean modifyAnonymity(Map<String, Object> anonymity,List<MultipartFile> uploadFile){
		
		if(boardDao.updateAnonymity(anonymity) > 0) {
			
			for(MultipartFile uploadFile1 : uploadFile) {  
				String fileName = writeFile(uploadFile1);
				Map<String, Object> params = new HashMap<String, Object>();
				long fileSize = uploadFile1.getSize();
				params.put("FILESIZE", fileSize);
				params.put("FILENAME", fileName);
				String boardNo = (String) anonymity.get("boardNo");
				params.put("BOARDNO", boardNo);
				boardDao.insertAttachAnonymity(params);
			}
			
			return true;
		}
		return false;
	}
	
	public Map<String, Object> getPreviousAnonymity(int boardNo) {
		return boardDao.getPreviousAnonymity(boardNo);
	}
	
	public Map<String, Object> getNextAnonymity(int boardNo) {
		return boardDao.getNextAnonymity(boardNo);
	}
	
	public Map<String, Object> readAnonymity(int boardNo) {
			
		Map<String, Object> anonymity = boardDao.selectOneAnonymity(boardNo);
		
		int viewCount = (int)anonymity.get("viewCount");
		BigDecimal bigDecimal = new BigDecimal(viewCount);
		viewCount = bigDecimal.intValueExact();
		anonymity.put("viewCount", viewCount+1);
		
		boardDao.readAnonymity(anonymity);
		
		return anonymity;
	}
	
	public List<Map<String, Object>> getFileListAnonymity(int boardNo){
		
		List<Map<String, Object>> fileList = boardDao.selectFileListAnonymity(boardNo);
		
		for(int i=0;i<fileList.size();i++) {
			Map<String, Object> map = fileList.get(i);
			int fileSize = (int)map.get("fileSize");
			long fileSize1 = fileSize; 
			String a = getAsString(fileSize1);
			fileList.get(i).put("fileSize", a);
		}
		
		return fileList;
	}
	
	public boolean checkAnonymityPass(Map<String, Object> anonymity) {
		
		int boardNo = Integer.parseInt((String)anonymity.get("boardNo"));
		String pass = (String)anonymity.get("boardPw");
		
		Map<String, Object> board = boardDao.selectOneAnonymity(boardNo);
		String originPass = (String) board.get("boardPw");
	
		if (originPass.equals(pass)) {
			return true;
		} else {
			return false;
		}
	}
	
	////////////////////////////페이징 용///////////////////////////////////////
	
	public String writeFile(MultipartFile file) {
		String fileName = null;
		UUID uuid = UUID.randomUUID();
		fileName = uuid.toString() + "_" + file.getOriginalFilename();
		File target = new File(UPLOAD_PATH, fileName);
		try {
			FileCopyUtils.copy(file.getBytes(), target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	private int getPageTotalCount(int totalCount) {
		int pageTotalCount = 0;
		if (totalCount != 0) {
			pageTotalCount = (int) Math.ceil(((double) totalCount / NUM_OF_BOARD_PER_PAGE));
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

	private int getStartPage1(int currentPage) {
		return ((currentPage - 1) / NUM_OF_NAVI_PAGE) * NUM_OF_NAVI_PAGE + 1;
	}

	private int getEndPage1(int currentPage) {
		return (((currentPage - 1) / NUM_OF_NAVI_PAGE) + 1) * NUM_OF_NAVI_PAGE;
	}

	private int getFirstRow1(int currentPage) {
		return (currentPage - 1) * NUM_OF_BOARD_PER_PAGE1 + 1;
	}

	private int getEndRow1(int currentPage) {
		return currentPage * NUM_OF_BOARD_PER_PAGE1;
	}
	
	private int getPageTotalCount2(int totalCount) {
		int pageTotalCount = 0;
		if (totalCount != 0) {
			pageTotalCount = (int) Math.ceil(((double) totalCount / NUM_OF_BOARD_PER_PAGE2));
		}
		return pageTotalCount;
	}

	private int getStartPage2(int currentPage) {
		return ((currentPage - 1) / NUM_OF_NAVI_PAGE) * NUM_OF_NAVI_PAGE + 1;
	}

	private int getEndPage2(int currentPage) {
		return (((currentPage - 1) / NUM_OF_NAVI_PAGE) + 1) * NUM_OF_NAVI_PAGE;
	}

	private int getFirstRow2(int currentPage) {
		return (currentPage - 1) * NUM_OF_BOARD_PER_PAGE2 + 1;
	}

	private int getEndRow2(int currentPage) {
		return currentPage * NUM_OF_BOARD_PER_PAGE2;
	}


	private static final String[] Q = new String[]{"", "KB", "MB", "GB", "TB", "P", "E"};
	public String getAsString(long bytes)
	{
	    for (int i = 6; i > 0; i--)
	    {
	        double step = Math.pow(1024, i);
	        if (bytes > step) return String.format("%3.1f %s", bytes / step, Q[i]);
	    }
	    return Long.toString(bytes);
	}
	
}

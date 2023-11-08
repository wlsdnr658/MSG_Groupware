package dao;

import java.util.List;
import java.util.Map;

public interface BoardDao {
	
	public Map<String, Object> getEmpById(String empNo);
	
	///////////////////////공지사항 Dao///////////////////////////////////////////
	public int insertNotice(Map<String, Object> notice);
	public int updateNotice(Map<String, Object> notice);
	public int readNotice(Map<String, Object> notice);
//	public int deleteNotice(int boardNo);
	public int deleteNotice(Map<String, Object> notice);
	public List<Map<String,Object>> getAllNotice();
	public int totalNoticeBoardCount(Map<String, Object> notice);
	public List<Map<String, Object>> searchNotice(Map<String, Object> notice);
	public Map<String, Object> getPreviousNotice(int boardNo);
	public Map<String, Object> getNextNotice(int boardNo);
	public Map<String, Object> selectOneNotice(int boardNo);
	public Map<String, Object> selectOneNotice1(Map<String, Object> map);
	public List<Map<String,Object>> selectFileList(int boardNo);
	
	//첨부파일  이름 저장
	public int insertAttachNotice(Map<String, Object> params);
	public int selectMaxNoticeBoardNo();
	
	/////////////////이벤트 Dao///////////////////////////////////////////
	public int insertEvent(Map<String, Object> event);
	public int updateEvent(Map<String, Object> event);
	public int readEvent(Map<String, Object> event);
	public int deleteEvent(Map<String, Object> event);
	public List<Map<String,Object>> getAllEvent();
	public int totalEventBoardCount(Map<String, Object> event);
	public List<Map<String, Object>> searchEvent(Map<String, Object> event);
	public List<Map<String, Object>> searchEvent1(Map<String, Object> event);
	public Map<String, Object> getPreviousEvent(int boardNo);
	public Map<String, Object> getNextEvent(int boardNo);
	public Map<String, Object> selectOneEvent(int boardNo);
	public Map<String, Object> selectOneEvent1(Map<String, Object> map);
	public List<Map<String,Object>> selectFileListEvent(int boardNo);
	
	//첨부파일  이름 저장
	public int insertAttachEvent(Map<String, Object> params);
	public int selectMaxEventBoardNo();
	
	/////////////////IT부서 게시판 Dao///////////////////////////////////////////
	public int insertIT(Map<String, Object> it);
	public int updateIT(Map<String, Object> it);
	public int readIT(Map<String, Object> it);
	public int deleteIT(Map<String, Object> it);
	public List<Map<String,Object>> getAllIT();
	public int totalITBoardCount(Map<String, Object> it);
	public List<Map<String, Object>> searchIT(Map<String, Object> it);
	public List<Map<String, Object>> searchIT1(Map<String, Object> it);
	public Map<String, Object> getPreviousIT(int boardNo);
	public Map<String, Object> getNextIT(int boardNo);
	public Map<String, Object> selectOneIT(int boardNo);
	public Map<String, Object> selectOneIT1(Map<String, Object> map);
	public List<Map<String,Object>> selectFileListIT(int boardNo);
	
	//첨부파일  이름 저장
		public int insertAttachIT(Map<String, Object> params);
		public int selectMaxITBoardNo();
	
	/////////////////경영기획부서 게시판 Dao///////////////////////////////////////////
	public int insertBP(Map<String, Object> BP);
	public int updateBP(Map<String, Object> BP);
	public int readBP(Map<String, Object> BP);
	public int deleteBP(Map<String, Object> BP);
	public List<Map<String,Object>> getAllBP();
	public int totalBPBoardCount(Map<String, Object> BP);
	public List<Map<String, Object>> searchBP(Map<String, Object> BP);
	public List<Map<String, Object>> searchBP1(Map<String, Object> BP);
	public Map<String, Object> getPreviousBP(int boardNo);
	public Map<String, Object> getNextBP(int boardNo);
	public Map<String, Object> selectOneBP(int boardNo);
	public Map<String, Object> selectOneBP1(Map<String, Object> map);
	public List<Map<String,Object>> selectFileListBP(int boardNo);
	
	//첨부파일  이름 저장
		public int insertAttachBP(Map<String, Object> params);
		public int selectMaxBPBoardNo();
	
	/////////////////재무부서 게시판 Dao///////////////////////////////////////////
	public int insertMin(Map<String, Object> min);
	public int updateMin(Map<String, Object> min);
	public int readMin(Map<String, Object> min);
	public int deleteMin(Map<String, Object> min);
	public List<Map<String,Object>> getAllMin();
	public int totalMinBoardCount(Map<String, Object> min);
	public List<Map<String, Object>> searchMin(Map<String, Object> min);
	public List<Map<String, Object>> searchMin1(Map<String, Object> min);
	public Map<String, Object> getPreviousMin(int boardNo);
	public Map<String, Object> getNextMin(int boardNo);
	public Map<String, Object> selectOneMin(int boardNo);
	public Map<String, Object> selectOneMin1(Map<String, Object> map);
	public List<Map<String,Object>> selectFileListMin(int boardNo);
	
	//첨부파일  이름 저장
		public int insertAttachMin(Map<String, Object> params);
		public int selectMaxMinBoardNo();
	
	/////////////////영업부서 게시판 Dao///////////////////////////////////////////
	public int insertSales(Map<String, Object> sales);
	public int updateSales(Map<String, Object> sales);
	public int readSales(Map<String, Object> sales);
	public int deleteSales(Map<String, Object> sales);
	public List<Map<String,Object>> getAllSales();
	public int totalSalesBoardCount(Map<String, Object> sales);
	public List<Map<String, Object>> searchSales(Map<String, Object> sales);
	public List<Map<String, Object>> searchSales1(Map<String, Object> sales);
	public Map<String, Object> getPreviousSales(int boardNo);
	public Map<String, Object> getNextSales(int boardNo);
	public Map<String, Object> selectOneSales(int boardNo);
	public Map<String, Object> selectOneSales1(Map<String, Object> map);
	public List<Map<String,Object>> selectFileListSales(int boardNo);
	
	//첨부파일  이름 저장
		public int insertAttachSales(Map<String, Object> params);
		public int selectMaxSalesBoardNo();
	
	/////////////////진행중 프로젝트 Dao///////////////////////////////////////////
	public int insertParticipant(Map<String, Object> participant);
	public int insertProjectOngoing(Map<String, Object> projectOngoing);
	public int updateProjectOngoing(Map<String, Object> projectOngoing);
	public int updateProjectOngoingTask(Map<String, Object> task);
	public int readProjectOngoing(Map<String, Object> projectOngoing);
	public int deleteProjectOngoing(Map<String, Object> projectOngoing);
	public int deleteProjectOngoingOfTask(Map<String, Object> task);
	public List<Map<String,Object>> getAllProjectOngoing();
	public int totalProjectOngoingBoardCount(Map<String, Object> projectOngoing);
	public int totalProjectOngoingTaskCount(Map<String, Object> projectOngoing);
	public List<Map<String, Object>> searchProjectOngoing(Map<String, Object> projectOngoing);
	public List<Map<String, Object>> searchProjectOngoingTask(Map<String, Object> projectOngoing);
	public Map<String, Object> getPreviousProjectOngoing(int boardNo);
	public Map<String, Object> getNextProjectOngoing(int boardNo);
	public Map<String, Object> selectOneProjectOngoing(int boardNo);
	public Map<String, Object> selectOneProjectOngoingTask(int taskNo);
	public Map<String, Object> selectOneProjectOngoingTask1(Map<String, Object> map);
	public Map<String, Object> selectOneProjectOngoing1(Map<String, Object> map);
	public List<Map<String,Object>> selectFileListProjectOngoing(int boardNo);
	public List<Map<String,Object>> selectFileListProjectOngoingTask(int boardNo);
	public int updateExistOfFile(Map<String, Object> exisOfFile);
	
	public List<Map<String, Object>>  selectByBoardNoProjectOngoing(int boardNo);
	public int insertTask(Map<String, Object> task);
	public List<Map<String, Object>> selectAttachOfTaskByTaskNo(int taskNo);
	
	//첨부파일  이름 저장
		public int insertAttachProjectOngoing(Map<String, Object> params);
		public int insertAttachProjectTask(Map<String, Object> params);
		public int selectMaxProjectOngoingBoardNo();
		public int selectMaxProjectOngoingTaskNo();

	
	/////////////////완료된 프로젝트 Dao///////////////////////////////////////////
	public int readFinished(Map<String, Object> finished);
	public int deleteFinished(Map<String, Object> finished);
	public List<Map<String,Object>> getAllFinished();
	public int totalFinishedBoardCount(Map<String, Object> finished);
	public List<Map<String, Object>> searchFinished(Map<String, Object> finished);
	
	public Map<String, Object> getPreviousProjectFinished(int boardNo);
	public Map<String, Object> getNextProjectFinished(int boardNo);
	public Map<String, Object> selectOneFinished(int boardNo);
	
	public int totalProjectFinishedBoardCount(Map<String, Object> projectOngoing);
	public List<Map<String, Object>> searchProjectFinished(Map<String, Object> projectOngoing);
	///////////////////익명게시판 Dao///////////////////////////////////////////
	public int insertAnonymity(Map<String, Object> anonymity);
	public int updateAnonymity(Map<String, Object> anonymity);
	public int readAnonymity(Map<String, Object> anonymity);
	public int deleteAnonymity(Map<String, Object> anonymity);
	public List<Map<String,Object>> getAllAnonymity();
	public int totalAnonymityBoardCount(Map<String, Object> param);
	public List<Map<String, Object>> searchAnonymity(Map<String, Object> anonymity);
	public List<Map<String, Object>> searchAnonymity1(Map<String, Object> anonymity);
	public Map<String, Object> getPreviousAnonymity(int boardNo);
	public Map<String, Object> getNextAnonymity(int boardNo);
	public Map<String, Object> selectOneAnonymity(int boardNo);
	public Map<String, Object> selectOneAnonymity1(Map<String, Object> map);
	public List<Map<String,Object>> selectFileListAnonymity(int boardNo);
	
	//첨부파일  이름 저장
		public int insertAttachAnonymity(Map<String, Object> params);
		public int selectMaxAnonymityBoardNo();
	
	
}

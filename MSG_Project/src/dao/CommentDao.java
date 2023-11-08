package dao;

import java.util.List;
import java.util.Map;

public interface CommentDao {
	/////////////////////////////////////notice 댓글///////////////////////////////////////////
	public int insertComment(Map<String, Object> comment);
	public int updateComment(Map<String, Object> comment);
	public int deleteComment(int cmtNo);
	public Map<String, Object> selectOne(int cmtNo);
	public List<Map<String, Object>> selectByBoardNo(int boardNo);
	public int getCount(int boardNo);
	//	/////////////////////////////////event 댓글///////////////////////////////////////////
	public int insertCommentEvent(Map<String, Object> comment);
	public int updateCommentEvent(Map<String, Object> comment);
	public int deleteCommentEvent(int cmtNo);
	public Map<String, Object> selectOneEvent(int cmtNo);
	public List<Map<String, Object>> selectByBoardNoEvent(int boardNo);
	public int getCountEvent(int boardNo);
	//	/////////////////////////////////it 댓글///////////////////////////////////////////
	public int insertCommentIt(Map<String, Object> comment);
	public int updateCommentIt(Map<String, Object> comment);
	public int deleteCommentIt(int cmtNo);
	public Map<String, Object> selectOneIt(int cmtNo);
	public List<Map<String, Object>> selectByBoardNoIt(int boardNo);
	public int getCountIt(int boardNo);
	//	/////////////////////////////////min 댓글///////////////////////////////////////////
	public int insertCommentMin(Map<String, Object> comment);
	public int updateCommentMin(Map<String, Object> comment);
	public int deleteCommentMin(int cmtNo);
	public Map<String, Object> selectOneMin(int cmtNo);
	public List<Map<String, Object>> selectByBoardNoMin(int boardNo);
	public int getCountMin(int boardNo);
	//	/////////////////////////////////bp 댓글///////////////////////////////////////////
	public int insertCommentBp(Map<String, Object> comment);
	public int updateCommentBp(Map<String, Object> comment);
	public int deleteCommentBp(int cmtNo);
	public Map<String, Object> selectOneBp(int cmtNo);
	public List<Map<String, Object>> selectByBoardNoBp(int boardNo);
	public int getCountBp(int boardNo);
	//	/////////////////////////////////sales 댓글///////////////////////////////////////////
	public int insertCommentSales(Map<String, Object> comment);
	public int updateCommentSales(Map<String, Object> comment);
	public int deleteCommentSales(int cmtNo);
	public Map<String, Object> selectOneSales(int cmtNo);
	public List<Map<String, Object>> selectByBoardNoSales(int boardNo);
	public int getCountSales(int boardNo);
	/////////////////////////////////////notice 댓글///////////////////////////////////////////
	public int insertCommentAnonymity(Map<String, Object> comment);
	public int updateCommentAnonymity(Map<String, Object> comment);
	public int deleteCommentAnonymity(int cmtNo);
	public Map<String, Object> selectOneAnonymity(int cmtNo);
	public List<Map<String, Object>> selectByBoardNoAnonymity(int boardNo);
	public int getCountAnonymity(int boardNo);
	/////////////////////////////////////project 댓글///////////////////////////////////////////
	public int insertCommentProjectOngoing(Map<String, Object> comment);
	public int updateCommentProjectOngoing(Map<String, Object> comment);
	public int deleteProjectOngoing(int cmtNo);
	public Map<String, Object> selectOneProjectOngoing(int cmtNo);
	public List<Map<String, Object>> selectByBoardNoProjectOngoing(int boardNo);
	public int getCountProjectOngoing(int boardNo);
}






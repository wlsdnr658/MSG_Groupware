package service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CommentDao;

@Service
public class CommentService {
	
	@Autowired
	private CommentDao commentDao;
	/////////////////////////////////////////////notice 댓글//////////////////////////////////////////////////
	public boolean addComment(Map<String, Object> comment) {
		if(commentDao.insertComment(comment)>0) {
			return true;
		}else {
			return false;
		}
	}
	public List<Map<String, Object>> getBoardComment(int boardNo){
		return commentDao.selectByBoardNo(boardNo);
	}
	public boolean removeComment(int cmtNo) {
		int result = commentDao.deleteComment(cmtNo);
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean modifyComment(Map<String, Object> comment) {
		
		System.out.println("commentservice의 mdofycomment 의 comment" + comment);
		
		if(commentDao.updateComment(comment) > 0) {
			return true;
		}else {
			return false;
		}
	}
	/////////////////////////////////////////////event 댓글//////////////////////////////////////////////////
	public boolean addCommentEvent(Map<String, Object> comment) {
		if(commentDao.insertCommentEvent(comment)>0) {
			return true;
		}else {
			return false;
		}
	}
	public List<Map<String, Object>> getBoardCommentEvent(int boardNo){
		return commentDao.selectByBoardNoEvent(boardNo);
	}
	public boolean removeCommentEvent(int cmtNo) {
		int result = commentDao.deleteCommentEvent(cmtNo);
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean modifyCommentEvent(Map<String, Object> comment) {
		
		System.out.println("commentservice의 mdofycommentEvent 의 comment" + comment);
		
		if(commentDao.updateCommentEvent(comment) > 0) {
			return true;
		}else {
			return false;
		}
	}
	/////////////////////////////////////////////it 댓글//////////////////////////////////////////////////
	public boolean addCommentIt(Map<String, Object> comment) {
		if(commentDao.insertCommentIt(comment)>0) {
			return true;
		}else {
			return false;
		}
	}
	public List<Map<String, Object>> getBoardCommentIt(int boardNo){
		return commentDao.selectByBoardNoIt(boardNo);
	}
	public boolean removeCommentIt(int cmtNo) {
		int result = commentDao.deleteCommentIt(cmtNo);
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean modifyCommentIt(Map<String, Object> comment) {
		if(commentDao.updateCommentIt(comment) > 0) {
			return true;
		}else {
			return false;
		}
	}
	/////////////////////////////////////////////min 댓글//////////////////////////////////////////////////
	public boolean addCommentMin(Map<String, Object> comment) {
		if(commentDao.insertCommentMin(comment)>0) {
			return true;
		}else {
			return false;
		}
	}
	public List<Map<String, Object>> getBoardCommentMin(int boardNo){
		return commentDao.selectByBoardNoMin(boardNo);
	}
	public boolean removeCommentMin(int cmtNo) {
		int result = commentDao.deleteCommentMin(cmtNo);
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean modifyCommentMin(Map<String, Object> comment) {
		if(commentDao.updateCommentMin(comment) > 0) {
			return true;
		}else {
			return false;
		}
	}
	/////////////////////////////////////////////bp 댓글//////////////////////////////////////////////////
	public boolean addCommentBp(Map<String, Object> comment) {
		if(commentDao.insertCommentBp(comment)>0) {
			return true;
		}else {
			return false;
		}
	}
	public List<Map<String, Object>> getBoardCommentBp(int boardNo){
		return commentDao.selectByBoardNoBp(boardNo);
	}
	public boolean removeCommentBp(int cmtNo) {
		int result = commentDao.deleteCommentBp(cmtNo);
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean modifyCommentBp(Map<String, Object> comment) {
		if(commentDao.updateCommentBp(comment) > 0) {
			return true;
		}else {
			return false;
		}
	}
	/////////////////////////////////////////////sales 댓글//////////////////////////////////////////////////
	public boolean addCommentSales(Map<String, Object> comment) {
		if(commentDao.insertCommentSales(comment)>0) {
			return true;
		}else {
			return false;
		}
	}
	public List<Map<String, Object>> getBoardCommentSales(int boardNo){
		return commentDao.selectByBoardNoSales(boardNo);
	}
	public boolean removeCommentSales(int cmtNo) {
		int result = commentDao.deleteCommentSales(cmtNo);
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean modifyCommentSales(Map<String, Object> comment) {
		if(commentDao.updateCommentSales(comment) > 0) {
			return true;
		}else {
			return false;
		}
	}
	/////////////////////////////////////////////anonymity 댓글//////////////////////////////////////////////////
	public boolean addCommentAnonymity(Map<String, Object> comment) {
		if(commentDao.insertCommentAnonymity(comment)>0) {
			return true;
		}else {
			return false;
		}
	}
	public List<Map<String, Object>> getBoardCommentAnonymity(int boardNo){
		return commentDao.selectByBoardNoAnonymity(boardNo);
	}
	public boolean removeCommentAnonymity(int cmtNo) {
		int result = commentDao.deleteCommentAnonymity(cmtNo);
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean modifyCommentAnonymity(Map<String, Object> comment) {
		if(commentDao.updateCommentAnonymity(comment) > 0) {
			return true;
		}else {
				return false;
		}
		
	}
		
	///////////////////////////////////////////////////////////////////////////////////////////////////
		
	public boolean addCommentProjectOngoing(Map<String, Object> comment) {
		if(commentDao.insertCommentProjectOngoing(comment)>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<Map<String, Object>> getBoardCommentProjectOngoing(int boardNo){
		return commentDao.selectByBoardNoProjectOngoing(boardNo);
	}
	
	public boolean removeCommentProjectOngoing(int cmtNo) {
		int result = commentDao.deleteProjectOngoing(cmtNo);
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean modifyCommentProjectOngoing(Map<String, Object> comment) {
		if(commentDao.updateCommentProjectOngoing(comment) > 0) {
			return true;
		}else {
			return false;
		}
	}
	
}






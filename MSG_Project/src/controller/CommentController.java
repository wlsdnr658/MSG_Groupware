package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.CommentService;

@Controller
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	CommentService commentSerivce;
	
	///////////////////////////////////////notice 댓글///////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value="Notice",method = RequestMethod.POST)
	public boolean register(@RequestParam Map<String, Object> comment) {
		
		boolean result = commentSerivce.addComment(comment);
		
		if(result) {
			return true;
		}else {
			return false;			
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/allNotice/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> commentList(@PathVariable("boardNo") int boardNo){
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> commentList = commentSerivce.getBoardComment(boardNo);
			entity = new ResponseEntity<List<Map<String, Object>>>(commentList,HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateNotice/{cmtNo}", method =RequestMethod.POST)
	public ResponseEntity<String> update(@PathVariable("cmtNo")int cmtNo,@RequestParam Map<String, Object> content) {
		
		Map<String, Object> comment = new HashMap<String, Object>();
		String con = (String) content.get("commentContent");
		
		comment.put("content",con);
		comment.put("cmtNo",cmtNo);
		
		ResponseEntity<String> entity = null;
		
		if(commentSerivce.modifyComment(comment)) {
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}else {
			entity = new ResponseEntity<String>("FAIL",HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteNotice/{cmtNo}",method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("cmtNo")int cmtNo) {
		
		return commentSerivce.removeComment(cmtNo);
	}
	
	///////////////////////////////////////event 댓글///////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value="Event",method = RequestMethod.POST)
	public boolean registerEvent(@RequestParam Map<String, Object> comment) {
		boolean result = commentSerivce.addCommentEvent(comment);
		if(result) {
			return true;
		}else {
			return false;			
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/allEvent/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> commentListEvent(@PathVariable("boardNo") int boardNo){
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> commentList = commentSerivce.getBoardCommentEvent(boardNo);
			entity = new ResponseEntity<List<Map<String, Object>>>(commentList,HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateEvent/{cmtNo}", method =RequestMethod.POST)
	public ResponseEntity<String> updateEvent(@PathVariable("cmtNo")int cmtNo,@RequestParam Map<String, Object> content) {
		
		Map<String, Object> comment = new HashMap<String, Object>();
		String con = (String) content.get("commentContent");
		
		comment.put("content",con);
		comment.put("cmtNo",cmtNo);
		ResponseEntity<String> entity = null;
		if(commentSerivce.modifyCommentEvent(comment)) {
			entity 
			= new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}else {
			entity = new ResponseEntity<String>("FAIL",HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteEvent/{cmtNo}",method = RequestMethod.DELETE)
	public boolean deleteEvent(@PathVariable("cmtNo")int cmtNo) {
		return commentSerivce.removeCommentEvent(cmtNo);
	}
	///////////////////////////////////////it 댓글///////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value="It",method = RequestMethod.POST)
	public boolean registerIt(@RequestParam Map<String, Object> comment) {
		boolean result = commentSerivce.addCommentIt(comment);
		if(result) {
			return true;
		}else {
			return false;			
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/allIt/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> commentListIt(@PathVariable("boardNo") int boardNo){
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> commentList = commentSerivce.getBoardCommentIt(boardNo);
			entity = new ResponseEntity<List<Map<String, Object>>>(commentList,HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateIt/{cmtNo}", method =RequestMethod.POST)
	public ResponseEntity<String> updateIt(
			@PathVariable("cmtNo")int cmtNo,@RequestParam Map<String, Object> content) {
		
		Map<String, Object> comment = new HashMap<String, Object>();
		String con = (String) content.get("commentContent");
		
		comment.put("content",con);
		comment.put("cmtNo",cmtNo);
		ResponseEntity<String> entity = null;
		if(commentSerivce.modifyCommentIt(comment)) {
			entity 
			= new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}else {
			entity = new ResponseEntity<String>("FAIL",HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteIt/{cmtNo}",method = RequestMethod.DELETE)
	public boolean deleteIt(@PathVariable("cmtNo")int cmtNo) {
		return commentSerivce.removeCommentIt(cmtNo);
	}
	///////////////////////////////////////min 댓글///////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value="Min",method = RequestMethod.POST)
	public boolean registerMin(@RequestParam Map<String, Object> comment) {
		boolean result = commentSerivce.addCommentMin(comment);
		if(result) {
			return true;
		}else {
			return false;			
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/allMin/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> commentListMin(@PathVariable("boardNo") int boardNo){
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> commentList = commentSerivce.getBoardCommentMin(boardNo);
			entity = new ResponseEntity<List<Map<String, Object>>>(commentList,HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateMin/{cmtNo}", method =RequestMethod.POST)
	public ResponseEntity<String> updateMin(
			@PathVariable("cmtNo")int cmtNo,@RequestParam Map<String, Object> content) {
		
		Map<String, Object> comment = new HashMap<String, Object>();
		String con = (String) content.get("commentContent");
		
		comment.put("content",con);
		comment.put("cmtNo",cmtNo);
		ResponseEntity<String> entity = null;
		if(commentSerivce.modifyCommentMin(comment)) {
			entity 
			= new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}else {
			entity = new ResponseEntity<String>("FAIL",HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteMin/{cmtNo}",method = RequestMethod.DELETE)
	public boolean deleteMin(@PathVariable("cmtNo")int cmtNo) {
		return commentSerivce.removeCommentMin(cmtNo);
	}
	///////////////////////////////////////bp 댓글///////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value="Bp",method = RequestMethod.POST)
	public boolean registerBp(@RequestParam Map<String, Object> comment) {
		boolean result = commentSerivce.addCommentBp(comment);
		if(result) {
			return true;
		}else {
			return false;			
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/allBp/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> commentListBp(@PathVariable("boardNo") int boardNo){
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> commentList = commentSerivce.getBoardCommentBp(boardNo);
			entity = new ResponseEntity<List<Map<String, Object>>>(commentList,HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateBp/{cmtNo}", method =RequestMethod.POST)
	public ResponseEntity<String> updateBp(
			@PathVariable("cmtNo")int cmtNo,@RequestParam Map<String, Object> content) {
		
		Map<String, Object> comment = new HashMap<String, Object>();
		String con = (String) content.get("commentContent");
		
		comment.put("content",con);
		comment.put("cmtNo",cmtNo);
		ResponseEntity<String> entity = null;
		if(commentSerivce.modifyCommentBp(comment)) {
			entity 
			= new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}else {
			entity = new ResponseEntity<String>("FAIL",HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteBp/{cmtNo}",method = RequestMethod.DELETE)
	public boolean deleteBp(@PathVariable("cmtNo")int cmtNo) {
		return commentSerivce.removeCommentBp(cmtNo);
	}
	///////////////////////////////////////sales 댓글///////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value="Sales",method = RequestMethod.POST)
	public boolean registerSales(@RequestParam Map<String, Object> comment) {
		boolean result = commentSerivce.addCommentSales(comment);
		if(result) {
			return true;
		}else {
			return false;			
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/allSales/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> commentListSales(@PathVariable("boardNo") int boardNo){
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> commentList = commentSerivce.getBoardCommentSales(boardNo);
			entity = new ResponseEntity<List<Map<String, Object>>>(commentList,HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateSales/{cmtNo}", method =RequestMethod.POST)
	public ResponseEntity<String> updateSales(
			@PathVariable("cmtNo")int cmtNo,@RequestParam Map<String, Object> content) {
		
		Map<String, Object> comment = new HashMap<String, Object>();
		String con = (String) content.get("commentContent");
		
		comment.put("content",con);
		comment.put("cmtNo",cmtNo);
		ResponseEntity<String> entity = null;
		if(commentSerivce.modifyCommentSales(comment)) {
			entity 
			= new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}else {
			entity = new ResponseEntity<String>("FAIL",HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteSales/{cmtNo}",method = RequestMethod.DELETE)
	public boolean deleteSales(@PathVariable("cmtNo")int cmtNo) {
		return commentSerivce.removeCommentSales(cmtNo);
	}
	///////////////////////////////////////anonymity 댓글///////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value="Anonymity",method = RequestMethod.POST)
	public boolean registerAnonymity(@RequestParam Map<String, Object> comment) {
		boolean result = commentSerivce.addCommentAnonymity(comment);
		if(result) {
			return true;
		}else {
			return false;			
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/allAnonymity/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> commentListAnonymity(@PathVariable("boardNo") int boardNo){
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> commentList = commentSerivce.getBoardCommentAnonymity(boardNo);
			entity = new ResponseEntity<List<Map<String, Object>>>(commentList,HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateAnonymity/{cmtNo}", method =RequestMethod.POST)
	public ResponseEntity<String> updateAnonymity(
			@PathVariable("cmtNo")int cmtNo,@RequestParam Map<String, Object> content) {
		
		Map<String, Object> comment = new HashMap<String, Object>();
		String con = (String) content.get("commentContent");
		
		comment.put("content",con);
		comment.put("cmtNo",cmtNo);
		ResponseEntity<String> entity = null;
		if(commentSerivce.modifyCommentAnonymity(comment)) {
			entity 
			= new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}else {
			entity = new ResponseEntity<String>("FAIL",HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteAnonymity/{cmtNo}",method = RequestMethod.DELETE)
	public boolean deleteAnonymity(@PathVariable("cmtNo")int cmtNo) {
		return commentSerivce.removeCommentAnonymity(cmtNo);
	}
	
	



///////////////////////////////////////projectOngoing 댓글///////////////////////////////////////////////////////////////


	@ResponseBody
	@RequestMapping(value="ProjectOngoing",method = RequestMethod.POST)
	public boolean registerProjectOngoing(@RequestParam Map<String, Object> comment) {
		boolean result = commentSerivce.addCommentProjectOngoing(comment);
		if(result) {
			return true;
		}else {
			return false;			
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/allProjectOngoing/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> commentListProjectOngoing(@PathVariable("boardNo") int boardNo){
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> commentList = commentSerivce.getBoardCommentProjectOngoing(boardNo);
			entity = new ResponseEntity<List<Map<String, Object>>>(commentList,HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateProjectOngoing/{cmtNo}", method =RequestMethod.POST)
	public ResponseEntity<String> updateProjectOngoing(
			@PathVariable("cmtNo")int cmtNo,@RequestParam Map<String, Object> content) {
		
		Map<String, Object> comment = new HashMap<String, Object>();
		String con = (String) content.get("commentContent");
		
		comment.put("content",con);
		comment.put("cmtNo",cmtNo);
		ResponseEntity<String> entity = null;
		if(commentSerivce.modifyCommentProjectOngoing(comment)) {
			entity 
			= new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}else {
			entity = new ResponseEntity<String>("FAIL",HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteProjectONgoing/{cmtNo}",method = RequestMethod.DELETE)
	public boolean deleteProjectOngoing(@PathVariable("cmtNo")int cmtNo) {
		return commentSerivce.removeCommentProjectOngoing(cmtNo); 
	}



}













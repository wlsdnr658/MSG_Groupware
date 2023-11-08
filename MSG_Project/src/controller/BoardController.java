package controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController { 

	@Autowired
	private BoardService boardService;
	
	//게시판 메인 페이지 요청
	@RequestMapping("/boardMainForm")
	public String boardMainForm(Model model) {
		List<Map<String, Object>> noticeList = boardService.getAllNoticeList();
		List<Map<String, Object>> eventList = boardService.getAllEventList();
		model.addAttribute("noticeList",noticeList);
		model.addAttribute("eventList",eventList);
		return "boardMainForm";
	}
	
	/*///////////////////공지사항 컨트롤러//////////////////////*/
	
	//공지사항 페이지 요청
	@RequestMapping("/boardNotice")
	public String boardNotice(Model model
			,@RequestParam(required = false)String keyword
			,@RequestParam(defaultValue = "0")int type
			,@RequestParam(defaultValue = "1")int page) {
		
		Map<String, Object> notice = new HashMap<String,Object>();
		notice.put("type", type);
		notice.put("keyword", keyword);
		Map<String, Object> viewData = boardService.getNoticeList(notice, page);
		
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		
		model.addAttribute("viewData", viewData);
		
		return "boardNotice";
	}
	
	//공지사항 글쓰기 페이지 요청
	@RequestMapping("/boardNoticeWriteForm")
	public String boardNoticeWriteForm(Model model) {
		return "boardNoticeWriteForm";
	}
	//공지사항 쓰기 요청
	@RequestMapping(value="/boardNoticeWrite",method=RequestMethod.POST)
	public String boardNoticeWrite(@RequestParam Map<String, Object> notice
			,Model model
			,@RequestParam(value="uploadFile",required=false)List<MultipartFile> uploadFile) {
		
		if(boardService.writeNotice(notice, uploadFile)) {
			model.addAttribute("msg", "등록완료");
		}else {
			model.addAttribute("msg", "등록실패");
		}
		model.addAttribute("url", "boardNotice");
		return "result";
	}
	
	@RequestMapping("/boardNoticeDownload")
	public View boardNoticeDownload(@RequestParam Map<String, Object> map) {
			
		File file = boardService.getAttachNoticeFile(map);
		View view = new DownloadView(file);
		return view;
	}
	
	//공지사항 수정 페이지 요청
	@RequestMapping(value="/boardNoticeModifyForm")
	public String boardNoticeModifyForm(int boardNo, Model model) {
		model.addAttribute("notice",boardService.getNotice(boardNo));
		return "boardNoticeModifyForm";
	}
	@RequestMapping(value="/boardNoticeModify")
	public String boardNoticeModify(@RequestParam Map<String, Object> notice
			,Model model
			,@RequestParam(value="uploadFile",required=false)List<MultipartFile> uploadFile) {
		
		model.addAttribute("url","boardNotice");
		
		if(boardService.modifyNotice(notice,uploadFile)) {
			model.addAttribute("msg","수정완료");
		}else {
			model.addAttribute("msg","수정실패");
		}
		return "result";
	}
	//공지사항 상세보기 페이지 요청
	@RequestMapping(value="/boardNoticeViewForm")
	public String boardNoticeViewForm(int boardNo,Model model) {
		
		Map<String, Object> notice = boardService.readNotice(boardNo);
		model.addAttribute("previous",boardService.getPreviousNotice(boardNo));
		model.addAttribute("next",boardService.getNextNotice(boardNo));
		model.addAttribute("notice",notice);
		
		return "boardNoticeViewForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/boardNoticeViewFormFileDown/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> fileListOfNotice(@PathVariable("boardNo")int boardNo) {
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> fileList = boardService.getFileList(boardNo);
			entity = new ResponseEntity<List<Map<String, Object>>>(fileList,HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

	@RequestMapping("/boardNoticeDelete")
	public String boardNoticeDelete(@RequestParam Map<String, Object> notice,Model model) {
		model.addAttribute("url","boardNotice");
		if(boardService.removeNotice(notice)) {
			model.addAttribute("msg","삭제완료");
		}else {
			model.addAttribute("msg","삭제실패");
		}
		return "result";
	}
	
//	/boardCheckPassForm  : 비밀번호 확인 양식 요청
	@RequestMapping(value = "/boardNoticeCheckPassForm")
	public String boardCheckPassForm(int boardNo, Model model) {
		model.addAttribute("notice",boardService.getNotice(boardNo));
		return "boardNoticeCheckPass";
	}
//	/boardCheckPass 	 : 비밀번호 확인 요청
	@RequestMapping(value = "/boardNoticeCheckPass")
	public String boardNoticeCheckPass(@RequestParam Map<String,Object> notice,Model model) {
		//입력받은 비밀번호와 게시글의 비밀번호를 비교
		//성공하면, checkSuccess.jsp 실패하면 boardCheckPass.jsp
		model.addAttribute("notice",notice);
		if(boardService.checkNoticePass(notice)) {
			return "boardNoticeCheckSuccess";
		}else {
			model.addAttribute("msg","비밀번호가 일치하지 않습니다.");
			return "boardNoticeCheckPass"; 	
		}
	}	
	
	/*//////////////////////////////////////////////이벤트 컨트롤러/////////////////////////////////////////////*/
	
	//이벤트 게시판 페이지 요청
	@RequestMapping("/boardEvent")
	public String boardEvent(Model model
	,@RequestParam(required = false)String keyword
	,@RequestParam(defaultValue = "0")int type
	,@RequestParam(defaultValue = "1")int page) {
		
		Map<String, Object> event = new HashMap<String,Object>();
		
		event.put("keyword", keyword);
		event.put("type", type);
		
		Map<String, Object> viewData = boardService.getEventList(event, page);
		Map<String, Object> noticeOfEvent = boardService.getEventList1(event);
		viewData.put("keyword", keyword);
		viewData.put("type", type);
		
		model.addAttribute("viewData", viewData);
		model.addAttribute("noticeOfEvent",noticeOfEvent);
		return "boardEvent";
	}
	//이벤트 게시판 글쓰기 페이지 요청
	@RequestMapping("/boardEventWriteForm")
	public String boardEventWriteForm() {
		return "boardEventWriteForm";
	}
	//이벤트 게시판 글쓰기 요청
	@RequestMapping(value="/boardEventWrite",method=RequestMethod.POST)
	public String boardEventWrite(@RequestParam Map<String, Object> event
			,Model model
			,@RequestParam(value="uploadFile",required=false)List<MultipartFile> uploadFile) {
	
		if(boardService.writeEvent(event, uploadFile)) {
			model.addAttribute("msg", "등록완료");
		}else {
			model.addAttribute("msg", "등록실패");
		}
		
		model.addAttribute("url", "boardEvent");
		return "result";
	}
	
	@RequestMapping("/boardEventDownload")
	public View boardEventDownload(@RequestParam Map<String, Object> map) {
			
		File file = boardService.getAttachEventFile(map);
		View view = new DownloadView(file);
		return view;
	}
	
	//이벤트 게시판 글삭제 요청
	@RequestMapping("/boardEventDelete")
	public String boardEventDelete(@RequestParam Map<String, Object> event,Model model) {
		model.addAttribute("url","boardEvent");
		if(boardService.removeEvent(event)) {
			model.addAttribute("msg","삭제완료");
		}else {
			model.addAttribute("msg","삭제실패");
		}
		return "result";
	}
	//이벤트 게시판 수정 페이지 요청
	@RequestMapping(value="/boardEventModifyForm")
	public String boardEventModifyForm(int boardNo, Model model) {
		model.addAttribute("event",boardService.getEvent(boardNo));
		return "boardEventModifyForm";
	}
	@RequestMapping(value="/boardEventModify")
	public String boardEventModify(@RequestParam Map<String, Object> event
			,Model model
			,@RequestParam(value="uploadFile",required=false)List<MultipartFile> uploadFile) {
		model.addAttribute("url","boardEvent");
		if(boardService.modifyEvent(event,uploadFile)) {
			model.addAttribute("msg","수정완료");
		}else {
			model.addAttribute("msg","수정실패");
		}
		return "result";
	}
	//이벤트 게시판 상세보기 페이지 요청
	@RequestMapping(value="/boardEventViewForm")
	public String boardEventViewForm(int boardNo,Model model) {
		
		Map<String, Object> event = boardService.readEvent(boardNo);
		model.addAttribute("previous",boardService.getPreviousEvent(boardNo));
		model.addAttribute("next",boardService.getNextEvent(boardNo));
		model.addAttribute("event",event);
		return "boardEventViewForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/boardEventViewFormFileDown/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> fileListOfEvent(@PathVariable("boardNo")int boardNo) {
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> fileList = boardService.getFileListEvent(boardNo);
			entity = new ResponseEntity<List<Map<String, Object>>>(fileList,HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/boardEventCheckPassForm")
	public String boardEventCheckPassForm(int boardNo, Model model) {
		model.addAttribute("event",boardService.getEvent(boardNo));
		return "boardEventCheckPass";
	}
//	/boardCheckPass 	 : 비밀번호 확인 요청
	@RequestMapping(value = "/boardEventCheckPass")
	public String boardEventCheckPass(@RequestParam Map<String,Object> event,Model model) {
		model.addAttribute("event",event);
		if(boardService.checkEventPass(event)) {
			return "boardEventCheckSuccess";
		}else {
			model.addAttribute("msg","비밀번호가 일치하지 않습니다.");
			return "boardEventCheckPass"; 	
		}
	}
	
	
	/*///////////////////부서 게시판 컨트롤러//////////////////////*/
	
	/////////////////////////////////////////////it 부서 ////////////////////////////////////////
	
	@RequestMapping("/boardDeptIt")
	public String boardDeptIt(Model model
	,@RequestParam(required = false)String keyword
	,@RequestParam(defaultValue = "0")int type
	,@RequestParam(defaultValue = "1")int page) {
		Map<String, Object> it = new HashMap<String,Object>();
		it.put("type", type);
		it.put("keyword", keyword);
		Map<String, Object> viewData = boardService.getITList(it, page);
		Map<String, Object> noticeOfIt = boardService.getITList1(it);
		model.addAttribute("noticeOfIt",noticeOfIt);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		model.addAttribute("viewData", viewData);
		return "boardDeptIt";
	}
	@RequestMapping("/boardDeptItWriteForm")
	public String boardDeptItWriteForm() {
		return "boardDeptItWriteForm";
	}
	@RequestMapping(value="/boardDeptItWrite",method=RequestMethod.POST)
	public String boardDeptItWrite(@RequestParam Map<String, Object> it
			,Model model
			,@RequestParam(value="uploadFile", required=false)List<MultipartFile> uploadFile) {
		
		if(boardService.writeIT(it, uploadFile)) {
			model.addAttribute("msg", "등록완료");
		}else {
			model.addAttribute("msg", "등록실패");
		}
		model.addAttribute("url", "boardDeptIt");
		return "result";
	}
	
	@RequestMapping("/boardDeptItDownload")
	public View boardDeptItDownload(@RequestParam Map<String, Object> map) {
			
		File file = boardService.getAttachItFile(map);
		
		View view = new DownloadView(file);
		
		return view;
	}
	
	@RequestMapping("/boardDeptItDelete")
	public String boardITDelete(@RequestParam Map<String, Object> it,Model model) {
		model.addAttribute("url","boardDeptIt");
		if(boardService.removeIT(it)) {
			model.addAttribute("msg","삭제완료");
		}else {
			model.addAttribute("msg","삭제실패");
		}
		return "result";
	}
	@RequestMapping(value="/boardDeptItModifyForm")
	public String boardDeptItModifyForm(int boardNo, Model model) {
		model.addAttribute("it",boardService.getIT(boardNo));
		return "boardDeptItModifyForm";
	}
	@RequestMapping(value="/boardDeptItModify")
	public String boardDeptItModify(@RequestParam Map<String, Object> it
			,Model model
			,@RequestParam(value="uploadFile",required=false)List<MultipartFile> uploadFile) {
		model.addAttribute("url","boardDeptIt");
		if(boardService.modifyIT(it,uploadFile)) {
			model.addAttribute("msg","수정완료");
		}else {
			model.addAttribute("msg","수정실패");
		}
		return "result";
	}
	@RequestMapping(value="/boardDeptItViewForm")
	public String boardDeptItViewForm(int boardNo,Model model) {
		
		Map<String, Object> it = boardService.readIT(boardNo);
		model.addAttribute("previous",boardService.getPreviousIT(boardNo));
		model.addAttribute("next",boardService.getNextIT(boardNo));
		model.addAttribute("it",it);
		return "boardDeptItViewForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/boardItViewFormFileDown/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> fileListOfIt(@PathVariable("boardNo")int boardNo) {
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> fileList = boardService.getFileListIT(boardNo);
			
			entity = new ResponseEntity<List<Map<String, Object>>>(fileList,HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/boardDeptItCheckPassForm")
	public String boardDeptItCheckPassForm(int boardNo, Model model) {
		model.addAttribute("it",boardService.getIT(boardNo));
		return "boardDeptItCheckPass";
	}
	@RequestMapping(value = "/boardDeptItCheckPass")
	public String boardDeptItCheckPass(@RequestParam Map<String,Object> it,Model model) {
		model.addAttribute("it",it);
		if(boardService.checkITPass(it)) {
			return "boardDeptItCheckSuccess";
		}else {
			model.addAttribute("msg","비밀번호가 일치하지 않습니다.");
			return "boardDeptItCheckPass"; 	
		}
	}
	
	/////////////////////////////////////////////경영기획부서 ////////////////////////////////////////
	
	@RequestMapping("/boardDeptBP")
	public String boardDeptBP(Model model
	,@RequestParam(required = false)String keyword
	,@RequestParam(defaultValue = "0")int type
	,@RequestParam(defaultValue = "1")int page) {
		Map<String, Object> bp = new HashMap<String,Object>();
		bp.put("keyword", keyword);
		bp.put("type", type);
		Map<String, Object> viewData = boardService.getBPList(bp, page);
		Map<String, Object> noticeOfBp = boardService.getBPList1(bp);
		model.addAttribute("noticeOfBp",noticeOfBp);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		model.addAttribute("viewData", viewData);
		return "boardDeptBP";
	}
	@RequestMapping("/boardDeptBpWriteForm")
	public String boardDeptBpWriteForm() {
		return "boardDeptBpWriteForm";
	}
	@RequestMapping(value="/boardDeptBpWrite",method=RequestMethod.POST)
	public String boardDeptBpWirte(@RequestParam Map<String, Object> bp
			,Model model
			,@RequestParam(value="uploadFile", required=false)List<MultipartFile> uploadFile) {
		if(boardService.writeBP(bp, uploadFile)) {
			model.addAttribute("msg", "등록완료");
		}else {
			model.addAttribute("msg", "등록실패");
		}
		model.addAttribute("url", "boardDeptBP");
		return "result";
	}
	@RequestMapping("/boardDeptBpDownload")
	public View boardDeptBpDownload(@RequestParam Map<String, Object> map) {
			
		File file = boardService.getAttachBPFile(map);
		
		View view = new DownloadView(file);
		
		return view;
	}
	
	@RequestMapping("/boardDeptBpDelete")
	public String boardBpDelete(@RequestParam Map<String, Object> bp,Model model) {
		model.addAttribute("url","boardDeptBP");
		if(boardService.removeBP(bp)) {
			model.addAttribute("msg","삭제완료");
		}else {
			model.addAttribute("msg","삭제실패");
		}
		return "result";
	}
	@RequestMapping(value="/boardDeptBpModifyForm")
	public String boardDeptBpModifyForm(int boardNo, Model model) {
		model.addAttribute("bp",boardService.getBP(boardNo));
		return "boardDeptBpModifyForm";
	}
	@RequestMapping(value="/boardDeptBpModify")
	public String boardDeptBpModify(@RequestParam Map<String, Object> bp
			,Model model
			,@RequestParam(value="uploadFile",required=false)List<MultipartFile> uploadFile) {
		model.addAttribute("url","boardDeptBP");
		if(boardService.modifyBP(bp,uploadFile)) {
			model.addAttribute("msg","수정완료");
		}else {
			model.addAttribute("msg","수정실패");
		}
		return "result";
	}
	@RequestMapping(value="/boardDeptBpViewForm")
	public String boardDeptBpViewForm(int boardNo,Model model) {
		
		Map<String, Object> bp = boardService.readBP(boardNo);
		model.addAttribute("previous",boardService.getPreviousBP(boardNo));
		model.addAttribute("next",boardService.getNextBP(boardNo));
		model.addAttribute("bp",bp);
		return "boardDeptBpViewForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/boardBpViewFormFileDown/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> fileListOfBp(@PathVariable("boardNo")int boardNo) {
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> fileList = boardService.getFileListBP(boardNo);
			
			entity = new ResponseEntity<List<Map<String, Object>>>(fileList,HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	@RequestMapping(value = "/boardDeptBpCheckPassForm")
	public String boardDeptBpCheckPassForm(int boardNo, Model model) {
		model.addAttribute("bp",boardService.getBP(boardNo));
		return "boardDeptBpCheckPass";
	}
	@RequestMapping(value = "/boardDeptBpCheckPass")
	public String boardDeptBpCheckPass(@RequestParam Map<String,Object> bp,Model model) {
		model.addAttribute("bp",bp);
		if(boardService.checkBPPass(bp)) {
			return "boardDeptBpCheckSuccess";
		}else {
			model.addAttribute("msg","비밀번호가 일치하지 않습니다.");
			return "boardDeptBpCheckPass"; 	
		}
	}
	
	
	
	///////////////////////////////////////////재무부서 ////////////////////////////////////////////
	
	@RequestMapping("/boardDeptMin")
	public String boardDeptMin(Model model
	,@RequestParam(required = false)String keyword
	,@RequestParam(defaultValue = "0")int type
	,@RequestParam(defaultValue = "1")int page) {
		Map<String, Object> min = new HashMap<String,Object>();
		min.put("keyword", keyword);
		min.put("type", type);
		//param에 keyword랑, type넣어주면됨
		Map<String, Object> viewData = boardService.getMinList(min, page);
		Map<String, Object> noticeOfMin = boardService.getMinList1(min);
		model.addAttribute("noticeOfMin",noticeOfMin);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		model.addAttribute("viewData", viewData);
		return "boardDeptMin";
	}
	@RequestMapping("/boardDeptMinWriteForm")
	public String boardDeptMinWriteForm() {
		return "boardDeptMinWriteForm";
	}
	@RequestMapping(value="/boardDeptMinWrite",method=RequestMethod.POST)
	public String boardDeptMinWrite(@RequestParam Map<String, Object> min
			,Model model
			,@RequestParam(value="uploadFile", required=false)List<MultipartFile> uploadFile) {
		if(boardService.writeMin(min, uploadFile)) {
			model.addAttribute("msg", "등록완료");
		}else {
			model.addAttribute("msg", "등록실패");
		}
		model.addAttribute("url", "boardDeptMin");
		return "result";
	}
	
	@RequestMapping("/boardDeptMinDownload")
	public View boardDeptMinDownload(@RequestParam Map<String, Object> map) {
		
		File file = boardService.getAttachMinFile(map);
		
		View view = new DownloadView(file);
		
		return view;
	}
	
	@RequestMapping("/boardDeptMinDelete")
	public String boardMinDelete(@RequestParam Map<String, Object> min,Model model) {
		model.addAttribute("url","boardDeptMin");
		if(boardService.removeMin(min)) {
			model.addAttribute("msg","삭제완료");
		}else {
			model.addAttribute("msg","삭제실패");
		}
		return "result";
	}
	@RequestMapping(value="/boardDeptMinModifyForm")
	public String boardDeptMinModifyForm(int boardNo, Model model) {
		model.addAttribute("min",boardService.getMin(boardNo));
		return "boardDeptMinModifyForm";
	}
	@RequestMapping(value="/boardDeptMinModify")
	public String boardDeptMinModify(@RequestParam Map<String, Object> min
			,Model model
			,@RequestParam(value="uploadFile",required=false)List<MultipartFile> uploadFile) {
		model.addAttribute("url","boardDeptMin");
		if(boardService.modifyMin(min,uploadFile)) {
			model.addAttribute("msg","수정완료");
		}else {
			model.addAttribute("msg","수정실패");
		}
		return "result";
	}
	@RequestMapping(value="/boardDeptMinViewForm")
	public String boardDeptMinViewForm(int boardNo,Model model) {
		
		Map<String, Object> min = boardService.readMin(boardNo);
		model.addAttribute("previous",boardService.getPreviousMin(boardNo));
		model.addAttribute("next",boardService.getNextMin(boardNo));
		model.addAttribute("min",min);
		return "boardDeptMinViewForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/boardMinViewFormFileDown/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> fileListOfMin(@PathVariable("boardNo")int boardNo) {
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> fileList = boardService.getFileListMin(boardNo);
			
			entity = new ResponseEntity<List<Map<String, Object>>>(fileList,HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/boardDeptMinCheckPassForm")
	public String boardDeptMinCheckPassForm(int boardNo, Model model) {
		model.addAttribute("min",boardService.getMin(boardNo));
		return "boardDeptMinCheckPass";
	}
	@RequestMapping(value = "/boardDeptMinCheckPass")
	public String boardDeptMinCheckPass(@RequestParam Map<String,Object> min,Model model) {
		model.addAttribute("min",min);
		if(boardService.checkMinPass(min)) {
			return "boardDeptMinCheckSuccess";
		}else {
			model.addAttribute("msg","비밀번호가 일치하지 않습니다.");
			return "boardDeptMinCheckPass"; 	
		}
	}
	
	
	///////////////////////////////////////////영업부서 ////////////////////////////////////////////
	
	@RequestMapping("/boardDeptSales")
	public String boardDeptSales(Model model
	,@RequestParam(required = false)String keyword
	,@RequestParam(defaultValue = "0")int type
	,@RequestParam(defaultValue = "1")int page) {
		
		Map<String, Object> sales = new HashMap<String,Object>();
		sales.put("keyword", keyword);
		sales.put("type", type);
		Map<String, Object> viewData = boardService.getSalesList(sales, page);
		Map<String, Object> noticeOfSales = boardService.getSalesList1(sales);
		model.addAttribute("noticeOfSales",noticeOfSales);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		
		model.addAttribute("viewData", viewData);
		return "boardDeptSales";
	}
	@RequestMapping("/boardDeptSalesWriteForm")
	public String boardDeptSalesWriteForm() {
		return "boardDeptSalesWriteForm";
	}
	@RequestMapping(value="/boardDeptSalesWrite",method=RequestMethod.POST)
	public String boardDeptSalesWrite(@RequestParam Map<String, Object> sales
			,Model model
			,@RequestParam(value="uploadFile", required=false)List<MultipartFile> uploadFile) {
		
		if(boardService.writeSales(sales, uploadFile)) {
			model.addAttribute("msg", "등록완료");
		}else {
			model.addAttribute("msg", "등록실패");
		}
		model.addAttribute("url", "boardDeptSales");
		return "result";
	}
	
	@RequestMapping("/boardDeptSalesDownload")
	public View boardDeptSalesDownload(@RequestParam Map<String, Object> map) {
			
		File file = boardService.getAttachSalesFile(map);
		
		View view = new DownloadView(file);
		
		return view;
	}
	
	@RequestMapping("/boardDeptSalesDelete")
	public String boardSalesDelete(@RequestParam Map<String, Object> sales,Model model) {
		model.addAttribute("url","boardDeptSales");
		if(boardService.removeSales(sales)) {
			model.addAttribute("msg","삭제완료");
		}else {
			model.addAttribute("msg","삭제실패");
		}
		return "result";
	}
	@RequestMapping(value="/boardDeptSalesModifyForm")
	public String boardDeptSalesModifyForm(int boardNo, Model model) {
		model.addAttribute("sales",boardService.getSales(boardNo));
		return "boardDeptSalesModifyForm";
	}
	@RequestMapping(value="/boardDeptSalesModify")
	public String boardDeptSalesModify(@RequestParam Map<String, Object> sales
			,Model model
			,@RequestParam(value="uploadFile",required=false)List<MultipartFile> uploadFile) {
		model.addAttribute("url","boardDeptSales");
		if(boardService.modifySales(sales,uploadFile)) {
			model.addAttribute("msg","수정완료");
		}else {
			model.addAttribute("msg","수정실패");
		}
		
		return "result";
	}
	@RequestMapping(value="/boardDeptSalesViewForm")
	public String boardDeptSalesViewForm(int boardNo,Model model) {
		
		Map<String, Object> sales = boardService.readSales(boardNo);
		model.addAttribute("previous",boardService.getPreviousSales(boardNo));
		model.addAttribute("next",boardService.getNextSales(boardNo));
		model.addAttribute("sales",sales);
		return "boardDeptSalesViewForm";
	}
	@ResponseBody
	@RequestMapping(value="/boardSalesViewFormFileDown/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> fileListOfSales(@PathVariable("boardNo")int boardNo) {
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> fileList = boardService.getFileListSales(boardNo);
			
			entity = new ResponseEntity<List<Map<String, Object>>>(fileList,HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	@RequestMapping(value = "/boardDeptSalesCheckPassForm")
	public String boardDeptSalesCheckPassForm(int boardNo, Model model) {
		model.addAttribute("sales",boardService.getSales(boardNo));
		return "boardDeptSalesCheckPass";
	}
	@RequestMapping(value = "/boardDeptSalesCheckPass")
	public String boardDeptSalesCheckPass(@RequestParam Map<String,Object> sales,Model model) {
		model.addAttribute("sales",sales);
		if(boardService.checkSalesPass(sales)) {
			return "boardDeptSalesCheckSuccess";
		}else {
			model.addAttribute("msg","비밀번호가 일치하지 않습니다.");
			return "boardDeptSalesCheckPass"; 	
		}
	}
	
	
	
	/*///////////////////프로젝트 게시판 컨트롤러//////////////////////*/
	
	//진행중 프로젝트 게시판
	@RequestMapping("/boardProjectOngoing")
	public String boardProjectOngoing(Model model
	,@RequestParam(required = false)String keyword
	,@RequestParam(defaultValue = "0")int type
	,@RequestParam(defaultValue = "1")int page) {
		
		Map<String, Object> projectOngoing = new HashMap<String,Object>();
		projectOngoing.put("keyword", keyword);
		projectOngoing.put("type", type);
		Map<String, Object> viewData = boardService.getProjectOngoingList(projectOngoing, page);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		
		model.addAttribute("viewData", viewData);
		return "boardProjectOngoing";
	}
	
	
	@RequestMapping("/boardProjectFinished")
	public String boardProjectFinished(Model model
	,@RequestParam(required = false)String keyword
	,@RequestParam(defaultValue = "0")int type
	,@RequestParam(defaultValue = "1")int page) {
		
		Map<String, Object> projectFinished = new HashMap<String,Object>();
		projectFinished.put("keyword", keyword);
		projectFinished.put("type", type);
		Map<String, Object> viewData = boardService.getProjectFinishedList(projectFinished, page);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		
		model.addAttribute("viewData", viewData);
		return "boardProjectFinished";
	}
	
	
	//프로젝트 게시판 글쓰기 페이지 요청
	@RequestMapping("/boardProjectWriteForm")
	public String boardProjectWriteForm() {
		return "boardProjectWriteForm";
	}
	
	//프로젝트 게시판 글쓰기 요청
	@RequestMapping(value="/boardProjectWrite",method=RequestMethod.POST)
	public String boardProjectWrite(@RequestParam Map<String, Object> projectOngoing
			,Model model
			,@RequestParam(value="uploadFile", required=false)List<MultipartFile> uploadFile
			,String[] PARTICIPANT) {
		
		if (boardService.writeProjectOngoing(projectOngoing, uploadFile)) {

			Map<String, Object> participant = new HashMap<String, Object>();

			if (PARTICIPANT != null) {

				for (String str : PARTICIPANT) {
					participant.put("PARTICIPANT", str);
					boardService.insertParticipant(participant);
				}

			}

			model.addAttribute("msg", "등록완료");

		} else {
			model.addAttribute("msg", "등록실패");
		}

		model.addAttribute("url", "boardProjectOngoing");

		return "result";
	}
	
	//프로젝트 게시판 상세보기 페이지 요청
	@RequestMapping(value="/boardProjectOngoingViewForm")
	public String boardProjectOngoingViewForm(int boardNo,
			Model model
			,@RequestParam(defaultValue = "1")int page) {
		
		Map<String, Object> projectOngoing = boardService.readProjectOngoing(boardNo);
		model.addAttribute("previous",boardService.getPreviousProjectOngoing(boardNo));
		model.addAttribute("next",boardService.getNextProjectOngoing(boardNo));
		model.addAttribute("projectOngoing",projectOngoing); 
		
		Map<String, Object> task = new HashMap<String,Object>();
		task.put("boardNo",boardNo);
		Map<String, Object> viewData = boardService.getProjectOngoingTaskList(task, page);
		model.addAttribute("viewData", viewData);
		
		return "boardProjectOngoingViewForm";
	}
	@ResponseBody
	@RequestMapping(value="/boardProjectOngoingViewFormFileDown/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> fileListOfProjectOngoing(@PathVariable("boardNo")int boardNo) {
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> fileList = boardService.getFileListProjectOngoing(boardNo);
			
			entity = new ResponseEntity<List<Map<String, Object>>>(fileList,HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	@ResponseBody
	@RequestMapping(value="/boardProjectOngoingTaskViewFormFileDown/{taskNo}")
	public ResponseEntity<List<Map<String, Object>>> fileListOfProjectOngoingOfTask(@PathVariable("taskNo")int taskNo) {
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> fileList = boardService.getFileListProjectOngoingTask(taskNo);
			
			entity = new ResponseEntity<List<Map<String, Object>>>(fileList,HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value="/boardProjectFinishedViewForm")
	public String boardProjectFinishedViewForm(int boardNo,
			Model model
			,@RequestParam(defaultValue = "1")int page) {
		
		Map<String, Object> projectFinished = boardService.readProjectOngoing(boardNo);
		model.addAttribute("previous",boardService.getPreviousProjectFinished(boardNo));
		model.addAttribute("next",boardService.getNextProjectFinished(boardNo));
		model.addAttribute("projectFinished",projectFinished); 
		
		Map<String, Object> task = new HashMap<String,Object>();
		task.put("boardNo",boardNo);
		Map<String, Object> viewData = boardService.getProjectOngoingTaskList(task, page);
		model.addAttribute("viewData", viewData);
		
		return "boardProjectFinishedViewForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "/allParticipant/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> participantList(@PathVariable("boardNo") int boardNo){
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> participantList = boardService.getParticipant(boardNo);
			entity = new ResponseEntity<List<Map<String, Object>>>(participantList,HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getProjectOngoing/{boardNo}")
	public ResponseEntity<Map<String, Object>> getProjectOngoing(@PathVariable("boardNo") int boardNo){
		
		ResponseEntity<Map<String, Object>> entity = null;
		try {
			Map<String, Object> participantList = boardService.getProjectOngoing(boardNo);
			entity = new ResponseEntity<Map<String, Object>>(participantList,HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
	@RequestMapping(value = "/boardProjectOngoingAddTaskForm")
	public String boardProjectOngoingAddTaskForm(int boardNo, Model model) {
		
		model.addAttribute("projectOngoing",boardService.getProjectOngoing(boardNo));
		
		return "boardProjectOngoingAddTaskForm";
	}
	
	@RequestMapping(value = "/boardProjectOngoingAddTask")
	public String boardProjectOngoingAddTask(@RequestParam Map<String,Object> task ,Model model ,List<MultipartFile> uploadFile, int boardNo) {
		
		if(boardService.writeTask(task,uploadFile,boardNo)) {
			model.addAttribute("msg", "등록완료");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("boardNo", boardNo);
			model.addAttribute("map",map);
			return  "boardProjectOngoingAddTaskSuccess";
			
		}else {
			model.addAttribute("msg", "등록실패");
			model.addAttribute("url", "boardProjectOngoingAddTaskForm");
			return "result";
		}
	}	
	
	@RequestMapping(value = "/boardProjectOngoingAddedTaskViewForm")
	public String boardProjectOngoingAddTaskViewForm(int taskNo,Model model) {
		
		Map<String, Object> task = boardService.readProjectOngoingTask(taskNo);
		model.addAttribute("task",task);
		
		return "boardProjectOngoingAddedTaskViewForm";
	}
	
	@RequestMapping(value = "/boardProjectOngoingCheckPassForm")
	public String boardProjectOngoingCheckPassForm(int boardNo, Model model) {
		model.addAttribute("projectOngoing",boardService.getProjectOngoing(boardNo));
		return "boardProjectOngoingCheckPass";
	}
	
	@RequestMapping(value = "/boardProjectOngoingCheckPass")
	public String boardProjectOngoingCheckPass(@RequestParam Map<String,Object> projectOngoing,Model model) {
		
		model.addAttribute("projectOngoing",projectOngoing);
		
		if(boardService.checkProjectOngoingPass(projectOngoing)) {
			return "boardProjectOngoingCheckSuccess";
		}else {
			model.addAttribute("msg","비밀번호가 일치하지 않습니다.");
			return "boardProjectOngoingCheckPass"; 	
		}
		
	}
	
	
	@RequestMapping("/boardProjectOngoingDelete")
	public String boardProjectOngoingDelete(@RequestParam Map<String, Object> projectOngoing,Model model) {
		
		if(boardService.removeProjectOngoing(projectOngoing)) {
			model.addAttribute("msg","삭제완료");
			model.addAttribute("url","boardProjectOngoing");
		}else {
			model.addAttribute("msg","삭제실패");
			model.addAttribute("url","boardProjectOngoing");
		}
		return "result";
	}
	
	@RequestMapping("/boardProjectOngoingOfTaskDelete")
	public String boardProjectOngoingOfTaskDelete(@RequestParam Map<String, Object> task,Model model) {
		
		String boardNo = (String)task.get("boardNo");
		
		if(boardService.removeProjectOngoingOfTask(task)) {
			model.addAttribute("msg","삭제완료");
			model.addAttribute("url","boardProjectOngoingViewForm?boardNo="+boardNo);
		}else {
			model.addAttribute("msg","삭제실패");
			model.addAttribute("url","boardProjectOngoingViewForm?boardNo="+boardNo);
		}
		return "result";
	}
	
	@RequestMapping("/boardProjectOngoingDownload")
	public View boardProjectOngoingDownload(@RequestParam Map<String, Object> map) {
			
		File file = boardService.getAttachProjectOngoingFile(map);
		
		View view = new DownloadView(file);
		
		
		return view;
	}
	
	@RequestMapping("/boardProjectOngoingDownloadOfTask")
	public View boardProjectOngoingDownloadOfTask (@RequestParam Map<String, Object> map,Model model) {
		
		File file = boardService.getAttachProjectOngoingFileOfTask(map);
		
		View view = new DownloadView(file);
		
		return view;
	}
	  
	@RequestMapping(value = "/boardProjectOngoingOfTaskAttachDownloadForm")
	public String boardProjectOngoingOfTaskAttachDownloadForm(int taskNo, Model model) {
		
		Map<String, Object> task = new HashMap<String,Object>();
		task.put("taskNo", taskNo);
		model.addAttribute("task",task);
		
		return "boardProjectOngoingOfTaskAttachDownloadForm";
	}
	
	
	@RequestMapping(value="/boardProjectOngoingModifyForm")
	public String boardProjectOngoingModifyForm(int boardNo, Model model) {
		model.addAttribute("projectOngoing",boardService.getProjectOngoing(boardNo));
		model.addAttribute("participant",boardService.getParticipant(boardNo));
		return "boardProjectModifyForm";
	}
	
	@RequestMapping(value="/boardProjectOngoingModify")
	public String boardProjectOngoingModify(@RequestParam Map<String, Object> projectOngoing
			, Model model
			,int boardNo
			,@RequestParam(value="uploadFile", required=false)List<MultipartFile> uploadFile) {
		
		if(boardService.modifyProjectOngoing(projectOngoing,uploadFile)) {
			model.addAttribute("url","boardProjectOngoingViewForm?boardNo="+boardNo);
			model.addAttribute("msg","수정완료");
		}else {
			model.addAttribute("url","boardProjectOngoingViewForm?boardNo="+boardNo);
			model.addAttribute("msg","수정실패");
		}
		return "result";
	}
	
	@RequestMapping(value="/boardProjectOngoingAddTaskModifyForm/{taskNo}")
	public String boardProjectOngoingAddTaskModifyForm(@PathVariable("taskNo")int taskNo, Model model) {
		model.addAttribute("task",boardService.getProjectOngoingTask(taskNo));
		return "boardProjectOngoingAddTaskModifyForm";
	}
	
	@RequestMapping(value="/boardProjectOngoingTaskModify")
	public String boardProjectOngoingTaskModify(@RequestParam Map<String, Object> task
			, Model model
			,int taskNo
			,@RequestParam(value="uploadFile", required=false)List<MultipartFile> uploadFile) {
		
		if(boardService.modifyProjectOngoingTask(task,uploadFile,taskNo)) {
			model.addAttribute("url","boardProjectOngoingAddedTaskViewForm?taskNo="+taskNo);
			model.addAttribute("msg","수정완료");
		}else {
			model.addAttribute("url","boardProjectOngoingAddedTaskViewForm?taskNo="+taskNo);
			model.addAttribute("msg","수정실패");
		}
		return "result";
	}  
	
	/////////////////////////////////////////////////// 익명 게시판 컨트롤러 /////////////////////////////////////////////////////////
	
	@RequestMapping("/boardAnonymity")
	public String boardAnonymtiy(Model model
			,@RequestParam(required = false)String keyword
			,@RequestParam(defaultValue = "0")int type
			,@RequestParam(defaultValue = "1")int page) {
		
		Map<String, Object> anonymity = new HashMap<String,Object>();
		anonymity.put("type", type);
		anonymity.put("keyword", keyword);
		
		Map<String, Object> viewData = boardService.getAnonymityList(anonymity, page);
		Map<String, Object> noticeOfAnonymity = boardService.getAnonymityList1(anonymity);
		model.addAttribute("noticeOfAnonymity",noticeOfAnonymity);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		
		model.addAttribute("viewData", viewData);
		return "boardAnonymity";
	}
	
	@RequestMapping("/boardAnonymityWriteForm")
	public String boardAnonymityWriteForm() {
		return "boardAnonymityWriteForm";
	}

	@RequestMapping(value="/boardAnonymityWrite",method=RequestMethod.POST)
	public String boardAnonymityWrite (@RequestParam Map<String, Object> anonymity
			,Model model
			,@RequestParam(value="uploadFile", required=false)List<MultipartFile> uploadFile) {
		
		if(boardService.writeAnonymity(anonymity, uploadFile)) {
			model.addAttribute("msg", "등록완료");
		}else {
			model.addAttribute("msg", "등록실패");
		}
		
		model.addAttribute("url", "boardAnonymity");
		return "result";
	}
	
	@RequestMapping("/boardAnonymityDownload")
	public View boardAnonymityDownload(@RequestParam Map<String, Object> map) {
		
		File file = boardService.getAttachAnonymityFile(map);
		View view = new DownloadView(file);
		
		return view;
	}
	
	
	@RequestMapping(value="/boardAnonymityModifyForm")
	public String boardAnonymityModifyForm(int boardNo, Model model) {
		model.addAttribute("anonymity",boardService.getAnonymity(boardNo));
		return "boardAnonymityModifyForm";
	}
	
	@RequestMapping(value="/boardAnonymityModify")
	public String boardAnonymityModify(@RequestParam Map<String, Object> anonymity
			, Model model
			,int boardNo
			,@RequestParam(value="uploadFile",required=false)List<MultipartFile> uploadFile) {
		
			
		if(boardService.modifyAnonymity(anonymity,uploadFile)) {
			model.addAttribute("url","boardAnonymityViewForm?boardNo="+boardNo);
			model.addAttribute("msg","수정완료");
		}else {
			model.addAttribute("url","boardAnonymity");
			model.addAttribute("msg","수정실패");
		}
		
		return "result";
	}
	@RequestMapping(value="/boardAnonymityViewForm")
	public String boardAnonymityViewForm(int boardNo,Model model) {
		
		List<Map<String, Object>> fileList = boardService.getFileListAnonymity(boardNo);
		if(fileList.get(0) != null) {
			model.addAttribute("fileList",fileList);
		}else {
			Map<String, Object> fileList1 = new HashMap<String, Object>();
			fileList1.put("key", 1);
			fileList.remove(0);
			fileList.add(fileList1);
			model.addAttribute("fileList",fileList);
		}
		
		Map<String, Object> anonymity = boardService.readAnonymity(boardNo);
		model.addAttribute("previous",boardService.getPreviousAnonymity(boardNo));
		model.addAttribute("next",boardService.getNextAnonymity(boardNo));
		model.addAttribute("anonymity",anonymity); 
		return "boardAnonymityViewForm";
	}
	
	@RequestMapping("/boardAnonymityDelete")
	public String boardAnonymityDelete(@RequestParam Map<String, Object> anonymity,Model model) {
		model.addAttribute("url","boardAnonymity");
		if(boardService.removeAnonymity(anonymity)) {
			model.addAttribute("msg","삭제완료");
		}else {
			model.addAttribute("msg","삭제실패");
		}
		return "result";
	}
	
	@RequestMapping(value = "/boardAnonymityCheckPassForm")
	public String anonymityCheckPassForm(int boardNo, Model model) {
		model.addAttribute("anonymity",boardService.getAnonymity(boardNo));
		return "boardAnonymityCheckPass";
	}
	
	@RequestMapping(value = "/boardAnonymityCheckPass")
	public String anonymityCheckPass(@RequestParam Map<String,Object> anonymity,Model model) {
		//입력받은 비밀번호와 게시글의 비밀번호를 비교
		//성공하면, checkSuccess.jsp 실패하면 boardCheckPass.jsp
		model.addAttribute("anonymity",anonymity);
		if(boardService.checkAnonymityPass(anonymity)) {
			return "boardAnonymityCheckSuccess";
		}else {
			model.addAttribute("msg","비밀번호가 일치하지 않습니다.");
			return "boardAnonymityCheckPass"; 	
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/boardAnonymityViewFormFileDown/{boardNo}")
	public ResponseEntity<List<Map<String, Object>>> fileListOfAnonymity(@PathVariable("boardNo")int boardNo) {
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		try {
			List<Map<String, Object>> fileList = boardService.getFileListAnonymity(boardNo);
			
			entity = new ResponseEntity<List<Map<String, Object>>>(fileList,HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}


}






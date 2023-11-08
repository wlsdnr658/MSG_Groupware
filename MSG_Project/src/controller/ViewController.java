package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import service.BoardService;
import service.CarItemService;
import service.MemoService;
import service.RoomItemService;

@Controller
@RequestMapping("/page")
public class ViewController {
   //----------------common-----------------------
   
   @Autowired
   private BoardService boardService;
   @Autowired
   private CarItemService carservice;
   @Autowired
   private RoomItemService roomservice;
   @Autowired
   private MemoService memoService;
   
      
   //로그인 페이지 요청
   @RequestMapping("/loginForm")
   public String loginForm() {
      return "loginForm";
   }
   //메인 페이지 요청
   @RequestMapping("/mainForm")
   public String main(Model model,HttpServletRequest req) {
      HttpSession session = req.getSession();
      
      Map<String, Object> i = (Map<String, Object>) session.getAttribute("user");
      
      Map<String, Object> empDeptNoMap = new HashMap<String, Object>();
      String deptNo1 = (String) i.get("deptNo");
      empDeptNoMap.put("deptNo1", deptNo1);
      model.addAttribute("empDeptNoMap",empDeptNoMap);
      
      String empNo = (String)i.get("empNo");
      String deptNo = boardService.getEmpById(empNo);
      
      List<Map<String, Object>> itBoardList = boardService.getAllITList();
      List<Map<String, Object>> bpBoardList = boardService.getAllBPList();
      List<Map<String, Object>> minBoardList = boardService.getAllMinList();
      List<Map<String, Object>> salesBoardList = boardService.getAllSalesList();
      
      if(deptNo.equals("40")) {
         model.addAttribute("deptList",itBoardList);
      }else if(deptNo.equals("30")) {
         model.addAttribute("deptList",minBoardList);
      }else if(deptNo.equals("20")) {
         model.addAttribute("deptList",salesBoardList);
      }else if(deptNo.equals("10")) {
         model.addAttribute("deptList",bpBoardList);
      }
      
      List<Map<String, Object>> noticeList = boardService.getAllNoticeList();
      List<Map<String, Object>> eventList = boardService.getAllEventList();
      List<Map<String, Object>> anonymityList = boardService.getAllAnonymityList();
      List<Map<String, Object>> projectOngoingList = boardService.getAllProjectOngoingList();
      
      model.addAttribute("noticeList",noticeList);
      model.addAttribute("eventList",eventList);
      model.addAttribute("anonymityList",anonymityList);
      model.addAttribute("projectOngoingList",projectOngoingList);
      
      
      
      Map<String, Object> caritem  = carservice.maincaritemList();
      Map<String, Object> roomitem  = roomservice.mainroomitemList();
      
      
      model.addAttribute("carStatus", caritem);
      model.addAttribute("roomStatus", roomitem);
      
      return "main1";
   }
   
   @ResponseBody
   @RequestMapping(value="/addMemo",method = RequestMethod.POST)
   public boolean register(@RequestParam Map<String, Object> memo) {
      
      boolean result = memoService.addMemo(memo);
      
      if(result) {
         return true;
      }else {
         return false;         
      }
   }
   
   @ResponseBody
   @RequestMapping(value = "/getAllMemo/{empNo}")
   public ResponseEntity<List<Map<String, Object>>> memoList(@PathVariable("empNo") String empNo){
      
      ResponseEntity<List<Map<String, Object>>> entity = null;
      try {
         List<Map<String, Object>> memoList = memoService.getAllMemo(empNo);
         entity = new ResponseEntity<List<Map<String, Object>>>(memoList,HttpStatus.OK);
      }catch(Exception e) {    
         entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      return entity;
   }
   
   @ResponseBody
   @RequestMapping(value = "/updateMemo/{memoNo}", method =RequestMethod.POST)
   public ResponseEntity<String> update(@PathVariable("memoNo")int memoNo,@RequestParam Map<String, Object> memo) {
      
      Map<String, Object> memo1 = new HashMap<String, Object>();
      String con = (String) memo.get("memoContent");
      String empNo = (String) memo.get("empNo");
      
      memo1.put("empNo",empNo);
      memo1.put("content",con);
      memo1.put("memoNo",memoNo);     
      
      ResponseEntity<String> entity = null;
      
      if(memoService.modifyMemo(memo1)) {
         entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
      }else {
         entity = new ResponseEntity<String>("FAIL",HttpStatus.BAD_REQUEST);
      }
      
      return entity;
   }
   
   
   @ResponseBody  
   @RequestMapping(value = "/deleteMemo/{memoNo}",method = RequestMethod.POST)
   public boolean delete(@PathVariable("memoNo")int memoNo,@RequestParam Map<String, Object> memo) {
      
      Map<String, Object> memo1 = new HashMap<String, Object>();
      
      String empNo1 = (String) memo.get("empNo");
      
      memo1.put("memoNo", memoNo);
      memo1.put("empNo",empNo1);
      
      return memoService.removeMemo(memo1);
   }
   
   
}


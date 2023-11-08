package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import service.ApprovalService;
import service.BoardService;
import service.EmpService;

@Controller
@RequestMapping("/emp")
public class EmpController {
	@Autowired
	private EmpService service;
	@Autowired
	private ApprovalService approvService;
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/empModifyForm") // 사원정보 수정페이지 요청
	public String empModiForm() {
		return "empModifyForm";
	}
	@RequestMapping("/empManageForm") // 사원 관리페이지 요청
	public String empManageForm() {
		return "empManageForm";
	}
	@RequestMapping("/empChartForm") // 조직도 요청
	public String empChart() {
		return "empChart";
	}
	@RequestMapping("/empList")
	public String empListForm() { // 사원목록 페이지 요청
		return "empListForm";
	}
	
	@RequestMapping("/userLogin")
	public String userLogin(@RequestParam Map<String, String> param, Model model, HttpServletRequest req) {
		String url = "/loginForm";
		
		if(service.checkLogin(param)) {
			req.getSession().setAttribute("user", service.getUserInfo(param.get("UID")));
			req.getSession().setMaxInactiveInterval(36000);
			
			List<Map<String, Object>> itBoardList = boardService.getAllITList();
			List<Map<String, Object>> bpBoardList = boardService.getAllBPList();
			List<Map<String, Object>> minBoardList = boardService.getAllMinList();
			List<Map<String, Object>> salesBoardList = boardService.getAllSalesList();
			
			String empNo = param.get("UID");
			String deptNo = boardService.getEmpById(empNo);
			
			Map<String, Object> empDeptNoMap = new HashMap<String, Object>();
			empDeptNoMap.put("deptNo1", deptNo);
			model.addAttribute("empDeptNoMap",empDeptNoMap);
			
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
			
			url = "main1";
		}
		
		return url;
	}
	@RequestMapping("/userLogout")
	public String userLogout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/page/loginForm";
	}
	
	
	@RequestMapping("/userModify")
	public String userModify(HttpSession session, @RequestParam Map<String, String> param, MultipartFile file) {
		Map<String, Object> user = (HashMap<String, Object>)session.getAttribute("user");
		String id = (String)user.get("empNo");
		service.modifyEmp(id, param, file);
		return "redirect:/page/mainForm";
	}
	
	@ResponseBody
	@RequestMapping("/getEmpImage")
	public byte[] getProfileImg(String loginEmp) {
		return service.getImageAsByteArray(loginEmp);
	}
	@ResponseBody
	@RequestMapping("/getEmpStamp")
	public byte[] getStampImg(String empNo) {
		return service.getStampAsByteArray(empNo);
	}
	
	@ResponseBody
	@RequestMapping("/selectEmpInfo")
	public Map<String, Object> selectOne(String empNo){
		return service.selectOneEmp(empNo);
	}
	
	@ResponseBody
	@RequestMapping("/selectByDept")
	public List<Map<String, Object>> getMembersByDept(String type){
		return service.selectAllByDept(type);
	}
	
	@ResponseBody
	@RequestMapping("/getAllList")
	public Map<String, Object> getAllOrderdEmp(Map<String, Object> params, int page){
		return service.getAllOrderdEmp(params, page);
	}
	
	@RequestMapping("/empListPage")
	public String getEmpList(Model model, @RequestParam Map<String, Object> params
			, @RequestParam(required=false) String keyword
			, @RequestParam(defaultValue="0") int type
			, @RequestParam(defaultValue="1") int page) {
		params.put("type", type);
		params.put("keyword", keyword);
		Map<String, Object> viewData = service.getAllOrderdEmp(params, page);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		model.addAttribute("viewData",viewData);
		
		return "empListForm";
	}
	
	@ResponseBody
	@RequestMapping("/modifyEmp")
	public boolean modifyEmp(@RequestParam Map<String, Object> params) {
		if(service.modifyEmpByAdmin(params) > 0) {
			return true;
		}
		return false;
	}
	
	@ResponseBody
	@RequestMapping("/empByName")
	public List<String> getEmpsByName(String input_Name){
		return service.getEmpsByName(input_Name);
	}

	@ResponseBody
	@RequestMapping("/selectEmpByNameAndDeptName")
	public Map<String, Object> getEmpByNameAndDeptName(String info){
		String[] tmp = info.split(" / ");
		Map<String, String> empInfo = new HashMap<String, String>();
		empInfo.put("empName", tmp[0]);
		empInfo.put("deptName", tmp[1]);
		return service.getEmpByNameAndDeptName(empInfo);
	}
	
}

package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import commons.DownloadView;
import service.ApprovalService;

@Controller
@RequestMapping("/approval")
public class ApprovalController {
	@Autowired
	private ApprovalService service;


	//--------------approval----------------------

	@RequestMapping("/approvalMainForm") // 결재 메인 페이지 요청
	public String appMain() {
		int[] arr = new int[8];
		int[] arr2 = new int[arr.length];
		return "approvStore";
	}
	@RequestMapping("/approvalWriteMain") // 결재작성 메인페이지 요청
	public String appWriteMainForm() {
		return "approvWriteMain";
	}
	@RequestMapping("/approvalStore") // 결재함페이지 요청
	public String appStore() {
		return "approvStore";
	}
	@RequestMapping("/denyDocForm") // 반려 결재함페이지 요청
	public String denyDoc(Model model, @RequestParam Map<String, Object> params
			, @RequestParam(required=false) String keyword
			, @RequestParam(defaultValue="0") int type
			, @RequestParam(defaultValue="1") int page) {
		params.put("type", type);
		params.put("keyword", keyword);
		Map<String, Object> viewData = service.getApprovListByType(params, page);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		model.addAttribute("viewData", viewData);

		return "denyApprovList";
	}
	@RequestMapping("/curDocForm")   // 진행중 결재함페이지 요청
	public String curDoc(Model model, @RequestParam Map<String, Object> params
			, @RequestParam(required=false) String keyword
			, @RequestParam(defaultValue="0") int type
			, @RequestParam(defaultValue="1") int page) {
		params.put("type", type);
		if(keyword != null) {
			params.put("keyword", keyword);			
		}

		Map<String, Object> viewData = service.getApprovListByType(params, page);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		model.addAttribute("viewData", viewData);

		return "currentApprovList";
	}
	@RequestMapping("/comDocForm") // 완료 결재함페이지 요청
	public String comDoc(Model model, @RequestParam Map<String, Object> params
			, @RequestParam(required=false) String keyword
			, @RequestParam(defaultValue="0") int type
			, @RequestParam(defaultValue="1") int page) {
		params.put("type", type);
		params.put("keyword", keyword);
		Map<String, Object> viewData = service.getApprovListByType(params, page);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		model.addAttribute("viewData", viewData);

		return "completeApprovList";
	}

	@RequestMapping("/approvRequire")
	public String approvReq(Model model, @RequestParam Map<String, Object> params
			, @RequestParam(required=false) String keyword
			, @RequestParam(defaultValue="0") int type
			, @RequestParam(defaultValue="1") int page) {
		
		params.put("type", type);
		params.put("keyword", keyword);
		Map<String, Object> viewData = service.getRequiredApprovList(params, page);
		viewData.put("type", type);
		viewData.put("keyword", keyword);
		model.addAttribute("viewData", viewData);
		return "approvRequire";
	}
	
	@RequestMapping("/approvalProcess")
	public String proApproval(@RequestParam Map<String, String> params) {
		// params -  approvNo = 결재 문서번호 , result = (1,0)값  ※ 1 : 승인, 2 : 반려
		// 1. 승인 시
		// ( drafting or disburse or vacation )approvNo에 해당하는 column -> line_order 와 approvLine lineNo가 일치하는 컬럼의 order를 +1 or 3 초과하면 1로 변경 후
		// ( drafting or disburse or vacation )의 line_order를  다음으로 옮긴다. (approv1였다면 approv2로, approv2였다면 approv3로, approv3였다면  approv4로, approv4 였다면 문서 상태 완료상태로 변경)
		//
		// 2. 반려 시
		// ( drafting or disburse or vacation )approvNo에 해당하는 column -> line_order -> approvLine 테이블의 order를  1로 초기화한 후
		// ( drafting or disburse or vacation )의 line_order를  approv1으로 변경
		
		service.updateApprovalProcess(params);
		return "approvStore";
	}
	
	@RequestMapping("/reqDoc") // 결재문서 요청
	public String approvDoc(String docType, Model model) {
		String docName = "";

		model.addAttribute("data", service.getDocHeaderInfo(docType));
		if(docType.equals("kian")) {
			docName = "draftingPaper";
		} else if(docType.equals("spend")) {
			docName = "disbursePaper";
		} else if(docType.equals("vac")) {
			docName = "vacationPaper";
		}
		return docName;
	}
	@RequestMapping("/writeApprov")
	public String writeApprov(@RequestParam Map<String, String> params, @RequestParam(required=false) List<MultipartFile> files) {
		System.out.println("params : " + params);  
		System.out.println("files : " + files); 
		service.writeDoc(params, files);
		return "redirect:/approval/approvalMainForm";
	}

	@ResponseBody
	@RequestMapping("/getMiniApprovList")	// 결재함 메인페이지로 각 상태별로 최신 글 5개씩 보여준다.
	public List<Map<String, Object>> getList(String empNo, String status){	// 문서 3종류에 대한 각각 반려, 진행, 완료 상태 가져오기
		Map<String, String> params = new HashMap<String,String>();
		params.put("EMPNO", empNo);
		params.put("STATUS", status);

		return service.getListByType(params);
	}

	@RequestMapping("/viewApprov")
	public String viewApprov(String approvNo,String docNo, Model model) {
		String url = "";	// 로딩 할 뷰 이름 선언

		Map<String, Object> data = service.viewApprov(approvNo);

		if(docNo.equals("0001")) {	// 기안서 세부보기
			url = "draftingView";
		} else if(docNo.equals("0002")) {	// 지출결의서 세부보기
			String[] splitTmp;
			String detailPrice = (String)data.get("detailPrice");
			data.remove("detailPrice");
			splitTmp = detailPrice.split("/");
			for(int i = 0 ; i < splitTmp.length; i++) {
				data.put("MONEY_DETAIL_"+(i+1), splitTmp[i]);
			}

			String detailNote = (String)data.get("detailNote");
			data.remove("detailNote");
			splitTmp = detailNote.split("/");
			for(int i = 0 ; i < splitTmp.length; i++) {
				data.put("CON_DETAIL_"+(i+1), splitTmp[i]);
			}


			int payType = (Integer)data.get("payType");
			data.remove("payType");
			String type = "";
			switch(payType) {
			case 1:
				type = "개인현금";
				break;
			case 2:
				type = "개인카드";
				break;
			default:
				type = "법인카드";
			}
			data.put("payType", type);

			url = "disburseView";
		} else {	// 휴가계획서 세부보기
			url = "vacationView";
		}

		model.addAttribute("data", data);
		return url;
	}

	@RequestMapping("/reApprov")
	public String reApprov(Model model, String docNo, HttpServletResponse resp) {
		System.out.println("DOCNO : "+docNo);
		String docName = "";
		String docType = docNo.substring(0, 2);
		System.out.println("reApprov : "+service.getApprovData(docNo, docType));
		model.addAttribute("data",service.getApprovData(docNo, docType));
		
		if(docType.equals("dr")) {
			docName = "draftingPaper";
		} else if (docType.equals("di")) {
			docName = "disbursePaper";
		} else if (docType.equals("va")) {
			docName = "vacationPaper";
		}

		return docName;
	}
	
	@ResponseBody
	@RequestMapping("/getLineInfo")
	public Map<String, String> getLines(String APNUM){
		return service.getLines(APNUM);
	}

	@RequestMapping("/downFile")
	public View download(String fileName) {
		File file = service.getAttchFile(fileName);
		View view = new DownloadView(file);

		return view;
	}

	@RequestMapping("/getAllTest")
	public void doTest(String approvNo) {
		service.doTest(approvNo);
	}
	
	@ResponseBody
	@RequestMapping("/getCurEmpInfo")
	public String getEmpInfo(@RequestParam Map<String, String> params) {
		return service.getCurEmpInfo(params);
	}
}

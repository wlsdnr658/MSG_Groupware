package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import dao.ApprovalDao;

@Service
public class ApprovalService {
	@Autowired
	private ApprovalDao dao;
	private int mOrder, bOrder, fOrder, iOrder;
	private int ordering;
	private static final int NUM_OF_BOARD_PER_PAGE = 5;
	private static final int NUM_OF_NAVI_PAGE = 10;
//	private String UPLOAD_PATH = "/usr/local/appFiles/";
	private String UPLOAD_PATH = "C://appFiles";
	

	// 문서 작성 시 헤더에 필요한 정보 map형태로 반환하는 메소드
	public Map<String, String> getDocHeaderInfo(String docType){	
		Map<String, String> data = new HashMap<String,String>();

		String cur_Num = "";
		String next_Num = "";
		if(docType.equals("kian")) {
			data.put("DOCTYPE", "기안서");
			cur_Num = dao.getCurDocNumOfdraft();
			next_Num = "dr";
		} else if(docType.equals("spend")) {
			data.put("DOCTYPE", "지출결의서");
			cur_Num = dao.getCurDocNumOfdisburse();
			next_Num = "di";
		} else if(docType.equals("vac")) {
			data.put("DOCTYPE", "휴가계획서");
			cur_Num = dao.getCurDocNumOfvacation();
			next_Num = "va";
		}
		// 각 문서마다 마지막으로 작성 된 문서번호를 가져온다.

		if(cur_Num != null) {

			String[] parts = cur_Num.split("_");	// [0] 배열에 문서의 타입이 들어가고 [1]에 문서의 순서번호가 들어감
			int num = Integer.parseInt(parts[1]) + 1;
			parts[1] = String.format("%04d", num);
			next_Num = parts[0]+"_"+parts[1];
		} else {
			next_Num += "_0001"; 
		}
		System.out.println(next_Num);
		data.put("approvNo", next_Num);
		data.put("approvDate", dao.getSysDate());	
		return data;
	}

	// 작성된 결재문서 저장 메소드
	public boolean writeDoc(Map<String, String> params, List<MultipartFile> files) {
		// 문서에 들어온 결재선 생성(insert)
		//  1. 부서 마다 최신 결재선 번호 생성(select, + 1)
		//  2. 인원에 맞춰 결재선 순서에 따른 사원 삽입

		// 결재문서 생성 (insert)
		//  1. 결재문서 최신 결재번호 생성(select, + 1)
		//  2. approvDate, title, upFile, etc, line_order(결재선 중 제일 첫 번째 결재선 번호), status(1), approv1(결재선 번호), 문서타입, 기안자 사원번호 insert

		// params = 문서 내용 정보, 작성 사원 정보, 결재선 정보

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////// 문서 결재선 service 시작 
		JSONArray line_Arr = new JSONArray(params.get("APLINE"));
		System.out.println(line_Arr);

		boolean flag = true;
		Map<String, String> lineParam = new HashMap<String, String>();


		int mCnt, bCnt, fCnt, iCnt;
		ordering = 1;
		mOrder = bOrder = fOrder = iOrder = 0;
		mCnt = bCnt = fCnt = iCnt = 0;
		String curMK, curBK, curFK, curIK;
		curMK = curBK = curFK = curIK = "";

		Map<String, String> mline = new HashMap<String, String>();
		Map<String, String> bline = new HashMap<String, String>();
		Map<String, String> fline = new HashMap<String, String>();
		Map<String, String> iline = new HashMap<String, String>();
		for(int i = 0; i < line_Arr.length(); i++) {
			JSONObject emp = line_Arr.getJSONObject(i);
			Map<String, String> line = new HashMap<String, String>();

			switch((String)emp.get("empDept")) {
			case "경영/기획부":
				if(mCnt == 0) {
					mOrder = ordering++;
					curMK = getNextKey(keyNullChk(dao.getCurLineNumber()));
					line.put("LINENO", curMK);
					line.put("FAPPROV", emp.getString("empNo"));
					line.put("DEPT", "10");
					dao.insertLine(line);
				} else {
					
					if(mCnt == 1) {
						mline.put("LINENO", curMK);
						mline.put("SAPPROV", emp.getString("empNo"));
						dao.updateLine(mline);
					}else {
						mline.put("LINENO", curMK);
						mline.put("TAPPROV", emp.getString("empNo"));
						dao.updateLine(mline);
					}
				}
				mCnt++;
				break;
			case "영업부":
				if(bCnt == 0) {
					bOrder = ordering++;
					curBK = getNextKey(keyNullChk(dao.getCurLineNumber()));
					line.put("LINENO", curBK);
					line.put("FAPPROV", emp.getString("empNo"));
					line.put("DEPT", "20");
					dao.insertLine(line);
				} else {
					
					if(bCnt == 1) {
						bline.put("LINENO", curBK);
						bline.put("SAPPROV", emp.getString("empNo"));
						dao.updateLine(bline);
					}else {
						bline.put("LINENO", curBK);
						bline.put("TAPPROV", emp.getString("empNo"));
						dao.updateLine(bline);
					}
				}
				bCnt++;
				break;
			case "재무부":
				if(fCnt == 0) {
					fOrder = ordering++;
					curFK = getNextKey(keyNullChk(dao.getCurLineNumber()));
					line.put("LINENO", curFK);
					line.put("FAPPROV", emp.getString("empNo"));
					line.put("DEPT", "30");
					dao.insertLine(line);
				} else {
	
					if(fCnt == 1) {
						fline.put("LINENO", curFK);
						fline.put("SAPPROV", emp.getString("empNo"));
						dao.updateLine(fline);
					}else {
						fline.put("LINENO", curFK);
						fline.put("TAPPROV", emp.getString("empNo"));
						dao.updateLine(fline);
					}
				}
				fCnt++;
				break;
			case "IT부":
				if(iCnt == 0) {
					iOrder = ordering++;
					curIK = getNextKey(keyNullChk(dao.getCurLineNumber()));
					line.put("LINENO", curIK);
					line.put("FAPPROV", emp.getString("empNo"));
					line.put("DEPT", "40");
					dao.insertLine(line);
				} else {
					
					if(iCnt == 1) {
						iline.put("LINENO", curIK);
						iline.put("SAPPROV", emp.getString("empNo"));
						dao.updateLine(iline);
					}else {
						iline.put("LINENO", curIK);
						iline.put("TAPPROV", emp.getString("empNo"));
						dao.updateLine(iline);
					}
				}
				iCnt++;
				break;
			}
		}
		// 문서 결재선 생성 완료
		String[] curLinNums = {curMK, curBK, curFK, curIK};
		String doc = params.get("DOCTYPE");
		
		params.put("APPROV"+mOrder, curLinNums[0]);
		params.put("APPROV"+bOrder, curLinNums[1]);
		params.put("APPROV"+fOrder, curLinNums[2]);
		params.put("APPROV"+iOrder, curLinNums[3]);
		
		Map<String, Object> fileParam = new HashMap<String, Object>();	// 첨부파일을 write 할 때 쓸 파라미터 맵
		
		if(doc.equals("기안서")) {
			fileParam.put("DOC", 1);
			writeDrafting(params);
		} else if(doc.equals("지출결의서")) {
			fileParam.put("DOC", 2);
			writeDisburse(params);
		} else if(doc.equals("휴가계획서")) {
			fileParam.put("DOC", 3);
			writeVacation(params);
		}
		
		if(files != null && !(files.get(0).getOriginalFilename().equals(""))) {
			fileParam.put("FILENAME", writeFile(files));
			fileParam.put("APPROVNO", params.get("APNUM"));
		
			dao.attachFile(fileParam);
		}
		return true;
	}
	private String writeFile(List<MultipartFile> files) {
		String fileName = "";
		
		System.out.println("파일스 사이즈 : "+files.size());
		
		for(int i = 0; i < files.size(); i++) {
			UUID uuid = UUID.randomUUID();
			String storedfileName = uuid.toString()+"_"+files.get(i).getOriginalFilename();
			File target = new File(UPLOAD_PATH,storedfileName);
			fileName += storedfileName+",";
			try {
				FileCopyUtils.copy(files.get(i).getBytes(), target);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileName;
		
	}
	
	public Map<String, Object> viewApprov(String approvNo){
		Map<String, Object> data = new HashMap<String, Object>();
		String[] tokens = approvNo.split("_");
		switch(tokens[0]) {
		case "di":
			data = dao.getDisburseView(approvNo);
			break;
		case "dr":
			data = dao.getDraftView(approvNo);
			break;
		case "va":
			data = dao.getVacationView(approvNo);
			break;
		}
		String fileTmp = (String)data.get("upFile");
		if(fileTmp != null) {
			String[] files = fileTmp.split(",");
			data.remove("upFile");
			data.put("upFile", files);
		}
		String writer = (String)data.get("empNo");
		data.put("writer", dao.getEmpNameByNo(writer));
		return data;
	}
	public List<Map<String, Object>> getListByType(Map<String, String> params){	// 	
		return dao.getMiniApprovByType(params);
	}
	public Map<String, String> getLines(String APNUM){
		Map<String, String> lines;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("APNUM", APNUM);
		if(APNUM.substring(0, 2).equals("dr")) {
			params.put("DOC", 1);
		} else if(APNUM.substring(0, 2).equals("di")) {
			params.put("DOC", 2);
		} else {
			params.put("DOC", 3);
		}
		lines = dao.approvToLine(params);
		Map<String, String> myLines = new TreeMap<String, String>(lines);
		
		return myLines;
	}
	private String getNextKey(String curKey) {	// Current Key값을 받아와서 next Key값을 반환해주는 메소드
		String[] current = curKey.split("_");
		int num = Integer.parseInt(current[1]) + 1;
		current[1] = String.format("%04d", num);
		current[1] = current[0]+"_"+current[1];
		return current[1];
	}
	private String keyNullChk(String key) {
		String ifNull = key;
		if(ifNull == null) {
			ifNull = "A_0001";
		}
		return ifNull;
	}
	
	
	
	
	// 문서 write 메소드들은 넘길 정보 파라미터 맵과 현재 문서작성중에 사용되고있는 결재선의 lineNo들을 가져간다.
	// keys는 [0]번부터 경영, 영업, 재무 ,IT 순
	private boolean writeDrafting(Map<String, String> params) {	// 기안서 작성
		// dao insert Drafting 호출
		params.put("DOCNO", "0001");
		System.out.println("이거로 insert 합니다   :   "+params);
		dao.insertDraft(params);
		
		return false;
	}
	private boolean writeDisburse(Map<String, String> params) {	// 지출 결의서 작성 
		// dao insert Disburse 호출
		params.put("DOCNO", "0002");
		
		String detailNote = "";
		detailNote += params.get("CON_DETAIL_1")+"/";
		detailNote += params.get("CON_DETAIL_2")+"/";
		detailNote += params.get("CON_DETAIL_3")+"/";
		detailNote += params.get("CON_DETAIL_4")+"/";
		detailNote += params.get("CON_DETAIL_5");
		
		String detailMoney = "";
		detailMoney += params.get("MONEY_DETAIL_1")+"/";
		detailMoney += params.get("MONEY_DETAIL_2")+"/";
		detailMoney += params.get("MONEY_DETAIL_3")+"/";
		detailMoney += params.get("MONEY_DETAIL_4")+"/";
		detailMoney += params.get("MONEY_DETAIL_5");
		
		params.put("DETAILNOTE", detailNote);
		params.put("DETAILPRICE", detailMoney);
		
		String total = params.get("MONEY_DETAIL_TOTAL");
		params.remove("MONEY_DETAIL_TOTAL");
		params.put("MONEY_DETAIL_TOTAL", total.replaceAll(",", ""));
		
		String pay = "";
		switch(params.get("PAYTYPE")) {
		case "p_cash":
			pay = "1";
			break;
		case "p_card":
			pay = "2";
			break;
		case "c_card":
			pay = "3";
			break;
		}
		params.remove("PAYTYPE");
		params.put("PAYTYPE", pay);
		System.out.println("지출결의서 : "+params);
		dao.insertDisburse(params);
		return false;
	}
	private boolean writeVacation(Map<String, String> params) {	// 휴가 계획서 작성
		// dao insert vacation 호출
		params.put("DOCNO", "0003");
		dao.insertVacation(params);
		return false;
	}
	
	public File getAttchFile(String fName) {
		return new File(UPLOAD_PATH,fName);
	}
	
	public Map<String, Object> getApprovListByType(Map<String, Object> params, int page){
		// params = empNo, status, 
		List<Map<String, Object>> approvList;
		int pageTotalCount;
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);
		
		
		params.put("firstRow", firstRow);
		params.put("endRow", endRow);
		int type = (int)params.get("type");
		
		if(type == 1) {
			String keyword = (String)params.get("keyword");
			params.put("SEARCHTITLE", keyword);
		}
		
		pageTotalCount = getPageTotalCount(dao.getApprovTotalCount(params));
		// empNo : 불러올 사번 , status : (1,2,3)진행,완료,반려 상태 값 , keyword : 검색 키워드 , type : 검색 타입 , firstRow : 페이징 번호 , endRow : 페이징번호
		// empNo, status, keyword, type 은 jsp 에서 넘김
		approvList = dao.getKindApprovs(params);
		for(int i = 0; i < approvList.size(); i++) {
			Map<String, String> findParam = new HashMap<String, String>();
			
			String approvNo = (String)((approvList.get(i)).get("approvNo"));
			String dType = approvNo.split("_")[0];
			if(dType.equals("dr")) {
				dType = "1";
				(approvList.get(i)).put("docName", "[기안서]");
			} else if (dType.equals("di")) {
				dType = "2";
				(approvList.get(i)).put("docName", "[지출결의서]");
			} else if (dType.equals("va")) {
				dType = "3";
				(approvList.get(i)).put("docName", "[휴가계획서]");
			}
			
			findParam.put("docType", dType);
			findParam.put("approvNo", approvNo);
			
			(approvList.get(i)).put("current", dao.currentApprovLineEmp(findParam));
			System.out.println("지금 반복되고있는 그거 맵 : "+approvList.get(i));
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("appList", approvList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);
		
		
		return result;
	}
	
	public Map<String, Object> getRequiredApprovList(Map<String, Object> params, int page){
		List<Map<String, Object>> approvList;
		int pageTotalCount;
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);
		
		
		params.put("firstRow", firstRow);
		params.put("endRow", endRow);
		int type = (int)params.get("type");
		
		if(type == 1) {
			String keyword = (String)params.get("keyword");
			params.put("SEARCHTITLE", keyword);
		}
		
		pageTotalCount = getPageTotalCount(dao.getApprovTotalCount(params));
		// empNo : 불러올 사번 , status : (1,2,3)진행,완료,반려 상태 값 , keyword : 검색 키워드 , type : 검색 타입 , firstRow : 페이징 번호 , endRow : 페이징번호
		// empNo, status, keyword, type 은 jsp 에서 넘김
		
		approvList = dao.getReqApprovs(params);
		
		for(int i = 0; i < approvList.size(); i++) {
			Map<String, String> findParam = new HashMap<String, String>();
			
			String approvNo = (String)((approvList.get(i)).get("approvNo"));
			String dType = approvNo.split("_")[0];
			if(dType.equals("dr")) {
				dType = "1";
				(approvList.get(i)).put("docName", "[기안서]");
			} else if (dType.equals("di")) {
				dType = "2";
				(approvList.get(i)).put("docName", "[지출결의서]");
			} else if (dType.equals("va")) {
				dType = "3";
				(approvList.get(i)).put("docName", "[휴가계획서]");
			}
			
			findParam.put("docType", dType);
			findParam.put("approvNo", approvNo);
			
			(approvList.get(i)).put("current", dao.currentApprovLineEmp(findParam));
			(approvList.get(i)).put("writer", dao.getEmpNameByNo((String)approvList.get(i).get("empNo")));
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("appList", approvList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);
		
		System.out.println("서비스에서 반환할 데이터 : "+result);
		
		return result;
	}
	
	public boolean updateApprovalProcess(Map<String, String> params) {
		// param data = approvNo, status, 현재 결재 진행한 curEmp(empNo) 필요
		boolean result = false;
		String status = (String)params.get("result");
		String docType =  ((String)params.get("approvNo")).substring(0, 2);
		
		if(status.equals("1")) {	// 결재선  진행 프로세스 구현
			// ( drafting or disburse or vacation )approvNo에 해당하는 column -> line_order 와 approvLine lineNo가 일치하는 컬럼의 order를 +1 or 3 초과하면 1로 변경 후
			// ( drafting or disburse or vacation )의 line_order를  다음으로 옮긴다. (approv1였다면 approv2로, approv2였다면 approv3로, approv3였다면  approv4로, approv4 였다면 문서 상태 완료상태로 변경)
			System.out.println("서비스1 : "+params);
			params.put("docType", docType);
			List<Map<String, String>> tmp = dao.getAllLinesByDoc(params);	// 결재문서 번호에 해당하는 모든 결재선 추출 List<HashMap<>>
					 
			for(int i = 0; i < tmp.size(); i++) {	// List<Map<>>형태를 정렬을 위해 List<TreeMap<>> 형식으로 convert
				TreeMap<String, String> convert = new TreeMap<String, String>(tmp.get(i));
				tmp.remove(i);
				tmp.add(i, convert);
			}
			
			Map<String, String> next  = new TreeMap<String,String>(); // 다음 결재자 
			
			boolean nextOne = false;
			int outer_Count = 0;
			OUTFOR:	// OUTFOR for문으로 명명
			for(Map<String, String> apline : tmp) {
				outer_Count++;
				int count = 0;
				for(Map.Entry<String, String> entry :apline.entrySet()) {
					count ++;
					if(nextOne) {	// 테이블에 갱신할 다음 결재자 정보   
						next.put("line_order", outer_Count+"");	// approvNo에 일치하는 컬럼의 line_order의 값을 entry.getKey()로 update
						next.put("curApprov", count+"");			// approvNo에 일치하는 컬럼의 line_order에 들어 있는 값과 일치하는 approvLine의 컬럼의 curApporv를 count값으로 update
						break OUTFOR;
					}
					if(entry.getValue().equals(params.get("curEmp"))){	// 현재 진행한 결재자 와 일치 할 때 nextOne flag를 true로 변경
						nextOne = true;
					}
					
				}
			}
			if(next.get("line_order") == null) {
				next.put("status", 2+"");
			}
			
			next.put("approvNo", params.get("approvNo"));
			next.put("docType", docType);
	
			System.out.println("next : "+next);
			dao.updateLineOrder(next);	// approvNo, docType, line_order, curApprov
			dao.updateCurApporv(next);
			
		} else if(status.equals("0")) {	// 결재선 반려 프로세스 구현
			// ( drafting or disburse or vacation )approvNo에 해당하는 column -> line_order -> approvLine 테이블의 order를  1로 초기화한 후
			// ( drafting or disburse or vacation )의 line_order를  approv1으로 변경
			params.put("docType", docType);
			dao.doDenyToLineOrder(params); // 문서 자체의 부서별 결재선 순서를 초기화
			dao.doDenyToCurApprov(params); // 결재선 내부 의 순서를 초기화
			
			result = true;
		}
		
		return result;
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
	
	public void doTest(String approvNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("approvNo", approvNo);
		params.put("docType", approvNo.substring(0, 2));
		
		List<Map<String, String>> tmp = dao.getAllLinesByDoc(params);	// 결재문서 번호에 해당하는 모든 결재선 추출 List<HashMap<>>
		
		 
		for(int i = 0; i < tmp.size(); i++) {	// List<Map<>>형태를 정렬을 위해 List<TreeMap<>> 형식으로 convert
			TreeMap<String, String> convert = new TreeMap<String, String>(tmp.get(i));
			tmp.remove(i);
			tmp.add(i, convert);
			
		}
		
		
		
		//System.out.println((new TreeMap<String,Object>(dao.getAllLinesByDoc(params))));
		//20180010 20180002 20180008 20180011
		
		Map<String, Object> next  = new TreeMap<String,Object>(); // 다음 결재자 
		OUTFOR:
		for(Map<String, String> apline : tmp) {
			int count = 0;
			boolean nextOne = false;
			for(Map.Entry<String, String> entry :apline.entrySet()) {
				count ++;
				if(nextOne) {	// 테이블에 갱신할 다음 결재자 정보   
					next.put("line_order", entry.getKey());	// approvNo에 일치하는 컬럼의 line_order의 값을 entry.getKey()로 update
					next.put("curApprov", count);			// approvNo에 일치하는 컬럼의 line_order에 들어 있는 값과 일치하는 approvLine의 컬럼의 curApporv를 count값으로 update
					break OUTFOR;
				}
				if(entry.getValue().equals("20180010")){	// 현재 진행한 결재자 와 일치 할 때 nextOne flag를 true로 변경
					nextOne = true;
				}
				
			}
		}

	}
	public Map<String, Object> getApprovData(String docNo, String docType){
		Map<String, String> params = new HashMap<String, String>();
		params.put("approvNo", docNo);
		params.put("docType", docType);
		return dao.getApprovData(params);
	}
	
	public int getReqCount(String empNo) {
		return dao.getReqCount(empNo);
	}
	public String getCurEmpInfo(Map<String, String> params) { 	// 현재 결재문서의 진행되고 있는 차례인 사원번호 get
		String docType = "";
		switch(params.get("approvNo").substring(0, 2)) {
		case "dr":
			docType = "1";
			break;
		case "di":
			docType = "2";
			break;
		case "va":
			docType = "3";
			break;
		}
		params.put("docType", docType);
		System.out.println("보낼 파람 : "+params);
		Map<String, Object> emp = dao.currentApprovLineEmp(params);
		System.out.println("받아온 정보 : "+emp);
		
		String currentEmpNo = "";
		if(emp == null) {
			currentEmpNo = "complete";
		} else {
			currentEmpNo = (String)emp.get("empNo");
		}
		
		return currentEmpNo;
	}
}

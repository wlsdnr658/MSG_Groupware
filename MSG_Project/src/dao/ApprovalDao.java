package dao;

import java.util.List;
import java.util.Map;

public interface ApprovalDao {
	public Map<String, String> getEmpNameByNo(String empNo);	// empNo로 부터 emp정보를 추출
	
	////////////  현재 작성 될 기안서 마다의 KEY 번호 get	////////////////
	public String getCurDocNumOfdraft();
	public String getCurDocNumOfdisburse();
	public String getCurDocNumOfvacation();
	////////////////////////////////////////////////////////
	
	public String getCurLineNumber();													// 작성 될 결재선 번호 get
	public String getSysDate();															// 작성 되는 시간 get
	public int getApprovTotalCount(Map<String, Object> params);							// 작성 된 결재문서들의 총 개수를 get
	public Map<String, Object> currentApprovLineEmp(Map<String, String> params); 		// 현재 문서의 결재선의 사원정보를 get
	public Map<String, String> approvToLine(Map<String, Object> params);				// 작성 된 문서의 결재선 자체를 get
	public Map<String, Object> getDraftView(String approvNo);							// 작성 된 문서 중 기안서를 결재문서 번호를 통해 get 
	public Map<String, Object> getDisburseView(String approvNo);						// 작성 된 문서 중 지출결의서를 결재문서 번호를 통해 get
	public Map<String, Object> getVacationView(String approvNo);						// 작성 된 문서 중 휴가계획서를 결재문서 번호를 통해 get
	public Map<String, Object> getApprovData(Map<String, String> params);							// 작성 된 문서의 내용들을 get
	public List<Map<String, Object>> getKindApprovs(Map<String, Object> params);		// 작성 된 문서를 상태별로 get
	public List<Map<String, Object>> getReqApprovs(Map<String, Object> params);			// 나에게 들어온 결재요청 문서들 get
	public List<Map<String, Object>> getMiniApprovByType(Map<String, String> params);	// 결재선 대쉬보드 get (상태 별 : 반려, 승인, 완료), 날짜 최신기준으로 5개씩
	public List<Map<String, String>> getAllLinesByDoc(Map<String, String> params);		// 결재문서에 등록 된 모든 결재들을 가져옴
	
	
	public int getReqCount(String empNo);
	public boolean updateLine(Map<String, String> params);			// 결재선 진행 상태에 따른 업데이트
	public boolean insertLine(Map<String, String> params);			// 문서작성에 따른 해당 결재선 insert
	public boolean insertDraft(Map<String, String> params);			// 기안서 작성(insert)
	public boolean insertDisburse(Map<String, String> params);		// 지출결의서 작성(insert)
	public boolean insertVacation(Map<String, String> params);		// 휴가계획서 작성(insert)
	public boolean attachFile(Map<String, Object> params);			// 결재문서에 들어 갈 파일 첨부
	public boolean doDenyToLineOrder(Map<String, String> params);	// 반려에 따른 결재문서 프로세스 처리 update
	public boolean doDenyToCurApprov(Map<String, String> params);	// 반려에 따른 결재선 프로세스 처리 update
	public boolean updateLineOrder(Map<String, String> next);		// 승인에 따른 결재문서 프로세스 처리 update
	public boolean updateCurApporv(Map<String, String> next);		// 승인에 따른 결재선 프로세스 처리 update
}

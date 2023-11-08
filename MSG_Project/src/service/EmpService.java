package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import dao.EmpDao;

@Service
public class EmpService {
	@Autowired
	private EmpDao dao;
	private static final int NUM_OF_BOARD_PER_PAGE = 5;
	private static final int NUM_OF_NAVI_PAGE = 10;
	private static final String UPLOAD_PATH = "C:\\empPic";
//	private static final String STAMP_PATH = "/usr/local/stamp/";
	private static final String STAMP_PATH = "C:\\stamp";
	
	public boolean checkLogin(Map<String, String> login_Info) {
		boolean result = false;
		String input_ID = login_Info.get("UID");
		String input_PWD = login_Info.get("UPW");
		
		Map<String, Object> member = dao.getMember(input_ID);
		if(member != null) {
			if(member.get("empPw").equals(input_PWD)) {
				result = true;
			}
		}
		return result;
	}
	
	public void modifyEmp(String empNo, Map<String, String> params, MultipartFile file) {
		String fName = writeFile(file);
		params.put("empNo", empNo);
		if(dao.modifyEmpByUser(params) > 0 ) {
			System.out.println("서비스 이프문 들어옴");
			Map<String, String> param = new HashMap<String, String>();
			param.put("EMPNO", empNo);
			param.put("FILE", fName);
			System.out.println("변경사항 개수 : "+dao.modifyEmpPic(param));
		}
	}

	public int modifyEmpByAdmin(Map<String, Object> params) {
		System.out.println("수정할 정보 : "+params);
		return dao.modifyEmpByAdmin(params);
	}
	
	public Map<String, Object> getUserInfo(String id){
		return dao.getMemberInfo(id);
	}
	
	private String writeFile(MultipartFile file) {
		String fName = null;
		UUID uuid = UUID.randomUUID();
		fName = uuid.toString()+"_"+file.getOriginalFilename();
		File target = new File(UPLOAD_PATH, fName);
		try {
			FileCopyUtils.copy(file.getBytes(), target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fName;
	}
	
	public byte[] getStampAsByteArray(String empNo) {
		String fileName = dao.getStampName(empNo);
		if(fileName == null) {
			fileName = "/basicStamp.JPG";
		}
		File originFile = new File(STAMP_PATH+"/"+fileName); 
		InputStream targetStream = null;
		
		try {
			targetStream = new FileInputStream(originFile);
			
			return IOUtils.toByteArray(targetStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public byte[] getImageAsByteArray(String empNo) {
		System.out.println("찾을 사원번호 : "+empNo);
		String fileName = dao.getFileName(empNo);
		if(fileName == null) {
			fileName = "default.jpg";
		}
		File originFile = new File(UPLOAD_PATH+"/"+fileName); 
		InputStream targetStream = null;
		
		try {
			targetStream = new FileInputStream(originFile);
			
			return IOUtils.toByteArray(targetStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String, Object>> selectAllByDept(String type){
		String dept = "";
		
		switch(type) {
		case "m":
			dept = "경영/기획부";
			break;
		case "b":
			dept = "영업부";
			break;
		case "i":
			dept = "IT부";
			break;
		default:
			dept = "재무부";
			break;
		}
		System.out.println("바뀐 값 : "+dept);
		return dao.selectByDept(dept);
	}
	
	public List<Map<String, Object>> getAllOrderdEmp(){
		return dao.selectOderedAll_B();
	}
	
	public Map<String, Object> selectOneEmp(String empNo){
		return dao.selectOne(empNo);
	}
	public Map<String, Object> getAllOrderdEmp(Map<String, Object> params, int page){
		List<Map<String, Object>> empList;
		int pageTotalCount;
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);
		
		params.put("firstRow", firstRow);
		params.put("endRow", endRow);
		int type = (int)params.get("type");
		
		if(type == 1) {
			params.put("SEARCHNAME", params.get("keyword"));
		}else if (type == 2) {
			params.put("SEARCHDEPT", params.get("keyword"));
		}
		
		pageTotalCount = getPageTotalCount(dao.getEmpTotal(params));
		empList = dao.selectOderedAll(params);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("empList", empList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);
		return result;
	}
	public List<String> getEmpsByName(String input_Name){
		List<Map<String, String>> emps = dao.getEmpsByName(input_Name);
		if(emps != null) {
			List<String> empsName = new ArrayList<String>();
			
			for(int i = 0; i < emps.size(); i++) {
				System.out.println(emps.get(i));
				empsName.add(emps.get(i).get("empName")+" / "+emps.get(i).get("deptName"));
			}
			return empsName;
		} else {
			System.out.println("없어");
			return null;			
		}
	}
	public Map<String, Object> getEmpByNameAndDeptName(Map<String, String> empInfo) {
		Map<String, Object> result = dao.getEmpByNameAndDeptName(empInfo);
		String empNo = (String) result.get("empNo");
		result.put("fileName", getImageAsByteArray(empNo));
		System.out.println(result);
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
}


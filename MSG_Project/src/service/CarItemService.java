package service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CaritemDao;

@Service
public class CarItemService {

	@Autowired
	private CaritemDao dao;
	
	
	private static final int NUM_OF_CAR_PER_PAGE = 5;
	// 한번에 표시될 네비게이션의 개수
	private static final int NUM_OF_NAVI_PAGE = 5;

	public boolean insertcaritem(Map<String, Object> caritem) {

		int result = dao.insertCaritem(caritem);
	
		if (result > 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean removecaritem(Map<String, Object> caritem) {

		if (dao.updatestatusCaritem(caritem) > 0) {

			return true;
		} else {
			return false;
		}

	}

	public boolean modifycaritem(Map<String, Object> caritem) {

		if (dao.updateCaritem(caritem) > 0) {

			return true;

		} else {
			return false;
		}

	}

	public Map<String, Object> caritemList(String typeName) {
		
		List<Map<String, Object>> caritemList = dao.selectAll(typeName);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("caritemList", caritemList);
		
	


		return result;

	}
public Map<String, Object> maincaritemList() {
		
		List<Map<String, Object>> maincaritemList = dao.mainselectAll();

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("maincaritemList", maincaritemList);
		
	


		return result;

	}

	
	
	public Map<String, Object> getCarItemList(Map<String, Object>param, int page){
		
		// param 에 검색어와 검색 타입이 들어있음
				// 검색 게시글 조회하기
				// 페이징 처리 포함
				List<Map<String, Object>> caritemList;
				// 현재게시글의 총 개수를 넣어주기
				 int pageTotalCount;
				/* 화면 출력에 필요한 값 */
				int startPage = getStartPage(page);
				int endPage = getEndPage(page);

				/* 데이터 조회에 필요한 값 */
				int firstRow = getFirstRow(page);
				int endRow = getEndRow(page);

				param.put("firstRow", firstRow);
				param.put("endRow", endRow);

				int type = (int) param.get("type");
				String keyword = (String) param.get("keyword");

				/*
				 * type == 1 제목검색 
				 * type == 2 작성자 
				 * type == 3 제목+작성자 
				 * type == 4 내용검색 
				 * type == 0 검색x,전체조회
				 */
				if (type == 1) {
					param.put("carName", keyword);
				} else if (type == 2) {
					param.put("assigned", keyword);
				} 
				pageTotalCount = getPageTotalCount(dao.totalCarCount(param));
				
				caritemList = dao.searchCaritem(param);
				
				
	
				/**** 결과값 Map에 넣어서 반환하기 ****/
				/*화면에서 쓸 데이터 */
				Map<String, Object> result = new HashMap<String,Object>();
			
				result.put("caritemList",caritemList);
				result.put("startPage", startPage);
				result.put("endPage", endPage);
				result.put("currentPage", page);
				result.put("pageTotalCount", pageTotalCount);
				
	
				
				
				return result;
			}
	
	
	
	
	private int getPageTotalCount(int totalCount) {
		int pageTotalCount = 0;
		if (totalCount != 0) {
			pageTotalCount = (int) Math.ceil(((double) totalCount / NUM_OF_CAR_PER_PAGE));
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
		return (currentPage - 1) * NUM_OF_CAR_PER_PAGE + 1;
	}

	private int getEndRow(int currentPage) {
		return currentPage * NUM_OF_CAR_PER_PAGE;
	}
	
	
	
	
	
	
	
	
	
	
	
	public boolean dateCarCheck(Map<String, Object> caritem) {

		
		
		
		String carname = (String) caritem.get("carName");
		Timestamp start = null;
		Timestamp end = null;
		
		try { 
			
			start = Timestamp.valueOf(((String) caritem.get("start")).replace("T", " ") + ":00");
			end = Timestamp.valueOf(((String) caritem.get("end")).replace("T", " ") + ":00");
			
	
	
			
		}catch(NullPointerException e) {
		
	
		}


		Map<String, Object> chcardate = dao.selectDate(caritem);
		

		boolean result = false;

	
		if(chcardate == null) { 
	
			result =true;
			
		}else if(carname.equals(chcardate.get("carName"))){
			result = false;
			
		}else if(start == chcardate.get("startDate") && end == chcardate.get("endDate") ) {
			result = false;
		}
		
		
		else if(start.after(end) && end.before(start)) {
			result = false;
		}

		return result;
	}

}

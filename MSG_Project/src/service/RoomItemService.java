package service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.RoomitemDao;

@Service
public class RoomItemService {

	@Autowired
	private RoomitemDao dao;

	private static final int NUM_OF_ROOM_PER_PAGE = 5;
	// 한번에 표시될 네비게이션의 개수
	private static final int NUM_OF_NAVI_PAGE = 5;

	public boolean insertroomitem(Map<String, Object> roomitem) {

		int result = dao.insertRoomitem(roomitem);

		if (result > 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean removeroomitem(Map<String, Object> roomitem) {

		if (dao.updatstatusRoomitem(roomitem) > 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean modifyroomitem(Map<String, Object> roomitem) {

		if (dao.updateRoomitem(roomitem) > 0) {

			return true;

		} else {
			return false;
		}

	}

	public Map<String, Object> roomitemList(String typeName) {

		List<Map<String, Object>> roomitemList = dao.selectAll(typeName);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("roomitemList", roomitemList);

		return result;
	}

	public Map<String, Object> mainroomitemList() {

		List<Map<String, Object>> mainroomitemList = dao.mainselectAll();

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("mainroomitemList", mainroomitemList);

		return result;
	}

	public Map<String, Object> getRoomItemList(Map<String, Object> param, int page) {

		List<Map<String, Object>> roomitemList;

		int pageTotalCount;
		int startPage = getStartPage(page);
		int endPage = getEndPage(page);

		/* 데이터 조회에 필요한 값 */
		int firstRow = getFirstRow(page);
		int endRow = getEndRow(page);

		param.put("firstRow", firstRow);
		param.put("endRow", endRow);

		int type = (int) param.get("type");
		String keyword = (String) param.get("keyword");

		if (type == 1) {
			param.put("roomName", keyword);
		} else if (type == 2) {
			param.put("assigned", keyword);
		}
		pageTotalCount = getPageTotalCount(dao.totalRoomcount(param));

		roomitemList = dao.searchRoomitem(param);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("roomitemList", roomitemList);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", page);
		result.put("pageTotalCount", pageTotalCount);

		return result;

	}

	private int getPageTotalCount(int totalCount) {
		int pageTotalCount = 0;
		if (totalCount != 0) {
			pageTotalCount = (int) Math.ceil(((double) totalCount / NUM_OF_ROOM_PER_PAGE));
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
		return (currentPage - 1) * NUM_OF_ROOM_PER_PAGE + 1;
	}

	private int getEndRow(int currentPage) {
		return currentPage * NUM_OF_ROOM_PER_PAGE;
	}

	public boolean dateRoomCheck(Map<String, Object> roomitem) {

		String roomname = (String) roomitem.get("roomName");
		Timestamp start = null;
		Timestamp end = null;

		try {

			start = Timestamp.valueOf(((String) roomitem.get("start")).replace("T", " ") + ":00");
			end = Timestamp.valueOf(((String) roomitem.get("end")).replace("T", " ") + ":00");

		} catch (NullPointerException e) {

		}

		Map<String, Object> chroomdate = dao.selectDate(roomitem);

		boolean result = false;

		if (chroomdate == null) {

			result = true;

		} else if (roomname.equals(chroomdate.get("roomName"))) {
			result = false;
		} else if (start == chroomdate.get("startDate") && end == chroomdate.get("endDate")) {
			result = false;
		}

		else if (start.after(end) && end.before(start)) {
			result = false;
		}

		return result;
	}
}

package dao;

import java.util.List;
import java.util.Map;

public interface RoomitemDao {
	public int insertRoomitem(Map<String, Object> roomitem);
	
	public int updateRoomitem(Map<String, Object> roomitem);
	
	public int updatstatusRoomitem(Map<String, Object> roomitem);
	
	public List<Map<String, Object>> selectAll(String typeName);
	public List<Map<String, Object>> mainselectAll();
	
	public Map<String, Object> selectDate(Map<String, Object> roomitem);
	
	public List<Map<String, Object>> searchRoomitem(Map<String, Object> roomitem);
	
	public int totalRoomcount(Map<String, Object> param);
	
}

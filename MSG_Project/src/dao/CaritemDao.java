package dao;

import java.util.List;
import java.util.Map;
public interface CaritemDao {
	public int insertCaritem(Map<String, Object> caritem);
	
//	public int deleteCatitem(String assigned, int rendNo);
	
	public int updateCaritem(Map<String,Object> caritem);
	
	
	public int updatestatusCaritem(Map<String, Object> caritem);
	
	public List<Map<String, Object>> selectAll(String typeName);
	
	public List<Map<String, Object>> mainselectAll();
	
	public Map<String, Object> selectDate(Map<String, Object> caritem);	


//	public List<Map<String,Object>> slectTest();

	public List<Map<String, Object>> searchCaritem(Map<String, Object> params);

	public int totalCarCount(Map<String, Object> param);

}

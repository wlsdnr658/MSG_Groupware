package dao;

import java.util.List;
import java.util.Map;

public interface ItemDao {
	

	public List<Map<String, Object>> mainselectAll();
	
	public List<Map<String, Object>> selectAll();
	
	public String getFileName(String typeName);
	
	public int selectCount(Map<String, Object> item);
	
	public List<Map<String, Object>> itemsAll();
	

	
	
	public int insertitem(Map<String, Object> item);
	public int updateitem(Map<String, Object> item);
	
	//item 상태값 변동
	public int updateitemstatus(Map<String, Object> item);

	
	public int insertuploaditem(Map<String, Object> params);
	
	public int totalItemCount(Map<String, Object> param);
	public List<Map<String, Object>> searchItem(Map<String, Object> item);
}

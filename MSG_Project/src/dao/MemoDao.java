package dao;


import java.util.List;
import java.util.Map;

public interface MemoDao {
	
	public int insertMemo(Map<String, Object> memo);
	
	public int deleteMemo(Map<String, Object> memo);
	
	public int updateMemo(Map<String, Object> memo);
	
	public Map<String, Object> selectOneMemo(Map<String, Object> memo);
	
	public List<Map<String, Object>> selectAllMemo(String empNo);
	
	
}

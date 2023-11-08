package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.MemoDao;

@Service
public class MemoService {

	@Autowired
	private MemoDao memoDao;
	
	
	public boolean addMemo(Map<String, Object> memo) {
		
		int result = memoDao.insertMemo(memo);
		
		if(result>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean removeMemo(Map<String, Object> memo) {
		int result = memoDao.deleteMemo(memo);
		
		if(result>0) {
			return true;
		}else {
			return false;
		}	
	}
	
	public boolean modifyMemo(Map<String, Object> memo1) {
		
		int result = memoDao.updateMemo(memo1);
		
		if( result > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public Map<String, Object> getOneMemo(Map<String, Object> memo){
		return memoDao.selectOneMemo(memo);
	}
	
	public List<Map<String, Object>> getAllMemo(String empNo){
		
//		List<Map<String, Object>> memoList = memoDao.selectAllMemo(memo);
		
//		Map<String, Object> result = new HashMap<String,Object>();
		
//		result.put("memoList", memoList);
		
		return memoDao.selectAllMemo(empNo);
		
	}
	
	
}

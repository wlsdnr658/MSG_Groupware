package dao;

import java.util.List;
import java.util.Map;

public interface EmpDao {
	public Map<String, Object> getMember(String empNo);
	public Map<String, Object> getMemberInfo(String empNo);
	public int modifyEmpByUser(Map<String, String> params);
	public int modifyEmpPic(Map<String, String> params);
	public int modifyEmpByAdmin(Map<String, Object> params);
	public String getFileName(String empNo);
	public String getStampName(String empNo);
	public int getEmpTotal(Map<String, Object> params);
	public Map<String, Object> selectOne(String empNo);
	public List<Map<String,Object>> selectByDept(String dept);
	public List<Map<String,Object>> selectOderedAll_B();
	public List<Map<String,Object>> selectOderedAll(Map<String, Object> params);
	public List<Map<String,Object>> search_name();
	public List<Map<String,String>> getEmpsByName(String input_Name);
	public Map<String, Object> getEmpByNameAndDeptName(Map<String,String> empInfo);
}

package commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView{
	
	private File file;
	
	public DownloadView(File file) {
		this.file = file;
		//응답 타입을 다운로드로 변경
		setContentType("application/download; utf-8");
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> arg0, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//파일담아서 주면 됨
		response.setContentType(getContentType());
		response.setContentLengthLong(file.length());
		
		
		//UUID만 없앤 파일 이름을 만들어야 함
		String fullName = file.getName();
		int idx = fullName.indexOf("_")+1;
		String tmpName = fullName.substring(idx);
		
		String fileName = null;
		//브라우저에 따라서 다르게 인코딩
		//요청 헤더에서 user-agent 속성 가져와서
		//해당 속성의 값을 확인
		String userAgent = request.getHeader("User-Agent");
		//userAgent가 'MSIE' 라는 문자열을 포함하면 ie 이다.  
		boolean isIE = userAgent.indexOf("MSIE") > -1;
		if(isIE) {
			fileName 
			= URLEncoder.encode(tmpName,"utf-8");
		}else {
			fileName 
			= new String(tmpName.getBytes("UTF-8"),"ISO-8859-1");
		}
		//만들어낸 파일이름을 응답해더에 싣기
		response.setHeader("Content-Disposition",
				"attachment; filename=\""+fileName+"\";");
						//fileName = "test.txt";
		response.setHeader("Content-Transfer-Encoding","binary");
		//파일을 스트림으로 읽어와서 응답에서 outstream으로 내보내기 
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis,out);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(fis != null) fis.close();
		}
		out.flush();
	}
}









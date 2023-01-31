package com.mobileleader.edoc.monitoring.support.view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.mobileleader.edoc.monitoring.utils.DateTimeUtil;

/**
 * 엑셀 다운로드 뷰 (xlsx 파일)
 */
public class ExcelView extends AbstractXlsView {
	
	@Override
	public void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
		@SuppressWarnings("unchecked")
        HashMap<String, Object> map = (HashMap<String, Object>) model.get("map");
		
		// 파일명
		String fileName = (String)map.get("filename");
		setHeader(request, response, fileName);

		//엑셀 내용
		workbook = (Workbook)map.get("workbook");
		
		// 엑셀 파일 내려주기
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            workbook.write(os);
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if(os != null) try { os.close(); } catch (IOException e) { }
        }
	}
	
	@Override
	protected Workbook createWorkbook() {
		return new XSSFWorkbook();
	}
	
	/**
	 * Response header 세팅
	 * 
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 * @param fileName	파일명(확장자 제외)
	 */
	private void setHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
		String userAgent = request.getHeader("User-Agent");
		
		String fullFileName = null;
		try {
			if (userAgent.indexOf("MSIE") > -1) {
				fullFileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				fullFileName = new String(fileName.getBytes("UTF-8"), "8859_1");
			}
		} catch (UnsupportedEncodingException uee) {
			fullFileName = DateTimeUtil.getCurrentTime(0);
		}
		fullFileName += ".xlsx";
		
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + fullFileName + "");
	}

}

package client.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.edoc.util.JsonUtils;

import client.model.PacketVo;
import client.util.UserHttpClient;

public class SearchTempDataTest {
	
	private String url;
	private String context = "/download/temp/data";
	
	private Logger logger = LoggerFactory.getLogger(SearchTempDataTest.class);
	
	public SearchTempDataTest(String url) {
		this.url = url + context;
	}
	
	public void run(PacketVo request, String downloadPath) {
		logger.info("--------------Search TempData Test Start--------------");
		logger.info("[Sever URL] = {}\n[Json Data] = {}", url, request.toString());
		
		downloadPath = System.getProperties().getProperty("user.dir") + File.separator + downloadPath;
		String resultFilePath = searchData(JsonUtils.ObjectPrettyPrint(request), downloadPath);
		
		logger.info("[SearchTempData ResultFile Path] = {}", resultFilePath);
	}
	
	private String searchData(String jsonData, String downloadPath) {
		String resultFilePath = UserHttpClient.download(url, jsonData, downloadPath);
		File resultFile = new File(resultFilePath);
		
//		String decFilePath = downloadPath + File.separator + "dec_" + resultFile.getName();
//
//		try {
//			if(FileUtils.decrypt(resultFilePath, decFilePath)) {
//				resultFile.delete();
//				new File(decFilePath).renameTo(resultFile);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return resultFilePath;
	}

}

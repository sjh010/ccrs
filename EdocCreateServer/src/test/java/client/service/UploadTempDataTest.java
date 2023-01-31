package client.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.edoc.util.JsonUtils;

import client.model.PacketVo;
import client.util.UserHttpClient;

public class UploadTempDataTest {
	
	private String url;
	private String context = "/edoc/upload/tempData";
	
	private Logger logger = LoggerFactory.getLogger(UploadTempDataTest.class);
	
	public UploadTempDataTest(String url) {
		this.url = url + context;
	}
	
	public PacketVo run(PacketVo request, File file, boolean encryptYn) {
		
		logger.info("--------------Upload TempData Test Start--------------");
		logger.info("[Sever URL] = {}\n[Json Data] = {}", url, request.toString());
		
		String jsonRes = upload(JsonUtils.ObjectPrettyPrint(request), file, encryptYn);
		logger.debug("[[UploadTempData String Response] = {}", jsonRes);
		
		PacketVo response = JsonUtils.fromJson(jsonRes, PacketVo.class);
		
		logger.info("[UploadTempData Vo Response] = {}", response.toString());
		
		return response;
	}
	

	private String upload(String jsonData, File file, boolean encryptYn) {
		String filePath = file.getAbsolutePath();
//		if(encryptYn) {
//			//암호화
//			try {
//				String encFilePath = file.getParent() + File.separator + "enc_" + file.getName();
//				AESCrypto.getInstance().Encrypt(filePath, encFilePath, false);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		return UserHttpClient.upload(url, jsonData, filePath);
	}

}

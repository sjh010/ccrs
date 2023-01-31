package client.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.edoc.util.JsonUtils;

import client.model.PacketVo;
import client.util.UserHttpClient;

public class UploadEdocDataTest {
	
	private String url;
	private String context = "/edoc/upload";
	
	private Logger logger = LoggerFactory.getLogger(UploadEdocDataTest.class);
	
	public UploadEdocDataTest(String url) {
		this.url = url + context;
	}
	
	public PacketVo run(PacketVo request, File file, boolean encryptYn) {
		
		logger.info("--------------Upload EdocData Test Start--------------");
		logger.info("[Sever URL] = {}\n[Json Data] = {}", url, request);
		
		String jsonRes = upload(JsonUtils.ObjectPrettyPrint(request), file, encryptYn);
		logger.debug("[UploadEdocData String Response] = {}", jsonRes);
		
		PacketVo response = JsonUtils.fromJson(jsonRes, PacketVo.class);
		
		logger.info("[UploadEdocData Vo Response] = {}", response.toString());
		
		return response;
	}
	
	private String upload(String jsonData, File file, boolean encryptYn) {
		
		String filePath = (file != null)? file.getAbsolutePath() : "";
		
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

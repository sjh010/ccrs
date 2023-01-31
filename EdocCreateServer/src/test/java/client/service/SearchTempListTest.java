package client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.edoc.util.JsonUtils;

import client.model.PacketVo;
import client.util.UserHttpClient;

public class SearchTempListTest {
	
	private String url;
	private String context = "/download/temp/list";
	
	private Logger logger = LoggerFactory.getLogger(SearchTempListTest.class);
	
	public SearchTempListTest(String url) {
		this.url = url + context;
	}
	
	public PacketVo run(PacketVo request) {
		logger.info("--------------Search TempList Test Start--------------");
		logger.info("[Sever URL] = {}\n[Json Data] = {}", url, request.toString());
		
		String jsonRes = searchList(JsonUtils.ObjectPrettyPrint(request));
		logger.debug("[SearchTempList String Response] = {}", jsonRes);
		
		PacketVo response = JsonUtils.fromJson(jsonRes, PacketVo.class);
		
		logger.info("[SearchTempList Vo Response] = {}", response.toString());
		
		return response;
	}
	
	private String searchList(String jsonData) {
		return UserHttpClient.messageTrans(url, jsonData);
	}

}

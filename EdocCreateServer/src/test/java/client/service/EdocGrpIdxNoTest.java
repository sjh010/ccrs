package client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.edoc.util.JsonUtils;

import client.model.PacketVo;
import client.util.UserHttpClient;

public class EdocGrpIdxNoTest {
	
	private String url;
	private String context = "/edoc/getKey";
	
	private Logger logger = LoggerFactory.getLogger(EdocGrpIdxNoTest.class);
	
	public EdocGrpIdxNoTest(String url) {
		this.url = url + context;
	}
	
	public PacketVo run(PacketVo request) {
		
		logger.info("--------------Generate EdocGrpIdxNoTest Start--------------");
		logger.info("[Sever URL] = {}\n[Json Data] = {}", url, request.toString());
		
		String jsonRes = getEdocKey(JsonUtils.ObjectPrettyPrint(request));
		logger.debug("[GenerateEdocGrpIdxNo String Response] = {}", jsonRes);
		
		PacketVo response = JsonUtils.fromJson(jsonRes, PacketVo.class);
		String edocIdxNo = response.getCorEdocGrpIdxNo();
		
		logger.info("[GenerateEdocGrpIdxNo Vo Response] = {}\n[CorEdocGrpIdxNo] = {}", response.toString(), edocIdxNo);

		return response;
	}
	
	private String getEdocKey(String jsonData) {
		return UserHttpClient.messageTrans(url, jsonData);
	}
}

package com.mobileleader.edoc.monitoring.service.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mobileleader.edoc.monitoring.common.model.request.ImageSearchReqeust;

public interface ImageServerService {

	public HashMap<String, Object> searchImageStoreList(ImageSearchReqeust request);

	public List<HashMap<String, Object>> getChannelStatisticsFromImageServer(String mode, String param);
	
	public List<HashMap<String, Object>> getBranchStatisticsFromImageServer(String mode, String param);
	
	public ArrayList<HashMap<String, Object>> getDepartmentList();
}

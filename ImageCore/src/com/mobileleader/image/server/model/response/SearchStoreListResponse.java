package com.mobileleader.image.server.model.response;

import java.util.List;

import com.mobileleader.image.db.dto.ImageStoreDto;
import com.mobileleader.image.util.JsonUtil;

public class SearchStoreListResponse {

	private int resultCode;
	
	private String resultMessage;
	
	private int totalCount;
	
	private List<ImageStoreDto> storeList;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<ImageStoreDto> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<ImageStoreDto> storeList) {
		this.storeList = storeList;
	}

	public String toJsonString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}
	
	
}

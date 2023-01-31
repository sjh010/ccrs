package com.mobileleader.image.server.model.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gson.Gson;
import com.mobileleader.image.util.JsonUtil;

/**
 * 메인키 리스트 검색에 의해 반환되는 정보
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.11.02
 *
 */
@JsonAutoDetect
@JsonInclude(Include.NON_NULL)
public class SearchListResponse extends BaseResponse {
	
	private List<MainkeyListResponse> imageInfos = null;
	
	
	
	public List<MainkeyListResponse> getImageInfos() {
		return imageInfos;
	}
	
	public SearchListResponse setImageInfos(List<MainkeyListResponse> imageInfos) {
		this.imageInfos = imageInfos;
		return this;
	}
	
	public SearchListResponse setImageInfos(MainkeyListResponse imageInfo) {
		if (this.imageInfos == null)
			this.imageInfos = new ArrayList<MainkeyListResponse>();
		
		this.imageInfos.add(imageInfo);
		return this;
	}
	
	public String getJson() {
		return new Gson().toJson(this);
	}
	
	@Override
	public String toString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}
 
}
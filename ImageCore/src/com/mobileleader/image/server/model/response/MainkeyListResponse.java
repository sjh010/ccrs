package com.mobileleader.image.server.model.response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mobileleader.image.db.dto.ImageFileDto;
import com.mobileleader.image.util.JsonUtil;

/**
 * 메인키 리스트 검색에 의해 반환되는 정보
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.11.02
 *
 */
public class MainkeyListResponse {
	
	private String mainKey;							// 메인키
	private List<ImageFileDto> imageInfo = null; 	// 이미지 정보
	
	public String getMainKey() {
		return mainKey;
	}
	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}
	
	public List<ImageFileDto> getImageInfo() {
		return imageInfo;
	}
	public void setImageInfo(List<ImageFileDto> imageInfo) {
		if (this.imageInfo == null) {
			this.imageInfo = new ArrayList<ImageFileDto>();
		}
		this.imageInfo = imageInfo;
	}
	public void setImageInfo(ImageFileDto imageInfo) {
		if (this.imageInfo == null) {
			this.imageInfo = new ArrayList<ImageFileDto>();
		}
		this.imageInfo.add(imageInfo);
	}
	
	public String getJson() {
		return new Gson().toJson(this);
	}
	
	@Override
	public String toString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}
 
}
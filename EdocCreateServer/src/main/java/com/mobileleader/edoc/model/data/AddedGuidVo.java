package com.mobileleader.edoc.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileleader.edoc.util.JsonUtils;

/**
 *  Data.cfg의 "ADDED_GUID" 항목 값인 배열의 각 원소에 해당하는 VO Class
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class AddedGuidVo {
	
	@JsonProperty("GUID")
	private String guid = "";

	
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String toString() {
		return JsonUtils.toJson(this);
	}	
}

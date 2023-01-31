package com.mobileleader.edoc.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileleader.edoc.util.JsonUtils;

/**
 * Data.cfg의 GUID ~ 서식코드 연결("GUID_FORMCODE_LINK") VO Class<br>
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GuidFormcodeLinkVo {

	@JsonProperty("GUID")
	private String guid = null;
	
	@JsonProperty("FORMCODE")
	private String formcode = null;
	
	@JsonProperty("EXTENSION")
	private String exPageFormcode = null;


	public String getGuid() {
		return guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getFormcode() {
		return formcode;
	}
	
	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getExPageFormcode() {
		return exPageFormcode;
	}
	
	public void setExPageFormcode(String exPageFormcode) {
		this.exPageFormcode = exPageFormcode;
	}


	public String toString() {
		return JsonUtils.ObjectPrettyPrint(this);
	}

}

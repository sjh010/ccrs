package com.mobileleader.edoc.model.data;

/**
 * GUID ~ 서식코드 ~ 메인서식코드 관계 VO Class
 *
 */
public class GuidFormcodeMainFormcodeVo {
	
	private String guid = null;
	private String formcode = null;
	private String mainFormcode = null;

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

	public String getMainFormcode() {
		return mainFormcode;
	}
	
	public void setMainFormcode(String mainFormcode) {
		this.mainFormcode = mainFormcode;
	}

}

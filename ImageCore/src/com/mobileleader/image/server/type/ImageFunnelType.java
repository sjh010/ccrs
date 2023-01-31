package com.mobileleader.image.server.type;

/**
 * 문서 유입 경로 (FUNNELS) 정의 클래스
 * 
 * @author user
 */
public enum ImageFunnelType {
	
	CYBER("cyber", "cyber"),
	SCAN("scan", "scan"),
	EDOC("edoc", "edoc")
	;
	
	private String code;
	private String descript;
	
	private ImageFunnelType(String code, String descript) {
		this.code = code;
		this.descript = descript;
	}
	
	public String getCode() {
		return code;
	}
	public String getDescript() {
		return descript;
	}

}

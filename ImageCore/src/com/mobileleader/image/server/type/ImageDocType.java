package com.mobileleader.image.server.type;

/**
 * 문서타입(DOC_TYPE) 정의 클래스
 * 
 * @author user
 */
public enum ImageDocType {

	JPG("jpg"),
	PDF("pdf"),
	TIF("tif")
	;
	
	private String type;
	
	private ImageDocType(String type) {
		this.type=type;
	}
	
	public String getType() {
		return type;
	}
}

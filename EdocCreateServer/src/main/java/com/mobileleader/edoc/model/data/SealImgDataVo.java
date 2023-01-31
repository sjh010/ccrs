package com.mobileleader.edoc.model.data;

import com.mobileleader.edoc.util.JsonUtils;

/**
 * Data.cfg의 서식폴더데이터("FLDR_DATA")의 인감이미지데이타("SEALIMG_DATA") VO Class
 * 주의 : 현재 사용하지 않고 있음.
 */
public class SealImgDataVo {

	String fileType = "";
	String fileName = "";

	public String getFileType() {
		return fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String toString() {
		return JsonUtils.toJson(this);
	}
}

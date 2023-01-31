package com.mobileleader.edoc.model.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileleader.edoc.util.JsonUtils;

/**
 * Data.cfg의 서식폴더데이터("FLDR_DATA") VO Class
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class FldrDataVo {
	
	@JsonProperty("FLDR_NM")
	private String fldrNm;
	
	@JsonProperty("FORMCODE")
	private String formcode;
	
	@JsonProperty("FORMNAME")
	private String formname;
	
	@JsonProperty("FORMVER")
	private String formver;

	@JsonProperty("XML_NM")
	private String xmlNm = " ";
	
	@JsonProperty("PDF_NM")
	private String pdfNm = " ";
	
	@JsonProperty("IMG_CNT")
	private long imgCnt = 0L;
	
	@JsonProperty("PAGE_CNT")
	private long pageCnt = 0L;
	
	@JsonProperty("SEALIMG_DATA")
	private List<SealImgDataVo> sealimgData = new ArrayList<SealImgDataVo>();

	
	public String getFldrNm() {
		return fldrNm;
	}
	
	public void setFldrNm(String fldrNm) {
		this.fldrNm = fldrNm;
	}

	public String getFormname() {
		return formname;
	}
	
	public void setFormname(String formname) {
		this.formname = formname;
	}

	public String getFormcode() {
		return formcode;
	}
	
	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getFormver() {
		return formver;
	}
	
	public void setFormver(String formver) {
		this.formver = formver;
	}

	public String getXmlNm() {
		return xmlNm;
	}
	
	public void setXmlNm(String xmlNm) {
		this.xmlNm = xmlNm;
	}

	public long getImgCnt() {
		return imgCnt;
	}
	
	public void setImgCnt(long imgCnt) {
		this.imgCnt = imgCnt;
	}
	
	public List<SealImgDataVo> getSealimgData() {
		return sealimgData;
	}
	
	public void setSealimgData(List<SealImgDataVo> sealimgData) {
		this.sealimgData = sealimgData;
	}
	
	public String getPdfNm() {
		return pdfNm;
	}
	
	public void setPdfNm(String pdfNm) {
		this.pdfNm = pdfNm;
	}
	
	public long getPageCnt() {
		return pageCnt;
	}
	
	public void setPageCnt(long pageCnt) {
		this.pageCnt = pageCnt;
	}
	
	
	public String toString() {
		return JsonUtils.ObjectPrettyPrint(this);
	}
}

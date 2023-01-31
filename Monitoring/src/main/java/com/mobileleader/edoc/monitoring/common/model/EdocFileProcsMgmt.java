package com.mobileleader.edoc.monitoring.common.model;

import java.util.Date;

public class EdocFileProcsMgmt {
	
	private String elecDocGroupInexNo;			// 전자문서 그룹인덱스번호
	private int fileSeqNo;						// 파일일련번호
	private String lefrmCd;						// 서식코드
	private String lefrmNm;						// 서식명
	private String xmlFileNm;					// XML파일명
	private String pdfFileNm;					// PDF파일명
	private int pageCnt;						// 페이지수
	private String procsStepCd;					// 처리단계코드
	private String procsStepStcd;				// 처리단계상태코드
	private String signFileNm;					// 서명파일명
	private String sealFileNm;					// 인감파일명
	private Date crtnTime;						// 생성시각
	private int lefrmVer;						// 생성서식버전
	private String mainLefrmCd;					// 메인서식코드
	private String guid;						// GUID
	
	public String getElecDocGroupInexNo() {
		return elecDocGroupInexNo;
	}
	public void setElecDocGroupInexNo(String elecDocGroupInexNo) {
		this.elecDocGroupInexNo = elecDocGroupInexNo;
	}
	public int getFileSeqNo() {
		return fileSeqNo;
	}
	public void setFileSeqNo(int fileSeqNo) {
		this.fileSeqNo = fileSeqNo;
	}
	public String getLefrmCd() {
		return lefrmCd;
	}
	public void setLefrmCd(String lefrmCd) {
		this.lefrmCd = lefrmCd;
	}
	public String getLefrmNm() {
		return lefrmNm;
	}
	public void setLefrmNm(String lefrmNm) {
		this.lefrmNm = lefrmNm;
	}
	public String getXmlFileNm() {
		return xmlFileNm;
	}
	public void setXmlFileNm(String xmlFileNm) {
		this.xmlFileNm = xmlFileNm;
	}
	public String getPdfFileNm() {
		return pdfFileNm;
	}
	public void setPdfFileNm(String pdfFileNm) {
		this.pdfFileNm = pdfFileNm;
	}
	public int getPageCnt() {
		return pageCnt;
	}
	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}
	public String getProcsStepCd() {
		return procsStepCd;
	}
	public void setProcsStepCd(String procsStepCd) {
		this.procsStepCd = procsStepCd;
	}
	public String getProcsStepStcd() {
		return procsStepStcd;
	}
	public void setProcsStepStcd(String procsStepStcd) {
		this.procsStepStcd = procsStepStcd;
	}
	public String getSignFileNm() {
		return signFileNm;
	}
	public void setSignFileNm(String signFileNm) {
		this.signFileNm = signFileNm;
	}
	public String getSealFileNm() {
		return sealFileNm;
	}
	public void setSealFileNm(String sealFileNm) {
		this.sealFileNm = sealFileNm;
	}
	public Date getCrtnTime() {
		return crtnTime;
	}
	public void setCrtnTime(Date crtnTime) {
		this.crtnTime = crtnTime;
	}
	public int getLefrmVer() {
		return lefrmVer;
	}
	public void setLefrmVer(int lefrmVer) {
		this.lefrmVer = lefrmVer;
	}
	public String getMainLefrmCd() {
		return mainLefrmCd;
	}
	public void setMainLefrmCd(String mainLefrmCd) {
		this.mainLefrmCd = mainLefrmCd;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Override
	public String toString() {
		return "EdocFileProcsMgmt [elecDocGroupInexNo=" + elecDocGroupInexNo + ", fileSeqNo=" + fileSeqNo + ", lefrmCd="
				+ lefrmCd + ", lefrmNm=" + lefrmNm + ", xmlFileNm=" + xmlFileNm + ", pdfFileNm=" + pdfFileNm
				+ ", pageCnt=" + pageCnt + ", procsStepCd=" + procsStepCd + ", procsStepStcd=" + procsStepStcd
				+ ", signFileNm=" + signFileNm + ", sealFileNm=" + sealFileNm + ", crtnTime=" + crtnTime + ", lefrmVer="
				+ lefrmVer + ", mainLefrmCd=" + mainLefrmCd + ", guid=" + guid + "]";
	}
}

package com.mobileleader.edoc.db.dto;

import java.util.Date;

import com.mobileleader.edoc.util.JsonUtils;

/**
 * 전자문서 처리 시 생성되는 파일 관리 정보 DTO (EDS_ELEC_DOC_FILE_PROCS_MGMT Table)
 */
public class EdocFileProcsMgmtDto {

	private String elecDocGroupInexNo;						// 전자문서그룹인덱스번호	ELEC_DOC_GROUP_INEX_NO
	private int   fileSeqNo;								// 파일순번			FILE_SEQ_NO
	private String lefrmCd;									// 서식코드			LEFRM_CD
	private String lefrmNm;									// 서식명				LEFRM_NM
	private String xmlFileNm;								// XML파일명			XML_FILE_NM
	private String pdfFileNm;								// PDF파일명			PDF_FILE_NM
	private int    pageCnt;									// 페이지수			PAGE_CNT
	private String procsStepCd;								// 처리단계코드			PROCS_STEP_CD
	private String procsStepStcd;							// 처리단계상태코드		PROCS_STEP_STCD
	private Date   crtnTime;								// 생성시각			CRTN_TIME
	private String lefrmVer;								// 서식버전			LEFRM_VER
	private String guid;									// GUID				GUID
	private String mainLefrmCd;								// 메인 서식 코드 		MAIN_LEFRM_CD

	public String getElecDocGroupInexNo() {
		return elecDocGroupInexNo;
	}

	public void setElecDocGroupInexNo(String elecDocGroupInexNo) {
		this.elecDocGroupInexNo = elecDocGroupInexNo;
	}

	public long getFileSeqNo() {
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

	public Date getCrtnTime() {
		return crtnTime;
	}

	public void setCrtnTime(Date crtnTime) {
		this.crtnTime = crtnTime;
	}
	
	public String getLefrmVer() {
		return lefrmVer;
	}

	public void setLefrmVer(String lefrmVer) {
		this.lefrmVer = lefrmVer;
	}

	public String getGuid() {
		return guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getMainLefrmCd() {
		return mainLefrmCd;
	}
	
	public void setMainLefrmCd(String mainLefrmCd) {
		this.mainLefrmCd = mainLefrmCd;
	}

	@Override
	public String toString() {
		return JsonUtils.ObjectPrettyPrint(this);
	}
}

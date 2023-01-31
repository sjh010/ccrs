package com.mobileleader.edoc.monitoring.common.model;

import java.util.Date;

public class EdsEcmRegInfo {
	
	private String elecDocGroupInexNo;			// 전자문서그룹인덱스번호
	private String fileId;						// FileID
	private	String docTycd;						// 문서유형코드
	private String docNm;						// 문서명
	private String bzwkDvcd;					// 업무구분코드
	private String subBzwkDvcd;					// 서브업무구분코드
	private String lefrmCd;						// 서식코드
	private String lefrmNm;						// 서식명
	private String fileTycd;					// 파일타입코드
	private String fileNm;						// 파일명
	private int regNth;							// 등록회차
	private Date regTime;						// 등록시각
	private String guid;						// GUID
	
	public String getElecDocGroupInexNo() {
		return elecDocGroupInexNo;
	}
	public void setElecDocGroupInexNo(String elecDocGroupInexNo) {
		this.elecDocGroupInexNo = elecDocGroupInexNo;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getDocTycd() {
		return docTycd;
	}
	public void setDocTycd(String docTycd) {
		this.docTycd = docTycd;
	}
	public String getDocNm() {
		return docNm;
	}
	public void setDocNm(String docNm) {
		this.docNm = docNm;
	}
	public String getBzwkDvcd() {
		return bzwkDvcd;
	}
	public void setBzwkDvcd(String bzwkDvcd) {
		this.bzwkDvcd = bzwkDvcd;
	}
	public String getSubBzwkDvcd() {
		return subBzwkDvcd;
	}
	public void setSubBzwkDvcd(String subBzwkDvcd) {
		this.subBzwkDvcd = subBzwkDvcd;
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
	public String getFileTycd() {
		return fileTycd;
	}
	public void setFileTycd(String fileTycd) {
		this.fileTycd = fileTycd;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public int getRegNth() {
		return regNth;
	}
	public void setRegNth(int regNth) {
		this.regNth = regNth;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@Override
	public String toString() {
		return "EdsEcmRegInfo [elecDocGroupInexNo=" + elecDocGroupInexNo + ", fileId=" + fileId + ", docTycd=" + docTycd
				+ ", docNm=" + docNm + ", bzwkDvcd=" + bzwkDvcd + ", subBzwkDvcd=" + subBzwkDvcd + ", lefrmCd="
				+ lefrmCd + ", lefrmNm=" + lefrmNm + ", fileTycd=" + fileTycd + ", fileNm=" + fileNm + ", regNth="
				+ regNth + ", regTime=" + regTime + ", guid=" + guid + "]";
	}

}

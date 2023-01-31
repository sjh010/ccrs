package com.mobileleader.edoc.monitoring.common.model;

import java.util.Date;

public class EdocGroupProcsMgmt {
	
	private String elecDocGroupInexNo;			// 전자문서 그룹인덱스번호
	private Date crtnTime;						// 생성시각
	private String procsStepCd;					// 처리단계코드
	private String procsStepStCd;				// 처리단계상태코드
	private String procsStepMsgCd;				// 처리단계메시지코드
	private Date procsStepStTime;				// 처리단계시작시각
	private Date procsStepEdTime;				// 처리단계종료시각
	private String svrIp;						// 서버IP
	
	public String getElecDocGroupInexNo() {
		return elecDocGroupInexNo;
	}
	public void setElecDocGroupInexNo(String elecDocGroupInexNo) {
		this.elecDocGroupInexNo = elecDocGroupInexNo;
	}
	public Date getCrtnTime() {
		return crtnTime;
	}
	public void setCrtnTime(Date crtnTime) {
		this.crtnTime = crtnTime;
	}
	public String getProcsStepCd() {
		return procsStepCd;
	}
	public void setProcsStepCd(String procsStepCd) {
		this.procsStepCd = procsStepCd;
	}
	public String getProcsStepStCd() {
		return procsStepStCd;
	}
	public void setProcsStepStCd(String procsStepStCd) {
		this.procsStepStCd = procsStepStCd;
	}
	public String getProcsStepMsgCd() {
		return procsStepMsgCd;
	}
	public void setProcsStepMsgCd(String procsStepMsgCd) {
		this.procsStepMsgCd = procsStepMsgCd;
	}
	public Date getProcsStepStTime() {
		return procsStepStTime;
	}
	public void setProcsStepStTime(Date procsStepStTime) {
		this.procsStepStTime = procsStepStTime;
	}
	public Date getProcsStepEdTime() {
		return procsStepEdTime;
	}
	public void setProcsStepEdTime(Date procsStepEdTime) {
		this.procsStepEdTime = procsStepEdTime;
	}
	public String getSvrIp() {
		return svrIp;
	}
	public void setSvrIp(String svrIp) {
		this.svrIp = svrIp;
	}
	@Override
	public String toString() {
		return "EdocGroupProcsMgmt [elecDocGroupInexNo=" + elecDocGroupInexNo + ", crtnTime=" + crtnTime
				+ ", procsStepCd=" + procsStepCd + ", procsStepStCd=" + procsStepStCd + ", procsStepMsgCd="
				+ procsStepMsgCd + ", procsStepStTime=" + procsStepStTime + ", procsStepEdTime=" + procsStepEdTime
				+ ", svrIp=" + svrIp + "]";
	}
	
}

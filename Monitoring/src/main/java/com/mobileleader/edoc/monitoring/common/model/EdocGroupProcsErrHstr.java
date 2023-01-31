package com.mobileleader.edoc.monitoring.common.model;

import java.util.Date;

public class EdocGroupProcsErrHstr {
	
	private String elecDocGroupInexNo;			// 전자문서 그룹인덱스번호
	private int seqno;							// 일련번호
	private Date crtnTime;						// 생성시각
	private String procsStepCd;					// 처리단계코드
	private String procsStepStcd;				// 처리단계상태코드
	private String procsStepMsgCd;				// 처리단계메시지코드
	private Date procsStepStTime;				// 처리단계시작시각
	private Date procsStepEdTime;				// 처리단계종료시각
	private String errMsg;						// 오류메시지
	private String svrIp;						// 서버IP
	
	public String getElecDocGroupInexNo() {
		return elecDocGroupInexNo;
	}
	public void setElecDocGroupInexNo(String elecDocGroupInexNo) {
		this.elecDocGroupInexNo = elecDocGroupInexNo;
	}
	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
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
	public String getProcsStepStcd() {
		return procsStepStcd;
	}
	public void setProcsStepStcd(String procsStepStcd) {
		this.procsStepStcd = procsStepStcd;
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
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getSvrIp() {
		return svrIp;
	}
	public void setSvrIp(String svrIp) {
		this.svrIp = svrIp;
	}
	@Override
	public String toString() {
		return "EdocGroupProcsErrHstr [elecDocGroupInexNo=" + elecDocGroupInexNo + ", seqno=" + seqno + ", crtnTime="
				+ crtnTime + ", procsStepCd=" + procsStepCd + ", procsStepStcd=" + procsStepStcd + ", procsStepMsgCd="
				+ procsStepMsgCd + ", procsStepStTime=" + procsStepStTime + ", procsStepEdTime=" + procsStepEdTime
				+ ", errMsg=" + errMsg + ", svrIp=" + svrIp + "]";
	}

}

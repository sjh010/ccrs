package com.mobileleader.edoc.monitoring.db.dto;

import java.util.Date;

/**
 * 시스템 관리 (EDS_SYSTEM_MGMT)
 * 
 * @author bkcho
 *
 */
public class SystemMgmtDto {
	
	/**
	 * 서버ID(PK)
	 */
	private String svrId;
	
	/**
	 * 어플리케이션ID
	 */
	private String appId;
	
	/**
	 * 서버명
	 */
	private String svrNm;
	
	/**
	 * 서버IP
	 */
	private String svrIp;
	
	/**
	 * 어플리케이션포트
	 */
	private String appPort;
	
	/**
	 * 등록일시
	 */
	private Date regTime;
	
	/**
	 * 등록직원번호
	 */
	private String regEmpNo;
	
	/**
	 * 수집시간
	 */
	private Date cltTime;
	
	/**
	 * 서버상태코드
	 */
	private String serverStatCd;
	
	/**
	 * 에러수집횟수
	 */
	private int errCnt;
	
	/**
	 * 서버 상태 체크 방식 (N:수집안함, S: socket방식, D: DB방식)
	 */
	private String cltKind; 
	
	/**
	 * 데몬 시작 스크립트 실행파일 FULL PATH
	 */
	private String startScript;
	
	/**
	 * 데몬 중지 스크립트 실행파일 FULL PATH
	 */
	private String stopScript;
	
	/**
	 * 데몬 재시작 스크립트 실행파일 FULL PATH
	 */
	private String restartScript;
	
	public String getSvrId() {
		return svrId;
	}
	public void setSvrId(String svrId) {
		this.svrId = svrId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSvrNm() {
		return svrNm;
	}
	public void setSvrNm(String svrNm) {
		this.svrNm = svrNm;
	}
	public String getSvrIp() {
		return svrIp;
	}
	public void setSvrIp(String svrIp) {
		this.svrIp = svrIp;
	}
	public String getAppPort() {
		return appPort;
	}
	public void setAppPort(String appPort) {
		this.appPort = appPort;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getRegEmpNo() {
		return regEmpNo;
	}
	public void setRegEmpNo(String regEmpNo) {
		this.regEmpNo = regEmpNo;
	}
	public Date getCltTime() {
		return cltTime;
	}
	public void setCltTime(Date cltTime) {
		this.cltTime = cltTime;
	}
	public String getServerStatCd() {
		return serverStatCd;
	}
	public void setServerStatCd(String serverStatCd) {
		this.serverStatCd = serverStatCd;
	}
	public int getErrCnt() {
		return errCnt;
	}
	public void setErrCnt(int errCnt) {
		this.errCnt = errCnt;
	}
	public String getCltKind() {
		return cltKind;
	}
	public void setCltKind(String cltKind) {
		this.cltKind = cltKind;
	}
	public String getStartScript() {
		return startScript;
	}
	public void setStartScript(String startScript) {
		this.startScript = startScript;
	}
	public String getStopScript() {
		return stopScript;
	}
	public void setStopScript(String stopScript) {
		this.stopScript = stopScript;
	}
	public String getRestartScript() {
		return restartScript;
	}
	public void setRestartScript(String restartScript) {
		this.restartScript = restartScript;
	}
	
	@Override
	public String toString() {
		return "SystemMgmtDto [svrId=" + svrId + ", appId=" + appId + ", svrNm=" + svrNm + ", svrIp=" + svrIp
				+ ", appPort=" + appPort + ", regTime=" + regTime + ", regEmpNo=" + regEmpNo + ", cltTime=" + cltTime
				+ ", serverStatCd=" + serverStatCd + ", errCnt=" + errCnt + ", cltKind=" + cltKind + ", startScript="
				+ startScript + ", stopScript=" + stopScript + ", restartScript=" + restartScript + "]";
	}
	
	
	
}

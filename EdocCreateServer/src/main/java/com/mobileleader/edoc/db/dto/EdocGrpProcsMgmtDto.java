package com.mobileleader.edoc.db.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobileleader.edoc.util.JsonUtils;

/**
 * 전자문서그룹 정보 관리 데이터 DTO  (EDS_ELEC_DOC_GROUP_PROCS_MGMT table)
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class EdocGrpProcsMgmtDto {

	private String elecDocGroupInexNo = null; 						// 전자문서그룹인덱스번호	ELEC_DOC_GROUP_INEX_NO
	private Date   crtnTime           = null; 						// 생성시각			CRTN_TIME
	private String procsStepCd        = null; 						// 처리단계코드			PROCS_STEP_CD
	private String procsStepStcd      = null; 						// 처리단계상태코드 		PROCS_STEP_STCD
	private String procsStepMsgCd     = null; 						// 처리단계메시지코드		PROCS_STEP_MSG_CD
	private Date   procsStepStTime    = null; 						// 처리단계시작시각 		PROCS_STEP_ST_TIME
	private Date   procsStepEdTime    = null; 						// 처리단계종료시각 		PROCS_STEP_ED_TIME
	private String svrIp              = null; 						// 서버IP				SVR_IP

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

	public String getSvrIp() {
		return svrIp;
	}

	public void setSvrIp(String svrIp) {
		this.svrIp = svrIp;
	}
	
	@Override
	public String toString() {
		return JsonUtils.ObjectPrettyPrint(this);
	}
}

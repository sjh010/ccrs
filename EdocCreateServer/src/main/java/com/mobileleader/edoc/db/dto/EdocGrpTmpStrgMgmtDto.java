package com.mobileleader.edoc.db.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileleader.edoc.util.JsonUtils;
import com.mobileleader.edoc.validation.ValidationGroup;

/**
 * 임시 저장 관리 정보 DTO (EDS_ELEC_DOC_GROUP_TMP_STRG_MGMT table)
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdocGrpTmpStrgMgmtDto extends EdocGrpBzwkInfoDto{

	@JsonIgnore
	private String svrFile;																		// 서버파일전체경로	SVR_FILE
	
	@JsonProperty("bizTmpDataRegTime")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyyMMddHHmmss", timezone="Asia/Seoul")
	private Date lastTmpStrgTime;																// 최종임시저장시각	LAST_TMP_STRG_TIME
	
	@NotEmpty(message="bizTmpDataStrgType is null", groups={ValidationGroup.SaveTmp.class})
	@Size(max=1, message="bizTmpDataStrgType size is wrong")
	@JsonProperty("bizTmpDataStrgType")
	private String tmpStrgTycd;																	// 임시저장유형코드	TMP_STRG_TYCD
	
	@JsonProperty("bizTmpDataMemo")
	private String tmpMemo;																		//	임시저장 메모 	TMP_MEMO

	public String getSvrFile() {
		return svrFile;
	}

	public void setSvrFile(String svrFile) {
		this.svrFile = svrFile;
	}

	public Date getLastTmpStrgTime() {
		return lastTmpStrgTime;
	}

	public void setLastTmpStrgTime(Date lastTmpStrgTime) {
		this.lastTmpStrgTime = lastTmpStrgTime;
	}

	public String getTmpStrgTycd() {
		return tmpStrgTycd;
	}

	public void setTmpStrgTycd(String tmpStrgTycd) {
		this.tmpStrgTycd = tmpStrgTycd;
	}
	
	public String getTmpMemo() {
		return tmpMemo;
	}

	public void setTmpMemo(String tmpMemo) {
		this.tmpMemo = tmpMemo;
	}


	@Override
	public String toString() {
		return JsonUtils.ObjectPrettyPrint(this);
	}
}

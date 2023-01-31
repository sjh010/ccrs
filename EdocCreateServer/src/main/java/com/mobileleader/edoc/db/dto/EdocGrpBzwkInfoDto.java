package com.mobileleader.edoc.db.dto;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileleader.edoc.annotation.ValidMainKey;
import com.mobileleader.edoc.util.CryptoSHA2;
import com.mobileleader.edoc.util.JsonUtils;
import com.mobileleader.edoc.validation.ValidationGroup;

/**
 * 전자문서그룹에 대한 업무 정보 DTO (EDS_ELEC_DOC_GROUP_BZWK_INFO table)
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdocGrpBzwkInfoDto {
	
	@NotEmpty(message="corEdocGrpIdxNo is null",
			groups={ValidationGroup.Upload.class, ValidationGroup.SaveTmp.class, ValidationGroup.DownloadTmp.class})
	@Size(max=15, message="corEdocGrpIdxNo size is wrong")
	@JsonProperty("corEdocGrpIdxNo")
	private String elecDocGroupInexNo;																					// 전자문서그룹인덱스번호	ELEC_DOC_GROUP_INEX_NO
	
	@JsonProperty("bizMappingKey")
	private String mappingKey;																							// 	매핑키 			MPNG_KEY

	@JsonProperty("bizScrnNo")
	private String scnNo;																								// 화면번호			SCN_NO

	@JsonProperty("bizSubScrnNo")
	private String subScrnNo;																							// 부화면번호			SUB_SCRN_NO

	@NotEmpty(message="bizRgstBrCd is null")
	@Size(max=5, message="bizRgstBrCd size is wrong")
	@JsonProperty("bizRgstBrCd")
	private String dsrbCd;																								// 취급점코드			DSRB_CD

	@JsonProperty("bizRgstBrNm")
	private String dsrbNm;																								// 취급점명			DSRB_NM
	
	@NotEmpty(message="bizRgstEmpNo is null")
	@Size(max=10, message="bizRgstEmpNo size is wrong")
	@JsonProperty("bizRgstEmpNo")
	private String hndrNo;																								// 취급자직번			HNDR_NO

	@JsonProperty("bizRgstEmpNm")
	private String hndrNm;																								// 취급자명			HNDR_NM

	@JsonProperty("bizTerminalNo")
	private String trmnNo;																								// 단말번호			TRMN_NO
	
	@NotEmpty(message="bizProcessType is null", groups={ValidationGroup.Upload.class})
	@Size(max=3, message="bizProcessType size is wrong")
	@JsonProperty("bizProcessType")
	private String prcsTycd;																							// 프로세스유형코드		PRCS_TYCD

	@JsonProperty("corCrtTs")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyyMMddHHmmss", timezone="Asia/Seoul")
	private Date   regTime;																								// 등록시각			REG_TIME

	@NotEmpty(message="MainKey is null", groups={ValidationGroup.Upload.class})
	@Size(max=14, message="MainKey size is wrong")
	@Pattern(regexp="[0-9]+", groups={ValidationGroup.Upload.class})
	@ValidMainKey
	@JsonProperty("MainKey")
	private String mainKey;																								// 통합 전자문서 키		MAIN_KEY							
	
	@Size(max=20, message="TaskKey size is wrong")
	@JsonProperty("TaskKey")
	private String taskKey;																								// 업무키				TASK_KEY
	
	@NotBlank(message="CustomerName is null", groups={ValidationGroup.Upload.class})
	@JsonProperty("CustomerName")
	private String customerName;																						// 고객명				CUSTOMER_NAME
	
	@NotEmpty(message="InsourceId is null", groups={ValidationGroup.Upload.class})
	@Size(max=15, message="InsourceId size is wrong")
	@JsonProperty("InsourceId")
	private String insourceId;																							// 업무코드			INSOURCE_ID

	@JsonProperty("InsourceTitle")
	private String insourceTitle;																						// 업무명				INSOURCE_TITLE

	@JsonProperty("Memo")
	private String memo;																								// 메모				MEMO

	
	public String getElecDocGroupInexNo() {
		return elecDocGroupInexNo;
	}
	
	public void setElecDocGroupInexNo(String elecDocGroupInexNo) {
		this.elecDocGroupInexNo = elecDocGroupInexNo;
	}

	public String getScnNo() {
		return scnNo;
	}

	public void setScnNo(String scnNo) {
		this.scnNo = scnNo;
	}

	public String getSubScrnNo() {
		return subScrnNo;
	}

	public void setSubScrnNo(String subScrnNo) {
		this.subScrnNo = subScrnNo;
	}

	public String getDsrbCd() {
		return dsrbCd;
	}

	public void setDsrbCd(String dsrbCd) {
		this.dsrbCd = dsrbCd;
	}

	public String getDsrbNm() {
		return dsrbNm;
	}

	public void setDsrbNm(String dsrbNm) {
		this.dsrbNm = dsrbNm;
	}

	public String getHndrNo() {
		return hndrNo;
	}

	public void setHndrNo(String hndrNo) {
		this.hndrNo = hndrNo;
	}

	public String getHndrNm() {
		return hndrNm;
	}

	public void setHndrNm(String hndrNm) {
		this.hndrNm = hndrNm;
	}

	public String getTrmnNo() {
		return trmnNo;
	}

	public void setTrmnNo(String trmnNo) {
		this.trmnNo = trmnNo;
	}

	public String getPrcsTycd() {
		return prcsTycd;
	}

	public void setPrcsTycd(String prcsTycd) {
		this.prcsTycd = prcsTycd;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getMainKey() {
		return mainKey;
	}

	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getInsourceId() {
		return insourceId;
	}

	public void setInsourceId(String insourceId) {
		this.insourceId = insourceId;
	}

	public String getInsourceTitle() {
		return insourceTitle;
	}

	public void setInsourceTitle(String insourceTitle) {
		this.insourceTitle = insourceTitle;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		//주민번호 암호화
		if(memo != null) {
			String encMemo = CryptoSHA2.getIdEnc(memo);
			this.memo = encMemo;
		}
	}

	public String getMappingKey() {
		return mappingKey;
	}

	public void setMappingKey(String mappingKey) {
		this.mappingKey = mappingKey;
	}

	@Override
	public String toString() {
		return JsonUtils.ObjectPrettyPrint(this);
	}
}

package com.mobileleader.edoc.monitoring.common.model;

import java.util.Date;

public class EdocGroupBzwkInfo {
	
	private String elecDocGroupInexNo;			// 전자문서인덱스번호
	private String scnNo;						// 화면번호
	private String dsrbCd;						// 취급점코드
	private String dsrbNm;						// 취급점명
	private String hndrNo;						// 취급자번호
	private String hndrNm;						// 취급자명
	private Date regTime;						// 등록시각
	private String prcsTycd;					// 프로세스유형코드
	
	private String mainKey;						// 통합전자문서키
	private String insourceId;					// 업무코드
	private String insourceTitle;				// 업무명
	private String taskKey;						// 업무키(접수번호)
	private String customerName;				// 고객명
	private String memo;						// 메모
	
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
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getPrcsTycd() {
		return prcsTycd;
	}
	public void setPrcsTycd(String prcsTycd) {
		this.prcsTycd = prcsTycd;
	}
	public String getMainKey() {
		return mainKey;
	}
	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EdocGroupBzwkInfo [");
		if (elecDocGroupInexNo != null)
			builder.append("elecDocGroupInexNo=").append(elecDocGroupInexNo).append(", ");
		if (scnNo != null)
			builder.append("scnNo=").append(scnNo).append(", ");
		if (dsrbCd != null)
			builder.append("dsrbCd=").append(dsrbCd).append(", ");
		if (dsrbNm != null)
			builder.append("dsrbNm=").append(dsrbNm).append(", ");
		if (hndrNo != null)
			builder.append("hndrNo=").append(hndrNo).append(", ");
		if (hndrNm != null)
			builder.append("hndrNm=").append(hndrNm).append(", ");
		if (regTime != null)
			builder.append("regTime=").append(regTime).append(", ");
		if (prcsTycd != null)
			builder.append("prcsTycd=").append(prcsTycd).append(", ");
		if (mainKey != null)
			builder.append("mainKey=").append(mainKey).append(", ");
		if (insourceId != null)
			builder.append("insourceId=").append(insourceId).append(", ");
		if (insourceTitle != null)
			builder.append("insourceTitle=").append(insourceTitle).append(", ");
		if (taskKey != null)
			builder.append("taskKey=").append(taskKey).append(", ");
		if (customerName != null)
			builder.append("customerName=").append(customerName).append(", ");
		if (memo != null)
			builder.append("memo=").append(memo);
		builder.append("]");
		return builder.toString();
	}
	
}

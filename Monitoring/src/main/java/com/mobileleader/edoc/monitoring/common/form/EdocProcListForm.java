package com.mobileleader.edoc.monitoring.common.form;

public class EdocProcListForm extends BaseFrom {
	
	private String elecDocGroupInexNo;			// 전자문서 그룹인덱스번호
	private String workType;					// 업무유형코드
	
	private String insourceId;					// 업무코드
	private String mainKey;						// 통합전자문서키
	
	private String subBzwkDvcd;					// 서브업무구분코드
	private String dsrbCd;						// 취급점코드
	private String hndrNo;						// 취급자번호
	private String prcsTycd;					// 프로세스유형코드
	private String procsStepCd;					// 처리단계코드
	private String procsStepStcd;				// 처리단계상태코드
	private String cstno;			// 고객번호
	private String customerName;			// 고객명
	private String svrIp;			// server IP
	
	private String workTypeChk = "on"; 
	private String insourceIdChk = "on"; 
	private String subBzwkDvcdChk = "on"; 
	private String dsrbCdChk = "on"; 
	private String hndrNoChk = "on"; 
	private String statParamStr;
	
	private String dateType = "D";
	private int workTypeStartIndex;
	private int workTypeEndIndex;
	
	public String getElecDocGroupInexNo() {
		return elecDocGroupInexNo;
	}
	public void setElecDocGroupInexNo(String elecDocGroupInexNo) {
		this.elecDocGroupInexNo = elecDocGroupInexNo;
	}

	public String getSubBzwkDvcd() {
		return subBzwkDvcd;
	}
	public void setSubBzwkDvcd(String subBzwkDvcd) {
		this.subBzwkDvcd = subBzwkDvcd;
	}
	public String getDsrbCd() {
		return dsrbCd;
	}
	public void setDsrbCd(String dsrbCd) {
		this.dsrbCd = dsrbCd;
	}
	public String getHndrNo() {
		return hndrNo;
	}
	public void setHndrNo(String hndrNo) {
		this.hndrNo = hndrNo;
	}
	public String getPrcsTycd() {
		return prcsTycd;
	}
	public void setPrcsTycd(String prcsTycd) {
		this.prcsTycd = prcsTycd;
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
	public String getCstno() {
		return cstno;
	}
	public void setCstno(String cstno) {
		this.cstno = cstno;
	}
	public String getSubBzwkDvcdChk() {
		return subBzwkDvcdChk;
	}
	public void setSubBzwkDvcdChk(String subBzwkDvcdChk) {
		this.subBzwkDvcdChk = subBzwkDvcdChk;
	}
	public String getDsrbCdChk() {
		return dsrbCdChk;
	}
	public void setDsrbCdChk(String dsrbCdChk) {
		this.dsrbCdChk = dsrbCdChk;
	}
	public String getHndrNoChk() {
		return hndrNoChk;
	}
	public void setHndrNoChk(String hndrNoChk) {
		this.hndrNoChk = hndrNoChk;
	}
	public String getStatParamStr() {
		return statParamStr;
	}
	public void setStatParamStr(String statParamStr) {
		this.statParamStr = statParamStr;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getSvrIp() {
		return svrIp;
	}
	public void setSvrIp(String svrIp) {
		this.svrIp = svrIp;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getWorkTypeChk() {
		return workTypeChk;
	}
	public void setWorkTypeChk(String workTypeChk) {
		this.workTypeChk = workTypeChk;
	}
	public int getWorkTypeStartIndex() {
		return workTypeStartIndex;
	}
	public void setWorkTypeStartIndex(int workTypeStartIndex) {
		this.workTypeStartIndex = workTypeStartIndex;
	}
	public int getWorkTypeEndIndex() {
		return workTypeEndIndex;
	}
	public void setWorkTypeEndIndex(int workTypeEndIndex) {
		this.workTypeEndIndex = workTypeEndIndex;
	}
	public String getInsourceId() {
		return insourceId;
	}
	public void setInsourceId(String insourceId) {
		this.insourceId = insourceId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getInsourceIdChk() {
		return insourceIdChk;
	}
	public void setInsourceIdChk(String insourceIdChk) {
		this.insourceIdChk = insourceIdChk;
	}
	
	public String getMainKey() {
		return mainKey;
	}
	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EdocProcListForm [");
		if (elecDocGroupInexNo != null)
			builder.append("elecDocGroupInexNo=").append(elecDocGroupInexNo).append(", ");
		if (workType != null)
			builder.append("workType=").append(workType).append(", ");
		if (insourceId != null)
			builder.append("insourceId=").append(insourceId).append(", ");
		if (mainKey != null)
			builder.append("mainKey=").append(mainKey).append(", ");
		if (subBzwkDvcd != null)
			builder.append("subBzwkDvcd=").append(subBzwkDvcd).append(", ");
		if (dsrbCd != null)
			builder.append("dsrbCd=").append(dsrbCd).append(", ");
		if (hndrNo != null)
			builder.append("hndrNo=").append(hndrNo).append(", ");
		if (prcsTycd != null)
			builder.append("prcsTycd=").append(prcsTycd).append(", ");
		if (procsStepCd != null)
			builder.append("procsStepCd=").append(procsStepCd).append(", ");
		if (procsStepStcd != null)
			builder.append("procsStepStcd=").append(procsStepStcd).append(", ");
		if (cstno != null)
			builder.append("cstno=").append(cstno).append(", ");
		if (customerName != null)
			builder.append("customerName=").append(customerName).append(", ");
		if (svrIp != null)
			builder.append("svrIp=").append(svrIp).append(", ");
		if (workTypeChk != null)
			builder.append("workTypeChk=").append(workTypeChk).append(", ");
		if (insourceIdChk != null)
			builder.append("insourceIdChk=").append(insourceIdChk).append(", ");
		if (subBzwkDvcdChk != null)
			builder.append("subBzwkDvcdChk=").append(subBzwkDvcdChk).append(", ");
		if (dsrbCdChk != null)
			builder.append("dsrbCdChk=").append(dsrbCdChk).append(", ");
		if (hndrNoChk != null)
			builder.append("hndrNoChk=").append(hndrNoChk).append(", ");
		if (statParamStr != null)
			builder.append("statParamStr=").append(statParamStr).append(", ");
		if (dateType != null)
			builder.append("dateType=").append(dateType).append(", ");
		builder.append("workTypeStartIndex=").append(workTypeStartIndex).append(", workTypeEndIndex=")
				.append(workTypeEndIndex).append("]");
		return builder.toString();
	}
	
	
}

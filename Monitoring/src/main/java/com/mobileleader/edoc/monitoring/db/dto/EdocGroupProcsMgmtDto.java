package com.mobileleader.edoc.monitoring.db.dto;

import java.util.Date;
import java.util.List;

import com.mobileleader.edoc.monitoring.common.model.EdsEcmRegInfo;

public class EdocGroupProcsMgmtDto {

	private String elecDocGroupInexNo;			// 전자문서그룹인덱스번호
	private Date crtnTime;						// 생성시각
	private String procsStepCd;					// 처리단계코드(00:시작,10:입력데이터검증,20:PDF생성,30:TSA 생성,40:PDF 전송, 50:이미지변환,60:취소거래,70:이미지전송)
	private String procsStepStcd;				// 처리단계상태코드 (0:진행중, 1:성공, 9:실패)
	private String procsStepMsgCd;				// 처리단계메시지코드
	private Date procsStepStTime;				// 처리단계시작시각
	private Date procsStepEdTime;				// 처리단계종료시각
	private String svrIp;						// 서버IP
	
	//
	private String mainKey;						// 통합전자문서키
	private String insourceId;					// 업무코드
	private String insourceTitle;				// 업무명
	private String taskKey;						// 업무키(접수번호)
	private String customerName;				// 고객명
	private String memo;						// 메모
	//
	
	private String scnNo;						// 화면번호
	private String workType = "";				// 업무유형 (화면용)
	private String subBzwkDvcd = "";			// 서브업무구분코드
	private String dsrbCd;						// 취급점코드
	private String dsrbNm;						// 취급점명
	private String hndrNo;						// 취급자번호
	private String hndrNm;						// 취급자명
	private Date regTime;						// 등록시각
	private String prcsTycd;					// 프로세스유형코드
	private String srcElecDocGroupInexNo;		// 소스전자문서그룹인덱스번호

	private List<EdsEcmRegInfo> ecmFiles; 					// ECM등록정보
	private List<EdocGroupProcsErrHstrDto> errorHistory; 		// 전자문서오류내역
	private List<EdocFileProcsMgmtDto> procFiles;			// 전자문서파일처리내역
	
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
	public String getScnNo() {
		return scnNo;
	}
	public void setScnNo(String scnNo) {
		this.scnNo = scnNo;
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
	public String getSrcElecDocGroupInexNo() {
		return srcElecDocGroupInexNo;
	}
	public void setSrcElecDocGroupInexNo(String srcElecDocGroupInexNo) {
		this.srcElecDocGroupInexNo = srcElecDocGroupInexNo;
	}
	public List<EdsEcmRegInfo> getEcmFiles() {
		return ecmFiles;
	}
	public void setEcmFiles(List<EdsEcmRegInfo> ecmFiles) {
		this.ecmFiles = ecmFiles;
	}
	public List<EdocGroupProcsErrHstrDto> getErrorHistory() {
		return errorHistory;
	}
	public void setErrorHistory(List<EdocGroupProcsErrHstrDto> errorHistory) {
		this.errorHistory = errorHistory;
	}
	public List<EdocFileProcsMgmtDto> getProcFiles() {
		return procFiles;
	}
	public void setProcFiles(List<EdocFileProcsMgmtDto> procFiles) {
		this.procFiles = procFiles;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
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
		builder.append("EdocGroupProcsMgmtDto [");
		if (elecDocGroupInexNo != null)
			builder.append("elecDocGroupInexNo=").append(elecDocGroupInexNo).append(", ");
		if (crtnTime != null)
			builder.append("crtnTime=").append(crtnTime).append(", ");
		if (procsStepCd != null)
			builder.append("procsStepCd=").append(procsStepCd).append(", ");
		if (procsStepStcd != null)
			builder.append("procsStepStcd=").append(procsStepStcd).append(", ");
		if (procsStepMsgCd != null)
			builder.append("procsStepMsgCd=").append(procsStepMsgCd).append(", ");
		if (procsStepStTime != null)
			builder.append("procsStepStTime=").append(procsStepStTime).append(", ");
		if (procsStepEdTime != null)
			builder.append("procsStepEdTime=").append(procsStepEdTime).append(", ");
		if (svrIp != null)
			builder.append("svrIp=").append(svrIp).append(", ");
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
			builder.append("memo=").append(memo).append(", ");
		if (scnNo != null)
			builder.append("scnNo=").append(scnNo).append(", ");
		if (workType != null)
			builder.append("workType=").append(workType).append(", ");
		if (subBzwkDvcd != null)
			builder.append("subBzwkDvcd=").append(subBzwkDvcd).append(", ");
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
		if (srcElecDocGroupInexNo != null)
			builder.append("srcElecDocGroupInexNo=").append(srcElecDocGroupInexNo).append(", ");
		if (ecmFiles != null)
			builder.append("ecmFiles=").append(ecmFiles).append(", ");
		if (errorHistory != null)
			builder.append("errorHistory=").append(errorHistory).append(", ");
		if (procFiles != null)
			builder.append("procFiles=").append(procFiles);
		builder.append("]");
		return builder.toString();
	}
}

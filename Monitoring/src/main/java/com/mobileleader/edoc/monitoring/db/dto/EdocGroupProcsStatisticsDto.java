package com.mobileleader.edoc.monitoring.db.dto;

public class EdocGroupProcsStatisticsDto {
	
	private String workType = "";				// 업무유형코드
	
	private String insourceId = "";				// 업무코드
	private String insourceTitle = "";			// 업무명
	private String dsrbCd = "";						// 취급점코드
	private String dsrbNm = "";						// 취급점명
	private String hndrNo = "";						// 취급자번호
	private String hndrNm = "";						// 취급자명
	private int pprCntTotal;		// 총 건수
	private int pprCnt0;			// 진행중
	private int pprCnt1;			// 성공
	private int pprCnt9;			// 실패
	private int bdtCntTotal;		
	private int bdtCnt0;		
	private int bdtCnt1;			
	private int bdtCnt9;			
	
	
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
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
	public int getPprCntTotal() {
		return pprCntTotal;
	}
	public void setPprCntTotal(int pprCntTotal) {
		this.pprCntTotal = pprCntTotal;
	}
	public int getPprCnt0() {
		return pprCnt0;
	}
	public void setPprCnt0(int pprCnt0) {
		this.pprCnt0 = pprCnt0;
	}
	public int getPprCnt1() {
		return pprCnt1;
	}
	public void setPprCnt1(int pprCnt1) {
		this.pprCnt1 = pprCnt1;
	}
	public int getPprCnt9() {
		return pprCnt9;
	}
	public void setPprCnt9(int pprCnt9) {
		this.pprCnt9 = pprCnt9;
	}
	public int getBdtCntTotal() {
		return bdtCntTotal;
	}
	public void setBdtCntTotal(int bdtCntTotal) {
		this.bdtCntTotal = bdtCntTotal;
	}
	public int getBdtCnt0() {
		return bdtCnt0;
	}
	public void setBdtCnt0(int bdtCnt0) {
		this.bdtCnt0 = bdtCnt0;
	}
	public int getBdtCnt1() {
		return bdtCnt1;
	}
	public void setBdtCnt1(int bdtCnt1) {
		this.bdtCnt1 = bdtCnt1;
	}
	public int getBdtCnt9() {
		return bdtCnt9;
	}
	public void setBdtCnt9(int bdtCnt9) {
		this.bdtCnt9 = bdtCnt9;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EdocGroupProcsStatisticsDto [");
		if (workType != null)
			builder.append("workType=").append(workType).append(", ");
		if (insourceId != null)
			builder.append("insourceId=").append(insourceId).append(", ");
		if (insourceTitle != null)
			builder.append("insourceTitle=").append(insourceTitle).append(", ");
		if (dsrbCd != null)
			builder.append("dsrbCd=").append(dsrbCd).append(", ");
		if (dsrbNm != null)
			builder.append("dsrbNm=").append(dsrbNm).append(", ");
		if (hndrNo != null)
			builder.append("hndrNo=").append(hndrNo).append(", ");
		if (hndrNm != null)
			builder.append("hndrNm=").append(hndrNm).append(", ");
		builder.append("pprCntTotal=").append(pprCntTotal).append(", pprCnt0=").append(pprCnt0).append(", pprCnt1=")
				.append(pprCnt1).append(", pprCnt9=").append(pprCnt9).append(", bdtCntTotal=").append(bdtCntTotal)
				.append(", bdtCnt0=").append(bdtCnt0).append(", bdtCnt1=").append(bdtCnt1).append(", bdtCnt9=")
				.append(bdtCnt9).append("]");
		return builder.toString();
	}
	
}

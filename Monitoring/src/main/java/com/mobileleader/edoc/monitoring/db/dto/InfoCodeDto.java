package com.mobileleader.edoc.monitoring.db.dto;

/**
 * 공통 코드 관리 VO
 */
public class InfoCodeDto {
	
	//ROWNUM
	private String rowNum;
	
	//코드그룹
	private String cdGroup;
	
	//코드값
	private String cdVal;

	//코드명
	private String cdNm;
	
	//일련번호
	private String seqno;               
	
	//사용여부
	private String uzYn;
	
	//등록점코드
	private String regBrcd;               
	
	//등록점명
	private String regBrnNm;               
	
	//등록직원번호
	private String regEmpno;               
	
	//등록직원명
	private String regEmpNm;               
	
	//등록시각
	private String regTime;

	//수정전 코드그룹
	private String exCdGroup;
	
	//수정전 코드값
	private String exCdVal;
		
	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getCdGroup() {
		return cdGroup;
	}

	public void setCdGroup(String cdGroup) {
		this.cdGroup = cdGroup;
	}

	public String getCdVal() {
		return cdVal;
	}

	public void setCdVal(String cdVal) {
		this.cdVal = cdVal;
	}

	public String getCdNm() {
		return cdNm;
	}

	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getUzYn() {
		return uzYn;
	}

	public void setUzYn(String uzYn) {
		this.uzYn = uzYn;
	}

	public String getRegBrcd() {
		return regBrcd;
	}

	public void setRegBrcd(String regBrcd) {
		this.regBrcd = regBrcd;
	}

	public String getRegBrnNm() {
		return regBrnNm;
	}

	public void setRegBrnNm(String regBrnNm) {
		this.regBrnNm = regBrnNm;
	}

	public String getRegEmpno() {
		return regEmpno;
	}

	public void setRegEmpno(String regEmpno) {
		this.regEmpno = regEmpno;
	}

	public String getRegEmpNm() {
		return regEmpNm;
	}

	public void setRegEmpNm(String regEmpNm) {
		this.regEmpNm = regEmpNm;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getExCdGroup() {
		return exCdGroup;
	}

	public void setExCdGroup(String exCdGroup) {
		this.exCdGroup = exCdGroup;
	}

	public String getExCdVal() {
		return exCdVal;
	}

	public void setExCdVal(String exCdVal) {
		this.exCdVal = exCdVal;
	}

	@Override
	public String toString() {
		return "InfoCodeDto [rowNum=" + rowNum + ", cdGroup=" + cdGroup + ", cdVal=" + cdVal + ", cdNm=" + cdNm
				+ ", seqno=" + seqno + ", uzYn=" + uzYn + ", regBrcd=" + regBrcd + ", regBrnNm=" + regBrnNm
				+ ", regEmpno=" + regEmpno + ", regEmpNm=" + regEmpNm + ", regTime=" + regTime + ", exCdGroup="
				+ exCdGroup + ", exCdVal=" + exCdVal + "]";
	}

}

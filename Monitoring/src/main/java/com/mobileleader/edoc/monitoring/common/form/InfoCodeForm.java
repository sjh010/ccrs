package com.mobileleader.edoc.monitoring.common.form;

/**
 * 공통 코드 관리 입력 form
 */
public class InfoCodeForm extends BaseFrom{
	
	//코드그룹
	private String cdGroup;
	
	//코드값
	private String cdVal;

	//코드명
	private String cdNm;
	
	//사용여부
	private String uzYn;

	//순서
	private String seqno;
	
	//수정전 코드그룹
	private String exCdGroup;
	
	//수정전 코드값
	private String exCdVal;
		
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

	public String getUzYn() {
		return uzYn;
	}

	public void setUzYn(String uzYn) {
		this.uzYn = uzYn;
	}

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
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
		return "InfoCodeForm [cdGroup=" + cdGroup + ", cdVal=" + cdVal + ", cdNm=" + cdNm + ", uzYn=" + uzYn
				+ ", seqno=" + seqno + ", exCdGroup=" + exCdGroup + ", exCdVal=" + exCdVal + "]";
	}

}

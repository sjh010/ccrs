package com.mobileleader.edoc.type;

/**
 * PROCS_STEP_STCD(처리단계 상태코드)에 대한 타입 클래스
 * 참고 : EDS_COM_CD_MGMT
 */
public enum ProcsStepStCd implements ComCodeEnumType {
	
	PROCESSING	("0", "진행중"),
	SUCCESS		("1", "성공"),
	FAIL		("9", "실패")
	;
	
	private String cdVal;
	private String cdNm;
	
	private ProcsStepStCd(String code, String descript) {
		this.cdVal = code;
		this.cdNm = descript;
	}
	
	public String getCdVal() {
		return cdVal;
	}

	public String getCdNm() {
		return cdNm;
	}

}

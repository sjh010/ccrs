package com.mobileleader.edoc.type;

/**
 * PROCS_STEP_MSG_CD(처리단계 메시지코드)에 대한 타입 클래스
 * 참고 : EDS_COM_CD_MGMT
 */
public enum ProcsStepMsgCd implements ComCodeEnumType {
	
	DEFAULT			("AAAAAA", "초기 값"),
	START_STAGE		("000000", "처리 대상"),
	STAGE_FINISH	("FINISH", "처리 완료"),
	MAXERR			("MAXERR", "3회 이상 실패"),
	CANCEL			("CANCEL", "취소처리 대상")
	;
	
	private String cdVal;
	private String cdNm;
	
	private ProcsStepMsgCd(String code, String descript) {
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

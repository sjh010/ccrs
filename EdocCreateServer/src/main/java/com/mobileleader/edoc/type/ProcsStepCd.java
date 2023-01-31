package com.mobileleader.edoc.type;

/**
 * PROCS_STEP_CD(처리단계코드)에 대한 타입 클래스
 * 참고 : EDS_COM_CD_MGMT
 */
public enum ProcsStepCd implements ComCodeEnumType {
	
	START					("00", "시작"),
	INPUT_DATA_VERIFICATION	("10", "입력데이터검증"),
	PDF_CREATE				("20", "PDF생성"),
	TSA_CERTIFICATION		("30", "TSA인증"),
	PDF_ECM_TRANSMISSION	("40", "PDF 공전소 전송"),
	PDF_IMG_CONVERSION		("50", "PDF 이미지 변환"),
	CANCEL_IMG_TRAMSMISSION	("60", "취소거래"),
	ECM_IMG_TRANSMISSION	("70", "PDF ECM 전송")
	;
	
	private String cdVal;
	private String cdNm;
	
	private ProcsStepCd(String cdVal, String cdNm) {
		this.cdVal = cdVal;
		this.cdNm = cdNm;
	}
	
	public String getCdVal() {
		return cdVal;
	}

	public String getCdNm() {
		return cdNm;
	}
}

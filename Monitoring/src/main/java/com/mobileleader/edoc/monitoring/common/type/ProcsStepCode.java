package com.mobileleader.edoc.monitoring.common.type;

public enum ProcsStepCode {

	STEP00("00", "시작"),
	STEP10("10", "입력데이터검증"),
	STEP20("20", "PDF 생성"),
	STEP30("30", "TSA인증"),
	STEP40("40", "PDF 공전소 전송"),
	STEP50("50", "PDF 이미지 변환"),
	STEP60("60", "취소거래"),
	STEP70("70", "PDF ECM 전송"),
	UNKNOWN("99", "Unknown Step");
	
	private String code;
	private String name;
	
	private ProcsStepCode(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public static ProcsStepCode getByCode(String code) {
	
		if(STEP00.getCode().equals(code)) {	return STEP00; } 
		else if(STEP10.getCode().equals(code)) { return STEP10; }
		else if(STEP20.getCode().equals(code)) { return STEP20; }
		else if(STEP30.getCode().equals(code)) { return STEP30; }
		else if(STEP40.getCode().equals(code)) { return STEP40; }
		else if(STEP50.getCode().equals(code)) { return STEP50; }
		else if(STEP60.getCode().equals(code)) { return STEP60; }
		else if(STEP70.getCode().equals(code)) { return STEP70; }
		else { return UNKNOWN; }
	}
}

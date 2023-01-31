package com.mobileleader.edoc.monitoring.db.dto;

public class EdocGroupProcsMgmtAggrDto {

	private String procsStepCd;					// 처리단계코드(00:시작,10:입력데이터검증,20:PDF생성,30:TSA 생성,40:PDF 전송, 50:이미지변환,60:취소거래,70:이미지전송)
	private String procsStepStcd;				// 처리단계상태코드 (0:진행중, 1:성공, 9:실패)
	private int itemCount;
	
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

	public int getItemCount() {
		return itemCount;
	}
	
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

}

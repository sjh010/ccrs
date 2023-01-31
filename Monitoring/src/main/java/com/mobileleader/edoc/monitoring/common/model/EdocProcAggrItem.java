package com.mobileleader.edoc.monitoring.common.model;

import com.mobileleader.edoc.monitoring.common.type.ProcsStepCode;

public class EdocProcAggrItem {

	private String stepCode;
	private String stepName;
	private int countReady=0;
	private int countSuccess=0;
	private int countFail=0;
	private int countTotal=0;
	
	public EdocProcAggrItem() {
		
	}
	
	public EdocProcAggrItem(ProcsStepCode procstepCode) {
		this.stepCode = procstepCode.getCode();
		this.stepName = procstepCode.getName();
	}
	
	public EdocProcAggrItem(String stepCode, String stepName) {
		this.stepCode = stepCode;
		this.stepName = stepName;
	}
	
	public String getStepCode() {
		return stepCode;
	}
	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public int getCountReady() {
		return countReady;
	}
	public void setCountReady(int countReady) {
		this.countReady = countReady;
	}
	
	public int getCountSuccess() {
		return countSuccess;
	}
	
	public void setCountSuccess(int countSuccess) {
		this.countSuccess = countSuccess;
	}
	
	public int getCountFail() {
		return countFail;
	}
	
	public void setCountFail(int countFail) {
		this.countFail = countFail;
	}
	
	public int getCountTotal() {
		return countTotal;
	}
	
	public void setCountTotal(int countTotal) {
		this.countTotal = countTotal;
	}
	
	public void setCountByStCd(String stepStCd, int itemCount) {
		
		if("0".equals(stepStCd)) {
			setCountReady(itemCount);	
			this.countTotal = this.countReady + this.countFail + this.countSuccess;
			
		} else if("1".equals(stepStCd)) {
			
			setCountSuccess(itemCount);	
			this.countTotal = this.countReady + this.countFail + this.countSuccess;
			
		} else if("9".equals(stepStCd)) {
			
			setCountFail(itemCount);
			this.countTotal = this.countReady + this.countFail + this.countSuccess;
		}
	}

}

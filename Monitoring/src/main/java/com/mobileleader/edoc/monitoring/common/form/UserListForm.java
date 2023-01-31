package com.mobileleader.edoc.monitoring.common.form;

public class UserListForm extends BaseFrom {
	private String empNo;
	private String brcd;
	private int permId;
	private String uzYn;
	
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getBrcd() {
		return brcd;
	}
	public void setBrcd(String brcd) {
		this.brcd = brcd;
	}
	public int getPermId() {
		return permId;
	}
	public void setPermId(int permId) {
		this.permId = permId;
	}
	public String getUzYn() {
		return uzYn;
	}
	public void setUzYn(String uzYn) {
		this.uzYn = uzYn;
	}
	@Override
	public String toString() {
		return "UserListForm [empNo=" + empNo + ", brcd=" + brcd
				+ ", permId=" + permId + ", uzYn=" + uzYn + "]";
	}
	
}

package com.mobileleader.edoc.monitoring.db.dto;

import java.util.Date;
import java.util.List;

public class PermMgmtDto {
	private int permId;		//권한ID
	private String permNm;		//권한명
	private String regEmpNo;	//등록직원번호
	private Date regTime;		//등록시간
	private String chngEmpNo;	//변경직원번호
	private Date chngTime;		//변경시간
	private List<MenuAccsPermDto> menuAccsPerm; //메뉴 접근권한
	public int getPermId() {
		return permId;
	}
	public void setPermId(int permId) {
		this.permId = permId;
	}
	public String getPermNm() {
		return permNm;
	}
	public void setPermNm(String permNm) {
		this.permNm = permNm;
	}
	public String getRegEmpNo() {
		return regEmpNo;
	}
	public void setRegEmpNo(String regEmpNo) {
		this.regEmpNo = regEmpNo;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getChngEmpNo() {
		return chngEmpNo;
	}
	public void setChngEmpNo(String chngEmpNo) {
		this.chngEmpNo = chngEmpNo;
	}
	public Date getChngTime() {
		return chngTime;
	}
	public void setChngTime(Date chngTime) {
		this.chngTime = chngTime;
	}	
	public List<MenuAccsPermDto> getMenuAccsPerm() {
		return menuAccsPerm;
	}
	public void setMenuAccsPerm(List<MenuAccsPermDto> menuAccsPerm) {
		this.menuAccsPerm = menuAccsPerm;
	}
	@Override
	public String toString() {
		return "PermMgmtDto [permId=" + permId + ", permNm=" + permNm
				+ ", regEmpNo=" + regEmpNo + ", retTime=" + regTime
				+ ", chngEmpNo=" + chngEmpNo + ", chngTime=" + chngTime
				+ ", menuAccsPerm=" + menuAccsPerm + "]";
	}
	
	
}

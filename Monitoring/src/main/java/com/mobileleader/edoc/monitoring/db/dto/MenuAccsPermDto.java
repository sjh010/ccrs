package com.mobileleader.edoc.monitoring.db.dto;

import java.util.Date;

public class MenuAccsPermDto {
	private int permId;			//권한Id
	private String menuCd;		//메뉴코드
	private String accsYn;		//접근가능여부
	private String listYn;		//목록조회가능여부
	private String dtalYn;		//상세조회가능여부
	private String regYn;		//등록가능여부
	private String editYn;		//수정가능여부
	private String delYn;		//삭제가능여부
	private String regEmpNo;	//등록직원번호
	private Date regTime;		//등록시간
	private String chngEmpNo;	//변경직원번호
	private Date chngTime;		//변경시간	
	
	public int getPermId() {
		return permId;
	}
	public void setPermId(int permId) {
		this.permId = permId;
	}
	public String getMenuCd() {
		return menuCd;
	}
	public void setMenuCd(String menuCd) {
		this.menuCd = menuCd;
	}
	public String getAccsYn() {
		return accsYn;
	}
	public void setAccsYn(String accsYn) {
		this.accsYn = accsYn;
	}
	public String getListYn() {
		return listYn;
	}
	public void setListYn(String listYn) {
		this.listYn = listYn;
	}
	public String getDtalYn() {
		return dtalYn;
	}
	public void setDtalYn(String dtalYn) {
		this.dtalYn = dtalYn;
	}
	public String getRegYn() {
		return regYn;
	}
	public void setRegYn(String regYn) {
		this.regYn = regYn;
	}
	public String getEditYn() {
		return editYn;
	}
	public void setEditYn(String editYn) {
		this.editYn = editYn;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
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
	@Override
	public String toString() {
		return "MenuAccsPermDto [permId=" + permId + ", menuCd=" + menuCd
				+ ", accsYn=" + accsYn + ", listYn=" + listYn + ", dtalYn="
				+ dtalYn + ", regYn=" + regYn + ", editYn=" + editYn
				+ ", delyn=" + delYn + ", regEmpNo=" + regEmpNo + ", retTime="
				+ regTime + ", chngEmpNo=" + chngEmpNo + ", chngTime="
				+ chngTime + "]";
	}
	
	public boolean equals(MenuAccsPermDto obj) {
		
		if(this.permId != obj.permId)
			return false;
		
		if(this.menuCd.compareTo(obj.menuCd) != 0)
			return false;
		
		if(this.accsYn.compareTo(obj.accsYn) != 0)
			return false;
		
		if(this.listYn.compareTo(obj.listYn) != 0)
			return false;
		
		if(this.dtalYn.compareTo(obj.dtalYn) != 0)
			return false;
			
		if(this.regYn.compareTo(obj.regYn) != 0)
			return false;
		
		if(this.editYn.compareTo(obj.editYn) != 0)
			return false;
		
		if(this.delYn.compareTo(obj.delYn) != 0)
			return false;
		
		
		return true;
	}
}

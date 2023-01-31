package com.mobileleader.edoc.monitoring.db.dto;

import java.io.Serializable;
import java.util.Date;

public class UserMgmtDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String empNo;		//직원번호
	private String password;	//비밀번호
	private int permId;			//권한 ID
	private String permNm;		//권한명
	private String brcd;		//지점코드
	private String brnNm;		//지점명
	private String empNm;		//직원명
	private String pstNm;		//직급명
	private String uzYn;		//사용여부
	private String regEmpNo;	//등록직원번호
	private Date regTime;		//등록시간
	private String chngEmpNo;	//변경직원번호
	private Date chngTime;		//변경시간				
	private int passwordErrorCount; // 비밀번호 오류 횟수
	
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
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
	public String getBrcd() {
		return brcd;
	}
	public void setBrcd(String brcd) {
		this.brcd = brcd;
	}
	public String getBrnNm() {
		return brnNm;
	}
	public void setBrnNm(String brnNm) {
		this.brnNm = brnNm;
	}
	public String getEmpNm() {
		return empNm;
	}
	public void setEmpNm(String empNm) {
		this.empNm = empNm;
	}
	public String getPstNm() {
		return pstNm;
	}
	public void setPstNm(String pstNm) {
		this.pstNm = pstNm;
	}
	public String getUzYn() {
		return uzYn;
	}
	public void setUzYn(String uzYn) {
		this.uzYn = uzYn;
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
	public int getPasswordErrorCount() {
		return passwordErrorCount;
	}
	public void setPasswordErrorCount(int passwordErrorCount) {
		this.passwordErrorCount = passwordErrorCount;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserMgmtDto [");
		if (empNo != null)
			builder.append("empNo=").append(empNo).append(", ");
		if (password != null)
			builder.append("password=").append(password).append(", ");
		builder.append("permId=").append(permId).append(", ");
		if (permNm != null)
			builder.append("permNm=").append(permNm).append(", ");
		if (brcd != null)
			builder.append("brcd=").append(brcd).append(", ");
		if (brnNm != null)
			builder.append("brnNm=").append(brnNm).append(", ");
		if (empNm != null)
			builder.append("empNm=").append(empNm).append(", ");
		if (pstNm != null)
			builder.append("pstNm=").append(pstNm).append(", ");
		if (uzYn != null)
			builder.append("uzYn=").append(uzYn).append(", ");
		if (regEmpNo != null)
			builder.append("regEmpNo=").append(regEmpNo).append(", ");
		if (regTime != null)
			builder.append("regTime=").append(regTime).append(", ");
		if (chngEmpNo != null)
			builder.append("chngEmpNo=").append(chngEmpNo).append(", ");
		if (chngTime != null)
			builder.append("chngTime=").append(chngTime).append(", ");
		builder.append("passwordErrorCount=").append(passwordErrorCount).append("]");
		return builder.toString();
	}		
}

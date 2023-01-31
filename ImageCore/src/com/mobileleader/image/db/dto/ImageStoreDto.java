package com.mobileleader.image.db.dto;


import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobileleader.image.annotation.ValidMainKey;
import com.mobileleader.image.util.JsonUtil;

/**
 * 업무정보 테이블 [IMAGE_STORE table]
 * 
 * @author bkcho
 * @since 2019.06.21
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImageStoreDto {
 
	@NotEmpty
	@Length(max=14)
	@Pattern(regexp="[0-9]+")
	@ValidMainKey
	private String mainKey;						// 통합 전자문서 키				MAIN_KEY

	@Length(max=20)
	private String taskKey;						// 업무키(접수번호)				TASK_KEY
	
	@NotEmpty
	@Length(max=20)
	private String branchCode;					// 지점 코드					BRANCH_CODE

	@NotEmpty
	private String branchTitle;					// 지점 명						BRANCH_TITLE
	
	private String guideName;					// 안내원						GUIDE_NAME
	
	@NotEmpty
	private String employeeName;				// 등록자 명(업무담당자 명)			EMPLOYEE_NAME
	
	@NotEmpty
	@Length(max=50)
	private String employeeId;					// 등록자 번호 (업무담당자 번호)		EMPLOYEE_ID

	@NotEmpty
	@Length(max=50)
	private String customerName;				// 고객 명						CUSTOMER_NAME
	
	private int docMappingCount;				// 파일 매핑 수					DOC_MAPPING_COUNT
	
	private String previousDeviceInfo;			// 정보전달 출발지 디바이스 정보		PREVIOUS_DEVICE_INFO
	
	@NotEmpty
	@Length(max=15)
	private String insourceId;					// 업무 코드					INSOURCE_ID
	
	private String insourceTitle;				// 업무 명						INSOURCE_TITLE

	//@Length(min=13, max=13)
	//@Pattern(regexp="[0-9]{13}")
	private String memo;						// 메모						MEMO
	
	private String tel;							// 전화번호					TEL

	private String createTime;					// 생성 시각					CREATE_TIME
		
	private String modifyTime;					// 수정 시각					MODIFY_TIME
	 		

	public String getMainKey() {
		return mainKey;
	}

	public ImageStoreDto setMainKey(String mainKey) {
		this.mainKey = mainKey;
		return this; 
	}

	public String getTaskKey() {
		return taskKey;
	}

	public ImageStoreDto setTaskKey(String taskKey) {
		this.taskKey = taskKey;
		return this;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public ImageStoreDto setBranchCode(String branchCode) {
		this.branchCode = branchCode;
		return this;
	}

	public String getGuideName() {
		return guideName;
	}

	public ImageStoreDto setGuideName(String guideName) {
		this.guideName = guideName;
		return this;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public ImageStoreDto setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
		return this;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public ImageStoreDto setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public String getCustomerName() {
		return customerName;
	}

	public ImageStoreDto setCustomerName(String customerName) {
		this.customerName = customerName;
		return this;
	}

	public int getDocMappingCount() {
		return docMappingCount;
	}

	public ImageStoreDto setDocMappingCount(int docMappingCount) {
		this.docMappingCount = docMappingCount;
		return this;
	}

	public String getPreviousDeviceInfo() {
		return previousDeviceInfo;
	}

	public ImageStoreDto setPreviousDeviceInfo(String previousDeviceInfo) {
		this.previousDeviceInfo = previousDeviceInfo;
		return this;
	}

	public String getInsourceId() {
		return insourceId;
	}

	public ImageStoreDto setInsourceId(String insourceId) {
		this.insourceId = insourceId;
		return this;
	}

	public String getMemo() {
		return memo;
	}

	public ImageStoreDto setMemo(String memo) {
		this.memo = memo;
		return this;
	}

	public String getTel() {
		return tel;
	}

	public ImageStoreDto setTel(String tel) {
		this.tel = tel;
		return this;
	}

	public String getCreateTime() {
		return createTime;
	}

	public ImageStoreDto setCreateTime(String createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public ImageStoreDto setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
		return this;
	}

	public String getBranchTitle() {
		return branchTitle;
	}

	public ImageStoreDto setBranchTitle(String branchTitle) {
		this.branchTitle = branchTitle;
		return this;
	}

	public String getInsourceTitle() {
		return insourceTitle;
	}

	public ImageStoreDto setInsourceTitle(String insourceTitle) {
		this.insourceTitle = insourceTitle;
		return this;
	}
	
	@Override
	public String toString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}
}
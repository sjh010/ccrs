package com.mobileleader.edoc.monitoring.common.model.request;

import com.mobileleader.edoc.monitoring.utils.CryptoSHA2;
import com.mobileleader.edoc.monitoring.utils.DateTimeUtil;
import com.mobileleader.image.util.JsonUtil;

public class ImageSearchReqeust {

	/* 메인키(IMAGE_FILE.MAIN_KEY 테이블과 조인키)	 */
	private String mainKey;
	
	/* 업무키(접수번호) */
	private String taskKey;
	
	/* 서식업무별 그룹코드 */
	private String docTaskGroupCode;
	
	/* 서식업무별 그룹명 */
	private String docTaskGroupTitle;
	
	/* 서브버튼 업무코드 */
	private String subBtnTaskCode;
	
	/* 서브버튼 업무명 */
	private String subBtnTaskTitle;
	
	/* 업데이트 여부 */
	private String updateYn;
	
	/* 지점코드  */
	private String branchCode;
	
	/* 지점 명 */
	private String branchTitle;
	
	/* 안내원 */
	private String guideName;
	
	/* 등록자 명(업무담당자 명) */
	private String employeeName;
	
	/* 등록자 번호 (업무담당자 번호) */
	private String employeeId;
	
	/* 고객명 */
	private String customerName;
	
	/* 매핑 개수 (IMAGE_FILE.MAIN_KEY) */
	private int docMappingCount;
	
	/* 정보전달 출발지 디바이스 정보 */
	private String previousDeviceInfo;
	
	/* 업무코드 */
	private String insourceId;
	
	/* 업무 명 */
	private String insourceTitle;
	
	/* 메모 */
	private String memo;
	
	/* 생성시각 */
	private String createTime;
		
	/* 수정시각 */
	private String modifyTime;
	
	private int pageNo = 1;		// 페이징 - 현재페이지

	private int pageSize = 10; 	// 페이징 - 페이지사이즈
	
	private int pageBlock = 10; // 페이징 - 페이지(링크)수
	
	private int pageStartIndex; // 페이징 - 시작인덱스
	
	private int pageEndIndex; 	// 페이징 - 끝인데스

	private String sortKey;		// 정렬키
	
	private String sortOrder;	// 정렬방식(asc, desc)
	
	private String startDate = DateTimeUtil.getDate() + "000000";
	
	private String endDate = DateTimeUtil.getDate() + "235959";
	
	public String getMainKey() {
		return mainKey;
	}

	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getDocTaskGroupCode() {
		return docTaskGroupCode;
	}

	public void setDocTaskGroupCode(String docTaskGroupCode) {
		this.docTaskGroupCode = docTaskGroupCode;
	}

	public String getDocTaskGroupTitle() {
		return docTaskGroupTitle;
	}

	public void setDocTaskGroupTitle(String docTaskGroupTitle) {
		this.docTaskGroupTitle = docTaskGroupTitle;
	}

	public String getSubBtnTaskCode() {
		return subBtnTaskCode;
	}

	public void setSubBtnTaskCode(String subBtnTaskCode) {
		this.subBtnTaskCode = subBtnTaskCode;
	}

	public String getSubBtnTaskTitle() {
		return subBtnTaskTitle;
	}

	public void setSubBtnTaskTitle(String subBtnTaskTitle) {
		this.subBtnTaskTitle = subBtnTaskTitle;
	}

	public String getUpdateYn() {
		return updateYn;
	}

	public void setUpdateYn(String updateYn) {
		this.updateYn = updateYn;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchTitle() {
		return branchTitle;
	}

	public void setBranchTitle(String branchTitle) {
		this.branchTitle = branchTitle;
	}

	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getDocMappingCount() {
		return docMappingCount;
	}

	public void setDocMappingCount(int docMappingCount) {
		this.docMappingCount = docMappingCount;
	}

	public String getPreviousDeviceInfo() {
		return previousDeviceInfo;
	}

	public void setPreviousDeviceInfo(String previousDeviceInfo) {
		this.previousDeviceInfo = previousDeviceInfo;
	}

	public String getInsourceId() {
		return insourceId;
	}

	public void setInsourceId(String insourceId) {
		this.insourceId = insourceId;
	}

	public String getInsourceTitle() {
		return insourceTitle;
	}

	public void setInsourceTitle(String insourceTitle) {
		this.insourceTitle = insourceTitle;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getEncryptedMemo() {
		String encryptedMemo = this.memo;
		
		if(encryptedMemo.indexOf("-") > 0) {
			encryptedMemo = encryptedMemo.replace("-", "");
		}
		
		return CryptoSHA2.getIdEnc(encryptedMemo);
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageBlock() {
		return pageBlock;
	}

	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}

	public int getPageStartIndex() {
		return ((this.pageNo - 1) * this.pageSize) + 1;
	}

	public void setPageStartIndex(int pageStartIndex) {
		this.pageStartIndex = pageStartIndex;
	}

	public int getPageEndIndex() {
		return this.pageNo * this.pageSize;
	}

	public void setPageEndIndex(int pageEndIndex) {
		this.pageEndIndex = pageEndIndex;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImageSearchReqeust [");
		if (mainKey != null)
			builder.append("mainKey=").append(mainKey).append(", ");
		if (taskKey != null)
			builder.append("taskKey=").append(taskKey).append(", ");
		if (docTaskGroupCode != null)
			builder.append("docTaskGroupCode=").append(docTaskGroupCode).append(", ");
		if (docTaskGroupTitle != null)
			builder.append("docTaskGroupTitle=").append(docTaskGroupTitle).append(", ");
		if (subBtnTaskCode != null)
			builder.append("subBtnTaskCode=").append(subBtnTaskCode).append(", ");
		if (subBtnTaskTitle != null)
			builder.append("subBtnTaskTitle=").append(subBtnTaskTitle).append(", ");
		if (updateYn != null)
			builder.append("updateYn=").append(updateYn).append(", ");
		if (branchCode != null)
			builder.append("branchCode=").append(branchCode).append(", ");
		if (branchTitle != null)
			builder.append("branchTitle=").append(branchTitle).append(", ");
		if (guideName != null)
			builder.append("guideName=").append(guideName).append(", ");
		if (employeeName != null)
			builder.append("employeeName=").append(employeeName).append(", ");
		if (employeeId != null)
			builder.append("employeeId=").append(employeeId).append(", ");
		if (customerName != null)
			builder.append("customerName=").append(customerName).append(", ");
		builder.append("docMappingCount=").append(docMappingCount).append(", ");
		if (previousDeviceInfo != null)
			builder.append("previousDeviceInfo=").append(previousDeviceInfo).append(", ");
		if (insourceId != null)
			builder.append("insourceId=").append(insourceId).append(", ");
		if (insourceTitle != null)
			builder.append("insourceTitle=").append(insourceTitle).append(", ");
		if (memo != null)
			builder.append("memo=").append(getEncryptedMemo()).append(", ");
		if (createTime != null)
			builder.append("createTime=").append(createTime).append(", ");
		if (modifyTime != null)
			builder.append("modifyTime=").append(modifyTime).append(", ");
		builder.append("pageNo=").append(pageNo).append(", pageSize=").append(pageSize).append(", pageBlock=")
				.append(pageBlock).append(", pageStartIndex=").append(pageStartIndex).append(", pageEndIndex=")
				.append(pageEndIndex).append(", ");
		if (sortKey != null)
			builder.append("sortKey=").append(sortKey).append(", ");
		if (sortOrder != null)
			builder.append("sortOrder=").append(sortOrder).append(", ");
		if (startDate != null)
			builder.append("startDate=").append(startDate).append(", ");
		if (endDate != null)
			builder.append("endDate=").append(endDate);
		builder.append("]");
		return builder.toString();
	}

	public String toJsonString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}
}

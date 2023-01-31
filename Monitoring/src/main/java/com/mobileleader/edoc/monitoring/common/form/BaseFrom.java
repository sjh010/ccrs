package com.mobileleader.edoc.monitoring.common.form;

import com.mobileleader.edoc.monitoring.utils.DateTimeUtil;

public class BaseFrom {
	
	private String startDate = DateTimeUtil.getDate()+"000000";		// 기간필터링 - 시작일자
	private String endDate = DateTimeUtil.getDate()+"235959";			// 기간필터링 - 끝일자
	
	private String startDateMonitoring = DateTimeUtil.getDate()+"0000";			// 기간필터링 - 시작일자(모니터링)
	private String endDateMonitoring = DateTimeUtil.getDate()+"2359";			// 기간필터링 - 끝일자(모니터링)

	private int pageNo = 1;					// 페이징 - 현재페이지
	private int pageSize = 10;				// 페이징 - 페이지사이즈
	private int pageBlock = 10; 			// 페이징 - 페이지(링크)수
	@SuppressWarnings("unused")
    private int pageStartIndex;				// 페이징 - 시작인덱스
	@SuppressWarnings("unused")
    private int pageEndIndex;				// 페이징 - 끝인데스
	
	private String sortKey;
	private String sortOrder;
	
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
	public String getStartDateMonitoring() {
		return startDateMonitoring;
	}
	public void setStartDateMonitoring(String startDateMonitoring) {
		this.startDateMonitoring = startDateMonitoring;
	}
	public String getEndDateMonitoring() {
		return endDateMonitoring;
	}
	public void setEndDateMonitoring(String endDateMonitoring) {
		this.endDateMonitoring = endDateMonitoring;
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
		return ((this.pageNo-1)*this.pageSize)+1;
	}
	public void setPageStartIndex(int pageStartIndex) {
		this.pageStartIndex = pageStartIndex;
	}
	public int getPageEndIndex() {
		return this.pageNo*this.pageSize;
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
	@SuppressWarnings("unused")
	private String makeDate(String dateString, String timeString) {
		return dateString + timeString.substring(0, 2) + (timeString.substring(2, 4).equals("60")?"00":timeString.substring(2, 4));
	}
}

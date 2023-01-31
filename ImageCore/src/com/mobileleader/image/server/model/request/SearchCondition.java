package com.mobileleader.image.server.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobileleader.image.util.JsonUtil;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SearchCondition {
	
	private int pageNo = 1;		// 페이징 - 현재페이지

	private int pageSize = 10; 	// 페이징 - 페이지사이즈
	
	private int pageBlock = 10; // 페이징 - 페이지(링크)수
	
	@SuppressWarnings("unused")
	private int pageStartIndex; // 페이징 - 시작인덱스
	
	@SuppressWarnings("unused")
	private int pageEndIndex; 	// 페이징 - 끝인데스

	private String sortKey;		// 정렬키

	private String sortOrder;	// 정렬방식(asc, desc)
	
	private String startDate;
	
	private String endDate;

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

	public String toJsonString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}

}

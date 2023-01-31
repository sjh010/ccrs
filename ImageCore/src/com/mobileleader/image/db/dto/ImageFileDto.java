package com.mobileleader.image.db.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobileleader.image.annotation.ValidMainKey;
import com.mobileleader.image.server.model.validation.ValidationGroup;
import com.mobileleader.image.util.JsonUtil;

/**
 * 이미지 파일 정보 Dto  (이미지 정보를 DB에 저장하기 위해 담는 객체)
 * [IMAGE_FILE table]
 * 
 * @author bkcho@mobileleader.com
 * @since 2019.06.24
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImageFileDto {
 	
	@Length(max=14, groups= {Default.class, ValidationGroup.Update.class})
	@Pattern(regexp="[0-9]+", groups= {Default.class, ValidationGroup.Update.class})
	@ValidMainKey(groups= {Default.class, ValidationGroup.Update.class})
	private String mainKey;														// 통합 전자문서키		MAIN_KEY
	
	@NotEmpty(groups= {Default.class, ValidationGroup.Update.class})
	@Length(max=10, groups= {Default.class, ValidationGroup.Update.class})
	private String docId;														// 문서 아이디			DOC_ID
	
	@NotEmpty
	private String docTitle;													// 문서명				DOC_TITLE
	
	private int pageCnt;														// 페이지 수			PAGE_CNT
	
	private int versionInfo;													// 버전 정보			VERSION_INFO
	
	@NotEmpty
	private String docType;														// 문서 타입			DOC_TYPE
	
	private String deleteYn;													// 삭제 여부(Y/N) 		DELETE_YN
	
	@AssertTrue(message="invalid deleteYn value")
	public boolean isValidDeleteYn() {
		if(deleteYn != null) {
			if(!deleteYn.equalsIgnoreCase("N") && !deleteYn.equalsIgnoreCase("Y")) {
				return false;
			}
		}
		
		return true;
	}
	
	@Min(0)
	private int fileOrder;														// 문서 정렬 순서		FILE_ORDER
	
	@NotEmpty
	private String funnels;														// 문서 유입 경로		FUNNELS
	
	private String fileId;														// ECM 파일 아이디		FILE_ID		
	
	@NotEmpty
	private String fileName;													// 파일 명				FILE_NAME

	private String createTime;													// 생성 시각			CREATE_TIME

	private String modifyTime;													// 수정 시각			MODIFY_TIME
	
	public String getMainKey() {
		return mainKey;
	}

	public ImageFileDto setMainKey(String mainKey) {
		this.mainKey = mainKey;
		return this;
	}

	public String getDocId() {
		return docId;
	}

	public ImageFileDto setDocId(String docId) {
		this.docId = docId;
		return this;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public ImageFileDto setDocTitle(String docTitle) {
		this.docTitle = docTitle;
		return this;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public ImageFileDto setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
		return this;
	}

	public int getVersionInfo() {
		return versionInfo;
	}

	public ImageFileDto setVersionInfo(int versionInfo) {
		this.versionInfo = versionInfo;
		return this;
	}

	public String getDocType() {
		return docType;
	}

	public ImageFileDto setDocType(String docType) {
		this.docType = docType;
		return this;
	}
	
	public String getDeleteYn() {
		return deleteYn;
	}

	public ImageFileDto setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
		return this;
	}

	public String getFunnels() {
		return funnels;
	}

	public ImageFileDto setFunnels(String funnels) {
		this.funnels = funnels;
		return this;
	}

	public String getFileId() {
		return fileId;
	}

	public ImageFileDto setFileId(String fileId) {
		this.fileId = fileId;
		return this;
	}

	public String getFileName() {
		return fileName;
	}

	public ImageFileDto setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public String getCreateTime() {
		return createTime;
	}

	public ImageFileDto setCreateTime(String createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public ImageFileDto setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
		return this;
	}
	
	public int getFileOrder() {
		return fileOrder;
	}

	public ImageFileDto setFileOrder(int fileOrder) {
		this.fileOrder = fileOrder;
		return this;
	}
	
	@Override
	public String toString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}

}

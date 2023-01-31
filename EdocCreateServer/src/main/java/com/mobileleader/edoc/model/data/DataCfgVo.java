package com.mobileleader.edoc.model.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileleader.edoc.util.JsonUtils;

/**
 * Data.cfg(전자문서압축파일정보) VO Class
 * 
 * @author user
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataCfgVo {
	
	@JsonProperty("GUID_FORMCODE_LINK")
	private List<GuidFormcodeLinkVo> guidFormcodeLinks = new ArrayList<GuidFormcodeLinkVo>();	// GUID와 서식코드 연결 관계
	
	@JsonProperty("EDOC_IDX_NO")
	private String edocIdxNo = "";																// 전자문서키
	
	@JsonProperty("GUID")
	private String guid = "";																	// GUID
		
	@JsonProperty("ADDED_GUID")
	private List<AddedGuidVo> addedGuids = new ArrayList<AddedGuidVo>();						// 연결거래로 추가된 GUID들
	
	@JsonProperty("FLDR_CNT")
	private long fldrCnt = 0L;																	// 서식 폴더 개수
		
	@JsonProperty("FLDR_DATA")
	private List<FldrDataVo> fldrDatas = new ArrayList<FldrDataVo>();							// 서식 폴더 리스트(결과xml 포함)
	
	@JsonProperty("SCAN_CNT")
	private long scanCnt = 0L;																	// 스캔 폴더 개수

	@JsonProperty("SCAN_DATA")
	private List<FldrDataVo> scanDatas = new ArrayList<FldrDataVo>();							// 스캔 폴더 리스트(스캔 생성된 이미지 포함)
	
	@JsonProperty("IMG_KEY")
	private String imgKey = "";
	
	@JsonProperty("SCREEN_NO")
	private String scrnNo = "";
	
	@JsonProperty("bswr_ecode")
	private String bswrEcode = "";

	private String rgsnBrcd = "";

	private String rgsnEmn = "";
	
	@JsonProperty("ecc_data")
	private String eccData = "";
	
	@JsonProperty("memo_data")
	private String memoData = "";																// 텔러 입력 메모
	
	private String edocBffcWrtnNo = "";
	
	private String tempSaveEdocIndex = "";
	
	private String refNo = "";
	

	public String getEdocIdxNo() {
		return edocIdxNo;
	}

	public void setEdocIdxNo(String edocIdxNo) {
		this.edocIdxNo = edocIdxNo;
	}

	public long getFldrCnt() {
		return fldrCnt;
	}
	
	public void setFldrCnt(long fldrCnt) {
		this.fldrCnt = fldrCnt;
	}

	public long getScanCnt() {
		return scanCnt;
	}
	
	public void setScanCnt(long scanFldrCnt) {
		this.scanCnt = scanFldrCnt;
	}

	public String getGuid() {
		return guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getEccData() {
		return eccData;
	}
	public void setEccData(String eccData) {
		this.eccData = eccData;
	}

	public List<FldrDataVo> getFldrDatas() {
		return fldrDatas;
	}
	
	public void setFldrDatas(List<FldrDataVo> fldaDatas) {
		fldrDatas = fldaDatas;
	}
	
	public void clearFldrDatas() {
		fldrDatas.clear();
	}
	
	public int getFldrDatasSize() {
		return fldrDatas.size();
	}
	
	public FldrDataVo getFldrData(int paramInt) {
		return fldrDatas.get(paramInt);
	}
	
	public void setFldrData(int i, FldrDataVo fldrDataVo) {
		fldrDatas.set(i, fldrDataVo);
	}
	
	public void addFldrData(FldrDataVo fldrDataVo) {
		fldrDatas.add(fldrDataVo);
	}

	public void addScanData(FldrDataVo fldrDataVo) {
		scanDatas.add(fldrDataVo);
	}
	
	public FldrDataVo getScanData(int paramInt) {
		return scanDatas.get(paramInt);
	}
	
	public List<FldrDataVo> getScanDatas() {
		return scanDatas;
	}
	
	public void setScanDatas(List<FldrDataVo> scanDatas) {
		this.scanDatas = scanDatas;
	}

	public List<AddedGuidVo> getAddedGuids() {
		return addedGuids;
	}
	
	public void setAddedGuids(List<AddedGuidVo> addedGuids) {
		this.addedGuids = addedGuids;
	}
	
	public void clearAddedGuids() {
		addedGuids.clear();
	}
	
	public int getAddedGuidsSize() {
		return addedGuids.size();
	}
	
	public AddedGuidVo getAddedGuid(int paramInt) {
		return addedGuids.get(paramInt);
	}
	
	public void setAddedGuid(int i, AddedGuidVo addedGuid) {
		addedGuids.set(i, addedGuid);
	}
	
	public void addAddedGuid(AddedGuidVo addedGuid) {
		addedGuids.add(addedGuid);
	}

	public String getMemoData() {
		return memoData;
	}
	
	public void setMemoData(String memoData) {
		this.memoData = memoData;
	}

	public List<GuidFormcodeLinkVo> getGuidFormcodeLinks() {
		return guidFormcodeLinks;
	}
	
	public void setGuidFormcodeLinks(List<GuidFormcodeLinkVo> guidFormcodeLinks) {
		this.guidFormcodeLinks = guidFormcodeLinks;
	}
	
	public void clearGuidFormcodeLinks() {
		guidFormcodeLinks.clear();
	}
	
	public int getGuidFormcodeLinksSize() {
		return guidFormcodeLinks.size();
	}
	
	public GuidFormcodeLinkVo getGuidFormcodeLink(int paramInt) {
		return guidFormcodeLinks.get(paramInt);
	}
	
	public void setGuidFormcodeLink(int i, GuidFormcodeLinkVo guidFormcodeLink) {
		guidFormcodeLinks.set(i, guidFormcodeLink);
	}
	
	public void addGuidFormcodeLink(GuidFormcodeLinkVo guidFormcodeLink) {
		guidFormcodeLinks.add(guidFormcodeLink);
	}

	public String getImgKey() {
		return imgKey;
	}
	public void setImgKey(String imgKey) {
		this.imgKey = imgKey;
	}
	public String getScrnNo() {
		return scrnNo;
	}
	public void setScrnNo(String scrnNo) {
		this.scrnNo = scrnNo;
	}
	public String getBswrEcode() {
		return bswrEcode;
	}
	public void setBswrEcode(String bswrEcode) {
		this.bswrEcode = bswrEcode;
	}
	public String getRgsnBrcd() {
		return rgsnBrcd;
	}
	public void setRgsnBrcd(String rgsnBrcd) {
		this.rgsnBrcd = rgsnBrcd;
	}
	public String getRgsnEmn() {
		return rgsnEmn;
	}
	public void setRgsnEmn(String rgsnEmn) {
		this.rgsnEmn = rgsnEmn;
	}
	public String getEdocBffcWrtnNo() {
		return edocBffcWrtnNo;
	}
	public void setEdocBffcWrtnNo(String edocBffcWrtnNo) {
		this.edocBffcWrtnNo = edocBffcWrtnNo;
	}
	public String getTempSaveEdocIndex() {
		return tempSaveEdocIndex;
	}
	public void setTempSaveEdocIndex(String tempSaveEdocIndex) {
		this.tempSaveEdocIndex = tempSaveEdocIndex;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	
	public String toString(){
		return JsonUtils.ObjectPrettyPrint(this);
	}

}

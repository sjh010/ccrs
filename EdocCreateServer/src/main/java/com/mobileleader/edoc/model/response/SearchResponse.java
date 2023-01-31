package com.mobileleader.edoc.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileleader.edoc.db.dto.EdocGrpTmpStrgMgmtDto;
import com.mobileleader.edoc.util.JsonUtils;

/**
 * 조회 요청에 대한 Response 모델
 * 
 * @author yh.kim
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponse extends BaseResponse{
	
	@JsonProperty("bizTmpData")
	private EdocGrpTmpStrgMgmtDto tmpData;				//임시저장 관리정보 데이터 (단일 데이터 조회)

	@JsonProperty("bizTmpDataList")
	private List<EdocGrpTmpStrgMgmtDto> tmpDataList;	//임시저장 관리정보 리스트 (리스트 조회)
	

	public EdocGrpTmpStrgMgmtDto getTmpData() {
		return tmpData;
	}

	public void setTmpData(EdocGrpTmpStrgMgmtDto tmpData) {
		this.tmpData = tmpData;
	}

	public List<EdocGrpTmpStrgMgmtDto> getTmpDataList() {
		return tmpDataList;
	}

	public void setTmpDataList(List<EdocGrpTmpStrgMgmtDto> tmpDataList) {
		this.tmpDataList = tmpDataList;
	}

	@Override
	public String toString() {		
		return JsonUtils.ObjectPrettyPrint(this);
	}

}

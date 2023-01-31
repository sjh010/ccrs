package com.mobileleader.edoc.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileleader.edoc.util.JsonUtils;

/**
 * 전자문서그룹인덱스번호 생성 & 업로드 요청에 대한 Response 모델
 * 
 * @author yh.kim
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UploadResponse extends BaseResponse{
	
	@JsonProperty("corEdocGrpIdxNo")
	private String edocGrpIdxNo;						//전자문서그룹 인덱스번호
	
	public String getEdocGrpIdxNo() {
		return edocGrpIdxNo;
	}

	public void setEdocGrpIdxNo(String edocGrpIdxNo) {
		this.edocGrpIdxNo = edocGrpIdxNo;
	}
	
	@Override
	public String toString() {
		return JsonUtils.ObjectPrettyPrint(this);
	}

}

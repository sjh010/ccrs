package com.mobileleader.image.server.model.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobileleader.image.util.JsonUtil;

/**
 * 파일 다운로드 요청 request
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.20
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DownloadRequest {

	@NotEmpty
	@Length(max=20)
	private String fileId;

	public DownloadRequest() {
		super();
	}
	public DownloadRequest(String fileId) {
		super();
		this.fileId = fileId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}
	
	/**
	 * set 한 정보들을 json 형태로 리턴한다.
	 * @return
	 */
/*	public String getCryptoJson() {
		String json = getJson();
		return AesBaseCrypto.enCrypto(json);
	}
*/	
	 
	
}

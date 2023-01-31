package com.mobileleader.image.server.model.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gson.Gson;
import com.mobileleader.image.util.JsonUtil;

/**
 * 파일 다운로드에 대한 응답
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.23
 */
@JsonAutoDetect
@JsonInclude(Include.NON_NULL)
public class DownloadResponse extends BaseResponse {

	private String fileName;					// 파일 명
	private long length;						// 파일 사이즈
	private byte[] buffer = null;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	
	public byte[] getBuffer() {
		if(this.buffer == null) {
			return null;
		}
		
		byte[] _buffer = new byte[this.buffer.length];
		
		for(int i=0; i<this.buffer.length; i++) {
			_buffer[i] = this.buffer[i];
		}
		return _buffer;
	}
	public void setBuffer(byte[] buffer) {
		if(buffer != null) {
			this.buffer = new byte[buffer.length];
			
			for(int i=0; i<buffer.length; i++) {
				this.buffer[i] = buffer[i];
			}
		}
	}
	
	// 버퍼는 출력되지 않도록 처리.
	public String getJson() {
		DownloadResponse _this = new DownloadResponse();
		_this.setLength(length);
		_this.setFileName(fileName);	
		return new Gson().toJson(_this);
	}
	
	// json으로 출력하면 불필요하게 엄청나게 큰 buffer데이터를 축력하게 되므로 아래와 같이 제거한다. 
	@Override
	public String toString() {
		DownloadResponse _this = new DownloadResponse();
		_this.setResultCode(getResultCode());
		_this.setResultMessage(getResultMessage());
		_this.setLength(length);
		_this.setFileName(fileName);		
		return JsonUtil.ObjectPrettyPrint(_this);
	}
}

package com.mobileleader.image.server.model.request;

import javax.validation.constraints.AssertTrue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobileleader.image.db.dto.ImageFileDto;
import com.mobileleader.image.db.dto.ImageStoreDto;
import com.mobileleader.image.util.JsonUtil;

/**
 * 이미지 검색 요청 request
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.23
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SearchRequest {
	
	private ImageStoreDto imageStoreDto;		// 이미지 업무 정보
	
	private ImageFileDto imageFileDto;			// 이미지 파일 정보
	
	/*
	 * SearchRequest 검증.
	 * 	mainKey - @NotNull 	@Max(12) @Min(?)
	 * 	docId 	- 			@Max(10) @Min(?)
	 */
	@AssertTrue(message="check SearchRequest")
	public boolean isValid() {

		imageFileDto = (imageFileDto == null)? new ImageFileDto() : imageFileDto;
		
		if(imageStoreDto != null) {
			imageFileDto.setMainKey(imageStoreDto.getMainKey());
		}
		
		if(imageFileDto.getMainKey() == null || imageFileDto.getMainKey().length() > 14) {
			return false;
		}
		if(imageFileDto.getDocId() != null && imageFileDto.getDocId().length() > 10) {
			return false;
		}
		if(imageFileDto.getVersionInfo() < 0) {
			return false;
		}
		return true;
	}
	
	public SearchRequest setImageStoreDto(ImageStoreDto imageStoreDto) {
		this.imageStoreDto = imageStoreDto;
		return this;
	}

	public ImageFileDto getImageFileDto() {
		return imageFileDto;
	}

	public SearchRequest setImageFileDto(ImageFileDto imageFileDto) {
		this.imageFileDto = imageFileDto;
		return this;
	}

	@Override
	public String toString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}
	
}

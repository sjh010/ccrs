package com.mobileleader.image.server.model.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mobileleader.image.db.dto.ImageFileDto;
import com.mobileleader.image.db.dto.ImageStoreDto;
import com.mobileleader.image.util.JsonUtil;

/**
 * 이미지 검색에 의해 반환되는 정보
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.23
 *
 */
@JsonAutoDetect
@JsonInclude(Include.NON_NULL)
public class SearchResponse extends BaseResponse {
	
	private ImageStoreDto imageStoreDto;		// 메인키
	private List<ImageFileDto> imageFileDto; 	// 이미지 정보
 
	public ImageStoreDto getImageStoreDto() {
		return imageStoreDto;
	}

	public void setImageStoreDto(ImageStoreDto imageStoreDto) {
		this.imageStoreDto = imageStoreDto;
	}

	public List<ImageFileDto> getImageFileDto() {
		return imageFileDto;
	}

	public void setImageFileDto(List<ImageFileDto> imageFileDto) {
		this.imageFileDto = imageFileDto;
	}
	
	public void setImageFileDto(ImageFileDto imageFileDto) {
		if (this.imageFileDto == null) {
			this.imageFileDto = new ArrayList<ImageFileDto>();
		}
		this.imageFileDto.add(imageFileDto);
	}
	
	@Override
	public String toString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}
 
}
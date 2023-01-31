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
 * 파일 업로드에 대한 응답
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.23
 * @update 2019.06.15 - imageFileDto 필드추가
 * @update 2019.06.26 - ImageStoreDtos 필드추가
 *  
 */
@JsonAutoDetect
@JsonInclude(Include.NON_NULL)
public class UploadResponse extends BaseResponse{

	ImageStoreDto imageStoreDto;
	List<ImageFileDto> imageFileDtos;		

	public ImageStoreDto getImageStoreDto() {
		return imageStoreDto;
	}

	public void setImageStoreDto(ImageStoreDto imageStoreDto) {
		this.imageStoreDto = imageStoreDto;
	}

	public List<ImageFileDto> getImageFileDtos() {
		return imageFileDtos;
	}

	public void setImageFileDtos(List<ImageFileDto> imageFileDtos) {
		this.imageFileDtos = imageFileDtos;
	}
	
	public void setImageFileDtos(ImageFileDto imageFileDto) {
		if (this.imageFileDtos == null) {
			this.imageFileDtos = new ArrayList<ImageFileDto>();
		}
		this.imageFileDtos.add(imageFileDto);
	}


	@Override
	public String toString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}
 
}
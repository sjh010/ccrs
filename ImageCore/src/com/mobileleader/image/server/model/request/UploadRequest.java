package com.mobileleader.image.server.model.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobileleader.image.db.dto.ImageFileDto;
import com.mobileleader.image.db.dto.ImageStoreDto;
import com.mobileleader.image.util.JsonUtil;

/**
 * 업로드 요청 request
 * 
 * @author bkho@mobileleader.com
 * @since 2019.06.24
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UploadRequest {
	
	@NotNull
	@Valid
	private ImageStoreDto imageStoreDto;
	
	@NotNull
	@Valid
	private List<ImageFileDto> imageFileDtos;

	public ImageStoreDto getImageStoreDto() {
		return imageStoreDto;
	}

	public UploadRequest setImageStoreDto(ImageStoreDto imageStoreDto) {
		this.imageStoreDto = imageStoreDto;
		return this;
	}

	public List<ImageFileDto> getImageFileDtos() {
		return imageFileDtos;
	}

	public UploadRequest setImageFileDtos(List<ImageFileDto> imageFileDtos) {
		this.imageFileDtos = imageFileDtos;
		return this;
	}
	public UploadRequest setImageFileDtos(ImageFileDto imageFileDto) {
		if (imageFileDtos == null) {
			imageFileDtos = new ArrayList<ImageFileDto>();
		}
			
		this.imageFileDtos.add(imageFileDto);
		return this;
	}

	/**
	 * json 데이터를 예쁘게 출력한다.
	 */
	@Override
	public String toString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}
}
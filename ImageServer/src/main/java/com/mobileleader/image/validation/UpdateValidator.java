package com.mobileleader.image.validation;

import java.util.List;

import javax.validation.Validator;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.mobileleader.image.db.dto.ImageFileDto;
import com.mobileleader.image.db.dto.ImageStoreDto;
import com.mobileleader.image.server.model.request.UploadRequest;
import com.mobileleader.image.server.model.validation.ValidationGroup;

/**
 * 보완스캔(파일 업데이트) 요청 Validator
 */
@Service
public class UpdateValidator implements org.springframework.validation.Validator{
	
	@Autowired
	private Validator validator;
	
	private SpringValidatorAdapter adapter;

	@Override
	public boolean supports(Class<?> clazz) {
		return UploadRequest.class.isAssignableFrom(clazz);
	}


	@Override
	public void validate(Object target, Errors errors) {
		adapter = new SpringValidatorAdapter(validator);
		
		UploadRequest request = (UploadRequest)target;
		
		imageStoreDtoValidation(request, errors);
		
		imageFileDtosValidation(request, errors);
	}
	
	/*
	 * ImageStoreDto validation
	 * 	->	업로드 요청과 동일하게 검증
	 */
	private void imageStoreDtoValidation(UploadRequest request, Errors errors) {
		ImageStoreDto imageStoreDto = request.getImageStoreDto();
		
		if(imageStoreDto == null) {
			errors.rejectValue("imageStoreDto", null, "must not be NULL");
		}else {
			errors.pushNestedPath("imageStoreDto");
			
			adapter.validate(imageStoreDto, errors);
			
			errors.popNestedPath();
		}
	}
	
	/*
	 * ImageFileDto validation
	 * 	->	삭제처리의 경우(deleteYn=Y), 최소한의 정보만 검증 진행
	 * 		그 외 수정 또는 추가의 경우, 업로드 요청 검증과 동일하게 진행
	 */
	private void imageFileDtosValidation(UploadRequest request, Errors errors) {
		List<ImageFileDto> imageFileDtos = request.getImageFileDtos();
		
		if(imageFileDtos == null || imageFileDtos.size() <= 0) {
			errors.rejectValue("imageFileDtos", null, "must not be Empty");
		}else {
		
			for(int i=0; i<imageFileDtos.size(); i++) {
				errors.pushNestedPath("imageFileDto["+ i +"]");
				
				ImageFileDto imageFileDto = imageFileDtos.get(i);
				
				if(imageFileDto.getDeleteYn() != null && imageFileDto.getDeleteYn().equalsIgnoreCase("Y")) {
					adapter.validate(imageFileDto, errors, ValidationGroup.Update.class);
					
				}else {
					adapter.validate(imageFileDto, errors, Default.class);
				}
				
				errors.popNestedPath();
			}
		}
	}
	
}

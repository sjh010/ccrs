package com.mobileleader.image.service;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mobileleader.image.db.dto.ImageFileDto;
import com.mobileleader.image.db.dto.ImageStoreDto;
import com.mobileleader.image.db.mapper.ImageFileMapper;
import com.mobileleader.image.db.mapper.ImageStoreMapper;
import com.mobileleader.image.exception.ServiceError;
import com.mobileleader.image.exception.ServiceException;
import com.mobileleader.image.server.model.request.UploadRequest;

/**
 * 이미지 보완스캔 업로드(파일 업데이트) 서비스 구현 클래스
 * 
 * @author yh.kim
 */
@Service("update")
public class UpdateServiceImpl extends UploadServiceImpl{
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateServiceImpl.class);

	@Override
	public void preProcessing(UploadRequest req) {
		
		// 1. ImageStoreDto setting
		setImageStoreDto(req);
		
		ImageStoreMapper imageStoreMapper = sqlSession.getMapper(ImageStoreMapper.class);
		
		// 2. ImageStoreDto validation
		// : 해당 mainKey에대한 imageStore데이터 없다면 invalid
		ImageStoreDto oldImageStoreDto = imageStoreMapper.selectByPrimaryKey(req.getImageStoreDto().getMainKey());
		
		if(oldImageStoreDto == null) {
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, "Invalid MainKey");
		}
		
		// 3. ImageFileDto 처리
		ImageFileMapper imageFileMapper = sqlSession.getMapper(ImageFileMapper.class);
		
		Iterator<ImageFileDto> iter = req.getImageFileDtos().iterator();
		while(iter.hasNext()) {
			ImageFileDto imageFileDto = iter.next();
			
			// ImageFileDto setting
			setImageFileDto(imageFileDto, req.getImageStoreDto().getMainKey());
			
			// 삭제처리된 파일 -> ECM업로드 진행하지 않기때문에 업로드 전 DB처리 후 imageFileDto 리스트에서 제거
			if(imageFileDto.getDeleteYn() != null && imageFileDto.getDeleteYn().equalsIgnoreCase("Y")) {
				
				int effect = imageFileMapper.updateDeleteYn(imageFileDto);
				if(effect <= 0) {
					throw new ServiceException(ServiceError.SQL_ERROR, "ImageFile Table Update Failed");
				}
				
				logger.debug("imageFileMapper.update: " + effect);	
				
				iter.remove();
			}
		}
	}
}

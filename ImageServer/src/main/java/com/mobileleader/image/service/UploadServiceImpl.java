package com.mobileleader.image.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.api.common.exception.EcmApiException;
import com.ecm.api.common.model.EcmApiResponse;
import com.inzisoft.util.AESCrypto;
import com.inzisoft.util.InziCryptoException;
import com.mobileleader.image.db.dto.ImageFileDto;
import com.mobileleader.image.db.dto.ImageStoreDto;
import com.mobileleader.image.db.mapper.ImageFileMapper;
import com.mobileleader.image.db.mapper.ImageStoreMapper;
import com.mobileleader.image.exception.ServiceError;
import com.mobileleader.image.exception.ServiceException;
import com.mobileleader.image.server.model.request.UploadRequest;
import com.mobileleader.image.server.model.response.UploadResponse;
import com.mobileleader.image.util.CryptoSHA2;

/**
 * ECM 파일 업로드 서비스 구현 클래스
 * 
 * @author bkcho@mobileleader.com
 * @since 2019.06.24
 *
 */
@Service("upload")
public class UploadServiceImpl implements UploadService{
	
	private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
	
	@Autowired(required=true)
	protected EcmService ecmService;
	
	@Autowired
	protected SqlSessionTemplate sqlSession;
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor= {Exception.class})
	public UploadResponse upload(UploadRequest req, Map<String, MultipartFile> fileMap) throws Exception{
		logger.debug("in uploadService");
		
		UploadResponse res = new UploadResponse();
		
		//1. ECM Upload 전 프로세스
		preProcessing(req);
		
		//2. ECM Upload
		EcmUploadProcess(req, fileMap);
		
		//3. ECM Upload 후 DB처리
		storageDB(req);
		
		//response setting
		res.setImageStoreDto(req.getImageStoreDto());				
		res.setImageFileDtos(req.getImageFileDtos());
		res.setResultCode(200);
		
		return res;	
	}
	
	public void preProcessing(UploadRequest req) {
		
		String mainKey = req.getImageStoreDto().getMainKey();

		//1. ImageStoreDto setting
		setImageStoreDto(req);
		
		//2. ImageFileDto setting
		for(ImageFileDto imageFileDto : req.getImageFileDtos()) {
			setImageFileDto(imageFileDto, mainKey);
		}	
	}
	
	protected void setImageStoreDto(UploadRequest req) {
		ImageStoreDto imageStoreDto = req.getImageStoreDto();
		
		// IMAGE_FILE 의 객체수를 카운트하여 FILE_STORE.DOC_MAPPING_COUNT 컬럼에 저장
		imageStoreDto.setDocMappingCount(req.getImageFileDtos().size());
		
		// 주민번호 암호화
		if(imageStoreDto.getMemo() != null) {
			imageStoreDto.setMemo(CryptoSHA2.getIdEnc(imageStoreDto.getMemo()));
		}
	}
	
	protected void setImageFileDto(ImageFileDto imageFileDto, String mainKey) {
		// IMAGE_FILE.MAIN_KEY 에 mainkey 셋팅
		imageFileDto.setMainKey(mainKey);
		
		// 파일 path에서 파일 name으로 변경하여 저장.
		String fileName = FilenameUtils.getName(imageFileDto.getFileName());
		imageFileDto.setFileName(fileName);
	}
	
	/*
	 * ECM Upload 프로세스
	 * 1. 파일 암호화
	 * 2. upload
	 * 3. set fileId
	 */
	public void EcmUploadProcess(UploadRequest req, Map<String, MultipartFile> fileMap) throws Exception {
		logger.debug("in EcmUploadProcess");
		
		EcmApiResponse ecmApiRes = null;
		InputStream is = null;
		String insourceId = req.getImageStoreDto().getInsourceId();
		
		// 마이그레이션용
		String createTime = req.getImageStoreDto().getCreateTime();
		
		try {
						
			for(ImageFileDto imageFileDto : req.getImageFileDtos()) {
				
				// ImageFile & MultipartFile 매칭
				String fileName = imageFileDto.getFileName();
				MultipartFile mpf = fileMap.get(fileName);
				
				if(mpf == null) {
					throw new ServiceException(ServiceError.NOT_FOUND_FILE, "file not found. fileName = " + fileName);
				}
				
				logger.info("docId = {}, fileName = {}",imageFileDto.getDocId(), fileName);
				
				// 파일 암호화
	            byte[] encBuffer = AESCrypto.encryptFileMem(mpf.getBytes());
	           
	            is = new ByteArrayInputStream(encBuffer);
	            
	            
	    		// 마이그레이션용 코드	            
	            logger.info("mig@start");
	            int mainKeyLen = req.getImageStoreDto().getMainKey().length(); 
				if (mainKeyLen <= 6) {
					logger.info("mig@len: " + mainKeyLen);
					fileName = req.getImageStoreDto().getMainKey() + ".pdf"; 
					logger.info("mig@str: " + fileName);					
					imageFileDto.setFileName(fileName);					
				}
				
	            // ECM 업로드
				ecmApiRes = ecmService.upload(insourceId, imageFileDto.getDocType(), fileName, is, encBuffer.length, createTime);
	            //ecmApiRes = ecmService.upload(insourceId, fileName, is, encBuffer.length);
	            
				if(ecmApiRes == null || !ecmApiRes.isSuccess()) {
					throw new ServiceException(ServiceError.ECM_UPLOAD_FAILED, String.format("ECM_ErrorCode : %d \t ECM_ErrorMsg : %s", ecmApiRes.getErrorCode(), ecmApiRes.getErrorMessage()));
				}
				
	            logger.info("[ECM Upload End] docId={}, fileId = {}", imageFileDto.getDocId(), ecmApiRes.getFileId());
	            
	            // set fileId
	            imageFileDto.setFileId(ecmApiRes.getFileId());
			}

		} catch(EcmApiException e) {
			throw new ServiceException(ServiceError.ECM_ERROR, String.format("[Ecm Api Error] ECM_ErrorCode : %d \t ECM_ErrorMsg : %s", e.getServiceError().getCode(), e.getMessage()));
		} catch(InziCryptoException | IOException e){
			throw new ServiceException(ServiceError.FILE_ENCRYPT_ERROR);
		} finally {
			if(is != null) {
				is.close();
			}
		}
	}
	
	/*
	 * DB 저장
	 * (imageStore의 docMappingcount 계산하기 위해 imageFile데이터를 먼저 저장한다.)
	 */
	public void storageDB(UploadRequest req) {
		logger.debug("storageDB PostProcessing.");
		
		//1. IMAGE_FILE table insert/update
		if(!storageImageFile(req)) {
			throw new ServiceException(ServiceError.SQL_ERROR, "ImageFile Table Insert Failed");
		}
		
		//2. IMAGE_STORE table insert/update
		if(!storageImageStore(req)) {
			throw new ServiceException(ServiceError.SQL_ERROR, "ImageStore Table Update Failed");
		}
		
	}
	
	protected boolean storageImageFile(UploadRequest req) {
		
		ImageFileMapper imageFileMapper = sqlSession.getMapper(ImageFileMapper.class);
		
		for(ImageFileDto imageFileDto : req.getImageFileDtos()) {
			
			//버전 업그레이드하여 insert
			int effect = imageFileMapper.insertWithVersionInfo(imageFileDto);

			if (effect <= 0) {
				return false;
			}
			
			logger.debug("imageFileMapper.update: " + effect);		
		}
		
		return true;
	}
	
	protected boolean storageImageStore(UploadRequest req) {
		
		ImageStoreDto imageStoreDto = req.getImageStoreDto();
		ImageStoreMapper imageStoreMapper = sqlSession.getMapper(ImageStoreMapper.class);
		
		// 이미 등록된 Image Store 있을경우 update, 없을경우  insert
		ImageStoreDto oldImageStoreDto = imageStoreMapper.selectByPrimaryKey(imageStoreDto.getMainKey());
		
		int effect = 0;
		if(oldImageStoreDto == null) {
			effect = imageStoreMapper.insertWithMappingCount(imageStoreDto);
		}else {
			effect = imageStoreMapper.updateWithMappingCount(imageStoreDto);
		}
		
		if (effect <= 0) {
			return false;
		}
		
		logger.debug("storeMapper.insert: " + effect);
		
		return true;
	}

}

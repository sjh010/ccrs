package com.mobileleader.edoc.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mobileleader.edoc.db.dto.EdocGrpBzwkInfoDto;
import com.mobileleader.edoc.db.dto.EdocGrpProcsMgmtDto;
import com.mobileleader.edoc.db.mapper.EdocGrpBzwkInfoMapper;
import com.mobileleader.edoc.db.mapper.EdocGrpProcsMgmtMapper;
import com.mobileleader.edoc.exception.EdocServerException;
import com.mobileleader.edoc.exception.EdocServerStatus;
import com.mobileleader.edoc.util.FileUtils;

/**
 * 파일 업로드 서비스 공통 함수 정의 클래스
 * 
 * @author yh.kim
 */
@Service
public abstract class UploadServiceAbstract implements UploadService {
	
	private Logger logger = LoggerFactory.getLogger(UploadServiceAbstract.class);
	
	@Autowired
	protected EdocGrpProcsMgmtMapper procsMgmtMapper;	
	
	@Autowired
	private EdocGrpBzwkInfoMapper bzwkInfoMapper;
	
	/*
	 * 해당 전자문서그룹의 문서 관리정보 조회
	 */
	protected EdocGrpProcsMgmtDto chkEdocProcessingStatus(String edocGrpIdxNo) {
		
		EdocGrpProcsMgmtDto grpProcsMgmtVo = procsMgmtMapper.selectByPk(edocGrpIdxNo);
		
		//조회 결과 null -> 서버로부터 발급받지 않은 전자문서키 -> invalid request
		if(grpProcsMgmtVo == null) {
			throw new EdocServerException(EdocServerStatus.INVALID_EDOC_KEY);
		}
		
		return grpProcsMgmtVo;
	}
	
	
	/*
	 * 업로드된 MultipartFile 서버에 저장 후 저장경로 리턴
	 */
	protected String transferFile(String folderPath, String edocGrpIdxNo, MultipartFile file) {
		String fileName = edocGrpIdxNo + ".zip";
		
		if(FileUtils.uploadFile(folderPath, fileName, file)) {
			return new File(folderPath, fileName).getAbsolutePath();
		}else {
			throw new EdocServerException(EdocServerStatus.FILE_TRANSFER_ERROR);
		}
	}
	
	/*
	 * EDS_ELEC_DOC_GROUP_PROCS_MGMT table update
	 */
	protected void updateProcsMgmtTable(EdocGrpProcsMgmtDto procsMgmtVo) {
		logger.debug("in updateProcsMgmtTable");
		
		int result = procsMgmtMapper.update(procsMgmtVo);
		if(result <= 0) {
			throw new EdocServerException(EdocServerStatus.DB_UPDATE_ERROR, "EDS_ELEC_DOC_GROUP_PROCS_MGMT table Update Failed");
		}
	}
	
	
	/*
	 * EDS_ELEC_DOC_GROUP_BZWK_INFO table update
	 */
	protected void updateBzwkInfoTable(EdocGrpBzwkInfoDto bzwkInfoDto) {
		logger.debug("in updateBzwkInfoTable");
		
		int result = bzwkInfoMapper.insertOrUpdate(bzwkInfoDto);
		if(result <= 0) {
			throw new EdocServerException(EdocServerStatus.DB_UPDATE_ERROR, "EdsElecDocGroupBzwkInfo Table Update Failed.");
		}
	}
}

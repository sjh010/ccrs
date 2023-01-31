package com.mobileleader.edoc.service;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mobileleader.edoc.db.dto.EdocGrpBzwkInfoDto;
import com.mobileleader.edoc.db.dto.EdocGrpProcsMgmtDto;
import com.mobileleader.edoc.db.dto.EdocGrpTmpStrgMgmtDto;
import com.mobileleader.edoc.db.mapper.EdocGrpTmpStrgMgmtMapper;
import com.mobileleader.edoc.exception.EdocServerException;
import com.mobileleader.edoc.exception.EdocServerStatus;
import com.mobileleader.edoc.model.response.UploadResponse;
import com.mobileleader.edoc.properties.EdocServiceProp;
import com.mobileleader.edoc.type.ProcsStepCd;
import com.mobileleader.edoc.type.ProcsStepMsgCd;
import com.mobileleader.edoc.type.ProcsStepStCd;
import com.mobileleader.edoc.util.DateUtils;

/**
 * 전자문서 임시저장 서비스 클래스
 * 
 * @author yh.kim
 */
@Service("uploadTempData")
public class UploadTempDataService extends UploadServiceAbstract{
	
	private Logger logger = LoggerFactory.getLogger(UploadTempDataService.class);
	
	@Autowired
	private EdocGrpTmpStrgMgmtMapper tmpStrgMgmtMapper;

	
	/*
	 * 업로드된 ZIP 파일 저장 경로
	 * $(DATA_HOME_PATH)/[yyyyMMdd]/[부점코드]/[전자문서키]/tmpr/
	 */
	private String folderPath(EdocGrpBzwkInfoDto bzwkInfoDto) {
		String folderPath = EdocServiceProp.DATA_HOME_PATH()
							+ DateUtils.toString(bzwkInfoDto.getRegTime(), null)	+ File.separator 
							+ bzwkInfoDto.getDsrbCd()                          		+ File.separator 
							+ bzwkInfoDto.getElecDocGroupInexNo()                   + File.separator
							+ EdocServiceProp.TMPR_FILE_PATH()
							;
		return folderPath;
	}
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.NESTED, rollbackFor=Exception.class)
	public UploadResponse upload(EdocGrpBzwkInfoDto request, MultipartFile file) throws Exception {
		logger.info("[Upload TempData Service - START]");
		
		UploadResponse response = new UploadResponse();
		EdocGrpTmpStrgMgmtDto tempMgmtDto = (EdocGrpTmpStrgMgmtDto)request;
		
		String edocIdxNo = tempMgmtDto.getElecDocGroupInexNo();
		tempMgmtDto.setRegTime(new Date());															//파일 업로드 시각
		
		try {
			// 1. 전자문서키 & 문서 처리상태 확인 
			EdocGrpProcsMgmtDto procsMgmtVo = chkEdocProcessingStatus(edocIdxNo);				
			
			procsMgmtVo.setProcsStepStTime(new Date());												//프로세스 시작 시각
			
			// 2. 파일 처리		
			String svrFilePath = transferFile(folderPath(tempMgmtDto), edocIdxNo, file);							
			tempMgmtDto.setSvrFile(svrFilePath);													//파일 저장 경로
			
			// 전자문서 그룹 관리정보 저장하기위해 procsMgmtVo에 처리 결과 set
			procsMgmtVo.setElecDocGroupInexNo	(edocIdxNo);
			procsMgmtVo.setSvrIp				(InetAddress.getLocalHost().getHostAddress());		// 서버 IP
			procsMgmtVo.setProcsStepCd      	(ProcsStepCd.START.getCdVal());						// 처리단계코드 		PROCS_STEP_CD : 00
			procsMgmtVo.setProcsStepStcd     	(ProcsStepStCd.SUCCESS.getCdVal());					// 처리단계상태코드 	PROCS_STEP_STCD : 1
			procsMgmtVo.setProcsStepMsgCd    	(ProcsStepMsgCd.DEFAULT.getCdVal());				// 처리단계메시지코드 	PROCS_STEP_MSG_CD : AAAAAA
			procsMgmtVo.setProcsStepEdTime		(new Date());										// 프로세스 완료 시각
			
			// 3.DB Update
			//전자문서그룹 관리정보테이블에 파일 처리정보 등록
			updateProcsMgmtTable(procsMgmtVo);
			
			//전자문서그룹 임시저장 관리정보 등록
			updateTmpStrgMgmtTable(tempMgmtDto);
			
			//업무정보 테이블 업데이트
			updateBzwkInfoTable(tempMgmtDto);
			
		}catch(UnknownHostException e) {
			throw new EdocServerException(EdocServerStatus.UNKNOWN_HOST_ERROR);
		}
		
		logger.info("[Upload TempData Service - END]");
	
		response.setEdocGrpIdxNo(edocIdxNo);
		response.setResult(EdocServerStatus.OK);

		return response;
	}
	
	/*
	 * EDS_ELEC_DOC_GROUP_TMP_STRG_MGMT table update
	 */
	private void updateTmpStrgMgmtTable(EdocGrpTmpStrgMgmtDto tempMgmtDto) {
		logger.info("[Update TmpStrgMgmt Table - Start]");
		logger.debug("tempMgmtDto = {}", tempMgmtDto);
		
		//임시저장 데이터 없을 경우, insert. 있을 경우, update.
		int res = tmpStrgMgmtMapper.insertOrUpdate(tempMgmtDto);
		
		if(res <= 0) {	
			throw new EdocServerException(EdocServerStatus.DB_UPDATE_ERROR, "EdsElecDocGroupTmpStrgMgmt Table Update Failed.");
		}
	}
}

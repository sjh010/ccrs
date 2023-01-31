package com.mobileleader.edoc.service;

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

import com.mobileleader.edoc.db.dto.EdocGrpBzwkInfoDto;
import com.mobileleader.edoc.db.dto.EdocGrpProcsMgmtDto;
import com.mobileleader.edoc.db.mapper.CommonMapper;
import com.mobileleader.edoc.db.mapper.EdocGrpBzwkInfoMapper;
import com.mobileleader.edoc.db.mapper.EdocGrpProcsMgmtMapper;
import com.mobileleader.edoc.exception.EdocServerException;
import com.mobileleader.edoc.exception.EdocServerStatus;
import com.mobileleader.edoc.model.response.UploadResponse;
import com.mobileleader.edoc.properties.EdocServiceProp;
import com.mobileleader.edoc.type.ProcsStepCd;
import com.mobileleader.edoc.type.ProcsStepMsgCd;
import com.mobileleader.edoc.type.ProcsStepStCd;
import com.mobileleader.edoc.util.DateUtils;

/**
 * 전자문서그룹 인덱스번호(전자문서키)생성 서비스 Class
 * 
 * @author yh.kim
 */
@Service
public class GenerateEdocKeyService implements GenerateKeyService{
	
	private Logger logger = LoggerFactory.getLogger(GenerateEdocKeyService.class);
	
	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private EdocGrpProcsMgmtMapper procsMgmtMapper;
	
	@Autowired
	private EdocGrpBzwkInfoMapper bzwkInfoMapper;
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.NESTED, rollbackFor=Exception.class)
	public UploadResponse generate(EdocGrpBzwkInfoDto bzwkInfoDto) throws Exception {
		
		logger.info("[EdocGrpIdxNo Generate - START]");
		
		UploadResponse response = new UploadResponse();
		EdocGrpProcsMgmtDto edGrpMgmtVo = new EdocGrpProcsMgmtDto();
		
		//요청 작업 시작시간 체크
		edGrpMgmtVo.setProcsStepStTime(new Date());

		// 전자문서인덱스번호 채번
		String edocGrpIdxNo = getEdocGrpIdxNo();
		bzwkInfoDto.setElecDocGroupInexNo(edocGrpIdxNo);
		
		logger.debug("edocGrpIdxNo : " + edocGrpIdxNo);

		//요청 작업 끝난 시간 체크
		edGrpMgmtVo.setProcsStepEdTime(new Date());
		
		//DB 저장
		insertProcsMgmtTable(edGrpMgmtVo, edocGrpIdxNo);
		insertBzwkInfoTable(bzwkInfoDto);
		
		//응답 작성		
		response.setResult(EdocServerStatus.OK);
		response.setEdocGrpIdxNo(edocGrpIdxNo);
		
		logger.info("[EdocGrpIdxNo GEN - END] edocGrpIdxNo = {}", edocGrpIdxNo);
		
		return response;
	}
	
	/**
	 * 전자문서그룹 인덱스번호 생성 함수
	 * : 'E'(전자문서 생성서버 프로세스 ID) + 접수일(8:YYMMDDHH) + 일련번호(6:DB 시퀀스)
	 */
	private String getEdocGrpIdxNo() {

		StringBuilder sb = new StringBuilder();

		sb.append(EdocServiceProp.EDOC_PROCESS_ID());
		sb.append(DateUtils.getCurrentDateString("yyMMddHH"));
		sb.append(String.format("%06d", Integer.parseInt(commonMapper.getNextSeqNo())));
		
		return sb.toString();
	}
	
	/**
	 * 전자문서그룹 '처리관리' 테이블에 획득한 전자문서인덱스번호 및 필요한 정보 등록
	 * 
	 * @param edocGrpProcsMgmtVo
	 * @param edocGrpIdxNo
	 * @throws UnknownHostException
	 */
	private void insertProcsMgmtTable(EdocGrpProcsMgmtDto edGrpMgmtVo, String edocGrpIdxNo) {
		
		String svrIp;
		try {
			svrIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new EdocServerException(EdocServerStatus.UNKNOWN_HOST_ERROR);
		}
		
		edGrpMgmtVo.setElecDocGroupInexNo(edocGrpIdxNo);								// 전자문서그룹 인덱스번호
		edGrpMgmtVo.setCrtnTime(new Date());											// DB등록 시간
		edGrpMgmtVo.setProcsStepCd(ProcsStepCd.START.getCdVal());						// 처리단계코드 - 00(시작)
		edGrpMgmtVo.setProcsStepStcd(ProcsStepStCd.PROCESSING.getCdVal());				// 처리단계 상태코드 - 0(진행중)
		edGrpMgmtVo.setProcsStepMsgCd(ProcsStepMsgCd.DEFAULT.getCdVal());				// 처리단계 메세지코드 - AAAAAA
		edGrpMgmtVo.setSvrIp(svrIp);													// 서버 IP
		
		logger.debug(edGrpMgmtVo.toString());
		
		int resCnt = procsMgmtMapper.insert(edGrpMgmtVo);
		if(resCnt <= 0) {
			throw new EdocServerException(EdocServerStatus.DB_INSERT_ERROR, "EdsElecDocGroupProcsMgmt Table Insert Error");
		}
		
		logger.debug("[insert in EDS_ELEC_DOC_GROUP_PROCS_MGMT table - COMPLETE]");
	}
	
	/**
	 * 전자문서그룹 업무정보 테이블에 데이터 추가
	 * 
	 * @param bzwkDto
	 */
	private void insertBzwkInfoTable(EdocGrpBzwkInfoDto bzwkInfoDto) {
		int result = bzwkInfoMapper.insert(bzwkInfoDto);
		if(result <= 0) {
			throw new EdocServerException(EdocServerStatus.DB_INSERT_ERROR, "EdsElecDocGroupBzwkInfoTable Insert Error");
		}
		
		logger.debug("[insert in EDS_ELEC_DOC_GROUP_BZWK_INFO table - COMPLETE]");
	}
}


package com.mobileleader.edoc.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobileleader.edoc.db.dto.EdocGrpBzwkInfoDto;
import com.mobileleader.edoc.db.dto.EdocGrpTmpStrgMgmtDto;
import com.mobileleader.edoc.db.mapper.EdocGrpTmpStrgMgmtMapper;
import com.mobileleader.edoc.exception.EdocServerException;
import com.mobileleader.edoc.exception.EdocServerStatus;
import com.mobileleader.edoc.model.response.SearchResponse;

/**
 * 임시저장 데이터 조회 클래스
 * 
 * @author yh.kim
 */
@Service
public class SearchTmpStrgMgmtService implements SearchService{
	
	private Logger logger = LoggerFactory.getLogger(SearchTmpStrgMgmtService.class);
	
	@Autowired
	private EdocGrpTmpStrgMgmtMapper tmpStrgMgmtMapper;

	/*
	 * 임시저장 관리정보 목록 조회
	 */
	@Transactional(readOnly=true)
	@Override
	public SearchResponse search(EdocGrpBzwkInfoDto request) throws Exception {
		logger.info("[SearchTempList Service - START]");
		
		SearchResponse response = new SearchResponse();
		EdocGrpTmpStrgMgmtDto tmpMgmtDto = (EdocGrpTmpStrgMgmtDto)request;
		
		//지정한 조건에 해당하는 임시저장 데이터 리스트 조회
		List<EdocGrpTmpStrgMgmtDto> resultDtoList = tmpStrgMgmtMapper.selectList(tmpMgmtDto);

		if(resultDtoList == null || resultDtoList.size() <= 0) {
			logger.info("[selected TbEdsElecDocGroupTmpStrgMgmtVo List is NULL]");
			response.setResult(EdocServerStatus.NO_TMP_LIST);
			response.setTmpDataList(resultDtoList);
			return response;
			//throw new EdocServerException(EdocServerStatus.NO_TMP_LIST);
		}
		
		logger.info("[select TmpStrgMgmtVo List - SUCCESS]");
		
		response.setTmpDataList(resultDtoList);
		response.setResult(EdocServerStatus.OK);
		
		return response;
	}

	@Override
	public String searchSvrFilePath(EdocGrpBzwkInfoDto request) throws Exception {
		logger.info("[SearchTempData Service - START]");
		
		EdocGrpTmpStrgMgmtDto tmpDto = (EdocGrpTmpStrgMgmtDto)request;
		String edocKey = tmpDto.getElecDocGroupInexNo();
		String mappingKey = tmpDto.getMappingKey();
		
		//1. 지정한 조건에 해당하는 임시저장 관리정보 조회
		EdocGrpTmpStrgMgmtDto resultVo = tmpStrgMgmtMapper.selectByPk(edocKey, mappingKey);
		if(resultVo == null) {
			logger.info("[selected TbEdsElecDocGroupTmpStrgMgmtVo Data is NULL] edocKey = {}, mappingKey = {}", edocKey, mappingKey);
			throw new EdocServerException(EdocServerStatus.NO_TMP_DATA);
		}
		
		String svrFilePath = resultVo.getSvrFile();
		logger.debug("svrFilePath = {}", svrFilePath);
		
		File svrFile = new File(svrFilePath);
		
		//서버에 파일 존재하지 않는 경우(이미 BPR 입고되어 중간 파일들이 삭제된 경우 or 이관되어 조회할 수 없는 경우)
		if(!svrFile.exists()) {
			
			//임시저장 이관 여부 확인(저장되어있는 정보와 요청정보가 다른 경우 조회 불가)
			if(!tmpDto.getDsrbCd().equals(resultVo.getDsrbCd()) 
						|| !tmpDto.getHndrNo().equals(resultVo.getHndrNo())) {
				throw new EdocServerException(EdocServerStatus.NO_AUTHORITY, "Authority has been Transferred");
			}

			logger.error("[TempData File NOT EXIST] edocKey = {}, mappingKey = {}, svrFile = {}", edocKey, mappingKey, svrFilePath);
			
			throw new EdocServerException(EdocServerStatus.TMP_FILE_NOT_EXIST);
		}
		
		logger.info("[SearchTempData Service - END]");
		
		return svrFilePath;
	}

}

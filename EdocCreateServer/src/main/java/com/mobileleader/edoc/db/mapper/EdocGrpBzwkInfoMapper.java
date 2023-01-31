package com.mobileleader.edoc.db.mapper;

import java.util.List;

import com.mobileleader.edoc.db.dto.EdocGrpBzwkInfoDto;
import com.mobileleader.edoc.db.dto.EdocGrpProcsMgmtDto;
import com.mobileleader.edoc.db.dto.EdocGrpTmpStrgMgmtDto;

/**
 * 전자문서그룹에 대한 업무정보 관리 테이블 DAO (EDS_ELEC_DOC_GROUP_BZWK_INFO table)
 * 
 */
public interface EdocGrpBzwkInfoMapper {

	/*
	 * insert
	 */
	public int insert(EdocGrpBzwkInfoDto bzwkInfoVo);
	
	/*
	 * insert or update
	 */
	public int insertOrUpdate(EdocGrpBzwkInfoDto bzwkInfoVo);
	
	/*
	 * select one
	 */
	public EdocGrpBzwkInfoDto select(EdocGrpBzwkInfoDto condition);
	
	/*
	 * 사용되지 않은 전자문서그룹의 업무정보 데이터 삭제
	 */
	public int deleteProcsMgmtData(EdocGrpProcsMgmtDto delCondition);
	
	/*
	 * 임시저장 기간 만료된 전자문서그룹리스트의 업무정보 데이터 삭제
	 */
	public int deleteTmpMgmtData(List<EdocGrpTmpStrgMgmtDto> list);
}
